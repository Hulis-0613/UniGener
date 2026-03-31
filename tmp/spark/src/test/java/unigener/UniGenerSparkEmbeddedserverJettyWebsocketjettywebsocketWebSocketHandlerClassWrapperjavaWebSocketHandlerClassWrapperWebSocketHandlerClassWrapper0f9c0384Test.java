import spark.embeddedserver.jetty.websocket.WebSocketHandlerClassWrapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试WebSocketHandlerClassWrapper构造函数的功能与异常处理
 */
public class UniGenerSparkEmbeddedserverJettyWebsocketjettywebsocketWebSocketHandlerClassWrapperjavaWebSocketHandlerClassWrapperWebSocketHandlerClassWrapper0f9c0384Test {

    // 定义一个测试用的WebSocket处理器类（模拟真实业务场景中的handlerClass）
    static class TestWebSocketHandler {}

    /**
     * 正常路径：传入有效的handlerClass构造实例
     * 验证：实例创建成功且handlerClass被正确设置
     */
    @Test
    void testConstructor_WithValidHandlerClass() {
        // 执行构造函数
        WebSocketHandlerClassWrapper wrapper = new WebSocketHandlerClassWrapper(TestWebSocketHandler.class);
        
        // 验证实例非空
        assertNotNull(wrapper, "WebSocketHandlerClassWrapper实例应成功创建");
        
        // 假设Wrapper提供getter方法获取handlerClass（若实际无getter，可删除此断言，但需确保构造逻辑覆盖）
        assertEquals(TestWebSocketHandler.class, wrapper.getHandlerClass(), 
                     "构造的Wrapper应持有传入的handlerClass");
    }

    /**
     * 异常路径：传入null作为handlerClass
     * 验证：构造函数抛出IllegalArgumentException
     */
    @Test
    void testConstructor_WithNullHandlerClass() {
        // 执行构造函数并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> new WebSocketHandlerClassWrapper(null),
            "传入null handlerClass时应抛出IllegalArgumentException");
        
        // 验证异常消息（若构造函数定义了具体消息，可补充断言，如：
        // assertTrue(exception.getMessage().contains("handlerClass must not be null"));
    }
}