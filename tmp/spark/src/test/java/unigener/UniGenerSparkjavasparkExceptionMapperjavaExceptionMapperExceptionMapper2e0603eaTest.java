import spark.ExceptionMapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import javax.ws.rs.core.Response;

public class UniGenerSparkjavasparkExceptionMapperjavaExceptionMapperExceptionMapper2e0603eaTest {

    /**
     * 测试正常路径：无参构造函数能成功创建ExceptionMapper实例
     */
    @Test
    void testConstructor_Success() {
        // 执行：创建ExceptionMapper实例
        ExceptionMapper mapper = new ExceptionMapper();
        
        // 断言：实例非空，验证构造成功
        assertNotNull(mapper, "ExceptionMapper实例应成功创建");
    }

    /**
     * 测试异常路径：toResponse方法接收null异常时抛出NullPointerException
     */
    @Test
    void toResponse_WithNullException_ThrowsNullPointerException() {
        // 准备：创建ExceptionMapper实例
        ExceptionMapper mapper = new ExceptionMapper();
        
        // 执行&断言：传入null时抛出NullPointerException
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> mapper.toResponse(null),
            "当输入异常为null时，应抛出NullPointerException"
        );
        
        // 补充断言：异常消息符合预期（若实现中定义了消息）
        assertEquals("异常对象不能为null", exception.getMessage(), "异常消息不符合预期");
    }

    /**
     * 测试正常路径：toResponse方法处理合法异常时返回正确响应
     */
    @Test
    void toResponse_WithValidException_ReturnsExpectedResponse() {
        // 准备：创建ExceptionMapper实例和测试异常
        ExceptionMapper mapper = new ExceptionMapper();
        String testMessage = "测试异常消息";
        Exception testException = new Exception(testMessage);
        
        // 执行：调用toResponse处理异常
        Response response = mapper.toResponse(testException);
        
        // 断言：响应状态码为500（内部服务器错误）
        assertEquals(
            Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(),
            response.getStatus(),
            "响应状态码应为500（INTERNAL_SERVER_ERROR）"
        );
        
        // 断言：响应实体包含异常消息
        assertEquals(
            testMessage,
            response.getEntity(),
            "响应实体应包含异常的消息内容"
        );
    }
}