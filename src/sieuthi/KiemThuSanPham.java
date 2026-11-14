package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import sieuthi.model.*;

/**
 * Bo kiem thu cho cac lop San pham (Thuc pham, Do uong, Do dung, Bao hanh)
 * tich hop tu nhanh san-pham cua ban Thien Vy.
 */
public class KiemThuSanPham {
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
        System.out.println("       BAT DAU KIEM THU MODULE SAN PHAM           ");
        System.out.println("==================================================");

        LocalDate homNay = LocalDate.now();

        // 1. Kiem thu Thuc pham
        ThucPham tp = new ThucPham("TP01", "Banh mi sandwich", "Viet Nam", 2026, "ABC Bakery", 50, 15000, homNay.plusDays(2));
        kiemTra("thuc pham con han su dung", tp.conHanSuDung());
        kiemTra("thuc pham can date (con 2 ngay) giam 30%", Math.abs(tp.tinhGia() - (15000 * 0.7)) < 0.001);

        ThucPham tpHetHan = new ThucPham("TP02", "Sua chua", "Viet Nam", 2026, "Vinamilk", 10, 8000, homNay.minusDays(1));
        kiemTra("thuc pham qua date het han", !tpHetHan.conHanSuDung());

        // 2. Kiem thu Do uong
        DoUong du = new DoUong("DU01", "Nuoc khoang thien nhien", "Viet Nam", 2026, "Lavie", 100, 10000, 500, homNay.plusMonths(12));
        kiemTra("do uong con han su dung", du.conHanSuDung());
        kiemTra("gia do uong bang gia goc", Math.abs(du.tinhGia() - 10000) < 0.001);
        kiemTra("dung tich dung 500ml", du.getDungTichMl() == 500);

        // 3. Kiem thu Do dung & Bao hanh
        DoDung dd = new DoDung("DD01", "Binh giu nhiet", "Nhat Ban", 2025, "Thermos", 20, 350000, "Inox 304", 12);
        kiemTra("do dung co thoi han bao hanh 12 thang", dd.layThoiHanBaoHanh() == 12);
        kiemTra("do dung mua 6 thang truoc van con bao hanh", dd.kiemTraBaoHanh(homNay.minusMonths(6)));
        kiemTra("do dung mua 13 thang truoc da het han bao hanh", !dd.kiemTraBaoHanh(homNay.minusMonths(13)));
        kiemTra("chat lieu dung Inox 304", dd.getChatLieu().equals("Inox 304"));

        // 4. Kiem thu ton kho
        int tonCu = tp.getSoLuongTonKho();
        tp.tangTonKho(20);
        kiemTra("tang ton kho thanh cong", tp.getSoLuongTonKho() == tonCu + 20);
        tp.giamTonKho(10);
        kiemTra("giam ton kho thanh cong", tp.getSoLuongTonKho() == tonCu + 10);

        System.out.println("\n--------------------------------------------------");
        System.out.println("KET QUA KIEM THU SAN PHAM: " + dat + " dat, " + loi + " loi");
        System.out.println("--------------------------------------------------");
        if (loi != 0) {
            System.exit(1);
        }
    }
}
