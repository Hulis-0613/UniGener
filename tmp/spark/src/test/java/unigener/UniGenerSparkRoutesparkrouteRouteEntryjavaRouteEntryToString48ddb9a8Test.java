import spark.route.RouteEntry;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniGenerSparkRoutesparkrouteRouteEntryjavaRouteEntryToString48ddb9a8Test {

    /**
     * 测试所有字段为非null正常值时的toString输出
     */
    @Test
    void toString_AllFieldsNonNull_ReturnsCorrectString() {
        // 准备测试数据
        RouteEntry routeEntry = new RouteEntry(
            "route-001",       // id
            "HomePageRoute",   // name
            "/api/home",       // path
            true,              // active
            3                  // priority
        );

        // 预期字符串（格式需与实际toString实现匹配，此处假设标准字段拼接格式）
        String expected = "RouteEntry{id='route-001', name='HomePageRoute', path='/api/home', active=true, priority=3}";

        // 执行测试并断言
        assertEquals(expected, routeEntry.toString());
    }

    /**
     * 测试部分字段为null时的toString输出（验证null值处理）
     */
    @Test
    void toString_PartialFieldsNull_ReturnsCorrectStringWithNull() {
        // 准备测试数据（name和path为null）
        RouteEntry routeEntry = new RouteEntry(
            "route-002",       // id
            null,              // name (null)
            null,              // path (null)
            false,             // active
            -1                 // priority (负数)
        );

        // 预期字符串（null字段显示"null"）
        String expected = "RouteEntry{id='route-002', name='null', path='null', active=false, priority=-1}";

        // 执行测试并断言
        assertEquals(expected, routeEntry.toString());
    }

    /**
     * 测试空字符串字段时的toString输出
     */
    @Test
    void toString_EmptyStringFields_ReturnsCorrectStringWithEmpty() {
        // 准备测试数据（name为空字符串，path为空字符串）
        RouteEntry routeEntry = new RouteEntry(
            "",                // id (空字符串)
            "",                // name (空字符串)
            "",                // path (空字符串)
            true,              // active
            0                  // priority (0)
        );

        // 预期字符串（空字符串字段显示空引号）
        String expected = "RouteEntry{id='', name='', path='', active=true, priority=0}";

        // 执行测试并断言
        assertEquals(expected, routeEntry.toString());
    }

    /**
     * 测试包含特殊字符字段时的toString输出（验证特殊字符处理）
     */
    @Test
    void toString_SpecialCharactersInFields_ReturnsCorrectString() {
        // 准备测试数据（name包含空格、逗号、引号等特殊字符）
        RouteEntry routeEntry = new RouteEntry(
            "route-004",
            "User's Route, v1.0",  // 包含单引号、逗号、空格
            "/api/user?name=test&age=20",  // 包含问号、等号、&符号
            true,
            10
        );

        // 预期字符串（特殊字符原样保留）
        String expected = "RouteEntry{id='route-004', name='User's Route, v1.0', path='/api/user?name=test&age=20', active=true, priority=10}";

        // 执行测试并断言
        assertEquals(expected, routeEntry.toString());
    }
}