import spark.route.SimpleRouteMatcher;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

// 假设路由实体类
public class UniGenerSparkRoutesparkrouteSimpleRouteMatcherjavaSimpleRouteMatcherClearRoutese719021bTest {
    private String id;
    public Route(String id) { this.id = id; }
    // 省略getter/setter
}

// 假设目标类（含clearRoutes方法）
class RouteManager {
    private List<Route> routeStore; // 内部路由存储

    public RouteManager() {
        this.routeStore = new java.util.ArrayList<>(); // 初始化存储
    }

    public void addRoute(Route route) {
        routeStore.add(route);
    }

    public List<Route> getRoutes() {
        return new java.util.ArrayList<>(routeStore); // 返回副本避免外部修改
    }

    public void clearRoutes() {
        if (routeStore == null) {
            throw new NullPointerException("Route store is not initialized");
        }
        routeStore.clear();
    }
}

// JUnit5测试类
class RouteManagerTest {

    /**
     * 正常路径：路由存在时清除，验证路由被清空
     */
    @Test
    void clearRoutes_WithExistingRoutes_ShouldClearAllRoutes() {
        // Arrange
        RouteManager manager = new RouteManager();
        Route route1 = new Route("route-1");
        Route route2 = new Route("route-2");
        manager.addRoute(route1);
        manager.addRoute(route2);

        // Act
        manager.clearRoutes();

        // Assert
        assertTrue(manager.getRoutes().isEmpty(), "Routes should be cleared");
    }

    /**
     * 正常路径：路由为空时清除，验证无异常且仍为空
     */
    @Test
    void clearRoutes_WithNoRoutes_ShouldDoNothing() {
        // Arrange
        RouteManager manager = new RouteManager(); // 初始路由为空

        // Act
        manager.clearRoutes(); // 执行清除（预期无副作用）

        // Assert
        assertTrue(manager.getRoutes().isEmpty(), "Routes should remain empty");
    }

    /**
     * 异常路径：路由存储未初始化（null）时调用，验证抛出NPE
     */
    @Test
    void clearRoutes_WithUninitializedRouteStore_ShouldThrowNullPointerException() throws Exception {
        // Arrange：通过反射将内部routeStore设为null（模拟未初始化场景）
        RouteManager manager = new RouteManager();
        Field routeStoreField = RouteManager.class.getDeclaredField("routeStore");
        routeStoreField.setAccessible(true);
        routeStoreField.set(manager, null); // 破坏内部状态

        // Act & Assert：验证抛出NPE且消息正确
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            manager::clearRoutes,
            "Expected NullPointerException when route store is uninitialized"
        );
        assertEquals("Route store is not initialized", exception.getMessage());
    }
}