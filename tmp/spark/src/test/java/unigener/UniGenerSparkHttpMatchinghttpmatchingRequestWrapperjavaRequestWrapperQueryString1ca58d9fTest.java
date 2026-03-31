import spark.http.matching.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperQueryString1ca58d9fTest {

    @Mock
    private HttpServletRequest request;  // 模拟底层HttpServletRequest依赖

    @InjectMocks
    private RequestWrapper requestWrapper;  // 待测试的RequestWrapper实例

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // 初始化Mockito注解
    }

    /**
     * 正常路径：存在查询字符串时，返回正确的查询字符串
     */
    @Test
    void queryString_WithExistingQueryString_ReturnsExpectedValue() {
        // 准备测试数据：模拟请求的查询字符串
        String expectedQuery = "username=testUser&role=admin";
        when(request.getQueryString()).thenReturn(expectedQuery);

        // 执行目标方法
        String actualQuery = requestWrapper.queryString();

        // 断言结果与预期一致
        assertEquals(expectedQuery, actualQuery, "查询字符串应正确返回");
    }

    /**
     * 异常路径：无查询字符串时，返回null（或空字符串，根据实际实现调整）
     */
    @Test
    void queryString_WithoutQueryString_ReturnsNull() {
        // 模拟请求无查询字符串（返回null）
        when(request.getQueryString()).thenReturn(null);

        // 执行目标方法
        String actualQuery = requestWrapper.queryString();

        // 断言结果为null（若实际实现返回空字符串，可改为assertEquals("", actualQuery)）
        assertEquals(null, actualQuery, "无查询字符串时应返回null");
    }
}