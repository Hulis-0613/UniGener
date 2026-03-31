import spark.embeddedserver.NotSupportedException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;

public class UniGenerSparkEmbeddedserversparkembeddedserverNotSupportedExceptionjavaNotSupportedExceptionNotSupportedException3419a0bfTest {

    // 正常路径：验证构造函数初始化及getMessage返回预期消息
    @Test
    void getMessage_WithValidParameters_ShouldReturnFormattedMessage() {
        // 准备测试数据
        Class<?> testClazz = String.class;
        String testFeature = "serialization";
        String expectedMessage = String.format(
            "Feature '%s' is not supported for class '%s'", 
            testFeature, 
            testClazz.getName()
        );

        // 执行构造函数
        NotSupportedException exception = new NotSupportedException(testClazz, testFeature);

        // 验证结果
        assertNotNull(exception, "Exception instance should not be null");
        assertEquals(expectedMessage, exception.getMessage(), "Exception message format mismatch");
    }

    // 异常路径：clazz为null时构造函数应抛出NullPointerException
    @Test
    void constructor_WithNullClazz_ShouldThrowNullPointerException() {
        // 准备测试数据
        Class<?> nullClazz = null;
        String validFeature = "deserialization";

        // 执行并验证异常
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> new NotSupportedException(nullClazz, validFeature),
            "Expected NullPointerException when clazz is null"
        );
        assertTrue(exception.getMessage().contains("clazz must not be null"), 
                  "Exception message should indicate null clazz");
    }

    // 异常路径：feature为null时构造函数应抛出NullPointerException
    @Test
    void constructor_WithNullFeature_ShouldThrowNullPointerException() {
        // 准备测试数据
        Class<?> validClazz = Integer.class;
        String nullFeature = null;

        // 执行并验证异常
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> new NotSupportedException(validClazz, nullFeature),
            "Expected NullPointerException when feature is null"
        );
        assertTrue(exception.getMessage().contains("feature must not be null"), 
                  "Exception message should indicate null feature");
    }

    // 异常路径：feature为空字符串时构造函数应抛出IllegalArgumentException
    @Test
    void constructor_WithEmptyFeature_ShouldThrowIllegalArgumentException() {
        // 准备测试数据
        Class<?> validClazz = List.class;
        String emptyFeature = "";

        // 执行并验证异常
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> new NotSupportedException(validClazz, emptyFeature),
            "Expected IllegalArgumentException when feature is empty"
        );
        assertTrue(exception.getMessage().contains("feature must not be empty"), 
                  "Exception message should indicate empty feature");
    }
}