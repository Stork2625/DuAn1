package Repository;

import config.DBConnect;
import entity.Hoa_Don_entity;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Repository_HoaDon {

    public ArrayList<Hoa_Don_entity> getAll() {
        ArrayList<Hoa_Don_entity> list = new ArrayList<>();
        String sql = "SELECT [IDHoaDon]\n"
                + "      ,[IDKhachHang]\n"
                + "      ,[IDNhanVien]\n"
                + "      ,[IDVoucher]\n"
                + "      ,[NgayMuaHang]\n"
                + "      ,[TrangThai]\n"
                + "      ,[TongGia]\n"
                + "  FROM [dbo].[HoaDon]";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Hoa_Don_entity hd = Hoa_Don_entity.builder()
                        .ID(rs.getInt(1))
                        .KhachHang(rs.getInt(2))
                        .ID_Nhan_Vien(rs.getInt(3))
                        .ID_Voucher(rs.getInt(4))
                        .ngayMua(rs.getString(5))
                        .TrangThai(rs.getBoolean(6))
                        .tong(rs.getFloat(7))
                        .build();
                list.add(hd);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
