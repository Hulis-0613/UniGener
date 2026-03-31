import spark.utils.delivers;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标方法所在类为 StringUtils（根据实际类名调整）
public class UniGenerSparkUtilssparkutilsStringUtilsjavadeliversHasLengthe25de9a9Test {

    /**
     * 测试输入为 null 时，hasLength 应返回 false
     */
    @Test
    void hasLength_withNullInput_returnsFalse() {
        CharSequence str = null;
        boolean result = StringUtils.hasLength(str);
        assertFalse(result, "null 输入应返回 false");
    }

    /**
     * 测试输入为空字符串（长度 0）时，hasLength 应返回 false
     */
    @Test
    void hasLength_withEmptyString_returnsFalse() {
        CharSequence str = "";
        boolean result = StringUtils.hasLength(str);
        assertFalse(result, "空字符串输入应返回 false");
    }

    /**
     * 测试输入为长度为 1 的字符串时，hasLength 应返回 true
     */
    @Test
    void hasLength_withSingleCharString_returnsTrue() {
        CharSequence str = "a";
        boolean result = StringUtils.hasLength(str);
        assertTrue(result, "长度为 1 的字符串应返回 true");
    }

    /**
     * 测试输入为长度大于 1 的字符串时，hasLength 应返回 true
     */
    @Test
    void hasLength_withLongString_returnsTrue() {
        CharSequence str = "hello world";
        boolean result = StringUtils.hasLength(str);
        assertTrue(result, "长度大于 1 的字符串应返回 true");
    }

    /**
     * 测试输入为非空 StringBuilder（CharSequence 实现类）时，hasLength 应返回 true
     */
    @Test
    void hasLength_withNonEmptyStringBuilder_returnsTrue() {
        CharSequence str = new StringBuilder("test");
        boolean result = StringUtils.hasLength(str);
        assertTrue(result, "非空 StringBuilder 应返回 true");
    }

    /**
     * 测试输入为空 StringBuilder（长度 0）时，hasLength 应返回 false
     */
    @Test
    void hasLength_withEmptyStringBuilder_returnsFalse() {
        CharSequence str = new StringBuilder("");
        boolean result = StringUtils.hasLength(str);
        assertFalse(result, "空 StringBuilder 应返回 false");
    }

    /**
     * 测试输入为非空 StringBuffer（CharSequence 实现类）时，hasLength 应返回 true
     */
    @Test
    void hasLength_withNonEmptyStringBuffer_returnsTrue() {
        CharSequence str = new StringBuffer("example");
        boolean result = StringUtils.hasLength(str);
        assertTrue(result, "非空 StringBuffer 应返回 true");
    }

    /**
     * 测试输入为空 StringBuffer（长度 0）时，hasLength 应返回 false
     */
    @Test
    void hasLength_withEmptyStringBuffer_returnsFalse() {
        CharSequence str = new StringBuffer("");
        boolean result = StringUtils.hasLength(str);
        assertFalse(result, "空 StringBuffer 应返回 false");
    }
}