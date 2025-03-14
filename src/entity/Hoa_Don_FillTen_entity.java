
package entity;

import javax.swing.text.StyledEditorKit;
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
public class Hoa_Don_FillTen_entity {
    private int ID_HoaDon;
    private String Ten_KhachHang;
    private String Ten_NhanVien;
    private Float voucher;
    private String ngayMua;
    private Boolean trangThai;
    private Float tong;
}
