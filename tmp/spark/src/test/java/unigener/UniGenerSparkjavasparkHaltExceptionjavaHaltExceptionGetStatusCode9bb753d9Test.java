import spark.HaltException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标方法属于 StatusHandler 类（需替换为实际类名）
public class UniGenerSparkjavasparkHaltExceptionjavaHaltExceptionGetStatusCode9bb753d9Test {

    private final StatusHandler statusHandler = new StatusHandler();

    /**
     * 正常路径：当内部状态为"成功"时，返回 200 OK 状态码
     */
    @Test
    void getStatusCode_WhenStatusIsSuccess_Returns200() {
        // 准备：设置内部状态为"成功"（假设存在设置状态的方法，需替换为实际逻辑）
        statusHandler.setStatus("SUCCESS");
        
        // 执行
        int actualStatusCode = statusHandler.getStatusCode();
        
        // 断言
        assertEquals(200, actualStatusCode, "成功状态下应返回 200");
    }

    /**
     * 正常路径：当内部状态为"资源创建"时，返回 201 Created 状态码
     */
    @Test
    void getStatusCode_WhenStatusIsCreated_Returns201() {
        // 准备：设置内部状态为"资源创建"
        statusHandler.setStatus("CREATED");
        
        // 执行
        int actualStatusCode = statusHandler.getStatusCode();
        
        // 断言
        assertEquals(201, actualStatusCode, "资源创建状态下应返回 201");
    }

    /**
     * 异常路径：当内部状态为"服务器错误"时，返回 500 Internal Server Error 状态码
     */
    @Test
    void getStatusCode_WhenStatusIsServerError_Returns500() {
        // 准备：设置内部状态为"服务器错误"
        statusHandler.setStatus("SERVER_ERROR");
        
        // 执行
        int actualStatusCode = statusHandler.getStatusCode();
        
        // 断言
        assertEquals(500, actualStatusCode, "服务器错误状态下应返回 500");
    }

    /**
     * 异常路径：当内部状态未初始化时，抛出 IllegalStateException
     */
    @Test
    void getStatusCode_WhenStatusUninitialized_ThrowsIllegalStateException() {
        // 准备：不设置状态（保持初始未初始化状态）
        
        // 执行 & 断言：验证抛出预期异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> statusHandler.getStatusCode(),
            "未初始化状态下应抛出 IllegalStateException"
        );
        
        // 可选：验证异常消息（若方法定义了具体消息）
        assertTrue(exception.getMessage().contains("状态未初始化"), "异常消息应包含'状态未初始化'");
    }
}