import spark.embeddedserver.EmbeddedServers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;

// 假设相关类型定义
interface RouteMatcher {}
public class UniGenerSparkEmbeddedserversparkembeddedserverEmbeddedServersjavaEmbeddedServersCreate3e2db8b3Test {}
class EmbeddedServer {}

@ExtendWith(MockitoExtension.class)
class EmbeddedServerFactoryTest {

    @Mock
    private RouteMatcher validRouteMatcher; // 模拟有效的RouteMatcher实例
    private final String validIdentifier = "test-server-123";
    private final StaticFilesConfiguration validStaticConfig = new StaticFilesConfiguration();


    // 正常路径：所有参数有效，multipleHandlers=true
    @Test
    void create_WithValidParametersAndMultipleHandlersTrue_ReturnsEmbeddedServer() {
        // 执行测试
        EmbeddedServer result = EmbeddedServerFactory.create(
                validIdentifier,
                validRouteMatcher,
                validStaticConfig,
                true
        );

        // 断言：返回非null实例
        assertNotNull(result, "EmbeddedServer should be created successfully");
    }

    // 正常路径：所有参数有效，multipleHandlers=false
    @Test
    void create_WithValidParametersAndMultipleHandlersFalse_ReturnsEmbeddedServer() {
        // 执行测试
        EmbeddedServer result = EmbeddedServerFactory.create(
                validIdentifier,
                validRouteMatcher,
                validStaticConfig,
                false
        );

        // 断言：返回非null实例
        assertNotNull(result, "EmbeddedServer should be created successfully");
    }

    // 正常路径：staticFilesConfiguration为null（允许空配置）
    @Test
    void create_WithNullStaticFilesConfiguration_ReturnsEmbeddedServer() {
        // 执行测试
        EmbeddedServer result = EmbeddedServerFactory.create(
                validIdentifier,
                validRouteMatcher,
                null, // staticFilesConfiguration为null
                true
        );

        // 断言：返回非null实例
        assertNotNull(result, "EmbeddedServer should be created with null staticFilesConfiguration");
    }

    // 异常路径：identifier为null
    @Test
    void create_WithNullIdentifier_ThrowsIllegalArgumentException() {
        // 执行测试并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> EmbeddedServerFactory.create(
                        null, // identifier为null
                        validRouteMatcher,
                        validStaticConfig,
                        true
                ),
                "Expected IllegalArgumentException when identifier is null"
        );

        // 可选：断言异常消息（若方法有明确消息）
        assertTrue(exception.getMessage().contains("identifier must not be null or empty"),
                "Exception message should indicate invalid identifier");
    }

    // 异常路径：identifier为空字符串
    @Test
    void create_WithEmptyIdentifier_ThrowsIllegalArgumentException() {
        // 执行测试并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> EmbeddedServerFactory.create(
                        "", // identifier为空字符串
                        validRouteMatcher,
                        validStaticConfig,
                        false
                ),
                "Expected IllegalArgumentException when identifier is empty"
        );

        // 可选：断言异常消息
        assertTrue(exception.getMessage().contains("identifier must not be null or empty"),
                "Exception message should indicate invalid identifier");
    }

    // 异常路径：routeMatcher为null
    @Test
    void create_WithNullRouteMatcher_ThrowsIllegalArgumentException() {
        // 执行测试并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> EmbeddedServerFactory.create(
                        validIdentifier,
                        null, // routeMatcher为null
                        validStaticConfig,
                        true
                ),
                "Expected IllegalArgumentException when routeMatcher is null"
        );

        // 可选：断言异常消息
        assertTrue(exception.getMessage().contains("routeMatcher must not be null"),
                "Exception message should indicate invalid routeMatcher");
    }
}