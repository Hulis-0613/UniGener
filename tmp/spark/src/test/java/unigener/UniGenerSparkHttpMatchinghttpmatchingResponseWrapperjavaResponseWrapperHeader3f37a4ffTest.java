import spark.http.matching.ResponseWrapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingResponseWrapperjavaResponseWrapperHeader3f37a4ffTest {

    // 测试正常路径：设置有效header和value
    @Test
    void header_ValidHeaderAndValue_SetsHeaderSuccessfully() {
        // 初始化测试对象
        ResponseWrapper responseWrapper = new ResponseWrapper();
        String testHeader = "Content-Type";
        String testValue = "application/json";

        // 执行目标方法
        responseWrapper.header(testHeader, testValue);

        // 断言header已正确设置
        assertEquals(testValue, responseWrapper.getHeader(testHeader), 
                     "Header value should be set correctly");
    }

    // 测试异常路径：header为Null
    @Test
    void header_NullHeader_ThrowsNullPointerException() {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        String nullHeader = null;
        String testValue = "test-value";

        // 断言方法抛出NPE
        assertThrows(NullPointerException.class, 
                     () -> responseWrapper.header(nullHeader, testValue),
                     "Setting null header should throw NullPointerException");
    }

    // 测试异常路径：header为空字符串
    @Test
    void header_EmptyHeader_ThrowsIllegalArgumentException() {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        String emptyHeader = "";
        String testValue = "test-value";

        // 断言方法抛出非法参数异常
        assertThrows(IllegalArgumentException.class, 
                     () -> responseWrapper.header(emptyHeader, testValue),
                     "Setting empty header should throw IllegalArgumentException");
    }

    // 测试边界路径：value为Null（允许设置Null值场景）
    @Test
    void header_NullValue_SetsNullHeaderValue() {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        String testHeader = "X-Null-Value";
        String nullValue = null;

        // 执行目标方法（假设允许设置Null值）
        responseWrapper.header(testHeader, nullValue);

        // 断言header值为Null
        assertNull(responseWrapper.getHeader(testHeader), 
                   "Header value should be null when setting null value");
    }

    // 测试特殊字符场景：header包含特殊字符
    @Test
    void header_SpecialCharHeader_SetsHeaderSuccessfully() {
        ResponseWrapper responseWrapper = new ResponseWrapper();
        String specialHeader = "X-Custom-Header:With-Special?Chars";
        String testValue = "value-with-underscores_and-hyphens";

        responseWrapper.header(specialHeader, testValue);

        assertEquals(testValue, responseWrapper.getHeader(specialHeader), 
                     "Header with special characters should be set correctly");
    }
}