from __future__ import annotations

from dataclasses import dataclass, field


@dataclass(slots=True)
class IntentToCodeRequest:
    unresolved_symbols: list[str] = field(default_factory=list)
    import_queries: list[str] = field(default_factory=list)


@dataclass(slots=True)
class SimilarCaseRequest:
    focal_symbol: str
    intent: str
