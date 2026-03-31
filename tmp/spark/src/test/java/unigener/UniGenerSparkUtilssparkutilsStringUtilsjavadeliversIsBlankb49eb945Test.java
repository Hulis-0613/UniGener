import spark.utils.delivers;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilssparkutilsStringUtilsjavadeliversIsBlankb49eb945Test {

    // 测试null输入
    @Test
    void testIsBlank_NullInput_ReturnsTrue() {
        assertTrue(Delivers.isBlank(null));
    }

    // 测试空字符串
    @Test
    void testIsBlank_EmptyString_ReturnsTrue() {
        assertTrue(Delivers.isBlank(""));
    }

    // 测试纯空格字符串
    @Test
    void testIsBlank_AllSpaces_ReturnsTrue() {
        assertTrue(Delivers.isBlank("   "));
    }

    // 测试制表符(\t)
    @Test
    void testIsBlank_TabCharacter_ReturnsTrue() {
        assertTrue(Delivers.isBlank("\t"));
    }

    // 测试换行符(\n)
    @Test
    void testIsBlank_NewLineCharacter_ReturnsTrue() {
        assertTrue(Delivers.isBlank("\n"));
    }

    // 测试回车符(\r)
    @Test
    void testIsBlank_CarriageReturn_ReturnsTrue() {
        assertTrue(Delivers.isBlank("\r"));
    }

    // 测试垂直制表符(\u000B)
    @Test
    void testIsBlank_VerticalTab_ReturnsTrue() {
        assertTrue(Delivers.isBlank("\u000B"));
    }

    // 测试换页符(\f)
    @Test
    void testIsBlank_FormFeed_ReturnsTrue() {
        assertTrue(Delivers.isBlank("\f"));
    }

    // 测试混合空白字符
    @Test
    void testIsBlank_MixedWhitespace_ReturnsTrue() {
        assertTrue(Delivers.isBlank(" \t\n\r\u000B\f "));
    }

    // 测试单个非空白字符
    @Test
    void testIsBlank_SingleNonWhitespace_ReturnsFalse() {
        assertFalse(Delivers.isBlank("a"));
    }

    // 测试多个非空白字符
    @Test
    void testIsBlank_MultipleNonWhitespace_ReturnsFalse() {
        assertFalse(Delivers.isBlank("abc123"));
    }

    // 测试前导空白+非空白字符
    @Test
    void testIsBlank_LeadingWhitespaceWithNonWhitespace_ReturnsFalse() {
        assertFalse(Delivers.isBlank("  test"));
    }

    // 测试尾随空白+非空白字符
    @Test
    void testIsBlank_TrailingWhitespaceWithNonWhitespace_ReturnsFalse() {
        assertFalse(Delivers.isBlank("test  "));
    }

    // 测试中间空白+非空白字符
    @Test
    void testIsBlank_MiddleWhitespaceWithNonWhitespace_ReturnsFalse() {
        assertFalse(Delivers.isBlank("te  st"));
    }
}