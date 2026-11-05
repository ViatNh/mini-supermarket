import java.time.LocalDate;

public interface IBaoHanh 
{
    boolean kiemTraBaoHanh(LocalDate ngayMua);
    int layThoiHanBaoHanh();
}