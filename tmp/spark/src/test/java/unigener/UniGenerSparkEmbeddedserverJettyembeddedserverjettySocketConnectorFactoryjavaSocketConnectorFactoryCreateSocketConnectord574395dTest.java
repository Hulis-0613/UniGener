import spark.embeddedserver.jetty.SocketConnectorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkEmbeddedserverJettyembeddedserverjettySocketConnectorFactoryjavaSocketConnectorFactoryCreateSocketConnectord574395dTest {

    @Mock
    private Server mockServer; // 假设服务器类型为Server，使用Mock避免真实依赖
    private SocketConnectorFactory factory;

    @BeforeEach
    void setUp() {
        factory = new SocketConnectorFactory(); // 初始化工厂实例
    }

    // 正常路径：有效参数 + 信任转发头=true
    @Test
    void createSocketConnector_ValidParams_TrustForwardHeadersTrue() {
        // 准备参数
        String host = "api.example.com";
        int port = 8080;
        boolean trustForwardHeaders = true;

        // 执行测试
        SocketConnector connector = factory.createSocketConnector(mockServer, host, port, trustForwardHeaders);

        // 断言结果
        assertNotNull(connector, "SocketConnector should not be null");
        assertEquals(host, connector.getHost(), "Host mismatch");
        assertEquals(port, connector.getPort(), "Port mismatch");
        assertTrue(connector.isTrustForwardHeaders(), "TrustForwardHeaders should be true");
        assertSame(mockServer, connector.getServer(), "Server reference mismatch");
    }

    // 正常路径：有效参数 + 信任转发头=false
    @Test
    void createSocketConnector_ValidParams_TrustForwardHeadersFalse() {
        // 准备参数
        String host = "localhost";
        int port = 443;
        boolean trustForwardHeaders = false;

        // 执行测试
        SocketConnector connector = factory.createSocketConnector(mockServer, host, port, trustForwardHeaders);

        // 断言结果
        assertNotNull(connector);
        assertEquals(host, connector.getHost());
        assertEquals(port, connector.getPort());
        assertFalse(connector.isTrustForwardHeaders(), "TrustForwardHeaders should be false");
        assertSame(mockServer, connector.getServer());
    }

    // 异常路径：服务器为null
    @Test
    void createSocketConnector_ServerNull_ThrowsIllegalArgumentException() {
        // 准备参数（服务器为null）
        String host = "valid.host";
        int port = 80;
        boolean trustForwardHeaders = true;

        // 执行测试并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createSocketConnector(null, host, port, trustForwardHeaders),
                "Expected IllegalArgumentException when server is null");

        assertTrue(exception.getMessage().contains("server must not be null"), "Exception message mismatch");
    }

    // 异常路径：主机为null
    @Test
    void createSocketConnector_HostNull_ThrowsIllegalArgumentException() {
        // 准备参数（主机为null）
        String host = null;
        int port = 8080;
        boolean trustForwardHeaders = true;

        // 执行测试并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createSocketConnector(mockServer, host, port, trustForwardHeaders),
                "Expected IllegalArgumentException when host is null");

        assertTrue(exception.getMessage().contains("host must not be null or empty"), "Exception message mismatch");
    }

    // 异常路径：主机为空字符串
    @Test
    void createSocketConnector_HostEmpty_ThrowsIllegalArgumentException() {
        // 准备参数（主机为空字符串）
        String host = "";
        int port = 8080;
        boolean trustForwardHeaders = true;

        // 执行测试并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createSocketConnector(mockServer, host, port, trustForwardHeaders),
                "Expected IllegalArgumentException when host is empty");

        assertTrue(exception.getMessage().contains("host must not be null or empty"), "Exception message mismatch");
    }

    // 异常路径：端口为负数
    @Test
    void createSocketConnector_PortNegative_ThrowsIllegalArgumentException() {
        // 准备参数（端口为负数）
        String host = "valid.host";
        int port = -1;
        boolean trustForwardHeaders = true;

        // 执行测试并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createSocketConnector(mockServer, host, port, trustForwardHeaders),
                "Expected IllegalArgumentException when port is negative");

        assertTrue(exception.getMessage().contains("port must be between 1 and 65535"), "Exception message mismatch");
    }

    // 异常路径：端口为0
    @Test
    void createSocketConnector_PortZero_ThrowsIllegalArgumentException() {
        // 准备参数（端口为0）
        String host = "valid.host";
        int port = 0;
        boolean trustForwardHeaders = true;

        // 执行测试并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createSocketConnector(mockServer, host, port, trustForwardHeaders),
                "Expected IllegalArgumentException when port is 0");

        assertTrue(exception.getMessage().contains("port must be between 1 and 65535"), "Exception message mismatch");
    }

    // 异常路径：端口超过最大允许值（65535）
    @Test
    void createSocketConnector_PortExceedsMax_ThrowsIllegalArgumentException() {
        // 准备参数（端口=65536）
        String host = "valid.host";
        int port = 65536;
        boolean trustForwardHeaders = true;

        // 执行测试并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.createSocketConnector(mockServer, host, port, trustForwardHeaders),
                "Expected IllegalArgumentException when port exceeds 65535");

        assertTrue(exception.getMessage().contains("port must be between 1 and 65535"), "Exception message mismatch");
    }
}