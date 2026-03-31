import spark.http.matching.ResponseWrapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

public class UniGenerSparkHttpMatchinghttpmatchingResponseWrapperjavaResponseWrapperCreatee9657859Test {

    // 正常路径：测试create方法成功创建并返回有效实例
    @Test
    void create_ShouldReturnInitializedResponseWrapper() {
        // 假设create方法依赖静态初始化，先确保初始化完成（若需）
        ResponseWrapper.initialize(); // 假设有初始化方法，根据实际实现调整
        
        // 执行目标方法
        ResponseWrapper response = ResponseWrapper.create();
        
        // 断言实例非空且默认属性正确（根据实际属性调整断言）
        assertNotNull(response, "create() should return non-null ResponseWrapper");
        assertEquals(200, response.getCode(), "Default code should be 200");
        assertTrue(response.isSuccess(), "Default success status should be true");
        assertEquals("Operation successful", response.getMessage(), "Default message mismatch");
    }

    // 异常路径：测试未初始化时调用create方法抛出异常
    @Test
    void create_WhenNotInitialized_ShouldThrowIllegalStateException() throws Exception {
        // 反射重置静态初始化状态（假设存在静态变量"initialized"控制初始化）
        Field initializedField = ResponseWrapper.class.getDeclaredField("initialized");
        initializedField.setAccessible(true);
        initializedField.set(null, false); // 强制设为未初始化状态

        // 断言调用create时抛出预期异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            ResponseWrapper::create, 
            "create() should throw IllegalStateException when not initialized");
        
        assertEquals("ResponseWrapper not initialized. Call initialize() first.", 
            exception.getMessage(), "Exception message mismatch");
    }
}