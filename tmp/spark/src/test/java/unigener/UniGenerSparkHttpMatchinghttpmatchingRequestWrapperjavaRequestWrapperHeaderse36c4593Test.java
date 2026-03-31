import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// 假设目标方法所在类为HeaderHandler，依赖RequestWrapper（此处简化为HttpServletRequest模拟）
@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperHeaderse36c4593Test {

    @Mock
    private HttpServletRequest requestWrapper; // 模拟RequestWrapper（实际项目中替换为真实类型）

    @InjectMocks
    private HeaderHandler headerHandler; // 被测试类


    /**
     * 正常路径：指定header存在且有非空值
     */
    @Test
    void headers_ExistingHeaderWithValue_ReturnsCorrectValue() {
        // 准备：模拟header存在且返回"testValue"
        String testHeader = "X-Test-Header";
        String expectedValue = "testValue";
        when(requestWrapper.getHeader(testHeader)).thenReturn(expectedValue);

        // 执行
        String actualValue = headerHandler.headers(testHeader);

        // 断言
        assertEquals(expectedValue, actualValue, "存在的header应返回正确值");
    }


    /**
     * 正常路径：指定header存在但值为空字符串
     */
    @Test
    void headers_ExistingHeaderWithEmptyValue_ReturnsEmptyString() {
        // 准备：模拟header存在但值为空
        String testHeader = "X-Empty-Header";
        String expectedValue = "";
        when(requestWrapper.getHeader(testHeader)).thenReturn(expectedValue);

        // 执行
        String actualValue = headerHandler.headers(testHeader);

        // 断言
        assertEquals(expectedValue, actualValue, "空值header应返回空字符串");
    }


    /**
     * 正常路径：指定header不存在
     */
    @Test
    void headers_NonExistingHeader_ReturnsNull() {
        // 准备：模拟header不存在（返回null）
        String nonExistingHeader = "X-Non-Existent-Header";
        when(requestWrapper.getHeader(nonExistingHeader)).thenReturn(null);

        // 执行
        String actualValue = headerHandler.headers(nonExistingHeader);

        // 断言
        assertNull(actualValue, "不存在的header应返回null");
    }


    /**
     * 异常路径：输入参数header为null
     */
    @Test
    void headers_NullHeaderParameter_ThrowsNullPointerException() {
        // 执行并断言：传入null时抛出NPE（根据方法设计，若允许null则改为断言返回null）
        assertThrows(NullPointerException.class,
                () -> headerHandler.headers(null),
                "传入null参数时应抛出NullPointerException");
    }
}

// 目标方法所在类的简化定义（仅为测试参考，实际项目中以真实类为准）
class HeaderHandler {
    private final HttpServletRequest requestWrapper;

    public HeaderHandler(HttpServletRequest requestWrapper) {
        this.requestWrapper = requestWrapper;
    }

    // 目标方法：获取指定header的值
    public String headers(String header) {
        if (header == null) {
            throw new NullPointerException("header参数不能为null");
        }
        return requestWrapper.getHeader(header);
    }
}