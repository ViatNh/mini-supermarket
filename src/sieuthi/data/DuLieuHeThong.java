package sieuthi.data;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;

import sieuthi.manage.QuanLy;
import sieuthi.model.*;

/**
 * Lop chiu trach nhiem luu tru va truy xuat toan bo du lieu cua sieu thi tu cac file text (.txt).
 * Su dung hoan toan mang doi tuong dong (array []) theo dung quy dinh do an OOP,
 * KHONG su dung ArrayList, Vector, LinkedList hay bat ky Collection Framework nao.
 */
public class DuLieuHeThong {
    private static final String THU_MUC_DATA = "data";
    private static final String FILE_KHACH_HANG = "data/khachhang.txt";
    private static final String FILE_NHAN_VIEN = "data/nhanvien.txt";
    private static final String FILE_CA_LAM = "data/calam.txt";
    private static final String FILE_NHA_CUNG_CAP = "data/nhacungcap.txt";
    private static final String FILE_SAN_PHAM = "data/sanpham.txt";
    private static final String FILE_KHUYEN_MAI = "data/khuyenmai.txt";
    private static final String FILE_NHAP_HANG = "data/nhaphang.txt";
    private static final String FILE_HOA_DON = "data/hoadon.txt";
    private static final String FILE_PHIEU_BAO_HANH = "data/phieubaohanh.txt";

    private final QuanLy quanLy;
    private NhanVien[] dsNhanVien = new NhanVien[10];
    private int soNhanVien = 0;

    private CaLam[] dsCaLam = new CaLam[10];
    private int soCaLam = 0;

    private NhaCungCap[] dsNhaCungCap = new NhaCungCap[10];
    private int soNhaCungCap = 0;

    private KhuyenMai[] dsKhuyenMai = new KhuyenMai[10];
    private int soKhuyenMai = 0;

    private PhieuBaoHanh[] dsPhieuBaoHanh = new PhieuBaoHanh[10];
    private int soPhieuBaoHanh = 0;

    private final KhoHang khoHang = new KhoHang("KHO01", "Kho Tong Sieu Thi Mini");

    private long thoiGianSuaFileKhachHang = 0;
    private long thoiGianSuaFileNhanVien = 0;
    private long thoiGianSuaFileCaLam = 0;
    private long thoiGianSuaFileNhaCungCap = 0;
    private long thoiGianSuaFileSanPham = 0;
    private long thoiGianSuaFileKhuyenMai = 0;
    private long thoiGianSuaFileNhapHang = 0;
    private long thoiGianSuaFileHoaDon = 0;
    private long thoiGianSuaFilePhieuBaoHanh = 0;

    public DuLieuHeThong(QuanLy quanLy) {
        this.quanLy = (quanLy != null) ? quanLy : new QuanLy("QL01", "Pham Van Quan Ly", "Ha Noi", "0988888888", 26, 2.0, 3_000_000);
    }

    public QuanLy getQuanLy() {
        return quanLy;
    }

    public NhanVien[] getDsNhanVien() {
        NhanVien[] res = new NhanVien[soNhanVien];
        System.arraycopy(dsNhanVien, 0, res, 0, soNhanVien);
        return res;
    }

    public CaLam[] getDsCaLam() {
        CaLam[] res = new CaLam[soCaLam];
        System.arraycopy(dsCaLam, 0, res, 0, soCaLam);
        return res;
    }

    public NhaCungCap[] getDsNhaCungCap() {
        NhaCungCap[] res = new NhaCungCap[soNhaCungCap];
        System.arraycopy(dsNhaCungCap, 0, res, 0, soNhaCungCap);
        return res;
    }

    public KhuyenMai[] getDsKhuyenMai() {
        KhuyenMai[] res = new KhuyenMai[soKhuyenMai];
        System.arraycopy(dsKhuyenMai, 0, res, 0, soKhuyenMai);
        return res;
    }

    public PhieuBaoHanh[] getDsPhieuBaoHanh() {
        PhieuBaoHanh[] res = new PhieuBaoHanh[soPhieuBaoHanh];
        System.arraycopy(dsPhieuBaoHanh, 0, res, 0, soPhieuBaoHanh);
        return res;
    }

    public KhoHang getKhoHang() {
        return khoHang;
    }

    public boolean themNhanVien(NhanVien nv) {
        if (nv == null || timNhanVien(nv.getMaNhanVien()) != null) return false;
        if (soNhanVien >= dsNhanVien.length) {
            NhanVien[] moi = new NhanVien[dsNhanVien.length * 2];
            System.arraycopy(dsNhanVien, 0, moi, 0, soNhanVien);
            dsNhanVien = moi;
        }
        dsNhanVien[soNhanVien++] = nv;
        return true;
    }

    public boolean themCaLam(CaLam ca) {
        if (ca == null || timCaLam(ca.getMaCa()) != null) return false;
        if (soCaLam >= dsCaLam.length) {
            CaLam[] moi = new CaLam[dsCaLam.length * 2];
            System.arraycopy(dsCaLam, 0, moi, 0, soCaLam);
            dsCaLam = moi;
        }
        dsCaLam[soCaLam++] = ca;
        return true;
    }

    public boolean xoaCaLam(String ma) {
        if (ma == null) return false;
        CaLam ca = timCaLam(ma);
        if (ca == null) return false;
        for (int i = 0; i < soNhanVien; i++) {
            dsNhanVien[i].xoaCaLam(ca);
        }
        if (quanLy != null) {
            quanLy.xoaCaLam(ca);
        }
        for (int i = 0; i < soCaLam; i++) {
            if (dsCaLam[i].getMaCa().equalsIgnoreCase(ma.trim())) {
                for (int j = i; j < soCaLam - 1; j++) {
                    dsCaLam[j] = dsCaLam[j + 1];
                }
                dsCaLam[--soCaLam] = null;
                return true;
            }
        }
        return false;
    }

