package sieuthi.model;

/** Chỉ ThuNgan được thực hiện nghiệp vụ thu tiền theo quy định cửa hàng. */
public class ThuNgan extends NhanVienFullTime {
    private final String maQuay;

    public ThuNgan(String maNhanVien, String hoTen, String diaChi, String soDienThoai,
                   int soNgayCong, double heSoLuong, String maQuay) {
        super(maNhanVien, hoTen, diaChi, soDienThoai, soNgayCong, heSoLuong);
        this.maQuay = maQuay;
    }

    public String getMaQuay() { return maQuay; }

    @Override
    public double tinhLuong() {
        return LUONG_CO_BAN * getHeSoLuong() * getSoNgayCong() / NGAY_CONG_CHUAN;
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("  Thu ngân | Quầy: " + maQuay);
    }

    /**
     * Thu tiền một hóa đơn. Trả về true nếu thanh toán thành công.
     * Thất bại (false) khi hóa đơn đã thanh toán, rỗng, hoặc phương thức thanh toán từ chối.
     */
    public boolean thanhToanHoaDon(HoaDon hd, IPhuongThucThanhToan phuongThuc) {
        if (hd == null || phuongThuc == null) return false;
        if (hd.isDaThanhToan() || hd.getDsChiTiet().isEmpty()) return false;

        double soTien = hd.tinhTienPhaiTra();
        if (!phuongThuc.thanhToan(soTien)) return false;

        hd.hoanTatThanhToan(this, phuongThuc);
        return true;
    }
}
