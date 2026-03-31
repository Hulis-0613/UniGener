import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperSchemed22ab1aaTest {

    @Mock
    private HttpServletRequest request;  // Mock底层请求对象

    private RequestWrapper requestWrapper;  // 待测试的包装类实例

    /**
     * 正常路径：测试HTTP协议场景
     */
    @Test
    void scheme_WithHttpScheme_ShouldReturnHttp() {
        // 1. 准备：模拟请求的scheme为"http"
        when(request.getScheme()).thenReturn("http");
        requestWrapper = new RequestWrapper(request);

        // 2. 执行：调用目标方法
        String actualScheme = requestWrapper.scheme();

        // 3. 断言：返回值应为"http"
        assertEquals("http", actualScheme);
    }

    /**
     * 正常路径：测试HTTPS协议场景
     */
    @Test
    void scheme_WithHttpsScheme_ShouldReturnHttps() {
        // 1. 准备：模拟请求的scheme为"https"
        when(request.getScheme()).thenReturn("https");
        requestWrapper = new RequestWrapper(request);

        // 2. 执行：调用目标方法
        String actualScheme = requestWrapper.scheme();

        // 3. 断言：返回值应为"https"
        assertEquals("https", actualScheme);
    }

    /**
     * 异常路径：测试协议信息为null的场景
     */
    @Test
    void scheme_WithNullScheme_ShouldReturnNull() {
        // 1. 准备：模拟请求的scheme为null（异常情况）
        when(request.getScheme()).thenReturn(null);
        requestWrapper = new RequestWrapper(request);

        // 2. 执行：调用目标方法
        String actualScheme = requestWrapper.scheme();

        // 3. 断言：返回值应为null（假设方法直接透传null，若有默认值可调整断言）
        assertEquals(null, actualScheme);
    }
}