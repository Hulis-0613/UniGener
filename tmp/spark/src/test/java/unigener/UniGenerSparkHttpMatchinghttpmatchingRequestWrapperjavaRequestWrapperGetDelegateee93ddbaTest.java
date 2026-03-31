import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

/**
 * 测试 {@link RequestWrapper#getDelegate()} 方法的功能与异常场景。
 */
public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperGetDelegateee93ddbaTest {

    /**
     * 正常路径：当委托对象已设置时，{@link RequestWrapper#getDelegate()} 应返回正确的委托对象。
     */
    @Test
    void getDelegate_WithValidDelegate_ReturnsDelegate() {
        // 1. 准备测试数据：创建 mock 委托对象和包装类实例
        Request mockDelegate = mock(Request.class);
        RequestWrapper wrapper = new RequestWrapper(mockDelegate);

        // 2. 执行目标方法
        Request result = wrapper.getDelegate();

        // 3. 断言结果：返回的委托对象与设置的一致
        assertSame("委托对象返回错误", mockDelegate, result);
    }

    /**
     * 异常路径：当委托对象未设置（为 null）时，{@link RequestWrapper#getDelegate()} 应抛出 {@link IllegalStateException}。
     */
    @Test
    void getDelegate_WithNullDelegate_ThrowsIllegalStateException() {
        // 1. 准备测试数据：创建未设置委托的包装类实例
        RequestWrapper wrapper = new RequestWrapper(null);

        // 2. 执行目标方法并断言异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            wrapper::getDelegate,
            "委托对象为 null 时未抛出预期异常"
        );

        // 3. 断言异常消息（若方法实现中定义了具体消息）
        assertEquals("委托对象未初始化", exception.getMessage(), "异常消息不符合预期");
    }
}