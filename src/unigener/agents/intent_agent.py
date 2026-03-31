from __future__ import annotations

import ast
import json
import os
import re
from datetime import datetime, timezone
from pathlib import Path

from unigener.agents.llm_client import LLMClient
from unigener.models import CodeSnippet, IntentResult

_PYTHON_BUILTINS = {
    "len",
    "str",
    "int",
    "float",
    "bool",
    "list",
    "dict",
    "set",
    "tuple",
    "sum",
    "min",
    "max",
    "range",
    "isinstance",
    "Exception",
    "ValueError",
    "TypeError",
    "print",
}


class IntentRecognitionAgent:
    name = "intent-recognition-agent"

    def __init__(self) -> None:
        self._llm_client = LLMClient()

    def run(
        self,
        focal_file: Path,
        focal_symbol: str,
        language: str = "python",
        llm_provider: str = "none",
        llm_model: str = "",
        code_context: list[CodeSnippet] | None = None,
        enable_query_planning: bool = False,
    ) -> IntentResult:
        if language == "java":
            return self._run_java(
                focal_file=focal_file,
                focal_symbol=focal_symbol,
                llm_provider=llm_provider,
                llm_model=llm_model,
                code_context=code_context or [],
                enable_query_planning=enable_query_planning,
            )
        source = focal_file.read_text(encoding="utf-8")
        tree = ast.parse(source)
        node = self._find_symbol(tree, focal_symbol)
        if node is None:
            return IntentResult(
                focal_symbol=focal_symbol,
                intent=f"无法在 {focal_file.name} 中找到符号 {focal_symbol}",
                uncovered_symbols=[],
            )
        default_intent = self._build_intent(node)
        context = ast.get_source_segment(source, node) or ""
        uncovered = self._find_uncovered_symbols(node)
        if llm_provider != "none" and enable_query_planning and not code_context:
            retrieval_queries, intent_draft = self._plan_retrieval_queries_with_llm(
                default_intent=default_intent,
                context=context,
                focal_symbol=focal_symbol,
                language=language,
                llm_provider=llm_provider,
                llm_model=llm_model,
                focal_file=focal_file,
            )
            return IntentResult(
                focal_symbol=focal_symbol,
                intent=intent_draft,
                uncovered_symbols=sorted(uncovered),
                need_context=bool(retrieval_queries),
                retrieval_queries=retrieval_queries,
            )
        intent = self._build_intent_with_llm(
            default_intent=default_intent,
            context=context,
            focal_symbol=focal_symbol,
            language=language,
            llm_provider=llm_provider,
            llm_model=llm_model,
            code_context=code_context or [],
        )
        return IntentResult(
            focal_symbol=focal_symbol,
            intent=intent,
            uncovered_symbols=sorted(uncovered),
            need_context=False,
            retrieval_queries=[],
        )

    def _find_symbol(
        self,
        tree: ast.AST,
        focal_symbol: str,
    ) -> ast.FunctionDef | ast.AsyncFunctionDef | ast.ClassDef | None:
        for node in ast.walk(tree):
            if isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef, ast.ClassDef)):
                if node.name == focal_symbol:
                    return node
        return None

    def _build_intent(self, node: ast.FunctionDef | ast.AsyncFunctionDef | ast.ClassDef) -> str:
        if isinstance(node, ast.ClassDef):
            return f"该类负责封装 {node.name} 的行为，需覆盖初始化、关键方法和异常路径。"
        args = [arg.arg for arg in node.args.args] if isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef)) else []
        action = node.name.replace("_", " ")
        return f"该函数意图为 {action}，输入参数包含 {', '.join(args) if args else '无显式参数'}。"

    def _find_uncovered_symbols(self, node: ast.AST) -> set[str]:
        locals_defined: set[str] = set()
        used: set[str] = set()
        for child in ast.walk(node):
            if isinstance(child, ast.arg):
                locals_defined.add(child.arg)
            elif isinstance(child, ast.Name):
                if isinstance(child.ctx, ast.Store):
                    locals_defined.add(child.id)
                elif isinstance(child.ctx, ast.Load):
                    used.add(child.id)
        uncovered = used - locals_defined - _PYTHON_BUILTINS
        if "self" in uncovered:
            uncovered.remove("self")
        return uncovered

    def _run_java(
        self,
        focal_file: Path,
        focal_symbol: str,
        llm_provider: str,
        llm_model: str,
        code_context: list[CodeSnippet],
        enable_query_planning: bool,
    ) -> IntentResult:
        source = focal_file.read_text(encoding="utf-8")
        method_match = re.search(
            rf"(public|protected|private)?\s*(static\s+)?[\w<>\[\], ?]+\s+{re.escape(focal_symbol)}\s*\(([^)]*)\)",
            source,
        )
        class_match = re.search(r"class\s+([A-Za-z_]\w*)", source)
        class_name = class_match.group(1) if class_match else ""
        if method_match:
            params = method_match.group(3).strip()
            param_names = []
            if params:
                for item in params.split(","):
                    name = item.strip().split(" ")[-1].strip()
                    if name:
                        param_names.append(name)
            default_intent = (
                f"该Java方法意图为 {focal_symbol}，所属类为 {class_name or '未知类'}，"
                f"输入参数包含 {', '.join(param_names) if param_names else '无显式参数'}。"
            )
            context = method_match.group(0)
            uncovered = self._find_java_uncovered_symbols(source, set(param_names))
            if llm_provider != "none" and enable_query_planning and not code_context:
                retrieval_queries, intent_draft = self._plan_retrieval_queries_with_llm(
                    default_intent=default_intent,
                    context=context,
                    focal_symbol=focal_symbol,
                    language="java",
                    llm_provider=llm_provider,
                    llm_model=llm_model,
                    focal_file=focal_file,
                )
                return IntentResult(
                    focal_symbol=focal_symbol,
                    intent=intent_draft,
                    uncovered_symbols=sorted(uncovered),
                    need_context=bool(retrieval_queries),
                    retrieval_queries=retrieval_queries,
                )
            intent = self._build_intent_with_llm(
                default_intent,
                context,
                focal_symbol,
                "java",
                llm_provider,
                llm_model,
                code_context,
            )
            return IntentResult(
                focal_symbol=focal_symbol,
                intent=intent,
                uncovered_symbols=sorted(uncovered),
                need_context=False,
                retrieval_queries=[],
            )
        if class_name and class_name == focal_symbol:
            return IntentResult(
                focal_symbol=focal_symbol,
                intent=f"该Java类负责封装 {class_name} 的行为，需覆盖构造、关键方法与异常路径。",
                uncovered_symbols=[],
            )
        return IntentResult(
            focal_symbol=focal_symbol,
            intent=f"无法在 {focal_file.name} 中找到符号 {focal_symbol}",
            uncovered_symbols=[],
        )

    def _find_java_uncovered_symbols(self, source: str, locals_defined: set[str]) -> set[str]:
        called = set(re.findall(r"\b([A-Za-z_]\w*)\s*\(", source))
        java_keywords = {
            "if",
            "for",
            "while",
            "switch",
            "catch",
            "return",
            "new",
            "throw",
            "assert",
            "this",
            "super",
        }
        return {item for item in called if item not in locals_defined and item not in java_keywords}

    def _build_intent_with_llm(
        self,
        default_intent: str,
        context: str,
        focal_symbol: str,
        language: str,
        llm_provider: str,
        llm_model: str,
        code_context: list[CodeSnippet],
    ) -> str:
        if llm_provider == "none":
            return default_intent
        sections = [
            "你是意图识别Agent。请根据目标代码生成一句中文意图描述，用于后续单元测试生成。",
            "要求：\n- 一句话\n- 包含核心行为与参数要点\n- 不要输出Markdown",
            f"语言: {language}",
            f"目标符号: {focal_symbol}",
            f"代码上下文:\n{context[:1200] if context else '无'}",
            f"默认意图参考:\n{default_intent}",
        ]
        formatted_context = self._format_code_context(code_context)
        if formatted_context:
            sections.append(f"可用检索上下文:\n{formatted_context}")
        prompt = "\n\n".join(sections)
        generated = self._llm_client.generate(
            prompt=prompt,
            provider=llm_provider,
            model=llm_model,
            metadata={
                "agent": self.name,
                "language": language,
                "focal_symbol": focal_symbol,
                "task": "intent_generation",
            },
        )
        return generated or default_intent

    def _plan_retrieval_queries_with_llm(
        self,
        default_intent: str,
        context: str,
        focal_symbol: str,
        language: str,
        llm_provider: str,
        llm_model: str,
        focal_file: Path,
    ) -> tuple[list[str], str]:
        if llm_provider == "none":
            return [], default_intent
        plan_prompt = (
            "你是意图识别Agent。先判断是否需要额外代码上下文才能准确生成意图。\n"
            "若需要，请给出可用于代码检索的queries。\n"
            "只输出JSON，不要输出其他内容。格式如下：\n"
            '{"need_context": true, "queries": ["..."], "intent_draft": "...", "reason": "..."}\n'
            f"语言: {language}\n"
            f"目标符号: {focal_symbol}\n"
            f"代码上下文:\n{context[:1200] if context else '无'}\n"
            f"默认意图参考:\n{default_intent}"
        )
        generated = self._llm_client.generate(
            prompt=plan_prompt,
            provider=llm_provider,
            model=llm_model,
            metadata={
                "agent": self.name,
                "language": language,
                "focal_symbol": focal_symbol,
                "task": "intent_planning",
            },
        )
        if not generated:
            self._append_planning_log(
                focal_file=focal_file,
                focal_symbol=focal_symbol,
                language=language,
                plan_prompt=plan_prompt,
                raw_response="",
                parsed_response={},
                sanitized_queries=[],
                need_context=False,
                intent_draft=default_intent,
                reason="empty_llm_response",
            )
            return [], default_intent
        parsed = self._parse_planning_json(generated)
        if not parsed:
            self._append_planning_log(
                focal_file=focal_file,
                focal_symbol=focal_symbol,
                language=language,
                plan_prompt=plan_prompt,
                raw_response=generated,
                parsed_response={},
                sanitized_queries=[],
                need_context=False,
                intent_draft=default_intent,
                reason="invalid_planning_json",
            )
            return [], default_intent
        raw_queries = parsed.get("queries", [])
        intent_draft = parsed.get("intent_draft", "")
        queries = self._sanitize_queries(raw_queries)
        if not isinstance(intent_draft, str) or not intent_draft.strip():
            intent_draft = default_intent
        need_context = bool(parsed.get("need_context")) and bool(queries)
        self._append_planning_log(
            focal_file=focal_file,
            focal_symbol=focal_symbol,
            language=language,
            plan_prompt=plan_prompt,
            raw_response=generated,
            parsed_response=parsed,
            sanitized_queries=queries,
            need_context=need_context,
            intent_draft=intent_draft.strip(),
            reason=str(parsed.get("reason", "")),
        )
        if not need_context:
            return [], intent_draft
        return queries, intent_draft.strip()

    def _parse_planning_json(self, text: str) -> dict | None:
        try:
            parsed = json.loads(text)
            if isinstance(parsed, dict):
                return parsed
        except Exception:
            pass
        json_match = re.search(r"\{[\s\S]*\}", text)
        if not json_match:
            return None
        try:
            parsed = json.loads(json_match.group(0))
        except Exception:
            return None
        if not isinstance(parsed, dict):
            return None
        return parsed

    def _sanitize_queries(self, queries: object) -> list[str]:
        if not isinstance(queries, list):
            return []
        sanitized: list[str] = []
        seen: set[str] = set()
        java_keywords = {
            "if",
            "for",
            "while",
            "switch",
            "catch",
            "return",
            "new",
            "throw",
            "assert",
            "this",
            "super",
        }
        for item in queries:
            if not isinstance(item, str):
                continue
            query = item.strip()
            if not query:
                continue
            if len(query) > 80:
                query = query[:80]
            lowered = query.lower()
            if lowered in java_keywords:
                continue
            if query in seen:
                continue
            seen.add(query)
            sanitized.append(query)
            if len(sanitized) >= 6:
                break
        return sanitized

    def _format_code_context(self, snippets: list[CodeSnippet]) -> str:
        if not snippets:
            return ""
        blocks: list[str] = []
        for idx, snippet in enumerate(snippets[:6], start=1):
            source = snippet.source.strip()
            if len(source) > 500:
                source = source[:500]
            blocks.append(
                f"[{idx}] symbol={snippet.symbol}\n"
                f"file={snippet.file_path}\n"
                f"reason={snippet.reason}\n"
                f"source:\n{source}"
            )
        return "\n\n".join(blocks)

    def _append_planning_log(
        self,
        focal_file: Path,
        focal_symbol: str,
        language: str,
        plan_prompt: str,
        raw_response: str,
        parsed_response: dict,
        sanitized_queries: list[str],
        need_context: bool,
        intent_draft: str,
        reason: str,
    ) -> None:
        log_path = self._resolve_planning_log_path()
        record = {
            "timestamp": datetime.now(timezone.utc).isoformat(),
            "agent": self.name,
            "focal_file": str(focal_file),
            "focal_symbol": focal_symbol,
            "language": language,
            "need_context": need_context,
            "sanitized_queries": sanitized_queries,
            "reason": reason,
            "intent_draft": intent_draft,
            "plan_prompt_length": len(plan_prompt),
            "plan_prompt_preview": plan_prompt[:240],
            "raw_response_length": len(raw_response),
            "raw_response": raw_response,
            "parsed_response": parsed_response,
        }
        try:
            log_path.parent.mkdir(parents=True, exist_ok=True)
            with log_path.open("a", encoding="utf-8") as fp:
                fp.write(json.dumps(record, ensure_ascii=False) + "\n")
        except Exception:
            return

    def _resolve_planning_log_path(self) -> Path:
        custom = os.getenv("UNIGENER_INTENT_PLAN_LOG_PATH", "").strip()
        if custom:
            return Path(custom).expanduser()
        root = Path(__file__).resolve().parents[3]
        return root / "logs" / "intent_planning.jsonl"
