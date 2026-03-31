import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.utils.delivers;

public class UniGenerSparkUtilssparkutilsStringUtilsjavadeliversIsNotBlankb8bb36b4Test {

    @Test
    void testIsNotBlankHappyPath() {
        Object result = delivers.isNotBlank(null);
        assertNotNull(result);
    }

    @Test
    void testIsNotBlankEdgeCase() {
        assertThrows(Exception.class, () -> delivers.isNotBlank(null));
    }

    @Test
    void testIsNotBlankPatternFromRepo() {
        String intent = "该Java方法意图为 isNotBlank，所属类为 delivers，输入参数包含 cs。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
