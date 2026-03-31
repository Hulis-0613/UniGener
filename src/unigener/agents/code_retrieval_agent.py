from __future__ import annotations

import json
import os
from datetime import datetime, timezone
from pathlib import Path

from unigener.models import CodeSnippet
from unigener.retrievers.code_indexer import RepoCodeIndexer
from unigener.retrievers.lsp_adapter import LSPCodeRetriever


class CodeRetrievalAgent:
    name = "code-retrieval-agent"

    def __init__(self) -> None:
        self._indexer = RepoCodeIndexer()
        self._lsp = LSPCodeRetriever()
        self._cached_repo: Path | None = None
        self._cached_language: str | None = None
        self._index = None

    def run(self, repo_root: Path, symbols: list[str], mode: str = "hybrid", language: str = "python") -> list[CodeSnippet]:
        if self._index is None or self._cached_repo != repo_root or self._cached_language != language:
            self._index = self._indexer.build(repo_root, language=language)
            self._cached_repo = repo_root
            self._cached_language = language
        ast_results = self._indexer.retrieve(self._index, symbols)
        results: list[CodeSnippet]
        if mode == "ast":
            results = ast_results
            self._append_retrieval_log(
                repo_root=repo_root,
                symbols=symbols,
                mode=mode,
                language=language,
                ast_results=ast_results,
                lsp_results=[],
                final_results=results,
            )
            return results
        lsp_results = self._lsp.retrieve(repo_root, symbols, language=language)
        if mode == "lsp":
            results = self._dedupe(lsp_results)
        else:
            results = self._dedupe(ast_results + lsp_results)
        self._append_retrieval_log(
            repo_root=repo_root,
            symbols=symbols,
            mode=mode,
            language=language,
            ast_results=ast_results,
            lsp_results=lsp_results,
            final_results=results,
        )
        return results

    def _dedupe(self, snippets: list[CodeSnippet]) -> list[CodeSnippet]:
        seen: set[tuple[str, str, str]] = set()
        deduped: list[CodeSnippet] = []
        for item in snippets:
            key = (item.symbol, str(item.file_path), item.reason)
            if key in seen:
                continue
            seen.add(key)
            deduped.append(item)
        return deduped

    def _append_retrieval_log(
        self,
        repo_root: Path,
        symbols: list[str],
        mode: str,
        language: str,
        ast_results: list[CodeSnippet],
        lsp_results: list[CodeSnippet],
        final_results: list[CodeSnippet],
    ) -> None:
        log_path = self._resolve_retrieval_log_path()
        record = {
            "timestamp": datetime.now(timezone.utc).isoformat(),
            "agent": self.name,
            "repo_root": str(repo_root),
            "mode": mode,
            "language": language,
            "query_symbols": symbols,
            "query_count": len(symbols),
            "ast_result_count": len(ast_results),
            "lsp_result_count": len(lsp_results),
            "final_result_count": len(final_results),
            "results": [self._snippet_to_log_item(item) for item in final_results[:30]],
        }
        try:
            log_path.parent.mkdir(parents=True, exist_ok=True)
            with log_path.open("a", encoding="utf-8") as fp:
                fp.write(json.dumps(record, ensure_ascii=False) + "\n")
        except Exception:
            return

    def _snippet_to_log_item(self, snippet: CodeSnippet) -> dict[str, str | int]:
        source = snippet.source.strip()
        preview = source[:400]
        return {
            "symbol": snippet.symbol,
            "file_path": str(snippet.file_path),
            "reason": snippet.reason,
            "source_length": len(source),
            "source_preview": preview,
        }

    def _resolve_retrieval_log_path(self) -> Path:
        custom = os.getenv("UNIGENER_CODE_RETRIEVAL_LOG_PATH", "").strip()
        if custom:
            return Path(custom).expanduser()
        root = Path(__file__).resolve().parents[3]
        return root / "logs" / "code_retrieval.jsonl"
