package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import sieuthi.model.*;

/** Kiem thu nhanh (khong can JUnit). Chay: xem README. Thoat ma 1 neu co test fail. */
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
        SanPham mi = new ThucPham("SP01", "Mi goi", "VN", 2026, "Hao Hao", 100, 5_000, homNay.plusMonths(6));
        SanPham nuoc = new DoUong("SP02", "Nuoc suoi", "VN", 2026, "Aquafina", 50, 10_000, 500, homNay.plusMonths(12));
        SanPham am = new DoDung("SP03", "Am", "TQ", 2025, "Sunhouse", 10, 200_000, "Inox", 12);
        KhachHang kh = new KhachHang("KH01", "A", "HCM", "0901");
        ThuNgan tn = new ThuNgan("NV01", "B", "HCM", "0902", 26, 1.5, "Q01");
        KhuyenMai km = new KhuyenMai("KM01", 0.10, homNay.minusDays(1), homNay.plusDays(1));

        System.out.println("Tinh tien:");
        HoaDon hd = new HoaDon("HD1", homNay, tn, kh, km);
        hd.themChiTiet(mi, 10);
        hd.themChiTiet(nuoc, 2);
        hd.themChiTiet(am, 1);
        kiemTra("tong tien = 270.000", hd.tinhTongTien() == 270_000);
        kiemTra("tien giam 10% = 27.000", Math.abs(hd.tinhTienGiam() - 27_000) < 1e-6);
        kiemTra("phai tra = 243.000", Math.abs(hd.tinhTienPhaiTra() - 243_000) < 1e-6);

        System.out.println("Cong don & rang buoc:");
        hd.themChiTiet(mi, 5);
        kiemTra("them cung san pham thi cong don (van 3 dong)", hd.getDsChiTiet().size() == 3);
        kiemTra("vuot ton kho bi tu choi", nem(() -> hd.themChiTiet(am, 99), IllegalStateException.class));
        kiemTra("so luong <= 0 bi tu choi", nem(() -> hd.themChiTiet(nuoc, 0), IllegalArgumentException.class));
        SanPham het = new ThucPham("SP09", "Sua het han", "VN", 2025, "X", 5, 20_000, homNay.minusDays(1));
        kiemTra("san pham het han bi tu choi", nem(() -> hd.themChiTiet(het, 1), IllegalStateException.class));

        System.out.println("Thanh toan:");
        kiemTra("the sai so bi tu choi", !tn.thanhToanHoaDon(hd, new TheNganHang("123")));
        kiemTra("chuyen khoan thieu ma bi tu choi", !tn.thanhToanHoaDon(hd, new ChuyenKhoan(" ")));
        kiemTra("hoa don van chua thanh toan sau khi that bai", !hd.isDaThanhToan() && mi.getSoLuongTonKho() == 100);
        double phaiTra = hd.tinhTienPhaiTra();
        kiemTra("tien mat thanh cong", tn.thanhToanHoaDon(hd, new TienMat()));
        kiemTra("da ghi nhan thu ngan", hd.getNguoiThanhToan() == tn);
        kiemTra("tru ton kho (mi 100 -> 85)", mi.getSoLuongTonKho() == 85);
        kiemTra("tich diem khach hang", kh.getDiemTichLuy() == (int) (phaiTra / 10_000));
        kiemTra("khong thanh toan lan 2", !tn.thanhToanHoaDon(hd, new TienMat()));
        kiemTra("khong sua hoa don da thanh toan", nem(() -> hd.themChiTiet(mi, 1), IllegalStateException.class));

        System.out.println("Khuyen mai / gia dac biet:");
        KhuyenMai kmHetHan = new KhuyenMai("KM02", 0.5, homNay.minusDays(10), homNay.minusDays(5));
        HoaDon hd2 = new HoaDon("HD2", homNay, tn, null, kmHetHan);
        hd2.themChiTiet(nuoc, 1);
        kiemTra("khuyen mai het han khong giam", hd2.tinhTienGiam() == 0);
        SanPham canDate = new ThucPham("SP08", "Sua can date", "VN", 2026, "X", 5, 10_000, homNay.plusDays(2));
        kiemTra("thuc pham can date giam 30%", Math.abs(canDate.tinhGia() - 7_000) < 1e-6);

        System.out.println("Phieu bao hanh:");
        PhieuBaoHanh pbh = new PhieuBaoHanh("BH1", hd, am, kh, "Loi");
        kiemTra("tao phieu cho do dung co bao hanh, con han", pbh.kiemTraConHan());
        kiemTra("han bao hanh = ngay lap + 12 thang", pbh.getNgayKetThuc().equals(homNay.plusMonths(12)));
        kiemTra("san pham khong bao hanh bi tu choi",
                nem(() -> new PhieuBaoHanh("BH2", hd, mi, kh, "x"), IllegalArgumentException.class));
        kiemTra("hoa don chua thanh toan bi tu choi",
                nem(() -> new PhieuBaoHanh("BH3", hd2, nuoc, null, "x"), IllegalStateException.class));

        System.out.println("\nKet qua: " + pass + " dat, " + fail + " loi");
        if (fail > 0) System.exit(1);
    }
}
