/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Repository;

import config.DBConnect;
import entity.Chat_Lieu_entity;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author GIGABYTE
 */
public class Repository_ChatLieu {

    public ArrayList<Chat_Lieu_entity> getAll() {
        ArrayList<Chat_Lieu_entity> list = new ArrayList<>();
        String sql = "select IDChatLieu,TenChatLieu from ChatLieu";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Chat_Lieu_entity cl = Chat_Lieu_entity.builder()
                        .id(rs.getInt("IDChatLieu"))
                        .tenChatLieu(rs.getString("TenChatLieu"))
                        .build();
                list.add(cl);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
