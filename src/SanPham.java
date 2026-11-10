public abstract class SanPham {
    private String maSanPham;
    private String tenSanPham;
    private String nuocSanXuat;
    private int namSanXuat;
    private String hangSanXuat;
    private int soLuongTonKho;
    private double giaGoc;

    public SanPham() {
    }

    public SanPham(String maSanPham, String tenSanPham, String nuocSanXuat, 
                   int namSanXuat, String hangSanXuat, int soLuongTonKho, double giaGoc) {
        this.maSanPham = maSanPham;
        this.tenSanPham = tenSanPham;
        this.nuocSanXuat = nuocSanXuat;
        this.namSanXuat = namSanXuat;
        this.hangSanXuat = hangSanXuat;
        this.soLuongTonKho = soLuongTonKho;
        this.giaGoc = giaGoc;
    }

    // Getters và Setters
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
    public void setSoLuongTonKho(int soLuongTonKho) { this.soLuongTonKho = soLuongTonKho; }

    public double getGiaGoc() { return giaGoc; }
    public void setGiaGoc(double giaGoc) { this.giaGoc = giaGoc; }

    // Phương thức trừu tượng tính giá
    public abstract double tinhGia();

    // Hiển thị thông tin chung
    public void hienThiThongTin() {
        System.out.println("Mã SP: " + maSanPham);
        System.out.println("Tên SP: " + tenSanPham);
        System.out.println("Nước SX: " + nuocSanXuat);
        System.out.println("Năm SX: " + namSanXuat);
        System.out.println("Hãng SX: " + hangSanXuat);
        System.out.println("Số lượng tồn: " + soLuongTonKho);
        System.out.println("Giá gốc: " + giaGoc);
        System.out.println("Giá bán tính được: " + tinhGia());
    }
}