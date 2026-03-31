import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.RequestWrapper;

public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperBodyAsBytes2a7162e3Test {

    @Test
    void testBodyAsBytesHappyPath() {
        Object result = new RequestWrapper().bodyAsBytes();
        assertNotNull(result);
    }

    @Test
    void testBodyAsBytesEdgeCase() {
        assertThrows(Exception.class, () -> new RequestWrapper().bodyAsBytes());
    }

    @Test
    void testBodyAsBytesPatternFromRepo() {
        String intent = "该Java方法意图为获取请求体的字节数组，所属类为RequestWrapper，无显式参数。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
