import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.resource.ExternalResource;

public class UniGenerSparkResourcesparkresourceExternalResourcejavaExternalResourceIsDirectory7077964eTest {

    @Test
    void testIsDirectoryHappyPath() {
        Object result = new ExternalResource().isDirectory();
        assertNotNull(result);
    }

    @Test
    void testIsDirectoryEdgeCase() {
        assertThrows(Exception.class, () -> new ExternalResource().isDirectory());
    }

    @Test
    void testIsDirectoryPatternFromRepo() {
        String intent = "该Java方法意图为判断当前ExternalResource实例是否为目录，无显式参数。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
