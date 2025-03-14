/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package giao_Dien;

import Repository.Repository_Voucher;
import config.DBConnect;
import entity.Voucher_entity;
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
public class Voucher extends javax.swing.JInternalFrame {

    private Repository.Repository_Voucher vc = new Repository_Voucher();
    private DefaultTableModel dtm;

    /**
     * Creates new form Voucher
     */
    public Voucher() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        setBorder(BorderFactory.createEmptyBorder());
        dtm = (DefaultTableModel) tbl_bang.getModel();
        Fill(vc.getAll());

    }

    private void Fill(ArrayList<Voucher_entity> list) {
        dtm.setRowCount(0);
        for (Voucher_entity vc : list) {
            Object[] toTable = {
                vc.getID(),
                vc.getPhanTram(),
                vc.getNgayBatDau(),
                vc.getNgayKetThuc(),
                vc.getTrangThai() ? "Hoạt động" : "Không hoạt động"
            };
            dtm.addRow(toTable);
        }
    }

    private void ShowTable(int index) {
        Voucher_entity svc = vc.getAll().get(index);
        jTextField1.setText(String.valueOf(svc.getID()));
        txt_PhanTram.setText(String.valueOf(svc.getPhanTram()));
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date ngayBatDau = dateFormat.parse(svc.getNgayBatDau());
            Date ngayKetThuc = dateFormat.parse(svc.getNgayKetThuc());
            jcb_NgayBatDau.setDate(ngayBatDau);
            jcb_NgayKetThuc.setDate(ngayKetThuc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        boolean isTrangThai = svc.getTrangThai();
        cbo_HD.setSelected(isTrangThai);
        cbo_KHD.setSelected(!isTrangThai);
    }

    private void themVC() {
        String sql = "INSERT INTO [dbo].[Voucher]\n"
                + "           ([PhanTramGiamGia]\n"
                + "           ,[NgayBatDau]\n"
                + "           ,[NgayKetThuc]\n"
                + "           ,[TrangThai])\n"
                + "     VALUES\n"
                + "           (?,?,?,?)";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            float phanTram = Float.parseFloat(txt_PhanTram.getText());
            ps.setFloat(1, phanTram);
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ngayBatDau = dateFormat.format(jcb_NgayBatDau.getDate());
            ps.setString(2, ngayBatDau);
            String ngayKetThuc = dateFormat.format(jcb_NgayKetThuc.getDate());
            ps.setString(3, ngayKetThuc);
            boolean trangThai = cbo_HD.isSelected();
            ps.setBoolean(4, trangThai);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Thêm thành công");
            Fill(vc.getAll());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void SuaVC() {
        String sql = "UPDATE [dbo].[Voucher]\n"
                + "   SET [PhanTramGiamGia] = ?\n"
                + "      ,[NgayBatDau] = ?\n"
                + "      ,[NgayKetThuc] = ?\n"
                + "      ,[TrangThai] = ?\n"
                + " WHERE IDVoucher = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            float phanTram = Float.parseFloat(txt_PhanTram.getText());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            String ngayBatDau = dateFormat.format(jcb_NgayBatDau.getDate());
            String ngayKetThuc = dateFormat.format(jcb_NgayKetThuc.getDate());
            boolean trangThai = cbo_HD.isSelected();
            int idVoucher = Integer.parseInt(jTextField1.getText());
            ps.setFloat(1, phanTram);
            ps.setString(2, ngayBatDau);
            ps.setString(3, ngayKetThuc);
            ps.setBoolean(4, trangThai);
            ps.setInt(5, idVoucher);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Cập nhật thành công");
            Fill(vc.getAll());
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi cập nhật");
        }
    }

    private void XoaVC() {
        String sql = "DELETE FROM [dbo].[Voucher] WHERE IDVoucher = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            int idVoucher = Integer.parseInt(jTextField1.getText());
            ps.setInt(1, idVoucher);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Xóa thành công");
            Fill(vc.getAll());
            Clear();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Có lỗi xảy ra khi xóa");
        }
    }

    private void Clear() {
        jTextField1.setText("");
        txt_PhanTram.setText("");
        jcb_NgayBatDau.setDate(null);
        jcb_NgayKetThuc.setDate(null);
        cbo_HD.setSelected(false);
        cbo_KHD.setSelected(false);
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
        tbl_bang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        txt_PhanTram = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jcb_NgayBatDau = new de.wannawork.jcalendar.JCalendarComboBox();
        jcb_NgayKetThuc = new de.wannawork.jcalendar.JCalendarComboBox();
        cbo_HD = new javax.swing.JRadioButton();
        cbo_KHD = new javax.swing.JRadioButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Voucher");

        tbl_bang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã voucher", "Phần trăm giảm ", "Ngày bắt đầu ", "Ngày kết thúc", "Trạng thái "
            }
        ));
        tbl_bang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_bang);

        jLabel2.setText("Mã vocher:");

        jLabel3.setText("Phần trăm giảm:");

        jLabel4.setText("Ngày bắt đầu:");

        jLabel5.setText("Ngày kết thúc:");

        jTextField1.setEditable(false);

        txt_PhanTram.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_PhanTramActionPerformed(evt);
            }
        });

        jLabel6.setText("Trạng thái:");

        jButton3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/add.png"))); // NOI18N
        jButton3.setText("Thêm ");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/tay.png"))); // NOI18N
        jButton4.setText("Sửa");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/trash.png"))); // NOI18N
        jButton5.setText("Xóa");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/anh/refresh.png"))); // NOI18N
        jButton6.setText("Mới");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        buttonGroup1.add(cbo_HD);
        cbo_HD.setText("Hoạt động");

        buttonGroup1.add(cbo_KHD);
        cbo_KHD.setText("Không hoạt động");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(77, 77, 77)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addGap(36, 36, 36)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jTextField1)
                                    .addComponent(txt_PhanTram, javax.swing.GroupLayout.DEFAULT_SIZE, 253, Short.MAX_VALUE))
                                .addGap(150, 150, 150)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addGap(46, 46, 46)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jcb_NgayBatDau, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
                                    .addComponent(jcb_NgayKetThuc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jButton3)
                                            .addComponent(cbo_HD))
                                        .addGap(233, 233, 233)
                                        .addComponent(jButton5))
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(117, 117, 117)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(cbo_KHD)
                                            .addComponent(jButton4))))
                                .addGap(83, 83, 83)
                                .addComponent(jButton6))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(473, 473, 473)
                        .addComponent(jLabel1)))
                .addContainerGap(174, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(25, 25, 25)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(jcb_NgayBatDau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(txt_PhanTram, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jcb_NgayKetThuc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cbo_HD)
                    .addComponent(cbo_KHD))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 35, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton3)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton6))
                .addGap(83, 83, 83))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_PhanTramActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_PhanTramActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_PhanTramActionPerformed

    private void tbl_bangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangMouseClicked
        // TODO add your handling code here:
        int index = tbl_bang.getSelectedRow();
        ShowTable(index);
    }//GEN-LAST:event_tbl_bangMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        themVC();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        SuaVC();
    }//GEN-LAST:event_jButton4ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
        XoaVC();
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_jButton6ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JRadioButton cbo_HD;
    private javax.swing.JRadioButton cbo_KHD;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField jTextField1;
    private de.wannawork.jcalendar.JCalendarComboBox jcb_NgayBatDau;
    private de.wannawork.jcalendar.JCalendarComboBox jcb_NgayKetThuc;
    private javax.swing.JTable tbl_bang;
    private javax.swing.JTextField txt_PhanTram;
    // End of variables declaration//GEN-END:variables
}
