import spark.http.matching.Body;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkHttpMatchinghttpmatchingBodyjavaBodyNotSete0701d79Test {

    /**
     * 测试所有属性均未设置时，notSet()返回true
     */
    @Test
    void notSet_AllPropertiesUnset_ReturnsTrue() {
        // 构造一个未设置任何属性的Body对象（假设默认构造函数或初始状态下所有属性为"未设置"状态，如null/默认值）
        Body body = new Body();
        
        assertTrue(body.notSet(), "当所有属性未设置时，notSet()应返回true");
    }

    /**
     * 测试仅id属性设置时，notSet()返回false
     */
    @Test
    void notSet_IdSet_ReturnsFalse() {
        Body body = new Body();
        body.setId(123); // 假设Body有setId(int)方法
        
        assertFalse(body.notSet(), "当id属性已设置时，notSet()应返回false");
    }

    /**
     * 测试仅name属性设置时，notSet()返回false
     */
    @Test
    void notSet_NameSet_ReturnsFalse() {
        Body body = new Body();
        body.setName("test-name"); // 假设Body有setName(String)方法
        
        assertFalse(body.notSet(), "当name属性已设置时，notSet()应返回false");
    }

    /**
     * 测试仅value属性设置时，notSet()返回false
     */
    @Test
    void notSet_ValueSet_ReturnsFalse() {
        Body body = new Body();
        body.setValue(45.6); // 假设Body有setValue(double)方法
        
        assertFalse(body.notSet(), "当value属性已设置时，notSet()应返回false");
    }

    /**
     * 测试多个属性同时设置时，notSet()返回false
     */
    @Test
    void notSet_MultiplePropertiesSet_ReturnsFalse() {
        Body body = new Body();
        body.setId(456);
        body.setName("multi-prop");
        body.setValue(78.9);
        
        assertFalse(body.notSet(), "当多个属性已设置时，notSet()应返回false");
    }
}