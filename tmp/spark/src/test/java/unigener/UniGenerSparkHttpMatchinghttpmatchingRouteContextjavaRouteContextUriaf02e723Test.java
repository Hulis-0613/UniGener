import spark.http.matching.RouteContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.when;

// 假设目标方法所在类为RouteHandler，依赖RouteContext
public class UniGenerSparkHttpMatchinghttpmatchingRouteContextjavaRouteContextUriaf02e723Test {

    @Mock // 模拟RouteContext依赖
    private RouteContext routeContext;

    @InjectMocks // 注入模拟的RouteContext到测试目标类
    private RouteHandler routeHandler;

    @BeforeEach
    void setUp() {
        // 初始化Mockito注解
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 正常路径：RouteContext存在且URI不为null时，返回正确URI
     */
    @Test
    void uri_WithValidRouteContext_ReturnsCorrectUri() {
        // 1. 准备测试数据
        String expectedUri = "https://example.com/api/v1/users";
        when(routeContext.getUri()).thenReturn(expectedUri); // 模拟RouteContext返回预期URI

        // 2. 执行目标方法
        String actualUri = routeHandler.uri();

        // 3. 断言结果
        Assertions.assertEquals(expectedUri, actualUri, "返回的URI与预期不符");
    }

    /**
     * 异常路径1：RouteContext存在但URI为null时，返回null
     */
    @Test
    void uri_WithRouteContextUriNull_ReturnsNull() {
        // 1. 准备测试数据：模拟RouteContext返回null
        when(routeContext.getUri()).thenReturn(null);

        // 2. 执行目标方法
        String actualUri = routeHandler.uri();

        // 3. 断言结果为null
        Assertions.assertNull(actualUri, "URI为null时应返回null");
    }

    /**
     * 异常路径2：RouteContext为null时，抛出NullPointerException
     */
    @Test
    void uri_WithNullRouteContext_ThrowsNullPointerException() {
        // 1. 准备测试数据：手动将RouteContext设为null（假设RouteHandler通过构造函数注入依赖）
        routeHandler = new RouteHandler(null); // 假设构造函数接收RouteContext参数

        // 2. 执行目标方法并断言异常
        NullPointerException exception = Assertions.assertThrows(
            NullPointerException.class,
            () -> routeHandler.uri(),
            "RouteContext为null时应抛出NullPointerException"
        );

        // 可选：验证异常信息（如果方法有明确异常信息）
        Assertions.assertTrue(exception.getMessage().contains("RouteContext cannot be null"), 
                            "异常信息不符合预期");
    }
}