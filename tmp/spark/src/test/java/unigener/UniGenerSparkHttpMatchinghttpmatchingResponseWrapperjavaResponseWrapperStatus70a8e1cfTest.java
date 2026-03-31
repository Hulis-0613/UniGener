import spark.http.matching.ResponseWrapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingResponseWrapperjavaResponseWrapperStatus70a8e1cfTest {

    // 正常路径：测试合法状态码设置（覆盖常见HTTP状态码及边界值）
    @Test
    void status_withValidStatusCode_shouldSetStatusCodeSuccessfully() {
        // Arrange
        ResponseWrapper responseWrapper = new ResponseWrapper();
        int[] validStatusCodes = {100, 200, 201, 404, 500, 599}; // 覆盖1xx-5xx及边界值

        for (int statusCode : validStatusCodes) {
            // Act
            ResponseWrapper result = responseWrapper.status(statusCode);

            // Assert：验证状态码设置正确且支持链式调用（若方法返回自身）
            assertEquals(statusCode, responseWrapper.getStatus(), 
                         "Failed to set status code: " + statusCode);
            assertSame(responseWrapper, result, 
                       "Method should return the same ResponseWrapper instance for chaining");
        }
    }

    // 异常路径：测试非法状态码（小于100）
    @Test
    void status_withStatusCodeLessThan100_shouldThrowIllegalArgumentException() {
        // Arrange
        ResponseWrapper responseWrapper = new ResponseWrapper();
        int[] invalidStatusCodes = {-1, 0, 99}; // 典型非法值（负数、0、99）

        for (int statusCode : invalidStatusCodes) {
            // Act & Assert
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
                () -> responseWrapper.status(statusCode), 
                "Expected IllegalArgumentException for status code: " + statusCode);
            
            assertTrue(exception.getMessage().contains("Invalid status code"), 
                      "Exception message should indicate invalid status code");
        }
    }

    // 异常路径：测试非法状态码（大于599）
    @Test
    void status_withStatusCodeGreaterThan599_shouldThrowIllegalArgumentException() {
        // Arrange
        ResponseWrapper responseWrapper = new ResponseWrapper();
        int[] invalidStatusCodes = {600, 1000, Integer.MAX_VALUE}; // 典型非法值（600、超大值）

        for (int statusCode : invalidStatusCodes) {
            // Act & Assert
            assertThrows(IllegalArgumentException.class, 
                () -> responseWrapper.status(statusCode), 
                "Expected IllegalArgumentException for status code: " + statusCode);
        }
    }
}