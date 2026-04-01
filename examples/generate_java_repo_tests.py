from __future__ import annotations

import argparse
from concurrent.futures import ThreadPoolExecutor, as_completed
import hashlib
import json
import re
import shutil
from dataclasses import dataclass
from datetime import datetime, timezone
import os
from pathlib import Path
import time

from unigener.models import FocalMethodRequest
from unigener.pipeline import JavaTestSandbox, UniGenerOrchestrator
from unigener.utils import current_session_id, ensure_session_id, resolve_log_path


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo", required=True, type=Path)
    parser.add_argument("--output-tests-dir", default="src/test/java/unigener")
    parser.add_argument("--llm-provider", default="openai", choices=["none", "openai", "ollama"])
    parser.add_argument("--llm-model", default="")
    parser.add_argument("--retrieval-mode", default="hybrid", choices=["ast", "lsp", "hybrid"])
    parser.add_argument("--max-files", type=int, default=0)
    parser.add_argument("--max-symbols", type=int, default=0)
    parser.add_argument("--worker-index", type=int, default=0)
    parser.add_argument("--worker-count", type=int, default=1)
    parser.add_argument("--disable-similar-cases", action="store_true")
    parser.add_argument("--auto-run-tests", action="store_true")
    parser.add_argument("--run-sandbox", action="store_true")
    parser.add_argument("--sandbox-output-json", type=Path, default=Path("sandbox_result.json"))
    parser.add_argument("--sandbox-test-selector", default="unigener.*")
    parser.add_argument("--sandbox-timeout-seconds", type=int, default=900)
    parser.add_argument("--sandbox-focal-file", type=Path, default=None)
    parser.add_argument("--results-output-json", type=Path, default=Path("results.json"))
    parser.add_argument("--concurrent-generate-workers", type=int, default=4)
    parser.add_argument("--sandbox-workers", type=int, default=4)
    parser.add_argument("--sandbox-isolated-workdir", action="store_true")
    parser.add_argument("--keep-worker-sandbox", action="store_true")
    parser.add_argument("--session-id", default="")
    return parser


def iter_java_files(repo_root: Path):
    for file_path in repo_root.rglob("*.java"):
        normalized = str(file_path).replace("\\", "/")
        if "/src/test/" in normalized or "/target/" in normalized:
            continue
        yield file_path


def extract_method_names(source: str) -> list[str]:
    names: list[str] = []
    pattern = re.compile(
        r"(public|protected|private)?\s*(static\s+)?(final\s+)?[\w<>\[\], ?]+\s+([A-Za-z_]\w*)\s*\(([^)]*)\)\s*\{"
    )
    for match in pattern.finditer(source):
        name = match.group(4)
        if name in {"if", "for", "while", "switch", "catch", "return", "new", "throw", "assert"}:
            continue
        names.append(name)
    deduped: list[str] = []
    seen: set[str] = set()
    for name in names:
        if name in seen:
            continue
        seen.add(name)
        deduped.append(name)
    return deduped


@dataclass(slots=True)
class TaskItem:
    focal_file: Path
    focal_symbol: str
    task_id: str


@dataclass(slots=True)
class GenerateResult:
    task: TaskItem
    generated_test_path: Path | None
    error: str = ""
    generate_queue_wait_ms: float = 0.0
    generate_elapsed_ms: float = 0.0
    generated_finished_at_perf: float = 0.0


def collect_tasks(args: argparse.Namespace, repo_root: Path) -> tuple[list[TaskItem], int]:
    tasks: list[TaskItem] = []
    scanned_files = 0
    task_index = 0
    processed_symbols = 0
    for java_file in iter_java_files(repo_root):
        scanned_files += 1
        if args.max_files and scanned_files > args.max_files:
            break
        source = java_file.read_text(encoding="utf-8")
        symbols = extract_method_names(source)
        for symbol in symbols:
            if args.max_symbols and processed_symbols >= args.max_symbols:
                break
            if task_index % args.worker_count != args.worker_index:
                task_index += 1
                continue
            task_index += 1
            processed_symbols += 1
            task_id = build_task_id(java_file, symbol)
            tasks.append(TaskItem(focal_file=java_file, focal_symbol=symbol, task_id=task_id))
        if args.max_symbols and processed_symbols >= args.max_symbols:
            break
    return tasks, processed_symbols


