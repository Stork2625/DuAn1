package Repository;

import config.DBConnect;
import entity.San_Pham_CT_entity;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Repository_SanPhamCT {

    public ArrayList<San_Pham_CT_entity> getAll() {
        ArrayList<San_Pham_CT_entity> list = new ArrayList<>();
        String sql = "SELECT [IDSanPhamChiTiet]\n"
                + "      ,[TenSanPhamChiTiet]\n"
                + "      ,[IDChatLieu]\n"
                + "      ,[IDSanPham]\n"
                + "      ,[IDMauSac]\n"
                + "      ,[IDKhuyenMai]\n"
                + "      ,[IDKichThuoc]\n"
                + "      ,[IDLoaiKhoa]\n"
                + "      ,[IDKieuDay]\n"
                + "      ,[TrangThai]\n"
                + "      ,[GiaBan]\n"
                + "      ,[SoLuong]\n"
                + "  FROM [dbo].[SanPhamChiTiet]";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                San_Pham_CT_entity sp = San_Pham_CT_entity.builder()
                        .IDChiTiet(rs.getInt(1))
                        .TenChiTiet(rs.getString(2))
                        .IDSanPham(rs.getInt(3))
                        .IDChatLieu(rs.getInt(4))
                        .IDMauSac(rs.getInt(5))
                        .IDKhuyenMai(rs.getInt(7))
                        .IDKichThuoc(rs.getInt(7))
                        .IDLoaiKhoa(rs.getInt(8))
                        .IDKieuDay(rs.getInt(9))
                        .TrangThai(rs.getBoolean(10))
                        .giaBan(rs.getFloat(11))
                        .soLuong(rs.getInt(12))
                        .build();

                list.add(sp);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
