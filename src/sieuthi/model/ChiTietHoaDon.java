package sieuthi.model;

public class ChiTietHoaDon {
    private final SanPham sanPham;
    private int soLuong;
    /** Đơn giá chốt tại thời điểm lập hóa đơn. */
    private final double donGia;

    public ChiTietHoaDon(SanPham sanPham, int soLuong) {
        this(sanPham, soLuong, (sanPham != null) ? sanPham.tinhGia() : 0);
    }

    public ChiTietHoaDon(SanPham sanPham, int soLuong, double donGia) {
        if (sanPham == null) throw new IllegalArgumentException("San pham khong duoc null");
        if (soLuong <= 0) throw new IllegalArgumentException("So luong phai > 0");
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
    }

    public SanPham getSanPham() { return sanPham; }
    public int getSoLuong() { return soLuong; }
    public double getDonGia() { return donGia; }

    void tangSoLuong(int them) { this.soLuong += them; }

    public double tinhThanhTien() {
        return soLuong * donGia;
    }
}
