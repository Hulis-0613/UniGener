import spark.embeddedserver.jetty.websocket.to;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

/**
 * 测试to类的getHandler方法，覆盖正常路径与异常路径。
 */
public class UniGenerSparkEmbeddedserverJettyWebsocketjettywebsocketWebSocketCreatorFactoryjavatoGetHandler95227cbdTest {

    /**
     * 测试正常路径：处理器已初始化时，getHandler返回非null实例。
     */
    @Test
    void testGetHandler_NormalCase() throws Exception {
        // 准备：通过反射设置to类的静态处理器字段为非null值
        Field handlerField = to.class.getDeclaredField("handler"); // 假设内部静态字段名为"handler"
        handlerField.setAccessible(true);
        Object mockHandler = new Object(); // 模拟处理器实例（实际类型需替换为真实处理器类型）
        handlerField.set(null, mockHandler); // 静态字段，第一个参数为null

        // 执行：调用getHandler方法
        Object result = to.getHandler();

        // 断言：返回结果非null且与预设实例一致
        assertNotNull(result, "处理器实例不应为null");
        assertSame(mockHandler, result, "返回的处理器实例与预设实例不一致");
    }

    /**
     * 测试异常路径：处理器未初始化时，getHandler抛出IllegalStateException。
     */
    @Test
    void testGetHandler_ExceptionCase() throws Exception {
        // 准备：通过反射将to类的静态处理器字段设为null（模拟未初始化状态）
        Field handlerField = to.class.getDeclaredField("handler");
        handlerField.setAccessible(true);
        handlerField.set(null, null);

        // 执行并断言：调用getHandler时抛出预期异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> to.getHandler(),
            "处理器未初始化时，getHandler应抛出IllegalStateException"
        );
        assertTrue(exception.getMessage().contains("处理器未初始化"), "异常信息应包含'处理器未初始化'");
    }
}