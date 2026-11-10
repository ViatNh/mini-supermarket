# HỆ THỐNG QUẢN LÝ SIÊU THỊ MINI (MINI-SUPERMARKET)
## TÀI LIỆU HƯỚNG DẪN KIẾN TRÚC, TỔNG HỢP LỖI & CẢI TIẾN NÂNG CAO KỸ NĂNG

Tài liệu này được biên soạn dành cho toàn bộ các thành viên trong nhóm đồ án Java:
- **Nhàn**: Vẽ sơ đồ thiết kế dự án (Draw.io)
- **Trang**: Thiết kế & xử lý nghiệp vụ Hóa đơn, Thanh toán, Bảo hành
- **Hoàng**: Thiết kế & xử lý phân hệ Con người / Nhân sự (Person)
- **Vỹ**: Thiết kế & xử lý phân hệ Sản phẩm & Đa hình
- **Vinh**: Thiết kế & xử lý phân hệ Quản lý (Manage) & Điều phối hệ thống

---

## I. TỔNG QUAN KIẾN TRÚC HỆ THỐNG SAU KHI CHUẨN HÓA

### 1. Cấu trúc thư mục chuẩn Java
Trước đây, các nhánh có mã nguồn nằm rải rác ở thư mục gốc (`src/DoDung.java`, `Person/Person.java`, `manage/*.java`), dẫn đến lỗi classpath trên VS Code và khó khăn khi biên dịch.
Hệ thống hiện tại đã được chuẩn hóa thống nhất:

```
mini-supermarket/
├── .vscode/                 # Cấu hình IDE (classpath: src, encoding: UTF-8)
├── bin/                     # Thư mục chứa bytecode (.class) sau khi biên dịch
├── data/                    # Thư mục lưu trữ file vĩnh viễn (Text files - UTF-8)
│   ├── calam.txt            # Dữ liệu ca làm việc (5+ bản ghi)
│   ├── hoadon.txt           # Dữ liệu hóa đơn bán hàng (5+ bản ghi)
│   ├── khachhang.txt        # Dữ liệu khách hàng & điểm tích lũy (5+ bản ghi)
│   ├── khuyenmai.txt        # Dữ liệu chương trình khuyến mãi (5+ bản ghi)
│   ├── nhacungcap.txt       # Dữ liệu nhà cung cấp (5+ bản ghi)
│   ├── nhanvien.txt         # Dữ liệu nhân sự các loại (5+ bản ghi)
│   ├── nhaphang.txt         # Dữ liệu phiếu nhập kho (5+ bản ghi)
│   ├── phieubaohanh.txt     # Dữ liệu phiếu bảo hành đồ dùng (5+ bản ghi)
│   └── sanpham.txt          # Dữ liệu sản phẩm & tồn kho thực tế (5+ bản ghi)
├── docs/                    # Tài liệu dự án & Sơ đồ UML
│   ├── Class_Structure.drawio
│   ├── SieuThiMini.drawio
│   └── HUONG_DAN_VA_CAI_TIEN.md
├── src/
│   └── sieuthi/
│       ├── Main.java        # Console Application CLI (Menu tương tác & Test)
│       ├── KiemThuHoaDon.java
│       ├── KiemThuSanPham.java
│       ├── KiemThuQuanLy.java
│       ├── KiemThuHeThongVaLuuTru.java
│       ├── data/            # Tầng truy xuất và lưu trữ file
│       │   └── DuLieuHeThong.java
│       ├── manage/          # Phân hệ quản lý (CRUD & Nghiệp vụ điều phối)
│       │   ├── IQuanLy.java
│       │   ├── QuanLy.java
│       │   ├── QuanLyKhachHang.java
│       │   ├── QuanLySanPham.java
│       │   ├── QuanLyDonHang.java
│       │   └── QuanLyNhapHang.java
│       └── model/           # Các thực thể dữ liệu nghiệp vụ (Domain Entities)
│           ├── Person.java
│           ├── KhachHang.java
│           ├── NhanVien.java
│           ├── NhanVienFullTime.java
│           ├── NhanVienPartTime.java
│           ├── ThuNgan.java
│           ├── CaLam.java
│           ├── SanPham.java
│           ├── ThucPham.java
│           ├── DoUong.java
│           ├── DoDung.java
│           ├── IBaoHanh.java
│           ├── NhaCungCap.java
│           ├── KhoHang.java
│           ├── ChiTietNhapHang.java
│           ├── NhapHang.java
│           ├── ChiTietHoaDon.java
│           ├── HoaDon.java
│           ├── IApDungKhuyenMai.java
│           ├── KhuyenMai.java
│           ├── IPhuongThucThanhToan.java
│           ├── TienMat.java
│           ├── ChuyenKhoan.java
│           ├── TheNganHang.java
│           └── PhieuBaoHanh.java
├── run.bat                  # Script biên dịch & chạy trên Windows
└── run.sh                   # Script biên dịch & chạy trên Linux/macOS
```

