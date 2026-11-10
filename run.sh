#!/usr/bin/env bash
# Dùng: ./run.sh          -> chạy demo tổng hợp
#       ./run.sh test     -> chạy toàn bộ kiểm thử
#       ./run.sh menu     -> chạy menu tương tác
#       ./run.sh manage   -> chạy kiểm thử module quản lý
#       ./run.sh product  -> chạy kiểm thử module sản phẩm
set -e
cd "$(dirname "$0")"
mkdir -p bin
javac -encoding UTF-8 -d bin $(find src -name "*.java")
if [ "$1" = "test" ]; then
  echo "=== 1. KIỂM THỬ HÓA ĐƠN ==="
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuHoaDon
  echo ""
  echo "=== 2. KIỂM THỬ SẢN PHẨM ==="
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuSanPham
  echo ""
  echo "=== 3. KIỂM THỬ QUẢN LÝ ==="
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuQuanLy
  echo ""
  echo "=== 4. KIỂM THỬ LƯU TRỮ VÀ TỒN KHO ==="
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuHeThongVaLuuTru
elif [ "$1" = "storage" ]; then
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuHeThongVaLuuTru
elif [ "$1" = "manage" ]; then
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuQuanLy
elif [ "$1" = "product" ]; then
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.KiemThuSanPham
elif [ "$1" = "menu" ]; then
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.Main menu
else
  java -Dfile.encoding=UTF-8 -cp bin sieuthi.Main
fi
