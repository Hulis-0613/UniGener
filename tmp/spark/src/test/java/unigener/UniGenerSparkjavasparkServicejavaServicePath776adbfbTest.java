import spark.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// 假设Service依赖ConfigService获取路径配置（根据实际依赖调整）
@ExtendWith(MockitoExtension.class)
public class UniGenerSparkjavasparkServicejavaServicePath776adbfbTest {

    @Mock
    private ConfigService configService; // 模拟路径配置依赖

    @InjectMocks
    private Service service; // 待测试的Service实例


    /**
     * 正常路径：配置存在且有效时，返回预期路径
     */
    @Test
    void path_WhenConfigValid_ReturnsExpectedPath() {
        // 准备：模拟配置服务返回有效路径
        String expectedPath = "/app/data/config";
        when(configService.getConfigPath()).thenReturn(expectedPath);

        // 执行
        String actualPath = service.path();

        // 断言：路径非空且与预期一致
        assertNotNull(actualPath, "返回路径不应为null");
        assertEquals(expectedPath, actualPath, "返回路径与预期不符");
        verify(configService, times(1)).getConfigPath(); // 验证依赖调用
    }


    /**
     * 异常路径1：配置不存在时，抛出IllegalStateException
     */
    @Test
    void path_WhenConfigMissing_ThrowsIllegalStateException() {
        // 准备：模拟配置服务返回null（配置缺失）
        when(configService.getConfigPath()).thenReturn(null);

        // 执行+断言：验证抛出指定异常及消息
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> service.path(), "配置缺失时应抛出IllegalStateException");
        assertTrue(exception.getMessage().contains("路径配置不存在"), "异常消息不符合预期");
        verify(configService, times(1)).getConfigPath();
    }


    /**
     * 异常路径2：配置读取失败（如IO异常）时，抛出RuntimeException
     */
    @Test
    void path_WhenConfigReadFails_ThrowsRuntimeException() {
        // 准备：模拟配置服务抛出IO异常（如文件读取失败）
        when(configService.getConfigPath()).thenThrow(new IOException("配置文件损坏"));

        // 执行+断言：验证异常传播
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.path(), "配置读取失败时应抛出RuntimeException");
        assertTrue(exception.getMessage().contains("获取路径失败"), "异常消息不符合预期");
        assertTrue(exception.getCause() instanceof IOException, "异常原因应为IOException");
        verify(configService, times(1)).getConfigPath();
    }
}