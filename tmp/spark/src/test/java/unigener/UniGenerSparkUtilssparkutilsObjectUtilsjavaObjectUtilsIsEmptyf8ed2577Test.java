import spark.utils.ObjectUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标函数所在类为ArrayUtils（根据实际类名调整）
public class UniGenerSparkUtilssparkutilsObjectUtilsjavaObjectUtilsIsEmptyf8ed2577Test {

    /**
     * 测试场景：输入数组为null时，isEmpty应返回true
     */
    @Test
    void isEmpty_WithNullArray_ReturnsTrue() {
        Object[] array = null;
        boolean result = ArrayUtils.isEmpty(array);
        assertTrue(result, "数组为null时应判定为空");
    }

    /**
     * 测试场景：输入数组长度为0时，isEmpty应返回true
     */
    @Test
    void isEmpty_WithEmptyArray_ReturnsTrue() {
        Object[] array = new Object[0]; // 长度为0的空数组
        boolean result = ArrayUtils.isEmpty(array);
        assertTrue(result, "长度为0的数组应判定为空");
    }

    /**
     * 测试场景：输入数组长度大于0时，isEmpty应返回false
     */
    @Test
    void isEmpty_WithNonEmptyArray_ReturnsFalse() {
        Object[] array = new Object[]{"element"}; // 长度为1的非空数组
        boolean result = ArrayUtils.isEmpty(array);
        assertFalse(result, "长度大于0的数组应判定为非空");
    }
}