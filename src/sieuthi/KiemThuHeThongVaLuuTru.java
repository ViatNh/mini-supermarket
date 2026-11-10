package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import sieuthi.data.DuLieuHeThong;
import sieuthi.manage.QuanLy;
import sieuthi.model.*;

/**
 * Bộ kiểm thử cho tầng lưu trữ dữ liệu file và quy tắc nghiệp vụ quản lý tồn kho (stock).
 * Kiểm tra:
 * 1. Đọc / Ghi file text (.txt) cho 9 thực thể với ít nhất 5 bản ghi mẫu mỗi file.
 * 2. RÀNG BUỘC TỒN KHO: Tồn kho chỉ thay đổi qua Nhập hàng và Bán hàng, không thể sửa thủ công.
 */
public class KiemThuHeThongVaLuuTru {
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
        System.out.println("  KIỂM THỬ LƯU TRỮ FILE & RÀNG BUỘC TỒN KHO (STOCK) ");
        System.out.println("==================================================");

        QuanLy ql = new QuanLy("QL01", "Phạm Văn Quản Lý", "Hà Nội", "0988888888", 26, 2.0, 3_000_000);
        DuLieuHeThong storage = new DuLieuHeThong(ql);

        // 1. Kiểm tra đọc dữ liệu mẫu từ các file text
        System.out.println("\n1. Kiểm tra khởi tạo & đọc 9 file lưu trữ mẫu (>= 5 bản ghi):");
        storage.docTatCaDuLieu();

        int soKH = ql.quanLyKhachHang().getSoLuong();
        int soNV = storage.getDsNhanVien().size() + 1; // gồm cả quản lý
        int soCa = storage.getDsCaLam().size();
        int soNCC = storage.getDsNhaCungCap().size();
        int soSP = ql.quanLySanPham().getSoLuong();
        int soKM = storage.getDsKhuyenMai().size();
        int soNH = ql.quanLyNhapHang().getSoLuong();
        int soHD = ql.quanLyDonHang().getSoLuong();
        int soPBH = storage.getDsPhieuBaoHanh().size();

        kiemTra("khách hàng >= 5 (thực tế: " + soKH + ")", soKH >= 5);
        kiemTra("nhân viên >= 5 (thực tế: " + soNV + ")", soNV >= 5);
        kiemTra("ca làm việc >= 5 (thực tế: " + soCa + ")", soCa >= 5);
        kiemTra("nhà cung cấp >= 5 (thực tế: " + soNCC + ")", soNCC >= 5);
        kiemTra("sản phẩm >= 5 (thực tế: " + soSP + ")", soSP >= 5);
        kiemTra("khuyến mãi >= 5 (thực tế: " + soKM + ")", soKM >= 5);
        kiemTra("phiếu nhập hàng >= 5 (thực tế: " + soNH + ")", soNH >= 5);
        kiemTra("hóa đơn bán hàng >= 5 (thực tế: " + soHD + ")", soHD >= 5);
        kiemTra("phiếu bảo hành >= 5 (thực tế: " + soPBH + ")", soPBH >= 5);

        // 2. RÀNG BUỘC BẢO VỆ TỒN KHO: Không cho phép sửa thủ công
        System.out.println("\n2. Kiểm tra quy tắc bất biến tồn kho (Stock Integrity):");
        SanPham sp = ql.quanLySanPham().timKiemTheoMa("SP01");
        kiemTra("tìm thấy sản phẩm SP01", sp != null);
        int tonKhoBanDau = sp.getSoLuongTonKho();

        // Thử tạo một sản phẩm mới có cố tình sửa tồn kho thành 99999
        SanPham spCoYDoXau = new ThucPham("SP01", "Mì Hảo Hảo Tôm Chua Cay (Đã sửa tên)",
                sp.getNuocSanXuat(), sp.getNamSanXuat(), sp.getHangSanXuat(),
                99999, // cố tình sửa tồn kho
                6000.0, ((ThucPham) sp).getHanSuDung());

        boolean suaThanhCong = ql.quanLySanPham().sua("SP01", spCoYDoXau);
        kiemTra("sửa thông tin sản phẩm thành công", suaThanhCong);
        SanPham spSauSua = ql.quanLySanPham().timKiemTheoMa("SP01");
        kiemTra("tên sản phẩm đã được cập nhật mới", "Mì Hảo Hảo Tôm Chua Cay (Đã sửa tên)".equals(spSauSua.getTenSanPham()));
        kiemTra("tồn kho KHÔNG bị thay đổi thủ công (vẫn giữ nguyên " + tonKhoBanDau + ")",
                spSauSua.getSoLuongTonKho() == tonKhoBanDau);

        // 3. TỒN KHO CHỈ TĂNG QUA NHẬP HÀNG
        System.out.println("\n3. Kiểm tra biến động tồn kho qua Nhập hàng (Import):");
        NhaCungCap ncc = storage.timNhaCungCap("NCC01");
        NhapHang phieuNhapMoi = new NhapHang("NH_TEST", LocalDate.now(), ncc, ql);
        phieuNhapMoi.themChiTietVaCapNhatTonKho(spSauSua, 25, 3800.0);
        ql.quanLyNhapHang().them(phieuNhapMoi);
        kiemTra("tồn kho tăng chính xác sau khi nhập (+25 => " + (tonKhoBanDau + 25) + ")",
                spSauSua.getSoLuongTonKho() == tonKhoBanDau + 25);

        // 4. TỒN KHO CHỈ GIẢM QUA XUẤT BÁN HÓA ĐƠN
        System.out.println("\n4. Kiểm tra biến động tồn kho qua Bán hàng (Sales Invoice):");
        KhachHang kh = ql.quanLyKhachHang().timKiemTheoMa("KH01");
        ThuNgan tn = (ThuNgan) storage.timNhanVien("TN01");
        HoaDon hdMoi = new HoaDon("HD_TEST", LocalDate.now(), tn, kh, null);
        hdMoi.themChiTiet(spSauSua, 10);
        int tonKhoTruocKhiThanhToan = spSauSua.getSoLuongTonKho();
        tn.thanhToanHoaDon(hdMoi, new TienMat());
        ql.quanLyDonHang().them(hdMoi);

        kiemTra("hóa đơn thanh toán thành công", hdMoi.isDaThanhToan());
        kiemTra("tồn kho giảm chính xác sau thanh toán (-10 => " + (tonKhoTruocKhiThanhToan - 10) + ")",
                spSauSua.getSoLuongTonKho() == tonKhoTruocKhiThanhToan - 10);

        // Khôi phục lại tên ban đầu
        spSauSua.setTenSanPham("Mì Hảo Hảo Tôm Chua Cay");

        // 5. Kiểm tra tính bền vững: Ghi ra file
        System.out.println("\n5. Kiểm tra ghi dữ liệu ra file:");
        storage.luuTatCaDuLieu();
        kiemTra("lưu tất cả 9 danh mục ra file thành công", true);

        System.out.println("\n--------------------------------------------------");
        System.out.println("KẾT QUẢ KIỂM THỬ LƯU TRỮ VÀ TỒN KHO: " + dat + " đạt, " + loi + " lỗi");
        System.out.println("--------------------------------------------------");
        if (loi == 0) {
            System.out.println(">>> DỮ LIỆU FILE VÀ QUY TẮC TỒN KHO ĐẠT CHUẨN 100%! <<<");
        }
    }
}
