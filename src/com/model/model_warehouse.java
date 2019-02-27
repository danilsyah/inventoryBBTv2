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
import com.view.FrmWarehouse;
import com.controller.controller_warehouse;
import java.awt.HeadlessException;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author danil
 */
public class model_warehouse implements controller_warehouse{

    final Connection con = DBKoneksi.getKoneksi();
    private String SQL;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;
    
    @Override
    public void New(FrmWarehouse wh){
        try {
            stmt = con.createStatement();
            SQL = "SELECT MAX(RIGHT(codeWarehouse,2)) FROM warehouse ";
            res = stmt.executeQuery(SQL);
            while(res.next()){
                if(res.first()==false){
                    wh.txtCode.setText("WH01");
                }else{
                    res.last();
                    int autoid = res.getInt(1)+1;
                    String nomor = String.valueOf(autoid);
                    int noLong = nomor.length();
                    for(int a = 0;a<2-noLong;a++){
                        nomor = "0"+nomor;
                    }
                    wh.txtCode.setText("WH"+nomor);
                }
            }
            res.close();
            stmt.close();
        } catch (Exception e) {
        }
    }

    @Override
    public void Save(FrmWarehouse wh) throws SQLException {
       if(wh.txtWarehouse.getText().isEmpty()||wh.txtLocation.getText().isEmpty()||wh.txtDesc.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Data Tidak Lengkap...!!!");
       }else{
           try {
               SQL = "INSERT INTO warehouse VALUES(?,?,?,?)";
               pstmt = con.prepareStatement(SQL);
               pstmt.setString(1, wh.txtCode.getText());
               pstmt.setString(2, wh.txtWarehouse.getText());
               pstmt.setString(3, wh.txtLocation.getText());
               pstmt.setString(4,wh.txtDesc.getText());
               pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Data Barang Berhasil Di Simpan","Notif",JOptionPane.INFORMATION_MESSAGE);
                pstmt.close();
           } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null,"Data Gagal Disimpan","Pesan",JOptionPane.WARNING_MESSAGE);
           }
           
           finally{
               ViewTable(wh);
               clearField(wh);
           }
       }
    }

    @Override
    public void Update(FrmWarehouse wh) throws SQLException {
       int ok = JOptionPane.showConfirmDialog(null, "Data Akan Di Ubah..???","Konfirmasi",JOptionPane.YES_NO_OPTION);
       if(ok == 0){
           try {
               SQL = "UPDATE warehouse SET warehouse=?,location=?,description=? WHERE codeWarehouse=?";
               pstmt = con.prepareStatement(SQL);
               pstmt.setString(4, wh.txtCode.getText());
               pstmt.setString(1, wh.txtWarehouse.getText());
               pstmt.setString(2, wh.txtLocation.getText());
               pstmt.setString(3, wh.txtDesc.getText());
               pstmt.executeUpdate();
               JOptionPane.showMessageDialog(null,"Update Berhasil");
               pstmt.close();
               clearField(wh);
           } catch (HeadlessException | SQLException e) {
                JOptionPane.showMessageDialog(null,"Update Gagal" + e);
           }
           finally{
               ViewTable(wh);
           }
       }else{
           ViewTable(wh);
       }
    }

    @Override
    public void Delete(FrmWarehouse wh) throws SQLException {
          int ok = JOptionPane.showConfirmDialog(null, "Data Akan Di Hapus..???","Konfirmasi",JOptionPane.YES_NO_OPTION);
          if(ok == 0){
              try {
                  SQL = "DELETE FROM warehouse WHERE codeWarehouse = ?";
                  pstmt = con.prepareStatement(SQL);
                  pstmt.setString(1, wh.txtCode.getText());
                  pstmt.executeUpdate();
                   JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
                   pstmt.close();
                   clearField(wh);
              } catch (HeadlessException | SQLException e) {
                   JOptionPane.showMessageDialog(null, "Data Gagal di hapus "+e);
              }
              finally{
                  ViewTable(wh);
              }
          }
    }

    @Override
    public void ViewTable(FrmWarehouse wh) throws SQLException {
           wh.tblModel.getDataVector().removeAllElements();
           wh.tblModel.fireTableDataChanged();
           wh.tblWareh.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
           wh.tblColumn = wh.tblWareh.getColumnModel().getColumn(0);
           wh.tblColumn.setPreferredWidth(40);
            wh.tblColumn = wh.tblWareh.getColumnModel().getColumn(1);
           wh.tblColumn.setPreferredWidth(50);
            wh.tblColumn = wh.tblWareh.getColumnModel().getColumn(2);
           wh.tblColumn.setPreferredWidth(50);
            wh.tblColumn = wh.tblWareh.getColumnModel().getColumn(3);
           wh.tblColumn.setPreferredWidth(150);
            wh.tblColumn = wh.tblWareh.getColumnModel().getColumn(4);
           wh.tblColumn.setPreferredWidth(200);           
           try {
            stmt = con.createStatement();
            SQL = "SELECT * FROM warehouse";
            res = stmt.executeQuery(SQL);
            int no = 0;
            while(res.next()){
                Object[] ob = new Object[5];
                no++;
                ob[0] = String.valueOf(no).toString();
                ob[1] = res.getString("codeWarehouse");
                ob[2]=res.getString("warehouse");
                ob[3]=res.getString("location");
                ob[4]=res.getString("description");
                wh.tblModel.addRow(ob);
                
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void ClickTable(FrmWarehouse wh) {
        try {
            int pilih = wh.tblWareh.getSelectedRow();
            if(pilih == -1){
                return;
            }
            wh.txtCode.setText(wh.tblModel.getValueAt(pilih,1).toString());
            wh.txtWarehouse.setText(wh.tblModel.getValueAt(pilih, 2).toString());
            wh.txtLocation.setText(wh.tblModel.getValueAt(pilih, 3).toString());
            wh.txtDesc.setText(wh.tblModel.getValueAt(pilih, 4).toString());
        } catch (Exception e) {
        }
    }
    
    private void clearField(FrmWarehouse w){
        w.txtCode.setText("");
        w.txtDesc.setText("");
        w.txtLocation.setText("");
        w.txtWarehouse.setText("");
    }
}
