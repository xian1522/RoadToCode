package app;

import javafx.scene.Node;

/**
 * 工具契约：每个小工具实现该接口，注册进 {@link ToolRegistry} 后自动出现在侧边栏。
 */
public interface Tool {

    /** 工具名（侧边栏显示名） */
    String getName();

    /** 所属分组（侧边栏分组导航用） */
    String getGroup();

    /** 工具主界面根节点 */
    Node getView();

    /** 工具描述（可选） */
    default String getDescription() {
        return "";
    }

    /** 打开工具时回调（可选） */
    default void onOpen() {
    }

    /** 关闭工具时回调（可选） */
    default void onClose() {
    }
}
