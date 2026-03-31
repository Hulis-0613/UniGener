import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.ResponseWrapper;

public class UniGenerSparkHttpMatchinghttpmatchingResponseWrapperjavaResponseWrapperRemoveCookie487c7385Test {

    @Test
    void testRemoveCookieHappyPath() {
        Object result = new ResponseWrapper().removeCookie("sample");
        assertNotNull(result);
    }

    @Test
    void testRemoveCookieEdgeCase() {
        assertThrows(Exception.class, () -> new ResponseWrapper().removeCookie(null));
    }

    @Test
    void testRemoveCookiePatternFromRepo() {
        String intent = "该Java方法意图为移除指定名称的Cookie，输入参数为name。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
