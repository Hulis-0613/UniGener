import spark.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkServicejavaServiceRegisterMimeTypee5f4f9c8Test {

    private final Service service = new Service();

    /**
     * 正常路径：扩展名为有效非空字符串，MIME类型为有效非空字符串，验证注册成功
     */
    @Test
    void registerMimeType_ValidExtensionAndMimeType_SuccessfullyRegistered() {
        // 准备测试数据
        String extension = "pdf";
        String mimeType = "application/pdf";

        // 执行目标方法
        service.registerMimeType(extension, mimeType);

        // 断言：注册后可正确获取MIME类型
        assertEquals(mimeType, service.getMimeType(extension), "注册的MIME类型与预期不符");
    }

    /**
     * 异常路径：扩展名为null，验证抛出IllegalArgumentException
     */
    @Test
    void registerMimeType_ExtensionNull_ThrowsIllegalArgumentException() {
        // 准备测试数据
        String extension = null;
        String mimeType = "text/plain";

        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.registerMimeType(extension, mimeType),
                "扩展名为null时未抛出预期异常");

        // 断言异常信息包含预期提示
        assertTrue(exception.getMessage().contains("Extension must not be null or empty"),
                "异常信息不符合预期");
    }

    /**
     * 异常路径：扩展名为空字符串，验证抛出IllegalArgumentException
     */
    @Test
    void registerMimeType_ExtensionEmpty_ThrowsIllegalArgumentException() {
        // 准备测试数据
        String extension = "";
        String mimeType = "text/plain";

        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.registerMimeType(extension, mimeType),
                "扩展名为空字符串时未抛出预期异常");

        // 断言异常信息包含预期提示
        assertTrue(exception.getMessage().contains("Extension must not be null or empty"),
                "异常信息不符合预期");
    }

    /**
     * 异常路径：MIME类型为null，验证抛出IllegalArgumentException
     */
    @Test
    void registerMimeType_MimeTypeNull_ThrowsIllegalArgumentException() {
        // 准备测试数据
        String extension = "txt";
        String mimeType = null;

        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.registerMimeType(extension, mimeType),
                "MIME类型为null时未抛出预期异常");

        // 断言异常信息包含预期提示
        assertTrue(exception.getMessage().contains("MIME type must not be null or empty"),
                "异常信息不符合预期");
    }

    /**
     * 异常路径：MIME类型为空字符串，验证抛出IllegalArgumentException
     */
    @Test
    void registerMimeType_MimeTypeEmpty_ThrowsIllegalArgumentException() {
        // 准备测试数据
        String extension = "txt";
        String mimeType = "";

        // 执行目标方法并捕获异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> service.registerMimeType(extension, mimeType),
                "MIME类型为空字符串时未抛出预期异常");

        // 断言异常信息包含预期提示
        assertTrue(exception.getMessage().contains("MIME type must not be null or empty"),
                "异常信息不符合预期");
    }

    /**
     * 边界场景：重复注册同一扩展名，验证覆盖原MIME类型
     */
    @Test
    void registerMimeType_DuplicateExtension_OverwritesExistingMimeType() {
        // 准备测试数据
        String extension = "jpg";
        String initialMimeType = "image/jpeg";
        String updatedMimeType = "image/jpg";

        // 首次注册
        service.registerMimeType(extension, initialMimeType);
        // 重复注册（覆盖）
        service.registerMimeType(extension, updatedMimeType);

        // 断言：最终MIME类型为更新后的值
        assertEquals(updatedMimeType, service.getMimeType(extension), "重复注册未覆盖原MIME类型");
    }
}