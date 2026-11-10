package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import manage.*;
import sieuthi.model.*;

/**
 * Bộ kiểm thử toàn diện cho module Quản lý (package manage).
 * Kiểm tra: IQuanLy, QuanLyKhachHang, QuanLySanPham, QuanLyDonHang, QuanLy.
 */
public class KiemThuQuanLy {
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
        System.out.println("       BẮT ĐẦU KIỂM THỬ MODULE QUẢN LÝ           ");
        System.out.println("==================================================");

        LocalDate homNay = LocalDate.now();

        // ============================================================
        // 1. KIỂM THỬ QUẢN LÝ KHÁCH HÀNG
        // ============================================================
        System.out.println("\n1. Quản lý khách hàng (QuanLyKhachHang):");
        QuanLyKhachHang qlkh = new QuanLyKhachHang();
        KhachHang kh1 = new KhachHang("KH01", "Nguyễn Văn A", "Hà Nội", "0901234567");
        KhachHang kh2 = new KhachHang("KH02", "Trần Thị B", "Đà Nẵng", "0912345678");
        KhachHang kh3 = new KhachHang("KH03", "Lê Văn C", "TP.HCM", "0923456789");

        kiemTra("thêm KH01 thành công", qlkh.them(kh1));
        kiemTra("thêm KH02 thành công", qlkh.them(kh2));
        kiemTra("thêm trùng mã KH01 bị từ chối", !qlkh.them(new KhachHang("KH01", "Người Khác", "Huế", "0999999999")));
        kiemTra("thêm null bị từ chối", !qlkh.them(null));
        kiemTra("số lượng khách hàng = 2", qlkh.getSoLuong() == 2);

        KhachHang timMa = qlkh.timKiemTheoMa("kh01");
        kiemTra("tìm kiếm theo mã KH01 (không phân biệt hoa thường)", timMa != null && timMa.getHoTen().equals("Nguyễn Văn A"));
        kiemTra("tìm mã không tồn tại trả về null", qlkh.timKiemTheoMa("KH99") == null);

        KhachHang timSDT = qlkh.timKiemTheoSDT("0912345678");
        kiemTra("tìm kiếm theo SĐT thành công", timSDT != null && timSDT.getMaKhachHang().equals("KH02"));
        kiemTra("tìm SĐT không tồn tại trả về null", qlkh.timKiemTheoSDT("0000000000") == null);

        KhachHang kh1Moi = new KhachHang("KH01", "Nguyễn Văn A (VIP)", "Hà Nội", "0901234567");
        kiemTra("sửa thông tin KH01 thành công", qlkh.sua("KH01", kh1Moi));
        kiemTra("thông tin KH01 sau khi sửa đã cập nhật", qlkh.timKiemTheoMa("KH01").getHoTen().contains("VIP"));
        kiemTra("sửa mã không tồn tại bị từ chối", !qlkh.sua("KH99", kh1Moi));

        kiemTra("xóa KH02 thành công", qlkh.xoa("KH02"));
        kiemTra("sau khi xóa, KH02 không còn trong danh sách", qlkh.timKiemTheoMa("KH02") == null);
        kiemTra("xóa mã không tồn tại bị từ chối", !qlkh.xoa("KH99"));
        kiemTra("số lượng khách hàng sau khi xóa = 1", qlkh.getSoLuong() == 1);

        // ============================================================
        // 2. KIỂM THỬ QUẢN LÝ SẢN PHẨM
        // ============================================================
        System.out.println("\n2. Quản lý sản phẩm (QuanLySanPham):");
        QuanLySanPham qlsp = new QuanLySanPham();
        SanPham sp1 = new ThucPham("SP01", "Mì Hảo Hảo Tôm Chua Cay", "Việt Nam", 2026, "Acecook", 100, 5000, homNay.plusMonths(6));
        SanPham sp2 = new DoUong("SP02", "Nước ngọt Coca Cola", "Việt Nam", 2026, "CocaCola", 50, 10000, 330, homNay.plusMonths(12));
        SanPham sp3 = new DoDung("SP03", "Ấm siêu tốc Inox", "Việt Nam", 2025, "Sunhouse", 20, 250000, "Inox", 12);
        SanPham sp4 = new ThucPham("SP04", "Mì Omachi Xốt Bò Hầm", "Việt Nam", 2026, "Masan", 80, 8000, homNay.plusMonths(5));

