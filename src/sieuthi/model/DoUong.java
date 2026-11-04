package sieuthi.model;

import java.time.LocalDate;

public class DoUong extends SanPham {
    private final int dungTichMl;
    private final LocalDate hanSuDung;

    public DoUong(String ma, String ten, String nuocSX, int namSX, String hangSX,
                  int tonKho, double giaGoc, int dungTichMl, LocalDate hanSuDung) {
        super(ma, ten, nuocSX, namSX, hangSX, tonKho, giaGoc);
        this.dungTichMl = dungTichMl;
        this.hanSuDung = hanSuDung;
    }

    public int getDungTichMl() { return dungTichMl; }
    public LocalDate getHanSuDung() { return hanSuDung; }

    @Override
    public boolean conHanSuDung() {
        return !LocalDate.now().isAfter(hanSuDung);
    }

    @Override
    public double tinhGia() {
        return getGiaGoc();
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("  Đồ uống | " + dungTichMl + "ml | HSD: " + hanSuDung);
    }
}
