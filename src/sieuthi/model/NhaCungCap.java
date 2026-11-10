package sieuthi.model;

/**
 * Lớp đại diện cho Nhà cung cấp sản phẩm cho siêu thị.
 * Cài đặt theo sơ đồ Class_Structure.drawio của thành viên Nhàn.
 */
public class NhaCungCap {
    private String maNhaCungCap;
    private String tenNhaCungCap;
    private String soDienThoai;
    private String diaChi;

    public NhaCungCap() {
        this.maNhaCungCap = "";
        this.tenNhaCungCap = "";
        this.soDienThoai = "";
        this.diaChi = "";
    }

    public NhaCungCap(String maNhaCungCap, String tenNhaCungCap, String soDienThoai, String diaChi) {
        this.maNhaCungCap = (maNhaCungCap != null) ? maNhaCungCap.trim() : "";
        this.tenNhaCungCap = (tenNhaCungCap != null) ? tenNhaCungCap.trim() : "";
        this.soDienThoai = (soDienThoai != null) ? soDienThoai.trim() : "";
        this.diaChi = (diaChi != null) ? diaChi.trim() : "";
    }

    public String getMaNhaCungCap() {
        return maNhaCungCap;
    }

    public void setMaNhaCungCap(String maNhaCungCap) {
        this.maNhaCungCap = maNhaCungCap;
    }

    public String getTenNhaCungCap() {
        return tenNhaCungCap;
    }

    public void setTenNhaCungCap(String tenNhaCungCap) {
        this.tenNhaCungCap = tenNhaCungCap;
    }

    public String getSoDienThoai() {
        return soDienThoai;
    }

    public void setSoDienThoai(String soDienThoai) {
        this.soDienThoai = soDienThoai;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    /**
     * Phương thức cung cấp sản phẩm theo sơ đồ: nhập thêm số lượng hàng hóa vào kho.
     *
     * @param sp      Sản phẩm cần cung cấp
     * @param soLuong Số lượng sản phẩm cung cấp
     */
    public void cungCapSanPham(SanPham sp, int soLuong) {
        if (sp == null) {
            throw new IllegalArgumentException("Sản phẩm không được null");
        }
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng cung cấp phải lớn hơn 0");
        }
        sp.tangTonKho(soLuong);
    }

    public void hienThiThongTin() {
        System.out.printf("%-10s | %-30s | %-12s | %s%n",
                maNhaCungCap, tenNhaCungCap, soDienThoai, diaChi);
    }

    @Override
    public String toString() {
        return maNhaCungCap + " - " + tenNhaCungCap + " (SĐT: " + soDienThoai + ")";
    }
}
