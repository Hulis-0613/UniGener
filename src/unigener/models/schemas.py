from __future__ import annotations

from dataclasses import dataclass, field
from pathlib import Path


@dataclass(slots=True)
class FocalMethodRequest:
    repo_root: Path
    focal_file: Path
    focal_symbol: str
    session_id: str = ""
    language: str = "auto"
    test_framework: str = "auto"
    retrieval_mode: str = "hybrid"
    llm_provider: str = "none"
    llm_model: str = ""
    auto_write_test: bool = True
    auto_run_tests: bool = True
    output_tests_dir: str = "tests"
    enable_similar_cases: bool = True


@dataclass(slots=True)
class CodeSnippet:
    symbol: str
    file_path: Path
    source: str
    reason: str


@dataclass(slots=True)
class IntentResult:
    focal_symbol: str
    intent: str
    uncovered_symbols: list[str] = field(default_factory=list)
    need_context: bool = False
    retrieval_queries: list[str] = field(default_factory=list)


@dataclass(slots=True)
class SimilarTestCase:
    focal_symbol: str
    test_file: Path
    test_name: str
    score: float
    source: str


@dataclass(slots=True)
class GeneratedTestCase:
    target_symbol: str
    framework: str
    content: str
    prompt: str


@dataclass(slots=True)
class TestExecutionResult:
    iteration: int
    compile_passed: bool
    tests_passed: bool
    coverage_percent: float
    coverage_backend: str
    compile_error: str
    runtime_error: str
    execution_output: str
    line_coverage_percent: float = 0.0
    branch_coverage_percent: float = 0.0
    focal_line_coverage_percent: float = 0.0
    focal_branch_coverage_percent: float = 0.0
    sandbox_result_json: str = ""


@dataclass(slots=True)
class PipelineResult:
    intent: IntentResult
    code_context: list[CodeSnippet]
    similar_cases: list[SimilarTestCase]
    generated_test: GeneratedTestCase
    generated_test_path: Path | None
    test_iterations: list[TestExecutionResult] = field(default_factory=list)
    failure_reports_json: str = "[]"