### 2. Các nguyên tắc cốt lõi đã được áp dụng
1. **100% Console Application (CLI)**: Không sử dụng bất kỳ thư viện GUI nào (AWT/Swing/JavaFX) theo đúng yêu cầu đề bài. Giao diện console tương tác đẹp mắt, hỗ trợ menu số, định dạng bảng rõ ràng và xử lý ngoại lệ an toàn.
2. **Lưu trữ file bền vững (File Persistence)**: Thay vì lưu tạm trên RAM, toàn bộ 9 thực thể đều được lưu vào các file `.txt` trong `data/` với phân cách `|`. Dữ liệu được tải tự động khi mở ứng dụng và ghi lại ngay khi có giao dịch hoặc trước khi thoát.
3. **Quy tắc bất biến về Quản lý Tồn kho (Stock Invariant)**:
   - Số lượng tồn kho (`soLuongTonKho`) **TUYỆT ĐỐI KHÔNG ĐƯỢC PHÉP** sửa thủ công qua chức năng sửa sản phẩm hoặc nhập tùy tiện khi tạo sản phẩm mới.
   - Tồn kho chỉ được phép **TĂNG** thông qua **Nhập Hàng** từ Nhà cung cấp (`NhapHang`).
   - Tồn kho chỉ được phép **GIẢM** thông qua **Bán Hàng** khi Hóa đơn được thanh toán thành công (`HoaDon.hoanTatThanhToan`).

---

## II. CHI TIẾT LỖI, GIẢI PHÁP & BÀI HỌC KINH NGHIỆM CHO TỪNG THÀNH VIÊN

---

### 1. DÀNH CHO BẠN NHÀN (Vẽ sơ đồ dự án Draw.io)

#### A. Điểm sáng trong thiết kế ban đầu
- Sơ đồ `Class_Structure.drawio` được Nhàn thiết kế rất bài bản, bao quát hầu hết các nghiệp vụ thực tế của một siêu thị mini.
- Đặc biệt, Nhàn đã dự tính trước các lớp quản lý chuỗi cung ứng: `NhaCungCap`, `NhapHang`, `ChiTietNhapHang`, `KhoHang`.

#### B. Vấn đề phát sinh khi các thành viên khác triển khai code
- Trong phiên bản code ban đầu, các bạn khác chưa cài đặt 4 lớp trên của Nhàn, dẫn đến việc sản phẩm có tồn kho mà không rõ nguồn gốc nhập từ đâu và vi phạm yêu cầu: *"Số lượng (stock) chỉ được phép dựa vào nhập hàng và xuất hàng chứ không được phép sửa thủ công"*.
- Mối quan hệ giữa `NhapHang` và `ChiTietNhapHang` trong sơ đồ là quan hệ Composition (hình thoi đen): Chi tiết nhập hàng gắn liền với phiếu nhập, điều này rất chính xác về mặt nghiệp vụ.