    public boolean themNhaCungCap(NhaCungCap ncc) {
        if (ncc == null || timNhaCungCap(ncc.getMaNhaCungCap()) != null) return false;
        if (soNhaCungCap >= dsNhaCungCap.length) {
            NhaCungCap[] moi = new NhaCungCap[dsNhaCungCap.length * 2];
            System.arraycopy(dsNhaCungCap, 0, moi, 0, soNhaCungCap);
            dsNhaCungCap = moi;
        }
        dsNhaCungCap[soNhaCungCap++] = ncc;
        return true;
    }

    public boolean themKhuyenMai(KhuyenMai km) {
        if (km == null || timKhuyenMai(km.getMaKhuyenMai()) != null) return false;
        if (soKhuyenMai >= dsKhuyenMai.length) {
            KhuyenMai[] moi = new KhuyenMai[dsKhuyenMai.length * 2];
            System.arraycopy(dsKhuyenMai, 0, moi, 0, soKhuyenMai);
            dsKhuyenMai = moi;
        }
        dsKhuyenMai[soKhuyenMai++] = km;
        return true;
    }

    public boolean themPhieuBaoHanh(PhieuBaoHanh pbh) {
        if (pbh == null || timPhieuBaoHanh(pbh.getMaPhieuBH()) != null) return false;
        if (soPhieuBaoHanh >= dsPhieuBaoHanh.length) {
            PhieuBaoHanh[] moi = new PhieuBaoHanh[dsPhieuBaoHanh.length * 2];
            System.arraycopy(dsPhieuBaoHanh, 0, moi, 0, soPhieuBaoHanh);
            dsPhieuBaoHanh = moi;
        }
        dsPhieuBaoHanh[soPhieuBaoHanh++] = pbh;
        return true;
    }

    public NhanVien timNhanVien(String ma) {
        if (ma == null) return null;
        if (quanLy != null && ma.equalsIgnoreCase(quanLy.getMaNhanVien())) return quanLy;
        for (int i = 0; i < soNhanVien; i++) {
            if (dsNhanVien[i].getMaNhanVien().equalsIgnoreCase(ma.trim())) return dsNhanVien[i];
        }
        return null;
    }

    public CaLam timCaLam(String ma) {
        if (ma == null) return null;
        for (int i = 0; i < soCaLam; i++) {
            if (dsCaLam[i].getMaCa().equalsIgnoreCase(ma.trim())) return dsCaLam[i];
        }
        return null;
    }

    public NhaCungCap timNhaCungCap(String ma) {
        if (ma == null) return null;
        for (int i = 0; i < soNhaCungCap; i++) {
            if (dsNhaCungCap[i].getMaNhaCungCap().equalsIgnoreCase(ma.trim())) return dsNhaCungCap[i];
        }
        return null;
    }

    public KhuyenMai timKhuyenMai(String ma) {
        if (ma == null) return null;
        for (int i = 0; i < soKhuyenMai; i++) {
            if (dsKhuyenMai[i].getMaKhuyenMai().equalsIgnoreCase(ma.trim())) return dsKhuyenMai[i];
        }
        return null;
    }

    public PhieuBaoHanh timPhieuBaoHanh(String ma) {
        if (ma == null) return null;
        for (int i = 0; i < soPhieuBaoHanh; i++) {
            if (dsPhieuBaoHanh[i].getMaPhieuBH().equalsIgnoreCase(ma.trim())) return dsPhieuBaoHanh[i];
        }
        return null;
    }

    public void xoaTatCaNhanVien() {
        soNhanVien = 0;
        dsNhanVien = new NhanVien[10];
    }

    public void xoaTatCaCaLam() {
        soCaLam = 0;
        dsCaLam = new CaLam[10];
        if (quanLy != null) {
            quanLy.xoaTatCaCaLam();
        }
        for (int i = 0; i < soNhanVien; i++) {
            if (dsNhanVien[i] != null) {
                dsNhanVien[i].xoaTatCaCaLam();
            }
        }
    }

    public void xoaTatCaNhaCungCap() {
        soNhaCungCap = 0;
        dsNhaCungCap = new NhaCungCap[10];
    }

    public void xoaTatCaKhuyenMai() {
        soKhuyenMai = 0;
        dsKhuyenMai = new KhuyenMai[10];
    }

    public void xoaTatCaPhieuBaoHanh() {
        soPhieuBaoHanh = 0;
        dsPhieuBaoHanh = new PhieuBaoHanh[10];
    }

