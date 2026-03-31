import spark.utils.urldecoding.TypeUtil;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UniGenerSparkUtilsUrldecodingutilsurldecodingTypeUtiljavaTypeUtilToHexStringb47ecfbfTest {

    // 测试正数值转换
    @Test
    void toHexString_WithZero_Returns00() {
        byte input = 0;
        String expected = "00";
        assertEquals(expected, HexUtils.toHexString(input));
    }

    @Test
    void toHexString_WithPositiveOne_Returns01() {
        byte input = 1;
        String expected = "01";
        assertEquals(expected, HexUtils.toHexString(input));
    }

    @Test
    void toHexString_WithPositiveTen_Returns0A() {
        byte input = 10;
        String expected = "0A";
        assertEquals(expected, HexUtils.toHexString(input));
    }

    @Test
    void toHexString_WithPositiveFifteen_Returns0F() {
        byte input = 15;
        String expected = "0F";
        assertEquals(expected, HexUtils.toHexString(input));
    }

    @Test
    void toHexString_WithPositiveSixteen_Returns10() {
        byte input = 16;
        String expected = "10";
        assertEquals(expected, HexUtils.toHexString(input));
    }

    @Test
    void toHexString_WithMaxValue127_Returns7F() {
        byte input = Byte.MAX_VALUE; // 127
        String expected = "7F";
        assertEquals(expected, HexUtils.toHexString(input));
    }

    // 测试负数值转换
    @Test
    void toHexString_WithNegativeOne_ReturnsFF() {
        byte input = -1;
        String expected = "FF";
        assertEquals(expected, HexUtils.toHexString(input));
    }

    @Test
    void toHexString_WithNegativeTen_ReturnsF6() {
        byte input = -10;
        String expected = "F6"; // 256 - 10 = 246 → 0xF6
        assertEquals(expected, HexUtils.toHexString(input));
    }

    @Test
    void toHexString_WithMinValueNegative128_Returns80() {
        byte input = Byte.MIN_VALUE; // -128
        String expected = "80"; // 补码为10000000 → 0x80
        assertEquals(expected, HexUtils.toHexString(input));
    }
}