import spark.QueryParamsMap;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkjavasparkQueryParamsMapjavaQueryParamsMapQueryParamsMapb56efa8cTest {

    @Mock
    private HttpServletRequest request;

    /**
     * 测试正常路径：多参数场景
     */
    @Test
    void testConstructorWithMultipleQueryParams() {
        // 模拟请求参数: name=Alice, age=25, city=Beijing
        Map<String, String[]> paramMap = Map.of(
            "name", new String[]{"Alice"},
            "age", new String[]{"25"},
            "city", new String[]{"Beijing"}
        );
        when(request.getParameterMap()).thenReturn(paramMap);

        // 构造QueryParamsMap实例
        QueryParamsMap queryParams = new QueryParamsMap(request);

        // 断言参数提取正确
        assertEquals(3, queryParams.size());
        assertEquals("Alice", queryParams.get("name"));
        assertEquals("25", queryParams.get("age"));
        assertEquals("Beijing", queryParams.get("city"));
    }

    /**
     * 测试正常路径：单参数场景
     */
    @Test
    void testConstructorWithSingleQueryParam() {
        // 模拟请求参数: id=1001
        Map<String, String[]> paramMap = Map.of("id", new String[]{"1001"});
        when(request.getParameterMap()).thenReturn(paramMap);

        QueryParamsMap queryParams = new QueryParamsMap(request);

        assertEquals(1, queryParams.size());
        assertEquals("1001", queryParams.get("id"));
    }

    /**
     * 测试正常路径：无参数场景
     */
    @Test
    void testConstructorWithNoQueryParams() {
        // 模拟空参数
        when(request.getParameterMap()).thenReturn(Map.of());

        QueryParamsMap queryParams = new QueryParamsMap(request);

        assertTrue(queryParams.isEmpty());
    }

    /**
     * 测试正常路径：多值参数场景
     */
    @Test
    void testConstructorWithMultiValueQueryParam() {
        // 模拟多值参数: tags=java&tags=junit&tags=mockito
        Map<String, String[]> paramMap = Map.of("tags", new String[]{"java", "junit", "mockito"});
        when(request.getParameterMap()).thenReturn(paramMap);

        QueryParamsMap queryParams = new QueryParamsMap(request);

        // 假设QueryParamsMap对多值参数返回List
        assertEquals(List.of("java", "junit", "mockito"), queryParams.get("tags"));
    }

    /**
     * 测试异常路径：传入null HttpServletRequest
     */
    @Test
    void testConstructorWithNullServletRequest() {
        // 断言构造时抛出NullPointerException
        assertThrows(NullPointerException.class, () -> new QueryParamsMap(null),
            "构造QueryParamsMap时传入null Request应抛出NPE");
    }
}