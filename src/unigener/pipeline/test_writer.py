from __future__ import annotations

import re
from pathlib import Path

from unigener.models import GeneratedTestCase


class GeneratedTestWriter:
    def write(
        self,
        repo_root: Path,
        tests_dir: str,
        focal_symbol: str,
        generated: GeneratedTestCase,
    ) -> Path:
        target_dir = repo_root / tests_dir
        target_dir.mkdir(parents=True, exist_ok=True)
        safe_symbol = focal_symbol.replace(".", "_")
        if generated.framework in {"junit4", "junit5"}:
            class_name = self._extract_java_public_class_name(generated.content)
            if not class_name:
                class_name = f"UniGener{safe_symbol[:1].upper() + safe_symbol[1:]}Test"
            target_file = target_dir / f"{class_name}.java"
            content = self._ensure_java_package_declaration(generated.content, tests_dir)
        else:
            target_file = target_dir / f"test_unigener_{safe_symbol}.py"
            content = generated.content
        target_file.write_text(content, encoding="utf-8")
        return target_file

    def _extract_java_public_class_name(self, source: str) -> str:
        match = re.search(r"\bpublic\s+class\s+([A-Za-z_]\w*)", source)
        if not match:
            return ""
        return match.group(1)

    def _ensure_java_package_declaration(self, source: str, tests_dir: str) -> str:
        if re.search(r"^\s*package\s+[\w.]+\s*;", source, flags=re.MULTILINE):
            return source
        normalized = tests_dir.replace("\\", "/").strip("/")
        marker = "src/test/java/"
        if marker not in normalized:
            return source
        package_path = normalized.split(marker, 1)[1].strip("/")
        if not package_path:
            return source
        package_name = package_path.replace("/", ".")
        return f"package {package_name};\n\n{source}"
