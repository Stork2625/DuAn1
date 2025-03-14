/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import config.DBConnect;
import entity.Chat_Lieu_entity;
import entity.Kieu_Day_entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author GIGABYTE
 */
public class Repository_KieuDay {
     public ArrayList<Kieu_Day_entity> getAll() {
        ArrayList<Kieu_Day_entity> list = new ArrayList<>();
        String sql = "select IDKieuDay,LoaiDay from KieuDay";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Kieu_Day_entity kd = Kieu_Day_entity.builder()
                        .id(rs.getInt(1))
                        .tenKieuDay(rs.getString(2))
                        .build();
                list.add(kd);
            }
        } catch (Exception e) {
        }
        return list;
    }
}
