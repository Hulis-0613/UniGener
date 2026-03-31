import spark.http.matching.RouteContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingRouteContextjavaRouteContextWithUri3d5ecb9eTest {

    /**
     * 测试正常路径：传入非空有效URI，验证URI设置成功且返回当前实例
     */
    @Test
    void withUri_ValidNonEmptyUri_SetsUriAndReturnsSelf() {
        // 准备：创建RouteContext实例，定义预期URI
        RouteContext context = new RouteContext();
        String expectedUri = "https://example.com/api/v1";

        // 执行：调用withUri方法设置URI
        RouteContext result = context.withUri(expectedUri);

        // 断言：返回实例为当前对象，且URI设置正确
        assertSame(context, result, "withUri应返回调用实例本身");
        assertEquals(expectedUri, context.getUri(), "URI未正确设置");
    }

    /**
     * 测试正常路径：传入空字符串URI，验证URI设置成功且返回当前实例
     */
    @Test
    void withUri_EmptyUri_SetsUriAndReturnsSelf() {
        // 准备：创建RouteContext实例，定义空字符串URI
        RouteContext context = new RouteContext();
        String expectedUri = "";

        // 执行：调用withUri方法设置空字符串URI
        RouteContext result = context.withUri(expectedUri);

        // 断言：返回实例为当前对象，且空字符串URI设置正确
        assertSame(context, result, "withUri应返回调用实例本身");
        assertEquals(expectedUri, context.getUri(), "空字符串URI未正确设置");
    }

    /**
     * 测试异常路径：传入null URI，验证抛出IllegalArgumentException
     */
    @Test
    void withUri_NullUri_ThrowsIllegalArgumentException() {
        // 准备：创建RouteContext实例
        RouteContext context = new RouteContext();

        // 执行并断言：调用withUri(null)时抛出预期异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> context.withUri(null),
                "传入null URI时应抛出IllegalArgumentException");
        
        // 验证异常消息（根据实际业务逻辑调整消息内容）
        assertTrue(exception.getMessage().contains("URI不能为null"), "异常消息不符合预期");
    }
}