package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import sieuthi.model.*;

/**
 * Bộ kiểm thử cho các lớp Sản phẩm (Thực phẩm, Đồ uống, Đồ dùng, Bảo hành)
 * tích hợp từ nhánh san-pham của bạn Thiên Vy.
 */
public class KiemThuSanPham {
    private static int dat = 0;
    private static int loi = 0;

    private static void kiemTra(String ten, boolean b) {
        if (b) {
            System.out.println("  [OK]   " + ten);
            dat++;
        } else {
            System.out.println("  [LỖI]  " + ten);
            loi++;
        }
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("==================================================");
        System.out.println("       BẮT ĐẦU KIỂM THỬ MODULE SẢN PHẨM          ");
        System.out.println("==================================================");

        LocalDate homNay = LocalDate.now();

        // 1. Kiểm thử Thực phẩm
        ThucPham tp = new ThucPham("TP01", "Bánh mì sandwich", "Việt Nam", 2026, "ABC Bakery", 50, 15000, homNay.plusDays(2));
        kiemTra("thực phẩm còn hạn sử dụng", tp.conHanSuDung());
        kiemTra("thực phẩm cận date (còn 2 ngày) giảm 30%", Math.abs(tp.tinhGia() - (15000 * 0.7)) < 0.001);

        ThucPham tpHetHan = new ThucPham("TP02", "Sữa chua", "Việt Nam", 2026, "Vinamilk", 10, 8000, homNay.minusDays(1));
        kiemTra("thực phẩm quá date hết hạn", !tpHetHan.conHanSuDung());

        // 2. Kiểm thử Đồ uống
        DoUong du = new DoUong("DU01", "Nước khoáng thiên nhiên", "Việt Nam", 2026, "Lavie", 100, 10000, 500, homNay.plusMonths(12));
        kiemTra("đồ uống còn hạn sử dụng", du.conHanSuDung());
        kiemTra("giá đồ uống bằng giá gốc", Math.abs(du.tinhGia() - 10000) < 0.001);
        kiemTra("dung tích đúng 500ml", du.getDungTichMl() == 500);

        // 3. Kiểm thử Đồ dùng & Bảo hành
        DoDung dd = new DoDung("DD01", "Bình giữ nhiệt", "Nhật Bản", 2025, "Thermos", 20, 350000, "Inox 304", 12);
        kiemTra("đồ dùng có thời hạn bảo hành 12 tháng", dd.layThoiHanBaoHanh() == 12);
        kiemTra("đồ dùng mua 6 tháng trước vẫn còn bảo hành", dd.kiemTraBaoHanh(homNay.minusMonths(6)));
        kiemTra("đồ dùng mua 13 tháng trước đã hết hạn bảo hành", !dd.kiemTraBaoHanh(homNay.minusMonths(13)));
        kiemTra("chất liệu đúng Inox 304", dd.getChatLieu().equals("Inox 304"));

        // 4. Kiểm thử tồn kho
        int tonCu = tp.getSoLuongTonKho();
        tp.tangTonKho(20);
        kiemTra("tăng tồn kho thành công", tp.getSoLuongTonKho() == tonCu + 20);
        tp.giamTonKho(10);
        kiemTra("giảm tồn kho thành công", tp.getSoLuongTonKho() == tonCu + 10);

        System.out.println("\n--------------------------------------------------");
        System.out.println("KẾT QUẢ KIỂM THỬ SẢN PHẨM: " + dat + " đạt, " + loi + " lỗi");
        System.out.println("--------------------------------------------------");
        if (loi != 0) {
            System.exit(1);
        }
    }
}
