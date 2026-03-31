import spark.utils.urldecoding.wraps;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilsUrldecodingutilsurldecodingUtf8StringBuilderjavawrapsUtf8StringBuilder6f274a8cTest {

    @Test
    void testConstructorWithPositiveCapacity() {
        // 正常路径：正整数初始容量
        int expectedCapacity = 1024;
        Utf8StringBuilder builder = new Utf8StringBuilder(expectedCapacity);
        assertEquals(expectedCapacity, builder.capacity(), "初始容量应等于指定的正整数");
    }

    @Test
    void testConstructorWithZeroCapacity() {
        // 正常路径：零初始容量
        int expectedCapacity = 0;
        Utf8StringBuilder builder = new Utf8StringBuilder(expectedCapacity);
        assertEquals(expectedCapacity, builder.capacity(), "初始容量为零时应正确初始化");
    }

    @Test
    void testConstructorWithNegativeCapacity() {
        // 异常路径：负初始容量
        int invalidCapacity = -1;
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> new Utf8StringBuilder(invalidCapacity),
                "当传入负容量时应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("capacity"), "异常消息应包含容量相关提示");
    }
}