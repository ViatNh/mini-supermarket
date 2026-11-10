package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;

import manage.QuanLy;
import sieuthi.model.*;

/**
 * Chương trình chính tổng hợp cho toàn bộ hệ thống Siêu thị mini.
 * Tích hợp đầy đủ thành quả từ 4 nhánh:
 * - Nhánh feature/hoa-don (Hóa đơn, Chi tiết hóa đơn, Thanh toán, Phiếu bảo hành)
 * - Nhánh san-pham (Sản phẩm: Thực phẩm, Đồ uống, Đồ dùng, Bảo hành)
 * - Nhánh Person (Nhân sự: Person, Nhân viên Part-time, Full-time, Thu ngân, Ca làm)
 * - Nhánh vinh (Quản lý: IQuanLy, Quản lý Khách hàng, Sản phẩm, Đơn hàng, Quản lý)
 */
public class Main {
    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        if (args.length > 0 && args[0].equalsIgnoreCase("menu")) {
            chayMenuTuongTac();
        } else {
            chayDemoTongHop();
        }
    }

    public static void chayDemoTongHop() {
        LocalDate homNay = LocalDate.now();

        System.out.println("==========================================================================");
        System.out.println("             HỆ THỐNG QUẢN LÝ SIÊU THỊ MINI (TÍCH HỢP TẤT CẢ NHÁNH)        ");
        System.out.println("==========================================================================");

        // --- 1. Khởi tạo đối tượng Quản lý và các danh mục ---
        QuanLy quanLy = new QuanLy("QL01", "Phạm Văn Quản Lý", "Hà Nội", "0988888888", 26, 2.0, 3_000_000);

        // Dữ liệu mẫu sản phẩm (tích hợp từ nhánh san-pham)
        SanPham mi = new ThucPham("SP01", "Mì gói Hảo Hảo", "Việt Nam", 2026, "Acecook",
                100, 5_000, homNay.plusMonths(6));
        SanPham nuoc = new DoUong("SP02", "Nước khoáng Aquafina", "Việt Nam", 2026, "PepsiCo",
                50, 10_000, 500, homNay.plusMonths(12));
        SanPham am = new DoDung("SP03", "Ấm đun nước siêu tốc", "Việt Nam", 2025, "Sunhouse",
                10, 200_000, "Inox 304", 12);

        quanLy.quanLySanPham().them(mi);
        quanLy.quanLySanPham().them(nuoc);
        quanLy.quanLySanPham().them(am);

        // Dữ liệu mẫu khách hàng & nhân sự (tích hợp từ nhánh Person)
        KhachHang kh = new KhachHang("KH01", "Nguyễn Văn A", "TP.HCM", "0901234567");
        quanLy.quanLyKhachHang().them(kh);

        NhanVien nvPartTime = new NhanVienPartTime("NV_PT01", "Lê Bán Thời Gian", "TP.HCM", "0933333333", 40, 30_000);
        ThuNgan thuNgan = new ThuNgan("NV02", "Trần Thị Thu Ngân", "TP.HCM", "0923456789", 26, 1.5, "Q02");
        KhuyenMai km = new KhuyenMai("KM01", 0.10, homNay.minusDays(5), homNay.plusDays(5));

        // --- 2. Demo hiển thị danh mục qua module Quản lý ---
        System.out.println("\n--- [MODULE QUẢN LÝ] DANH SÁCH SẢN PHẨM & KHÁCH HÀNG ---");
        quanLy.quanLySanPham().hienThiDanhSach();
        System.out.println();
        quanLy.quanLyKhachHang().hienThiDanhSach();

        // --- 3. Demo phân công ca làm việc (nhánh Person & manage) ---
        System.out.println("\n--- [MODULE NHÂN SỰ & QUẢN LÝ] PHÂN CÔNG CA LÀM VIỆC ---");
        CaLam ca1 = new CaLam("CA01", homNay, LocalTime.of(7, 30), LocalTime.of(12, 0));
        CaLam caTrung = new CaLam("CA_TRUNG", homNay, LocalTime.of(10, 0), LocalTime.of(15, 0));
        CaLam ca2 = new CaLam("CA02", homNay, LocalTime.of(13, 0), LocalTime.of(17, 30));

        quanLy.phanCongCa(thuNgan, ca1);
        quanLy.phanCongCa(thuNgan, caTrung); // Bị từ chối vì trùng giờ
        quanLy.phanCongCa(thuNgan, ca2);

        // Hiển thị nhân sự
        System.out.println("\nThông tin nhân viên bán thời gian:");
        nvPartTime.hienThiThongTin();

        // --- 4. Demo Lập hóa đơn & Thu ngân thanh toán (nhánh hoa-don) ---
        System.out.println("\n--- [MODULE HÓA ĐƠN] LẬP VÀ THANH TOÁN HÓA ĐƠN ---");
        HoaDon hd = new HoaDon("HD001", homNay, thuNgan, kh, km);
        hd.themChiTiet(mi, 10);
        hd.themChiTiet(nuoc, 2);
        hd.themChiTiet(am, 1);
        hd.hienThiHoaDon();

        System.out.println("\nThu ngân thu tiền bằng tiền mặt: "
                + (thuNgan.thanhToanHoaDon(hd, new TienMat()) ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println();
        hd.hienThiHoaDon();

        // Thêm hóa đơn vào Quản lý đơn hàng
        quanLy.quanLyDonHang().them(hd);

        // --- 5. Thông tin cập nhật sau giao dịch ---
        System.out.println("\n--- THÔNG TIN CẬP NHẬT SAU GIAO DỊCH ---");
        System.out.println("Tồn kho Mì gói còn lại: " + mi.getSoLuongTonKho());
        kh.hienThiThongTin();

        // Lập phiếu bảo hành cho đồ dùng (nhánh san-pham & hoa-don)
        System.out.println("\n--- LẬP PHIẾU BẢO HÀNH CHO ĐỒ DÙNG ---");
        PhieuBaoHanh pbh = new PhieuBaoHanh("BH001", hd, am, kh, "Ấm không nóng");
        pbh.hienThiThongTin();
        pbh.capNhatTrangThai("Đang sửa chữa");
        pbh.hienThiThongTin();

        // --- 6. Thống kê doanh thu qua Quản lý đơn hàng ---
        System.out.println("\n--- [MODULE QUẢN LÝ] THỐNG KÊ DOANH THU & DANH SÁCH ĐƠN HÀNG ---");
        quanLy.quanLyDonHang().hienThiDanhSach();
        double doanhThu = quanLy.quanLyDonHang().thongKeDoanhThu(homNay.minusDays(1), homNay.plusDays(1));
        System.out.println(">> TỔNG DOANH THU THỰC TẾ: " + HoaDon.dinhDangTien(doanhThu));

        // --- 7. Bảng lương Quản lý ---
        System.out.println("\n--- THÔNG TIN NHÂN SỰ QUẢN LÝ ---");
        quanLy.hienThiThongTin();
        System.out.println("==========================================================================");
        System.out.println("Chạy 'run.bat test' để chạy toàn bộ 80 ca kiểm thử tự động.");
        System.out.println("Chạy 'run.bat menu' để vào menu tương tác quản lý nhân sự.");
    }

    public static void chayMenuTuongTac() {
        Scanner sc = new Scanner(System.in);
        MenuNhanSu menuNS = new MenuNhanSu();
        int chon = -1;

        do {
            System.out.println("\n=======================================================");
            System.out.println("            MENU HỆ THỐNG SIÊU THỊ MINI                ");
            System.out.println("=======================================================");
            System.out.println("1. Chạy Demo tổng hợp (Tất cả tính năng)");
            System.out.println("2. Quản lý Nhân sự (nhánh Person)");
            System.out.println("3. Chạy kiểm thử Hóa đơn (KiemThuHoaDon)");
            System.out.println("4. Chạy kiểm thử Quản lý (KiemThuQuanLy)");
            System.out.println("5. Chạy kiểm thử Sản phẩm (KiemThuSanPham)");
            System.out.println("0. Thoát");
            System.out.print("Lựa chọn của bạn: ");
            try {
                chon = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1:
                    chayDemoTongHop();
                    break;
                case 2:
                    menuNS.chayMenu(sc);
                    break;
                case 3:
                    KiemThuHoaDon.main(new String[0]);
                    break;
                case 4:
                    KiemThuQuanLy.main(new String[0]);
                    break;
                case 5:
                    KiemThuSanPham.main(new String[0]);
                    break;
                case 0:
                    System.out.println("Tạm biệt!");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }
}
