/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package giao_Dien;

import Repository.Repository_KhuyenMai;
import config.DBConnect;
import entity.Khuyen_Mai_entity;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.JOptionPane;

/**
 *
 * @author GIGABYTE
 */
public class Khuyen_Mai extends javax.swing.JInternalFrame {

    private Repository.Repository_KhuyenMai km = new Repository_KhuyenMai();
    private DefaultTableModel dtm;

    /**
     * Creates new form Khuyen_Mai
     */
    public Khuyen_Mai() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        setBorder(BorderFactory.createEmptyBorder());
        dtm = (DefaultTableModel) tbl_KhuyenMai.getModel();
        fillTable(km.getAll());
    }

    private void fillTable(ArrayList<Khuyen_Mai_entity> list) {
        dtm.setRowCount(0);
        for (Khuyen_Mai_entity km : list) {
            Object[] rowData = {
                km.getId(),
                km.getPhanTram(),
                km.getNgayBatDau(),
                km.getNgayKetThuc(),
                km.getTrangThai() ? "Còn hoạt động" : "Dừng hoạt động"
            };
            dtm.addRow(rowData);
        }
    }
    public void LoadKM(){
        fillTable(km.getAll());
    }

    private void showTable(int index) {
        Khuyen_Mai_entity skm = km.getAll().get(index);
        txt_ID.setText(String.valueOf(skm.getId()));
        txt_PhanTram.setText(String.valueOf(skm.getPhanTram()));

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date ngayBatDau = dateFormat.parse(skm.getNgayBatDau());
            Date ngayKetThuc = dateFormat.parse(skm.getNgayKetThuc());
            jcb_NgayBatDau.setDate(ngayBatDau);
            jcb_NgayKetThuc.setDate(ngayKetThuc);
        } catch (Exception e) {
            e.printStackTrace();
        }

        boolean isTrangThai = skm.getTrangThai();
        cbo_CHD.setSelected(isTrangThai);
        cbo_DHD.setSelected(!isTrangThai);
    }

    public void ThemKH() {
        String sql = "INSERT INTO KhuyenMai (PhanTramKhuyenMai, NgayBatDau, NgayKetThuc, TrangThai) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            float phanTram = Float.parseFloat(txt_PhanTram.getText());
            ps.setFloat(1, phanTram);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ngayBatDau = dateFormat.format(jcb_NgayBatDau.getDate());
            ps.setString(2, ngayBatDau);
            String ngayKetThuc = dateFormat.format(jcb_NgayKetThuc.getDate());
            ps.setString(3, ngayKetThuc);
            boolean trangThai = cbo_CHD.isSelected();
            ps.setBoolean(4, trangThai);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            fillTable(km.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void SuaKM() {
        int selectedRow = tbl_KhuyenMai.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khuyến mãi để sửa.");
            return;
        }

        String sql = "UPDATE [dbo].[KhuyenMai]\n"
                + "   SET [PhanTramKhuyenMai] = ?\n"
                + "      ,[NgayBatDau] = ?\n"
                + "      ,[NgayKetThuc] = ?\n"
                + "      ,[TrangThai] = ?\n"
                + " WHERE IDKhuyenMai = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            float phanTram = Float.parseFloat(txt_PhanTram.getText());
            ps.setFloat(1, phanTram);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ngayBatDau = dateFormat.format(jcb_NgayBatDau.getDate());
            ps.setString(2, ngayBatDau);
            String ngayKetThuc = dateFormat.format(jcb_NgayKetThuc.getDate());
            ps.setString(3, ngayKetThuc);
            boolean trangThai = cbo_CHD.isSelected();
            ps.setBoolean(4, trangThai);
            int id = Integer.parseInt(txt_ID.getText());
            ps.setInt(5, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Sửa thành công");
            fillTable(km.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi sửa khuyến mãi");
        }
    }

    public void XoaKM() {
        int selectedRow = tbl_KhuyenMai.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một khuyến mãi để xóa.");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa khuyến mãi này?", "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) {
            return;
        }
        String sql = "DELETE FROM KhuyenMai WHERE IDKhuyenMai = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            int id = Integer.parseInt(txt_ID.getText());
            ps.setInt(1, id);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            fillTable(km.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa khuyến mãi");
        }
    }

    public void Clear() {
        txt_ID.setText("");
        txt_PhanTram.setText("");
        jcb_NgayBatDau.setDate(null);
        jcb_NgayKetThuc.setDate(null);
        buttonGroup1.clearSelection();
        tbl_KhuyenMai.clearSelection();
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
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_KhuyenMai = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        txt_ID = new javax.swing.JTextField();
        txt_PhanTram = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jcb_NgayBatDau = new de.wannawork.jcalendar.JCalendarComboBox();
        jcb_NgayKetThuc = new de.wannawork.jcalendar.JCalendarComboBox();
        jLabel6 = new javax.swing.JLabel();
        cbo_CHD = new javax.swing.JRadioButton();
        cbo_DHD = new javax.swing.JRadioButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Khuyến mãi ");

        tbl_KhuyenMai.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID khuyến mãi ", "Phần trăm giảm", "Ngày bắt đầu ", "Ngày kết thúc ", "Trạng thái"
            }
        ));
        tbl_KhuyenMai.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_KhuyenMaiMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_KhuyenMai);

        jLabel2.setText("ID:");

        jLabel3.setText("Phần trăm:");

        jLabel4.setText("Ngày bắt đầu:");

        jLabel5.setText("Ngày kết thúc:");

        txt_ID.setEditable(false);

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/add.png"))); // NOI18N
        jButton1.setText("Thêm");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/tay.png"))); // NOI18N
        jButton2.setText("Sửa");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/trash.png"))); // NOI18N
        jButton3.setText("Xóa");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/refresh.png"))); // NOI18N
        jButton4.setText("Mới");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jLabel6.setText("Trạng thai:");

        buttonGroup1.add(cbo_CHD);
        cbo_CHD.setText("Còn hoạt động");

        buttonGroup1.add(cbo_DHD);
        cbo_DHD.setText("Dừng hoạt động");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))))
                .addGap(27, 27, 27)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_ID, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(txt_PhanTram, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                            .addComponent(jcb_NgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jcb_NgayKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(54, 54, 54)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cbo_CHD)
                        .addGap(18, 18, 18)
                        .addComponent(cbo_DHD)))
                .addGap(37, 37, 37))
            .addGroup(layout.createSequentialGroup()
                .addGap(446, 446, 446)
                .addComponent(jLabel1)
                .addContainerGap(536, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel1)
                .addGap(59, 59, 59)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(70, 70, 70)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)
                            .addComponent(jButton1))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txt_PhanTram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(jButton2))
                        .addGap(24, 24, 24)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jcb_NgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel4))
                            .addComponent(jButton3))
                        .addGap(29, 29, 29)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcb_NgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(jButton4))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(cbo_CHD)
                            .addComponent(cbo_DHD)))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(133, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void tbl_KhuyenMaiMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_KhuyenMaiMouseClicked
        // TODO add your handling code here:
        int index = tbl_KhuyenMai.getSelectedRow();
        showTable(index);
    }//GEN-LAST:event_tbl_KhuyenMaiMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ThemKH();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        SuaKM();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        XoaKM();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton cbo_CHD;
    private javax.swing.JRadioButton cbo_DHD;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private de.wannawork.jcalendar.JCalendarComboBox jcb_NgayBatDau;
    private de.wannawork.jcalendar.JCalendarComboBox jcb_NgayKetThuc;
    private javax.swing.JTable tbl_KhuyenMai;
    private javax.swing.JTextField txt_ID;
    private javax.swing.JTextField txt_PhanTram;
    // End of variables declaration//GEN-END:variables
}
