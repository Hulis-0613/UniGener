import spark.ssl.SslStores;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkSslsparksslSslStoresjavaSslStoresCertAlias8779f155Test {

    /**
     * 测试正常路径：证书别名已正确设置时，返回预期别名
     */
    @Test
    void certAlias_returnsExpectedAliasWhenSet() {
        // 准备：创建SslStores实例并设置有效证书别名
        String expectedAlias = "test-cert-alias-123";
        SslStores sslStores = new SslStores(expectedAlias); // 假设构造函数接收别名参数

        // 执行：调用目标方法
        String actualAlias = sslStores.certAlias();

        // 断言：返回值与预期一致
        assertEquals(expectedAlias, actualAlias, "证书别名返回值不匹配");
    }

    /**
     * 测试异常路径：证书别名为null时，抛出IllegalStateException
     */
    @Test
    void certAlias_throwsIllegalStateExceptionWhenAliasIsNull() {
        // 准备：创建SslStores实例，证书别名为null
        SslStores sslStores = new SslStores(null); // 假设构造函数允许null参数

        // 执行&断言：验证抛出预期异常
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            sslStores::certAlias, 
            "未设置证书别名时应抛出IllegalStateException");
        
        // 可选：验证异常消息（若方法定义了具体消息）
        assertTrue(exception.getMessage().contains("证书别名未设置"), "异常消息不符合预期");
    }

    /**
     * 测试异常路径：证书别名为空字符串时，抛出IllegalStateException
     */
    @Test
    void certAlias_throwsIllegalStateExceptionWhenAliasIsEmpty() {
        // 准备：创建SslStores实例，证书别名为空字符串
        SslStores sslStores = new SslStores(""); // 假设构造函数允许空字符串参数

        // 执行&断言：验证抛出预期异常
        assertThrows(IllegalStateException.class, 
            sslStores::certAlias, 
            "空字符串证书别名时应抛出IllegalStateException");
    }
}