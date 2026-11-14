package sieuthi.model;

/**
 * Lớp đại diện cho một dòng chi tiết trong phiếu nhập hàng.
 * Cài đặt theo sơ đồ Class_Structure.drawio.
 */
public class ChiTietNhapHang {
    private SanPham sanPham;
    private int soLuong;
    private double donGiaNhap;

    public ChiTietNhapHang() {
        this.soLuong = 0;
        this.donGiaNhap = 0;
    }

    public ChiTietNhapHang(SanPham sanPham, int soLuong, double donGiaNhap) {
        if (sanPham == null) {
            throw new IllegalArgumentException("San pham khong duoc null");
        }
        if (soLuong <= 0) {
            throw new IllegalArgumentException("So luong nhap phai lon hon 0");
        }
        if (donGiaNhap < 0) {
            throw new IllegalArgumentException("Don gia nhap khong duoc am");
        }
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.donGiaNhap = donGiaNhap;
    }

    public SanPham getSanPham() {
        return sanPham;
    }

    public void setSanPham(SanPham sanPham) {
        if (sanPham == null) {
            throw new IllegalArgumentException("San pham khong duoc null");
        }
        this.sanPham = sanPham;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        if (soLuong <= 0) {
            throw new IllegalArgumentException("So luong nhap phai lon hon 0");
        }
        this.soLuong = soLuong;
    }

    public double getDonGiaNhap() {
        return donGiaNhap;
    }

    public void setDonGiaNhap(double donGiaNhap) {
        if (donGiaNhap < 0) {
            throw new IllegalArgumentException("Don gia nhap khong duoc am");
        }
        this.donGiaNhap = donGiaNhap;
    }

    /**
     * Tính thành tiền của chi tiết nhập hàng: số lượng * đơn giá nhập.
     *
     * @return Thành tiền (VNĐ)
     */
    public double tinhThanhTien() {
        return soLuong * donGiaNhap;
    }

    public void tangSoLuong(int them) {
        if (them <= 0) {
            throw new IllegalArgumentException("So luong cong don phai lon hon 0");
        }
        this.soLuong += them;
    }
}
