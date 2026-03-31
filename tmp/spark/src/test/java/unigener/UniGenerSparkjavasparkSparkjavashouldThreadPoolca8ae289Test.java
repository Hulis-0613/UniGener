import spark.should;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

public class UniGenerSparkjavasparkSparkjavashouldThreadPoolca8ae289Test {

    // 正常路径：测试有效正整数maxThreads（一般值）
    @Test
    void threadPool_WithValidMaxThreads_ReturnsThreadPoolWithCorrectMaxSize() {
        // 准备：定义有效最大线程数
        int maxThreads = 5;
        
        // 执行：调用目标方法创建线程池
        ExecutorService executor = threadPool(maxThreads);
        
        // 断言：线程池类型正确且最大线程数匹配
        assertTrue(executor instanceof ThreadPoolExecutor, "返回的线程池应为ThreadPoolExecutor类型");
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) executor;
        assertEquals(maxThreads, threadPool.getMaximumPoolSize(), "最大线程数未按输入参数设置");
        
        // 清理：关闭线程池释放资源
        threadPool.shutdown();
    }

    // 正常路径：测试边界值maxThreads=1
    @Test
    void threadPool_WithMaxThreadsOne_ReturnsThreadPoolWithMaxSizeOne() {
        // 准备：边界值1
        int maxThreads = 1;
        
        // 执行：创建线程池
        ExecutorService executor = threadPool(maxThreads);
        
        // 断言：最大线程数为1
        ThreadPoolExecutor threadPool = (ThreadPoolExecutor) executor;
        assertEquals(1, threadPool.getMaximumPoolSize(), "边界值maxThreads=1未正确处理");
        
        // 清理
        threadPool.shutdown();
    }

    // 异常路径：测试maxThreads=0（非正整数）
    @Test
    void threadPool_WithZeroMaxThreads_ThrowsIllegalArgumentException() {
        // 准备：无效参数0
        int maxThreads = 0;
        
        // 执行并断言：抛出IllegalArgumentException
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> threadPool(maxThreads), "maxThreads=0时未抛出预期异常");
        
        // 可选：验证异常消息（若方法实现包含具体消息）
        assertTrue(exception.getMessage().contains("maxThreads must be positive"), 
                "异常消息不符合预期");
    }

    // 异常路径：测试maxThreads为负数
    @Test
    void threadPool_WithNegativeMaxThreads_ThrowsIllegalArgumentException() {
        // 准备：无效参数-3
        int maxThreads = -3;
        
        // 执行并断言：抛出IllegalArgumentException
        assertThrows(IllegalArgumentException.class,
                () -> threadPool(maxThreads), "maxThreads为负数时未抛出预期异常");
    }

    // 目标方法（假设的实现，实际测试时需替换为真实方法引用）
    private ExecutorService threadPool(int maxThreads) {
        if (maxThreads <= 0) {
            throw new IllegalArgumentException("maxThreads must be positive");
        }
        return new ThreadPoolExecutor(
                maxThreads,    // 核心线程数（假设与maxThreads一致）
                maxThreads,    // 最大线程数
                0L,            // 空闲线程存活时间
                java.util.concurrent.TimeUnit.MILLISECONDS,
                new java.util.concurrent.SynchronousQueue<>()
        );
    }
}