import spark.resource.providing;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkResourcesparkresourceAbstractResourceHandlerjavaprovidingAddPaths20c17a13Test {

    // 正常路径：无斜杠的两个非空段
    @Test
    void addPaths_NormalSegmentsWithoutSlashes() {
        String result = PathUtils.addPaths("user", "docs");
        assertEquals("user/docs", result);
    }

    // 正常路径：第一段以斜杠结尾，第二段无斜杠
    @Test
    void addPaths_Segment1EndsWithSlash() {
        String result = PathUtils.addPaths("user/", "docs");
        assertEquals("user/docs", result);
    }

    // 正常路径：第二段以斜杠开头，第一段无斜杠
    @Test
    void addPaths_Segment2StartsWithSlash() {
        String result = PathUtils.addPaths("user", "/docs");
        assertEquals("user/docs", result);
    }

    // 正常路径：两段均包含中间斜杠
    @Test
    void addPaths_BothSegmentsWithInnerSlashes() {
        String result = PathUtils.addPaths("user/profile", "docs/report");
        assertEquals("user/profile/docs/report", result);
    }

    // 正常路径：两段斜杠重叠（第一段尾斜杠+第二段头斜杠）
    @Test
    void addPaths_SlashesOverlap() {
        String result = PathUtils.addPaths("user/profile/", "/docs/report");
        assertEquals("user/profile/docs/report", result);
    }

    // 正常路径：第一段为空字符串
    @Test
    void addPaths_Segment1Empty() {
        String result = PathUtils.addPaths("", "docs/report");
        assertEquals("docs/report", result);
    }

    // 正常路径：第二段为空字符串
    @Test
    void addPaths_Segment2Empty() {
        String result = PathUtils.addPaths("user/profile", "");
        assertEquals("user/profile", result);
    }

    // 正常路径：两段均为空字符串
    @Test
    void addPaths_BothSegmentsEmpty() {
        String result = PathUtils.addPaths("", "");
        assertEquals("", result);
    }

    // 异常路径：第一段为null
    @Test
    void addPaths_Segment1Null_ThrowsException() {
        assertThrows(NullPointerException.class, 
            () -> PathUtils.addPaths(null, "docs"));
    }

    // 异常路径：第二段为null
    @Test
    void addPaths_Segment2Null_ThrowsException() {
        assertThrows(NullPointerException.class, 
            () -> PathUtils.addPaths("user", null));
    }

    // 异常路径：两段均为null
    @Test
    void addPaths_BothSegmentsNull_ThrowsException() {
        assertThrows(NullPointerException.class, 
            () -> PathUtils.addPaths(null, null));
    }
}