import spark.routematch.RouteMatch;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkRoutematchsparkroutematchRouteMatchjavaRouteMatchGetTarget0838fe48Test {

    /**
     * 正常路径：目标已设置时，getTarget返回正确的目标值
     */
    @Test
    void getTarget_WhenTargetIsSet_ShouldReturnTarget() {
        // 准备：创建RouteMatch实例并设置目标
        RouteMatch routeMatch = new RouteMatch();
        String expectedTarget = "serviceA";
        routeMatch.setTarget(expectedTarget); // 假设存在设置目标的方法

        // 执行：调用getTarget方法
        String actualTarget = routeMatch.getTarget();

        // 断言：返回值与预期目标一致
        assertEquals(expectedTarget, actualTarget, "目标值不匹配");
    }

    /**
     * 异常路径：目标未设置时，getTarget抛出IllegalStateException
     */
    @Test
    void getTarget_WhenTargetNotSet_ShouldThrowIllegalStateException() {
        // 准备：创建RouteMatch实例但不设置目标
        RouteMatch routeMatch = new RouteMatch();

        // 执行并断言：调用getTarget时抛出异常，且异常信息正确
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            routeMatch::getTarget,
            "未设置目标时应抛出IllegalStateException"
        );
        assertEquals("目标未设置", exception.getMessage(), "异常信息不匹配");
    }
}