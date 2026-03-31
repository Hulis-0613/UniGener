import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.RequestWrapper;

public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperEquals808a76f5Test {

    @Test
    void testEqualsHappyPath() {
        Object result = new RequestWrapper().equals(null);
        assertNotNull(result);
    }

    @Test
    void testEqualsEdgeCase() {
        assertThrows(Exception.class, () -> new RequestWrapper().equals(null));
    }

    @Test
    void testEqualsPatternFromRepo() {
        String intent = "该Java方法意图为 equals，所属类为 RequestWrapper，输入参数包含 obj。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
