import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperRawfc0ff925Test {

    @Mock
    private HttpServletRequest mockRequest; // 模拟正常的HttpServletRequest对象

    private RequestWrapper requestWrapper;

    /**
     * 正常路径测试：当内部持有有效HttpServletRequest时，raw()应返回该对象
     */
    @Test
    void raw_WithValidOriginalRequest_ShouldReturnOriginalRequest() {
        // 准备：创建RequestWrapper并注入模拟的HttpServletRequest
        requestWrapper = new RequestWrapper(mockRequest);

        // 执行：调用raw()方法
        HttpServletRequest result = requestWrapper.raw();

        // 断言：返回的对象与注入的模拟对象一致
        assertSame(mockRequest, result, "raw() should return the original HttpServletRequest");
    }

    /**
     * 异常路径测试：当内部HttpServletRequest为null时，raw()应抛出NullPointerException
     */
    @Test
    void raw_WithNullOriginalRequest_ShouldThrowNullPointerException() {
        // 准备：创建RequestWrapper并注入null（模拟未初始化的场景）
        requestWrapper = new RequestWrapper(null);

        // 执行&断言：调用raw()时抛出NullPointerException
        NullPointerException exception = assertThrows(NullPointerException.class, 
            () -> requestWrapper.raw(), 
            "raw() should throw NullPointerException when original request is null"
        );
        assertTrue(exception.getMessage().contains("Original HttpServletRequest is not initialized"), 
            "Exception message should indicate uninitialized request");
    }
}