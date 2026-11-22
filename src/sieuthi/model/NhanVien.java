package sieuthi.model;

/**
 * Lop truu tuong dai dien cho Nhan vien sieu thi.
 * Ke thua tu Person theo so do Class_Structure.drawio.
 * Su dung mang doi tuong (CaLam[]) tu co gian theo yeu cau do an OOP (khong dung Collection).
 */
public abstract class NhanVien extends Person {
    public static final double LUONG_CO_BAN = 5_000_000;
    private static final int SUC_CHUA_MAC_DINH = 10;

    private String maNhanVien;
    private CaLam[] dsCaLam;
    private int soLuongCaLam;

    public NhanVien() {
        super();
        this.maNhanVien = "";
        this.dsCaLam = new CaLam[SUC_CHUA_MAC_DINH];
        this.soLuongCaLam = 0;
    }

    public NhanVien(String maNhanVien, String hoTen, String diaChi, String soDienThoai) {
        super(hoTen, diaChi, soDienThoai);
        this.maNhanVien = (maNhanVien != null) ? maNhanVien.trim() : "";
        this.dsCaLam = new CaLam[SUC_CHUA_MAC_DINH];
        this.soLuongCaLam = 0;
    }

    public String getMaNhanVien() { return maNhanVien; }
    public void setMaNhanVien(String maNhanVien) { this.maNhanVien = maNhanVien; }

    public CaLam[] getDsCaLam() {
        CaLam[] ketQua = new CaLam[soLuongCaLam];
        System.arraycopy(dsCaLam, 0, ketQua, 0, soLuongCaLam);
        return ketQua;
    }

    public int getSoLuongCaLam() {
        return soLuongCaLam;
    }

    public boolean coCaLam(CaLam ca) {
        if (ca == null) return false;
        for (int i = 0; i < soLuongCaLam; i++) {
            if (dsCaLam[i].equals(ca)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Them ca lam cho nhan vien, kiem tra trung ca truoc khi them.
     * Tu dong mo rong mang khi day.
     *
     * @param ca Ca lam can them
     * @return true neu them thanh cong, false neu bi trung ca lam
     */
    public boolean themCaLam(CaLam ca) {
        if (ca == null) return false;
        for (int i = 0; i < soLuongCaLam; i++) {
            if (dsCaLam[i].kiemTraTrungCa(ca)) {
                return false;
            }
        }
        if (soLuongCaLam >= dsCaLam.length) {
            CaLam[] moi = new CaLam[dsCaLam.length * 2];
            System.arraycopy(dsCaLam, 0, moi, 0, soLuongCaLam);
            dsCaLam = moi;
        }
        dsCaLam[soLuongCaLam++] = ca;
        return true;
    }

    public boolean xoaCaLam(CaLam ca) {
        if (ca == null) return false;
        for (int i = 0; i < soLuongCaLam; i++) {
            if (dsCaLam[i].equals(ca)) {
                for (int j = i; j < soLuongCaLam - 1; j++) {
                    dsCaLam[j] = dsCaLam[j + 1];
                }
                dsCaLam[--soLuongCaLam] = null;
                return true;
            }
        }
        return false;
    }

    public abstract double tinhLuong();

    @Override
    public void hienThiThongTin() {
        System.out.println("Nhan vien: " + maNhanVien + " | " + getHoTen() + " | " + getSoDienThoai());
    }
}
