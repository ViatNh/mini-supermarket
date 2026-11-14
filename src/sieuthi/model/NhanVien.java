package sieuthi.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Lớp trừu tượng đại diện cho Nhân viên siêu thị.
 * Kế thừa từ Person theo sơ đồ Class_Structure.drawio.
 */
public abstract class NhanVien extends Person {
    public static final double LUONG_CO_BAN = 5_000_000;

    private String maNhanVien;
    private final List<CaLam> dsCaLam = new ArrayList<>();

    public NhanVien() {
        super();
        this.maNhanVien = "";
    }

    public NhanVien(String maNhanVien, String hoTen, String diaChi, String soDienThoai) {
        super(hoTen, diaChi, soDienThoai);
        this.maNhanVien = (maNhanVien != null) ? maNhanVien.trim() : "";
    }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public List<CaLam> getDsCaLam() {
        return Collections.unmodifiableList(dsCaLam);
    }

    /**
     * Thêm ca làm cho nhân viên, kiểm tra trùng ca trước khi thêm.
     *
     * @param ca Ca làm cần thêm
     * @return true nếu thêm thành công, false nếu bị trùng ca làm
     */
    public boolean themCaLam(CaLam ca) {
        if (ca == null) return false;
        for (CaLam c : dsCaLam) {
            if (c.kiemTraTrungCa(ca)) {
                return false;
            }
        }
        dsCaLam.add(ca);
        return true;
    }

    public abstract double tinhLuong();

    @Override
    public void hienThiThongTin() {
        System.out.println("Nhan vien: " + maNhanVien + " | " + getHoTen() + " | " + getSoDienThoai());
    }
}
