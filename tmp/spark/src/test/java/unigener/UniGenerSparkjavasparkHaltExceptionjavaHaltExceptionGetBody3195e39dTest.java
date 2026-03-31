import spark.HaltException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标类名为TargetClass（需替换为实际类名）
public class UniGenerSparkjavasparkHaltExceptionjavaHaltExceptionGetBody3195e39dTest {

    /**
     * 正常路径：当body已初始化时，getBody返回预期内容
     */
    @Test
    void getBody_WithInitializedBody_ReturnsExpectedBody() {
        // Arrange：创建TargetClass实例并初始化body（假设通过构造函数或setter设置）
        String expectedBody = "test body content";
        TargetClass target = new TargetClass();
        target.setBody(expectedBody); // 假设存在setBody方法用于设置body

        // Act：调用getBody方法
        String actualBody = target.getBody();

        // Assert：验证返回值与预期一致
        assertEquals(expectedBody, actualBody, "getBody应返回初始化的body内容");
    }

    /**
     * 异常路径：当body未初始化（如为null）时，getBody抛出异常
     */
    @Test
    void getBody_WithoutInitializedBody_ThrowsIllegalStateException() {
        // Arrange：创建TargetClass实例但不初始化body（默认body为null）
        TargetClass target = new TargetClass();

        // Act & Assert：验证调用getBody时抛出IllegalStateException（根据实际异常类型调整）
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            target::getBody, 
            "当body未初始化时，getBody应抛出IllegalStateException");
        
        // 可选：验证异常消息（若方法定义了具体消息）
        assertTrue(exception.getMessage().contains("body未初始化"), "异常消息应包含'body未初始化'");
    }
}