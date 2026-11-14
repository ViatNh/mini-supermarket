package sieuthi.model;

import java.time.LocalDate;

/**
 * Lớp đại diện cho Đồ dùng trong siêu thị.
 * Kế thừa từ SanPham và thực thi IBaoHanh theo sơ đồ Class_Structure.drawio.
 */
public class DoDung extends SanPham implements IBaoHanh {
    private String chatLieu;
    /** Thời hạn bảo hành tính theo THÁNG. */
    private int thoiHanBaoHanh;

    public DoDung() {
        super();
        this.chatLieu = "Inox";
        this.thoiHanBaoHanh = 12;
    }

    public DoDung(String ma, String ten, String nuocSX, int namSX, String hangSX,
                  int tonKho, double giaGoc, String chatLieu, int thoiHanBaoHanh) {
        super(ma, ten, nuocSX, namSX, hangSX, tonKho, giaGoc);
        this.chatLieu = chatLieu;
        this.thoiHanBaoHanh = Math.max(0, thoiHanBaoHanh);
    }

    public String getChatLieu() { return chatLieu; }
    public void setChatLieu(String chatLieu) { this.chatLieu = chatLieu; }

    public void setThoiHanBaoHanh(int thoiHanBaoHanh) { this.thoiHanBaoHanh = Math.max(0, thoiHanBaoHanh); }

    @Override
    public double tinhGia() {
        return getGiaGoc();
    }

    @Override
    public boolean kiemTraBaoHanh(LocalDate ngayMua) {
        if (ngayMua == null) return false;
        return !LocalDate.now().isAfter(ngayMua.plusMonths(thoiHanBaoHanh));
    }

    @Override
    public int layThoiHanBaoHanh() {
        return thoiHanBaoHanh;
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("  Do dung | Chat lieu: " + chatLieu + " | Bao hanh: " + thoiHanBaoHanh + " thang");
    }
}
