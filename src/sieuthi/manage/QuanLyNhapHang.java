package sieuthi.manage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sieuthi.model.HoaDon;
import sieuthi.model.NhapHang;

/**
 * Lớp quản lý danh sách Phiếu Nhập Hàng trong siêu thị mini.
 * Thực thi interface IQuanLy<NhapHang> theo sơ đồ Class_Structure.drawio.
 * Đóng vai trò hạt nhân trong quy trình kiểm soát nguồn gốc và số lượng tồn kho của siêu thị.
 */
public class QuanLyNhapHang implements IQuanLy<NhapHang> {
    private final List<NhapHang> dsNhapHang;

    public QuanLyNhapHang() {
        this.dsNhapHang = new ArrayList<>();
    }

    public QuanLyNhapHang(List<NhapHang> dsNhapHang) {
        this.dsNhapHang = (dsNhapHang != null) ? new ArrayList<>(dsNhapHang) : new ArrayList<>();
    }

    public List<NhapHang> getDsNhapHang() {
        return Collections.unmodifiableList(dsNhapHang);
    }

    public int getSoLuong() {
        return dsNhapHang.size();
    }

    @Override
    public boolean them(NhapHang nh) {
        if (nh == null || nh.getMaNhapHang() == null || nh.getMaNhapHang().trim().isEmpty()) {
            return false;
        }
        if (timKiemTheoMa(nh.getMaNhapHang()) != null) {
            return false;
        }
        return dsNhapHang.add(nh);
    }

    @Override
    public boolean sua(String maNH, NhapHang nh) {
        if (maNH == null || nh == null) {
            return false;
        }
        for (int i = 0; i < dsNhapHang.size(); i++) {
            NhapHang current = dsNhapHang.get(i);
            if (current.getMaNhapHang().equalsIgnoreCase(maNH.trim())) {
                dsNhapHang.set(i, nh);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoa(String maNH) {
        if (maNH == null) {
            return false;
        }
        NhapHang nh = timKiemTheoMa(maNH);
        if (nh != null) {
            return dsNhapHang.remove(nh);
        }
        return false;
    }

    @Override
    public NhapHang timKiemTheoMa(String maNH) {
        if (maNH == null) {
            return null;
        }
        for (NhapHang nh : dsNhapHang) {
            if (nh.getMaNhapHang() != null && nh.getMaNhapHang().equalsIgnoreCase(maNH.trim())) {
                return nh;
            }
        }
        return null;
    }

    /**
     * Thống kê tổng chi phí nhập hàng từ ngày đến ngày (bao gồm cả 2 ngày).
     *
     * @param tu  Ngày bắt đầu
     * @param den Ngày kết thúc
     * @return Tổng chi phí nhập hàng (VNĐ)
     */
    public double thongKeChiPhiNhap(LocalDate tu, LocalDate den) {
        if (tu == null || den == null) {
            return 0;
        }
        LocalDate start = tu;
        LocalDate end = den;
        if (start.isAfter(end)) {
            start = den;
            end = tu;
        }

        double tongChiPhi = 0;
        for (NhapHang nh : dsNhapHang) {
            LocalDate ngay = nh.getNgayNhap();
            if (ngay != null && !ngay.isBefore(start) && !ngay.isAfter(end)) {
                tongChiPhi += nh.tinhTongTien();
            }
        }
        return tongChiPhi;
    }

    /**
     * Lọc danh sách đơn nhập hàng theo nhà cung cấp.
     *
     * @param maNCC Mã nhà cung cấp
     * @return Danh sách phiếu nhập từ nhà cung cấp đó
     */
    public List<NhapHang> locTheoNhaCungCap(String maNCC) {
        List<NhapHang> ketQua = new ArrayList<>();
        if (maNCC == null || maNCC.trim().isEmpty()) {
            return ketQua;
        }
        String target = maNCC.trim();
        for (NhapHang nh : dsNhapHang) {
            if (nh.getNhaCungCap() != null && target.equalsIgnoreCase(nh.getNhaCungCap().getMaNhaCungCap())) {
                ketQua.add(nh);
            }
        }
        return ketQua;
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("============================================= DANH SÁCH PHIẾU NHẬP HÀNG =============================================");
        if (dsNhapHang.isEmpty()) {
            System.out.println("  (Danh sách phiếu nhập hàng đang trống)");
            System.out.println("=====================================================================================================================");
            return;
        }
        System.out.printf("%-5s | %-10s | %-12s | %-20s | %-30s | %-16s%n",
                "STT", "Mã PN", "Ngày nhập", "Người tiếp nhận", "Nhà cung cấp", "Tổng tiền nhập");
        System.out.println("---------------------------------------------------------------------------------------------------------------------");
        int stt = 1;
        for (NhapHang nh : dsNhapHang) {
            String tenNCC = (nh.getNhaCungCap() != null) ? nh.getNhaCungCap().getTenNhaCungCap() : "N/A";
            String nguoiLap = (nh.getNguoiLap() != null) ? nh.getNguoiLap().getHoTen() : "N/A";
            System.out.printf("%-5d | %-10s | %-12s | %-20s | %-30s | %-16s%n",
                    stt++,
                    nh.getMaNhapHang(),
                    nh.getNgayNhap(),
                    nguoiLap,
                    tenNCC,
                    HoaDon.dinhDangTien(nh.tinhTongTien()));
        }
        System.out.println("=====================================================================================================================");
    }
}
