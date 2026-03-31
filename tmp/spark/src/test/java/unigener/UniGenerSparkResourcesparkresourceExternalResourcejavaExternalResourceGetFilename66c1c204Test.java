import spark.resource.ExternalResource;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkResourcesparkresourceExternalResourcejavaExternalResourceGetFilename66c1c204Test {

    /**
     * 测试正常路径：资源正确初始化时，getFilename返回预期文件名
     */
    @Test
    void testGetFilename_NormalCase() {
        // 假设构造函数接收资源路径并初始化内部状态
        ExternalResource resource = new ExternalResource("sample_data.txt");
        String actualFilename = resource.getFilename();
        
        // 断言返回的文件名与构造时传入的一致
        assertEquals("sample_data.txt", actualFilename, "正常初始化时应返回正确的文件名");
    }

    /**
     * 测试异常路径：资源未初始化（如构造时传入null路径），getFilename抛出NullPointerException
     */
    @Test
    void testGetFilename_WhenResourcePathNull_ThrowsNullPointerException() {
        // 构造时传入null路径，模拟未正确初始化的场景
        ExternalResource resource = new ExternalResource(null);
        
        // 断言调用getFilename时抛出NullPointerException
        NullPointerException exception = assertThrows(NullPointerException.class, 
            resource::getFilename, 
            "资源路径为null时，getFilename应抛出NullPointerException");
        
        // 可选：验证异常消息（若方法实现中定义了具体消息）
        assertTrue(exception.getMessage().contains("资源路径不能为空"), "异常消息应提示路径为空");
    }

    /**
     * 测试异常路径：资源路径未设置（如默认构造函数未初始化路径），getFilename抛出IllegalStateException
     */
    @Test
    void testGetFilename_WhenResourcePathUnset_ThrowsIllegalStateException() {
        // 使用默认构造函数，模拟路径未设置的场景
        ExternalResource resource = new ExternalResource();
        
        // 断言调用getFilename时抛出IllegalStateException
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            resource::getFilename, 
            "未设置资源路径时，getFilename应抛出IllegalStateException");
        
        // 可选：验证异常消息
        assertTrue(exception.getMessage().contains("资源路径未初始化"), "异常消息应提示路径未初始化");
    }
}