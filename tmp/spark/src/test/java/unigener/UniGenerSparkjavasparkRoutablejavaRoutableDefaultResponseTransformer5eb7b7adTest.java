import spark.Routable;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标方法所在类为 ResponseConfig
public class UniGenerSparkjavasparkRoutablejavaRoutableDefaultResponseTransformer5eb7b7adTest {

    /**
     * 正常路径：传入有效 ResponseTransformer，验证默认转换器被正确设置。
     */
    @Test
    void defaultResponseTransformer_WithValidTransformer_SetsDefaultSuccessfully() {
        // 1. 准备测试数据：创建一个测试用的 ResponseTransformer 实例
        ResponseTransformer testTransformer = response -> {
            // 简单实现：原样返回响应（可根据实际接口定义调整）
            return response;
        };

        // 2. 执行目标方法：设置默认转换器
        ResponseConfig.defaultResponseTransformer(testTransformer);

        // 3. 断言：默认转换器已被设置为测试实例
        assertSame(
            testTransformer, 
            ResponseConfig.getDefaultResponseTransformer(), 
            "默认响应转换器未正确设置"
        );
    }

    /**
     * 异常路径：传入 null 参数，验证抛出 NullPointerException。
     */
    @Test
    void defaultResponseTransformer_WithNullTransformer_ThrowsNullPointerException() {
        // 执行目标方法并断言异常类型
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> ResponseConfig.defaultResponseTransformer(null),
            "传入 null 时未抛出预期的 NullPointerException"
        );

        // （可选）验证异常消息（若方法实现中定义了具体消息）
        assertTrue(
            exception.getMessage().contains("transformer must not be null"),
            "异常消息不符合预期"
        );
    }
}