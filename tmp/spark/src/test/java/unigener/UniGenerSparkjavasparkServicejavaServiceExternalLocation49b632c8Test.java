import spark.Service;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class UniGenerSparkjavasparkServicejavaServiceExternalLocation49b632c8Test {

    private final ExternalLocationProcessor processor = new ExternalLocationProcessor();

    /**
     * 正常路径测试：外部目录存在且可访问，返回正确路径
     */
    @Test
    void testExternalLocation_NormalCase(@TempDir File tempDir) throws IOException {
        // 执行目标方法
        String result = processor.externalLocation(tempDir);
        
        // 断言返回路径为临时目录的绝对路径（假设方法返回输入目录的绝对路径）
        Assertions.assertEquals(tempDir.getAbsolutePath(), result);
        // 断言目录确实存在
        Assertions.assertTrue(tempDir.exists());
        Assertions.assertTrue(tempDir.isDirectory());
    }

    /**
     * 异常路径测试：外部目录为null，预期抛出IllegalArgumentException
     */
    @Test
    void testExternalLocation_NullExternalFolder() {
        // 执行目标方法并捕获异常
        IllegalArgumentException exception = Assertions.assertThrows(
            IllegalArgumentException.class,
            () -> processor.externalLocation(null),
            "当externalFolder为null时，应抛出IllegalArgumentException"
        );
        
        // 断言异常消息（假设方法会提示"外部目录不能为null"）
        Assertions.assertTrue(exception.getMessage().contains("外部目录不能为null"));
    }

    /**
     * 异常路径测试：外部目录不存在，预期抛出IOException
     */
    @Test
    void testExternalLocation_ExternalFolderNotExists() {
        // 创建不存在的目录路径
        File nonExistentFolder = new File("non_existent_external_folder_" + System.currentTimeMillis());
        
        // 执行目标方法并捕获异常
        IOException exception = Assertions.assertThrows(
            IOException.class,
            () -> processor.externalLocation(nonExistentFolder),
            "当外部目录不存在时，应抛出IOException"
        );
        
        // 断言异常消息（假设方法会提示"外部目录不存在"）
        Assertions.assertTrue(exception.getMessage().contains("外部目录不存在"));
    }

    /**
     * 异常路径测试：外部目录是文件（非目录），预期抛出IOException
     */
    @Test
    void testExternalLocation_ExternalFolderIsFile(@TempDir File tempDir) throws IOException {
        // 在临时目录下创建一个文件（而非目录）
        File fileAsFolder = new File(tempDir, "test_file.txt");
        Files.createFile(fileAsFolder.toPath()); // 确保是文件
        
        // 执行目标方法并捕获异常
        IOException exception = Assertions.assertThrows(
            IOException.class,
            () -> processor.externalLocation(fileAsFolder),
            "当外部路径是文件时，应抛出IOException"
        );
        
        // 断言异常消息（假设方法会提示"外部路径不是目录"）
        Assertions.assertTrue(exception.getMessage().contains("外部路径不是目录"));
    }

    /**
     * 异常路径测试：外部目录无访问权限，预期抛出IOException
     */
    @Test
    void testExternalLocation_ExternalFolderNoPermission(@TempDir File tempDir) throws IOException {
        // 创建受限目录（仅在支持权限设置的系统生效，如Linux/macOS）
        File restrictedFolder = new File(tempDir, "restricted_folder");
        restrictedFolder.mkdir();
        // 设置目录不可读（模拟无权限场景）
        boolean permissionSet = restrictedFolder.setReadable(false);
        
        if (permissionSet) { // 仅在权限设置成功时执行测试
            IOException exception = Assertions.assertThrows(
                IOException.class,
                () -> processor.externalLocation(restrictedFolder),
                "当外部目录无访问权限时，应抛出IOException"
            );
            Assertions.assertTrue(exception.getMessage().contains("无访问权限"));
        }
    }
}