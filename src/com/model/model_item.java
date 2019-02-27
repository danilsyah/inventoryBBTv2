/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import com.koneksi.DBKoneksi;
import com.view.FrmItem;
import com.controller.controller_item;
import java.awt.HeadlessException;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author danil
 */
public class model_item implements controller_item {

    final Connection con = DBKoneksi.getKoneksi();
    private String SQL;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;
    private String categoryname,unitName,codeCategory,codeUnits,jmlRecord;
    
    /**
     *
     * @param item
     */
    @Override
    public void New(FrmItem item) {
        try {
            stmt = con.createStatement();
            SQL = "SELECT MAX(RIGHT(codeItem,3))FROM items";
            res = stmt.executeQuery(SQL);
            while(res.next()){
                if(res.first()==false){
                    item.txtCodeItem.setText("CI001");
                }else{
                    res.last();
                    int autoid = res.getInt(1)+1;
                    String nomor = String.valueOf(autoid);
                    int noLong = nomor.length();
                    for(int a = 0;a<3-noLong;a++){
                        nomor = "0"+nomor;
                    }
                    item.txtCodeItem.setText("CI"+nomor);
                }
            }
            res.close();
            stmt.close();
            ClearKolom(item);
        } catch (SQLException e) {
             JOptionPane.showMessageDialog(null, e);
        }
    }

