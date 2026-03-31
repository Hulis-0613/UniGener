import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperCookiesbd1b2cb2Test {

    @Mock
    private HttpServletRequest request; // 模拟HttpServletRequest依赖

    private RequestWrapper requestWrapper; // 待测试的类实例


    /**
     * 正常路径：当请求中存在cookies时，cookies()方法应返回正确的Cookie数组
     */
    @Test
    void cookies_WithExistingCookies_ReturnsCookies() {
        // 1. 准备测试数据：预期的Cookie数组
        Cookie cookie1 = new Cookie("sessionId", "abc123");
        Cookie cookie2 = new Cookie("username", "testUser");
        Cookie[] expectedCookies = {cookie1, cookie2};

        // 2. 模拟依赖行为：当调用request.getCookies()时返回预期的Cookie数组
        when(request.getCookies()).thenReturn(expectedCookies);

        // 3. 创建测试对象
        requestWrapper = new RequestWrapper(request);

        // 4. 执行目标方法
        Cookie[] actualCookies = requestWrapper.cookies();

        // 5. 断言结果：返回的Cookie数组与预期一致（内容和顺序）
        assertArrayEquals(expectedCookies, actualCookies, "返回的cookies与预期不符");
    }


    /**
     * 异常路径：当请求中无cookies时（返回null），cookies()方法应返回null
     */
    @Test
    void cookies_WithoutCookies_ReturnsNull() {
        // 1. 模拟依赖行为：当调用request.getCookies()时返回null（无cookies）
        when(request.getCookies()).thenReturn(null);

        // 2. 创建测试对象
        requestWrapper = new RequestWrapper(request);

        // 3. 执行目标方法
        Cookie[] actualCookies = requestWrapper.cookies();

        // 4. 断言结果：返回null
        assertNull(actualCookies, "无cookies时应返回null");
    }


    /**
     * 边界路径：当请求中存在空Cookie数组时，cookies()方法应返回空数组
     * （补充覆盖可能的边界场景，确保100%覆盖率）
     */
    @Test
    void cookies_WithEmptyCookies_ReturnsEmptyArray() {
        // 1. 准备测试数据：空Cookie数组
        Cookie[] expectedEmptyCookies = new Cookie[0];

        // 2. 模拟依赖行为：当调用request.getCookies()时返回空数组
        when(request.getCookies()).thenReturn(expectedEmptyCookies);

        // 3. 创建测试对象
        requestWrapper = new RequestWrapper(request);

        // 4. 执行目标方法
        Cookie[] actualCookies = requestWrapper.cookies();

        // 5. 断言结果：返回空数组
        assertArrayEquals(expectedEmptyCookies, actualCookies, "空cookies时应返回空数组");
    }
}