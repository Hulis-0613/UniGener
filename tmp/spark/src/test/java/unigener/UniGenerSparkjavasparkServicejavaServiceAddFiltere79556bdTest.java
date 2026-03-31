import spark.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkjavasparkServicejavaServiceAddFiltere79556bdTest {

    private final FilterManager filterManager = new FilterManager();

    @Mock
    private FilterImpl mockFilter;

    @Mock
    private FilterImpl anotherMockFilter;

    // 正常路径：添加合法HTTP方法和过滤器
    @Test
    void addFilter_WithValidHttpMethodAndFilter_AddsSuccessfully() {
        // 准备测试数据
        HttpMethod httpMethod = HttpMethod.GET;

        // 执行目标方法
        filterManager.addFilter(httpMethod, mockFilter);

        // 验证结果：过滤器已添加到对应HTTP方法
        assertThat(filterManager.getFilters(httpMethod))
                .containsExactly(mockFilter)
                .hasSize(1);
    }

    // 正常路径：添加不同HTTP方法的过滤器
    @Test
    void addFilter_WithDifferentHttpMethods_AddsSuccessfully() {
        // 准备测试数据
        HttpMethod getMethod = HttpMethod.GET;
        HttpMethod postMethod = HttpMethod.POST;

        // 执行目标方法
        filterManager.addFilter(getMethod, mockFilter);
        filterManager.addFilter(postMethod, anotherMockFilter);

        // 验证结果：不同方法对应不同过滤器
        assertThat(filterManager.getFilters(getMethod)).containsExactly(mockFilter);
        assertThat(filterManager.getFilters(postMethod)).containsExactly(anotherMockFilter);
        assertThat(filterManager.getFilters(HttpMethod.PUT)).isEmpty(); // 未添加PUT方法的过滤器
    }

    // 异常路径：HTTP方法为null时抛出IllegalArgumentException
    @Test
    void addFilter_WithNullHttpMethod_ThrowsIllegalArgumentException() {
        // 执行目标方法并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> filterManager.addFilter(null, mockFilter));
        
        // 验证异常信息（假设原方法异常消息包含"httpMethod"）
        assertThat(exception.getMessage()).contains("httpMethod");
    }

    // 异常路径：过滤器为null时抛出IllegalArgumentException
    @Test
    void addFilter_WithNullFilter_ThrowsIllegalArgumentException() {
        // 准备测试数据
        HttpMethod httpMethod = HttpMethod.GET;

        // 执行目标方法并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> filterManager.addFilter(httpMethod, null));
        
        // 验证异常信息（假设原方法异常消息包含"filter"）
        assertThat(exception.getMessage()).contains("filter");
    }
}