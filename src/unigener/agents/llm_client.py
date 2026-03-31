from __future__ import annotations

import json
import os
import time
from datetime import datetime, timezone
from pathlib import Path
from urllib import error
from urllib import request


class LLMClient:
    def __init__(self) -> None:
        self._dotenv_loaded = False
        self._last_error = ""

    def generate(
        self,
        prompt: str,
        provider: str,
        model: str,
        metadata: dict[str, str | int | bool] | None = None,
    ) -> str | None:
        self._last_error = ""
        self._load_dotenv_once()
        selected_model = model
        if provider == "openai":
            selected_model = model or os.getenv("OPENAI_MODEL", "gpt-4o-mini")
        elif provider == "ollama":
            selected_model = model or os.getenv("OLLAMA_MODEL", "qwen2.5-coder:7b")
        result: str | None = None
        attempt_count = 0
        if provider == "openai":
            result, attempt_count = self._call_openai(prompt, selected_model)
        elif provider == "ollama":
            result, attempt_count = self._call_ollama(prompt, selected_model)
        else:
            self._last_error = f"unsupported_provider:{provider}"
        self._append_api_log(
            provider=provider,
            model=selected_model,
            prompt=prompt,
            response=result or "",
            metadata=metadata or {},
            attempt_count=attempt_count,
            success=bool(result),
            error_message=self._last_error,
        )
        return result

    @property
    def last_error(self) -> str:
        return self._last_error

    def _call_openai(self, prompt: str, model: str) -> tuple[str | None, int]:
        api_key = os.getenv("OPENAI_API_KEY", "")
        if not api_key:
            self._last_error = "missing_openai_api_key"
            return None, 0
        base_url = os.getenv("OPENAI_BASE_URL", "https://api.openai.com")
        payload = {
            "model": model,
            "messages": [
                {"role": "system", "content": "你是专业的Python单元测试生成助手。"},
                {"role": "user", "content": prompt},
            ],
            "temperature": 0.2,
        }
        data = json.dumps(payload).encode("utf-8")
        url = self._build_openai_chat_url(base_url)
        for attempt in range(3):
            req = request.Request(
                url=url,
                data=data,
                headers={
                    "Content-Type": "application/json",
                    "Authorization": f"Bearer {api_key}",
                },
                method="POST",
            )
            try:
                with request.urlopen(req, timeout=60) as resp:
                    body = json.loads(resp.read().decode("utf-8"))
            except error.HTTPError as exc:
                response_body = ""
                try:
                    response_body = exc.read().decode("utf-8")
                except Exception:
                    response_body = ""
                self._last_error = f"openai_http_error:{exc.code}:{response_body[:240]}"
                if attempt < 2:
                    time.sleep(1.0 * (attempt + 1))
                    continue
                return None, attempt + 1
            except Exception as exc:
                self._last_error = f"openai_request_error:{type(exc).__name__}:{exc}"
                if attempt < 2:
                    time.sleep(1.0 * (attempt + 1))
                    continue
                return None, attempt + 1
            choices = body.get("choices", [])
            if not choices:
                self._last_error = "openai_empty_choices"
                if attempt < 2:
                    time.sleep(1.0 * (attempt + 1))
                    continue
                return None, attempt + 1
            message = choices[0].get("message", {})
            content = message.get("content", "")
            if isinstance(content, list):
                text_parts = []
                for item in content:
                    if isinstance(item, dict) and item.get("type") == "text":
                        text_value = item.get("text", "")
                        if isinstance(text_value, str):
                            text_parts.append(text_value)
                content = "\n".join(text_parts)
            if not isinstance(content, str):
                self._last_error = f"openai_invalid_content_type:{type(content).__name__}"
                if attempt < 2:
                    time.sleep(1.0 * (attempt + 1))
                    continue
                return None, attempt + 1
            normalized = content.strip()
            if normalized:
                return normalized, attempt + 1
            self._last_error = "openai_empty_content"
            if attempt < 2:
                time.sleep(1.0 * (attempt + 1))
                continue
            return None, attempt + 1
        return None, 3

    def _call_ollama(self, prompt: str, model: str) -> tuple[str | None, int]:
        base_url = os.getenv("OLLAMA_BASE_URL", "http://localhost:11434")
        payload = {
            "model": model,
            "prompt": prompt,
            "stream": False,
        }
        data = json.dumps(payload).encode("utf-8")
        req = request.Request(
            url=f"{base_url.rstrip('/')}/api/generate",
            data=data,
            headers={"Content-Type": "application/json"},
            method="POST",
        )
        try:
            with request.urlopen(req, timeout=30) as resp:
                body = json.loads(resp.read().decode("utf-8"))
        except Exception as exc:
            self._last_error = f"ollama_request_error:{type(exc).__name__}:{exc}"
            return None, 1
        content = body.get("response", "")
        if not isinstance(content, str):
            self._last_error = f"ollama_invalid_content_type:{type(content).__name__}"
            return None, 1
        normalized = content.strip()
        if not normalized:
            self._last_error = "ollama_empty_content"
            return None, 1
        return normalized, 1

    def _build_openai_chat_url(self, base_url: str) -> str:
        normalized = base_url.rstrip("/")
        if normalized.endswith("/v1") or normalized.endswith("/api/v3"):
            return f"{normalized}/chat/completions"
        return f"{normalized}/v1/chat/completions"

    def _load_dotenv_once(self) -> None:
        if self._dotenv_loaded:
            return
        self._dotenv_loaded = True
        candidates = [
            os.getcwd(),
            os.path.abspath(os.path.join(os.path.dirname(__file__), "../../..")),
        ]
        for base in candidates:
            env_path = os.path.join(base, ".env")
            if not os.path.isfile(env_path):
                continue
            self._load_dotenv_file(env_path)
            break

    def _load_dotenv_file(self, env_path: str) -> None:
        try:
            with open(env_path, "r", encoding="utf-8") as fp:
                lines = fp.read().splitlines()
        except Exception:
            return
        for raw_line in lines:
            line = raw_line.strip()
            if not line or line.startswith("#") or "=" not in line:
                continue
            key, value = line.split("=", 1)
            key = key.strip()
            value = value.strip()
            if (value.startswith("'") and value.endswith("'")) or (value.startswith('"') and value.endswith('"')):
                value = value[1:-1]
            if key and key not in os.environ:
                os.environ[key] = value

    def _append_api_log(
        self,
        provider: str,
        model: str,
        prompt: str,
        response: str,
        metadata: dict[str, str | int | bool],
        attempt_count: int,
        success: bool,
        error_message: str,
    ) -> None:
        log_path = self._resolve_api_log_path()
        if not log_path:
            return
        record = {
            "timestamp": datetime.now(timezone.utc).isoformat(),
            "provider": provider,
            "model": model,
            "attempt_count": attempt_count,
            "success": success,
            "error": error_message,
            "prompt_length": len(prompt),
            "prompt_preview": prompt[:200],
            "response_length": len(response),
            "response": response,
        }
        if metadata:
            record["metadata"] = metadata
        try:
            log_path.parent.mkdir(parents=True, exist_ok=True)
            with log_path.open("a", encoding="utf-8") as fp:
                fp.write(json.dumps(record, ensure_ascii=False) + "\n")
        except Exception:
            return

    def _resolve_api_log_path(self) -> Path | None:
        custom_path = os.getenv("UNIGENER_API_LOG_PATH", "").strip()
        if custom_path:
            return Path(custom_path).expanduser()
        root = Path(__file__).resolve().parents[3]
        return root / "logs" / "agent_api_calls.jsonl"
