import spark.Base64;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkBase64javaBase64Encodeec52518fTest {

    private final Encoder encoder = new Encoder(); // 假设encode方法所在类为Encoder

    /**
     * 测试正常字符串编码（无特殊字符）
     */
    @Test
    void encode_NormalString_ReturnsEncoded() {
        // 输入：普通字符串（无特殊字符）
        String input = "helloWorld123";
        // 期望：URL编码后与原字符串一致（无特殊字符无需编码）
        String expected = "helloWorld123";
        
        String result = encoder.encode(input);
        assertEquals(expected, result, "普通字符串应直接返回原内容");
    }

    /**
     * 测试含空格的字符串编码
     */
    @Test
    void encode_StringWithSpaces_ReturnsEncodedWithPlus() {
        // 输入：含空格的字符串
        String input = "hello world";
        // 期望：空格被编码为"+"（URL编码标准）
        String expected = "hello+world";
        
        String result = encoder.encode(input);
        assertEquals(expected, result, "空格应编码为'+'");
    }

    /**
     * 测试含特殊字符（&、=、#）的字符串编码
     */
    @Test
    void encode_StringWithSpecialChars_ReturnsEncoded() {
        // 输入：含&、=、#的字符串
        String input = "a&b=c#d";
        // 期望：特殊字符被URL编码（&→%26，=→%3D，#→%23）
        String expected = "a%26b%3Dc%23d";
        
        String result = encoder.encode(input);
        assertEquals(expected, result, "特殊字符应正确编码");
    }

    /**
     * 测试空字符串编码
     */
    @Test
    void encode_EmptyString_ReturnsEmpty() {
        // 输入：空字符串
        String input = "";
        // 期望：返回空字符串
        String expected = "";
        
        String result = encoder.encode(input);
        assertEquals(expected, result, "空字符串应返回空");
    }

    /**
     * 测试输入为null时抛出NullPointerException
     */
    @Test
    void encode_NullInput_ThrowsNullPointerException() {
        // 输入：null
        String input = null;
        
        NullPointerException exception = assertThrows(NullPointerException.class, 
            () -> encoder.encode(input), 
            "输入null时应抛出NullPointerException");
        assertTrue(exception.getMessage().contains("输入内容不能为null"), "异常信息应提示输入为null");
    }

    /**
     * 测试含非法控制字符（如\u0000空字符）时抛出IllegalArgumentException
     */
    @Test
    void encode_StringWithInvalidControlChar_ThrowsIllegalArgumentException() {
        // 输入：包含空字符（\u0000）的字符串
        String input = "test" + '\u0000' + "invalid";
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> encoder.encode(input), 
            "含非法控制字符时应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("包含非法控制字符"), "异常信息应提示非法字符");
    }

    /**
     * 测试超长字符串编码（假设方法支持任意长度，验证无异常）
     */
    @Test
    void encode_LongString_ReturnsEncoded() {
        // 输入：1000个字符的超长字符串（由'a'重复组成）
        String input = "a".repeat(1000);
        // 期望：编码后长度与原字符串一致（无特殊字符，无需编码）
        String expected = input;
        
        String result = encoder.encode(input);
        assertEquals(expected, result, "超长字符串应正常编码");
    }
}