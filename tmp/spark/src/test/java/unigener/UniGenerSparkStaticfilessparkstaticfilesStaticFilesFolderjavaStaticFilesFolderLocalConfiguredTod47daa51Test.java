import spark.staticfiles.StaticFilesFolder;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkStaticfilessparkstaticfilesStaticFilesFolderjavaStaticFilesFolderLocalConfiguredTod47daa51Test {

    @Test
    void localConfiguredTo_WithValidFolderPath_SetsLocalFolderSuccessfully() {
        // Arrange
        StaticFilesFolder staticFilesFolder = new StaticFilesFolder();
        String expectedFolder = "/app/static/resources";

        // Act
        StaticFilesFolder result = staticFilesFolder.localConfiguredTo(expectedFolder);

        // Assert
        assertNotNull(result, "配置后实例不应为null");
        assertSame(staticFilesFolder, result, "应返回当前实例以支持链式调用");
        assertEquals(expectedFolder, staticFilesFolder.getLocalFolder(), "本地文件夹路径未正确设置");
    }

    @Test
    void localConfiguredTo_WithNullFolder_ThrowsIllegalArgumentException() {
        // Arrange
        StaticFilesFolder staticFilesFolder = new StaticFilesFolder();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> staticFilesFolder.localConfiguredTo(null), 
            "传入null路径时应抛出IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("folder must not be null"), 
            "异常消息应提示路径不可为null");
    }

    @Test
    void localConfiguredTo_WithEmptyFolder_ThrowsIllegalArgumentException() {
        // Arrange
        StaticFilesFolder staticFilesFolder = new StaticFilesFolder();

        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> staticFilesFolder.localConfiguredTo(""), 
            "传入空字符串路径时应抛出IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("folder must not be empty"), 
            "异常消息应提示路径不可为空");
    }
}