def run_single_generation(
    args: argparse.Namespace,
    repo_root: Path,
    task: TaskItem,
    queued_at_perf: float,
) -> GenerateResult:
    start_perf = time.perf_counter()
    queue_wait_ms = round((start_perf - queued_at_perf) * 1000.0, 2)
    started = time.perf_counter()
    request = FocalMethodRequest(
        repo_root=repo_root,
        focal_file=task.focal_file,
        focal_symbol=task.focal_symbol,
        session_id=os.getenv("UNIGENER_SESSION_ID", ""),
        language="java",
        test_framework="auto",
        retrieval_mode=args.retrieval_mode,
        llm_provider=args.llm_provider,
        llm_model=args.llm_model,
        auto_write_test=True,
        auto_run_tests=False,
        output_tests_dir=args.output_tests_dir,
        enable_similar_cases=not args.disable_similar_cases,
    )
    try:
        orchestrator = UniGenerOrchestrator()
        result = orchestrator.run(request)
        elapsed_ms = round((time.perf_counter() - started) * 1000.0, 2)
        return GenerateResult(
            task=task,
            generated_test_path=result.generated_test_path,
            error="",
            generate_queue_wait_ms=queue_wait_ms,
            generate_elapsed_ms=elapsed_ms,
            generated_finished_at_perf=time.perf_counter(),
        )
    except Exception as exc:
        elapsed_ms = round((time.perf_counter() - started) * 1000.0, 2)
        return GenerateResult(
            task=task,
            generated_test_path=None,
            error=f"{type(exc).__name__}: {exc}",
            generate_queue_wait_ms=queue_wait_ms,
            generate_elapsed_ms=elapsed_ms,
            generated_finished_at_perf=time.perf_counter(),
        )


def run_generation_stage(args: argparse.Namespace, repo_root: Path, tasks: list[TaskItem]) -> tuple[int, list[str], list[GenerateResult]]:
    generated_count = 0
    failures: list[str] = []
    generated_items: list[GenerateResult] = []
    max_workers = max(1, args.concurrent_generate_workers)
    if max_workers == 1:
        for task in tasks:
            queued_at_perf = time.perf_counter()
            item = run_single_generation(args, repo_root, task, queued_at_perf)
            if item.generated_test_path is not None:
                generated_count += 1
                generated_items.append(item)
                append_concurrency_log(
                    repo_root=repo_root,
                    stage="generate",
                    task_id=task.task_id,
                    focal_file=task.focal_file,
                    focal_symbol=task.focal_symbol,
                    elapsed_ms=item.generate_elapsed_ms,
                    status="ok",
                    extra={
                        "generated_test_path": str(item.generated_test_path),
                        "queue_wait_ms": item.generate_queue_wait_ms,
                    },
                )
                print(
                    f"[OK] task_id={task.task_id} {task.focal_file}:{task.focal_symbol} "
                    f"-> {item.generated_test_path} queue_wait_ms={item.generate_queue_wait_ms} "
                    f"elapsed_ms={item.generate_elapsed_ms}"
                )
            else:
                reason = item.error or "未生成落盘文件"
                failures.append(f"{task.focal_file}:{task.focal_symbol}: {reason}")
                append_concurrency_log(
                    repo_root=repo_root,
                    stage="generate",
                    task_id=task.task_id,
                    focal_file=task.focal_file,
                    focal_symbol=task.focal_symbol,
                    elapsed_ms=item.generate_elapsed_ms,
                    status="fail",
                    extra={"error": reason, "queue_wait_ms": item.generate_queue_wait_ms},
                )
                print(f"[FAIL] task_id={task.task_id} {task.focal_file}:{task.focal_symbol} -> {reason}")
        return generated_count, failures, generated_items
    with ThreadPoolExecutor(max_workers=max_workers) as executor:
        future_to_task: dict = {}
        for task in tasks:
            queued_at_perf = time.perf_counter()
            future = executor.submit(run_single_generation, args, repo_root, task, queued_at_perf)
            future_to_task[future] = task
        for future in as_completed(future_to_task):
            task = future_to_task[future]
            item = future.result()
            if item.generated_test_path is not None:
                generated_count += 1
                generated_items.append(item)
                append_concurrency_log(
                    repo_root=repo_root,
                    stage="generate",
                    task_id=task.task_id,
                    focal_file=task.focal_file,
                    focal_symbol=task.focal_symbol,
                    elapsed_ms=item.generate_elapsed_ms,
                    status="ok",
                    extra={
                        "generated_test_path": str(item.generated_test_path),
                        "queue_wait_ms": item.generate_queue_wait_ms,
                    },
                )
                print(
                    f"[OK] task_id={task.task_id} {task.focal_file}:{task.focal_symbol} "
                    f"-> {item.generated_test_path} queue_wait_ms={item.generate_queue_wait_ms} "
                    f"elapsed_ms={item.generate_elapsed_ms}"
                )
            else:
                reason = item.error or "未生成落盘文件"
                failures.append(f"{task.focal_file}:{task.focal_symbol}: {reason}")
                append_concurrency_log(
                    repo_root=repo_root,
                    stage="generate",
                    task_id=task.task_id,
                    focal_file=task.focal_file,
                    focal_symbol=task.focal_symbol,
                    elapsed_ms=item.generate_elapsed_ms,
                    status="fail",
                    extra={"error": reason, "queue_wait_ms": item.generate_queue_wait_ms},
                )
                print(f"[FAIL] task_id={task.task_id} {task.focal_file}:{task.focal_symbol} -> {reason}")
    return generated_count, failures, generated_items


