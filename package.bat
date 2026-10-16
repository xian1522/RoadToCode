@echo off
setlocal

REM Build a self-contained app-image for distribution.
REM Prerequisite: run "mvn clean package -Pdist" first to produce target\toolbox.jar.

if not exist "target\toolbox.jar" (
    echo [ERROR] target\toolbox.jar not found.
    echo Please run: mvn clean package -Pdist
    exit /b 1
)

if exist dist rmdir /s /q dist

jpackage --input target --main-jar toolbox.jar --main-class app.Launcher --name ToolBox --type app-image --dest dist

if errorlevel 1 (
    echo [ERROR] jpackage failed.
    exit /b 1
)

echo.
echo Done: dist\ToolBox\ToolBox.exe
echo Zip the whole dist\ToolBox folder to distribute.
