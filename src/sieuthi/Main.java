package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;

import manage.QuanLy;
import sieuthi.model.*;

/**
 * Chương trình demo Siêu thị mini:
 * - Module Hóa đơn: lập hóa đơn, thu ngân thanh toán, trừ tồn kho, tích điểm, lập phiếu bảo hành.
 * - Module Quản lý: Quản lý khách hàng, sản phẩm, đơn hàng, thống kê doanh thu, phân công ca làm việc.
 */
public class Main {
    public static void main(String[] args) {
        // Ép console in UTF-8 để không lỗi font tiếng Việt trên Windows
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        LocalDate homNay = LocalDate.now();

        System.out.println("==========================================================================");
        System.out.println("                 HỆ THỐNG QUẢN LÝ SIÊU THỊ MINI                           ");
        System.out.println("==========================================================================");

        // --- 1. Khởi tạo đối tượng Quản lý và các danh mục ---
        QuanLy quanLy = new QuanLy("QL01", "Phạm Văn Quản Lý", "Hà Nội", "0988888888", 26, 2.0, 3_000_000);

        // Dữ liệu mẫu sản phẩm
        SanPham mi = new ThucPham("SP01", "Mì gói Hảo Hảo", "Việt Nam", 2026, "Acecook",
                100, 5_000, homNay.plusMonths(6));
        SanPham nuoc = new DoUong("SP02", "Nước khoáng Aquafina", "Việt Nam", 2026, "PepsiCo",
                50, 10_000, 500, homNay.plusMonths(12));
        SanPham am = new DoDung("SP03", "Ấm đun nước siêu tốc", "Việt Nam", 2025, "Sunhouse",
                10, 200_000, "Inox", 12);

        quanLy.quanLySanPham().them(mi);
        quanLy.quanLySanPham().them(nuoc);
        quanLy.quanLySanPham().them(am);

        // Dữ liệu mẫu khách hàng & nhân viên
        KhachHang kh = new KhachHang("KH01", "Nguyễn Văn A", "TP.HCM", "0901234567");
        quanLy.quanLyKhachHang().them(kh);

        NhanVien nguoiLap = new ThuNgan("NV01", "Trần Thị B", "TP.HCM", "0912345678", 26, 1.5, "Q01");
        ThuNgan thuNgan = new ThuNgan("NV02", "Lê Văn C", "TP.HCM", "0923456789", 26, 1.5, "Q02");
        KhuyenMai km = new KhuyenMai("KM01", 0.10, homNay.minusDays(5), homNay.plusDays(5));

        // --- 2. Demo hiển thị danh mục qua module Quản lý ---
        System.out.println("\n--- [MODULE QUẢN LÝ] DANH SÁCH SẢN PHẨM & KHÁCH HÀNG BAN ĐẦU ---");
        quanLy.quanLySanPham().hienThiDanhSach();
        System.out.println();
        quanLy.quanLyKhachHang().hienThiDanhSach();

        // --- 3. Demo phân công ca làm việc ---
        System.out.println("\n--- [MODULE QUẢN LÝ] PHÂN CÔNG CA LÀM VIỆC ---");
        CaLam ca1 = new CaLam("CA01", homNay, LocalTime.of(7, 30), LocalTime.of(12, 0));
        CaLam caTrung = new CaLam("CA_TRUNG", homNay, LocalTime.of(10, 0), LocalTime.of(15, 0));
        CaLam ca2 = new CaLam("CA02", homNay, LocalTime.of(13, 0), LocalTime.of(17, 30));

        quanLy.phanCongCa(thuNgan, ca1);
        quanLy.phanCongCa(thuNgan, caTrung); // Bị từ chối vì trùng giờ
        quanLy.phanCongCa(thuNgan, ca2);

        // --- 4. Demo Lập hóa đơn & Thu ngân thanh toán ---
        System.out.println("\n--- [MODULE HÓA ĐƠN] LẬP VÀ THANH TOÁN HÓA ĐƠN ---");
        HoaDon hd = new HoaDon("HD001", homNay, nguoiLap, kh, km);
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

        // --- 5. Kiểm tra thông tin cập nhật sau thanh toán ---
        System.out.println("\n--- THÔNG TIN CẬP NHẬT SAU GIAO DỊCH ---");
        System.out.println("Tồn kho Mì gói còn lại: " + mi.getSoLuongTonKho());
        kh.hienThiThongTin();

        // Phiếu bảo hành
        System.out.println("\n--- LẬP PHIẾU BẢO HÀNH CHO ĐỒ DÙNG ---");
        PhieuBaoHanh pbh = new PhieuBaoHanh("BH001", hd, am, kh, "Ấm không nóng");
        pbh.hienThiThongTin();
        pbh.capNhatTrangThai("Đang sửa");
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
    }
}
