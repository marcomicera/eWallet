@echo off

REM Change these two directories as needed
set JDKBIN="C:\Program Files\Java\jdk1.8.0_91\bin"
set PROJECT_DIR=G:\OneDrive\Ingegneria\Altro\Triennale\Programmazione\Progetto\Portafoglio

REM Compiling
%JDKBIN%\javac %PROJECT_DIR%\src\ServerLogAttivitaXML.java -cp %PROJECT_DIR%\build\classes -d %PROJECT_DIR%\build\classes

REM Running
cd %PROJECT_DIR%\build\classes\
%JDKBIN%\java ServerLogAttivitaXML

pause
