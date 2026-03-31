import spark.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设被测试的Service类名为ServerService
public class UniGenerSparkjavasparkServicejavaServiceEmbeddedServerIdentifier18a4a198Test {

    private final ServerService serverService = new ServerService(); // 实例化被测试类

    /**
     * 正常路径：输入合法String类型，包含服务器标识符（如"server-123"格式）
     */
    @Test
    void embeddedServerIdentifier_WithValidString_ReturnsIdentifier() {
        // 输入：符合格式的字符串（假设格式为"server-{id}"）
        Object validObj = "server-456";
        String result = serverService.embeddedServerIdentifier(validObj);
        
        // 断言：提取到正确的标识符（"456"）
        assertEquals("456", result);
    }

    /**
     * 正常路径：输入自定义对象，其toString()返回合法标识符格式
     */
    @Test
    void embeddedServerIdentifier_WithValidObject_ReturnsIdentifier() {
        // 自定义对象：toString()返回"server-789"
        Object validObj = new Object() {
            @Override
            public String toString() {
                return "server-789";
            }
        };
        String result = serverService.embeddedServerIdentifier(validObj);
        
        // 断言：提取到正确的标识符（"789"）
        assertEquals("789", result);
    }

    /**
     * 异常路径：输入null，预期抛出IllegalArgumentException
     */
    @Test
    void embeddedServerIdentifier_WithNullObj_ThrowsIllegalArgumentException() {
        Object nullObj = null;
        
        // 断言：抛出异常且消息包含"输入对象不能为null"
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serverService.embeddedServerIdentifier(nullObj));
        assertTrue(exception.getMessage().contains("输入对象不能为null"));
    }

    /**
     * 异常路径：输入不支持的类型（如Integer），toString()无合法标识符
     */
    @Test
    void embeddedServerIdentifier_WithUnsupportedType_ThrowsIllegalArgumentException() {
        Object unsupportedObj = 123; // Integer类型，toString()为"123"（无"server-"前缀）
        
        // 断言：抛出异常且消息包含"不支持的对象类型"
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serverService.embeddedServerIdentifier(unsupportedObj));
        assertTrue(exception.getMessage().contains("不支持的对象类型"));
    }

    /**
     * 异常路径：输入String但格式错误（无标识符信息）
     */
    @Test
    void embeddedServerIdentifier_WithInvalidStringFormat_ThrowsIllegalArgumentException() {
        Object invalidStrObj = "invalid-server"; // 无"server-{id}"格式
        
        // 断言：抛出异常且消息包含"无效的服务器标识符格式"
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> serverService.embeddedServerIdentifier(invalidStrObj));
        assertTrue(exception.getMessage().contains("无效的服务器标识符格式"));
    }
}