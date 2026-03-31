import spark.staticfiles.StaticFilesConfiguration;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkStaticfilessparkstaticfilesStaticFilesConfigurationjavaStaticFilesConfigurationIsStaticResourcesSet7247ed5dTest {

    /**
     * 测试场景：静态资源未设置时，isStaticResourcesSet应返回false
     */
    @Test
    void isStaticResourcesSet_WhenStaticResourcesNotSet_ReturnsFalse() {
        // 初始化未设置静态资源的配置对象（假设默认构造函数下静态资源为未设置状态）
        StaticFilesConfiguration config = new StaticFilesConfiguration();
        
        // 断言方法返回false
        assertFalse(config.isStaticResourcesSet(), "静态资源未设置时，isStaticResourcesSet应返回false");
    }

    /**
     * 测试场景：静态资源已设置时，isStaticResourcesSet应返回true
     */
    @Test
    void isStaticResourcesSet_WhenStaticResourcesSet_ReturnsTrue() {
        // 初始化配置对象并设置静态资源（假设通过setter方法设置静态资源）
        StaticFilesConfiguration config = new StaticFilesConfiguration();
        config.setStaticResources(new StaticResources()); // 假设存在StaticResources类及setter方法
        
        // 断言方法返回true
        assertTrue(config.isStaticResourcesSet(), "静态资源已设置时，isStaticResourcesSet应返回true");
    }
}