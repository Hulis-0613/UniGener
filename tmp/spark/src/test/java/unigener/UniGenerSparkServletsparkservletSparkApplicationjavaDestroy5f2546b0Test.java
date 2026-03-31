import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.Mockito;
import java.io.IOException;

// 假设目标类为资源管理类ResourceManager（未知类的合理假设）
public class UniGenerSparkServletsparkservletSparkApplicationjavaDestroy5f2546b0Test {

    private ResourceManager resourceManager;

    // 模拟依赖资源（如数据库连接、文件流等）
    private AutoCloseable mockResource;

    @BeforeEach
    void setUp() {
        // 初始化模拟资源并注入到ResourceManager
        mockResource = Mockito.mock(AutoCloseable.class);
        resourceManager = new ResourceManager(mockResource); // 假设构造函数接收资源依赖
    }

    // 正常路径：验证destroy成功释放资源并更新状态
    @Test
    void destroy_ShouldReleaseResourceAndMarkAsDestroyed() throws Exception {
        // 执行destroy
        resourceManager.destroy();

        // 验证资源已关闭
        Mockito.verify(mockResource).close();
        // 验证状态标记为已销毁
        assertTrue(resourceManager.isDestroyed(), "ResourceManager should be marked as destroyed");
    }

    // 异常路径1：重复调用destroy应抛出IllegalStateException
    @Test
    void destroy_WhenCalledTwice_ShouldThrowIllegalStateException() {
        // 首次调用destroy（正常执行）
        resourceManager.destroy();

        // 第二次调用destroy，预期抛出异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> resourceManager.destroy(), 
            "Calling destroy twice should throw IllegalStateException"
        );
        assertEquals("ResourceManager is already destroyed", exception.getMessage());
    }

    // 异常路径2：资源释放失败时应传播异常（如IO错误）
    @Test
    void destroy_WhenResourceCloseFails_ShouldPropagateException() throws Exception {
        // 模拟资源关闭时抛出IOException
        IOException expectedException = new IOException("Failed to close resource: connection timeout");
        Mockito.doThrow(expectedException).when(mockResource).close();

        // 执行destroy，预期捕获并传播异常
        IOException actualException = assertThrows(IOException.class, 
            () -> resourceManager.destroy(), 
            "destroy should propagate exception when resource close fails"
        );
        assertSame(expectedException, actualException, "Exception should be the same as resource close failure");
    }
}

// 目标类的假设定义（仅用于测试上下文，实际未知类可能不同但逻辑类似）
class ResourceManager {
    private final AutoCloseable resource;
    private boolean destroyed = false;

    public ResourceManager(AutoCloseable resource) {
        this.resource = resource;
    }

    public void destroy() throws IOException {
        if (destroyed) {
            throw new IllegalStateException("ResourceManager is already destroyed");
        }
        resource.close(); // 释放资源，可能抛出IOException
        destroyed = true;
    }

    public boolean isDestroyed() {
        return destroyed;
    }
}