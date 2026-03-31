import spark.utils.that;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设notNull方法所在类为com.example.ValidationUtils
import com.example.ValidationUtils;

public class UniGenerSparkUtilssparkutilsAssertjavathatNotNull18c7336fTest {

    /**
     * 测试正常路径：输入非null对象时，不抛异常且返回原对象
     */
    @Test
    void notNull_WithNonNullObject_ReturnsObject() {
        // 准备测试数据
        Object testObj = new Object();
        String errorMessage = "自定义异常消息";

        // 执行目标方法
        Object result = ValidationUtils.notNull(testObj, errorMessage);

        // 断言：返回对象与输入对象一致（验证方法返回逻辑）
        assertSame(testObj, result);
    }

    /**
     * 测试异常路径：输入null对象时，抛出包含指定消息的NullPointerException
     */
    @Test
    void notNull_WithNullObject_ThrowsExceptionWithMessage() {
        // 准备测试数据
        String expectedMessage = "对象不能为空";

        // 执行目标方法并捕获异常
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> ValidationUtils.notNull(null, expectedMessage));

        // 断言：异常类型正确且消息匹配
        assertEquals(expectedMessage, exception.getMessage());
    }
}