from __future__ import annotations

import os
import re
from datetime import datetime, timezone
from pathlib import Path


def ensure_session_id(repo_root: Path | None = None, requested_session_id: str = "") -> str:
    raw = requested_session_id.strip() or os.getenv("UNIGENER_SESSION_ID", "").strip()
    if not raw:
        stamp = datetime.now(timezone.utc).strftime("%Y%m%dT%H%M%SZ")
        raw = f"sess_{stamp}"
    session_id = _sanitize(raw)
    os.environ["UNIGENER_SESSION_ID"] = session_id
    if repo_root is not None:
        os.environ["UNIGENER_LOG_REPO_ROOT"] = str(repo_root.resolve())
    return session_id


def current_session_id() -> str:
    raw = os.getenv("UNIGENER_SESSION_ID", "").strip()
    return _sanitize(raw) if raw else "sess_unknown"


def resolve_log_path(
    file_name: str,
    category: str,
    repo_root: Path | None = None,
) -> Path:
    root = _project_root() / "logs"
    scope_repo = repo_root
    if scope_repo is None:
        raw_repo = os.getenv("UNIGENER_LOG_REPO_ROOT", "").strip()
        if raw_repo:
            scope_repo = Path(raw_repo)
    repo_key = _sanitize(scope_repo.name if scope_repo is not None else "global")
    session_id = current_session_id()
    safe_category = _sanitize(category)
    return root / repo_key / session_id / safe_category / file_name


def _project_root() -> Path:
    return Path(__file__).resolve().parents[3]


def _sanitize(value: str) -> str:
    return re.sub(r"[^A-Za-z0-9_.-]+", "_", value).strip("_") or "default"
