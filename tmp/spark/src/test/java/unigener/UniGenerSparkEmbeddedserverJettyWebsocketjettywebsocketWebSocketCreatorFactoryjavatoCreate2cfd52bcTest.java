import spark.embeddedserver.jetty.websocket.to;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkEmbeddedserverJettyWebsocketjettywebsocketWebSocketCreatorFactoryjavatoCreate2cfd52bcTest {

    @Mock
    private WebSocketHandlerWrapper validHandlerWrapper; // 模拟有效入参

    private WebSocketCreatorFactory factory;

    @BeforeEach
    void setUp() {
        factory = new WebSocketCreatorFactory(); // 初始化目标类实例
    }

    // 正常路径：入参为有效 WebSocketHandlerWrapper 时，成功创建 WebSocketCreator
    @Test
    void create_WithValidHandlerWrapper_ShouldReturnNonNullWebSocketCreator() {
        // 执行目标方法
        WebSocketCreator result = factory.create(validHandlerWrapper);

        // 断言结果非空（验证创建成功）
        assertNotNull(result, "WebSocketCreator should not be null when handlerWrapper is valid");
    }

    // 异常路径：入参为 null 时，抛出 IllegalArgumentException
    @Test
    void create_WithNullHandlerWrapper_ShouldThrowIllegalArgumentException() {
        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> factory.create(null),
                "Expected IllegalArgumentException when handlerWrapper is null");

        // 断言异常消息（假设方法会提示入参非空要求）
        assertThat(exception.getMessage())
                .as("Exception message should indicate non-null requirement")
                .contains("handlerWrapper must not be null");
    }
}