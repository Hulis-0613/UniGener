import spark.Redirect;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRedirectjavaRedirectStatus3bbdae1aTest {

    /**
     * 测试正常路径：构造方法接收有效intValue参数时，对象属性正确初始化
     */
    @Test
    void statusConstructor_WithValidIntValue_InitializesCorrectly() {
        // 测试用例：边界值（0）、正数、最大int值
        int[] validValues = {0, 42, 100, Integer.MAX_VALUE};
        
        for (int value : validValues) {
            Status status = new Status(value);
            assertEquals(value, status.getIntValue(), 
                "构造方法应正确初始化intValue为：" + value);
        }
    }

    /**
     * 测试异常路径：构造方法接收无效intValue（如负数）时，抛出IllegalArgumentException
     */
    @Test
    void statusConstructor_WithInvalidIntValue_ThrowsIllegalArgumentException() {
        // 测试用例：负整数、最小int值
        int[] invalidValues = {-1, -100, Integer.MIN_VALUE};
        
        for (int value : invalidValues) {
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Status(value), 
                "构造方法接收无效值" + value + "时应抛出IllegalArgumentException");
            
            assertTrue(exception.getMessage().contains("intValue"), 
                "异常信息应包含参数校验提示");
        }
    }
}