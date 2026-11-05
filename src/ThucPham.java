import java.time.LocalDate;

public class ThucPham extends SanPham {
    private LocalDate hanSuDung;

    public ThucPham() {
        super();
    }

    public ThucPham(String maSanPham, String tenSanPham, String nuocSanXuat, 
                    int namSanXuat, String hangSanXuat, int soLuongTonKho, 
                    double giaGoc, LocalDate hanSuDung) {
        super(maSanPham, tenSanPham, nuocSanXuat, namSanXuat, hangSanXuat, soLuongTonKho, giaGoc);
        this.hanSuDung = hanSuDung;
    }

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    @Override
    public double tinhGia() {
        // Ví dụ logic tính giá: Nếu cận hạn (dưới 7 ngày) giảm giá 20%, ngược lại giữ nguyên giá gốc
        if (LocalDate.now().plusDays(7).isAfter(hanSuDung)) {
            return getGiaGoc() * 0.8;
        }
        return getGiaGoc();
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("Hạn sử dụng: " + hanSuDung);
    }
}