import spark.should;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSparkjavashouldWebSocketIdleTimeoutMillis8ea2f038Test {

    private final WebSocketConfig webSocketConfig = new WebSocketConfig();

    /**
     * 测试正常路径：设置正整数超时时间，验证设置成功
     */
    @Test
    void webSocketIdleTimeoutMillis_WithPositiveTimeout_SetsSuccessfully() {
        // 准备：定义有效超时时间（5秒）
        int expectedTimeout = 5000;
        
        // 执行：调用目标方法设置超时时间
        webSocketConfig.webSocketIdleTimeoutMillis(expectedTimeout);
        
        // 断言：验证设置的超时时间与预期一致
        assertEquals(expectedTimeout, webSocketConfig.getWebSocketIdleTimeoutMillis(),
                "设置正整数超时时间后，获取的值应与输入一致");
    }

    /**
     * 测试边界值：设置最小有效超时时间（1毫秒），验证设置成功
     */
    @Test
    void webSocketIdleTimeoutMillis_WithMinimumValidTimeout_SetsSuccessfully() {
        // 准备：定义最小有效超时时间（1毫秒）
        int expectedTimeout = 1;
        
        // 执行：调用目标方法设置超时时间
        webSocketConfig.webSocketIdleTimeoutMillis(expectedTimeout);
        
        // 断言：验证设置的超时时间与预期一致
        assertEquals(expectedTimeout, webSocketConfig.getWebSocketIdleTimeoutMillis(),
                "设置最小有效超时时间（1毫秒）后，获取的值应与输入一致");
    }

    /**
     * 测试异常路径：设置超时时间为0，预期抛出IllegalArgumentException
     */
    @Test
    void webSocketIdleTimeoutMillis_WithZeroTimeout_ThrowsIllegalArgumentException() {
        // 准备：定义无效超时时间（0毫秒）
        int invalidTimeout = 0;
        
        // 执行并断言：调用目标方法时抛出IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> webSocketConfig.webSocketIdleTimeoutMillis(invalidTimeout),
                "设置超时时间为0时，应抛出IllegalArgumentException");
        
        // 可选：验证异常消息（若方法定义了具体消息）
        assertTrue(exception.getMessage().contains("超时时间必须大于0"),
                "异常消息应提示超时时间必须大于0");
    }

    /**
     * 测试异常路径：设置负数值超时时间，预期抛出IllegalArgumentException
     */
    @Test
    void webSocketIdleTimeoutMillis_WithNegativeTimeout_ThrowsIllegalArgumentException() {
        // 准备：定义无效超时时间（-1000毫秒）
        int invalidTimeout = -1000;
        
        // 执行并断言：调用目标方法时抛出IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> webSocketConfig.webSocketIdleTimeoutMillis(invalidTimeout),
                "设置负数值超时时间时，应抛出IllegalArgumentException");
        
        // 可选：验证异常消息（若方法定义了具体消息）
        assertTrue(exception.getMessage().contains("超时时间必须大于0"),
                "异常消息应提示超时时间必须大于0");
    }
}