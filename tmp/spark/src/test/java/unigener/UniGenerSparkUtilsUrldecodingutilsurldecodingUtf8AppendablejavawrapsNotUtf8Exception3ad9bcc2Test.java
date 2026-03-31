import spark.utils.urldecoding.wraps;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilsUrldecodingutilsurldecodingUtf8AppendablejavawrapsNotUtf8Exception3ad9bcc2Test {

    /**
     * 测试正常路径：传入非null的reason参数，验证异常消息正确设置
     */
    @Test
    void constructor_withNonNulReason_shouldSetMessageCorrectly() {
        // 准备测试数据
        String expectedReason = "Invalid UTF-8 byte sequence detected";
        
        // 执行构造方法
        NotUtf8Exception exception = new NotUtf8Exception(expectedReason);
        
        // 验证异常消息与输入reason一致
        assertEquals(expectedReason, exception.getMessage());
    }

    /**
     * 测试边界路径：传入null的reason参数，验证异常消息为null
     * （注：标准异常构造逻辑允许null reason，此时getMessage()返回null）
     */
    @Test
    void constructor_withNullReason_shouldSetMessageToNull() {
        // 执行构造方法（传入null）
        NotUtf8Exception exception = new NotUtf8Exception(null);
        
        // 验证异常消息为null
        assertNull(exception.getMessage());
    }
}