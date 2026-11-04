package sieuthi.model;

import java.time.LocalDate;

public interface IBaoHanh {
    boolean kiemTraBaoHanh(LocalDate ngayMua);

    /** Thời hạn bảo hành, tính theo THÁNG. */
    int layThoiHanBaoHanh();
}
