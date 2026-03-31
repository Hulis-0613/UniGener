import spark.http.matching.RouteContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingRouteContextjavaRouteContextWithResponseWrapper554d7599Test {

    /**
     * 正常路径测试：传入有效ResponseWrapper，验证设置成功且返回自身实例
     */
    @Test
    void withResponseWrapper_ValidResponseWrapper_SetsWrapperAndReturnsSelf() {
        // 准备测试对象
        RouteContext routeContext = new RouteContext();
        ResponseWrapper mockWrapper = new ResponseWrapper(); // 假设ResponseWrapper为具体类，若为接口可使用Mockito.mock(ResponseWrapper.class)

        // 执行目标方法
        RouteContext resultContext = routeContext.withResponseWrapper(mockWrapper);

        // 验证结果：设置的包装器正确，且返回自身实例（支持链式调用）
        assertSame(mockWrapper, routeContext.getResponseWrapper(), "ResponseWrapper未正确设置");
        assertSame(routeContext, resultContext, "方法应返回自身实例以支持链式调用");
    }

    /**
     * 异常路径测试：传入null参数，验证抛出IllegalArgumentException
     */
    @Test
    void withResponseWrapper_NullWrapper_ThrowsIllegalArgumentException() {
        // 准备测试对象
        RouteContext routeContext = new RouteContext();

        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routeContext.withResponseWrapper(null),
                "传入null时应抛出IllegalArgumentException");

        // 验证异常信息（可选，根据实际参数校验逻辑调整）
        assertTrue(exception.getMessage().contains("responseWrapper must not be null"), 
                  "异常消息应提示参数非空约束");
        
        // 验证未设置null值（若原方法设计允许null则删除此断言）
        assertNull(routeContext.getResponseWrapper(), "null参数不应被设置到实例中");
    }
}