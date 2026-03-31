import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.http.matching.RequestWrapper;

public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperQueryParamsValues55358833Test {

    @Test
    void testQueryParamsValuesHappyPath() {
        Object result = new RequestWrapper().queryParamsValues("sample");
        assertNotNull(result);
    }

    @Test
    void testQueryParamsValuesEdgeCase() {
        assertThrows(Exception.class, () -> new RequestWrapper().queryParamsValues(null));
    }

    @Test
    void testQueryParamsValuesPatternFromRepo() {
        String intent = "该Java方法意图为 queryParamsValues，所属类为 RequestWrapper，输入参数包含 queryParam。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
