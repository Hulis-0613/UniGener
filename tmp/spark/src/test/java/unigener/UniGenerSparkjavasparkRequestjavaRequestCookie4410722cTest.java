import spark.Request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRequestjavaRequestCookie4410722cTest {

    /**
     * 测试正常路径：存在指定名称的cookie时，返回正确值
     */
    @Test
    void cookie_WithExistingName_ReturnsCookieValue() {
        // 准备测试环境：创建Request实例并添加测试cookie
        Request request = new Request();
        request.addCookie("sessionId", "abc123xyz"); // 假设Request有添加cookie的方法
        
        // 执行目标方法
        String actualValue = request.cookie("sessionId");
        
        // 断言结果与预期一致
        assertEquals("abc123xyz", actualValue, "存在的cookie应返回正确值");
    }

    /**
     * 测试异常路径：不存在指定名称的cookie时，返回null
     */
    @Test
    void cookie_WithNonExistingName_ReturnsNull() {
        // 准备测试环境：创建Request实例（不添加目标cookie）
        Request request = new Request();
        
        // 执行目标方法（查询不存在的cookie）
        String actualValue = request.cookie("nonExistentCookie");
        
        // 断言结果为null
        assertNull(actualValue, "不存在的cookie应返回null");
    }

    /**
     * 测试异常路径：输入名称为null时，返回null
     */
    @Test
    void cookie_WithNullName_ReturnsNull() {
        // 准备测试环境：创建Request实例
        Request request = new Request();
        
        // 执行目标方法（传入null名称）
        String actualValue = request.cookie(null);
        
        // 断言结果为null
        assertNull(actualValue, "名称为null时应返回null");
    }

    /**
     * 测试异常路径：输入名称为空字符串时，返回null
     */
    @Test
    void cookie_WithEmptyName_ReturnsNull() {
        // 准备测试环境：创建Request实例
        Request request = new Request();
        
        // 执行目标方法（传入空字符串名称）
        String actualValue = request.cookie("");
        
        // 断言结果为null
        assertNull(actualValue, "名称为空字符串时应返回null");
    }
}