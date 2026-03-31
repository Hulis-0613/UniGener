import spark.http.matching.ResponseWrapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

// 假设Response是接口，使用Mockito模拟Response实例
@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingResponseWrapperjavaResponseWrapperSetDelegate0bbe75daTest {

    @Mock
    private Response mockDelegate; // 模拟正常的Response委托对象

    private final ResponseWrapper wrapper = new ResponseWrapper(); // 测试目标对象

    /**
     * 正常路径测试：设置非null委托对象，验证委托被正确设置
     */
    @Test
    void setDelegate_WithValidDelegate_SetsDelegateSuccessfully() {
        // 执行设置委托操作
        wrapper.setDelegate(mockDelegate);

        // 断言委托已正确设置（假设ResponseWrapper提供getDelegate()方法）
        assertSame(mockDelegate, wrapper.getDelegate(), "Delegate未被正确设置");
    }

    /**
     * 异常路径测试：设置null委托对象，验证抛出NullPointerException
     */
    @Test
    void setDelegate_WithNullDelegate_ThrowsNullPointerException() {
        // 执行设置null委托并断言抛出NPE
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> wrapper.setDelegate(null),
            "设置null委托时未抛出预期的NullPointerException"
        );

        // 可选：验证异常消息（若方法实现中定义了具体消息）
        assertTrue(exception.getMessage().contains("delegate cannot be null"), "异常消息不符合预期");
    }
}