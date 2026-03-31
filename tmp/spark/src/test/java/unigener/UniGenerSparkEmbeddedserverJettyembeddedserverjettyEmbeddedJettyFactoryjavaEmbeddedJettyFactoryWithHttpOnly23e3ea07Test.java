import spark.embeddedserver.jetty.EmbeddedJettyFactory;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkEmbeddedserverJettyembeddedserverjettyEmbeddedJettyFactoryjavaEmbeddedJettyFactoryWithHttpOnly23e3ea07Test {

    /**
     * 测试当输入httpOnly为true时，属性被正确设置且返回当前实例
     */
    @Test
    void withHttpOnly_WhenHttpOnlyIsTrue_ThenSetsHttpOnlyToTrueAndReturnsSelf() {
        // Arrange：创建工厂实例
        EmbeddedJettyFactory factory = new EmbeddedJettyFactory();

        // Act：调用withHttpOnly方法传入true
        EmbeddedJettyFactory result = factory.withHttpOnly(true);

        // Assert：验证httpOnly属性为true，且返回实例为当前对象（支持链式调用）
        assertTrue(factory.isHttpOnly(), "httpOnly属性应被设置为true");
        assertSame(factory, result, "方法应返回当前EmbeddedJettyFactory实例以支持链式调用");
    }

    /**
     * 测试当输入httpOnly为false时，属性被正确设置且返回当前实例
     */
    @Test
    void withHttpOnly_WhenHttpOnlyIsFalse_ThenSetsHttpOnlyToFalseAndReturnsSelf() {
        // Arrange：创建工厂实例
        EmbeddedJettyFactory factory = new EmbeddedJettyFactory();

        // Act：调用withHttpOnly方法传入false
        EmbeddedJettyFactory result = factory.withHttpOnly(false);

        // Assert：验证httpOnly属性为false，且返回实例为当前对象（支持链式调用）
        assertFalse(factory.isHttpOnly(), "httpOnly属性应被设置为false");
        assertSame(factory, result, "方法应返回当前EmbeddedJettyFactory实例以支持链式调用");
    }
}