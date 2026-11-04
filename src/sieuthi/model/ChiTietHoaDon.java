package sieuthi.model;

public class ChiTietHoaDon {
    private final SanPham sanPham;
    private int soLuong;
    /** Đơn giá chốt tại thời điểm lập hóa đơn. */
    private final double donGia;

    public ChiTietHoaDon(SanPham sanPham, int soLuong) {
        if (sanPham == null) throw new IllegalArgumentException("Sản phẩm không được null");
        if (soLuong <= 0) throw new IllegalArgumentException("Số lượng phải > 0");
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.donGia = sanPham.tinhGia();
    }

    public SanPham getSanPham() { return sanPham; }
    public int getSoLuong() { return soLuong; }
    public double getDonGia() { return donGia; }

    void tangSoLuong(int them) { this.soLuong += them; }

    public double tinhThanhTien() {
        return soLuong * donGia;
    }
}
