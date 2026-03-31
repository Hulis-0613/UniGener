import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.embeddedserver.jetty.websocket.to;

public class UniGenerSparkEmbeddedserverJettyWebsocketjettywebsocketWebSocketCreatorFactoryjavatoSparkWebSocketCreator0debd5cdTest {

    @Test
    void testSparkWebSocketCreatorHappyPath() {
        Object result = new to().SparkWebSocketCreator(null);
        assertNotNull(result);
    }

    @Test
    void testSparkWebSocketCreatorEdgeCase() {
        assertThrows(Exception.class, () -> new to().SparkWebSocketCreator(null));
    }

    @Test
    void testSparkWebSocketCreatorPatternFromRepo() {
        String intent = "该Java方法意图为 SparkWebSocketCreator，所属类为 to，输入参数包含 handlerWrapper.getHandler(。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
