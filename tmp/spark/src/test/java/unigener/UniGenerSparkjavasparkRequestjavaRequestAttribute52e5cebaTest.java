import spark.Request;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRequestjavaRequestAttribute52e5cebaTest {

    private Request request;

    @BeforeEach
    void setUp() {
        // 初始化Request实例（假设存在无参构造）
        request = new Request();
    }

    // 正常路径：设置字符串类型属性
    @Test
    void attribute_WithValidStringAttribute_SetsAttributeSuccessfully() {
        // 执行目标方法
        Request result = request.attribute("username", "testUser123");

        // 验证：链式调用返回自身，且属性值正确
        assertSame(request, result, "Method should return the same Request instance for chaining");
        assertEquals("testUser123", request.getAttribute("username"), "String attribute value mismatch");
    }

    // 正常路径：设置数字类型属性
    @Test
    void attribute_WithValidNumberAttribute_SetsAttributeSuccessfully() {
        Request result = request.attribute("age", 28);

        assertSame(request, result);
        assertEquals(28, request.getAttribute("age"), "Number attribute value mismatch");
    }

    // 正常路径：设置布尔类型属性
    @Test
    void attribute_WithValidBooleanAttribute_SetsAttributeSuccessfully() {
        Request result = request.attribute("isVip", true);

        assertSame(request, result);
        assertTrue((Boolean) request.getAttribute("isVip"), "Boolean attribute value mismatch");
    }

    // 正常路径：设置null值属性
    @Test
    void attribute_WithNullValue_SetsAttributeSuccessfully() {
        Request result = request.attribute("email", null);

        assertSame(request, result);
        assertNull(request.getAttribute("email"), "Null value attribute should be set to null");
    }

    // 异常路径：属性名为null
    @Test
    void attribute_WithNullAttribute_ThrowsIllegalArgumentException() {
        // 验证抛出异常及消息
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> request.attribute(null, "invalid"), 
                "Null attribute name should throw IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("Attribute name cannot be null or empty"), 
                "Exception message mismatch for null attribute");
    }

    // 异常路径：属性名为空字符串
    @Test
    void attribute_WithEmptyAttribute_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> request.attribute("", "invalid"), 
                "Empty attribute name should throw IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("Attribute name cannot be null or empty"), 
                "Exception message mismatch for empty attribute");
    }
}