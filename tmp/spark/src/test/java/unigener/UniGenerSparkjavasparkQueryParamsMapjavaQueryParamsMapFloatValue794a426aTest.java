import spark.QueryParamsMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkQueryParamsMapjavaQueryParamsMapFloatValue794a426aTest {

    // 测试正常路径：存在有效Float值，返回正确结果
    @Test
    public void floatValue_WithValidFloatValue_ReturnsCorrectFloat() {
        // 准备：创建QueryParamsMap实例并设置有效Float字符串值
        QueryParamsMap queryParams = new QueryParamsMap();
        queryParams.put("floatParam", "123.45"); // 假设内部通过"floatParam"键获取值
        
        // 执行：调用floatValue方法
        Float result = queryParams.floatValue();
        
        // 断言：结果与预期Float值一致
        assertEquals(123.45f, result);
    }

    // 异常路径1：键不存在，返回null
    @Test
    public void floatValue_WhenKeyNotPresent_ReturnsNull() {
        // 准备：创建QueryParamsMap实例，不设置目标键
        QueryParamsMap queryParams = new QueryParamsMap();
        
        // 执行：调用floatValue方法
        Float result = queryParams.floatValue();
        
        // 断言：结果为null
        assertNull(result);
    }

    // 异常路径2：值为null，返回null
    @Test
    public void floatValue_WithNullValue_ReturnsNull() {
        // 准备：创建QueryParamsMap实例，设置目标键值为null
        QueryParamsMap queryParams = new QueryParamsMap();
        queryParams.put("floatParam", null);
        
        // 执行：调用floatValue方法
        Float result = queryParams.floatValue();
        
        // 断言：结果为null
        assertNull(result);
    }

    // 异常路径3：值无法转换为Float，抛出NumberFormatException
    @Test
    public void floatValue_WithInvalidFloatString_ThrowsNumberFormatException() {
        // 准备：创建QueryParamsMap实例，设置无法转换为Float的字符串值
        QueryParamsMap queryParams = new QueryParamsMap();
        queryParams.put("floatParam", "invalid_float_123");
        
        // 执行&断言：调用floatValue时抛出NumberFormatException
        assertThrows(NumberFormatException.class, queryParams::floatValue);
    }
}