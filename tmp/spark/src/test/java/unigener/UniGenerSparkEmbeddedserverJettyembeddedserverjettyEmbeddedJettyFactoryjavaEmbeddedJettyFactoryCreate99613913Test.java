import spark.embeddedserver.jetty.EmbeddedJettyFactory;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

// 假设EmbeddedServer、RouteMatcher等类的包路径
import com.example.EmbeddedServer;
import com.example.RouteMatcher;
import com.example.StaticFilesConfiguration;
import com.example.ExceptionMapper;

@MockitoSettings(strictness = Strictness.LENIENT)
public class UniGenerSparkEmbeddedserverJettyembeddedserverjettyEmbeddedJettyFactoryjavaEmbeddedJettyFactoryCreate99613913Test {

    @Mock
    private RouteMatcher mockRouteMatcher;
    @Mock
    private StaticFilesConfiguration mockStaticFilesConfig;
    @Mock
    private ExceptionMapper mockExceptionMapper;

    // 正常路径：所有参数有效（staticFilesConfig非null，hasMultipleHandler=true）
    @Test
    void create_WithAllValidParams_HasMultipleHandlerTrue_ReturnsEmbeddedServer() {
        // 执行测试
        EmbeddedServer result = EmbeddedServerCreator.create(
            mockRouteMatcher, 
            mockStaticFilesConfig, 
            mockExceptionMapper, 
            true
        );

        // 断言：返回非null实例
        Assertions.assertNotNull(result, "EmbeddedServer实例应成功创建");
    }

    // 正常路径：staticFilesConfig为null，hasMultipleHandler=false
    @Test
    void create_WithStaticFilesConfigNull_HasMultipleHandlerFalse_ReturnsEmbeddedServer() {
        // 执行测试
        EmbeddedServer result = EmbeddedServerCreator.create(
            mockRouteMatcher, 
            null,  // staticFilesConfig为null
            mockExceptionMapper, 
            false
        );

        // 断言：返回非null实例
        Assertions.assertNotNull(result, "即使staticFilesConfig为null，EmbeddedServer实例也应创建");
    }

    // 异常路径：routeMatcher为null（必传参数）
    @Test
    void create_WithNullRouteMatcher_ThrowsIllegalArgumentException() {
        // 执行测试并断言异常
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> EmbeddedServerCreator.create(
                null,  // routeMatcher为null
                mockStaticFilesConfig, 
                mockExceptionMapper, 
                true
            ),
            "当routeMatcher为null时，应抛出IllegalArgumentException"
        );

        // 可选：验证异常消息（如果方法实现有具体消息）
        Assertions.assertTrue(exception.getMessage().contains("routeMatcher"), "异常消息应包含参数名'routeMatcher'");
    }

    // 异常路径：exceptionMapper为null（必传参数）
    @Test
    void create_WithNullExceptionMapper_ThrowsIllegalArgumentException() {
        // 执行测试并断言异常
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> EmbeddedServerCreator.create(
                mockRouteMatcher, 
                mockStaticFilesConfig, 
                null,  // exceptionMapper为null
                false
            ),
            "当exceptionMapper为null时，应抛出IllegalArgumentException"
        );

        // 可选：验证异常消息
        Assertions.assertTrue(exception.getMessage().contains("exceptionMapper"), "异常消息应包含参数名'exceptionMapper'");
    }
}