    @Override
    public void Save(FrmItem item) throws SQLException {
        if(item.txtCodeItem.getText().isEmpty()||item.txtItems.getText().isEmpty()||item.txtStockMin.getText().isEmpty()||
                item.cbCategory.getSelectedItem().equals("-PILIH-")||item.cbUnits.getSelectedItem().equals("-PILIH-"))
        {
             JOptionPane.showMessageDialog(null,"Data Tidak Lengkap...!!!");
        }else{
            try {
                SQL = "INSERT INTO items VALUES(?,?,?,?,?)";
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1, item.txtCodeItem.getText());
                pstmt.setString(2, item.txtItems.getText());
                pstmt.setString(3, getCodeCategory(item));
                pstmt.setString(4, getCodeUnits(item));
                pstmt.setInt(5, Integer.parseInt(item.txtStockMin.getText()));
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Barang Berhasil Di Simpan","Notif",JOptionPane.INFORMATION_MESSAGE);
                pstmt.close();
                refresh_frame(item);
            } catch (HeadlessException | NumberFormatException | SQLException e) {
                 JOptionPane.showMessageDialog(null,"Data Gagal Disimpan","Pesan",JOptionPane.WARNING_MESSAGE);
            }
            
            finally{
                ViewTable(item);
                ClearKolom(item);
            }
        }
    }

    @Override
    public void Update(FrmItem item) throws SQLException {
               int ok = JOptionPane.showConfirmDialog(null, "Data Akan Di Ubah..???","Konfirmasi",JOptionPane.YES_NO_OPTION);
               if(ok == 0){
                   try {
                       SQL = "UPDATE items SET itemName = ?,codeCategory=?,codeUnit=?,stockMin=? WHERE codeItem = ?";
                       pstmt = con.prepareStatement(SQL);
                       pstmt.setString(1, item.txtItems.getText());
                       pstmt.setString(2, getCodeCategory(item));
                       pstmt.setString(3,getCodeUnits(item));
                       pstmt.setString(4, item.txtStockMin.getText());
                       pstmt.setString(5, item.txtCodeItem.getText());
                       pstmt.executeUpdate();
                        JOptionPane.showMessageDialog(null,"Update Berhasil");
                        pstmt.close();
                        refresh_frame(item);
                   } catch (HeadlessException | SQLException e) {
                       JOptionPane.showMessageDialog(null,"Update Gagal" + e);
                   }
                   finally{
                       ViewTable(item);
                   }
               }else{
                   ViewTable(item);
               }
    }

    @Override
    public void Delete(FrmItem item) throws SQLException {
            int ok = JOptionPane.showConfirmDialog(null, "Data Akan Di Hapus..???","Konfirmasi",JOptionPane.YES_NO_OPTION);
            if(ok == 0){
            
                try {
                    SQL = "DELETE FROM items WHERE codeItem = ?";
                    pstmt = con.prepareStatement(SQL);
                    pstmt.setString(1, item.txtCodeItem.getText());
                    pstmt.executeUpdate();
                    JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
                     pstmt.close();
                     refresh_frame(item);
                } catch (HeadlessException | SQLException e) {
                     JOptionPane.showMessageDialog(null, "Data Gagal di hapus "+e);
                }
                finally{
                    ViewTable(item);
                }
            }
    }

    @Override
    public void ViewTable(FrmItem item) throws SQLException {
        item.tblModel.getDataVector().removeAllElements();
        item.tblModel.fireTableDataChanged();
        try {
            stmt = con.createStatement();
            SQL = "SELECT * FROM v_items LIMIT 15";
            res = stmt.executeQuery(SQL);
            while(res.next()){
                Object[] ob = new Object[5];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(3);
                ob[3] = res.getString(4);
                ob[4] = res.getString(5);
                item.tblModel.addRow(ob);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void ClickTable(FrmItem item) {
        try {
            int pilih = item.tblItems.getSelectedRow();
            if(pilih == -1){
                return;
            }
            item.txtCodeItem.setText(item.tblModel.getValueAt(pilih, 0).toString());
            item.txtItems.setText(item.tblModel.getValueAt(pilih, 1).toString());
            item.cbCategory.setSelectedItem(item.tblModel.getValueAt(pilih, 2).toString());
            item.cbUnits.setSelectedItem(item.tblModel.getValueAt(pilih, 3).toString());
            item.txtStockMin.setText(item.tblModel.getValueAt(pilih,4).toString());
        } catch (Exception e) {
        }
    }

    @Override
    public void ListCategory(FrmItem item) throws SQLException {
        try {
            SQL = "SELECT categoryName FROM category";
            stmt = con.createStatement();
            res = stmt.executeQuery(SQL);
            item.cbCategory.addItem("-PILIH-");
            while(res.next()){
                categoryname = res.getString("categoryName");
                item.cbCategory.addItem(categoryname);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void ListUnits(FrmItem item) throws SQLException {
        try {
            SQL = "SELECT unitName FROM units ";
             stmt = con.createStatement();
            res = stmt.executeQuery(SQL);
            item.cbUnits.addItem("-PILIH-");
            while(res.next()){
                unitName = res.getString("unitName");
                item.cbUnits.addItem(unitName);
            }
        } catch (SQLException e) {
             System.out.println(e);
        }
    }
    
    private void ClearKolom(FrmItem item) throws SQLException{
        item.txtItems.setText("");
        item.txtStockMin.setText("");
        item.txtSearch.setText("");
        item.cbCategory.setSelectedItem("-PILIH-");
        item.cbUnits.setSelectedItem("-PILIH-");
           
    }
    
    private void refresh_frame(FrmItem i) throws SQLException{
        i.dispose();
        new FrmItem().setVisible(true);
    }
    

    private String getCodeCategory(FrmItem item) throws SQLException {
        try {
            categoryname = item.cbCategory.getSelectedItem().toString();
            SQL = "SELECT codeCategory FROM category WHERE categoryName = '"+categoryname+"'";
            stmt = con.createStatement();
            res = stmt.executeQuery(SQL);
            while(res.next()){
                codeCategory = res.getString("codeCategory");
            }
        } catch (SQLException e) {
        }
        return codeCategory;
    }
    
    private String getCodeUnits(FrmItem item)throws SQLException{
        try {
            unitName = item.cbUnits.getSelectedItem().toString();
            SQL = "SELECT codeUnit FROM units WHERE unitName = '"+unitName+"'";
            stmt = con.createStatement();
            res = stmt.executeQuery(SQL);
            while(res.next()){
                codeUnits = res.getString("codeUnit");
            }
        } catch (SQLException e) {
        }
        
        return codeUnits;
        
    }

    @Override
    public void Pagination(FrmItem item,int limit,int getpagesize) throws SQLException {
        item.tblModel.getDataVector().removeAllElements();
        item.tblModel.fireTableDataChanged();
        try {
            stmt = con.createStatement();
            SQL = "SELECT * FROM v_items LIMIT "+limit+","+getpagesize+"";
            res = stmt.executeQuery(SQL);
            while(res.next()){
                Object[] ob = new Object[5];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                ob[2] = res.getString(3);
                ob[3] = res.getString(4);
                ob[4] = res.getString(5);
                item.tblModel.addRow(ob);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    @Override
   public  String JmlRecord(FrmItem i)throws SQLException{
        try {
            SQL = "SELECT COUNT(codeItem) AS jml FROM items";
            stmt = con.createStatement();
            res = stmt.executeQuery(SQL);
            while (res.next()){
                jmlRecord = res.getString("jml");
            }
        } catch (SQLException e) {
        }
              
        
        return jmlRecord;
    }

    @Override
    public void Searching_Items(FrmItem item) throws SQLException {
        try {
            item.tblModel = new DefaultTableModel(null, item.header);
            item.tblItems.setModel(item.tblModel);
            stmt = con.createStatement();
            SQL = "SELECT * FROM v_items WHERE codeItem LIKE '%"+item.txtSearch.getText()+"%' OR"
                    + " itemName LIKE '%"+item.txtSearch.getText()+"%' LIMIT 15";
            res = stmt.executeQuery(SQL);
            while(res.next()){
                item.tblModel.addRow(new Object[]{
                    res.getString("codeItem"),
                    res.getString("itemName"),
                    res.getString("categoryName"),
                    res.getString("unitName"),
                    res.getString("stockMin")
                });
            }
        } catch (Exception e) {
        }
    }
    
}
