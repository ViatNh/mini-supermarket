package sieuthi.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import java.util.Scanner;

/**
 * Lớp đại diện cho Ca làm việc của nhân viên siêu thị.
 * Cài đặt theo sơ đồ Class_Structure.drawio.
 */
public class CaLam {
    private String maCa;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private LocalDate ngayLam;

    public CaLam() {
        this.maCa = "";
        this.ngayLam = LocalDate.now();
        this.gioBatDau = LocalTime.of(8, 0);
        this.gioKetThuc = LocalTime.of(12, 0);
    }

    public CaLam(String maCa, LocalDate ngayLam, LocalTime gioBatDau, LocalTime gioKetThuc) {
        if (maCa == null || maCa.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma ca khong duoc de trong");
        }
        if (ngayLam == null || gioBatDau == null || gioKetThuc == null) {
            throw new IllegalArgumentException("Ngay lam va gio lam khong duoc null");
        }
        if (!gioKetThuc.isAfter(gioBatDau)) {
            throw new IllegalArgumentException("Gio ket thuc phai sau gio bat dau");
        }
        this.maCa = maCa.trim();
        this.ngayLam = ngayLam;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
    }

    // Constructor bổ sung theo thứ tự (maCa, gioBatDau, gioKetThuc, ngayLam) từ nhánh Person
    public CaLam(String maCa, LocalTime gioBatDau, LocalTime gioKetThuc, LocalDate ngayLam) {
        this(maCa, ngayLam, gioBatDau, gioKetThuc);
    }

    public String getMaCa() { return maCa; }
    public void setMaCa(String maCa) { this.maCa = maCa; }

    public LocalDate getNgayLam() { return ngayLam; }
    public void setNgayLam(LocalDate ngayLam) { this.ngayLam = ngayLam; }

    public LocalTime getGioBatDau() { return gioBatDau; }
    public void setGioBatDau(LocalTime gioBatDau) { this.gioBatDau = gioBatDau; }

    public LocalTime getGioKetThuc() { return gioKetThuc; }
    public void setGioKetThuc(LocalTime gioKetThuc) { this.gioKetThuc = gioKetThuc; }

    /**
     * Kiểm tra xem ca làm này có bị trùng thời gian với một ca làm khác hay không.
     * Hai ca làm được coi là trùng nhau nếu diễn ra trong cùng một ngày và có khoảng thời gian giao nhau.
     *
     * @param ca Ca làm cần so sánh
     * @return true nếu bị trùng thời gian, false nếu không trùng
     */
    public boolean kiemTraTrungCa(CaLam ca) {
        if (ca == null) return false;
        if (!this.ngayLam.equals(ca.ngayLam)) return false;
        // Không trùng nếu ca này kết thúc trước/đúng lúc ca kia bắt đầu, hoặc ca này bắt đầu sau/đúng lúc ca kia kết thúc
        boolean khongGiaoNhau = !this.gioKetThuc.isAfter(ca.gioBatDau) || !this.gioBatDau.isBefore(ca.gioKetThuc);
        return !khongGiaoNhau;
    }

    /**
     * Nhập thông tin ca làm từ bàn phím.
     */
    public void nhapCaLam() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Nhap ma ca: ");
        this.maCa = sc.nextLine();

        System.out.print("Nhap ngay lam (dinh dang yyyy-MM-dd): ");
        this.ngayLam = LocalDate.parse(sc.nextLine(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        System.out.print("Nhap gio bat dau (dinh dang HH:mm): ");
        this.gioBatDau = LocalTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));

        System.out.print("Nhap gio ket thuc (dinh dang HH:mm): ");
        this.gioKetThuc = LocalTime.parse(sc.nextLine(), DateTimeFormatter.ofPattern("HH:mm"));
    }

    public void hienThiThongTin() {
        System.out.println("Ca lam: " + maCa + " | Ngay: " + ngayLam + " | Gio: " + gioBatDau + " - " + gioKetThuc);
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
        return "CaLam[Ma: " + maCa + " | Ngay: " + ngayLam + " | " + gioBatDau + " - " + gioKetThuc + "]";
    }
}
