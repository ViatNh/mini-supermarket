package sieuthi.manage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sieuthi.model.HoaDon;

/**
 * Lớp quản lý danh sách Đơn hàng (Hóa đơn) trong siêu thị mini.
 * Thực thi interface IQuanLy<HoaDon> theo sơ đồ Class_Structure.drawio của thành viên Vinh.
 */
public class QuanLyDonHang implements IQuanLy<HoaDon> {
    private final List<HoaDon> dsDonHang;

    public QuanLyDonHang() {
        this.dsDonHang = new ArrayList<>();
    }

    public QuanLyDonHang(List<HoaDon> dsDonHang) {
        this.dsDonHang = (dsDonHang != null) ? new ArrayList<>(dsDonHang) : new ArrayList<>();
    }

    public List<HoaDon> getDsDonHang() {
        return Collections.unmodifiableList(dsDonHang);
    }

    public int getSoLuong() {
        return dsDonHang.size();
    }

    @Override
    public boolean them(HoaDon hd) {
        if (hd == null || hd.getMaHoaDon() == null || hd.getMaHoaDon().trim().isEmpty()) {
            return false;
        }
        // Kiểm tra trùng mã hóa đơn
        if (timKiemTheoMa(hd.getMaHoaDon()) != null) {
            return false;
        }
        return dsDonHang.add(hd);
    }

    @Override
    public boolean sua(String maHD, HoaDon hd) {
        if (maHD == null || hd == null) {
            return false;
        }
        for (int i = 0; i < dsDonHang.size(); i++) {
            HoaDon current = dsDonHang.get(i);
            if (current.getMaHoaDon().equalsIgnoreCase(maHD.trim())) {
                dsDonHang.set(i, hd);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoa(String maHD) {
        if (maHD == null) {
            return false;
        }
        HoaDon hd = timKiemTheoMa(maHD);
        if (hd != null) {
            return dsDonHang.remove(hd);
        }
        return false;
    }

    @Override
    public HoaDon timKiemTheoMa(String maHD) {
        if (maHD == null) {
            return null;
        }
        for (HoaDon hd : dsDonHang) {
            if (hd.getMaHoaDon() != null && hd.getMaHoaDon().equalsIgnoreCase(maHD.trim())) {
                return hd;
            }
        }
        return null;
    }

    /**
     * Thống kê tổng doanh thu thực tế từ ngày đến ngày (bao gồm cả 2 ngày).
     * Chỉ tính những hóa đơn ĐÃ THANH TOÁN thành công (theo số tiền thực tế khách đã trả: tinhTienPhaiTra()).
     *
     * @param tu  Ngày bắt đầu
     * @param den Ngày kết thúc
     * @return Tổng doanh thu (VNĐ)
     */
    public double thongKeDoanhThu(LocalDate tu, LocalDate den) {
        if (tu == null || den == null) {
            return 0;
        }
        LocalDate start = tu;
        LocalDate end = den;
        if (start.isAfter(end)) {
            start = den;
            end = tu;
        }

        double tongDoanhThu = 0;
        for (HoaDon hd : dsDonHang) {
            LocalDate ngay = hd.getNgayLap();
            if (ngay != null && !ngay.isBefore(start) && !ngay.isAfter(end)) {
                if (hd.isDaThanhToan()) {
                    tongDoanhThu += hd.tinhTienPhaiTra();
                }
            }
        }
        return tongDoanhThu;
    }

    /**
     * Lọc danh sách hóa đơn theo ngày lập.
     *
     * @param ngay Ngày cần lọc
     * @return Danh sách các hóa đơn lập trong ngày đó
     */
    public List<HoaDon> locDonHangTheoNgay(LocalDate ngay) {
        List<HoaDon> ketQua = new ArrayList<>();
        if (ngay == null) {
            return ketQua;
        }
        for (HoaDon hd : dsDonHang) {
            if (ngay.equals(hd.getNgayLap())) {
                ketQua.add(hd);
            }
        }
        return ketQua;
    }

    /**
     * Lọc danh sách hóa đơn theo mã khách hàng.
     *
     * @param maKH Mã khách hàng
     * @return Danh sách hóa đơn của khách hàng đó
     */
    public List<HoaDon> locDonHangTheoKhachHang(String maKH) {
        List<HoaDon> ketQua = new ArrayList<>();
        if (maKH == null || maKH.trim().isEmpty()) {
            return ketQua;
        }
        String target = maKH.trim();
        for (HoaDon hd : dsDonHang) {
            if (hd.getKhachHang() != null && target.equalsIgnoreCase(hd.getKhachHang().getMaKhachHang())) {
                ketQua.add(hd);
            }
        }
        return ketQua;
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("================================================ DANH SÁCH ĐƠN HÀNG ================================================");
        if (dsDonHang.isEmpty()) {
            System.out.println("  (Danh sách đơn hàng đang trống)");
            System.out.println("====================================================================================================================");
            return;
        }
        System.out.printf("%-5s | %-10s | %-12s | %-20s | %-20s | %-14s | %-14s%n",
                "STT", "Mã HĐ", "Ngày lập", "Người lập", "Khách hàng", "Thành tiền", "Trạng thái");
        System.out.println("--------------------------------------------------------------------------------------------------------------------");
        int stt = 1;
        for (HoaDon hd : dsDonHang) {
            String tenKH = (hd.getKhachHang() != null) ? hd.getKhachHang().getHoTen() : "Khách vãng lai";
            String trangThai = hd.isDaThanhToan() ? "Đã thanh toán" : "Chưa thanh toán";
            System.out.printf("%-5d | %-10s | %-12s | %-20s | %-20s | %-14s | %-14s%n",
                    stt++,
                    hd.getMaHoaDon(),
                    hd.getNgayLap(),
                    hd.getNguoiLap().getHoTen(),
                    tenKH,
                    HoaDon.dinhDangTien(hd.tinhTienPhaiTra()),
                    trangThai);
        }
        System.out.println("====================================================================================================================");
    }
}
