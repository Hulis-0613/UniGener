import spark.is;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkCustomErrorPagesjavaisGetDefaultFor94010955Test {

    // 实例化目标类（假设为实例方法，若为静态方法可直接调用类名）
    private final StatusDefaultProvider provider = new StatusDefaultProvider();

    // -------------------------- 正常路径测试 --------------------------
    @Test
    void getDefaultFor_WithStatus1_ReturnsDefaultValue1() {
        // 假设status=1对应默认值"DEFAULT_1"
        String result = provider.getDefaultFor(1);
        assertEquals("DEFAULT_1", result, "status=1时应返回默认值DEFAULT_1");
    }

    @Test
    void getDefaultFor_WithStatus2_ReturnsDefaultValue2() {
        // 假设status=2对应默认值"DEFAULT_2"
        String result = provider.getDefaultFor(2);
        assertEquals("DEFAULT_2", result, "status=2时应返回默认值DEFAULT_2");
    }

    @Test
    void getDefaultFor_WithStatus3_ReturnsDefaultValue3() {
        // 假设status=3对应默认值"DEFAULT_3"（若存在更多有效status，需补充对应测试）
        String result = provider.getDefaultFor(3);
        assertEquals("DEFAULT_3", result, "status=3时应返回默认值DEFAULT_3");
    }

    // -------------------------- 异常路径测试 --------------------------
    @Test
    void getDefaultFor_WithNegativeStatus_ThrowsIllegalArgumentException() {
        // 测试负数值（无效status）
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> provider.getDefaultFor(-1),
            "负status值应抛出IllegalArgumentException"
        );
        assertTrue(exception.getMessage().contains("无效的status"), "异常消息应提示无效status");
    }

    @Test
    void getDefaultFor_WithZeroStatus_ThrowsIllegalArgumentException() {
        // 测试0（假设0为无效status）
        assertThrows(
            IllegalArgumentException.class,
            () -> provider.getDefaultFor(0),
            "status=0时应抛出IllegalArgumentException"
        );
    }

    @Test
    void getDefaultFor_WithExceedMaxStatus_ThrowsIllegalArgumentException() {
        // 测试超过最大有效status的值（假设最大有效status为3）
        assertThrows(
            IllegalArgumentException.class,
            () -> provider.getDefaultFor(4),
            "超过最大有效status的值应抛出IllegalArgumentException"
        );
    }
}