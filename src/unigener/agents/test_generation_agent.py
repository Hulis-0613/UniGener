from __future__ import annotations

import ast
import hashlib
import re
from pathlib import Path

from unigener.agents.llm_client import LLMClient
from unigener.models import GeneratedTestCase, IntentResult, SimilarTestCase


class UnitTestGenerationAgent:
    name = "unit-test-generation-agent"

    def __init__(self) -> None:
        self._llm_client = LLMClient()

    def run(
        self,
        repo_root: Path,
        focal_file: Path,
        intent_result: IntentResult,
        similar_cases: list[SimilarTestCase],
        language: str = "python",
        framework: str = "pytest",
        llm_provider: str = "none",
        llm_model: str = "",
        repair_feedback: str = "",
    ) -> GeneratedTestCase:
        normalized_framework = self._normalize_framework(language=language, framework=framework)
        prompt = self._build_few_shot_prompt(intent_result, similar_cases, repair_feedback, normalized_framework)
        raw_generated = self._llm_client.generate(
            prompt=prompt,
            provider=llm_provider,
            model=llm_model,
            metadata={
                "agent": self.name,
                "language": language,
                "framework": normalized_framework,
                "focal_symbol": intent_result.focal_symbol,
                "task": "test_generation",
            },
        )
        generated = self._sanitize_generated_output(raw_generated, normalized_framework)
        fallback_reason = ""
        if raw_generated is None:
            fallback_reason = self._llm_client.last_error or "llm_no_output"
        elif generated is None:
            fallback_reason = "llm_output_not_executable_code"
        import_stmt, invoke_expr, class_name, invoke_args, edge_args = self._infer_import_and_invoke(
            repo_root=repo_root,
            focal_file=focal_file,
            focal_symbol=intent_result.focal_symbol,
            language=language,
        )
        if generated and normalized_framework in {"junit4", "junit5"}:
            generated = self._force_java_test_class_name(generated, class_name or "UniGenerGeneratedTest")
        if normalized_framework in {"junit4", "junit5"}:
            body = generated or self._build_junit_content(
                intent_result,
                similar_cases,
                import_stmt,
                invoke_expr,
                class_name or "UniGenerGeneratedTest",
                invoke_args,
                edge_args,
                normalized_framework,
            )
        else:
            body = generated or self._build_pytest_content(
                intent_result,
                similar_cases,
                import_stmt,
                invoke_expr,
                repair_feedback,
            )
        if generated and import_stmt and import_stmt not in body and normalized_framework == "pytest":
            body = f"{import_stmt}\n\n{body}"
        if generated and import_stmt and import_stmt not in body and normalized_framework in {"junit4", "junit5"}:
            body = f"{import_stmt}\n{body}"
        prompt_with_meta = prompt
        if fallback_reason:
            prompt_with_meta = f"{prompt}\n\nfallback_reason={fallback_reason}"
        return GeneratedTestCase(
            target_symbol=intent_result.focal_symbol,
            framework=normalized_framework,
            content=body,
            prompt=prompt_with_meta,
        )

    def _build_few_shot_prompt(
        self,
        intent_result: IntentResult,
        similar_cases: list[SimilarTestCase],
        repair_feedback: str,
        framework: str,
    ) -> str:
        examples = similar_cases[:3]
        sections = [
            f"你是单元测试生成Agent，请基于以下信息输出{framework}测试代码。",
            f"目标函数: {intent_result.focal_symbol}",
            f"意图: {intent_result.intent}",
            "要求:\n- 覆盖正常路径与异常路径\n- 覆盖率目标为100%\n- 复用仓库已有断言风格\n- 返回完整可执行测试函数",
        ]
        if examples:
            blocks: list[str] = []
            for idx, case in enumerate(examples, start=1):
                blocks.append(
                    f"示例{idx}:\n"
                    f"- 测试名称: {case.test_name}\n"
                    f"- 文件: {case.test_file}\n"
                    f"- 内容:\n{case.source}\n"
                )
            blocks_text = "\n".join(blocks)
            sections.append(f"few-shot示例:\n{blocks_text}")
        if repair_feedback.strip():
            sections.append(f"上一轮错误反馈:\n{repair_feedback}")
        return "\n\n".join(sections)

    def _build_pytest_content(
        self,
        intent_result: IntentResult,
        similar_cases: list[SimilarTestCase],
        import_stmt: str,
        invoke_expr: str,
        repair_feedback: str,
    ) -> str:
        symbol = intent_result.focal_symbol
        seed_case = similar_cases[0].test_name if similar_cases else "test_happy_path"
        happy_args = "None" if "missing 1 required positional argument" in repair_feedback else ""
        edge_args = "None"
        return f'''import pytest
{import_stmt}


def test_{symbol}_happy_path():
    result = {invoke_expr}({happy_args})
    assert result is not None


def test_{symbol}_edge_case():
    with pytest.raises(Exception):
        {invoke_expr}({edge_args})


def test_{symbol}_pattern_from_repo():
    """
    意图: {intent_result.intent}
    参考相似用例: {seed_case}
    """
    assert True
'''

    def _infer_import_and_invoke(
        self,
        repo_root: Path,
        focal_file: Path,
        focal_symbol: str,
        language: str,
    ) -> tuple[str, str, str, str, str]:
        if language == "java":
            return self._infer_java_import_and_invoke(focal_file=focal_file, focal_symbol=focal_symbol)
        source = focal_file.read_text(encoding="utf-8")
        class_name = self._find_owner_class_name(source, focal_symbol)
        try:
            relative_file = focal_file.resolve().relative_to(repo_root.resolve())
        except ValueError:
            return "", focal_symbol, class_name, "", ""
        parts = list(relative_file.with_suffix("").parts)
        if parts and parts[0] == "src":
            parts = parts[1:]
        if not parts:
            return "", focal_symbol, class_name, "", ""
        if parts[-1] == "__init__":
            module_path = ".".join(parts[:-1])
        else:
            module_path = ".".join(parts)
        if not module_path:
            return "", focal_symbol, class_name, "", ""
        if class_name:
            return f"from {module_path} import {class_name}", f"{class_name}().{focal_symbol}", class_name, "", ""
        return f"from {module_path} import {focal_symbol}", focal_symbol, class_name, "", ""

    def _find_owner_class_name(self, source: str, focal_symbol: str) -> str:
        tree = ast.parse(source)
        for node in ast.walk(tree):
            if isinstance(node, ast.ClassDef):
                for child in node.body:
                    if isinstance(child, (ast.FunctionDef, ast.AsyncFunctionDef)) and child.name == focal_symbol:
                        return node.name
        return ""

    def _normalize_framework(self, language: str, framework: str) -> str:
        if framework == "auto":
            return "junit5" if language == "java" else "pytest"
        return framework

    def _infer_java_import_and_invoke(self, focal_file: Path, focal_symbol: str) -> tuple[str, str, str, str, str]:
        source = focal_file.read_text(encoding="utf-8")
        package_match = re.search(r"^\s*package\s+([\w.]+)\s*;", source, flags=re.MULTILINE)
        package_name = package_match.group(1) if package_match else ""
        class_match = re.search(r"\bclass\s+([A-Za-z_]\w*)", source)
        class_name = class_match.group(1) if class_match else ""
        static_match = re.search(
            rf"(public|protected|private)?\s*static\s+[\w<>\[\], ?]+\s+{re.escape(focal_symbol)}\s*\(",
            source,
        )
        signature_match = re.search(
            rf"(public|protected|private)?\s*(static\s+)?[\w<>\[\], ?]+\s+{re.escape(focal_symbol)}\s*\(([^)]*)\)",
            source,
        )
        import_stmt = ""
        if package_name and class_name:
            import_stmt = f"import {package_name}.{class_name};"
        elif class_name:
            import_stmt = f"import {class_name};"
        constructor_match = re.search(
            rf"(public|protected|private)?\s*{re.escape(class_name)}\s*\(([^)]*)\)",
            source,
        ) if class_name else None
        is_constructor = bool(class_name and focal_symbol == class_name)
        invoke_expr = f"{class_name}.{focal_symbol}" if static_match and class_name else focal_symbol
        if is_constructor and class_name:
            invoke_expr = f"new {class_name}"
        elif class_name and not static_match:
            invoke_expr = f"new {class_name}().{focal_symbol}"
        method_suffix = focal_symbol[:1].upper() + focal_symbol[1:] if focal_symbol else "Method"
        package_suffix = "".join([part[:1].upper() + part[1:] for part in package_name.split(".") if part]) if package_name else ""
        path_hint_raw = "".join(focal_file.parts[-3:])
        path_hint = re.sub(r"[^A-Za-z0-9]", "", path_hint_raw)
        unique_seed = f"{focal_file.resolve()}::{focal_symbol}"
        unique_suffix = hashlib.md5(unique_seed.encode("utf-8")).hexdigest()[:8]
        test_class_name = (
            f"UniGener{package_suffix}{path_hint}{class_name}{method_suffix}{unique_suffix}Test"
            if class_name
            else f"UniGener{package_suffix}{path_hint}{method_suffix}{unique_suffix}Test"
        )
        invoke_args = ""
        edge_args = ""
        arg_text = ""
        if is_constructor and constructor_match:
            arg_text = constructor_match.group(2).strip()
        elif signature_match:
            arg_text = signature_match.group(3).strip()
        if arg_text:
            arg_items = [item.strip() for item in arg_text.split(",") if item.strip()]
            invoke_args = ", ".join([self._build_java_happy_arg(item) for item in arg_items])
            edge_args = ", ".join([self._build_java_edge_arg(item) for item in arg_items])
        return import_stmt, invoke_expr, test_class_name, invoke_args, edge_args

    def _build_junit_content(
        self,
        intent_result: IntentResult,
        similar_cases: list[SimilarTestCase],
        import_stmt: str,
        invoke_expr: str,
        test_class_name: str,
        invoke_args: str,
        edge_args: str,
        framework: str,
    ) -> str:
        symbol = intent_result.focal_symbol
        seed_case = similar_cases[0].test_name if similar_cases else "generatedPattern"
        if framework == "junit4":
            return f'''import org.junit.Test;
import static org.junit.Assert.*;
{import_stmt}

public class {test_class_name} {{

    @Test
    public void test{symbol[0].upper() + symbol[1:]}HappyPath() {{
        Object result = {invoke_expr}({invoke_args});
        assertNotNull(result);
    }}

    @Test(expected = Exception.class)
    public void test{symbol[0].upper() + symbol[1:]}EdgeCase() {{
        {invoke_expr}({edge_args});
    }}

    @Test
    public void test{symbol[0].upper() + symbol[1:]}PatternFromRepo() {{
        String intent = "{intent_result.intent}";
        String refCase = "{seed_case}";
        assertNotNull(intent);
        assertNotNull(refCase);
    }}
}}
'''
        return f'''import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
{import_stmt}

public class {test_class_name} {{

    @Test
    void test{symbol[0].upper() + symbol[1:]}HappyPath() {{
        Object result = {invoke_expr}({invoke_args});
        assertNotNull(result);
    }}

    @Test
    void test{symbol[0].upper() + symbol[1:]}EdgeCase() {{
        assertThrows(Exception.class, () -> {invoke_expr}({edge_args}));
    }}

    @Test
    void test{symbol[0].upper() + symbol[1:]}PatternFromRepo() {{
        String intent = "{intent_result.intent}";
        String refCase = "{seed_case}";
        assertNotNull(intent);
        assertNotNull(refCase);
    }}
}}
'''

    def _build_java_happy_arg(self, raw_param: str) -> str:
        type_name = " ".join(raw_param.split(" ")[:-1]).strip()
        if "String" in type_name:
            return '"sample"'
        if "boolean" in type_name or "Boolean" in type_name:
            return "true"
        if "long" in type_name or "Long" in type_name:
            return "1L"
        if "double" in type_name or "Double" in type_name:
            return "1.0"
        if "float" in type_name or "Float" in type_name:
            return "1.0f"
        if "byte" in type_name or "short" in type_name or "int" in type_name or "Integer" in type_name:
            return "1"
        return "null"

    def _build_java_edge_arg(self, raw_param: str) -> str:
        type_name = " ".join(raw_param.split(" ")[:-1]).strip()
        primitives = {"byte", "short", "int", "long", "float", "double", "boolean", "char"}
        if any(item in type_name for item in primitives):
            if "boolean" in type_name:
                return "false"
            if "char" in type_name:
                return "'\\0'"
            return "0"
        return "null"

    def _sanitize_generated_output(self, generated: str | None, framework: str) -> str | None:
        if not generated:
            return None
        if framework in {"junit4", "junit5"}:
            return self._sanitize_junit_output(generated)
        return self._sanitize_pytest_output(generated)

    def _sanitize_junit_output(self, content: str) -> str | None:
        block = self._extract_code_block(content, "java")
        candidate = block or content
        candidate = self._trim_to_code_start(candidate, ["import ", "public class ", "class ", "@Test"])
        candidate = candidate.strip()
        if "public class " not in candidate:
            candidate = re.sub(r"\bclass\s+([A-Za-z_]\w*)", r"public class \1", candidate, count=1)
        if "public class " not in candidate:
            return None
        if "@Test" not in candidate:
            return None
        return candidate

    def _sanitize_pytest_output(self, content: str) -> str | None:
        block = self._extract_code_block(content, "python")
        candidate = block or content
        candidate = self._trim_to_code_start(candidate, ["import ", "from ", "def test_"])
        candidate = candidate.strip()
        if "def test_" not in candidate:
            return None
        return candidate

    def _extract_code_block(self, content: str, language: str) -> str | None:
        pattern = re.compile(r"```([A-Za-z0-9_-]*)\s*([\s\S]*?)```")
        fallback_block = ""
        for lang, body in pattern.findall(content):
            normalized_lang = lang.lower().strip()
            code = body.strip()
            if not code:
                continue
            if normalized_lang == language:
                return code
            if not normalized_lang and not fallback_block:
                fallback_block = code
        return fallback_block or None

    def _trim_to_code_start(self, content: str, prefixes: list[str]) -> str:
        start_positions = [content.find(prefix) for prefix in prefixes if content.find(prefix) >= 0]
        if not start_positions:
            return content
        start = min(start_positions)
        return content[start:]

    def _force_java_test_class_name(self, content: str, expected_class_name: str) -> str | None:
        if not content:
            return None
        replaced = re.sub(
            r"\bpublic\s+class\s+([A-Za-z_]\w*)",
            f"public class {expected_class_name}",
            content,
            count=1,
        )
        if "public class " in replaced:
            return replaced
        replaced = re.sub(
            r"\bclass\s+([A-Za-z_]\w*)",
            f"public class {expected_class_name}",
            replaced,
            count=1,
        )
        if "public class " in replaced:
            return replaced
        return None
