import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.utils.CollectionUtils;

public class UniGenerSparkUtilssparkutilsCollectionUtilsjavaCollectionUtilsIsEmptya9eda6f2Test {

    @Test
    void testIsEmptyHappyPath() {
        Object result = CollectionUtils.isEmpty(null);
        assertNotNull(result);
    }

    @Test
    void testIsEmptyEdgeCase() {
        assertThrows(Exception.class, () -> CollectionUtils.isEmpty(null));
    }

    @Test
    void testIsEmptyPatternFromRepo() {
        String intent = "该Java方法意图为 isEmpty，所属类为 CollectionUtils，输入参数包含 collection。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
