
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
public class KhachHang_entity {
    private int Id;
    private String KhachHang;
    private int tuoi;
    private Boolean gioiTinh;
    private String diaChi;
    private String email;
    private Float tongTien;
}
