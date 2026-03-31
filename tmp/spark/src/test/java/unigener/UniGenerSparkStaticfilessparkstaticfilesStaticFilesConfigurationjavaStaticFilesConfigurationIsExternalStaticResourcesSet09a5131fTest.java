import spark.staticfiles.StaticFilesConfiguration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkStaticfilessparkstaticfilesStaticFilesConfigurationjavaStaticFilesConfigurationIsExternalStaticResourcesSet09a5131fTest {

    @Test
    void isExternalStaticResourcesSet_WhenExternalResourcesConfigured_ReturnsTrue() {
        // 准备：创建配置实例并设置外部静态资源路径
        StaticFilesConfiguration config = new StaticFilesConfiguration();
        config.setExternalStaticResourcesPath("/opt/static-resources"); // 假设存在设置外部资源的setter方法
        
        // 执行目标方法
        boolean result = config.isExternalStaticResourcesSet();
        
        // 断言：已配置时返回true
        assertTrue(result, "外部静态资源已设置时应返回true");
    }

    @Test
    void isExternalStaticResourcesSet_WhenExternalResourcesNotConfigured_ReturnsFalse() {
        // 准备：创建配置实例，不设置外部静态资源（默认状态）
        StaticFilesConfiguration config = new StaticFilesConfiguration();
        // 显式设置为null（模拟未配置场景，若默认已为null可省略）
        config.setExternalStaticResourcesPath(null);
        
        // 执行目标方法
        boolean result = config.isExternalStaticResourcesSet();
        
        // 断言：未配置时返回false
        assertFalse(result, "外部静态资源未设置时应返回false");
    }

    @Test
    void isExternalStaticResourcesSet_WhenExternalResourcesPathEmpty_ReturnsFalse() {
        // 准备：创建配置实例，设置空字符串路径（边界场景）
        StaticFilesConfiguration config = new StaticFilesConfiguration();
        config.setExternalStaticResourcesPath("");
        
        // 执行目标方法
        boolean result = config.isExternalStaticResourcesSet();
        
        // 断言：空路径视为未设置
        assertFalse(result, "外部静态资源路径为空时应返回false");
    }
}