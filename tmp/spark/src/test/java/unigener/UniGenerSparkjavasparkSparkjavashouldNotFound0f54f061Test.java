import spark.should;
import org.junit.jupiter.api.Test;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.Model;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSparkjavashouldNotFound0f54f061Test {

    private final should shouldInstance = new should(); // 实例化目标类

    /**
     * 正常路径：测试有效page参数（非空、非空白字符串）
     * 预期：返回404错误视图，模型中正确携带page参数
     */
    @Test
    void notFound_WithValidPage_Returns404ViewAndModel() {
        // 准备参数
        String testPage = "user/profile";
        Model model = new ExtendedModelMap();

        // 执行目标方法
        String resultView = shouldInstance.notFound(testPage, model);

        // 断言：视图名称正确，模型包含page参数且值匹配
        assertEquals("error/404", resultView, "未返回预期的404错误视图");
        assertTrue(model.containsAttribute("page"), "模型未包含page参数");
        assertEquals(testPage, model.getAttribute("page"), "模型中page参数值不匹配");
    }

    /**
     * 异常路径：测试page为null
     * 预期：正常处理null参数，返回404视图，模型中page参数为null
     */
    @Test
    void notFound_WithNullPage_Returns404ViewAndHandlesNull() {
        // 准备参数（page为null）
        String nullPage = null;
        Model model = new ExtendedModelMap();

        // 执行目标方法（不应抛出异常）
        String resultView = shouldInstance.notFound(nullPage, model);

        // 断言：视图名称正确，模型包含page参数且值为null
        assertEquals("error/404", resultView, "未返回预期的404错误视图");
        assertTrue(model.containsAttribute("page"), "模型未包含page参数");
        assertNull(model.getAttribute("page"), "模型中page参数应为null");
    }

    /**
     * 异常路径：测试page为空字符串
     * 预期：正常处理空字符串，返回404视图，模型中page参数为空字符串
     */
    @Test
    void notFound_WithEmptyPage_Returns404ViewAndHandlesEmpty() {
        // 准备参数（page为空字符串）
        String emptyPage = "";
        Model model = new ExtendedModelMap();

        // 执行目标方法
        String resultView = shouldInstance.notFound(emptyPage, model);

        // 断言：视图名称正确，模型包含page参数且值为空字符串
        assertEquals("error/404", resultView, "未返回预期的404错误视图");
        assertTrue(model.containsAttribute("page"), "模型未包含page参数");
        assertEquals(emptyPage, model.getAttribute("page"), "模型中page参数值不匹配");
    }

    /**
     * 异常路径：测试page包含特殊字符（如符号、空格）
     * 预期：正确处理特殊字符，返回404视图，模型中page参数值完整保留
     */
    @Test
    void notFound_WithSpecialCharsPage_Returns404ViewAndPreservesChars() {
        // 准备参数（包含特殊字符的page）
        String specialCharsPage = "admin@#/dashboard 123";
        Model model = new ExtendedModelMap();

        // 执行目标方法
        String resultView = shouldInstance.notFound(specialCharsPage, model);

        // 断言：视图名称正确，模型包含page参数且特殊字符完整保留
        assertEquals("error/404", resultView, "未返回预期的404错误视图");
        assertTrue(model.containsAttribute("page"), "模型未包含page参数");
        assertEquals(specialCharsPage, model.getAttribute("page"), "模型中page参数特殊字符未保留");
    }
}