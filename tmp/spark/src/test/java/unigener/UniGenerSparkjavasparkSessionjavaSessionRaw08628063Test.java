import spark.Session;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import javax.servlet.http.HttpSession;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class UniGenerSparkjavasparkSessionjavaSessionRaw08628063Test {

    @Mock
    private HttpSession mockHttpSession;

    // 正常路径：HttpSession存在时，返回正确的原始HttpSession对象
    @Test
    void raw_ShouldReturnHttpSession_WhenSessionExists() {
        // 准备：创建Session实例并注入已初始化的HttpSession
        Session session = new Session(mockHttpSession);
        
        // 执行：调用raw方法
        HttpSession result = session.raw();
        
        // 断言：返回的HttpSession与注入的一致
        assertEquals(mockHttpSession, result);
    }

    // 异常路径：HttpSession未初始化时，抛出IllegalStateException
    @Test
    void raw_ShouldThrowIllegalStateException_WhenSessionNotInitialized() {
        // 准备：创建Session实例但不初始化HttpSession（假设构造函数允许null或未设置）
        Session session = new Session(null);
        
        // 执行&断言：调用raw方法时抛出IllegalStateException
        assertThrows(IllegalStateException.class, session::raw, 
            "未初始化HttpSession时，调用raw方法应抛出IllegalStateException");
    }
}