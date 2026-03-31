import spark.RequestResponseFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkjavasparkRequestResponseFactoryjavaRequestResponseFactoryCreate8dccc1afTest {

    // 待测试的工厂类实例
    private final RequestResponseFactory factory = new RequestResponseFactory();

    // Mock HttpServletRequest对象
    @Mock
    private HttpServletRequest mockRequest;

    /**
     * 正常路径测试：使用有效HttpServletRequest创建Request实例
     * 验证：返回的Request实例非空，且属性与输入请求一致
     */
    @Test
    void create_WithValidHttpServletRequest_ReturnsRequestInstance() {
        // 1. 准备测试数据
        String expectedUri = "/v1/api/users";
        String expectedMethod = "POST";
        String expectedQueryString = "id=123&name=test";
        Map<String, String[]> expectedParams = Map.of(
            "id", new String[]{"123"},
            "name", new String[]{"test"}
        );

        // 2. 模拟HttpServletRequest的行为
        when(mockRequest.getRequestURI()).thenReturn(expectedUri);
        when(mockRequest.getMethod()).thenReturn(expectedMethod);
        when(mockRequest.getQueryString()).thenReturn(expectedQueryString);
        when(mockRequest.getParameterMap()).thenReturn(expectedParams);

        // 3. 执行目标方法
        Request result = factory.create(mockRequest);

        // 4. 断言结果（假设Request类有对应的getter方法）
        assertNotNull(result, "创建的Request实例不能为null");
        assertEquals(expectedUri, result.getUri(), "URI不匹配");
        assertEquals(expectedMethod, result.getMethod(), "请求方法不匹配");
        assertEquals(expectedQueryString, result.getQueryString(), "查询字符串不匹配");
        assertArrayEquals(expectedParams.get("id"), result.getParameterMap().get("id"), "参数id不匹配");
        assertArrayEquals(expectedParams.get("name"), result.getParameterMap().get("name"), "参数name不匹配");
    }

    /**
     * 异常路径测试：输入null的HttpServletRequest
     * 验证：抛出NullPointerException（或其他预期异常，根据实际实现调整）
     */
    @Test
    void create_WithNullHttpServletRequest_ThrowsNullPointerException() {
        // 执行目标方法并断言异常
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> factory.create(null),
            "输入null时应抛出NullPointerException"
        );
        assertTrue(exception.getMessage().contains("HttpServletRequest cannot be null"), "异常信息不符合预期");
    }
}