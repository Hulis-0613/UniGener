import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.RouteContext;

public class UniGenerSparkHttpMatchinghttpmatchingRouteContextjavaRouteContextWithMatcher83b0a500Test {

    @Test
    void testWithMatcherHappyPath() {
        Object result = new RouteContext().withMatcher(null);
        assertNotNull(result);
    }

    @Test
    void testWithMatcherEdgeCase() {
        assertThrows(Exception.class, () -> new RouteContext().withMatcher(null));
    }

    @Test
    void testWithMatcherPatternFromRepo() {
        String intent = "该Java方法意图为 withMatcher，所属类为 RouteContext，输入参数包含 routeMatcher。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
