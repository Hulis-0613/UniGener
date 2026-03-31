import spark.should;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSparkjavashouldExternalStaticFileLocation9a3def20Test {

    @BeforeEach
    void setUp() {
        // 重置静态变量，避免测试间状态干扰
        FileConfig.externalStaticFileDir = null;
    }

    @Test
    void testExternalStaticFileLocation_ValidPath(@TempDir Path tempDir) {
        // 正常路径：使用临时目录作为有效外部文件夹
        String validPath = tempDir.toFile().getAbsolutePath();
        
        FileConfig.externalStaticFileLocation(validPath);
        
        // 断言静态变量被正确设置
        assertEquals(validPath, FileConfig.externalStaticFileDir);
    }

    @Test
    void testExternalStaticFileLocation_NullPath() {
        // 异常路径：输入null
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FileConfig.externalStaticFileLocation(null));
        
        // 断言异常消息（根据实际实现调整）
        assertTrue(exception.getMessage().contains("外部文件夹路径不能为null"));
        // 断言静态变量未被设置
        assertNull(FileConfig.externalStaticFileDir);
    }

    @Test
    void testExternalStaticFileLocation_EmptyString() {
        // 异常路径：输入空字符串
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FileConfig.externalStaticFileLocation(""));
        
        assertTrue(exception.getMessage().contains("外部文件夹路径不能为空"));
        assertNull(FileConfig.externalStaticFileDir);
    }

    @Test
    void testExternalStaticFileLocation_NonExistentPath() {
        // 异常路径：不存在的路径
        String nonExistentPath = "non/existent/directory";
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FileConfig.externalStaticFileLocation(nonExistentPath));
        
        assertTrue(exception.getMessage().contains("外部文件夹路径不存在"));
        assertNull(FileConfig.externalStaticFileDir);
    }

    @Test
    void testExternalStaticFileLocation_FilePathInsteadOfDir(@TempDir Path tempDir) throws Exception {
        // 异常路径：输入文件路径而非目录
        File tempFile = tempDir.resolve("test.txt").toFile();
        assertTrue(tempFile.createNewFile()); // 创建临时文件
        
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FileConfig.externalStaticFileLocation(tempFile.getAbsolutePath()));
        
        assertTrue(exception.getMessage().contains("外部文件夹路径必须是目录"));
        assertNull(FileConfig.externalStaticFileDir);
    }
}