#### C. Cải tiến đã thực hiện
- Đã cài đặt hoàn chỉnh 4 lớp theo đúng sơ đồ của Nhàn:
  + `NhaCungCap`: Đại diện cho đối tác cung ứng, có phương thức `cungCapSanPham()`.
  + `ChiTietNhapHang`: Lưu `SanPham`, `soLuong`, `donGiaNhap` và phương thức `tinhThanhTien()`.
  + `NhapHang`: Ghi nhận giao dịch nhập hàng, kết nối với `NhaCungCap`, `NhanVien` lập phiếu và danh sách `ChiTietNhapHang`. Cung cấp phương thức `themChiTietVaCapNhatTonKho()` để trực tiếp gia tăng tồn kho hợp lệ.
  + `KhoHang`: Quản lý tập trung biến động xuất/nhập tồn kho.

#### D. Bài học kinh nghiệm cho Nhàn
1. **Tính nhất quán giữa Thiết kế và Hiện thực (Architecture Conformance)**: Khi vẽ UML xong, cần họp nhóm để giải thích rõ vai trò của từng lớp cho các thành viên phụ trách code tương ứng, tránh việc thiết kế có lớp nhưng lập trình viên lại bỏ qua.
2. **Ký hiệu quan hệ**:
   - `Composition` (thoi đen): `HoaDon` -> `ChiTietHoaDon`, `NhapHang` -> `ChiTietNhapHang`. Khi hóa đơn bị xóa thì các dòng chi tiết đi kèm cũng mất.
   - `Aggregation` (thoi trắng): `KhoHang` -> `SanPham`. Sản phẩm vẫn tồn tại trong danh mục dù kho có thể được tái cấu trúc.
   - `Association` (mũi tên thường): `NhapHang` -> `NhaCungCap`.

---

### 2. DÀNH CHO BẠN TRANG (Phân hệ Hóa đơn & Bán hàng)

#### A. Điểm sáng trong code của Trang
- Đã tách riêng `HoaDon`, `ChiTietHoaDon`, `KhuyenMai`, `PhieuBaoHanh`.
- Áp dụng Interface `IPhuongThucThanhToan` đa hình với các hình thức `TienMat`, `ChuyenKhoan`, `TheNganHang`.

#### B. Các lỗi logic & rủi ro phát hiện trong code ban đầu
1. **Lỗi trừ tồn kho quá sớm**: Nếu trừ tồn kho ngay khi người dùng chọn sản phẩm vào hóa đơn, nhưng sau đó thanh toán thất bại (thẻ sai số, chuyển khoản lỗi) thì tồn kho bị mất mà không bán được hàng.
2. **Lỗi cộng dồn & kiểm tra vượt tồn kho**: Khi khách hàng mua cùng 1 sản phẩm nhiều lần (ví dụ quét mã vạch 2 lần), nếu không cộng dồn thì danh sách bị phân mảnh; nếu không kiểm tra tổng số lượng đã chọn với `getSoLuongTonKho()` thì khách có thể mua vượt quá số lượng siêu thị đang có.
3. **Lỗi NullPointerException với Khuyến mãi**: Khi khách hàng không áp dụng khuyến mãi (`khuyenMai == null`), phương thức tính tiền giảm gọi trực tiếp `khuyenMai.conHieuLuc()` sẽ làm văng ứng dụng ngay lập tức.
4. **Lỗi lặp trừ tồn kho khi đọc file**: Khi chương trình khởi động và đọc lại lịch sử hóa đơn từ file `hoadon.txt`, nếu dùng lại phương thức thanh toán của Thu ngân thì kho sẽ bị trừ thêm một lần nữa!

