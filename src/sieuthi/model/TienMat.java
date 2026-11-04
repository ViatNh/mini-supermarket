package sieuthi.model;

public class TienMat implements IPhuongThucThanhToan {
    @Override
    public boolean thanhToan(double soTien) {
        return soTien > 0;
    }
}
