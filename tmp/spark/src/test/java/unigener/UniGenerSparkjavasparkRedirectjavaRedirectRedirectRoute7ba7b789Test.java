import spark.Redirect;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.http.ResponseEntity;
import java.net.URI;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标方法所在类为RouteRedirector
public class UniGenerSparkjavasparkRedirectjavaRedirectRedirectRoute7ba7b789Test {

    private final RouteRedirector routeRedirector = new RouteRedirector();

    // -------------------------- 正常路径测试 --------------------------
    @Test
    @DisplayName("正常参数-302临时重定向")
    void redirectRoute_ValidParams_302Redirect_ReturnsCorrectResponse() {
        // 准备参数
        String fromPath = "/old-path";
        String toPath = "/new-path";
        int status = 302;

        // 执行方法
        ResponseEntity<Void> response = routeRedirector.redirectRoute(fromPath, toPath, status);

        // 断言结果
        assertNotNull(response, "响应实体不应为null");
        assertEquals(status, response.getStatusCodeValue(), "状态码不匹配");
        assertEquals(URI.create(toPath), response.getHeaders().getLocation(), "重定向目标路径不匹配");
    }

    @Test
    @DisplayName("正常参数-301永久重定向")
    void redirectRoute_ValidParams_301Redirect_ReturnsCorrectResponse() {
        // 准备参数
        String fromPath = "/legacy-page";
        String toPath = "/current-page";
        int status = 301;

        // 执行方法
        ResponseEntity<Void> response = routeRedirector.redirectRoute(fromPath, toPath, status);

        // 断言结果
        assertNotNull(response, "响应实体不应为null");
        assertEquals(status, response.getStatusCodeValue(), "状态码不匹配");
        assertEquals(URI.create(toPath), response.getHeaders().getLocation(), "重定向目标路径不匹配");
    }

    // -------------------------- 异常路径测试 --------------------------
    @Test
    @DisplayName("fromPath为null-抛出IllegalArgumentException")
    void redirectRoute_FromPathNull_ThrowsIllegalArgumentException() {
        // 准备参数（fromPath为null）
        String fromPath = null;
        String toPath = "/valid-path";
        int status = 302;

        // 执行并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routeRedirector.redirectRoute(fromPath, toPath, status),
                "当fromPath为null时应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("fromPath"), "异常消息应包含参数名'fromPath'");
    }

    @Test
    @DisplayName("fromPath为空字符串-抛出IllegalArgumentException")
    void redirectRoute_FromPathEmpty_ThrowsIllegalArgumentException() {
        // 准备参数（fromPath为空字符串）
        String fromPath = "";
        String toPath = "/valid-path";
        int status = 302;

        // 执行并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routeRedirector.redirectRoute(fromPath, toPath, status),
                "当fromPath为空字符串时应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("fromPath"), "异常消息应包含参数名'fromPath'");
    }

    @Test
    @DisplayName("toPath为null-抛出IllegalArgumentException")
    void redirectRoute_ToPathNull_ThrowsIllegalArgumentException() {
        // 准备参数（toPath为null）
        String fromPath = "/valid-path";
        String toPath = null;
        int status = 302;

        // 执行并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routeRedirector.redirectRoute(fromPath, toPath, status),
                "当toPath为null时应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("toPath"), "异常消息应包含参数名'toPath'");
    }

    @Test
    @DisplayName("toPath为空字符串-抛出IllegalArgumentException")
    void redirectRoute_ToPathEmpty_ThrowsIllegalArgumentException() {
        // 准备参数（toPath为空字符串）
        String fromPath = "/valid-path";
        String toPath = "";
        int status = 302;

        // 执行并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routeRedirector.redirectRoute(fromPath, toPath, status),
                "当toPath为空字符串时应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("toPath"), "异常消息应包含参数名'toPath'");
    }

    @ParameterizedTest
    @ValueSource(ints = {200, 404, 500, 300, 310}) // 非3xx或无效3xx状态码
    @DisplayName("状态码非有效重定向码-抛出IllegalArgumentException")
    void redirectRoute_InvalidStatus_ThrowsIllegalArgumentException(int invalidStatus) {
        // 准备参数（状态码无效）
        String fromPath = "/valid-path";
        String toPath = "/valid-target";

        // 执行并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routeRedirector.redirectRoute(fromPath, toPath, invalidStatus),
                "当状态码为" + invalidStatus + "时应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("status"), "异常消息应包含参数名'status'");
    }
}