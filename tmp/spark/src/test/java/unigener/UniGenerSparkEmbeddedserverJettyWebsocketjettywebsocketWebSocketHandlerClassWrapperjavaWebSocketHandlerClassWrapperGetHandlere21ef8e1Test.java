import spark.embeddedserver.jetty.websocket.WebSocketHandlerClassWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkEmbeddedserverJettyWebsocketjettywebsocketWebSocketHandlerClassWrapperjavaWebSocketHandlerClassWrapperGetHandlere21ef8e1Test {

    @Mock
    private HandlerProvider handlerProvider; // 假设getHandler依赖的处理器提供服务

    @InjectMocks
    private HandlerManager handlerManager; // 目标测试类


    /**
     * 正常路径：处理器提供服务返回有效处理器时，getHandler应返回非null处理器实例
     */
    @Test
    void getHandler_WhenHandlerProviderReturnsValidHandler_ReturnsNonNulHandler() {
        // Arrange：模拟依赖返回有效处理器
        Handler expectedHandler = new Handler(); // 假设存在Handler类
        when(handlerProvider.provide()).thenReturn(expectedHandler);

        // Act：调用目标方法
        Handler result = handlerManager.getHandler();

        // Assert：验证返回结果非null且与预期实例一致
        assertNotNull(result, "处理器不应为null");
        assertSame(expectedHandler, result, "返回的处理器与预期实例不一致");
    }


    /**
     * 异常路径：处理器提供服务返回null时，getHandler应抛出IllegalStateException
     */
    @Test
    void getHandler_WhenHandlerProviderReturnsNull_ThrowsIllegalStateException() {
        // Arrange：模拟依赖返回null（获取处理器失败）
        when(handlerProvider.provide()).thenReturn(null);

        // Act & Assert：验证抛出预期异常及消息
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> handlerManager.getHandler(),
            "处理器获取失败时应抛出IllegalStateException"
        );
        assertEquals("Failed to obtain handler from provider", exception.getMessage(), "异常消息不符合预期");
    }
}