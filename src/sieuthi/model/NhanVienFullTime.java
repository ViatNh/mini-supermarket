package sieuthi.model;

/**
 * Lớp trừu tượng đại diện cho Nhân viên toàn thời gian (Full-time).
 * Kế thừa từ NhanVien theo sơ đồ Class_Structure.drawio.
 */
public abstract class NhanVienFullTime extends NhanVien {
    public static final int NGAY_CONG_CHUAN = 26;

    private int soNgayCong;
    private double heSoLuong;

    public NhanVienFullTime() {
        super();
        this.soNgayCong = 0;
        this.heSoLuong = 1.0;
    }

    public NhanVienFullTime(String maNhanVien, String hoTen, String diaChi, String soDienThoai,
                           int soNgayCong, double heSoLuong) {
        super(maNhanVien, hoTen, diaChi, soDienThoai);
        this.soNgayCong = Math.max(0, soNgayCong);
        this.heSoLuong = Math.max(0, heSoLuong);
    }

    public int getSoNgayCong() { return soNgayCong; }
    public void setSoNgayCong(int soNgayCong) { this.soNgayCong = Math.max(0, soNgayCong); }

    public double getHeSoLuong() { return heSoLuong; }
    public void setHeSoLuong(double heSoLuong) { this.heSoLuong = Math.max(0, heSoLuong); }

    @Override
    public abstract double tinhLuong();

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("  Full-time | Ngày công: " + soNgayCong + "/" + NGAY_CONG_CHUAN
                + " | Hệ số lương: " + heSoLuong);
    }
}
