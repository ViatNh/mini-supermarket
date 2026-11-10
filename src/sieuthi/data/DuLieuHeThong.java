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
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sieuthi.manage.QuanLy;
import sieuthi.model.*;

/**
 * Lớp chịu trách nhiệm lưu trữ và truy xuất toàn bộ dữ liệu của siêu thị từ các file text (.txt).
 * Đảm bảo:
 * 1. Dữ liệu được lưu trữ vĩnh viễn trên ổ đĩa trong thư mục "data/".
 * 2. Mỗi file có ít nhất 5 dữ liệu mẫu đầy đủ, chân thực.
 * 3. Hỗ trợ đọc ghi tự động và an toàn (chuẩn UTF-8, phân cách bằng '|').
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
    private final List<NhanVien> dsNhanVien = new ArrayList<>();
    private final List<CaLam> dsCaLam = new ArrayList<>();
    private final List<NhaCungCap> dsNhaCungCap = new ArrayList<>();
    private final List<KhuyenMai> dsKhuyenMai = new ArrayList<>();
    private final List<PhieuBaoHanh> dsPhieuBaoHanh = new ArrayList<>();
    private final KhoHang khoHang = new KhoHang("KHO01", "Kho Tổng Siêu Thị Mini");

    public DuLieuHeThong(QuanLy quanLy) {
        this.quanLy = (quanLy != null) ? quanLy : new QuanLy("QL01", "Phạm Văn Quản Lý", "Hà Nội", "0988888888", 26, 2.0, 3_000_000);
    }

    public QuanLy getQuanLy() {
        return quanLy;
    }

    public List<NhanVien> getDsNhanVien() {
        return Collections.unmodifiableList(dsNhanVien);
    }

    public List<CaLam> getDsCaLam() {
        return Collections.unmodifiableList(dsCaLam);
    }

    public List<NhaCungCap> getDsNhaCungCap() {
        return Collections.unmodifiableList(dsNhaCungCap);
    }

    public List<KhuyenMai> getDsKhuyenMai() {
        return Collections.unmodifiableList(dsKhuyenMai);
    }

    public List<PhieuBaoHanh> getDsPhieuBaoHanh() {
        return Collections.unmodifiableList(dsPhieuBaoHanh);
    }

    public KhoHang getKhoHang() {
        return khoHang;
    }

    public boolean themNhanVien(NhanVien nv) {
        if (nv == null || timNhanVien(nv.getMaNhanVien()) != null) return false;
        return dsNhanVien.add(nv);
    }

    public boolean themCaLam(CaLam ca) {
        if (ca == null || timCaLam(ca.getMaCa()) != null) return false;
        return dsCaLam.add(ca);
    }

    public boolean themNhaCungCap(NhaCungCap ncc) {
        if (ncc == null || timNhaCungCap(ncc.getMaNhaCungCap()) != null) return false;
        return dsNhaCungCap.add(ncc);
    }

    public boolean themKhuyenMai(KhuyenMai km) {
        if (km == null || timKhuyenMai(km.getMaKhuyenMai()) != null) return false;
        return dsKhuyenMai.add(km);
    }

    public boolean themPhieuBaoHanh(PhieuBaoHanh pbh) {
        if (pbh == null || timPhieuBaoHanh(pbh.getMaPhieuBH()) != null) return false;
        return dsPhieuBaoHanh.add(pbh);
    }

    public NhanVien timNhanVien(String ma) {
        if (ma == null) return null;
        if (quanLy != null && ma.equalsIgnoreCase(quanLy.getMaNhanVien())) return quanLy;
        for (NhanVien nv : dsNhanVien) {
            if (nv.getMaNhanVien().equalsIgnoreCase(ma.trim())) return nv;
        }
        return null;
    }

    public CaLam timCaLam(String ma) {
        if (ma == null) return null;
        for (CaLam c : dsCaLam) {
            if (c.getMaCa().equalsIgnoreCase(ma.trim())) return c;
        }
        return null;
    }

    public NhaCungCap timNhaCungCap(String ma) {
        if (ma == null) return null;
        for (NhaCungCap ncc : dsNhaCungCap) {
            if (ncc.getMaNhaCungCap().equalsIgnoreCase(ma.trim())) return ncc;
        }
        return null;
    }

    public KhuyenMai timKhuyenMai(String ma) {
        if (ma == null) return null;
        for (KhuyenMai km : dsKhuyenMai) {
            if (km.getMaKhuyenMai().equalsIgnoreCase(ma.trim())) return km;
        }
        return null;
    }

    public PhieuBaoHanh timPhieuBaoHanh(String ma) {
        if (ma == null) return null;
        for (PhieuBaoHanh pbh : dsPhieuBaoHanh) {
            if (pbh.getMaPhieuBH().equalsIgnoreCase(ma.trim())) return pbh;
        }
        return null;
    }

    /**
     * Khởi tạo thư mục data và các file mẫu nếu chưa tồn tại.
     */
    public void khoiTaoDuLieuMacDinhNeuChuaCo() {
        File dir = new File(THU_MUC_DATA);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        khoiTaoFileNeuChuaCo(FILE_KHACH_HANG, List.of(
                "KH01|Nguyễn Văn An|123 Lê Lợi, Q.1, TP.HCM|0901234567|150",
                "KH02|Trần Thị Bình|456 Hai Bà Trưng, Hà Nội|0912345678|320",
                "KH03|Lê Hoàng Cường|789 Nguyễn Huệ, Đà Nẵng|0987654321|50",
                "KH04|Phạm Thị Dung|12 Trần Hưng Đạo, Cần Thơ|0934567890|0",
                "KH05|Võ Minh Em|34 Pasteur, TP.HCM|0978123456|85"
        ));

        khoiTaoFileNeuChuaCo(FILE_NHAN_VIEN, List.of(
                "QUAN_LY|QL01|Phạm Văn Quản Lý|101 Cầu Giấy, Hà Nội|0988888888|26|2.0|3000000.0",
                "THU_NGAN|TN01|Trần Thị Thu Ngân|202 Nam Kỳ Khởi Nghĩa, TP.HCM|0923456789|26|1.5|Quầy 01",
                "THU_NGAN|TN02|Nguyễn Thu Hà|15 Lý Thường Kiệt, TP.HCM|0934111222|24|1.4|Quầy 02",
                "FULL_TIME|FT01|Lê Văn Kiểm Kho|88 Cách Mạng Tháng 8, TP.HCM|0945666777|26|1.6|0.0",
                "PART_TIME|PT01|Đỗ Sinh Viên|50 Võ Văn Ngân, TP.Thủ Đức|0933333333|45|30000.0|0.0"
        ));

        khoiTaoFileNeuChuaCo(FILE_CA_LAM, List.of(
                "CA01|2026-11-10|07:30|12:00|TN01",
                "CA02|2026-11-10|12:30|17:30|TN02",
                "CA03|2026-11-10|17:30|22:00|PT01",
                "CA04|2026-11-11|07:30|16:30|FT01",
                "CA05|2026-11-11|08:00|17:00|QL01"
        ));

        khoiTaoFileNeuChuaCo(FILE_NHA_CUNG_CAP, List.of(
                "NCC01|Công ty CP Acecook Việt Nam|02838154064|KCN Tân Bình, P. Tây Thạnh, Q. Tân Phú, TP.HCM",
                "NCC02|Công ty TNHH Suntory PepsiCo Việt Nam|02838219468|88 Đồng Khởi, Q.1, TP.HCM",
                "NCC03|Tập đoàn Sunhouse Group|02437366676|139 Nguyễn Thái Học, Ba Đình, Hà Nội",
                "NCC04|Công ty CP Sữa Việt Nam (Vinamilk)|02854155555|10 Tân Trào, P. Tân Phú, Q.7, TP.HCM",
                "NCC05|Công ty TNHH Unilever Việt Nam|02854135686|A2-3 KCN Tây Bắc Củ Chi, TP.HCM"
        ));

        // Tồn kho mẫu = Tổng số lượng nhập trừ đi tổng số lượng đã bán qua hóa đơn
        khoiTaoFileNeuChuaCo(FILE_SAN_PHAM, List.of(
                "THUC_PHAM|SP01|Mì Hảo Hảo Tôm Chua Cay|Việt Nam|2026|Acecook|150|5000.0|2027-05-15|",
                "THUC_PHAM|SP02|Sữa tươi tiệt trùng Vinamilk 1L|Việt Nam|2026|Vinamilk|80|32000.0|2027-02-28|",
                "DO_UONG|SP03|Nước tinh khiết Aquafina 500ml|Việt Nam|2026|PepsiCo|200|10000.0|500|2027-11-10",
                "DO_UONG|SP04|Trà xanh Không Độ 455ml|Việt Nam|2026|Tân Hiệp Phát|120|12000.0|455|2027-08-20",
                "DO_DUNG|SP05|Ấm siêu tốc Inox Sunhouse 1.8L|Việt Nam|2025|Sunhouse|45|220000.0|Inox 304|12",
                "DO_DUNG|SP06|Chảo chống dính Sunhouse 26cm|Việt Nam|2026|Sunhouse|30|180000.0|Nhôm phủ chống dính|6"
        ));

        khoiTaoFileNeuChuaCo(FILE_KHUYEN_MAI, List.of(
                "KM01|0.10|2026-11-01|2026-11-30",
                "KM02|0.15|2026-11-10|2026-11-15",
                "KM03|0.05|2026-10-01|2026-12-31",
                "KM04|0.20|2026-11-20|2026-11-25",
                "KM_HETHAN|0.25|2026-01-01|2026-02-01"
        ));

        khoiTaoFileNeuChuaCo(FILE_NHAP_HANG, List.of(
                "NH001|2026-11-01|NCC01|QL01|SP01:110:3500.0",
                "NH002|2026-11-02|NCC02|QL01|SP03:207:6000.0;SP04:123:8000.0",
                "NH003|2026-11-03|NCC03|FT01|SP05:46:150000.0;SP06:31:120000.0",
                "NH004|2026-11-05|NCC04|QL01|SP02:82:24000.0",
                "NH005|2026-11-08|NCC01|FT01|SP01:51:3600.0"
        ));

        khoiTaoFileNeuChuaCo(FILE_HOA_DON, List.of(
                "HD001|2026-11-05|TN01|KH01|KM01|true|TienMat|TN01|SP01:5:5000.0;SP03:2:10000.0",
                "HD002|2026-11-06|TN01|KH02|KM02|true|ChuyenKhoan:CK99812|TN01|SP05:1:220000.0;SP02:2:32000.0",
                "HD003|2026-11-07|TN02|KH03|KM03|true|TheNganHang:9704123456789012|TN02|SP04:3:12000.0;SP01:4:5000.0",
                "HD004|2026-11-08|TN01|KH04||true|TienMat|TN01|SP03:5:10000.0",
                "HD005|2026-11-09|TN02|KH05|KM01|true|ChuyenKhoan:CK88421|TN02|SP06:1:180000.0;SP01:2:5000.0"
        ));

        khoiTaoFileNeuChuaCo(FILE_PHIEU_BAO_HANH, List.of(
                "PBH001|HD002|SP05|KH02|2026-11-06|2027-11-06|Ấm không vào điện|Đang xử lý",
                "PBH002|HD005|SP06|KH05|2026-11-09|2027-05-09|Tróc lớp chống dính|Đã tiếp nhận",
                "PBH003|HD002|SP05|KH02|2026-11-06|2027-11-06|Bảo hành nút bấm|Đã hoàn thành",
                "PBH004|HD005|SP06|KH05|2026-11-09|2027-05-09|Kiểm tra tay cầm lỏng|Đã tiếp nhận",
                "PBH005|HD002|SP05|KH02|2026-11-06|2027-11-06|Bảo dưỡng định kỳ|Đã hoàn thành"
        ));
    }

    private void khoiTaoFileNeuChuaCo(String duongDan, List<String> dongMacDinh) {
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

    private void docKhachHang() {
        List<String> lines = docTatCaDong(FILE_KHACH_HANG);
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

    private void docNhanVien() {
        List<String> lines = docTatCaDong(FILE_NHAN_VIEN);
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
                        double heSo = (p.length > 6) ? Double.parseDouble(p[6].trim()) : 1.0;
                        String quay = (p.length > 7) ? p[7].trim() : "Quầy 01";
                        ThuNgan tn = new ThuNgan(ma, ten, diaChi, sdt, ngayCong, heSo, quay);
                        themNhanVien(tn);
                    }
                    case "PART_TIME" -> {
                        int gioLam = (p.length > 5) ? Integer.parseInt(p[5].trim()) : 0;
                        double luongGio = (p.length > 6) ? Double.parseDouble(p[6].trim()) : 30_000;
                        NhanVienPartTime pt = new NhanVienPartTime(ma, ten, diaChi, sdt, gioLam, luongGio);
                        themNhanVien(pt);
                    }
                    case "FULL_TIME" -> {
                        int ngayCong = (p.length > 5) ? Integer.parseInt(p[5].trim()) : 26;
                        double heSo = (p.length > 6) ? Double.parseDouble(p[6].trim()) : 1.0;
                        NhanVienFullTime ft = new NhanVienFullTime(ma, ten, diaChi, sdt, ngayCong, heSo);
                        themNhanVien(ft);
                    }
                }
            }
        }
    }

    private void docCaLam() {
        List<String> lines = docTatCaDong(FILE_CA_LAM);
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

    private void docNhaCungCap() {
        List<String> lines = docTatCaDong(FILE_NHA_CUNG_CAP);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 4) {
                NhaCungCap ncc = new NhaCungCap(p[0].trim(), p[1].trim(), p[2].trim(), p[3].trim());
                themNhaCungCap(ncc);
            }
        }
    }

    private void docSanPham() {
        List<String> lines = docTatCaDong(FILE_SAN_PHAM);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 8) {
                String loai = p[0].trim().toUpperCase();
                String ma = p[1].trim();
                String ten = p[2].trim();
                String nuocSX = p[3].trim();
                int namSX = Integer.parseInt(p[4].trim());
                String hangSX = p[5].trim();
                int tonKho = Integer.parseInt(p[6].trim());
                double giaGoc = Double.parseDouble(p[7].trim());

                SanPham sp = null;
                switch (loai) {
                    case "THUC_PHAM" -> {
                        LocalDate hsd = (p.length > 8 && !p[8].trim().isEmpty())
                                ? LocalDate.parse(p[8].trim()) : LocalDate.now().plusMonths(6);
                        sp = new ThucPham(ma, ten, nuocSX, namSX, hangSX, tonKho, giaGoc, hsd);
                    }
                    case "DO_UONG" -> {
                        int dungTich = (p.length > 8 && !p[8].trim().isEmpty()) ? Integer.parseInt(p[8].trim()) : 500;
                        LocalDate hsd = (p.length > 9 && !p[9].trim().isEmpty())
                                ? LocalDate.parse(p[9].trim()) : LocalDate.now().plusMonths(12);
                        sp = new DoUong(ma, ten, nuocSX, namSX, hangSX, tonKho, giaGoc, dungTich, hsd);
                    }
                    case "DO_DUNG" -> {
                        String chatLieu = (p.length > 8 && !p[8].trim().isEmpty()) ? p[8].trim() : "Inox";
                        int bhThang = (p.length > 9 && !p[9].trim().isEmpty()) ? Integer.parseInt(p[9].trim()) : 12;
                        sp = new DoDung(ma, ten, nuocSX, namSX, hangSX, tonKho, giaGoc, chatLieu, bhThang);
                    }
                }
                if (sp != null) {
                    quanLy.quanLySanPham().them(sp);
                }
            }
        }
    }

    private void docKhuyenMai() {
        List<String> lines = docTatCaDong(FILE_KHUYEN_MAI);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 4) {
                String ma = p[0].trim();
                double tiLe = Double.parseDouble(p[1].trim());
                LocalDate bd = LocalDate.parse(p[2].trim());
                LocalDate kt = LocalDate.parse(p[3].trim());
                KhuyenMai km = new KhuyenMai(ma, tiLe, bd, kt);
                themKhuyenMai(km);
            }
        }
    }

    private void docNhapHang() {
        List<String> lines = docTatCaDong(FILE_NHAP_HANG);
        for (String line : lines) {
            String[] p = line.split("\\|", -1);
            if (p.length >= 5) {
                String maNH = p[0].trim();
                LocalDate ngayNhap = LocalDate.parse(p[1].trim());
                String maNCC = p[2].trim();
                String maNguoiLap = p[3].trim();
                String dsChiTietStr = p[4].trim();

                NhaCungCap ncc = timNhaCungCap(maNCC);
                NhanVien nguoiLap = timNhanVien(maNguoiLap);
                if (ncc == null) ncc = new NhaCungCap(maNCC, "Nhà cung cấp " + maNCC, "", "");
                if (nguoiLap == null) nguoiLap = quanLy;

                NhapHang nh = new NhapHang(maNH, ngayNhap, ncc, nguoiLap);
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

    private void docHoaDon() {
        List<String> lines = docTatCaDong(FILE_HOA_DON);
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

                // Xử lý phương thức thanh toán & thu ngân
                NhanVien nvTN = timNhanVien(maTN);
                ThuNgan tn = (nvTN instanceof ThuNgan thuNgan) ? thuNgan : new ThuNgan(maTN, "Thu ngân " + maTN, "", "", 26, 1.0, "Q01");

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

    private void docPhieuBaoHanh() {
        List<String> lines = docTatCaDong(FILE_PHIEU_BAO_HANH);
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
        List<String> lines = new ArrayList<>();
        for (KhachHang kh : quanLy.quanLyKhachHang().getDsKhachHang()) {
            lines.add(String.join("|",
                    kh.getMaKhachHang(),
                    kh.getHoTen(),
                    kh.getDiaChi(),
                    kh.getSoDienThoai(),
                    String.valueOf(kh.getDiemTichLuy())));
        }
        ghiTatCaDong(FILE_KHACH_HANG, lines);
    }

    public void luuNhanVien() {
        List<String> lines = new ArrayList<>();
        // Lưu Quản lý
        lines.add(String.join("|",
                "QUAN_LY",
                quanLy.getMaNhanVien(),
                quanLy.getHoTen(),
                quanLy.getDiaChi(),
                quanLy.getSoDienThoai(),
                String.valueOf(quanLy.getSoNgayCong()),
                String.valueOf(quanLy.getHeSoLuong()),
                String.valueOf(quanLy.getPhuCap())));

        for (NhanVien nv : dsNhanVien) {
            if (nv.getMaNhanVien().equalsIgnoreCase(quanLy.getMaNhanVien())) continue;
            if (nv instanceof ThuNgan tn) {
                lines.add(String.join("|",
                        "THU_NGAN",
                        tn.getMaNhanVien(),
                        tn.getHoTen(),
                        tn.getDiaChi(),
                        tn.getSoDienThoai(),
                        String.valueOf(tn.getSoNgayCong()),
                        String.valueOf(tn.getHeSoLuong()),
                        tn.getMaQuay()));
            } else if (nv instanceof NhanVienPartTime pt) {
                lines.add(String.join("|",
                        "PART_TIME",
                        pt.getMaNhanVien(),
                        pt.getHoTen(),
                        pt.getDiaChi(),
                        pt.getSoDienThoai(),
                        String.valueOf(pt.getSoGioLam()),
                        String.valueOf(pt.getLuongTheoGio()),
                        "0.0"));
            } else if (nv instanceof NhanVienFullTime ft) {
                lines.add(String.join("|",
                        "FULL_TIME",
                        ft.getMaNhanVien(),
                        ft.getHoTen(),
                        ft.getDiaChi(),
                        ft.getSoDienThoai(),
                        String.valueOf(ft.getSoNgayCong()),
                        String.valueOf(ft.getHeSoLuong()),
                        "0.0"));
            }
        }
        ghiTatCaDong(FILE_NHAN_VIEN, lines);
    }

    public void luuCaLam() {
        List<String> lines = new ArrayList<>();
        for (CaLam ca : dsCaLam) {
            String maNV = "";
            for (NhanVien nv : dsNhanVien) {
                if (nv.getDsCaLam().contains(ca)) {
                    maNV = nv.getMaNhanVien();
                    break;
                }
            }
            if (maNV.isEmpty() && quanLy.getDsCaLam().contains(ca)) {
                maNV = quanLy.getMaNhanVien();
            }
            lines.add(String.join("|",
                    ca.getMaCa(),
                    ca.getNgayLam().toString(),
                    ca.getGioBatDau().toString(),
                    ca.getGioKetThuc().toString(),
                    maNV));
        }
        ghiTatCaDong(FILE_CA_LAM, lines);
    }

    public void luuNhaCungCap() {
        List<String> lines = new ArrayList<>();
        for (NhaCungCap ncc : dsNhaCungCap) {
            lines.add(String.join("|",
                    ncc.getMaNhaCungCap(),
                    ncc.getTenNhaCungCap(),
                    ncc.getSoDienThoai(),
                    ncc.getDiaChi()));
        }
        ghiTatCaDong(FILE_NHA_CUNG_CAP, lines);
    }

    public void luuSanPham() {
        List<String> lines = new ArrayList<>();
        for (SanPham sp : quanLy.quanLySanPham().getDsSanPham()) {
            String loai = "SAN_PHAM";
            String t1 = "";
            String t2 = "";
            if (sp instanceof ThucPham tp) {
                loai = "THUC_PHAM";
                t1 = tp.getHanSuDung().toString();
            } else if (sp instanceof DoUong du) {
                loai = "DO_UONG";
                t1 = String.valueOf(du.getDungTichMl());
                t2 = du.getHanSuDung().toString();
            } else if (sp instanceof DoDung dd) {
                loai = "DO_DUNG";
                t1 = dd.getChatLieu();
                t2 = String.valueOf(dd.layThoiHanBaoHanh());
            }
            lines.add(String.join("|",
                    loai,
                    sp.getMaSanPham(),
                    sp.getTenSanPham(),
                    sp.getNuocSanXuat(),
                    String.valueOf(sp.getNamSanXuat()),
                    sp.getHangSanXuat(),
                    String.valueOf(sp.getSoLuongTonKho()),
                    String.valueOf(sp.getGiaGoc()),
                    t1,
                    t2));
        }
        ghiTatCaDong(FILE_SAN_PHAM, lines);
    }

    public void luuKhuyenMai() {
        List<String> lines = new ArrayList<>();
        for (KhuyenMai km : dsKhuyenMai) {
            lines.add(String.join("|",
                    km.getMaKhuyenMai(),
                    String.valueOf(km.getTiLeGiam()),
                    km.getNgayBatDau().toString(),
                    km.getNgayKetThuc().toString()));
        }
        ghiTatCaDong(FILE_KHUYEN_MAI, lines);
    }

    public void luuNhapHang() {
        List<String> lines = new ArrayList<>();
        for (NhapHang nh : quanLy.quanLyNhapHang().getDsNhapHang()) {
            StringBuilder sb = new StringBuilder();
            for (ChiTietNhapHang ct : nh.getDsChiTiet()) {
                if (sb.length() > 0) sb.append(";");
                sb.append(ct.getSanPham().getMaSanPham())
                        .append(":")
                        .append(ct.getSoLuong())
                        .append(":")
                        .append(ct.getDonGiaNhap());
            }
            String maNCC = (nh.getNhaCungCap() != null) ? nh.getNhaCungCap().getMaNhaCungCap() : "";
            String maNV = (nh.getNguoiLap() != null) ? nh.getNguoiLap().getMaNhanVien() : "";
            lines.add(String.join("|",
                    nh.getMaNhapHang(),
                    nh.getNgayNhap().toString(),
                    maNCC,
                    maNV,
                    sb.toString()));
        }
        ghiTatCaDong(FILE_NHAP_HANG, lines);
    }

    public void luuHoaDon() {
        List<String> lines = new ArrayList<>();
        for (HoaDon hd : quanLy.quanLyDonHang().getDsDonHang()) {
            StringBuilder sb = new StringBuilder();
            for (ChiTietHoaDon ct : hd.getDsChiTiet()) {
                if (sb.length() > 0) sb.append(";");
                sb.append(ct.getSanPham().getMaSanPham())
                        .append(":")
                        .append(ct.getSoLuong())
                        .append(":")
                        .append(ct.getDonGia());
            }

            String maNL = (hd.getNguoiLap() != null) ? hd.getNguoiLap().getMaNhanVien() : "";
            String maKH = (hd.getKhachHang() != null) ? hd.getKhachHang().getMaKhachHang() : "";
            String maKM = (hd.getKhuyenMai() != null) ? hd.getKhuyenMai().getMaKhuyenMai() : "";
            String ptStr = "TienMat";
            if (hd.getPhuongThucThanhToan() instanceof ChuyenKhoan ck) {
                ptStr = "ChuyenKhoan:" + ck.getMaGiaoDich();
            } else if (hd.getPhuongThucThanhToan() instanceof TheNganHang tg) {
                ptStr = "TheNganHang:" + tg.getSoThe();
            }
            String maTN = (hd.getNguoiThanhToan() != null) ? hd.getNguoiThanhToan().getMaNhanVien() : "";

            lines.add(String.join("|",
                    hd.getMaHoaDon(),
                    hd.getNgayLap().toString(),
                    maNL,
                    maKH,
                    maKM,
                    String.valueOf(hd.isDaThanhToan()),
                    ptStr,
                    maTN,
                    sb.toString()));
        }
        ghiTatCaDong(FILE_HOA_DON, lines);
    }

    public void luuPhieuBaoHanh() {
        List<String> lines = new ArrayList<>();
        for (PhieuBaoHanh pbh : dsPhieuBaoHanh) {
            String maHD = (pbh.getHoaDon() != null) ? pbh.getHoaDon().getMaHoaDon() : "";
            String maSP = (pbh.getSanPham() != null) ? pbh.getSanPham().getMaSanPham() : "";
            String maKH = (pbh.getKhachHang() != null) ? pbh.getKhachHang().getMaKhachHang() : "";
            lines.add(String.join("|",
                    pbh.getMaPhieuBH(),
                    maHD,
                    maSP,
                    maKH,
                    pbh.getNgayBatDau().toString(),
                    pbh.getNgayKetThuc().toString(),
                    pbh.getMoTaLoi(),
                    pbh.getTrangThai()));
        }
        ghiTatCaDong(FILE_PHIEU_BAO_HANH, lines);
    }

    // =========================================================================
    // HELPER ĐỌC / GHI FILE VĂN BẢN UTF-8
    // =========================================================================
    private List<String> docTatCaDong(String duongDan) {
        List<String> lines = new ArrayList<>();
        File file = new File(duongDan);
        if (!file.exists()) return lines;
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    lines.add(line);
                }
            }
        } catch (Exception e) {
            System.err.println("Lỗi đọc file " + duongDan + ": " + e.getMessage());
        }
        return lines;
    }

    private void ghiTatCaDong(String duongDan, List<String> lines) {
        File file = new File(duongDan);
        File parent = file.getParentFile();
        if (parent != null && !parent.exists()) {
            parent.mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, false), StandardCharsets.UTF_8))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (Exception e) {
            System.err.println("Lỗi ghi file " + duongDan + ": " + e.getMessage());
        }
    }
}
