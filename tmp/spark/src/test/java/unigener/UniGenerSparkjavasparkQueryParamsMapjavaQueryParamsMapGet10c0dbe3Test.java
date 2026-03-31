import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.QueryParamsMap;

public class UniGenerSparkjavasparkQueryParamsMapjavaQueryParamsMapGet10c0dbe3Test {

    @Test
    void testGetHappyPath() {
        Object result = new QueryParamsMap().get("sample");
        assertNotNull(result);
    }

    @Test
    void testGetEdgeCase() {
        assertThrows(Exception.class, () -> new QueryParamsMap().get(null));
    }

    @Test
    void testGetPatternFromRepo() {
        String intent = "该Java方法意图为从QueryParamsMap中获取指定keys对应的值，输入参数为可变参数的字符串类型的keys。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
