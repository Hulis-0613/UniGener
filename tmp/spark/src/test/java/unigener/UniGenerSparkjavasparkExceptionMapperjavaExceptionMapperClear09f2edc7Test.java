import spark.ExceptionMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UniGenerSparkjavasparkExceptionMapperjavaExceptionMapperClear09f2edc7Test {

    private StateHolder stateHolder;

    @BeforeEach
    void setUp() {
        stateHolder = new StateHolder();
    }

    // 正常路径：初始化后调用clear，验证状态被清除
    @Test
    void clear_WhenInitializedWithData_ShouldClearAllState() {
        // Arrange：初始化并添加测试数据
        stateHolder.initialize();
        stateHolder.addItem("testItem1");
        stateHolder.addItem("testItem2");
        assertEquals(2, stateHolder.getItemCount(), "初始化后应包含2个元素");

        // Act：调用clear方法
        stateHolder.clear();

        // Assert：验证状态已清除
        assertEquals(0, stateHolder.getItemCount(), "clear后元素数量应为0");
        assertTrue(stateHolder.getItems().isEmpty(), "clear后集合应为空");
        assertFalse(stateHolder.hasUncommittedChanges(), "clear后不应有未提交变更");
    }

    // 异常路径1：未初始化时调用clear，预期抛出IllegalStateException
    @Test
    void clear_WhenNotInitialized_ShouldThrowIllegalStateException() {
        // Arrange：未调用initialize方法（初始状态）

        // Act & Assert：调用clear应抛出异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> stateHolder.clear(),
            "未初始化时调用clear应抛出IllegalStateException"
        );
        assertEquals("StateHolder未初始化，无法执行clear操作", exception.getMessage(), "异常信息不匹配");
    }

    // 异常路径2：已关闭状态调用clear，预期抛出IllegalStateException
    @Test
    void clear_WhenClosed_ShouldThrowIllegalStateException() {
        // Arrange：初始化后关闭
        stateHolder.initialize();
        stateHolder.close();
        assertTrue(stateHolder.isClosed(), "StateHolder应处于关闭状态");

        // Act & Assert：调用clear应抛出异常
        IllegalStateException exception = assertThrows(
            IllegalStateException.class,
            () -> stateHolder.clear(),
            "已关闭时调用clear应抛出IllegalStateException"
        );
        assertEquals("StateHolder已关闭，无法执行clear操作", exception.getMessage(), "异常信息不匹配");
    }

    // 边界场景：空状态下调用clear（正常路径扩展，确保无副作用）
    @Test
    void clear_WhenAlreadyEmpty_ShouldDoNothing() {
        // Arrange：初始化但未添加数据（空状态）
        stateHolder.initialize();
        assertEquals(0, stateHolder.getItemCount(), "初始空状态元素数量应为0");

        // Act：调用clear方法
        stateHolder.clear();

        // Assert：状态无变化（仍为空）
        assertEquals(0, stateHolder.getItemCount(), "空状态clear后元素数量仍应为0");
        assertDoesNotThrow(() -> stateHolder.clear(), "空状态clear不应抛出异常");
    }
}