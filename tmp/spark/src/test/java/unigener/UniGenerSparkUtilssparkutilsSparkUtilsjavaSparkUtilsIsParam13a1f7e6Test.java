import spark.utils.SparkUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标方法所在类为RouteUtils
public class UniGenerSparkUtilssparkutilsSparkUtilsjavaSparkUtilsIsParam13a1f7e6Test {

    /**
     * 测试正常路径：符合参数格式（以冒号开头且长度>1）
     */
    @Test
    void isParam_ValidParamFormat_ReturnsTrue() {
        // 典型参数（字母开头）
        assertTrue(RouteUtils.isParam(":id"));
        // 参数含数字
        assertTrue(RouteUtils.isParam(":userId123"));
        // 参数含连字符
        assertTrue(RouteUtils.isParam(":order-id"));
        // 参数含下划线
        assertTrue(RouteUtils.isParam(":user_name"));
        // 参数含特殊字符（假设允许）
        assertTrue(RouteUtils.isParam(":id?"));
    }

    /**
     * 测试异常路径：不符合参数格式（非冒号开头）
     */
    @Test
    void isParam_NonColonPrefix_ReturnsFalse() {
        // 纯字母（无冒号）
        assertFalse(RouteUtils.isParam("id"));
        // 冒号在中间
        assertFalse(RouteUtils.isParam("user:id"));
        // 纯数字
        assertFalse(RouteUtils.isParam("123"));
        // 特殊字符开头（非冒号）
        assertFalse(RouteUtils.isParam("#id"));
    }

    /**
     * 测试边界值：空输入或无效长度
     */
    @Test
    void isParam_BoundaryCases_ReturnsFalse() {
        // null输入
        assertFalse(RouteUtils.isParam(null));
        // 空字符串
        assertFalse(RouteUtils.isParam(""));
        // 仅含冒号（长度=1）
        assertFalse(RouteUtils.isParam(":"));
        // 空格字符串
        assertFalse(RouteUtils.isParam("  "));
    }
}