package manage;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sieuthi.model.KhachHang;

/**
 * Lớp quản lý danh sách Khách hàng trong siêu thị mini.
 * Thực thi interface IQuanLy<KhachHang> theo sơ đồ Class_Structure.drawio.
 */
public class QuanLyKhachHang implements IQuanLy<KhachHang> {
    private final List<KhachHang> dsKhachHang;

    public QuanLyKhachHang() {
        this.dsKhachHang = new ArrayList<>();
    }

    public QuanLyKhachHang(List<KhachHang> dsKhachHang) {
        this.dsKhachHang = (dsKhachHang != null) ? new ArrayList<>(dsKhachHang) : new ArrayList<>();
    }

    public List<KhachHang> getDsKhachHang() {
        return Collections.unmodifiableList(dsKhachHang);
    }

    public int getSoLuong() {
        return dsKhachHang.size();
    }

    @Override
    public boolean them(KhachHang kh) {
        if (kh == null || kh.getMaKhachHang() == null || kh.getMaKhachHang().trim().isEmpty()) {
            return false;
        }
        // Kiểm tra trùng mã khách hàng
        if (timKiemTheoMa(kh.getMaKhachHang()) != null) {
            return false;
        }
        return dsKhachHang.add(kh);
    }

    @Override
    public boolean sua(String maKH, KhachHang kh) {
        if (maKH == null || kh == null) {
            return false;
        }
        for (int i = 0; i < dsKhachHang.size(); i++) {
            KhachHang current = dsKhachHang.get(i);
            if (current.getMaKhachHang().equalsIgnoreCase(maKH.trim())) {
                dsKhachHang.set(i, kh);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean xoa(String maKH) {
        if (maKH == null) {
            return false;
        }
        KhachHang kh = timKiemTheoMa(maKH);
        if (kh != null) {
            return dsKhachHang.remove(kh);
        }
        return false;
    }

    @Override
    public KhachHang timKiemTheoMa(String maKH) {
        if (maKH == null) {
            return null;
        }
        for (KhachHang kh : dsKhachHang) {
            if (kh.getMaKhachHang() != null && kh.getMaKhachHang().equalsIgnoreCase(maKH.trim())) {
                return kh;
            }
        }
        return null;
    }

    /**
     * Tìm kiếm khách hàng theo số điện thoại.
     *
     * @param sdt Số điện thoại cần tìm
     * @return KhachHang nếu tìm thấy, ngược lại null
     */
    public KhachHang timKiemTheoSDT(String sdt) {
        if (sdt == null || sdt.trim().isEmpty()) {
            return null;
        }
        String target = sdt.trim().replaceAll("\\s+", "");
        for (KhachHang kh : dsKhachHang) {
            if (kh.getSoDienThoai() != null) {
                String sdtKH = kh.getSoDienThoai().trim().replaceAll("\\s+", "");
                if (sdtKH.equals(target)) {
                    return kh;
                }
            }
        }
        return null;
    }

    @Override
    public void hienThiDanhSach() {
        System.out.println("======================================== DANH SÁCH KHÁCH HÀNG ========================================");
        if (dsKhachHang.isEmpty()) {
            System.out.println("  (Danh sách khách hàng đang trống)");
            System.out.println("======================================================================================================");
            return;
        }
        System.out.printf("%-5s | %-12s | %-25s | %-12s | %-25s | %-8s%n",
                "STT", "Mã KH", "Họ và tên", "Số ĐT", "Địa chỉ", "Điểm");
        System.out.println("------------------------------------------------------------------------------------------------------");
        int stt = 1;
        for (KhachHang kh : dsKhachHang) {
            System.out.printf("%-5d | %-12s | %-25s | %-12s | %-25s | %-8d%n",
                    stt++,
                    kh.getMaKhachHang(),
                    kh.getHoTen(),
                    kh.getSoDienThoai(),
                    kh.getDiaChi(),
                    kh.getDiemTichLuy());
        }
        System.out.println("======================================================================================================");
    }
}
