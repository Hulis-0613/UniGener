import spark.utils.ResourceUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilssparkutilsResourceUtilsjavaResourceUtilsIsUrlc8f16979Test {

    // 正常路径：有效的URL格式（预期返回true）
    @Test
    void isUrl_WithHttpProtocol_ShouldReturnTrue() {
        assertTrue(ResourceUtils.isUrl("http://example.com"));
    }

    @Test
    void isUrl_WithHttpsProtocol_ShouldReturnTrue() {
        assertTrue(ResourceUtils.isUrl("https://www.google.com"));
    }

    @Test
    void isUrl_WithFtpProtocol_ShouldReturnTrue() {
        assertTrue(ResourceUtils.isUrl("ftp://ftp.example.net"));
    }

    @Test
    void isUrl_WithPathAndQueryParams_ShouldReturnTrue() {
        assertTrue(ResourceUtils.isUrl("https://example.com/api/v1/users?page=1&size=10"));
    }

    @Test
    void isUrl_WithPortNumber_ShouldReturnTrue() {
        assertTrue(ResourceUtils.isUrl("http://localhost:8080/swagger-ui.html"));
    }

    @Test
    void isUrl_WithUserInfo_ShouldReturnTrue() {
        assertTrue(ResourceUtils.isUrl("http://admin:pass123@example.com"));
    }

    @Test
    void isUrl_WithMailtoProtocol_ShouldReturnTrue() {
        assertTrue(ResourceUtils.isUrl("mailto:contact@example.com"));
    }

    // 异常路径：非URL格式（预期返回false）
    @Test
    void isUrl_WithNullInput_ShouldReturnFalse() {
        assertFalse(ResourceUtils.isUrl(null));
    }

    @Test
    void isUrl_WithEmptyString_ShouldReturnFalse() {
        assertFalse(ResourceUtils.isUrl(""));
    }

    @Test
    void isUrl_WithoutProtocol_ShouldReturnFalse() {
        assertFalse(ResourceUtils.isUrl("example.com"));
    }

    @Test
    void isUrl_WithInvalidProtocol_ShouldReturnFalse() {
        assertFalse(ResourceUtils.isUrl("htt://invalid-protocol.com")); // 缺少 'p'
    }

    @Test
    void isUrl_WithProtocolOnly_ShouldReturnFalse() {
        assertFalse(ResourceUtils.isUrl("https://")); // 无主机名
    }

    @Test
    void isUrl_WithSpaceInUrl_ShouldReturnFalse() {
        assertFalse(ResourceUtils.isUrl("http:// example.com")); // 未编码空格
    }

    @Test
    void isUrl_WithLocalAbsolutePath_ShouldReturnFalse() {
        assertFalse(ResourceUtils.isUrl("/usr/local/resources/config.txt"));
    }

    @Test
    void isUrl_WithRelativePath_ShouldReturnFalse() {
        assertFalse(ResourceUtils.isUrl("src/main/java/ResourceUtils.java"));
    }

    @Test
    void isUrl_WithNonUrlSpecialChars_ShouldReturnFalse() {
        assertFalse(ResourceUtils.isUrl("!@#$%^&*()"));
    }

    @Test
    void isUrl_WithNumericString_ShouldReturnFalse() {
        assertFalse(ResourceUtils.isUrl("1234567890"));
    }
}