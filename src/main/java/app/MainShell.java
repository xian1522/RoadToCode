package app;

import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.Map;

/**
 * 宿主主窗口：左侧分组导航 + 右侧内容区。
 * 只负责「导航 + 切换 + 承载」，不关心具体是哪个工具。
 */
public class MainShell {

    private final ToolRegistry registry;
    private final BorderPane root = new BorderPane();
    private final Map<TreeItem<String>, Tool> toolByItem = new HashMap<>();
    private final Map<Tool, Node> viewCache = new HashMap<>();

    public MainShell(ToolRegistry registry) {
        this.registry = registry;
        TreeView<String> nav = buildNav();
        root.setLeft(nav);
        root.setCenter(new StackPane(new Label("请从左侧选择一个工具")));
        selectFirstTool(nav);
    }

    public Node getView() {
        return root;
    }

    private TreeView<String> buildNav() {
        TreeItem<String> treeRoot = new TreeItem<>("工具");
        for (String group : registry.getGroups()) {
            TreeItem<String> groupItem = new TreeItem<>(group);
            groupItem.setExpanded(true);
            for (Tool tool : registry.getByGroup(group)) {
                TreeItem<String> leaf = new TreeItem<>(tool.getName());
                groupItem.getChildren().add(leaf);
                toolByItem.put(leaf, tool);
            }
            treeRoot.getChildren().add(groupItem);
        }

        TreeView<String> tree = new TreeView<>(treeRoot);
        tree.setShowRoot(false);
        tree.setMinWidth(180);
        tree.setPrefWidth(200);
        tree.getSelectionModel().selectedItemProperty().addListener((obs, old, sel) -> {
            if (sel != null) {
                Tool tool = toolByItem.get(sel);
                if (tool != null) {
                    // 缓存视图，避免每次切换重建导致输入内容丢失
                    Node view = viewCache.computeIfAbsent(tool, Tool::getView);
                    root.setCenter(view);
                }
            }
        });
        return tree;
    }

    private void selectFirstTool(TreeView<String> nav) {
        TreeItem<String> treeRoot = nav.getRoot();
        if (treeRoot != null && !treeRoot.getChildren().isEmpty()) {
            TreeItem<String> firstGroup = treeRoot.getChildren().get(0);
            if (!firstGroup.getChildren().isEmpty()) {
                nav.getSelectionModel().select(firstGroup.getChildren().get(0));
            }
        }
    }
}
