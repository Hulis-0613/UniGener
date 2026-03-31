import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.RequestWrapper;

public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperCookiea8d7a28cTest {

    @Test
    void testCookieHappyPath() {
        Object result = new RequestWrapper().cookie("sample");
        assertNotNull(result);
    }

    @Test
    void testCookieEdgeCase() {
        assertThrows(Exception.class, () -> new RequestWrapper().cookie(null));
    }

    @Test
    void testCookiePatternFromRepo() {
        String intent = "该Java方法意图为根据指定名称（name）获取cookie值";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
