import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<SanPham> dsSanPham = new ArrayList<>();

        ThucPham tp = new ThucPham("TP01", "Bánh mì", "Việt Nam", 2024, "ABC Bakery", 50, 15000, LocalDate.now().plusDays(3));
        DoUong du = new DoUong("DU01", "Nước suối", "Việt Nam", 2024, "Aquafina", 100, 10000, 500, LocalDate.now().plusMonths(12));
        DoDung dd = new DoDung("DD01", "Bình giữ nhiệt", "Nhật Bản", 2023, "Thermos", 20, 350000, "Inox 304", 12);

        dsSanPham.add(tp);
        dsSanPham.add(du);
        dsSanPham.add(dd);

        System.out.println("=== DANH SÁCH SẢN PHẨM ===");
        for (SanPham sp : dsSanPham) {
            sp.hienThiThongTin();
            System.out.println("-----------------------------------");
        }

        // Kiểm tra bảo hành đồ dùng
        LocalDate ngayMua = LocalDate.now().minusMonths(6);
        System.out.println("Kiểm tra bảo hành cho món đồ dùng (mua ngày " + ngayMua + "): " 
                + (dd.kiemTraBaoHanh(ngayMua) ? "Còn bảo hành" : "Hết hạn bảo hành"));
    }
}