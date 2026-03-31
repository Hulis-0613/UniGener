import spark.utils.urldecoding.wraps;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilsUrldecodingutilsurldecodingUtf8AppendablejavawrapsCheckStated26df8edTest {

    /**
     * 测试正常路径：当对象状态有效时，checkState方法执行无异常。
     */
    @Test
    void checkState_WithValidState_DoesNotThrowException() {
        // 初始化wraps实例并设置为有效状态（假设通过构造函数或setter设置状态）
        Wraps wraps = new Wraps();
        wraps.setState(true); // 假设存在设置状态的方法（如setState(boolean)）

        // 验证调用checkState时无异常抛出
        assertDoesNotThrow(wraps::checkState);
    }

    /**
     * 测试异常路径：当对象状态无效时，checkState方法抛出IllegalStateException。
     */
    @Test
    void checkState_WithInvalidState_ThrowsIllegalStateException() {
        // 初始化wraps实例并设置为无效状态
        Wraps wraps = new Wraps();
        wraps.setState(false);

        // 验证调用checkState时抛出预期异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, wraps::checkState);
        // 可选：验证异常消息（若方法定义了具体消息）
        assertEquals("Invalid state", exception.getMessage());
    }
}