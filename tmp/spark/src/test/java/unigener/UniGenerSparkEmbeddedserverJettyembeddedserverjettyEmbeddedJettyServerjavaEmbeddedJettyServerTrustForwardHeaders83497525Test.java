import spark.embeddedserver.jetty.EmbeddedJettyServer;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 测试EmbeddedJettyServer类的trustForwardHeaders方法功能正确性。
 */
public class UniGenerSparkEmbeddedserverJettyembeddedserverjettyEmbeddedJettyServerjavaEmbeddedJettyServerTrustForwardHeaders83497525Test {

    /**
     * 测试当输入参数为true时，信任转发头状态应被正确设置为true。
     */
    @Test
    void trustForwardHeaders_WithTrueParameter_SetsTrustToTrue() {
        // 准备：创建测试实例
        EmbeddedJettyServer server = new EmbeddedJettyServer();
        boolean expectedTrust = true;

        // 执行：调用目标方法设置信任状态
        server.trustForwardHeaders(expectedTrust);

        // 验证：检查信任状态是否正确设置
        assertEquals(expectedTrust, server.getTrustForwardHeaders(), 
            "当输入为true时，信任转发头状态应被设置为true");
    }

    /**
     * 测试当输入参数为false时，信任转发头状态应被正确设置为false。
     */
    @Test
    void trustForwardHeaders_WithFalseParameter_SetsTrustToFalse() {
        // 准备：创建测试实例
        EmbeddedJettyServer server = new EmbeddedJettyServer();
        boolean expectedTrust = false;

        // 执行：调用目标方法设置信任状态
        server.trustForwardHeaders(expectedTrust);

        // 验证：检查信任状态是否正确设置
        assertEquals(expectedTrust, server.getTrustForwardHeaders(), 
            "当输入为false时，信任转发头状态应被设置为false");
    }
}