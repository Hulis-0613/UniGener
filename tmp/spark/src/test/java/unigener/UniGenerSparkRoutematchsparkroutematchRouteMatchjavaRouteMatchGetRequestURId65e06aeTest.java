import spark.routematch.RouteMatch;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkRoutematchsparkroutematchRouteMatchjavaRouteMatchGetRequestURId65e06aeTest {

    /**
     * 正常路径：已设置请求URI时，返回正确的URI字符串
     */
    @Test
    void getRequestURI_WithSetUri_ReturnsCorrectUri() {
        // 准备：创建RouteMatch实例并设置URI
        RouteMatch routeMatch = new RouteMatch();
        String expectedUri = "/api/v1/orders/123";
        routeMatch.setRequestURI(expectedUri); // 假设存在设置URI的setter方法

        // 执行：调用目标方法
        String actualUri = routeMatch.getRequestURI();

        // 断言：返回的URI与预期一致
        assertEquals(expectedUri, actualUri, "获取的请求URI与设置值不匹配");
    }

    /**
     * 异常路径：未设置请求URI时，抛出IllegalStateException
     */
    @Test
    void getRequestURI_WithoutSetUri_ThrowsIllegalStateException() {
        // 准备：创建RouteMatch实例但不设置URI
        RouteMatch routeMatch = new RouteMatch();

        // 执行并断言：调用方法时抛出异常，且异常信息符合预期
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                routeMatch::getRequestURI,
                "未设置URI时，应抛出IllegalStateException");
        assertTrue(exception.getMessage().contains("Request URI not set"), 
                "异常信息应提示URI未设置");
    }
}