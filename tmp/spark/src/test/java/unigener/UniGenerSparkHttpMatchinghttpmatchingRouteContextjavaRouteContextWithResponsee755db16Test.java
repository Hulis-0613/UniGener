import spark.http.matching.RouteContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingRouteContextjavaRouteContextWithResponsee755db16Test {

    /**
     * 测试正常路径：传入非null Response时，正确设置响应并返回当前RouteContext实例
     */
    @Test
    void withResponse_NormalCase_SetsResponseAndReturnsSelf() {
        // 准备测试数据
        RouteContext routeContext = new RouteContext();
        Response testResponse = new Response(); // 假设Response有可访问的构造方法

        // 执行目标方法
        RouteContext result = routeContext.withResponse(testResponse);

        // 断言：返回的实例是原实例（链式调用支持）
        assertSame(routeContext, result, "withResponse应返回调用者自身实例");
        // 断言：响应已正确设置
        assertEquals(testResponse, routeContext.getResponse(), "设置的响应与输入响应不一致");
    }

    /**
     * 测试异常路径：传入null Response时，抛出NullPointerException
     */
    @Test
    void withResponse_NullResponse_ThrowsNullPointerException() {
        // 准备测试数据
        RouteContext routeContext = new RouteContext();

        // 执行目标方法并断言异常
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> routeContext.withResponse(null),
                "传入null响应时应抛出NullPointerException");
        
        // 可选：验证异常消息（若方法实现中定义了消息）
        assertTrue(exception.getMessage().contains("response must not be null"), 
                "异常消息应提示响应不可为null");
    }
}