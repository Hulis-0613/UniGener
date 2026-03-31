import spark.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkServicejavaServiceHalt831450b7Test {

    /**
     * 测试正常路径：halt方法成功执行，无异常抛出
     * 场景：服务处于可停止状态（如已启动），调用halt后正常终止
     */
    @Test
    void halt_正常路径_成功执行无异常() {
        // 初始化Service实例（假设需先启动服务以进入可停止状态）
        Service service = new Service();
        service.start(); // 假设Service有start方法用于启动服务
        
        // 执行halt方法，断言无异常抛出
        assertDoesNotThrow(() -> service.halt(), "halt方法在正常状态下应成功执行");
    }

    /**
     * 测试异常路径：服务未启动时调用halt，抛出IllegalStateException
     * 场景：服务未启动（初始状态），调用halt触发非法状态异常
     */
    @Test
    void halt_异常路径_服务未启动时调用_抛出IllegalStateException() {
        // 初始化Service实例（未启动状态）
        Service service = new Service();
        
        // 执行halt方法，断言抛出预期异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> service.halt(),
            "服务未启动时调用halt应抛出IllegalStateException"
        );
        
        // 可选：验证异常消息（若方法实现中定义了具体消息）
        assertTrue(exception.getMessage().contains("服务未启动，无法执行halt操作"), 
                  "异常消息应包含服务未启动的提示");
    }

    /**
     * 测试异常路径：服务已停止后重复调用halt，抛出IllegalStateException
     * 场景：服务已停止，再次调用halt触发非法状态异常
     */
    @Test
    void halt_异常路径_服务已停止后重复调用_抛出IllegalStateException() {
        // 初始化并启动服务
        Service service = new Service();
        service.start();
        service.halt(); // 首次调用halt使服务停止
        
        // 再次调用halt，断言抛出异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> service.halt(),
            "服务已停止后重复调用halt应抛出IllegalStateException"
        );
        
        assertTrue(exception.getMessage().contains("服务已停止，无法重复执行halt操作"),
                  "异常消息应包含服务已停止的提示");
    }
}