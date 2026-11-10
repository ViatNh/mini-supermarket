package sieuthi.manage;

/**
 * Interface định nghĩa các nghiệp vụ quản lý cơ bản (CRUD) cho một đối tượng kiểu T.
 * Cài đặt chuẩn theo sơ đồ lớp Class_Structure.drawio của thành viên Vinh.
 *
 * @param <T> Kiểu thực thể cần quản lý (KhachHang, SanPham, HoaDon, NhapHang,...)
 */
public interface IQuanLy<T> {

    /**
     * Thêm một đối tượng mới vào danh sách quản lý.
     *
     * @param doiTuong Đối tượng cần thêm
     * @return true nếu thêm thành công, false nếu thất bại (trùng mã, null,...)
     */
    boolean them(T doiTuong);

    /**
     * Sửa (cập nhật) thông tin đối tượng theo mã định danh.
     *
     * @param ma       Mã định danh của đối tượng cần sửa
     * @param doiTuong Thông tin đối tượng mới để cập nhật
     * @return true nếu sửa thành công, false nếu không tìm thấy mã
     */
    boolean sua(String ma, T doiTuong);

    /**
     * Xóa một đối tượng khỏi danh sách theo mã định danh.
     *
     * @param ma Mã định danh của đối tượng cần xóa
     * @return true nếu xóa thành công, false nếu không tìm thấy mã
     */
    boolean xoa(String ma);

    /**
     * Tìm kiếm đối tượng theo mã định danh.
     *
     * @param ma Mã định danh cần tìm kiếm
     * @return Đối tượng tìm thấy hoặc null nếu không tồn tại
     */
    T timKiemTheoMa(String ma);

    /**
     * Hiển thị toàn bộ danh sách đối tượng đang quản lý ra console.
     */
    void hienThiDanhSach();
}
