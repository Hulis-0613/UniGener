import spark.staticfiles.StaticFilesConfiguration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkStaticfilessparkstaticfilesStaticFilesConfigurationjavaStaticFilesConfigurationSetExpireTimeSeconds65381f1aTest {

    private ExpireTimeConfig expireTimeConfig;

    @BeforeEach
    void setUp() {
        // 初始化测试对象
        expireTimeConfig = new ExpireTimeConfig();
    }

    /**
     * 正常路径：设置过期时间为0秒（边界值）
     */
    @Test
    void setExpireTimeSeconds_withZero_shouldSetSuccessfully() {
        // 执行目标方法
        expireTimeConfig.setExpireTimeSeconds(0);
        
        // 验证结果（假设存在getExpireTimeSeconds()方法获取设置值）
        assertEquals(0, expireTimeConfig.getExpireTimeSeconds(), "过期时间未正确设置为0秒");
    }

    /**
     * 正常路径：设置过期时间为正整数（典型值）
     */
    @Test
    void setExpireTimeSeconds_withPositiveValue_shouldSetSuccessfully() {
        long expectedExpireTime = 3600; // 1小时
        
        // 执行目标方法
        expireTimeConfig.setExpireTimeSeconds(expectedExpireTime);
        
        // 验证结果
        assertEquals(expectedExpireTime, expireTimeConfig.getExpireTimeSeconds(), 
            "过期时间未正确设置为" + expectedExpireTime + "秒");
    }

    /**
     * 异常路径：设置过期时间为负数（非法值）
     */
    @Test
    void setExpireTimeSeconds_withNegativeValue_shouldThrowIllegalArgumentException() {
        long invalidExpireTime = -1; // 负数为非法输入
        
        // 验证抛出预期异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
            () -> expireTimeConfig.setExpireTimeSeconds(invalidExpireTime),
            "设置负数过期时间时未抛出IllegalArgumentException");
        
        // 验证异常消息（可选，根据实际实现调整）
        assertTrue(exception.getMessage().contains("过期时间不能为负数"), 
            "异常消息未包含预期提示");
    }
}