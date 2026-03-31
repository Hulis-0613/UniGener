import spark.embeddedserver.jetty.HttpRequestWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.servlet.ReadListener;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkEmbeddedserverJettyembeddedserverjettyHttpRequestWrapperjavaHttpRequestWrapperSetReadListener8cb5537cTest {

    private HttpRequestWrapper httpRequestWrapper;

    @Mock
    private ReadListener mockReadListener; // Mock一个合法的ReadListener实例

    @BeforeEach
    void setUp() {
        // 初始化测试环境：创建HttpRequestWrapper实例（假设构造函数无需参数，若有参数可补充mock）
        httpRequestWrapper = new HttpRequestWrapper();
        // 初始化Mockito注解（如@Mock）
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 正常路径：传入非null的ReadListener，验证监听器被正确设置
     */
    @Test
    void setReadListener_WithValidReadListener_SetsListenerSuccessfully() {
        // 执行目标方法：设置监听器
        httpRequestWrapper.setReadListener(mockReadListener);

        // 验证：通过getter方法获取已设置的监听器，断言与入参一致
        ReadListener actualListener = httpRequestWrapper.getReadListener();
        assertSame(mockReadListener, actualListener, "设置的监听器未正确保存");
    }

    /**
     * 异常路径：传入null的ReadListener，验证抛出NullPointerException
     */
    @Test
    void setReadListener_WithNullReadListener_ThrowsNullPointerException() {
        // 执行并验证：调用setReadListener(null)时抛出NPE
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> httpRequestWrapper.setReadListener(null),
                "传入null时未抛出预期的NullPointerException");

        // 可选：验证异常消息（若原方法有明确异常信息）
        assertTrue(exception.getMessage().contains("readListener must not be null"), 
                "异常消息不符合预期");
    }
}