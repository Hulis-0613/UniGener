import spark.is;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.HashMap;
import java.util.Map;

/**
 * ModelAndView构造方法测试类，覆盖正常路径与边界情况
 */
public class UniGenerSparkjavasparkModelAndViewjavaisModelAndView8bb85e73Test {

    /**
     * 测试正常参数：非null model和非null viewName
     */
    @Test
    void testConstructor_WithValidModelAndViewName() {
        // 准备测试数据
        Map<String, Object> testModel = new HashMap<>();
        testModel.put("user", "testUser");
        testModel.put("age", 25);
        String testViewName = "homepage";

        // 执行构造方法
        ModelAndView modelAndView = new ModelAndView(testModel, testViewName);

        // 断言结果
        assertNotNull(modelAndView, "ModelAndView对象不应为null");
        assertEquals(testModel, modelAndView.getModel(), "model属性未正确设置");
        assertEquals(testViewName, modelAndView.getViewName(), "viewName属性未正确设置");
    }

    /**
     * 测试边界情况：null model + 非null viewName
     */
    @Test
    void testConstructor_WithNullModel() {
        // 准备测试数据
        Map<String, Object> nullModel = null;
        String testViewName = "errorPage";

        // 执行构造方法
        ModelAndView modelAndView = new ModelAndView(nullModel, testViewName);

        // 断言结果
        assertNotNull(modelAndView, "ModelAndView对象不应为null");
        assertNull(modelAndView.getModel(), "null model应被正确设置");
        assertEquals(testViewName, modelAndView.getViewName(), "viewName属性未正确设置");
    }

    /**
     * 测试边界情况：非null model + null viewName
     */
    @Test
    void testConstructor_WithNullViewName() {
        // 准备测试数据
        Map<String, Object> testModel = new HashMap<>();
        testModel.put("status", "success");
        String nullViewName = null;

        // 执行构造方法
        ModelAndView modelAndView = new ModelAndView(testModel, nullViewName);

        // 断言结果
        assertNotNull(modelAndView, "ModelAndView对象不应为null");
        assertEquals(testModel, modelAndView.getModel(), "model属性未正确设置");
        assertNull(modelAndView.getViewName(), "null viewName应被正确设置");
    }

    /**
     * 测试边界情况：null model + null viewName
     */
    @Test
    void testConstructor_WithNullModelAndViewName() {
        // 准备测试数据
        Map<String, Object> nullModel = null;
        String nullViewName = null;

        // 执行构造方法
        ModelAndView modelAndView = new ModelAndView(nullModel, nullViewName);

        // 断言结果
        assertNotNull(modelAndView, "ModelAndView对象不应为null");
        assertNull(modelAndView.getModel(), "null model应被正确设置");
        assertNull(modelAndView.getViewName(), "null viewName应被正确设置");
    }
}