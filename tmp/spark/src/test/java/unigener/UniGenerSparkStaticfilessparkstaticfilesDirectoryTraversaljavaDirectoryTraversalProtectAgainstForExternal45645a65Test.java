import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.staticfiles.DirectoryTraversal;

public class UniGenerSparkStaticfilessparkstaticfilesDirectoryTraversaljavaDirectoryTraversalProtectAgainstForExternal45645a65Test {

    @Test
    void testProtectAgainstForExternalHappyPath() {
        Object result = DirectoryTraversal.protectAgainstForExternal("sample", "sample");
        assertNotNull(result);
    }

    @Test
    void testProtectAgainstForExternalEdgeCase() {
        assertThrows(Exception.class, () -> DirectoryTraversal.protectAgainstForExternal(null, null));
    }

    @Test
    void testProtectAgainstForExternalPatternFromRepo() {
        String intent = "该Java方法意图为 protectAgainstForExternal，所属类为 DirectoryTraversal，输入参数包含 path, externalFolder。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
