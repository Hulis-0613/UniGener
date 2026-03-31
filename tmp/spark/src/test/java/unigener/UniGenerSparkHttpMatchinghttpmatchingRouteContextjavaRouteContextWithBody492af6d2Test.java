import spark.http.matching.RouteContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingRouteContextjavaRouteContextWithBody492af6d2Test {

    /**
     * 测试正常路径：传入非空Body时，正确设置请求体并返回当前RouteContext实例
     */
    @Test
    void withBody_NormalBody_SetsBodyAndReturnsSelf() {
        // Arrange
        RouteContext routeContext = new RouteContext();
        Body testBody = new Body("sample request body"); // 假设Body类支持字符串构造

        // Act
        RouteContext resultContext = routeContext.withBody(testBody);

        // Assert
        // 验证返回的是当前实例（支持链式调用）
        assertSame(routeContext, resultContext, "withBody should return the same RouteContext instance");
        // 验证请求体被正确设置
        assertNotNull(routeContext.getBody(), "Body should not be null after setting");
        assertEquals(testBody, routeContext.getBody(), "Set body does not match input body");
    }

    /**
     * 测试异常路径：传入null Body时，抛出IllegalArgumentException
     */
    @Test
    void withBody_NullBody_ThrowsIllegalArgumentException() {
        // Arrange
        RouteContext routeContext = new RouteContext();

        // Act & Assert：验证抛出指定异常及消息
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routeContext.withBody(null),
                "withBody should throw IllegalArgumentException when body is null");
        assertEquals("Body must not be null", exception.getMessage(), "Exception message does not match expected");
    }
}