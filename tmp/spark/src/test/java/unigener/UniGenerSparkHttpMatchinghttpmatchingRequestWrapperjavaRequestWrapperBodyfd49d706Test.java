import spark.http.matching.RequestWrapper;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

// 假设RequestWrapper类的全限定名为com.example.RequestWrapper
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperBodyfd49d706Test {

    @Mock
    private com.example.RequestWrapper requestWrapper;

    @BeforeEach
    void setUp() {
        // 初始化Mockito模拟对象
        MockitoAnnotations.openMocks(this);
    }

    /**
     * 正常路径：请求体存在时，正确返回请求体内容
     */
    @Test
    void body_WithValidRequestBody_ReturnsBodyContent() throws IOException {
        // 1. 准备测试数据
        String expectedBody = "{\n  \"name\": \"test\",\n  \"id\": 123\n}";
        InputStream inputStream = new ByteArrayInputStream(expectedBody.getBytes(StandardCharsets.UTF_8));

        // 2. 模拟依赖行为：当调用getInputStream()时返回包含预期内容的输入流
        when(requestWrapper.getInputStream()).thenReturn(inputStream);

        // 3. 执行目标方法
        String actualBody = requestWrapper.body();

        // 4. 断言结果：返回内容与预期一致
        assertEquals(expectedBody, actualBody, "请求体内容不匹配");
    }

    /**
     * 异常路径1：请求体为空时，返回空字符串
     */
    @Test
    void body_WithEmptyRequestBody_ReturnsEmptyString() throws IOException {
        // 1. 准备空输入流
        InputStream emptyInputStream = new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8));

        // 2. 模拟依赖行为：返回空输入流
        when(requestWrapper.getInputStream()).thenReturn(emptyInputStream);

        // 3. 执行目标方法
        String actualBody = requestWrapper.body();

        // 4. 断言结果：返回空字符串
        assertEquals("", actualBody, "空请求体应返回空字符串");
    }

    /**
     * 异常路径2：读取输入流时抛出IO异常，应抛出运行时异常
     */
    @Test
    void body_WhenInputStreamThrowsIOException_ThrowsRuntimeException() throws IOException {
        // 1. 模拟依赖行为：调用getInputStream()时抛出IO异常
        when(requestWrapper.getInputStream()).thenThrow(new IOException("模拟输入流读取失败"));

        // 2. 执行目标方法并断言异常：预期抛出RuntimeException，且异常信息包含原始错误
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> requestWrapper.body(), 
            "读取输入流异常时应抛出RuntimeException"
        );
        assertTrue(exception.getMessage().contains("模拟输入流读取失败"), "异常信息应包含原始错误原因");
    }
}