import spark.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkServicejavaServiceDisableMimeTypeGuessing903ddf3eTest {

    /**
     * 正常路径：初始状态启用MIME类型猜测，调用方法后成功禁用
     */
    @Test
    void disableMimeTypeGuessing_WhenInitiallyEnabled_ShouldDisableGuessing() {
        // 1. 准备：创建Service实例，假设初始状态MIME类型猜测为启用
        Service service = new Service();
        assertTrue(service.isMimeTypeGuessingEnabled(), 
            "初始状态下MIME类型猜测应处于启用状态");

        // 2. 执行：调用禁用方法
        service.disableMimeTypeGuessing();

        // 3. 断言：验证MIME类型猜测已被禁用
        assertFalse(service.isMimeTypeGuessingEnabled(), 
            "调用disableMimeTypeGuessing后，MIME类型猜测应被禁用");
    }

    /**
     * 异常路径：已禁用状态下重复调用，预期抛出IllegalStateException
     * （假设业务逻辑不允许重复禁用，需根据实际实现调整异常类型）
     */
    @Test
    void disableMimeTypeGuessing_WhenAlreadyDisabled_ShouldThrowException() {
        // 1. 准备：创建Service实例并先禁用MIME类型猜测
        Service service = new Service();
        service.disableMimeTypeGuessing();
        assertFalse(service.isMimeTypeGuessingEnabled(), 
            "前置操作：MIME类型猜测已被禁用");

        // 2. 执行&断言：重复调用应抛出异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> service.disableMimeTypeGuessing(), 
            "已禁用状态下重复调用disableMimeTypeGuessing应抛出IllegalStateException");
        
        assertEquals("MIME类型猜测已处于禁用状态，无需重复操作", exception.getMessage(), 
            "异常信息不符合预期");
    }

    /**
     * 异常路径：Service未初始化时调用，预期抛出IllegalStateException
     * （假设方法依赖初始化状态，需根据实际实现调整依赖条件）
     */
    @Test
    void disableMimeTypeGuessing_WhenServiceUninitialized_ShouldThrowException() {
        // 1. 准备：创建未初始化的Service实例（假设构造函数不自动初始化，需显式调用init()）
        Service uninitializedService = new Service(false); // 假设带参数构造函数控制初始化状态

        // 2. 执行&断言：未初始化时调用应抛出异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> uninitializedService.disableMimeTypeGuessing(), 
            "未初始化的Service调用disableMimeTypeGuessing应抛出IllegalStateException");
        
        assertEquals("Service未初始化，无法执行禁用操作", exception.getMessage(), 
            "异常信息不符合预期");
    }
}