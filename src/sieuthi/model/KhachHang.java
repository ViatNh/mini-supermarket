package sieuthi.model;

/**
 * Lớp đại diện cho Khách hàng trong siêu thị mini.
 * Kế thừa từ Person theo sơ đồ Class_Structure.drawio.
 */
public class KhachHang extends Person {
    /** Cứ mỗi 10.000đ thanh toán được 1 điểm. */
    private static final double TIEN_MOI_DIEM = 10_000;

    private String maKhachHang;
    private int diemTichLuy;

    public KhachHang() {
        super();
        this.maKhachHang = "";
        this.diemTichLuy = 0;
    }

    public KhachHang(String maKhachHang, String hoTen, String diaChi, String soDienThoai) {
        super(hoTen, diaChi, soDienThoai);
        this.maKhachHang = (maKhachHang != null) ? maKhachHang.trim() : "";
        this.diemTichLuy = 0;
    }

    public KhachHang(String hoTen, String diaChi, String soDienThoai, String maKhachHang, int diemTichLuy) {
        super(hoTen, diaChi, soDienThoai);
        this.maKhachHang = (maKhachHang != null) ? maKhachHang.trim() : "";
        this.diemTichLuy = Math.max(0, diemTichLuy);
    }

    public String getMaKhachHang() { return maKhachHang; }
    public void setMaKhachHang(String maKhachHang) { this.maKhachHang = maKhachHang; }

    public int getDiemTichLuy() { return diemTichLuy; }
    public void setDiemTichLuy(int diemTichLuy) { this.diemTichLuy = Math.max(0, diemTichLuy); }

    // --- Alias cho tương thích nhánh Person ---
    public String getmaKH() { return this.maKhachHang; }
    public void setmaKH(String maKH) { this.maKhachHang = maKH; }

    public int getdiemTichLuy() { return this.diemTichLuy; }
    public void setdiemTichLuy(int diemTichLuy) { this.diemTichLuy = diemTichLuy; }

    /**
     * Tích lũy điểm dựa trên tổng tiền hóa đơn (10.000đ = 1 điểm).
     *
     * @param tongTien Tổng tiền hóa đơn thanh toán
     */
    public void tichDiem(double tongTien) {
        if (tongTien > 0) {
            int diemMoi = (int) (tongTien / TIEN_MOI_DIEM);
            this.diemTichLuy += diemMoi;
        }
    }

    @Override
    public void hienThiThongTin() {
        System.out.println("Khách hàng: " + maKhachHang + " | " + getHoTen()
                + " | " + getSoDienThoai() + " | Điểm tích lũy: " + diemTichLuy);
    }
}
