package sieuthi.manage;

import sieuthi.model.DoDung;
import sieuthi.model.DoUong;
import sieuthi.model.HoaDon;
import sieuthi.model.SanPham;
import sieuthi.model.ThucPham;

/**
 * Lop quan ly danh sach San pham trong sieu thi mini (Class danh sach trung tam).
 * Cai dat bang mang doi tuong dong (SanPham[]) tu co gian theo dung yeu cau OOP (khong dung Collection).
 * Thuc hien day du cac chuc nang:
 * - Them 1 phan tu, them k phan tu
 * - Sua, xoa theo ma
 * - Tim kiem: ma chinh xac, ten gan dung, khoang gia
 * - Thong ke: so luong theo loai, tong gia tri ton kho
 * - Da hinh khi xuat danh sach va tinh gia
 */
public class QuanLySanPham implements IQuanLy<SanPham> {
    private static final int SUC_CHUA_MAC_DINH = 10;
    private SanPham[] dsSanPham;
    private int soLuong;

    public QuanLySanPham() {
        this.dsSanPham = new SanPham[SUC_CHUA_MAC_DINH];
        this.soLuong = 0;
    }

    public QuanLySanPham(SanPham[] dsSanPham) {
        if (dsSanPham != null) {
            this.dsSanPham = new SanPham[Math.max(dsSanPham.length, SUC_CHUA_MAC_DINH)];
            this.soLuong = dsSanPham.length;
            System.arraycopy(dsSanPham, 0, this.dsSanPham, 0, dsSanPham.length);
        } else {
            this.dsSanPham = new SanPham[SUC_CHUA_MAC_DINH];
            this.soLuong = 0;
        }
    }

    public SanPham[] getDsSanPham() {
        SanPham[] res = new SanPham[soLuong];
        System.arraycopy(dsSanPham, 0, res, 0, soLuong);
        return res;
    }

    @Override
    public int getSoLuong() {
        return soLuong;
    }

    private void tangKichThuocMang() {
        SanPham[] moi = new SanPham[dsSanPham.length * 2];
        System.arraycopy(dsSanPham, 0, moi, 0, soLuong);
        dsSanPham = moi;
    }

    @Override
    public boolean them(SanPham sp) {
        if (sp == null || sp.getMaSanPham() == null || sp.getMaSanPham().trim().isEmpty()) {
            return false;
        }
        if (timKiemTheoMa(sp.getMaSanPham()) != null) {
            return false;
        }
        if (soLuong >= dsSanPham.length) {
            tangKichThuocMang();
        }
        dsSanPham[soLuong++] = sp;
        return true;
    }

    /**
     * Yeu cau Buoc 2 (1) c: Them k phan tu vao danh sach.
     */
    public int themKPhanTu(SanPham[] mangK) {
        if (mangK == null) return 0;
        int dem = 0;
        for (SanPham sp : mangK) {
            if (them(sp)) dem++;
        }
        return dem;
    }

