import spark.should;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSparkjavashouldPort5c8bf876Test {

    private final ServerConfig serverConfig = new ServerConfig();

    /**
     * 正常路径：测试有效端口范围（1-65535）的设置
     * 覆盖边界值（1、65535）和典型值（80、443、8080）
     */
    @ParameterizedTest
    @ValueSource(ints = {1, 80, 443, 8080, 65535})
    void setPort_WithValidPort_SetsSuccessfully(int port) {
        // 执行设置端口
        serverConfig.setPort(port);
        // 断言端口设置正确
        assertEquals(port, serverConfig.getPort(), "端口设置失败");
    }

    /**
     * 异常路径：测试无效端口（负数、0、超出65535）的处理
     * 验证方法抛出IllegalArgumentException
     */
    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 65536, 100000})
    void setPort_WithInvalidPort_ThrowsIllegalArgumentException(int port) {
        // 断言设置无效端口时抛出异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> serverConfig.setPort(port),
            "无效端口未抛出异常"
        );
        // 可选：验证异常消息（若方法实现中定义了具体消息）
        assertTrue(exception.getMessage().contains("无效端口"), "异常消息不符合预期");
    }

    /**
     * 补充测试：验证初始端口（若有默认值）
     * 假设初始端口为-1（未设置状态），可根据实际实现调整
     */
    @Test
    void getPort_Initially_ReturnsDefault() {
        assertEquals(-1, serverConfig.getPort(), "初始端口不符合预期");
    }
}