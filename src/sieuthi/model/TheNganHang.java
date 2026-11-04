package sieuthi.model;

public class TheNganHang implements IPhuongThucThanhToan {
    private final String soThe;

    public TheNganHang(String soThe) {
        this.soThe = soThe;
    }

    public String getSoThe() { return soThe; }

    /** Số thẻ hợp lệ khi gồm đúng 16 chữ số. */
    @Override
    public boolean thanhToan(double soTien) {
        return soTien > 0 && soThe != null && soThe.matches("\\d{16}");
    }
}
