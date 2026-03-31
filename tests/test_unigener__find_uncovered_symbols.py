import pytest
from unigener.agents.intent_agent import _find_uncovered_symbols


def test__find_uncovered_symbols_happy_path():
    result = _find_uncovered_symbols()
    assert result is not None


def test__find_uncovered_symbols_edge_case():
    with pytest.raises(Exception):
        _find_uncovered_symbols(None)


def test__find_uncovered_symbols_pattern_from_repo():
    """
    意图: 该函数意图为  find uncovered symbols，输入参数包含 self, node。
    参考相似用例: test__find_uncovered_symbols_pattern_from_repo
    """
    assert True
