import spark.Redirect;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRedirectjavaRedirectIntValueb798f2a7Test {

    /**
     * 测试正常路径：当对象内部存储正整数时，intValue返回正确值
     */
    @Test
    void intValue_WithPositiveValue_ReturnsCorrectInt() {
        // 假设Redirect类有构造方法接收int值初始化内部状态
        Redirect redirect = new Redirect(2023);
        int result = redirect.intValue();
        assertEquals(2023, result, "正整数场景下intValue返回值不匹配");
    }

    /**
     * 测试正常路径：当对象内部存储负整数时，intValue返回正确值
     */
    @Test
    void intValue_WithNegativeValue_ReturnsCorrectInt() {
        Redirect redirect = new Redirect(-123);
        int result = redirect.intValue();
        assertEquals(-123, result, "负整数场景下intValue返回值不匹配");
    }

    /**
     * 测试正常路径：当对象内部存储0时，intValue返回0
     */
    @Test
    void intValue_WithZero_ReturnsZero() {
        Redirect redirect = new Redirect(0);
        int result = redirect.intValue();
        assertEquals(0, result, "零值场景下intValue返回值不匹配");
    }

    /**
     * 测试异常路径：当对象内部值未初始化（如为null）时，intValue抛出NullPointerException
     * （假设内部使用Integer类型存储值，未初始化时为null）
     */
    @Test
    void intValue_WhenInternalValueNull_ThrowsNullPointerException() {
        // 假设存在构造方法允许内部值为null（或默认未初始化）
        Redirect redirect = new Redirect(null);
        NullPointerException exception = assertThrows(NullPointerException.class, 
            redirect::intValue, "内部值为null时未抛出预期的NullPointerException");
        assertTrue(exception.getMessage().contains("Cannot invoke \"Integer.intValue()\" because \"this.value\" is null"),
            "异常信息不符合预期");
    }
}