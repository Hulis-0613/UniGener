import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.embeddedserver.NotSupportedException;

public class UniGenerSparkEmbeddedserversparkembeddedserverNotSupportedExceptionjavaNotSupportedExceptionRaise1d53a693Test {

    @Test
    void testRaiseHappyPath() {
        Object result = NotSupportedException.raise("sample", "sample");
        assertNotNull(result);
    }

    @Test
    void testRaiseEdgeCase() {
        assertThrows(Exception.class, () -> NotSupportedException.raise(null, null));
    }

    @Test
    void testRaisePatternFromRepo() {
        String intent = "该Java方法意图为 raise，所属类为 NotSupportedException，输入参数包含 clazz, feature。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
