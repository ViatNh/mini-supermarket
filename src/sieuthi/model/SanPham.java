package sieuthi.model;

public abstract class SanPham {
    private final String maSanPham;
    private String tenSanPham;
    private String nuocSanXuat;
    private int namSanXuat;
    private String hangSanXuat;
    private int soLuongTonKho;
    private double giaGoc;

    protected SanPham(String maSanPham, String tenSanPham, String nuocSanXuat, int namSanXuat,
                      String hangSanXuat, int soLuongTonKho, double giaGoc) {
        if (giaGoc < 0 || soLuongTonKho < 0) {
            throw new IllegalArgumentException("Giá và tồn kho không được âm");
        }
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.nuocSanXuat = nuocSanXuat;
        this.namSanXuat = namSanXuat;
        this.hangSanXuat = hangSanXuat;
        this.soLuongTonKho = soLuongTonKho;
        this.giaGoc = giaGoc;
    }

    public String getMaSanPham() { return maSanPham; }
    public String getTenSanPham() { return tenSanPham; }
    public String getNuocSanXuat() { return nuocSanXuat; }
    public int getNamSanXuat() { return namSanXuat; }
    public String getHangSanXuat() { return hangSanXuat; }
    public int getSoLuongTonKho() { return soLuongTonKho; }
    public double getGiaGoc() { return giaGoc; }
    public void setTenSanPham(String tenSanPham) { this.tenSanPham = tenSanPham; }
    public void setGiaGoc(double giaGoc) { this.giaGoc = giaGoc; }

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
                + ", " + namSanXuat + ") | Tồn: " + soLuongTonKho + " | Giá: " + HoaDon.dinhDangTien(tinhGia()));
    }
}
