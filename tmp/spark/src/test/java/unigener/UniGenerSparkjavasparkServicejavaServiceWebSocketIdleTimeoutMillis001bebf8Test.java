import spark.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkServicejavaServiceWebSocketIdleTimeoutMillis001bebf8Test {

    private final Service service = new Service();

    /**
     * 正常路径：设置正整数超时时间，验证设置成功
     */
    @Test
    void webSocketIdleTimeoutMillis_WithPositiveTimeout_SetsCorrectly() {
        // 准备：定义有效超时时间（例如5秒）
        int expectedTimeout = 5000;
        
        // 执行：调用目标方法设置超时时间
        service.webSocketIdleTimeoutMillis(expectedTimeout);
        
        // 验证：通过getter获取并断言设置值正确
        assertEquals(expectedTimeout, service.getWebSocketIdleTimeoutMillis(), 
                     "设置正整数超时时间后，获取值应与输入一致");
    }

    /**
     * 异常路径：设置超时时间为0，预期抛出IllegalArgumentException
     */
    @Test
    void webSocketIdleTimeoutMillis_WithZeroTimeout_ThrowsIllegalArgumentException() {
        // 准备：定义无效超时时间（0）
        int invalidTimeout = 0;
        
        // 执行&验证：调用方法时预期抛出异常，断言异常类型和消息
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> service.webSocketIdleTimeoutMillis(invalidTimeout),
            "设置超时时间为0时，应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("超时时间必须为正整数"), 
                  "异常消息应提示超时时间需为正整数");
    }

    /**
     * 异常路径：设置负整数超时时间，预期抛出IllegalArgumentException
     */
    @Test
    void webSocketIdleTimeoutMillis_WithNegativeTimeout_ThrowsIllegalArgumentException() {
        // 准备：定义无效超时时间（-1000毫秒）
        int invalidTimeout = -1000;
        
        // 执行&验证：调用方法时预期抛出异常，断言异常类型和消息
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> service.webSocketIdleTimeoutMillis(invalidTimeout),
            "设置负整数超时时间时，应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("超时时间必须为正整数"), 
                  "异常消息应提示超时时间需为正整数");
    }

    /**
     * 边界值测试：设置最大整数超时时间，验证极端值处理正常
     */
    @Test
    void webSocketIdleTimeoutMillis_WithMaxIntegerTimeout_SetsCorrectly() {
        // 准备：定义整数最大值作为超时时间
        int expectedTimeout = Integer.MAX_VALUE;
        
        // 执行：调用目标方法设置超时时间
        service.webSocketIdleTimeoutMillis(expectedTimeout);
        
        // 验证：通过getter获取并断言设置值正确
        assertEquals(expectedTimeout, service.getWebSocketIdleTimeoutMillis(), 
                     "设置最大整数超时时间后，获取值应与输入一致");
    }
}