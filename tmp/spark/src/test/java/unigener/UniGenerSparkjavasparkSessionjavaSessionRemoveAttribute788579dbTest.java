import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.Session;

public class UniGenerSparkjavasparkSessionjavaSessionRemoveAttribute788579dbTest {

    @Test
    void testRemoveAttributeHappyPath() {
        Object result = new Session().removeAttribute("sample");
        assertNotNull(result);
    }

    @Test
    void testRemoveAttributeEdgeCase() {
        assertThrows(Exception.class, () -> new Session().removeAttribute(null));
    }

    @Test
    void testRemoveAttributePatternFromRepo() {
        String intent = "该Java方法意图为 removeAttribute，所属类为 Session，输入参数包含 name。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
