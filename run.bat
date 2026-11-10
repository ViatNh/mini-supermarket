@echo off
chcp 65001 >nul
cd /d "%~dp0"
if not exist bin mkdir bin

powershell -NoProfile -Command "$lines = (Get-ChildItem -Path src,manage -Filter *.java -Recurse | ForEach-Object { $_.FullName.Substring($PWD.Path.Length + 1).Replace('\', '/') }); [IO.File]::WriteAllLines('sources.txt', $lines, (New-Object Text.UTF8Encoding $false))"
javac -encoding UTF-8 -d bin @sources.txt
if exist sources.txt del sources.txt

if "%1"=="test" (
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuHoaDon
  echo.
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuQuanLy
) else if "%1"=="manage" (
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuQuanLy
) else (
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.Main
)
