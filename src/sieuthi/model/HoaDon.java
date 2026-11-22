package sieuthi.model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.Locale;

/**
 * Hoa don ban hang. Quan ly mang cac ChiTietHoaDon theo chuan mang doi tuong OOP.
 * Khong dung bat ky Collection nao (ArrayList, Vector, LinkedList).
 */
public class HoaDon {
    private static final int SUC_CHUA_MAC_DINH = 10;

    private final String maHoaDon;
    private final LocalDate ngayLap;
    private ChiTietHoaDon[] dsChiTiet;
    private int soLuongChiTiet;
    private final KhuyenMai khuyenMai;      // co the null
    private final KhachHang khachHang;      // co the null (khach vang lai)
    private final NhanVien nguoiLap;
    private ThuNgan nguoiThanhToan;
    private IPhuongThucThanhToan phuongThucThanhToan;
    private boolean daThanhToan;

    public HoaDon(String maHoaDon, LocalDate ngayLap, NhanVien nguoiLap,
                  KhachHang khachHang, KhuyenMai khuyenMai) {
        if (nguoiLap == null) throw new IllegalArgumentException("Hoa don phai co nguoi lap");
        this.maHoaDon = maHoaDon;
        this.ngayLap = ngayLap;
        this.nguoiLap = nguoiLap;
        this.khachHang = khachHang;
        this.khuyenMai = khuyenMai;
        this.dsChiTiet = new ChiTietHoaDon[SUC_CHUA_MAC_DINH];
        this.soLuongChiTiet = 0;
    }

    public String getMaHoaDon() { return maHoaDon; }
    public LocalDate getNgayLap() { return ngayLap; }
    public KhuyenMai getKhuyenMai() { return khuyenMai; }
    public KhachHang getKhachHang() { return khachHang; }
    public NhanVien getNguoiLap() { return nguoiLap; }
    public ThuNgan getNguoiThanhToan() { return nguoiThanhToan; }
    public IPhuongThucThanhToan getPhuongThucThanhToan() { return phuongThucThanhToan; }
    public boolean isDaThanhToan() { return daThanhToan; }

    public ChiTietHoaDon[] getDsChiTiet() {
        ChiTietHoaDon[] ketQua = new ChiTietHoaDon[soLuongChiTiet];
        System.arraycopy(dsChiTiet, 0, ketQua, 0, soLuongChiTiet);
        return ketQua;
    }

    public int getSoLuongChiTiet() {
        return soLuongChiTiet;
    }

    private void tangKichThuocMang() {
        ChiTietHoaDon[] moi = new ChiTietHoaDon[dsChiTiet.length * 2];
        System.arraycopy(dsChiTiet, 0, moi, 0, soLuongChiTiet);
        dsChiTiet = moi;
    }

    /** Them san pham vao hoa don; neu da co thi cong don so luong. */
    public void themChiTiet(SanPham sp, int soLuong) {
        if (daThanhToan) throw new IllegalStateException("Hoa don da thanh toan, khong the sua");
        if (sp == null) throw new IllegalArgumentException("San pham khong duoc null");
        if (soLuong <= 0) throw new IllegalArgumentException("So luong phai > 0");
        if (!sp.conHanSuDung()) {
            throw new IllegalStateException("San pham da het han: " + sp.getTenSanPham());
        }

        ChiTietHoaDon daCo = timChiTiet(sp);
        int daChon = (daCo == null) ? 0 : daCo.getSoLuong();
        if (daChon + soLuong > sp.getSoLuongTonKho()) {
            throw new IllegalStateException("Khong du ton kho cho " + sp.getTenSanPham()
                    + " (con " + sp.getSoLuongTonKho() + ")");
        }

        if (daCo != null) {
            daCo.tangSoLuong(soLuong);
        } else {
            if (soLuongChiTiet >= dsChiTiet.length) {
                tangKichThuocMang();
            }
            dsChiTiet[soLuongChiTiet++] = new ChiTietHoaDon(sp, soLuong);
        }
    }

