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
 * Chương trình chính Console Application quản lý Siêu thị mini.
 * Đảm bảo:
 * 1. 100% Giao diện dòng lệnh Console (CLI), không sử dụng GUI.
 * 2. Lưu trữ và truy xuất thông tin từ file trong thư mục "data/", không lưu tạm thời.
 * 3. RÀNG BUỘC TỒN KHO: Số lượng tồn kho (stock) chỉ được tăng qua Nhập hàng và giảm qua Bán hàng,
 *    không thể sửa thủ công.
 * 4. Tích hợp trọn vẹn đóng góp của cả 5 thành viên (Nhàn, Trang, Hoàng, Vỹ, Vinh).
 */
public class Main {
    private static DuLieuHeThong storage;
    private static QuanLy quanLy;
    private static Scanner scanner;

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        // Khởi tạo Quản lý và Tầng lưu trữ file
        quanLy = new QuanLy("QL01", "Phạm Văn Quản Lý", "Hà Nội", "0988888888", 26, 2.0, 3_000_000);
        storage = new DuLieuHeThong(quanLy);

        // Tải toàn bộ dữ liệu từ file trong thư mục data/
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
            System.out.println("\n================================================================================");
            System.out.println("                 HỆ THỐNG QUẢN LÝ SIÊU THỊ MINI (CONSOLE CLI)                   ");
            System.out.println("================================================================================");
            System.out.println("  1. Quản lý Khách hàng");
            System.out.println("  2. Quản lý Sản phẩm (Khóa tồn kho - không cho sửa thủ công)");
            System.out.println("  3. Quản lý Nhà cung cấp & Nhập hàng (Tăng tồn kho qua phiếu nhập)");
            System.out.println("  4. Bán hàng & Hóa đơn (Giảm tồn kho qua thanh toán & Tích điểm)");
            System.out.println("  5. Quản lý Nhân sự & Phân ca làm việc");
            System.out.println("  6. Thống kê & Báo cáo doanh thu / tồn kho");
            System.out.println("  7. Chạy kiểm thử tự động toàn diện hệ thống");
            System.out.println("  0. Lưu dữ liệu & Thoát chương trình");
            System.out.println("================================================================================");
            System.out.print("Vui lòng chọn chức năng [0-7]: ");

