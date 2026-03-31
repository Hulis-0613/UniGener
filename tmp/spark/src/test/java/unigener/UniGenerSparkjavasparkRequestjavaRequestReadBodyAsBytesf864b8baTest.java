import spark.Request;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

// 假设目标方法所在类为Request（根据实际类名调整）
@ExtendWith(MockitoExtension.class)
public class UniGenerSparkjavasparkRequestjavaRequestReadBodyAsBytesf864b8baTest {

    @Mock
    private Request request; // 模拟Request对象

    /**
     * 正常路径：请求体有内容，成功读取字节数组
     */
    @Test
    void readBodyAsBytes_NormalCase_ReturnsCorrectBytes() throws IOException {
        // 准备测试数据
        String testBody = "Hello, World!";
        byte[] expectedBytes = testBody.getBytes(StandardCharsets.UTF_8);
        InputStream inputStream = new ByteArrayInputStream(expectedBytes);

        // 模拟Request的输入流
        when(request.getInputStream()).thenReturn(inputStream);

        // 执行目标方法
        byte[] result = request.readBodyAsBytes();

        // 断言结果与预期一致
        assertArrayEquals(expectedBytes, result);
    }

    /**
     * 异常路径1：请求体为空（输入流无数据）
     */
    @Test
    void readBodyAsBytes_EmptyBody_ReturnsEmptyArray() throws IOException {
        // 准备空输入流
        InputStream emptyStream = new ByteArrayInputStream(new byte[0]);
        when(request.getInputStream()).thenReturn(emptyStream);

        // 执行目标方法
        byte[] result = request.readBodyAsBytes();

        // 断言返回空字节数组
        assertArrayEquals(new byte[0], result);
    }

    /**
     * 异常路径2：输入流为null（模拟未初始化的流）
     */
    @Test
    void readBodyAsBytes_InputStreamNull_ReturnsEmptyArray() throws IOException {
        // 模拟输入流为null
        when(request.getInputStream()).thenReturn(null);

        // 执行目标方法
        byte[] result = request.readBodyAsBytes();

        // 断言返回空字节数组（或根据实际逻辑调整为null）
        assertArrayEquals(new byte[0], result);
    }

    /**
     * 异常路径3：读取输入流时抛出IO异常
     */
    @Test
    void readBodyAsBytes_InputStreamThrowsIOException_PropagatesException() throws IOException {
        // 模拟输入流读取时抛出异常
        IOException expectedException = new IOException("Stream read failed");
        when(request.getInputStream()).thenThrow(expectedException);

        // 断言方法抛出预期异常
        IOException actualException = assertThrows(IOException.class, () -> request.readBodyAsBytes());
        assertEquals(expectedException.getMessage(), actualException.getMessage());
    }
}