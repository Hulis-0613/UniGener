import spark.http.matching.RouteContext;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingRouteContextjavaRouteContextWithAcceptType5000624aTest {

    /**
     * 测试正常路径：设置非空非空字符串的acceptType
     */
    @Test
    void withAcceptType_ValidNonEmptyAcceptType_SetsCorrectly() {
        // Arrange
        RouteContext context = new RouteContext();
        String expectedAcceptType = "application/json";

        // Act
        RouteContext resultContext = context.withAcceptType(expectedAcceptType);

        // Assert
        // 验证返回自身（支持链式调用）
        assertSame(context, resultContext, "withAcceptType should return the same RouteContext instance");
        // 验证acceptType被正确设置
        assertEquals(expectedAcceptType, context.getAcceptType(), "AcceptType was not set to the expected value");
    }

    /**
     * 测试正常路径：设置空字符串的acceptType（假设允许空字符串作为合法值）
     */
    @Test
    void withAcceptType_EmptyAcceptType_SetsCorrectly() {
        // Arrange
        RouteContext context = new RouteContext();
        String expectedAcceptType = "";

        // Act
        RouteContext resultContext = context.withAcceptType(expectedAcceptType);

        // Assert
        assertSame(context, resultContext, "withAcceptType should return the same RouteContext instance");
        assertEquals(expectedAcceptType, context.getAcceptType(), "Empty acceptType was not set correctly");
    }

    /**
     * 测试异常路径：传入null的acceptType（假设方法不允许null，需抛出异常）
     */
    @Test
    void withAcceptType_NullAcceptType_ThrowsIllegalArgumentException() {
        // Arrange
        RouteContext context = new RouteContext();

        // Act & Assert：验证传入null时抛出IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> context.withAcceptType(null),
                "Expected IllegalArgumentException when acceptType is null");
        
        // 验证异常消息包含预期提示（可选，根据实际方法实现调整）
        assertTrue(exception.getMessage().contains("acceptType must not be null"), 
                "Exception message does not contain expected hint");
    }
}