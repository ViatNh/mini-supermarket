package sieuthi.model;

public interface IPhuongThucThanhToan {
    /** Thực hiện thanh toán số tiền; true nếu thành công. */
    boolean thanhToan(double soTien);
}
