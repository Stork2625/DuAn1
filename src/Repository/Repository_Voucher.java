/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import config.DBConnect;
import entity.Voucher_entity;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author GIGABYTE
 */
public class Repository_Voucher {

    public ArrayList<Voucher_entity> getAll() {
        ArrayList<Voucher_entity> list = new ArrayList<>();
        String sql = "SELECT [IDVoucher]\n"
                + "      ,[PhanTramGiamGia]\n"
                + "      ,[NgayBatDau]\n"
                + "      ,[NgayKetThuc]\n"
                + "      ,[TrangThai]\n"
                + "  FROM [dbo].[Voucher]";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Voucher_entity vc = Voucher_entity.builder()
                        .ID(rs.getInt(1))
                        .PhanTram(rs.getFloat(2))
                        .NgayBatDau(rs.getString(3))
                        .NgayKetThuc(rs.getString(4))
                        .TrangThai(rs.getBoolean(5))
                        .build();
                list.add(vc);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
