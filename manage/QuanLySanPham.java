package manage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sieuthi.model.HoaDon;
import sieuthi.model.SanPham;

/**
 * Lớp quản lý danh sách Sản phẩm trong siêu thị mini.
 * Thực thi interface IQuanLy<SanPham> theo sơ đồ Class_Structure.drawio.
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

    @Override
    public boolean sua(String maSP, SanPham sp) {
        if (maSP == null || sp == null) {
            return false;
        }
        for (int i = 0; i < dsSanPham.size(); i++) {
            SanPham current = dsSanPham.get(i);
            if (current.getMaSanPham().equalsIgnoreCase(maSP.trim())) {
                dsSanPham.set(i, sp);
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

    @Override
    public void hienThiDanhSach() {
        System.out.println("======================================== DANH SÁCH SẢN PHẨM ========================================");
        if (dsSanPham.isEmpty()) {
            System.out.println("  (Danh sách sản phẩm đang trống)");
            System.out.println("====================================================================================================");
            return;
        }
        System.out.printf("%-5s | %-10s | %-25s | %-15s | %-10s | %-8s | %-12s%n",
                "STT", "Mã SP", "Tên sản phẩm", "Hãng SX", "Xuất xứ", "Tồn kho", "Giá bán");
        System.out.println("----------------------------------------------------------------------------------------------------");
        int stt = 1;
        for (SanPham sp : dsSanPham) {
            System.out.printf("%-5d | %-10s | %-25s | %-15s | %-10s | %-8d | %-12s%n",
                    stt++,
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getHangSanXuat(),
                    sp.getNuocSanXuat(),
                    sp.getSoLuongTonKho(),
                    HoaDon.dinhDangTien(sp.tinhGia()));
        }
        System.out.println("====================================================================================================");
    }
}

