package sieuthi.model;

import java.time.LocalDate;

/**
 * Lớp đại diện cho Đồ uống trong siêu thị.
 * Kế thừa từ SanPham theo sơ đồ Class_Structure.drawio.
 */
public class DoUong extends SanPham {
    private int dungTichMl;
    private LocalDate hanSuDung;

    public DoUong() {
        super();
        this.dungTichMl = 500;
        this.hanSuDung = LocalDate.now().plusMonths(12);
    }

    public DoUong(String ma, String ten, String nuocSX, int namSX, String hangSX,
                  int tonKho, double giaGoc, int dungTichMl, LocalDate hanSuDung) {
        super(ma, ten, nuocSX, namSX, hangSX, tonKho, giaGoc);
        this.dungTichMl = Math.max(0, dungTichMl);
        this.hanSuDung = hanSuDung;
    }

    public int getDungTichMl() { return dungTichMl; }
    public void setDungTichMl(int dungTichMl) { this.dungTichMl = Math.max(0, dungTichMl); }

    public LocalDate getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(LocalDate hanSuDung) { this.hanSuDung = hanSuDung; }

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
        System.out.println("  Do uong | " + dungTichMl + "ml | HSD: " + hanSuDung);
    }
}
