package Repository;

import config.DBConnect;
import entity.KhachHang_entity;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Repository_KhachHang {

    public ArrayList<KhachHang_entity> getAll() {
        ArrayList<KhachHang_entity> list = new ArrayList<>();
        String sql = "Select kh.IDKhachHang,kh.TenKhachHang,kh.Tuoi,kh.GioiTinh,kh.DiaChi,kh.Email,hd.TongGia from KhachHang kh\n"
                + "inner join HoaDon hd on hd.IDKhachHang = kh.IDKhachHang";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                KhachHang_entity kh = KhachHang_entity.builder()
                        .Id(rs.getInt(1))
                        .KhachHang(rs.getString(2))
                        .tuoi(rs.getInt(3))
                        .gioiTinh(rs.getBoolean(4))
                        .diaChi(rs.getString(5))
                        .email(rs.getString(6))
                        .tongTien(rs.getFloat(7))
                        .build();
                list.add(kh);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