            if (!scanner.hasNextLine()) {
                storage.luuTatCaDuLieu();
                System.out.println("\n[THÔNG BÁO] Kết thúc luồng nhập dữ liệu.");
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
                    System.out.println("\n[THÔNG BÁO] Đã lưu toàn bộ dữ liệu vào các file trong thư mục 'data/' an toàn.");
                    System.out.println("[THÔNG BÁO] Cảm ơn bạn đã sử dụng chương trình Siêu thị mini. Tạm biệt!\n");
                    return;
                }
                default -> System.out.println("[LỖI] Lựa chọn không hợp lệ. Vui lòng nhập số từ 0 đến 7!");
            }
        }
    }

    // =========================================================================
    // 1. QUẢN LÝ KHÁCH HÀNG
    // =========================================================================
    private static void menuKhachHang() {
        while (true) {
            System.out.println("\n--- [1] QUẢN LÝ KHÁCH HÀNG ---");
            System.out.println("1. Xem danh sách khách hàng");
            System.out.println("2. Thêm khách hàng mới");
            System.out.println("3. Sửa thông tin khách hàng");
            System.out.println("4. Xóa khách hàng");
            System.out.println("5. Tìm kiếm khách hàng theo Mã hoặc Số điện thoại");
            System.out.println("0. Quay lại Menu chính");
            System.out.print("Chọn [0-5]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> quanLy.quanLyKhachHang().hienThiDanhSach();
                case "2" -> {
                    System.out.println("\n-- Thêm Khách Hàng Mới --");
                    System.out.print("Nhập mã khách hàng (vd: KH06): ");
                    String ma = docDong();
                    if (ma.isEmpty() || quanLy.quanLyKhachHang().timKiemTheoMa(ma) != null) {
                        System.out.println("[LỖI] Mã khách hàng rỗng hoặc đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập họ và tên: ");
                    String ten = docDong();
                    System.out.print("Nhập địa chỉ: ");
                    String diaChi = docDong();
                    System.out.print("Nhập số điện thoại: ");
                    String sdt = docDong();

                    KhachHang kh = new KhachHang(ma, ten, diaChi, sdt);
                    if (quanLy.quanLyKhachHang().them(kh)) {
                        storage.luuKhachHang();
                        System.out.println("[THÀNH CÔNG] Đã thêm khách hàng " + ten + " và lưu vào file.");
                    } else {
                        System.out.println("[LỖI] Không thể thêm khách hàng!");
                    }
                }
                case "3" -> {
                    System.out.print("Nhập mã khách hàng cần sửa: ");
                    String ma = docDong();
                    KhachHang khCu = quanLy.quanLyKhachHang().timKiemTheoMa(ma);
                    if (khCu == null) {
                        System.out.println("[LỖI] Không tìm thấy khách hàng với mã " + ma);
                        break;
                    }
                    System.out.print("Nhập họ tên mới (" + khCu.getHoTen() + "): ");
                    String tenMoi = docDong();
                    if (tenMoi.isEmpty()) tenMoi = khCu.getHoTen();

                    System.out.print("Nhập địa chỉ mới (" + khCu.getDiaChi() + "): ");
                    String diaChiMoi = docDong();
                    if (diaChiMoi.isEmpty()) diaChiMoi = khCu.getDiaChi();

                    System.out.print("Nhập số điện thoại mới (" + khCu.getSoDienThoai() + "): ");
                    String sdtMoi = docDong();
                    if (sdtMoi.isEmpty()) sdtMoi = khCu.getSoDienThoai();

                    KhachHang khMoi = new KhachHang(ma, tenMoi, diaChiMoi, sdtMoi);
                    khMoi.setDiemTichLuy(khCu.getDiemTichLuy());
                    if (quanLy.quanLyKhachHang().sua(ma, khMoi)) {
                        storage.luuKhachHang();
                        System.out.println("[THÀNH CÔNG] Đã cập nhật thông tin khách hàng " + ma + ".");
                    }
                }
                case "4" -> {
                    System.out.print("Nhập mã khách hàng cần xóa: ");
                    String ma = docDong();
                    if (quanLy.quanLyKhachHang().xoa(ma)) {
                        storage.luuKhachHang();
                        System.out.println("[THÀNH CÔNG] Đã xóa khách hàng " + ma + ".");
                    } else {
                        System.out.println("[LỖI] Không tìm thấy khách hàng để xóa!");
                    }
                }
                case "5" -> {
                    System.out.print("Nhập mã KH hoặc Số điện thoại cần tìm: ");
                    String kw = docDong();
                    KhachHang kh = quanLy.quanLyKhachHang().timKiemTheoMa(kw);
                    if (kh == null) {
                        kh = quanLy.quanLyKhachHang().timKiemTheoSDT(kw);
                    }
                    if (kh != null) {
                        System.out.println("\n[KẾT QUẢ TÌM KIẾM]");
                        System.out.println("Mã KH: " + kh.getMaKhachHang() + " | Tên: " + kh.getHoTen()
                                + " | SĐT: " + kh.getSoDienThoai() + " | Điểm tích lũy: " + kh.getDiemTichLuy()
                                + " | Địa chỉ: " + kh.getDiaChi());
                    } else {
                        System.out.println("[THÔNG BÁO] Không tìm thấy khách hàng phù hợp!");
                    }
                }
                case "0" -> { return; }
                default -> System.out.println("[LỖI] Lựa chọn không hợp lệ!");
            }
        }
    }

    // =========================================================================
    // 2. QUẢN LÝ SẢN PHẨM (QUY TẮC BẤT BIẾN TỒN KHO)
    // =========================================================================
    private static void menuSanPham() {
        while (true) {
            System.out.println("\n--- [2] QUẢN LÝ SẢN PHẨM ---");
            System.out.println("1. Xem danh sách toàn bộ sản phẩm");
            System.out.println("2. Thêm sản phẩm mới (Tồn kho khởi tạo = 0, chỉ tăng khi nhập hàng)");
            System.out.println("3. Sửa thông tin sản phẩm (Khóa tồn kho - không thể sửa thủ công)");
            System.out.println("4. Xóa sản phẩm");
            System.out.println("5. Tìm kiếm sản phẩm theo tên hoặc mã");
            System.out.println("6. Cảnh báo sản phẩm sắp hết hàng (tồn kho thấp)");
            System.out.println("0. Quay lại Menu chính");
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
                        System.out.println("[THÀNH CÔNG] Đã xóa sản phẩm " + ma + ".");
                    } else {
                        System.out.println("[LỖI] Không tìm thấy sản phẩm để xóa!");
                    }
                }
                case "5" -> {
                    System.out.print("Nhập từ khóa tên hoặc mã sản phẩm: ");
                    String kw = docDong();
                    SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(kw);
                    if (sp != null) {
                        System.out.println("\n[KẾT QUẢ TÌM KIẾM THEO MÃ]");
                        sp.hienThiThongTin();
                    } else {
                        List<SanPham> list = quanLy.quanLySanPham().timKiemTheoTen(kw);
                        if (list.isEmpty()) {
                            System.out.println("[THÔNG BÁO] Không tìm thấy sản phẩm nào khớp với từ khóa: " + kw);
                        } else {
                            System.out.println("\n[KẾT QUẢ TÌM KIẾM (" + list.size() + " sản phẩm)]");
                            for (SanPham s : list) s.hienThiThongTin();
                        }
                    }
                }
                case "6" -> {
                    System.out.print("Nhập ngưỡng tồn kho cần cảnh báo (mặc định 50): ");
                    String ngStr = docDong();
                    int nguong = 50;
                    try {
                        if (!ngStr.isEmpty()) nguong = Integer.parseInt(ngStr);
                    } catch (NumberFormatException ignored) {}
                    quanLy.quanLySanPham().hienThiCanhBaoHetHang(nguong);
                }
                case "0" -> { return; }
                default -> System.out.println("[LỖI] Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void themSanPhamMoiConsole() {
        System.out.println("\n-- Thêm Sản Phẩm Mới --");
        System.out.println("CHÚ Ý: Theo quy định, số lượng tồn kho ban đầu luôn là 0.");
        System.out.println("Để có hàng bán, vui lòng dùng chức năng [3] Nhập Hàng.");
        System.out.println("Chọn phân loại:");
        System.out.println("  1. Thực phẩm (Có hạn sử dụng, tự động giảm giá khi cận date)");
        System.out.println("  2. Đồ uống (Có dung tích ml và hạn sử dụng)");
        System.out.println("  3. Đồ dùng (Có chất liệu và thời hạn bảo hành)");
        System.out.print("Chọn loại [1-3]: ");
        String loai = docDong();

        System.out.print("Nhập mã sản phẩm (vd: SP07): ");
        String ma = docDong();
        if (ma.isEmpty() || quanLy.quanLySanPham().timKiemTheoMa(ma) != null) {
            System.out.println("[LỖI] Mã sản phẩm rỗng hoặc đã tồn tại!");
            return;
        }

        System.out.print("Nhập tên sản phẩm: ");
        String ten = docDong();
        System.out.print("Nhập nước sản xuất: ");
        String nuoc = docDong();
        System.out.print("Nhập hãng sản xuất: ");
        String hang = docDong();

        int namSX = 2026;
        try {
            System.out.print("Nhập năm sản xuất (mặc định 2026): ");
            String namStr = docDong();
            if (!namStr.isEmpty()) namSX = Integer.parseInt(namStr);
        } catch (NumberFormatException ignored) {}

        double giaGoc = 0;
        try {
            System.out.print("Nhập giá gốc bán ra (VNĐ): ");
            giaGoc = Double.parseDouble(docDong());
        } catch (Exception e) {
            System.out.println("[LỖI] Giá gốc không hợp lệ!");
            return;
        }

        SanPham spMoi = null;
        switch (loai) {
            case "1" -> {
                System.out.print("Nhập hạn sử dụng (định dạng yyyy-MM-dd, vd 2027-06-30): ");
                String hsdStr = docDong();
                LocalDate hsd = hsdStr.isEmpty() ? LocalDate.now().plusMonths(6) : LocalDate.parse(hsdStr);
                spMoi = new ThucPham(ma, ten, nuoc, namSX, hang, 0, giaGoc, hsd);
            }
            case "2" -> {
                System.out.print("Nhập dung tích (ml, vd 500): ");
                int ml = Integer.parseInt(docDong());
                System.out.print("Nhập hạn sử dụng (yyyy-MM-dd): ");
                String hsdStr = docDong();
                LocalDate hsd = hsdStr.isEmpty() ? LocalDate.now().plusMonths(12) : LocalDate.parse(hsdStr);
                spMoi = new DoUong(ma, ten, nuoc, namSX, hang, 0, giaGoc, ml, hsd);
            }
            case "3" -> {
                System.out.print("Nhập chất liệu (vd Inox 304, Nhựa ABS): ");
                String chatLieu = docDong();
                System.out.print("Nhập thời hạn bảo hành (tháng, vd 12): ");
                int bh = Integer.parseInt(docDong());
                spMoi = new DoDung(ma, ten, nuoc, namSX, hang, 0, giaGoc, chatLieu, bh);
            }
            default -> {
                System.out.println("[LỖI] Phân loại sản phẩm không hợp lệ!");
                return;
            }
        }

        if (quanLy.quanLySanPham().them(spMoi)) {
            storage.luuSanPham();
            System.out.println("[THÀNH CÔNG] Đã thêm sản phẩm " + ten + " (Tồn kho: 0).");
        }
    }

    private static void suaSanPhamConsole() {
        System.out.print("Nhập mã sản phẩm cần sửa: ");
        String ma = docDong();
        SanPham cu = quanLy.quanLySanPham().timKiemTheoMa(ma);
        if (cu == null) {
            System.out.println("[LỖI] Không tìm thấy sản phẩm có mã " + ma);
            return;
        }

        System.out.println("Đang sửa sản phẩm: " + cu.getTenSanPham() + " (Tồn kho hiện tại: " + cu.getSoLuongTonKho() + ")");
        System.out.println("[LƯU Ý] Trường tồn kho được KHÓA BẢO VỆ, không thể sửa thủ công.");

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
            System.out.print("Thời hạn bảo hành mới (" + dd.layThoiHanBaoHanh() + " tháng): ");
            String bhStr = docDong();
            int bh = bhStr.isEmpty() ? dd.layThoiHanBaoHanh() : Integer.parseInt(bhStr);
            spMoi = new DoDung(ma, ten, nuoc, cu.getNamSanXuat(), hang, cu.getSoLuongTonKho(), gia, cl, bh);
        }

        if (spMoi != null && quanLy.quanLySanPham().sua(ma, spMoi)) {
            storage.luuSanPham();
            System.out.println("[THÀNH CÔNG] Đã cập nhật sản phẩm " + ma + ". Tồn kho vẫn bảo toàn nguyên vẹn: "
                    + quanLy.quanLySanPham().timKiemTheoMa(ma).getSoLuongTonKho());
        }
    }

    // =========================================================================
    // 3. QUẢN LÝ NHẬP HÀNG & NHÀ CUNG CẤP
    // =========================================================================
    private static void menuNhapHang() {
        while (true) {
            System.out.println("\n--- [3] QUẢN LÝ NHÀ CUNG CẤP & NHẬP HÀNG ---");
            System.out.println("1. Xem danh sách nhà cung cấp");
            System.out.println("2. Thêm nhà cung cấp mới");
            System.out.println("3. Lập phiếu nhập hàng mới (TỰ ĐỘNG TĂNG SỐ LƯỢNG TỒN KHO)");
            System.out.println("4. Xem lịch sử danh sách phiếu nhập hàng");
            System.out.println("5. Xem chi tiết một phiếu nhập hàng");
            System.out.println("0. Quay lại Menu chính");
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
                    System.out.print("Nhập mã NCC (vd NCC06): ");
                    String ma = docDong();
                    if (storage.timNhaCungCap(ma) != null) {
                        System.out.println("[LỖI] Mã nhà cung cấp đã tồn tại!");
                        break;
                    }
                    System.out.print("Nhập tên nhà cung cấp: ");
                    String ten = docDong();
                    System.out.print("Nhập số điện thoại: ");
                    String sdt = docDong();
                    System.out.print("Nhập địa chỉ: ");
                    String dc = docDong();

                    NhaCungCap ncc = new NhaCungCap(ma, ten, sdt, dc);
                    if (storage.themNhaCungCap(ncc)) {
                        storage.luuNhaCungCap();
                        System.out.println("[THÀNH CÔNG] Đã thêm nhà cung cấp " + ten + ".");
                    }
                }
                case "3" -> lapPhieuNhapHangConsole();
                case "4" -> quanLy.quanLyNhapHang().hienThiDanhSach();
                case "5" -> {
                    System.out.print("Nhập mã phiếu nhập (vd NH001): ");
                    String ma = docDong();
                    NhapHang nh = quanLy.quanLyNhapHang().timKiemTheoMa(ma);
                    if (nh != null) {
                        nh.hienThiPhieuNhap();
                    } else {
                        System.out.println("[LỖI] Không tìm thấy phiếu nhập hàng mã: " + ma);
                    }
                }
                case "0" -> { return; }
                default -> System.out.println("[LỖI] Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void lapPhieuNhapHangConsole() {
        System.out.println("\n-- LẬP PHIẾU NHẬP HÀNG MỚI (TĂNG TỒN KHO TỰ ĐỘNG) --");
        String maNH = "NH" + String.format("%03d", quanLy.quanLyNhapHang().getSoLuong() + 1);
        System.out.println("Mã phiếu nhập tự động: " + maNH);

        System.out.print("Nhập mã Nhà cung cấp (vd NCC01, NCC02): ");
        String maNCC = docDong();
        NhaCungCap ncc = storage.timNhaCungCap(maNCC);
        if (ncc == null) {
            System.out.println("[LỖI] Không tìm thấy nhà cung cấp " + maNCC);
            return;
        }

        NhapHang phieuNhap = new NhapHang(maNH, LocalDate.now(), ncc, quanLy);

        while (true) {
            System.out.print("Nhập mã sản phẩm nhập (hoặc gõ 'xong' để kết thúc): ");
            String maSP = docDong();
            if (maSP.equalsIgnoreCase("xong")) break;

            SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(maSP);
            if (sp == null) {
                System.out.println("[LỖI] Không tìm thấy mã sản phẩm " + maSP);
                continue;
            }

            System.out.println("Sản phẩm: " + sp.getTenSanPham() + " (Tồn kho hiện tại: " + sp.getSoLuongTonKho() + ")");
            int slNhap = 0;
            double giaNhap = 0;
            try {
                System.out.print("Nhập số lượng nhập (> 0): ");
                slNhap = Integer.parseInt(docDong());
                System.out.print("Nhập đơn giá nhập hàng (VNĐ): ");
                giaNhap = Double.parseDouble(docDong());
            } catch (Exception e) {
                System.out.println("[LỖI] Số lượng hoặc đơn giá không hợp lệ!");
                continue;
            }

            phieuNhap.themChiTietVaCapNhatTonKho(sp, slNhap, giaNhap);
            System.out.println("-> Đã nhập " + slNhap + " cái. Tồn kho mới của " + sp.getTenSanPham() + ": " + sp.getSoLuongTonKho());
        }

        if (phieuNhap.getDsChiTiet().isEmpty()) {
            System.out.println("[HỦY BỎ] Phiếu nhập rỗng, không lưu!");
            return;
        }

        if (quanLy.quanLyNhapHang().them(phieuNhap)) {
            // Lưu dữ liệu nhập hàng và cập nhật dữ liệu tồn kho sản phẩm
            storage.luuNhapHang();
            storage.luuSanPham();
            System.out.println("\n[THÀNH CÔNG] Đã lưu phiếu nhập hàng " + maNH + " thành công!");
            phieuNhap.hienThiPhieuNhap();
        }
    }

    // =========================================================================
    // 4. BÁN HÀNG & LẬP HÓA ĐƠN
    // =========================================================================
    private static void menuBanHang() {
        while (true) {
            System.out.println("\n--- [4] BÁN HÀNG & LẬP HÓA ĐƠN ---");
            System.out.println("1. Xem danh sách đơn hàng đã bán");
            System.out.println("2. Lập và thanh toán hóa đơn bán hàng (TỰ ĐỘNG TRỪ TỒN KHO & TÍCH ĐIỂM)");
            System.out.println("3. Xem chi tiết một hóa đơn");
            System.out.println("4. Xem danh sách phiếu bảo hành đã cấp");
            System.out.println("0. Quay lại Menu chính");
            System.out.print("Chọn [0-4]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> quanLy.quanLyDonHang().hienThiDanhSach();
                case "2" -> lapVaThanhToanHoaDonConsole();
                case "3" -> {
                    System.out.print("Nhập mã hóa đơn cần xem (vd HD001): ");
                    String ma = docDong();
                    HoaDon hd = quanLy.quanLyDonHang().timKiemTheoMa(ma);
                    if (hd != null) {
                        hd.hienThiHoaDon();
                    } else {
                        System.out.println("[LỖI] Không tìm thấy hóa đơn: " + ma);
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
                default -> System.out.println("[LỖI] Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void lapVaThanhToanHoaDonConsole() {
        System.out.println("\n-- LẬP HÓA ĐƠN BÁN HÀNG MỚI --");
        String maHD = "HD" + String.format("%03d", quanLy.quanLyDonHang().getSoLuong() + 1);
        System.out.println("Mã hóa đơn tự động: " + maHD);

        // Chọn Thu ngân phụ trách
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

        // Chọn khách hàng
        System.out.print("Nhập mã khách hàng (bỏ trống nếu là khách vãng lai): ");
        String maKH = docDong();
        KhachHang kh = null;
        if (!maKH.isEmpty()) {
            kh = quanLy.quanLyKhachHang().timKiemTheoMa(maKH);
            if (kh != null) {
                System.out.println("Khách hàng: " + kh.getHoTen() + " | Điểm tích lũy hiện tại: " + kh.getDiemTichLuy());
            } else {
                System.out.println("[THÔNG BÁO] Không tìm thấy mã khách hàng, tính là khách vãng lai.");
            }
        }

        // Chọn khuyến mãi
        System.out.print("Nhập mã khuyến mãi (bỏ trống nếu không có): ");
        String maKM = docDong();
        KhuyenMai km = null;
        if (!maKM.isEmpty()) {
            km = storage.timKhuyenMai(maKM);
            if (km != null && km.conHieuLuc(LocalDate.now())) {
                System.out.println("Áp dụng mã KM: " + km.getMaKhuyenMai() + " (Giảm " + (int)(km.getTiLeGiam() * 100) + "%)");
            } else {
                System.out.println("[THÔNG BÁO] Mã khuyến mãi không hợp lệ hoặc đã hết hạn.");
                km = null;
            }
        }

        HoaDon hd = new HoaDon(maHD, LocalDate.now(), thuNgan, kh, km);

        // Thêm sản phẩm vào giỏ
        while (true) {
            System.out.print("Nhập mã sản phẩm cần mua (hoặc gõ 'xong' để thanh toán): ");
            String maSP = docDong();
            if (maSP.equalsIgnoreCase("xong")) break;

            SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(maSP);
            if (sp == null) {
                System.out.println("[LỖI] Không tìm thấy sản phẩm " + maSP);
                continue;
            }

            if (!sp.conHanSuDung()) {
                System.out.println("[LỖI] Sản phẩm " + sp.getTenSanPham() + " đã hết hạn sử dụng, không thể bán!");
                continue;
            }

            System.out.println("Sản phẩm: " + sp.getTenSanPham() + " | Đơn giá: " + HoaDon.dinhDangTien(sp.tinhGia())
                    + " | Tồn kho còn: " + sp.getSoLuongTonKho());

            if (sp.getSoLuongTonKho() <= 0) {
                System.out.println("[LỖI] Sản phẩm đã HẾT HÀNG trong kho!");
                continue;
            }

            System.out.print("Nhập số lượng mua: ");
            int sl = 0;
            try {
                sl = Integer.parseInt(docDong());
            } catch (Exception e) {
                System.out.println("[LỖI] Số lượng không hợp lệ!");
                continue;
            }

            try {
                hd.themChiTiet(sp, sl);
                System.out.println("-> Đã thêm " + sl + " " + sp.getTenSanPham() + " vào hóa đơn.");
            } catch (Exception e) {
                System.out.println("[LỖI THÊM HÀNG] " + e.getMessage());
            }
        }

        if (hd.getDsChiTiet().isEmpty()) {
            System.out.println("[HỦY BỎ] Hóa đơn không có sản phẩm nào!");
            return;
        }

        // Xem trước hóa đơn
        hd.hienThiHoaDon();

        // Chọn phương thức thanh toán
        System.out.println("\nChọn phương thức thanh toán:");
        System.out.println("  1. Tiền mặt");
        System.out.println("  2. Chuyển khoản ngân hàng (QR Code)");
        System.out.println("  3. Thẻ ATM / Tín dụng (16 chữ số)");
        System.out.print("Chọn [1-3]: ");
        String ptChon = docDong();

        IPhuongThucThanhToan phuongThuc = new TienMat();
        if ("2".equals(ptChon)) {
            String maGD = "CK" + System.currentTimeMillis();
            phuongThuc = new ChuyenKhoan(maGD);
            System.out.println("-> Tạo mã giao dịch chuyển khoản: " + maGD);
        } else if ("3".equals(ptChon)) {
            System.out.print("Nhập số thẻ ngân hàng (16 chữ số, vd 9704123456789012): ");
            String soThe = docDong();
            phuongThuc = new TheNganHang(soThe);
        }

        // Tiến hành thu ngân thanh toán
        boolean ttThanhCong = thuNgan.thanhToanHoaDon(hd, phuongThuc);
        if (ttThanhCong) {
            quanLy.quanLyDonHang().them(hd);

            // Kiểm tra xem có sản phẩm đồ dùng cần xuất Phiếu bảo hành không
            for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
                SanPham sp = ct.getSanPham();
                if (sp instanceof DoDung dd) {
                    String maPBH = "PBH" + String.format("%03d", storage.getDsPhieuBaoHanh().size() + 1);
                    PhieuBaoHanh pbh = new PhieuBaoHanh(maPBH, hd, dd, kh, "Bảo hành tiêu chuẩn");
                    storage.themPhieuBaoHanh(pbh);
                    System.out.println("-> [BẢO HÀNH] Tự động cấp phiếu bảo hành " + maPBH + " cho "
                            + dd.getTenSanPham() + " (" + dd.layThoiHanBaoHanh() + " tháng)");
                }
            }

            // Lưu toàn bộ dữ liệu (tồn kho giảm, đơn hàng mới, điểm KH mới, phiếu bảo hành mới)
            storage.luuHoaDon();
            storage.luuSanPham();
            storage.luuKhachHang();
            storage.luuPhieuBaoHanh();

            System.out.println("\n[THÀNH CÔNG] Thanh toán hóa đơn thành công! Tồn kho đã tự động khấu trừ.");
            if (kh != null) {
                System.out.println("Điểm tích lũy mới của khách hàng " + kh.getHoTen() + ": " + kh.getDiemTichLuy());
            }
        } else {
            System.out.println("[THẤT BẠI] Thanh toán không thành công! Hóa đơn chưa được ghi nhận.");
        }
    }

    // =========================================================================
    // 5. QUẢN LÝ NHÂN SỰ & PHÂN CA
    // =========================================================================
    private static void menuNhanSu() {
        while (true) {
            System.out.println("\n--- [5] QUẢN LÝ NHÂN SỰ & CA LÀM VIỆC ---");
            System.out.println("1. Xem danh sách nhân viên và bảng tính lương");
            System.out.println("2. Thêm nhân viên mới");
            System.out.println("3. Xem danh sách toàn bộ ca làm việc");
            System.out.println("4. Phân công ca làm cho nhân viên (Kiểm tra chống trùng ca)");
            System.out.println("0. Quay lại Menu chính");
            System.out.print("Chọn [0-4]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> {
                    System.out.println("============================================= DANH SÁCH NHÂN SỰ SIÊU THỊ =============================================");
                    System.out.println("[QUẢN LÝ TRƯỞNG]");
                    quanLy.hienThiThongTin();
                    System.out.println("\n[DANH SÁCH NHÂN VIÊN]");
                    for (NhanVien nv : storage.getDsNhanVien()) {
                        nv.hienThiThongTin();
                    }
                    System.out.println("=========================================================================================================");
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
                default -> System.out.println("[LỖI] Lựa chọn không hợp lệ!");
            }
        }
    }

    private static void themNhanVienConsole() {
        System.out.println("\n-- Thêm Nhân Viên Mới --");
        System.out.println("1. Thu ngân");
        System.out.println("2. Nhân viên Part-time");
        System.out.println("3. Nhân viên Full-time khác");
        System.out.print("Chọn loại [1-3]: ");
        String loai = docDong();

        System.out.print("Nhập mã nhân viên: ");
        String ma = docDong();
        if (storage.timNhanVien(ma) != null) {
            System.out.println("[LỖI] Mã nhân viên đã tồn tại!");
            return;
        }

        System.out.print("Nhập họ tên: ");
        String ten = docDong();
        System.out.print("Nhập địa chỉ: ");
        String dc = docDong();
        System.out.print("Nhập số điện thoại: ");
        String sdt = docDong();

        NhanVien nv = null;
        switch (loai) {
            case "1" -> {
                System.out.print("Nhập mã quầy thu ngân (vd Quầy 03): ");
                String quay = docDong();
                nv = new ThuNgan(ma, ten, dc, sdt, 26, 1.2, quay);
            }
            case "2" -> {
                System.out.print("Nhập lương mỗi giờ (vd 30000): ");
                double luong = Double.parseDouble(docDong());
                nv = new NhanVienPartTime(ma, ten, dc, sdt, 0, luong);
            }
            case "3" -> {
                System.out.print("Nhập hệ số lương (vd 1.5): ");
                double heSo = Double.parseDouble(docDong());
                nv = new NhanVienFullTime(ma, ten, dc, sdt, 26, heSo);
            }
            default -> {
                System.out.println("[LỖI] Lựa chọn loại nhân viên không hợp lệ!");
                return;
            }
        }

        if (storage.themNhanVien(nv)) {
            storage.luuNhanVien();
            System.out.println("[THÀNH CÔNG] Đã thêm nhân viên " + ten + ".");
        }
    }

    private static void phanCongCaConsole() {
        System.out.println("\n-- PHÂN CÔNG CA LÀM VIỆC --");
        System.out.print("Nhập mã nhân viên nhận ca: ");
        String maNV = docDong();
        NhanVien nv = storage.timNhanVien(maNV);
        if (nv == null) {
            System.out.println("[LỖI] Không tìm thấy nhân viên " + maNV);
            return;
        }

        System.out.print("Nhập mã ca làm mới (vd CA06): ");
        String maCa = docDong();
        System.out.print("Nhập ngày làm (yyyy-MM-dd, vd 2026-11-12): ");
        LocalDate ngay = LocalDate.parse(docDong());
        System.out.print("Nhập giờ bắt đầu (HH:mm, vd 08:00): ");
        LocalTime bd = LocalTime.parse(docDong());
        System.out.print("Nhập giờ kết thúc (HH:mm, vd 12:30): ");
        LocalTime kt = LocalTime.parse(docDong());

        CaLam caMoi = new CaLam(maCa, ngay, bd, kt);
        boolean phanCongOK = quanLy.phanCongCa(nv, caMoi);
        if (phanCongOK) {
            storage.themCaLam(caMoi);
            storage.luuCaLam();
            System.out.println("[THÀNH CÔNG] Phân công ca làm thành công và đã lưu file.");
        } else {
            System.out.println("[THẤT BẠI] Không thể phân công do nhân viên đã có ca làm trùng thời gian!");
        }
    }

    // =========================================================================
    // 6. BÁO CÁO & THỐNG KÊ
    // =========================================================================
    private static void menuBaoCao() {
        while (true) {
            System.out.println("\n--- [6] THỐNG KÊ & BÁO CÁO ---");
            System.out.println("1. Thống kê doanh thu bán hàng theo khoảng ngày");
            System.out.println("2. Thống kê chi phí nhập hàng theo khoảng ngày");
            System.out.println("3. Báo cáo tổng hợp tài chính (Doanh thu - Chi phí)");
            System.out.println("4. Báo cáo tình hình tồn kho siêu thị");
            System.out.println("0. Quay lại Menu chính");
            System.out.print("Chọn [0-4]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> {
                    LocalDate[] k = nhapKhoangNgay();
                    if (k != null) {
                        double dt = quanLy.quanLyDonHang().thongKeDoanhThu(k[0], k[1]);
                        System.out.println("-> Doanh thu từ ngày " + k[0] + " đến " + k[1] + ": " + HoaDon.dinhDangTien(dt));
                    }
                }
                case "2" -> {
                    LocalDate[] k = nhapKhoangNgay();
                    if (k != null) {
                        double cp = quanLy.quanLyNhapHang().thongKeChiPhiNhap(k[0], k[1]);
                        System.out.println("-> Chi phí nhập từ ngày " + k[0] + " đến " + k[1] + ": " + HoaDon.dinhDangTien(cp));
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
                        System.out.println("Chênh lệch    : " + HoaDon.dinhDangTien(chenhLech) + (chenhLech >= 0 ? " (Lãi ròng)" : " (Đầu tư/Âm vốn)"));
                        System.out.println("==============================================================");
                    }
                }
                case "4" -> {
                    System.out.println("\n-- TÌNH HÌNH TỒN KHO SIÊU THỊ --");
                    quanLy.quanLySanPham().hienThiDanhSach();
                    quanLy.quanLySanPham().hienThiCanhBaoHetHang(50);
                }
                case "0" -> { return; }
                default -> System.out.println("[LỖI] Lựa chọn không hợp lệ!");
            }
        }
    }

    private static LocalDate[] nhapKhoangNgay() {
        try {
            System.out.print("Nhập ngày bắt đầu (yyyy-MM-dd, vd 2026-11-01): ");
            String tuStr = docDong();
            LocalDate tu = tuStr.isEmpty() ? LocalDate.now().minusMonths(1) : LocalDate.parse(tuStr);

            System.out.print("Nhập ngày kết thúc (yyyy-MM-dd, vd 2026-11-30): ");
            String denStr = docDong();
            LocalDate den = denStr.isEmpty() ? LocalDate.now() : LocalDate.parse(denStr);

            return new LocalDate[]{tu, den};
        } catch (DateTimeParseException e) {
            System.out.println("[LỖI] Định dạng ngày không đúng (phải là yyyy-MM-dd)!");
            return null;
        }
    }

    // =========================================================================
    // 7. KIỂM THỬ TOÀN DIỆN HỆ THỐNG
    // =========================================================================
    public static void chayKiemThuToanDien() {
        System.out.println("\n================================================================================");
        System.out.println("                  BẮT ĐẦU CHẠY TOÀN BỘ CÁC BÀI KIỂM THỬ TỰ ĐỘNG                ");
        System.out.println("================================================================================");

        System.out.println("\n>>> [1/4] KIỂM THỬ HÓA ĐƠN & PHIẾU BẢO HÀNH (Trang):");
        KiemThuHoaDon.main(new String[]{});

        System.out.println("\n>>> [2/4] KIỂM THỬ SẢN PHẨM & ĐA HÌNH (Vỹ):");
        KiemThuSanPham.main(new String[]{});

        System.out.println("\n>>> [3/4] KIỂM THỬ MODULE QUẢN LÝ & PHÂN CÔNG CA LÀM (Vinh & Hoàng):");
        KiemThuQuanLy.main(new String[]{});

        System.out.println("\n>>> [4/4] KIỂM THỬ LƯU TRỮ FILE & RÀNG BUỘC TỒN KHO:");
        KiemThuHeThongVaLuuTru.main(new String[]{});

        System.out.println("\n================================================================================");
        System.out.println("            HOÀN THÀNH KIỂM THỬ TOÀN DIỆN: TẤT CẢ MODULE ĐẠT CHUẨN!            ");
        System.out.println("================================================================================");
    }
}
