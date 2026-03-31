import spark.QueryParamsMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkQueryParamsMapjavaQueryParamsMapIntegerValue66cd90e5Test {

    private QueryParamsMap queryParamsMap;

    @BeforeEach
    void setUp() {
        // 初始化测试对象，确保每个测试方法独立
        queryParamsMap = new QueryParamsMap();
    }

    /**
     * 正常路径：存在有效的整数值，验证返回正确结果
     */
    @Test
    void integerValue_WithValidIntegerValue_ReturnsExpectedValue() {
        // 假设QueryParamsMap通过put方法设置内部参数（根据实际实现调整key）
        queryParamsMap.put("targetKey", 42); // 模拟设置有效的整数值
        
        int result = queryParamsMap.integerValue();
        
        assertEquals(42, result, "应返回设置的整数值42");
    }

    /**
     * 异常路径1：未设置任何值，验证抛出状态异常
     */
    @Test
    void integerValue_WithoutAnyValue_ThrowsIllegalStateException() {
        // 不设置任何参数，直接调用方法
        
        Exception exception = assertThrows(IllegalStateException.class, 
            () -> queryParamsMap.integerValue(), 
            "未设置值时应抛出IllegalStateException"
        );
        
        assertTrue(exception.getMessage().contains("未找到整数值"), "异常信息应提示值不存在");
    }

    /**
     * 异常路径2：设置非整数类型值（如字符串），验证抛出格式转换异常
     */
    @Test
    void integerValue_WithNonIntegerValue_ThrowsNumberFormatException() {
        // 设置非整数类型的值（如字符串"notANumber"）
        queryParamsMap.put("targetKey", "notANumber");
        
        Exception exception = assertThrows(NumberFormatException.class, 
            () -> queryParamsMap.integerValue(), 
            "非整数类型值应抛出NumberFormatException"
        );
        
        assertTrue(exception.getMessage().contains("无法转换为整数"), "异常信息应提示转换失败");
    }

    /**
     * 异常路径3：设置空值（null），验证抛出空指针异常
     */
    @Test
    void integerValue_WithNullValue_ThrowsNullPointerException() {
        // 设置null值
        queryParamsMap.put("targetKey", null);
        
        Exception exception = assertThrows(NullPointerException.class, 
            () -> queryParamsMap.integerValue(), 
            "空值应抛出NullPointerException"
        );
        
        assertTrue(exception.getMessage().contains("值为null"), "异常信息应提示空值");
    }
}