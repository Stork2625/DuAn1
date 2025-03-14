package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class San_Pham_CT_FillTen_entity {

    private Integer IdChiTiet;
    private String TenChiTiet;
    private String TenSanPham;
    private String TenChatLieu;
    private String TenMauSac;
    private Float PhanTram;
    private String TenKichThuoc;
    private String TenLoaiKhoa;
    private String TenKieuDay;
    private Boolean TrangThai;
    private Float GiaBan;
    private Integer SoLuong;
}
