import spark.should;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标类为ServerConfig，需根据实际类名调整
public class UniGenerSparkjavasparkSparkjavashouldSetPort8f700463Test {

    private final ServerConfig serverConfig = new ServerConfig();

    /**
     * 测试正常路径：设置有效范围内的端口（常规有效值）
     */
    @Test
    void setPort_ValidPort_SuccessfullySetsPort() {
        int validPort = 8080;
        serverConfig.setPort(validPort);
        assertEquals(validPort, serverConfig.getPort(), "端口应设置为" + validPort);
    }

    /**
     * 测试边界值：设置最小有效端口（0）
     */
    @Test
    void setPort_MinValidPort_SuccessfullySetsPort() {
        int minPort = 0;
        serverConfig.setPort(minPort);
        assertEquals(minPort, serverConfig.getPort(), "端口应设置为最小有效值" + minPort);
    }

    /**
     * 测试边界值：设置最大有效端口（65535）
     */
    @Test
    void setPort_MaxValidPort_SuccessfullySetsPort() {
        int maxPort = 65535;
        serverConfig.setPort(maxPort);
        assertEquals(maxPort, serverConfig.getPort(), "端口应设置为最大有效值" + maxPort);
    }

    /**
     * 测试异常路径：设置负数端口（小于0）
     */
    @Test
    void setPort_NegativePort_ThrowsIllegalArgumentException() {
        int negativePort = -1;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serverConfig.setPort(negativePort),
                "设置负数端口应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("无效端口"), "异常信息应提示无效端口");
    }

    /**
     * 测试异常路径：设置超出最大范围的端口（大于65535）
     */
    @Test
    void setPort_ExceedMaxPort_ThrowsIllegalArgumentException() {
        int exceedPort = 65536;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serverConfig.setPort(exceedPort),
                "设置超出最大范围端口应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("无效端口"), "异常信息应提示无效端口");
    }
}