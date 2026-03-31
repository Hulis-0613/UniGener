import spark.Request;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkRequestjavaRequestQueryStringd3bb09b4Test {

    /**
     * 测试场景：URL包含正常查询参数时，返回正确的查询字符串
     */
    @Test
    void queryString_WithValidParameters_ReturnsCorrectQuery() {
        // 构造包含查询参数的Request（假设Request通过URL构造）
        Request request = new Request("https://example.com/api?name=alice&age=30&city=shanghai");
        
        // 执行目标方法
        String actualQuery = request.queryString();
        
        // 断言结果与预期一致
        assertEquals("name=alice&age=30&city=shanghai", actualQuery);
    }

    /**
     * 测试场景：URL无查询参数（不含"?"）时，返回null
     */
    @Test
    void queryString_WithoutQuestionMark_ReturnsNull() {
        // 构造无查询参数的URL（不含"?"）
        Request request = new Request("https://example.com/api/users");
        
        // 执行目标方法
        String actualQuery = request.queryString();
        
        // 断言结果为null
        assertNull(actualQuery);
    }

    /**
     * 测试场景：URL含"?"但无参数（空查询字符串）时，返回空字符串
     */
    @Test
    void queryString_WithQuestionMarkButNoParameters_ReturnsEmptyString() {
        // 构造含"?"但无参数的URL
        Request request = new Request("https://example.com/api?");
        
        // 执行目标方法
        String actualQuery = request.queryString();
        
        // 断言结果为空字符串
        assertEquals("", actualQuery);
    }

    /**
     * 测试场景：URL含多个"?"时，返回第一个"?"后的所有内容（符合URL规范）
     */
    @Test
    void queryString_WithMultipleQuestionMarks_ReturnsFirstQueryPart() {
        // 构造含多个"?"的URL（实际URL中仅第一个"?"后为查询字符串）
        Request request = new Request("https://example.com/api?page=1?size=10&sort=desc");
        
        // 执行目标方法
        String actualQuery = request.queryString();
        
        // 断言结果为第一个"?"后的内容（包含后续"?"）
        assertEquals("page=1?size=10&sort=desc", actualQuery);
    }
}