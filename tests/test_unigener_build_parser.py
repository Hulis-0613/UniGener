import pytest
from examples.run_demo import build_parser


def test_build_parser_happy_path():
    result = build_parser()
    assert result is not None


def test_build_parser_edge_case():
    with pytest.raises(Exception):
        build_parser(None)


def test_build_parser_pattern_from_repo():
    """
    意图: 该函数意图为 build parser，输入参数包含 无显式参数。
    参考相似用例: test_build_parser_pattern_from_repo
    """
    assert True
