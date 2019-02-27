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
import com.view.*;
import com.controller.controller_user;
/**
 *
 * @author danil
 */
public class model_user  implements controller_user{
    
    final Connection con = DBKoneksi.getKoneksi();
    private String SQL;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;
    String username,pwd;
    char [] pass;
    int idUser;
    String nama;
    @Override
    public void Login(FrmLogin login) throws SQLException {
         String username = login.txtUser.getText();
         char [] pass = login.txtPass.getPassword();
         String pwd = String.copyValueOf(pass);
         
         if(username.length()==0 && pass.length==0){
              JOptionPane.showMessageDialog(null, "username and password belum di isi ! mohon lengkapi data login");
              login.txtUser.requestFocus();
         }else if(pass.length==0){
             JOptionPane.showMessageDialog(null, "password belum di isi ! mohon lengkapi data login");
             login.txtPass.requestFocus();
         }else if(username.length()==0 ){
             JOptionPane.showMessageDialog(null, "user belum di isi ! mohon lengkapi data login");
             login.txtUser.requestFocus();
         }
          else{
             if(prosesLogin(username, pwd)){
                  JOptionPane.showMessageDialog(null, "Selamat Datang "+" ' " + getNama() +" ' ");
                  new FrmHome(getIdUser(),getNama()).setVisible(true);
                  sessionLogin();
                  login.dispose();
             }
             else{
                  JOptionPane.showMessageDialog(null,"Login Gagal");
                  login.txtUser.requestFocus();
             }
         }
           
    }
    
    private String getNama(){
        return nama;
    }
    
    private int getIdUser(){
        return idUser;
    }
    
    public void sessionLogin(){
        System.out.println("nama :  "+getNama());
        System.out.println("id user : "+getIdUser());
    }
    
    private boolean prosesLogin(String username,String password){
         try {
            SQL = "SELECT * FROM users WHERE username=? and pass=?";
            pstmt = con.prepareStatement(SQL);
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            res = pstmt.executeQuery();
            if(res.next()){
                idUser = res.getInt("idUser");
                nama = res.getString("nameUser");
                return true;
            }else{
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    
}
