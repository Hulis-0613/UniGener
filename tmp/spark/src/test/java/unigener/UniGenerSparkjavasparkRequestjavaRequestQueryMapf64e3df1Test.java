import spark.Request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRequestjavaRequestQueryMapf64e3df1Test {

    /**
     * 正常路径：当Request内部已初始化QueryParamsMap时，返回非null的QueryParamsMap对象
     */
    @Test
    void queryMap_WithInitializedMap_ReturnsNonNullMap() {
        // 准备：创建Request实例并初始化QueryParamsMap
        Request request = new Request();
        QueryParamsMap expectedMap = new QueryParamsMap();
        request.setQueryParamsMap(expectedMap); // 假设Request类有设置QueryParamsMap的方法

        // 执行：调用queryMap()方法
        QueryParamsMap actualMap = request.queryMap();

        // 断言：返回的对象非null且与预期实例一致
        assertNotNull(actualMap, "QueryParamsMap should not be null");
        assertSame(expectedMap, actualMap, "Returned map should be the initialized instance");
    }

    /**
     * 异常路径：当Request内部未初始化QueryParamsMap时，抛出IllegalStateException
     */
    @Test
    void queryMap_WithoutInitializedMap_ThrowsIllegalStateException() {
        // 准备：创建Request实例但不初始化QueryParamsMap（默认null）
        Request request = new Request();

        // 执行&断言：调用queryMap()时抛出异常，验证异常类型和消息
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            request::queryMap,
            "Expected IllegalStateException when QueryParamsMap is not initialized"
        );
        assertEquals("QueryParamsMap has not been initialized", exception.getMessage(), "Exception message mismatch");
    }

    /**
     * 边界路径：当QueryParamsMap为空（已初始化但无数据）时，返回空Map
     */
    @Test
    void queryMap_WithEmptyInitializedMap_ReturnsEmptyMap() {
        // 准备：创建Request实例并初始化空的QueryParamsMap
        Request request = new Request();
        QueryParamsMap emptyMap = new QueryParamsMap(); // 假设QueryParamsMap默认构造为“空”
        request.setQueryParamsMap(emptyMap);

        // 执行：调用queryMap()方法
        QueryParamsMap actualMap = request.queryMap();

        // 断言：返回空Map（根据QueryParamsMap的isEmpty()方法定义）
        assertNotNull(actualMap, "QueryParamsMap should not be null");
        assertTrue(actualMap.isEmpty(), "Returned map should be empty");
    }
}