    /**
     * Sua thong tin san pham.
     * QUY TAC BAT BIEN: Bao luu so luong ton kho cu, khong cho phep sua thu cong.
     */
    @Override
    public boolean sua(String maSP, SanPham spMoi) {
        if (maSP == null || spMoi == null) {
            return false;
        }
        for (int i = 0; i < soLuong; i++) {
            SanPham current = dsSanPham[i];
            if (current.getMaSanPham().equalsIgnoreCase(maSP.trim())) {
                spMoi.setSoLuongTonKho(current.getSoLuongTonKho());
                dsSanPham[i] = spMoi;
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
        for (int i = 0; i < soLuong; i++) {
            if (dsSanPham[i].getMaSanPham().equalsIgnoreCase(maSP.trim())) {
                for (int j = i; j < soLuong - 1; j++) {
                    dsSanPham[j] = dsSanPham[j + 1];
                }
                dsSanPham[--soLuong] = null;
                return true;
            }
        }
        return false;
    }

    @Override
    public SanPham timKiemTheoMa(String maSP) {
        if (maSP == null) {
            return null;
        }
        for (int i = 0; i < soLuong; i++) {
            if (dsSanPham[i].getMaSanPham() != null && dsSanPham[i].getMaSanPham().equalsIgnoreCase(maSP.trim())) {
                return dsSanPham[i];
            }
        }
        return null;
    }

    /**
     * Yeu cau Buoc 2 (1) f: Tim kiem gan dung theo ten (khong phan biet hoa thuong).
     */
    public SanPham[] timKiemTheoTen(String ten) {
        if (ten == null || ten.trim().isEmpty()) {
            return new SanPham[0];
        }
        String keyword = ten.trim().toLowerCase();
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            if (dsSanPham[i].getTenSanPham() != null && dsSanPham[i].getTenSanPham().toLowerCase().contains(keyword)) {
                dem++;
            }
        }
        SanPham[] ketQua = new SanPham[dem];
        int idx = 0;
        for (int i = 0; i < soLuong; i++) {
            if (dsSanPham[i].getTenSanPham() != null && dsSanPham[i].getTenSanPham().toLowerCase().contains(keyword)) {
                ketQua[idx++] = dsSanPham[i];
            }
        }
        return ketQua;
    }

    /**
     * Yeu cau Buoc 2 (1) f: Tim kiem theo khoang gia ban.
     */
    public SanPham[] timKiemTheoKhoangGia(double giaTu, double giaDen) {
        int dem = 0;
        for (int i = 0; i < soLuong; i++) {
            double g = dsSanPham[i].tinhGia();
            if (g >= giaTu && g <= giaDen) {
                dem++;
            }
        }
        SanPham[] res = new SanPham[dem];
        int idx = 0;
        for (int i = 0; i < soLuong; i++) {
            double g = dsSanPham[i].tinhGia();
            if (g >= giaTu && g <= giaDen) {
                res[idx++] = dsSanPham[i];
            }
        }
        return res;
    }

    /**
     * Yeu cau Buoc 2 (1) g: Thong ke so luong theo tung loai san pham va tong gia tri kho.
     */
    public void thongKeSoLuongTheoLoai() {
        int thucPham = 0, doUong = 0, doDung = 0;
        double tongGiaTri = 0;
        for (int i = 0; i < soLuong; i++) {
            SanPham sp = dsSanPham[i];
            if (sp instanceof ThucPham) thucPham++;
            else if (sp instanceof DoUong) doUong++;
            else if (sp instanceof DoDung) doDung++;
            tongGiaTri += sp.getSoLuongTonKho() * sp.tinhGia();
        }
        System.out.println("====== THONG KE CO CAU SAN PHAM ======");
        System.out.println("- Tong so san pham   : " + soLuong);
        System.out.println("  + Thuc pham        : " + thucPham);
        System.out.println("  + Do uong          : " + doUong);
        System.out.println("  + Do dung          : " + doDung);
        System.out.println("- Tong gia tri ton kho: " + HoaDon.dinhDangTien(tongGiaTri));
        System.out.println("======================================");
    }

    /**
     * Yeu cau Buoc 2 (1) h: Canh bao het hang hoac ton kho thap.
     */
    public void hienThiCanhBaoHetHang(int nguong) {
        System.out.println("============================== CANH BAO TON KHO THAP (<= " + nguong + ") ==============================");
        boolean coCanhBao = false;
        for (int i = 0; i < soLuong; i++) {
            SanPham sp = dsSanPham[i];
            if (sp.getSoLuongTonKho() <= nguong) {
                coCanhBao = true;
                System.out.printf("  [!] Ma: %-8s | Ten: %-30s | Ton kho hien tai: %-4d | Gia ban: %s%n",
                        sp.getMaSanPham(), sp.getTenSanPham(), sp.getSoLuongTonKho(),
                        HoaDon.dinhDangTien(sp.tinhGia()));
            }
        }
        if (!coCanhBao) {
            System.out.println("  (Tat ca san pham trong kho deu dam bao so luong an toan)");
        }
        System.out.println("=========================================================================================");
    }

    /**
     * Yeu cau Buoc 2 (1) b & i: Xuat danh sach co su dung da hinh.
     */
    @Override
    public void hienThiDanhSach() {
        System.out.println("================================================= DANH SACH SAN PHAM =================================================");
        if (soLuong == 0) {
            System.out.println("  (Danh sach san pham dang trong)");
            System.out.println("======================================================================================================================");
            return;
        }
        System.out.printf("%-5s | %-8s | %-10s | %-30s | %-12s | %-8s | %-13s | %s%n",
                "STT", "Ma SP", "Phan loai", "Ten san pham", "Hang SX", "Ton kho", "Gia ban", "Thong tin chi tiet");
        System.out.println("----------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < soLuong; i++) {
            SanPham sp = dsSanPham[i];
            String phanLoai = "Khac";
            String thongTinRieng = "";
            if (sp instanceof ThucPham tp) {
                phanLoai = "Thuc pham";
                thongTinRieng = "HSD: " + tp.getHanSuDung() + (tp.conHanSuDung() ? "" : " (HET HAN)");
            } else if (sp instanceof DoUong du) {
                phanLoai = "Do uong";
                thongTinRieng = du.getDungTichMl() + "ml | HSD: " + du.getHanSuDung();
            } else if (sp instanceof DoDung dd) {
                phanLoai = "Do dung";
                thongTinRieng = dd.getChatLieu() + " | BH: " + dd.layThoiHanBaoHanh() + " thang";
            }
            System.out.printf("%-5d | %-8s | %-10s | %-30s | %-12s | %-8d | %-13s | %s%n",
                    (i + 1),
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
