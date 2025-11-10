# Simplemente ejecuta:
run.bat@echo off
REM Script para compilar y ejecutar la aplicación JavaFX en Windows

echo ========================================
echo Plataforma de Cursos - JavaFX
echo ========================================

REM Configurar variables
set JAVAFX_PATH=javafx-sdk-21.0.9\lib
set JAVAFX_MODULES=javafx.controls,javafx.fxml,javafx.graphics
set SRC_DIR=src
set OUT_DIR=bin
set LIB_DIR=lib

REM Crear directorio de salida si no existe
if not exist %OUT_DIR% mkdir %OUT_DIR%
if not exist %OUT_DIR%\vista\fxml mkdir %OUT_DIR%\vista\fxml
if not exist %OUT_DIR%\vista\css mkdir %OUT_DIR%\vista\css

echo.
echo [1/3] Compilando aplicacion...
javac --module-path %JAVAFX_PATH% --add-modules %JAVAFX_MODULES% -encoding UTF-8 -cp "%LIB_DIR%\*" -d %OUT_DIR% %SRC_DIR%\vista\MainApplication.java %SRC_DIR%\vista\controladores\*.java %SRC_DIR%\controller\*.java %SRC_DIR%\data\*.java %SRC_DIR%\modelos\usuario\*.java %SRC_DIR%\modelos\cursos\*.java %SRC_DIR%\modelos\inscripcion\*.java %SRC_DIR%\modelos\pago\*.java %SRC_DIR%\exception\*.java %SRC_DIR%\modelos\ConstantesDeNegocio.java

if %ERRORLEVEL% NEQ 0 (
    echo.
    echo ERROR: La compilacion fallo
    pause
    exit /b 1
)

echo.
echo [2/3] Copiando recursos FXML y CSS...
xcopy /Y /Q %SRC_DIR%\vista\fxml\*.fxml %OUT_DIR%\vista\fxml\ >nul 2>&1
xcopy /Y /Q %SRC_DIR%\vista\css\*.css %OUT_DIR%\vista\css\ >nul 2>&1

echo.
echo [3/3] Ejecutando aplicacion...
echo.
java --module-path %JAVAFX_PATH% --add-modules %JAVAFX_MODULES% -cp "%OUT_DIR%;%LIB_DIR%\*" vista.MainApplication

pause