def build_test_selector(test_path: Path, output_tests_dir: str) -> str:
    normalized = output_tests_dir.replace("\\", "/").strip("/")
    marker = "src/test/java/"
    if marker not in normalized:
        return test_path.stem
    package_path = normalized.split(marker, 1)[1].strip("/")
    if not package_path:
        return test_path.stem
    return f"{package_path.replace('/', '.')}.{test_path.stem}"


def run_sandbox_stage(
    args: argparse.Namespace,
    repo_root: Path,
    generated_items: list[GenerateResult],
) -> tuple[int, int, int, int, float, float, float, float, list[float], list[float]]:
    candidate_items = [item for item in generated_items if item.generated_test_path is not None and item.generated_test_path.suffix.lower() == ".java"]
    if not candidate_items:
        return (0, 0, 0, 0, 0.0, 0.0, 0.0, 0.0, [], [])
    worker_count = max(1, args.sandbox_workers)
    if worker_count == 1 and not args.sandbox_isolated_workdir:
        worker_repos = [repo_root]
    else:
        worker_repos = prepare_worker_repos(repo_root, worker_count, args.keep_worker_sandbox)
    buckets: list[list[GenerateResult]] = [[] for _ in range(worker_count)]
    for idx, item in enumerate(candidate_items):
        buckets[idx % worker_count].append(item)
    with ThreadPoolExecutor(max_workers=worker_count) as executor:
        futures = [
            executor.submit(
                run_sandbox_worker,
                args,
                repo_root,
                worker_repos[worker_idx],
                worker_idx,
                buckets[worker_idx],
            )
            for worker_idx in range(worker_count)
        ]
        aggregated = [future.result() for future in futures]
    executed_total = sum(item[0] for item in aggregated)
    compile_error_count = sum(item[1] for item in aggregated)
    runtime_error_count = sum(item[2] for item in aggregated)
    runnable_count = sum(item[3] for item in aggregated)
    runnable_line_coverage_sum = sum(item[4] for item in aggregated)
    runnable_branch_coverage_sum = sum(item[5] for item in aggregated)
    sandbox_elapsed_sum_ms = sum(item[6] for item in aggregated)
    sandbox_queue_wait_sum_ms = sum(item[7] for item in aggregated)
    sandbox_elapsed_values_ms = [value for item in aggregated for value in item[8]]
    sandbox_queue_wait_values_ms = [value for item in aggregated for value in item[9]]
    if (worker_count > 1 or args.sandbox_isolated_workdir) and not args.keep_worker_sandbox:
        cleanup_worker_repos(repo_root)
    return (
        executed_total,
        compile_error_count,
        runtime_error_count,
        runnable_count,
        runnable_line_coverage_sum,
        runnable_branch_coverage_sum,
        sandbox_elapsed_sum_ms,
        sandbox_queue_wait_sum_ms,
        sandbox_elapsed_values_ms,
        sandbox_queue_wait_values_ms,
    )


