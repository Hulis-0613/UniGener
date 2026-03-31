import spark.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkResponsejavaResponseBody5ea298b6Test {

    /**
     * 测试正常路径：传入非null字符串，验证响应体被正确设置
     */
    @Test
    void body_WithNonNulLBody_SetsBodySuccessfully() {
        // 准备测试对象和输入数据
        Response response = new Response();
        String expectedBody = "test response body";

        // 执行目标方法
        response.body(expectedBody);

        // 验证结果：响应体与输入一致
        assertEquals(expectedBody, response.getBody(), "响应体未正确设置");
    }

    /**
     * 测试异常路径：传入null，验证抛出IllegalArgumentException
     */
    @Test
    void body_WithNullBody_ThrowsIllegalArgumentException() {
        // 准备测试对象和null输入
        Response response = new Response();
        String nullBody = null;

        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> response.body(nullBody),
            "传入null时未抛出预期异常"
        );

        // 验证异常信息（假设原方法对null输入的错误提示为"Body cannot be null"）
        assertEquals("Body cannot be null", exception.getMessage(), "异常信息不符合预期");
    }
}