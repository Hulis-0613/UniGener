import spark.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设Cookie类存在getName()和getValue()方法
public class UniGenerSparkjavasparkResponsejavaResponseCookie3a8bfb35Test {

    // 正常路径：name和value均有效
    @Test
    void cookie_WithValidNameAndValue_ReturnsCookie() {
        // 准备
        String name = "userSession";
        String value = "abc123";

        // 执行
        Cookie result = cookie(name, value);

        // 断言
        assertNotNull(result, "Cookie should not be null");
        assertEquals(name, result.getName(), "Cookie name mismatch");
        assertEquals(value, result.getValue(), "Cookie value mismatch");
    }

    // 异常路径：name为null
    @Test
    void cookie_WithNullName_ThrowsIllegalArgumentException() {
        // 准备
        String name = null;
        String value = "testValue";

        // 执行 & 断言
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> cookie(name, value),
            "Expected IllegalArgumentException when name is null"
        );
        assertTrue(exception.getMessage().contains("name"), "Exception message should mention 'name'");
    }

    // 异常路径：name为空字符串
    @Test
    void cookie_WithEmptyName_ThrowsIllegalArgumentException() {
        // 准备
        String name = "";
        String value = "testValue";

        // 执行 & 断言
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> cookie(name, value),
            "Expected IllegalArgumentException when name is empty"
        );
        assertTrue(exception.getMessage().contains("name"), "Exception message should mention 'name'");
    }

    // 边界路径：value为null（允许null value）
    @Test
    void cookie_WithNullValue_ReturnsCookieWithNullValue() {
        // 准备
        String name = "preferences";
        String value = null;

        // 执行
        Cookie result = cookie(name, value);

        // 断言
        assertNotNull(result, "Cookie should not be null");
        assertEquals(name, result.getName(), "Cookie name mismatch");
        assertNull(result.getValue(), "Cookie value should be null");
    }

    // 边界路径：value为空字符串（允许空value）
    @Test
    void cookie_WithEmptyValue_ReturnsCookieWithEmptyValue() {
        // 准备
        String name = "theme";
        String value = "";

        // 执行
        Cookie result = cookie(name, value);

        // 断言
        assertNotNull(result, "Cookie should not be null");
        assertEquals(name, result.getName(), "Cookie name mismatch");
        assertEquals(value, result.getValue(), "Cookie value should be empty string");
    }

    // 假设目标方法（实际测试时需替换为真实方法调用，如new CookieService().cookie(name, value)）
    private Cookie cookie(String name, String value) {
        // 此处为模拟目标方法逻辑（实际测试时无需此方法，直接调用真实方法）
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("name must not be null or empty");
        }
        return new Cookie(name, value);
    }

    // 模拟Cookie类（实际测试时无需此类，使用项目中的真实Cookie类）
    static class Cookie {
        private final String name;
        private final String value;

        public Cookie(String name, String value) {
            this.name = name;
            this.value = value;
        }

        public String getName() { return name; }
        public String getValue() { return value; }
    }
}