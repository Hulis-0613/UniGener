import spark.staticfiles.MimeType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkStaticfilessparkstaticfilesMimeTypejavaMimeTypeFromPathInfo2959484cTest {

    // 测试null输入（假设方法对null抛出NullPointerException）
    @Test
    void fromPathInfo_WithNullPathInfo_ThrowsNullPointerException() {
        // 执行测试并断言异常
        assertThrows(NullPointerException.class, () -> MimeType.fromPathInfo(null));
    }

    // 测试空字符串输入（返回默认MIME类型）
    @Test
    void fromPathInfo_WithEmptyPathInfo_ReturnsDefaultMimeType() {
        String result = MimeType.fromPathInfo("");
        assertEquals("application/octet-stream", result);
    }

    // 测试无扩展名的路径（返回默认MIME类型）
    @Test
    void fromPathInfo_WithoutExtension_ReturnsDefaultMimeType() {
        String result = MimeType.fromPathInfo("plainfile");
        assertEquals("application/octet-stream", result);
    }

    // 测试HTML扩展名（正常映射）
    @Test
    void fromPathInfo_WithHtmlExtension_ReturnsTextHtml() {
        String result = MimeType.fromPathInfo("index.html");
        assertEquals("text/html", result);
    }

    // 测试JPG扩展名（正常映射）
    @Test
    void fromPathInfo_WithJpgExtension_ReturnsImageJpeg() {
        String result = MimeType.fromPathInfo("photo.jpg");
        assertEquals("image/jpeg", result);
    }

    // 测试TXT扩展名（正常映射）
    @Test
    void fromPathInfo_WithTxtExtension_ReturnsTextPlain() {
        String result = MimeType.fromPathInfo("notes.txt");
        assertEquals("text/plain", result);
    }

    // 测试多扩展名（取最后一个扩展名映射）
    @Test
    void fromPathInfo_WithMultipleDots_ReturnsLastExtensionMimeType() {
        String result = MimeType.fromPathInfo("archive.tar.gz");
        assertEquals("application/gzip", result); // 假设gz对应gzip
    }

    // 测试含特殊字符的路径（正确识别扩展名）
    @Test
    void fromPathInfo_WithSpecialCharacters_ReturnsCorrectMimeType() {
        String result = MimeType.fromPathInfo("file!@#$%^&.pdf");
        assertEquals("application/pdf", result);
    }

    // 测试大小写混合扩展名（不区分大小写映射）
    @Test
    void fromPathInfo_WithMixedCaseExtension_ReturnsCorrectMimeType() {
        String result = MimeType.fromPathInfo("DocumenT.PdF");
        assertEquals("application/pdf", result);
    }

    // 测试隐藏文件（无有效扩展名，返回默认类型）
    @Test
    void fromPathInfo_WithHiddenFile_ReturnsDefaultMimeType() {
        String result = MimeType.fromPathInfo(".bashrc");
        assertEquals("application/octet-stream", result);
    }

    // 测试含路径分隔符的路径（提取文件名扩展名）
    @Test
    void fromPathInfo_WithPathSeparators_ReturnsCorrectMimeType() {
        String result = MimeType.fromPathInfo("/var/www/docs/report.pdf");
        assertEquals("application/pdf", result);
    }
}