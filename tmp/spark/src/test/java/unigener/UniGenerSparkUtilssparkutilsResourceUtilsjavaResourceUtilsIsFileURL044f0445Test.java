import spark.utils.ResourceUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.net.MalformedURLException;
import java.net.URL;

public class UniGenerSparkUtilssparkutilsResourceUtilsjavaResourceUtilsIsFileURL044f0445Test {

    /**
     * 测试文件协议URL（正常路径）：预期返回true
     */
    @Test
    void isFileURL_WithFileProtocol_ReturnsTrue() throws MalformedURLException {
        // 构造文件协议URL（本地文件路径示例）
        URL fileUrl = new URL("file:///Users/test/doc.txt");
        assertTrue(ResourceUtils.isFileURL(fileUrl), "文件协议URL应返回true");
    }

    /**
     * 测试HTTP协议URL（非文件协议）：预期返回false
     */
    @Test
    void isFileURL_WithHttpProtocol_ReturnsFalse() throws MalformedURLException {
        URL httpUrl = new URL("http://example.com/api");
        assertFalse(ResourceUtils.isFileURL(httpUrl), "HTTP协议URL应返回false");
    }

    /**
     * 测试HTTPS协议URL（非文件协议）：预期返回false
     */
    @Test
    void isFileURL_WithHttpsProtocol_ReturnsFalse() throws MalformedURLException {
        URL httpsUrl = new URL("https://example.com/secure");
        assertFalse(ResourceUtils.isFileURL(httpsUrl), "HTTPS协议URL应返回false");
    }

    /**
     * 测试FTP协议URL（非文件协议）：预期返回false
     */
    @Test
    void isFileURL_WithFtpProtocol_ReturnsFalse() throws MalformedURLException {
        URL ftpUrl = new URL("ftp://ftp.example.com/file.zip");
        assertFalse(ResourceUtils.isFileURL(ftpUrl), "FTP协议URL应返回false");
    }

    /**
     * 测试JAR协议URL（非文件协议）：预期返回false
     */
    @Test
    void isFileURL_WithJarProtocol_ReturnsFalse() throws MalformedURLException {
        URL jarUrl = new URL("jar:file:/app/lib.jar!//com/example/Class.class");
        assertFalse(ResourceUtils.isFileURL(jarUrl), "JAR协议URL应返回false");
    }

    /**
     * 测试输入为null（异常路径）：预期抛出NullPointerException
     */
    @Test
    void isFileURL_WithNullUrl_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, 
            () -> ResourceUtils.isFileURL(null), 
            "输入null时应抛出NullPointerException"
        );
    }
}