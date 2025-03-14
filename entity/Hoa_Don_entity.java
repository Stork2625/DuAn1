
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
public class Hoa_Don_entity {
    private int ID;
    private int KhachHang;
    private int ID_Nhan_Vien;
    private int ID_Voucher;
    private String ngayMua;
    private Boolean TrangThai;
    private Float tong;
}
