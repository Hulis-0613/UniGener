import spark.resource.path;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkResourcesparkresourceClassPathResourcejavapathGetFilename20308029Test {

    // 正常路径：完整文件路径（Unix风格）
    @Test
    void getFilename_WithUnixFullPath_ReturnsFilename() {
        Path path = new Path("/home/user/docs/report.pdf");
        String filename = path.getFilename();
        assertEquals("report.pdf", filename);
    }

    // 正常路径：完整文件路径（Windows风格）
    @Test
    void getFilename_WithWindowsFullPath_ReturnsFilename() {
        Path path = new Path("D:\\data\\images\\photo.jpg");
        String filename = path.getFilename();
        assertEquals("photo.jpg", filename);
    }

    // 正常路径：仅文件名（无目录）
    @Test
    void getFilename_WithOnlyFilename_ReturnsFilename() {
        Path path = new Path("readme.txt");
        String filename = path.getFilename();
        assertEquals("readme.txt", filename);
    }

    // 正常路径：文件名包含多个点（如.tar.gz）
    @Test
    void getFilename_WithMultiDotFilename_ReturnsFullFilename() {
        Path path = new Path("/backup/archive.tar.gz");
        String filename = path.getFilename();
        assertEquals("archive.tar.gz", filename);
    }

    // 异常路径：路径为Null
    @Test
    void getFilename_WithNullPath_ThrowsNullPointerException() {
        Path path = new Path(null);
        assertThrows(NullPointerException.class, path::getFilename);
    }

    // 异常路径：空字符串路径
    @Test
    void getFilename_WithEmptyPath_ThrowsIllegalArgumentException() {
        Path path = new Path("");
        assertThrows(IllegalArgumentException.class, path::getFilename);
    }

    // 异常路径：目录路径（无文件名，以分隔符结尾）
    @Test
    void getFilename_WithDirectoryPath_ReturnsEmptyString() {
        Path unixDirPath = new Path("/usr/local/");
        Path windowsDirPath = new Path("C:\\Program Files\\");
        
        assertEquals("", unixDirPath.getFilename());
        assertEquals("", windowsDirPath.getFilename());
    }

    // 异常路径：路径包含特殊字符（验证兼容性）
    @Test
    void getFilename_WithSpecialCharsInFilename_ReturnsCorrectFilename() {
        Path path = new Path("/tmp/file?name#123.txt");
        String filename = path.getFilename();
        assertEquals("file?name#123.txt", filename);
    }
}