#### C. Cải tiến đã thực hiện cho Trang
1. **Thanh toán nguyên tử (Atomic Payment)**: Chỉ khi `phuongThuc.thanhToan()` trả về `true`, `hd.hoanTatThanhToan(thuNgan, phuongThuc)` mới được kích hoạt để trừ tồn kho và tích điểm cho khách.
2. **Phân tách nghiệp vụ Lập hóa đơn mới và Đọc file**:
   - `themChiTiet(sp, soLuong)`: Dành cho giao dịch trực tiếp (kiểm tra tồn kho, kiểm tra hạn dùng, chống vượt tồn kho).
   - `themChiTietLuuTru(sp, soLuong, donGia)`: Dành cho khôi phục hóa đơn cũ từ file text (giữ nguyên đơn giá lịch sử, không trừ kho lặp lại).
3. **Bảo vệ Khuyến mãi an toàn**:
   ```java
   if (khuyenMai == null || !khuyenMai.conHieuLuc(ngayLap)) return 0;
   ```
4. **Liên kết bảo hành tự động**: Khi thanh toán hóa đơn thành công, nếu trong giỏ có sản phẩm implements `IBaoHanh` (ví dụ `DoDung`), hệ thống tự động sinh `PhieuBaoHanh` với thời hạn tính bằng tháng và lưu vào file `phieubaohanh.txt`.

#### D. Bài học kinh nghiệm cho Trang
1. **State Pattern & Immutable Fields**: Trạng thái hóa đơn sau khi đã thanh toán (`daThanhToan == true`) cần được khóa lại để không thể thêm/sửa/xóa sản phẩm bên trong.
2. **Tách biệt người lập và người thanh toán**: Nhân viên bán hàng tạo đơn (người lập), nhưng chỉ có Thu ngân mới có thẩm quyền thu tiền và xác nhận hoàn tất giao dịch.

---

### 3. DÀNH CHO BẠN HOÀNG (Phân hệ Person - Nhân sự)

#### A. Điểm sáng trong code của Hoàng
- Xây dựng đầy đủ các vai trò nhân sự: `KhachHang`, `NhanVienFullTime`, `NhanVienPartTime`, `ThuNgan`, `QuanLy`.
- Thiết kế lớp `CaLam` với kiểm tra trùng ca.

#### B. Các lỗi & vấn đề phát sinh trong code ban đầu
1. **Vi phạm nguyên tắc Single Responsibility (SRP) - Code dồn trong 1 file**:
   Hoàng đặt toàn bộ các lớp `Person`, `KhachHang`, `NhanVien`, `ThuNgan`, `CaLam`, `main` vào duy nhất file `Person/Person.java`. Khi làm việc nhóm qua Git, bất kỳ ai sửa logic liên quan đều gây ra Merge Conflict nặng. Hoàng đã đồng ý chia tách file thành các module độc lập.
2. **Lỗi Duplicate Constructor Signatures**:
   Trong `NhanVien`, `ThuNgan` có nhiều constructor cùng nhận 4 tham số kiểu `String` (`(ma, hoTen, diaChi, sdt)` và `(hoTen, diaChi, sdt, ma)`), gây lỗi biên dịch:
   `error: constructor NhanVien(String,String,String,String) is already defined`.
3. **Lỗi kiểm tra trùng ca làm việc**:
   Ban đầu chỉ so sánh chuỗi hoặc kiểm tra chưa chặt chẽ. Hai ca làm việc có thể trùng nhau nếu khoảng thời gian `[gioBatDau, gioKetThuc]` của chúng giao nhau trong cùng một ngày.

#### C. Cải tiến đã thực hiện cho Hoàng
1. **Module hóa từng lớp vào `src/sieuthi/model/`**:
   Mỗi lớp có file `.java` riêng biệt: `Person.java`, `KhachHang.java`, `NhanVien.java`, `NhanVienFullTime.java`, `NhanVienPartTime.java`, `ThuNgan.java`, `CaLam.java`.
