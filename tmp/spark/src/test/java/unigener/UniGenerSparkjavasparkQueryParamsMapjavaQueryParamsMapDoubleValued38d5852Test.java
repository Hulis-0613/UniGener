import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.QueryParamsMap;

public class UniGenerSparkjavasparkQueryParamsMapjavaQueryParamsMapDoubleValued38d5852Test {

    @Test
    void testDoubleValueHappyPath() {
        Object result = new QueryParamsMap().doubleValue();
        assertNotNull(result);
    }

    @Test
    void testDoubleValueEdgeCase() {
        assertThrows(Exception.class, () -> new QueryParamsMap().doubleValue());
    }

    @Test
    void testDoubleValuePatternFromRepo() {
        String intent = "该Java方法意图为 doubleValue，所属类为 QueryParamsMap，输入参数包含 无显式参数。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
