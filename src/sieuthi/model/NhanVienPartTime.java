package sieuthi.model;

/**
 * Lớp đại diện cho Nhân viên bán thời gian (Part-time).
 * Kế thừa từ NhanVien theo sơ đồ Class_Structure.drawio.
 */
public class NhanVienPartTime extends NhanVien {
    private int soGioLam;
    private double luongTheoGio;

    public NhanVienPartTime() {
        super();
        this.soGioLam = 0;
        this.luongTheoGio = 0;
    }

    public NhanVienPartTime(String maNhanVien, String hoTen, String diaChi, String soDienThoai,
                           int soGioLam, double luongTheoGio) {
        super(maNhanVien, hoTen, diaChi, soDienThoai);
        this.soGioLam = Math.max(0, soGioLam);
        this.luongTheoGio = Math.max(0, luongTheoGio);
    }

    public int getSoGioLam() { return soGioLam; }
    public void setSoGioLam(int soGioLam) { this.soGioLam = Math.max(0, soGioLam); }

    public double getLuongTheoGio() { return luongTheoGio; }
    public void setLuongTheoGio(double luongTheoGio) { this.luongTheoGio = Math.max(0, luongTheoGio); }

    @Override
    public double tinhLuong() {
        return this.soGioLam * this.luongTheoGio;
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("  Part-time | Số giờ làm: " + soGioLam + "h | Lương/giờ: "
                + HoaDon.dinhDangTien(luongTheoGio) + " | Thực lĩnh: " + HoaDon.dinhDangTien(tinhLuong()));
    }
}
