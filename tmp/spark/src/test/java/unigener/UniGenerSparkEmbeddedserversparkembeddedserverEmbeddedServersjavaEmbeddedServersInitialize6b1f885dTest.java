import spark.embeddedserver.EmbeddedServers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;

public class UniGenerSparkEmbeddedserversparkembeddedserverEmbeddedServersjavaEmbeddedServersInitialize6b1f885dTest {

    private EmbeddedServers embeddedServers;

    @BeforeEach
    void setUp() {
        // 每次测试前创建新实例，避免状态污染
        embeddedServers = new EmbeddedServers();
    }

    /**
     * 测试正常路径：initialize成功执行，状态正确初始化
     */
    @Test
    void initialize_Success() {
        // 执行初始化
        assertDoesNotThrow(() -> embeddedServers.initialize(), "初始化应成功执行");
        
        // 验证初始化状态（假设类中有isInitialized()方法标记初始化完成）
        assertTrue(embeddedServers.isInitialized(), "初始化后状态应为已初始化");
        
        // 验证核心资源已加载（假设类中有getServer()方法返回服务器实例）
        assertNotNull(embeddedServers.getServer(), "服务器实例不应为null");
    }

    /**
     * 测试异常路径：重复调用initialize应抛出IllegalStateException
     */
    @Test
    void initialize_WhenAlreadyInitialized_ThrowsIllegalStateException() {
        // 首次初始化成功
        embeddedServers.initialize();
        
        // 重复初始化应抛出异常
        IllegalStateException exception = assertThrows(IllegalStateException.class,
                () -> embeddedServers.initialize(), "重复初始化应抛出IllegalStateException");
        
        assertEquals("Embedded servers already initialized", exception.getMessage(), 
                "异常消息应提示已初始化");
    }

    /**
     * 测试异常路径：核心资源缺失时初始化应抛出InitializationException
     */
    @Test
    void initialize_WhenCoreResourceMissing_ThrowsInitializationException() throws Exception {
        // 通过反射模拟核心资源缺失（假设类中有"coreResource"字段存储关键依赖）
        Field coreResourceField = EmbeddedServers.class.getDeclaredField("coreResource");
        coreResourceField.setAccessible(true);
        coreResourceField.set(embeddedServers, null); // 将核心资源置为null模拟缺失
        
        // 执行初始化，预期抛出自定义初始化异常
        InitializationException exception = assertThrows(InitializationException.class,
                () -> embeddedServers.initialize(), "资源缺失时应抛出InitializationException");
        
        assertTrue(exception.getMessage().contains("Core resource not found"), 
                "异常消息应包含资源缺失提示");
    }

    /**
     * 测试异常路径：端口占用时初始化应抛出BindException
     */
    @Test
    void initialize_WhenPortInUse_ThrowsBindException() throws Exception {
        // 模拟端口已被占用（假设类中有"serverPort"字段控制端口）
        Field portField = EmbeddedServers.class.getDeclaredField("serverPort");
        portField.setAccessible(true);
        portField.setInt(embeddedServers, 8080); // 设置一个已知被占用的端口（需提前确保端口占用）
        
        // 执行初始化，预期抛出端口绑定异常
        BindException exception = assertThrows(BindException.class,
                () -> embeddedServers.initialize(), "端口占用时应抛出BindException");
        
        assertTrue(exception.getMessage().contains("Address already in use"), 
                "异常消息应包含端口占用提示");
    }
}