import spark.is;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkCustomErrorPagesjavaisAdd6188f3b8Test {

    // 测试对象（假设目标方法所在类为AddService）
    private final AddService addService = new AddService();

    /**
     * 正常路径：合法status（正整数）+ 合法page（非空非空白字符串）
     */
    @Test
    void add_WithValidStatusAndPage_ReturnsSuccessMessage() {
        // 输入参数
        int validStatus = 200;
        String validPage = "home";
        
        // 执行目标方法
        String result = addService.add(validStatus, validPage);
        
        // 断言：返回预期的成功信息
        assertEquals("Added successfully - status: 200, page: home", result);
    }

    /**
     * 异常路径：status为负数（非法状态码）
     */
    @Test
    void add_WithNegativeStatus_ThrowsIllegalArgumentException() {
        // 输入参数（status非法，page合法）
        int invalidStatus = -10;
        String validPage = "about";
        
        // 执行目标方法并断言抛出异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> addService.add(invalidStatus, validPage));
        
        // 断言异常信息（可选，根据实际业务需求）
        assertEquals("Status must be a positive integer", exception.getMessage());
    }

    /**
     * 异常路径：status为0（非法状态码）
     */
    @Test
    void add_WithZeroStatus_ThrowsIllegalArgumentException() {
        // 输入参数（status为0，page合法）
        int invalidStatus = 0;
        String validPage = "contact";
        
        // 执行目标方法并断言抛出异常
        assertThrows(IllegalArgumentException.class, 
            () -> addService.add(invalidStatus, validPage));
    }

    /**
     * 异常路径：page为null（非法页面）
     */
    @Test
    void add_WithNullPage_ThrowsIllegalArgumentException() {
        // 输入参数（status合法，page为null）
        int validStatus = 100;
        String nullPage = null;
        
        // 执行目标方法并断言抛出异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, 
            () -> addService.add(validStatus, nullPage));
        
        // 断言异常信息（可选）
        assertEquals("Page cannot be null or blank", exception.getMessage());
    }

    /**
     * 异常路径：page为空字符串（非法页面）
     */
    @Test
    void add_WithEmptyPage_ThrowsIllegalArgumentException() {
        // 输入参数（status合法，page为空串）
        int validStatus = 150;
        String emptyPage = "";
        
        // 执行目标方法并断言抛出异常
        assertThrows(IllegalArgumentException.class, 
            () -> addService.add(validStatus, emptyPage));
    }

    /**
     * 异常路径：page为空白字符串（非法页面）
     */
    @Test
    void add_WithBlankPage_ThrowsIllegalArgumentException() {
        // 输入参数（status合法，page为空白串）
        int validStatus = 300;
        String blankPage = "   "; // 全空格
        
        // 执行目标方法并断言抛出异常
        assertThrows(IllegalArgumentException.class, 
            () -> addService.add(validStatus, blankPage));
    }
}