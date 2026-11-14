package sieuthi;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import sieuthi.manage.QuanLy;
import sieuthi.model.*;

/**
 * Menu quan ly nhan su tuong tac qua ban phim (CLI).
 * Ke thua va hoan thien tu nhanh Person cua ban Hoang.
 */
public class MenuNhanSu {
    private final List<Person> dsNhanSu = new ArrayList<>();

    public MenuNhanSu() {
        // Du lieu mau ban dau (khong dau)
        dsNhanSu.add(new NhanVienPartTime("NV_PT01", "Nguyen Van An", "Ha Noi", "0901112233", 40, 30000));
        dsNhanSu.add(new ThuNgan("NV_TN01", "Tran Thi Binh", "TP.HCM", "0912223344", 26, 1.2, "Q01"));
        dsNhanSu.add(new QuanLy("NV_QL01", "Le Van Cuong", "Da Nang", "0923334455", 26, 2.0, 3000000));
    }

    public void themNhanSu(Scanner sc) {
        System.out.println("\n--- THEM NHAN SU MOI ---");
        System.out.println("1. Nhan vien Part-time");
        System.out.println("2. Nhan vien Full-time (Thu ngan / Quan ly)");
        System.out.print("Chon loai nhan vien (1/2): ");
        int luaChon;
        try {
            luaChon = Integer.parseInt(sc.nextLine().trim());
        } catch (Exception e) {
            System.out.println("Lua chon khong hop le!");
            return;
        }

        System.out.print("Nhap ma nhan vien: ");
        String maNV = sc.nextLine().trim();
        System.out.print("Nhap ho va ten: ");
        String hoTen = sc.nextLine().trim();
        System.out.print("Nhap dia chi: ");
        String diaChi = sc.nextLine().trim();
        System.out.print("Nhap so dien thoai: ");
        String sdt = sc.nextLine().trim();

        Person ps = null;
        switch (luaChon) {
            case 1:
                System.out.print("Nhap so gio lam: ");
                int soGioLam = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Nhap luong theo gio: ");
                double luongGio = Double.parseDouble(sc.nextLine().trim());
                ps = new NhanVienPartTime(maNV, hoTen, diaChi, sdt, soGioLam, luongGio);
                break;
            case 2:
                System.out.println("Chon chuc vu cu the: ");
                System.out.println("  1. Quan ly");
                System.out.println("  2. Thu ngan");
                System.out.print("Lua chon (1 hoac 2): ");
                int cv = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Nhap so ngay cong: ");
                int ngayCong = Integer.parseInt(sc.nextLine().trim());
                System.out.print("Nhap he so luong: ");
                double heSo = Double.parseDouble(sc.nextLine().trim());

                if (cv == 1) {
                    System.out.print("Nhap so tien phu cap: ");
                    double phuCap = Double.parseDouble(sc.nextLine().trim());
                    ps = new QuanLy(maNV, hoTen, diaChi, sdt, ngayCong, heSo, phuCap);
                } else if (cv == 2) {
                    System.out.print("Nhap ma quay: ");
                    String maQuay = sc.nextLine().trim();
                    ps = new ThuNgan(maNV, hoTen, diaChi, sdt, ngayCong, heSo, maQuay);
                } else {
                    System.out.println("Chuc vu khong hop le!");
                    return;
                }
                break;
            default:
                System.out.println("Lua chon loai nhan su khong hop le!");
                return;
        }

        if (ps != null) {
            dsNhanSu.add(ps);
            System.out.println("-> Them nhan su thanh cong!");
        }
    }

    public void inDanhSach() {
        System.out.println("\n============= DANH SACH NHAN SU HIEN CO =============");
        if (dsNhanSu.isEmpty()) {
            System.out.println("  (Danh sach nhan su dang trong)");
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
            System.out.println("\n--- MENU QUAN LY NHAN SU (NHANH PERSON) ---");
            System.out.println("1. Them nhan su moi");
            System.out.println("2. Hien thi danh sach nhan su");
            System.out.println("0. Quay lai Menu chinh");
            System.out.print("Lua chon: ");
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
                    System.out.println("Quay lai menu chinh.");
                    break;
                default:
                    System.out.println("Lua chon khong hop le!");
            }
        } while (chon != 0);
    }
}
