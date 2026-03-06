@echo off
setlocal
cd /d "%~dp0"

REM JDK a Program Files alatt (valamelyik legyen)
if exist "C:\Program Files\Java\jdk-21.0.10\bin\java.exe" set "JAVA_HOME=C:\Program Files\Java\jdk-21.0.10"
if exist "C:\Program Files\Java\jdk-21\bin\java.exe" set "JAVA_HOME=C:\Program Files\Java\jdk-21"
if exist "C:\Program Files\Java\latest\bin\java.exe" set "JAVA_HOME=C:\Program Files\Java\latest"

if not defined JAVA_HOME (
  echo JAVA_HOME nem talalhato. Telepits JDK-t pl. a https://adoptium.net oldalrol.
  echo Majd allitsd be: set "JAVA_HOME=C:\Program Files\Java\jdk-21"
  exit /b 1
)

REM Maven a Letoltesekben
set "MAVEN_CMD=C:\Users\{user}\Downloads\apache-maven-3.9.13\apache-maven-3.9.13\bin\mvn.cmd"
if not exist "%MAVEN_CMD%" (
  echo Maven nem talalhato: %MAVEN_CMD%
  echo Valtoztasd meg a MAVEN_CMD utvonalat ebben a fajlban, vagy add a mvn-t a PATH-hoz.
  exit /b 1
)

echo JAVA_HOME=%JAVA_HOME%
echo Backend inditasa...
call "%MAVEN_CMD%" spring-boot:run
exit /b %ERRORLEVEL%
