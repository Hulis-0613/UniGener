import spark.Routable;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRoutablejavaRoutableConnectcaaeb13cTest {

    private Routable routable;
    private static final String VALID_PATH = "/valid/path";
    private static final String VALID_ROUTE = "valid_route_123";
    private static final String INVALID_ROUTE = ""; // 无效路由（空字符串）

    @BeforeEach
    void setUp() {
        routable = new Routable(); // 假设Routable有默认构造器
    }

    // 正常路径：有效path和有效route，连接成功
    @Test
    void connect_WithValidPathAndValidRoute_ReturnsTrue() {
        boolean result = routable.connect(VALID_PATH, VALID_ROUTE);
        assertTrue(result, "连接应成功返回true");
    }

    // 异常路径：path为null，抛出IllegalArgumentException
    @Test
    void connect_WithNullPath_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routable.connect(null, VALID_ROUTE),
                "path为null时应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("path"), "异常信息应包含'path'");
    }

    // 异常路径：path为空字符串，抛出IllegalArgumentException
    @Test
    void connect_WithEmptyPath_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routable.connect("", VALID_ROUTE),
                "path为空字符串时应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("path"), "异常信息应包含'path'");
    }

    // 异常路径：route为null，抛出IllegalArgumentException
    @Test
    void connect_WithNullRoute_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routable.connect(VALID_PATH, null),
                "route为null时应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("route"), "异常信息应包含'route'");
    }

    // 异常路径：route为无效值（空字符串），抛出IllegalArgumentException
    @Test
    void connect_WithInvalidRoute_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routable.connect(VALID_PATH, INVALID_ROUTE),
                "无效route时应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("route"), "异常信息应包含'route'");
    }
}