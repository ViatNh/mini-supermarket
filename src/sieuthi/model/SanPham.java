package sieuthi.model;

/**
 * Lớp trừu tượng đại diện cho Sản phẩm trong siêu thị mini.
 * Cài đặt theo sơ đồ Class_Structure.drawio.
 */
public abstract class SanPham {
    private String maSanPham;
    private String tenSanPham;
    private String nuocSanXuat;
    private int namSanXuat;
    private String hangSanXuat;
    private int soLuongTonKho;
    private double giaGoc;

    public SanPham() {
        this.maSanPham = "";
        this.tenSanPham = "";
        this.nuocSanXuat = "";
        this.namSanXuat = 2026;
        this.hangSanXuat = "";
        this.soLuongTonKho = 0;
        this.giaGoc = 0;
    }

    public SanPham(String maSanPham, String tenSanPham, String nuocSanXuat, int namSanXuat,
                   String hangSanXuat, int soLuongTonKho, double giaGoc) {
        if (giaGoc < 0 || soLuongTonKho < 0) {
            throw new IllegalArgumentException("Giá và tồn kho không được âm");
        }
        this.maSanPham = (maSanPham != null) ? maSanPham.trim() : "";
        this.tenSanPham = tenSanPham;
        this.nuocSanXuat = nuocSanXuat;
        this.namSanXuat = namSanXuat;
        this.hangSanXuat = hangSanXuat;
        this.soLuongTonKho = soLuongTonKho;
        this.giaGoc = giaGoc;
    }

    public String getMaSanPham() { return maSanPham; }
    public void setMaSanPham(String maSanPham) { this.maSanPham = maSanPham; }

    public String getTenSanPham() { return tenSanPham; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }

    public String getNuocSanXuat() { return nuocSanXuat; }
    public void setNuocSanXuat(String nuocSanXuat) { this.nuocSanXuat = nuocSanXuat; }

    public int getNamSanXuat() { return namSanXuat; }
    public void setNamSanXuat(int namSanXuat) { this.namSanXuat = namSanXuat; }

    public String getHangSanXuat() { return hangSanXuat; }
    public void setHangSanXuat(String hangSanXuat) { this.hangSanXuat = hangSanXuat; }

    public int getSoLuongTonKho() { return soLuongTonKho; }
    public void setSoLuongTonKho(int soLuongTonKho) {
        if (soLuongTonKho < 0) throw new IllegalArgumentException("Số lượng tồn kho không được âm");
        this.soLuongTonKho = soLuongTonKho;
    }

    public double getGiaGoc() { return giaGoc; }
    public void setGiaGoc(double giaGoc) {
        if (giaGoc < 0) throw new IllegalArgumentException("Giá gốc không được âm");
        this.giaGoc = giaGoc;
    }

    /** Giá bán hiện tại (đã tính các quy tắc riêng của từng loại sản phẩm). */
    public abstract double tinhGia();

    /** Sản phẩm còn dùng được/bán được không (mặc định: có). Thực phẩm, đồ uống ghi đè theo hạn dùng. */
    public boolean conHanSuDung() { return true; }

    public void tangTonKho(int soLuong) {
        if (soLuong < 0) throw new IllegalArgumentException("Số lượng không được âm");
        soLuongTonKho += soLuong;
    }

    public void giamTonKho(int soLuong) {
        if (soLuong < 0 || soLuong > soLuongTonKho) {
            throw new IllegalStateException("Không đủ tồn kho cho sản phẩm " + tenSanPham);
        }
        soLuongTonKho -= soLuong;
    }

    public void hienThiThongTin() {
        System.out.println(maSanPham + " | " + tenSanPham + " | " + hangSanXuat + " (" + nuocSanXuat
                + ", " + namSanXuat + ") | Tồn: " + soLuongTonKho + " | Giá bán: " + HoaDon.dinhDangTien(tinhGia()));
    }
}
