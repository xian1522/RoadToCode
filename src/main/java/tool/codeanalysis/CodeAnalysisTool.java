package tool.codeanalysis;

import app.Tool;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;
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
        TreeView treeView = new TreeView();
        VBox vBox = new VBox(treeView);

        TreeNode treeNode = codeAnalysisService.codeAnalysis();
        TreeItem root = generateTreeItem(treeNode);

        treeView.setRoot(root);

        return vBox;
    }

    private TreeItem generateTreeItem(TreeNode treeNode) {

        TreeItem treeItem = new TreeItem(treeNode.getContent());

        if(treeNode.hasChildren()) {
            for (TreeNode childNode : treeNode.getChildren()) {
                TreeItem childItem = generateTreeItem(childNode);
                treeItem.getChildren().add(childItem);
            }
        }
        return treeItem;
    }
}
