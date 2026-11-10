package sieuthi.manage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sieuthi.model.DoDung;
import sieuthi.model.DoUong;
import sieuthi.model.HoaDon;
import sieuthi.model.SanPham;
import sieuthi.model.ThucPham;

/**
 * Lớp quản lý danh sách Sản phẩm trong siêu thị mini.
 * Thực thi interface IQuanLy<SanPham> theo sơ đồ Class_Structure.drawio của thành viên Vinh và Vỹ.
 *
 * QUY TẮC BẤT BIẾN NGHIỆP VỤ:
 * Số lượng tồn kho (stock) của sản phẩm KHÔNG ĐƯỢC PHÉP sửa thủ công trong chức năng cập nhật.
 * Tồn kho chỉ được phép thay đổi qua:
 * 1. Nhập hàng (tăng tồn kho thông qua NhapHang / NhaCungCap)
 * 2. Xuất bán (giảm tồn kho khi HoaDon hoàn tất thanh toán)
 */
public class QuanLySanPham implements IQuanLy<SanPham> {
    private final List<SanPham> dsSanPham;

    public QuanLySanPham() {
        this.dsSanPham = new ArrayList<>();
    }

    public QuanLySanPham(List<SanPham> dsSanPham) {
        this.dsSanPham = (dsSanPham != null) ? new ArrayList<>(dsSanPham) : new ArrayList<>();
    }

    public List<SanPham> getDsSanPham() {
        return Collections.unmodifiableList(dsSanPham);
    }

    public int getSoLuong() {
        return dsSanPham.size();
    }

    @Override
    public boolean them(SanPham sp) {
        if (sp == null || sp.getMaSanPham() == null || sp.getMaSanPham().trim().isEmpty()) {
            return false;
        }
        // Kiểm tra trùng mã sản phẩm
        if (timKiemTheoMa(sp.getMaSanPham()) != null) {
            return false;
        }
        return dsSanPham.add(sp);
    }

