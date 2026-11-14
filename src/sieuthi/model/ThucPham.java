package sieuthi.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

/**
 * Lớp đại diện cho Thực phẩm trong siêu thị.
 * Kế thừa từ SanPham theo sơ đồ Class_Structure.drawio.
 */
public class ThucPham extends SanPham {
    private static final int SO_NGAY_CAN_DATE = 3;
    private static final double TI_LE_GIAM_CAN_DATE = 0.3;

    private LocalDate hanSuDung;

    public ThucPham() {
        super();
        this.hanSuDung = LocalDate.now().plusDays(30);
    }

    public ThucPham(String ma, String ten, String nuocSX, int namSX, String hangSX,
                    int tonKho, double giaGoc, LocalDate hanSuDung) {
        super(ma, ten, nuocSX, namSX, hangSX, tonKho, giaGoc);
        this.hanSuDung = hanSuDung;
    }

    public LocalDate getHanSuDung() { return hanSuDung; }
    public void setHanSuDung(LocalDate hanSuDung) { this.hanSuDung = hanSuDung; }

    @Override
    public boolean conHanSuDung() {
        return !LocalDate.now().isAfter(hanSuDung);
    }

    /** Còn từ 0 đến 3 ngày là hết hạn thì giảm 30%. */
    @Override
    public double tinhGia() {
        long ngayConLai = ChronoUnit.DAYS.between(LocalDate.now(), hanSuDung);
        if (ngayConLai >= 0 && ngayConLai <= SO_NGAY_CAN_DATE) {
            return getGiaGoc() * (1 - TI_LE_GIAM_CAN_DATE);
        }
        return getGiaGoc();
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("  Thuc pham | HSD: " + hanSuDung);
    }
}
