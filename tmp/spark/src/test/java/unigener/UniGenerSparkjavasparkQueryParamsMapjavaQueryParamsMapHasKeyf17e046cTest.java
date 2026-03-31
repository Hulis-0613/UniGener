import spark.QueryParamsMap;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkQueryParamsMapjavaQueryParamsMapHasKeyf17e046cTest {

    private StringKeyStore keyStore;

    // 每个测试前初始化空的keyStore
    @BeforeEach
    void setUp() {
        keyStore = new StringKeyStore();
    }

    // 正常路径：key存在（普通字符串）
    @Test
    void hasKey_ExistingKey_ReturnsTrue() {
        String testKey = "validKey123";
        keyStore.addKey(testKey); // 预先添加key
        boolean result = keyStore.hasKey(testKey);
        assertTrue(result, "Existing key should return true");
    }

    // 正常路径：key不存在（普通字符串）
    @Test
    void hasKey_NonExistingKey_ReturnsFalse() {
        String testKey = "nonExistingKey456";
        boolean result = keyStore.hasKey(testKey); // 未添加key
        assertFalse(result, "Non-existing key should return false");
    }

    // 正常路径：空字符串key存在
    @Test
    void hasKey_EmptyStringKey_Existing_ReturnsTrue() {
        String emptyKey = "";
        keyStore.addKey(emptyKey); // 添加空字符串key
        boolean result = keyStore.hasKey(emptyKey);
        assertTrue(result, "Existing empty string key should return true");
    }

    // 正常路径：空字符串key不存在
    @Test
    void hasKey_EmptyStringKey_NonExisting_ReturnsFalse() {
        String emptyKey = "";
        boolean result = keyStore.hasKey(emptyKey); // 未添加空字符串key
        assertFalse(result, "Non-existing empty string key should return false");
    }

    // 异常路径：key为null（抛出NullPointerException）
    @Test
    void hasKey_NullKey_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, 
            () -> keyStore.hasKey(null), 
            "Null key should throw NullPointerException"
        );
    }
}