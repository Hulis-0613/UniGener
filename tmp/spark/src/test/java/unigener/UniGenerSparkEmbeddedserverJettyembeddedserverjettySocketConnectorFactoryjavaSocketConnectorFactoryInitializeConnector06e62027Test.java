import spark.embeddedserver.jetty.SocketConnectorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkEmbeddedserverJettyembeddedserverjettySocketConnectorFactoryjavaSocketConnectorFactoryInitializeConnector06e62027Test {

    @Mock
    private Connector mockConnector; // 模拟连接器对象

    // 正常场景：有效连接器、合法host和port
    @Test
    void initializeConnector_NormalCase_SuccessfullyInitialized() {
        // 准备：模拟连接器未初始化
        when(mockConnector.isInitialized()).thenReturn(false);

        // 执行：调用初始化方法
        ConnectorManager.initializeConnector(mockConnector, "valid.host.com", 8080);

        // 验证：连接器状态及参数设置
        assertTrue(mockConnector.isInitialized()); // 假设isInitialized()返回true表示初始化完成
        verify(mockConnector).setHost("valid.host.com"); // 验证host被设置
        verify(mockConnector).setPort(8080); // 验证port被设置
        verify(mockConnector).connect(); // 验证执行了连接操作
    }

    // 异常场景1：连接器为null
    @Test
    void initializeConnector_ConnectorNull_ThrowsIllegalArgumentException() {
        // 执行并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ConnectorManager.initializeConnector(null, "host", 8080));
        assertEquals("Connector cannot be null", exception.getMessage()); // 假设异常消息
    }

    // 异常场景2：host为null
    @Test
    void initializeConnector_HostNull_ThrowsIllegalArgumentException() {
        // 执行并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ConnectorManager.initializeConnector(mockConnector, null, 8080));
        assertEquals("Host cannot be null or empty", exception.getMessage());
    }

    // 异常场景3：host为空字符串
    @Test
    void initializeConnector_HostEmpty_ThrowsIllegalArgumentException() {
        // 执行并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ConnectorManager.initializeConnector(mockConnector, "", 8080));
        assertEquals("Host cannot be null or empty", exception.getMessage());
    }

    // 异常场景4：port为负数
    @Test
    void initializeConnector_PortNegative_ThrowsIllegalArgumentException() {
        // 执行并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ConnectorManager.initializeConnector(mockConnector, "host", -1));
        assertEquals("Port must be between 1 and 65535", exception.getMessage());
    }

    // 异常场景5：port为0（非有效端口）
    @Test
    void initializeConnector_PortZero_ThrowsIllegalArgumentException() {
        // 执行并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ConnectorManager.initializeConnector(mockConnector, "host", 0));
        assertEquals("Port must be between 1 and 65535", exception.getMessage());
    }

    // 异常场景6：port超过最大端口（65535）
    @Test
    void initializeConnector_PortExceedsMax_ThrowsIllegalArgumentException() {
        // 执行并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ConnectorManager.initializeConnector(mockConnector, "host", 65536));
        assertEquals("Port must be between 1 and 65535", exception.getMessage());
    }

    // 异常场景7：连接器已初始化
    @Test
    void initializeConnector_ConnectorAlreadyInitialized_ThrowsIllegalStateException() {
        // 准备：模拟连接器已初始化
        when(mockConnector.isInitialized()).thenReturn(true);

        // 执行并验证异常
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> ConnectorManager.initializeConnector(mockConnector, "host", 8080));
        assertEquals("Connector is already initialized", exception.getMessage());
    }
}