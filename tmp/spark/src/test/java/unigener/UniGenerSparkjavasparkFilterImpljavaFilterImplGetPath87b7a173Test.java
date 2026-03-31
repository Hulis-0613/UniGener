import spark.FilterImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkFilterImpljavaFilterImplGetPath87b7a173Test {

    private FilterImpl filter;

    @BeforeEach
    void setUp() {
        filter = new FilterImpl();
    }

    @AfterEach
    void tearDown() {
        // 清理测试中设置的系统属性，避免影响其他测试
        System.clearProperty("filter.config.path");
    }

    /**
     * 正常路径测试：配置有效路径时，返回预期结果
     */
    @Test
    void getPath_WithValidConfig_ReturnsExpectedPath() {
        // 准备：设置有效的配置路径
        String expectedPath = "/app/config/filters.xml";
        System.setProperty("filter.config.path", expectedPath);

        // 执行
        String actualPath = filter.getPath();

        // 断言：路径匹配预期值
        assertEquals(expectedPath, actualPath, "getPath() should return configured path");
    }

    /**
     * 异常路径测试：未配置路径时，抛出IllegalStateException
     */
    @Test
    void getPath_WithoutConfig_ThrowsIllegalStateException() {
        // 准备：确保配置路径未设置（tearDown已清理）

        // 执行 & 断言：验证抛出预期异常及消息
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            filter::getPath,
            "getPath() should throw exception when path is not configured"
        );
        assertTrue(exception.getMessage().contains("Path not configured"), "Exception message should indicate missing configuration");
    }

    /**
     * 异常路径测试：配置路径为空字符串时，抛出IllegalArgumentException
     */
    @Test
    void getPath_WithEmptyConfig_ThrowsIllegalArgumentException() {
        // 准备：设置空字符串路径
        System.setProperty("filter.config.path", "");

        // 执行 & 断言：验证抛出参数异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            filter::getPath,
            "getPath() should throw exception when path is empty"
        );
        assertTrue(exception.getMessage().contains("Path cannot be empty"), "Exception message should indicate empty path");
    }
}