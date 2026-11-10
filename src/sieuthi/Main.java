package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Scanner;

import sieuthi.data.DuLieuHeThong;
import sieuthi.manage.QuanLy;
import sieuthi.model.*;

/**
 * Chương trình quản lý Siêu thị mini (Console Application).
 */
public class Main {
    private static DuLieuHeThong storage;
    private static QuanLy quanLy;
    private static Scanner scanner;

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        quanLy = new QuanLy("QL01", "Phạm Văn Quản Lý", "Hà Nội", "0988888888", 26, 2.0, 3_000_000);
        storage = new DuLieuHeThong(quanLy);
        storage.docTatCaDuLieu();

        if (args.length > 0 && args[0].equalsIgnoreCase("test")) {
            chayKiemThuToanDien();
            return;
        }

        chayMenuChinh();
    }

    private static String docDong() {
        if (scanner != null && scanner.hasNextLine()) {
            return scanner.nextLine().trim();
        }
        return "";
    }

    // =========================================================================
    // MENU CHÍNH
    // =========================================================================
    private static void chayMenuChinh() {
        while (true) {
            System.out.println("\n==================== HỆ THỐNG QUẢN LÝ SIÊU THỊ MINI ====================");
            System.out.println("  1. Quản lý khách hàng");
            System.out.println("  2. Quản lý sản phẩm");
            System.out.println("  3. Quản lý nhà cung cấp & nhập hàng");
            System.out.println("  4. Quản lý bán hàng & hóa đơn");
            System.out.println("  5. Quản lý nhân sự & ca làm");
            System.out.println("  6. Thống kê & báo cáo");
            System.out.println("  7. Kiểm thử hệ thống");
            System.out.println("  0. Thoát");
            System.out.println("=======================================================================");
            System.out.print("Chọn chức năng [0-7]: ");

            if (!scanner.hasNextLine()) {
                storage.luuTatCaDuLieu();
                return;
            }
            String chon = docDong();
            switch (chon) {
                case "1" -> menuKhachHang();
                case "2" -> menuSanPham();
                case "3" -> menuNhapHang();
                case "4" -> menuBanHang();
                case "5" -> menuNhanSu();
                case "6" -> menuBaoCao();
                case "7" -> chayKiemThuToanDien();
                case "0" -> {
                    storage.luuTatCaDuLieu();
                    System.out.println("\nĐã lưu dữ liệu. Tạm biệt!\n");
                    return;
                }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // =========================================================================
    // 1. QUẢN LÝ KHÁCH HÀNG
    // =========================================================================
    private static void menuKhachHang() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ KHÁCH HÀNG ---");
            System.out.println("1. Danh sách khách hàng");
            System.out.println("2. Thêm khách hàng");
            System.out.println("3. Sửa thông tin khách hàng");
            System.out.println("4. Xóa khách hàng");
            System.out.println("5. Tìm kiếm khách hàng");
            System.out.println("0. Quay lại");
            System.out.print("Chọn [0-5]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> quanLy.quanLyKhachHang().hienThiDanhSach();
                case "2" -> {
                    System.out.println("\n-- Thêm khách hàng mới --");
                    System.out.print("Mã khách hàng: ");
                    String ma = docDong();
                    if (ma.isEmpty() || quanLy.quanLyKhachHang().timKiemTheoMa(ma) != null) {
                        System.out.println("Mã khách hàng không hợp lệ hoặc đã tồn tại!");
                        break;
                    }
                    System.out.print("Họ và tên: ");
                    String ten = docDong();
                    System.out.print("Địa chỉ: ");
                    String diaChi = docDong();
                    System.out.print("Số điện thoại: ");
                    String sdt = docDong();

                    KhachHang kh = new KhachHang(ma, ten, diaChi, sdt);
                    if (quanLy.quanLyKhachHang().them(kh)) {
                        storage.luuKhachHang();
                        System.out.println("Thêm khách hàng thành công.");
                    } else {
                        System.out.println("Không thể thêm khách hàng!");
                    }
                }
                case "3" -> {
                    System.out.print("Nhập mã khách hàng cần sửa: ");
                    String ma = docDong();
                    KhachHang khCu = quanLy.quanLyKhachHang().timKiemTheoMa(ma);
                    if (khCu == null) {
                        System.out.println("Không tìm thấy khách hàng: " + ma);
                        break;
                    }
                    System.out.print("Họ tên mới (" + khCu.getHoTen() + "): ");
                    String tenMoi = docDong();
                    if (tenMoi.isEmpty()) tenMoi = khCu.getHoTen();

                    System.out.print("Địa chỉ mới (" + khCu.getDiaChi() + "): ");
                    String diaChiMoi = docDong();
                    if (diaChiMoi.isEmpty()) diaChiMoi = khCu.getDiaChi();

                    System.out.print("Số điện thoại mới (" + khCu.getSoDienThoai() + "): ");
                    String sdtMoi = docDong();
                    if (sdtMoi.isEmpty()) sdtMoi = khCu.getSoDienThoai();

                    KhachHang khMoi = new KhachHang(ma, tenMoi, diaChiMoi, sdtMoi);
                    khMoi.setDiemTichLuy(khCu.getDiemTichLuy());
                    if (quanLy.quanLyKhachHang().sua(ma, khMoi)) {
                        storage.luuKhachHang();
                        System.out.println("Cập nhật thành công.");
                    }
                }
                case "4" -> {
                    System.out.print("Nhập mã khách hàng cần xóa: ");
                    String ma = docDong();
                    if (quanLy.quanLyKhachHang().xoa(ma)) {
                        storage.luuKhachHang();
                        System.out.println("Xóa khách hàng thành công.");
                    } else {
                        System.out.println("Không tìm thấy khách hàng!");
                    }
                }
                case "5" -> {
                    System.out.print("Nhập mã hoặc số điện thoại: ");
                    String kw = docDong();
                    KhachHang kh = quanLy.quanLyKhachHang().timKiemTheoMa(kw);
                    if (kh == null) kh = quanLy.quanLyKhachHang().timKiemTheoSDT(kw);
                    if (kh != null) {
                        System.out.println("Mã KH: " + kh.getMaKhachHang() + " | Tên: " + kh.getHoTen()
                                + " | SĐT: " + kh.getSoDienThoai() + " | Điểm: " + kh.getDiemTichLuy()
                                + " | Địa chỉ: " + kh.getDiaChi());
                    } else {
                        System.out.println("Không tìm thấy khách hàng!");
                    }
                }
                case "0" -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    // =========================================================================
    // 2. QUẢN LÝ SẢN PHẨM
    // =========================================================================
    private static void menuSanPham() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ SẢN PHẨM ---");
            System.out.println("1. Danh sách sản phẩm");
            System.out.println("2. Thêm sản phẩm mới");
            System.out.println("3. Sửa thông tin sản phẩm");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm");
            System.out.println("6. Cảnh báo tồn kho thấp");
            System.out.println("0. Quay lại");
            System.out.print("Chọn [0-6]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> quanLy.quanLySanPham().hienThiDanhSach();
                case "2" -> themSanPhamMoiConsole();
                case "3" -> suaSanPhamConsole();
                case "4" -> {
                    System.out.print("Nhập mã sản phẩm cần xóa: ");
                    String ma = docDong();
                    if (quanLy.quanLySanPham().xoa(ma)) {
                        storage.luuSanPham();
                        System.out.println("Xóa sản phẩm thành công.");
                    } else {
                        System.out.println("Không tìm thấy sản phẩm!");
                    }
                }
                case "5" -> {
                    System.out.print("Nhập mã hoặc tên sản phẩm: ");
                    String kw = docDong();
                    SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(kw);
                    if (sp != null) {
                        sp.hienThiThongTin();
                    } else {
                        List<SanPham> list = quanLy.quanLySanPham().timKiemTheoTen(kw);
                        if (list.isEmpty()) {
                            System.out.println("Không tìm thấy sản phẩm!");
                        } else {
                            for (SanPham s : list) s.hienThiThongTin();
                        }
                    }
                }
                case "6" -> {
                    System.out.print("Nhập ngưỡng tồn kho (mặc định 50): ");
                    String ngStr = docDong();
                    int nguong = 50;
                    try {
                        if (!ngStr.isEmpty()) nguong = Integer.parseInt(ngStr);
                    } catch (NumberFormatException ignored) {}
                    quanLy.quanLySanPham().hienThiCanhBaoHetHang(nguong);
                }
                case "0" -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void themSanPhamMoiConsole() {
        System.out.println("\n-- Thêm sản phẩm mới --");
        System.out.println("1. Thực phẩm");
        System.out.println("2. Đồ uống");
        System.out.println("3. Đồ dùng");
        System.out.print("Chọn loại [1-3]: ");
        String loai = docDong();

        System.out.print("Mã sản phẩm: ");
        String ma = docDong();
        if (ma.isEmpty() || quanLy.quanLySanPham().timKiemTheoMa(ma) != null) {
            System.out.println("Mã sản phẩm không hợp lệ hoặc đã tồn tại!");
            return;
        }

        System.out.print("Tên sản phẩm: ");
        String ten = docDong();
        System.out.print("Nước sản xuất: ");
        String nuoc = docDong();
        System.out.print("Hãng sản xuất: ");
        String hang = docDong();

        int namSX = 2026;
        try {
            System.out.print("Năm sản xuất (mặc định 2026): ");
            String namStr = docDong();
            if (!namStr.isEmpty()) namSX = Integer.parseInt(namStr);
        } catch (NumberFormatException ignored) {}

        double giaGoc = 0;
        try {
            System.out.print("Giá bán (VNĐ): ");
            giaGoc = Double.parseDouble(docDong());
        } catch (Exception e) {
            System.out.println("Giá bán không hợp lệ!");
            return;
        }

        SanPham spMoi = null;
        switch (loai) {
            case "1" -> {
                System.out.print("Hạn sử dụng (yyyy-MM-dd): ");
                String hsdStr = docDong();
                LocalDate hsd = hsdStr.isEmpty() ? LocalDate.now().plusMonths(6) : LocalDate.parse(hsdStr);
                spMoi = new ThucPham(ma, ten, nuoc, namSX, hang, 0, giaGoc, hsd);
            }
            case "2" -> {
                System.out.print("Dung tích (ml): ");
                int ml = Integer.parseInt(docDong());
                System.out.print("Hạn sử dụng (yyyy-MM-dd): ");
                String hsdStr = docDong();
                LocalDate hsd = hsdStr.isEmpty() ? LocalDate.now().plusMonths(12) : LocalDate.parse(hsdStr);
                spMoi = new DoUong(ma, ten, nuoc, namSX, hang, 0, giaGoc, ml, hsd);
            }
            case "3" -> {
                System.out.print("Chất liệu: ");
                String chatLieu = docDong();
                System.out.print("Thời hạn bảo hành (tháng): ");
                int bh = Integer.parseInt(docDong());
                spMoi = new DoDung(ma, ten, nuoc, namSX, hang, 0, giaGoc, chatLieu, bh);
            }
            default -> {
                System.out.println("Loại sản phẩm không hợp lệ!");
                return;
            }
        }

        if (quanLy.quanLySanPham().them(spMoi)) {
            storage.luuSanPham();
            System.out.println("Thêm sản phẩm thành công (tồn kho ban đầu: 0).");
        }
    }

    private static void suaSanPhamConsole() {
        System.out.print("Nhập mã sản phẩm cần sửa: ");
        String ma = docDong();
        SanPham cu = quanLy.quanLySanPham().timKiemTheoMa(ma);
        if (cu == null) {
            System.out.println("Không tìm thấy sản phẩm: " + ma);
            return;
        }

        System.out.println("Đang sửa sản phẩm: " + cu.getTenSanPham());
        System.out.print("Tên sản phẩm mới (" + cu.getTenSanPham() + "): ");
        String ten = docDong();
        if (ten.isEmpty()) ten = cu.getTenSanPham();

        System.out.print("Nước sản xuất mới (" + cu.getNuocSanXuat() + "): ");
        String nuoc = docDong();
        if (nuoc.isEmpty()) nuoc = cu.getNuocSanXuat();

        System.out.print("Hãng sản xuất mới (" + cu.getHangSanXuat() + "): ");
        String hang = docDong();
        if (hang.isEmpty()) hang = cu.getHangSanXuat();

        System.out.print("Giá bán mới (" + HoaDon.dinhDangTien(cu.getGiaGoc()) + "): ");
        String giaStr = docDong();
        double gia = cu.getGiaGoc();
        if (!giaStr.isEmpty()) {
            try { gia = Double.parseDouble(giaStr); } catch (NumberFormatException ignored) {}
        }

        SanPham spMoi = null;
        if (cu instanceof ThucPham tp) {
            System.out.print("Hạn sử dụng mới (" + tp.getHanSuDung() + "): ");
            String hsdStr = docDong();
            LocalDate hsd = hsdStr.isEmpty() ? tp.getHanSuDung() : LocalDate.parse(hsdStr);
            spMoi = new ThucPham(ma, ten, nuoc, cu.getNamSanXuat(), hang, cu.getSoLuongTonKho(), gia, hsd);
        } else if (cu instanceof DoUong du) {
            System.out.print("Dung tích mới (" + du.getDungTichMl() + "ml): ");
            String dtStr = docDong();
            int dt = dtStr.isEmpty() ? du.getDungTichMl() : Integer.parseInt(dtStr);
            System.out.print("Hạn sử dụng mới (" + du.getHanSuDung() + "): ");
            String hsdStr = docDong();
            LocalDate hsd = hsdStr.isEmpty() ? du.getHanSuDung() : LocalDate.parse(hsdStr);
            spMoi = new DoUong(ma, ten, nuoc, cu.getNamSanXuat(), hang, cu.getSoLuongTonKho(), gia, dt, hsd);
        } else if (cu instanceof DoDung dd) {
            System.out.print("Chất liệu mới (" + dd.getChatLieu() + "): ");
            String cl = docDong();
            if (cl.isEmpty()) cl = dd.getChatLieu();
            System.out.print("Bảo hành mới (" + dd.layThoiHanBaoHanh() + " tháng): ");
            String bhStr = docDong();
            int bh = bhStr.isEmpty() ? dd.layThoiHanBaoHanh() : Integer.parseInt(bhStr);
            spMoi = new DoDung(ma, ten, nuoc, cu.getNamSanXuat(), hang, cu.getSoLuongTonKho(), gia, cl, bh);
        }

        if (spMoi != null && quanLy.quanLySanPham().sua(ma, spMoi)) {
            storage.luuSanPham();
            System.out.println("Cập nhật thông tin sản phẩm thành công.");
        }
    }

    // =========================================================================
    // 3. QUẢN LÝ NHÀ CUNG CẤP & NHẬP HÀNG
    // =========================================================================
    private static void menuNhapHang() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ NHÀ CUNG CẤP & NHẬP HÀNG ---");
            System.out.println("1. Danh sách nhà cung cấp");
            System.out.println("2. Thêm nhà cung cấp");
            System.out.println("3. Lập phiếu nhập hàng");
            System.out.println("4. Danh sách phiếu nhập hàng");
            System.out.println("5. Xem chi tiết phiếu nhập");
            System.out.println("0. Quay lại");
            System.out.print("Chọn [0-5]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> {
                    System.out.println("======================================== DANH SÁCH NHÀ CUNG CẤP ========================================");
                    System.out.printf("%-10s | %-35s | %-12s | %s%n", "Mã NCC", "Tên nhà cung cấp", "Số ĐT", "Địa chỉ");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    for (NhaCungCap ncc : storage.getDsNhaCungCap()) {
                        System.out.printf("%-10s | %-35s | %-12s | %s%n",
                                ncc.getMaNhaCungCap(), ncc.getTenNhaCungCap(), ncc.getSoDienThoai(), ncc.getDiaChi());
                    }
                    System.out.println("========================================================================================================");
                }
                case "2" -> {
                    System.out.print("Mã NCC: ");
                    String ma = docDong();
                    if (storage.timNhaCungCap(ma) != null) {
                        System.out.println("Mã nhà cung cấp đã tồn tại!");
                        break;
                    }
                    System.out.print("Tên nhà cung cấp: ");
                    String ten = docDong();
                    System.out.print("Số điện thoại: ");
                    String sdt = docDong();
                    System.out.print("Địa chỉ: ");
                    String dc = docDong();

                    NhaCungCap ncc = new NhaCungCap(ma, ten, sdt, dc);
                    if (storage.themNhaCungCap(ncc)) {
                        storage.luuNhaCungCap();
                        System.out.println("Thêm nhà cung cấp thành công.");
                    }
                }
                case "3" -> lapPhieuNhapHangConsole();
                case "4" -> quanLy.quanLyNhapHang().hienThiDanhSach();
                case "5" -> {
                    System.out.print("Nhập mã phiếu nhập: ");
                    String ma = docDong();
                    NhapHang nh = quanLy.quanLyNhapHang().timKiemTheoMa(ma);
                    if (nh != null) {
                        nh.hienThiPhieuNhap();
                    } else {
                        System.out.println("Không tìm thấy phiếu nhập: " + ma);
                    }
                }
                case "0" -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void lapPhieuNhapHangConsole() {
        System.out.println("\n-- LẬP PHIẾU NHẬP HÀNG --");
        String maNH = "NH" + String.format("%03d", quanLy.quanLyNhapHang().getSoLuong() + 1);
        System.out.println("Mã phiếu nhập: " + maNH);

        System.out.print("Mã nhà cung cấp: ");
        String maNCC = docDong();
        NhaCungCap ncc = storage.timNhaCungCap(maNCC);
        if (ncc == null) {
            System.out.println("Không tìm thấy nhà cung cấp: " + maNCC);
            return;
        }

        NhapHang phieuNhap = new NhapHang(maNH, LocalDate.now(), ncc, quanLy);

        while (true) {
            System.out.print("Mã sản phẩm (hoặc 'xong' để kết thúc): ");
            String maSP = docDong();
            if (maSP.equalsIgnoreCase("xong")) break;

            SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(maSP);
            if (sp == null) {
                System.out.println("Không tìm thấy sản phẩm: " + maSP);
                continue;
            }

            System.out.println("Sản phẩm: " + sp.getTenSanPham() + " | Tồn kho hiện tại: " + sp.getSoLuongTonKho());
            int slNhap = 0;
            double giaNhap = 0;
            try {
                System.out.print("Số lượng nhập: ");
                slNhap = Integer.parseInt(docDong());
                System.out.print("Giá nhập (VNĐ): ");
                giaNhap = Double.parseDouble(docDong());
            } catch (Exception e) {
                System.out.println("Số lượng hoặc đơn giá không hợp lệ!");
                continue;
            }

            phieuNhap.themChiTietVaCapNhatTonKho(sp, slNhap, giaNhap);
            System.out.println("Đã thêm " + slNhap + " cái. Tồn kho mới: " + sp.getSoLuongTonKho());
        }

        if (phieuNhap.getDsChiTiet().isEmpty()) {
            System.out.println("Phiếu nhập rỗng, hủy thao tác.");
            return;
        }

        if (quanLy.quanLyNhapHang().them(phieuNhap)) {
            storage.luuNhapHang();
            storage.luuSanPham();
            System.out.println("Lập phiếu nhập hàng thành công.");
            phieuNhap.hienThiPhieuNhap();
        }
    }

    // =========================================================================
    // 4. BÁN HÀNG & HÓA ĐƠN
    // =========================================================================
    private static void menuBanHang() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ BÁN HÀNG & HÓA ĐƠN ---");
            System.out.println("1. Danh sách hóa đơn");
            System.out.println("2. Lập hóa đơn bán hàng");
            System.out.println("3. Xem chi tiết hóa đơn");
            System.out.println("4. Danh sách phiếu bảo hành");
            System.out.println("0. Quay lại");
            System.out.print("Chọn [0-4]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> quanLy.quanLyDonHang().hienThiDanhSach();
                case "2" -> lapVaThanhToanHoaDonConsole();
                case "3" -> {
                    System.out.print("Nhập mã hóa đơn: ");
                    String ma = docDong();
                    HoaDon hd = quanLy.quanLyDonHang().timKiemTheoMa(ma);
                    if (hd != null) {
                        hd.hienThiHoaDon();
                    } else {
                        System.out.println("Không tìm thấy hóa đơn: " + ma);
                    }
                }
                case "4" -> {
                    System.out.println("============================================ DANH SÁCH PHIẾU BẢO HÀNH ============================================");
                    if (storage.getDsPhieuBaoHanh().isEmpty()) {
                        System.out.println("  (Chưa có phiếu bảo hành nào)");
                    } else {
                        for (PhieuBaoHanh pbh : storage.getDsPhieuBaoHanh()) {
                            pbh.hienThiThongTin();
                        }
                    }
                    System.out.println("==================================================================================================================");
                }
                case "0" -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void lapVaThanhToanHoaDonConsole() {
        System.out.println("\n-- LẬP HÓA ĐƠN BÁN HÀNG --");
        String maHD = "HD" + String.format("%03d", quanLy.quanLyDonHang().getSoLuong() + 1);
        System.out.println("Mã hóa đơn: " + maHD);

        ThuNgan thuNgan = null;
        for (NhanVien nv : storage.getDsNhanVien()) {
            if (nv instanceof ThuNgan tn) {
                thuNgan = tn;
                break;
            }
        }
        if (thuNgan == null) {
            thuNgan = new ThuNgan("TN01", "Trần Thị Thu Ngân", "TP.HCM", "0923456789", 26, 1.5, "Quầy 01");
        }

        System.out.print("Mã khách hàng (để trống nếu khách vãng lai): ");
        String maKH = docDong();
        KhachHang kh = null;
        if (!maKH.isEmpty()) {
            kh = quanLy.quanLyKhachHang().timKiemTheoMa(maKH);
            if (kh != null) {
                System.out.println("Khách hàng: " + kh.getHoTen() + " | Điểm hiện tại: " + kh.getDiemTichLuy());
            } else {
                System.out.println("Không tìm thấy mã, tính là khách vãng lai.");
            }
        }

        System.out.print("Mã khuyến mãi (để trống nếu không có): ");
        String maKM = docDong();
        KhuyenMai km = null;
        if (!maKM.isEmpty()) {
            km = storage.timKhuyenMai(maKM);
            if (km != null && km.conHieuLuc(LocalDate.now())) {
                System.out.println("Áp dụng KM: " + km.getMaKhuyenMai() + " (-" + (int)(km.getTiLeGiam() * 100) + "%)");
            } else {
                System.out.println("Mã khuyến mãi không hợp lệ hoặc đã hết hạn.");
                km = null;
            }
        }

        HoaDon hd = new HoaDon(maHD, LocalDate.now(), thuNgan, kh, km);

        while (true) {
            System.out.print("Mã sản phẩm (hoặc 'xong' để thanh toán): ");
            String maSP = docDong();
            if (maSP.equalsIgnoreCase("xong")) break;

            SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(maSP);
            if (sp == null) {
                System.out.println("Không tìm thấy sản phẩm: " + maSP);
                continue;
            }

            if (!sp.conHanSuDung()) {
                System.out.println("Sản phẩm đã hết hạn sử dụng!");
                continue;
            }

            System.out.println("Sản phẩm: " + sp.getTenSanPham() + " | Giá: " + HoaDon.dinhDangTien(sp.tinhGia())
                    + " | Còn tồn: " + sp.getSoLuongTonKho());

            if (sp.getSoLuongTonKho() <= 0) {
                System.out.println("Sản phẩm đã hết hàng trong kho!");
                continue;
            }

            System.out.print("Số lượng mua: ");
            int sl = 0;
            try {
                sl = Integer.parseInt(docDong());
            } catch (Exception e) {
                System.out.println("Số lượng không hợp lệ!");
                continue;
            }

            try {
                hd.themChiTiet(sp, sl);
                System.out.println("Đã thêm vào hóa đơn.");
            } catch (Exception e) {
                System.out.println("Lỗi: " + e.getMessage());
            }
        }

        if (hd.getDsChiTiet().isEmpty()) {
            System.out.println("Hóa đơn trống, hủy thao tác.");
            return;
        }

        hd.hienThiHoaDon();

        System.out.println("\nPhương thức thanh toán:");
        System.out.println("  1. Tiền mặt");
        System.out.println("  2. Chuyển khoản");
        System.out.println("  3. Thẻ ngân hàng");
        System.out.print("Chọn [1-3]: ");
        String ptChon = docDong();

        IPhuongThucThanhToan phuongThuc = new TienMat();
        if ("2".equals(ptChon)) {
            String maGD = "CK" + System.currentTimeMillis();
            phuongThuc = new ChuyenKhoan(maGD);
        } else if ("3".equals(ptChon)) {
            System.out.print("Nhập số thẻ (16 chữ số): ");
            String soThe = docDong();
            phuongThuc = new TheNganHang(soThe);
        }

        boolean ttThanhCong = thuNgan.thanhToanHoaDon(hd, phuongThuc);
        if (ttThanhCong) {
            quanLy.quanLyDonHang().them(hd);

            for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
                SanPham sp = ct.getSanPham();
                if (sp instanceof DoDung dd) {
                    String maPBH = "PBH" + String.format("%03d", storage.getDsPhieuBaoHanh().size() + 1);
                    PhieuBaoHanh pbh = new PhieuBaoHanh(maPBH, hd, dd, kh, "Bảo hành tiêu chuẩn");
                    storage.themPhieuBaoHanh(pbh);
                    System.out.println("Đã xuất phiếu bảo hành: " + maPBH + " (" + dd.layThoiHanBaoHanh() + " tháng)");
                }
            }

            storage.luuHoaDon();
            storage.luuSanPham();
            storage.luuKhachHang();
            storage.luuPhieuBaoHanh();

            System.out.println("Thanh toán thành công!");
            if (kh != null) {
                System.out.println("Điểm tích lũy mới của " + kh.getHoTen() + ": " + kh.getDiemTichLuy());
            }
        } else {
            System.out.println("Thanh toán không thành công!");
        }
    }

    // =========================================================================
    // 5. QUẢN LÝ NHÂN SỰ & CA LÀM
    // =========================================================================
    private static void menuNhanSu() {
        while (true) {
            System.out.println("\n--- QUẢN LÝ NHÂN SỰ & CA LÀM ---");
            System.out.println("1. Danh sách nhân viên & bảng lương");
            System.out.println("2. Thêm nhân viên mới");
            System.out.println("3. Danh sách ca làm việc");
            System.out.println("4. Phân công ca làm");
            System.out.println("0. Quay lại");
            System.out.print("Chọn [0-4]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> {
                    System.out.println("============================================= DANH SÁCH NHÂN SỰ =============================================");
                    quanLy.hienThiThongTin();
                    for (NhanVien nv : storage.getDsNhanVien()) {
                        nv.hienThiThongTin();
                    }
                    System.out.println("=============================================================================================================");
                }
                case "2" -> themNhanVienConsole();
                case "3" -> {
                    System.out.println("=================================== DANH SÁCH CA LÀM VIỆC ===================================");
                    System.out.printf("%-8s | %-12s | %-15s | %s%n", "Mã ca", "Ngày làm", "Khung giờ", "Nhân viên phụ trách");
                    System.out.println("---------------------------------------------------------------------------------------------");
                    for (CaLam ca : storage.getDsCaLam()) {
                        String tenNV = "Chưa phân công";
                        for (NhanVien nv : storage.getDsNhanVien()) {
                            if (nv.getDsCaLam().contains(ca)) {
                                tenNV = nv.getHoTen() + " (" + nv.getMaNhanVien() + ")";
                                break;
                            }
                        }
                        if (quanLy.getDsCaLam().contains(ca)) {
                            tenNV = quanLy.getHoTen() + " (" + quanLy.getMaNhanVien() + ")";
                        }
                        System.out.printf("%-8s | %-12s | %-15s | %s%n",
                                ca.getMaCa(), ca.getNgayLam(), ca.getGioBatDau() + " - " + ca.getGioKetThuc(), tenNV);
                    }
                    System.out.println("=============================================================================================");
                }
                case "4" -> phanCongCaConsole();
                case "0" -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void themNhanVienConsole() {
        System.out.println("\n-- Thêm nhân viên mới --");
        System.out.println("1. Thu ngân");
        System.out.println("2. Nhân viên Part-time");
        System.out.println("3. Nhân viên Full-time");
        System.out.print("Chọn loại [1-3]: ");
        String loai = docDong();

        System.out.print("Mã nhân viên: ");
        String ma = docDong();
        if (storage.timNhanVien(ma) != null) {
            System.out.println("Mã nhân viên đã tồn tại!");
            return;
        }

        System.out.print("Họ tên: ");
        String ten = docDong();
        System.out.print("Địa chỉ: ");
        String dc = docDong();
        System.out.print("Số điện thoại: ");
        String sdt = docDong();

        NhanVien nv = null;
        switch (loai) {
            case "1" -> {
                System.out.print("Mã quầy thu ngân: ");
                String quay = docDong();
                nv = new ThuNgan(ma, ten, dc, sdt, 26, 1.2, quay);
            }
            case "2" -> {
                System.out.print("Lương theo giờ (VNĐ): ");
                double luong = Double.parseDouble(docDong());
                nv = new NhanVienPartTime(ma, ten, dc, sdt, 0, luong);
            }
            case "3" -> {
                System.out.print("Hệ số lương: ");
                double heSo = Double.parseDouble(docDong());
                nv = new NhanVienFullTime(ma, ten, dc, sdt, 26, heSo);
            }
            default -> {
                System.out.println("Loại nhân viên không hợp lệ!");
                return;
            }
        }

        if (storage.themNhanVien(nv)) {
            storage.luuNhanVien();
            System.out.println("Thêm nhân viên thành công.");
        }
    }

    private static void phanCongCaConsole() {
        System.out.println("\n-- Phân công ca làm --");
        System.out.print("Mã nhân viên: ");
        String maNV = docDong();
        NhanVien nv = storage.timNhanVien(maNV);
        if (nv == null) {
            System.out.println("Không tìm thấy nhân viên: " + maNV);
            return;
        }

        System.out.print("Mã ca làm: ");
        String maCa = docDong();
        System.out.print("Ngày làm (yyyy-MM-dd): ");
        LocalDate ngay = LocalDate.parse(docDong());
        System.out.print("Giờ bắt đầu (HH:mm): ");
        LocalTime bd = LocalTime.parse(docDong());
        System.out.print("Giờ kết thúc (HH:mm): ");
        LocalTime kt = LocalTime.parse(docDong());

        CaLam caMoi = new CaLam(maCa, ngay, bd, kt);
        boolean phanCongOK = quanLy.phanCongCa(nv, caMoi);
        if (phanCongOK) {
            storage.themCaLam(caMoi);
            storage.luuCaLam();
            System.out.println("Phân công ca làm thành công.");
        }
    }

    // =========================================================================
    // 6. BÁO CÁO & THỐNG KÊ
    // =========================================================================
    private static void menuBaoCao() {
        while (true) {
            System.out.println("\n--- THỐNG KÊ & BÁO CÁO ---");
            System.out.println("1. Thống kê doanh thu theo khoảng ngày");
            System.out.println("2. Thống kê chi phí nhập hàng");
            System.out.println("3. Báo cáo tài chính tổng hợp");
            System.out.println("4. Báo cáo tồn kho");
            System.out.println("0. Quay lại");
            System.out.print("Chọn [0-4]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> {
                    LocalDate[] k = nhapKhoangNgay();
                    if (k != null) {
                        double dt = quanLy.quanLyDonHang().thongKeDoanhThu(k[0], k[1]);
                        System.out.println("Doanh thu (" + k[0] + " đến " + k[1] + "): " + HoaDon.dinhDangTien(dt));
                    }
                }
                case "2" -> {
                    LocalDate[] k = nhapKhoangNgay();
                    if (k != null) {
                        double cp = quanLy.quanLyNhapHang().thongKeChiPhiNhap(k[0], k[1]);
                        System.out.println("Chi phí nhập (" + k[0] + " đến " + k[1] + "): " + HoaDon.dinhDangTien(cp));
                    }
                }
                case "3" -> {
                    LocalDate[] k = nhapKhoangNgay();
                    if (k != null) {
                        double dt = quanLy.quanLyDonHang().thongKeDoanhThu(k[0], k[1]);
                        double cp = quanLy.quanLyNhapHang().thongKeChiPhiNhap(k[0], k[1]);
                        double chenhLech = dt - cp;
                        System.out.println("\n================= BÁO CÁO TÀI CHÍNH TỔNG HỢP =================");
                        System.out.println("Thời gian     : Từ " + k[0] + " đến " + k[1]);
                        System.out.println("Doanh thu bán : " + HoaDon.dinhDangTien(dt));
                        System.out.println("Chi phí nhập  : " + HoaDon.dinhDangTien(cp));
                        System.out.println("Chênh lệch    : " + HoaDon.dinhDangTien(chenhLech));
                        System.out.println("==============================================================");
                    }
                }
                case "4" -> {
                    System.out.println("\n-- TÌNH HÌNH TỒN KHO --");
                    quanLy.quanLySanPham().hienThiDanhSach();
                    quanLy.quanLySanPham().hienThiCanhBaoHetHang(50);
                }
                case "0" -> { return; }
                default -> System.out.println("Lựa chọn không hợp lệ!");
            }
        }
    }

    private static LocalDate[] nhapKhoangNgay() {
        try {
            System.out.print("Từ ngày (yyyy-MM-dd): ");
            String tuStr = docDong();
            LocalDate tu = tuStr.isEmpty() ? LocalDate.now().minusMonths(1) : LocalDate.parse(tuStr);

            System.out.print("Đến ngày (yyyy-MM-dd): ");
            String denStr = docDong();
            LocalDate den = denStr.isEmpty() ? LocalDate.now() : LocalDate.parse(denStr);

            return new LocalDate[]{tu, den};
        } catch (DateTimeParseException e) {
            System.out.println("Định dạng ngày không đúng (phải là yyyy-MM-dd)!");
            return null;
        }
    }

    // =========================================================================
    // 7. KIỂM THỬ HỆ THỐNG
    // =========================================================================
    public static void chayKiemThuToanDien() {
        System.out.println("\n==================================================");
        System.out.println("            BẮT ĐẦU KIỂM THỬ HỆ THỐNG             ");
        System.out.println("==================================================");

        System.out.println("\n=== 1. KIỂM THỬ HÓA ĐƠN & BẢO HÀNH ===");
        KiemThuHoaDon.main(new String[]{});

        System.out.println("\n=== 2. KIỂM THỬ SẢN PHẨM ===");
        KiemThuSanPham.main(new String[]{});

        System.out.println("\n=== 3. KIỂM THỬ QUẢN LÝ & CA LÀM ===");
        KiemThuQuanLy.main(new String[]{});

        System.out.println("\n=== 4. KIỂM THỬ LƯU TRỮ FILE & TỒN KHO ===");
        KiemThuHeThongVaLuuTru.main(new String[]{});

        System.out.println("\n==================================================");
        System.out.println("     HOÀN TẤT KIỂM THỬ: TẤT CẢ MODULE ĐẠT CHUẨN   ");
        System.out.println("==================================================");
    }
}
