package Person;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public abstract class Person{
    private String hoten;
    private String diaChi;
    private String soDienThoai;

    public Person(){};

    public Person(String hoten,String diaChi,String soDienThoai){
        this.hoten = hoten;
        this.diaChi = diaChi;
        this.soDienThoai = soDienThoai;
    }

    public abstract void hienThiThongTin();

    //Getter,Setter xài
    public void setHoten(String hoten){
        this.hoten = hoten;
    }
    public String getHoten(){
        return this.hoten;
    }

    public void setDiaChi(String diaChi){
        this.diaChi = diaChi;
    }
    public String getDiaChi(){
        return this.diaChi;
    }

    public void setsoDienThoai(String soDienThoai){
        this.soDienThoai = soDienThoai;
    }
    public String getsoDienThoai(){
        return this.soDienThoai;
    }

    //Hm nhập
    Scanner sc = new Scanner(System.in);
    public void nhap(){
        System.out.println("Moi nhap ho ten: ");
        this.hoten = sc.nextLine();
        System.out.println("Moi nhap dia chi: ");
        this.diaChi = sc.nextLine();
        System.out.println("Moi nhap so dien thoai: ");
        this.soDienThoai = sc.nextLine();
    }

    public String xuat(){
        return "Ten khach hang: "+this.hoten+" /Dia chi: "+this.diaChi+" /So dien thoai: "+this.soDienThoai;
    }
}

//Lớp khách hagnf
class KhachHang extends Person{
    private String maKH;
    private int diemTichLuy;

    public KhachHang(){
        super();
    };
    public KhachHang(String hoTen,String diaChi,String soDienThoai,String maKH,int diemTichLuy){
        super(hoTen,diaChi,soDienThoai);
        this.maKH = maKH;
        this.diemTichLuy = diemTichLuy;
        hienThiThongTin();
    }

    //Setter ,Getter
    public void setmaKH(String maKH){
        this.maKH = maKH;
    }
    public String getmaKH(){
        return this.maKH;
    }

    public void setdiemTichLuy(int diemTichLuy){
        this.diemTichLuy = diemTichLuy;
    }
    public int getdiemTichLuy(){
        return this.diemTichLuy;
    }

    //Hàm tích điểm
    public void tichDiem(double tongTien){
        if(tongTien<0){
            System.out.println("So tien tong khong hop le!");
            return;
        }
        else{
            int diemMoi = (int) tongTien/100000;
            this.diemTichLuy+=diemMoi;
            System.out.println("Tich thanh cong ,Khach hang "+getHoten()+" da tich duoc them "+diemMoi+", So du diem hien tai: "+getdiemTichLuy());
        }
    }


    //Hiển thị thông tin
    @Override 
    public void hienThiThongTin(){
        System.out.println("Ten khach hang: "+this.getHoten()+" /Dia chi: "+getDiaChi()+" /So dien thoai: "+getsoDienThoai()+" /Ma Khach Hang: "+getmaKH()+" /Diem tich luy: "+getdiemTichLuy());
    }
}


class CaLam{
    private String maCa;
    private LocalTime gioBatDau;
    private LocalTime gioKetThuc;
    private LocalDate ngayLam;

    public CaLam(String maCa,LocalTime gioBatDau,LocalTime gioKetThuc,LocalDate ngayLam){
        this.maCa = maCa;
        this.gioBatDau = gioBatDau;
        this.gioKetThuc = gioKetThuc;
        this.ngayLam = ngayLam;
    }

    //=============================
    //Kiem tra trung
    //============================
    public boolean kiemTraTrungCa(CaLam caMoi){
        //Nếu ngày của ca này là giá trị nhân bản (tức y chang) của ca cũ có trước
        if(this.ngayLam.equals(caMoi.ngayLam)){
            //Thay đổi hoàn toàn giờ : một là kết thúc sớm hơn ca mới hai là bắt đằu muộn hơn
            return !(this.gioKetThuc.compareTo(caMoi.gioBatDau) <=0 || this.gioBatDau.compareTo(caMoi.gioKetThuc) >= 0);
        }
        return false;
    }
    
