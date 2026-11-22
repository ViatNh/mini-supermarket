package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import sieuthi.data.DuLieuHeThong;
import sieuthi.manage.QuanLy;
import sieuthi.model.*;

/**
 * Chuong trinh quan ly Sieu thi mini (Console Application).
 */
public class Main {
    private static DuLieuHeThong storage;
    private static QuanLy quanLy;
    private static Scanner scanner;

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        scanner = new Scanner(System.in, StandardCharsets.UTF_8);

        quanLy = new QuanLy("QL01", "Pham Van Quan Ly", "Ha Noi", "0988888888", 26, 2.0, 3_000_000);
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

    private static LocalDate parseNgayResilient(String input) {
        if (input == null || input.trim().isEmpty()) return null;
        String s = input.trim();
        String[] patterns = {"yyyy-MM-dd", "dd-MM-yyyy", "yyyy/MM/dd", "dd/MM/yyyy", "d-M-yyyy", "d/M/yyyy"};
        for (String p : patterns) {
            try {
                return LocalDate.parse(s, DateTimeFormatter.ofPattern(p));
            } catch (DateTimeParseException ignored) {}
        }
        return null;
    }

    private static LocalDate nhapNgayHopLe(String thongBao, boolean choPhepDeTrong, LocalDate macDinh) {
        while (true) {
            System.out.print(thongBao);
            String s = docDong();
            if (s.isEmpty()) {
                if (choPhepDeTrong) return macDinh;
                System.out.println("Loi: Khong duoc de trong!");
                continue;
            }
            if (s.equalsIgnoreCase("huy")) return null;
            LocalDate d = parseNgayResilient(s);
            if (d != null) return d;
            System.out.println("Loi: Dinh dang ngay khong hop le (go yyyy-MM-dd hoac dd-MM-yyyy, go 'huy' de thoat)!");
        }
    }

    private static LocalTime parseGioResilient(String input) {
        if (input == null || input.trim().isEmpty()) return null;
        String s = input.trim();
        String[] patterns = {"HH:mm", "H:mm", "HH:mm:ss", "H:m"};
        for (String p : patterns) {
            try {
                return LocalTime.parse(s, DateTimeFormatter.ofPattern(p));
            } catch (DateTimeParseException ignored) {}
        }
        return null;
    }

    private static LocalTime nhapGioHopLe(String thongBao) {
        while (true) {
            System.out.print(thongBao);
            String s = docDong();
            if (s.equalsIgnoreCase("huy")) return null;
            LocalTime t = parseGioResilient(s);
            if (t != null) return t;
            System.out.println("Loi: Dinh dang gio khong hop le (vi du: 07:30 hoac 7:30, go 'huy' de thoat)!");
        }
    }

