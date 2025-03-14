package entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author GIGABYTE
 */
//@ annotatio
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class San_Pham_CT_entity {

    private Integer IDChiTiet;
    private String TenChiTiet;
    private Integer IDSanPham;
    private Integer IDChatLieu;
    private Integer IDMauSac;
    private Integer IDKhuyenMai;
    private Integer IDKichThuoc;
    private Integer IDLoaiKhoa;
    private Integer IDKieuDay;
    private Boolean TrangThai;
    private Float giaBan;
    private Integer soLuong;
    
    
}
