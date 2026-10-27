package app.tool.sqlformat;

import app.Tool;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.control.ToolBar;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.fxmisc.richtext.CodeArea;

/**
 * SQL 格式化工具：第一个「插件式」工具。
 */
public class SqlFormatTool implements Tool {

    private final FormatService service = new FormatService();
    private CodeArea input;
    private CodeArea output;

    @Override
    public String getName() {
        return "SQL 格式化";
    }

    @Override
    public String getGroup() {
        return "数据库";
    }

    @Override
    public Node getView() {
        input = SqlCodeArea.create(true);
        output = SqlCodeArea.create(false);

        Button format = new Button("格式化");
        format.setOnAction(e -> doFormat());

        Button copy = new Button("复制结果");
        copy.setOnAction(e -> copyOutput());

        Button clear = new Button("清空");
        clear.setOnAction(e -> {
            input.clear();
            output.clear();
        });

        ToolBar toolBar = new ToolBar(format, copy, clear);

        VBox inputPane = labeledPane("输入 SQL", input);
        VBox outputPane = labeledPane("格式化结果", output);

        SplitPane split = new SplitPane(inputPane, outputPane);
        split.setOrientation(Orientation.VERTICAL);
        split.setDividerPositions(0.5);

        BorderPane root = new BorderPane();
        root.setTop(toolBar);
        root.setCenter(split);
        return root;
    }

    private VBox labeledPane(String title, CodeArea area) {
        Label label = new Label(title);
        VBox box = new VBox(5, label, area);
        VBox.setVgrow(area, Priority.ALWAYS);
        box.setPadding(new Insets(6));
        return box;
    }

    private void doFormat() {
        try {
            String result = service.format(input.getText());
            output.replaceText(result);
        } catch (Exception ex) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("格式化失败");
            alert.setHeaderText("SQL 解析出错");
            alert.setContentText(ex.getMessage());
            alert.showAndWait();
        }
    }

    private void copyOutput() {
        String text = output.getText();
        if (text != null && !text.isEmpty()) {
            ClipboardContent content = new ClipboardContent();
            content.putString(text);
            Clipboard.getSystemClipboard().setContent(content);
        }
    }
}
