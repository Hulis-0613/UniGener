import spark.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import java.util.Map;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkjavasparkServicejavaServiceWebSocket2d93739dTest {

    // 测试用WebSocket处理器类
    static class TestWebSocketHandler {}

    private final Service service = new Service();

    /**
     * 正常场景：注册有效的路径和处理器类，验证注册成功
     */
    @Test
    void webSocket_ValidPathAndHandlerClass_RegistersSuccessfully() {
        // 准备测试数据
        String validPath = "/ws/chat";
        Class<?> validHandlerClass = TestWebSocketHandler.class;

        // 执行目标方法
        service.webSocket(validPath, validHandlerClass);

        // 验证：通过Service内部注册映射确认路径与处理器类已关联
        Map<String, Class<?>> registeredHandlers = service.getWebSocketHandlers();
        assertThat(registeredHandlers)
            .isNotNull()
            .containsEntry(validPath, validHandlerClass);
    }

    /**
     * 异常场景：路径为null，验证抛出IllegalArgumentException
     */
    @Test
    void webSocket_NullPath_ThrowsIllegalArgumentException() {
        // 准备测试数据（路径为null，处理器类有效）
        String nullPath = null;
        Class<?> validHandlerClass = TestWebSocketHandler.class;

        // 执行并验证异常
        assertThatThrownBy(() -> service.webSocket(nullPath, validHandlerClass))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("WebSocket path must not be null");
    }

    /**
     * 异常场景：路径为空字符串，验证抛出IllegalArgumentException
     */
    @Test
    void webSocket_EmptyPath_ThrowsIllegalArgumentException() {
        // 准备测试数据（路径为空字符串，处理器类有效）
        String emptyPath = "";
        Class<?> validHandlerClass = TestWebSocketHandler.class;

        // 执行并验证异常
        assertThatThrownBy(() -> service.webSocket(emptyPath, validHandlerClass))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("WebSocket path must not be empty");
    }

    /**
     * 异常场景：处理器类为null，验证抛出IllegalArgumentException
     */
    @Test
    void webSocket_NullHandlerClass_ThrowsIllegalArgumentException() {
        // 准备测试数据（路径有效，处理器类为null）
        String validPath = "/ws/notifications";
        Class<?> nullHandlerClass = null;

        // 执行并验证异常
        assertThatThrownBy(() -> service.webSocket(validPath, nullHandlerClass))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("WebSocket handler class must not be null");
    }
}