2. **Chuẩn hóa chữ ký Constructor**:
   Thống nhất thứ tự chuẩn: `(maNhanVien, hoTen, diaChi, soDienThoai)`.
3. **Thuật toán kiểm tra trùng ca làm chuẩn xác**:
   ```java
   public boolean kiemTraTrungCa(CaLam caKhac) {
       if (caKhac == null) return false;
       if (!this.ngayLam.equals(caKhac.ngayLam)) return false; // Khác ngày thì không trùng
       // Hai khoảng thời gian giao nhau khi: bắt đầu của ca này < kết thúc ca kia VÀ kết thúc ca này > bắt đầu ca kia
       return this.gioBatDau.isBefore(caKhac.gioKetThuc) && this.gioKetThuc.isAfter(caKhac.gioBatDau);
   }
   ```
4. **Chuyển `NhanVienFullTime` thành concrete class**:
   Cho phép khởi tạo trực tiếp nhân viên toàn thời gian (nhân viên kho, kiểm kê, tạp vụ) mà không bắt buộc phải là Thu ngân hay Quản lý.

#### D. Bài học kinh nghiệm cho Hoàng
1. **Quy tắc "Một file - Một lớp public"**: Đây là quy chuẩn bắt buộc của ngôn ngữ Java để trình biên dịch và JVM có thể định vị class dễ dàng.
2. **Tính đóng gói (Encapsulation)**: Luôn khai báo thuộc tính `private` và cung cấp getter/setter có validate (ví dụ ngày công không âm, hệ số lương không âm).

---

### 4. DÀNH CHO BẠN VỸ (Phân hệ Sản phẩm & Tồn kho)

#### A. Điểm sáng trong code của Vỹ
- Kế thừa đa hình tốt từ `SanPham` thành `ThucPham`, `DoUong`, `DoDung`.
- Ý tưởng sáng tạo: Thực phẩm cận hạn sử dụng (còn <= 3 ngày) thì tự động giảm giá 30%.

#### B. Các lỗi & vấn đề phát sinh trong code ban đầu
1. **Tên thuộc tính không khớp giữa các nhánh**: Nhánh Hóa đơn gọi `getGiaGoc()` hoặc `tinhGia()`, trong khi nhánh Sản phẩm ban đầu đặt là `gia`, gây lỗi `cannot find symbol`.
2. **Chưa có phương thức đa hình tính giá bán**: Ban đầu logic giảm giá cận date bị viết cứng bên ngoài lớp `ThucPham`, làm mất tính đa hình của hướng đối tượng.
3. **CHÍNH SÁCH TỒN KHO BỊ SƠ HỞ**:
   Phương thức khởi tạo hoặc sửa sản phẩm cho phép người dùng truyền trực tiếp `tonKho` mới. Điều này dẫn đến việc nhân viên có thể gian lận bằng cách vào mục "Sửa sản phẩm" và tự gõ tồn kho từ 10 thành 100!

#### C. Cải tiến đã thực hiện cho Vỹ
1. **Thiết kế phương thức trừu tượng `tinhGia()` trong `SanPham`**:
   - `SanPham.tinhGia()`: Trừu tượng.
   - `ThucPham.tinhGia()`: Nếu còn từ 0 đến 3 ngày là hết hạn thì giá = `giaGoc * 0.7`, ngược lại giữ nguyên `giaGoc`.
   - `DoUong.tinhGia()`: Trả về `giaGoc`.
   - `DoDung.tinhGia()`: Trả về `giaGoc`.
2. **Phương thức kiểm tra hạn sử dụng `conHanSuDung()`**:
   - `SanPham`: Mặc định trả về `true`.
   - `ThucPham` & `DoUong`: Ghi đè so sánh `LocalDate.now().isAfter(hanSuDung)`. Nếu hết hạn, hệ thống bán hàng sẽ từ chối đưa vào hóa đơn.
