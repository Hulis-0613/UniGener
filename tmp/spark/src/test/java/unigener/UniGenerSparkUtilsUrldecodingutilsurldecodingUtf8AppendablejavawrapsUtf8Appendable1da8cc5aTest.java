import spark.utils.urldecoding.wraps;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.StringWriter;
import java.lang.Appendable;

public class UniGenerSparkUtilsUrldecodingutilsurldecodingUtf8AppendablejavawrapsUtf8Appendable1da8cc5aTest {

    /**
     * 测试正常路径：传入非null的Appendable实例，构造方法应成功初始化
     */
    @Test
    void testConstructorWithValidAppendable() {
        // 准备有效Appendable实例（如StringWriter，实现了Appendable接口）
        Appendable validAppendable = new StringWriter();
        
        // 执行构造方法，断言无异常抛出
        assertDoesNotThrow(() -> new Utf8Appendable(validAppendable), 
            "构造方法应接受非null Appendable并成功初始化");
    }

    /**
     * 测试异常路径：传入null的Appendable，构造方法应抛出NullPointerException
     */
    @Test
    void testConstructorWithNullAppendable() {
        // 执行构造方法，断言抛出NullPointerException
        NullPointerException exception = assertThrows(NullPointerException.class, 
            () -> new Utf8Appendable(null), 
            "构造方法应在Appendable为null时抛出NullPointerException");
        
        // 可选：验证异常消息（若构造方法有明确消息）
        assertTrue(exception.getMessage().contains("appendable"), 
            "异常消息应包含参数名'appendable'");
    }
}