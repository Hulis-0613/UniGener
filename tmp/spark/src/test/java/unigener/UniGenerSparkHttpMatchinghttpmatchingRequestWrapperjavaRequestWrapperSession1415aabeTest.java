import spark.http.matching.RequestWrapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperSession1415aabeTest {

    @Mock
    private HttpServletRequest request;  // 模拟依赖的HttpServletRequest

    @InjectMocks
    private RequestWrapper requestWrapper;  // 待测试的RequestWrapper实例


    /**
     * 正常路径测试：成功获取Session对象
     */
    @Test
    void session_ShouldReturnValidSession_WhenSessionExists() {
        // 1. 准备测试数据：模拟一个有效的Session对象
        HttpSession mockSession = mock(HttpSession.class);
        
        // 2. 模拟依赖行为：当调用request.getSession()时返回模拟的Session
        when(request.getSession()).thenReturn(mockSession);
        
        // 3. 执行目标方法
        HttpSession result = requestWrapper.session();
        
        // 4. 断言结果：返回的Session与模拟对象一致
        assertNotNull(result, "Session should not be null");
        assertSame(mockSession, result, "Returned session should match the mocked session");
        verify(request).getSession();  // 验证依赖方法被调用
    }


    /**
     * 异常路径测试：获取Session时抛出异常
     */
    @Test
    void session_ShouldThrowException_WhenSessionRetrievalFails() {
        // 1. 准备测试数据：定义预期异常
        IllegalStateException expectedException = new IllegalStateException("Session unavailable: request is invalid");
        
        // 2. 模拟依赖行为：当调用request.getSession()时抛出异常
        when(request.getSession()).thenThrow(expectedException);
        
        // 3. 执行目标方法并断言异常
        IllegalStateException actualException = assertThrows(
            IllegalStateException.class,
            () -> requestWrapper.session(),
            "Expected IllegalStateException when session retrieval fails"
        );
        
        // 4. 断言异常信息匹配
        assertEquals(expectedException.getMessage(), actualException.getMessage(), "Exception message mismatch");
        verify(request).getSession();  // 验证依赖方法被调用
    }
}