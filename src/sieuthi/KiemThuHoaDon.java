package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import sieuthi.model.*;

/** Kiểm thử nhanh (không cần JUnit). Chạy: xem README. Thoát mã 1 nếu có test fail. */
public class KiemThuHoaDon {
    private static int pass = 0;
    private static int fail = 0;

    private static void kiemTra(String ten, boolean dieuKien) {
        if (dieuKien) { pass++; System.out.println("  [OK]   " + ten); }
        else { fail++; System.out.println("  [FAIL] " + ten); }
    }

    private static boolean nem(Runnable r, Class<? extends Exception> loai) {
        try { r.run(); return false; }
        catch (Exception e) { return loai.isInstance(e); }
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        LocalDate homNay = LocalDate.now();
        SanPham mi = new ThucPham("SP01", "Mì gói", "VN", 2026, "Hảo Hảo", 100, 5_000, homNay.plusMonths(6));
        SanPham nuoc = new DoUong("SP02", "Nước suối", "VN", 2026, "Aquafina", 50, 10_000, 500, homNay.plusMonths(12));
        SanPham am = new DoDung("SP03", "Ấm", "TQ", 2025, "Sunhouse", 10, 200_000, "Inox", 12);
        KhachHang kh = new KhachHang("KH01", "A", "HCM", "0901");
        ThuNgan tn = new ThuNgan("NV01", "B", "HCM", "0902", 26, 1.5, "Q01");
        KhuyenMai km = new KhuyenMai("KM01", 0.10, homNay.minusDays(1), homNay.plusDays(1));

        System.out.println("Tính tiền:");
        HoaDon hd = new HoaDon("HD1", homNay, tn, kh, km);
        hd.themChiTiet(mi, 10);
        hd.themChiTiet(nuoc, 2);
        hd.themChiTiet(am, 1);
        kiemTra("tổng tiền = 270.000", hd.tinhTongTien() == 270_000);
        kiemTra("tiền giảm 10% = 27.000", Math.abs(hd.tinhTienGiam() - 27_000) < 1e-6);
        kiemTra("phải trả = 243.000", Math.abs(hd.tinhTienPhaiTra() - 243_000) < 1e-6);

        System.out.println("Cộng dồn & ràng buộc:");
        hd.themChiTiet(mi, 5);
        kiemTra("thêm cùng sản phẩm thì cộng dồn (vẫn 3 dòng)", hd.getDsChiTiet().size() == 3);
        kiemTra("vượt tồn kho bị từ chối", nem(() -> hd.themChiTiet(am, 99), IllegalStateException.class));
        kiemTra("số lượng <= 0 bị từ chối", nem(() -> hd.themChiTiet(nuoc, 0), IllegalArgumentException.class));
        SanPham het = new ThucPham("SP09", "Sữa hết hạn", "VN", 2025, "X", 5, 20_000, homNay.minusDays(1));
        kiemTra("sản phẩm hết hạn bị từ chối", nem(() -> hd.themChiTiet(het, 1), IllegalStateException.class));

        System.out.println("Thanh toán:");
        kiemTra("thẻ sai số bị từ chối", !tn.thanhToanHoaDon(hd, new TheNganHang("123")));
        kiemTra("chuyển khoản thiếu mã bị từ chối", !tn.thanhToanHoaDon(hd, new ChuyenKhoan(" ")));
        kiemTra("hóa đơn vẫn chưa thanh toán sau khi thất bại", !hd.isDaThanhToan() && mi.getSoLuongTonKho() == 100);
        double phaiTra = hd.tinhTienPhaiTra();
        kiemTra("tiền mặt thành công", tn.thanhToanHoaDon(hd, new TienMat()));
        kiemTra("đã ghi nhận thu ngân", hd.getNguoiThanhToan() == tn);
        kiemTra("trừ tồn kho (mì 100 -> 85)", mi.getSoLuongTonKho() == 85);
        kiemTra("tích điểm khách hàng", kh.getDiemTichLuy() == (int) (phaiTra / 10_000));
        kiemTra("không thanh toán lần 2", !tn.thanhToanHoaDon(hd, new TienMat()));
        kiemTra("không sửa hóa đơn đã thanh toán", nem(() -> hd.themChiTiet(mi, 1), IllegalStateException.class));

        System.out.println("Khuyến mãi / giá đặc biệt:");
        KhuyenMai kmHetHan = new KhuyenMai("KM02", 0.5, homNay.minusDays(10), homNay.minusDays(5));
        HoaDon hd2 = new HoaDon("HD2", homNay, tn, null, kmHetHan);
        hd2.themChiTiet(nuoc, 1);
        kiemTra("khuyến mãi hết hạn không giảm", hd2.tinhTienGiam() == 0);
        SanPham canDate = new ThucPham("SP08", "Sữa cận date", "VN", 2026, "X", 5, 10_000, homNay.plusDays(2));
        kiemTra("thực phẩm cận date giảm 30%", Math.abs(canDate.tinhGia() - 7_000) < 1e-6);

        System.out.println("Phiếu bảo hành:");
        PhieuBaoHanh pbh = new PhieuBaoHanh("BH1", hd, am, kh, "Lỗi");
        kiemTra("tạo phiếu cho đồ dùng có bảo hành, còn hạn", pbh.kiemTraConHan());
        kiemTra("hạn bảo hành = ngày lập + 12 tháng", pbh.getNgayKetThuc().equals(homNay.plusMonths(12)));
        kiemTra("sản phẩm không bảo hành bị từ chối",
                nem(() -> new PhieuBaoHanh("BH2", hd, mi, kh, "x"), IllegalArgumentException.class));
        kiemTra("hóa đơn chưa thanh toán bị từ chối",
                nem(() -> new PhieuBaoHanh("BH3", hd2, nuoc, null, "x"), IllegalStateException.class));

        System.out.println("\nKết quả: " + pass + " đạt, " + fail + " lỗi");
        if (fail > 0) System.exit(1);
    }
}
