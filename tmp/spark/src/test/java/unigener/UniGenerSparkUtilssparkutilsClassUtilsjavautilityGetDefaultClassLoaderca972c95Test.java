import spark.utils.utility;
import org.junit.jupiter.api.Test;
import java.security.Permission;
import static org.junit.jupiter.api.Assertions.*;

// 假设目标方法所在类为ClassLoaderUtils（根据实际类名调整）
public class UniGenerSparkUtilssparkutilsClassUtilsjavautilityGetDefaultClassLoaderca972c95Test {

    /**
     * 测试正常路径：成功获取默认类加载器，返回非null实例
     */
    @Test
    void testGetDefaultClassLoader_NormalCase() {
        // 执行目标方法
        ClassLoader defaultClassLoader = ClassLoaderUtils.getDefaultClassLoader();
        
        // 断言：默认类加载器不应为null
        assertNotNull(defaultClassLoader, "默认类加载器必须非null");
    }

    /**
     * 测试异常路径：当安全管理器限制类加载器访问时，抛出SecurityException
     */
    @Test
    void testGetDefaultClassLoader_SecurityRestriction() {
        // 保存原始安全管理器，用于测试后恢复
        SecurityManager originalSecurityManager = System.getSecurityManager();
        
        try {
            // 设置自定义安全管理器，拒绝类加载器访问权限
            System.setSecurityManager(new SecurityManager() {
                @Override
                public void checkPermission(Permission perm) {
                    // 拦截类加载器相关权限请求（根据实际实现调整权限名称）
                    if (perm.getName().contains("getClassLoader") || perm.getName().contains("setContextClassLoader")) {
                        throw new SecurityException("拒绝访问类加载器");
                    }
                }
            });

            // 执行目标方法，断言抛出SecurityException
            SecurityException exception = assertThrows(
                SecurityException.class,
                ClassLoaderUtils::getDefaultClassLoader,
                "当安全管理器限制时，应抛出SecurityException"
            );
            
            // 验证异常信息（可选，根据实际需求调整）
            assertTrue(exception.getMessage().contains("拒绝访问类加载器"));
            
        } finally {
            // 恢复原始安全管理器，避免影响其他测试
            System.setSecurityManager(originalSecurityManager);
        }
    }
}