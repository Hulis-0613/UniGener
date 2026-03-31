import spark.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkResponsejavaResponseHeadera81b4f80Test {

    // 正常路径：设置有效header和value
    @Test
    void header_WithValidHeaderAndValue_SetsHeaderSuccessfully() {
        // 准备
        Response response = new Response();
        String testHeader = "Content-Type";
        String testValue = "application/json";

        // 执行
        response.header(testHeader, testValue);

        // 断言：验证header被正确设置
        assertEquals(testValue, response.getHeader(testHeader), "Header value should match the input value");
    }

    // 异常路径：header为null时抛出IllegalArgumentException
    @Test
    void header_WithNullHeader_ThrowsIllegalArgumentException() {
        // 准备
        Response response = new Response();
        String nullHeader = null;
        String testValue = "validValue";

        // 执行 & 断言：预期抛出异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> response.header(nullHeader, testValue),
                "Setting null header should throw IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("Header cannot be null or empty"), 
                "Exception message should indicate invalid header");
    }

    // 异常路径：header为空字符串时抛出IllegalArgumentException
    @Test
    void header_WithEmptyHeader_ThrowsIllegalArgumentException() {
        // 准备
        Response response = new Response();
        String emptyHeader = "";
        String testValue = "validValue";

        // 执行 & 断言：预期抛出异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> response.header(emptyHeader, testValue),
                "Setting empty header should throw IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("Header cannot be null or empty"), 
                "Exception message should indicate invalid header");
    }

    // 正常路径：value为null时设置成功（允许null值）
    @Test
    void header_WithNullValue_SetsNullHeaderValue() {
        // 准备
        Response response = new Response();
        String testHeader = "Nullable-Header";
        String nullValue = null;

        // 执行
        response.header(testHeader, nullValue);

        // 断言：验证value被设为null
        assertNull(response.getHeader(testHeader), "Header value should be null when input value is null");
    }

    // 正常路径：header包含特殊字符时设置成功
    @Test
    void header_WithSpecialCharHeader_SetsHeaderSuccessfully() {
        // 准备
        Response response = new Response();
        String specialHeader = "X-Request-ID_123!"; // 包含连字符、下划线、数字、特殊符号
        String specialValue = "value with spaces & symbols: @#$";

        // 执行
        response.header(specialHeader, specialValue);

        // 断言：验证特殊字符header和value被正确设置
        assertEquals(specialValue, response.getHeader(specialHeader), 
                "Special character header and value should be set correctly");
    }
}