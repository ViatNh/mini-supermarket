package sieuthi.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Lớp đại diện cho Đơn nhập hàng từ Nhà cung cấp vào kho của siêu thị.
 * Cài đặt theo sơ đồ Class_Structure.drawio.
 * Nghiệp vụ quan trọng: Số lượng tồn kho (stock) của sản phẩm CHỈ ĐƯỢC TĂNG
 * thông qua việc nhập hàng hợp lệ từ phiếu này.
 */
public class NhapHang {
    private String maNhapHang;
    private LocalDate ngayNhap;
    private NhaCungCap nhaCungCap;
    private NhanVien nguoiLap;
    private final List<ChiTietNhapHang> dsChiTiet;

    public NhapHang() {
        this.maNhapHang = "";
        this.ngayNhap = LocalDate.now();
        this.dsChiTiet = new ArrayList<>();
    }

    public NhapHang(String maNhapHang, LocalDate ngayNhap, NhaCungCap nhaCungCap, NhanVien nguoiLap) {
        if (maNhapHang == null || maNhapHang.trim().isEmpty()) {
            throw new IllegalArgumentException("Mã nhập hàng không được rỗng");
        }
        if (nhaCungCap == null) {
            throw new IllegalArgumentException("Nhà cung cấp không được null");
        }
        if (nguoiLap == null) {
            throw new IllegalArgumentException("Người lập phiếu nhập không được null");
        }
        this.maNhapHang = maNhapHang.trim();
        this.ngayNhap = (ngayNhap != null) ? ngayNhap : LocalDate.now();
        this.nhaCungCap = nhaCungCap;
        this.nguoiLap = nguoiLap;
        this.dsChiTiet = new ArrayList<>();
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

    public List<ChiTietNhapHang> getDsChiTiet() {
        return Collections.unmodifiableList(dsChiTiet);
    }

    /**
     * Thêm sản phẩm vào phiếu nhập hàng và TỰ ĐỘNG CẬP NHẬT TĂNG TỒN KHO cho sản phẩm.
     * Đây là cách duy nhất hợp lệ để gia tăng số lượng tồn kho (stock) trong siêu thị.
     *
     * @param sp         Sản phẩm nhập
     * @param soLuong    Số lượng nhập (> 0)
     * @param donGiaNhap Đơn giá nhập hàng (>= 0)
     */
    public void themChiTietVaCapNhatTonKho(SanPham sp, int soLuong, double donGiaNhap) {
        if (sp == null) {
            throw new IllegalArgumentException("Sản phẩm không được null");
        }
        if (soLuong <= 0) {
            throw new IllegalArgumentException("Số lượng nhập phải lớn hơn 0");
        }
        if (donGiaNhap < 0) {
            throw new IllegalArgumentException("Đơn giá nhập không được âm");
        }

        // Tăng tồn kho của sản phẩm
        sp.tangTonKho(soLuong);

        // Lưu vào chi tiết nhập hàng
        ChiTietNhapHang daCo = timChiTiet(sp);
        if (daCo != null) {
            daCo.tangSoLuong(soLuong);
            daCo.setDonGiaNhap(donGiaNhap);
        } else {
            dsChiTiet.add(new ChiTietNhapHang(sp, soLuong, donGiaNhap));
        }
    }

    /**
     * Dùng khi khôi phục dữ liệu từ file lưu trữ (đã có sẵn tồn kho trong file sản phẩm).
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
            dsChiTiet.add(new ChiTietNhapHang(sp, soLuong, donGiaNhap));
        }
    }

    private ChiTietNhapHang timChiTiet(SanPham sp) {
        for (ChiTietNhapHang ct : dsChiTiet) {
            if (ct.getSanPham().getMaSanPham().equalsIgnoreCase(sp.getMaSanPham())) {
                return ct;
            }
        }
        return null;
    }

    /**
     * Tính tổng tiền chi phí nhập của cả phiếu nhập hàng.
     *
     * @return Tổng tiền (VNĐ)
     */
    public double tinhTongTien() {
        double tong = 0;
        for (ChiTietNhapHang ct : dsChiTiet) {
            tong += ct.tinhThanhTien();
        }
        return tong;
    }

    public void hienThiPhieuNhap() {
        System.out.println("==================== PHIẾU NHẬP HÀNG " + maNhapHang + " ====================");
        System.out.println("Ngày nhập     : " + ngayNhap);
        System.out.println("Người tiếp nhận: " + (nguoiLap != null ? nguoiLap.getHoTen() : "N/A"));
        System.out.println("Nhà cung cấp  : " + (nhaCungCap != null ? nhaCungCap.getTenNhaCungCap() : "N/A"));
        System.out.println("----------------------------------------------------------------------");
        System.out.printf("%-5s | %-10s | %-25s | %-8s | %-14s | %-14s%n",
                "STT", "Mã SP", "Tên sản phẩm", "SL nhập", "Giá nhập", "Thành tiền");
        System.out.println("----------------------------------------------------------------------");
        int stt = 1;
        for (ChiTietNhapHang ct : dsChiTiet) {
            System.out.printf("%-5d | %-10s | %-25s | %-8d | %-14s | %-14s%n",
                    stt++,
                    ct.getSanPham().getMaSanPham(),
                    ct.getSanPham().getTenSanPham(),
                    ct.getSoLuong(),
                    HoaDon.dinhDangTien(ct.getDonGiaNhap()),
                    HoaDon.dinhDangTien(ct.tinhThanhTien()));
        }
        System.out.println("----------------------------------------------------------------------");
        System.out.println("TỔNG TIỀN NHẬP: " + HoaDon.dinhDangTien(tinhTongTien()));
        System.out.println("======================================================================");
    }
}
