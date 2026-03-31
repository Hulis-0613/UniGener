import spark.Redirect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkjavasparkRedirectjavaRedirectCreate2cc911bdTest {

    @Mock
    private Routable mockHttp; // 模拟Routable类型参数

    /**
     * 正常路径：输入有效Routable实例，验证成功创建Redirect并正确初始化属性
     */
    @Test
    void create_WithValidRoutable_ReturnsRedirectInstance() {
        // 1. 准备：模拟Routable的必要属性（假设Redirect依赖路由路径和状态码）
        String expectedPath = "/target";
        int expectedStatusCode = 302;
        when(mockHttp.getPath()).thenReturn(expectedPath);
        when(mockHttp.getStatusCode()).thenReturn(expectedStatusCode);

        // 2. 执行：调用create方法
        Redirect result = Redirect.create(mockHttp);

        // 3. 断言：验证返回实例非空且属性正确
        assertNotNull(result, "Redirect实例不应为null");
        assertEquals(expectedPath, result.getTargetPath(), "目标路径应与Routable一致");
        assertEquals(expectedStatusCode, result.getStatusCode(), "状态码应与Routable一致");
    }

    /**
     * 异常路径：输入null的Routable参数，验证抛出IllegalArgumentException
     */
    @Test
    void create_WithNullRoutable_ThrowsIllegalArgumentException() {
        // 1. 执行+断言：调用create方法并验证异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Redirect.create(null),
            "输入null时应抛出IllegalArgumentException"
        );

        // 2. 验证异常消息（假设方法约定异常消息为"http must not be null"）
        assertEquals("http must not be null", exception.getMessage(), "异常消息不符合预期");
    }

    /**
     * 异常路径：输入Routable的关键属性为null（如路径为空），验证抛出IllegalArgumentException
     * （若create方法对Routable属性有校验，需补充此场景）
     */
    @Test
    void create_WithInvalidRoutableProperty_ThrowsIllegalArgumentException() {
        // 1. 准备：模拟Routable的路径为null（假设路径为必要属性）
        when(mockHttp.getPath()).thenReturn(null);

        // 2. 执行+断言：调用create方法并验证异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> Redirect.create(mockHttp),
            "Routable路径为null时应抛出IllegalArgumentException"
        );

        // 3. 验证异常消息（假设约定消息为"http path must not be null"）
        assertEquals("http path must not be null", exception.getMessage(), "异常消息不符合预期");
    }
}