def run_sandbox_worker(
    args: argparse.Namespace,
    origin_repo_root: Path,
    worker_repo_root: Path,
    worker_index: int,
    items: list[GenerateResult],
) -> tuple[int, int, int, int, float, float, float, float, list[float], list[float]]:
    sandbox = JavaTestSandbox()
    is_isolated_worker = worker_repo_root.resolve() != origin_repo_root.resolve()
    if is_isolated_worker:
        generated_tests_dir = worker_repo_root / args.output_tests_dir
        if generated_tests_dir.exists():
            shutil.rmtree(generated_tests_dir, ignore_errors=True)
        generated_tests_dir.mkdir(parents=True, exist_ok=True)
    executed_total = 0
    compile_error_count = 0
    runtime_error_count = 0
    runnable_count = 0
    runnable_line_coverage_sum = 0.0
    runnable_branch_coverage_sum = 0.0
    sandbox_elapsed_sum_ms = 0.0
    sandbox_queue_wait_sum_ms = 0.0
    sandbox_elapsed_values_ms: list[float] = []
    sandbox_queue_wait_values_ms: list[float] = []
    for item in items:
        test_path = item.generated_test_path
        if test_path is None:
            continue
        if is_isolated_worker:
            worker_test_path = map_to_worker_path(test_path, origin_repo_root, worker_repo_root)
            worker_test_path.parent.mkdir(parents=True, exist_ok=True)
            shutil.copy2(test_path, worker_test_path)
        selector = build_test_selector(test_path, args.output_tests_dir)
        safe_name = re.sub(r"[^A-Za-z0-9_]", "_", f"{item.task.focal_file.name}_{item.task.focal_symbol}")
        output_json_path = resolve_log_path(
            file_name=f"worker_{worker_index}_{safe_name}.json",
            category="sandbox_batch",
            repo_root=origin_repo_root,
        )
        sandbox_queue_wait_ms = round((time.perf_counter() - item.generated_finished_at_perf) * 1000.0, 2)
        sandbox_queue_wait_sum_ms += sandbox_queue_wait_ms
        sandbox_queue_wait_values_ms.append(sandbox_queue_wait_ms)
        worker_focal = map_to_worker_path(item.task.focal_file, origin_repo_root, worker_repo_root)
        started = time.perf_counter()
        sandbox_result = sandbox.run(
            repo_root=worker_repo_root,
            output_json_path=output_json_path,
            test_selector=selector,
            focal_file=worker_focal,
            timeout_seconds=args.sandbox_timeout_seconds,
        )
        sandbox_elapsed_ms = round((time.perf_counter() - started) * 1000.0, 2)
        sandbox_elapsed_sum_ms += sandbox_elapsed_ms
        sandbox_elapsed_values_ms.append(sandbox_elapsed_ms)
        executed_total += 1
        if not sandbox_result.compile_passed:
            compile_error_count += 1
        elif not sandbox_result.tests_passed:
            runtime_error_count += 1
        else:
            runnable_count += 1
            runnable_line_coverage_sum += sandbox_result.line_coverage_percent
            runnable_branch_coverage_sum += sandbox_result.branch_coverage_percent
        print(
            "[SANDBOX] "
            f"worker={worker_index} "
            f"task_id={item.task.task_id} "
            f"selector={selector} "
            f"compile_passed={sandbox_result.compile_passed} "
            f"tests_passed={sandbox_result.tests_passed} "
            f"line={sandbox_result.line_coverage_percent} "
            f"branch={sandbox_result.branch_coverage_percent} "
            f"queue_wait_ms={sandbox_queue_wait_ms} "
            f"elapsed_ms={sandbox_elapsed_ms} "
            f"sandbox_json={output_json_path}"
        )
        append_concurrency_log(
            repo_root=origin_repo_root,
            stage="sandbox",
            task_id=item.task.task_id,
            focal_file=item.task.focal_file,
            focal_symbol=item.task.focal_symbol,
            elapsed_ms=sandbox_elapsed_ms,
            status="ok" if sandbox_result.compile_passed and sandbox_result.tests_passed else "fail",
            extra={
                "worker_index": worker_index,
                "selector": selector,
                "compile_passed": sandbox_result.compile_passed,
                "tests_passed": sandbox_result.tests_passed,
                "line_coverage_percent": sandbox_result.line_coverage_percent,
                "branch_coverage_percent": sandbox_result.branch_coverage_percent,
                "queue_wait_ms": sandbox_queue_wait_ms,
                "sandbox_json": str(output_json_path),
            },
        )
    return (
        executed_total,
        compile_error_count,
        runtime_error_count,
        runnable_count,
        runnable_line_coverage_sum,
        runnable_branch_coverage_sum,
        sandbox_elapsed_sum_ms,
        sandbox_queue_wait_sum_ms,
        sandbox_elapsed_values_ms,
        sandbox_queue_wait_values_ms,
    )


