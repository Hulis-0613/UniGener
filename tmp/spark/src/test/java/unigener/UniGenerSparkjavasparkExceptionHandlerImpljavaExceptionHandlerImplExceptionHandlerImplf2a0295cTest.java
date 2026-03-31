import spark.ExceptionHandlerImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkExceptionHandlerImpljavaExceptionHandlerImplExceptionHandlerImplf2a0295cTest {

    /**
     * 正常路径测试：传入有效异常类Class对象，验证构造方法成功创建实例且参数正确。
     */
    @Test
    void testConstructor_WithValidExceptionClass_Success() {
        // 准备：定义一个具体的异常类Class（如RuntimeException.class）
        Class<RuntimeException> exceptionClass = RuntimeException.class;

        // 执行：调用构造方法创建实例
        ExceptionHandlerImpl<RuntimeException> handler = new ExceptionHandlerImpl<>(exceptionClass);

        // 断言：实例非null，且内部保存的异常类与传入一致（假设存在getExceptionClass()方法）
        assertNotNull(handler, "ExceptionHandlerImpl实例应成功创建");
        assertEquals(exceptionClass, handler.getExceptionClass(), "构造方法应正确保存传入的异常类");
    }

    /**
     * 异常路径测试：传入null作为异常类Class，验证构造方法抛出IllegalArgumentException。
     */
    @Test
    void testConstructor_WithNullExceptionClass_ThrowsIllegalArgumentException() {
        // 执行并断言：构造方法传入null时抛出IllegalArgumentException
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new ExceptionHandlerImpl<>(null),
            "传入null异常类时，构造方法应抛出IllegalArgumentException"
        );

        // 可选：验证异常消息（若构造方法定义了具体消息）
        assertTrue(exception.getMessage().contains("exceptionClass must not be null"), 
            "异常消息应提示参数非空要求");
    }
}