    // =========================================================================
    // MENU CHINH
    // =========================================================================
    private static void chayMenuChinh() {
        while (true) {
            System.out.println("\n==================== HE THONG QUAN LY SIEU THI MINI ====================");
            System.out.println("  1. Quan ly khach hang");
            System.out.println("  2. Quan ly san pham");
            System.out.println("  3. Quan ly nha cung cap & nhap hang");
            System.out.println("  4. Quan ly ban hang & hoa don");
            System.out.println("  5. Quan ly nhan su & ca lam");
            System.out.println("  6. Thong ke & bao cao");
            System.out.println("  7. Kiem thu he thong");
            System.out.println("  0. Thoat");
            System.out.println("=======================================================================");
            System.out.print("Chon chuc nang [0-7]: ");

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
                    System.out.println("\nDa luu du lieu. Tam biet!\n");
                    return;
                }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // =========================================================================
    // 1. QUAN LY KHACH HANG
    // =========================================================================
    private static void menuKhachHang() {
        while (true) {
            System.out.println("\n--- QUAN LY KHACH HANG ---");
            System.out.println("1. Danh sach khach hang");
            System.out.println("2. Them khach hang");
            System.out.println("3. Sua thong tin khach hang");
            System.out.println("4. Xoa khach hang");
            System.out.println("5. Tim kiem khach hang");
            System.out.println("0. Quay lai");
            System.out.print("Chon [0-5]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> quanLy.quanLyKhachHang().hienThiDanhSach();
                case "2" -> {
                    System.out.println("\n-- Them khach hang moi --");
                    System.out.print("Ma khach hang: ");
                    String ma = docDong();
                    if (ma.isEmpty() || quanLy.quanLyKhachHang().timKiemTheoMa(ma) != null) {
                        System.out.println("Ma khach hang khong hop le hoac da ton tai!");
                        break;
                    }
                    System.out.print("Ho va ten: ");
                    String ten = docDong();
                    System.out.print("Dia chi: ");
                    String diaChi = docDong();
                    System.out.print("So dien thoai: ");
                    String sdt = docDong();

                    KhachHang kh = new KhachHang(ma, ten, diaChi, sdt);
                    if (quanLy.quanLyKhachHang().them(kh)) {
                        storage.luuKhachHang();
                        System.out.println("Them khach hang thanh cong.");
                    } else {
                        System.out.println("Khong the them khach hang!");
                    }
                }
                case "3" -> {
                    System.out.print("Nhap ma khach hang can sua: ");
                    String ma = docDong();
                    KhachHang khCu = quanLy.quanLyKhachHang().timKiemTheoMa(ma);
                    if (khCu == null) {
                        System.out.println("Khong tim thay khach hang: " + ma);
                        break;
                    }
                    System.out.print("Ho ten moi (" + khCu.getHoTen() + "): ");
                    String tenMoi = docDong();
                    if (tenMoi.isEmpty()) tenMoi = khCu.getHoTen();

                    System.out.print("Dia chi moi (" + khCu.getDiaChi() + "): ");
                    String diaChiMoi = docDong();
                    if (diaChiMoi.isEmpty()) diaChiMoi = khCu.getDiaChi();

                    System.out.print("So dien thoai moi (" + khCu.getSoDienThoai() + "): ");
                    String sdtMoi = docDong();
                    if (sdtMoi.isEmpty()) sdtMoi = khCu.getSoDienThoai();

                    KhachHang khMoi = new KhachHang(ma, tenMoi, diaChiMoi, sdtMoi);
                    khMoi.setDiemTichLuy(khCu.getDiemTichLuy());
                    if (quanLy.quanLyKhachHang().sua(ma, khMoi)) {
                        storage.luuKhachHang();
                        System.out.println("Cap nhat thanh cong.");
                    }
                }
                case "4" -> {
                    System.out.print("Nhap ma khach hang can xoa: ");
                    String ma = docDong();
                    if (quanLy.quanLyKhachHang().xoa(ma)) {
                        storage.luuKhachHang();
                        System.out.println("Xoa khach hang thanh cong.");
                    } else {
                        System.out.println("Khong tim thay khach hang!");
                    }
                }
                case "5" -> {
                    System.out.print("Nhap ma hoac so dien thoai: ");
                    String kw = docDong();
                    KhachHang kh = quanLy.quanLyKhachHang().timKiemTheoMa(kw);
                    if (kh == null) kh = quanLy.quanLyKhachHang().timKiemTheoSDT(kw);
                    if (kh != null) {
                        System.out.println("Ma KH: " + kh.getMaKhachHang() + " | Ten: " + kh.getHoTen()
                                + " | SDT: " + kh.getSoDienThoai() + " | Diem: " + kh.getDiemTichLuy()
                                + " | Dia chi: " + kh.getDiaChi());
                    } else {
                        System.out.println("Khong tim thay khach hang!");
                    }
                }
                case "0" -> { return; }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    // =========================================================================
    // 2. QUAN LY SAN PHAM
    // =========================================================================
    private static void menuSanPham() {
        while (true) {
            System.out.println("\n--- QUAN LY SAN PHAM ---");
            System.out.println("1. Danh sach san pham");
            System.out.println("2. Them san pham moi");
            System.out.println("3. Sua thong tin san pham");
            System.out.println("4. Xoa san pham");
            System.out.println("5. Tim kiem san pham");
            System.out.println("6. Canh bao ton kho thap");
            System.out.println("0. Quay lai");
            System.out.print("Chon [0-6]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> quanLy.quanLySanPham().hienThiDanhSach();
                case "2" -> themSanPhamMoiConsole();
                case "3" -> suaSanPhamConsole();
                case "4" -> {
                    System.out.print("Nhap ma san pham can xoa: ");
                    String ma = docDong();
                    if (quanLy.quanLySanPham().xoa(ma)) {
                        storage.luuSanPham();
                        System.out.println("Xoa san pham thanh cong.");
                    } else {
                        System.out.println("Khong tim thay san pham!");
                    }
                }
                case "5" -> {
                    System.out.print("Nhap ma hoac ten san pham: ");
                    String kw = docDong();
                    SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(kw);
                    if (sp != null) {
                        sp.hienThiThongTin();
                    } else {
                        SanPham[] list = quanLy.quanLySanPham().timKiemTheoTen(kw);
                        if (list.length == 0) {
                            System.out.println("Khong tim thay san pham!");
                        } else {
                            for (SanPham s : list) s.hienThiThongTin();
                        }
                    }
                }
                case "6" -> {
                    System.out.print("Nhap nguong ton kho (mac dinh 50): ");
                    String ngStr = docDong();
                    int nguong = 50;
                    try {
                        if (!ngStr.isEmpty()) nguong = Integer.parseInt(ngStr);
                    } catch (NumberFormatException ignored) {}
                    quanLy.quanLySanPham().hienThiCanhBaoHetHang(nguong);
                }
                case "0" -> { return; }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void themSanPhamMoiConsole() {
        System.out.println("\n-- Them san pham moi --");
        System.out.println("1. Thuc pham");
        System.out.println("2. Do uong");
        System.out.println("3. Do dung");
        System.out.print("Chon loai [1-3]: ");
        String loai = docDong();

        System.out.print("Ma san pham: ");
        String ma = docDong();
        if (ma.isEmpty() || quanLy.quanLySanPham().timKiemTheoMa(ma) != null) {
            System.out.println("Ma san pham khong hop le hoac da ton tai!");
            return;
        }

        System.out.print("Ten san pham: ");
        String ten = docDong();
        System.out.print("Nuoc san xuat (mac dinh Viet Nam): ");
        String nuoc = docDong();
        if (nuoc.isEmpty()) nuoc = "Viet Nam";

        System.out.print("Hang san xuat: ");
        String hang = docDong();

        int namSX = 2026;
        try {
            System.out.print("Nam san xuat (mac dinh 2026): ");
            String namStr = docDong();
            if (!namStr.isEmpty()) namSX = Integer.parseInt(namStr);
        } catch (NumberFormatException ignored) {}

        double giaGoc = 0;
        try {
            System.out.print("Gia ban (VND): ");
            giaGoc = Double.parseDouble(docDong());
        } catch (Exception e) {
            System.out.println("Gia ban khong hop le!");
            return;
        }

        SanPham spMoi = null;
        switch (loai) {
            case "1" -> {
                LocalDate hsd = nhapNgayHopLe("Han su dung (yyyy-MM-dd hoac dd-MM-yyyy): ", true, LocalDate.now().plusMonths(6));
                spMoi = new ThucPham(ma, ten, nuoc, namSX, hang, 0, giaGoc, hsd);
            }
            case "2" -> {
                int ml = 500;
                try {
                    System.out.print("Dung tich (ml, mac dinh 500): ");
                    String dtStr = docDong();
                    if (!dtStr.isEmpty()) ml = Integer.parseInt(dtStr);
                } catch (NumberFormatException ignored) {}

                LocalDate hsd = nhapNgayHopLe("Han su dung (yyyy-MM-dd hoac dd-MM-yyyy): ", true, LocalDate.now().plusMonths(12));
                spMoi = new DoUong(ma, ten, nuoc, namSX, hang, 0, giaGoc, ml, hsd);
            }
            case "3" -> {
                System.out.print("Chat lieu: ");
                String chatLieu = docDong();
                if (chatLieu.isEmpty()) chatLieu = "Inox";

                int bh = 12;
                try {
                    System.out.print("Thoi han bao hanh (thang, mac dinh 12): ");
                    String bhStr = docDong();
                    if (!bhStr.isEmpty()) bh = Integer.parseInt(bhStr);
                } catch (NumberFormatException ignored) {}

                spMoi = new DoDung(ma, ten, nuoc, namSX, hang, 0, giaGoc, chatLieu, bh);
            }
            default -> {
                System.out.println("Loai san pham khong hop le!");
                return;
            }
        }

        if (quanLy.quanLySanPham().them(spMoi)) {
            storage.luuSanPham();
            System.out.println("Them san pham thanh cong (ton kho ban dau: 0).");
        }
    }

    private static void suaSanPhamConsole() {
        System.out.print("Nhap ma san pham can sua: ");
        String ma = docDong();
        SanPham cu = quanLy.quanLySanPham().timKiemTheoMa(ma);
        if (cu == null) {
            System.out.println("Khong tim thay san pham: " + ma);
            return;
        }

        System.out.println("Dang sua san pham: " + cu.getTenSanPham() + " (An Enter de giu nguyen thong tin cu)");
        System.out.print("Ten san pham moi (" + cu.getTenSanPham() + "): ");
        String ten = docDong();
        if (ten.isEmpty()) ten = cu.getTenSanPham();

        System.out.print("Nuoc san xuat moi (" + cu.getNuocSanXuat() + "): ");
        String nuoc = docDong();
        if (nuoc.isEmpty()) nuoc = cu.getNuocSanXuat();

        System.out.print("Hang san xuat moi (" + cu.getHangSanXuat() + "): ");
        String hang = docDong();
        if (hang.isEmpty()) hang = cu.getHangSanXuat();

        System.out.print("Gia ban moi (" + HoaDon.dinhDangTien(cu.getGiaGoc()) + "): ");
        String giaStr = docDong();
        double gia = cu.getGiaGoc();
        if (!giaStr.isEmpty()) {
            try { gia = Double.parseDouble(giaStr); } catch (NumberFormatException ignored) {}
        }

        SanPham spMoi = null;
        if (cu instanceof ThucPham tp) {
            LocalDate hsd = nhapNgayHopLe("Han su dung moi (" + tp.getHanSuDung() + "): ", true, tp.getHanSuDung());
            spMoi = new ThucPham(ma, ten, nuoc, cu.getNamSanXuat(), hang, cu.getSoLuongTonKho(), gia, hsd);
        } else if (cu instanceof DoUong du) {
            System.out.print("Dung tich moi (" + du.getDungTichMl() + "ml): ");
            String dtStr = docDong();
            int dt = du.getDungTichMl();
            if (!dtStr.isEmpty()) {
                try { dt = Integer.parseInt(dtStr); } catch (NumberFormatException ignored) {}
            }
            LocalDate hsd = nhapNgayHopLe("Han su dung moi (" + du.getHanSuDung() + "): ", true, du.getHanSuDung());
            spMoi = new DoUong(ma, ten, nuoc, cu.getNamSanXuat(), hang, cu.getSoLuongTonKho(), gia, dt, hsd);
        } else if (cu instanceof DoDung dd) {
            System.out.print("Chat lieu moi (" + dd.getChatLieu() + "): ");
            String cl = docDong();
            if (cl.isEmpty()) cl = dd.getChatLieu();

            System.out.print("Bao hanh moi (" + dd.layThoiHanBaoHanh() + " thang): ");
            String bhStr = docDong();
            int bh = dd.layThoiHanBaoHanh();
            if (!bhStr.isEmpty()) {
                try { bh = Integer.parseInt(bhStr); } catch (NumberFormatException ignored) {}
            }
            spMoi = new DoDung(ma, ten, nuoc, cu.getNamSanXuat(), hang, cu.getSoLuongTonKho(), gia, cl, bh);
        }

        if (spMoi != null && quanLy.quanLySanPham().sua(ma, spMoi)) {
            storage.luuSanPham();
            System.out.println("Cap nhat thong tin san pham thanh cong.");
        }
    }

    // =========================================================================
    // 3. QUAN LY NHA CUNG CAP & NHAP HANG
    // =========================================================================
    private static void menuNhapHang() {
        while (true) {
            System.out.println("\n--- QUAN LY NHA CUNG CAP & NHAP HANG ---");
            System.out.println("1. Danh sach nha cung cap");
            System.out.println("2. Them nha cung cap");
            System.out.println("3. Lap phieu nhap hang");
            System.out.println("4. Danh sach phieu nhap hang");
            System.out.println("5. Xem chi tiet phieu nhap");
            System.out.println("0. Quay lai");
            System.out.print("Chon [0-5]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> {
                    System.out.println("======================================== DANH SACH NHA CUNG CAP ========================================");
                    System.out.printf("%-10s | %-35s | %-12s | %s%n", "Ma NCC", "Ten nha cung cap", "So DT", "Dia chi");
                    System.out.println("--------------------------------------------------------------------------------------------------------");
                    for (NhaCungCap ncc : storage.getDsNhaCungCap()) {
                        System.out.printf("%-10s | %-35s | %-12s | %s%n",
                                ncc.getMaNhaCungCap(), ncc.getTenNhaCungCap(), ncc.getSoDienThoai(), ncc.getDiaChi());
                    }
                    System.out.println("========================================================================================================");
                }
                case "2" -> {
                    System.out.print("Ma NCC: ");
                    String ma = docDong();
                    if (storage.timNhaCungCap(ma) != null) {
                        System.out.println("Ma nha cung cap da ton tai!");
                        break;
                    }
                    System.out.print("Ten nha cung cap: ");
                    String ten = docDong();
                    System.out.print("So dien thoai: ");
                    String sdt = docDong();
                    System.out.print("Dia chi: ");
                    String dc = docDong();

                    NhaCungCap ncc = new NhaCungCap(ma, ten, sdt, dc);
                    if (storage.themNhaCungCap(ncc)) {
                        storage.luuNhaCungCap();
                        System.out.println("Them nha cung cap thanh cong.");
                    }
                }
                case "3" -> lapPhieuNhapHangConsole();
                case "4" -> quanLy.quanLyNhapHang().hienThiDanhSach();
                case "5" -> {
                    System.out.print("Nhap ma phieu nhap: ");
                    String ma = docDong();
                    NhapHang nh = quanLy.quanLyNhapHang().timKiemTheoMa(ma);
                    if (nh != null) {
                        nh.hienThiPhieuNhap();
                    } else {
                        System.out.println("Khong tim thay phieu nhap: " + ma);
                    }
                }
                case "0" -> { return; }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void lapPhieuNhapHangConsole() {
        System.out.println("\n-- LAP PHIEU NHAP HANG --");
        String maNH = "NH" + String.format("%03d", quanLy.quanLyNhapHang().getSoLuong() + 1);
        System.out.println("Ma phieu nhap: " + maNH);

        System.out.print("Ma nha cung cap: ");
        String maNCC = docDong();
        NhaCungCap ncc = storage.timNhaCungCap(maNCC);
        if (ncc == null) {
            System.out.println("Khong tim thay nha cung cap: " + maNCC);
            return;
        }

        NhapHang phieuNhap = new NhapHang(maNH, LocalDate.now(), ncc, quanLy);

        while (true) {
            System.out.print("Ma san pham (hoac 'xong' de ket thuc): ");
            String maSP = docDong();
            if (maSP.equalsIgnoreCase("xong")) break;

            SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(maSP);
            if (sp == null) {
                System.out.println("Khong tim thay san pham: " + maSP);
                continue;
            }

            System.out.println("San pham: " + sp.getTenSanPham() + " | Ton kho hien tai: " + sp.getSoLuongTonKho());
            int slNhap = 0;
            double giaNhap = 0;
            try {
                System.out.print("So luong nhap: ");
                slNhap = Integer.parseInt(docDong());
                System.out.print("Gia nhap (VND): ");
                giaNhap = Double.parseDouble(docDong());
            } catch (Exception e) {
                System.out.println("So luong hoac don gia khong hop le!");
                continue;
            }

            phieuNhap.themChiTietVaCapNhatTonKho(sp, slNhap, giaNhap);
            System.out.println("Da them " + slNhap + " cai. Ton kho moi: " + sp.getSoLuongTonKho());
        }

        if (phieuNhap.getDsChiTiet().length == 0) {
            System.out.println("Phieu nhap rong, huy thao tac.");
            return;
        }

        if (quanLy.quanLyNhapHang().them(phieuNhap)) {
            storage.luuNhapHang();
            storage.luuSanPham();
            System.out.println("Lap phieu nhap hang thanh cong.");
            phieuNhap.hienThiPhieuNhap();
        }
    }

    // =========================================================================
    // 4. BAN HANG & HOA DON
    // =========================================================================
    private static void menuBanHang() {
        while (true) {
            System.out.println("\n--- QUAN LY BAN HANG & HOA DON ---");
            System.out.println("1. Danh sach hoa don");
            System.out.println("2. Lap hoa don ban hang");
            System.out.println("3. Xem chi tiet hoa don");
            System.out.println("4. Quan ly phieu bao hanh");
            System.out.println("0. Quay lai");
            System.out.print("Chon [0-4]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> quanLy.quanLyDonHang().hienThiDanhSach();
                case "2" -> lapVaThanhToanHoaDonConsole();
                case "3" -> {
                    System.out.print("Nhap ma hoa don: ");
                    String ma = docDong();
                    HoaDon hd = quanLy.quanLyDonHang().timKiemTheoMa(ma);
                    if (hd != null) {
                        hd.hienThiHoaDon();
                    } else {
                        System.out.println("Khong tim thay hoa don: " + ma);
                    }
                }
                case "4" -> menuQuanLyBaoHanh();
                case "0" -> { return; }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void menuQuanLyBaoHanh() {
        while (true) {
            System.out.println("\n--- QUAN LY PHIEU BAO HANH ---");
            System.out.println("1. Danh sach phieu bao hanh");
            System.out.println("2. Cap nhat trang thai phieu bao hanh");
            System.out.println("0. Quay lai");
            System.out.print("Chon [0-2]: ");

            String c = docDong();
            switch (c) {
                case "1" -> {
                    System.out.println("============================================ DANH SACH PHIEU BAO HANH ============================================");
                    if (storage.getDsPhieuBaoHanh().length == 0) {
                        System.out.println("  (Chua co phieu bao hanh nao)");
                    } else {
                        for (PhieuBaoHanh pbh : storage.getDsPhieuBaoHanh()) {
                            pbh.hienThiThongTin();
                        }
                    }
                    System.out.println("==================================================================================================================");
                }
                case "2" -> {
                    System.out.print("Nhap ma phieu bao hanh can cap nhat: ");
                    String ma = docDong();
                    PhieuBaoHanh pbh = storage.timPhieuBaoHanh(ma);
                    if (pbh == null) {
                        System.out.println("Khong tim thay phieu bao hanh: " + ma);
                        break;
                    }
                    System.out.println("Phieu hien tai: " + pbh.getMaPhieuBH() + " | Trang thai: " + pbh.getTrangThai());
                    System.out.println("Chon trang thai moi:");
                    System.out.println("  1. Da tiep nhan");
                    System.out.println("  2. Dang xu ly");
                    System.out.println("  3. Da hoan thanh");
                    System.out.println("  4. Tu choi bao hanh");
                    System.out.print("Chon [1-4] hoac nhap truc tiep: ");
                    String ttChon = docDong();
                    String ttMoi = switch (ttChon) {
                        case "1" -> "Da tiep nhan";
                        case "2" -> "Dang xu ly";
                        case "3" -> "Da hoan thanh";
                        case "4" -> "Tu choi bao hanh";
                        default -> ttChon.isEmpty() ? pbh.getTrangThai() : ttChon;
                    };
                    pbh.capNhatTrangThai(ttMoi);
                    storage.luuPhieuBaoHanh();
                    System.out.println("Cap nhat trang thai phieu bao hanh thanh cong: " + ttMoi);
                }
                case "0" -> { return; }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void lapVaThanhToanHoaDonConsole() {
        System.out.println("\n-- LAP HOA DON BAN HANG --");
        String maHD = "HD" + String.format("%03d", quanLy.quanLyDonHang().getSoLuong() + 1);
        System.out.println("Ma hoa don: " + maHD);

        ThuNgan thuNgan = null;
        for (NhanVien nv : storage.getDsNhanVien()) {
            if (nv instanceof ThuNgan tn) {
                thuNgan = tn;
                break;
            }
        }
        if (thuNgan == null) {
            thuNgan = new ThuNgan("TN01", "Tran Thi Thu Ngan", "TP.HCM", "0923456789", 26, 1.5, "Quay 01");
        }

        System.out.print("Ma khach hang (de trong neu khach vang lai): ");
        String maKH = docDong();
        KhachHang kh = null;
        if (!maKH.isEmpty()) {
            kh = quanLy.quanLyKhachHang().timKiemTheoMa(maKH);
            if (kh != null) {
                System.out.println("Khach hang: " + kh.getHoTen() + " | Diem hien tai: " + kh.getDiemTichLuy());
            } else {
                System.out.println("Khong tim thay ma, tinh la khach vang lai.");
            }
        }

        System.out.print("Ma khuyen mai (de trong neu khong co): ");
        String maKM = docDong();
        KhuyenMai km = null;
        if (!maKM.isEmpty()) {
            km = storage.timKhuyenMai(maKM);
            if (km != null && km.conHieuLuc(LocalDate.now())) {
                System.out.println("Ap dung KM: " + km.getMaKhuyenMai() + " (-" + (int)(km.getTiLeGiam() * 100) + "%)");
            } else {
                System.out.println("Ma khuyen mai khong hop le hoac da het han.");
                km = null;
            }
        }

        HoaDon hd = new HoaDon(maHD, LocalDate.now(), thuNgan, kh, km);

        while (true) {
            System.out.print("Ma san pham (hoac 'xong' de thanh toan): ");
            String maSP = docDong();
            if (maSP.equalsIgnoreCase("xong")) break;

            SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(maSP);
            if (sp == null) {
                System.out.println("Khong tim thay san pham: " + maSP);
                continue;
            }

            if (!sp.conHanSuDung()) {
                System.out.println("San pham da het han su dung!");
                continue;
            }

            System.out.println("San pham: " + sp.getTenSanPham() + " | Gia: " + HoaDon.dinhDangTien(sp.tinhGia())
                    + " | Con ton: " + sp.getSoLuongTonKho());

            if (sp.getSoLuongTonKho() <= 0) {
                System.out.println("San pham da het hang trong kho!");
                continue;
            }

            System.out.print("So luong mua: ");
            int sl = 0;
            try {
                sl = Integer.parseInt(docDong());
            } catch (Exception e) {
                System.out.println("So luong khong hop le!");
                continue;
            }

            try {
                hd.themChiTiet(sp, sl);
                System.out.println("Da them vao hoa don.");
            } catch (Exception e) {
                System.out.println("Loi: " + e.getMessage());
            }
        }

        if (hd.getDsChiTiet().length == 0) {
            System.out.println("Hoa don trong, huy thao tac.");
            return;
        }

        hd.hienThiHoaDon();

        System.out.println("\nPhuong thuc thanh toan:");
        System.out.println("  1. Tien mat");
        System.out.println("  2. Chuyen khoan");
        System.out.println("  3. The ngan hang");
        System.out.print("Chon [1-3]: ");
        String ptChon = docDong();

        IPhuongThucThanhToan phuongThuc = new TienMat();
        double tongPhaiTra = hd.tinhTienPhaiTra();

        if ("2".equals(ptChon)) {
            String maGD = "CK" + System.currentTimeMillis();
            phuongThuc = new ChuyenKhoan(maGD);
        } else if ("3".equals(ptChon)) {
            System.out.print("Nhap so the (16 chu so): ");
            String soThe = docDong();
            phuongThuc = new TheNganHang(soThe);
        } else {
            // Thanh toan tien mat - ho tro nhap tien khach dua va tinh tien thoi lai
            System.out.println("Tong tien can thanh toan: " + HoaDon.dinhDangTien(tongPhaiTra));
            double tienKhachDua = 0;
            while (true) {
                System.out.print("Tien khach dua (VND): ");
                String kdStr = docDong();
                try {
                    tienKhachDua = Double.parseDouble(kdStr);
                    if (tienKhachDua < tongPhaiTra) {
                        System.out.println("Loi: So tien khach dua nho hon tong hoa don (" + HoaDon.dinhDangTien(tongPhaiTra) + ")!");
                        continue;
                    }
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Loi: So tien nhap vao khong hop le!");
                }
            }
            double tienThoi = tienKhachDua - tongPhaiTra;
            System.out.println("-> Tien thoi lai cho khach: " + HoaDon.dinhDangTien(tienThoi));
            phuongThuc = new TienMat();
        }

        boolean ttThanhCong = thuNgan.thanhToanHoaDon(hd, phuongThuc);
        if (ttThanhCong) {
            quanLy.quanLyDonHang().them(hd);

            for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
                SanPham sp = ct.getSanPham();
                if (sp instanceof DoDung dd) {
                    String maPBH = "PBH" + String.format("%03d", storage.getDsPhieuBaoHanh().length + 1);
                    PhieuBaoHanh pbh = new PhieuBaoHanh(maPBH, hd, dd, kh, "Bao hanh tieu chuan");
                    storage.themPhieuBaoHanh(pbh);
                    System.out.println("Da xuat phieu bao hanh: " + maPBH + " (" + dd.layThoiHanBaoHanh() + " thang)");
                }
            }

            storage.luuHoaDon();
            storage.luuSanPham();
            storage.luuKhachHang();
            storage.luuPhieuBaoHanh();

            System.out.println("Thanh toan thanh cong!");
            if (kh != null) {
                System.out.println("Diem tich luy moi cua " + kh.getHoTen() + ": " + kh.getDiemTichLuy());
            }
        } else {
            System.out.println("Thanh toan khong thanh cong!");
        }
    }

    // =========================================================================
    // 5. QUAN LY NHAN SU & CA LAM
    // =========================================================================
    private static void menuNhanSu() {
        while (true) {
            System.out.println("\n--- QUAN LY NHAN SU & CA LAM ---");
            System.out.println("1. Danh sach nhan vien & bang luong");
            System.out.println("2. Them nhan vien moi");
            System.out.println("3. Danh sach ca lam viec");
            System.out.println("4. Phan cong ca lam");
            System.out.println("5. Xoa ca lam viec");
            System.out.println("0. Quay lai");
            System.out.print("Chon [0-5]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> {
                    System.out.println("============================================= DANH SACH NHAN SU =============================================");
                    quanLy.hienThiThongTin();
                    for (NhanVien nv : storage.getDsNhanVien()) {
                        nv.hienThiThongTin();
                    }
                    System.out.println("=============================================================================================================");
                }
                case "2" -> themNhanVienConsole();
                case "3" -> hienThiDanhSachCaLamConsole();
                case "4" -> phanCongCaConsole();
                case "5" -> xoaCaLamConsole();
                case "0" -> { return; }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static void hienThiDanhSachCaLamConsole() {
        System.out.println("=================================== DANH SACH CA LAM VIEC ===================================");
        System.out.printf("%-8s | %-12s | %-15s | %s%n", "Ma ca", "Ngay lam", "Khung gio", "Nhan vien phu trach");
        System.out.println("---------------------------------------------------------------------------------------------");
        for (CaLam ca : storage.getDsCaLam()) {
            String tenNV = "Chua phan cong";
            for (NhanVien nv : storage.getDsNhanVien()) {
                if (nv.coCaLam(ca)) {
                    tenNV = nv.getHoTen() + " (" + nv.getMaNhanVien() + ")";
                    break;
                }
            }
            if (quanLy.coCaLam(ca)) {
                tenNV = quanLy.getHoTen() + " (" + quanLy.getMaNhanVien() + ")";
            }
            System.out.printf("%-8s | %-12s | %-15s | %s%n",
                    ca.getMaCa(), ca.getNgayLam(), ca.getGioBatDau() + " - " + ca.getGioKetThuc(), tenNV);
        }
        System.out.println("=============================================================================================");
    }

    private static void themNhanVienConsole() {
        System.out.println("\n-- Them nhan vien moi --");
        System.out.println("1. Nhan vien Part-time");
        System.out.println("2. Nhan vien Full-time");
        System.out.print("Chon loai [1-2]: ");
        String loai = docDong();

        System.out.print("Ma nhan vien: ");
        String ma = docDong();
        if (ma.isEmpty() || storage.timNhanVien(ma) != null) {
            System.out.println("Ma nhan vien khong hop le hoac da ton tai!");
            return;
        }

        System.out.print("Ho ten: ");
        String ten = docDong();
        System.out.print("Dia chi: ");
        String dc = docDong();
        System.out.print("So dien thoai: ");
        String sdt = docDong();

        NhanVien nv = null;
        switch (loai) {
            case "1" -> {
                System.out.print("Luong theo gio (VND): ");
                double luong = 30000;
                try {
                    String lStr = docDong();
                    if (!lStr.isEmpty()) luong = Double.parseDouble(lStr);
                } catch (NumberFormatException ignored) {}
                nv = new NhanVienPartTime(ma, ten, dc, sdt, 0, luong);
            }
            case "2" -> {
                System.out.println("Chon vi tri cu the:");
                System.out.println("  1. Quan ly");
                System.out.println("  2. Thu ngan");
                System.out.println("  3. Nhan vien Full-time khac (Kiem kho / Hau can)");
                System.out.print("Chon vi tri [1-3]: ");
                String vt = docDong();
                switch (vt) {
                    case "1" -> {
                        System.out.print("He so luong (mac dinh 2.0): ");
                        String hsStr = docDong();
                        double heSo = hsStr.isEmpty() ? 2.0 : Double.parseDouble(hsStr);
                        System.out.print("Phu cap trach nhiem (VND, mac dinh 3,000,000): ");
                        String pcStr = docDong();
                        double phuCap = pcStr.isEmpty() ? 3_000_000 : Double.parseDouble(pcStr);
                        nv = new QuanLy(ma, ten, dc, sdt, 26, heSo, phuCap);
                    }
                    case "2" -> {
                        System.out.print("Ma quay thu ngan (mac dinh Quay 01): ");
                        String quay = docDong();
                        if (quay.isEmpty()) quay = "Quay 01";
                        System.out.print("He so luong (mac dinh 1.2): ");
                        String hsStr = docDong();
                        double heSo = hsStr.isEmpty() ? 1.2 : Double.parseDouble(hsStr);
                        nv = new ThuNgan(ma, ten, dc, sdt, 26, heSo, quay);
                    }
                    default -> {
                        System.out.print("He so luong (mac dinh 1.0): ");
                        String hsStr = docDong();
                        double heSo = hsStr.isEmpty() ? 1.0 : Double.parseDouble(hsStr);
                        nv = new NhanVienFullTime(ma, ten, dc, sdt, 26, heSo);
                    }
                }
            }
            default -> {
                System.out.println("Loai nhan vien khong hop le!");
                return;
            }
        }

        if (storage.themNhanVien(nv)) {
            storage.luuNhanVien();
            System.out.println("Them nhan vien thanh cong.");
        }
    }

    private static void phanCongCaConsole() {
        System.out.println("\n-- Phan cong ca lam --");
        System.out.print("Ma nhan vien: ");
        String maNV = docDong();
        NhanVien nv = storage.timNhanVien(maNV);
        if (nv == null) {
            System.out.println("Khong tim thay nhan vien: " + maNV);
            return;
        }

        System.out.print("Ma ca lam: ");
        String maCa = docDong();
        if (maCa.isEmpty()) {
            System.out.println("Ma ca khong duoc de trong!");
            return;
        }

        CaLam caHienTai = storage.timCaLam(maCa);
        if (caHienTai != null) {
            System.out.println("Thong bao: Ma ca " + maCa + " da ton tai trong he thong ("
                    + caHienTai.getNgayLam() + " " + caHienTai.getGioBatDau() + " - " + caHienTai.getGioKetThuc() + ").");
            System.out.print("Ban co muon phan cong lai nhan vien nay cho ca do khong? (c/k): ");
            String xn = docDong();
            if (!xn.equalsIgnoreCase("c")) {
                System.out.println("Huy thao tac phan cong.");
                return;
            }
            // Go khoi cac nhan vien cu
            for (NhanVien n : storage.getDsNhanVien()) {
                n.xoaCaLam(caHienTai);
            }
            if (quanLy != null) {
                quanLy.xoaCaLam(caHienTai);
            }
            if (quanLy.phanCongCa(nv, caHienTai)) {
                storage.luuCaLam();
                storage.luuNhanVien();
                System.out.println("Phan cong lai ca lam cho " + nv.getHoTen() + " thanh cong.");
            } else {
                System.out.println("Khong the phan cong (trung gio lam cua nhan vien nay)!");
            }
            return;
        }

        LocalDate ngay = nhapNgayHopLe("Ngay lam (yyyy-MM-dd hoac dd-MM-yyyy): ", false, null);
        if (ngay == null) {
            System.out.println("Huy thao tac phan cong ca lam.");
            return;
        }

        LocalTime bd = nhapGioHopLe("Gio bat dau (HH:mm hoac H:mm, vi du 07:30 hoac 7:30): ");
        if (bd == null) {
            System.out.println("Huy thao tac phan cong ca lam.");
            return;
        }

        LocalTime kt = nhapGioHopLe("Gio ket thuc (HH:mm hoac H:mm, vi du 12:00): ");
        if (kt == null) {
            System.out.println("Huy thao tac phan cong ca lam.");
            return;
        }

        if (!kt.isAfter(bd)) {
            System.out.println("Loi: Gio ket thuc phai sau gio bat dau!");
            return;
        }

        CaLam caMoi = new CaLam(maCa, ngay, bd, kt);
        boolean phanCongOK = quanLy.phanCongCa(nv, caMoi);
        if (phanCongOK) {
            storage.themCaLam(caMoi);
            storage.luuCaLam();
            storage.luuNhanVien();
            System.out.println("Phan cong ca lam thanh cong.");
        } else {
            System.out.println("Phan cong that bai (trung ca lam voi ca khac cua nhan vien)!");
        }
    }

    private static void xoaCaLamConsole() {
        System.out.print("Nhap ma ca lam can xoa: ");
        String maCa = docDong();
        if (storage.xoaCaLam(maCa)) {
            storage.luuCaLam();
            storage.luuNhanVien();
            System.out.println("Xoa ca lam thanh cong: " + maCa);
        } else {
            System.out.println("Khong tim thay ca lam: " + maCa);
        }
    }

    // =========================================================================
    // 6. THONG KE & BAO CAO
    // =========================================================================
    private static void menuBaoCao() {
        while (true) {
            System.out.println("\n--- THONG KE & BAO CAO ---");
            System.out.println("1. Thong ke doanh thu theo khoang ngay");
            System.out.println("2. Thong ke chi phi nhap hang");
            System.out.println("3. Bao cao tai chinh tong hop");
            System.out.println("4. Bao cao ton kho");
            System.out.println("0. Quay lai");
            System.out.print("Chon [0-4]: ");

            String chon = docDong();
            switch (chon) {
                case "1" -> {
                    LocalDate[] k = nhapKhoangNgay();
                    if (k != null) {
                        double dt = quanLy.quanLyDonHang().thongKeDoanhThu(k[0], k[1]);
                        System.out.println("Doanh thu (" + k[0] + " den " + k[1] + "): " + HoaDon.dinhDangTien(dt));
                    }
                }
                case "2" -> {
                    LocalDate[] k = nhapKhoangNgay();
                    if (k != null) {
                        double cp = quanLy.quanLyNhapHang().thongKeChiPhiNhap(k[0], k[1]);
                        System.out.println("Chi phi nhap (" + k[0] + " den " + k[1] + "): " + HoaDon.dinhDangTien(cp));
                    }
                }
                case "3" -> {
                    LocalDate[] k = nhapKhoangNgay();
                    if (k != null) {
                        double dt = quanLy.quanLyDonHang().thongKeDoanhThu(k[0], k[1]);
                        double cp = quanLy.quanLyNhapHang().thongKeChiPhiNhap(k[0], k[1]);
                        double chenhLech = dt - cp;
                        System.out.println("\n================= BAO CAO TAI CHINH TONG HOP =================");
                        System.out.println("Thoi gian     : Tu " + k[0] + " den " + k[1]);
                        System.out.println("Doanh thu ban : " + HoaDon.dinhDangTien(dt));
                        System.out.println("Chi phi nhap  : " + HoaDon.dinhDangTien(cp));
                        System.out.println("Chenh lech    : " + HoaDon.dinhDangTien(chenhLech));
                        System.out.println("==============================================================");
                    }
                }
                case "4" -> {
                    System.out.println("\n-- TINH HINH TON KHO --");
                    System.out.print("Nhap nguong ton kho toi thieu de canh bao (mac dinh 50): ");
                    String ngStr = docDong();
                    int nguong = 50;
                    try {
                        if (!ngStr.isEmpty()) nguong = Integer.parseInt(ngStr);
                    } catch (NumberFormatException ignored) {}
                    quanLy.quanLySanPham().hienThiDanhSach();
                    quanLy.quanLySanPham().hienThiCanhBaoHetHang(nguong);
                }
                case "0" -> { return; }
                default -> System.out.println("Lua chon khong hop le!");
            }
        }
    }

    private static LocalDate[] nhapKhoangNgay() {
        System.out.println("Nhap khoang ngay thong ke (go Enter de lay mac dinh):");
        LocalDate tu = nhapNgayHopLe("Tu ngay (yyyy-MM-dd hoac dd-MM-yyyy, mac dinh 1 thang truoc): ", true, LocalDate.now().minusMonths(1));
        if (tu == null) return null;
        LocalDate den = nhapNgayHopLe("Den ngay (yyyy-MM-dd hoac dd-MM-yyyy, mac dinh hom nay): ", true, LocalDate.now());
        if (den == null) return null;
        return new LocalDate[]{tu, den};
    }

    // =========================================================================
    // 7. KIEM THU HE THONG
    // =========================================================================
    public static void chayKiemThuToanDien() {
        System.out.println("\n==================================================");
        System.out.println("            BAT DAU KIEM THU HE THONG             ");
        System.out.println("==================================================");

        System.out.println("\n=== 1. KIEM THU HOA DON & BAO HANH ===");
        KiemThuHoaDon.main(new String[]{});

        System.out.println("\n=== 2. KIEM THU SAN PHAM ===");
        KiemThuSanPham.main(new String[]{});

        System.out.println("\n=== 3. KIEM THU QUAN LY & CA LAM ===");
        KiemThuQuanLy.main(new String[]{});

        System.out.println("\n=== 4. KIEM THU LUU TRU FILE & TON KHO ===");
        KiemThuHeThongVaLuuTru.main(new String[]{});

        System.out.println("\n==================================================");
        System.out.println("     HOAN TAT KIEM THU: TAT CA MODULE DAT CHUAN   ");
        System.out.println("==================================================");
    }
}
