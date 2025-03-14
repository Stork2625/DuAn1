/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JInternalFrame.java to edit this template
 */
package giao_Dien;

import Repository.Repository_NhanVien;
import config.DBConnect;
import entity.NhanVien_entity;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author GIGABYTE
 */
public class Nhan_Vien extends javax.swing.JInternalFrame {

    private Repository_NhanVien nv = new Repository_NhanVien();
    private DefaultTableModel dtm;

    /**
     * Creates new form Nhan_Vien
     */
    public Nhan_Vien() {
        initComponents();
        ((javax.swing.plaf.basic.BasicInternalFrameUI) this.getUI()).setNorthPane(null);
        setBorder(BorderFactory.createEmptyBorder());
        dtm = (DefaultTableModel) tbl_bang.getModel();
        FillTable(nv.getAll());
    }

    private void FillTable(ArrayList<NhanVien_entity> list) {
        dtm.setRowCount(0);
        for (NhanVien_entity fnv : list) {
            Object[] rowData = {
                fnv.getID(),
                fnv.getTenNV(),
                fnv.getTuoi(),
                fnv.getDiaChi(),
                fnv.getGioiTinh() ? "Nam" : "Nữ",
                fnv.getChucVu() ? "Quản lý" : "Nhân viên",
                fnv.getTaiKhoan(),
                fnv.getMatKhau()
            };
            dtm.addRow(rowData);
        }
    }

    private void Show(int index) {
        NhanVien_entity snv = nv.getAll().get(index);
        txt_ID.setText(String.valueOf(snv.getID()));
        txt_TenNV.setText(snv.getTenNV());
        txt_TaiKhoan.setText(snv.getTaiKhoan());
        txt_MatKhau.setText(snv.getMatKhau());
        boolean isGioiTinh = snv.getGioiTinh();
        rdo_Nam.setSelected(isGioiTinh);
        rdo_Nu.setSelected(!isGioiTinh);
        txt_DiaChi.setText(snv.getDiaChi());
        txt_Tuoi.setText(String.valueOf(snv.getTuoi()));
        boolean isChucVu = snv.getChucVu();
        rdo_NhanVien.setSelected(!isChucVu);
        rdo_QuanLy.setSelected(isChucVu);
    }

