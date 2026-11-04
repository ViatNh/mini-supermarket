package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import sieuthi.model.*;

/** Demo phần Hóa đơn: lập hóa đơn -> thu ngân thanh toán -> tích điểm -> phiếu bảo hành. */
public class Main {
    public static void main(String[] args) {
        // Ép console in UTF-8 để không lỗi font tiếng Việt trên Windows
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));

        LocalDate homNay = LocalDate.now();

        // --- Dữ liệu mẫu ---
        SanPham mi = new ThucPham("SP01", "Mì gói", "Việt Nam", 2026, "Hảo Hảo",
                100, 5_000, homNay.plusMonths(6));
        SanPham nuoc = new DoUong("SP02", "Nước suối", "Việt Nam", 2026, "Aquafina",
                50, 10_000, 500, homNay.plusMonths(12));
        SanPham am = new DoDung("SP03", "Ấm đun nước", "Trung Quốc", 2025, "Sunhouse",
                10, 200_000, "Inox", 12);

        KhachHang kh = new KhachHang("KH01", "Nguyễn Văn A", "TP.HCM", "0901234567");
        NhanVien nguoiLap = new ThuNgan("NV01", "Trần Thị B", "TP.HCM", "0912345678", 26, 1.5, "Q01");
        ThuNgan thuNgan = new ThuNgan("NV02", "Lê Văn C", "TP.HCM", "0923456789", 26, 1.5, "Q02");
        KhuyenMai km = new KhuyenMai("KM01", 0.10, homNay.minusDays(5), homNay.plusDays(5));

        // --- Lập hóa đơn ---
        HoaDon hd = new HoaDon("HD001", homNay, nguoiLap, kh, km);
        hd.themChiTiet(mi, 10);
        hd.themChiTiet(nuoc, 2);
        hd.themChiTiet(am, 1);
        hd.hienThiHoaDon();

        // --- Thu ngân thanh toán ---
        System.out.println("\nThanh toán bằng thẻ sai số thẻ: "
                + (thuNgan.thanhToanHoaDon(hd, new TheNganHang("123")) ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println("Thanh toán bằng tiền mặt     : "
                + (thuNgan.thanhToanHoaDon(hd, new TienMat()) ? "THÀNH CÔNG" : "THẤT BẠI"));
        System.out.println();
        hd.hienThiHoaDon();

        // --- Kết quả sau thanh toán ---
        System.out.println("\nTồn kho Mì gói còn: " + mi.getSoLuongTonKho());
        kh.hienThiThongTin();

        // --- Phiếu bảo hành ---
        System.out.println();
        PhieuBaoHanh pbh = new PhieuBaoHanh("BH001", hd, am, kh, "Ấm không nóng");
        pbh.hienThiThongTin();
        pbh.capNhatTrangThai("Đang sửa");
        pbh.hienThiThongTin();
    }
}
