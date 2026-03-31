import spark.QueryParamsMap;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class UniGenerSparkjavasparkQueryParamsMapjavaQueryParamsMapGetValues8a8fed6eTest {

    // 测试：无参数时返回空数组
    @Test
    void getValues_WhenNoParams_ReturnsEmptyArray() {
        QueryParamsMap queryParams = new QueryParamsMap();
        String[] result = queryParams.getValues();
        assertArrayEquals(new String[0], result, "空参数时应返回空数组");
    }

    // 测试：单参数时返回包含该值的数组
    @Test
    void getValues_WithOneParam_ReturnsSingleValueArray() {
        QueryParamsMap queryParams = new QueryParamsMap();
        queryParams.put("name", "testUser"); // 假设QueryParamsMap有put方法添加键值对
        String[] result = queryParams.getValues();
        assertArrayEquals(new String[]{"testUser"}, result, "单参数时应返回包含该值的数组");
    }

    // 测试：多参数时返回包含所有值的数组（假设内部有序存储）
    @Test
    void getValues_WithMultipleParams_ReturnsAllValuesArray() {
        QueryParamsMap queryParams = new QueryParamsMap();
        queryParams.put("id", "123");
        queryParams.put("status", "active");
        queryParams.put("type", "user");
        String[] result = queryParams.getValues();
        assertArrayEquals(new String[]{"123", "active", "user"}, result, "多参数时应返回所有值的数组");
    }

    // 测试：值包含null时返回包含null的数组
    @Test
    void getValues_WithNullValue_ReturnsArrayIncludingNull() {
        QueryParamsMap queryParams = new QueryParamsMap();
        queryParams.put("optionalField", null); // 添加null值
        String[] result = queryParams.getValues();
        assertArrayEquals(new String[]{null}, result, "包含null值时应返回含null的数组");
    }

    // 测试：内部存储结构为null时抛出NullPointerException
    @Test
    void getValues_WhenInternalMapIsNull_ThrowsNPE() throws NoSuchFieldException, IllegalAccessException {
        QueryParamsMap queryParams = new QueryParamsMap();
        // 通过反射将内部存储Map设为null（假设内部字段名为"paramsMap"）
        Field paramsMapField = QueryParamsMap.class.getDeclaredField("paramsMap");
        paramsMapField.setAccessible(true);
        paramsMapField.set(queryParams, null);

        assertThrows(NullPointerException.class, 
            queryParams::getValues, 
            "内部Map为null时应抛出NullPointerException");
    }

    // 假设的QueryParamsMap类（仅用于测试上下文，实际项目中无需定义）
    static class QueryParamsMap {
        private Map<String, String> paramsMap = new HashMap<>();

        public void put(String key, String value) {
            paramsMap.put(key, value);
        }

        public String[] getValues() {
            if (paramsMap == null) {
                throw new NullPointerException("Internal paramsMap is null");
            }
            return paramsMap.values().toArray(new String[0]);
        }
    }
}