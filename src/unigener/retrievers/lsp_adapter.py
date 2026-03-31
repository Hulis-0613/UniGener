from __future__ import annotations

import importlib
import importlib.util
from pathlib import Path

from unigener.models import CodeSnippet


class LSPCodeRetriever:
    def __init__(self) -> None:
        self._jedi_available = importlib.util.find_spec("jedi") is not None

    def retrieve(self, repo_root: Path, symbols: list[str], language: str = "python") -> list[CodeSnippet]:
        if language != "python":
            return []
        if not self._jedi_available:
            return []
        jedi = importlib.import_module("jedi")

        targets = {s for s in symbols if s}
        results: list[CodeSnippet] = []
        for file_path in repo_root.rglob("*.py"):
            if "site-packages" in str(file_path):
                continue
            source = file_path.read_text(encoding="utf-8")
            script = jedi.Script(code=source, path=str(file_path))
            names = script.get_names(all_scopes=True, definitions=True, references=False)
            for name in names:
                if name.name not in targets:
                    continue
                start, end = name.line, name.get_definition_end_position()[0]
                snippet = self._extract_snippet(source, start, end)
                results.append(
                    CodeSnippet(
                        symbol=name.name,
                        file_path=file_path,
                        source=snippet,
                        reason="lsp-definition",
                    )
                )
        return results

    def _extract_snippet(self, source: str, start_line: int, end_line: int) -> str:
        lines = source.splitlines()
        start = max(start_line - 1, 0)
        end = min(max(end_line, start_line), len(lines))
        return "\n".join(lines[start:end]).strip()
