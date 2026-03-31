import spark.route.Routes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Collections;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkRoutesparkrouteRoutesjavaRoutesFindAllff75c9dcTest {

    @Mock  // 模拟数据源依赖（如数据库访问层）
    private RouteRepository routeRepository;

    @InjectMocks  // 注入测试目标对象
    private RouteService routeService;

    // 测试场景1：存在RouteMatch数据时，返回完整列表
    @Test
    void findAll_WithExistingRoutes_ReturnsAllMatches() {
        // 准备测试数据
        RouteMatch route1 = new RouteMatch("route1", "path1");
        RouteMatch route2 = new RouteMatch("route2", "path2");
        List<RouteMatch> expectedRoutes = List.of(route1, route2);

        // 模拟依赖行为：数据源返回预期数据
        when(routeRepository.findAll()).thenReturn(expectedRoutes);

        // 执行目标方法
        List<RouteMatch> actualRoutes = routeService.findAll();

        // 断言结果：列表非空、大小一致、内容匹配
        assertThat(actualRoutes).isNotEmpty()
                                .hasSize(2)
                                .containsExactlyElementsOf(expectedRoutes);
        // 验证依赖方法被调用
        verify(routeRepository).findAll();
    }

    // 测试场景2：无RouteMatch数据时，返回空列表
    @Test
    void findAll_WithNoRoutes_ReturnsEmptyList() {
        // 模拟依赖行为：数据源返回空列表
        when(routeRepository.findAll()).thenReturn(Collections.emptyList());

        // 执行目标方法
        List<RouteMatch> actualRoutes = routeService.findAll();

        // 断言结果：列表为空
        assertThat(actualRoutes).isEmpty();
        // 验证依赖方法被调用
        verify(routeRepository).findAll();
    }

    // 测试场景3：数据源异常时，抛出运行时异常
    @Test
    void findAll_WhenDataSourceFails_ThrowsRuntimeException() {
        // 模拟依赖行为：数据源抛出异常（如数据库连接失败）
        when(routeRepository.findAll()).thenThrow(
            new RuntimeException("Database connection failed")
        );

        // 断言目标方法抛出预期异常
        assertThatThrownBy(() -> routeService.findAll())
            .isInstanceOf(RuntimeException.class)
            .hasMessageContaining("Database connection failed");
        // 验证依赖方法被调用
        verify(routeRepository).findAll();
    }
}