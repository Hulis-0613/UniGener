import spark.utils.provides;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * 测试IOUtils类的私有构造方法，确保无法实例化该工具类。
 */
public class UniGenerSparkUtilssparkutilsIOUtilsjavaprovidesIOUtils8ec1ecd3Test {

    /**
     * 验证私有构造方法被调用时抛出异常，防止类实例化。
     * 
     * @throws NoSuchMethodException 如果IOUtils类不存在无参构造方法（理论上不应发生）
     */
    @Test
    void testPrivateConstructorPreventsInstantiation() throws NoSuchMethodException {
        // 1. 获取IOUtils类的私有无参构造方法
        Constructor<IOUtils> constructor = IOUtils.class.getDeclaredConstructor();
        
        // 2. 设置构造方法可访问（私有方法默认不可反射调用）
        constructor.setAccessible(true);
        
        // 3. 尝试通过反射实例化，期望抛出InvocationTargetException（构造方法内部抛出的异常会被包装）
        InvocationTargetException exception = assertThrows(
            InvocationTargetException.class, 
            constructor::newInstance, 
            "调用私有构造方法应抛出异常"
        );
        
        // 4. 验证异常根源是UnsupportedOperationException（工具类通常用此异常阻止实例化）
        assertTrue(
            exception.getCause() instanceof UnsupportedOperationException,
            "构造方法应抛出UnsupportedOperationException以防止实例化"
        );
        
        // 5. （可选）验证异常消息（若构造方法定义了具体消息）
        // assertEquals("Utility class cannot be instantiated", exception.getCause().getMessage());
    }
}