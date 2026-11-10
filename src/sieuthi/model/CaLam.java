package sieuthi.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

/**
 * Lớp đại diện cho Ca làm việc của nhân viên siêu thị.
 * Cài đặt theo sơ đồ Class_Structure.drawio.
 */
public class CaLam {
    private final String maCa;
    private final LocalTime gioBatDau;
    private final LocalTime gioKetThuc;
    private final LocalDate ngayLam;

    public CaLam(String maCa, LocalDate ngayLam, LocalTime gioBatDau, LocalTime gioKetThuc) {
        if (maCa == null || maCa.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã ca không được để trống");
        }
        if (ngayLam == null || gioBatDau == null || gioKetThuc == null) {
            throw new IllegalArgumentException("Ngày làm và giờ làm không được null");
        }
        if (!gioKetThuc.isAfter(gioBatDau)) {
            throw new IllegalArgumentException("Giờ kết thúc phải sau giờ bắt đầu");
        }
        this.maCa = maCa.trim();
        this.ngayLam = ngayLam;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
    }

    public String getMaCa() {
        return maCa;
    }

    public LocalDate getNgayLam() {
        return ngayLam;
    }

    public LocalTime getGioBatDau() {
        return gioBatDau;
    }

    public LocalTime getGioKetThuc() {
        return gioKetThuc;
    }

    /**
     * Kiểm tra xem ca làm này có bị trùng thời gian với một ca làm khác hay không.
     * Hai ca làm được coi là trùng nhau nếu diễn ra trong cùng một ngày và có khoảng thời gian giao nhau.
     *
     * @param ca Ca làm cần so sánh
     * @return true nếu bị trùng thời gian, false nếu không trùng
     */
    public boolean kiemTraTrungCa(CaLam ca) {
        if (ca == null) {
            return false;
        }
        if (!this.ngayLam.equals(ca.ngayLam)) {
            return false;
        }
        // Không trùng nếu ca này kết thúc trước/đúng lúc ca kia bắt đầu, hoặc ca này bắt đầu sau/đúng lúc ca kia kết thúc
        boolean khongGiaoNhau = !this.gioKetThuc.isAfter(ca.gioBatDau) || !this.gioBatDau.isBefore(ca.gioKetThuc);
        return !khongGiaoNhau;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CaLam caLam = (CaLam) o;
        return Objects.equals(maCa, caLam.maCa);
    }

    @Override
    public int hashCode() {
        return Objects.hash(maCa);
    }

    @Override
    public String toString() {
        return "CaLam[Mã: " + maCa + " | Ngày: " + ngayLam + " | " + gioBatDau + " - " + gioKetThuc + "]";
    }
}

