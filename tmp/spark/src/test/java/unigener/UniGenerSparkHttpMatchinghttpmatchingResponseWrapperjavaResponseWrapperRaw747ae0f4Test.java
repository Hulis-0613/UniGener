import spark.http.matching.ResponseWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletResponse;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingResponseWrapperjavaResponseWrapperRaw747ae0f4Test {

    @Mock
    private HttpServletResponse mockOriginalResponse;

    /**
     * 正常路径测试：当存在原始HttpServletResponse时，raw()应返回该对象
     */
    @Test
    void raw_WithValidOriginalResponse_ReturnsOriginalResponse() {
        // Arrange：创建包含原始响应的包装类实例
        ResponseWrapper wrapper = new ResponseWrapper(mockOriginalResponse);

        // Act：调用raw()方法获取原始响应
        HttpServletResponse result = wrapper.raw();

        // Assert：验证返回的是原始响应对象
        assertSame("原始响应对象未正确返回", mockOriginalResponse, result);
    }

    /**
     * 异常路径测试：当原始HttpServletResponse为null时，raw()应抛出IllegalStateException
     * （注：异常类型根据实际业务逻辑调整，此处假设未初始化原始响应时抛出IllegalStateException）
     */
    @Test
    void raw_WithNullOriginalResponse_ThrowsIllegalStateException() {
        // Arrange：创建原始响应为null的包装类实例（假设构造函数允许传入null）
        ResponseWrapper wrapper = new ResponseWrapper(null);

        // Act & Assert：验证调用raw()时抛出预期异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            wrapper::raw, 
            "当原始响应为null时，raw()应抛出IllegalStateException"
        );
        assertTrue(exception.getMessage().contains("原始HttpServletResponse未初始化"), 
            "异常消息应包含未初始化提示");
    }
}