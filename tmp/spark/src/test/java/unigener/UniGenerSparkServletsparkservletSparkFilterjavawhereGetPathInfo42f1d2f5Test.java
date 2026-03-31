import spark.servlet.where;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkServletsparkservletSparkFilterjavawhereGetPathInfo42f1d2f5Test {

    private PathUtils pathUtils;
    private String originalUserDir; // 保存原始系统属性，用于恢复环境

    @BeforeEach
    void setUp() {
        pathUtils = new PathUtils();
        originalUserDir = System.getProperty("user.dir"); // 假设方法依赖user.dir属性
    }

    @AfterEach
    void tearDown() {
        // 恢复原始系统属性，避免影响其他测试
        System.setProperty("user.dir", originalUserDir);
    }

    // 正常路径：成功获取有效路径
    @Test
    void getPathInfo_正常情况_返回非空路径() throws Exception {
        // 执行目标方法
        String pathInfo = pathUtils.getPathInfo();
        
        // 断言：路径非空且不为空字符串（符合"获取路径信息"的意图）
        assertNotNull(pathInfo, "路径信息不应为null");
        assertFalse(pathInfo.trim().isEmpty(), "路径信息不应为空字符串");
    }

    // 异常路径1：系统属性获取失败（如user.dir为空）
    @Test
    void getPathInfo_系统属性为空_抛出IOException() {
        // 模拟异常场景：设置user.dir为空字符串
        System.setProperty("user.dir", "");
        
        // 断言：方法抛出IOException且消息正确
        IOException exception = assertThrows(IOException.class, 
            () -> pathUtils.getPathInfo(), 
            "当路径获取失败时应抛出IOException"
        );
        assertTrue(exception.getMessage().contains("无法获取路径"), "异常消息应包含'无法获取路径'");
    }

    // 异常路径2：路径权限不足（模拟安全管理器限制）
    @Test
    void getPathInfo_权限不足_抛出SecurityException() {
        // 模拟安全管理器拒绝访问系统属性
        System.setSecurityManager(new SecurityManager() {
            @Override
            public void checkPropertyAccess(String key) {
                if ("user.dir".equals(key)) {
                    throw new SecurityException("权限不足");
                }
            }
        });

        // 断言：方法抛出SecurityException
        SecurityException exception = assertThrows(SecurityException.class, 
            () -> pathUtils.getPathInfo(), 
            "权限不足时应抛出SecurityException"
        );
        assertEquals("权限不足", exception.getMessage(), "异常消息不匹配");
        
        // 清除安全管理器（避免影响其他测试）
        System.setSecurityManager(null);
    }
}