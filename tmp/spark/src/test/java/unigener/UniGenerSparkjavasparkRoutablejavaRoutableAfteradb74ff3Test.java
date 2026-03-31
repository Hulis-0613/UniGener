import spark.Routable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.util.List;

// 假设Filter是过滤器接口
interface Filter {}

// 假设Routable类定义（实际测试时需替换为真实类路径）
public class UniGenerSparkjavasparkRoutablejavaRoutableAfteradb74ff3Test {
    // 内部存储路径与过滤器列表的映射（简化实现，仅为测试参考）
    private final java.util.Map<String, List<Filter>> pathFilters = new java.util.HashMap<>();
    
    public void after(String path, Filter filter) {
        if (path == null) throw new IllegalArgumentException("Path must not be null");
        if (filter == null) throw new IllegalArgumentException("Filter must not be null");
        pathFilters.computeIfAbsent(path, k -> new java.util.ArrayList<>()).add(filter);
    }
    
    // 用于测试的过滤器获取方法（实际项目中可能已有类似方法或需通过反射获取）
    public List<Filter> getFilters(String path) {
        return pathFilters.getOrDefault(path, java.util.Collections.emptyList());
    }
}

class RoutableAfterTest {

    @Test
    void testAfter_ValidPathAndFilter_AddsFilterSuccessfully() {
        // 准备
        Routable routable = new Routable();
        Filter testFilter = Mockito.mock(Filter.class);
        String testPath = "/api/users";
        
        // 执行
        routable.after(testPath, testFilter);
        
        // 验证
        List<Filter> filters = routable.getFilters(testPath);
        assertNotNull(filters, "Filters list should not be null");
        assertEquals(1, filters.size(), "Should have one filter for path");
        assertTrue(filters.contains(testFilter), "Filter should be added to path");
    }

    @Test
    void testAfter_SamePathMultipleFilters_AddsAllFilters() {
        // 准备
        Routable routable = new Routable();
        Filter filter1 = Mockito.mock(Filter.class);
        Filter filter2 = Mockito.mock(Filter.class);
        String testPath = "/api/orders";
        
        // 执行
        routable.after(testPath, filter1);
        routable.after(testPath, filter2);
        
        // 验证
        List<Filter> filters = routable.getFilters(testPath);
        assertEquals(2, filters.size(), "Should have two filters for path");
        assertTrue(filters.containsAll(List.of(filter1, filter2)), "All filters should be added");
    }

    @Test
    void testAfter_EmptyPath_AddsFilterSuccessfully() {
        // 准备
        Routable routable = new Routable();
        Filter testFilter = Mockito.mock(Filter.class);
        String emptyPath = "";
        
        // 执行
        routable.after(emptyPath, testFilter);
        
        // 验证
        List<Filter> filters = routable.getFilters(emptyPath);
        assertEquals(1, filters.size(), "Should support empty path");
        assertTrue(filters.contains(testFilter), "Filter should be added to empty path");
    }

    @Test
    void testAfter_NullPath_ThrowsIllegalArgumentException() {
        // 准备
        Routable routable = new Routable();
        Filter testFilter = Mockito.mock(Filter.class);
        
        // 执行 & 验证
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> routable.after(null, testFilter), 
            "Should throw exception for null path");
        assertTrue(exception.getMessage().contains("Path must not be null"), "Exception message mismatch");
    }

    @Test
    void testAfter_NullFilter_ThrowsIllegalArgumentException() {
        // 准备
        Routable routable = new Routable();
        String testPath = "/api/products";
        
        // 执行 & 验证
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> routable.after(testPath, null), 
            "Should throw exception for null filter");
        assertTrue(exception.getMessage().contains("Filter must not be null"), "Exception message mismatch");
    }
}