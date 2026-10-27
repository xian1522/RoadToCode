package tool.codeanalysis;

import app.Tool;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeCell;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.util.Callback;
import javafx.util.Duration;
import sourcecode.processors.model.TreeNode;

import java.io.IOException;
import java.util.List;

/**
 * 源码解析工具
 */
public class CodeAnalysisTool implements Tool {

    private final CodeAnalysisService codeAnalysisService = new CodeAnalysisService();

    @FXML
    private TextField projectPathField;
    @FXML
    private TextField classNameField;
    @FXML
    private TextField methodNameField;
    @FXML
    private TextField paramNamesField;
    @FXML
    private Button analyzeButton;
    @FXML
    private TreeView<TreeNode> treeView;

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
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("code-analysis.fxml"));
            loader.setController(this);
            Node root = loader.load();
            bindTreeViewToolTip(treeView);
            return root;
        } catch (IOException e) {
            throw new IllegalStateException("加载 code-analysis.fxml 失败", e);
        }
    }

    @FXML
    private void onAnalyze() {
        String projectPath = projectPathField.getText();
        String className = classNameField.getText();
        String methodName = methodNameField.getText();
        List<String> paramNames = CodeAnalysisService.parseParamNames(paramNamesField.getText());

        if (projectPath == null || projectPath.isBlank()) {
            showError("请填写项目路径");
            return;
        }
        if (className == null || className.isBlank()) {
            showError("请填写类名");
            return;
        }
        if (methodName == null || methodName.isBlank()) {
            showError("请填写方法名");
            return;
        }

        analyzeButton.setDisable(true);
        try {
            TreeNode treeNode = codeAnalysisService.codeAnalysis(projectPath, className, methodName, paramNames);
            TreeItem<TreeNode> rootItem = generateTreeItem(treeNode);
            treeView.setRoot(rootItem);
            rootItem.setExpanded(true);
        } catch (Exception ex) {
            showError(ex.getMessage());
        } finally {
            analyzeButton.setDisable(false);
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("解析失败");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private TreeItem<TreeNode> generateTreeItem(TreeNode treeNode) {
        TreeItem<TreeNode> treeItem = new TreeItem(treeNode);
        if (treeNode.hasChildren()) {
            for (TreeNode childNode : treeNode.getChildren()) {
                TreeItem<TreeNode> childItem = generateTreeItem(childNode);
                treeItem.getChildren().add(childItem);
            }
        }
        return treeItem;
    }

    private void bindTreeViewToolTip(TreeView<TreeNode> treeView) {
        // ========== ① 自定义TreeCell：实现悬浮Tooltip ==========
        treeView.setCellFactory(new Callback<TreeView<TreeNode>, TreeCell<TreeNode>>() {
            @Override
            public TreeCell<TreeNode> call(TreeView<TreeNode> treeView) {
                return new TreeCell<TreeNode>() {
                    private final Tooltip tooltip = new Tooltip();
                    {
                        tooltip.setShowDelay(Duration.millis(150));
                        tooltip.setShowDuration(Duration.INDEFINITE);
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
