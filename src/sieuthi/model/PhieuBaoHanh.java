package sieuthi.model;

import java.time.LocalDate;

/** Phiếu bảo hành kèm theo một hóa đơn đã thanh toán, cho sản phẩm có bảo hành (IBaoHanh). */
public class PhieuBaoHanh {
    private final String maPhieuBH;
    private final HoaDon hoaDon;
    private final SanPham sanPham;
    private final KhachHang khachHang;
    private final LocalDate ngayBatDau;
    private final LocalDate ngayKetThuc;
    private String moTaLoi;
    private String trangThai;

    public PhieuBaoHanh(String maPhieuBH, HoaDon hoaDon, SanPham sanPham,
                        KhachHang khachHang, String moTaLoi) {
        if (!hoaDon.isDaThanhToan()) {
            throw new IllegalStateException("Hóa đơn chưa thanh toán, không thể tạo phiếu bảo hành");
        }
        if (!hoaDon.coSanPham(sanPham)) {
            throw new IllegalArgumentException("Sản phẩm không có trong hóa đơn " + hoaDon.getMaHoaDon());
        }
        if (!(sanPham instanceof IBaoHanh)) {
            throw new IllegalArgumentException("Sản phẩm " + sanPham.getTenSanPham() + " không có bảo hành");
        }
        this.maPhieuBH = maPhieuBH;
        this.hoaDon = hoaDon;
        this.sanPham = sanPham;
        this.khachHang = khachHang;
        this.ngayBatDau = hoaDon.getNgayLap();
        this.ngayKetThuc = ngayBatDau.plusMonths(((IBaoHanh) sanPham).layThoiHanBaoHanh());
        this.moTaLoi = moTaLoi;
        this.trangThai = "Đã tiếp nhận";
    }

    public PhieuBaoHanh(String maPhieuBH, HoaDon hoaDon, SanPham sanPham,
                        KhachHang khachHang, LocalDate ngayBatDau, LocalDate ngayKetThuc,
                        String moTaLoi, String trangThai) {
        this.maPhieuBH = maPhieuBH;
        this.hoaDon = hoaDon;
        this.sanPham = sanPham;
        this.khachHang = khachHang;
        this.ngayBatDau = (ngayBatDau != null) ? ngayBatDau : LocalDate.now();
        this.ngayKetThuc = (ngayKetThuc != null) ? ngayKetThuc : this.ngayBatDau.plusMonths(12);
        this.moTaLoi = (moTaLoi != null) ? moTaLoi : "Bảo hành định kỳ";
        this.trangThai = (trangThai != null) ? trangThai : "Đang hiệu lực";
    }

    public String getMaPhieuBH() { return maPhieuBH; }
    public HoaDon getHoaDon() { return hoaDon; }
    public SanPham getSanPham() { return sanPham; }
    public KhachHang getKhachHang() { return khachHang; }
    public LocalDate getNgayBatDau() { return ngayBatDau; }
    public LocalDate getNgayKetThuc() { return ngayKetThuc; }
    public String getMoTaLoi() { return moTaLoi; }
    public String getTrangThai() { return trangThai; }

    public boolean kiemTraConHan() {
        return !LocalDate.now().isAfter(ngayKetThuc);
    }

    public void capNhatTrangThai(String trangThaiMoi) {
        this.trangThai = trangThaiMoi;
    }

    public void hienThiThongTin() {
        System.out.println("Phiếu BH " + maPhieuBH + " | Hóa đơn " + hoaDon.getMaHoaDon()
                + " | " + sanPham.getTenSanPham()
                + " | " + (khachHang == null ? "Khách vãng lai" : khachHang.getHoTen())
                + " | " + ngayBatDau + " -> " + ngayKetThuc
                + " | " + (kiemTraConHan() ? "Còn hạn" : "Hết hạn")
                + " | Lỗi: " + moTaLoi + " | Trạng thái: " + trangThai);
    }
}
