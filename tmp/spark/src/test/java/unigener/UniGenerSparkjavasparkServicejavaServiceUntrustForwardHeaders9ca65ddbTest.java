import spark.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkServicejavaServiceUntrustForwardHeaders9ca65ddbTest {

    /**
     * 正常路径测试：验证调用untrustForwardHeaders后，信任转发头被设置为false，且返回当前Service实例
     */
    @Test
    void untrustForwardHeaders_NormalPath_ShouldSetUntrustAndReturnSelf() {
        // Arrange：创建Service实例（假设默认trustForwardHeaders为true）
        Service service = new Service();
        
        // Act：调用目标方法
        Service result = service.untrustForwardHeaders();
        
        // Assert：1. 返回实例与原实例相同；2. 信任转发头已设为false
        assertSame(service, result, "untrustForwardHeaders应返回调用实例本身");
        assertFalse(service.isTrustForwardHeaders(), "调用后trustForwardHeaders应被设置为false");
    }

    /**
     * 异常路径测试：假设Service需初始化后才能调用该方法，验证未初始化时调用抛出异常
     */
    @Test
    void untrustForwardHeaders_WhenUninitialized_ShouldThrowIllegalStateException() {
        // Arrange：创建未初始化的Service实例（假设构造后需调用init()完成初始化）
        Service uninitializedService = new Service(); // 未调用init()
        
        // Act & Assert：验证调用时抛出预期异常
        assertThrows(IllegalStateException.class, 
            () -> uninitializedService.untrustForwardHeaders(), 
            "未初始化的Service调用untrustForwardHeaders应抛出IllegalStateException"
        );
    }
}