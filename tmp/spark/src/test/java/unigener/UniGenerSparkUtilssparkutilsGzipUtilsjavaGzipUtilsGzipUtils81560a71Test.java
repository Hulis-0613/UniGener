import spark.utils.GzipUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 测试GzipUtils工具类的实例化防护逻辑。
 */
public class UniGenerSparkUtilssparkutilsGzipUtilsjavaGzipUtilsGzipUtils81560a71Test {

    /**
     * 验证GzipUtils类无法被实例化：
     * 1. 构造方法为私有
     * 2. 反射调用构造方法时抛出异常（防止实例化）
     */
    @Test
    void testPrivateConstructorPreventsInstantiation() throws NoSuchMethodException {
        // 1. 获取GzipUtils的私有构造方法
        Constructor<GzipUtils> constructor = GzipUtils.class.getDeclaredConstructor();
        
        // 2. 验证构造方法确实为私有
        assertTrue(constructor.isAccessible(), "构造方法初始应不可访问");
        
        // 3. 强制设置构造方法可访问（模拟反射攻击）
        constructor.setAccessible(true);
        
        // 4. 尝试通过反射实例化，预期抛出InvocationTargetException（构造方法内部抛出异常）
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, 
            constructor::newInstance, "调用私有构造方法应抛出异常");
        
        // 5. 验证异常根源为工具类防实例化的特定异常（如AssertionError）
        assertTrue(exception.getCause() instanceof AssertionError, 
            "构造方法应抛出AssertionError防止实例化");
        assertEquals("Utility class should not be instantiated", exception.getCause().getMessage(),
            "异常消息应提示工具类不可实例化");
    }
}