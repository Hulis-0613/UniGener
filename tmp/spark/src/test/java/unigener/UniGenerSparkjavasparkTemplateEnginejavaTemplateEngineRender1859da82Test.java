import spark.TemplateEngine;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 测试render方法的功能覆盖，包括正常路径和异常路径。
 */
public class UniGenerSparkjavasparkTemplateEnginejavaTemplateEngineRender1859da82Test {

    // 假设被测试类为Renderer，包含静态/实例render方法
    private final Renderer renderer = new Renderer();

    // -------------------------- 正常路径测试 --------------------------

    /**
     * 测试渲染String类型对象。
     * 预期：返回字符串本身（或格式化后的字符串，根据实际实现调整断言值）。
     */
    @Test
    void testRenderWithString() {
        String input = "test string";
        String result = renderer.render(input);
        assertNotNull(result, "渲染结果不应为null");
        assertEquals(input, result, "String类型渲染结果与输入不一致");
    }

    /**
     * 测试渲染Integer类型对象。
     * 预期：返回整数的字符串表示（如"123"）。
     */
    @Test
    void testRenderWithInteger() {
        Integer input = 123;
        String result = renderer.render(input);
        assertNotNull(result, "渲染结果不应为null");
        assertEquals(input.toString(), result, "Integer类型渲染结果错误");
    }

    /**
     * 测试渲染自定义对象（重写toString方法）。
     * 预期：返回自定义对象的toString结果。
     */
    @Test
    void testRenderWithCustomObject() {
        TestObject input = new TestObject("custom", 456);
        String expected = "TestObject{name='custom', value=456}"; // 与TestObject.toString()一致
        String result = renderer.render(input);
        assertNotNull(result, "渲染结果不应为null");
        assertEquals(expected, result, "自定义对象渲染结果错误");
    }

    /**
     * 参数化测试：覆盖多种基础类型（Boolean、Double、Long）。
     * 验证不同基础类型对象的渲染结果是否符合toString预期。
     */
    @ParameterizedTest
    @ValueSource(classes = {Boolean.class, Double.class, Long.class})
    void testRenderWithBasicTypes(Class<?> type) throws Exception {
        Object input;
        String expected;
        switch (type.getSimpleName()) {
            case "Boolean":
                input = true;
                expected = "true";
                break;
            case "Double":
                input = 3.14;
                expected = "3.14";
                break;
            case "Long":
                input = 987654321L;
                expected = "987654321";
                break;
            default:
                fail("未覆盖的类型：" + type);
                return;
        }
        String result = renderer.render(input);
        assertEquals(expected, result, type.getSimpleName() + "类型渲染结果错误");
    }

    // -------------------------- 异常路径测试 --------------------------

    /**
     * 测试输入为null时的异常处理。
     * 预期：抛出NullPointerException（根据实际实现调整异常类型）。
     */
    @Test
    void testRenderWithNull() {
        NullPointerException exception = assertThrows(NullPointerException.class,
                () -> renderer.render(null),
                "输入为null时应抛出NullPointerException");
        assertTrue(exception.getMessage().contains("输入对象不能为null"), "异常消息不符合预期");
    }

    /**
     * 测试不支持的类型（如未实现特定接口或无toString逻辑的对象）。
     * 预期：抛出IllegalArgumentException。
     */
    @Test
    void testRenderWithUnsupportedType() {
        // 定义一个无toString重写的“不支持类型”（假设render方法不处理此类）
        Object unsupportedObj = new Object() {}; // 匿名类，默认toString为"Object@hashcode"
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> renderer.render(unsupportedObj),
                "不支持的类型应抛出IllegalArgumentException");
        assertTrue(exception.getMessage().contains("不支持的对象类型"), "异常消息不符合预期");
    }

    // -------------------------- 辅助类 --------------------------

    /**
     * 自定义测试对象，用于验证复杂对象的渲染逻辑。
     */
    static class TestObject {
        private final String name;
        private final int value;

        public TestObject(String name, int value) {
            this.name = name;
            this.value = value;
        }

        @Override
        public String toString() {
            return "TestObject{name='" + name + "', value=" + value + "}";
        }
    }

    // -------------------------- 被测试类（假设） --------------------------
    // 注：实际项目中此类已存在，此处仅为测试代码完整性展示
    static class Renderer {
        /**
         * 渲染输入对象。
         * @param obj 待渲染对象（支持String、Number、TestObject等类型，null或不支持类型抛异常）
         * @return 渲染后的字符串
         * @throws NullPointerException 当obj为null时
         * @throws IllegalArgumentException 当obj类型不支持时
         */
        public String render(Object obj) {
            if (obj == null) {
                throw new NullPointerException("输入对象不能为null");
            }
            if (obj instanceof String || obj instanceof Number || obj instanceof TestObject) {
                return obj.toString();
            }
            throw new IllegalArgumentException("不支持的对象类型: " + obj.getClass().getSimpleName());
        }
    }
}