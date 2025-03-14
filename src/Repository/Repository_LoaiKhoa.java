/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import config.DBConnect;
import entity.Loai_Khoa_entity;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

/**
 *
 * @author GIGABYTE
 */
public class Repository_LoaiKhoa {
    public ArrayList<Loai_Khoa_entity> getAll() {
        ArrayList<Loai_Khoa_entity> list = new ArrayList<>();
        String sql = "select IDLoaiKhoa,LoaiKhoa from LoaiKhoa";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Loai_Khoa_entity lk = Loai_Khoa_entity.builder()
                        .id(rs.getInt(1))
                        .kieuKhoa(rs.getString(2))
                        .build();
                list.add(lk);
            }
        } catch (Exception e) {
        }
        return list;
    }
}
