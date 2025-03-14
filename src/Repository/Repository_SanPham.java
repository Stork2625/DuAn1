package Repository;

import config.DBConnect;
import entity.San_Pham_entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class Repository_SanPham {

    public ArrayList<San_Pham_entity> getAll() {
        ArrayList<San_Pham_entity> list = new ArrayList<>();
        String sql = "select IDSanPham,TenSanPham from SanPham";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                San_Pham_entity sp = San_Pham_entity.builder()
                        .IdSanPham(rs.getInt(1))
                        .tenSanPham(rs.getString(2))
                        .build();
                list.add(sp);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

}
