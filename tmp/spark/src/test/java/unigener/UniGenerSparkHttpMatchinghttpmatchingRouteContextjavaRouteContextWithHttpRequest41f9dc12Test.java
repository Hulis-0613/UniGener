import spark.http.matching.RouteContext;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRouteContextjavaRouteContextWithHttpRequest41f9dc12Test {

    @Mock
    private HttpServletRequest validRequest; // 模拟有效HttpServletRequest

    /**
     * 正常路径：传入非null的HttpServletRequest，验证参数被正确设置且返回当前实例
     */
    @Test
    void withHttpRequest_ValidRequest_SetsHttpRequestAndReturnsSelf() throws Exception {
        // 准备：创建RouteContext实例
        RouteContext routeContext = new RouteContext();

        // 执行：调用withHttpRequest方法设置有效请求
        RouteContext result = routeContext.withHttpRequest(validRequest);

        // 验证：1. 返回当前实例（支持链式调用）；2. httpRequest字段被正确设置
        assertSame(routeContext, result, "方法应返回当前RouteContext实例");
        
        Field httpRequestField = RouteContext.class.getDeclaredField("httpRequest");
        httpRequestField.setAccessible(true);
        assertSame(validRequest, httpRequestField.get(routeContext), "httpRequest参数未正确设置");
    }

    /**
     * 异常路径：传入null，验证抛出IllegalArgumentException
     */
    @Test
    void withHttpRequest_NullRequest_ThrowsIllegalArgumentException() {
        // 准备：创建RouteContext实例
        RouteContext routeContext = new RouteContext();

        // 执行&验证：调用withHttpRequest(null)时抛出异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> routeContext.withHttpRequest(null),
                "传入null时应抛出IllegalArgumentException");
        
        assertEquals("httpRequest must not be null", exception.getMessage(), "异常消息不符合预期");
    }
}