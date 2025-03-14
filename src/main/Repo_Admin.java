/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main;

import giao_Dien.San_Pham;
import javax.swing.JDesktopPane;

/**
 *
 * @author GIGABYTE
 */
public class Repo_Admin {
    public  void sanPham(JDesktopPane detop){
        San_Pham sp = new San_Pham();
        detop.add(sp);
        sp.setVisible(true);
    }
}
