import spark.Redirect;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRedirectjavaRedirectRedirectf0bfa52aTest {

    // 正常路径：验证合法的 http/https 参数能成功创建实例
    @Test
    void createRedirect_withValidHttpUrl_shouldInitializeSuccessfully() {
        // 测试 http 协议
        String validHttp = "http://example.com";
        Redirect redirectHttp = new Redirect(validHttp);
        assertEquals(validHttp, redirectHttp.getHttp(), "http 参数应正确存储");

        // 测试 https 协议
        String validHttps = "https://example.com/path?query=1";
        Redirect redirectHttps = new Redirect(validHttps);
        assertEquals(validHttps, redirectHttps.getHttp(), "https 参数应正确存储");
    }

    // 异常路径：http 为 null 时抛出 IllegalArgumentException
    @Test
    void createRedirect_withNullHttp_shouldThrowIllegalArgumentException() {
        String nullHttp = null;
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Redirect(nullHttp),
            "http 为 null 时应抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("http must not be null or empty"), 
                  "异常信息应提示参数非空");
    }

    // 异常路径：http 为空字符串时抛出 IllegalArgumentException
    @Test
    void createRedirect_withEmptyHttp_shouldThrowIllegalArgumentException() {
        String emptyHttp = "";
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new Redirect(emptyHttp),
            "http 为空字符串时应抛出 IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("http must not be null or empty"), 
                  "异常信息应提示参数非空");
    }

    // 异常路径：http 非 http/https 协议时抛出 IllegalArgumentException
    @Test
    void createRedirect_withInvalidSchemeHttp_shouldThrowIllegalArgumentException() {
        // 测试 ftp 协议
        String ftpHttp = "ftp://example.com";
        IllegalArgumentException ftpException = assertThrows(
            IllegalArgumentException.class,
            () -> new Redirect(ftpHttp),
            "非 http/https 协议应抛出 IllegalArgumentException"
        );
        assertTrue(ftpException.getMessage().contains("must start with http:// or https://"), 
                  "异常信息应提示协议要求");

        // 测试无协议（纯域名）
        String noSchemeHttp = "example.com";
        IllegalArgumentException noSchemeException = assertThrows(
            IllegalArgumentException.class,
            () -> new Redirect(noSchemeHttp),
            "无协议参数应抛出 IllegalArgumentException"
        );
        assertTrue(noSchemeException.getMessage().contains("must start with http:// or https://"), 
                  "异常信息应提示协议要求");
    }
}