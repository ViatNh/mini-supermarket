package sieuthi.model;

import java.time.LocalDate;

public class DoDung extends SanPham implements IBaoHanh {
    private final String chatLieu;
    /** Thời hạn bảo hành tính theo THÁNG. */
    private final int thoiHanBaoHanh;

    public DoDung(String ma, String ten, String nuocSX, int namSX, String hangSX,
                  int tonKho, double giaGoc, String chatLieu, int thoiHanBaoHanh) {
        super(ma, ten, nuocSX, namSX, hangSX, tonKho, giaGoc);
        this.chatLieu = chatLieu;
        this.thoiHanBaoHanh = thoiHanBaoHanh;
    }

    public String getChatLieu() { return chatLieu; }

    @Override
    public double tinhGia() {
        return getGiaGoc();
    }

    @Override
    public boolean kiemTraBaoHanh(LocalDate ngayMua) {
        return !LocalDate.now().isAfter(ngayMua.plusMonths(thoiHanBaoHanh));
    }

    @Override
    public int layThoiHanBaoHanh() {
        return thoiHanBaoHanh;
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("  Đồ dùng | " + chatLieu + " | Bảo hành: " + thoiHanBaoHanh + " tháng");
    }
}
