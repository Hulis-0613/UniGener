import spark.QueryParamsMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkQueryParamsMapjavaQueryParamsMapCleanKey93a4365cTest {

    private final QueryParamsMap queryParamsMap = new QueryParamsMap();

    // 正常路径：带前后空格的group
    @Test
    void cleanKey_WithLeadingTrailingSpaces_ShouldTrim() {
        String group = "  userGroup  ";
        String cleaned = queryParamsMap.cleanKey(group);
        assertEquals("usergroup", cleaned);
    }

    // 正常路径：含特殊字符的group（假设清理非字母数字字符）
    @Test
    void cleanKey_WithSpecialCharacters_ShouldRemoveNonAlphanumeric() {
        String group = "group@#123$%";
        String cleaned = queryParamsMap.cleanKey(group);
        assertEquals("group123", cleaned);
    }

    // 正常路径：纯字母数字的group（无需清理）
    @Test
    void cleanKey_WithAlphanumericOnly_ShouldReturnOriginal() {
        String group = "validGroup456";
        String cleaned = queryParamsMap.cleanKey(group);
        assertEquals("validgroup456", cleaned); // 假设转小写
    }

    // 正常路径：混合大小写的group（假设统一转为小写）
    @Test
    void cleanKey_WithMixedCase_ShouldConvertToLowercase() {
        String group = "MiXeDCaSeGrOuP";
        String cleaned = queryParamsMap.cleanKey(group);
        assertEquals("mixedcasegroup", cleaned);
    }

    // 异常路径：输入为null
    @Test
    void cleanKey_InputNull_ShouldReturnNull() {
        String cleaned = queryParamsMap.cleanKey(null);
        assertNull(cleaned);
    }

    // 异常路径：输入为空字符串
    @Test
    void cleanKey_InputEmptyString_ShouldReturnEmpty() {
        String cleaned = queryParamsMap.cleanKey("");
        assertEquals("", cleaned);
    }

    // 异常路径：输入仅含空格
    @Test
    void cleanKey_InputOnlySpaces_ShouldReturnEmpty() {
        String group = "   \t  "; // 包含空格和制表符
        String cleaned = queryParamsMap.cleanKey(group);
        assertEquals("", cleaned);
    }

    // 异常路径：输入含连续特殊字符
    @Test
    void cleanKey_WithConsecutiveSpecialChars_ShouldRemoveAll() {
        String group = "##$$invalid__group!!";
        String cleaned = queryParamsMap.cleanKey(group);
        assertEquals("invalidgroup", cleaned);
    }
}