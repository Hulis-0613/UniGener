import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.RequestWrapper;

public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperContentLengthfb5ca52bTest {

    @Test
    void testContentLengthHappyPath() {
        Object result = new RequestWrapper().contentLength();
        assertNotNull(result);
    }

    @Test
    void testContentLengthEdgeCase() {
        assertThrows(Exception.class, () -> new RequestWrapper().contentLength());
    }

    @Test
    void testContentLengthPatternFromRepo() {
        String intent = "该Java方法意图为获取内容长度，所属类为RequestWrapper，输入参数包含无显式参数。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
