import spark.Request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRequestjavaRequestMatchedPathe66da8a8Test {

    // 正常场景：路径已设置且为常规有效路径
    @Test
    @DisplayName("matchedPath when valid path is set returns correct path")
    void matchedPath_ValidPathSet_ReturnsPath() {
        // 假设Request通过构造函数设置路径
        Request request = new Request("/api/v1/users");
        String result = request.matchedPath();
        assertEquals("/api/v1/users", result, "Should return the set path");
    }

    // 正常场景：路径为根路径"/"
    @Test
    @DisplayName("matchedPath when root path is set returns root")
    void matchedPath_RootPathSet_ReturnsRoot() {
        Request request = new Request("/");
        String result = request.matchedPath();
        assertEquals("/", result, "Should return root path");
    }

    // 正常场景：路径包含路径参数（如{id}）
    @Test
    @DisplayName("matchedPath with parameterized path returns original path")
    void matchedPath_ParameterizedPathSet_ReturnsOriginal() {
        Request request = new Request("/api/v1/users/{id}/profile");
        String result = request.matchedPath();
        assertEquals("/api/v1/users/{id}/profile", result, "Should return parameterized path");
    }

    // 异常场景：路径未设置（null）
    @Test
    @DisplayName("matchedPath when path is null returns null")
    void matchedPath_PathNull_ReturnsNull() {
        Request request = new Request(null); // 假设构造函数允许null路径
        String result = request.matchedPath();
        assertNull(result, "Should return null when path is not set");
    }

    // 异常场景：路径为空字符串
    @Test
    @DisplayName("matchedPath when path is empty returns empty string")
    void matchedPath_PathEmpty_ReturnsEmpty() {
        Request request = new Request("");
        String result = request.matchedPath();
        assertEquals("", result, "Should return empty string when path is empty");
    }
}