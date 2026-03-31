from __future__ import annotations

import ast
import re
from pathlib import Path

from unigener.models import SimilarTestCase


class SimilarTestRetriever:
    def retrieve(
        self,
        repo_root: Path,
        focal_symbol: str,
        intent: str,
        language: str = "python",
        top_k: int = 5,
    ) -> list[SimilarTestCase]:
        tokens = set(focal_symbol.lower().split("_")) | set(intent.lower().split())
        candidates: list[SimilarTestCase] = []
        for test_file in self._iter_test_files(repo_root, language):
            source = test_file.read_text(encoding="utf-8")
            if language == "java":
                candidates.extend(self._extract_java_cases(test_file, source, focal_symbol, tokens))
                continue
            try:
                tree = ast.parse(source)
            except SyntaxError:
                continue
            for node in ast.walk(tree):
                if isinstance(node, ast.FunctionDef) and node.name.startswith("test_"):
                    test_source = self._extract_source(source, node)
                    score = self._score(node.name, test_source, tokens)
                    if score <= 0:
                        continue
                    candidates.append(
                        SimilarTestCase(
                            focal_symbol=focal_symbol,
                            test_file=test_file,
                            test_name=node.name,
                            score=score,
                            source=test_source,
                        )
                    )
        candidates.sort(key=lambda x: x.score, reverse=True)
        return candidates[:top_k]

    def _iter_test_files(self, repo_root: Path, language: str):
        if language == "java":
            for file_path in repo_root.rglob("*Test.java"):
                yield file_path
            for file_path in repo_root.rglob("*Tests.java"):
                yield file_path
            return
        for file_path in repo_root.rglob("*.py"):
            p = str(file_path).lower()
            if "/test" in p or p.endswith("_test.py") or p.endswith("tests.py"):
                yield file_path

    def _extract_source(self, file_source: str, node: ast.FunctionDef) -> str:
        lines = file_source.splitlines()
        start = max(node.lineno - 1, 0)
        end = min(getattr(node, "end_lineno", node.lineno), len(lines))
        return "\n".join(lines[start:end]).strip()

    def _score(self, test_name: str, test_source: str, tokens: set[str]) -> float:
        text = f"{test_name} {test_source}".lower()
        hit = sum(1 for t in tokens if t and t in text)
        return float(hit)

    def _extract_java_cases(
        self,
        test_file: Path,
        source: str,
        focal_symbol: str,
        tokens: set[str],
    ) -> list[SimilarTestCase]:
        results: list[SimilarTestCase] = []
        lines = source.splitlines()
        for idx, line in enumerate(lines):
            if "@Test" not in line:
                continue
            if idx + 1 >= len(lines):
                continue
            method_line = lines[idx + 1]
            method_match = re.search(r"\bvoid\s+([A-Za-z_]\w*)\s*\(", method_line)
            if not method_match:
                continue
            test_name = method_match.group(1)
            start = max(idx - 1, 0)
            end = min(idx + 8, len(lines))
            test_source = "\n".join(lines[start:end]).strip()
            score = self._score(test_name, test_source, tokens)
            if score <= 0:
                continue
            results.append(
                SimilarTestCase(
                    focal_symbol=focal_symbol,
                    test_file=test_file,
                    test_name=test_name,
                    score=score,
                    source=test_source,
                )
            )
        return results
