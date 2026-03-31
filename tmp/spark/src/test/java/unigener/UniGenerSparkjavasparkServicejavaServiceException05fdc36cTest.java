import spark.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设异常处理器函数式接口
@FunctionalInterface
interface ExceptionHandler<E extends Exception> {
    void handle(E exception);
}

// 待测试的Service类（仅为测试上下文，实际应引用项目中的Service）
public class UniGenerSparkjavasparkServicejavaServiceException05fdc36cTest {
    private final java.util.Map<Class<? extends Exception>, ExceptionHandler<? extends Exception>> handlers = new java.util.HashMap<>();

    public <E extends Exception> void exception(Class<E> exceptionClass, ExceptionHandler<E> handler) {
        if (exceptionClass == null) {
            throw new IllegalArgumentException("exceptionClass must not be null");
        }
        if (handler == null) {
            throw new IllegalArgumentException("handler must not be null");
        }
        handlers.put(exceptionClass, handler);
    }

    // 测试用：获取已注册的处理器（实际项目中可通过包私有方法或测试工具类提供）
    <E extends Exception> ExceptionHandler<E> getExceptionHandler(Class<E> exceptionClass) {
        @SuppressWarnings("unchecked")
        ExceptionHandler<E> handler = (ExceptionHandler<E>) handlers.get(exceptionClass);
        return handler;
    }
}

class ServiceExceptionTest {

    @Test
    void testException_NormalRegistration_Success() {
        // 准备
        Service service = new Service();
        Class<IllegalArgumentException> exceptionClass = IllegalArgumentException.class;
        ExceptionHandler<IllegalArgumentException> testHandler = e -> System.out.println("Handling IllegalArgumentException");

        // 执行
        service.exception(exceptionClass, testHandler);

        // 断言：处理器已正确注册
        ExceptionHandler<IllegalArgumentException> registeredHandler = service.getExceptionHandler(exceptionClass);
        assertNotNull(registeredHandler, "注册的处理器不应为null");
        assertSame(testHandler, registeredHandler, "注册的处理器实例不匹配");
    }

    @Test
    void testException_DuplicateRegistration_OverrideSuccess() {
        // 准备
        Service service = new Service();
        Class<RuntimeException> exceptionClass = RuntimeException.class;
        ExceptionHandler<RuntimeException> handler1 = e -> {};
        ExceptionHandler<RuntimeException> handler2 = e -> {};

        // 执行：首次注册
        service.exception(exceptionClass, handler1);
        // 执行：重复注册（覆盖）
        service.exception(exceptionClass, handler2);

        // 断言：最终注册的是新处理器
        ExceptionHandler<RuntimeException> registeredHandler = service.getExceptionHandler(exceptionClass);
        assertSame(handler2, registeredHandler, "重复注册未覆盖旧处理器");
    }

    @Test
    void testException_ExceptionClassNull_ThrowIllegalArgument() {
        // 准备
        Service service = new Service();
        ExceptionHandler<Exception> testHandler = e -> {};

        // 执行并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.exception(null, testHandler),
                "异常类为null时应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("exceptionClass must not be null"),
                "异常消息不符合预期");
    }

    @Test
    void testException_HandlerNull_ThrowIllegalArgument() {
        // 准备
        Service service = new Service();
        Class<NullPointerException> exceptionClass = NullPointerException.class;

        // 执行并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.exception(exceptionClass, null),
                "处理器为null时应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("handler must not be null"),
                "异常消息不符合预期");
    }
}