package sieuthi.model;

public class KhachHang extends Person {
    /** Cứ mỗi 10.000đ thanh toán được 1 điểm. */
    private static final double TIEN_MOI_DIEM = 10_000;

    private final String maKhachHang;
    private int diemTichLuy;

    public KhachHang(String maKhachHang, String hoTen, String diaChi, String soDienThoai) {
        super(hoTen, diaChi, soDienThoai);
        this.maKhachHang = maKhachHang;
        this.diemTichLuy = 0;
    }

    public String getMaKhachHang() { return maKhachHang; }
    public int getDiemTichLuy() { return diemTichLuy; }

    public void tichDiem(double tongTien) {
        if (tongTien > 0) {
            diemTichLuy += (int) (tongTien / TIEN_MOI_DIEM);
        }
    }

    @Override
    public void hienThiThongTin() {
        System.out.println("Khách hàng: " + maKhachHang + " | " + getHoTen()
                + " | " + getSoDienThoai() + " | Điểm: " + diemTichLuy);
    }
}