    /**
     * Khoi tao thu muc data va cac file mau neu chua ton tai.
     */
    public void khoiTaoDuLieuMacDinhNeuChuaCo() {
        File dir = new File(THU_MUC_DATA);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        khoiTaoFileNeuChuaCo(FILE_KHACH_HANG, new String[]{
                "KH01|Nguyen Van An|123 Le Loi, Q.1, TP.HCM|0901234567|150",
                "KH02|Tran Thi Binh|456 Hai Ba Trung, Ha Noi|0912345678|320",
                "KH03|Le Hoang Cuong|789 Nguyen Hue, Da Nang|0987654321|50",
                "KH04|Pham Thi Dung|12 Tran Hung Dao, Can Tho|0934567890|0",
                "KH05|Vo Minh Em|34 Pasteur, TP.HCM|0978123456|85"
        });

        khoiTaoFileNeuChuaCo(FILE_NHAN_VIEN, new String[]{
                "QUAN_LY|QL01|Pham Van Quan Ly|101 Cau Giay, Ha Noi|0988888888|26|2.0|3000000.0",
                "THU_NGAN|TN01|Tran Thi Thu Ngan|202 Nam Ky Khoi Nghia, TP.HCM|0923456789|26|1.5|Quay 01",
                "THU_NGAN|TN02|Nguyen Thu Ha|15 Ly Thuong Kiet, TP.HCM|0934111222|24|1.4|Quay 02",
                "FULL_TIME|FT01|Le Van Kiem Kho|88 Cach Mang Thang 8, TP.HCM|0945666777|26|1.6|0.0",
                "PART_TIME|PT01|Do Sinh Vien|50 Vo Van Ngan, TP.Thu Duc|0933333333|45|30000.0|0.0"
        });

        khoiTaoFileNeuChuaCo(FILE_CA_LAM, new String[]{
                "CA01|2026-11-10|07:30|12:00|TN01",
                "CA02|2026-11-10|12:30|17:30|TN02",
                "CA03|2026-11-10|17:30|22:00|PT01",
                "CA04|2026-11-11|07:30|16:30|FT01",
                "CA05|2026-11-11|08:00|17:00|QL01"
        });

        khoiTaoFileNeuChuaCo(FILE_NHA_CUNG_CAP, new String[]{
                "NCC01|Cong ty CP Acecook Viet Nam|02838154064|KCN Tan Binh, P. Tay Thanh, Q. Tan Phu, TP.HCM",
                "NCC02|Cong ty TNHH Suntory PepsiCo Viet Nam|02838219468|88 Dong Khoi, Q.1, TP.HCM",
                "NCC03|Tap doan Sunhouse Group|02437366676|139 Nguyen Thai Hoc, Ba Dinh, Ha Noi",
                "NCC04|Cong ty CP Sua Viet Nam (Vinamilk)|02854155555|10 Tan Trao, P. Tan Phu, Q.7, TP.HCM",
                "NCC05|Cong ty TNHH Unilever Viet Nam|02854135686|A2-3 KCN Tay Bac Cu Chi, TP.HCM"
        });

        khoiTaoFileNeuChuaCo(FILE_SAN_PHAM, new String[]{
                "THUC_PHAM|SP01|Mi Hao Hao Tom Chua Cay|Viet Nam|2026|Acecook|150|5000.0|2027-05-15|",
                "THUC_PHAM|SP02|Sua tuoi tiet trung Vinamilk 1L|Viet Nam|2026|Vinamilk|80|32000.0|2027-02-28|",
                "DO_UONG|SP03|Nuoc tinh khiet Aquafina 500ml|Viet Nam|2026|PepsiCo|200|10000.0|500|2027-11-10",
                "DO_UONG|SP04|Tra xanh Khong Do 455ml|Viet Nam|2026|Tan Hiep Phat|120|12000.0|455|2027-08-20",
                "DO_DUNG|SP05|Am sieu toc Inox Sunhouse 1.8L|Viet Nam|2025|Sunhouse|45|220000.0|Inox 304|12",
                "DO_DUNG|SP06|Chao chong dinh Sunhouse 26cm|Viet Nam|2026|Sunhouse|30|180000.0|Nhom phu chong dinh|6"
        });

        khoiTaoFileNeuChuaCo(FILE_KHUYEN_MAI, new String[]{
                "KM01|0.10|2026-11-01|2026-11-30",
                "KM02|0.15|2026-11-10|2026-11-15",
                "KM03|0.05|2026-10-01|2026-12-31",
                "KM04|0.20|2026-11-20|2026-11-25",
                "KM_HETHAN|0.25|2026-01-01|2026-02-01"
        });

        khoiTaoFileNeuChuaCo(FILE_NHAP_HANG, new String[]{
                "NH001|2026-11-01|NCC01|QL01|SP01:110:3500.0",
                "NH002|2026-11-02|NCC02|QL01|SP03:207:6000.0;SP04:123:8000.0",
                "NH003|2026-11-03|NCC03|FT01|SP05:46:150000.0;SP06:31:120000.0",
                "NH004|2026-11-05|NCC04|QL01|SP02:82:24000.0",
                "NH005|2026-11-08|NCC01|FT01|SP01:51:3600.0"
        });

        khoiTaoFileNeuChuaCo(FILE_HOA_DON, new String[]{
                "HD001|2026-11-05|TN01|KH01|KM01|true|TienMat|TN01|SP01:5:5000.0;SP03:2:10000.0",
                "HD002|2026-11-06|TN01|KH02|KM02|true|ChuyenKhoan:CK99812|TN01|SP05:1:220000.0;SP02:2:32000.0",
                "HD003|2026-11-07|TN02|KH03|KM03|true|TheNganHang:9704123456789012|TN02|SP04:3:12000.0;SP01:4:5000.0",
                "HD004|2026-11-08|TN01|KH04||true|TienMat|TN01|SP03:5:10000.0",
                "HD005|2026-11-09|TN02|KH05|KM01|true|ChuyenKhoan:CK88421|TN02|SP06:1:180000.0;SP01:2:5000.0"
        });

        khoiTaoFileNeuChuaCo(FILE_PHIEU_BAO_HANH, new String[]{
                "PBH001|HD002|SP05|KH02|2026-11-06|2027-11-06|Am khong vao dien|Dang xu ly",
                "PBH002|HD005|SP06|KH05|2026-11-09|2027-05-09|Troc lop chong dinh|Da tiep nhan",
                "PBH003|HD002|SP05|KH02|2026-11-06|2027-11-06|Bao hanh nut bam|Da hoan thanh",
                "PBH004|HD005|SP06|KH05|2026-11-09|2027-05-09|Kiem tra tay cam long|Da tiep nhan",
                "PBH005|HD002|SP05|KH02|2026-11-06|2027-11-06|Bao duong dinh ky|Da hoan thanh"
        });
    }

    private void khoiTaoFileNeuChuaCo(String duongDan, String[] dongMacDinh) {
        File file = new File(duongDan);
        if (!file.exists() || file.length() == 0) {
            ghiTatCaDong(duongDan, dongMacDinh);
        }
    }