    /**
     * Sửa thông tin sản phẩm.
     * CHÚ Ý BẢO VỆ TỒN KHO: Phương thức này TUYỆT ĐỐI KHÔNG cho phép sửa thủ công tồn kho.
     * Số lượng tồn kho hiện tại của sản phẩm cũ luôn được bảo lưu nguyên vẹn.
     */
    @Override
    public boolean sua(String maSP, SanPham spMoi) {
        if (maSP == null || spMoi == null) {
            return false;
        }
        for (int i = 0; i < dsSanPham.size(); i++) {
            SanPham current = dsSanPham.get(i);
            if (current.getMaSanPham().equalsIgnoreCase(maSP.trim())) {
                // Đảm bảo số lượng tồn kho cũ được bảo toàn, không bị can thiệp thủ công
                spMoi.setSoLuongTonKho(current.getSoLuongTonKho());
                dsSanPham.set(i, spMoi);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoa(String maSP) {
        if (maSP == null) {
            return false;
        }
        SanPham sp = timKiemTheoMa(maSP);
        if (sp != null) {
            return dsSanPham.remove(sp);
        }
        return false;
    }

    @Override
    public SanPham timKiemTheoMa(String maSP) {
        if (maSP == null) {
            return null;
        }
        for (SanPham sp : dsSanPham) {
            if (sp.getMaSanPham() != null && sp.getMaSanPham().equalsIgnoreCase(maSP.trim())) {
                return sp;
            }
        }
        return null;
    }

    /**
     * Tìm kiếm các sản phẩm theo tên (tìm gần đúng, không phân biệt hoa thường).
     *
     * @param ten Tên hoặc một phần tên sản phẩm cần tìm
     * @return Danh sách các sản phẩm khớp với từ khóa tìm kiếm
     */
    public List<SanPham> timKiemTheoTen(String ten) {
        List<SanPham> ketQua = new ArrayList<>();
        if (ten == null || ten.trim().isEmpty()) {
            return ketQua;
        }
        String keyword = ten.trim().toLowerCase();
        for (SanPham sp : dsSanPham) {
            if (sp.getTenSanPham() != null && sp.getTenSanPham().toLowerCase().contains(keyword)) {
                ketQua.add(sp);
            }
        }
        return ketQua;
    }

    /**
     * Lọc danh sách sản phẩm theo loại.
     *
     * @param loaiClass Lớp loại sản phẩm (ThucPham.class, DoUong.class, DoDung.class)
     * @return Danh sách các sản phẩm thuộc loại đó
     */
    public <T extends SanPham> List<T> locTheoLoai(Class<T> loaiClass) {
        List<T> ketQua = new ArrayList<>();
        for (SanPham sp : dsSanPham) {
            if (loaiClass.isInstance(sp)) {
                ketQua.add(loaiClass.cast(sp));
            }
        }
        return ketQua;
    }

    /**
     * Cảnh báo các sản phẩm có số lượng tồn kho thấp hơn hoặc bằng một ngưỡng nhất định.
     *
     * @param nguong Ngưỡng tồn kho tối thiểu cần cảnh báo
     */
    public void hienThiCanhBaoHetHang(int nguong) {
        System.out.println("============================== CẢNH BÁO TỒN KHO THẤP (<= " + nguong + ") ==============================");
        boolean coCanhBao = false;
        for (SanPham sp : dsSanPham) {
            if (sp.getSoLuongTonKho() <= nguong) {
                coCanhBao = true;
                System.out.printf("  [!] Mã: %-8s | Tên: %-30s | Tồn kho hiện tại: %-4d | Giá bán: %s%n",
                        sp.getMaSanPham(), sp.getTenSanPham(), sp.getSoLuongTonKho(),
                        HoaDon.dinhDangTien(sp.tinhGia()));
            }
        }
        if (!coCanhBao) {
            System.out.println("  (Tất cả sản phẩm trong kho đều đảm bảo số lượng an toàn)");
        }
        System.out.println("=========================================================================================");
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("================================================= DANH SÁCH SẢN PHẨM =================================================");
        if (dsSanPham.isEmpty()) {
            System.out.println("  (Danh sách sản phẩm đang trống)");
            System.out.println("======================================================================================================================");
            return;
        }
        System.out.printf("%-5s | %-8s | %-10s | %-30s | %-12s | %-8s | %-13s | %s%n",
                "STT", "Mã SP", "Phân loại", "Tên sản phẩm", "Hãng SX", "Tồn kho", "Giá bán", "Thông tin chi tiết");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        int stt = 1;
        for (SanPham sp : dsSanPham) {
            String phanLoai = "Khác";
            String thongTinRieng = "";
            if (sp instanceof ThucPham tp) {
                phanLoai = "Thực phẩm";
                thongTinRieng = "HSD: " + tp.getHanSuDung() + (tp.conHanSuDung() ? "" : " (HẾT HẠN)");
            } else if (sp instanceof DoUong du) {
                phanLoai = "Đồ uống";
                thongTinRieng = du.getDungTichMl() + "ml | HSD: " + du.getHanSuDung();
            } else if (sp instanceof DoDung dd) {
                phanLoai = "Đồ dùng";
                thongTinRieng = dd.getChatLieu() + " | BH: " + dd.layThoiHanBaoHanh() + " tháng";
            }
            System.out.printf("%-5d | %-8s | %-10s | %-30s | %-12s | %-8d | %-13s | %s%n",
                    stt++,
                    sp.getMaSanPham(),
                    phanLoai,
                    sp.getTenSanPham(),
                    sp.getHangSanXuat(),
                    sp.getSoLuongTonKho(),
                    HoaDon.dinhDangTien(sp.tinhGia()),
                    thongTinRieng);
        }
        System.out.println("======================================================================================================================");
    }
}