3. **BẢO VỆ RÀNG BUỘC TỒN KHO**:
   - Khi tạo mới sản phẩm trong hệ thống: Tồn kho mặc định luôn bằng `0`.
   - Khi sửa thông tin sản phẩm trong `QuanLySanPham`:
     ```java
     // Luôn bảo toàn số lượng tồn kho cũ, khóa can thiệp thủ công:
     spMoi.setSoLuongTonKho(current.getSoLuongTonKho());
     ```
   - Tồn kho chỉ thay đổi thông qua 2 hàm duy nhất: `sp.tangTonKho(sl)` (gọi từ NhapHang) và `sp.giamTonKho(sl)` (gọi từ HoaDon).

#### D. Bài học kinh nghiệm cho Vỹ
1. **Nguyên tắc Đa hình (Polymorphism)**: Hãy để đối tượng tự quyết định hành vi của nó (ví dụ Thực phẩm tự biết khi nào nó giảm giá, thay vì để lớp Hóa đơn phải kiểm tra `instanceof ThucPham`).
2. **Quy tắc Nghiệp vụ (Business Invariants)**: Đừng bao giờ tin tưởng dữ liệu đầu vào của người dùng cho các trường nhạy cảm như Tồn kho hay Tiền bạc. Hãy bảo vệ chúng từ tầng mô hình dữ liệu.

---

### 5. DÀNH CHO BẠN VINH (Phân hệ Quản lý & Điều phối)

#### A. Điểm sáng trong code của Vinh
- Đã thiết kế Interface Generic chuẩn `IQuanLy<T>` với các thao tác CRUD cơ bản: `them`, `sua`, `xoa`, `timKiemTheoMa`, `hienThiDanhSach`.
- Triển khai các lớp chuyên trách: `QuanLyKhachHang`, `QuanLySanPham`, `QuanLyDonHang`.
- Lớp `QuanLy` kế thừa `NhanVienFullTime` vừa có vai trò nhân viên nhận lương, vừa làm nhạc trưởng điều phối các quản lý con.

#### B. Các lỗi & vấn đề phát sinh trong code ban đầu
1. **Vị trí thư mục sai quy chuẩn**: Thư mục `manage` nằm ở thư mục gốc của repo thay vì nằm trong `src/sieuthi/manage`. Điều này khiến VS Code báo lỗi `manage is not on classpath` và gây khó khăn cho việc quản lý package.
2. **Chưa có cơ chế lưu trữ file (File Persistence)**: Dữ liệu chỉ nằm trong `ArrayList` trong bộ nhớ RAM. Khi tắt chương trình hoặc xảy ra sự cố, tất cả khách hàng, hóa đơn, sản phẩm mới đều biến mất hoàn toàn.
3. **Thiếu quản lý nhập hàng**: Vinh mới có Quản lý Khách hàng, Sản phẩm, Đơn hàng nhưng chưa có `QuanLyNhapHang` để quản lý giao dịch với Nhà cung cấp.
4. **Lỗi logic trong hàm `sua()` của `QuanLySanPham`**: Trước đây hàm `sua()` dùng `dsSanPham.set(i, spMoi)` mà không giữ lại tồn kho của sản phẩm cũ, dẫn đến mất dữ liệu tồn kho.

#### C. Cải tiến đã thực hiện cho Vinh
1. **Chuẩn hóa package thành `sieuthi.manage`**: Di chuyển toàn bộ các lớp vào `src/sieuthi/manage/` để quy về một mối nguồn `src/`.
2. **Bổ sung `QuanLyNhapHang`**: Thực thi `IQuanLy<NhapHang>`, hỗ trợ thống kê chi phí nhập hàng theo khoảng ngày, lọc theo nhà cung cấp.
3. **Xây dựng Tầng lưu trữ file `DuLieuHeThong`**:
   - Tự động tạo thư mục `data/` và khởi tạo 9 file text mẫu với 5 bản ghi chân thực mỗi file nếu file chưa có.
   - Sử dụng bảng mã UTF-8 và phân cách ký tự `|` rõ ràng, an toàn.
   - Hỗ trợ lưu trữ tức thì sau mỗi thao tác thêm/sửa/xóa/nhập/bán và lưu toàn bộ khi chọn thoát chương trình.
