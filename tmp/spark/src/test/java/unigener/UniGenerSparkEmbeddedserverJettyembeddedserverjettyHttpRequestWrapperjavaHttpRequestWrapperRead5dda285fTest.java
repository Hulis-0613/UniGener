import spark.embeddedserver.jetty.HttpRequestWrapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkEmbeddedserverJettyembeddedserverjettyHttpRequestWrapperjavaHttpRequestWrapperRead5dda285fTest {

    @Mock
    private HttpServletRequest request;  // 模拟HTTP请求对象
    private HttpRequestWrapper httpRequestWrapper;  // 测试目标对象

    @BeforeEach
    void setUp() {
        // 初始化测试对象，注入模拟的request
        httpRequestWrapper = new HttpRequestWrapper(request);
    }

    @Test
    @DisplayName("正常路径：读取非空请求体内容")
    void read_WithValidRequestBody_ReturnsContent() throws IOException {
        // 1. 准备测试数据：模拟请求体内容
        String expectedContent = "{\n  \"username\": \"testUser\",\n  \"password\": \"testPass\"\n}";
        InputStream inputStream = new ByteArrayInputStream(expectedContent.getBytes());
        
        // 2. 模拟request的输入流
        when(request.getInputStream()).thenReturn((ServletInputStream) inputStream);
        
        // 3. 执行目标方法
        String actualContent = httpRequestWrapper.read();
        
        // 4. 断言结果（复用标准断言风格）
        assertNotNull(actualContent, "读取结果不应为null");
        assertEquals(expectedContent, actualContent, "请求体内容与预期不符");
        verify(request, times(1)).getInputStream();  // 验证输入流被正确调用
    }

    @Test
    @DisplayName("异常路径：请求输入流为null")
    void read_WithNullInputStream_ThrowsIllegalStateException() {
        // 1. 模拟request的输入流为null
        when(request.getInputStream()).thenReturn(null);
        
        // 2. 执行目标方法并断言异常（假设read方法在输入流为null时抛出IllegalStateException）
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            () -> httpRequestWrapper.read(), 
            "输入流为null时应抛出IllegalStateException"
        );
        
        // 3. 验证异常信息（根据实际实现调整）
        assertTrue(exception.getMessage().contains("Request input stream is null"), "异常信息不符合预期");
        verify(request, times(1)).getInputStream();
    }

    @Test
    @DisplayName("异常路径：读取输入流时发生IO异常")
    void read_WithIOExceptionDuringRead_ThrowsRuntimeException() throws IOException {
        // 1. 模拟输入流读取时抛出IO异常
        ServletInputStream mockInputStream = mock(ServletInputStream.class);
        doThrow(new IOException("Connection reset by peer")).when(mockInputStream).read(any(byte[].class));
        when(request.getInputStream()).thenReturn(mockInputStream);
        
        // 2. 执行目标方法并断言异常（假设read方法将IO异常包装为RuntimeException）
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> httpRequestWrapper.read(), 
            "读取输入流发生IO异常时应抛出RuntimeException"
        );
        
        // 3. 验证异常链和信息
        assertTrue(exception.getCause() instanceof IOException, "异常原因应为IOException");
        assertEquals("Failed to read request body", exception.getMessage(), "异常信息不符合预期");
        verify(mockInputStream, times(1)).read(any(byte[].class));  // 验证输入流read方法被调用
    }

    @Test
    @DisplayName("边界路径：读取空请求体（输入流无内容）")
    void read_WithEmptyRequestBody_ReturnsEmptyString() throws IOException {
        // 1. 模拟空请求体（输入流长度为0）
        InputStream emptyStream = new ByteArrayInputStream(new byte[0]);
        when(request.getInputStream()).thenReturn((ServletInputStream) emptyStream);
        
        // 2. 执行目标方法
        String actualContent = httpRequestWrapper.read();
        
        // 3. 断言结果（空字符串而非null）
        assertEquals("", actualContent, "空请求体应返回空字符串");
        verify(request, times(1)).getInputStream();
    }
}