import spark.Service;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkServicejavaServiceIpAddress020463c8Test {

    private final Service service = new Service();

    // 正常路径：有效 IPv4 地址
    @Test
    void ipAddress_ValidIpv4_ReturnsTrue() {
        // 标准有效地址
        assertTrue(service.ipAddress("192.168.1.1"));
        // 边界值（0.0.0.0 和 255.255.255.255）
        assertTrue(service.ipAddress("0.0.0.0"));
        assertTrue(service.ipAddress("255.255.255.255"));
        // 普通范围地址
        assertTrue(service.ipAddress("10.0.0.1"));
        assertTrue(service.ipAddress("172.16.0.255"));
    }

    // 异常路径：null 输入
    @Test
    void ipAddress_NullInput_ReturnsFalse() {
        assertFalse(service.ipAddress(null));
    }

    // 异常路径：空字符串
    @Test
    void ipAddress_EmptyString_ReturnsFalse() {
        assertFalse(service.ipAddress(""));
    }

    // 异常路径：段数不足（少于 4 段）
    @Test
    void ipAddress_InvalidSegmentCount_LessThan4_ReturnsFalse() {
        assertFalse(service.ipAddress("192.168.1"));   // 3 段
        assertFalse(service.ipAddress("192.168"));     // 2 段
        assertFalse(service.ipAddress("192"));         // 1 段
    }

    // 异常路径：段数过多（多于 4 段）
    @Test
    void ipAddress_InvalidSegmentCount_MoreThan4_ReturnsFalse() {
        assertFalse(service.ipAddress("192.168.1.1.1")); // 5 段
        assertFalse(service.ipAddress("1.2.3.4.5.6"));   // 6 段
    }

    // 异常路径：段包含非数字字符
    @Test
    void ipAddress_NonNumericSegment_ReturnsFalse() {
        assertFalse(service.ipAddress("192.168.a.1"));   // 字母
        assertFalse(service.ipAddress("192.b.1.1"));     // 字母
        assertFalse(service.ipAddress("c.168.1.1"));     // 字母
        assertFalse(service.ipAddress("192.168.1.x"));   // 字母
        assertFalse(service.ipAddress("192.168.1.1#"));  // 特殊符号
    }

    // 异常路径：段值大于 255
    @Test
    void ipAddress_SegmentValue_Exceeds255_ReturnsFalse() {
        assertFalse(service.ipAddress("256.0.0.1"));     // 第一段超限
        assertFalse(service.ipAddress("192.300.1.1"));   // 第二段超限
        assertFalse(service.ipAddress("192.168.256.1")); // 第三段超限
        assertFalse(service.ipAddress("192.168.1.256")); // 第四段超限
    }

    // 异常路径：段值小于 0
    @Test
    void ipAddress_SegmentValue_Negative_ReturnsFalse() {
        assertFalse(service.ipAddress("-1.0.0.1"));      // 第一段负数
        assertFalse(service.ipAddress("192.-5.1.1"));    // 第二段负数
        assertFalse(service.ipAddress("192.168.-100.1"));// 第三段负数
    }

    // 异常路径：以点开头或结尾
    @Test
    void ipAddress_LeadingOrTrailingDot_ReturnsFalse() {
        assertFalse(service.ipAddress(".192.168.1.1"));  // 开头点
        assertFalse(service.ipAddress("192.168.1.1."));  // 结尾点
        assertFalse(service.ipAddress(".192.168.1."));   // 开头和结尾点
    }

    // 异常路径：包含空格
    @Test
    void ipAddress_WithSpaces_ReturnsFalse() {
        assertFalse(service.ipAddress("192.168. 1.1"));  // 段内空格
        assertFalse(service.ipAddress(" 192.168.1.1"));  // 开头空格
        assertFalse(service.ipAddress("192.168.1.1 "));  // 结尾空格
        assertFalse(service.ipAddress("192. 168.1.1"));  // 段间空格
    }

    // 异常路径：段含前导零（非 0 段的前导零视为无效，如 "01" 不是合法段）
    @Test
    void ipAddress_LeadingZeroInSegment_ReturnsFalse() {
        assertFalse(service.ipAddress("192.168.01.1"));  // 第三段前导零
        assertFalse(service.ipAddress("01.0.0.1"));      // 第一段前导零
        assertFalse(service.ipAddress("192.001.1.1"));   // 第二段前导零
    }
}