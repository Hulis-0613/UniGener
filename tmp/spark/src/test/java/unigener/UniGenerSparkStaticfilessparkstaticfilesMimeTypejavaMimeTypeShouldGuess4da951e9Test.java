import spark.staticfiles.MimeType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkStaticfilessparkstaticfilesMimeTypejavaMimeTypeShouldGuess4da951e9Test {

    /**
     * 测试场景：当启用猜测且无明确MIME类型时，应返回true（正常路径：应该猜测）
     */
    @Test
    void shouldGuess_WhenGuessEnabledAndNoExplicitType_ThenReturnTrue() {
        // 构造MimeType实例：未设置明确类型，且启用猜测
        MimeType mimeType = new MimeType(null, true);
        
        boolean result = mimeType.shouldGuess();
        
        assertTrue(result, "当启用猜测且无明确类型时，shouldGuess应返回true");
    }

    /**
     * 测试场景：当启用猜测但已有明确MIME类型时，应返回false（正常路径：无需猜测）
     */
    @Test
    void shouldGuess_WhenGuessEnabledAndHasExplicitType_ThenReturnFalse() {
        // 构造MimeType实例：已设置明确类型（如"text/plain"），且启用猜测
        MimeType mimeType = new MimeType("text/plain", true);
        
        boolean result = mimeType.shouldGuess();
        
        assertFalse(result, "当启用猜测但已有明确类型时，shouldGuess应返回false");
    }

    /**
     * 测试场景：当禁用猜测且无明确MIME类型时，应返回false（正常路径：不允许猜测）
     */
    @Test
    void shouldGuess_WhenGuessDisabledAndNoExplicitType_ThenReturnFalse() {
        // 构造MimeType实例：未设置明确类型，但禁用猜测
        MimeType mimeType = new MimeType(null, false);
        
        boolean result = mimeType.shouldGuess();
        
        assertFalse(result, "当禁用猜测且无明确类型时，shouldGuess应返回false");
    }

    /**
     * 测试场景：当禁用猜测且已有明确MIME类型时，应返回false（正常路径：无需且不允许猜测）
     */
    @Test
    void shouldGuess_WhenGuessDisabledAndHasExplicitType_ThenReturnFalse() {
        // 构造MimeType实例：已设置明确类型（如"application/json"），且禁用猜测
        MimeType mimeType = new MimeType("application/json", false);
        
        boolean result = mimeType.shouldGuess();
        
        assertFalse(result, "当禁用猜测且已有明确类型时，shouldGuess应返回false");
    }

    /**
     * 测试场景：当明确类型为空字符串（边界情况）且启用猜测时，应返回true
     */
    @Test
    void shouldGuess_WhenExplicitTypeIsEmptyAndGuessEnabled_ThenReturnTrue() {
        // 构造MimeType实例：明确类型为空字符串（视为"无明确类型"），且启用猜测
        MimeType mimeType = new MimeType("", true);
        
        boolean result = mimeType.shouldGuess();
        
        assertTrue(result, "当明确类型为空字符串且启用猜测时，shouldGuess应返回true");
    }
}