package Repository;

import config.DBConnect;
import entity.San_Pham_CT_FillTen_entity;
import java.awt.List;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Repository_SanPhamFIllTen {

    public ArrayList<San_Pham_CT_FillTen_entity> getAll() {
        ArrayList<San_Pham_CT_FillTen_entity> list = new ArrayList<>();
        String sql = "select sqct.IDSanPhamChiTiet,sqct.TenSanPhamChiTiet,sp.TenSanPham,cl.TenChatLieu,ms.TenMau,km.PhanTramKhuyenMai,kt.Size,lk.LoaiKhoa,kd.LoaiDay,sqct.TrangThai,sqct.GiaBan,sqct.SoLuong from SanPhamChiTiet sqct\n"
                + "left join SanPham sp on sqct.IDSanPham = sp.IDSanPham\n"
                + "left join ChatLieu cl on sqct.IDChatLieu = cl.IDChatLieu\n"
                + "left join MauSac ms on sqct.IDMauSac = ms.IDMauSac\n"
                + "left join KhuyenMai km on sqct.IDKhuyenMai = km.IDKhuyenMai\n"
                + "left join KichThuoc kt on sqct.IDKichThuoc = kt.IDKichThuoc\n"
                + "left join LoaiKhoa lk on sqct.IDLoaiKhoa = lk.IDLoaiKhoa\n"
                + "left join KieuDay kd on sqct.IDKieuDay = kd.IDKieuDay";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                San_Pham_CT_FillTen_entity sqct = San_Pham_CT_FillTen_entity.builder()
                        .IdChiTiet(rs.getInt(1))
                        .TenChiTiet(rs.getString(2))
                        .TenSanPham(rs.getString(3))
                        .TenChatLieu(rs.getString(4))
                        .TenMauSac(rs.getString(5))
                        .PhanTram(rs.getFloat(6))
                        .TenKichThuoc(rs.getString(7))
                        .TenLoaiKhoa(rs.getString(8))
                        .TenKieuDay(rs.getString(9))
                        .TrangThai(rs.getBoolean(10))
                        .GiaBan(rs.getFloat(11))
                        .SoLuong(rs.getInt(12))
                        .build();
                list.add(sqct);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
