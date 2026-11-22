package sieuthi.manage;

import java.time.LocalDate;
import sieuthi.model.HoaDon;
import sieuthi.model.NhapHang;

/**
 * Lop quan ly danh sach Phieu Nhap Hang trong sieu thi mini.
 * Cai dat bang mang doi tuong dong (NhapHang[]) khong dung Collection.
 */
public class QuanLyNhapHang implements IQuanLy<NhapHang> {
    private static final int SUC_CHUA_MAC_DINH = 10;
    private NhapHang[] dsNhapHang;
    private int soLuong;

    public QuanLyNhapHang() {
        this.dsNhapHang = new NhapHang[SUC_CHUA_MAC_DINH];
        this.soLuong = 0;
    }

    public QuanLyNhapHang(NhapHang[] dsNhapHang) {
        if (dsNhapHang != null) {
            this.dsNhapHang = new NhapHang[Math.max(dsNhapHang.length, SUC_CHUA_MAC_DINH)];
            this.soLuong = dsNhapHang.length;
            System.arraycopy(dsNhapHang, 0, this.dsNhapHang, 0, dsNhapHang.length);
        } else {
            this.dsNhapHang = new NhapHang[SUC_CHUA_MAC_DINH];
            this.soLuong = 0;
        }
    }

    public NhapHang[] getDsNhapHang() {
        NhapHang[] res = new NhapHang[soLuong];
        System.arraycopy(dsNhapHang, 0, res, 0, soLuong);
        return res;
    }

    @Override
    public int getSoLuong() {
        return soLuong;
    }

    private void tangKichThuocMang() {
        NhapHang[] moi = new NhapHang[dsNhapHang.length * 2];
        System.arraycopy(dsNhapHang, 0, moi, 0, soLuong);
        dsNhapHang = moi;
    }

    @Override
    public boolean them(NhapHang nh) {
        if (nh == null || nh.getMaNhapHang() == null || nh.getMaNhapHang().trim().isEmpty()) {
            return false;
        }
        if (timKiemTheoMa(nh.getMaNhapHang()) != null) {
            return false;
        }
        if (soLuong >= dsNhapHang.length) {
            tangKichThuocMang();
        }
        dsNhapHang[soLuong++] = nh;
        return true;
    }

    @Override
    public boolean sua(String maNH, NhapHang nh) {
        if (maNH == null || nh == null) {
            return false;
        }
        for (int i = 0; i < soLuong; i++) {
            if (dsNhapHang[i].getMaNhapHang().equalsIgnoreCase(maNH.trim())) {
                dsNhapHang[i] = nh;
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
        for (int i = 0; i < soLuong; i++) {
            if (dsNhapHang[i].getMaNhapHang().equalsIgnoreCase(maNH.trim())) {
                for (int j = i; j < soLuong - 1; j++) {
                    dsNhapHang[j] = dsNhapHang[j + 1];
                }
                dsNhapHang[--soLuong] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public NhapHang timKiemTheoMa(String maNH) {
        if (maNH == null) {
            return null;
        }
        for (int i = 0; i < soLuong; i++) {
            if (dsNhapHang[i].getMaNhapHang() != null && dsNhapHang[i].getMaNhapHang().equalsIgnoreCase(maNH.trim())) {
                return dsNhapHang[i];
            }
        }
        return null;
    }

    /**
     * Thong ke tong chi phi nhap hang tu ngay den ngay.
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
        for (int i = 0; i < soLuong; i++) {
            NhapHang nh = dsNhapHang[i];
            LocalDate ngay = nh.getNgayNhap();
            if (ngay != null && !ngay.isBefore(start) && !ngay.isAfter(end)) {
                tongChiPhi += nh.tinhTongTien();
            }
        }
        return tongChiPhi;
    }

    public NhapHang[] locTheoNhaCungCap(String maNCC) {
        if (maNCC == null || maNCC.trim().isEmpty()) return new NhapHang[0];
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            NhapHang nh = dsNhapHang[i];
            if (nh.getNhaCungCap() != null && nh.getNhaCungCap().getMaNhaCungCap().equalsIgnoreCase(maNCC.trim())) {
                dem++;
            }
        }
        NhapHang[] res = new NhapHang[dem];
        int idx = 0;
        for (int i = 0; i < soLuong; i++) {
            NhapHang nh = dsNhapHang[i];
            if (nh.getNhaCungCap() != null && nh.getNhaCungCap().getMaNhaCungCap().equalsIgnoreCase(maNCC.trim())) {
                res[idx++] = nh;
            }
        }
        return res;
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("============================================== DANH SACH PHIEU NHAP HANG ==============================================");
        if (soLuong == 0) {
            System.out.println("  (Danh sach phieu nhap hang dang trong)");
            System.out.println("=======================================================================================================================");
            return;
        }
        System.out.printf("%-5s | %-10s | %-12s | %-30s | %-20s | %s%n",
                "STT", "Ma PN", "Ngay nhap", "Nha cung cap", "Nguoi lap", "Tong chi phi");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < soLuong; i++) {
            NhapHang nh = dsNhapHang[i];
            String tenNCC = (nh.getNhaCungCap() != null) ? nh.getNhaCungCap().getTenNhaCungCap() : "N/A";
            String tenNguoiLap = (nh.getNguoiLap() != null) ? nh.getNguoiLap().getHoTen() : "N/A";
            System.out.printf("%-5d | %-10s | %-12s | %-30s | %-20s | %s%n",
                    (i + 1),
                    nh.getMaNhapHang(),
                    nh.getNgayNhap(),
                    tenNCC,
                    tenNguoiLap,
                    HoaDon.dinhDangTien(nh.tinhTongTien()));
        }
        System.out.println("=======================================================================================================================");
    }
}
