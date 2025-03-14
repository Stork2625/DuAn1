
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
public class HDCT_entity {
    private Integer ID_HoaDonCT;
    private Integer IDSanPham;
    private String TenSanPham;
    private Integer IDHoaDon;
    private Integer soLuong;
    private Float DonGia;
}
