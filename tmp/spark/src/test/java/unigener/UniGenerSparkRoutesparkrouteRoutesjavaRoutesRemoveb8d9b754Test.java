import spark.route.Routes;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpMethod;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkRoutesparkrouteRoutesjavaRoutesRemoveb8d9b754Test {

    private final RouteManager routeManager = new RouteManager();

    /**
     * 测试正常场景：移除已存在的路由，验证路由被成功删除
     */
    @Test
    void remove_ExistingRoute_SuccessfullyRemoved() {
        // Arrange：添加测试路由
        String testPath = "/api/users";
        HttpMethod testMethod = HttpMethod.GET;
        routeManager.addRoute(testPath, testMethod);
        assertTrue(routeManager.hasRoute(testPath, testMethod), "路由添加后应存在");

        // Act：执行移除操作
        routeManager.remove(testPath, testMethod);

        // Assert：验证路由已被移除
        assertFalse(routeManager.hasRoute(testPath, testMethod), "移除后路由应不存在");
    }

    /**
     * 测试异常场景：移除不存在的路由，验证无副作用
     */
    @Test
    void remove_NonExistingRoute_NoSideEffect() {
        // Arrange：准备不存在的路由信息
        String nonExistentPath = "/invalid/path";
        HttpMethod nonExistentMethod = HttpMethod.POST;
        assertFalse(routeManager.hasRoute(nonExistentPath, nonExistentMethod), "移除前路由应不存在");

        // Act：执行移除操作（预期无异常）
        routeManager.remove(nonExistentPath, nonExistentMethod);

        // Assert：验证状态无变化
        assertFalse(routeManager.hasRoute(nonExistentPath, nonExistentMethod), "移除不存在的路由后状态应不变");
    }

    /**
     * 测试边界场景：path为null，验证抛出参数非法异常
     */
    @Test
    void remove_NullPath_ThrowsIllegalArgumentException() {
        // Arrange：path为null，method合法
        String nullPath = null;
        HttpMethod validMethod = HttpMethod.PUT;

        // Act & Assert：验证抛出异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routeManager.remove(nullPath, validMethod),
                "path为null时应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("path must not be null"), "异常信息应提示path非空");
    }

    /**
     * 测试边界场景：httpMethod为null，验证抛出参数非法异常
     */
    @Test
    void remove_NullHttpMethod_ThrowsIllegalArgumentException() {
        // Arrange：path合法，method为null
        String validPath = "/api/orders";
        HttpMethod nullMethod = null;

        // Act & Assert：验证抛出异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routeManager.remove(validPath, nullMethod),
                "httpMethod为null时应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("httpMethod must not be null"), "异常信息应提示method非空");
    }

    /**
     * 测试边界场景：路径存在但HTTP方法不匹配，验证原路由不受影响
     */
    @Test
    void remove_ExistingPathWithMismatchedMethod_RouteRemains() {
        // Arrange：添加GET路由，准备用POST方法移除
        String sharedPath = "/api/products";
        HttpMethod existingMethod = HttpMethod.GET;
        HttpMethod removeMethod = HttpMethod.POST;
        routeManager.addRoute(sharedPath, existingMethod);
        assertTrue(routeManager.hasRoute(sharedPath, existingMethod), "原路由添加后应存在");

        // Act：用不匹配的方法移除
        routeManager.remove(sharedPath, removeMethod);

        // Assert：原路由仍存在，未被误删
        assertTrue(routeManager.hasRoute(sharedPath, existingMethod), "方法不匹配时原路由应保留");
    }
}