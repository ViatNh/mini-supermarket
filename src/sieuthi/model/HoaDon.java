package sieuthi.model;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

/**
 * Hóa đơn bán hàng. Lưu riêng nguoiLap (NhanVien) và nguoiThanhToan (ThuNgan).
 * Chi tiết hóa đơn là composition: sống/chết cùng hóa đơn.
 */
public class HoaDon {
    private final String maHoaDon;
    private final LocalDate ngayLap;
    private final List<ChiTietHoaDon> dsChiTiet = new ArrayList<>();
    private final KhuyenMai khuyenMai;      // có thể null
    private final KhachHang khachHang;      // có thể null (khách vãng lai)
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
    }

    public String getMaHoaDon() { return maHoaDon; }
    public LocalDate getNgayLap() { return ngayLap; }
    public KhuyenMai getKhuyenMai() { return khuyenMai; }
    public KhachHang getKhachHang() { return khachHang; }
    public NhanVien getNguoiLap() { return nguoiLap; }
    public ThuNgan getNguoiThanhToan() { return nguoiThanhToan; }
    public IPhuongThucThanhToan getPhuongThucThanhToan() { return phuongThucThanhToan; }
    public boolean isDaThanhToan() { return daThanhToan; }

    public List<ChiTietHoaDon> getDsChiTiet() {
        return Collections.unmodifiableList(dsChiTiet);
    }

    /** Thêm sản phẩm vào hóa đơn; nếu đã có thì cộng dồn số lượng. */
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
            dsChiTiet.add(new ChiTietHoaDon(sp, soLuong));
        }
    }

    private ChiTietHoaDon timChiTiet(SanPham sp) {
        for (ChiTietHoaDon ct : dsChiTiet) {
            if (ct.getSanPham() == sp) return ct;
        }
        return null;
    }

    /**
     * Dùng khi khôi phục hóa đơn đã lưu từ file lưu trữ (không kiểm tra tồn kho và không trừ tồn kho lại).
     */
    public void themChiTietLuuTru(SanPham sp, int soLuong, double donGia) {
        if (sp == null || soLuong <= 0) return;
        dsChiTiet.add(new ChiTietHoaDon(sp, soLuong, donGia));
    }

    /**
     * Dùng khi khôi phục trạng thái thanh toán từ file lưu trữ.
     */
    public void thietLapTrangThaiThanhToan(ThuNgan thuNgan, IPhuongThucThanhToan phuongThuc, boolean daThanhToan) {
        this.nguoiThanhToan = thuNgan;
        this.phuongThucThanhToan = phuongThuc;
        this.daThanhToan = daThanhToan;
    }

    public boolean coSanPham(SanPham sp) {
        return timChiTiet(sp) != null;
    }

    /** Tổng tiền hàng, CHƯA trừ khuyến mãi. */
    public double tinhTongTien() {
        double tong = 0;
        for (ChiTietHoaDon ct : dsChiTiet) tong += ct.tinhThanhTien();
        return tong;
    }

    /** Tổng tiền được giảm nhờ khuyến mãi (0 nếu không có hoặc hết hiệu lực tại ngày lập). */
    public double tinhTienGiam() {
        if (khuyenMai == null || !khuyenMai.conHieuLuc(ngayLap)) return 0;
        double giam = 0;
        for (ChiTietHoaDon ct : dsChiTiet) {
            giam += khuyenMai.apDung(ct.getSanPham()) * ct.getSoLuong();
        }
        return giam;
    }

    /** Số tiền khách phải trả = tổng tiền - tiền giảm. */
    public double tinhTienPhaiTra() {
        return tinhTongTien() - tinhTienGiam();
    }

    /** Được ThuNgan gọi sau khi phương thức thanh toán đã chấp nhận. */
    public void hoanTatThanhToan(ThuNgan thuNgan, IPhuongThucThanhToan phuongThuc) {
        if (daThanhToan) throw new IllegalStateException("Hoa don da thanh toan");
        for (ChiTietHoaDon ct : dsChiTiet) {
            ct.getSanPham().giamTonKho(ct.getSoLuong());
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
        for (ChiTietHoaDon ct : dsChiTiet) {
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
