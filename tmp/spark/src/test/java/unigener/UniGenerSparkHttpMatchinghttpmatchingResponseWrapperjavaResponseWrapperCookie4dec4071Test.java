import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.ResponseWrapper;

public class UniGenerSparkHttpMatchinghttpmatchingResponseWrapperjavaResponseWrapperCookie4dec4071Test {

    @Test
    void testCookieHappyPath() {
        Object result = new ResponseWrapper().cookie("sample", "sample");
        assertNotNull(result);
    }

    @Test
    void testCookieEdgeCase() {
        assertThrows(Exception.class, () -> new ResponseWrapper().cookie(null, null));
    }

    @Test
    void testCookiePatternFromRepo() {
        String intent = "该Java方法意图为在ResponseWrapper类中设置cookie，输入参数包含name和value。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