    private ChiTietHoaDon timChiTiet(SanPham sp) {
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (dsChiTiet[i].getSanPham() == sp) return dsChiTiet[i];
        }
        return null;
    }

    /**
     * Dung khi khoi phuc hoa don da luu tu file luu tru.
     */
    public void themChiTietLuuTru(SanPham sp, int soLuong, double donGia) {
        if (sp == null || soLuong <= 0) return;
        if (soLuongChiTiet >= dsChiTiet.length) {
            tangKichThuocMang();
        }
        dsChiTiet[soLuongChiTiet++] = new ChiTietHoaDon(sp, soLuong, donGia);
    }

    /**
     * Dung khi khoi phuc trang thai thanh toan tu file luu tru.
     */
    public void thietLapTrangThaiThanhToan(ThuNgan thuNgan, IPhuongThucThanhToan phuongThuc, boolean daThanhToan) {
        this.nguoiThanhToan = thuNgan;
        this.phuongThucThanhToan = phuongThuc;
        this.daThanhToan = daThanhToan;
    }

    public boolean coSanPham(SanPham sp) {
        return timChiTiet(sp) != null;
    }

    /** Tong tien hang, CHUA tru khuyen mai. */
    public double tinhTongTien() {
        double tong = 0;
        for (int i = 0; i < soLuongChiTiet; i++) {
            tong += dsChiTiet[i].tinhThanhTien();
        }
        return tong;
    }

    /** Tong tien duoc giam nho khuyen mai (0 neu khong co hoac het hieu luc tai ngay lap). */
    public double tinhTienGiam() {
        if (khuyenMai == null || !khuyenMai.conHieuLuc(ngayLap)) return 0;
        double giam = 0;
        for (int i = 0; i < soLuongChiTiet; i++) {
            giam += khuyenMai.apDung(dsChiTiet[i].getSanPham()) * dsChiTiet[i].getSoLuong();
        }
        return giam;
    }

    /** So tien khach phai tra = tong tien - tien giam. */
    public double tinhTienPhaiTra() {
        return tinhTongTien() - tinhTienGiam();
    }

    /** Duoc ThuNgan goi sau khi phuong thuc thanh toan da chap nhan. */
    public void hoanTatThanhToan(ThuNgan thuNgan, IPhuongThucThanhToan phuongThuc) {
        if (daThanhToan) throw new IllegalStateException("Hoa don da thanh toan");
        for (int i = 0; i < soLuongChiTiet; i++) {
            dsChiTiet[i].getSanPham().giamTonKho(dsChiTiet[i].getSoLuong());
        }
        this.nguoiThanhToan = thuNgan;
        this.phuongThucThanhToan = phuongThuc;
        this.daThanhToan = true;
        if (khachHang != null) khachHang.tichDiem(tinhTienPhaiTra());
    }

    public static String dinhDangTien(double soTien) {
        NumberFormat nf = NumberFormat.getInstance(Locale.forLanguageTag("vi-VN"));
        nf.setMaximumFractionDigits(0);
        return nf.format(soTien) + " VND";
    }

    public void hienThiHoaDon() {
        System.out.println("==================== HOA DON " + maHoaDon + " ====================");
        System.out.println("Ngay lap   : " + ngayLap);
        System.out.println("Nguoi lap  : " + nguoiLap.getHoTen());
        System.out.println("Khach hang : " + (khachHang == null ? "Khach vang lai" : khachHang.getHoTen()));
        System.out.println("---------------------------------------------------");
        int stt = 1;
        for (int i = 0; i < soLuongChiTiet; i++) {
            ChiTietHoaDon ct = dsChiTiet[i];
            System.out.println(stt++ + ". " + ct.getSanPham().getTenSanPham()
                    + "  x" + ct.getSoLuong()
                    + "  @" + dinhDangTien(ct.getDonGia())
                    + "  = " + dinhDangTien(ct.tinhThanhTien()));
        }
        System.out.println("---------------------------------------------------");
        System.out.println("Tong tien hang: " + dinhDangTien(tinhTongTien()));
        System.out.println("Khuyen mai    : -" + dinhDangTien(tinhTienGiam()));
        System.out.println("PHAI TRA      : " + dinhDangTien(tinhTienPhaiTra()));
        if (daThanhToan) {
            System.out.println("Trang thai    : DA THANH TOAN (" + phuongThucThanhToan.getClass().getSimpleName()
                    + ", thu ngan " + nguoiThanhToan.getHoTen() + ")");
        } else {
            System.out.println("Trang thai    : CHUA THANH TOAN");
        }
        System.out.println("===================================================");
    }
}
