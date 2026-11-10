import java.time.LocalDate;

public class DoDung extends SanPham implements IBaoHanh {
    private String chatLieu;
    private int thoiHanBaoHanh; // tính theo tháng

    public DoDung() {
        super();
    }

    public DoDung(String maSanPham, String tenSanPham, String nuocSanXuat, 
                  int namSanXuat, String hangSanXuat, int soLuongTonKho, 
                  double giaGoc, String chatLieu, int thoiHanBaoHanh) {
        super(maSanPham, tenSanPham, nuocSanXuat, namSanXuat, hangSanXuat, soLuongTonKho, giaGoc);
        this.chatLieu = chatLieu;
        this.thoiHanBaoHanh = thoiHanBaoHanh;
    }

    public String getChatLieu() {
        return chatLieu;
    }

    public void setChatLieu(String chatLieu) {
        this.chatLieu = chatLieu;
    }

    public void setThoiHanBaoHanh(int thoiHanBaoHanh) {
        this.thoiHanBaoHanh = thoiHanBaoHanh;
    }

    @Override
    public double tinhGia() {
        return getGiaGoc();
    }

    @Override
    public boolean kiemTraBaoHanh(LocalDate ngayMua) {
        // Kiểm tra xem thời điểm hiện tại đã vượt quá hạn bảo hành hay chưa
        LocalDate hetHanBaoHanh = ngayMua.plusMonths(thoiHanBaoHanh);
        return !LocalDate.now().isAfter(hetHanBaoHanh);
    }

    @Override
    public int layThoiHanBaoHanh() {
        return this.thoiHanBaoHanh;
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("Chất liệu: " + chatLieu);
        System.out.println("Thời hạn bảo hành: " + thoiHanBaoHanh + " tháng");
    }
}