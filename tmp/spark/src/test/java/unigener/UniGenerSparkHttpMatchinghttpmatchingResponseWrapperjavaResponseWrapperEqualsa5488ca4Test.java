import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.ResponseWrapper;

public class UniGenerSparkHttpMatchinghttpmatchingResponseWrapperjavaResponseWrapperEqualsa5488ca4Test {

    @Test
    void testEqualsHappyPath() {
        Object result = new ResponseWrapper().equals(null);
        assertNotNull(result);
    }

    @Test
    void testEqualsEdgeCase() {
        assertThrows(Exception.class, () -> new ResponseWrapper().equals(null));
    }

    @Test
    void testEqualsPatternFromRepo() {
        String intent = "该Java方法意图为比较ResponseWrapper对象与指定Object类型的obj是否相等。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
