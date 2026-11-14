package sieuthi.model;

import java.time.LocalDate;

public class KhuyenMai implements IApDungKhuyenMai {
    private final String maKhuyenMai;
    /** Tỉ lệ giảm dạng phân số: 0.1 = giảm 10%. */
    private final double tiLeGiam;
    private final LocalDate ngayBatDau;
    private final LocalDate ngayKetThuc;

    public KhuyenMai(String maKhuyenMai, double tiLeGiam, LocalDate ngayBatDau, LocalDate ngayKetThuc) {
        if (tiLeGiam < 0 || tiLeGiam > 1) {
            throw new IllegalArgumentException("Ti le giam phai nam trong [0, 1]");
        }
        if (ngayKetThuc.isBefore(ngayBatDau)) {
            throw new IllegalArgumentException("Ngay ket thuc phai sau ngay bat dau");
        }
        this.maKhuyenMai = maKhuyenMai;
        this.tiLeGiam = tiLeGiam;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
    }

    public String getMaKhuyenMai() { return maKhuyenMai; }
    public double getTiLeGiam() { return tiLeGiam; }
    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public LocalDate getNgayKetThuc() { return ngayKetThuc; }

    public boolean conHieuLuc(LocalDate ngay) {
        return !ngay.isBefore(ngayBatDau) && !ngay.isAfter(ngayKetThuc);
    }

    @Override
    public double apDung(SanPham sp) {
        return sp.tinhGia() * tiLeGiam;
    }
}
