import spark.should;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSparkjavashouldGetInstancea4db15c4Test {

    // 正常路径：首次调用返回非null实例
    @Test
    void getInstance_ShouldReturnNonNullInstance() {
        Service instance = Service.getInstance();
        assertNotNull(instance, "Service实例不应为null");
    }

    // 正常路径：多次调用返回同一实例（单例特性）
    @Test
    void getInstance_MultipleCalls_ShouldReturnSameInstance() {
        Service instance1 = Service.getInstance();
        Service instance2 = Service.getInstance();
        assertSame(instance1, instance2, "多次调用应返回同一实例");
    }

    // 异常路径：模拟初始化失败场景（如依赖缺失）
    @Test
    void getInstance_WhenInitializationFails_ShouldThrowException() {
        // 保存原始环境配置，测试后恢复
        String originalConfig = System.getProperty("service.init.config");
        try {
            // 清除关键配置，触发初始化失败
            System.clearProperty("service.init.config");
            
            // 验证抛出预期异常（假设初始化失败时抛IllegalStateException）
            IllegalStateException exception = assertThrows(
                IllegalStateException.class, 
                Service::getInstance, 
                "初始化失败时应抛出IllegalStateException"
            );
            assertTrue(exception.getMessage().contains("初始化失败"), "异常信息应包含初始化失败原因");
        } finally {
            // 恢复原始配置，避免影响其他测试
            if (originalConfig != null) {
                System.setProperty("service.init.config", originalConfig);
            } else {
                System.clearProperty("service.init.config");
            }
        }
    }
}