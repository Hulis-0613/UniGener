import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.http.HttpMethod; // 假设目标方法返回Spring的HttpMethod枚举

public class UniGenerSparkRoutesparkrouteHttpMethodjavaGetadd7b854Test {

    // 正常路径：测试所有有效的HTTP方法字符串
    @Test
    void get_WithGetMethodStr_ReturnsGet() {
        HttpMethod result = HttpMethodUtils.get("GET");
        assertEquals(HttpMethod.GET, result);
    }

    @Test
    void get_WithPostMethodStr_ReturnsPost() {
        HttpMethod result = HttpMethodUtils.get("POST");
        assertEquals(HttpMethod.POST, result);
    }

    @Test
    void get_WithPutMethodStr_ReturnsPut() {
        HttpMethod result = HttpMethodUtils.get("PUT");
        assertEquals(HttpMethod.PUT, result);
    }

    @Test
    void get_WithDeleteMethodStr_ReturnsDelete() {
        HttpMethod result = HttpMethodUtils.get("DELETE");
        assertEquals(HttpMethod.DELETE, result);
    }

    @Test
    void get_WithPatchMethodStr_ReturnsPatch() {
        HttpMethod result = HttpMethodUtils.get("PATCH");
        assertEquals(HttpMethod.PATCH, result);
    }

    @Test
    void get_WithHeadMethodStr_ReturnsHead() {
        HttpMethod result = HttpMethodUtils.get("HEAD");
        assertEquals(HttpMethod.HEAD, result);
    }

    @Test
    void get_WithOptionsMethodStr_ReturnsOptions() {
        HttpMethod result = HttpMethodUtils.get("OPTIONS");
        assertEquals(HttpMethod.OPTIONS, result);
    }

    @Test
    void get_WithTraceMethodStr_ReturnsTrace() {
        HttpMethod result = HttpMethodUtils.get("TRACE");
        assertEquals(HttpMethod.TRACE, result);
    }

    // 异常路径：测试无效输入
    @Test
    void get_WithNullMethodStr_ThrowsNullPointerException() {
        assertThrows(NullPointerException.class, 
            () -> HttpMethodUtils.get(null), 
            "输入为null时应抛出NullPointerException");
    }

    @Test
    void get_WithEmptyMethodStr_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, 
            () -> HttpMethodUtils.get(""), 
            "输入为空字符串时应抛出IllegalArgumentException");
    }

    @Test
    void get_WithInvalidMethodStr_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, 
            () -> HttpMethodUtils.get("INVALID_METHOD"), 
            "输入无效方法名时应抛出IllegalArgumentException");
    }

    @Test
    void get_WithLowercaseMethodStr_ThrowsIllegalArgumentException() {
        assertThrows(IllegalArgumentException.class, 
            () -> HttpMethodUtils.get("get"), 
            "输入小写方法名时应抛出IllegalArgumentException（区分大小写）");
    }
}