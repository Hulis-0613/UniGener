import spark.should;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.util.function.Consumer;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSparkjavashouldInitExceptionHandler80b82b93Test {

    // 重置静态异常处理器字段，确保测试隔离
    @BeforeEach
    void setUp() throws Exception {
        resetExceptionHandlerField();
    }

    /**
     * 测试正常路径：传入有效Consumer时正确初始化异常处理器
     */
    @Test
    void initExceptionHandler_WithValidConsumer_SetsHandlerSuccessfully() throws Exception {
        // Arrange：创建测试用Consumer
        Consumer<Exception> testHandler = e -> System.out.println("Test exception handled: " + e.getMessage());

        // Act：调用初始化方法
        ExceptionHandlerInitializer.initExceptionHandler(testHandler);

        // Assert：验证静态字段已被正确设置
        Consumer<Exception> actualHandler = getExceptionHandlerField();
        assertSame(testHandler, actualHandler, "异常处理器应被设置为传入的Consumer实例");
    }

    /**
     * 测试异常路径：传入null时抛出NullPointerException
     */
    @Test
    void initExceptionHandler_WithNullConsumer_ThrowsNullPointerException() {
        // Act & Assert：验证传入null时抛出预期异常
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> ExceptionHandlerInitializer.initExceptionHandler(null),
                "传入null时应抛出NullPointerException");
        
        assertTrue(exception.getMessage().contains("handler"), "异常消息应包含参数名提示");
    }

    // 反射工具：获取私有静态异常处理器字段
    private Consumer<Exception> getExceptionHandlerField() throws Exception {
        Field field = ExceptionHandlerInitializer.class.getDeclaredField("exceptionHandler");
        field.setAccessible(true);
        return (Consumer<Exception>) field.get(null); // 静态字段用null作为实例参数
    }

    // 反射工具：重置私有静态异常处理器字段为null
    private void resetExceptionHandlerField() throws Exception {
        Field field = ExceptionHandlerInitializer.class.getDeclaredField("exceptionHandler");
        field.setAccessible(true);
        field.set(null, null); // 静态字段用null作为实例参数
    }
}