import spark.route.SimpleRouteMatcher;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设路由存储在静态列表中，需在测试前重置状态
import static com.example.RouteManager.routes; // 示例路由存储类

public class UniGenerSparkRoutesparkrouteSimpleRouteMatcherjavaSimpleRouteMatcherParseValidateAddRoute2b57bafbTest {

    // 测试前清空路由列表，确保测试隔离
    @BeforeEach
    void setUp() {
        routes.clear();
    }

    // -------------- 正常路径测试 --------------
    @Test
    void parseValidateAddRoute_WithValidParams_ReturnsTrueAndAddsRoute() {
        // 准备：有效参数（route格式"path:method"，acceptType支持，target合法URL）
        String validRoute = "/api/user:GET";
        String supportedAcceptType = "json";
        String validTarget = "http://service-user:8080";

        // 执行
        boolean result = parseValidateAddRoute(validRoute, supportedAcceptType, validTarget);

        // 断言：返回true，且路由列表包含新添加的路由
        assertTrue(result);
        assertEquals(1, routes.size());
        assertTrue(routes.stream()
                .anyMatch(r -> r.getRoute().equals(validRoute) 
                        && r.getAcceptType().equals(supportedAcceptType) 
                        && r.getTarget().equals(validTarget)));
    }

    // -------------- 异常路径测试 --------------
    @Test
    void parseValidateAddRoute_WithNullRoute_ThrowsIllegalArgumentException() {
        // 准备：route为null，其他参数合法
        String nullRoute = null;
        String acceptType = "json";
        String target = "http://service:8080";

        // 执行 & 断言：抛出参数异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> parseValidateAddRoute(nullRoute, acceptType, target));
        assertTrue(exception.getMessage().contains("route must not be null"));
        assertTrue(routes.isEmpty()); // 路由未添加
    }

    @Test
    void parseValidateAddRoute_WithEmptyRoute_ReturnsFalse() {
        // 准备：route为空字符串
        String emptyRoute = "";
        String acceptType = "xml";
        String target = "http://service:8080";

        // 执行
        boolean result = parseValidateAddRoute(emptyRoute, acceptType, target);

        // 断言：返回false，路由未添加
        assertFalse(result);
        assertTrue(routes.isEmpty());
    }

    @Test
    void parseValidateAddRoute_WithInvalidRouteFormat_ReturnsFalse() {
        // 准备：route格式错误（缺少冒号分隔path和method）
        String invalidRoute = "/api/userGET"; // 正确格式应为"/api/user:GET"
        String acceptType = "json";
        String target = "http://service:8080";

        // 执行
        boolean result = parseValidateAddRoute(invalidRoute, acceptType, target);

        // 断言：返回false，路由未添加
        assertFalse(result);
        assertTrue(routes.isEmpty());
    }

    @Test
    void parseValidateAddRoute_WithUnsupportedAcceptType_ReturnsFalse() {
        // 准备：acceptType不支持（假设仅支持"json"、"xml"）
        String validRoute = "/api/order:POST";
        String unsupportedAcceptType = "text/plain";
        String target = "http://service-order:8080";

        // 执行
        boolean result = parseValidateAddRoute(validRoute, unsupportedAcceptType, target);

        // 断言：返回false，路由未添加
        assertFalse(result);
        assertTrue(routes.isEmpty());
    }

    @Test
    void parseValidateAddRoute_WithNullTarget_ReturnsFalse() {
        // 准备：target为null，其他参数合法
        String validRoute = "/api/payment:PUT";
        String acceptType = "xml";
        String nullTarget = null;

        // 执行
        boolean result = parseValidateAddRoute(validRoute, acceptType, nullTarget);

        // 断言：返回false，路由未添加
        assertFalse(result);
        assertTrue(routes.isEmpty());
    }

    @Test
    void parseValidateAddRoute_WithInvalidTargetUrl_ReturnsFalse() {
        // 准备：target不是合法URL（缺少协议）
        String validRoute = "/api/log:GET";
        String acceptType = "json";
        String invalidTarget = "service-log:8080"; // 正确应为"http://service-log:8080"

        // 执行
        boolean result = parseValidateAddRoute(validRoute, acceptType, invalidTarget);

        // 断言：返回false，路由未添加
        assertFalse(result);
        assertTrue(routes.isEmpty());
    }
}