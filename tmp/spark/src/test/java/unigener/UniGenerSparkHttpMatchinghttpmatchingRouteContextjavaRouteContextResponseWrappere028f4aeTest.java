import spark.http.matching.RouteContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingRouteContextjavaRouteContextResponseWrappere028f4aeTest {

    /**
     * 正常路径测试：当ResponseWrapper已初始化时，成功返回对象
     */
    @Test
    void responseWrapper_NormalPath_ReturnsInitializedWrapper() {
        // Arrange：创建RouteContext实例并初始化ResponseWrapper
        ResponseWrapper expectedWrapper = new ResponseWrapper();
        RouteContext routeContext = new RouteContext();
        // 假设RouteContext通过setter方法设置ResponseWrapper（根据实际类结构调整）
        routeContext.setResponseWrapper(expectedWrapper);

        // Act：调用目标方法
        ResponseWrapper actualWrapper = routeContext.responseWrapper();

        // Assert：验证返回对象非空且与预期实例一致
        assertNotNull(actualWrapper, "ResponseWrapper不应为null");
        assertSame(expectedWrapper, actualWrapper, "返回的ResponseWrapper应与初始化实例一致");
    }

    /**
     * 异常路径测试：当ResponseWrapper未初始化时，抛出预期异常
     */
    @Test
    void responseWrapper_ExceptionPath_ThrowsWhenWrapperNotInitialized() {
        // Arrange：创建RouteContext实例但不初始化ResponseWrapper
        RouteContext routeContext = new RouteContext(); // 假设默认构造函数不初始化ResponseWrapper

        // Act & Assert：验证调用时抛出异常（根据实际异常类型调整，此处以NullPointerException为例）
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            routeContext::responseWrapper,
            "当ResponseWrapper未初始化时，应抛出NullPointerException"
        );
        assertTrue(exception.getMessage().contains("ResponseWrapper未初始化"), "异常信息应包含未初始化提示");
    }
}