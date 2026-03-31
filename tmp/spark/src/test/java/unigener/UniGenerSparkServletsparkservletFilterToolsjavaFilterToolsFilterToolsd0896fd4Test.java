import spark.servlet.FilterTools;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkServletsparkservletFilterToolsjavaFilterToolsFilterToolsd0896fd4Test {

    /**
     * 测试私有构造方法的行为：确保无法实例化，且构造方法为私有。
     */
    @Test
    void testPrivateConstructor() throws NoSuchMethodException {
        // 1. 获取FilterTools类的无参构造方法
        Constructor<FilterTools> constructor = FilterTools.class.getDeclaredConstructor();
        
        // 2. 验证构造方法为私有
        assertTrue(Modifier.isPrivate(constructor.getModifiers()), "构造方法必须为私有");
        
        // 3. 设置构造方法可访问（绕过私有修饰符）
        constructor.setAccessible(true);
        
        // 4. 验证调用构造方法时抛出异常（防止实例化）
        AssertionError exception = assertThrows(AssertionError.class, constructor::newInstance,
                "工具类构造方法应抛出异常以防止实例化");
        
        // 5. 验证异常消息（可选，根据实际实现调整）
        assertEquals("Utility class should not be instantiated", exception.getMessage(),
                "异常消息不符合预期");
    }
}