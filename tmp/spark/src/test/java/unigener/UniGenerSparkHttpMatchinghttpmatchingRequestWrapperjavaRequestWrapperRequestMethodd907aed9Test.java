import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperRequestMethodd907aed9Test {

    @Mock
    private HttpServletRequest mockRequest; // 模拟底层请求对象

    /**
     * 正常路径：请求方法为标准HTTP方法（GET）
     */
    @Test
    void requestMethod_WhenGetMethod_ShouldReturnGet() {
        // 准备：模拟请求对象返回"GET"方法
        when(mockRequest.getMethod()).thenReturn("GET");
        RequestWrapper wrapper = new RequestWrapper(mockRequest);

        // 执行
        String result = wrapper.requestMethod();

        // 断言：返回值与预期一致
        assertEquals("GET", result);
    }

    /**
     * 正常路径：请求方法为标准HTTP方法（POST）
     */
    @Test
    void requestMethod_WhenPostMethod_ShouldReturnPost() {
        // 准备：模拟请求对象返回"POST"方法
        when(mockRequest.getMethod()).thenReturn("POST");
        RequestWrapper wrapper = new RequestWrapper(mockRequest);

        // 执行
        String result = wrapper.requestMethod();

        // 断言：返回值与预期一致
        assertEquals("POST", result);
    }

    /**
     * 异常路径：请求方法为null（底层请求对象返回null）
     */
    @Test
    void requestMethod_WhenMethodIsNull_ShouldReturnNull() {
        // 准备：模拟请求对象返回null
        when(mockRequest.getMethod()).thenReturn(null);
        RequestWrapper wrapper = new RequestWrapper(mockRequest);

        // 执行
        String result = wrapper.requestMethod();

        // 断言：返回null
        assertNull(result);
    }

    /**
     * 异常路径：请求方法为空字符串（底层请求对象返回空字符串）
     */
    @Test
    void requestMethod_WhenMethodIsEmpty_ShouldReturnEmpty() {
        // 准备：模拟请求对象返回空字符串
        when(mockRequest.getMethod()).thenReturn("");
        RequestWrapper wrapper = new RequestWrapper(mockRequest);

        // 执行
        String result = wrapper.requestMethod();

        // 断言：返回空字符串
        assertEquals("", result);
    }
}