import spark.Request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRequestjavaRequestUri175b75faTest {

    // 正常路径：URI为有效非空字符串
    @Test
    void uri_WithValidNonEmptyUri_ReturnsCorrectUri() {
        // 准备：创建设置了有效URI的Request实例
        String expectedUri = "/api/v1/users";
        Request request = new Request(expectedUri); // 假设构造函数接收URI参数
        
        // 执行：调用uri方法
        String actualUri = request.uri();
        
        // 断言：返回的URI与预期一致
        assertEquals(expectedUri, actualUri, "URI应返回设置的有效字符串");
    }

    // 异常路径：URI为null
    @Test
    void uri_WithNullUri_ReturnsNull() {
        // 准备：创建URI为null的Request实例
        Request request = new Request(null);
        
        // 执行：调用uri方法
        String actualUri = request.uri();
        
        // 断言：返回null
        assertNull(actualUri, "URI为null时应返回null");
    }

    // 边界路径：URI为空字符串
    @Test
    void uri_WithEmptyUri_ReturnsEmptyString() {
        // 准备：创建URI为空字符串的Request实例
        String expectedUri = "";
        Request request = new Request(expectedUri);
        
        // 执行：调用uri方法
        String actualUri = request.uri();
        
        // 断言：返回空字符串
        assertEquals(expectedUri, actualUri, "URI为空字符串时应返回空字符串");
    }

    // 特殊场景：URI包含特殊字符（如查询参数、路径分隔符等）
    @Test
    void uri_WithSpecialCharacters_ReturnsCorrectUri() {
        // 准备：创建包含特殊字符的URI的Request实例
        String expectedUri = "/path?query=123&name=测试#fragment";
        Request request = new Request(expectedUri);
        
        // 执行：调用uri方法
        String actualUri = request.uri();
        
        // 断言：返回包含特殊字符的完整URI
        assertEquals(expectedUri, actualUri, "含特殊字符的URI应完整返回");
    }
}