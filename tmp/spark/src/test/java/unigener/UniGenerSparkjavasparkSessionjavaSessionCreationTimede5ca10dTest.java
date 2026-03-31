import spark.Session;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSessionjavaSessionCreationTimede5ca10dTest {

    /**
     * 正常路径测试：验证创建Session后，creationTime方法能正确返回非空的创建时间，且时间接近对象创建时刻
     */
    @Test
    void creationTime_ShouldReturnValidCreationTime() {
        // 1. 创建Session对象（假设构造函数会初始化creationTime）
        Session session = new Session();
        
        // 2. 调用目标方法获取创建时间
        LocalDateTime creationTime = session.creationTime();
        
        // 3. 断言：创建时间不为null，且与当前时间的差值在合理范围内（如1秒内，避免系统时间微小偏差）
        assertNotNull(creationTime, "创建时间不应为null");
        LocalDateTime now = LocalDateTime.now();
        long timeDiff = java.time.Duration.between(creationTime, now).toMillis();
        assertTrue(timeDiff >= 0 && timeDiff < 1000, "创建时间应接近对象创建时刻（偏差<1秒）");
    }

    /**
     * 异常路径测试：模拟创建时间未初始化的场景，验证方法抛出预期异常
     */
    @Test
    void creationTime_WhenCreationTimeNotInitialized_ShouldThrowIllegalStateException() throws NoSuchFieldException, IllegalAccessException {
        // 1. 创建Session对象
        Session session = new Session();
        
        // 2. 通过反射修改私有字段creationTime为null（模拟未初始化场景）
        Field creationTimeField = Session.class.getDeclaredField("creationTime");
        creationTimeField.setAccessible(true);
        creationTimeField.set(session, null);
        
        // 3. 调用目标方法，断言抛出IllegalStateException（假设方法内部有非null校验）
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            session::creationTime, 
            "当创建时间未初始化时，应抛出IllegalStateException");
        
        // 4. （可选）验证异常信息
        assertTrue(exception.getMessage().contains("创建时间未初始化"), "异常信息应包含'创建时间未初始化'");
    }
}