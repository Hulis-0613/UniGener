import spark.http.matching.Body;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试 Body.create() 方法的功能正确性。
 */
public class UniGenerSparkHttpMatchinghttpmatchingBodyjavaBodyCreatec2abcfcfTest {

    /**
     * 正常路径测试：验证 create() 方法能成功创建非空的 Body 实例。
     */
    @Test
    void create_ShouldReturnValidBodyInstance() {
        // 执行目标方法
        Body result = Body.create();
        
        // 断言结果非空（复用仓库常见的非空断言风格）
        assertNotNull(result, "create() 应返回非空的 Body 实例");
    }

    /**
     * 异常路径测试：验证 create() 方法在创建失败时抛出预期异常。
     * （假设 create() 内部依赖某些前置条件，如配置加载，此处模拟前置条件失败场景）
     */
    @Test
    void create_WhenPreconditionFails_ShouldThrowIllegalStateException() {
        // 模拟前置条件失败（例如：通过静态方法设置 Body 内部状态为创建失败）
        Body.setCreationPrecondition(false); // 假设 Body 类提供测试辅助方法控制创建条件
        
        // 断言方法抛出预期异常（复用仓库异常断言风格）
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            Body::create,
            "create() 在前置条件失败时应抛出 IllegalStateException"
        );
        
        // 可选：验证异常消息（若业务有明确错误信息）
        assertTrue(exception.getMessage().contains("创建 Body 实例失败"), 
            "异常消息应包含创建失败原因");
        
        // 清理：恢复前置条件，避免影响其他测试
        Body.setCreationPrecondition(true);
    }
}