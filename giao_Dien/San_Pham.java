/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package giao_Dien;

import Repository.Repository_ChatLieu;
import Repository.Repository_KhuyenMai;
import Repository.Repository_KieuDay;
import Repository.Repository_LoaiKhoa;
import Repository.Repository_MauSac;
import Repository.Repository_SanPham;
import Repository.Repository_SanPhamCT;
import Repository.Repository_SanPhamFIllTen;
import Repository.Repository_Size;
import config.DBConnect;
import entity.Chat_Lieu_entity;
import entity.Khuyen_Mai_entity;
import entity.Kieu_Day_entity;
import entity.Loai_Khoa_entity;
import entity.Mau_Sac_entity;
import entity.San_Pham_CT_FillTen_entity;
import entity.San_Pham_CT_entity;
import entity.San_Pham_entity;
import entity.Size_entity;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import javax.swing.BorderFactory;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 *
 * @author GIGABYTE
 */
public class San_Pham extends javax.swing.JInternalFrame {

    private DefaultTableModel dtm;
    private DefaultTableModel dtm2;
    private Repository_SanPhamCT repo;
    private Repository_SanPhamFIllTen fullTen;
    private Repository_SanPham repo2;
    private Repository_ChatLieu cl = new Repository_ChatLieu();
    private Repository_Size sz = new Repository_Size();
    private Repository_KieuDay kd = new Repository_KieuDay();
    private Repository_LoaiKhoa lk = new Repository_LoaiKhoa();
    private Repository_MauSac ms = new Repository_MauSac();
    private Repository_KhuyenMai km = new Repository_KhuyenMai();
    private Repository_SanPham sp = new Repository_SanPham();
    private DefaultComboBoxModel cbcl;
    private DefaultComboBoxModel cbkm;
    private DefaultComboBoxModel cbkd;
    private DefaultComboBoxModel cblk;
    private DefaultComboBoxModel cbsp;
    private DefaultComboBoxModel cbms;
    private DefaultComboBoxModel cbsz;

