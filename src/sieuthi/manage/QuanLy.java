package sieuthi.manage;

import sieuthi.model.CaLam;
import sieuthi.model.HoaDon;
import sieuthi.model.NhanVien;
import sieuthi.model.NhanVienFullTime;

/**
 * Lớp Quản lý (Manager) - kế thừa từ NhanVienFullTime.
 * Có trách nhiệm điều phối toàn bộ các bộ phận: Quản lý khách hàng, Quản lý sản phẩm,
 * Quản lý đơn hàng, Quản lý nhập hàng và phân công ca làm việc cho nhân viên.
 * Cài đặt chuẩn theo sơ đồ Class_Structure.drawio của thành viên Vinh.
 */
public class QuanLy extends NhanVienFullTime {
    private double phuCap;
    private final QuanLyKhachHang qlKhachHang;
    private final QuanLySanPham qlSanPham;
    private final QuanLyDonHang qlDonHang;
    private final QuanLyNhapHang qlNhapHang;

    public QuanLy() {
        super();
        this.phuCap = 0;
        this.qlKhachHang = new QuanLyKhachHang();
        this.qlSanPham = new QuanLySanPham();
        this.qlDonHang = new QuanLyDonHang();
        this.qlNhapHang = new QuanLyNhapHang();
    }

    public QuanLy(String maNhanVien, String hoTen, String diaChi, String soDienThoai,
                  int soNgayCong, double heSoLuong, double phuCap) {
        this(maNhanVien, hoTen, diaChi, soDienThoai, soNgayCong, heSoLuong, phuCap,
                new QuanLyKhachHang(), new QuanLySanPham(), new QuanLyDonHang(), new QuanLyNhapHang());
    }

    public QuanLy(String maNhanVien, String hoTen, String diaChi, String soDienThoai,
                  int soNgayCong, double heSoLuong, double phuCap,
                  QuanLyKhachHang qlKhachHang, QuanLySanPham qlSanPham, QuanLyDonHang qlDonHang) {
        this(maNhanVien, hoTen, diaChi, soDienThoai, soNgayCong, heSoLuong, phuCap,
                qlKhachHang, qlSanPham, qlDonHang, new QuanLyNhapHang());
    }

    public QuanLy(String maNhanVien, String hoTen, String diaChi, String soDienThoai,
                  int soNgayCong, double heSoLuong, double phuCap,
                  QuanLyKhachHang qlKhachHang, QuanLySanPham qlSanPham,
                  QuanLyDonHang qlDonHang, QuanLyNhapHang qlNhapHang) {
        super(maNhanVien, hoTen, diaChi, soDienThoai, soNgayCong, heSoLuong);
        if (phuCap < 0) {
            throw new IllegalArgumentException("Phu cap khong duoc am");
        }
        this.phuCap = phuCap;
        this.qlKhachHang = (qlKhachHang != null) ? qlKhachHang : new QuanLyKhachHang();
        this.qlSanPham = (qlSanPham != null) ? qlSanPham : new QuanLySanPham();
        this.qlDonHang = (qlDonHang != null) ? qlDonHang : new QuanLyDonHang();
        this.qlNhapHang = (qlNhapHang != null) ? qlNhapHang : new QuanLyNhapHang();
    }

    public double getPhuCap() {
        return phuCap;
    }

    public void setPhuCap(double phuCap) {
        if (phuCap < 0) {
            throw new IllegalArgumentException("Phu cap khong duoc am");
        }
        this.phuCap = phuCap;
    }

    /**
     * Tính lương của Quản lý: Lương cơ bản * Hệ số lương * (Số ngày công / 26) + Phụ cấp.
     */
    @Override
    public double tinhLuong() {
        return LUONG_CO_BAN * getHeSoLuong() * getSoNgayCong() / NGAY_CONG_CHUAN + phuCap;
    }

    @Override
    public void hienThiThongTin() {
        super.hienThiThongTin();
        System.out.println("  Chuc vu: Quan ly | Phu cap: " + HoaDon.dinhDangTien(phuCap)
                + " | Thuc linh: " + HoaDon.dinhDangTien(tinhLuong()));
    }

    /**
     * Phân công ca làm cho nhân viên, kiểm tra nếu nhân viên đã có ca làm trùng giờ trong ngày.
     *
     * @param nv Nhân viên được xếp ca
     * @param ca Ca làm việc
     * @return true nếu xếp ca thành công, false nếu bị trùng
     */
    public boolean phanCongCa(NhanVien nv, CaLam ca) {
        if (nv == null || ca == null) {
            System.out.println("Loi: Thong tin nhan vien hoac ca lam khong hop le!");
            return false;
        }
        boolean thanhCong = nv.themCaLam(ca);
        if (thanhCong) {
            System.out.println("-> Phan cong thanh cong: Ca " + ca.getMaCa()
                    + " (" + ca.getNgayLam() + " " + ca.getGioBatDau() + "-" + ca.getGioKetThuc() + ")"
                    + " cho nhan vien " + nv.getHoTen() + " (" + nv.getMaNhanVien() + ")");
            return true;
        } else {
            System.out.println("-> Phan cong THAT BAI: Nhan vien " + nv.getHoTen()
                    + " da co ca lam bi trung thoi gian vao ngay " + ca.getNgayLam() + "!");
            return false;
        }
    }

    public QuanLyKhachHang quanLyKhachHang() {
        return qlKhachHang;
    }

    public QuanLySanPham quanLySanPham() {
        return qlSanPham;
    }

    public QuanLyDonHang quanLyDonHang() {
        return qlDonHang;
    }

    public QuanLyNhapHang quanLyNhapHang() {
        return qlNhapHang;
    }
}
