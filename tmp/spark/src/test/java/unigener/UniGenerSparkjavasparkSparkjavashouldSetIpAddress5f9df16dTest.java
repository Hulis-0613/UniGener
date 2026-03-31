import spark.should;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkSparkjavashouldSetIpAddress5f9df16dTest {

    private NetworkConfig networkConfig;

    @BeforeEach
    void setUp() {
        networkConfig = new NetworkConfig(); // 初始化测试对象，避免状态干扰
    }

    // 正常路径：输入合法IPv4地址，验证设置成功
    @Test
    void setIpAddress_ValidIpv4_Success() {
        // 准备：合法IPv4地址
        String validIp = "192.168.1.1";
        
        // 执行：调用setIpAddress
        networkConfig.setIpAddress(validIp);
        
        // 断言：IP地址设置正确
        assertEquals(validIp, networkConfig.getIpAddress());
    }

    // 异常路径：输入null，验证抛出NullPointerException
    @Test
    void setIpAddress_NullIp_ThrowsNullPointerException() {
        // 执行&断言：调用setIpAddress(null)时抛出NullPointerException
        assertThrows(NullPointerException.class, 
            () -> networkConfig.setIpAddress(null), 
            "IP地址为null时应抛出NullPointerException");
    }

    // 异常路径：输入空字符串，验证抛出IllegalArgumentException
    @Test
    void setIpAddress_EmptyIp_ThrowsIllegalArgumentException() {
        // 执行&断言：调用setIpAddress("")时抛出IllegalArgumentException
        assertThrows(IllegalArgumentException.class, 
            () -> networkConfig.setIpAddress(""), 
            "IP地址为空字符串时应抛出IllegalArgumentException");
    }

    // 异常路径：输入少于4段的IP（如3段），验证抛出IllegalArgumentException
    @Test
    void setIpAddress_IpWithLessThan4Segments_ThrowsIllegalArgumentException() {
        String invalidIp = "192.168.1"; // 仅3段
        
        assertThrows(IllegalArgumentException.class, 
            () -> networkConfig.setIpAddress(invalidIp), 
            "IP地址段数不足4时应抛出IllegalArgumentException");
    }

    // 异常路径：输入多于4段的IP（如5段），验证抛出IllegalArgumentException
    @Test
    void setIpAddress_IpWithMoreThan4Segments_ThrowsIllegalArgumentException() {
        String invalidIp = "192.168.1.1.1"; // 5段
        
        assertThrows(IllegalArgumentException.class, 
            () -> networkConfig.setIpAddress(invalidIp), 
            "IP地址段数超过4时应抛出IllegalArgumentException");
    }

    // 异常路径：输入段值大于255的IP，验证抛出IllegalArgumentException
    @Test
    void setIpAddress_IpSegmentExceeds255_ThrowsIllegalArgumentException() {
        String invalidIp = "256.0.0.1"; // 第一段256>255
        
        assertThrows(IllegalArgumentException.class, 
            () -> networkConfig.setIpAddress(invalidIp), 
            "IP段值超过255时应抛出IllegalArgumentException");
    }

    // 异常路径：输入含非数字字符的IP段，验证抛出IllegalArgumentException
    @Test
    void setIpAddress_IpWithNonNumericSegment_ThrowsIllegalArgumentException() {
        String invalidIp = "192.168.a.1"; // 第三段含字母"a"
        
        assertThrows(IllegalArgumentException.class, 
            () -> networkConfig.setIpAddress(invalidIp), 
            "IP段含非数字字符时应抛出IllegalArgumentException");
    }
}