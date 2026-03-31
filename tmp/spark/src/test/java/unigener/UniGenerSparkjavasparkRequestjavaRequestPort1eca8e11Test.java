import spark.Request;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标方法所在类为 ServerConfig
public class UniGenerSparkjavasparkRequestjavaRequestPort1eca8e11Test {

    private ServerConfig serverConfig;

    @BeforeEach
    void setUp() {
        // 初始化目标类实例
        serverConfig = new ServerConfig();
    }

    @AfterEach
    void tearDown() {
        // 清理系统属性，避免测试间干扰
        System.clearProperty("server.port");
    }

    // 正常路径：未配置端口时返回默认值
    @Test
    void port_WithNoConfig_ReturnsDefaultPort() {
        int actualPort = serverConfig.port();
        assertEquals(8080, actualPort, "未配置端口时应返回默认端口8080");
    }

    // 正常路径：配置有效端口时返回配置值
    @Test
    void port_WithValidConfig_ReturnsConfiguredPort() {
        System.setProperty("server.port", "9090"); // 模拟配置有效端口
        int actualPort = serverConfig.port();
        assertEquals(9090, actualPort, "配置有效端口时应返回9090");
    }

    // 异常路径：配置非数字端口时抛出格式异常
    @Test
    void port_WithNonNumericConfig_ThrowsNumberFormatException() {
        System.setProperty("server.port", "invalid-port"); // 模拟非数字配置
        assertThrows(NumberFormatException.class, 
            () -> serverConfig.port(), 
            "非数字端口配置应抛出NumberFormatException"
        );
    }

    // 异常路径：配置端口小于1时抛出参数异常
    @Test
    void port_WithPortLessThan1_ThrowsIllegalArgumentException() {
        System.setProperty("server.port", "0"); // 模拟无效端口（小于1）
        assertThrows(IllegalArgumentException.class, 
            () -> serverConfig.port(), 
            "端口小于1时应抛出IllegalArgumentException"
        );
    }

    // 异常路径：配置端口大于65535时抛出参数异常
    @Test
    void port_WithPortGreaterThan65535_ThrowsIllegalArgumentException() {
        System.setProperty("server.port", "65536"); // 模拟无效端口（大于65535）
        assertThrows(IllegalArgumentException.class, 
            () -> serverConfig.port(), 
            "端口大于65535时应抛出IllegalArgumentException"
        );
    }
}