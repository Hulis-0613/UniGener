import spark.Base64;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设被测试类为Decoder，包含decode方法
public class UniGenerSparkjavasparkBase64javaBase64Decode5765949dTest {

    private final Decoder decoder = new Decoder();

    /**
     * 测试正常路径：有效Base64编码内容解码为正确字符串
     */
    @Test
    void testDecode_ValidBase64Content_ReturnsDecodedString() {
        // 准备：Base64编码的"Hello, World!"为"SGVsbG8sIFdvcmxkIQ=="
        String encodedContent = "SGVsbG8sIFdvcmxkIQ==";
        String expectedDecoded = "Hello, World!";

        // 执行
        String actualDecoded = decoder.decode(encodedContent);

        // 断言
        assertEquals(expectedDecoded, actualDecoded, "有效Base64内容应正确解码");
    }

    /**
     * 测试异常路径：输入为null时抛出NullPointerException
     */
    @Test
    void testDecode_NullToDecodeContent_ThrowsNullPointerException() {
        // 执行 & 断言：输入null时抛出NPE
        assertThrows(NullPointerException.class, 
            () -> decoder.decode(null), 
            "输入为null时应抛出NullPointerException");
    }

    /**
     * 测试边界路径：输入为空字符串时返回空字符串
     */
    @Test
    void testDecode_EmptyToDecodeContent_ReturnsEmptyString() {
        // 准备
        String emptyContent = "";
        String expected = "";

        // 执行
        String actual = decoder.decode(emptyContent);

        // 断言
        assertEquals(expected, actual, "空字符串输入应返回空字符串");
    }

    /**
     * 测试异常路径：输入包含非法Base64字符时抛出IllegalArgumentException
     */
    @Test
    void testDecode_InvalidBase64Content_ThrowsIllegalArgumentException() {
        // 准备：包含非法字符'#'的Base64内容（正确应为字母、数字、+、/、=）
        String invalidContent = "SGVsbG8#"; // '#'为非法字符

        // 执行 & 断言：非法格式应抛出IllegalArgumentException
        assertThrows(IllegalArgumentException.class, 
            () -> decoder.decode(invalidContent), 
            "非法Base64内容应抛出IllegalArgumentException");
    }

    /**
     * 测试边界路径：输入为Base64填充字符（=）时返回对应解码结果
     */
    @Test
    void testDecode_Base64PaddingContent_ReturnsDecodedString() {
        // 准备：Base64编码的"Hi"为"SGk="（带1个填充符=）
        String paddedContent = "SGk=";
        String expectedDecoded = "Hi";

        // 执行
        String actualDecoded = decoder.decode(paddedContent);

        // 断言
        assertEquals(expectedDecoded, actualDecoded, "带填充符的Base64内容应正确解码");
    }
}