import spark.embeddedserver.jetty.HttpRequestWrapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkEmbeddedserverJettyembeddedserverjettyHttpRequestWrapperjavaHttpRequestWrapperAvailable2f51fbe3Test {

    @Mock
    private InputStream mockInputStream; // 模拟输入流依赖

    private HttpRequestWrapper httpRequestWrapper;

    /**
     * 正常路径：输入流可用时，返回正确的可用字节数
     */
    @Test
    void available_WhenInputStreamAvailable_ReturnsCorrectByteCount() throws IOException {
        // Arrange：模拟输入流的available()返回1024字节
        when(mockInputStream.available()).thenReturn(1024);
        httpRequestWrapper = new HttpRequestWrapper(mockInputStream);

        // Act：调用目标方法
        int availableBytes = httpRequestWrapper.available();

        // Assert：验证返回值与预期一致，且输入流方法被调用
        assertEquals(1024, availableBytes);
        verify(mockInputStream).available(); // 确保输入流的available()被调用
    }

    /**
     * 异常路径1：输入流为null时，抛出NullPointerException
     */
    @Test
    void available_WhenInputStreamNull_ThrowsNullPointerException() {
        // Arrange：构造HttpRequestWrapper时传入null输入流
        httpRequestWrapper = new HttpRequestWrapper(null);

        // Act & Assert：验证调用available()时抛出NPE
        NullPointerException exception = assertThrows(NullPointerException.class, 
            () -> httpRequestWrapper.available());
        assertTrue(exception.getMessage().contains("inputStream")); // 验证异常信息（可选）
    }

    /**
     * 异常路径2：输入流available()抛出IOException时，向上传递异常
     */
    @Test
    void available_WhenInputStreamThrowsIOException_PropagatesException() throws IOException {
        // Arrange：模拟输入流的available()抛出IOException
        IOException expectedException = new IOException("Stream read error");
        when(mockInputStream.available()).thenThrow(expectedException);
        httpRequestWrapper = new HttpRequestWrapper(mockInputStream);

        // Act & Assert：验证调用available()时抛出预期的IOException
        IOException thrownException = assertThrows(IOException.class, 
            () -> httpRequestWrapper.available());
        assertSame(expectedException, thrownException); // 确保异常实例一致
        verify(mockInputStream).available(); // 确保输入流方法被调用
    }
}