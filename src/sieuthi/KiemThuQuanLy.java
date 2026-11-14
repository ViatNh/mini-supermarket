package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import sieuthi.manage.*;
import sieuthi.model.*;

/**
 * Bo kiem thu toan dien cho module Quan ly (package manage).
 * Kiem tra: IQuanLy, QuanLyKhachHang, QuanLySanPham, QuanLyDonHang, QuanLy.
 */
public class KiemThuQuanLy {
    private static int dat = 0;
    private static int loi = 0;

    private static void kiemTra(String ten, boolean b) {
        if (b) {
            System.out.println("  [OK]   " + ten);
            dat++;
        } else {
            System.out.println("  [LOI]  " + ten);
            loi++;
        }
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("==================================================");
        System.out.println("       BAT DAU KIEM THU MODULE QUAN LY           ");
        System.out.println("==================================================");

        LocalDate homNay = LocalDate.now();

        // ============================================================
        // 1. KIEM THU QUAN LY KHACH HANG
        // ============================================================
        System.out.println("\n1. Quan ly khach hang (QuanLyKhachHang):");
        QuanLyKhachHang qlkh = new QuanLyKhachHang();
        KhachHang kh1 = new KhachHang("KH01", "Nguyen Van A", "Ha Noi", "0901234567");
        KhachHang kh2 = new KhachHang("KH02", "Tran Thi B", "Da Nang", "0912345678");
        KhachHang kh3 = new KhachHang("KH03", "Le Van C", "TP.HCM", "0923456789");

        kiemTra("them KH01 thanh cong", qlkh.them(kh1));
        kiemTra("them KH02 thanh cong", qlkh.them(kh2));
        kiemTra("them trung ma KH01 bi tu choi", !qlkh.them(new KhachHang("KH01", "Nguoi Khac", "Hue", "0999999999")));
        kiemTra("them null bi tu choi", !qlkh.them(null));
        kiemTra("so luong khach hang = 2", qlkh.getSoLuong() == 2);

        KhachHang timMa = qlkh.timKiemTheoMa("kh01");
        kiemTra("tim kiem theo ma KH01 (khong phan biet hoa thuong)", timMa != null && timMa.getHoTen().equals("Nguyen Van A"));
        kiemTra("tim ma khong ton tai tra ve null", qlkh.timKiemTheoMa("KH99") == null);

        KhachHang timSDT = qlkh.timKiemTheoSDT("0912345678");
        kiemTra("tim kiem theo SDT thanh cong", timSDT != null && timSDT.getMaKhachHang().equals("KH02"));
        kiemTra("tim SDT khong ton tai tra ve null", qlkh.timKiemTheoSDT("0000000000") == null);

        KhachHang kh1Moi = new KhachHang("KH01", "Nguyen Van A (VIP)", "Ha Noi", "0901234567");
        kiemTra("sua thong tin KH01 thanh cong", qlkh.sua("KH01", kh1Moi));
        kiemTra("thong tin KH01 sau khi sua da cap nhat", qlkh.timKiemTheoMa("KH01").getHoTen().contains("VIP"));
        kiemTra("sua ma khong ton tai bi tu choi", !qlkh.sua("KH99", kh1Moi));

        kiemTra("xoa KH02 thanh cong", qlkh.xoa("KH02"));
        kiemTra("sau khi xoa, KH02 khong con trong danh sach", qlkh.timKiemTheoMa("KH02") == null);
        kiemTra("xoa ma khong ton tai bi tu choi", !qlkh.xoa("KH99"));
        kiemTra("so luong khach hang sau khi xoa = 1", qlkh.getSoLuong() == 1);

        // ============================================================
        // 2. KIEM THU QUAN LY SAN PHAM
        // ============================================================
        System.out.println("\n2. Quan ly san pham (QuanLySanPham):");
        QuanLySanPham qlsp = new QuanLySanPham();
        SanPham sp1 = new ThucPham("SP01", "Mi Hao Hao Tom Chua Cay", "Viet Nam", 2026, "Acecook", 100, 5000, homNay.plusMonths(6));
        SanPham sp2 = new DoUong("SP02", "Nuoc ngot Coca Cola", "Viet Nam", 2026, "CocaCola", 50, 10000, 330, homNay.plusMonths(12));
        SanPham sp3 = new DoDung("SP03", "Am sieu toc Inox", "Viet Nam", 2025, "Sunhouse", 20, 250000, "Inox", 12);
        SanPham sp4 = new ThucPham("SP04", "Mi Omachi Xot Bo Ham", "Viet Nam", 2026, "Masan", 80, 8000, homNay.plusMonths(5));

        kiemTra("them san pham SP01 thanh cong", qlsp.them(sp1));
        kiemTra("them san pham SP02 thanh cong", qlsp.them(sp2));
        kiemTra("them san pham SP03 thanh cong", qlsp.them(sp3));
        kiemTra("them san pham SP04 thanh cong", qlsp.them(sp4));
        kiemTra("them trung ma SP01 bi tu choi", !qlsp.them(sp1));
        kiemTra("them san pham null bi tu choi", !qlsp.them(null));
        kiemTra("so luong san pham = 4", qlsp.getSoLuong() == 4);

        SanPham timSP = qlsp.timKiemTheoMa("sp03");
        kiemTra("tim kiem san pham theo ma SP03", timSP != null && timSP.getTenSanPham().equals("Am sieu toc Inox"));

        List<SanPham> timTheoTen = qlsp.timKiemTheoTen("mi");
        kiemTra("tim theo ten 'mi' ra dung 2 san pham", timTheoTen.size() == 2);

        SanPham sp1Moi = new ThucPham("SP01", "Mi Hao Hao Sa Te", "Viet Nam", 2026, "Acecook", 120, 5500, homNay.plusMonths(6));
        kiemTra("sua san pham SP01 thanh cong", qlsp.sua("SP01", sp1Moi));
        kiemTra("ten san pham SP01 sau sua doi thanh 'Mi Hao Hao Sa Te'", qlsp.timKiemTheoMa("SP01").getTenSanPham().equals("Mi Hao Hao Sa Te"));

        kiemTra("xoa san pham SP04 thanh cong", qlsp.xoa("SP04"));
        kiemTra("SP04 khong con sau khi xoa", qlsp.timKiemTheoMa("SP04") == null);
        kiemTra("so luong san pham con lai = 3", qlsp.getSoLuong() == 3);

        // ============================================================
        // 3. KIEM THU QUAN LY DON HANG
        // ============================================================
        System.out.println("\n3. Quan ly don hang (QuanLyDonHang):");
        QuanLyDonHang qldh = new QuanLyDonHang();
        NhanVien thuNgan = new ThuNgan("NV01", "Le Thu Ngan", "Ha Noi", "0987654321", 26, 1.2, "Q01");

        HoaDon hd1 = new HoaDon("HD01", homNay.minusDays(2), thuNgan, kh1, null);
        hd1.themChiTiet(sp1Moi, 10); // 10 * 5500 = 55.000
        ((ThuNgan) thuNgan).thanhToanHoaDon(hd1, new TienMat()); // Da thanh toan: 55.000

        HoaDon hd2 = new HoaDon("HD02", homNay.minusDays(1), thuNgan, kh3, null);
        hd2.themChiTiet(sp2, 5); // 5 * 10000 = 50.000
        ((ThuNgan) thuNgan).thanhToanHoaDon(hd2, new TienMat()); // Da thanh toan: 50.000

        HoaDon hd3 = new HoaDon("HD03", homNay, thuNgan, kh1, null);
        hd3.themChiTiet(sp3, 1); // 250.000 - CHUA thanh toan

        kiemTra("them hoa don HD01 thanh cong", qldh.them(hd1));
        kiemTra("them hoa don HD02 thanh cong", qldh.them(hd2));
        kiemTra("them hoa don HD03 thanh cong", qldh.them(hd3));
        kiemTra("them trung ma HD01 bi tu choi", !qldh.them(hd1));
        kiemTra("so luong don hang = 3", qldh.getSoLuong() == 3);

        HoaDon timHD = qldh.timKiemTheoMa("hd02");
        kiemTra("tim kiem theo ma HD02 thanh cong", timHD != null && timHD.isDaThanhToan());

        // Thong ke doanh thu tu (hom nay - 3 ngay) den hom nay
        // Chi tinh HD01 (55.000) + HD02 (50.000) = 105.000 (HD03 chua thanh toan nen khong tinh)
        double doanhThu = qldh.thongKeDoanhThu(homNay.minusDays(3), homNay);
        kiemTra("thong ke doanh thu 3 ngay = 105.000 VND (chi tinh don da thanh toan)", Math.abs(doanhThu - 105_000) < 0.001);

        double doanhThuHomNay = qldh.thongKeDoanhThu(homNay, homNay);
        kiemTra("doanh thu hom nay = 0 VND vi don hom nay chua thanh toan", doanhThuHomNay == 0);

        List<HoaDon> dsHDTheoKH1 = qldh.locDonHangTheoKhachHang("KH01");
        kiemTra("loc don hang cua KH01 ra dung 2 hoa don", dsHDTheoKH1.size() == 2);

        kiemTra("xoa don hang HD03 thanh cong", qldh.xoa("HD03"));
        kiemTra("sau xoa so luong don = 2", qldh.getSoLuong() == 2);

        // ============================================================
        // 4. KIEM THU LOP QUAN LY (QuanLy) & PHAN CONG CA LAM
        // ============================================================
        System.out.println("\n4. Lop Quan ly & Phan cong ca lam (QuanLy & CaLam):");
        QuanLy quanLy = new QuanLy("QL01", "Tran Quan Ly", "Ha Noi", "0911223344", 26, 2.0, 3_000_000,
                qlkh, qlsp, qldh);

        // Luong: 5.000.000 * 2.0 * (26 / 26) + 3.000.000 = 13.000.000
        double luongQL = quanLy.tinhLuong();
        kiemTra("tinh luong Quan ly chuan = 13.000.000 VND", Math.abs(luongQL - 13_000_000) < 0.001);

        kiemTra("dieu phoi quanLyKhachHang() khong null", quanLy.quanLyKhachHang() != null);
        kiemTra("dieu phoi quanLySanPham() khong null", quanLy.quanLySanPham() != null);
        kiemTra("dieu phoi quanLyDonHang() khong null", quanLy.quanLyDonHang() != null);

        // Phan cong ca lam
        CaLam caSang = new CaLam("CA01", homNay, LocalTime.of(7, 0), LocalTime.of(12, 0));
        CaLam caChieu = new CaLam("CA02", homNay, LocalTime.of(13, 0), LocalTime.of(18, 0));
        CaLam caTrung = new CaLam("CA03", homNay, LocalTime.of(10, 0), LocalTime.of(15, 0));

        kiemTra("ca sang va ca chieu khong trung ca", !caSang.kiemTraTrungCa(caChieu));
        kiemTra("ca trung va ca sang co giao nhau ve gio", caSang.kiemTraTrungCa(caTrung));

        quanLy.phanCongCa(thuNgan, caSang);
        kiemTra("nhan vien nhan ca sang thanh cong (dsCaLam = 1)", thuNgan.getDsCaLam().size() == 1);

        quanLy.phanCongCa(thuNgan, caTrung);
        kiemTra("nhan vien bi trung ca thi khong duoc nhan them (dsCaLam van = 1)", thuNgan.getDsCaLam().size() == 1);

        quanLy.phanCongCa(thuNgan, caChieu);
        kiemTra("nhan vien nhan ca chieu hop le (dsCaLam = 2)", thuNgan.getDsCaLam().size() == 2);

        System.out.println("\n--------------------------------------------------");
        System.out.println("KET QUA KIEM THU: " + dat + " dat, " + loi + " loi");
        System.out.println("--------------------------------------------------");
        if (loi == 0) {
            System.out.println(">>> TAT CA CAC KIEM THU DEU DAT CHUAN 100%! <<<");
        } else {
            System.exit(1);
        }
    }
}