    //Get/Set
    public void setMaCa(String maCa){
        this.maCa = maCa;
    }
    public String getMaCa(){
        return maCa;
    }
    
    public void setNgayLam(LocalDate ngayLam){
        Scanner sc = new Scanner (System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        while(true){
            System.out.print("Nhap ngay lam (quy dinh yyyy-mm-dd): ");
            String input = sc.nextLine();
            try{
                //Phan tich thu đung chưa
                LocalDate ngay = LocalDate.parse(input ,formatter);
                this.ngayLam = ngay;//Gán
                System.out.println("Success! "+ this.ngayLam);
                break; 
            } catch(DateTimeParseException e){
                System.out.println("Khong hop le!");
            }
        }
    }
    public LocalDate getNgayLam(){
        return this.ngayLam;
    }
    
    public LocalTime getGioBatDau(){
        return this.gioBatDau;
    }
    public void setGioBatDau(LocalTime gioBatDau){
        this.gioBatDau = gioBatDau;
    }
    
    public LocalTime getGioKetThuc(){
        return this.gioKetThuc;
    }
    public void setGioKetThuc(LocalTime gioKetThuc){
        this.gioKetThuc = gioKetThuc;
    }
    
    public void hienThiThongTin(){
        System.out.println("Ma ca: "+getMaCa()+" /Ngay lam: "+getNgayLam()+" /Tu: "+getGioBatDau()+" /Den: "+getGioKetThuc());
    }
    
}

abstract class NhanVien extends Person{
    private String maNhanVien;
    public final double luongCoBan = 5000000;

    private List<CaLam> dsCaLam;

    //Constructor
    public NhanVien(){
        super();
        this.dsCaLam = new ArrayList<>(); // Danh sach rong
    };

    //2.Constructor bỏ qua giá trị cố định
    public NhanVien(String hoTen,String diaChi,String soDienThoai,String maNhanVien){
        super(hoTen,diaChi,soDienThoai);
        this.maNhanVien = maNhanVien;
        this.dsCaLam = new ArrayList<>();
    }

    //==================================
    //Ca Làm
    //==================================
    public void themCaLam(CaLam ca){
        this.dsCaLam.add(ca);
    }

    public List<CaLam> getCaLam(){
        return dsCaLam;
    }
    ////////////////////////////////////

    public void setmaNhanVien(String maNhanVien){
        this.maNhanVien = maNhanVien;
    }
    public String getmaNhanVien(){
        return this.maNhanVien;
    }

    
    public abstract double tinhLuong();
    
    @Override 
    public void hienThiThongTin(){
        System.out.println("Ten nhan vien: "+getHoten()+" /Dia chi: "+getDiaChi()+" /So dien thoai: "+getsoDienThoai()+" /Ma nhan vien: "+getmaNhanVien());
    }
}
class NhanVienPartTime extends NhanVien{
    private int soGioLam;
    private double luongTheoGio;
    
    public NhanVienPartTime(){
        super();
    }
    public NhanVienPartTime(String hoTen,String diaChi,String soDienThoai,String maNhanVien,int soGioLam,double luongTheoGio){
        super(hoTen, diaChi, soDienThoai, maNhanVien);
        this.soGioLam = soGioLam;
        this.luongTheoGio = luongTheoGio;
    }

    public void setSoGio(int soGioLam){
        this.soGioLam = soGioLam;
    }
    public int getSoGioLam(){
        return this.soGioLam;
    }

    public void setLuongTheoGio(double luongTheoGio){
        this.luongTheoGio = luongTheoGio;
    }
    public double getLuongTheoGio(){
        return this.luongTheoGio;
    }

    @Override 
    public double tinhLuong(){
        return getSoGioLam()*getLuongTheoGio();
    }

    @Override
    public void hienThiThongTin(){
        System.out.print("Nhan vien: "+getHoten()+"Chuc vu: Part time "+"Ma nhan vien: "+getmaNhanVien()+" /Dia chi: "+getDiaChi()+" /So dien thoai: "+getsoDienThoai()+" /So gio lam mot ngay: "+getSoGioLam()+" /Luong theo gio: "+getLuongTheoGio()+" /Tong luong: "+tinhLuong());
    }
}

abstract class NhanVienFullTime extends NhanVien{
    private int soNgayCong;
    private double heSoLuong;

