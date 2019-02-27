/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;

import com.controller.controller_kategori;
import com.koneksi.DBKoneksi;
import com.view.FrmKategori;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;

/**
 *
 * @author danil
 */
public class model_kategori implements controller_kategori{
    
    final Connection con = DBKoneksi.getKoneksi();
    private String SQL;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;
    
    @Override
    public void baru(FrmKategori kategori) {
        try {
            stmt=con.createStatement();
            SQL = "select max(right(codeCategory,3)) from category";
            res = stmt.executeQuery(SQL);
            while(res.next()){
                if(res.first()==false){
                    kategori.txtKode.setText("CG001");
                }else{
                    res.last();
                    int autoid = res.getInt(1)+1;
                    String nomor = String.valueOf(autoid);
                    int noLong = nomor.length();
                    for(int a = 0; a<3-noLong;a++){
                        nomor = "0"+nomor;
                    }
                    kategori.txtKode.setText("CG"+nomor);
                }
            }
            res.close();
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, e);
        }
        kategori.txtKategori.setText("");
    }

    @Override
    public void simpan(FrmKategori kategori) throws SQLException {
       if(kategori.txtKode.getText().isEmpty()||kategori.txtKategori.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Data Tidak Lengkap...!!!");
       }else{
           try {
            SQL = "insert into category values(?,?)";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, kategori.txtKode.getText());
            pstmt.setString(2, kategori.txtKategori.getText());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null,"Data Berhasil Di Simpan");
            pstmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null,"Data Gagal Di Simpan");
            System.out.println(e);
        }
        
        finally{
            tampilTabel(kategori);
             clear(kategori);
        }
       }
    }

    @Override
    public void update(FrmKategori kategori) throws SQLException {
       int ok = JOptionPane.showConfirmDialog(null, "Data Akan Di Ubah..???","Konfirmasi",JOptionPane.YES_NO_OPTION);
       if(ok==0){
            try {
                SQL = "update category set categoryName = ? where codeCategory = ?";
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(2, kategori.txtKode.getText());
                pstmt.setString(1, kategori.txtKategori.getText());
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null,"Update Berhasil");
                pstmt.close();
           } catch (SQLException e) {
               JOptionPane.showMessageDialog(null,"Update Gagal" + e);
           }
             finally{
               tampilTabel(kategori);
                nonAktifKolom(kategori);
               }
       }
       else{
           nonAktifKolom(kategori);
       }
      
    }

    @Override
    public void hapus(FrmKategori kategori) throws SQLException {
        int ok = JOptionPane.showConfirmDialog(null, "Data Akan Di Hapus..???","Konfirmasi",JOptionPane.YES_NO_OPTION);
        if(ok == 0){
            try {
                SQL = "delete from category where codeCategory = ?";
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1, kategori.txtKode.getText());
                pstmt.executeUpdate();
                 JOptionPane.showMessageDialog(null, "Data berhasil di hapus");
                 pstmt.close();
                 clear(kategori);
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(null, "Data Gagal di hapus "+e);
            }
            finally{
               tampilTabel(kategori);
               }
        }
        
    }

    @Override
    public void tampilTabel(FrmKategori kategori) throws SQLException {
        kategori.tblModel.getDataVector().removeAllElements();
        kategori.tblModel.fireTableDataChanged();
        try {
            stmt = con.createStatement();
            SQL = "select * from category";
            res = stmt.executeQuery(SQL);
            while(res.next()){
                Object[] ob = new Object[2];
                ob[0] = res.getString(1);
                ob[1] = res.getString(2);
                kategori.tblModel.addRow(ob);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void klikTabel(FrmKategori kategori) {
        try {
            int pilih = kategori.jTable_kategori.getSelectedRow();
            if(pilih == -1){
                return;
            }
            kategori.txtKode.setText(kategori.tblModel.getValueAt(pilih, 0).toString());
            kategori.txtKategori.setText(kategori.tblModel.getValueAt(pilih, 1).toString());
        } catch (Exception e) {
        }
    }
    
    private void clear(FrmKategori kategori){
        kategori.txtKode.setText("");
        kategori.txtKategori.setText("");
    }

    @Override
    public void aktifKolom(FrmKategori kategori) {
        kategori.txtKategori.setEnabled(true);
        kategori.txtKategori.requestFocus();
    }

    @Override
    public void nonAktifKolom(FrmKategori kategori) {
        kategori.txtKode.setEnabled(false);
        kategori.txtKategori.setEnabled(false);
    }

}
