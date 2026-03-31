import spark.embeddedserver.jetty.HttpRequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkEmbeddedserverJettyembeddedserverjettyHttpRequestWrapperjavaHttpRequestWrapperHttpRequestWrapper4619a04aTest {

    @Mock
    private HttpServletRequest validRequest; // 模拟正常的HttpServletRequest

    /**
     * 正常路径测试：传入有效HttpServletRequest，验证构造方法成功创建实例并持有传入的request
     */
    @Test
    void testConstructor_WithValidRequest_Succeeds() {
        // 执行构造方法
        HttpRequestWrapper wrapper = new HttpRequestWrapper(validRequest);

        // 断言实例非空
        assertNotNull(wrapper, "HttpRequestWrapper实例应成功创建");
        // 假设存在getter方法获取内部request（若实际无getter，可删除此断言或调整验证逻辑）
        assertSame(validRequest, wrapper.getHttpServletRequest(), "构造方法应持有传入的HttpServletRequest实例");
    }

    /**
     * 异常路径测试：传入null，验证构造方法抛出NullPointerException
     */
    @Test
    void testConstructor_WithNullRequest_ThrowsNullPointerException() {
        // 执行构造方法并断言抛出NPE
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> new HttpRequestWrapper(null),
            "传入null时构造方法应抛出NullPointerException"
        );

        // 可选：验证异常消息（若构造方法指定了消息）
        assertTrue(exception.getMessage().contains("request must not be null"), "异常消息应提示request不可为null");
    }
}