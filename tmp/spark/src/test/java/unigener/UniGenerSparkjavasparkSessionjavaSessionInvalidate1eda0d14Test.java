import spark.Session;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSessionjavaSessionInvalidate1eda0d14Test {

    // 正常路径：首次调用invalidate()使对象失效
    @Test
    void invalidate_WhenObjectValid_ShouldSetToInvalid() {
        // 1. 准备测试对象（初始状态有效）
        ValidatableObject obj = new ValidatableObject();
        assertTrue(obj.isValid(), "对象初始状态应为有效");

        // 2. 执行目标方法
        obj.invalidate();

        // 3. 断言结果：对象状态变为失效
        assertFalse(obj.isValid(), "调用invalidate()后对象应失效");
    }

    // 异常路径：对象已失效时重复调用invalidate()应抛出异常
    @Test
    void invalidate_WhenObjectAlreadyInvalid_ShouldThrowIllegalStateException() {
        // 1. 准备测试对象并使其先失效
        ValidatableObject obj = new ValidatableObject();
        obj.invalidate();
        assertFalse(obj.isValid(), "前置条件：对象已失效");

        // 2. 执行目标方法并断言异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            obj::invalidate,
            "对象已失效时调用invalidate()应抛出IllegalStateException"
        );

        // 3. （可选）验证异常信息
        assertEquals("对象已失效，无法重复调用invalidate()", exception.getMessage());
    }
}