@REM ----------------------------------------------------------------------------
@REM Licensed to the Apache Software Foundation (ASF) under one
@REM or more contributor license agreements. See the NOTICE file
@REM distributed with this work for additional information
@REM regarding copyright ownership. The ASF licenses this file
@REM to you under the Apache License, Version 2.0 (the
@REM "License"); you may not use this file except in compliance
@REM with the License. You may obtain a copy of the License at
@REM
@REM http://www.apache.org/licenses/LICENSE-2.0
@REM
@REM Unless required by applicable law or agreed to in writing,
@REM software distributed under the License is distributed on an
@REM "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
@REM KIND, either express or implied. See the License for the
@REM specific language governing permissions and limitations
@REM under the License.
@REM ----------------------------------------------------------------------------

@REM Maven Start Up Batch script
@echo off
@setlocal

set ERROR_CODE=0

@REM ==== START VALIDATION ====
if not "%JAVA_HOME%" == "" goto OkJHome

@REM Try to find Java from PATH (first result)
where java >nul 2>nul
if %ERRORLEVEL% neq 0 goto NoJava
for /f "tokens=*" %%i in ('where java 2^>nul') do (
  set "JAVA_EXE=%%i"
  goto FoundJava
)
:FoundJava
@REM Derive JAVA_HOME: path is ...\bin\java.exe or ...\javapath\java.exe
set "JAVA_EXE=%JAVA_EXE:\=/%"
for %%i in ("%JAVA_EXE%") do set "JAVA_BIN=%%~dpi"
set "JAVA_BIN=%JAVA_BIN:~0,-1%"
@REM If we have ...\bin, then JAVA_HOME is parent
echo %JAVA_BIN% | findstr /i "\\bin$" >nul 2>nul && (
  for %%i in ("%JAVA_BIN%") do set "JAVA_HOME=%%~dpi"
  set "JAVA_HOME=%JAVA_HOME:~0,-1%"
) || (
  @REM e.g. javapath: use parent of JAVA_BIN as JAVA_HOME
  for %%i in ("%JAVA_BIN%") do set "JAVA_HOME=%%~dpi"
  set "JAVA_HOME=%JAVA_HOME:~0,-1%"
)
if exist "%JAVA_HOME%\bin\java.exe" goto OkJHome
if exist "%JAVA_HOME%\jre\bin\java.exe" goto OkJHome

:NoJava
echo.
echo HIBA: JAVA_HOME nincs beallitva. Allitsa be a JDK konyvtara, pl.: >&2
echo   set "JAVA_HOME=C:\Program Files\Java\jdk-21" >&2
echo.
goto error

:OkJHome
if exist "%JAVA_HOME%\bin\java.exe" set "JAVA_EXE=%JAVA_HOME%\bin\java.exe" & goto init
if exist "%JAVA_HOME%\jre\bin\java.exe" set "JAVA_EXE=%JAVA_HOME%\jre\bin\java.exe" & goto init

echo.
echo HIBA: JAVA_HOME ervenytelen. JAVA_HOME = "%JAVA_HOME%" >&2
echo.
goto error

@REM ==== END VALIDATION ====

:init
@REM Find project base dir
set MAVEN_PROJECTBASEDIR=%CD%
:findBaseDir
IF EXIST "%MAVEN_PROJECTBASEDIR%"\.mvn goto baseDirFound
cd ..
IF "%MAVEN_PROJECTBASEDIR%"=="%CD%" goto baseDirNotFound
set MAVEN_PROJECTBASEDIR=%CD%
goto findBaseDir

:baseDirNotFound
set MAVEN_PROJECTBASEDIR=%CD%
cd "%MAVEN_PROJECTBASEDIR%"

:baseDirFound
cd "%MAVEN_PROJECTBASEDIR%"

set WRAPPER_JAR="%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar"
set WRAPPER_LAUNCHER=org.apache.maven.wrapper.MavenWrapperMain

@REM Download wrapper jar if missing
if exist %WRAPPER_JAR% goto runWrapper

echo Maven wrapper letoltese...
powershell -NoProfile -ExecutionPolicy Bypass -Command ^
  "$ProgressPreference='SilentlyContinue'; " ^
  "[Net.ServicePointManager]::SecurityProtocol=[Net.SecurityProtocolType]::Tls12; " ^
  "Invoke-WebRequest -Uri 'https://repo.maven.apache.org/maven2/org/apache/maven/wrapper/maven-wrapper/3.2.0/maven-wrapper-3.2.0.jar' " ^
  "-OutFile '%MAVEN_PROJECTBASEDIR%\.mvn\wrapper\maven-wrapper.jar' -UseBasicParsing"
if %ERRORLEVEL% neq 0 goto error

:runWrapper
if not defined JAVA_EXE set "JAVA_EXE=%JAVA_HOME%\bin\java.exe"

"%JAVA_EXE%" -classpath %WRAPPER_JAR% "-Dmaven.multiModuleProjectDirectory=%MAVEN_PROJECTBASEDIR%" %WRAPPER_LAUNCHER% %*
if ERRORLEVEL 1 goto error
goto end

:error
set ERROR_CODE=1

:end
@endlocal & set ERROR_CODE=%ERROR_CODE%
exit /B %ERROR_CODE%
