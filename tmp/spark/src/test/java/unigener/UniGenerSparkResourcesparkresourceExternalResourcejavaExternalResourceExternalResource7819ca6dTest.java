import spark.resource.ExternalResource;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class UniGenerSparkResourcesparkresourceExternalResourcejavaExternalResourceExternalResource7819ca6dTest {

    // 临时目录，用于生成测试文件/目录
    @TempDir
    Path tempDir;

    /**
     * 正常路径：使用有效文件路径构造ExternalResource，预期成功创建实例
     */
    @Test
    void testConstructorWithValidFilePath() throws IOException {
        // 1. 准备：在临时目录创建一个有效文件
        Path validFilePath = tempDir.resolve("test-file.txt");
        Files.createFile(validFilePath); // 确保文件存在

        // 2. 执行：调用构造方法
        ExternalResource resource = new ExternalResource(validFilePath.toString());

        // 3. 断言：实例不为null，且路径正确（假设存在getPath()方法获取路径）
        assertNotNull(resource, "ExternalResource实例应成功创建");
        assertEquals(validFilePath.toString(), resource.getPath(), "构造的路径与输入路径不一致");
    }

    /**
     * 异常路径：输入null路径，预期抛出NullPointerException
     */
    @Test
    void testConstructorWithNullPath() {
        // 执行并断言异常
        assertThrows(NullPointerException.class,
            () -> new ExternalResource(null),
            "输入null路径时应抛出NullPointerException");
    }

    /**
     * 异常路径：输入空字符串路径，预期抛出IllegalArgumentException
     */
    @Test
    void testConstructorWithEmptyPath() {
        assertThrows(IllegalArgumentException.class,
            () -> new ExternalResource(""),
            "输入空字符串路径时应抛出IllegalArgumentException");
    }

    /**
     * 异常路径：输入不存在的文件路径，预期抛出IOException
     */
    @Test
    void testConstructorWithNonExistentPath() {
        String nonExistentPath = tempDir.resolve("non-existent-file.txt").toString();
        
        assertThrows(IOException.class,
            () -> new ExternalResource(nonExistentPath),
            "输入不存在的文件路径时应抛出IOException");
    }

    /**
     * 异常路径：输入目录路径（而非文件路径），预期抛出IllegalArgumentException
     */
    @Test
    void testConstructorWithDirectoryPath() throws IOException {
        // 创建临时目录作为路径（而非文件）
        Path dirPath = tempDir.resolve("test-dir");
        Files.createDirectory(dirPath);

        assertThrows(IllegalArgumentException.class,
            () -> new ExternalResource(dirPath.toString()),
            "输入目录路径时应抛出IllegalArgumentException");
    }
}