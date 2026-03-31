import spark.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

// 请将YourClass替换为实际包含location方法的类名
public class UniGenerSparkjavasparkServicejavaServiceLocationca494e16Test {

    private final YourClass tested = new YourClass();

    // 临时目录，自动创建和清理，用于测试存在的目录场景
    @TempDir
    Path tempDir;

    /**
     * 正常路径：输入存在的有效目录，验证返回正确的绝对路径
     */
    @Test
    void location_WithValidExistingDirectory_ReturnsAbsoluteRealPath() throws Exception {
        // 执行目标方法
        String result = tested.location(tempDir.toString());
        
        // 断言返回路径与临时目录的真实路径一致
        assertEquals(tempDir.toRealPath().toString(), result);
    }

    /**
     * 异常路径：输入null，验证抛出IllegalArgumentException
     */
    @Test
    void location_WithNullFolder_ThrowsIllegalArgumentException() {
        // 验证异常类型和消息
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> tested.location(null));
        assertTrue(exception.getMessage().contains("folder cannot be null"));
    }

    /**
     * 异常路径：输入空字符串，验证抛出IllegalArgumentException
     */
    @Test
    void location_WithEmptyFolder_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> tested.location(""));
        assertTrue(exception.getMessage().contains("folder cannot be empty"));
    }

    /**
     * 异常路径：输入不存在的目录，验证抛出IOException
     */
    @Test
    void location_WithNonExistentFolder_ThrowsIOException() {
        String nonExistentFolder = tempDir.resolve("non_existent_dir").toString();
        
        Exception exception = assertThrows(Exception.class, 
            () -> tested.location(nonExistentFolder));
        assertTrue(exception instanceof java.io.IOException);
        assertTrue(exception.getMessage().contains("folder does not exist"));
    }

    /**
     * 异常路径：输入存在的文件（非目录），验证抛出IOException
     */
    @Test
    void location_WithExistingFile_ThrowsIOException() throws Exception {
        // 创建临时文件（非目录）
        Path tempFile = tempDir.resolve("test_file.txt");
        Files.createFile(tempFile);
        
        Exception exception = assertThrows(Exception.class, 
            () -> tested.location(tempFile.toString()));
        assertTrue(exception instanceof java.io.IOException);
        assertTrue(exception.getMessage().contains("not a directory"));
    }
}