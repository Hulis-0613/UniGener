import spark.http.matching.MatcherFilter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import javax.servlet.http.HttpServletRequest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// 假设目标方法所在类为HttpUtils（请根据实际类名调整）
import com.example.HttpUtils;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingMatcherFilterjavaMatcherFilterGetHttpMethodFrom73b5ea5cTest {

    @Mock
    private HttpServletRequest request;

    /**
     * 正常路径：测试所有标准HTTP方法的获取
     * 覆盖GET/POST/PUT/DELETE等常见方法
     */
    @ParameterizedTest
    @ValueSource(strings = {"GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS", "PATCH", "TRACE"})
    void getHttpMethodFrom_ValidRequest_ReturnsCorrectMethod(String expectedMethod) {
        // 模拟request.getMethod()返回预期方法
        when(request.getMethod()).thenReturn(expectedMethod);
        
        // 执行目标方法
        String actualMethod = HttpUtils.getHttpMethodFrom(request);
        
        // 断言结果与预期一致
        assertEquals(expectedMethod, actualMethod, 
            "HTTP方法获取错误，预期：" + expectedMethod + "，实际：" + actualMethod);
    }

    /**
     * 异常路径：测试传入null请求对象的情况
     * 假设方法在入参为null时抛出IllegalArgumentException（根据实际实现调整异常类型）
     */
    @Test
    void getHttpMethodFrom_NullRequest_ThrowsIllegalArgumentException() {
        // 执行目标方法并断言抛出指定异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> HttpUtils.getHttpMethodFrom(null),
            "当请求对象为null时，应抛出IllegalArgumentException");
        
        // 可选：断言异常消息（如果方法实现包含具体消息）
        assertTrue(exception.getMessage().contains("request must not be null"), 
            "异常消息不符合预期");
    }
}