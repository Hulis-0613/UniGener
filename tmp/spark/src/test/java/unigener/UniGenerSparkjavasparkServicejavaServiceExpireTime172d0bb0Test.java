import spark.Service;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkServicejavaServiceExpireTime172d0bb0Test {

    private Service service;

    @BeforeEach
    void setUp() {
        service = new Service(); // 初始化测试目标类
    }

    /**
     * 测试正常路径：输入正整数秒数，验证过期时间设置正确
     * 假设expireTime方法将过期时间设置为"当前时间 + seconds秒"的时间戳（毫秒）
     */
    @Test
    void expireTime_WithPositiveSeconds_SetsValidExpireTime() {
        // 准备：定义正整数秒数
        int seconds = 60;
        long expectedMinExpireTime = System.currentTimeMillis() + seconds * 1000L;
        long expectedMaxExpireTime = expectedMinExpireTime + 100; // 允许100ms时间误差

        // 执行：调用目标方法
        service.expireTime(seconds);

        // 验证：获取设置的过期时间，确认在预期范围内
        long actualExpireTime = service.getExpireTime(); // 假设Service有获取过期时间的方法
        assertTrue(actualExpireTime >= expectedMinExpireTime && actualExpireTime <= expectedMaxExpireTime,
                "过期时间应在当前时间+" + seconds + "秒左右");
    }

    /**
     * 测试异常路径：输入0秒，预期抛出IllegalArgumentException
     */
    @Test
    void expireTime_WithZeroSeconds_ThrowsIllegalArgumentException() {
        // 执行&验证：调用方法时抛出异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.expireTime(0),
                "输入0秒时应抛出IllegalArgumentException");
        
        // 可选：验证异常消息（若方法定义了具体消息）
        assertTrue(exception.getMessage().contains("秒数必须为正整数"), 
                "异常消息应提示秒数必须为正整数");
    }

    /**
     * 测试异常路径：输入负整数秒数，预期抛出IllegalArgumentException
     */
    @Test
    void expireTime_WithNegativeSeconds_ThrowsIllegalArgumentException() {
        // 执行&验证：调用方法时抛出异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.expireTime(-30),
                "输入负秒数时应抛出IllegalArgumentException");
        
        // 可选：验证异常消息（若方法定义了具体消息）
        assertTrue(exception.getMessage().contains("秒数必须为正整数"), 
                "异常消息应提示秒数必须为正整数");
    }
}