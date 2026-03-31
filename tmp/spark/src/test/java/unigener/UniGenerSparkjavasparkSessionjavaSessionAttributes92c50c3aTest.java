import spark.Session;
import org.junit.jupiter.api.Test;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSessionjavaSessionAttributes92c50c3aTest {

    /**
     * 正常路径：Session已初始化且包含属性时，返回正确的属性映射
     */
    @Test
    void attributes_WithInitializedSessionAndAttributes_ReturnsCorrectMap() {
        // 准备：创建Session并初始化，添加测试属性
        Session session = new Session();
        session.initialize(); // 假设Session需显式初始化
        String expectedName = "testUser";
        int expectedId = 123;
        session.setAttribute("name", expectedName);
        session.setAttribute("id", expectedId);

        // 执行：调用attributes()方法
        Map<String, Object> result = session.attributes();

        // 断言：结果非空、包含预期属性及值
        assertNotNull(result, "属性映射不应为null");
        assertEquals(2, result.size(), "属性数量不匹配");
        assertEquals(expectedName, result.get("name"), "name属性值不匹配");
        assertEquals(expectedId, result.get("id"), "id属性值不匹配");
    }

    /**
     * 正常路径：Session已初始化但无属性时，返回空映射
     */
    @Test
    void attributes_WithInitializedSessionNoAttributes_ReturnsEmptyMap() {
        // 准备：创建Session并初始化，不添加属性
        Session session = new Session();
        session.initialize();

        // 执行：调用attributes()方法
        Map<String, Object> result = session.attributes();

        // 断言：结果非空且为空映射
        assertNotNull(result, "属性映射不应为null");
        assertTrue(result.isEmpty(), "无属性时应返回空映射");
    }

    /**
     * 异常路径：Session未初始化时，调用attributes()抛出IllegalStateException
     */
    @Test
    void attributes_WithUninitializedSession_ThrowsIllegalStateException() {
        // 准备：创建未初始化的Session（不调用initialize()）
        Session session = new Session();

        // 执行&断言：验证抛出预期异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            session::attributes,
            "未初始化的Session调用attributes()应抛出IllegalStateException"
        );
        assertTrue(exception.getMessage().contains("Session not initialized"), "异常消息不符合预期");
    }
}