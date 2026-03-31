from __future__ import annotations

import json
import re
import subprocess
import xml.etree.ElementTree as ET
from dataclasses import asdict, dataclass
from pathlib import Path


@dataclass(slots=True)
class JavaSandboxResult:
    compile_passed: bool
    tests_passed: bool
    line_coverage_percent: float
    branch_coverage_percent: float
    focal_line_coverage_percent: float
    focal_branch_coverage_percent: float
    compile_error_stack: str
    runtime_error_stack: str
    compile_output: str
    runtime_output: str
    coverage_xml_path: str
    repair_feedback: str
    test_selector: str
    maven_executable: str


class JavaTestSandbox:
    def run(
        self,
        repo_root: Path,
        output_json_path: Path,
        test_selector: str = "",
        focal_file: Path | None = None,
        timeout_seconds: int = 900,
    ) -> JavaSandboxResult:
        mvn_executable = self._resolve_maven_executable(repo_root)
        compile_cmd = [mvn_executable, "-q", "-DskipTests", "test-compile"]
        compile_run = self._run_command(compile_cmd, cwd=repo_root, timeout_seconds=timeout_seconds)
        compile_output = self._merge_output(compile_run.stdout, compile_run.stderr)
        if compile_run.returncode != 0:
            result = JavaSandboxResult(
                compile_passed=False,
                tests_passed=False,
                line_coverage_percent=0.0,
                branch_coverage_percent=0.0,
                focal_line_coverage_percent=0.0,
                focal_branch_coverage_percent=0.0,
                compile_error_stack=compile_output,
                runtime_error_stack="",
                compile_output=compile_output,
                runtime_output="",
                coverage_xml_path="",
                repair_feedback=self._build_repair_feedback(
                    compile_passed=False,
                    tests_passed=False,
                    line_coverage_percent=0.0,
                    branch_coverage_percent=0.0,
                    compile_error_stack=compile_output,
                    runtime_error_stack="",
                ),
                test_selector=test_selector,
                maven_executable=mvn_executable,
            )
            self._write_result_json(output_json_path, result)
            return result
        run_cmd = [
            mvn_executable,
            "-q",
            "-DfailIfNoTests=false",
            "org.jacoco:jacoco-maven-plugin:0.8.11:prepare-agent",
        ]
        if test_selector:
            run_cmd.append(f"-Dtest={test_selector}")
        run_cmd.extend(["test", "org.jacoco:jacoco-maven-plugin:0.8.11:report"])
        run_run = self._run_command(run_cmd, cwd=repo_root, timeout_seconds=timeout_seconds)
        runtime_output = self._merge_output(run_run.stdout, run_run.stderr)
        if run_run.returncode != 0:
            result = JavaSandboxResult(
                compile_passed=True,
                tests_passed=False,
                line_coverage_percent=0.0,
                branch_coverage_percent=0.0,
                focal_line_coverage_percent=0.0,
                focal_branch_coverage_percent=0.0,
                compile_error_stack="",
                runtime_error_stack=runtime_output,
                compile_output=compile_output,
                runtime_output=runtime_output,
                coverage_xml_path="",
                repair_feedback=self._build_repair_feedback(
                    compile_passed=True,
                    tests_passed=False,
                    line_coverage_percent=0.0,
                    branch_coverage_percent=0.0,
                    compile_error_stack="",
                    runtime_error_stack=runtime_output,
                ),
                test_selector=test_selector,
                maven_executable=mvn_executable,
            )
            self._write_result_json(output_json_path, result)
            return result
        coverage_xml_path = repo_root / "target" / "site" / "jacoco" / "jacoco.xml"
        line_coverage_percent, branch_coverage_percent = self._parse_overall_coverage(coverage_xml_path)
        focal_line_coverage_percent = 0.0
        focal_branch_coverage_percent = 0.0
        if focal_file is not None:
            focal_line_coverage_percent, focal_branch_coverage_percent = self._parse_focal_coverage(
                repo_root=repo_root,
                coverage_xml_path=coverage_xml_path,
                focal_file=focal_file,
            )
        result = JavaSandboxResult(
            compile_passed=True,
            tests_passed=True,
            line_coverage_percent=line_coverage_percent,
            branch_coverage_percent=branch_coverage_percent,
            focal_line_coverage_percent=focal_line_coverage_percent,
            focal_branch_coverage_percent=focal_branch_coverage_percent,
            compile_error_stack="",
            runtime_error_stack="",
            compile_output=compile_output,
            runtime_output=runtime_output,
            coverage_xml_path=str(coverage_xml_path),
            repair_feedback=self._build_repair_feedback(
                compile_passed=True,
                tests_passed=True,
                line_coverage_percent=line_coverage_percent,
                branch_coverage_percent=branch_coverage_percent,
                compile_error_stack="",
                runtime_error_stack="",
            ),
            test_selector=test_selector,
            maven_executable=mvn_executable,
        )
        self._write_result_json(output_json_path, result)
        return result

    def _resolve_maven_executable(self, repo_root: Path) -> str:
        mvnw = repo_root / "mvnw"
        if mvnw.exists():
            return str(mvnw)
        return "mvn"

    def _run_command(self, command: list[str], cwd: Path, timeout_seconds: int) -> subprocess.CompletedProcess[str]:
        return subprocess.run(
            command,
            cwd=str(cwd),
            text=True,
            capture_output=True,
            timeout=timeout_seconds,
            check=False,
        )

    def _merge_output(self, stdout: str, stderr: str) -> str:
        stdout_text = stdout or ""
        stderr_text = stderr or ""
        stdout_text = self._strip_ansi(stdout_text)
        stderr_text = self._strip_ansi(stderr_text)
        if stdout_text and stderr_text:
            return f"{stdout_text}\n{stderr_text}"
        return stdout_text or stderr_text

    def _strip_ansi(self, text: str) -> str:
        return re.sub(r"\x1B\[[0-?]*[ -/]*[@-~]", "", text)

    def _parse_overall_coverage(self, coverage_xml_path: Path) -> tuple[float, float]:
        if not coverage_xml_path.exists():
            return 0.0, 0.0
        tree = ET.parse(coverage_xml_path)
        root = tree.getroot()
        line_coverage = self._extract_coverage_percent(root.findall("./counter"), counter_type="LINE")
        branch_coverage = self._extract_coverage_percent(root.findall("./counter"), counter_type="BRANCH")
        return line_coverage, branch_coverage

    def _parse_focal_coverage(
        self,
        repo_root: Path,
        coverage_xml_path: Path,
        focal_file: Path,
    ) -> tuple[float, float]:
        if not coverage_xml_path.exists():
            return 0.0, 0.0
        tree = ET.parse(coverage_xml_path)
        root = tree.getroot()
        relative = focal_file.resolve().relative_to((repo_root / "src" / "main" / "java").resolve())
        package_parts = relative.parts[:-1]
        class_name = relative.stem
        package_name = ".".join(package_parts)
        package_node = None
        for node in root.findall("./package"):
            if node.get("name", "").replace("/", ".") == package_name:
                package_node = node
                break
        if package_node is None:
            return 0.0, 0.0
        class_node = None
        for node in package_node.findall("./class"):
            if node.get("name", "").split("/")[-1] == class_name:
                class_node = node
                break
        if class_node is None:
            return 0.0, 0.0
        line_coverage = self._extract_coverage_percent(class_node.findall("./counter"), counter_type="LINE")
        branch_coverage = self._extract_coverage_percent(class_node.findall("./counter"), counter_type="BRANCH")
        return line_coverage, branch_coverage

    def _extract_coverage_percent(self, counters: list[ET.Element], counter_type: str) -> float:
        for counter in counters:
            if counter.get("type") != counter_type:
                continue
            missed = int(counter.get("missed", "0"))
            covered = int(counter.get("covered", "0"))
            total = missed + covered
            if total <= 0:
                return 0.0
            return round((covered * 100.0) / total, 2)
        return 0.0

    def _build_repair_feedback(
        self,
        compile_passed: bool,
        tests_passed: bool,
        line_coverage_percent: float,
        branch_coverage_percent: float,
        compile_error_stack: str,
        runtime_error_stack: str,
    ) -> str:
        return (
            f"compile_passed={compile_passed}\n"
            f"tests_passed={tests_passed}\n"
            f"line_coverage_percent={line_coverage_percent}\n"
            f"branch_coverage_percent={branch_coverage_percent}\n"
            f"compile_error_stack={compile_error_stack}\n"
            f"runtime_error_stack={runtime_error_stack}"
        )

    def _write_result_json(self, output_json_path: Path, result: JavaSandboxResult) -> None:
        output_json_path.parent.mkdir(parents=True, exist_ok=True)
        output_json_path.write_text(
            json.dumps(asdict(result), ensure_ascii=False, indent=2),
            encoding="utf-8",
        )
