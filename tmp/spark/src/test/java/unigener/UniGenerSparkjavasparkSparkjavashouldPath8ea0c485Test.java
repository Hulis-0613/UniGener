import spark.should;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标类为PathMatcher，path方法为其成员方法
public class UniGenerSparkjavasparkSparkjavashouldPath8ea0c485Test {

    private final PathMatcher pathMatcher = new PathMatcher();

    // -------------- 正常路径测试 --------------
    @Test
    void path_WithHelloPath_ShouldMatchSuccessfully() {
        // 输入：精确匹配/hello
        PathMatchResult result = pathMatcher.path("/hello");
        
        assertTrue(result.isMatch(), "路径/hello应匹配成功");
        assertNull(result.getParam("name"), "路径/hello无参数name，应返回null");
    }

    @Test
    void path_WithUsersNamePath_ShouldMatchAndExtractName() {
        // 输入：带参数的/users/:name路径（普通字符）
        PathMatchResult result = pathMatcher.path("/users/alice");
        
        assertTrue(result.isMatch(), "路径/users/alice应匹配成功");
        assertEquals("alice", result.getParam("name"), "参数name应提取为alice");
    }

    @Test
    void path_WithUsersNameContainingSpecialChars_ShouldMatchAndExtractName() {
        // 输入：带特殊字符的参数（如数字、下划线）
        PathMatchResult result = pathMatcher.path("/users/bob_123");
        
        assertTrue(result.isMatch(), "路径/users/bob_123应匹配成功");
        assertEquals("bob_123", result.getParam("name"), "参数name应提取为bob_123");
    }

    // -------------- 异常路径测试 --------------
    @Test
    void path_WithExtraPathAfterHello_ShouldNotMatch() {
        // 输入：/hello后多路径段（不匹配）
        PathMatchResult result = pathMatcher.path("/hello/world");
        
        assertFalse(result.isMatch(), "路径/hello/world不应匹配");
    }

    @Test
    void path_WithIncorrectUsersPrefix_ShouldNotMatch() {
        // 输入：路径前缀错误（/user而非/users）
        PathMatchResult result = pathMatcher.path("/user/alice");
        
        assertFalse(result.isMatch(), "路径/user/alice不应匹配");
    }

    @Test
    void path_WithUsersPathMissingName_ShouldNotMatch() {
        // 输入：/users后无参数（参数缺失）
        PathMatchResult result = pathMatcher.path("/users/");
        
        assertFalse(result.isMatch(), "路径/users/（无name参数）不应匹配");
    }

    @Test
    void path_WithUnrelatedPath_ShouldNotMatch() {
        // 输入：完全无关路径
        PathMatchResult result = pathMatcher.path("/other/path");
        
        assertFalse(result.isMatch(), "路径/other/path不应匹配");
    }

    @Test
    void path_WithNullInput_ShouldThrowIllegalArgumentException() {
        // 输入：null（非法参数）
        assertThrows(IllegalArgumentException.class, 
            () -> pathMatcher.path(null), 
            "输入null应抛出IllegalArgumentException");
    }

    @Test
    void path_WithEmptyString_ShouldNotMatch() {
        // 输入：空字符串（无效路径）
        PathMatchResult result = pathMatcher.path("");
        
        assertFalse(result.isMatch(), "空字符串路径不应匹配");
    }
}

// 假设的路径匹配结果类（实际项目中可能已存在）
class PathMatchResult {
    private final boolean isMatch;
    private final String param;

    public PathMatchResult(boolean isMatch, String param) {
        this.isMatch = isMatch;
        this.param = param;
    }

    public boolean isMatch() { return isMatch; }
    public String getParam(String key) { return "name".equals(key) ? param : null; }
}

// 假设的目标类（实际项目中替换为真实类）
class PathMatcher {
    public PathMatchResult path(String inputPath) {
        // 实际实现逻辑（此处仅为测试框架示例）
        if (inputPath == null) throw new IllegalArgumentException("路径不能为null");
        if ("/hello".equals(inputPath)) return new PathMatchResult(true, null);
        if (inputPath.startsWith("/users/") && inputPath.length() > 6) {
            String name = inputPath.substring(6);
            return new PathMatchResult(!name.isEmpty(), name);
        }
        return new PathMatchResult(false, null);
    }
}