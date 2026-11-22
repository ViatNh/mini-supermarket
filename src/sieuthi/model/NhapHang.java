package sieuthi.model;

import java.time.LocalDate;

/**
 * Lop dai dien cho Don nhap hang tu Nha cung cap vao kho cua sieu thi.
 * Quan ly mang cac ChiTietNhapHang theo chuan mang doi tuong OOP.
 * Nghiep vu: So luong ton kho (stock) cua san pham CHI DUOC TANG thong qua viec nhap hang hop le tu phieu nay.
 */
public class NhapHang {
    private static final int SUC_CHUA_MAC_DINH = 10;

    private String maNhapHang;
    private LocalDate ngayNhap;
    private NhaCungCap nhaCungCap;
    private NhanVien nguoiLap;
    private ChiTietNhapHang[] dsChiTiet;
    private int soLuongChiTiet;

    public NhapHang() {
        this.maNhapHang = "";
        this.ngayNhap = LocalDate.now();
        this.dsChiTiet = new ChiTietNhapHang[SUC_CHUA_MAC_DINH];
        this.soLuongChiTiet = 0;
    }

    public NhapHang(String maNhapHang, LocalDate ngayNhap, NhaCungCap nhaCungCap, NhanVien nguoiLap) {
        if (maNhapHang == null || maNhapHang.trim().isEmpty()) {
            throw new IllegalArgumentException("Ma nhap hang khong duoc rong");
        }
        if (nhaCungCap == null) {
            throw new IllegalArgumentException("Nha cung cap khong duoc null");
        }
        if (nguoiLap == null) {
            throw new IllegalArgumentException("Nguoi lap phieu nhap khong duoc null");
        }
        this.maNhapHang = maNhapHang.trim();
        this.ngayNhap = (ngayNhap != null) ? ngayNhap : LocalDate.now();
        this.nhaCungCap = nhaCungCap;
        this.nguoiLap = nguoiLap;
        this.dsChiTiet = new ChiTietNhapHang[SUC_CHUA_MAC_DINH];
        this.soLuongChiTiet = 0;
    }

    public String getMaNhapHang() {
        return maNhapHang;
    }

    public void setMaNhapHang(String maNhapHang) {
        this.maNhapHang = maNhapHang;
    }

    public LocalDate getNgayNhap() {
        return ngayNhap;
    }

    public void setNgayNhap(LocalDate ngayNhap) {
        this.ngayNhap = ngayNhap;
    }

    public NhaCungCap getNhaCungCap() {
        return nhaCungCap;
    }

    public void setNhaCungCap(NhaCungCap nhaCungCap) {
        this.nhaCungCap = nhaCungCap;
    }

    public NhanVien getNguoiLap() {
        return nguoiLap;
    }

    public void setNguoiLap(NhanVien nguoiLap) {
        this.nguoiLap = nguoiLap;
    }

    public ChiTietNhapHang[] getDsChiTiet() {
        ChiTietNhapHang[] ketQua = new ChiTietNhapHang[soLuongChiTiet];
        System.arraycopy(dsChiTiet, 0, ketQua, 0, soLuongChiTiet);
        return ketQua;
    }

    public int getSoLuongChiTiet() {
        return soLuongChiTiet;
    }

    private void tangKichThuocMang() {
        ChiTietNhapHang[] moi = new ChiTietNhapHang[dsChiTiet.length * 2];
        System.arraycopy(dsChiTiet, 0, moi, 0, soLuongChiTiet);
        dsChiTiet = moi;
    }

