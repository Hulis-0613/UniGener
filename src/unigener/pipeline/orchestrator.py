from __future__ import annotations

import ast
import json
import re
from pathlib import Path

from unigener.agents.code_retrieval_agent import CodeRetrievalAgent
from unigener.agents.intent_agent import IntentRecognitionAgent
from unigener.agents.similar_test_agent import SimilarTestRetrievalAgent
from unigener.agents.test_generation_agent import UnitTestGenerationAgent
from unigener.models import (
    FocalMethodRequest,
    PipelineResult,
    TestExecutionResult,
)
from unigener.pipeline.contracts import IntentToCodeRequest, SimilarCaseRequest
from unigener.pipeline.java_test_sandbox import JavaTestSandbox
from unigener.pipeline.test_runner import GeneratedTestRunner
from unigener.pipeline.test_writer import GeneratedTestWriter

N_INTERATION = 10


class UniGenerOrchestrator:
    def __init__(self) -> None:
        self.intent_agent = IntentRecognitionAgent()
        self.code_agent = CodeRetrievalAgent()
        self.similar_agent = SimilarTestRetrievalAgent()
        self.generation_agent = UnitTestGenerationAgent()
        self.test_writer = GeneratedTestWriter()
        self.test_runner = GeneratedTestRunner()
        self.java_test_sandbox = JavaTestSandbox()

    def run(
        self,
        request: FocalMethodRequest,
    ) -> PipelineResult:
        language = self._resolve_language(request)
        framework = self._resolve_framework(request, language)
        intent_result = self.intent_agent.run(
            focal_file=request.focal_file,
            focal_symbol=request.focal_symbol,
            language=language,
            llm_provider=request.llm_provider,
            llm_model=request.llm_model,
            enable_query_planning=True,
        )
        code_context = []
        retrieval_symbols = list(intent_result.uncovered_symbols)
        if intent_result.need_context and intent_result.retrieval_queries:
            retrieval_symbols.extend(intent_result.retrieval_queries)
        if retrieval_symbols:
            code_request = IntentToCodeRequest(
                unresolved_symbols=self._dedupe_items(retrieval_symbols),
                import_queries=self._extract_import_symbols(request.focal_file, request.focal_symbol, language),
            )
            symbols = self._dedupe_items(code_request.unresolved_symbols + code_request.import_queries)
            code_context = self.code_agent.run(
                request.repo_root,
                symbols,
                mode=request.retrieval_mode,
                language=language,
            )
            if intent_result.need_context:
                intent_result = self.intent_agent.run(
                    focal_file=request.focal_file,
                    focal_symbol=request.focal_symbol,
                    language=language,
                    llm_provider=request.llm_provider,
                    llm_model=request.llm_model,
                    code_context=code_context,
                    enable_query_planning=False,
                )
        similar_cases = []
        if request.enable_similar_cases:
            similar_request = SimilarCaseRequest(
                focal_symbol=request.focal_symbol,
                intent=intent_result.intent,
            )
            similar_cases = self.similar_agent.run(
                repo_root=request.repo_root,
                focal_symbol=similar_request.focal_symbol,
                intent=similar_request.intent,
                language=language,
            )
        generated = None
        generated_path = None
        test_iterations = []
        repair_feedback = ""
        for iteration in range(1, N_INTERATION + 1):
            generated = self.generation_agent.run(
                repo_root=request.repo_root,
                focal_file=request.focal_file,
                intent_result=intent_result,
                similar_cases=similar_cases,
                language=language,
                framework=framework,
                llm_provider=request.llm_provider,
                llm_model=request.llm_model,
                repair_feedback=repair_feedback,
            )
            if request.auto_write_test:
                generated_path = self.test_writer.write(
                    repo_root=request.repo_root,
                    tests_dir=request.output_tests_dir,
                    focal_symbol=request.focal_symbol,
                    generated=generated,
                )
            if not request.auto_run_tests or generated_path is None:
                break
            if language == "python":
                execution = self.test_runner.run(
                    iteration=iteration,
                    repo_root=request.repo_root,
                    test_file=generated_path,
                    focal_file=request.focal_file,
                    focal_symbol=request.focal_symbol,
                )
            else:
                execution = self._run_java_sandbox_iteration(
                    request=request,
                    iteration=iteration,
                )
            test_iterations.append(execution)
            if self._is_iteration_success(execution):
                break
            if self._should_abort_iteration(execution):
                break
            repair_feedback = self._build_repair_feedback(execution)
        if generated is None:
            raise RuntimeError("测试生成失败")
        failure_reports_json = self._build_failure_reports_json(test_iterations)
        return PipelineResult(
            intent=intent_result,
            code_context=code_context,
            similar_cases=similar_cases,
            generated_test=generated,
            generated_test_path=generated_path,
            test_iterations=test_iterations,
            failure_reports_json=failure_reports_json,
        )

    def _is_iteration_success(self, execution: TestExecutionResult) -> bool:
        if execution.coverage_backend == "jacoco":
            return (
                execution.compile_passed
                and execution.tests_passed
                and execution.line_coverage_percent >= 100.0
                and execution.branch_coverage_percent >= 100.0
            )
        return execution.compile_passed and execution.tests_passed and execution.coverage_percent >= 100.0

    def _build_repair_feedback(self, execution: TestExecutionResult) -> str:
        return (
            f"compile_passed={execution.compile_passed}\n"
            f"tests_passed={execution.tests_passed}\n"
            f"coverage_percent={execution.coverage_percent}\n"
            f"line_coverage_percent={execution.line_coverage_percent}\n"
            f"branch_coverage_percent={execution.branch_coverage_percent}\n"
            f"focal_line_coverage_percent={execution.focal_line_coverage_percent}\n"
            f"focal_branch_coverage_percent={execution.focal_branch_coverage_percent}\n"
            f"coverage_backend={execution.coverage_backend}\n"
            f"compile_error={execution.compile_error}\n"
            f"runtime_error={execution.runtime_error}\n"
            f"execution_output={execution.execution_output}\n"
            f"sandbox_result_json={execution.sandbox_result_json}"
        )

    def _should_abort_iteration(self, execution: TestExecutionResult) -> bool:
        if "ModuleNotFoundError: No module named 'pytest'" in execution.runtime_error:
            return True
        return False

    def _build_failure_reports_json(self, test_iterations: list[TestExecutionResult]) -> str:
        reports = []
        for item in test_iterations:
            if self._is_iteration_success(item):
                continue
            reports.append(
                {
                    "iteration": item.iteration,
                    "compile_passed": item.compile_passed,
                    "tests_passed": item.tests_passed,
                    "coverage_percent": item.coverage_percent,
                    "line_coverage_percent": item.line_coverage_percent,
                    "branch_coverage_percent": item.branch_coverage_percent,
                    "focal_line_coverage_percent": item.focal_line_coverage_percent,
                    "focal_branch_coverage_percent": item.focal_branch_coverage_percent,
                    "coverage_backend": item.coverage_backend,
                    "compile_error": item.compile_error,
                    "runtime_error": item.runtime_error,
                    "execution_output": item.execution_output,
                    "sandbox_result_json": item.sandbox_result_json,
                }
            )
        return json.dumps(reports, ensure_ascii=False, indent=2)

    def _run_java_sandbox_iteration(
        self,
        request: FocalMethodRequest,
        iteration: int,
    ) -> TestExecutionResult:
        logs_dir = request.repo_root / "logs" / "sandbox_iterations"
        safe_symbol = re.sub(r"[^A-Za-z0-9_]", "_", request.focal_symbol)
        json_path = logs_dir / f"{safe_symbol}_iter_{iteration}.json"
        sandbox_result = self.java_test_sandbox.run(
            repo_root=request.repo_root,
            output_json_path=json_path,
            test_selector="unigener.*",
            focal_file=request.focal_file,
            timeout_seconds=900,
        )
        return TestExecutionResult(
            iteration=iteration,
            compile_passed=sandbox_result.compile_passed,
            tests_passed=sandbox_result.tests_passed,
            coverage_percent=sandbox_result.focal_line_coverage_percent or sandbox_result.line_coverage_percent,
            coverage_backend="jacoco",
            compile_error=sandbox_result.compile_error_stack,
            runtime_error=sandbox_result.runtime_error_stack,
            execution_output=sandbox_result.runtime_output or sandbox_result.compile_output,
            line_coverage_percent=sandbox_result.line_coverage_percent,
            branch_coverage_percent=sandbox_result.branch_coverage_percent,
            focal_line_coverage_percent=sandbox_result.focal_line_coverage_percent,
            focal_branch_coverage_percent=sandbox_result.focal_branch_coverage_percent,
            sandbox_result_json=str(json_path),
        )

    def _extract_import_symbols(self, focal_file: Path, focal_symbol: str, language: str) -> list[str]:
        if language == "java":
            return self._extract_import_symbols_java(focal_file)
        source = focal_file.read_text(encoding="utf-8")
        tree = ast.parse(source)
        symbols: list[str] = []
        for node in ast.walk(tree):
            if isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef)) and node.name == focal_symbol:
                for child in ast.walk(node):
                    if isinstance(child, ast.Call):
                        if isinstance(child.func, ast.Name):
                            symbols.append(child.func.id)
                        elif isinstance(child.func, ast.Attribute):
                            symbols.append(child.func.attr)
        return symbols

    def _extract_import_symbols_java(self, focal_file: Path) -> list[str]:
        source = focal_file.read_text(encoding="utf-8")
        imports = re.findall(r"^\s*import\s+([\w.]+);", source, flags=re.MULTILINE)
        symbols = [item.split(".")[-1] for item in imports]
        method_calls = re.findall(r"\b([A-Za-z_]\w*)\s*\(", source)
        java_keywords = {"if", "for", "while", "switch", "catch", "return", "new", "throw", "assert"}
        symbols.extend([item for item in method_calls if item not in java_keywords])
        return symbols

    def _resolve_language(self, request: FocalMethodRequest) -> str:
        if request.language != "auto":
            return request.language
        suffix = request.focal_file.suffix.lower()
        if suffix == ".java":
            return "java"
        return "python"

    def _resolve_framework(self, request: FocalMethodRequest, language: str) -> str:
        if request.test_framework != "auto":
            return request.test_framework
        if language == "java":
            return self._detect_java_test_framework(request.repo_root)
        return "pytest"

    def _detect_java_test_framework(self, repo_root: Path) -> str:
        pom_path = repo_root / "pom.xml"
        if not pom_path.exists():
            return "junit5"
        try:
            pom = pom_path.read_text(encoding="utf-8")
        except Exception:
            return "junit5"
        lowered = pom.lower()
        has_jupiter = "org.junit.jupiter" in lowered or "junit-jupiter" in lowered
        has_junit4 = "<groupid>junit</groupid>" in lowered and "<artifactid>junit</artifactid>" in lowered
        if has_jupiter:
            return "junit5"
        if has_junit4:
            return "junit4"
        return "junit5"

    def _dedupe_items(self, items: list[str]) -> list[str]:
        deduped: list[str] = []
        seen: set[str] = set()
        for item in items:
            normalized = item.strip()
            if not normalized:
                continue
            if normalized in seen:
                continue
            seen.add(normalized)
            deduped.append(normalized)
        return deduped
