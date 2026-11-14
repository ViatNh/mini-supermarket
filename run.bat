@echo off
chcp 65001 >nul
cd /d "%~dp0."
if exist bin rd /s /q bin
mkdir bin

powershell -NoProfile -Command "$lines = (Get-ChildItem -Path src -Filter *.java -Recurse | ForEach-Object { $_.FullName.Substring($PWD.Path.Length + 1).Replace('\', '/') }); [IO.File]::WriteAllLines('sources.txt', $lines, (New-Object Text.UTF8Encoding $false))"
javac --release 17 -encoding UTF-8 -d bin @sources.txt
if errorlevel 1 (
  if exist sources.txt del sources.txt
  echo [LOI] Bien dich that bai!
  exit /b 1
)
if exist sources.txt del sources.txt

if "%1"=="test" (
  echo ==================================================
  echo           1. KIEM THU HOA DON
  echo ==================================================
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuHoaDon
  echo.
  echo ==================================================
  echo           2. KIEM THU SAN PHAM
  echo ==================================================
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuSanPham
  echo.
  echo ==================================================
  echo           3. KIEM THU QUAN LY
  echo ==================================================
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuQuanLy
  echo.
  echo ==================================================
  echo           4. KIEM THU LUU TRU VA TON KHO
  echo ==================================================
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuHeThongVaLuuTru
) else if "%1"=="storage" (
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuHeThongVaLuuTru
) else if "%1"=="manage" (
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuQuanLy
) else if "%1"=="product" (
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuSanPham
) else if "%1"=="menu" (
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.Main menu
) else (
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.Main
)
