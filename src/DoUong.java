import java.time.LocalDate;

public class DoUong extends SanPham {
    private int dungTichMl;
    private LocalDate hanSuDung;

    public DoUong() {
        super();
    }

    public DoUong(String maSanPham, String tenSanPham, String nuocSanXuat, 
                  int namSanXuat, String hangSanXuat, int soLuongTonKho, 
                  double giaGoc, int dungTichMl, LocalDate hanSuDung) {
        super(maSanPham, tenSanPham, nuocSanXuat, namSanXuat, hangSanXuat, soLuongTonKho, giaGoc);
        this.dungTichMl = dungTichMl;
        this.hanSuDung = hanSuDung;
    }

    public int getDungTichMl() {
        return dungTichMl;
    }

    public void setDungTichMl(int dungTichMl) {
        this.dungTichMl = dungTichMl;
    }

    public LocalDate getHanSuDung() {
        return hanSuDung;
    }

    public void setHanSuDung(LocalDate hanSuDung) {
        this.hanSuDung = hanSuDung;
    }

    @Override
    public double tinhGia() {
        // Tùy theo công thức tính giá yêu cầu của đề bài (ví dụ giữ nguyên giá gốc hoặc thuế đồ uống)
        return getGiaGoc();
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("Dung tích: " + dungTichMl + " ml");
        System.out.println("Hạn sử dụng: " + hanSuDung);
    }
}