import spark.utils.MimeParse;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilssparkutilsMimeParsejavaMimeParseFitnessAndQualitye29cf3bcTest {

    /**
     * 测试正常路径：使用合法参数构造对象，验证字段值正确性
     */
    @Test
    void testConstructor_ValidParameters() throws Exception {
        // 1. 准备合法参数
        int expectedFitness = 95;
        float expectedQuality = 0.85f;

        // 2. 通过反射获取私有构造方法并设置可访问
        Class<?> targetClass = FitnessAndQuality.class;
        Constructor<?> constructor = targetClass.getDeclaredConstructor(int.class, float.class);
        constructor.setAccessible(true);

        // 3. 调用构造方法创建实例
        Object instance = constructor.newInstance(expectedFitness, expectedQuality);
        assertNotNull(instance, "构造对象不应为null");

        // 4. 反射获取私有字段并验证值
        Field fitnessField = targetClass.getDeclaredField("fitness");
        fitnessField.setAccessible(true);
        int actualFitness = (int) fitnessField.get(instance);
        assertEquals(expectedFitness, actualFitness, "fitness字段值不匹配");

        Field qualityField = targetClass.getDeclaredField("quality");
        qualityField.setAccessible(true);
        float actualQuality = (float) qualityField.get(instance);
        assertEquals(expectedQuality, actualQuality, 0.001f, "quality字段值不匹配");
    }

    /**
     * 测试异常路径：fitness为负数时构造方法抛出IllegalArgumentException
     */
    @Test
    void testConstructor_InvalidFitness_Negative() {
        // 1. 准备非法参数（fitness为负）
        int invalidFitness = -10;
        float validQuality = 0.5f;

        // 2. 验证构造方法抛出异常
        assertThrows(IllegalArgumentException.class, () -> {
            Class<?> targetClass = FitnessAndQuality.class;
            Constructor<?> constructor = targetClass.getDeclaredConstructor(int.class, float.class);
            constructor.setAccessible(true);
            constructor.newInstance(invalidFitness, validQuality);
        }, "fitness为负数时应抛出IllegalArgumentException");
    }

    /**
     * 测试异常路径：quality为负数时构造方法抛出IllegalArgumentException
     */
    @Test
    void testConstructor_InvalidQuality_Negative() {
        // 1. 准备非法参数（quality为负）
        int validFitness = 80;
        float invalidQuality = -0.2f;

        // 2. 验证构造方法抛出异常
        assertThrows(IllegalArgumentException.class, () -> {
            Class<?> targetClass = FitnessAndQuality.class;
            Constructor<?> constructor = targetClass.getDeclaredConstructor(int.class, float.class);
            constructor.setAccessible(true);
            constructor.newInstance(validFitness, invalidQuality);
        }, "quality为负数时应抛出IllegalArgumentException");
    }

    /**
     * 测试异常路径：quality大于1时构造方法抛出IllegalArgumentException
     */
    @Test
    void testConstructor_InvalidQuality_ExceedsUpperBound() {
        // 1. 准备非法参数（quality大于1）
        int validFitness = 80;
        float invalidQuality = 1.1f;

        // 2. 验证构造方法抛出异常
        assertThrows(IllegalArgumentException.class, () -> {
            Class<?> targetClass = FitnessAndQuality.class;
            Constructor<?> constructor = targetClass.getDeclaredConstructor(int.class, float.class);
            constructor.setAccessible(true);
            constructor.newInstance(validFitness, invalidQuality);
        }, "quality大于1时应抛出IllegalArgumentException");
    }
}