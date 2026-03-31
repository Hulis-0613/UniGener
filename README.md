# UniGener

UniGener 是一个面向仓库内单元测试自动生成的多 Agent 系统，核心由四个角色组成：

- 意图识别 Agent
- 代码检索 Agent
- 相似测试用例检索 Agent
- 单元测试用例生成 Agent

## 目标

给定 focal method，自动完成：

1. 理解函数意图与关键行为
2. 补全缺失上下文信息（依赖、对象定义、变量来源）
3. 检索仓库内相似测试模式
4. 生成可落地的单元测试函数

## 交互流程

1. **意图识别 Agent** 读取 focal method 源码，先执行“上下文需求判断”，可返回检索 query 列表。
2. 若存在缺失信息或意图判断需补充，调用 **代码检索 Agent** 扫描 repo，返回依赖解析、类/对象/变量定义。
3. **相似测试用例检索 Agent** 根据 focal method 与意图，检索已有测试并返回函数-测试配对。
4. **单元测试用例生成 Agent** 使用意图 + 相似用例，按需组装 few-shot 提示并产出新测试代码。
5. 生成阶段会根据 `focal_file` 自动推断 `from module import symbol` 导入语句。
6. 编排器可将生成结果自动落盘到目标仓库 `tests/` 目录并自动执行测试。
7. Python 每轮执行优先使用 `coverage.py` 采集覆盖率；若不可用则回退到 `trace` 统计。
8. Java 每轮执行通过 Maven + JaCoCo 采集编译/运行状态、行覆盖率与分支覆盖率。
9. 系统会输出每轮失败摘要的结构化 JSON，便于后续分析面板消费。
10. 当覆盖率未达100%或测试失败时，系统会把错误反馈注入生成 Agent，最多迭代 `N_ITERATION=10` 轮。

## 执行前提

- Python 自动执行测试依赖 `pytest`。若环境缺失，系统会记录 `ModuleNotFoundError` 并停止后续迭代。
- Python 若安装了 `coverage` 包，会输出更精确覆盖率；否则自动回退为 `trace`。
- Java 自动执行依赖 Maven 测试环境与 JaCoCo 插件（运行时通过 `org.jacoco:jacoco-maven-plugin` 调用）。
- 可通过 `--no-auto-run` 关闭自动执行，仅保留生成与落盘。

## 检索模式

- `ast`: 仅使用仓库 AST 索引检索。
- `lsp`: 仅使用 LSP 适配层检索（当前默认接入 Jedi 后端）。
- `hybrid`: 同时使用 AST + LSP，并自动去重。

## 生成模式

- `none`: 不调用外部模型，使用内置模板生成并附带 few-shot prompt。
- `openai`: 调用 OpenAI 兼容接口（支持从项目根目录 `.env` 自动加载 `OPENAI_API_KEY`、`OPENAI_BASE_URL`、`OPENAI_MODEL`；也支持系统环境变量）。
- `ollama`: 调用本地 Ollama 服务（可选 `OLLAMA_BASE_URL`、`OLLAMA_MODEL`）。

## 语言适配

- 支持 `python` 与 `java` 两类项目的测试生成。
- `python` 默认框架为 `pytest`，`java` 默认框架会根据 `pom.xml` 自动识别（`junit4` / `junit5`）。
- `--language auto` 时会根据 `focal_file` 后缀自动推断语言。
- Python 与 Java 均支持自动执行与迭代修复。

## 目录结构

```text
src/unigener/
  agents/
  retrievers/
  pipeline/
  models/
examples/
```

## 快速开始

```bash
python -m examples.run_demo \
  --repo /absolute/path/to/target_repo \
  --focal-file /absolute/path/to/target_repo/pkg/foo.py \
  --focal-symbol some_function \
  --language python \
  --retrieval-mode hybrid \
  --llm-provider none \
  --output-tests-dir tests

python -m examples.run_demo \
  --repo /absolute/path/to/target_repo \
  --focal-file /absolute/path/to/target_repo/pkg/foo.py \
  --focal-symbol some_function \
  --no-auto-run

python -m examples.run_demo \
  --repo /absolute/path/to/java_repo \
  --focal-file /absolute/path/to/java_repo/src/main/java/com/acme/FooService.java \
  --focal-symbol processOrder \
  --language java \
  --test-framework auto \
  --llm-provider openai \
  --output-tests-dir src/test/java/com/acme

python -m examples.generate_java_repo_tests \
  --repo /absolute/path/to/java_repo \
  --output-tests-dir src/test/java/unigener \
  --llm-provider openai \
  --retrieval-mode ast \
  --auto-run-tests \
  --results-output-json /absolute/path/to/results.json
```

## 日志与结果文件

- `logs/agent_api_calls.jsonl`：模型 API 调用日志（含 prompt/response、重试次数、错误信息）。
- `logs/intent_planning.jsonl`：意图规划日志（原始 JSON、解析结果、清洗后 queries、是否需要上下文）。
- `logs/code_retrieval.jsonl`：代码检索日志（query symbols、命中数量、结果预览）。
- `repo/logs/sandbox_iterations/*.json`：Java 迭代沙箱每轮结果（编译/运行/覆盖率/错误栈）。
- `--results-output-json`：批量生成脚本的聚合统计结果（总数、编译错误占比、运行错误占比、可运行占比、平均覆盖率）。

## 备注


- 以java项目“Spark”为例，生成了全量测试用例，覆盖了 `src/main/java/` 目录下的所有类。  
- 当前只使用了三个agent：`intent_planning_agent.py`、`code_retrieval_agent.py`、`test_generation_agent.py`，相似测试用例检索agent没有使用（没有emb model 的api可调）。  
- logs 目录下包含所有日志文件，包括模型 API 调用、意图规划、代码检索、测试生成等，供后续分析与调试。



输出会包含：

- 识别出的意图
- 代码检索补全上下文
- 相似测试候选
- few-shot 生成提示词
- 生成的测试函数草案
- 自动写入的测试文件路径
- 每轮编译/运行/覆盖率结果与错误信息
- 每轮失败摘要 JSON（`failure_reports_json`）
