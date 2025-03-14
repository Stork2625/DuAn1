package Repository;

import config.DBConnect;
import entity.Hoa_Don_FillTen_entity;
import java.util.ArrayList;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Repository_HoaDonFillTen {

    public ArrayList<Hoa_Don_FillTen_entity> getAll() {
        ArrayList<Hoa_Don_FillTen_entity> list = new ArrayList<>();
        String sql = "select hd.IDHoaDon,kh.TenKhachHang,nv.TenNhanVien,vc.PhanTramGiamGia,hd.NgayMuaHang,hd.TrangThai,hd.TongGia from HoaDon hd\n"
                + "inner join KhachHang kh on hd.IDKhachHang = kh.IDKhachHang\n"
                + "inner join NhanVien nv on hd.IDNhanVien = nv.IDNhanVien\n"
                + "inner join Voucher vc on hd.IDVoucher = vc.IDVoucher\n"
                + "where hd.TrangThai = 0;";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Hoa_Don_FillTen_entity hdft = Hoa_Don_FillTen_entity.builder()
                        .ID_HoaDon(rs.getInt(1))
                        .Ten_KhachHang(rs.getString(2))
                        .Ten_NhanVien(rs.getString(3))
                        .voucher(rs.getFloat(4))
                        .ngayMua(rs.getString(5))
                        .trangThai(rs.getBoolean(6))
                        .tong(rs.getFloat(7))
                        .build();
                list.add(hdft);
            }
        } catch (Exception e) {
        }
        return list;
    }

    public ArrayList<Hoa_Don_FillTen_entity> getAlll() {
        ArrayList<Hoa_Don_FillTen_entity> list = new ArrayList<>();
        String sql = "select hd.IDHoaDon, kh.TenKhachHang, nv.TenNhanVien, vc.PhanTramGiamGia, hd.NgayMuaHang, hd.TrangThai, hd.TongGia \n"
                + "from HoaDon hd\n"
                + "inner join KhachHang kh on hd.IDKhachHang = kh.IDKhachHang\n"
                + "inner join NhanVien nv on hd.IDNhanVien = nv.IDNhanVien\n"
                + "inner join Voucher vc on hd.IDVoucher = vc.IDVoucher;";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Hoa_Don_FillTen_entity hdft = Hoa_Don_FillTen_entity.builder()
                        .ID_HoaDon(rs.getInt(1))
                        .Ten_KhachHang(rs.getString(2))
                        .Ten_NhanVien(rs.getString(3))
                        .voucher(rs.getFloat(4))
                        .ngayMua(rs.getString(5))
                        .trangThai(rs.getBoolean(6))
                        .tong(rs.getFloat(7))
                        .build();
                list.add(hdft);
            }
        } catch (Exception e) {
        }
        return list;
    }
}