    public NhanVienFullTime(){
        super();        
    }

    public NhanVienFullTime(String hoTen,String diaChi,String soDienThoai,String maNhanVien,int soNgayCong,double heSoLuong){
        super(hoTen,diaChi,soDienThoai,maNhanVien);
        this.soNgayCong = soNgayCong;
        this.heSoLuong = heSoLuong;
    }

    public void setSoNgayCong(int soNgayCong){
        this.soNgayCong = soNgayCong;
    }
    public int getSoNgayCong(){
        return this.soNgayCong;
    }

    public void setHeSoLuong(double heSoLuong){
        this.heSoLuong = heSoLuong;
    }
    public double getHeSoLuong(){
        return this.heSoLuong;
    }
 
    public abstract double tinhLuong();

    @Override 
    public void hienThiThongTin(){
        System.out.println("Ma nhan vien: "+getmaNhanVien()+" /Ho ten: "+getHoten()+" /Dia chi: "+getDiaChi()+" /So dien thoai: "+getsoDienThoai()+" /So ngay cong: "+getSoNgayCong()+" /He so luong: "+getHeSoLuong());
    }
}


//============================
//Dùng cái này phân loại cho dễ nhìn chứ k có AI đâu:))
//==========THU NGÂN=============
//====================

class ThuNgan extends NhanVienFullTime{
    private String maQuay;

    public ThuNgan(){
        super();
    }
    public ThuNgan(String hoTen,String diaChi,String soDienThoai,String maNhanVien,int soNgayCong,double heSoLuong,String maQuay){
        super(hoTen,diaChi,soDienThoai,maNhanVien,soNgayCong,heSoLuong);
        this.maQuay = maQuay;
    }

    public void setMaQuay(String maQuay){
        this.maQuay = maQuay;
    }
    public String getMaQuay(){
        return this.maQuay;
    }


    @Override
    public double tinhLuong(){
        return luongCoBan*getHeSoLuong()*getSoNgayCong();
    }

    @Override 
    public void hienThiThongTin(){
        System.out.println("Ma nhan vien: "+getmaNhanVien()+" /Ho ten: "+getHoten()+" /Dia chỉ: "+getDiaChi()+" /So dien thoai: "+getsoDienThoai()+" /Ma quay: "+getMaQuay()+" /So ngay cong: "+getSoNgayCong()+" /He so luong: "+getHeSoLuong());
    }

    //============================================
    //Tinh tien chua co de bo vo code
    //===========================================
}
class QuanLy extends NhanVienFullTime{
    private double phuCap;

    //========================================
    //Quan ly Khach hang: Chua có
    //========================================

    //========================================
    //Quan ly san pham chua co
    //========================================

    //========================================
    //Quan ly don hang
    //========================================

    public QuanLy(){
        super();
    }
    public QuanLy(String hoTen,String diaChi,String soDienThoai,String maNhanVien,int soNgayCong,double heSoLuong,double phuCap){
        super(hoTen,diaChi,soDienThoai,maNhanVien,soNgayCong,heSoLuong);
        this.phuCap = phuCap;
    }

    public void setPhuCap(double phuCap){
        this.phuCap = phuCap;
    }
    public double getPhuCap(){
        return this.phuCap;
    }

    @Override 
    public double tinhLuong(){
        return (luongCoBan*getHeSoLuong())+phuCap;
    }

    @Override 
    public void hienThiThongTin(){
        System.out.println("Ma nhan vien: "+getmaNhanVien()+" /Ho ten: "+getHoten()+" /Dia chỉ: "+getDiaChi()+" /So dien thoai: "+getsoDienThoai()+ "/So ngay cong: "+getSoNgayCong()+" /He so luong: "+getHeSoLuong()+" /Phu cap: "+getPhuCap());
    }

    //========================================
    // +phanCongCa(nv: NhanVien, ca: CaLam): void
    // + quanLyKhachHang(): QuanLyKhachHang
    // + quanLySanPham(): QuanLySanPham
    // + quanLyDonHang(): QuanLyDonHang
    //====================================
}
class main{
        
    
    private Person[] dS;
    private int soLuong;

