#!/usr/bin/env bash
# Dùng: ./run.sh          -> chạy demo
#       ./run.sh test     -> chạy toàn bộ kiểm thử
#       ./run.sh manage   -> chạy kiểm thử module quản lý
set -e
cd "$(dirname "$0")"
mkdir -p bin
javac -encoding UTF-8 -d bin $(find src manage -name "*.java")
if [ "$1" = "test" ]; then
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuHoaDon
  echo ""
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuQuanLy
elif [ "$1" = "manage" ]; then
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuQuanLy
else
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.Main
fi
