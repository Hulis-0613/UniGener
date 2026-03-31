from __future__ import annotations

import argparse
import json
import re
from pathlib import Path

from unigener.models import FocalMethodRequest
from unigener.pipeline import JavaTestSandbox, UniGenerOrchestrator


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


def main() -> None:
    args = build_parser().parse_args()
    if args.worker_count <= 0:
        raise ValueError("worker-count 必须大于 0")
    if args.worker_index < 0 or args.worker_index >= args.worker_count:
        raise ValueError("worker-index 必须在 [0, worker-count) 范围内")
    if args.run_sandbox and args.worker_count > 1 and args.worker_index != 0:
        raise ValueError("并行模式下仅允许 worker-index=0 执行 --run-sandbox")
    repo_root = args.repo.resolve()
    orchestrator = UniGenerOrchestrator()
    generated = 0
    failures: list[str] = []
    executed_total = 0
    compile_error_count = 0
    runtime_error_count = 0
    runnable_count = 0
    runnable_line_coverage_sum = 0.0
    runnable_branch_coverage_sum = 0.0
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
            request = FocalMethodRequest(
                repo_root=repo_root,
                focal_file=java_file,
                focal_symbol=symbol,
                language="java",
                test_framework="auto",
                retrieval_mode=args.retrieval_mode,
                llm_provider=args.llm_provider,
                llm_model=args.llm_model,
                auto_write_test=True,
                auto_run_tests=args.auto_run_tests,
                output_tests_dir=args.output_tests_dir,
                enable_similar_cases=not args.disable_similar_cases,
            )
            try:
                result = orchestrator.run(request)
                if result.generated_test_path is not None:
                    generated += 1
                    print(f"[OK] {java_file}:{symbol} -> {result.generated_test_path}")
                    if args.auto_run_tests and result.test_iterations:
                        latest = result.test_iterations[-1]
                        executed_total += 1
                        if not latest.compile_passed:
                            compile_error_count += 1
                        elif not latest.tests_passed:
                            runtime_error_count += 1
                        else:
                            runnable_count += 1
                            runnable_line_coverage_sum += latest.line_coverage_percent
                            runnable_branch_coverage_sum += latest.branch_coverage_percent
                        print(
                            "[SANDBOX] "
                            f"compile_passed={latest.compile_passed} "
                            f"tests_passed={latest.tests_passed} "
                            f"line={latest.line_coverage_percent} "
                            f"branch={latest.branch_coverage_percent} "
                            f"sandbox_json={latest.sandbox_result_json}"
                        )
                else:
                    failures.append(f"{java_file}:{symbol}: 未生成落盘文件")
                    print(f"[FAIL] {java_file}:{symbol} -> 未生成落盘文件")
            except Exception as exc:
                failures.append(f"{java_file}:{symbol}: {type(exc).__name__}: {exc}")
                print(f"[FAIL] {java_file}:{symbol} -> {type(exc).__name__}: {exc}")
        if args.max_symbols and processed_symbols >= args.max_symbols:
            break
    print("=== Summary ===")
    print(f"repo={repo_root}")
    print(f"worker_index={args.worker_index}")
    print(f"worker_count={args.worker_count}")
    print(f"processed_symbols={processed_symbols}")
    print(f"generated_count={generated}")
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


if __name__ == "__main__":
    main()
