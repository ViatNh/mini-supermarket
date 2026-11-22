package sieuthi.manage;

import sieuthi.model.KhachHang;

/**
 * Lop quan ly danh sach Khach hang trong sieu thi mini.
 * Cai dat bang mang doi tuong dong (KhachHang[]) khong dung Collection.
 */
public class QuanLyKhachHang implements IQuanLy<KhachHang> {
    private static final int SUC_CHUA_MAC_DINH = 10;
    private KhachHang[] dsKhachHang;
    private int soLuong;

    public QuanLyKhachHang() {
        this.dsKhachHang = new KhachHang[SUC_CHUA_MAC_DINH];
        this.soLuong = 0;
    }

    public QuanLyKhachHang(KhachHang[] dsKhachHang) {
        if (dsKhachHang != null) {
            this.dsKhachHang = new KhachHang[Math.max(dsKhachHang.length, SUC_CHUA_MAC_DINH)];
            this.soLuong = dsKhachHang.length;
            System.arraycopy(dsKhachHang, 0, this.dsKhachHang, 0, dsKhachHang.length);
        } else {
            this.dsKhachHang = new KhachHang[SUC_CHUA_MAC_DINH];
            this.soLuong = 0;
        }
    }

    public KhachHang[] getDsKhachHang() {
        KhachHang[] res = new KhachHang[soLuong];
        System.arraycopy(dsKhachHang, 0, res, 0, soLuong);
        return res;
    }

    @Override
    public int getSoLuong() {
        return soLuong;
    }

    public void xoaTatCa() {
        for (int i = 0; i < soLuong; i++) {
            dsKhachHang[i] = null;
        }
        soLuong = 0;
    }

    private void tangKichThuocMang() {
        KhachHang[] moi = new KhachHang[dsKhachHang.length * 2];
        System.arraycopy(dsKhachHang, 0, moi, 0, soLuong);
        dsKhachHang = moi;
    }

    @Override
    public boolean them(KhachHang kh) {
        if (kh == null || kh.getMaKhachHang() == null || kh.getMaKhachHang().trim().isEmpty()) {
            return false;
        }
        if (timKiemTheoMa(kh.getMaKhachHang()) != null) {
            return false;
        }
        if (soLuong >= dsKhachHang.length) {
            tangKichThuocMang();
        }
        dsKhachHang[soLuong++] = kh;
        return true;
    }

    @Override
    public boolean sua(String maKH, KhachHang kh) {
        if (maKH == null || kh == null) {
            return false;
        }
        for (int i = 0; i < soLuong; i++) {
            KhachHang current = dsKhachHang[i];
            if (current.getMaKhachHang().equalsIgnoreCase(maKH.trim())) {
                if (kh.getDiemTichLuy() == 0 && current.getDiemTichLuy() > 0) {
                    kh.setDiemTichLuy(current.getDiemTichLuy());
                }
                dsKhachHang[i] = kh;
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoa(String maKH) {
        if (maKH == null) {
            return false;
        }
        for (int i = 0; i < soLuong; i++) {
            if (dsKhachHang[i].getMaKhachHang().equalsIgnoreCase(maKH.trim())) {
                for (int j = i; j < soLuong - 1; j++) {
                    dsKhachHang[j] = dsKhachHang[j + 1];
                }
                dsKhachHang[--soLuong] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public KhachHang timKiemTheoMa(String maKH) {
        if (maKH == null) {
            return null;
        }
        for (int i = 0; i < soLuong; i++) {
            if (dsKhachHang[i].getMaKhachHang() != null && dsKhachHang[i].getMaKhachHang().equalsIgnoreCase(maKH.trim())) {
                return dsKhachHang[i];
            }
        }
        return null;
    }

    public KhachHang timKiemTheoSDT(String sdt) {
        if (sdt == null || sdt.trim().isEmpty()) {
            return null;
        }
        String target = sdt.trim().replaceAll("\\s+", "");
        for (int i = 0; i < soLuong; i++) {
            KhachHang kh = dsKhachHang[i];
            if (kh.getSoDienThoai() != null) {
                String cleanPhone = kh.getSoDienThoai().trim().replaceAll("\\s+", "");
                if (cleanPhone.equals(target)) {
                    return kh;
                }
            }
        }
        return null;
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("======================================== DANH SACH KHACH HANG ========================================");
        if (soLuong == 0) {
            System.out.println("  (Danh sach khach hang dang trong)");
            System.out.println("======================================================================================================");
            return;
        }
        System.out.printf("%-5s | %-10s | %-25s | %-12s | %-8s | %s%n",
                "STT", "Ma KH", "Ho va ten", "So DT", "Diem TL", "Dia chi");
        System.out.println("------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < soLuong; i++) {
            KhachHang kh = dsKhachHang[i];
            System.out.printf("%-5d | %-10s | %-25s | %-12s | %-8d | %s%n",
                    (i + 1),
                    kh.getMaKhachHang(),
                    kh.getHoTen(),
                    kh.getSoDienThoai(),
                    kh.getDiemTichLuy(),
                    kh.getDiaChi());
        }
        System.out.println("======================================================================================================");
    }
}
