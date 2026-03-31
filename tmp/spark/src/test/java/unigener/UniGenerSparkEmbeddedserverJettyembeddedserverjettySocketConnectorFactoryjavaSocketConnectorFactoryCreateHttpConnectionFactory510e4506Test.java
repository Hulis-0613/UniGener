import spark.embeddedserver.jetty.SocketConnectorFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkEmbeddedserverJettyembeddedserverjettySocketConnectorFactoryjavaSocketConnectorFactoryCreateHttpConnectionFactory510e4506Test {

    /**
     * 正常路径：trustForwardHeaders为true时，工厂应启用信任转发头配置
     */
    @Test
    void createHttpConnectionFactory_WithTrustForwardHeadersTrue_ShouldEnableTrust() {
        // 执行目标方法
        HttpConnectionFactory factory = HttpConnectionUtils.createHttpConnectionFactory(true);
        
        // 断言工厂创建成功且配置正确
        assertNotNull(factory, "HttpConnectionFactory should not be null");
        assertTrue(factory.isTrustForwardHeaders(), "Trust forward headers should be enabled");
    }

    /**
     * 正常路径：trustForwardHeaders为false时，工厂应禁用信任转发头配置
     */
    @Test
    void createHttpConnectionFactory_WithTrustForwardHeadersFalse_ShouldDisableTrust() {
        // 执行目标方法
        HttpConnectionFactory factory = HttpConnectionUtils.createHttpConnectionFactory(false);
        
        // 断言工厂创建成功且配置正确
        assertNotNull(factory, "HttpConnectionFactory should not be null");
        assertFalse(factory.isTrustForwardHeaders(), "Trust forward headers should be disabled");
    }

    /**
     * 异常路径：trustForwardHeaders为null时，应抛出IllegalArgumentException
     */
    @Test
    void createHttpConnectionFactory_WithNullTrustForwardHeaders_ShouldThrowIllegalArgumentException() {
        // 验证异常类型和消息（如有明确消息可补充）
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> HttpConnectionUtils.createHttpConnectionFactory(null),
            "Null trustForwardHeaders should throw IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("trustForwardHeaders must not be null"), 
            "Exception message should indicate null parameter");
    }
}