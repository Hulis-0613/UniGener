import spark.staticfiles.StaticFilesConfiguration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkStaticfilessparkstaticfilesStaticFilesConfigurationjavaStaticFilesConfigurationCreated1de9051Test {

    /**
     * 正常路径测试：验证create()方法能成功创建StaticFilesConfiguration实例
     */
    @Test
    void create_ShouldReturnValidInstance() {
        // 执行目标方法
        StaticFilesConfiguration config = StaticFilesConfiguration.create();
        
        // 断言：返回实例不为null，且类型正确
        assertNotNull(config, "create()应返回非null的StaticFilesConfiguration实例");
        assertTrue(config instanceof StaticFilesConfiguration, "返回实例类型应为StaticFilesConfiguration");
    }

    /**
     * 异常路径测试：验证初始化失败时create()方法抛出预期异常
     * （假设场景：当关键配置缺失时，create()会抛出IllegalStateException）
     */
    @Test
    void create_WhenCriticalConfigMissing_ShouldThrowIllegalStateException() {
        // 准备：模拟关键配置缺失场景（假设create()依赖系统属性"static.files.root"）
        String originalRootPath = System.getProperty("static.files.root");
        System.clearProperty("static.files.root"); // 清除关键配置，触发异常
        
        try {
            // 执行目标方法并断言异常
            assertThrows(IllegalStateException.class, 
                StaticFilesConfiguration::create, 
                "当关键配置缺失时，create()应抛出IllegalStateException");
        } finally {
            // 清理：恢复原始系统属性，避免影响其他测试
            if (originalRootPath != null) {
                System.setProperty("static.files.root", originalRootPath);
            } else {
                System.clearProperty("static.files.root");
            }
        }
    }
}