        kiemTra("thêm sản phẩm SP01 thành công", qlsp.them(sp1));
        kiemTra("thêm sản phẩm SP02 thành công", qlsp.them(sp2));
        kiemTra("thêm sản phẩm SP03 thành công", qlsp.them(sp3));
        kiemTra("thêm sản phẩm SP04 thành công", qlsp.them(sp4));
        kiemTra("thêm trùng mã SP01 bị từ chối", !qlsp.them(sp1));
        kiemTra("thêm sản phẩm null bị từ chối", !qlsp.them(null));
        kiemTra("số lượng sản phẩm = 4", qlsp.getSoLuong() == 4);

        SanPham timSP = qlsp.timKiemTheoMa("sp03");
        kiemTra("tìm kiếm sản phẩm theo mã SP03", timSP != null && timSP.getTenSanPham().equals("Ấm siêu tốc Inox"));

        List<SanPham> timTheoTen = qlsp.timKiemTheoTen("mì");
        kiemTra("tìm theo tên 'mì' ra đúng 2 sản phẩm", timTheoTen.size() == 2);

        SanPham sp1Moi = new ThucPham("SP01", "Mì Hảo Hảo Sa Tế", "Việt Nam", 2026, "Acecook", 120, 5500, homNay.plusMonths(6));
        kiemTra("sửa sản phẩm SP01 thành công", qlsp.sua("SP01", sp1Moi));
        kiemTra("tên sản phẩm SP01 sau sửa đổi thành 'Mì Hảo Hảo Sa Tế'", qlsp.timKiemTheoMa("SP01").getTenSanPham().equals("Mì Hảo Hảo Sa Tế"));

        kiemTra("xóa sản phẩm SP04 thành công", qlsp.xoa("SP04"));
        kiemTra("SP04 không còn sau khi xóa", qlsp.timKiemTheoMa("SP04") == null);
        kiemTra("số lượng sản phẩm còn lại = 3", qlsp.getSoLuong() == 3);

        // ============================================================
        // 3. KIỂM THỬ QUẢN LÝ ĐƠN HÀNG
        // ============================================================
        System.out.println("\n3. Quản lý đơn hàng (QuanLyDonHang):");
        QuanLyDonHang qldh = new QuanLyDonHang();
        NhanVien thuNgan = new ThuNgan("NV01", "Lê Thu Ngân", "Hà Nội", "0987654321", 26, 1.2, "Q01");

        HoaDon hd1 = new HoaDon("HD01", homNay.minusDays(2), thuNgan, kh1, null);
        hd1.themChiTiet(sp1Moi, 10); // 10 * 5500 = 55.000
        ((ThuNgan) thuNgan).thanhToanHoaDon(hd1, new TienMat()); // Đã thanh toán: 55.000

        HoaDon hd2 = new HoaDon("HD02", homNay.minusDays(1), thuNgan, kh3, null);
        hd2.themChiTiet(sp2, 5); // 5 * 10000 = 50.000
        ((ThuNgan) thuNgan).thanhToanHoaDon(hd2, new TienMat()); // Đã thanh toán: 50.000

        HoaDon hd3 = new HoaDon("HD03", homNay, thuNgan, kh1, null);
        hd3.themChiTiet(sp3, 1); // 250.000 - CHƯA thanh toán

        kiemTra("thêm hóa đơn HD01 thành công", qldh.them(hd1));
        kiemTra("thêm hóa đơn HD02 thành công", qldh.them(hd2));
        kiemTra("thêm hóa đơn HD03 thành công", qldh.them(hd3));
        kiemTra("thêm trùng mã HD01 bị từ chối", !qldh.them(hd1));
        kiemTra("số lượng đơn hàng = 3", qldh.getSoLuong() == 3);

