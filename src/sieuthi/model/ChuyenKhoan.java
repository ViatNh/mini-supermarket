package sieuthi.model;

public class ChuyenKhoan implements IPhuongThucThanhToan {
    private final String maGiaoDich;

    public ChuyenKhoan(String maGiaoDich) {
        this.maGiaoDich = maGiaoDich;
    }

    public String getMaGiaoDich() { return maGiaoDich; }

    @Override
    public boolean thanhToan(double soTien) {
        return soTien > 0 && maGiaoDich != null && !maGiaoDich.isBlank();
    }
}
