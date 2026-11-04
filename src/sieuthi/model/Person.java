package sieuthi.model;

/** Lớp trừu tượng chung cho KhachHang và NhanVien. */
public abstract class Person {
    private String hoTen;
    private String diaChi;
    private String soDienThoai;

    protected Person(String hoTen, String diaChi, String soDienThoai) {
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public String getHoTen() { return hoTen; }
    public String getDiaChi() { return diaChi; }
    public String getSoDienThoai() { return soDienThoai; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    public abstract void hienThiThongTin();
}
