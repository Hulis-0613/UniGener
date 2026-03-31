import spark.staticfiles.MimeType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.io.IOException;

public class UniGenerSparkStaticfilessparkstaticfilesMimeTypejavaMimeTypeFromResource7a8754adTest {

    // 正常路径：已知MIME类型的资源（文本文件）
    @Test
    void fromResource_ValidTextResource_ReturnsTextPlain() throws IOException {
        String mimeType = MimeType.fromResource("test-resources/sample.txt");
        assertEquals("text/plain", mimeType, "文本文件应解析为text/plain");
    }

    // 正常路径：已知MIME类型的资源（JPEG图片）
    @Test
    void fromResource_ValidImageResource_ReturnsImageJpeg() throws IOException {
        String mimeType = MimeType.fromResource("test-resources/image.jpg");
        assertEquals("image/jpeg", mimeType, "JPEG图片应解析为image/jpeg");
    }

    // 正常路径：未知扩展名但可通过内容识别的资源（如无扩展名的PDF）
    @Test
    void fromResource_UnknownExtensionResource_ReturnsPdf() throws IOException {
        String mimeType = MimeType.fromResource("test-resources/document"); // 无扩展名但内容为PDF
        assertEquals("application/pdf", mimeType, "无扩展名PDF应解析为application/pdf");
    }

    // 异常路径：资源路径为null
    @Test
    void fromResource_NullResourcePath_ThrowsIllegalArgumentException() {
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> MimeType.fromResource(null), "传入null应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("resourcePath must not be null"), "异常消息应提示资源路径非空");
    }

    // 异常路径：资源不存在（无效路径）
    @Test
    void fromResource_NonExistentResource_ThrowsIOException() {
        IOException exception = assertThrows(IOException.class,
                () -> MimeType.fromResource("test-resources/nonexistent.file"), "不存在的资源应抛出IOException");
        assertTrue(exception.getMessage().contains("Resource not found"), "异常消息应提示资源未找到");
    }

    // 异常路径：资源为目录（非文件）
    @Test
    void fromResource_DirectoryResource_ThrowsIOException() {
        IOException exception = assertThrows(IOException.class,
                () -> MimeType.fromResource("test-resources/dir/"), "目录资源应抛出IOException");
        assertTrue(exception.getMessage().contains("is a directory"), "异常消息应提示资源是目录");
    }

    // 异常路径：资源存在但无法读取（权限不足，模拟场景）
    @Test
    void fromResource_UnreadableResource_ThrowsIOException() {
        // 假设test-resources/unreadable.file设置了不可读权限（需提前准备环境）
        IOException exception = assertThrows(IOException.class,
                () -> MimeType.fromResource("test-resources/unreadable.file"), "不可读资源应抛出IOException");
        assertTrue(exception.getMessage().contains("Permission denied"), "异常消息应提示权限不足");
    }

    // 边界路径：空文件（无内容，应返回默认二进制类型）
    @Test
    void fromResource_EmptyFile_ReturnsOctetStream() throws IOException {
        String mimeType = MimeType.fromResource("test-resources/empty.dat");
        assertEquals("application/octet-stream", mimeType, "空文件应解析为application/octet-stream");
    }
}