import spark.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkjavasparkServicejavaServiceInitializeRouteMatcher8b3f4b0fTest {

    // 模拟Service依赖的配置服务（假设初始化路由匹配器需读取配置）
    @Mock
    private RouteConfigService routeConfigService;

    // 注入待测试的Service实例
    @InjectMocks
    private Service service;

    /**
     * 正常路径：配置有效时，初始化路由匹配器成功
     */
    @Test
    void initializeRouteMatcher_WithValidConfig_RouteMatcherInitialized() {
        // 准备：模拟配置服务返回有效路由配置
        RouteConfig validConfig = new RouteConfig(); // 假设RouteConfig为配置实体类
        when(routeConfigService.loadRouteConfig()).thenReturn(validConfig);

        // 执行：调用初始化方法
        service.initializeRouteMatcher();

        // 断言：路由匹配器已成功初始化（假设Service有getter方法获取路由匹配器）
        assertNotNull(service.getRouteMatcher(), "路由匹配器未初始化");
        // 验证：配置服务的加载方法被调用
        verify(routeConfigService, times(1)).loadRouteConfig();
    }

    /**
     * 异常路径1：配置服务返回null时，抛出IllegalStateException
     */
    @Test
    void initializeRouteMatcher_WithNullConfig_ThrowsIllegalStateException() {
        // 准备：模拟配置服务返回null（无效配置）
        when(routeConfigService.loadRouteConfig()).thenReturn(null);

        // 执行并断言：调用初始化方法时抛出预期异常
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> service.initializeRouteMatcher(),
                "未抛出配置为空的异常");

        // 验证异常信息（可选，根据实际业务需求）
        assertTrue(exception.getMessage().contains("路由配置为空，初始化失败"), "异常信息不符合预期");
        // 验证：配置服务的加载方法被调用
        verify(routeConfigService, times(1)).loadRouteConfig();
    }

    /**
     * 异常路径2：配置服务抛出异常时，向上传递异常
     */
    @Test
    void initializeRouteMatcher_ConfigServiceThrowsException_PropagatesException() {
        // 准备：模拟配置服务加载配置时抛出异常（如IO异常）
        RuntimeException configLoadException = new RuntimeException("配置文件读取失败");
        when(routeConfigService.loadRouteConfig()).thenThrow(configLoadException);

        // 执行并断言：调用初始化方法时抛出配置服务的异常
        RuntimeException thrownException = assertThrows(RuntimeException.class,
                () -> service.initializeRouteMatcher(),
                "未传递配置服务的异常");

        // 验证异常根源
        assertSame(configLoadException, thrownException, "异常未正确传递");
        // 验证：配置服务的加载方法被调用
        verify(routeConfigService, times(1)).loadRouteConfig();
    }
}