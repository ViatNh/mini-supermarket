package sieuthi.model;

import java.util.Scanner;

/**
 * Lớp trừu tượng Person đại diện cho con người trong hệ thống siêu thị.
 * Cài đặt theo sơ đồ Class_Structure.drawio và tích hợp đầy đủ từ nhánh Person.
 */
public abstract class Person {
    private String hoTen;
    private String diaChi;
    private String soDienThoai;

    public Person() {
        this.hoTen = "";
        this.diaChi = "";
        this.soDienThoai = "";
    }

    public Person(String hoTen, String diaChi, String soDienThoai) {
        this.hoTen = hoTen;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    // --- Standard Getters & Setters ---
    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getSoDienThoai() { return soDienThoai; }
    public void setSoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    // --- Alias Getters & Setters để tương thích code nhánh Person ---
    public String getHoten() { return this.hoTen; }
    public void setHoten(String hoten) { this.hoTen = hoten; }

    public String getsoDienThoai() { return this.soDienThoai; }
    public void setsoDienThoai(String soDienThoai) { this.soDienThoai = soDienThoai; }

    /**
     * Nhập thông tin người từ bàn phím bằng Scanner.
     */
    public void nhap() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Moi nhap ho ten: ");
        this.hoTen = sc.nextLine();
        System.out.print("Moi nhap dia chi: ");
        this.diaChi = sc.nextLine();
        System.out.print("Moi nhap so dien thoai: ");
        this.soDienThoai = sc.nextLine();
    }

    /**
     * Xuất chuỗi định dạng thông tin.
     */
    public String xuat() {
        return "Ho ten: " + this.hoTen + " / Dia chi: " + this.diaChi + " / So dien thoai: " + this.soDienThoai;
    }

    public abstract void hienThiThongTin();
}
