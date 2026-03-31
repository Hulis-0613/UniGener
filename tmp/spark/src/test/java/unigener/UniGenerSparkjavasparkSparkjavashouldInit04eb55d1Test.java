import spark.should;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// 假设被测试类为Initializer，依赖ResourceLoader加载初始化资源
public class UniGenerSparkjavasparkSparkjavashouldInit04eb55d1Test {
    private boolean initialized = false;
    private final ResourceLoader resourceLoader;

    public Initializer(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public void init() throws IOException {
        if (initialized) {
            throw new IllegalStateException("Already initialized");
        }
        resourceLoader.loadConfig(); // 依赖资源加载
        initialized = true;
    }

    public boolean isInitialized() {
        return initialized;
    }
}

// 依赖的资源加载接口（模拟）
interface ResourceLoader {
    void loadConfig() throws IOException;
}

@ExtendWith(MockitoExtension.class)
class InitializerTest {

    @Mock
    private ResourceLoader resourceLoader; // 模拟依赖组件

    @InjectMocks
    private Initializer initializer; // 被测试对象（注入模拟依赖）

    /**
     * 正常路径：未初始化时调用init，应成功初始化
     */
    @Test
    void init_WhenNotInitialized_ShouldInitializeSuccessfully() throws IOException {
        // 执行测试动作
        initializer.init();

        // 断言：初始化状态为true，资源加载方法被调用
        assertTrue(initializer.isInitialized());
        verify(resourceLoader, times(1)).loadConfig();
    }

    /**
     * 异常路径1：已初始化后重复调用init，应抛出IllegalStateException
     */
    @Test
    void init_WhenAlreadyInitialized_ShouldThrowIllegalStateException() throws IOException {
        // 前置条件：先正常初始化
        initializer.init();

        // 执行测试动作并断言异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, () -> initializer.init());
        assertEquals("Already initialized", exception.getMessage());
        verify(resourceLoader, times(1)).loadConfig(); // 资源加载仅调用一次
    }

    /**
     * 异常路径2：资源加载失败时，应抛出IOException
     */
    @Test
    void init_WhenResourceLoadingFails_ShouldThrowIOException() throws IOException {
        // 模拟依赖抛出异常
        doThrow(new IOException("Config file not found")).when(resourceLoader).loadConfig();

        // 执行测试动作并断言异常
        IOException exception = assertThrows(IOException.class, () -> initializer.init());
        assertEquals("Config file not found", exception.getMessage());
        assertFalse(initializer.isInitialized()); // 初始化失败，状态仍为false
    }
}