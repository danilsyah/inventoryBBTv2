/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;
import  com.view.FrmKategori;
import java.sql.SQLException;

/**
 *
 * @author danil
 */
public interface controller_kategori {
    public void baru(FrmKategori kategori);
    public void simpan(FrmKategori kategori)throws SQLException;
    public void update(FrmKategori kategori)throws SQLException;
    public void hapus(FrmKategori kategori)throws SQLException;
    public void tampilTabel(FrmKategori kategori) throws SQLException;
    public void klikTabel(FrmKategori kategori);
    public void aktifKolom(FrmKategori kategori);
    public void nonAktifKolom(FrmKategori kategori);
}
