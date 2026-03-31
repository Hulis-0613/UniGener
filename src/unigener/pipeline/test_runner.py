from __future__ import annotations

import ast
import importlib
import io
import os
import py_compile
import traceback
from contextlib import redirect_stderr, redirect_stdout
from pathlib import Path
from trace import Trace

from unigener.models import TestExecutionResult


class GeneratedTestRunner:
    def run(
        self,
        iteration: int,
        repo_root: Path,
        test_file: Path,
        focal_file: Path,
        focal_symbol: str,
    ) -> TestExecutionResult:
        compile_passed, compile_error = self._compile_test_file(test_file)
        if not compile_passed:
            return TestExecutionResult(
                iteration=iteration,
                compile_passed=False,
                tests_passed=False,
                coverage_percent=0.0,
                coverage_backend="none",
                compile_error=compile_error,
                runtime_error="",
                execution_output="",
            )
        tests_passed, execution_output, runtime_error, executed_lines, coverage_backend = self._run_pytest(
            repo_root=repo_root,
            test_file=test_file,
            focal_file=focal_file,
        )
        coverage_percent = self._calculate_focal_coverage(
            focal_file=focal_file,
            focal_symbol=focal_symbol,
            executed_lines=executed_lines,
        )
        return TestExecutionResult(
            iteration=iteration,
            compile_passed=True,
            tests_passed=tests_passed,
            coverage_percent=coverage_percent,
            coverage_backend=coverage_backend,
            compile_error="",
            runtime_error=runtime_error,
            execution_output=execution_output,
        )

    def _compile_test_file(self, test_file: Path) -> tuple[bool, str]:
        try:
            py_compile.compile(str(test_file), doraise=True)
            return True, ""
        except Exception:
            return False, traceback.format_exc()

    def _run_pytest(
        self,
        repo_root: Path,
        test_file: Path,
        focal_file: Path,
    ) -> tuple[bool, str, str, set[int], str]:
        try:
            coverage_mod = importlib.import_module("coverage")
        except Exception:
            return self._run_pytest_with_trace(repo_root, test_file, focal_file)
        try:
            pytest = importlib.import_module("pytest")
        except Exception:
            return False, "", traceback.format_exc(), set(), "coverage.py"
        output = io.StringIO()
        error = io.StringIO()
        executed_lines: set[int] = set()
        cwd_before = Path.cwd()
        coverage_api = coverage_mod.Coverage(data_file=None)
        try:
            os.chdir(repo_root)
            coverage_api.start()
            with redirect_stdout(output), redirect_stderr(error):
                exit_code = pytest.main([str(test_file), "-q"])
            coverage_api.stop()
            coverage_api.save()
            lines = coverage_api.get_data().lines(str(focal_file.resolve()))
            if lines:
                executed_lines = set(lines)
        except Exception:
            return False, output.getvalue(), traceback.format_exc(), set(), "coverage.py"
        finally:
            os.chdir(cwd_before)
        runtime_error = error.getvalue()
        execution_output = output.getvalue()
        tests_passed = int(exit_code) == 0
        return tests_passed, execution_output, runtime_error, executed_lines, "coverage.py"

    def _run_pytest_with_trace(
        self,
        repo_root: Path,
        test_file: Path,
        focal_file: Path,
    ) -> tuple[bool, str, str, set[int], str]:
        try:
            pytest = importlib.import_module("pytest")
        except Exception as exc:
            return False, "", f"{type(exc).__name__}: {exc}", set(), "trace"
        output = io.StringIO()
        error = io.StringIO()
        tracer = Trace(count=True, trace=False)
        cwd_before = Path.cwd()
        try:
            os.chdir(repo_root)
            with redirect_stdout(output), redirect_stderr(error):
                exit_code = tracer.runfunc(pytest.main, [str(test_file), "-q"])
        except Exception:
            return False, output.getvalue(), traceback.format_exc(), set(), "trace"
        finally:
            os.chdir(cwd_before)
        runtime_error = error.getvalue()
        execution_output = output.getvalue()
        tests_passed = int(exit_code) == 0
        counts = tracer.results().counts
        executed_lines: set[int] = set()
        for (file_name, line_no), count in counts.items():
            if count <= 0:
                continue
            if Path(file_name).resolve() == focal_file.resolve():
                executed_lines.add(line_no)
        return tests_passed, execution_output, runtime_error, executed_lines, "trace"

    def _calculate_focal_coverage(
        self,
        focal_file: Path,
        focal_symbol: str,
        executed_lines: set[int],
    ) -> float:
        source = focal_file.read_text(encoding="utf-8")
        lines = source.splitlines()
        target_lines = self._extract_symbol_lines(source, focal_symbol)
        if not target_lines:
            target_lines = {idx + 1 for idx, line in enumerate(lines) if line.strip()}
        if not target_lines:
            return 100.0
        hit = len(target_lines & executed_lines)
        return round((hit / len(target_lines)) * 100.0, 2)

    def _extract_symbol_lines(self, source: str, focal_symbol: str) -> set[int]:
        tree = ast.parse(source)
        all_lines = source.splitlines()
        for node in ast.walk(tree):
            if isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef, ast.ClassDef)) and node.name == focal_symbol:
                start = getattr(node, "lineno", 1)
                end = getattr(node, "end_lineno", start)
                lines = set()
                for line_no in range(start, end + 1):
                    if line_no - 1 < len(all_lines) and all_lines[line_no - 1].strip():
                        lines.add(line_no)
                return lines
        return set()