    private void ThemNV() {
        String sql = "INSERT INTO [dbo].[NhanVien] "
                + "([TenNhanVien], [Tuoi], [DiaChi], [GioiTinh], [ChucVu], [TaiKhoan], [MatKhau]) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {

            // Set parameters for the PreparedStatement
            ps.setString(1, txt_TenNV.getText());
            ps.setInt(2, Integer.parseInt(txt_Tuoi.getText()));
            ps.setString(3, txt_DiaChi.getText());
            ps.setBoolean(4, rdo_Nam.isSelected());
            ps.setBoolean(5, rdo_QuanLy.isSelected());
            ps.setString(6, txt_TaiKhoan.getText());
            ps.setString(7, txt_MatKhau.getText());
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Thêm nhân viên thành công!");
                FillTable(nv.getAll());
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Thêm nhân viên thất bại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    private void SuaNV() {
        String sql = "UPDATE [dbo].[NhanVien] SET "
                + "[TenNhanVien] = ?, "
                + "[Tuoi] = ?, "
                + "[DiaChi] = ?, "
                + "[GioiTinh] = ?, "
                + "[ChucVu] = ?, "
                + "[TaiKhoan] = ?, "
                + "[MatKhau] = ? "
                + "WHERE IDNhanVien = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, txt_TenNV.getText());
            ps.setInt(2, Integer.parseInt(txt_Tuoi.getText()));
            ps.setString(3, txt_DiaChi.getText());
            ps.setBoolean(4, rdo_Nam.isSelected());
            ps.setBoolean(5, rdo_QuanLy.isSelected());
            ps.setString(6, txt_TaiKhoan.getText());
            ps.setString(7, txt_MatKhau.getText());
            ps.setInt(8, Integer.parseInt(txt_ID.getText())); // Update ID to target the specific record

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thành công!");
                FillTable(nv.getAll());
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Cập nhật nhân viên thất bại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    private void XoaNV() {
        String sql = "DELETE FROM [dbo].[NhanVien] WHERE IDNhanVien = ?";
        try (Connection con = DBConnect.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, Integer.parseInt(txt_ID.getText())); // Use ID to target the specific record

            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                javax.swing.JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
                FillTable(nv.getAll());
                Clear(); // Clear the form after deletion
            } else {
                javax.swing.JOptionPane.showMessageDialog(this, "Xóa nhân viên thất bại.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            javax.swing.JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage());
        }
    }

    private void Clear() {
        txt_ID.setText("");
        txt_TenNV.setText("");
        txt_TaiKhoan.setText("");
        txt_MatKhau.setText("");
        txt_DiaChi.setText("");
        txt_Tuoi.setText("");
        rdo_Nam.setSelected(true); // Set default gender if needed
        rdo_NhanVien.setSelected(true); // Set default role if needed
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
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_bang = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_ID = new javax.swing.JTextField();
        txt_TenNV = new javax.swing.JTextField();
        txt_TaiKhoan = new javax.swing.JTextField();
        txt_MatKhau = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        rdo_Nam = new javax.swing.JRadioButton();
        rdo_Nu = new javax.swing.JRadioButton();
        txt_DiaChi = new javax.swing.JTextField();
        txt_Tuoi = new javax.swing.JTextField();
        rdo_NhanVien = new javax.swing.JRadioButton();
        rdo_QuanLy = new javax.swing.JRadioButton();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("Danh sách nhân viên");

        tbl_bang.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Mã nhân viên ", "Tên nhân viên", "Tài khoản ", "Mật khẩu", "Giới tính ", "Địa chỉ ", "Tuổi ", "Chức vụ"
            }
        ));
        tbl_bang.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_bangMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_bang);

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel2.setText("Thêm nhân viên");

        jLabel3.setText("Mã nhân viên:");

        jLabel4.setText("Tên nhân viên:");

        jLabel5.setText("Tài khoản:");

        jLabel6.setText("Mật khẩu:");

        txt_ID.setEditable(false);

        txt_TenNV.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_TenNVActionPerformed(evt);
            }
        });

        jLabel7.setText("Giới tính:");

        jLabel8.setText("Địa chỉ:");

        jLabel9.setText("Tuổi:");

        jLabel10.setText("Chức vụ:");

        buttonGroup1.add(rdo_Nam);
        rdo_Nam.setText("Nam ");

        buttonGroup1.add(rdo_Nu);
        rdo_Nu.setText("Nữ");

        buttonGroup2.add(rdo_NhanVien);
        rdo_NhanVien.setText("Nhân viên");

        buttonGroup2.add(rdo_QuanLy);
        rdo_QuanLy.setText("Quản lý");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(417, 417, 417)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(437, 437, 437)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addGap(26, 26, 26)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_ID)
                            .addComponent(txt_TenNV)
                            .addComponent(txt_TaiKhoan)
                            .addComponent(txt_MatKhau, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE))
                        .addGap(249, 249, 249)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addGap(28, 28, 28)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdo_Nam)
                                .addGap(18, 18, 18)
                                .addComponent(rdo_Nu))
                            .addComponent(txt_DiaChi)
                            .addComponent(txt_Tuoi, javax.swing.GroupLayout.DEFAULT_SIZE, 235, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(rdo_NhanVien)
                                .addGap(18, 18, 18)
                                .addComponent(rdo_QuanLy))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(246, 246, 246)
                        .addComponent(jButton1)
                        .addGap(72, 72, 72)
                        .addComponent(jButton2)
                        .addGap(79, 79, 79)
                        .addComponent(jButton3)
                        .addGap(68, 68, 68)
                        .addComponent(jButton4)))
                .addContainerGap(143, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txt_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)
                            .addComponent(rdo_Nam)
                            .addComponent(rdo_Nu))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(txt_TenNV, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(txt_DiaChi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(txt_TaiKhoan, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)
                            .addComponent(txt_Tuoi, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(txt_MatKhau, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10)
                            .addComponent(rdo_NhanVien)
                            .addComponent(rdo_QuanLy))
                        .addGap(60, 131, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jButton1)
                            .addComponent(jButton2)
                            .addComponent(jButton3)
                            .addComponent(jButton4))
                        .addGap(76, 76, 76))))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txt_TenNVActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_TenNVActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_TenNVActionPerformed

    private void tbl_bangMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_bangMouseClicked
        // TODO add your handling code here:
        int index = tbl_bang.getSelectedRow();
        Show(index);
    }//GEN-LAST:event_tbl_bangMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
        ThemNV();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        // TODO add your handling code here:
        SuaNV();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
        XoaNV();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        // TODO add your handling code here:
        Clear();
    }//GEN-LAST:event_jButton4ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rdo_Nam;
    private javax.swing.JRadioButton rdo_NhanVien;
    private javax.swing.JRadioButton rdo_Nu;
    private javax.swing.JRadioButton rdo_QuanLy;
    private javax.swing.JTable tbl_bang;
    private javax.swing.JTextField txt_DiaChi;
    private javax.swing.JTextField txt_ID;
    private javax.swing.JTextField txt_MatKhau;
    private javax.swing.JTextField txt_TaiKhoan;
    private javax.swing.JTextField txt_TenNV;
    private javax.swing.JTextField txt_Tuoi;
    // End of variables declaration//GEN-END:variables
}
