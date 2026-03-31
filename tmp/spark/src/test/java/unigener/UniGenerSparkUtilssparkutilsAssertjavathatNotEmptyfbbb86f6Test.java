import spark.utils.that;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试工具类中的notEmpty方法，验证数组非空检查逻辑。
 */
public class UniGenerSparkUtilssparkutilsAssertjavathatNotEmptyfbbb86f6Test {

    /**
     * 测试场景：数组为null时，应抛出异常并包含指定错误消息。
     */
    @Test
    void notEmpty_WithNullArray_ThrowsException() {
        // 准备测试数据
        Object[] nullArray = null;
        String errorMessage = "数组不能为null";

        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Utils.notEmpty(nullArray, errorMessage),
                "数组为null时未抛出预期异常");
        
        assertEquals(errorMessage, exception.getMessage(), "异常消息与预期不符");
    }

    /**
     * 测试场景：数组为空数组（长度为0）时，应抛出异常并包含指定错误消息。
     */
    @Test
    void notEmpty_WithEmptyArray_ThrowsException() {
        // 准备测试数据
        Object[] emptyArray = new Object[0];
        String errorMessage = "数组不能为空";

        // 执行测试并验证异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Utils.notEmpty(emptyArray, errorMessage),
                "空数组时未抛出预期异常");
        
        assertEquals(errorMessage, exception.getMessage(), "异常消息与预期不符");
    }

    /**
     * 测试场景：数组非空（长度>0）时，不抛出异常。
     */
    @Test
    void notEmpty_WithNonEmptyArray_DoesNotThrow() {
        // 准备测试数据（包含元素，即使元素为null也视为非空数组）
        Object[] nonEmptyArray = new Object[]{null, "element", 123};
        String errorMessage = "此消息不应被抛出";

        // 执行测试并验证无异常抛出
        assertDoesNotThrow(() -> Utils.notEmpty(nonEmptyArray, errorMessage),
                "非空数组时意外抛出异常");
    }
}