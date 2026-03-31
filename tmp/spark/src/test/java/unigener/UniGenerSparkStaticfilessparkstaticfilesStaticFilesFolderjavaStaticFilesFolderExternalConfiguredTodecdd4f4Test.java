import spark.staticfiles.StaticFilesFolder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import java.io.File;
import java.io.IOException;

/**
 * 测试 {@link ExternalConfig#externalConfiguredTo(String)} 方法的功能正确性。
 */
public class UniGenerSparkStaticfilessparkstaticfilesStaticFilesFolderjavaStaticFilesFolderExternalConfiguredTodecdd4f4Test {

    private ExternalConfig externalConfig;

    @BeforeEach
    void setUp() {
        // 初始化测试对象
        externalConfig = new ExternalConfig();
    }

    // ------------------------------ 正常路径测试 ------------------------------
    @Test
    void externalConfiguredTo_WithValidFolder_SuccessfullyConfigures(@TempDir File tempDir) throws IOException {
        // 准备：使用JUnit5临时目录作为有效文件夹
        String validFolderPath = tempDir.getAbsolutePath();

        // 执行：配置外部文件夹
        externalConfig.externalConfiguredTo(validFolderPath);

        // 验证：配置成功且路径正确
        assertThat(externalConfig.getConfiguredFolder()).isEqualTo(validFolderPath);
    }

    // ------------------------------ 异常路径测试 ------------------------------
    @Test
    void externalConfiguredTo_WithNullFolder_ThrowsIllegalArgumentException() {
        // 执行 & 验证：传入null时抛出参数异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> externalConfig.externalConfiguredTo(null));
        assertThat(exception.getMessage()).contains("Folder path must not be null");
    }

    @Test
    void externalConfiguredTo_WithEmptyFolder_ThrowsIllegalArgumentException() {
        // 执行 & 验证：传入空字符串时抛出参数异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> externalConfig.externalConfiguredTo(""));
        assertThat(exception.getMessage()).contains("Folder path must not be empty");
    }

    @Test
    void externalConfiguredTo_WithNonExistentFolder_ThrowsIOException() {
        // 准备：不存在的文件夹路径
        String nonExistentPath = "non_existent_folder_9876";

        // 执行 & 验证：路径不存在时抛出IO异常
        IOException exception = assertThrows(IOException.class,
                () -> externalConfig.externalConfiguredTo(nonExistentPath));
        assertThat(exception.getMessage()).contains("Folder does not exist");
    }

    @Test
    void externalConfiguredTo_WithFilePathInsteadOfFolder_ThrowsIOException(@TempDir File tempDir) throws IOException {
        // 准备：在临时目录中创建一个文件（非文件夹）
        File testFile = new File(tempDir, "test_file.txt");
        assertThat(testFile.createNewFile()).isTrue(); // 确保文件创建成功

        // 执行 & 验证：传入文件路径时抛出IO异常
        IOException exception = assertThrows(IOException.class,
                () -> externalConfig.externalConfiguredTo(testFile.getAbsolutePath()));
        assertThat(exception.getMessage()).contains("Path is not a directory");
    }
}