    /**
     * Creates new form San_Pham
     */
    public San_Pham() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        setBorder(BorderFactory.createEmptyBorder());
        dtm = (DefaultTableModel) tbl_ChiTiet.getModel();
        dtm2 = (DefaultTableModel) tbl_SanPham.getModel();
        repo = new Repository_SanPhamCT();
        repo2 = new Repository_SanPham();
        fullTen = new Repository_SanPhamFIllTen();
        cbcl = (DefaultComboBoxModel) cbo_ChatLieu.getModel();
        cbkm = (DefaultComboBoxModel) cbo_KhuyenMai.getModel();
        cbkd = (DefaultComboBoxModel) cbo_KieuDay.getModel();
        cblk = (DefaultComboBoxModel) cbo_LoaiKhoa.getModel();
        cbsp = (DefaultComboBoxModel) cbo_SanPham.getModel();
        cbms = (DefaultComboBoxModel) cbo_MauSac.getModel();
        cbsz = (DefaultComboBoxModel) cbo_KichThuoc.getModel();
        fillTable(fullTen.getAll());
        fillTableSanPham(repo2.getAll());
        ShowChatLieu(cl.getAll());
        ShowKhuyenMai(km.getAll());
        ShowKieuDay(kd.getAll());
        ShowLoaiKhoa(lk.getAll());
        ShowSanPham(sp.getAll());
        ShowMauSac(ms.getAll());
        ShowKichThuoc(sz.getAll());
        rdo_TimHetHang.addActionListener(e -> timKiemSanPham());
        rdo_TimConHang.addActionListener(e -> timKiemSanPham());
        txt_TimKiem.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                TimText();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                TimText();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                // Not needed for plain text changes
            }
        });


    }

    private void fillTable(ArrayList<San_Pham_CT_FillTen_entity> list) {
        dtm.setRowCount(0);
        for (San_Pham_CT_FillTen_entity sqct : list) {
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
                sqct.getTrangThai() ? "Còn hàng" : "Hết hàng",
                sqct.getGiaBan(),
                sqct.getSoLuong(),};
            dtm.addRow(rowData);
        }
    }

    private void ShowChatLieu(ArrayList<Chat_Lieu_entity> danhSach) {
        cbcl.removeAllElements();
        for (Chat_Lieu_entity cl : danhSach) {
            cbcl.addElement(cl.getTenChatLieu());
        }
    }

    private void ShowKhuyenMai(ArrayList<Khuyen_Mai_entity> danhSach) {
        cbkm.removeAllElements();
        for (Khuyen_Mai_entity km : danhSach) {
            cbkm.addElement(km.getPhanTram());
        }
    }

    private void ShowKieuDay(ArrayList<Kieu_Day_entity> danhSach) {
        cbkd.removeAllElements();
        for (Kieu_Day_entity kd : danhSach) {
            cbkd.addElement(kd.getTenKieuDay());
        }
    }

    private void ShowLoaiKhoa(ArrayList<Loai_Khoa_entity> danhSach) {
        cblk.removeAllElements();
        for (Loai_Khoa_entity lk : danhSach) {
            cblk.addElement(lk.getKieuKhoa());
        }
    }

    private void ShowSanPham(ArrayList<San_Pham_entity> danhSach) {
        cbsp.removeAllElements();
        for (San_Pham_entity sp : danhSach) {
            cbsp.addElement(sp.getTenSanPham());
        }
    }

    private void ShowMauSac(ArrayList<Mau_Sac_entity> danhSach) {
        cbms.removeAllElements();
        for (Mau_Sac_entity ms : danhSach) {
            cbms.addElement(ms.getTenMau());
        }
    }

    private void ShowKichThuoc(ArrayList<Size_entity> danhSach) {
        cbsz.removeAllElements();
        for (Size_entity sz : danhSach) {
            cbsz.addElement(sz.getSize());
        }
    }

    private void fillTableSanPham(ArrayList<San_Pham_entity> list) {
        dtm2.setRowCount(0);
        for (San_Pham_entity sp : list) {
            Object[] rowData = {
                sp.getIdSanPham(),
                sp.getTenSanPham()
            };
            dtm2.addRow(rowData);
        }
    }

    private void updateCboSanPham() {
        cbo_SanPham.removeAllItems();
        for (San_Pham_entity sp : sp.getAll()) {
            cbo_SanPham.addItem(sp.getTenSanPham());
        }
    }

    private void updateCboChatLieu() {
        cbo_ChatLieu.removeAllItems();
        for (Chat_Lieu_entity cl : cl.getAll()) {
            cbo_ChatLieu.addItem(cl.getTenChatLieu());
        }
    }

    private void updateCboMauSac() {
        cbo_MauSac.removeAllItems();
        for (Mau_Sac_entity ms : ms.getAll()) {
            cbo_MauSac.addItem(ms.getTenMau());
        }
    }

    private void updateCboLoaiKhoa() {
        cbo_LoaiKhoa.removeAllItems();
        for (Loai_Khoa_entity lk : lk.getAll()) {
            cbo_LoaiKhoa.addItem(lk.getKieuKhoa());
        }
    }

    private void updateCboKieuDay() {
        cbo_KieuDay.removeAllItems();
        for (Kieu_Day_entity kd : kd.getAll()) {
            cbo_KieuDay.addItem(kd.getTenKieuDay());
        }
    }

    private void updateKichThuoc() {
        cbo_KichThuoc.removeAllItems();
        for (Size_entity sz : sz.getAll()) {
            cbo_KichThuoc.addItem(sz.getSize());
        }
    }

    private void updateCboKhuyenMai() {
        cbo_KhuyenMai.removeAllItems();
        for (Khuyen_Mai_entity km : km.getAll()) {
            cbo_KhuyenMai.addItem(String.valueOf(km.getPhanTram()));
        }
    }

    public void LoadKM() {
        updateCboKhuyenMai();
    }
    public void LoadSP(){
        fillTable(fullTen.getAll());
    }

    private void setSelectedItem(JComboBox<String> comboBox, String valueOf) {
        for (int i = 0; i < comboBox.getItemCount(); i++) {
            if (comboBox.getItemAt(i).equals(valueOf)) {
                comboBox.setSelectedIndex(i);
                return;
            }
        }
        System.out.println("Giá trị không tồn tại trong combobox: " + valueOf);
    }

    private void detail(int index) {
        San_Pham_CT_FillTen_entity sp = fullTen.getAll().get(index);
        txt_ChiTiet.setText(String.valueOf(sp.getIdChiTiet()));
        txt_TenChiTiet.setText(sp.getTenChiTiet());
        cbo_SanPham.setSelectedItem(sp.getTenSanPham());
        cbo_ChatLieu.setSelectedItem(sp.getTenChatLieu());
        cbo_MauSac.setSelectedItem(sp.getTenMauSac());
        cbo_KhuyenMai.setSelectedItem(sp.getPhanTram());
        cbo_KichThuoc.setSelectedItem(sp.getTenKichThuoc());
        cbo_LoaiKhoa.setSelectedItem(sp.getTenLoaiKhoa());
        cbo_KieuDay.setSelectedItem(sp.getTenKieuDay());
        txt_GiaBan.setText(String.valueOf(sp.getGiaBan()));
        txt_SoLuong.setText(String.valueOf(sp.getSoLuong()));
        boolean trangThai = sp.getTrangThai();
        rdo_ConHang.setSelected(trangThai);
        rdo_HetHang.setSelected(!trangThai);
    }

    public int DoiSanPham() {
        int index = cbo_SanPham.getSelectedIndex();
        ArrayList<San_Pham_entity> dsp = repo2.getAll();
        San_Pham_entity spDuocChon = dsp.get(index);
        return spDuocChon.getIdSanPham();
    }

    public int DoiChatLieu() {
        int index = cbo_ChatLieu.getSelectedIndex();
        ArrayList<Chat_Lieu_entity> dcl = cl.getAll();
        Chat_Lieu_entity clDuocChon = dcl.get(index);
        return clDuocChon.getId();
    }

    public int DoiMauSac() {
        int index = cbo_MauSac.getSelectedIndex();
        ArrayList<Mau_Sac_entity> dms = ms.getAll();
        Mau_Sac_entity msDuocChon = dms.get(index);
        return msDuocChon.getIDMauSac();
    }

    public int DoiKhuyenMai() {
        int index = cbo_KhuyenMai.getSelectedIndex();
        ArrayList<Khuyen_Mai_entity> dkm = km.getAll();
        Khuyen_Mai_entity kmDuocChon = dkm.get(index);
        return kmDuocChon.getId();
    }

    public int DoiLoaiKhoa() {
        int index = cbo_LoaiKhoa.getSelectedIndex();
        ArrayList<Loai_Khoa_entity> dlk = lk.getAll();
        Loai_Khoa_entity lkDuocChon = dlk.get(index);
        return lkDuocChon.getId();
    }

    public int DoiKieuDay() {
        int index = cbo_KieuDay.getSelectedIndex();
        ArrayList<Kieu_Day_entity> dkd = kd.getAll();
        Kieu_Day_entity kdDuocChon = dkd.get(index);
        return kdDuocChon.getId();
    }

    public int DoiKichThuoc() {
        int index = cbo_KichThuoc.getSelectedIndex();
        ArrayList<Size_entity> dsz = sz.getAll();
        Size_entity szDuocChon = dsz.get(index);
        return szDuocChon.getId();
    }

    private void detail2(int index) {
        San_Pham_entity sp = repo2.getAll().get(index);
        txt_IDSanPham.setText(String.valueOf(sp.getIdSanPham()));
        txt_TenSanPham.setText(sp.getTenSanPham());
    }

   private void deleteRecord(int id) {
    String xoaHDCT = "DELETE FROM HoaDonChiTiet WHERE IDSanPhamChiTiet = ?";
    String xoaSanPham = "DELETE FROM SanPhamChiTiet WHERE IDSanPhamChiTiet = ?";
    
    try (Connection con = DBConnect.getConnection()) {
        con.setAutoCommit(false); // Bắt đầu giao dịch

        // Xóa chi tiết hóa đơn
        try (PreparedStatement psHDCT = con.prepareStatement(xoaHDCT)) {
            psHDCT.setInt(1, id);
            psHDCT.executeUpdate();
        }

        // Xóa sản phẩm chi tiết
        try (PreparedStatement psSanPham = con.prepareStatement(xoaSanPham)) {
            psSanPham.setInt(1, id);
            psSanPham.executeUpdate();
        }

        con.commit(); // Cam kết giao dịch
        JOptionPane.showMessageDialog(this, "Xóa thành công.");
        fillTable(fullTen.getAll());
    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xóa dữ liệu.", "Lỗi", JOptionPane.ERROR_MESSAGE);
        // Có thể thêm logic để hoàn tác giao dịch nếu cần
    }
}

    private void clear() {
        txt_IDSanPham.setText("");
        txt_TenSanPham.setText("");
    }

    private void clearCT() {
        txt_ChiTiet.setText("");
        txt_TenChiTiet.setText("");
        cbo_SanPham.setSelectedIndex(0);
        cbo_ChatLieu.setSelectedIndex(0);
        cbo_MauSac.setSelectedIndex(0);
        cbo_KhuyenMai.setSelectedIndex(0);
        cbo_KichThuoc.setSelectedIndex(0);
        cbo_LoaiKhoa.setSelectedIndex(0);
        cbo_KieuDay.setSelectedIndex(0);
        txt_SoLuong.setText("");
        txt_GiaBan.setText("");
        rdo_ConHang.setSelected(true);

    }

    private void timKiemSanPham() {
        ArrayList<San_Pham_CT_FillTen_entity> kiemTra = new ArrayList<>();
        boolean checkHetHang = rdo_TimHetHang.isSelected();
        boolean checkConHang = rdo_TimConHang.isSelected();
        if (!checkHetHang && !checkConHang) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn trạng thái sản phẩm để tìm kiếm.");
            return;
        }
        for (San_Pham_CT_FillTen_entity sp : fullTen.getAll()) {
            boolean trangThai = sp.getTrangThai();
            if (checkHetHang && !trangThai) {
                kiemTra.add(sp);
            }
            if (checkConHang && trangThai) {
                kiemTra.add(sp);
            }
        }

        fillTable(kiemTra);
    }

    private void TimText() {
    ArrayList<San_Pham_CT_FillTen_entity> kiemTra = new ArrayList<>();
    String timTen = txt_TimKiem.getText().trim().toLowerCase();

    for (San_Pham_CT_FillTen_entity sp : fullTen.getAll()) {  
        if (sp.getTenSanPham().toLowerCase().contains(timTen)) {
            kiemTra.add(sp);
        }
    }

    // Update the table with filtered results
    updateTable(kiemTra);
}
private void updateTable(ArrayList<San_Pham_CT_FillTen_entity> list) {
    DefaultTableModel model = (DefaultTableModel) tbl_ChiTiet.getModel();  // Your JTable model
    model.setRowCount(0);  // Clear existing rows

    for (San_Pham_CT_FillTen_entity sqct : list) {
        model.addRow(new Object[]{
            sqct.getIdChiTiet(),
                sqct.getTenChiTiet(),
                sqct.getTenSanPham(),
                sqct.getTenChatLieu(),
                sqct.getTenMauSac(),
                sqct.getPhanTram(),
                sqct.getTenKichThuoc(),
                sqct.getTenLoaiKhoa(),
                sqct.getTenKieuDay(),
                sqct.getTrangThai() ? "Còn hàng" : "Hết hàng",
                sqct.getGiaBan(),
                sqct.getSoLuong(),
        });
    }
}


    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenu2 = new javax.swing.JMenu();
        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jTabbedPane2 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tbl_SanPham = new javax.swing.JTable();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txt_IDSanPham = new javax.swing.JTextField();
        txt_TenSanPham = new javax.swing.JTextField();
        btn_ThemSP = new javax.swing.JButton();
        btn_SuaSP = new javax.swing.JButton();
        btn_XoaSP = new javax.swing.JButton();
        btn_MoiSP = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_ChiTiet = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_ChiTiet = new javax.swing.JTextField();
        cbo_ChatLieu = new javax.swing.JComboBox<>();
        cbo_MauSac = new javax.swing.JComboBox<>();
        cbo_KhuyenMai = new javax.swing.JComboBox<>();
        cbo_KichThuoc = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        cbo_LoaiKhoa = new javax.swing.JComboBox<>();
        cbo_KieuDay = new javax.swing.JComboBox<>();
        txt_GiaBan = new javax.swing.JTextField();
        txt_SoLuong = new javax.swing.JTextField();
        btn_ThemCT = new javax.swing.JButton();
        txt_SuaCT = new javax.swing.JButton();
        btn_XoaCT = new javax.swing.JButton();
        btn_MoiCT = new javax.swing.JButton();
        cbo_SanPham = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        txt_TenChiTiet = new javax.swing.JTextField();
        rdo_ConHang = new javax.swing.JRadioButton();
        rdo_HetHang = new javax.swing.JRadioButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        rdo_TimConHang = new javax.swing.JRadioButton();
        rdo_TimHetHang = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        txt_TimKiem = new javax.swing.JTextField();

        jMenu1.setText("jMenu1");

        jMenu2.setText("jMenu2");

        tbl_SanPham.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "ID sản phẩm", "Tên sản phẩm "
            }
        ));
        tbl_SanPham.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_SanPhamMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tbl_SanPham);

        jLabel12.setText("ID sản phẩm:");

        jLabel13.setText("Tên sản phẩm:");

        txt_IDSanPham.setEditable(false);

        btn_ThemSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/them.png"))); // NOI18N
        btn_ThemSP.setText("Thêm");
        btn_ThemSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemSPActionPerformed(evt);
            }
        });

        btn_SuaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/tay.png"))); // NOI18N
        btn_SuaSP.setText("Sửa ");
        btn_SuaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SuaSPActionPerformed(evt);
            }
        });

        btn_XoaSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/trash.png"))); // NOI18N
        btn_XoaSP.setText("Xóa");
        btn_XoaSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaSPActionPerformed(evt);
            }
        });

        btn_MoiSP.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/refresh.png"))); // NOI18N
        btn_MoiSP.setText("Mới");
        btn_MoiSP.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_MoiSPActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 1065, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(214, 214, 214)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12)
                                    .addComponent(jLabel13))
                                .addGap(81, 81, 81)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_IDSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_TenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(527, 527, 527)
                                        .addComponent(btn_XoaSP))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(260, 260, 260)
                                        .addComponent(btn_ThemSP)
                                        .addGap(62, 62, 62)
                                        .addComponent(btn_SuaSP)))
                                .addGap(62, 62, 62)
                                .addComponent(btn_MoiSP)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 196, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(txt_IDSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(txt_TenSanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 61, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_ThemSP, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_SuaSP)
                    .addComponent(btn_XoaSP)
                    .addComponent(btn_MoiSP))
                .addGap(153, 153, 153))
        );

        jTabbedPane2.addTab("Sản Phẩm", jPanel1);

        tbl_ChiTiet.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID Sản phẩm chi tiết", "Tên chi tiết", "Tên sản phẩm", "Tên chất liệu", "Tên màu sắc ", "Phần trăm", "Kích thước", "Loại khóa", "Kiểu dây", "Trạng thái", "Giá bán", "Số lượng"
            }
        ));
        tbl_ChiTiet.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ChiTietMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_ChiTiet);

        jLabel1.setText("ID sản phẩm chi tiết:");

        jLabel2.setText("Tên sản phẩm:");

        jLabel3.setText("Tên chất liệu:");

        jLabel4.setText("Màu sắc:");

        jLabel5.setText("Phầm trăm giảm:");

        jLabel6.setText("Kích thước:");

        txt_ChiTiet.setEditable(false);

        cbo_ChatLieu.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_MauSac.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_KhuyenMai.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_KichThuoc.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel7.setText("Loại khóa:");

        jLabel8.setText("Kiểu dây:");

        jLabel9.setText("Trạng thái:");

        jLabel10.setText("Giá bán:");

        jLabel11.setText("Số lượng:");

        cbo_LoaiKhoa.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbo_KieuDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        btn_ThemCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/them.png"))); // NOI18N
        btn_ThemCT.setText("Thêm");
        btn_ThemCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ThemCTActionPerformed(evt);
            }
        });

        txt_SuaCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/tay.png"))); // NOI18N
        txt_SuaCT.setText("Sửa");
        txt_SuaCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_SuaCTActionPerformed(evt);
            }
        });

        btn_XoaCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/trash.png"))); // NOI18N
        btn_XoaCT.setText("Xóa");
        btn_XoaCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_XoaCTActionPerformed(evt);
            }
        });

        btn_MoiCT.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/refresh.png"))); // NOI18N
        btn_MoiCT.setText("Mới");
        btn_MoiCT.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_MoiCTActionPerformed(evt);
            }
        });

        cbo_SanPham.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbo_SanPham.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cbo_SanPhamActionPerformed(evt);
            }
        });

        jLabel14.setText("Tên chi tiết:");

        buttonGroup1.add(rdo_ConHang);
        rdo_ConHang.setText("Còn hàng");

        buttonGroup1.add(rdo_HetHang);
        rdo_HetHang.setText("Hết hàng");

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/them.png"))); // NOI18N
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/them.png"))); // NOI18N
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton10.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/them.png"))); // NOI18N
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });

        jButton11.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/them.png"))); // NOI18N
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });

        jButton12.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/them.png"))); // NOI18N
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Tìm sản phẩm", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 1, 14), new java.awt.Color(255, 0, 51))); // NOI18N

        buttonGroup2.add(rdo_TimConHang);
        rdo_TimConHang.setText("Còn hàng");
        rdo_TimConHang.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rdo_TimConHangActionPerformed(evt);
            }
        });

        buttonGroup2.add(rdo_TimHetHang);
        rdo_TimHetHang.setText("Hết hàng");

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/refresh.png"))); // NOI18N
        jButton1.setText("Làm mới");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap(187, Short.MAX_VALUE)
                .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(151, 151, 151)
                .addComponent(rdo_TimConHang)
                .addGap(32, 32, 32)
                .addComponent(rdo_TimHetHang, javax.swing.GroupLayout.PREFERRED_SIZE, 82, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(100, 100, 100)
                .addComponent(jButton1)
                .addGap(70, 70, 70))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt_TimKiem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(rdo_TimHetHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(rdo_TimConHang, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5)
                        .addComponent(jLabel4)
                        .addComponent(jLabel1)
                        .addComponent(jLabel2)
                        .addComponent(jLabel14))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(47, 47, 47)
                        .addComponent(jLabel3)))
                .addGap(57, 57, 57)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_ChiTiet, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_TenChiTiet, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(cbo_KhuyenMai, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(cbo_MauSac, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                            .addComponent(cbo_ChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addComponent(cbo_SanPham, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 94, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(rdo_ConHang)
                            .addGap(18, 18, 18)
                            .addComponent(rdo_HetHang))
                        .addComponent(txt_GiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(cbo_KichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(cbo_KieuDay, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(cbo_LoaiKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, 199, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jButton10))))
                .addGap(18, 24, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btn_XoaCT)
                            .addComponent(btn_MoiCT))
                        .addGap(46, 46, 46))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(btn_ThemCT, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_SuaCT))
                        .addGap(45, 45, 45))))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(txt_ChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7)
                        .addComponent(cbo_LoaiKhoa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton10))
                    .addComponent(btn_ThemCT))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(jLabel8)
                            .addComponent(cbo_KieuDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cbo_SanPham, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel14)
                                .addComponent(txt_TenChiTiet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(cbo_KichThuoc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton12))
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButton7)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(cbo_ChatLieu, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel9)
                                .addComponent(rdo_ConHang)
                                .addComponent(rdo_HetHang)))
                        .addGap(18, 18, 18))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(txt_SuaCT, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_XoaCT, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(31, 31, 31)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_MauSac, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(txt_GiaBan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(jButton8)
                    .addComponent(btn_MoiCT, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbo_KhuyenMai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(txt_SoLuong, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        jTabbedPane2.addTab("Sản phẩm chi tiết", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 1077, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane2)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btn_MoiSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MoiSPActionPerformed
        // TODO add your handling code here:
        clear();
    }//GEN-LAST:event_btn_MoiSPActionPerformed

    private void btn_XoaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaSPActionPerformed
        // TODO add your handling code here:
        String sql = "delete from SanPham where IDSanPham = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            int idSanPham = Integer.parseInt(txt_IDSanPham.getText());
            ps.setInt(1, idSanPham);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            updateCboSanPham();
            fillTableSanPham(repo2.getAll());
            clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_XoaSPActionPerformed

    private void btn_SuaSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SuaSPActionPerformed
        String updateSql = "UPDATE [dbo].[SanPham]\n"
                + "   SET [TenSanPham] = ?\n"
                + " WHERE IDSanPham = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(updateSql)) {
            ps.setString(1, txt_TenSanPham.getText());
            ps.setInt(2, Integer.parseInt(txt_IDSanPham.getText()));
            ps.executeUpdate();
            updateCboSanPham();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
            fillTableSanPham(repo2.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_SuaSPActionPerformed

    private void btn_ThemSPActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemSPActionPerformed
        // TODO add your handling code here:
        String sql = "INSERT INTO [dbo].[SanPham]\n"
                + "           ([TenSanPham])\n"
                + "     VALUES\n"
                + "           (?)";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, txt_TenSanPham.getText());
            ps.executeUpdate();
            updateCboSanPham();
            fillTableSanPham(repo2.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_ThemSPActionPerformed

    private void tbl_SanPhamMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_SanPhamMouseClicked
        // TODO add your handling code here:
        detail2(tbl_SanPham.getSelectedRow());
    }//GEN-LAST:event_tbl_SanPhamMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        fillTable(fullTen.getAll());
        buttonGroup2.clearSelection();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void rdo_TimConHangActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rdo_TimConHangActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_rdo_TimConHangActionPerformed

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Them_Kich_Thuoc kt = new Them_Kich_Thuoc();
                kt.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                kt.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateKichThuoc();
                    }
                });
                kt.setVisible(true);
            }
        });
    }//GEN-LAST:event_jButton12ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Them_Kieu_Day kd = new Them_Kieu_Day();
                kd.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                kd.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateCboKieuDay();
                    }
                });
                kd.setVisible(true);
            }
        });
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Them_Loai_Khoa lk = new Them_Loai_Khoa();
                lk.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                lk.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateCboLoaiKhoa();
                    }
                });
                lk.setVisible(true);
            }
        });
    }//GEN-LAST:event_jButton10ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Them_Mau_Sac ms = new Them_Mau_Sac();
                ms.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                ms.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateCboMauSac();
                    }
                });
                ms.setVisible(true);
            }
        });
    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        // TODO add your handling code here:
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                Them_Chat_Lieu cl = new Them_Chat_Lieu();
                cl.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                cl.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        updateCboChatLieu();
                    }
                });
                cl.setVisible(true);
            }
        });
    }//GEN-LAST:event_jButton7ActionPerformed

    private void cbo_SanPhamActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cbo_SanPhamActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cbo_SanPhamActionPerformed

    private void btn_MoiCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_MoiCTActionPerformed
        clearCT();
    }//GEN-LAST:event_btn_MoiCTActionPerformed

    private void btn_XoaCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_XoaCTActionPerformed
        int selectedRow = tbl_ChiTiet.getSelectedRow();
    if (selectedRow != -1) {
        int id = (int) tbl_ChiTiet.getValueAt(selectedRow, 0);
        deleteRecord(id);
        clearCT();
    } else {
        JOptionPane.showMessageDialog(this, "Vui lòng chọn dòng để xóa.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    }//GEN-LAST:event_btn_XoaCTActionPerformed

    private void txt_SuaCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_SuaCTActionPerformed
        int id = Integer.parseInt(txt_ChiTiet.getText());
        int idSanPham = DoiSanPham();
        int idChatLieu = DoiChatLieu();
        int idMauSac = DoiMauSac();
        int idKhuyenMai = DoiKhuyenMai();
        int idLoaiKhoa = DoiLoaiKhoa();
        int idKieuDay = DoiKieuDay();
        int idKichThuoc = DoiKichThuoc();
        String sql = "UPDATE [dbo].[SanPhamChiTiet] SET "
        + "[TenSanPhamChiTiet] = ?, "
        + "[IDSanPham] = ?, "
        + "[IDChatLieu] = ?, "
        + "[IDMauSac] = ?, "
        + "[IDKhuyenMai] = ?, "
        + "[IDKichThuoc] = ?, "
        + "[IDLoaiKhoa] = ?, "
        + "[IDKieuDay] = ?, "
        + "[TrangThai] = ?, "
        + "[GiaBan] = ?, "
        + "[SoLuong] = ? "
        + "WHERE [IDSanPhamChiTiet] = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, txt_TenChiTiet.getText());
            ps.setInt(2, idSanPham);
            ps.setInt(3, idChatLieu);
            ps.setInt(4, idMauSac);
            ps.setInt(5, idKhuyenMai);
            ps.setInt(6, idKichThuoc);
            ps.setInt(7, idLoaiKhoa);
            ps.setInt(8, idKieuDay);
            ps.setBoolean(9, rdo_ConHang.isSelected());
            ps.setFloat(10, Float.parseFloat(txt_GiaBan.getText()));
            ps.setInt(11, Integer.parseInt(txt_SoLuong.getText()));
            ps.setInt(12, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
            fillTable(fullTen.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_txt_SuaCTActionPerformed

    private void btn_ThemCTActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ThemCTActionPerformed
        int idSanPham = DoiSanPham();
        int idChatLieu = DoiChatLieu();
        int idMauSac = DoiMauSac();
        int idKhuyenMai = DoiKhuyenMai();
        int idLoaiKhoa = DoiLoaiKhoa();
        int idKieuDay = DoiKieuDay();
        int idKichThuoc = DoiKichThuoc();

        String sql = "INSERT INTO [dbo].[SanPhamChiTiet]\n"
        + "           ([TenSanPhamChiTiet]\n"
        + "	      ,[IDSanPham]\n"
        + "           ,[IDChatLieu]\n"
        + "           ,[IDMauSac]\n"
        + "           ,[IDKhuyenMai]\n"
        + "           ,[IDKichThuoc]\n"
        + "           ,[IDLoaiKhoa]\n"
        + "	      ,[IDKieuDay]\n"
        + "           ,[TrangThai]\n"
        + "           ,[GiaBan]\n"
        + "           ,[SoLuong])\n"
        + "     VALUES\n"
        + "           (?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, txt_TenChiTiet.getText());
            ps.setInt(2, idSanPham);
            ps.setInt(3, idChatLieu);
            ps.setInt(4, idMauSac);
            ps.setInt(5, idKhuyenMai);
            ps.setInt(6, idKichThuoc);
            ps.setInt(7, idLoaiKhoa);
            ps.setInt(8, idKieuDay);
            ps.setBoolean(9, rdo_ConHang.isSelected());
            ps.setFloat(10, Float.parseFloat(txt_GiaBan.getText()));
            ps.setInt(11, Integer.parseInt(txt_SoLuong.getText()));
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            fillTable(fullTen.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btn_ThemCTActionPerformed

    private void tbl_ChiTietMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ChiTietMouseClicked
        // TODO add your handling code here:
        detail(tbl_ChiTiet.getSelectedRow());
    }//GEN-LAST:event_tbl_ChiTietMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btn_MoiCT;
    private javax.swing.JButton btn_MoiSP;
    private javax.swing.JButton btn_SuaSP;
    private javax.swing.JButton btn_ThemCT;
    private javax.swing.JButton btn_ThemSP;
    private javax.swing.JButton btn_XoaCT;
    private javax.swing.JButton btn_XoaSP;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JComboBox<String> cbo_ChatLieu;
    private javax.swing.JComboBox<String> cbo_KhuyenMai;
    private javax.swing.JComboBox<String> cbo_KichThuoc;
    private javax.swing.JComboBox<String> cbo_KieuDay;
    private javax.swing.JComboBox<String> cbo_LoaiKhoa;
    private javax.swing.JComboBox<String> cbo_MauSac;
    private javax.swing.JComboBox<String> cbo_SanPham;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane2;
    private javax.swing.JRadioButton rdo_ConHang;
    private javax.swing.JRadioButton rdo_HetHang;
    private javax.swing.JRadioButton rdo_TimConHang;
    private javax.swing.JRadioButton rdo_TimHetHang;
    private javax.swing.JTable tbl_ChiTiet;
    private javax.swing.JTable tbl_SanPham;
    private javax.swing.JTextField txt_ChiTiet;
    private javax.swing.JTextField txt_GiaBan;
    private javax.swing.JTextField txt_IDSanPham;
    private javax.swing.JTextField txt_SoLuong;
    private javax.swing.JButton txt_SuaCT;
    private javax.swing.JTextField txt_TenChiTiet;
    private javax.swing.JTextField txt_TenSanPham;
    private javax.swing.JTextField txt_TimKiem;
    // End of variables declaration//GEN-END:variables

    private void setSelectedItem(JComboBox<String> cbo_ChatLieu) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