def main() -> None:
    args = build_parser().parse_args()
    if args.worker_count <= 0:
        raise ValueError("worker-count 必须大于 0")
    if args.worker_index < 0 or args.worker_index >= args.worker_count:
        raise ValueError("worker-index 必须在 [0, worker-count) 范围内")
    if args.run_sandbox and args.worker_count > 1 and args.worker_index != 0:
        raise ValueError("并行模式下仅允许 worker-index=0 执行 --run-sandbox")
    if args.concurrent_generate_workers <= 0:
        raise ValueError("concurrent-generate-workers 必须大于 0")
    if args.sandbox_workers <= 0:
        raise ValueError("sandbox-workers 必须大于 0")
    if args.sandbox_workers > 1:
        args.sandbox_isolated_workdir = True
    repo_root = args.repo.resolve()
    session_id = ensure_session_id(repo_root, args.session_id)
    print(f"session_id={session_id}")
    tasks, processed_symbols = collect_tasks(args, repo_root)
    generated, failures, generated_items = run_generation_stage(args, repo_root, tasks)
    executed_total = 0
    compile_error_count = 0
    runtime_error_count = 0
    runnable_count = 0
    runnable_line_coverage_sum = 0.0
    runnable_branch_coverage_sum = 0.0
    sandbox_elapsed_sum_ms = 0.0
    sandbox_queue_wait_sum_ms = 0.0
    sandbox_elapsed_values_ms: list[float] = []
    sandbox_queue_wait_values_ms: list[float] = []
    generate_elapsed_sum_ms = round(sum(item.generate_elapsed_ms for item in generated_items), 2)
    generate_queue_wait_sum_ms = round(sum(item.generate_queue_wait_ms for item in generated_items), 2)
    generate_elapsed_values_ms = [item.generate_elapsed_ms for item in generated_items]
    generate_queue_wait_values_ms = [item.generate_queue_wait_ms for item in generated_items]
    if args.auto_run_tests:
        (
            executed_total,
            compile_error_count,
            runtime_error_count,
            runnable_count,
            runnable_line_coverage_sum,
            runnable_branch_coverage_sum,
            sandbox_elapsed_sum_ms,
            sandbox_queue_wait_sum_ms,
            sandbox_elapsed_values_ms,
            sandbox_queue_wait_values_ms,
        ) = run_sandbox_stage(args, repo_root, generated_items)
    print("=== Summary ===")
    print(f"repo={repo_root}")
    print(f"worker_index={args.worker_index}")
    print(f"worker_count={args.worker_count}")
    print(f"concurrent_generate_workers={args.concurrent_generate_workers}")
    print(f"processed_symbols={processed_symbols}")
    print(f"generated_count={generated}")
    print(f"avg_generate_queue_wait_ms={round(generate_queue_wait_sum_ms / generated, 2) if generated else 0.0}")
    print(f"avg_generate_elapsed_ms={round(generate_elapsed_sum_ms / generated, 2) if generated else 0.0}")
    print(f"failure_count={len(failures)}")
    if args.auto_run_tests:
        compile_error_ratio = _safe_ratio(compile_error_count, executed_total)
        runtime_error_ratio = _safe_ratio(runtime_error_count, executed_total)
        runnable_ratio = _safe_ratio(runnable_count, executed_total)
        avg_line_coverage = round(runnable_line_coverage_sum / runnable_count, 2) if runnable_count else 0.0
        avg_branch_coverage = round(runnable_branch_coverage_sum / runnable_count, 2) if runnable_count else 0.0
        results = {
            "repo": str(repo_root),
            "worker_index": args.worker_index,
            "worker_count": args.worker_count,
            "concurrent_generate_workers": args.concurrent_generate_workers,
            "sandbox_workers": args.sandbox_workers,
            "executed_testcase_count": executed_total,
            "compile_error_testcase_count": compile_error_count,
            "compile_error_ratio": compile_error_ratio,
            "runtime_error_testcase_count": runtime_error_count,
            "runtime_error_ratio": runtime_error_ratio,
            "runnable_testcase_count": runnable_count,
            "runnable_ratio": runnable_ratio,
            "average_line_coverage_percent": avg_line_coverage,
            "average_branch_coverage_percent": avg_branch_coverage,
            "coverage_average_denominator": "runnable_testcase_count",
            "average_generate_queue_wait_ms": round(generate_queue_wait_sum_ms / generated, 2) if generated else 0.0,
            "average_generate_elapsed_ms": round(generate_elapsed_sum_ms / generated, 2) if generated else 0.0,
            "average_sandbox_queue_wait_ms": round(sandbox_queue_wait_sum_ms / executed_total, 2) if executed_total else 0.0,
            "average_sandbox_elapsed_ms": round(sandbox_elapsed_sum_ms / executed_total, 2) if executed_total else 0.0,
            "p50_generate_queue_wait_ms": _percentile(generate_queue_wait_values_ms, 50),
            "p95_generate_queue_wait_ms": _percentile(generate_queue_wait_values_ms, 95),
            "p50_generate_elapsed_ms": _percentile(generate_elapsed_values_ms, 50),
            "p95_generate_elapsed_ms": _percentile(generate_elapsed_values_ms, 95),
            "p50_sandbox_queue_wait_ms": _percentile(sandbox_queue_wait_values_ms, 50),
            "p95_sandbox_queue_wait_ms": _percentile(sandbox_queue_wait_values_ms, 95),
            "p50_sandbox_elapsed_ms": _percentile(sandbox_elapsed_values_ms, 50),
            "p95_sandbox_elapsed_ms": _percentile(sandbox_elapsed_values_ms, 95),
        }
        results_path = args.results_output_json.resolve()
        results_path.parent.mkdir(parents=True, exist_ok=True)
        results_path.write_text(json.dumps(results, ensure_ascii=False, indent=2), encoding="utf-8")
        print("=== Results ===")
        print(f"results_output_json={results_path}")
        print(f"executed_testcase_count={executed_total}")
        print(f"compile_error_testcase_count={compile_error_count} ratio={compile_error_ratio}")
        print(f"runtime_error_testcase_count={runtime_error_count} ratio={runtime_error_ratio}")
        print(f"runnable_testcase_count={runnable_count} ratio={runnable_ratio}")
        print(f"average_line_coverage_percent={avg_line_coverage}")
        print(f"average_branch_coverage_percent={avg_branch_coverage}")
        print(f"average_sandbox_queue_wait_ms={results['average_sandbox_queue_wait_ms']}")
        print(f"average_sandbox_elapsed_ms={results['average_sandbox_elapsed_ms']}")
        print(f"p95_generate_elapsed_ms={results['p95_generate_elapsed_ms']}")
        print(f"p95_sandbox_elapsed_ms={results['p95_sandbox_elapsed_ms']}")
    if failures:
        print("=== Failures ===")
        for item in failures:
            print(item)
    if args.run_sandbox:
        sandbox = JavaTestSandbox()
        focal_file = args.sandbox_focal_file.resolve() if args.sandbox_focal_file else None
        sandbox_result = sandbox.run(
            repo_root=repo_root,
            output_json_path=args.sandbox_output_json.resolve(),
            test_selector=args.sandbox_test_selector,
            focal_file=focal_file,
            timeout_seconds=args.sandbox_timeout_seconds,
        )
        print("=== Sandbox ===")
        print(f"sandbox_output_json={args.sandbox_output_json.resolve()}")
        print(f"compile_passed={sandbox_result.compile_passed}")
        print(f"tests_passed={sandbox_result.tests_passed}")
        print(f"line_coverage_percent={sandbox_result.line_coverage_percent}")
        print(f"branch_coverage_percent={sandbox_result.branch_coverage_percent}")


