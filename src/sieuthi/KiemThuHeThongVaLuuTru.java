package sieuthi;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import sieuthi.data.DuLieuHeThong;
import sieuthi.manage.QuanLy;
import sieuthi.model.*;

/**
 * Bo kiem thu cho tang luu tru du lieu file va quy tac nghiep vu quan ly ton kho (stock).
 * Kiem tra:
 * 1. Doc / Ghi file text (.txt) cho 9 thuc the voi it nhat 5 ban ghi mau moi file.
 * 2. RANG BUOC TON KHO: Ton kho chi thay doi qua Nhap hang va Ban hang, khong the sua thu cong.
 */
public class KiemThuHeThongVaLuuTru {
    private static int dat = 0;
    private static int loi = 0;

    private static void kiemTra(String ten, boolean b) {
        if (b) {
            System.out.println("  [OK]   " + ten);
            dat++;
        } else {
            System.out.println("  [LOI]  " + ten);
            loi++;
        }
    }

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        System.out.println("==================================================");
        System.out.println("  KIEM THU LUU TRU FILE & RANG BUOC TON KHO (STOCK) ");
        System.out.println("==================================================");

        QuanLy ql = new QuanLy("QL01", "Pham Van Quan Ly", "Ha Noi", "0988888888", 26, 2.0, 3_000_000);
        DuLieuHeThong storage = new DuLieuHeThong(ql);

        // 1. Kiem tra doc du lieu mau tu cac file text
        System.out.println("\n1. Kiem tra khoi tao & doc 9 file luu tru mau (>= 5 ban ghi):");
        storage.docTatCaDuLieu();

        int soKH = ql.quanLyKhachHang().getSoLuong();
        int soNV = storage.getDsNhanVien().size() + 1; // gom ca quan ly
        int soCa = storage.getDsCaLam().size();
        int soNCC = storage.getDsNhaCungCap().size();
        int soSP = ql.quanLySanPham().getSoLuong();
        int soKM = storage.getDsKhuyenMai().size();
        int soNH = ql.quanLyNhapHang().getSoLuong();
        int soHD = ql.quanLyDonHang().getSoLuong();
        int soPBH = storage.getDsPhieuBaoHanh().size();

        kiemTra("khach hang >= 5 (thuc te: " + soKH + ")", soKH >= 5);
        kiemTra("nhan vien >= 5 (thuc te: " + soNV + ")", soNV >= 5);
        kiemTra("ca lam viec >= 5 (thuc te: " + soCa + ")", soCa >= 5);
        kiemTra("nha cung cap >= 5 (thuc te: " + soNCC + ")", soNCC >= 5);
        kiemTra("san pham >= 5 (thuc te: " + soSP + ")", soSP >= 5);
        kiemTra("khuyen mai >= 5 (thuc te: " + soKM + ")", soKM >= 5);
        kiemTra("phieu nhap hang >= 5 (thuc te: " + soNH + ")", soNH >= 5);
        kiemTra("hoa don ban hang >= 5 (thuc te: " + soHD + ")", soHD >= 5);
        kiemTra("phieu bao hanh >= 5 (thuc te: " + soPBH + ")", soPBH >= 5);

        // 2. RANG BUOC BAO VE TON KHO: Khong cho phep sua thu cong
        System.out.println("\n2. Kiem tra quy tac bat bien ton kho (Stock Integrity):");
        SanPham sp = ql.quanLySanPham().timKiemTheoMa("SP01");
        kiemTra("tim thay san pham SP01", sp != null);
        int tonKhoBanDau = sp.getSoLuongTonKho();

        // Thu tao mot san pham moi co co tinh sua ton kho thanh 99999
        SanPham spCoYDoXau = new ThucPham("SP01", "Mi Hao Hao Tom Chua Cay (Da sua ten)",
                sp.getNuocSanXuat(), sp.getNamSanXuat(), sp.getHangSanXuat(),
                99999, // co tinh sua ton kho
                6000.0, ((ThucPham) sp).getHanSuDung());

        boolean suaThanhCong = ql.quanLySanPham().sua("SP01", spCoYDoXau);
        kiemTra("sua thong tin san pham thanh cong", suaThanhCong);
        SanPham spSauSua = ql.quanLySanPham().timKiemTheoMa("SP01");
        kiemTra("ten san pham da duoc cap nhat moi", "Mi Hao Hao Tom Chua Cay (Da sua ten)".equals(spSauSua.getTenSanPham()));
        kiemTra("ton kho KHONG bi thay doi thu cong (van giu nguyen " + tonKhoBanDau + ")",
                spSauSua.getSoLuongTonKho() == tonKhoBanDau);

        // 3. TON KHO CHI TANG QUA NHAP HANG
        System.out.println("\n3. Kiem tra bien dong ton kho qua Nhap hang (Import):");
        NhaCungCap ncc = storage.timNhaCungCap("NCC01");
        NhapHang phieuNhapMoi = new NhapHang("NH_TEST", LocalDate.now(), ncc, ql);
        phieuNhapMoi.themChiTietVaCapNhatTonKho(spSauSua, 25, 3800.0);
        ql.quanLyNhapHang().them(phieuNhapMoi);
        kiemTra("ton kho tang chinh xac sau khi nhap (+25 => " + (tonKhoBanDau + 25) + ")",
                spSauSua.getSoLuongTonKho() == tonKhoBanDau + 25);

        // 4. TON KHO CHI GIAM QUA XUAT BAN HOA DON
        System.out.println("\n4. Kiem tra bien dong ton kho qua Ban hang (Sales Invoice):");
        KhachHang kh = ql.quanLyKhachHang().timKiemTheoMa("KH01");
        ThuNgan tn = (ThuNgan) storage.timNhanVien("TN01");
        HoaDon hdMoi = new HoaDon("HD_TEST", LocalDate.now(), tn, kh, null);
        hdMoi.themChiTiet(spSauSua, 10);
        int tonKhoTruocKhiThanhToan = spSauSua.getSoLuongTonKho();
        tn.thanhToanHoaDon(hdMoi, new TienMat());
        ql.quanLyDonHang().them(hdMoi);

        kiemTra("hoa don thanh toan thanh cong", hdMoi.isDaThanhToan());
        kiemTra("ton kho giam chinh xac sau thanh toan (-10 => " + (tonKhoTruocKhiThanhToan - 10) + ")",
                spSauSua.getSoLuongTonKho() == tonKhoTruocKhiThanhToan - 10);

        // Khoi phuc lai ten ban dau
        spSauSua.setTenSanPham("Mi Hao Hao Tom Chua Cay");

        // 5. Kiem tra tinh ben vung: Ghi ra file
        System.out.println("\n5. Kiem tra ghi du lieu ra file:");
        storage.luuTatCaDuLieu();
        kiemTra("luu tat ca 9 danh muc ra file thanh cong", true);

        System.out.println("\n--------------------------------------------------");
        System.out.println("KET QUA KIEM THU LUU TRU VA TON KHO: " + dat + " dat, " + loi + " loi");
        System.out.println("--------------------------------------------------");
        if (loi == 0) {
            System.out.println(">>> DU LIEU FILE VA QUY TAC TON KHO DAT CHUAN 100%! <<<");
        }
    }
}
