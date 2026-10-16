# 工具箱 RoadToCode · 打包分发手册

## 一、环境前提（一次性）

- JDK 21（`jpackage` 随 JDK 提供，位于 JDK 的 `bin` 目录）
- IntelliJ IDEA + Maven
- 首次打包需联网（下载 JavaFX 平台本地库 + maven-shade-plugin）

## 二、日常开发调试

启动：`mvn javafx:run`（或 IntelliJ 右侧 Maven 面板 → Plugins → javafx → javafx:run 双击）

> 不要直接运行 `app.App` 的 main，会报「缺少 JavaFX 运行时组件」。
> 这是 JavaFX 模块机制导致的，不是代码 bug。正确入口：
> - 开发期：`mvn javafx:run`
> - 打包后：`app.Launcher`（fat jar 的主类）

## 三、新增小工具

1. 新建包 `tool.xxx`，写类 `XxxTool implements app.Tool`（实现 `getName / getGroup / getView`）。
2. 在 `app.App.start()` 里 `registry.register(new XxxTool())`。
3. 壳（`MainShell` / `ToolRegistry` / `Tool`）无需改动，侧边栏自动出现。

## 四、打包分发（三步）

1. **确认平台**：`pom.xml` 的 `dist` profile 里，3 个 javafx 依赖的 `classifier`
   - Windows = `win`（当前）
   - macOS = `mac`
   - Linux = `linux`
2. **打 fat jar**：`mvn clean package -Pdist`
   （IntelliJ：Maven 面板勾选 `dist` profile → 运行 `package`）
   产出 `target\toolbox.jar`
3. **出免装应用**：双击 `package.bat`
   产出 `dist\ToolBox\ToolBox.exe`（自带 JRE）

## 五、分发

把 `dist\ToolBox` 整个目录打成 zip 发给对方，对方双击 `ToolBox.exe` 即可，无需装 JDK/Maven。

## 六、进阶（可选）

- **单个安装包**：装 WiX 后，`jpackage` 加 `--type msi`（当前是 `--type app-image` 免装目录）
- **图标 / 版本号**：`jpackage` 加 `--icon app.ico --app-version 1.0.0`

## 七、踩坑速查

| 现象 | 原因 | 解决 |
|---|---|---|
| 缺少 JavaFX 运行时组件 | 直接 Run `App.main` | 用 `mvn javafx:run` 或 `app.Launcher` 启动 |
| bat 报 `'n' / 'ist' 不是命令` | bat 里中文 UTF-8 被当 GBK 读 | bat 全英文，或存成 GBK |
| 换平台打不出对应包 | classifier 写死 `win` | `dist` profile 改 `mac` / `linux` |
| jpackage 找不到 | JDK 的 `bin` 不在 PATH | 把 JDK bin 加入 PATH |
