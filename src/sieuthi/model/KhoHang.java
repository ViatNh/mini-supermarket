package sieuthi.model;

/**
 * Lớp đại diện cho Kho hàng của siêu thị.
 * Cài đặt theo sơ đồ Class_Structure.drawio.
 */
public class KhoHang {
    private String maKho;
    private String tenKho;

    public KhoHang() {
        this.maKho = "KHO01";
        this.tenKho = "Kho Trung Tâm Siêu Thị Mini";
    }

    public KhoHang(String maKho, String tenKho) {
        this.maKho = (maKho != null) ? maKho.trim() : "KHO01";
        this.tenKho = (tenKho != null) ? tenKho.trim() : "Kho Hàng";
    }

    public String getMaKho() {
        return maKho;
    }

    public void setMaKho(String maKho) {
        this.maKho = maKho;
    }

    public String getTenKho() {
        return tenKho;
    }

    public void setTenKho(String tenKho) {
        this.tenKho = tenKho;
    }

    /**
     * Cập nhật số lượng tồn kho của một sản phẩm trong kho:
     * - Nếu số lượng dương: Nhập thêm hàng vào kho.
     * - Nếu số lượng âm: Xuất bán hoặc giảm hàng khỏi kho.
     *
     * @param sp      Sản phẩm cần cập nhật
     * @param soLuong Lượng biến động (dương để tăng, âm để giảm)
     */
    public void capNhatTonKho(SanPham sp, int soLuong) {
        if (sp == null) {
            throw new IllegalArgumentException("Sản phẩm không được null");
        }
        if (soLuong > 0) {
            sp.tangTonKho(soLuong);
        } else if (soLuong < 0) {
            sp.giamTonKho(-soLuong);
        }
    }

    public void hienThiThongTin() {
        System.out.println("Mã kho: " + maKho + " | Tên kho: " + tenKho);
    }
}
