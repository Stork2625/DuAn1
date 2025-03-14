/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import config.DBConnect;
import entity.NhanVien_entity;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Repository_NhanVien {

    public ArrayList<NhanVien_entity> getAll() {
        ArrayList<NhanVien_entity> list = new ArrayList<>();
        String sql = "SELECT [IDNhanVien]\n"
                + "      ,[TenNhanVien]\n"
                + "      ,[Tuoi]\n"
                + "      ,[DiaChi]\n"
                + "      ,[GioiTinh]\n"
                + "      ,[ChucVu]\n"
                + "      ,[TaiKhoan]\n"
                + "      ,[MatKhau]\n"
                + "  FROM [dbo].[NhanVien]";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                NhanVien_entity nv = NhanVien_entity.builder()
                        .ID(rs.getInt(1))
                        .TenNV(rs.getString(2))
                        .Tuoi(rs.getInt(3))
                        .diaChi(rs.getString(4))
                        .gioiTinh(rs.getBoolean(5))
                        .chucVu(rs.getBoolean(6))
                        .taiKhoan(rs.getString(7))
                        .matKhau(rs.getString(8))
                        .build();
                list.add(nv);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