    public main(){
        this.dS = new Person[100];
        this.soLuong = 0;
    }

    public void themNhanSu(){
        if(soLuong>=dS.length){
            System.out.println("Da vuot qua gioi han nhan vien co the them!");
            return;
        }

        Scanner sc = new Scanner(System.in);
        System.out.println("\nDanh sach Cac chuc vu cho nhan vien moi");
        System.out.println("1. Nhan Vien Part-time");
        System.out.println("2. Nhan Vien Full-time");
        System.out.println("\nNhan vien moi thuoc chuc vu nao: ");
        int luaChon = Integer.parseInt(sc.nextLine());

        //Nhap cac thong tin ai cung co
        System.out.println("Nhap ho va ten: ");
        String hoTen = sc.nextLine();

        System.out.println("Nhap dia chi: ");
        String diaChi = sc.nextLine();

        System.out.println("Nhap so dien thoai: ");
        String soDienThoai = sc.nextLine();

        System.out.println("Nhap ma nhan vien: ");
        String maNhanVien = sc.nextLine();

        Person ps = null;
        
        //Chon khuon ,nhap cac gia tri doc quyen tuong ung
        switch(luaChon){
            case 1: 
                System.out.println("Nhap so gio lam: ");
                int soGioLam = Integer.parseInt(sc.nextLine());
                
                System.out.println("Nhap so luong theo gio: ");
                double luongTheoGio = Double.parseDouble(sc.nextLine());
                
                ps = new NhanVienPartTime(hoTen,diaChi,soDienThoai,maNhanVien,soGioLam,luongTheoGio);
                break;
            case 2:
                System.out.println("Chuc vu cong viec cu the la gi vay: ");
                System.out.println("1.Nhan vien Quan Ly");
                System.out.println("2.Nhan vien thu ngan");
                System.out.println("=================================================");
                System.out.println("Vui long dua ra lua chon (1 hoac 2): ");
                int lc = Integer.parseInt(sc.nextLine());
                
                
                System.out.println("Vui long nhap so ngay cong: ");
                int soNgayCong = Integer.parseInt(sc.nextLine());

                System.out.println("Vui long nhap he so luong: ");
                double heSoLuong = Double.parseDouble(sc.nextLine());

                if(lc == 1){
                    System.out.print("Nhap so tien phu cap: ");
                    double phuCap = Double.parseDouble(sc.nextLine());

                    ps = new QuanLy(hoTen,diaChi,soDienThoai,maNhanVien,soNgayCong,heSoLuong,phuCap);

                }else if(lc == 2){
                    System.out.print("Vui long nhap ma quay: ");
                    String maQuay = sc.nextLine();

                    ps = new ThuNgan(hoTen,diaChi,soDienThoai,maNhanVien,soNgayCong,heSoLuong,maQuay);
                }else{
                    System.out.println("Lua chon khong hop le! ");
                    return;
                }
                break;
            default:
                System.out.println("Lua chon chuc vu khong hop le!");
                return;
        }

        //Dua vao mang
        if(ps!=null){
            dS[soLuong]=ps;
            soLuong++;
            System.out.println("Success");
        }
    }

    public void inDanhSach(){
        if(soLuong == 0){
            System.out.println("Danh sach trong");
            return;
        }
        System.out.println("=============Danh Sach Nhan Luc Hien Co==============");
        for(int i = 0;i<soLuong;i++){//Length chua co ma sao hien duoc
            dS[i].hienThiThongTin();
        }
    }

    public static void main(String[] args) {
        main app = new main();
        Scanner sc = new Scanner(System.in);

        int chon;
        do{
            System.out.println("\n---Menu Quan Ly Nhan Su---");
            System.out.println("1.Them nhan su moi");
            System.out.println("2.Hien thi danh sach nhan su");
            System.out.println("0. Thoat");
            chon= Integer.parseInt(sc.nextLine());

            switch(chon){
                case 1:
                    app.themNhanSu();
                    break;
                case 2:
                    app.inDanhSach();
                    break;
                case 0:
                    System.out.println("Bye");
                    break;
            }
        }while(chon!=0);
    }
 }
