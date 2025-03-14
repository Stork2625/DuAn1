/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import config.DBConnect;
import entity.Mau_Sac_entity;
import entity.Size_entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author GIGABYTE
 */
public class Repository_MauSac {
    public ArrayList<Mau_Sac_entity> getAll() {
        ArrayList<Mau_Sac_entity> list = new ArrayList<>();
        String sql = "select IDMauSac,TenMau from MauSac";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Mau_Sac_entity ms = Mau_Sac_entity.builder()
                        .IDMauSac(rs.getInt(1))
                        .tenMau(rs.getString(2))
                        .build();
                list.add(ms);
            }
        } catch (Exception e) {
        }
        return list;
    }
}
