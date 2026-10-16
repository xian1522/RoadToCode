package app;

import javafx.application.Application;

/**
 * 非 Application 启动类：供 java -jar / jpackage 打包后的启动入口。
 * 绕开 JVM 对「main 类继承 Application」的模块检查。
 */
public class Launcher {

    public static void main(String[] args) {
        Application.launch(App.class, args);
    }
}