    /**
     * Them san pham vao phieu nhap hang va TU DONG CAP NHAT TANG TON KHO cho san pham.
     * Day la cach duy nhat hop le de gia tang so luong ton kho (stock) trong sieu thi.
     *
     * @param sp         San pham nhap
     * @param soLuong    So luong nhap (> 0)
     * @param donGiaNhap Don gia nhap hang (>= 0)
     */
    public void themChiTietVaCapNhatTonKho(SanPham sp, int soLuong, double donGiaNhap) {
        if (sp == null) {
            throw new IllegalArgumentException("San pham khong duoc null");
        }
        if (soLuong <= 0) {
            throw new IllegalArgumentException("So luong nhap phai lon hon 0");
        }
        if (donGiaNhap < 0) {
            throw new IllegalArgumentException("Don gia nhap khong duoc am");
        }

        // Tang ton kho cua san pham
        sp.tangTonKho(soLuong);

        // Luu vao chi tiet nhap hang
        ChiTietNhapHang daCo = timChiTiet(sp);
        if (daCo != null) {
            daCo.tangSoLuong(soLuong);
            daCo.setDonGiaNhap(donGiaNhap);
        } else {
            if (soLuongChiTiet >= dsChiTiet.length) {
                tangKichThuocMang();
            }
            dsChiTiet[soLuongChiTiet++] = new ChiTietNhapHang(sp, soLuong, donGiaNhap);
        }
    }

    /**
     * Dung khi khoi phuc du lieu tu file luu tru (da co san ton kho trong file san pham).
     */
    public void themChiTietLuuTru(SanPham sp, int soLuong, double donGiaNhap) {
        if (sp == null || soLuong <= 0 || donGiaNhap < 0) {
            return;
        }
        ChiTietNhapHang daCo = timChiTiet(sp);
        if (daCo != null) {
            daCo.tangSoLuong(soLuong);
            daCo.setDonGiaNhap(donGiaNhap);
        } else {
            if (soLuongChiTiet >= dsChiTiet.length) {
                tangKichThuocMang();
            }
            dsChiTiet[soLuongChiTiet++] = new ChiTietNhapHang(sp, soLuong, donGiaNhap);
        }
    }

    private ChiTietNhapHang timChiTiet(SanPham sp) {
        for (int i = 0; i < soLuongChiTiet; i++) {
            if (dsChiTiet[i].getSanPham().getMaSanPham().equalsIgnoreCase(sp.getMaSanPham())) {
                return dsChiTiet[i];
            }
        }
        return null;
    }

    /**
     * Tinh tong tien chi phi nhap cua ca phieu nhap hang.
     *
     * @return Tong tien (VND)
     */
    public double tinhTongTien() {
        double tong = 0;
        for (int i = 0; i < soLuongChiTiet; i++) {
            tong += dsChiTiet[i].tinhThanhTien();
        }
        return tong;
    }

    public void hienThiPhieuNhap() {
        System.out.println("==================== PHIEU NHAP HANG " + maNhapHang + " ====================");
        System.out.println("Ngay nhap      : " + ngayNhap);
        System.out.println("Nguoi tiep nhan: " + (nguoiLap != null ? nguoiLap.getHoTen() : "N/A"));
        System.out.println("Nha cung cap   : " + (nhaCungCap != null ? nhaCungCap.getTenNhaCungCap() : "N/A"));
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-5s | %-10s | %-25s | %-8s | %-14s | %-14s%n",
                "STT", "Ma SP", "Ten san pham", "SL nhap", "Gia nhap", "Thanh tien");
        System.out.println("----------------------------------------------------------------------");
        int stt = 1;
        for (int i = 0; i < soLuongChiTiet; i++) {
            ChiTietNhapHang ct = dsChiTiet[i];
            System.out.printf("%-5d | %-10s | %-25s | %-8d | %-14s | %-14s%n",
                    stt++,
                    ct.getSanPham().getMaSanPham(),
                    ct.getSanPham().getTenSanPham(),
                    ct.getSoLuong(),
                    HoaDon.dinhDangTien(ct.getDonGiaNhap()),
                    HoaDon.dinhDangTien(ct.tinhThanhTien()));
        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("TONG TIEN NHAP: " + HoaDon.dinhDangTien(tinhTongTien()));
        System.out.println("======================================================================");
    }
}
