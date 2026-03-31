import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.ResponseWrapper;

public class UniGenerSparkHttpMatchinghttpmatchingResponseWrapperjavaResponseWrapperBody98a8f3bdTest {

    @Test
    void testBodyHappyPath() {
        Object result = new ResponseWrapper().body("sample");
        assertNotNull(result);
    }

    @Test
    void testBodyEdgeCase() {
        assertThrows(Exception.class, () -> new ResponseWrapper().body(null));
    }

    @Test
    void testBodyPatternFromRepo() {
        String intent = "该Java方法意图为 body，所属类为 ResponseWrapper，输入参数包含 body。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
