import spark.Response;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkResponsejavaResponseStatusa4a3f7b1Test {

    // 正常路径：测试合法状态码（覆盖不同范围的有效值）
    @Test
    void setStatus_WithValidStatusCode_LowerBound() {
        // 准备
        StatusManager statusManager = new StatusManager();
        int validStatusCode = 100; // 最小合法值

        // 执行
        statusManager.setStatus(validStatusCode);

        // 断言
        assertEquals(validStatusCode, statusManager.getStatus(), "状态码设置失败（下界值）");
    }

    @Test
    void setStatus_WithValidStatusCode_MiddleRange() {
        // 准备
        StatusManager statusManager = new StatusManager();
        int validStatusCode = 200; // 中间范围值

        // 执行
        statusManager.setStatus(validStatusCode);

        // 断言
        assertEquals(validStatusCode, statusManager.getStatus(), "状态码设置失败（中间值）");
    }

    @Test
    void setStatus_WithValidStatusCode_UpperBound() {
        // 准备
        StatusManager statusManager = new StatusManager();
        int validStatusCode = 599; // 最大合法值

        // 执行
        statusManager.setStatus(validStatusCode);

        // 断言
        assertEquals(validStatusCode, statusManager.getStatus(), "状态码设置失败（上界值）");
    }

    // 异常路径：测试非法状态码（覆盖小于最小值、大于最大值、负数、零）
    @Test
    void setStatus_WithStatusCodeBelowMinimum_ThrowsIllegalArgumentException() {
        // 准备
        StatusManager statusManager = new StatusManager();
        int invalidStatusCode = 99; // 小于最小合法值（100）

        // 执行 & 断言
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> statusManager.setStatus(invalidStatusCode),
                "未抛出预期的IllegalArgumentException（小于最小值）");
        
        assertTrue(exception.getMessage().contains("Invalid status code"), 
                "异常消息未包含预期内容（小于最小值）");
    }

    @Test
    void setStatus_WithStatusCodeAboveMaximum_ThrowsIllegalArgumentException() {
        // 准备
        StatusManager statusManager = new StatusManager();
        int invalidStatusCode = 600; // 大于最大合法值（599）

        // 执行 & 断言
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> statusManager.setStatus(invalidStatusCode),
                "未抛出预期的IllegalArgumentException（大于最大值）");
        
        assertTrue(exception.getMessage().contains("Invalid status code"), 
                "异常消息未包含预期内容（大于最大值）");
    }

    @Test
    void setStatus_WithNegativeStatusCode_ThrowsIllegalArgumentException() {
        // 准备
        StatusManager statusManager = new StatusManager();
        int invalidStatusCode = -50; // 负数

        // 执行 & 断言
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> statusManager.setStatus(invalidStatusCode),
                "未抛出预期的IllegalArgumentException（负数）");
        
        assertTrue(exception.getMessage().contains("Invalid status code"), 
                "异常消息未包含预期内容（负数）");
    }

    @Test
    void setStatus_WithZeroStatusCode_ThrowsIllegalArgumentException() {
        // 准备
        StatusManager statusManager = new StatusManager();
        int invalidStatusCode = 0; // 零

        // 执行 & 断言
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> statusManager.setStatus(invalidStatusCode),
                "未抛出预期的IllegalArgumentException（零）");
        
        assertTrue(exception.getMessage().contains("Invalid status code"), 
                "异常消息未包含预期内容（零）");
    }
}