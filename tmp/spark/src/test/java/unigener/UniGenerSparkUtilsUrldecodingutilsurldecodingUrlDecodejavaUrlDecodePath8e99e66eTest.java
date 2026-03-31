import spark.utils.urldecoding.UrlDecode;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilsUrldecodingutilsurldecodingUrlDecodejavaUrlDecodePath8e99e66eTest {

    private final UrlDecode urlDecode = new UrlDecode();

    // 正常路径测试：普通路径（无特殊字符）
    @Test
    void testPathNormalWithoutSpecialChars() {
        String input = "/api/v1/users";
        String expected = "/api/v1/users";
        assertEquals(expected, urlDecode.path(input));
    }

    // 正常路径测试：含空格的URL编码（%20）
    @Test
    void testPathWithSpaceEncoding() {
        String input = "/documents/report%202024.pdf";
        String expected = "/documents/report 2024.pdf";
        assertEquals(expected, urlDecode.path(input));
    }

    // 正常路径测试：含斜杠的URL编码（%2F）
    @Test
    void testPathWithSlashEncoding() {
        String input = "root%2Fsubfolder%2Ffile.txt";
        String expected = "root/subfolder/file.txt";
        assertEquals(expected, urlDecode.path(input));
    }

    // 正常路径测试：含特殊字符@的URL编码（%40）
    @Test
    void testPathWithAtSymbolEncoding() {
        String input = "user%40example.com/profile";
        String expected = "user@example.com/profile";
        assertEquals(expected, urlDecode.path(input));
    }

    // 正常路径测试：多个连续编码字符（如%25代表%，解码后保留单层编码）
    @Test
    void testPathWithMultipleEncodedChars() {
        String input = "path%2520with%252Fencoded%2525"; // %25解码为%，故原输入实为"path%20with%2Fencoded%"
        String expected = "path with/encoded%";
        assertEquals(expected, urlDecode.path(input));
    }

    // 正常路径测试：空字符串输入
    @Test
    void testPathWithEmptyString() {
        String input = "";
        String expected = "";
        assertEquals(expected, urlDecode.path(input));
    }

    // 异常路径测试：null输入（预期抛出NullPointerException）
    @Test
    void testPathWithNullInput() {
        assertThrows(NullPointerException.class, () -> urlDecode.path(null));
    }

    // 异常路径测试：无效URL编码（%后非十六进制字符，如%G3）
    @Test
    void testPathWithInvalidEncoding() {
        String input = "invalid%G3encoding";
        assertThrows(IllegalArgumentException.class, () -> urlDecode.path(input));
    }

    // 异常路径测试：不完整URL编码（%后仅1个字符，如%2）
    @Test
    void testPathWithIncompleteEncoding() {
        String input = "incomplete%2encoding";
        assertThrows(IllegalArgumentException.class, () -> urlDecode.path(input));
    }
}