package sieuthi.model;

public abstract class NhanVienFullTime extends NhanVien {
    protected static final int NGAY_CONG_CHUAN = 26;

    private int soNgayCong;
    private double heSoLuong;

    protected NhanVienFullTime(String maNhanVien, String hoTen, String diaChi, String soDienThoai,
                               int soNgayCong, double heSoLuong) {
        super(maNhanVien, hoTen, diaChi, soDienThoai);
        this.soNgayCong = soNgayCong;
        this.heSoLuong = heSoLuong;
    }

    public int getSoNgayCong() { return soNgayCong; }
    public double getHeSoLuong() { return heSoLuong; }
    public void setSoNgayCong(int soNgayCong) { this.soNgayCong = soNgayCong; }
    public void setHeSoLuong(double heSoLuong) { this.heSoLuong = heSoLuong; }

    @Override
    public abstract double tinhLuong();

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("  Full-time | Ngày công: " + soNgayCong + " | Hệ số lương: " + heSoLuong);
    }
}