4. **Hoàn thiện giao diện dòng lệnh Console trong `Main.java`**:
   - Menu 7 chức năng lớn và các menu con trực quan.
   - Tích hợp kiểm tra chống nhập liệu sai, phòng chống `NoSuchElementException` khi chạy dạng luồng/pipeline.

#### D. Bài học kinh nghiệm cho Vinh
1. **Tách biệt tầng hiển thị (Presentation / CLI) và tầng nghiệp vụ (Service / Manage)**: Lớp quản lý chỉ nên nhận tham số và trả về kết quả `boolean` hoặc dữ liệu, còn việc nhập/xuất từ bàn phím console nên để lớp giao diện (`Main`) phụ trách.
2. **Bảo toàn dữ liệu (Data Persistence)**: Bất kỳ ứng dụng quản lý thực tế nào cũng cần tầng lưu trữ (File hoặc Database). Việc thiết kế các hàm đọc/ghi tách biệt giúp hệ thống dễ dàng chuyển sang dùng cơ sở dữ liệu (SQL) sau này.

---

## III. HƯỚNG DẪN BIÊN DỊCH VÀ CHẠY CHƯƠNG TRÌNH

### 1. Trên Windows (Sử dụng `run.bat`)
Mở PowerShell hoặc Command Prompt tại thư mục dự án:
```cmd
# Chạy chương trình giao diện tương tác Menu Console:
run.bat

# Chạy toàn bộ 67 bài kiểm thử tự động của cả 4 phân hệ:
run.bat test

# Chạy riêng kiểm thử lưu trữ file và quy tắc tồn kho:
run.bat storage

# Chạy riêng kiểm thử phân hệ quản lý:
run.bat manage

# Chạy riêng kiểm thử phân hệ sản phẩm:
run.bat product
```

### 2. Trên Linux / macOS (Sử dụng `run.sh`)
```bash
chmod +x run.sh

# Chạy tương tác Console:
./run.sh

# Chạy toàn bộ kiểm thử tự động:
./run.sh test
```

---

## IV. BẢNG TỔNG HỢP KIỂM THỬ HỆ THỐNG (TEST SUITE SUMMARY)

Hệ thống đã xây dựng 4 bộ kiểm thử tự động toàn diện với **67/67 kịch bản kiểm thử đạt chuẩn 100%**:

| STT | Phân hệ kiểm thử | Lớp thực thi | Số ca kiểm thử | Kết quả |
|:---:|:---|:---|:---:|:---:|
| 1 | Hóa đơn, Giảm giá, Thanh toán, Bảo hành | `sieuthi.KiemThuHoaDon` | 22 | 22 Đạt - 0 Lỗi |
| 2 | Sản phẩm, Đa hình tính giá, Hạn dùng | `sieuthi.KiemThuSanPham` | 12 | 12 Đạt - 0 Lỗi |
| 3 | CRUD Quản lý, Phân công ca chống trùng | `sieuthi.KiemThuQuanLy` | 16 | 16 Đạt - 0 Lỗi |
| 4 | Lưu trữ 9 File Text & Quy tắc Tồn kho | `sieuthi.KiemThuHeThongVaLuuTru` | 17 | 17 Đạt - 0 Lỗi |
| **Tổng** | **Toàn bộ hệ thống Siêu thị mini** | **Tất cả các lớp** | **67** | **67 Đạt - 0 Lỗi** |

Dự án hiện đã hoàn thiện, chuẩn hóa cấu trúc, đảm bảo tính bảo mật và toàn vẹn của dữ liệu kho hàng, đáp ứng chính xác mọi yêu cầu của giảng viên và đồ án môn học.
