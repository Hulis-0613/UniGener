import spark.utils.MimeParse;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

public class UniGenerSparkUtilssparkutilsMimeParsejavaMimeParseToString204a20daTest {

    /**
     * 测试正常路径：所有属性（type、subtype、parameters）均非空且有值
     * 验证toString返回包含完整类型、子类型及参数的格式化字符串
     */
    @Test
    void toString_WithAllFieldsPopulated_ReturnsFormattedString() {
        // Arrange：创建包含type、subtype和多参数的MimeParse对象
        Map<String, String> parameters = new HashMap<>();
        parameters.put("charset", "UTF-8");
        parameters.put("boundary", "mime-boundary-123");
        MimeParse mimeParse = new MimeParse("text", "plain", parameters);

        // Act：调用toString方法
        String result = mimeParse.toString();

        // Assert：验证结果格式（类型/子类型; 参数1=值1; 参数2=值2）
        assertEquals("text/plain; charset=UTF-8; boundary=mime-boundary-123", result);
    }

    /**
     * 测试正常路径：parameters为空集合（无参数）
     * 验证toString仅返回类型和子类型，不含参数部分
     */
    @Test
    void toString_WithEmptyParameters_ReturnsTypeSubtypeOnly() {
        // Arrange：创建parameters为空的MimeParse对象
        MimeParse mimeParse = new MimeParse("application", "json", new HashMap<>());

        // Act：调用toString方法
        String result = mimeParse.toString();

        // Assert：验证结果仅包含类型/子类型
        assertEquals("application/json", result);
    }

    /**
     * 测试异常路径：type为null（关键属性缺失）
     * 验证toString调用时抛出NullPointerException
     */
    @Test
    void toString_WithNullType_ThrowsNullPointerException() {
        // Arrange：创建type为null的MimeParse对象（subtype和parameters非空）
        MimeParse mimeParse = new MimeParse(null, "xml", new HashMap<>());

        // Act & Assert：验证调用toString时抛出NPE
        assertThrows(NullPointerException.class, mimeParse::toString);
    }

    /**
     * 测试异常路径：subtype为null（关键属性缺失）
     * 验证toString调用时抛出NullPointerException
     */
    @Test
    void toString_WithNullSubtype_ThrowsNullPointerException() {
        // Arrange：创建subtype为null的MimeParse对象（type和parameters非空）
        MimeParse mimeParse = new MimeParse("application", null, new HashMap<>());

        // Act & Assert：验证调用toString时抛出NPE
        assertThrows(NullPointerException.class, mimeParse::toString);
    }

    /**
     * 测试边界路径：parameters为null（而非空集合）
     * 验证toString处理null参数时返回类型和子类型，不抛出异常
     */
    @Test
    void toString_WithNullParameters_ReturnsTypeSubtypeOnly() {
        // Arrange：创建parameters为null的MimeParse对象
        MimeParse mimeParse = new MimeParse("image", "png", null);

        // Act：调用toString方法
        String result = mimeParse.toString();

        // Assert：验证结果仅包含类型/子类型（忽略null参数）
        assertEquals("image/png", result);
    }
}