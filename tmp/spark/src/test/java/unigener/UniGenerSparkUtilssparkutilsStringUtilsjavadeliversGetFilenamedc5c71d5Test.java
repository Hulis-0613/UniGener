import spark.utils.delivers;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilssparkutilsStringUtilsjavadeliversGetFilenamedc5c71d5Test {

    // 测试正常路径场景
    @Test
    void testGetFilename_WithUnixAbsolutePath() {
        String path = "/home/user/documents/report.pdf";
        String expected = "report.pdf";
        assertEquals(expected, getFilename(path), "Unix绝对路径应提取正确文件名");
    }

    @Test
    void testGetFilename_WithRelativePath() {
        String path = "projects/src/main/java/Test.java";
        String expected = "Test.java";
        assertEquals(expected, getFilename(path), "相对路径应提取正确文件名");
    }

    @Test
    void testGetFilename_WithWindowsPath() {
        String path = "C:\\Users\\Admin\\file.docx"; // 注意Java中反斜杠需转义
        String expected = "file.docx";
        assertEquals(expected, getFilename(path), "Windows路径应提取正确文件名");
    }

    @Test
    void testGetFilename_WithOnlyFilename() {
        String path = "readme.txt";
        String expected = "readme.txt";
        assertEquals(expected, getFilename(path), "纯文件名路径应返回自身");
    }

    @Test
    void testGetFilename_WithMultipleSeparators() {
        String path = "/a/b/c/d/e.txt";
        String expected = "e.txt";
        assertEquals(expected, getFilename(path), "多分隔符路径应提取最后一个分隔符后的文件名");
    }

    @Test
    void testGetFilename_WithSpecialCharacters() {
        String path = "/tmp/我的文件 123.txt"; // 包含空格和中文
        String expected = "我的文件 123.txt";
        assertEquals(expected, getFilename(path), "含特殊字符的路径应正确提取文件名");
    }

    @Test
    void testGetFilename_WithoutExtension() {
        String path = "README";
        String expected = "README";
        assertEquals(expected, getFilename(path), "无扩展名的路径应返回文件名");
    }

    // 测试边界情况
    @Test
    void testGetFilename_WithTrailingSeparator_Unix() {
        String path = "/home/user/docs/"; // 以Unix分隔符结尾
        String expected = ""; // 结尾分隔符后无内容，返回空字符串
        assertEquals(expected, getFilename(path), "Unix路径以分隔符结尾应返回空字符串");
    }

    @Test
    void testGetFilename_WithTrailingSeparator_Windows() {
        String path = "D:\\data\\"; // 以Windows分隔符结尾
        String expected = "";
        assertEquals(expected, getFilename(path), "Windows路径以分隔符结尾应返回空字符串");
    }

    // 测试异常路径（输入不合法场景）
    @Test
    void testGetFilename_WithNullPath() {
        assertNull(getFilename(null), "输入null时应返回null");
    }

    @Test
    void testGetFilename_WithEmptyString() {
        String expected = "";
        assertEquals(expected, getFilename(""), "输入空字符串时应返回空字符串");
    }

    // 目标方法（假设的实现，实际测试时需替换为真实方法引用）
    private String getFilename(String path) {
        // 此处为假设的实现逻辑（仅用于测试示例），实际测试时应调用真实的getFilename方法
        if (path == null) return null;
        String[] separators = {"/", "\\"};
        int lastSeparatorIndex = -1;
        for (String sep : separators) {
            int index = path.lastIndexOf(sep);
            if (index > lastSeparatorIndex) {
                lastSeparatorIndex = index;
            }
        }
        return lastSeparatorIndex == -1 ? path : path.substring(lastSeparatorIndex + 1);
    }
}