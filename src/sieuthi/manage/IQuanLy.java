package sieuthi.manage;

/**
 * Interface dinh nghia cac nghiep vu quan ly co ban (CRUD) cho mot doi tuong kieu T.
 * Cai dat chuan theo so do lop Class_Structure.drawio.
 *
 * @param <T> Kieu thuc the can quan ly (KhachHang, SanPham, HoaDon, NhapHang,...)
 */
public interface IQuanLy<T> {

    /**
     * Them mot doi tuong moi vao danh sach quan ly.
     *
     * @param doiTuong Doi tuong can them
     * @return true neu them thanh cong, false neu that bai (trung ma, null,...)
     */
    boolean them(T doiTuong);

    /**
     * Sua (cap nhat) thong tin doi tuong theo ma dinh danh.
     *
     * @param ma       Ma dinh danh cua doi tuong can sua
     * @param doiTuong Thong tin doi tuong moi de cap nhat
     * @return true neu sua thanh cong, false neu khong tim thay ma
     */
    boolean sua(String ma, T doiTuong);

    /**
     * Xoa mot doi tuong khoi danh sach theo ma dinh danh.
     *
     * @param ma Ma dinh danh cua doi tuong can xoa
     * @return true neu xoa thanh cong, false neu khong tim thay ma
     */
    boolean xoa(String ma);

    /**
     * Tim kiem doi tuong theo ma dinh danh.
     *
     * @param ma Ma dinh danh can tim kiem
     * @return Doi tuong tim thay hoac null neu khong ton tai
     */
    T timKiemTheoMa(String ma);

    /**
     * Hien thi toan bo danh sach doi tuong dang quan ly ra console.
     */
    void hienThiDanhSach();

    /**
     * Lay so luong doi tuong hien co trong danh sach.
     */
    int getSoLuong();
}
