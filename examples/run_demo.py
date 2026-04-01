from __future__ import annotations

import argparse
from pathlib import Path

from unigener.models import FocalMethodRequest
from unigener.pipeline import UniGenerOrchestrator
from unigener.utils import ensure_session_id


def build_parser() -> argparse.ArgumentParser:
    parser = argparse.ArgumentParser()
    parser.add_argument("--repo", required=True, type=Path)
    parser.add_argument("--focal-file", required=True, type=Path)
    parser.add_argument("--focal-symbol", required=True)
    parser.add_argument("--language", default="auto", choices=["auto", "python", "java"])
    parser.add_argument("--test-framework", default="auto")
    parser.add_argument("--retrieval-mode", default="hybrid", choices=["ast", "lsp", "hybrid"])
    parser.add_argument("--llm-provider", default="none", choices=["none", "openai", "ollama"])
    parser.add_argument("--llm-model", default="")
    parser.add_argument("--output-tests-dir", default="tests")
    parser.add_argument("--no-auto-write", action="store_true")
    parser.add_argument("--no-auto-run", action="store_true")
    parser.add_argument("--session-id", default="")
    return parser


def main() -> None:
    args = build_parser().parse_args()
    session_id = ensure_session_id(args.repo.resolve(), args.session_id)
    orchestrator = UniGenerOrchestrator()
    request = FocalMethodRequest(
        repo_root=args.repo.resolve(),
        focal_file=args.focal_file.resolve(),
        focal_symbol=args.focal_symbol,
        session_id=session_id,
        language=args.language,
        test_framework=args.test_framework,
        retrieval_mode=args.retrieval_mode,
        llm_provider=args.llm_provider,
        llm_model=args.llm_model,
        auto_write_test=not args.no_auto_write,
        auto_run_tests=not args.no_auto_run,
        output_tests_dir=args.output_tests_dir,
    )
    result = orchestrator.run(request)
    print(f"=== Session ===\n{session_id}")
    print("=== Intent ===")
    print(result.intent)
    print("\n=== Code Context ===")
    for item in result.code_context:
        print(f"- {item.symbol} @ {item.file_path} ({item.reason})")
    print("\n=== Similar Cases ===")
    for item in result.similar_cases:
        print(f"- {item.test_name} score={item.score} file={item.test_file}")
    print("\n=== Generated Test ===")
    print(result.generated_test.content)
    print("\n=== Few-shot Prompt ===")
    print(result.generated_test.prompt)
    if result.generated_test_path is not None:
        print("\n=== Generated File ===")
        print(result.generated_test_path)
    print("\n=== Iterations ===")
    for item in result.test_iterations:
        print(
            f"- iter={item.iteration} compile={item.compile_passed} "
            f"tests={item.tests_passed} coverage={item.coverage_percent} backend={item.coverage_backend}"
        )
        if item.compile_error:
            print(item.compile_error)
        if item.runtime_error:
            print(item.runtime_error)
    print("\n=== Failure Reports JSON ===")
    print(result.failure_reports_json)


if __name__ == "__main__":
    main()
