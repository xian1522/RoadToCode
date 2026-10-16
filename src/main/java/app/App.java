package app;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tool.sqlformat.SqlFormatTool;

/**
 * 工具箱主程序入口。
 * 后续新增工具：在这里 registry.register(new XxxTool()) 即可。
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        ToolRegistry registry = new ToolRegistry();
        registry.register(new SqlFormatTool());

        MainShell shell = new MainShell(registry);

        Scene scene = new Scene((Parent) shell.getView(), 880, 600);
        stage.setScene(scene);
        stage.setTitle("工具箱");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
