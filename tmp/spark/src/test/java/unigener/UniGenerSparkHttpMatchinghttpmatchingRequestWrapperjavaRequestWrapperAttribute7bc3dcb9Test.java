import spark.http.matching.RequestWrapper;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingRequestWrapperjavaRequestWrapperAttribute7bc3dcb9Test {

    /**
     * 正常路径：测试设置非空属性名和非空属性值
     */
    @Test
    void attribute_ValidAttributeAndValue_SetsAttributeSuccessfully() {
        // 准备测试数据
        RequestWrapper requestWrapper = new RequestWrapper();
        String testAttribute = "userName";
        Object testValue = "testUser";

        // 执行目标方法
        requestWrapper.attribute(testAttribute, testValue);

        // 验证结果：属性值正确设置
        Object actualValue = requestWrapper.getAttribute(testAttribute);
        assertNotNull(actualValue, "设置的属性值不应为null");
        assertEquals(testValue, actualValue, "属性值与预期不符");
    }

    /**
     * 正常路径：测试设置非空属性名和null属性值
     */
    @Test
    void attribute_ValidAttributeAndNullValue_SetsNullValueSuccessfully() {
        // 准备测试数据
        RequestWrapper requestWrapper = new RequestWrapper();
        String testAttribute = "sessionId";
        Object testValue = null;

        // 执行目标方法
        requestWrapper.attribute(testAttribute, testValue);

        // 验证结果：null值正确设置
        Object actualValue = requestWrapper.getAttribute(testAttribute);
        assertNull(actualValue, "设置的null属性值未正确存储");
    }

    /**
     * 异常路径：测试属性名为null时抛出IllegalArgumentException
     */
    @Test
    void attribute_NullAttribute_ThrowsIllegalArgumentException() {
        // 准备测试数据
        RequestWrapper requestWrapper = new RequestWrapper();
        String nullAttribute = null;
        Object testValue = "testValue";

        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> requestWrapper.attribute(nullAttribute, testValue),
                "属性名为null时应抛出IllegalArgumentException");

        // 验证异常信息（若方法定义了具体消息可补充断言）
        assertNotNull(exception.getMessage(), "异常消息不应为null");
    }

    /**
     * 异常路径：测试属性名为空字符串时抛出IllegalArgumentException
     */
    @Test
    void attribute_EmptyAttribute_ThrowsIllegalArgumentException() {
        // 准备测试数据
        RequestWrapper requestWrapper = new RequestWrapper();
        String emptyAttribute = "";
        Object testValue = "testValue";

        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> requestWrapper.attribute(emptyAttribute, testValue),
                "属性名为空字符串时应抛出IllegalArgumentException");

        // 验证异常信息（若方法定义了具体消息可补充断言）
        assertNotNull(exception.getMessage(), "异常消息不应为null");
    }
}