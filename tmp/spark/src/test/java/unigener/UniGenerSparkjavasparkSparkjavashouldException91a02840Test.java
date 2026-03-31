import spark.should;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设异常处理器接口
interface ExceptionHandler {
    void handle(Throwable e);
}

// 目标类（假设）
public class UniGenerSparkjavasparkSparkjavashouldException91a02840Test {
    private final Map<Class<? extends Throwable>, ExceptionHandler> handlerMap = new HashMap<>();

    public ExceptionHandlerRegistry exception(Class<? extends Throwable> exceptionClass, ExceptionHandler handler) {
        if (exceptionClass == null) {
            throw new IllegalArgumentException("Exception class must not be null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("Exception handler must not be null");
        }
        handlerMap.put(exceptionClass, handler);
        return this;
    }

    // 用于测试验证的获取方法（假设）
    public ExceptionHandler getHandler(Class<? extends Throwable> exceptionClass) {
        return handlerMap.get(exceptionClass);
    }
}

class ExceptionHandlerRegistryTest {

    @Test
    void testException_ValidExceptionAndHandler_SuccessfullyRegistered() {
        // 准备
        ExceptionHandlerRegistry registry = new ExceptionHandlerRegistry();
        Class<RuntimeException> exceptionClass = RuntimeException.class;
        ExceptionHandler testHandler = e -> {}; // 空实现处理器

        // 执行
        ExceptionHandlerRegistry result = registry.exception(exceptionClass, testHandler);

        // 验证：注册成功且返回自身（支持链式调用）
        assertSame(registry, result, "Should return the same registry instance for chaining");
        assertSame(testHandler, registry.getHandler(exceptionClass), "Handler should be registered for the exception class");
    }

    @Test
    void testException_ExceptionClassNull_ThrowsIllegalArgumentException() {
        // 准备
        ExceptionHandlerRegistry registry = new ExceptionHandlerRegistry();
        ExceptionHandler testHandler = e -> {};

        // 执行 & 验证
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> registry.exception(null, testHandler),
                "Should throw IllegalArgumentException when exception class is null");
        assertTrue(exception.getMessage().contains("Exception class must not be null"), "Exception message mismatch");
    }

    @Test
    void testException_HandlerNull_ThrowsIllegalArgumentException() {
        // 准备
        ExceptionHandlerRegistry registry = new ExceptionHandlerRegistry();
        Class<IllegalArgumentException> exceptionClass = IllegalArgumentException.class;

        // 执行 & 验证
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> registry.exception(exceptionClass, null),
                "Should throw IllegalArgumentException when handler is null");
        assertTrue(exception.getMessage().contains("Exception handler must not be null"), "Exception message mismatch");
    }
}