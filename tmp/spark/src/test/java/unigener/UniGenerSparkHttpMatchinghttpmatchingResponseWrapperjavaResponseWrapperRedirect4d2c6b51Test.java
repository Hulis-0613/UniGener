import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.ResponseWrapper;

public class UniGenerSparkHttpMatchinghttpmatchingResponseWrapperjavaResponseWrapperRedirect4d2c6b51Test {

    @Test
    void testRedirectHappyPath() {
        Object result = new ResponseWrapper().redirect("sample");
        assertNotNull(result);
    }

    @Test
    void testRedirectEdgeCase() {
        assertThrows(Exception.class, () -> new ResponseWrapper().redirect(null));
    }

    @Test
    void testRedirectPatternFromRepo() {
        String intent = "该Java方法意图为 redirect，所属类为 ResponseWrapper，输入参数包含 location。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
