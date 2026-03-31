import spark.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 测试用WebSocket处理器（模拟业务处理器）
public class UniGenerSparkjavasparkServicejavaServiceAddWebSocketHandler6b831a4bTest extends WebSocketHandler {}

// 非WebSocket处理器（用于异常测试）
class NonWebSocketHandler {}

class WebSocketConfigTest {

    private final WebSocketConfig config = new WebSocketConfig();

    /**
     * 正常路径：添加有效的WebSocket处理器
     */
    @Test
    void shouldAddWebSocketHandlerSuccessfully() {
        // 执行目标方法
        config.addWebSocketHandler("/chat", TestWebSocketHandler.class);
        
        // 验证处理器已正确注册
        assertEquals(TestWebSocketHandler.class, config.getHandlers().get("/chat"));
    }

    /**
     * 异常路径：path为null时抛出IllegalArgumentException
     */
    @Test
    void shouldThrowExceptionWhenPathIsNull() {
        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> config.addWebSocketHandler(null, TestWebSocketHandler.class));
        
        // 验证异常消息
        assertTrue(exception.getMessage().contains("path must not be null"));
    }

    /**
     * 异常路径：path为空字符串时抛出IllegalArgumentException
     */
    @Test
    void shouldThrowExceptionWhenPathIsEmpty() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> config.addWebSocketHandler("", TestWebSocketHandler.class));
        
        assertTrue(exception.getMessage().contains("path must not be empty"));
    }

    /**
     * 异常路径：handlerClass为null时抛出IllegalArgumentException
     */
    @Test
    void shouldThrowExceptionWhenHandlerClassIsNull() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> config.addWebSocketHandler("/chat", null));
        
        assertTrue(exception.getMessage().contains("handlerClass must not be null"));
    }

    /**
     * 异常路径：handlerClass非WebSocketHandler子类时抛出IllegalArgumentException
     */
    @Test
    void shouldThrowExceptionWhenHandlerClassIsNotWebSocketHandler() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> config.addWebSocketHandler("/chat", NonWebSocketHandler.class));
        
        assertTrue(exception.getMessage().contains("handlerClass must be a subclass of WebSocketHandler"));
    }
}