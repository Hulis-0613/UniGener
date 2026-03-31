import spark.Session;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSessionjavaSessionMaxInactiveInterval481edaf0Test {

    // 测试场景1：未设置自定义间隔时，返回默认最大非活动间隔
    @Test
    void maxInactiveInterval_returnsDefaultIntervalWhenNotCustomized() {
        // 假设Session类的默认最大非活动间隔为1800秒（30分钟）
        Session session = new Session();
        int expectedDefaultInterval = 1800;
        
        int actualInterval = session.maxInactiveInterval();
        
        assertEquals(expectedDefaultInterval, actualInterval, 
            "未设置自定义间隔时，应返回默认最大非活动间隔");
    }

    // 测试场景2：设置自定义间隔后，返回设置的间隔值
    @Test
    void maxInactiveInterval_returnsCustomIntervalWhenSet() {
        Session session = new Session();
        int customInterval = 3600; // 自定义1小时
        session.setMaxInactiveInterval(customInterval); // 假设存在设置间隔的方法
        
        int actualInterval = session.maxInactiveInterval();
        
        assertEquals(customInterval, actualInterval, 
            "设置自定义间隔后，应返回设置的间隔值");
    }

    // 测试场景3：会话失效后调用方法，抛出IllegalStateException
    @Test
    void maxInactiveInterval_throwsExceptionWhenSessionInvalidated() {
        Session session = new Session();
        session.invalidate(); // 假设存在使会话失效的方法
        
        IllegalStateException exception = assertThrows(IllegalStateException.class, 
            session::maxInactiveInterval, 
            "会话失效后调用maxInactiveInterval应抛出IllegalStateException");
        
        assertTrue(exception.getMessage().contains("Session is invalid"), 
            "异常信息应包含会话失效提示");
    }
}