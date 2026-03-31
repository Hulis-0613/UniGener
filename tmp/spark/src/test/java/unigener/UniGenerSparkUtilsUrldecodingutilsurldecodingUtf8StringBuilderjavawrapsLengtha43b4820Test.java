import spark.utils.urldecoding.wraps;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilsUrldecodingutilsurldecodingUtf8StringBuilderjavawrapsLengtha43b4820Test {

    /**
     * 测试场景：内部状态为非空内容时，length方法返回正确长度
     */
    @Test
    void length_WithNonEmptyContent_ReturnsCorrectLength() {
        // 假设wraps类通过构造函数初始化内部状态（如字符串、集合等）
        wraps wrapsObj = new wraps("hello"); // 示例：内部状态为"hello"（长度5）
        int actualLength = wrapsObj.length();
        assertEquals(5, actualLength, "非空内容时长度应等于内容实际长度");
    }

    /**
     * 测试场景：内部状态为空内容时，length方法返回0
     */
    @Test
    void length_WithEmptyContent_ReturnsZero() {
        wraps wrapsObj = new wraps(""); // 示例：内部状态为空字符串（长度0）
        int actualLength = wrapsObj.length();
        assertEquals(0, actualLength, "空内容时长度应返回0");
    }

    /**
     * 测试场景：内部状态为null时，length方法抛出NullPointerException
     */
    @Test
    void length_WithNullContent_ThrowsNullPointerException() {
        wraps wrapsObj = new wraps(null); // 示例：内部状态为null
        assertThrows(NullPointerException.class, 
            () -> wrapsObj.length(), 
            "内部状态为null时应抛出NullPointerException");
    }
}