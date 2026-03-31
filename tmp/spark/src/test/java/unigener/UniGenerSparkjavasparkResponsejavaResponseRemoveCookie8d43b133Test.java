import spark.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

// 假设Cookie类定义（实际项目中需替换为真实Cookie类）
public class UniGenerSparkjavasparkResponsejavaResponseRemoveCookie8d43b133Test {
    private final String name;
    private final String value;

    public Cookie(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}

// 目标测试类
class Response {
    private final java.util.List<Cookie> cookies = new java.util.ArrayList<>();

    public void addCookie(Cookie cookie) {
        if (cookie == null) throw new IllegalArgumentException("Cookie cannot be null");
        cookies.add(cookie);
    }

    public Cookie getCookie(String name) {
        if (name == null) return null;
        return cookies.stream()
                .filter(c -> name.equals(c.getName()))
                .findFirst()
                .orElse(null);
    }

    public java.util.List<Cookie> getCookies() {
        return new java.util.ArrayList<>(cookies); // 返回副本避免外部修改
    }

    // 待测试方法
    public void removeCookie(String name) {
        if (name == null) throw new IllegalArgumentException("Cookie name cannot be null");
        cookies.removeIf(c -> name.equals(c.getName()));
    }
}

// JUnit5测试类
class ResponseRemoveCookieTest {
    private Response response;

    @BeforeEach
    void setUp() {
        response = new Response();
    }

    /**
     * 正常路径：移除存在的Cookie
     */
    @Test
    void removeCookie_ExistingCookie_ShouldRemoveSuccessfully() {
        // 准备：添加目标Cookie
        Cookie targetCookie = new Cookie("SESSION_ID", "123456");
        response.addCookie(targetCookie);
        Cookie otherCookie = new Cookie("USER_TOKEN", "abcdef");
        response.addCookie(otherCookie);
        int initialSize = response.getCookies().size();

        // 执行：移除目标Cookie
        response.removeCookie("SESSION_ID");

        // 断言：目标Cookie被移除，其他Cookie保留，集合大小正确
        assertNull(response.getCookie("SESSION_ID"), "目标Cookie未被移除");
        assertNotNull(response.getCookie("USER_TOKEN"), "其他Cookie不应被影响");
        assertEquals(initialSize - 1, response.getCookies().size(), "Cookie集合大小未正确减少");
    }

    /**
     * 正常路径：移除不存在的Cookie（无操作）
     */
    @Test
    void removeCookie_NonExistingCookie_ShouldDoNothing() {
        // 准备：添加其他Cookie
        Cookie existingCookie = new Cookie("LANG", "zh-CN");
        response.addCookie(existingCookie);
        int initialSize = response.getCookies().size();

        // 执行：移除不存在的Cookie
        response.removeCookie("NON_EXISTENT");

        // 断言：集合无变化
        assertEquals(initialSize, response.getCookies().size(), "Cookie集合大小不应改变");
        assertNotNull(response.getCookie("LANG"), "现有Cookie不应被影响");
    }

    /**
     * 异常路径：入参name为null（抛出IllegalArgumentException）
     */
    @Test
    void removeCookie_NullName_ShouldThrowIllegalArgumentException() {
        // 执行并断言异常
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> response.removeCookie(null),
                "移除null名称的Cookie应抛出IllegalArgumentException");
        
        assertTrue(exception.getMessage().contains("Cookie name cannot be null"), 
                "异常消息应包含参数校验信息");
    }
}