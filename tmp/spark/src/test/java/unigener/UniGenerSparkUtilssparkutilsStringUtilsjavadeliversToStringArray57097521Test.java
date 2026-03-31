import spark.utils.delivers;
import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkUtilssparkutilsStringUtilsjavadeliversToStringArray57097521Test {

    private final Delivers delivers = new Delivers();

    /**
     * 测试空集合输入：应返回空字符串数组
     */
    @Test
    void toStringArray_WithEmptyCollection_ReturnsEmptyArray() {
        // 准备：空集合
        List<Object> emptyCollection = Collections.emptyList();
        
        // 执行
        String[] result = delivers.toStringArray(emptyCollection);
        
        // 断言：结果为长度0的数组
        assertArrayEquals(new String[0], result);
    }

    /**
     * 测试包含多个字符串元素的集合：应返回对应字符串数组
     */
    @Test
    void toStringArray_WithStringElements_ReturnsMatchingStringArray() {
        // 准备：包含字符串的集合
        List<String> stringCollection = Arrays.asList("apple", "banana", "cherry");
        
        // 执行
        String[] result = delivers.toStringArray(stringCollection);
        
        // 断言：数组元素与集合元素完全一致
        assertArrayEquals(new String[]{"apple", "banana", "cherry"}, result);
    }

    /**
     * 测试包含非字符串对象的集合：应返回对象toString结果的数组
     */
    @Test
    void toStringArray_WithNonStringObjects_ReturnsObjectToStringArray() {
        // 准备：包含Integer、Double等非字符串对象的集合
        List<Object> nonStringCollection = Arrays.asList(123, 45.67, new Object() {
            @Override
            public String toString() {
                return "CustomObject"; // 自定义对象的toString
            }
        });
        
        // 执行
        String[] result = delivers.toStringArray(nonStringCollection);
        
        // 断言：数组元素为对象的toString结果
        assertArrayEquals(new String[]{"123", "45.67", "CustomObject"}, result);
    }

    /**
     * 测试包含null元素的集合：应将null转换为"null"字符串
     */
    @Test
    void toStringArray_WithNullElements_ReturnsNullAsStringArray() {
        // 准备：包含null的集合
        List<Object> collectionWithNull = Arrays.asList("hello", null, "world");
        
        // 执行
        String[] result = delivers.toStringArray(collectionWithNull);
        
        // 断言：null元素被转换为"null"字符串
        assertArrayEquals(new String[]{"hello", "null", "world"}, result);
    }

    /**
     * 测试输入null集合：应抛出NullPointerException
     */
    @Test
    void toStringArray_WithNullCollection_ThrowsNullPointerException() {
        // 执行并断言：输入null时抛出NPE
        assertThrows(NullPointerException.class, 
            () -> delivers.toStringArray(null), 
            "输入null集合时应抛出NullPointerException");
    }
}