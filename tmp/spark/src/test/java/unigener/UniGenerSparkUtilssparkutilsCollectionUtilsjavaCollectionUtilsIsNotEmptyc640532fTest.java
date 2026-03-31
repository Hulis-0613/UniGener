import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.utils.CollectionUtils;

public class UniGenerSparkUtilssparkutilsCollectionUtilsjavaCollectionUtilsIsNotEmptyc640532fTest {

    @Test
    void testIsNotEmptyHappyPath() {
        Object result = CollectionUtils.isNotEmpty(null);
        assertNotNull(result);
    }

    @Test
    void testIsNotEmptyEdgeCase() {
        assertThrows(Exception.class, () -> CollectionUtils.isNotEmpty(null));
    }

    @Test
    void testIsNotEmptyPatternFromRepo() {
        String intent = "该Java方法意图为CollectionUtils类的isNotEmpty，用于判断输入的集合collection是否非空并返回布尔值。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
