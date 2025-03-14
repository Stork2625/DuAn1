package Repository;

import config.DBConnect;
import entity.HDCT_entity;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Repository_HDCT {

    public ArrayList<HDCT_entity> getAll(int IDHoaDon) {
        ArrayList<HDCT_entity> list = new ArrayList<>();
        String sql = "select hdct.IDHoaDon,sqct.IDSanPhamChiTiet,sqct.TenSanPhamChiTiet,hdct.SoLuong,hdct.DonGia from HoaDonChiTiet hdct \n"
                + "inner join SanPhamChiTiet sqct on hdct.IDSanPhamChiTiet = sqct.IDSanPhamChiTiet\n"
                + "where hdct.IDHoaDon = ?";
        try(Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, IDHoaDon);
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                HDCT_entity hdct = HDCT_entity.builder()
                        .ID_HoaDonCT(rs.getInt(1))
                        .IDSanPham(rs.getInt(2))
                        .TenSanPham(rs.getString(3))
                        .soLuong(rs.getInt(4))
                        .DonGia(rs.getFloat(5))
                        .build();
                list.add(hdct);
            }
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
