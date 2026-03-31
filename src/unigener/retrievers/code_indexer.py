from __future__ import annotations

import ast
import re
from dataclasses import dataclass, field
from pathlib import Path

from unigener.models import CodeSnippet


@dataclass(slots=True)
class CodeIndex:
    definitions: dict[str, list[CodeSnippet]] = field(default_factory=dict)
    imports: dict[str, list[CodeSnippet]] = field(default_factory=dict)


class RepoCodeIndexer:
    def build(self, repo_root: Path, language: str = "python") -> CodeIndex:
        index = CodeIndex()
        pattern = "*.java" if language == "java" else "*.py"
        for file_path in repo_root.rglob(pattern):
            if "site-packages" in str(file_path) or "/target/" in str(file_path).replace("\\", "/"):
                continue
            self._index_file(index, file_path, language)
        return index

    def retrieve(self, index: CodeIndex, symbols: list[str]) -> list[CodeSnippet]:
        results: list[CodeSnippet] = []
        for symbol in symbols:
            results.extend(index.definitions.get(symbol, []))
            results.extend(index.imports.get(symbol, []))
        seen: set[tuple[str, str]] = set()
        deduped: list[CodeSnippet] = []
        for item in results:
            key = (item.symbol, str(item.file_path))
            if key in seen:
                continue
            seen.add(key)
            deduped.append(item)
        return deduped

    def _index_file(self, index: CodeIndex, file_path: Path, language: str) -> None:
        if language == "java":
            self._index_java_file(index, file_path)
            return
        source = file_path.read_text(encoding="utf-8")
        try:
            tree = ast.parse(source)
        except SyntaxError:
            return
        source_lines = source.splitlines()
        for node in ast.walk(tree):
            if isinstance(node, (ast.FunctionDef, ast.AsyncFunctionDef, ast.ClassDef)):
                snippet = self._node_snippet(
                    symbol=node.name,
                    file_path=file_path,
                    source_lines=source_lines,
                    node=node,
                    reason="definition",
                )
                index.definitions.setdefault(node.name, []).append(snippet)
            elif isinstance(node, (ast.Import, ast.ImportFrom)):
                for alias in node.names:
                    import_symbol = alias.asname or alias.name.split(".")[-1]
                    snippet = self._node_snippet(
                        symbol=import_symbol,
                        file_path=file_path,
                        source_lines=source_lines,
                        node=node,
                        reason="import",
                    )
                    index.imports.setdefault(import_symbol, []).append(snippet)
            elif isinstance(node, ast.Assign):
                for target in node.targets:
                    if isinstance(target, ast.Name):
                        snippet = self._node_snippet(
                            symbol=target.id,
                            file_path=file_path,
                            source_lines=source_lines,
                            node=node,
                            reason="variable",
                        )
                        index.definitions.setdefault(target.id, []).append(snippet)

    def _index_java_file(self, index: CodeIndex, file_path: Path) -> None:
        source = file_path.read_text(encoding="utf-8")
        lines = source.splitlines()
        for idx, line in enumerate(lines):
            class_match = re.search(r"\bclass\s+([A-Za-z_]\w*)", line)
            if class_match:
                symbol = class_match.group(1)
                snippet = self._line_snippet(symbol, file_path, lines, idx, "definition")
                index.definitions.setdefault(symbol, []).append(snippet)
            method_match = re.search(
                r"(public|protected|private)?\s*(static\s+)?[\w<>\[\], ?]+\s+([A-Za-z_]\w*)\s*\(",
                line,
            )
            if method_match:
                symbol = method_match.group(3)
                snippet = self._line_snippet(symbol, file_path, lines, idx, "definition")
                index.definitions.setdefault(symbol, []).append(snippet)
            import_match = re.search(r"^\s*import\s+([\w.*]+);", line)
            if import_match:
                import_path = import_match.group(1)
                import_symbol = import_path.split(".")[-1]
                snippet = self._line_snippet(import_symbol, file_path, lines, idx, "import")
                index.imports.setdefault(import_symbol, []).append(snippet)

    def _node_snippet(
        self,
        symbol: str,
        file_path: Path,
        source_lines: list[str],
        node: ast.AST,
        reason: str,
    ) -> CodeSnippet:
        start = max(getattr(node, "lineno", 1) - 1, 0)
        end = min(getattr(node, "end_lineno", start + 1), len(source_lines))
        snippet = "\n".join(source_lines[start:end]).strip()
        return CodeSnippet(
            symbol=symbol,
            file_path=file_path,
            source=snippet,
            reason=reason,
        )

    def _line_snippet(
        self,
        symbol: str,
        file_path: Path,
        source_lines: list[str],
        line_index: int,
        reason: str,
    ) -> CodeSnippet:
        start = max(line_index - 2, 0)
        end = min(line_index + 3, len(source_lines))
        snippet = "\n".join(source_lines[start:end]).strip()
        return CodeSnippet(
            symbol=symbol,
            file_path=file_path,
            source=snippet,
            reason=reason,
        )
