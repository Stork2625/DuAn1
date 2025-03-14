/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import config.DBConnect;
import entity.Size_entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author GIGABYTE
 */
public class Repository_Size {
    public ArrayList<Size_entity> getAll() {
        ArrayList<Size_entity> list = new ArrayList<>();
        String sql = "select IDKichThuoc,Size from KichThuoc";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Size_entity kd = Size_entity.builder()
                        .id(rs.getInt(1))
                        .size(rs.getString(2))
                        .build();
                list.add(kd);
            }
        } catch (Exception e) {
        }
        return list;
    }
}
