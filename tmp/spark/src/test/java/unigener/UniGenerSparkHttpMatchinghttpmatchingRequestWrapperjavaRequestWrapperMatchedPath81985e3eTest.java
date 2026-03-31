import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperMatchedPath81985e3eTest {

    @Mock
    private HttpServletRequest request;  // 模拟HttpServletRequest

    @InjectMocks
    private RequestWrapper requestWrapper;  // 待测试的RequestWrapper实例

    /**
     * 正常路径：存在有效的匹配路径时，返回正确路径
     */
    @Test
    void matchedPath_WithValidMatchedPath_ReturnsCorrectPath() {
        // 准备：模拟请求中存在匹配路径属性
        String expectedPath = "/api/orders/123";
        when(request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"))
                .thenReturn(expectedPath);

        // 执行：调用目标方法
        String actualPath = requestWrapper.matchedPath();

        // 断言：返回值与预期一致
        assertEquals(expectedPath, actualPath);
    }

    /**
     * 正常路径：匹配路径为空字符串时，返回空字符串
     */
    @Test
    void matchedPath_WithEmptyMatchedPath_ReturnsEmptyString() {
        // 准备：模拟请求中匹配路径为空字符串
        String expectedEmptyPath = "";
        when(request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"))
                .thenReturn(expectedEmptyPath);

        // 执行：调用目标方法
        String actualPath = requestWrapper.matchedPath();

        // 断言：返回空字符串
        assertEquals(expectedEmptyPath, actualPath);
    }

    /**
     * 异常路径：无匹配路径（属性为null）时，返回null
     */
    @Test
    void matchedPath_WithoutMatchedPath_ReturnsNull() {
        // 准备：模拟请求中匹配路径属性为null（无匹配路径）
        when(request.getAttribute("org.springframework.web.servlet.HandlerMapping.pathWithinHandlerMapping"))
                .thenReturn(null);

        // 执行：调用目标方法
        String actualPath = requestWrapper.matchedPath();

        // 断言：返回null
        assertNull(actualPath);
    }
}