def _safe_ratio(count: int, total: int) -> float:
    if total <= 0:
        return 0.0
    return round((count * 100.0) / total, 2)


def prepare_worker_repos(repo_root: Path, worker_count: int, keep_existing: bool) -> list[Path]:
    base = resolve_log_path(".unigener_sandbox_workers", category="pipeline_runtime", repo_root=repo_root)
    if base.exists() and not keep_existing:
        shutil.rmtree(base, ignore_errors=True)
    base.mkdir(parents=True, exist_ok=True)
    repos: list[Path] = []
    for worker_index in range(worker_count):
        worker_repo = base / f"worker_{worker_index}" / "repo"
        if worker_repo.exists():
            if not keep_existing:
                shutil.rmtree(worker_repo, ignore_errors=True)
            else:
                repos.append(worker_repo)
                continue
        worker_repo.parent.mkdir(parents=True, exist_ok=True)
        shutil.copytree(
            repo_root,
            worker_repo,
            dirs_exist_ok=True,
            ignore=shutil.ignore_patterns("target", ".git", ".unigener_sandbox_workers"),
        )
        repos.append(worker_repo)
    return repos


def cleanup_worker_repos(repo_root: Path) -> None:
    base = resolve_log_path(".unigener_sandbox_workers", category="pipeline_runtime", repo_root=repo_root)
    if base.exists():
        shutil.rmtree(base, ignore_errors=True)


