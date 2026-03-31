import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.utils.that;

public class UniGenerSparkUtilssparkutilsAssertjavathatHasLength239cb698Test {

    @Test
    void testHasLengthHappyPath() {
        Object result = that.hasLength("sample", "sample");
        assertNotNull(result);
    }

    @Test
    void testHasLengthEdgeCase() {
        assertThrows(Exception.class, () -> that.hasLength(null, null));
    }

    @Test
    void testHasLengthPatternFromRepo() {
        String intent = "该Java方法意图为 hasLength，所属类为 that，输入参数包含 text, message。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
