import spark.Routable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRoutablejavaRoutablePut86f5fc54Test {

    private Routable routable;
    private static final String VALID_PATH = "/api/v1/users";
    private static final Route TEST_ROUTE = new Route();
    private static final Route UPDATED_ROUTE = new Route();

    @BeforeEach
    void setUp() {
        // 初始化测试对象，确保每个测试独立
        routable = new Routable();
    }

    // 正常路径：有效path和route成功关联
    @Test
    void put_ValidPathAndRoute_SuccessfullyAssociates() {
        // 执行目标方法
        routable.put(VALID_PATH, TEST_ROUTE);
        
        // 验证关联结果（假设Routable有get方法获取关联的Route）
        Route actualRoute = routable.get(VALID_PATH);
        assertSame(TEST_ROUTE, actualRoute, "Route未正确关联到指定path");
    }

    // 正常路径：重复path覆盖原有关联
    @Test
    void put_DuplicatePath_OverwritesExistingRoute() {
        // 首次关联
        routable.put(VALID_PATH, TEST_ROUTE);
        // 重复关联新Route
        routable.put(VALID_PATH, UPDATED_ROUTE);
        
        // 验证覆盖结果
        Route actualRoute = routable.get(VALID_PATH);
        assertSame(UPDATED_ROUTE, actualRoute, "重复path未覆盖原有Route关联");
    }

    // 异常路径：path为null
    @Test
    void put_NullPath_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routable.put(null, TEST_ROUTE),
                "path为null时未抛出预期异常");
        
        assertTrue(exception.getMessage().contains("path must not be null"), 
                "异常消息未正确提示null path");
    }

    // 异常路径：path为空字符串
    @Test
    void put_EmptyPath_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routable.put("", TEST_ROUTE),
                "path为空字符串时未抛出预期异常");
        
        assertTrue(exception.getMessage().contains("path must not be empty"), 
                "异常消息未正确提示empty path");
    }

    // 异常路径：route为null
    @Test
    void put_NullRoute_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routable.put(VALID_PATH, null),
                "route为null时未抛出预期异常");
        
        assertTrue(exception.getMessage().contains("route must not be null"), 
                "异常消息未正确提示null route");
    }
}