import spark.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkjavasparkServicejavaServiceIgnite0f65029eTest {

    @Mock
    private ResourceManager resourceManager; // 假设ignite依赖资源管理器（示例依赖）

    @InjectMocks
    private Service service; // 待测试的Service实例


    /**
     * 正常路径：首次调用ignite，成功初始化服务
     */
    @Test
    void ignite_ShouldInitializeServiceSuccessfully_WhenFirstCall() {
        // 执行目标方法
        service.ignite();

        // 验证依赖组件被正确调用（假设ignite需初始化资源）
        verify(resourceManager, times(1)).init();
        
        // 验证服务状态（假设Service有isIgnited()方法标识初始化状态）
        assertTrue(service.isIgnited(), "服务应成功初始化");
    }


    /**
     * 异常路径1：重复调用ignite，抛出"服务已启动"异常
     */
    @Test
    void ignite_ShouldThrowIllegalStateException_WhenCalledAgain() {
        // 首次调用：正常初始化
        service.ignite();
        
        // 第二次调用：预期抛出异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> service.ignite(), 
            "重复调用ignite应抛出IllegalStateException"
        );
        
        // 验证异常消息（假设异常消息为"Service already ignited"）
        assertEquals("Service already ignited", exception.getMessage());
        
        // 验证依赖组件仅初始化一次（避免重复初始化）
        verify(resourceManager, times(1)).init();
    }


    /**
     * 异常路径2：依赖组件初始化失败，ignite抛出运行时异常
     */
    @Test
    void ignite_ShouldPropagateException_WhenResourceInitializationFails() {
        // 模拟依赖组件初始化失败
        doThrow(new RuntimeException("Resource init failed"))
            .when(resourceManager).init();

        // 执行目标方法，预期抛出异常
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.ignite(), 
            "依赖初始化失败时ignite应抛出异常"
        );
        
        // 验证异常链（确保捕获到依赖组件的异常）
        assertEquals("Resource init failed", exception.getMessage());
        verify(resourceManager, times(1)).init(); // 验证依赖确实被调用
    }
}