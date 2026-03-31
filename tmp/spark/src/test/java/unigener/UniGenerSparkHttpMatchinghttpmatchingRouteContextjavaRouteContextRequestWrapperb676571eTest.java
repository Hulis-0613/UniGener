import spark.http.matching.RouteContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingRouteContextjavaRouteContextRequestWrapperb676571eTest {

    /**
     * 正常路径：当RouteContext中存在RequestWrapper实例时，返回该实例
     */
    @Test
    void requestWrapper_WithExistingRequestWrapper_ReturnsInstance() {
        // 1. 准备测试数据
        RequestWrapper expectedWrapper = new RequestWrapper(); // 假设RequestWrapper有默认构造器
        RouteContext context = new RouteContext();
        // 假设RouteContext通过setter方法设置RequestWrapper（根据实际实现调整注入方式）
        context.setRequestWrapper(expectedWrapper);

        // 2. 执行目标方法
        RequestWrapper actualWrapper = context.requestWrapper();

        // 3. 断言结果（验证返回的是预期实例）
        assertSame(expectedWrapper, actualWrapper, "返回的RequestWrapper实例与预期不一致");
    }

    /**
     * 异常路径：当RouteContext中不存在RequestWrapper实例时，抛出IllegalStateException
     */
    @Test
    void requestWrapper_WithoutRequestWrapper_ThrowsIllegalStateException() {
        // 1. 准备测试数据（不设置RequestWrapper）
        RouteContext context = new RouteContext();

        // 2. 执行目标方法并断言异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            context::requestWrapper,
            "未设置RequestWrapper时，应抛出IllegalStateException"
        );

        // 3. 验证异常信息（可选，根据实际实现补充）
        assertTrue(exception.getMessage().contains("RequestWrapper not found"), "异常信息不符合预期");
    }
}