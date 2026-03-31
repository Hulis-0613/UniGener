import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperUserAgent18bac701Test {

    @Mock
    private HttpServletRequest request; // 模拟HttpServletRequest对象

    private RequestWrapper requestWrapper; // 待测试的RequestWrapper实例

    /**
     * 正常路径：当请求头中存在User-Agent时，返回正确的用户代理字符串
     */
    @Test
    void userAgent_WithUserAgentHeaderPresent_ReturnsUserAgentString() {
        // 1. 准备测试数据
        String expectedUserAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 14_0) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.0 Safari/605.1.15";
        
        // 2. 模拟依赖行为：当调用request.getHeader("User-Agent")时返回预期值
        when(request.getHeader("User-Agent")).thenReturn(expectedUserAgent);
        requestWrapper = new RequestWrapper(request); // 初始化RequestWrapper，传入模拟的request

        // 3. 执行目标方法
        String actualUserAgent = requestWrapper.userAgent();

        // 4. 断言结果：返回的用户代理字符串与预期一致
        assertEquals(expectedUserAgent, actualUserAgent, "User-Agent字符串不匹配");
    }

    /**
     * 异常路径1：当请求头中不存在User-Agent时，返回null
     */
    @Test
    void userAgent_WithoutUserAgentHeader_ReturnsNull() {
        // 1. 模拟依赖行为：当调用request.getHeader("User-Agent")时返回null（无该头）
        when(request.getHeader("User-Agent")).thenReturn(null);
        requestWrapper = new RequestWrapper(request);

        // 2. 执行目标方法
        String actualUserAgent = requestWrapper.userAgent();

        // 3. 断言结果：返回null
        assertNull(actualUserAgent, "无User-Agent头时应返回null");
    }

    /**
     * 异常路径2：当RequestWrapper持有的HttpServletRequest为null时，抛出NullPointerException
     */
    @Test
    void userAgent_WithNullRequest_ThrowsNullPointerException() {
        // 1. 初始化RequestWrapper时传入null（模拟请求对象为空的场景）
        requestWrapper = new RequestWrapper(null);

        // 2. 执行目标方法并断言抛出NPE
        NullPointerException exception = assertThrows(NullPointerException.class, 
            () -> requestWrapper.userAgent(), 
            "当request为null时应抛出NullPointerException"
        );
        
        // （可选）验证异常信息（若方法实现中明确抛出带消息的NPE）
        assertTrue(exception.getMessage().contains("request is null"), "异常信息不符合预期");
    }
}