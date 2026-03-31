import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperContextPath7358bb7bTest {

    @Mock
    private HttpServletRequest mockRequest;

    /**
     * 正常路径测试：当内部请求对象存在有效上下文路径时，返回正确路径
     */
    @Test
    void contextPath_WithValidRequest_ShouldReturnCorrectContextPath() {
        // 准备：模拟请求的上下文路径
        String expectedContextPath = "/api/v1";
        when(mockRequest.getContextPath()).thenReturn(expectedContextPath);
        
        // 创建包装类实例（假设构造函数接收HttpServletRequest）
        RequestWrapper requestWrapper = new RequestWrapper(mockRequest);

        // 执行目标方法
        String actualContextPath = requestWrapper.contextPath();

        // 断言：返回值与预期一致
        assertEquals(expectedContextPath, actualContextPath);
    }

    /**
     * 异常路径测试：当内部请求对象为null时，调用contextPath应抛出NullPointerException
     */
    @Test
    void contextPath_WithNullRequest_ShouldThrowNullPointerException() {
        // 准备：创建包装类实例时传入null（假设构造函数允许null入参）
        RequestWrapper requestWrapper = new RequestWrapper(null);

        // 执行并断言：调用contextPath时抛出NPE
        assertThrows(NullPointerException.class, requestWrapper::contextPath);
    }
}