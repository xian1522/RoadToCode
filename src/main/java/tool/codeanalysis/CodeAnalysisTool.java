package tool.codeanalysis;

import app.Tool;
import com.alibaba.fastjson2.JSON;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.Duration;
import org.apache.commons.io.FileUtils;
import sourcecode.processors.model.TreeNode;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private HBox legend;
    @FXML
    private Label legendCaret;
    @FXML
    private VBox inputPanel;
    @FXML
    private TreeView<TreeNode> treeView;

    private boolean collapsed;

    private final String APPDATA = System.getenv("APPDATA");

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
            Parent root = loader.load();
            // 样式表挂到根节点上（而非 Scene），不依赖 view 何时被加入 Scene
            root.getStylesheets().add(getClass().getResource("code-analysis.css").toExternalForm());
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
        Path myToolKit = Paths.get(APPDATA)
                .resolve("MyToolKit\\"
                + className.substring(className.lastIndexOf(".") + 1)
                + "_" + methodNameField.getText() + ".json");
        try {

            File file = new File(myToolKit.toUri());
            //创建目录
            FileUtils.createParentDirectories(file);
            //创建文件
            boolean newFile = file.createNewFile();

            TreeNode treeNode = null;
            System.out.println(file);
            //文件已存在
            if(!newFile) {
                String analysisContent = FileUtils.readFileToString(file, "UTF-8");
                treeNode = JSON.to(TreeNode.class, analysisContent);
            }else {
                treeNode = codeAnalysisService.codeAnalysis(projectPath, className, methodName, paramNames);
            }
            TreeItem<TreeNode> rootItem = CodeAnalysisTool.generateTreeItem(treeNode);
            treeView.setRoot(rootItem);
            rootItem.setExpanded(true);
        } catch (Exception ex) {
            showError(ex.getMessage());
        } finally {
            analyzeButton.setDisable(false);
        }
    }

    /**
     * 保存解析结果
     */
    @FXML
    private void onSave() {
        TreeItem<TreeNode> root = treeView.getRoot();
        if(root == null) {
            showError("无解析结果可保存");
            return;
        }
        String analysisContent = JSON.toJSONString(root.getValue());
        String className = classNameField.getText();

        Path myToolKit = Paths.get(APPDATA)
                .resolve("MyToolKit\\"
                + className.substring(className.lastIndexOf(".") + 1)
                + "_" + methodNameField.getText() + ".json");

        try {
            FileUtils.writeStringToFile(new File(myToolKit.toUri()), analysisContent, "UTF-8");
        } catch (IOException e) {
            showError(e.getMessage());
        }
        showSuccess("保存成功");
    }

    @FXML
    private void onToggleCollapse() {
        collapsed = !collapsed;
        for (Node child : inputPanel.getChildren()) {
            child.setVisible(!collapsed);
            child.setManaged(!collapsed);
        }
        legendCaret.setText(collapsed ? "▸" : "▾");
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("解析失败");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }


    public static TreeItem<TreeNode> generateTreeItem(TreeNode treeNode) {
        TreeItem<TreeNode> treeItem = new TreeItem(treeNode);
        if (treeNode.hasChildren()) {
            for (TreeNode childNode : treeNode.getChildren()) {
                treeItem.getChildren().add(generateTreeItem(childNode));
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
