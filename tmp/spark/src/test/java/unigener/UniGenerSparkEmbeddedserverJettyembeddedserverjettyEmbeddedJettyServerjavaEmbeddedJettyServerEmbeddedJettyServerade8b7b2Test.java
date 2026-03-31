import spark.embeddedserver.jetty.EmbeddedJettyServer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

// 假设依赖的类型（根据实际项目调整包路径）
import org.eclipse.jetty.server.Handler;
import com.example.JettyServerFactory; // 假设的serverFactory类型

public class UniGenerSparkEmbeddedserverJettyembeddedserverjettyEmbeddedJettyServerjavaEmbeddedJettyServerEmbeddedJettyServerade8b7b2Test {

    // Mock依赖对象
    private final JettyServerFactory mockServerFactory = mock(JettyServerFactory.class);
    private final Handler mockHandler = mock(Handler.class);

    /**
     * 正常路径：使用非null的serverFactory和handler构造实例
     */
    @Test
    void constructor_WithValidParameters_CreatesInstanceSuccessfully() {
        // 执行构造方法
        EmbeddedJettyServer server = new EmbeddedJettyServer(mockServerFactory, mockHandler);

        // 验证实例创建及属性赋值
        assertNotNull(server, "EmbeddedJettyServer实例应成功创建");
        assertSame(mockServerFactory, server.getServerFactory(), "serverFactory属性未正确赋值");
        assertSame(mockHandler, server.getHandler(), "handler属性未正确赋值");
    }

    /**
     * 异常路径：当serverFactory为null时，构造方法应抛出IllegalArgumentException
     */
    @Test
    void constructor_WithNullServerFactory_ThrowsIllegalArgumentException() {
        // 执行构造方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new EmbeddedJettyServer(null, mockHandler),
                "当serverFactory为null时，应抛出IllegalArgumentException");

        // 验证异常消息（根据实际校验逻辑调整消息内容）
        assertTrue(exception.getMessage().contains("serverFactory"), 
                "异常消息应包含参数名'serverFactory'");
    }

    /**
     * 异常路径：当handler为null时，构造方法应抛出IllegalArgumentException
     */
    @Test
    void constructor_WithNullHandler_ThrowsIllegalArgumentException() {
        // 执行构造方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new EmbeddedJettyServer(mockServerFactory, null),
                "当handler为null时，应抛出IllegalArgumentException");

        // 验证异常消息（根据实际校验逻辑调整消息内容）
        assertTrue(exception.getMessage().contains("handler"), 
                "异常消息应包含参数名'handler'");
    }
}