package sieuthi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import manage.QuanLy;
import sieuthi.model.*;

/**
 * Menu quản lý nhân sự tương tác qua bàn phím (CLI).
 * Kế thừa và hoàn thiện từ nhánh Person của bạn Hoàng.
 */
public class MenuNhanSu {
    private final List<Person> dsNhanSu = new ArrayList<>();

    public MenuNhanSu() {
        // Dữ liệu mẫu ban đầu
        dsNhanSu.add(new NhanVienPartTime("NV_PT01", "Nguyễn Văn An", "Hà Nội", "0901112233", 40, 30000));
        dsNhanSu.add(new ThuNgan("NV_TN01", "Trần Thị Bình", "TP.HCM", "0912223344", 26, 1.2, "Q01"));
        dsNhanSu.add(new QuanLy("NV_QL01", "Lê Văn Cường", "Đà Nẵng", "0923334455", 26, 2.0, 3000000));
    }

    public void themNhanSu(Scanner sc) {
        System.out.println("\n--- THÊM NHÂN SỰ MỚI ---");
        System.out.println("1. Nhân viên Part-time");
        System.out.println("2. Nhân viên Full-time (Thu ngân / Quản lý)");
        System.out.print("Chọn loại nhân viên (1/2): ");
        int luaChon;
        try {
            luaChon = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Lựa chọn không hợp lệ!");
            return;
        }

        System.out.print("Nhập mã nhân viên: ");
        String maNV = sc.nextLine().trim();
        System.out.print("Nhập họ và tên: ");
        String hoTen = sc.nextLine().trim();
        System.out.print("Nhập địa chỉ: ");
        String diaChi = sc.nextLine().trim();
        System.out.print("Nhập số điện thoại: ");
        String sdt = sc.nextLine().trim();

        Person ps = null;
        switch (luaChon) {
            case 1:
                System.out.print("Nhập số giờ làm: ");
                int soGioLam = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Nhập lương theo giờ: ");
                double luongGio = Double.parseDouble(sc.nextLine().trim());
                ps = new NhanVienPartTime(maNV, hoTen, diaChi, sdt, soGioLam, luongGio);
                break;
            case 2:
                System.out.println("Chọn chức vụ cụ thể: ");
                System.out.println("  1. Quản lý");
                System.out.println("  2. Thu ngân");
                System.out.print("Lựa chọn (1 hoặc 2): ");
                int cv = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Nhập số ngày công: ");
                int ngayCong = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Nhập hệ số lương: ");
                double heSo = Double.parseDouble(sc.nextLine().trim());

                if (cv == 1) {
                    System.out.print("Nhập số tiền phụ cấp: ");
                    double phuCap = Double.parseDouble(sc.nextLine().trim());
                    ps = new QuanLy(maNV, hoTen, diaChi, sdt, ngayCong, heSo, phuCap);
                } else if (cv == 2) {
                    System.out.print("Nhập mã quầy: ");
                    String maQuay = sc.nextLine().trim();
                    ps = new ThuNgan(maNV, hoTen, diaChi, sdt, ngayCong, heSo, maQuay);
                } else {
                    System.out.println("Chức vụ không hợp lệ!");
                    return;
                }
                break;
            default:
                System.out.println("Lựa chọn loại nhân sự không hợp lệ!");
                return;
        }

        if (ps != null) {
            dsNhanSu.add(ps);
            System.out.println("-> Thêm nhân sự thành công!");
        }
    }

    public void inDanhSach() {
        System.out.println("\n============= DANH SÁCH NHÂN SỰ HIỆN CÓ =============");
        if (dsNhanSu.isEmpty()) {
            System.out.println("  (Danh sách nhân sự đang trống)");
            return;
        }
        for (Person p : dsNhanSu) {
            p.hienThiThongTin();
            System.out.println("-----------------------------------------------------");
        }
    }

    public void chayMenu(Scanner sc) {
        int chon = -1;
        do {
            System.out.println("\n--- MENU QUẢN LÝ NHÂN SỰ (NHÁNH PERSON) ---");
            System.out.println("1. Thêm nhân sự mới");
            System.out.println("2. Hiển thị danh sách nhân sự");
            System.out.println("0. Quay lại Menu chính");
            System.out.print("Lựa chọn: ");
            try {
                chon = Integer.parseInt(sc.nextLine().trim());
            } catch (Exception e) {
                chon = -1;
            }

            switch (chon) {
                case 1:
                    themNhanSu(sc);
                    break;
                case 2:
                    inDanhSach();
                    break;
                case 0:
                    System.out.println("Quay lại menu chính.");
                    break;
                default:
                    System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (chon != 0);
    }
}
