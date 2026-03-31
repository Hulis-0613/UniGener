import spark.RouteImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;

// 假设Route是一个接口或类，此处使用Mockito模拟
interface Route {}

// 假设RouteImpl的定义（仅为测试上下文，实际可能由业务代码提供）
public class UniGenerSparkjavasparkRouteImpljavaRouteImplCreate57ef1988Test {
    private final String path;
    private final Route route;

    private RouteImpl(String path, Route route) {
        this.path = path;
        this.route = route;
    }

    // 目标方法：创建RouteImpl实例
    public static RouteImpl create(String path, Route route) {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("path must not be null or empty");
        }
        if (route == null) {
            throw new IllegalArgumentException("route must not be null");
        }
        return new RouteImpl(path, route);
    }

    // Getter方法（用于测试断言）
    public String getPath() { return path; }
    public Route getRoute() { return route; }
}

// JUnit5测试类
class RouteImplCreateTest {

    // 正常路径：参数有效，成功创建实例
    @Test
    void create_WithValidPathAndRoute_ReturnsRouteImpl() {
        // 准备测试数据
        String validPath = "/api/v1/users";
        Route mockRoute = Mockito.mock(Route.class); // 模拟Route对象

        // 执行目标方法
        RouteImpl result = RouteImpl.create(validPath, mockRoute);

        // 断言：实例非空，且属性正确
        assertNotNull(result, "创建的RouteImpl实例不应为null");
        assertEquals(validPath, result.getPath(), "path属性未正确设置");
        assertSame(mockRoute, result.getRoute(), "route属性未正确关联");
    }

    // 异常路径：path为null
    @Test
    void create_WithNullPath_ThrowsIllegalArgumentException() {
        // 准备测试数据（path为null，route有效）
        String nullPath = null;
        Route mockRoute = Mockito.mock(Route.class);

        // 执行目标方法并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> RouteImpl.create(nullPath, mockRoute),
                "当path为null时，应抛出IllegalArgumentException");

        // 断言异常消息（可选，根据实际业务逻辑调整）
        assertTrue(exception.getMessage().contains("path must not be null or empty"),
                "异常消息应提示path不为null或空");
    }

    // 异常路径：path为空字符串
    @Test
    void create_WithEmptyPath_ThrowsIllegalArgumentException() {
        // 准备测试数据（path为空字符串，route有效）
        String emptyPath = "";
        Route mockRoute = Mockito.mock(Route.class);

        // 执行目标方法并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> RouteImpl.create(emptyPath, mockRoute),
                "当path为空字符串时，应抛出IllegalArgumentException");

        // 断言异常消息
        assertTrue(exception.getMessage().contains("path must not be null or empty"),
                "异常消息应提示path不为null或空");
    }

    // 异常路径：route为null
    @Test
    void create_WithNullRoute_ThrowsIllegalArgumentException() {
        // 准备测试数据（path有效，route为null）
        String validPath = "/api/v1/orders";
        Route nullRoute = null;

        // 执行目标方法并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> RouteImpl.create(validPath, nullRoute),
                "当route为null时，应抛出IllegalArgumentException");

        // 断言异常消息
        assertTrue(exception.getMessage().contains("route must not be null"),
                "异常消息应提示route不为null");
    }
}