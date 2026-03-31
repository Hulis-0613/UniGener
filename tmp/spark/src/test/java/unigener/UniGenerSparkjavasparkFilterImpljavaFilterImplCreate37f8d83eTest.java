import spark.FilterImpl;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkFilterImpljavaFilterImplCreate37f8d83eTest {

    // 正常路径：有效path和filter参数，成功创建FilterImpl实例
    @Test
    void testCreate_WithValidPathAndFilter_ReturnsFilterImpl() {
        // 准备测试数据
        String validPath = "/test/path";
        Object validFilter = new Object(); // 假设filter为任意非null对象

        // 执行目标方法
        FilterImpl result = FilterImpl.create(validPath, validFilter);

        // 断言结果：实例非null，且属性正确
        assertNotNull(result, "创建的FilterImpl实例不应为null");
        assertEquals(validPath, result.getPath(), "返回实例的path应与输入一致");
        assertSame(validFilter, result.getFilter(), "返回实例的filter应与输入一致");
    }

    // 异常路径：path为null时抛出IllegalArgumentException
    @Test
    void testCreate_WithNullPath_ThrowsIllegalArgumentException() {
        // 准备测试数据（path为null，filter为非null）
        String nullPath = null;
        Object validFilter = new Object();

        // 执行目标方法并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FilterImpl.create(nullPath, validFilter),
                "path为null时应抛出IllegalArgumentException");
        
        // 可选：验证异常消息（若方法定义了具体消息）
        assertTrue(exception.getMessage().contains("path"), "异常消息应包含'path'关键字");
    }

    // 异常路径：path为空字符串时抛出IllegalArgumentException
    @Test
    void testCreate_WithEmptyPath_ThrowsIllegalArgumentException() {
        // 准备测试数据（path为空字符串，filter为非null）
        String emptyPath = "";
        Object validFilter = new Object();

        // 执行目标方法并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FilterImpl.create(emptyPath, validFilter),
                "path为空字符串时应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("path"), "异常消息应包含'path'关键字");
    }

    // 异常路径：path为空白字符串（仅空格）时抛出IllegalArgumentException
    @Test
    void testCreate_WithBlankPath_ThrowsIllegalArgumentException() {
        // 准备测试数据（path为空白字符串，filter为非null）
        String blankPath = "   ";
        Object validFilter = new Object();

        // 执行目标方法并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FilterImpl.create(blankPath, validFilter),
                "path为空白字符串时应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("path"), "异常消息应包含'path'关键字");
    }

    // 异常路径：filter为null时抛出IllegalArgumentException
    @Test
    void testCreate_WithNullFilter_ThrowsIllegalArgumentException() {
        // 准备测试数据（path有效，filter为null）
        String validPath = "/test/path";
        Object nullFilter = null;

        // 执行目标方法并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> FilterImpl.create(validPath, nullFilter),
                "filter为null时应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("filter"), "异常消息应包含'filter'关键字");
    }
}