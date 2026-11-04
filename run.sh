#!/usr/bin/env bash
# Dùng: ./run.sh          -> chạy demo
#       ./run.sh test     -> chạy kiểm thử
set -e
cd "$(dirname "$0")"
mkdir -p bin
javac -encoding UTF-8 -d bin $(find src -name "*.java")
if [ "$1" = "test" ]; then
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuHoaDon
else
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.Main
fi