    // =========================================================================
    // ĐỌC DỮ LIỆU TỪ FILE VÀO HỆ THỐNG
    // =========================================================================
    public void docTatCaDuLieu() {
        khoiTaoDuLieuMacDinhNeuChuaCo();

        docKhachHang();
        docNhanVien();
        docCaLam();
        docNhaCungCap();
        docSanPham();
        docKhuyenMai();
        docNhapHang();
        docHoaDon();
        docPhieuBaoHanh();
    }

    public synchronized void docKhachHang() {
        File f = new File(FILE_KHACH_HANG);
        thoiGianSuaFileKhachHang = f.lastModified();
        quanLy.quanLyKhachHang().xoaTatCa();
        String[] lines = docTatCaDong(FILE_KHACH_HANG);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 4) {
                String ma = p[0].trim();
                String ten = p[1].trim();
                String diaChi = p[2].trim();
                String sdt = p[3].trim();
                KhachHang kh = new KhachHang(ma, ten, diaChi, sdt);
                if (p.length >= 5 && !p[4].trim().isEmpty()) {
                    try {
                        kh.setDiemTichLuy((int) Double.parseDouble(p[4].trim()));
                    } catch (NumberFormatException ignored) {}
                }
                quanLy.quanLyKhachHang().them(kh);
            }
        }
    }

    public synchronized void docNhanVien() {
        File f = new File(FILE_NHAN_VIEN);
        thoiGianSuaFileNhanVien = f.lastModified();
        xoaTatCaNhanVien();
        String[] lines = docTatCaDong(FILE_NHAN_VIEN);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 5) {
                String loai = p[0].trim().toUpperCase();
                String ma = p[1].trim();
                String ten = p[2].trim();
                String diaChi = p[3].trim();
                String sdt = p[4].trim();

                switch (loai) {
                    case "QUAN_LY" -> {
                        int ngayCong = (p.length > 5) ? Integer.parseInt(p[5].trim()) : 26;
                        double heSo = (p.length > 6) ? Double.parseDouble(p[6].trim()) : 2.0;
                        double phuCap = (p.length > 7) ? Double.parseDouble(p[7].trim()) : 0;
                        if (quanLy.getMaNhanVien().equalsIgnoreCase(ma)) {
                            quanLy.setHoTen(ten);
                            quanLy.setDiaChi(diaChi);
                            quanLy.setSoDienThoai(sdt);
                            quanLy.setSoNgayCong(ngayCong);
                            quanLy.setHeSoLuong(heSo);
                            quanLy.setPhuCap(phuCap);
                        } else {
                            QuanLy ql = new QuanLy(ma, ten, diaChi, sdt, ngayCong, heSo, phuCap);
                            themNhanVien(ql);
                        }
                    }
                    case "THU_NGAN" -> {
                        int ngayCong = (p.length > 5) ? Integer.parseInt(p[5].trim()) : 26;
                        double heSo = (p.length > 6) ? Double.parseDouble(p[6].trim()) : 1.2;
                        String maQuay = (p.length > 7) ? p[7].trim() : "Quay 01";
                        ThuNgan tn = new ThuNgan(ma, ten, diaChi, sdt, ngayCong, heSo, maQuay);
                        themNhanVien(tn);
                    }
                    case "FULL_TIME" -> {
                        int ngayCong = (p.length > 5) ? Integer.parseInt(p[5].trim()) : 26;
                        double heSo = (p.length > 6) ? Double.parseDouble(p[6].trim()) : 1.0;
                        NhanVienFullTime ft = new NhanVienFullTime(ma, ten, diaChi, sdt, ngayCong, heSo);
                        themNhanVien(ft);
                    }
                    case "PART_TIME" -> {
                        int soGio = (p.length > 5) ? Integer.parseInt(p[5].trim()) : 0;
                        double luongGio = (p.length > 6) ? Double.parseDouble(p[6].trim()) : 30000;
                        NhanVienPartTime pt = new NhanVienPartTime(ma, ten, diaChi, sdt, soGio, luongGio);
                        themNhanVien(pt);
                    }
                }
            }
        }
    }

    public synchronized void docCaLam() {
        File f = new File(FILE_CA_LAM);
        thoiGianSuaFileCaLam = f.lastModified();
        xoaTatCaCaLam();
        String[] lines = docTatCaDong(FILE_CA_LAM);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 4) {
                String maCa = p[0].trim();
                LocalDate ngay = LocalDate.parse(p[1].trim());
                LocalTime bd = LocalTime.parse(p[2].trim());
                LocalTime kt = LocalTime.parse(p[3].trim());
                CaLam ca = new CaLam(maCa, ngay, bd, kt);
                themCaLam(ca);

                if (p.length >= 5 && !p[4].trim().isEmpty()) {
                    String maNV = p[4].trim();
                    NhanVien nv = timNhanVien(maNV);
                    if (nv != null) {
                        nv.themCaLam(ca);
                    }
                }
            }
        }
    }

    public synchronized void docNhaCungCap() {
        File f = new File(FILE_NHA_CUNG_CAP);
        thoiGianSuaFileNhaCungCap = f.lastModified();
        xoaTatCaNhaCungCap();
        String[] lines = docTatCaDong(FILE_NHA_CUNG_CAP);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 4) {
                String ma = p[0].trim();
                String ten = p[1].trim();
                String sdt = p[2].trim();
                String diaChi = p[3].trim();
                themNhaCungCap(new NhaCungCap(ma, ten, sdt, diaChi));
            }
        }
    }

    public synchronized void docSanPham() {
        File f = new File(FILE_SAN_PHAM);
        thoiGianSuaFileSanPham = f.lastModified();
        quanLy.quanLySanPham().xoaTatCa();
        String[] lines = docTatCaDong(FILE_SAN_PHAM);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 8) {
                String loai = p[0].trim().toUpperCase();
                String ma = p[1].trim();
                String ten = p[2].trim();
                String nuoc = p[3].trim();
                int nam = Integer.parseInt(p[4].trim());
                String hang = p[5].trim();
                int tonKho = Integer.parseInt(p[6].trim());
                double gia = Double.parseDouble(p[7].trim());

                switch (loai) {
                    case "THUC_PHAM" -> {
                        LocalDate hsd = (p.length > 8 && !p[8].trim().isEmpty()) ? LocalDate.parse(p[8].trim()) : LocalDate.now().plusMonths(6);
                        quanLy.quanLySanPham().them(new ThucPham(ma, ten, nuoc, nam, hang, tonKho, gia, hsd));
                    }
                    case "DO_UONG" -> {
                        int ml = (p.length > 8 && !p[8].trim().isEmpty()) ? Integer.parseInt(p[8].trim()) : 500;
                        LocalDate hsd = (p.length > 9 && !p[9].trim().isEmpty()) ? LocalDate.parse(p[9].trim()) : LocalDate.now().plusMonths(12);
                        quanLy.quanLySanPham().them(new DoUong(ma, ten, nuoc, nam, hang, tonKho, gia, ml, hsd));
                    }
                    case "DO_DUNG" -> {
                        String chatLieu = (p.length > 8 && !p[8].trim().isEmpty()) ? p[8].trim() : "Inox";
                        int bh = (p.length > 9 && !p[9].trim().isEmpty()) ? Integer.parseInt(p[9].trim()) : 12;
                        quanLy.quanLySanPham().them(new DoDung(ma, ten, nuoc, nam, hang, tonKho, gia, chatLieu, bh));
                    }
                }
            }
        }
    }

    public synchronized void docKhuyenMai() {
        File f = new File(FILE_KHUYEN_MAI);
        thoiGianSuaFileKhuyenMai = f.lastModified();
        xoaTatCaKhuyenMai();
        String[] lines = docTatCaDong(FILE_KHUYEN_MAI);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 4) {
                String ma = p[0].trim();
                double tiLe = Double.parseDouble(p[1].trim());
                LocalDate bd = LocalDate.parse(p[2].trim());
                LocalDate kt = LocalDate.parse(p[3].trim());
                themKhuyenMai(new KhuyenMai(ma, tiLe, bd, kt));
            }
        }
    }

    public synchronized void docNhapHang() {
        File f = new File(FILE_NHAP_HANG);
        thoiGianSuaFileNhapHang = f.lastModified();
        quanLy.quanLyNhapHang().xoaTatCa();
        String[] lines = docTatCaDong(FILE_NHAP_HANG);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 5) {
                String maNH = p[0].trim();
                LocalDate ngay = LocalDate.parse(p[1].trim());
                String maNCC = p[2].trim();
                String maNguoiLap = p[3].trim();
                String dsChiTietStr = p[4].trim();

                NhaCungCap ncc = timNhaCungCap(maNCC);
                NhanVien nguoiLap = timNhanVien(maNguoiLap);
                if (nguoiLap == null) nguoiLap = quanLy;
                if (ncc == null) ncc = new NhaCungCap(maNCC, "Nha cung cap " + maNCC, "", "");

                NhapHang nh = new NhapHang(maNH, ngay, ncc, nguoiLap);
                if (!dsChiTietStr.isEmpty()) {
                    String[] items = dsChiTietStr.split(";");
                    for (String item : items) {
                        String[] ct = item.split(":");
                        if (ct.length >= 3) {
                            String maSP = ct[0].trim();
                            int sl = Integer.parseInt(ct[1].trim());
                            double donGia = Double.parseDouble(ct[2].trim());
                            SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(maSP);
                            if (sp != null) {
                                nh.themChiTietLuuTru(sp, sl, donGia);
                            }
                        }
                    }
                }
                quanLy.quanLyNhapHang().them(nh);
            }
        }
    }

    public synchronized void docHoaDon() {
        File f = new File(FILE_HOA_DON);
        thoiGianSuaFileHoaDon = f.lastModified();
        quanLy.quanLyDonHang().xoaTatCa();
        String[] lines = docTatCaDong(FILE_HOA_DON);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 9) {
                String maHD = p[0].trim();
                LocalDate ngayLap = LocalDate.parse(p[1].trim());
                String maNguoiLap = p[2].trim();
                String maKH = p[3].trim();
                String maKM = p[4].trim();
                boolean daTT = Boolean.parseBoolean(p[5].trim());
                String ptStr = p[6].trim();
                String maTN = p[7].trim();
                String dsChiTietStr = p[8].trim();

                NhanVien nguoiLap = timNhanVien(maNguoiLap);
                if (nguoiLap == null) nguoiLap = quanLy;

                KhachHang kh = quanLy.quanLyKhachHang().timKiemTheoMa(maKH);
                KhuyenMai km = timKhuyenMai(maKM);

                HoaDon hd = new HoaDon(maHD, ngayLap, nguoiLap, kh, km);
                if (!dsChiTietStr.isEmpty()) {
                    String[] items = dsChiTietStr.split(";");
                    for (String item : items) {
                        String[] ct = item.split(":");
                        if (ct.length >= 3) {
                            String maSP = ct[0].trim();
                            int sl = Integer.parseInt(ct[1].trim());
                            double donGia = Double.parseDouble(ct[2].trim());
                            SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(maSP);
                            if (sp != null) {
                                hd.themChiTietLuuTru(sp, sl, donGia);
                            }
                        }
                    }
                }

                NhanVien nvTN = timNhanVien(maTN);
                ThuNgan tn = (nvTN instanceof ThuNgan thuNgan) ? thuNgan : new ThuNgan(maTN, "Thu ngan " + maTN, "", "", 26, 1.0, "Q01");

                IPhuongThucThanhToan pt = new TienMat();
                if (ptStr.startsWith("ChuyenKhoan")) {
                    String[] parts = ptStr.split(":", 2);
                    String maGD = (parts.length > 1) ? parts[1] : "CK" + System.currentTimeMillis();
                    pt = new ChuyenKhoan(maGD);
                } else if (ptStr.startsWith("TheNganHang")) {
                    String[] parts = ptStr.split(":", 2);
                    String soThe = (parts.length > 1) ? parts[1] : "9704000000000000";
                    pt = new TheNganHang(soThe);
                }

                hd.thietLapTrangThaiThanhToan(tn, pt, daTT);
                quanLy.quanLyDonHang().them(hd);
            }
        }
    }

    public synchronized void docPhieuBaoHanh() {
        File f = new File(FILE_PHIEU_BAO_HANH);
        thoiGianSuaFilePhieuBaoHanh = f.lastModified();
        xoaTatCaPhieuBaoHanh();
        String[] lines = docTatCaDong(FILE_PHIEU_BAO_HANH);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 8) {
                String maPBH = p[0].trim();
                String maHD = p[1].trim();
                String maSP = p[2].trim();
                String maKH = p[3].trim();
                LocalDate ngayBD = LocalDate.parse(p[4].trim());
                LocalDate ngayKT = LocalDate.parse(p[5].trim());
                String moTa = p[6].trim();
                String trangThai = p[7].trim();

                HoaDon hd = quanLy.quanLyDonHang().timKiemTheoMa(maHD);
                SanPham sp = quanLy.quanLySanPham().timKiemTheoMa(maSP);
                KhachHang kh = quanLy.quanLyKhachHang().timKiemTheoMa(maKH);

                if (hd != null && sp != null) {
                    PhieuBaoHanh pbh = new PhieuBaoHanh(maPBH, hd, sp, kh, ngayBD, ngayKT, moTa, trangThai);
                    themPhieuBaoHanh(pbh);
                }
            }
        }
    }

    // =========================================================================
    // DONG BO THOI GIAN THUC CHO DA TIEN TRINH (MULTI-TERMINAL CONCURRENCY)
    // =========================================================================
    private boolean canDongBo(String duongDan, long thoiGianCu) {
        File f = new File(duongDan);
        if (!f.exists()) return false;
        return f.lastModified() != thoiGianCu;
    }

    /**
     * Kiem tra su thay doi tren disk cua ca 9 file txt. Neu co tien trinh / terminal khac
     * vua sua doi hoac xoa du lieu trong file, tien trinh hien tai se tu dong cap nhat lai
     * bo nho mang dong de dam bao tinh dong nhat du lieu thoi gian thuc.
     */
    public synchronized void dongBoTatCaTuFile() {
        boolean khChanged = canDongBo(FILE_KHACH_HANG, thoiGianSuaFileKhachHang);
        boolean nvChanged = canDongBo(FILE_NHAN_VIEN, thoiGianSuaFileNhanVien);
        boolean caChanged = canDongBo(FILE_CA_LAM, thoiGianSuaFileCaLam);
        boolean nccChanged = canDongBo(FILE_NHA_CUNG_CAP, thoiGianSuaFileNhaCungCap);
        boolean spChanged = canDongBo(FILE_SAN_PHAM, thoiGianSuaFileSanPham);
        boolean kmChanged = canDongBo(FILE_KHUYEN_MAI, thoiGianSuaFileKhuyenMai);
        boolean nhChanged = canDongBo(FILE_NHAP_HANG, thoiGianSuaFileNhapHang);
        boolean hdChanged = canDongBo(FILE_HOA_DON, thoiGianSuaFileHoaDon);
        boolean pbhChanged = canDongBo(FILE_PHIEU_BAO_HANH, thoiGianSuaFilePhieuBaoHanh);

        if (!khChanged && !nvChanged && !caChanged && !nccChanged && !spChanged && !kmChanged && !nhChanged && !hdChanged && !pbhChanged) {
            return;
        }

        // Neu co san pham hoac doi tac thay doi, reload ca don hang va phieu bao hanh de dong bo con tro
        if (spChanged || khChanged || nvChanged || kmChanged) {
            nhChanged = true;
            hdChanged = true;
            pbhChanged = true;
        }
        if (nvChanged) {
            caChanged = true;
        }

        if (khChanged) docKhachHang();
        if (nvChanged) docNhanVien();
        if (caChanged) docCaLam();
        if (nccChanged) docNhaCungCap();
        if (spChanged) docSanPham();
        if (kmChanged) docKhuyenMai();
        if (nhChanged) docNhapHang();
        if (hdChanged) docHoaDon();
        if (pbhChanged) docPhieuBaoHanh();
    }

    // =========================================================================
    // LƯU DỮ LIỆU TỪ HỆ THỐNG VÀO FILE
    // =========================================================================
    public void luuTatCaDuLieu() {
        luuKhachHang();
        luuNhanVien();
        luuCaLam();
        luuNhaCungCap();
        luuSanPham();
        luuKhuyenMai();
        luuNhapHang();
        luuHoaDon();
        luuPhieuBaoHanh();
    }

    public void luuKhachHang() {
        KhachHang[] ds = quanLy.quanLyKhachHang().getDsKhachHang();
        String[] lines = new String[ds.length];
        for (int i = 0; i < ds.length; i++) {
            KhachHang kh = ds[i];
            lines[i] = String.join("|",
                    kh.getMaKhachHang(),
                    kh.getHoTen(),
                    kh.getDiaChi(),
                    kh.getSoDienThoai(),
                    String.valueOf(kh.getDiemTichLuy()));
        }
        ghiTatCaDong(FILE_KHACH_HANG, lines);
        thoiGianSuaFileKhachHang = new File(FILE_KHACH_HANG).lastModified();
    }

    public synchronized void luuNhanVien() {
        String[] lines = new String[soNhanVien + 1];
        lines[0] = String.join("|",
                "QUAN_LY",
                quanLy.getMaNhanVien(),
                quanLy.getHoTen(),
                quanLy.getDiaChi(),
                quanLy.getSoDienThoai(),
                String.valueOf(quanLy.getSoNgayCong()),
                String.valueOf(quanLy.getHeSoLuong()),
                String.valueOf(quanLy.getPhuCap()));

        int count = 1;
        for (int i = 0; i < soNhanVien; i++) {
            NhanVien nv = dsNhanVien[i];
            if (nv.getMaNhanVien().equalsIgnoreCase(quanLy.getMaNhanVien())) continue;
            if (nv instanceof ThuNgan tn) {
                lines[count++] = String.join("|",
                        "THU_NGAN",
                        tn.getMaNhanVien(),
                        tn.getHoTen(),
                        tn.getDiaChi(),
                        tn.getSoDienThoai(),
                        String.valueOf(tn.getSoNgayCong()),
                        String.valueOf(tn.getHeSoLuong()),
                        tn.getMaQuay());
            } else if (nv instanceof NhanVienPartTime pt) {
                lines[count++] = String.join("|",
                        "PART_TIME",
                        pt.getMaNhanVien(),
                        pt.getHoTen(),
                        pt.getDiaChi(),
                        pt.getSoDienThoai(),
                        String.valueOf(pt.getSoGioLam()),
                        String.valueOf(pt.getLuongTheoGio()),
                        "0.0");
            } else if (nv instanceof NhanVienFullTime ft) {
                lines[count++] = String.join("|",
                        "FULL_TIME",
                        ft.getMaNhanVien(),
                        ft.getHoTen(),
                        ft.getDiaChi(),
                        ft.getSoDienThoai(),
                        String.valueOf(ft.getSoNgayCong()),
                        String.valueOf(ft.getHeSoLuong()),
                        "0.0");
            }
        }
        String[] actualLines = new String[count];
        System.arraycopy(lines, 0, actualLines, 0, count);
        ghiTatCaDong(FILE_NHAN_VIEN, actualLines);
        thoiGianSuaFileNhanVien = new File(FILE_NHAN_VIEN).lastModified();
    }

    public synchronized void luuCaLam() {
        String[] lines = new String[soCaLam];
        for (int i = 0; i < soCaLam; i++) {
            CaLam ca = dsCaLam[i];
            String maNV = "";
            for (int j = 0; j < soNhanVien; j++) {
                if (dsNhanVien[j].coCaLam(ca)) {
                    maNV = dsNhanVien[j].getMaNhanVien();
                    break;
                }
            }
            if (maNV.isEmpty() && quanLy != null && quanLy.coCaLam(ca)) {
                maNV = quanLy.getMaNhanVien();
            }
            lines[i] = String.join("|",
                    ca.getMaCa(),
                    ca.getNgayLam().toString(),
                    ca.getGioBatDau().toString(),
                    ca.getGioKetThuc().toString(),
                    maNV);
        }
        ghiTatCaDong(FILE_CA_LAM, lines);
        thoiGianSuaFileCaLam = new File(FILE_CA_LAM).lastModified();
    }

    public synchronized void luuNhaCungCap() {
        String[] lines = new String[soNhaCungCap];
        for (int i = 0; i < soNhaCungCap; i++) {
            NhaCungCap ncc = dsNhaCungCap[i];
            lines[i] = String.join("|",
                    ncc.getMaNhaCungCap(),
                    ncc.getTenNhaCungCap(),
                    ncc.getSoDienThoai(),
                    ncc.getDiaChi());
        }
        ghiTatCaDong(FILE_NHA_CUNG_CAP, lines);
        thoiGianSuaFileNhaCungCap = new File(FILE_NHA_CUNG_CAP).lastModified();
    }

    public synchronized void luuSanPham() {
        SanPham[] ds = quanLy.quanLySanPham().getDsSanPham();
        String[] lines = new String[ds.length];
        for (int i = 0; i < ds.length; i++) {
            SanPham sp = ds[i];
            if (sp instanceof ThucPham tp) {
                lines[i] = String.join("|",
                        "THUC_PHAM",
                        tp.getMaSanPham(),
                        tp.getTenSanPham(),
                        tp.getNuocSanXuat(),
                        String.valueOf(tp.getNamSanXuat()),
                        tp.getHangSanXuat(),
                        String.valueOf(tp.getSoLuongTonKho()),
                        String.valueOf(tp.getGiaGoc()),
                        (tp.getHanSuDung() != null ? tp.getHanSuDung().toString() : ""),
                        "");
            } else if (sp instanceof DoUong du) {
                lines[i] = String.join("|",
                        "DO_UONG",
                        du.getMaSanPham(),
                        du.getTenSanPham(),
                        du.getNuocSanXuat(),
                        String.valueOf(du.getNamSanXuat()),
                        du.getHangSanXuat(),
                        String.valueOf(du.getSoLuongTonKho()),
                        String.valueOf(du.getGiaGoc()),
                        String.valueOf(du.getDungTichMl()),
                        (du.getHanSuDung() != null ? du.getHanSuDung().toString() : ""));
            } else if (sp instanceof DoDung dd) {
                lines[i] = String.join("|",
                        "DO_DUNG",
                        dd.getMaSanPham(),
                        dd.getTenSanPham(),
                        dd.getNuocSanXuat(),
                        String.valueOf(dd.getNamSanXuat()),
                        dd.getHangSanXuat(),
                        String.valueOf(dd.getSoLuongTonKho()),
                        String.valueOf(dd.getGiaGoc()),
                        dd.getChatLieu(),
                        String.valueOf(dd.layThoiHanBaoHanh()));
            }
        }
        ghiTatCaDong(FILE_SAN_PHAM, lines);
        thoiGianSuaFileSanPham = new File(FILE_SAN_PHAM).lastModified();
    }

    public synchronized void luuKhuyenMai() {
        String[] lines = new String[soKhuyenMai];
        for (int i = 0; i < soKhuyenMai; i++) {
            KhuyenMai km = dsKhuyenMai[i];
            lines[i] = String.join("|",
                    km.getMaKhuyenMai(),
                    String.valueOf(km.getTiLeGiam()),
                    km.getNgayBatDau().toString(),
                    km.getNgayKetThuc().toString());
        }
        ghiTatCaDong(FILE_KHUYEN_MAI, lines);
        thoiGianSuaFileKhuyenMai = new File(FILE_KHUYEN_MAI).lastModified();
    }

    public synchronized void luuNhapHang() {
        NhapHang[] ds = quanLy.quanLyNhapHang().getDsNhapHang();
        String[] lines = new String[ds.length];
        for (int i = 0; i < ds.length; i++) {
            NhapHang nh = ds[i];
            StringBuilder sb = new StringBuilder();
            ChiTietNhapHang[] ctArr = nh.getDsChiTiet();
            for (int j = 0; j < ctArr.length; j++) {
                ChiTietNhapHang ct = ctArr[j];
                sb.append(ct.getSanPham().getMaSanPham())
                        .append(":")
                        .append(ct.getSoLuong())
                        .append(":")
                        .append(ct.getDonGiaNhap());
                if (j < ctArr.length - 1) sb.append(";");
            }

            lines[i] = String.join("|",
                    nh.getMaNhapHang(),
                    nh.getNgayNhap().toString(),
                    (nh.getNhaCungCap() != null ? nh.getNhaCungCap().getMaNhaCungCap() : ""),
                    (nh.getNguoiLap() != null ? nh.getNguoiLap().getMaNhanVien() : ""),
                    sb.toString());
        }
        ghiTatCaDong(FILE_NHAP_HANG, lines);
        thoiGianSuaFileNhapHang = new File(FILE_NHAP_HANG).lastModified();
    }

    public synchronized void luuHoaDon() {
        HoaDon[] ds = quanLy.quanLyDonHang().getDsDonHang();
        String[] lines = new String[ds.length];
        for (int i = 0; i < ds.length; i++) {
            HoaDon hd = ds[i];
            String maKH = (hd.getKhachHang() != null) ? hd.getKhachHang().getMaKhachHang() : "";
            String maKM = (hd.getKhuyenMai() != null) ? hd.getKhuyenMai().getMaKhuyenMai() : "";
            String maTN = (hd.getNguoiThanhToan() != null) ? hd.getNguoiThanhToan().getMaNhanVien() : "";

            String ptStr = "TienMat";
            if (hd.getPhuongThucThanhToan() instanceof ChuyenKhoan ck) {
                ptStr = "ChuyenKhoan:" + ck.getMaGiaoDich();
            } else if (hd.getPhuongThucThanhToan() instanceof TheNganHang tnh) {
                ptStr = "TheNganHang:" + tnh.getSoThe();
            }

            StringBuilder sb = new StringBuilder();
            ChiTietHoaDon[] ctArr = hd.getDsChiTiet();
            for (int j = 0; j < ctArr.length; j++) {
                ChiTietHoaDon ct = ctArr[j];
                sb.append(ct.getSanPham().getMaSanPham())
                        .append(":")
                        .append(ct.getSoLuong())
                        .append(":")
                        .append(ct.getDonGia());
                if (j < ctArr.length - 1) sb.append(";");
            }

            lines[i] = String.join("|",
                    hd.getMaHoaDon(),
                    hd.getNgayLap().toString(),
                    hd.getNguoiLap().getMaNhanVien(),
                    maKH,
                    maKM,
                    String.valueOf(hd.isDaThanhToan()),
                    ptStr,
                    maTN,
                    sb.toString());
        }
        ghiTatCaDong(FILE_HOA_DON, lines);
        thoiGianSuaFileHoaDon = new File(FILE_HOA_DON).lastModified();
    }

    public synchronized void luuPhieuBaoHanh() {
        String[] lines = new String[soPhieuBaoHanh];
        for (int i = 0; i < soPhieuBaoHanh; i++) {
            PhieuBaoHanh pbh = dsPhieuBaoHanh[i];
            String maHD = (pbh.getHoaDon() != null) ? pbh.getHoaDon().getMaHoaDon() : "";
            String maSP = (pbh.getSanPham() != null) ? pbh.getSanPham().getMaSanPham() : "";
            String maKH = (pbh.getKhachHang() != null) ? pbh.getKhachHang().getMaKhachHang() : "";
            lines[i] = String.join("|",
                    pbh.getMaPhieuBH(),
                    maHD,
                    maSP,
                    maKH,
                    pbh.getNgayBatDau().toString(),
                    pbh.getNgayKetThuc().toString(),
                    pbh.getMoTaLoi(),
                    pbh.getTrangThai());
        }
        ghiTatCaDong(FILE_PHIEU_BAO_HANH, lines);
        thoiGianSuaFilePhieuBaoHanh = new File(FILE_PHIEU_BAO_HANH).lastModified();
    }

    // =========================================================================
    // HELPER ĐỌC / GHI FILE VĂN BẢN UTF-8
    // =========================================================================
    private String[] docTatCaDong(String duongDan) {
        String[] lines = new String[16];
        int count = 0;
        File file = new File(duongDan);
        if (!file.exists()) return new String[0];
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    if (count >= lines.length) {
                        String[] moi = new String[lines.length * 2];
                        System.arraycopy(lines, 0, moi, 0, count);
                        lines = moi;
                    }
                    lines[count++] = line;
                }
            }
        } catch (Exception e) {
            System.err.println("Loi doc file " + duongDan + ": " + e.getMessage());
        }
        String[] ketQua = new String[count];
        System.arraycopy(lines, 0, ketQua, 0, count);
        return ketQua;
    }

    private void ghiTatCaDong(String duongDan, String[] lines) {
        File file = new File(duongDan);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            if (lines != null) {
                for (String line : lines) {
                    bw.write(line);
                    bw.newLine();
                }
            }
        } catch (Exception e) {
            System.err.println("Loi ghi file " + duongDan + ": " + e.getMessage());
        }
    }
}
