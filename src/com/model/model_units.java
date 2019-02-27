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
import com.view.FrmUnits;
import com.controller.controller_units;
import java.awt.HeadlessException;
/**
 *
 * @author danil
 */
public class model_units  implements controller_units{

    final Connection con = DBKoneksi.getKoneksi();
    private String SQL;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;
    
    
    @Override
    public void New(FrmUnits unit) {
        try {
            stmt = con.createStatement();
            SQL = "select max(right(codeUnit,2)) from units";
            res = stmt.executeQuery(SQL);
            while(res.next()){
                if(res.first()==false){
                    unit.txtCode.setText("U01");
                }else{
                    res.last();
                    int autoid = res.getInt(1)+1;
                    String nomor = String.valueOf(autoid);
                    int noLong = nomor.length();
                    for(int a = 0;a<2-noLong;a++){
                        nomor = "0"+nomor;
                    }
                    unit.txtCode.setText("U"+nomor);
                }
            }
            res.close();
            stmt.close();
            unit.txtUnit.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    @Override
    public void Save(FrmUnits unit) throws SQLException {
       if(unit.txtCode.getText().isEmpty()||unit.txtUnit.getText().isEmpty()){
           JOptionPane.showMessageDialog(null,"Data Tidak Lengkap...!!!");
       }else{
           try {
               SQL = "insert into units values(?,?)";
               pstmt = con.prepareStatement(SQL);
               pstmt.setString(1, unit.txtCode.getText());
               pstmt.setString(2, unit.txtUnit.getText());
               pstmt.executeUpdate();
               JOptionPane.showMessageDialog(null,"Data Berhasil Di Simpan");
               pstmt.close();
           } catch (HeadlessException | SQLException e) {
               JOptionPane.showMessageDialog(null,"Data Gagal Di Simpan");
                System.out.println(e);
           }
           
           finally{
               ViewTable(unit);
               clear(unit);
           }
       }
    }

    @Override
    public void Update(FrmUnits unit) throws SQLException {
       int ok = JOptionPane.showConfirmDialog(null, "Data Akan Di Ubah..???","Konfirmasi",JOptionPane.YES_NO_OPTION);
       if(ok == 0){
           try {
               SQL = "UPDATE units SET unitName = ? WHERE codeUnit = ?";
               pstmt = con.prepareStatement(SQL);
               pstmt.setString(2,unit.txtCode.getText());
               pstmt.setString(1, unit.txtUnit.getText());
               pstmt.executeUpdate();
               JOptionPane.showMessageDialog(null,"Update Berhasil");
               clear(unit);
                pstmt.close();
           } catch (SQLException e) {
               JOptionPane.showMessageDialog(null,"Update Gagal" + e);
           }
           finally{
               ViewTable(unit);
               nonAktifKolom(unit);
           }
           
       }else{
           ViewTable(unit);
           nonAktifKolom(unit);
       }
    }

    @Override
    public void Delete(FrmUnits unit) throws SQLException {
          int ok = JOptionPane.showConfirmDialog(null, "Data Akan Di Hapus..???","Konfirmasi",JOptionPane.YES_NO_OPTION);
          if(ok == 0){
            try {
                SQL = "DELETE FROM units WHERE codeUnit = ?";
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1, unit.txtCode.getText());
                pstmt.executeUpdate();
                 JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
                 pstmt.close();
                 clear(unit);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal di hapus "+e);
            }
            finally{
                ViewTable(unit);
               }
        }
    }

    @Override
    public void ViewTable(FrmUnits unit) throws SQLException {
       unit.tblModel.getDataVector().removeAllElements();
        unit.tblModel.fireTableDataChanged();
        try {
            stmt = con.createStatement();
            SQL = "select * from units";
            res = stmt.executeQuery(SQL);
            while(res.next()){
                Object[] ob = new Object[2];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                unit.tblModel.addRow(ob);
            }
        } catch (SQLException e) {
             System.out.println(e);
        }
    }

    @Override
    public void ClickTable(FrmUnits unit) {
        try {
            int pilih = unit.tblUnits.getSelectedRow();
            if(pilih == -1){
                return;
            }
            unit.txtCode.setText(unit.tblModel.getValueAt(pilih,0).toString());
            unit.txtUnit.setText(unit.tblModel.getValueAt(pilih, 1).toString());
        } catch (Exception e) {
        }
    }
    
    private void clear(FrmUnits unit){
        unit.txtCode.setText("");
        unit.txtUnit.setText("");
    }
    
    private void nonAktifKolom(FrmUnits u){
        u.txtUnit.setEnabled(false);
        u.btnUpdate.setEnabled(false);
    }
    
}
