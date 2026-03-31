import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperPort26e64b49Test {

    private final PortProvider portProvider = new PortProvider();

    /**
     * 测试正常路径：port()返回有效端口号（1-65535范围）
     */
    @Test
    void port_returnsValidPort() {
        int actualPort = portProvider.port();
        assertTrue(actualPort >= 1 && actualPort <= 65535, 
                  "Valid port should be between 1 and 65535");
    }

    /**
     * 测试异常路径：当内部端口配置为无效值（<1或>65535）时，抛出IllegalStateException
     */
    @Test
    void port_withInvalidConfig_throwsIllegalStateException() throws NoSuchFieldException, IllegalAccessException {
        // 反射获取并修改内部端口配置（假设PortProvider有私有字段"configuredPort"）
        Field portField = PortProvider.class.getDeclaredField("configuredPort");
        portField.setAccessible(true);
        
        // 测试端口<1的情况
        portField.set(portProvider, 0);
        assertThrows(IllegalStateException.class, 
                    portProvider::port, 
                    "Port 0 should throw IllegalStateException");
        
        // 测试端口>65535的情况
        portField.set(portProvider, 65536);
        assertThrows(IllegalStateException.class, 
                    portProvider::port, 
                    "Port 65536 should throw IllegalStateException");
    }
}