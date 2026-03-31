import spark.should;
import org.junit.jupiter.api.Test;
import org.springframework.web.socket.WebSocketHandler;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * 测试WebSocket路径与处理器注册功能
 */
public class UniGenerSparkjavasparkSparkjavashouldWebSocketbb137741Test {

    // 假设目标方法所在的配置类（根据实际项目调整类名）
    private final WebSocketConfig webSocketConfig = new WebSocketConfig();
    // 模拟WebSocket处理器实例
    private final WebSocketHandler mockHandler = mock(WebSocketHandler.class);

    /**
     * 正常场景：有效路径和处理器应成功注册
     */
    @Test
    void webSocket_ValidPathAndHandler_RegistersSuccessfully() {
        // 准备测试数据
        String validPath = "/ws/messages";

        // 执行目标方法
        webSocketConfig.webSocket(validPath, mockHandler);

        // 验证注册结果（假设配置类提供获取注册信息的方法）
        WebSocketHandler registeredHandler = webSocketConfig.getRegisteredHandler(validPath);
        assertSame(mockHandler, registeredHandler, "处理器未正确注册到指定路径");
    }

    /**
     * 异常场景：路径为null时应抛出IllegalArgumentException
     */
    @Test
    void webSocket_NullPath_ThrowsIllegalArgumentException() {
        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> webSocketConfig.webSocket(null, mockHandler),
                "路径为null时未抛出预期异常");

        // 验证异常信息
        assertTrue(exception.getMessage().contains("路径不能为空或null"), "异常信息不符合预期");
    }

    /**
     * 异常场景：路径为空字符串时应抛出IllegalArgumentException
     */
    @Test
    void webSocket_EmptyPath_ThrowsIllegalArgumentException() {
        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> webSocketConfig.webSocket("", mockHandler),
                "路径为空字符串时未抛出预期异常");

        // 验证异常信息
        assertTrue(exception.getMessage().contains("路径不能为空或null"), "异常信息不符合预期");
    }

    /**
     * 异常场景：处理器为null时应抛出IllegalArgumentException
     */
    @Test
    void webSocket_NullHandler_ThrowsIllegalArgumentException() {
        // 准备测试数据
        String validPath = "/ws/alerts";

        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> webSocketConfig.webSocket(validPath, null),
                "处理器为null时未抛出预期异常");

        // 验证异常信息
        assertTrue(exception.getMessage().contains("处理器不能为空"), "异常信息不符合预期");
    }
}