import spark.servlet.where;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkServletsparkservletSparkFilterjavawhereHttpServletRequestWrapper1aab30efTest {

    @Mock
    private HttpServletRequest mockHttpRequest; // 模拟有效的HttpServletRequest

    /**
     * 正常路径：传入有效HttpServletRequest，验证Wrapper实例创建成功且正确包装请求对象
     */
    @Test
    void testConstructor_WithValidHttpRequest_Success() {
        // 执行：创建HttpServletRequestWrapper实例
        HttpServletRequestWrapper wrapper = new HttpServletRequestWrapper(mockHttpRequest);

        // 断言：实例非空，且包装的请求对象与传入的一致
        assertThat(wrapper).isNotNull();
        assertThat(wrapper.getRequest()).isSameAs(mockHttpRequest);
    }

    /**
     * 异常路径：传入null作为HttpServletRequest，验证抛出IllegalArgumentException
     */
    @Test
    void testConstructor_WithNullHttpRequest_ThrowsIllegalArgumentException() {
        // 执行并断言：传入null时抛出异常，且异常消息符合预期
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new HttpServletRequestWrapper(null));
        
        assertThat(exception).hasMessage("Request cannot be null");
    }
}