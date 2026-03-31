import spark.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkServicejavaServiceStaticFileLocation57cc5476Test {

    private Path validTempDir; // 有效临时目录（正常路径测试用）

    @BeforeEach
    void setUp() throws Exception {
        // 创建临时目录用于正常路径测试
        validTempDir = Files.createTempDirectory("static-test-dir");
    }

    /**
     * 测试正常路径：输入有效目录路径，验证返回的Service对象正确设置静态文件位置
     */
    @Test
    void testStaticFileLocation_ValidDirectoryPath_ReturnsServiceWithCorrectLocation() {
        // 执行目标方法
        Service result = Service.staticFileLocation(validTempDir.toString());
        
        // 断言：返回非null的Service对象，且静态文件位置正确
        assertNotNull(result, "Service should not be null for valid directory path");
        assertEquals(validTempDir.toString(), result.getStaticFileLocation(), 
            "Static file location should match input directory path");
    }

    /**
     * 测试异常路径：输入null，验证抛出IllegalArgumentException
     */
    @Test
    void testStaticFileLocation_NullPath_ThrowsIllegalArgumentException() {
        // 断言：输入null时抛出IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> Service.staticFileLocation(null),
            "Null path should throw IllegalArgumentException");
        
        // 可选：验证异常消息（如果方法实现包含消息）
        assertTrue(exception.getMessage().contains("path must not be null"), 
            "Exception message should indicate null path is invalid");
    }

    /**
     * 测试异常路径：输入空字符串，验证抛出IllegalArgumentException
     */
    @Test
    void testStaticFileLocation_EmptyPath_ThrowsIllegalArgumentException() {
        // 断言：输入空字符串时抛出IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> Service.staticFileLocation(""),
            "Empty path should throw IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("path must not be empty"), 
            "Exception message should indicate empty path is invalid");
    }

    /**
     * 测试异常路径：输入不存在的路径，验证抛出IllegalArgumentException
     */
    @Test
    void testStaticFileLocation_NonExistentPath_ThrowsIllegalArgumentException() {
        String nonExistentPath = "/non/existent/path/that/does/not/exist-12345";
        
        // 断言：输入不存在路径时抛出IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> Service.staticFileLocation(nonExistentPath),
            "Non-existent path should throw IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("path does not exist"), 
            "Exception message should indicate path does not exist");
    }

    /**
     * 测试异常路径：输入文件路径（非目录），验证抛出IllegalArgumentException
     */
    @Test
    void testStaticFileLocation_FilePathInsteadOfDirectory_ThrowsIllegalArgumentException() throws Exception {
        // 创建临时文件（非目录）
        Path tempFile = Files.createTempFile("test-file", ".txt");
        
        try {
            // 断言：输入文件路径时抛出IllegalArgumentException
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> Service.staticFileLocation(tempFile.toString()),
                "File path (non-directory) should throw IllegalArgumentException");
            
            assertTrue(exception.getMessage().contains("path is not a directory"), 
                "Exception message should indicate path is not a directory");
        } finally {
            // 清理临时文件
            Files.deleteIfExists(tempFile);
        }
    }
}