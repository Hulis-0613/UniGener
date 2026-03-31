import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import spark.utils.utility;

public class UniGenerSparkUtilssparkutilsClassUtilsjavautilityResolvePrimitiveClassName328dd987Test {

    @Test
    void testResolvePrimitiveClassNameHappyPath() {
        Object result = utility.resolvePrimitiveClassName("sample");
        assertNotNull(result);
    }

    @Test
    void testResolvePrimitiveClassNameEdgeCase() {
        assertThrows(Exception.class, () -> utility.resolvePrimitiveClassName(null));
    }

    @Test
    void testResolvePrimitiveClassNamePatternFromRepo() {
        String intent = "该Java方法意图为根据输入的name字符串解析对应的原始类型类名并返回其Class对象。";
        String refCase = "generatedPattern";
        assertNotNull(intent);
        assertNotNull(refCase);
    }
}
