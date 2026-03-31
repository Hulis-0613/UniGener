import spark.ssl.SslStores;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkSslsparksslSslStoresjavaSslStoresKeystorePassword4facea69Test {

    // 假设方法从系统属性"ssl.keystore.password"获取密码（根据常见实现推测）
    private static final String KEYSTORE_PASSWORD_PROPERTY = "ssl.keystore.password";
    private final SslStores sslStores = new SslStores();

    // 测试后清理系统属性，避免影响其他测试
    @AfterEach
    void tearDown() {
        System.clearProperty(KEYSTORE_PASSWORD_PROPERTY);
    }

    /**
     * 正常路径：配置存在且有有效值时，返回正确密码
     */
    @Test
    void keystorePassword_WithValidConfig_ReturnsPassword() {
        // 准备：设置有效的系统属性
        String expectedPassword = "testKeystore123!";
        System.setProperty(KEYSTORE_PASSWORD_PROPERTY, expectedPassword);

        // 执行
        String actualPassword = sslStores.keystorePassword();

        // 断言：返回值与预期一致
        assertEquals(expectedPassword, actualPassword, "密码应匹配配置值");
    }

    /**
     * 异常路径1：配置不存在时，返回null（常见实现逻辑）
     */
    @Test
    void keystorePassword_WithoutConfig_ReturnsNull() {
        // 准备：确保系统属性未设置（tearDown已清理）

        // 执行
        String actualPassword = sslStores.keystorePassword();

        // 断言：返回null
        assertNull(actualPassword, "配置不存在时应返回null");
    }

    /**
     * 异常路径2：配置存在但值为空字符串时，返回空字符串
     */
    @Test
    void keystorePassword_WithEmptyConfig_ReturnsEmptyString() {
        // 准备：设置空字符串配置
        System.setProperty(KEYSTORE_PASSWORD_PROPERTY, "");

        // 执行
        String actualPassword = sslStores.keystorePassword();

        // 断言：返回空字符串
        assertEquals("", actualPassword, "空配置应返回空字符串");
    }
}