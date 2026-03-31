import spark.resource.path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.junit.jupiter.api.Assertions.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class UniGenerSparkResourcesparkresourceClassPathResourcejavapathGetPath747d5165Test {

    private Path pathInstance;
    @TempDir
    private Path tempDir; // 临时目录用于模拟配置文件环境

    @BeforeEach
    void setUp() {
        pathInstance = new path(); // 实例化目标类
    }

    // 正常路径：配置文件存在且路径配置有效
    @Test
    void getPath_WhenConfigValid_ReturnsExpectedPath() throws IOException {
        // 准备：创建包含有效路径的配置文件
        File configFile = tempDir.resolve("config.properties").toFile();
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("app.path=/usr/local/data"); // 模拟有效配置
        }

        // 执行
        String result = pathInstance.getPath();

        // 断言：返回预期路径
        assertEquals("/usr/local/data", result);
    }

    // 异常路径1：配置文件不存在（模拟文件缺失）
    @Test
    void getPath_WhenConfigFileMissing_ThrowsIOException() {
        // 准备：确保临时目录下无配置文件（TempDir初始为空）

        // 执行 & 断言：抛出IOException
        assertThrows(IOException.class, () -> pathInstance.getPath(), 
            "配置文件缺失时应抛出IOException");
    }

    // 异常路径2：配置文件存在但路径配置项为空
    @Test
    void getPath_WhenPathPropertyEmpty_ReturnsNull() throws IOException {
        // 准备：创建路径配置项为空的配置文件
        File configFile = tempDir.resolve("config.properties").toFile();
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("app.path="); // 空配置
        }

        // 执行
        String result = pathInstance.getPath();

        // 断言：返回null（或根据实际逻辑调整为isEmpty()）
        assertNull(result, "路径配置为空时应返回null");
    }

    // 异常路径3：配置文件存在但路径配置项为null（模拟配置错误）
    @Test
    void getPath_WhenPathPropertyNull_ThrowsIllegalArgumentException() throws IOException {
        // 准备：创建路径配置项为null的配置文件（模拟配置解析错误）
        File configFile = tempDir.resolve("config.properties").toFile();
        try (FileWriter writer = new FileWriter(configFile)) {
            writer.write("app.path=null"); // 模拟null配置
        }

        // 执行 & 断言：抛出IllegalArgumentException
        assertThrows(IllegalArgumentException.class, () -> pathInstance.getPath(), 
            "路径配置为null时应抛出IllegalArgumentException");
    }
}