@rem Gradle startup script for Windows

@if "%DEBUG%"=="" @echo off
setlocal

set DIRNAME=%~dp0
set APP_HOME=%DIRNAME%
set APP_NAME=Gradle
set CLASSPATH=%APP_HOME%gradle\wrapper\gradle-wrapper.jar

if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
goto fail

:init
if not exist "%APP_HOME%gradle\wrapper\gradle-wrapper.jar" (
    echo ERROR: gradle\wrapper\gradle-wrapper.jar not found.
    echo Run 'gradle wrapper --gradle-version 8.7' once with network access, or open
    echo this project in Android Studio, which will regenerate it.
    goto fail
)

"%JAVA_EXE%" -Dorg.gradle.appname=%APP_NAME% -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %*
goto end

:fail
exit /b 1

:end
endlocal
