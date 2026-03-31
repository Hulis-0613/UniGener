import spark.should;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSparkjavashouldUntrustForwardHeaders32d178b5Test {

    // 正常路径：成功取消信任转发头
    @Test
    void untrustForwardHeaders_NormalCase_SetsTrustForwardHeadersToFalse() {
        // 1. 准备：初始化依赖并设置初始信任状态为true
        ServerConfig serverConfig = new ServerConfig();
        serverConfig.setTrustForwardHeaders(true);
        ForwardHeaderConfig config = new ForwardHeaderConfig(serverConfig);

        // 2. 执行：调用目标方法
        config.untrustForwardHeaders();

        // 3. 验证：确认转发头信任已取消（状态为false）
        assertFalse(serverConfig.isTrustForwardHeaders(), "转发头信任状态未被正确取消");
    }

    // 异常路径：依赖未初始化时抛出异常
    @Test
    void untrustForwardHeaders_ServerConfigNotInitialized_ThrowsIllegalStateException() {
        // 1. 准备：创建未初始化依赖的配置对象（ServerConfig为null）
        ForwardHeaderConfig config = new ForwardHeaderConfig(null);

        // 2. 执行并验证：调用方法时抛出预期异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            config::untrustForwardHeaders, 
            "未初始化ServerConfig时未抛出预期异常"
        );

        // 3. 验证异常信息
        assertEquals("ServerConfig未初始化，无法取消转发头信任", exception.getMessage(), "异常信息不匹配");
    }

    // 依赖类（模拟，实际项目中可能为框架或自定义类）
    static class ServerConfig {
        private boolean trustForwardHeaders;

        public void setTrustForwardHeaders(boolean trust) {
            this.trustForwardHeaders = trust;
        }

        public boolean isTrustForwardHeaders() {
            return trustForwardHeaders;
        }
    }

    // 目标类（包含待测试方法）
    static class ForwardHeaderConfig {
        private final ServerConfig serverConfig;

        public ForwardHeaderConfig(ServerConfig serverConfig) {
            this.serverConfig = serverConfig;
        }

        public void untrustForwardHeaders() {
            if (serverConfig == null) {
                throw new IllegalStateException("ServerConfig未初始化，无法取消转发头信任");
            }
            serverConfig.setTrustForwardHeaders(false);
        }
    }
}