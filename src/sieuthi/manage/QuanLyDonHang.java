package sieuthi.manage;

import java.time.LocalDate;
import sieuthi.model.HoaDon;

/**
 * Lop quan ly danh sach Hoa Don (Don Hang) trong sieu thi mini.
 * Cai dat bang mang doi tuong dong (HoaDon[]) khong dung Collection.
 */
public class QuanLyDonHang implements IQuanLy<HoaDon> {
    private static final int SUC_CHUA_MAC_DINH = 10;
    private HoaDon[] dsDonHang;
    private int soLuong;

    public QuanLyDonHang() {
        this.dsDonHang = new HoaDon[SUC_CHUA_MAC_DINH];
        this.soLuong = 0;
    }

    public QuanLyDonHang(HoaDon[] dsDonHang) {
        if (dsDonHang != null) {
            this.dsDonHang = new HoaDon[Math.max(dsDonHang.length, SUC_CHUA_MAC_DINH)];
            this.soLuong = dsDonHang.length;
            System.arraycopy(dsDonHang, 0, this.dsDonHang, 0, dsDonHang.length);
        } else {
            this.dsDonHang = new HoaDon[SUC_CHUA_MAC_DINH];
            this.soLuong = 0;
        }
    }

    public HoaDon[] getDsDonHang() {
        HoaDon[] res = new HoaDon[soLuong];
        System.arraycopy(dsDonHang, 0, res, 0, soLuong);
        return res;
    }

    @Override
    public int getSoLuong() {
        return soLuong;
    }

    private void tangKichThuocMang() {
        HoaDon[] moi = new HoaDon[dsDonHang.length * 2];
        System.arraycopy(dsDonHang, 0, moi, 0, soLuong);
        dsDonHang = moi;
    }

    @Override
    public boolean them(HoaDon hd) {
        if (hd == null || hd.getMaHoaDon() == null || hd.getMaHoaDon().trim().isEmpty()) {
            return false;
        }
        if (timKiemTheoMa(hd.getMaHoaDon()) != null) {
            return false;
        }
        if (soLuong >= dsDonHang.length) {
            tangKichThuocMang();
        }
        dsDonHang[soLuong++] = hd;
        return true;
    }

    @Override
    public boolean sua(String maHD, HoaDon hd) {
        if (maHD == null || hd == null) {
            return false;
        }
        for (int i = 0; i < soLuong; i++) {
            if (dsDonHang[i].getMaHoaDon().equalsIgnoreCase(maHD.trim())) {
                dsDonHang[i] = hd;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoa(String maHD) {
        if (maHD == null) {
            return false;
        }
        for (int i = 0; i < soLuong; i++) {
            if (dsDonHang[i].getMaHoaDon().equalsIgnoreCase(maHD.trim())) {
                for (int j = i; j < soLuong - 1; j++) {
                    dsDonHang[j] = dsDonHang[j + 1];
                }
                dsDonHang[--soLuong] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public HoaDon timKiemTheoMa(String maHD) {
        if (maHD == null) {
            return null;
        }
        for (int i = 0; i < soLuong; i++) {
            if (dsDonHang[i].getMaHoaDon() != null && dsDonHang[i].getMaHoaDon().equalsIgnoreCase(maHD.trim())) {
                return dsDonHang[i];
            }
        }
        return null;
    }

    /**
     * Thong ke tong doanh thu thuc te tu ngay den ngay.
     * Chi tinh cac hoa don da thanh toan (tinhTienPhaiTra()).
     */
    public double thongKeDoanhThu(LocalDate tu, LocalDate den) {
        if (tu == null || den == null) {
            return 0;
        }
        LocalDate start = tu;
        LocalDate end = den;
        if (start.isAfter(end)) {
            start = den;
            end = tu;
        }

        double tongDoanhThu = 0;
        for (int i = 0; i < soLuong; i++) {
            HoaDon hd = dsDonHang[i];
            LocalDate ngay = hd.getNgayLap();
            if (ngay != null && !ngay.isBefore(start) && !ngay.isAfter(end)) {
                if (hd.isDaThanhToan()) {
                    tongDoanhThu += hd.tinhTienPhaiTra();
                }
            }
        }
        return tongDoanhThu;
    }

    public HoaDon[] locDonHangTheoNgay(LocalDate ngay) {
        if (ngay == null) return new HoaDon[0];
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (ngay.equals(dsDonHang[i].getNgayLap())) dem++;
        }
        HoaDon[] ketQua = new HoaDon[dem];
        int idx = 0;
        for (int i = 0; i < soLuong; i++) {
            if (ngay.equals(dsDonHang[i].getNgayLap())) {
                ketQua[idx++] = dsDonHang[i];
            }
        }
        return ketQua;
    }

    public HoaDon[] locDonHangTheoKhachHang(String maKH) {
        if (maKH == null || maKH.trim().isEmpty()) return new HoaDon[0];
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            HoaDon hd = dsDonHang[i];
            if (hd.getKhachHang() != null && hd.getKhachHang().getMaKhachHang().equalsIgnoreCase(maKH.trim())) {
                dem++;
            }
        }
        HoaDon[] ketQua = new HoaDon[dem];
        int idx = 0;
        for (int i = 0; i < soLuong; i++) {
            HoaDon hd = dsDonHang[i];
            if (hd.getKhachHang() != null && hd.getKhachHang().getMaKhachHang().equalsIgnoreCase(maKH.trim())) {
                ketQua[idx++] = hd;
            }
        }
        return ketQua;
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("================================================== DANH SACH HOA DON ==================================================");
        if (soLuong == 0) {
            System.out.println("  (Danh sach hoa don dang trong)");
            System.out.println("=======================================================================================================================");
            return;
        }
        System.out.printf("%-5s | %-8s | %-12s | %-10s | %-20s | %-15s | %s%n",
                "STT", "Ma HD", "Ngay lap", "Ma KH", "Nguoi lap", "Thanh tien", "Trang thai");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < soLuong; i++) {
            HoaDon hd = dsDonHang[i];
            String tenKH = (hd.getKhachHang() != null) ? hd.getKhachHang().getMaKhachHang() : "Vang lai";
            String trangThai = hd.isDaThanhToan() ? "Da thanh toan" : "Chua thanh toan";
            System.out.printf("%-5d | %-8s | %-12s | %-10s | %-20s | %-15s | %s%n",
                    (i + 1),
                    hd.getMaHoaDon(),
                    hd.getNgayLap(),
                    tenKH,
                    hd.getNguoiLap().getHoTen(),
                    HoaDon.dinhDangTien(hd.tinhTienPhaiTra()),
                    trangThai);
        }
        System.out.println("=======================================================================================================================");
    }
}
