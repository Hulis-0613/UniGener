from __future__ import annotations

from abc import ABC, abstractmethod


class Agent(ABC):
    name: str

    @abstractmethod
    def run(self, *args, **kwargs):
        raise NotImplementedError
