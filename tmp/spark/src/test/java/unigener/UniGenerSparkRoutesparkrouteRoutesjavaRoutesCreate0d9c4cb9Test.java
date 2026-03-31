import spark.route.Routes;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkRoutesparkrouteRoutesjavaRoutesCreate0d9c4cb9Test {

    /**
     * 正常路径：首次调用create方法，应成功初始化路由配置
     * 验证：路由列表非空且初始化状态为true
     */
    @Test
    void create_WhenFirstInvocation_ShouldInitializeRoutesSuccessfully() {
        // 初始化测试对象
        Routes routes = new Routes();
        
        // 执行目标方法
        routes.create();
        
        // 断言：路由已初始化且列表非空（假设Routes有isInitialized()和getRoutes()方法）
        assertTrue(routes.isInitialized(), "Routes should be marked as initialized after create");
        assertFalse(routes.getRoutes().isEmpty(), "Routes list should not be empty after initialization");
    }

    /**
     * 异常路径：重复调用create方法，应抛出IllegalStateException
     * 验证：第二次调用时抛出预期异常
     */
    @Test
    void create_WhenInvokedTwice_ShouldThrowIllegalStateException() {
        // 初始化测试对象并首次调用create（正常初始化）
        Routes routes = new Routes();
        routes.create();
        
        // 第二次调用create，断言抛出异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> routes.create(),
            "Expected IllegalStateException when create is called multiple times"
        );
        
        // 验证异常消息（可选，根据实际实现调整）
        assertEquals("Routes already initialized", exception.getMessage(), "Exception message mismatch");
    }
}