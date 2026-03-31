import spark.HaltException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkHaltExceptionjavaHaltExceptionStatusCodefb772378Test {

    // 正常路径：验证自定义状态码的返回值
    @Test
    void statusCode_WithCustomStatusCode_ReturnsGivenValue() {
        int expectedCode = 404;
        HaltException exception = new HaltException(expectedCode);
        int actualCode = exception.statusCode();
        assertEquals(expectedCode, actualCode, "自定义状态码返回值不匹配");
    }

    // 正常路径：验证默认构造函数的默认状态码
    @Test
    void statusCode_WithDefaultConstructor_ReturnsDefaultValue() {
        int expectedDefaultCode = 500; // 假设默认状态码为500
        HaltException exception = new HaltException();
        int actualCode = exception.statusCode();
        assertEquals(expectedDefaultCode, actualCode, "默认状态码返回值不匹配");
    }

    // 异常路径：验证构造函数对无效状态码的校验（如负数）
    @Test
    void constructor_WithInvalidStatusCode_ThrowsIllegalArgumentException() {
        int invalidCode = -1; // 无效状态码（假设状态码需为非负整数）
        assertThrows(IllegalArgumentException.class, 
            () -> new HaltException(invalidCode), 
            "无效状态码未触发参数校验异常");
    }
}