import spark.utils.SparkUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试路由部分是否为splat的工具方法
 */
public class UniGenerSparkUtilssparkutilsSparkUtilsjavaSparkUtilsIsSplat7f2206aaTest {

    /**
     * 测试null输入：应返回false
     */
    @Test
    void isSplat_WithNull_ReturnsFalse() {
        assertFalse(RouteUtils.isSplat(null), "null路由部分不应被判定为splat");
    }

    /**
     * 测试空字符串输入：应返回false
     */
    @Test
    void isSplat_WithEmptyString_ReturnsFalse() {
        assertFalse(RouteUtils.isSplat(""), "空字符串路由部分不应被判定为splat");
    }

    /**
     * 测试非*开头的路由部分：应返回false
     */
    @Test
    void isSplat_WithNonStarPrefix_ReturnsFalse() {
        // 普通字符串（无*）
        assertFalse(RouteUtils.isSplat("user"), "不含*的路由部分不应被判定为splat");
        // *在中间
        assertFalse(RouteUtils.isSplat("path*info"), "*在中间的路由部分不应被判定为splat");
        // *在末尾
        assertFalse(RouteUtils.isSplat("api*"), "*在末尾的路由部分不应被判定为splat");
        // 前有空格（非*开头）
        assertFalse(RouteUtils.isSplat(" *splat"), "空格+*开头的路由部分不应被判定为splat");
    }

    /**
     * 测试*开头的有效splat路由部分：应返回true
     */
    @Test
    void isSplat_WithStarPrefix_ReturnsTrue() {
        // 单个*
        assertTrue(RouteUtils.isSplat("*"), "单个*应被判定为splat");
        // 双**（多级通配符）
        assertTrue(RouteUtils.isSplat("**"), "双**应被判定为splat");
        // *+字母
        assertTrue(RouteUtils.isSplat("*path"), "*+字母的路由部分应被判定为splat");
        // *+数字
        assertTrue(RouteUtils.isSplat("*123"), "*+数字的路由部分应被判定为splat");
        // *+特殊字符
        assertTrue(RouteUtils.isSplat("*_user"), "*+特殊字符的路由部分应被判定为splat");
    }
}