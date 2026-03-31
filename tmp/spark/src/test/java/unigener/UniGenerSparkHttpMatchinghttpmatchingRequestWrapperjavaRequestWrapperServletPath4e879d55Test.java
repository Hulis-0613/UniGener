import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperServletPath4e879d55Test {

    @Mock
    private HttpServletRequest mockRequest;

    /**
     * 测试正常路径：当被包装请求的servlet路径为有效字符串时，返回正确路径
     */
    @Test
    void servletPath_WithValidPath_ReturnsCorrectPath() {
        // 准备：模拟请求返回有效路径
        when(mockRequest.getServletPath()).thenReturn("/api/users");
        
        // 执行：创建包装类并调用方法
        RequestWrapper wrapper = new RequestWrapper(mockRequest);
        String result = wrapper.servletPath();
        
        // 断言：结果与模拟路径一致
        assertEquals("/api/users", result);
    }

    /**
     * 测试边界路径：当被包装请求的servlet路径为null时，返回null
     */
    @Test
    void servletPath_WithNullPath_ReturnsNull() {
        // 准备：模拟请求返回null路径
        when(mockRequest.getServletPath()).thenReturn(null);
        
        // 执行：创建包装类并调用方法
        RequestWrapper wrapper = new RequestWrapper(mockRequest);
        String result = wrapper.servletPath();
        
        // 断言：结果为null
        assertNull(result);
    }

    /**
     * 测试异常路径：当包装的请求为null时，调用方法抛出NullPointerException
     */
    @Test
    void servletPath_WithNullRequest_ThrowsNPE() {
        // 准备：创建包装类时传入null请求
        RequestWrapper wrapper = new RequestWrapper(null);
        
        // 执行 & 断言：调用方法抛出NPE
        assertThrows(NullPointerException.class, wrapper::servletPath);
    }
}