package tool.codeanalysis;

import app.Tool;
import javafx.scene.Node;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import sourcecode.processors.model.TreeNode;

/**
 * 源码解析工具
 */
public class CodeAnalysisTool implements Tool {

    private final CodeAnalysisService codeAnalysisService = new CodeAnalysisService();

    @Override
    public String getName() {
        return "源码解析";
    }

    @Override
    public String getGroup() {
        return "代码";
    }

    @Override
    public Node getView() {
        TreeView<TreeNode> treeView = new TreeView();
        VBox vBox = new VBox(treeView);

        TreeNode treeNode = codeAnalysisService.codeAnalysis();
        TreeItem root = generateTreeItem(treeNode);

        treeView.setRoot(root);
        //绑定鼠标悬浮提醒
        bindTreeViewToolTip(treeView);

        return vBox;
    }

    private TreeItem generateTreeItem(TreeNode treeNode) {

        TreeItem<TreeNode> treeItem = new TreeItem(treeNode);
        if(treeNode.hasChildren()) {
            for (TreeNode childNode : treeNode.getChildren()) {
                TreeItem childItem = generateTreeItem(childNode);
                treeItem.getChildren().add(childItem);
            }
        }
        return treeItem;
    }

    private void bindTreeViewToolTip(TreeView treeView) {
        // ========== ① 自定义TreeCell：实现悬浮Tooltip ==========
        treeView.setCellFactory(new Callback<TreeView<TreeNode>, TreeCell<TreeNode>>() {
            @Override
            public TreeCell call(TreeView<TreeNode> treeView) {
                return new TreeCell<TreeNode>() {
                    private final Tooltip tooltip = new Tooltip();
                    {
                        tooltip.setShowDelay(Duration.millis(150));
                        tooltip.setWrapText(true);
                        tooltip.setMaxWidth(400);
                    }
                    @Override
                    protected void updateItem(TreeNode item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                            setTooltip(null);
                        } else {
                            setText(item.getContent());
                            tooltip.setText(item.getAiAnalysis());
                            setTooltip(tooltip); // 绑定悬浮提示
                        }
                    }
                };
            }
        });
    }
}
