# Siêu thị mini – phần Hóa đơn (Java)

Cài đặt theo sơ đồ lớp `docs/SieuThiMini.drawio`: **HoaDon, ChiTietHoaDon, IPhuongThucThanhToan (TienMat / TheNganHang / ChuyenKhoan), PhieuBaoHanh**, cùng các lớp phụ thuộc tối thiểu để chạy được (Person, KhachHang, NhanVien, NhanVienFullTime, ThuNgan, SanPham, ThucPham, DoUong, DoDung, IBaoHanh, KhuyenMai, IApDungKhuyenMai).

> Các lớp phụ thuộc là bản tối thiểu theo đúng sơ đồ. Nếu thành viên khác đã viết bản đầy đủ của lớp nào (vd. `SanPham`, `KhachHang`), hãy **dùng bản của họ** và chỉ giữ lại các hàm mà `HoaDon` đang gọi (xem mục "Hợp nhất code").

## 1. Cài đặt (làm 1 lần)

1. Cài **JDK 17 trở lên** (khuyên dùng Temurin: https://adoptium.net). Kiểm tra trong terminal:
   ```
   java -version
   javac -version
   ```
   Cả hai lệnh đều phải in ra phiên bản >= 17.
2. Cài **VS Code** và extension **Extension Pack for Java** (của Microsoft).
3. Cài **Git**: https://git-scm.com

## 2. Chạy trong VS Code

1. `File → Open Folder…` → chọn thư mục `mini-supermarket` (thư mục chứa `src/`).
2. Chờ góc dưới phải hết biểu tượng loading của Java.
3. Mở `src/sieuthi/Main.java` → bấm **▶ Run** phía trên `main`, hoặc nhấn **F5** (chọn "Chạy Main").
4. Chạy kiểm thử: mở `KiemThuHoaDon.java` → ▶ Run. Cuối output phải có `22 đạt, 0 lỗi`.

Chạy bằng terminal (không cần VS Code):
```
# Windows
run.bat          (demo)
run.bat test     (kiểm thử)

# macOS / Linux / Git Bash
./run.sh
./run.sh test
```

### Lỗi thường gặp
| Lỗi | Cách xử lý |
|---|---|
| Chữ tiếng Việt thành `???` | Dùng `run.bat` / nút Run của VS Code (đã ép UTF-8). Nếu vẫn lỗi: `chcp 65001` trong terminal |
| `javac is not recognized` | Chưa cài JDK hoặc chưa thêm vào PATH – cài lại Temurin, tick "Add to PATH", mở lại VS Code |
| VS Code báo `... is a non-project file` | Mở **đúng thư mục `mini-supermarket`** (không mở thư mục cha) |
| Không thấy nút Run | Chưa cài Extension Pack for Java, hoặc đợi Java load xong |

## 3. Đẩy lên GitHub (người viết code)

Repo: https://github.com/ViatNh/mini-supermarket.git

```bash
# 1) Lấy repo về máy (bỏ qua nếu đã clone)
git clone https://github.com/ViatNh/mini-supermarket.git
cd mini-supermarket

# 2) Tạo nhánh riêng cho phần hóa đơn
git checkout -b feature/hoa-don

# 3) Chép toàn bộ nội dung của gói này (src/, docs/, .vscode/, run.sh, run.bat,
#    .gitignore, README.md) vào thư mục repo vừa clone

# 4) Khai báo danh tính (chỉ lần đầu trên máy)
git config --global user.name  "Tên của bạn"
git config --global user.email "email-dang-ky-github@example.com"

# 5) Kiểm tra, commit, push
git status
git add .
git commit -m "Thêm phần Hóa đơn: HoaDon, ChiTietHoaDon, thanh toán, phiếu bảo hành"
git push -u origin feature/hoa-don
```
- Lần push đầu GitHub sẽ yêu cầu đăng nhập qua trình duyệt (hoặc dùng Personal Access Token thay mật khẩu).
- Bạn phải là **collaborator** của repo (chủ repo: Settings → Collaborators → Add people), nếu không sẽ bị lỗi 403.
- Vào GitHub bấm **Compare & pull request** để gộp vào `main`. Nhóm nhỏ có thể gộp thẳng: `git checkout main && git merge feature/hoa-don && git push`.

Trong VS Code có thể làm tương tự qua tab **Source Control** (Ctrl+Shift+G): Stage → nhập message → Commit → Sync/Publish Branch.

## 4. Các thành viên khác chạy lại

```bash
git clone https://github.com/ViatNh/mini-supermarket.git   # lần đầu
cd mini-supermarket
git checkout main && git pull                              # (hoặc: git checkout feature/hoa-don)
```
Rồi làm theo **mục 2** (mở thư mục trong VS Code → Run `Main`). Máy nào cũng cần JDK 17+.

Cập nhật về sau: `git pull`. Muốn đóng góp: tạo nhánh mới `git checkout -b feature/ten-phan`, commit, push, mở Pull Request. **Không commit thư mục `bin/`** (đã có trong `.gitignore`).

## 5. Hợp nhất code với thành viên khác

- Mỗi file một lớp, package `sieuthi.model`. Nếu nhóm đã có package khác, đổi dòng `package ...;` và `import` cho khớp.
- Gặp conflict ở file cùng tên (vd. `SanPham.java`): giữ bản của nhóm, rồi đảm bảo còn các hàm mà phần hóa đơn dùng:
  - `SanPham`: `tinhGia()`, `getTenSanPham()`, `getSoLuongTonKho()`, `giamTonKho(int)`, `conHanSuDung()`
  - `KhachHang`: `tichDiem(double)`, `getHoTen()`
  - `NhanVien`/`ThuNgan`: `getHoTen()`; `ThuNgan.thanhToanHoaDon(HoaDon, IPhuongThucThanhToan)`
- `QuanLyDonHang` (quản lý danh sách HoaDon) chưa nằm trong gói này; nó chỉ cần giữ `List<HoaDon>` và dùng `getMaHoaDon()`, `getNgayLap()`, `tinhTienPhaiTra()`.

## 6. Quy tắc nghiệp vụ đã cài

- `tinhTongTien()` = tổng hàng **chưa** giảm; `tinhTienGiam()` = giảm theo khuyến mãi (chỉ khi ngày lập hóa đơn nằm trong hiệu lực); `tinhTienPhaiTra()` = hiệu hai số trên.
- Đơn giá chốt lúc thêm vào hóa đơn; thêm cùng sản phẩm thì cộng dồn; không vượt tồn kho; không bán hàng hết hạn.
- **Chỉ `ThuNgan`** thu tiền (`thanhToanHoaDon`). Thanh toán thành công mới: trừ tồn kho, ghi `nguoiThanhToan`, tích điểm (10.000đ = 1 điểm) và khóa hóa đơn. Hóa đơn lưu riêng `nguoiLap` (NhanVien) và `nguoiThanhToan` (ThuNgan).
- Thẻ ngân hàng hợp lệ = đúng 16 chữ số; chuyển khoản cần mã giao dịch không rỗng.
- Thực phẩm còn <= 3 ngày hết hạn giảm 30%.
- Phiếu bảo hành chỉ tạo cho hóa đơn **đã thanh toán**, sản phẩm có trong hóa đơn và có `IBaoHanh`; hạn bảo hành tính theo **tháng**.
