/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package giao_Dien;

import Repository.Repository_HDCT;
import Repository.Repository_HoaDon;
import Repository.Repository_HoaDonFillTen;
import Repository.Repository_KhachHang;
import Repository.Repository_SanPhamFIllTen;
import Repository.Repository_Voucher;
import config.DBConnect;
import entity.HDCT_entity;
import entity.Hoa_Don_FillTen_entity;
import entity.Hoa_Don_entity;
import entity.KhachHang_entity;
import entity.San_Pham_CT_FillTen_entity;
import entity.Voucher_entity;
import java.math.BigDecimal;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.EventObject;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

/**
 *
 * @author GIGABYTE
 */
public class Ban_Hang extends javax.swing.JInternalFrame {

    private Repository.Repository_SanPhamFIllTen spct;
    private DefaultTableModel dtm;
    private DefaultTableModel dtm2;
    private DefaultTableModel dtm3;
    private Repository_HoaDonFillTen hdft = new Repository_HoaDonFillTen();
    private Repository_HDCT hdct = new Repository_HDCT();
    private Repository_HoaDon hd = new Repository_HoaDon();
    private Repository_Voucher vc = new Repository_Voucher();
    private DefaultComboBoxModel cbvc;
    private Repository_KhachHang kh;
    private Trang_Chu tc = new Trang_Chu();