        HoaDon timHD = qldh.timKiemTheoMa("hd02");
        kiemTra("tìm kiếm theo mã HD02 thành công", timHD != null && timHD.isDaThanhToan());

        // Thống kê doanh thu từ (hôm nay - 3 ngày) đến hôm nay
        // Chỉ tính HD01 (55.000) + HD02 (50.000) = 105.000 (HD03 chưa thanh toán nên không tính)
        double doanhThu = qldh.thongKeDoanhThu(homNay.minusDays(3), homNay);
        kiemTra("thống kê doanh thu 3 ngày = 105.000đ (chỉ tính đơn đã thanh toán)", Math.abs(doanhThu - 105_000) < 0.001);

        double doanhThuHomNay = qldh.thongKeDoanhThu(homNay, homNay);
        kiemTra("doanh thu hôm nay = 0đ vì đơn hôm nay chưa thanh toán", doanhThuHomNay == 0);

        List<HoaDon> dsHDTheoKH1 = qldh.locDonHangTheoKhachHang("KH01");
        kiemTra("lọc đơn hàng của KH01 ra đúng 2 hóa đơn", dsHDTheoKH1.size() == 2);

        kiemTra("xóa đơn hàng HD03 thành công", qldh.xoa("HD03"));
        kiemTra("sau xóa số lượng đơn = 2", qldh.getSoLuong() == 2);

        // ============================================================
        // 4. KIỂM THỬ LỚP QUẢN LÝ (QuanLy) & PHÂN CÔNG CA LÀM
        // ============================================================
        System.out.println("\n4. Lớp Quản lý & Phân công ca làm (QuanLy & CaLam):");
        QuanLy quanLy = new QuanLy("QL01", "Trần Quản Lý", "Hà Nội", "0911223344", 26, 2.0, 3_000_000,
                qlkh, qlsp, qldh);

        // Lương: 5.000.000 * 2.0 * (26 / 26) + 3.000.000 = 13.000.000
        double luongQL = quanLy.tinhLuong();
        kiemTra("tính lương Quản lý chuẩn = 13.000.000đ", Math.abs(luongQL - 13_000_000) < 0.001);

        kiemTra("điều phối quanLyKhachHang() không null", quanLy.quanLyKhachHang() != null);
        kiemTra("điều phối quanLySanPham() không null", quanLy.quanLySanPham() != null);
        kiemTra("điều phối quanLyDonHang() không null", quanLy.quanLyDonHang() != null);

        // Phân công ca làm
        CaLam caSang = new CaLam("CA01", homNay, LocalTime.of(7, 0), LocalTime.of(12, 0));
        CaLam caChieu = new CaLam("CA02", homNay, LocalTime.of(13, 0), LocalTime.of(18, 0));
        CaLam caTrung = new CaLam("CA03", homNay, LocalTime.of(10, 0), LocalTime.of(15, 0));

        kiemTra("ca sáng và ca chiều không trùng ca", !caSang.kiemTraTrungCa(caChieu));
        kiemTra("ca trùng và ca sáng có giao nhau về giờ", caSang.kiemTraTrungCa(caTrung));

        quanLy.phanCongCa(thuNgan, caSang);
        kiemTra("nhân viên nhận ca sáng thành công (dsCaLam = 1)", thuNgan.getDsCaLam().size() == 1);

        quanLy.phanCongCa(thuNgan, caTrung);
        kiemTra("nhân viên bị trùng ca thì không được nhận thêm (dsCaLam vẫn = 1)", thuNgan.getDsCaLam().size() == 1);

        quanLy.phanCongCa(thuNgan, caChieu);
        kiemTra("nhân viên nhận ca chiều hợp lệ (dsCaLam = 2)", thuNgan.getDsCaLam().size() == 2);

        System.out.println("\n--------------------------------------------------");
        System.out.println("KẾT QUẢ KIỂM THỬ: " + dat + " đạt, " + loi + " lỗi");
        System.out.println("--------------------------------------------------");
        if (loi == 0) {
            System.out.println(">>> TẤT CẢ CÁC KIỂM THỬ ĐỀU ĐẠT CHUẨN 100%! <<<");
        } else {
            System.exit(1);
        }
    }
}

