import spark.Request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRequestjavaRequestHost39835799Test {

    /**
     * 正常路径测试：当host已设置时，host()方法应返回正确的host值
     */
    @Test
    void host_WithHostSet_ReturnsCorrectHost() {
        // 准备：创建Request实例并设置host
        Request request = new Request();
        String expectedHost = "api.example.com";
        request.setHost(expectedHost); // 假设Request类提供setHost方法设置host

        // 执行：调用host()方法
        String actualHost = request.host();

        // 断言：返回值与预期一致
        assertEquals(expectedHost, actualHost, "Host值不匹配");
    }

    /**
     * 异常路径测试：当host未设置时，host()方法应抛出IllegalStateException
     */
    @Test
    void host_WithoutHostSet_ThrowsIllegalStateException() {
        // 准备：创建Request实例，不设置host（默认host为null或空）
        Request request = new Request();

        // 执行并断言：调用host()方法时抛出异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            request::host,
            "未设置host时，应抛出IllegalStateException"
        );

        // 可选：验证异常消息（若方法实现中定义了具体消息）
        assertTrue(exception.getMessage().contains("host未设置"), "异常消息不符合预期");
    }
}