    /**
     * Creates new form Ban_Hang
     */
    public Ban_Hang() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        setBorder(BorderFactory.createEmptyBorder());
        dtm = (DefaultTableModel) tbl_bangCT.getModel();
        dtm2 = (DefaultTableModel) tbl_HoaDon.getModel();
        dtm3 = (DefaultTableModel) tbl_giohang.getModel();
        spct = new Repository_SanPhamFIllTen();
        hdft = new Repository_HoaDonFillTen();
        cbvc = (DefaultComboBoxModel) cbo_Voucher.getModel();
        ShowVoucher(vc.getAll());
        fillTable(spct.getAll());
        FillHoaDon(hdft.getAll());
        jcb_NgayMua.setEnabled(false);
    }

    public void ShowVoucher(ArrayList<Voucher_entity> danhSach) {
        cbvc.removeAllElements();
        for (Voucher_entity cl : danhSach) {
            cbvc.addElement(cl.getPhanTram());
        }
    }

    public void LoadVC() {
        ShowVoucher(vc.getAll());
    }

    private void fillTable(ArrayList<San_Pham_CT_FillTen_entity> list) {
        dtm.setRowCount(0);
        for (San_Pham_CT_FillTen_entity sqct : list) {
            if (sqct.getTrangThai()) {
                Object[] rowData = {
                    sqct.getIdChiTiet(),
                    sqct.getTenChiTiet(),
                    sqct.getTenSanPham(),
                    sqct.getTenChatLieu(),
                    sqct.getTenMauSac(),
                    sqct.getPhanTram(),
                    sqct.getTenKichThuoc(),
                    sqct.getTenLoaiKhoa(),
                    sqct.getTenKieuDay(),
                    "Còn hàng", // Trạng thái đã được kiểm tra ở điều kiện if
                    sqct.getGiaBan(),
                    sqct.getSoLuong()
                };
                // Thêm hàng vào bảng
                dtm.addRow(rowData);
            }
        }
    }

    public void loadData() {
        fillTable(spct.getAll());
    }

    private void FillHoaDon(ArrayList<Hoa_Don_FillTen_entity> list) {
        dtm2.setRowCount(0);
        for (Hoa_Don_FillTen_entity hdft : list) {
            Object[] rowdata = {
                hdft.getID_HoaDon(),
                hdft.getTen_KhachHang(),
                hdft.getTen_NhanVien(),
                hdft.getVoucher(),
                hdft.getNgayMua(),
                hdft.getTrangThai() ? "Đã thanh toán" : "Chưa thanh toán",
                hdft.getTong()
            };
            dtm2.addRow(rowdata);
        }
    }

    public int DoiVoucher() {
        int index = cbo_Voucher.getSelectedIndex();
        ArrayList<Voucher_entity> dsp = vc.getAll();
        Voucher_entity spDuocChon = dsp.get(index);
        return spDuocChon.getID();
    }

    public void ShowGioHang(ArrayList<HDCT_entity> list) {
        dtm3.setRowCount(0);
        for (HDCT_entity hdct : list) {
            dtm3.addRow(new Object[]{
                hdct.getIDHoaDon(),
                hdct.getIDSanPham(),
                hdct.getTenSanPham(),
                hdct.getSoLuong(),
                hdct.getDonGia()
            });
        }
    }

    private int getCustomerIdByName(String name) throws SQLException {
        String sql = "SELECT IDKhachHang FROM KhachHang WHERE TenKhachHang = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, name);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("IDKhachHang");
                } else {
                    System.out.println("Customer not found");
                    return -1;
                }
            }
        }
    }

    private void ThemKhachHang() {
        String sql = "INSERT INTO [dbo].[KhachHang] ([TenKhachHang], [Tuoi], [GioiTinh], [DiaChi], [Email]) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, txt_KhachHang.getText());

            // Nếu trường Tuổi có thể để trống, hãy sử dụng NULL nếu không có giá trị nhập vào
            try {
                ps.setInt(2, Integer.parseInt(txt_Tuoi.getText()));
            } catch (NumberFormatException e) {
                ps.setNull(2, java.sql.Types.INTEGER);
            }

            // Nếu trường GioiTinh có thể để trống, hãy sử dụng NULL nếu không có giá trị
            ps.setBoolean(3, rdo_Nam.isSelected()); // Hoặc ps.setNull(3, java.sql.Types.BOOLEAN);

            // Nếu trường DiaChi có thể để trống, hãy sử dụng NULL nếu không có giá trị nhập vào
            String diaChi = txt_DiaChi.getText().trim();
            if (diaChi.isEmpty()) {
                ps.setNull(4, java.sql.Types.VARCHAR);
            } else {
                ps.setString(4, diaChi);
            }

            // Nếu trường Email có thể để trống, hãy sử dụng NULL nếu không có giá trị nhập vào
            String email = txt_Email.getText().trim();
            if (email.isEmpty()) {
                ps.setNull(5, java.sql.Types.VARCHAR);
            } else {
                ps.setString(5, email);
            }

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void TaoHoaDon() {
        String sql = "INSERT INTO [dbo].[HoaDon] ([IDKhachHang], [IDNhanVien], [IDVoucher], [NgayMuaHang], [TrangThai], [TongGia]) VALUES (?,?,?,?,?,?)";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            // Check if customer name is empty
            String khachHangText = txt_KhachHang.getText().trim();
            int khachHangId = getCustomerIdByName(khachHangText);
            ps.setInt(1, khachHangId);
            int idNhanVien = Integer.parseInt(tc.getMaNhanVien().trim());
            ps.setInt(2, idNhanVien);
            ps.setInt(3, DoiVoucher());
            Date selectedDate = jcb_NgayMua.getDate();
            java.sql.Date sqlDate = new java.sql.Date(selectedDate.getTime());
            ps.setDate(4, sqlDate);
            ps.setInt(5, 0);
            ps.setFloat(6, 0);
            ps.executeUpdate();
            FillHoaDon(hdft.getAll());

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void ThemHDCT(HDCT_entity hdct) {
        String insertSql = "INSERT INTO [dbo].[HoaDonChiTiet] "
                + "([IDHoaDon], [IDSanPhamChiTiet], [SoLuong], [DonGia]) "
                + "VALUES (?, ?, ?, ?)";
        String updateSql = "UPDATE [dbo].[HoaDonChiTiet] SET SoLuong = SoLuong + ? WHERE IDHoaDon = ? AND IDSanPhamChiTiet = ?";
        String updateTotalSql = "UPDATE [dbo].[HoaDon] SET TongGia = ? WHERE IDHoaDon = ?";

        try (Connection con = DBConnect.getConnection()) {
            // Start a transaction
            con.setAutoCommit(false);

            // Check if the product already exists in the order details
            String checkSql = "SELECT COUNT(*) FROM HoaDonChiTiet WHERE IDHoaDon = ? AND IDSanPhamChiTiet = ?";
            try (PreparedStatement checkPs = con.prepareStatement(checkSql)) {
                checkPs.setInt(1, hdct.getIDHoaDon());
                checkPs.setInt(2, hdct.getIDSanPham());
                try (ResultSet checkRs = checkPs.executeQuery()) {
                    if (checkRs.next() && checkRs.getInt(1) > 0) {
                        // Product exists, update the quantity
                        try (PreparedStatement updatePs = con.prepareStatement(updateSql)) {
                            updatePs.setInt(1, hdct.getSoLuong());
                            updatePs.setInt(2, hdct.getIDHoaDon());
                            updatePs.setInt(3, hdct.getIDSanPham());
                            updatePs.executeUpdate();
                        }
                    } else {
                        // Product does not exist, insert new record
                        try (PreparedStatement insertPs = con.prepareStatement(insertSql)) {
                            insertPs.setInt(1, hdct.getIDHoaDon());
                            insertPs.setInt(2, hdct.getIDSanPham());
                            insertPs.setInt(3, hdct.getSoLuong());
                            insertPs.setFloat(4, hdct.getDonGia());
                            insertPs.executeUpdate();
                        }
                    }
                    float productTotal = hdct.getSoLuong() * hdct.getDonGia();
                    try (PreparedStatement updateTotalPs = con.prepareStatement(updateTotalSql)) {
                        updateTotalPs.setFloat(1, productTotal);
                        updateTotalPs.setInt(2, hdct.getIDHoaDon());
                        updateTotalPs.executeUpdate();
                    }

                    // Commit the transaction
                    con.commit();
                }
            } catch (SQLException e) {
                // Rollback the transaction if an error occurs
                con.rollback();
                e.printStackTrace();
            } finally {
                // Set auto-commit back to true
                con.setAutoCommit(true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SuaSoLuong(San_Pham_CT_FillTen_entity spct) {
        String sql = "UPDATE SanPhamChiTiet SET SoLuong = ? WHERE IDSanPhamChiTiet = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, spct.getSoLuong());
            ps.setInt(2, spct.getIdChiTiet());
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void thanhToan(Hoa_Don_FillTen_entity hdtt) {
        String sql = "update HoaDon set TrangThai = 1, TongGia = ? where IDHoaDon = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            Float tong = hdtt.getTong();
            if (tong == null) {
                JOptionPane.showMessageDialog(null, "Tổng tiền không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            ps.setFloat(1, tong);
            ps.setInt(2, hdtt.getID_HoaDon());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private float calculateTotalPrice(int idHoaDon) {
        float totalPrice = 0;
        // Fetch all product details for the given invoice ID
        List<HDCT_entity> products = hdct.getAll(idHoaDon);

        for (HDCT_entity product : products) {
            totalPrice += product.getDonGia() * product.getSoLuong();
        }

        return totalPrice;
    }

    public void showHoaDon(int index) {
        // Lấy thông tin hóa đơn từ danh sách
        Hoa_Don_FillTen_entity hds = hdft.getAll().get(index);
        txt_KhachHang.setText(hds.getTen_KhachHang());
        float total = hds.getTong(); // Tổng hóa đơn
        float voucher = hds.getVoucher(); // Giá trị voucher
        txt_TongTien.setText(String.valueOf(total));
        cbo_Voucher.setSelectedItem(voucher);
        float giaGiam = total - (total * voucher / 100);
        txt_GiaGiam.setText(String.valueOf(giaGiam));
    }

    public void TongTien(Hoa_Don_FillTen_entity hdTongTien) {
        String sql = "update HoaDon set TongGia = ? where IDHoaDon = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setFloat(1, hdTongTien.getTong());
            ps.setInt(2, hdTongTien.getID_HoaDon());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DoiSoLuongGioHang(HDCT_entity hdctDoiSoLuong) {
        String sql = " update HoaDonChiTiet set SoLuong = ? where IDSanPhamChiTiet =?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, hdctDoiSoLuong.getSoLuong());
            ps.setInt(2, hdctDoiSoLuong.getIDSanPham());
            ps.execute();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void HuyHoaDon(Hoa_Don_FillTen_entity hhd) {
        // Câu lệnh SQL
        String sqlSelectHDCT = "select IDSanPhamChiTiet, SoLuong from HoaDonChiTiet where IDHoaDon = ?";
        String sqlUpdateSanPham = "update SanPhamChiTiet set SoLuong = SoLuong + ? where IDSanPhamChiTiet = ?";
        String sqlDeleteHDCT = "delete from HoaDonChiTiet where IDHoaDon = ?";
        String sqlDeleteHoaDon = "delete from HoaDon where IDHoaDon = ?";

        try (Connection con = DBConnect.getConnection()) {
            con.setAutoCommit(false); // Bắt đầu giao dịch

            // Bước 1: Lấy thông tin chi tiết hóa đơn
            try (PreparedStatement psSelectHDCT = con.prepareStatement(sqlSelectHDCT)) {
                psSelectHDCT.setInt(1, hhd.getID_HoaDon());
                try (ResultSet rs = psSelectHDCT.executeQuery()) {
                    while (rs.next()) {
                        int idSanPham = rs.getInt("IDSanPhamChiTiet");
                        int soLuong = rs.getInt("SoLuong");

                        // Bước 2: Cập nhật lại số lượng sản phẩm
                        try (PreparedStatement psUpdateSanPham = con.prepareStatement(sqlUpdateSanPham)) {
                            psUpdateSanPham.setInt(1, soLuong);
                            psUpdateSanPham.setInt(2, idSanPham);
                            psUpdateSanPham.executeUpdate();
                        }
                    }
                }
            }

            // Bước 3: Xóa các chi tiết hóa đơn
            try (PreparedStatement psDeleteHDCT = con.prepareStatement(sqlDeleteHDCT)) {
                psDeleteHDCT.setInt(1, hhd.getID_HoaDon());
                psDeleteHDCT.executeUpdate();
            }

            // Bước 4: Xóa hóa đơn
            try (PreparedStatement psDeleteHoaDon = con.prepareStatement(sqlDeleteHoaDon)) {
                psDeleteHoaDon.setInt(1, hhd.getID_HoaDon());
                psDeleteHoaDon.executeUpdate();
            }

            con.commit(); // Cam kết giao dịch

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Hoa_Don_FillTen_entity getSelectedInvoiceDetails() {
        Hoa_Don_FillTen_entity invoice = new Hoa_Don_FillTen_entity();

        try {
            // Lấy chỉ số hàng được chọn trong bảng
            int selectedRow = tbl_HoaDon.getSelectedRow();

            // Kiểm tra nếu có hàng nào được chọn
            if (selectedRow != -1) {
                // Lấy giá trị ID hóa đơn từ cột đầu tiên của hàng được chọn
                int invoiceId = Integer.parseInt(tbl_HoaDon.getValueAt(selectedRow, 0).toString());

                // Thiết lập ID hóa đơn cho đối tượng
                invoice.setID_HoaDon(invoiceId);
            } else {
                // Nếu không có hàng nào được chọn, trả về null
                JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } catch (NumberFormatException e) {
            // Xử lý lỗi nếu không thể chuyển đổi giá trị thành số nguyên
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "ID hóa đơn không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return null;
        }
        return invoice;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_giohang = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_HoaDon = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_bangCT = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_KhachHang = new javax.swing.JTextField();
        txt_Email = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        cbo_Voucher = new javax.swing.JComboBox<>();
        btn_TaoHoaDon = new javax.swing.JButton();
        jLabel14 = new javax.swing.JLabel();
        rdo_Nam = new javax.swing.JRadioButton();
        rdo_Nữ = new javax.swing.JRadioButton();
        jLabel15 = new javax.swing.JLabel();
        txt_Tuoi = new javax.swing.JTextField();
        txt_DiaChi = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        cbo_KhachHang = new javax.swing.JButton();
        jcb_NgayMua = new de.wannawork.jcalendar.JCalendarComboBox();
        jPanel1 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        txt_TongTien = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txt_GiaGiam = new javax.swing.JTextField();
        txt_TienDua = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btn_ThanhToan = new javax.swing.JButton();

        jLabel2.setText("Giỏ hàng");

        tbl_giohang.setAutoCreateRowSorter(true);
        tbl_giohang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID hóa đơn", "ID sản phẩm", "Tên sản phẩm ", "Số lượng ", "Giá"
            }
        ));
        tbl_giohang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_giohangMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_giohang);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 112, Short.MAX_VALUE))
        );

        tbl_HoaDon.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã hóa đơn ", "Mã khách hàng", "Nhân viên", "Voucher", "Ngày mua", "Trạng thái", "Tổng"
            }
        ));
        tbl_HoaDon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_HoaDonMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_HoaDon);

        tbl_bangCT.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã sản phẩm", "Tên chi tiết", "Tên sản phẩm", "Tên chất liệu", "Tên màu sắc", "Phần trăm", "Kích thước", "Loại khóa", "Kiểu dây", "Trạng thái", "Giá bán", "Số lượng"
            }
        ));
        tbl_bangCT.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangCTMouseClicked(evt);
            }
            public void mousePressed(java.awt.event.MouseEvent evt) {
                tbl_bangCTMousePressed(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_bangCT);
        if (tbl_bangCT.getColumnModel().getColumnCount() > 0) {
            tbl_bangCT.getColumnModel().getColumn(10).setResizable(false);
        }

        jLabel1.setText("Sản phẩm ");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 641, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addGap(0, 7, Short.MAX_VALUE)
                .addComponent(jLabel1)
                .addGap(12, 12, 12)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jLabel3.setText("Hóa đơn chờ ");

        jPanel5.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel4.setText("Thông tin tạo hóa đơn:");

        jLabel5.setText("Tên khách hàng:");

        jLabel6.setText("Email:");

        jLabel7.setText("Voucher:");

        cbo_Voucher.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_Voucher.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_VoucherActionPerformed(evt);
            }
        });

        btn_TaoHoaDon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/bill.png"))); // NOI18N
        btn_TaoHoaDon.setText("Tạo hóa đơn ");
        btn_TaoHoaDon.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_TaoHoaDonActionPerformed(evt);
            }
        });

        jLabel14.setText("Giới tính:");

        buttonGroup1.add(rdo_Nam);
        rdo_Nam.setText("Nam");

        buttonGroup1.add(rdo_Nữ);
        rdo_Nữ.setText("Nữ");

        jLabel15.setText("Tuổi:");

        jLabel16.setText("Địa chỉ:");

        jButton1.setText("Hủy");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        cbo_KhachHang.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/user.png"))); // NOI18N
        cbo_KhachHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_KhachHangActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addComponent(btn_TaoHoaDon)
                                .addGap(29, 29, 29)
                                .addComponent(jButton1))
                            .addGroup(jPanel5Layout.createSequentialGroup()
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel14)
                                    .addComponent(jLabel15)
                                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jLabel7)
                                        .addComponent(jLabel16)))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel5Layout.createSequentialGroup()
                                        .addComponent(rdo_Nam)
                                        .addGap(18, 18, 18)
                                        .addComponent(rdo_Nữ))
                                    .addComponent(txt_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_Tuoi, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cbo_Voucher, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbo_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbo_KhachHang, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel5)
                        .addComponent(txt_KhachHang, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txt_Email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(21, 21, 21)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(rdo_Nam)
                    .addComponent(rdo_Nữ))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txt_Tuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(txt_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_Voucher, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_TaoHoaDon, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel8.setText("Tổng tiền:");

        jLabel9.setText("Giá giảm:");

        txt_GiaGiam.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_GiaGiamActionPerformed(evt);
            }
        });

        jLabel10.setText("Tiền khách đưa:");

        btn_ThanhToan.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/money.png"))); // NOI18N
        btn_ThanhToan.setText("Thanh toán");
        btn_ThanhToan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThanhToanActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel10)
                        .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.LEADING))
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txt_GiaGiam, javax.swing.GroupLayout.DEFAULT_SIZE, 136, Short.MAX_VALUE)
                    .addComponent(txt_TienDua)
                    .addComponent(txt_TongTien))
                .addContainerGap(106, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_ThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 156, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(20, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txt_TongTien, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txt_GiaGiam, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_TienDua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(34, 34, 34)
                .addComponent(btn_ThanhToan, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(47, 47, 47))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(24, 24, 24)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jcb_NgayMua, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(49, 49, 49)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(12, 12, 12)
                                .addComponent(jLabel3))
                            .addGroup(layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jcb_NgayMua, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_bangCTMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangCTMouseClicked
        try {
            // Nhập số lượng sản phẩm
            int soLuongSanPham = Integer.parseInt(JOptionPane.showInputDialog("Mời bạn nhập số lượng:"));
            if (soLuongSanPham <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0.");
                return;
            }

            // Lấy thông tin hóa đơn và sản phẩm
            int idHoaDon = Integer.parseInt(tbl_HoaDon.getValueAt(tbl_HoaDon.getSelectedRow(), 0).toString());
            int idSanPham = Integer.parseInt(tbl_bangCT.getValueAt(tbl_bangCT.getSelectedRow(), 0).toString());
            String tenSanPham = tbl_bangCT.getValueAt(tbl_bangCT.getSelectedRow(), 1).toString();
            float donGia = Float.parseFloat(tbl_bangCT.getValueAt(tbl_bangCT.getSelectedRow(), 10).toString());
            float khuyenMai = Float.parseFloat(tbl_bangCT.getValueAt(tbl_bangCT.getSelectedRow(), 5).toString());
            float gia = donGia - (donGia * khuyenMai) / 100;

            // Tạo đối tượng chi tiết hóa đơn
            HDCT_entity ct = HDCT_entity.builder()
                    .IDHoaDon(idHoaDon)
                    .IDSanPham(idSanPham)
                    .TenSanPham(tenSanPham)
                    .soLuong(soLuongSanPham)
                    .DonGia(gia)
                    .build();

            // Cập nhật số lượng sản phẩm
            int soLuongConLai = Integer.parseInt(tbl_bangCT.getValueAt(tbl_bangCT.getSelectedRow(), 11).toString()) - soLuongSanPham;
            if (soLuongConLai < 0) {
                JOptionPane.showMessageDialog(null, "Số lượng không đủ.");
                return;
            }

            // Thêm chi tiết hóa đơn và cập nhật số lượng sản phẩm
            ThemHDCT(ct);

            San_Pham_CT_FillTen_entity ctsp = San_Pham_CT_FillTen_entity.builder()
                    .SoLuong(soLuongConLai)
                    .IdChiTiet(idSanPham)
                    .build();
            SuaSoLuong(ctsp);

            // Cập nhật giao diện người dùng
            fillTable(spct.getAll());
            ShowGioHang(hdct.getAll(idHoaDon));

            // Cập nhật tổng giá vào bảng hóa đơn
            float totalPrice = calculateTotalPrice(idHoaDon);
            tbl_HoaDon.setValueAt(totalPrice, tbl_HoaDon.getSelectedRow(), 6);

            // Cập nhật tổng tiền vào cơ sở dữ liệu
            TongTien(Hoa_Don_FillTen_entity.builder()
                    .ID_HoaDon(idHoaDon)
                    .tong(totalPrice)
                    .build());

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dữ liệu đầu vào không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + e.getMessage());
        }

    }//GEN-LAST:event_tbl_bangCTMouseClicked

    private void tbl_bangCTMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangCTMousePressed

    }//GEN-LAST:event_tbl_bangCTMousePressed

    private void tbl_HoaDonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_HoaDonMouseClicked
        int idHoaDon = Integer.valueOf(tbl_HoaDon.getValueAt(tbl_HoaDon.getSelectedRow(), 0).toString());
        ShowGioHang(hdct.getAll(idHoaDon));
        int index = tbl_HoaDon.getSelectedRow();
        showHoaDon(index);
    }//GEN-LAST:event_tbl_HoaDonMouseClicked

    private void btn_TaoHoaDonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_TaoHoaDonActionPerformed
        ThemKhachHang();
        TaoHoaDon();
        int newRow = tbl_HoaDon.getRowCount() - 1;
        tbl_HoaDon.setRowSelectionInterval(tbl_HoaDon.getRowCount() - 1, tbl_HoaDon.getRowCount() - 1);
        tbl_HoaDon.scrollRectToVisible(tbl_HoaDon.getCellRect(newRow, 0, true));
    }//GEN-LAST:event_btn_TaoHoaDonActionPerformed

    private void cbo_VoucherActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_VoucherActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_VoucherActionPerformed

    private void btn_ThanhToanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThanhToanActionPerformed
        try {
            // Parse the input values
            float tienGiam = Float.parseFloat(txt_GiaGiam.getText());
            float tienKhachDua = Float.parseFloat(txt_TienDua.getText());
            if (tienKhachDua < 0) {
                JOptionPane.showMessageDialog(null, "Số tiền khách đưa không được âm.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            BigDecimal soTienThanhToan = new BigDecimal(tienKhachDua).subtract(new BigDecimal(tienGiam));
            if (soTienThanhToan.compareTo(BigDecimal.ZERO) < 0) {
                JOptionPane.showMessageDialog(null, "Số tiền khách đưa không đủ để thanh toán.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }
            JOptionPane.showMessageDialog(null, "Tiền thừa: " + soTienThanhToan, "Thông báo", JOptionPane.INFORMATION_MESSAGE);

            // Update the invoice
            int selectedRow = tbl_HoaDon.getSelectedRow();
            if (selectedRow != -1) {
                int idHoaDon = Integer.parseInt(tbl_HoaDon.getValueAt(selectedRow, 0).toString());
                Hoa_Don_FillTen_entity hdtt = Hoa_Don_FillTen_entity.builder()
                        .ID_HoaDon(idHoaDon)
                        .tong(tienGiam)
                        .build();
                thanhToan(hdtt);
                dtm3.setRowCount(0);
                FillHoaDon(hdft.getAll());
                txt_GiaGiam.setText("");
                txt_TienDua.setText("");
                txt_TongTien.setText("");
                cbo_Voucher.setSelectedIndex(-1);
            } else {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn hóa đơn để thanh toán.", "Thông báo", JOptionPane.WARNING_MESSAGE);
            }

        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Giá trị nhập vào không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btn_ThanhToanActionPerformed

    private void txt_GiaGiamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_GiaGiamActionPerformed

    }//GEN-LAST:event_txt_GiaGiamActionPerformed

    private void tbl_giohangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_giohangMouseClicked
        // TODO add your handling code here:
        try {
            // Nhận số lượng mới từ người dùng
            String input = JOptionPane.showInputDialog("Mời bạn nhập số lượng mới");
            if (input == null || input.trim().isEmpty()) {
                return;
            }
            int soLuongMoi = Integer.parseInt(input);
            if (soLuongMoi <= 0) {
                JOptionPane.showMessageDialog(null, "Số lượng phải lớn hơn 0");
                return;
            }

            int rowIndex = tbl_giohang.getSelectedRow();
            if (rowIndex < 0) {
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một sản phẩm trong giỏ hàng.");
                return;
            }

            // Lấy thông tin sản phẩm từ giỏ hàng
            Object idSanPhamObj = tbl_giohang.getValueAt(rowIndex, 1);
            Object tenSanPhamObj = tbl_giohang.getValueAt(rowIndex, 2);
            Object donGiaObj = tbl_giohang.getValueAt(rowIndex, 4);
            Object soLuongGioHangSanObj = tbl_giohang.getValueAt(rowIndex, 3);

            if (idSanPhamObj == null || tenSanPhamObj == null || donGiaObj == null || soLuongGioHangSanObj == null) {
                JOptionPane.showMessageDialog(null, "Dữ liệu từ bảng giỏ hàng không hợp lệ.");
                return;
            }
            int idHoaDon = Integer.parseInt(tbl_HoaDon.getValueAt(tbl_HoaDon.getSelectedRow(), 0).toString());
            int idSanPham = Integer.parseInt(idSanPhamObj.toString());
            String tenSanPham = tenSanPhamObj.toString();
            float donGia = Float.parseFloat(donGiaObj.toString());
            int soLuongGioHangSan = Integer.parseInt(soLuongGioHangSanObj.toString());

            // Tính giá sau khuyến mãi
            float khuyenMai = 0; // Nếu cần, lấy từ một nguồn dữ liệu khác
            float gia = donGia - (donGia * khuyenMai) / 100;

            // Cập nhật giỏ hàng
            HDCT_entity ct = HDCT_entity.builder()
                    .IDHoaDon(idHoaDon)
                    .IDSanPham(idSanPham)
                    .TenSanPham(tenSanPham)
                    .soLuong(soLuongMoi)
                    .DonGia(gia)
                    .build();
            DoiSoLuongGioHang(ct);

            // Cập nhật bảng chi tiết
            int soLuongConLai = 0; // Khai báo và khởi tạo biến
            for (int i = 0; i < tbl_bangCT.getRowCount(); i++) {
                if (Integer.parseInt(tbl_bangCT.getValueAt(i, 0).toString()) == idSanPham) {
                    int soLuongBangCT = Integer.parseInt(tbl_bangCT.getValueAt(i, 11).toString());
                    soLuongConLai = soLuongBangCT + (soLuongGioHangSan - soLuongMoi);

                    // Kiểm tra số lượng còn lại không âm
                    if (soLuongConLai < 0) {
                        JOptionPane.showMessageDialog(null, "Số lượng trong kho không đủ. Chỉ còn " + soLuongBangCT + " sản phẩm.");
                        return;
                    }

                    tbl_bangCT.setValueAt(soLuongConLai, i, 11);
                    break;
                }
            }

            // Cập nhật tồn kho
            San_Pham_CT_FillTen_entity cstp = San_Pham_CT_FillTen_entity.builder()
                    .SoLuong(soLuongConLai)
                    .IdChiTiet(idSanPham)
                    .build();
            SuaSoLuong(cstp);

            // Cập nhật số lượng trong bảng giỏ hàng
            tbl_giohang.setValueAt(soLuongMoi, rowIndex, 3);
            fillTable(spct.getAll());
            float totalPrice = calculateTotalPrice(idHoaDon);
            tbl_HoaDon.setValueAt(totalPrice, tbl_HoaDon.getSelectedRow(), 6);

            // Cập nhật tổng tiền vào cơ sở dữ liệu
            TongTien(Hoa_Don_FillTen_entity.builder()
                    .ID_HoaDon(idHoaDon)
                    .tong(totalPrice)
                    .build());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Dữ liệu đầu vào không hợp lệ: " + e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Đã xảy ra lỗi: " + e.getMessage());
        }
    }//GEN-LAST:event_tbl_giohangMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        Hoa_Don_FillTen_entity selectedInvoice = getSelectedInvoiceDetails();

        if (selectedInvoice != null) {
            HuyHoaDon(selectedInvoice);
            fillTable(spct.getAll());
            FillHoaDon(hdft.getAll());
            dtm3.setRowCount(0);
            txt_KhachHang.setText("");
            JOptionPane.showMessageDialog(this, "Hủy hóa đơn thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hóa đơn hợp lệ để hủy.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void cbo_KhachHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_KhachHangActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Tạo một đối tượng của cửa sổ Them_Khach_Hang
                Them_Khach_Hang kh = new Them_Khach_Hang();
                // Đặt hành động đóng cửa sổ là DISPOSE_ON_CLOSE
                kh.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                // Hiển thị cửa sổ
                kh.setVisible(true);
            }
        });
    }//GEN-LAST:event_cbo_KhachHangActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_TaoHoaDon;
    private javax.swing.JButton btn_ThanhToan;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cbo_KhachHang;
    private javax.swing.JComboBox<String> cbo_Voucher;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private de.wannawork.jcalendar.JCalendarComboBox jcb_NgayMua;
    private javax.swing.JRadioButton rdo_Nam;
    private javax.swing.JRadioButton rdo_Nữ;
    private javax.swing.JTable tbl_HoaDon;
    private javax.swing.JTable tbl_bangCT;
    private javax.swing.JTable tbl_giohang;
    private javax.swing.JTextField txt_DiaChi;
    private javax.swing.JTextField txt_Email;
    private javax.swing.JTextField txt_GiaGiam;
    private javax.swing.JTextField txt_KhachHang;
    private javax.swing.JTextField txt_TienDua;
    private javax.swing.JTextField txt_TongTien;
    private javax.swing.JTextField txt_Tuoi;
    // End of variables declaration//GEN-END:variables
}