def map_to_worker_path(source_path: Path, origin_repo_root: Path, worker_repo_root: Path) -> Path:
    relative = source_path.resolve().relative_to(origin_repo_root.resolve())
    return worker_repo_root / relative


def _percentile(values: list[float], percentile: int) -> float:
    if not values:
        return 0.0
    if percentile <= 0:
        return round(min(values), 2)
    if percentile >= 100:
        return round(max(values), 2)
    ordered = sorted(values)
    rank = (len(ordered) - 1) * (percentile / 100.0)
    low = int(rank)
    high = min(low + 1, len(ordered) - 1)
    weight = rank - low
    result = ordered[low] * (1 - weight) + ordered[high] * weight
    return round(result, 2)


def build_task_id(focal_file: Path, focal_symbol: str) -> str:
    seed = f"{focal_file.resolve()}::{focal_symbol}"
    return hashlib.md5(seed.encode("utf-8")).hexdigest()[:12]


def append_concurrency_log(
    repo_root: Path,
    stage: str,
    task_id: str,
    focal_file: Path,
    focal_symbol: str,
    elapsed_ms: float,
    status: str,
    extra: dict,
) -> None:
    record = {
        "timestamp": datetime.now(timezone.utc).isoformat(),
        "session_id": current_session_id(),
        "stage": stage,
        "task_id": task_id,
        "focal_file": str(focal_file),
        "focal_symbol": focal_symbol,
        "elapsed_ms": elapsed_ms,
        "status": status,
        "extra": extra,
    }
    log_path = resolve_log_path("repo_concurrency_tasks.jsonl", category="pipeline", repo_root=repo_root)
    log_path.parent.mkdir(parents=True, exist_ok=True)
    with log_path.open("a", encoding="utf-8") as fp:
        fp.write(json.dumps(record, ensure_ascii=False) + "\n")


if __name__ == "__main__":
    main()
