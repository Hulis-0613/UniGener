import spark.resource.ExternalResource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkResourcesparkresourceExternalResourcejavaExternalResourceGetPath33ec0be3Test {

    // 正常路径：资源初始化后调用getPath，返回预期路径
    @Test
    void getPath_WhenResourceInitialized_ReturnsValidPath() {
        // 初始化外部资源（假设需调用initialize方法完成初始化）
        ExternalResource resource = new ExternalResource();
        resource.initialize(); // 假设该方法用于完成资源初始化
        
        // 调用目标方法
        String actualPath = resource.getPath();
        
        // 断言路径非空且符合预期（假设预期路径为"/external/resources/data"）
        assertNotNull(actualPath, "路径不应为null");
        assertEquals("/external/resources/data", actualPath, "返回路径与预期不符");
    }

    // 异常路径：资源未初始化时调用getPath，抛出IllegalStateException
    @Test
    void getPath_WhenResourceNotInitialized_ThrowsIllegalStateException() {
        // 创建未初始化的资源实例
        ExternalResource resource = new ExternalResource();
        
        // 断言调用getPath时抛出IllegalStateException
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            resource::getPath, "未初始化资源调用getPath应抛出IllegalStateException");
        
        // 可选：断言异常消息（若方法实现包含具体消息）
        assertTrue(exception.getMessage().contains("资源未初始化"), "异常消息不符合预期");
    }
}