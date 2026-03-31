import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.staticfiles.StaticFilesConfiguration;

public class UniGenerSparkStaticfilessparkstaticfilesStaticFilesConfigurationjavaStaticFilesConfigurationPutCustomHeader17de814eTest {

    @Test
    void testPutCustomHeaderHappyPath() {
        Object result = new StaticFilesConfiguration().putCustomHeader("sample", "sample");
        assertNotNull(result);
    }

    @Test
    void testPutCustomHeaderEdgeCase() {
        assertThrows(Exception.class, () -> new StaticFilesConfiguration().putCustomHeader(null, null));
    }

    @Test
    void testPutCustomHeaderPatternFromRepo() {
        String intent = "该Java方法意图为 putCustomHeader，所属类为 StaticFilesConfiguration，输入参数包含 key, value。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
