import spark.TemplateViewRouteImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

// 假设相关依赖类的包路径
import com.example.Route;
import com.example.TemplateEngine;
import com.example.TemplateViewRouteImpl;

@MockitoSettings(strictness = Strictness.LENIENT)
public class UniGenerSparkjavasparkTemplateViewRouteImpljavaTemplateViewRouteImplCreatee4059eb8Test {

    @Mock
    private Route mockRoute;  // 模拟Route依赖
    @Mock
    private TemplateEngine mockEngine;  // 模拟TemplateEngine依赖
    private static final String VALID_PATH = "/test/template";  // 有效路径参数

    /**
     * 正常路径测试：所有参数有效时成功创建实例
     */
    @Test
    void create_WithValidParameters_ReturnsTemplateViewRouteImpl() {
        // 执行目标方法
        TemplateViewRouteImpl result = TemplateViewRouteImpl.create(VALID_PATH, mockRoute, mockEngine);

        // 断言：实例非空且属性正确赋值
        Assertions.assertNotNull(result, "创建的TemplateViewRouteImpl实例不应为null");
        Assertions.assertEquals(VALID_PATH, result.getPath(), "路径参数未正确设置");
        Assertions.assertSame(mockRoute, result.getRoute(), "Route实例未正确引用");
        Assertions.assertSame(mockEngine, result.getEngine(), "TemplateEngine实例未正确引用");
    }

    /**
     * 异常路径测试：path为null时抛出IllegalArgumentException
     */
    @Test
    void create_WithNullPath_ThrowsIllegalArgumentException() {
        // 执行目标方法并捕获异常
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> TemplateViewRouteImpl.create(null, mockRoute, mockEngine),
            "当path为null时应抛出IllegalArgumentException"
        );

        // 断言异常消息（假设方法会提示参数名称，可根据实际实现调整）
        Assertions.assertTrue(
            exception.getMessage().contains("path"),
            "异常消息应包含参数名'path'"
        );
    }

    /**
     * 异常路径测试：route为null时抛出IllegalArgumentException
     */
    @Test
    void create_WithNullRoute_ThrowsIllegalArgumentException() {
        // 执行目标方法并捕获异常
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> TemplateViewRouteImpl.create(VALID_PATH, null, mockEngine),
            "当route为null时应抛出IllegalArgumentException"
        );

        // 断言异常消息
        Assertions.assertTrue(
            exception.getMessage().contains("route"),
            "异常消息应包含参数名'route'"
        );
    }

    /**
     * 异常路径测试：engine为null时抛出IllegalArgumentException
     */
    @Test
    void create_WithNullEngine_ThrowsIllegalArgumentException() {
        // 执行目标方法并捕获异常
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> TemplateViewRouteImpl.create(VALID_PATH, mockRoute, null),
            "当engine为null时应抛出IllegalArgumentException"
        );

        // 断言异常消息
        Assertions.assertTrue(
            exception.getMessage().contains("engine"),
            "异常消息应包含参数名'engine'"
        );
    }
}