import spark.utils.delivers;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标方法所在类为StringUtils（根据实际类名调整）
public class UniGenerSparkUtilssparkutilsStringUtilsjavadeliversIsNotEmpty334da74cTest {

    /**
     * 测试输入为null时，返回false
     */
    @Test
    void isNotEmpty_WithNullInput_ReturnsFalse() {
        boolean result = StringUtils.isNotEmpty(null);
        assertFalse(result, "输入为null时，isNotEmpty应返回false");
    }

    /**
     * 测试输入为空字符串（""）时，返回false
     */
    @Test
    void isNotEmpty_WithEmptyString_ReturnsFalse() {
        boolean result = StringUtils.isNotEmpty("");
        assertFalse(result, "输入为空字符串时，isNotEmpty应返回false");
    }

    /**
     * 测试输入为非空字符串（普通字符）时，返回true
     */
    @Test
    void isNotEmpty_WithNonEmptyString_ReturnsTrue() {
        boolean result = StringUtils.isNotEmpty("test");
        assertTrue(result, "输入非空字符串时，isNotEmpty应返回true");
    }

    /**
     * 测试输入为空格字符串（" "）时，返回true（因长度>0）
     */
    @Test
    void isNotEmpty_WithWhitespaceString_ReturnsTrue() {
        boolean result = StringUtils.isNotEmpty(" ");
        assertTrue(result, "输入空格字符串时，isNotEmpty应返回true");
    }
}