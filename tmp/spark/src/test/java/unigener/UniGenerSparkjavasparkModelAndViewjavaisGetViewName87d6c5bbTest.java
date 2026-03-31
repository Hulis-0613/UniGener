import spark.is;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkModelAndViewjavaisGetViewName87d6c5bbTest {

    /**
     * 测试正常路径：当视图名称已正确初始化时，返回预期的视图名称
     */
    @Test
    void getViewName_WithValidViewName_ReturnsExpectedView() {
        // Arrange：创建is类实例并初始化视图名称
        is testInstance = new is("userProfileView"); // 假设is类有构造函数可初始化视图名称

        // Act：调用目标方法
        String actualViewName = testInstance.getViewName();

        // Assert：验证返回值与预期一致
        assertEquals("userProfileView", actualViewName, "视图名称应匹配初始化值");
    }

    /**
     * 测试异常路径：当视图名称未初始化（为空）时，抛出NullPointerException
     */
    @Test
    void getViewName_WithNullViewName_ThrowsNullPointerException() {
        // Arrange：创建is类实例但不初始化视图名称（设为null）
        is testInstance = new is(null); // 假设构造函数允许传入null

        // Act & Assert：验证调用方法时抛出预期异常
        NullPointerException exception = assertThrows(
            NullPointerException.class,
            () -> testInstance.getViewName(),
            "当视图名称为null时，应抛出NullPointerException"
        );
        assertEquals("视图名称未初始化", exception.getMessage(), "异常消息应符合预期");
    }
}