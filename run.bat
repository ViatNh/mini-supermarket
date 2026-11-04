@echo off
chcp 65001 >nul
cd /d "%~dp0"
if not exist bin mkdir bin
dir /s /b src\*.java > sources.txt
javac -encoding UTF-8 -d bin @sources.txt
del sources.txt
if "%1"=="test" (
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuHoaDon
) else (
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.Main
)
