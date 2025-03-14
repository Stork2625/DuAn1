/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import config.DBConnect;
import entity.Khuyen_Mai_entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author GIGABYTE
 */
public class Repository_KhuyenMai {
    public ArrayList<Khuyen_Mai_entity> getAll() {
        ArrayList<Khuyen_Mai_entity> list = new ArrayList<>();
        String sql = "select IDKhuyenMai,PhanTramKhuyenMai,NgayBatDau,NgayKetThuc,TrangThai from KhuyenMai";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Khuyen_Mai_entity km = Khuyen_Mai_entity.builder()
                        .id(rs.getInt(1))
                        .PhanTram(rs.getFloat(2))
                        .NgayBatDau(rs.getString(3))
                        .NgayKetThuc(rs.getString(4))
                        .TrangThai(rs.getBoolean(5))
                        .build();
                list.add(km);
            }
        } catch (Exception e) {
        }
        return list;
    }
}
