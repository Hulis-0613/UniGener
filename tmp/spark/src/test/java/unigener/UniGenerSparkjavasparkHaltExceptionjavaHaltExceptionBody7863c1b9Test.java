import spark.HaltException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkHaltExceptionjavaHaltExceptionBody7863c1b9Test {

    /**
     * 测试正常路径：当HaltException实例包含消息时，body方法应返回该消息
     */
    @Test
    void body_WithMessage_ReturnsExpectedMessage() {
        // 准备测试数据
        String expectedBody = "System halt requested";
        HaltException exception = new HaltException(expectedBody);
        
        // 执行目标方法
        String actualBody = exception.body();
        
        // 验证结果（复用仓库常见断言风格）
        assertEquals(expectedBody, actualBody, "body方法应返回异常消息内容");
    }

    /**
     * 测试异常路径：当HaltException实例无消息时，body方法应抛出IllegalStateException
     */
    @Test
    void body_WithoutMessage_ThrowsIllegalStateException() {
        // 准备测试数据（无参构造的异常实例，消息为null）
        HaltException exception = new HaltException();
        
        // 执行目标方法并验证异常（复用仓库常见断言风格）
        IllegalStateException thrown = assertThrows(IllegalStateException.class, 
            exception::body, 
            "当异常无消息时，body方法应抛出IllegalStateException");
        
        // 验证异常消息（可选，根据实际实现补充）
        assertEquals("HaltException body requires non-null message", thrown.getMessage(), 
            "异常消息不符合预期");
    }
}