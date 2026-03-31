from __future__ import annotations

from pathlib import Path

from unigener.models import SimilarTestCase
from unigener.retrievers.similar_tests import SimilarTestRetriever


class SimilarTestRetrievalAgent:
    name = "similar-test-retrieval-agent"

    def __init__(self) -> None:
        self._retriever = SimilarTestRetriever()

    def run(self, repo_root: Path, focal_symbol: str, intent: str, language: str = "python") -> list[SimilarTestCase]:
        return self._retriever.retrieve(
            repo_root=repo_root,
            focal_symbol=focal_symbol,
            intent=intent,
            language=language,
        )
