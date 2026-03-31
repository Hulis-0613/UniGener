import spark.staticfiles.MimeType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkStaticfilessparkstaticfilesMimeTypejavaMimeTypeDisableGuessingccd7893bTest {

    /**
     * 正常路径测试：验证首次调用disableGuessing()能成功禁用猜测功能
     */
    @Test
    void disableGuessing_NormalPath_SuccessfullyDisabled() {
        // 1. 初始化MimeType实例（假设默认猜测功能为启用状态）
        MimeType mimeType = new MimeType();
        
        // 2. 前置状态校验：确认初始时猜测功能为启用
        assertTrue(mimeType.isGuessingEnabled(), "初始状态下猜测功能应默认启用");
        
        // 3. 执行目标方法：禁用猜测功能
        mimeType.disableGuessing();
        
        // 4. 结果校验：确认猜测功能已被禁用
        assertFalse(mimeType.isGuessingEnabled(), "调用disableGuessing()后猜测功能应被禁用");
    }

    /**
     * 异常路径测试：验证重复调用disableGuessing()时抛出预期异常
     */
    @Test
    void disableGuessing_ExceptionPath_AlreadyDisabled() {
        // 1. 初始化MimeType实例并首次禁用猜测功能
        MimeType mimeType = new MimeType();
        mimeType.disableGuessing();
        
        // 2. 前置状态校验：确认猜测功能已被禁用
        assertFalse(mimeType.isGuessingEnabled(), "首次禁用后猜测功能应处于禁用状态");
        
        // 3. 执行目标方法（重复禁用）并校验异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> mimeType.disableGuessing(),
            "重复调用disableGuessing()时应抛出IllegalStateException"
        );
        
        // 4. 异常信息校验（可选，根据实际业务需求补充）
        assertEquals("猜测功能已处于禁用状态，无需重复操作", exception.getMessage());
    }
}