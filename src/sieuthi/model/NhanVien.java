package sieuthi.model;

public abstract class NhanVien extends Person {
    public static final double LUONG_CO_BAN = 5_000_000;

    private final String maNhanVien;

    protected NhanVien(String maNhanVien, String hoTen, String diaChi, String soDienThoai) {
        super(hoTen, diaChi, soDienThoai);
        this.maNhanVien = maNhanVien;
    }

    public String getMaNhanVien() { return maNhanVien; }

    public abstract double tinhLuong();

    @Override
    public void hienThiThongTin() {
        System.out.println("Nhân viên: " + maNhanVien + " | " + getHoTen() + " | " + getSoDienThoai());
    }
}
