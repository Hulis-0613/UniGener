import spark.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设存在Match类（包含必要属性和getter/setter）
public class UniGenerSparkjavasparkRequestjavaRequestChangeMatch43a3f85bTest {
    private Long id;
    private String name;

    public Match() {}

    public Match(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getter和Setter
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
}

// 待测试的Request类（假设定义）
class Request {
    private Match match;

    public boolean changeMatch(Match newMatch) {
        // 业务逻辑假设：
        // 1. 若newMatch为null，抛出IllegalArgumentException
        // 2. 若newMatch的id或name为null，抛出IllegalStateException
        // 3. 否则更新内部match并返回true
        if (newMatch == null) {
            throw new IllegalArgumentException("Match cannot be null");
        }
        if (newMatch.getId() == null || newMatch.getName() == null) {
            throw new IllegalStateException("Match must have non-null id and name");
        }
        this.match = newMatch;
        return true;
    }

    public Match getMatch() { return match; }
}

// JUnit5测试类
class RequestChangeMatchTest {
    private Request request;
    private Match validMatch;

    @BeforeEach
    void setUp() {
        request = new Request();
        validMatch = new Match(1L, "Valid Match"); // 构造一个合法的Match对象
    }

    // 正常路径：传入合法Match，验证修改成功
    @Test
    void changeMatch_WithValidMatch_ReturnsTrueAndUpdatesMatch() {
        // 执行测试方法
        boolean result = request.changeMatch(validMatch);

        // 断言返回值和内部状态
        assertTrue(result, "修改匹配应返回true");
        assertNotNull(request.getMatch(), "修改后match不应为null");
        assertEquals(validMatch.getId(), request.getMatch().getId(), "id应与输入匹配");
        assertEquals(validMatch.getName(), request.getMatch().getName(), "name应与输入匹配");
    }

    // 异常路径1：传入null的Match，预期抛出IllegalArgumentException
    @Test
    void changeMatch_WithNullMatch_ThrowsIllegalArgumentException() {
        // 验证异常类型和消息
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> request.changeMatch(null),
                "传入null时应抛出IllegalArgumentException");
        assertEquals("Match cannot be null", exception.getMessage(), "异常消息不匹配");
    }

    // 异常路径2：传入id为null的Match，预期抛出IllegalStateException
    @Test
    void changeMatch_WithMatchNullId_ThrowsIllegalStateException() {
        Match invalidMatch = new Match(null, "Invalid Match"); // id为null

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> request.changeMatch(invalidMatch),
                "id为null时应抛出IllegalStateException");
        assertEquals("Match must have non-null id and name", exception.getMessage(), "异常消息不匹配");
    }

    // 异常路径3：传入name为null的Match，预期抛出IllegalStateException
    @Test
    void changeMatch_WithMatchNullName_ThrowsIllegalStateException() {
        Match invalidMatch = new Match(2L, null); // name为null

        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> request.changeMatch(invalidMatch),
                "name为null时应抛出IllegalStateException");
        assertEquals("Match must have non-null id and name", exception.getMessage(), "异常消息不匹配");
    }

    // 边界情况：传入与当前match相同的对象，验证仍能成功修改
    @Test
    void changeMatch_WithSameMatch_ReturnsTrueAndMaintainsState() {
        // 先设置初始match
        request.changeMatch(validMatch);
        Match currentMatch = request.getMatch();

        // 传入相同的match对象
        boolean result = request.changeMatch(currentMatch);

        assertTrue(result, "修改相同match应返回true");
        assertSame(currentMatch, request.getMatch(), "应保持原对象引用");
    }
}