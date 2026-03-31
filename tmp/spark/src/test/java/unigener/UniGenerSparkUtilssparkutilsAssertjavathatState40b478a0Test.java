import spark.utils.that;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标方法所在类为StateValidator（请根据实际类名调整）
public class UniGenerSparkUtilssparkutilsAssertjavathatState40b478a0Test {

    /**
     * 测试正常路径：当boolean表达式为true时，state方法不抛出异常。
     */
    @Test
    void state_ExpressionTrue_DoesNotThrowException() {
        // 执行目标方法（表达式为true）
        assertDoesNotThrow(() -> StateValidator.state(true, "正常情况：表达式为true"));
    }

    /**
     * 测试异常路径：当boolean表达式为false时，state方法抛出包含指定消息的IllegalStateException。
     */
    @Test
    void state_ExpressionFalse_ThrowsIllegalStateExceptionWithMessage() {
        // 定义预期异常消息
        String expectedMessage = "错误：表达式为false";
        
        // 执行目标方法（表达式为false）并捕获异常
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> StateValidator.state(false, expectedMessage));
        
        // 验证异常消息是否符合预期
        assertEquals(expectedMessage, exception.getMessage());
    }
}