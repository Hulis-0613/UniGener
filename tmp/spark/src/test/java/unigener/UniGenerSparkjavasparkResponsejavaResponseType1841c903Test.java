import spark.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkResponsejavaResponseType1841c903Test {

    private final Response response = new Response();

    // 正常路径：contentType为标准JSON类型
    @Test
    void typeWithApplicationJsonReturnsJson() {
        String result = response.type("application/json");
        assertEquals("JSON", result);
    }

    // 正常路径：contentType为带参数的JSON类型
    @Test
    void typeWithApplicationJsonWithCharsetReturnsJson() {
        String result = response.type("application/json; charset=utf-8");
        assertEquals("JSON", result);
    }

    // 正常路径：contentType为标准TEXT类型
    @Test
    void typeWithTextPlainReturnsText() {
        String result = response.type("text/plain");
        assertEquals("TEXT", result);
    }

    // 正常路径：contentType为带参数的TEXT类型
    @Test
    void typeWithTextPlainWithCharsetReturnsText() {
        String result = response.type("text/plain; charset=ISO-8859-1");
        assertEquals("TEXT", result);
    }

    // 正常路径：contentType为PNG图片类型
    @Test
    void typeWithImagePngReturnsImage() {
        String result = response.type("image/png");
        assertEquals("IMAGE", result);
    }

    // 正常路径：contentType为JPEG图片类型
    @Test
    void typeWithImageJpegReturnsImage() {
        String result = response.type("image/jpeg");
        assertEquals("IMAGE", result);
    }

    // 正常路径：contentType为未知类型
    @Test
    void typeWithUnknownContentTypeReturnsUnknown() {
        String result = response.type("application/xml");
        assertEquals("UNKNOWN", result);
    }

    // 异常路径：contentType为空字符串
    @Test
    void typeWithEmptyContentTypeReturnsUnknown() {
        String result = response.type("");
        assertEquals("UNKNOWN", result);
    }

    // 异常路径：contentType为null（预期抛IllegalArgumentException）
    @Test
    void typeWithNullContentTypeThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> response.type(null));
        assertEquals("contentType must not be null", exception.getMessage());
    }
}