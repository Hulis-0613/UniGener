import spark.staticfiles.DirectoryTraversal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkStaticfilessparkstaticfilesDirectoryTraversaljavaDirectoryTraversalDirectoryTraversalDetectionaeb009a7Test {

    private final DirectoryTraversalDetection detector = new DirectoryTraversalDetection();

    /**
     * 测试正常路径：输入合法路径时不抛出异常
     */
    @Test
    void detect_WithValidPath_ShouldNotThrowException() {
        // 正常路径示例：不包含"classpath"的合法路径
        String validPath = "/usr/local/app/config";
        
        // 断言执行detect方法时无异常抛出
        assertDoesNotThrow(() -> detector.detect(validPath), 
            "合法路径不应触发目录遍历检测异常");
    }

    /**
     * 测试异常路径：输入"classpath"时抛出指定异常
     */
    @Test
    void detect_WithClasspathPath_ShouldThrowDirectoryTraversalDetectionException() {
        // 异常路径：触发检测的"classpath"字符串
        String maliciousPath = "classpath";
        
        // 断言执行detect方法时抛出目标异常，并验证异常参数
        DirectoryTraversalDetectionException exception = assertThrows(
            DirectoryTraversalDetectionException.class,
            () -> detector.detect(maliciousPath),
            "输入'classpath'时应抛出DirectoryTraversalDetection异常"
        );
        
        // 验证异常携带的参数是否为"classpath"（假设异常通过构造函数传入参数并可通过getParam()获取）
        assertEquals("classpath", exception.getParam(), 
            "异常参数应与输入的'classpath'一致");
    }
}