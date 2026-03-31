import spark.utils.SparkUtils;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilssparkutilsSparkUtilsjavaSparkUtilsSparkUtilsdea5bf2aTest {

    /**
     * 测试私有构造方法的不可实例化特性及覆盖率
     */
    @Test
    void testPrivateConstructor() throws NoSuchMethodException {
        // 1. 验证构造方法存在且为私有
        Constructor<SparkUtils> constructor = SparkUtils.class.getDeclaredConstructor();
        assertTrue(Modifier.isPrivate(constructor.getModifiers()), "构造方法必须为私有");

        // 2. 尝试通过反射实例化，预期抛出异常（构造方法内部通常会主动抛出异常防止实例化）
        constructor.setAccessible(true); // 允许访问私有构造方法
        InvocationTargetException exception = assertThrows(InvocationTargetException.class, 
            constructor::newInstance, "工具类不允许实例化");

        // 3. 验证异常根源为构造方法主动抛出的AssertionError（标准工具类实现）
        assertTrue(exception.getCause() instanceof AssertionError, "构造方法应抛出AssertionError");
        assertEquals("Utility class cannot be instantiated", exception.getCause().getMessage(), 
            "异常消息不符合预期");
    }
}