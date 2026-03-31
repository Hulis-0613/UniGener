import spark.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标方法所在类为 ThreadPoolServiceProvider
public class UniGenerSparkjavasparkServicejavaServiceThreadPool131fe277Test {

    /**
     * 测试正常路径：传入正整数 maxThreads，应返回非 null 的 Service 实例，且线程池配置符合预期。
     */
    @Test
    void threadPool_WithPositiveMaxThreads_ReturnsValidService() {
        // 准备：定义有效最大线程数（含边界值1和常规值10）
        int[] validMaxThreads = {1, 10};
        
        for (int maxThreads : validMaxThreads) {
            // 执行：调用目标方法
            ThreadPoolService service = ThreadPoolServiceProvider.threadPool(maxThreads);
            
            // 断言：返回实例非 null，且线程池最大线程数与输入一致
            assertNotNull(service, "Service instance should not be null for maxThreads=" + maxThreads);
            assertEquals(maxThreads, service.getMaxThreads(), 
                "Max threads of service should match input value: " + maxThreads);
        }
    }

    /**
     * 测试异常路径：传入 maxThreads=0，应抛出 IllegalArgumentException。
     */
    @Test
    void threadPool_WithZeroMaxThreads_ThrowsIllegalArgumentException() {
        int invalidMaxThreads = 0;
        
        // 执行并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> ThreadPoolServiceProvider.threadPool(invalidMaxThreads),
            "Calling threadPool with maxThreads=0 should throw IllegalArgumentException");
        
        // 验证异常信息（可选，根据实际业务逻辑调整）
        assertTrue(exception.getMessage().contains("maxThreads must be positive"),
            "Exception message should indicate positive requirement");
    }

    /**
     * 测试异常路径：传入负整数 maxThreads，应抛出 IllegalArgumentException。
     */
    @Test
    void threadPool_WithNegativeMaxThreads_ThrowsIllegalArgumentException() {
        int[] invalidMaxThreads = {-1, -100};
        
        for (int maxThreads : invalidMaxThreads) {
            // 执行并断言异常
            IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> ThreadPoolServiceProvider.threadPool(maxThreads),
                "Calling threadPool with maxThreads=" + maxThreads + " should throw IllegalArgumentException");
            
            // 验证异常信息（可选，根据实际业务逻辑调整）
            assertTrue(exception.getMessage().contains("maxThreads must be positive"),
                "Exception message should indicate positive requirement for maxThreads=" + maxThreads);
        }
    }
}