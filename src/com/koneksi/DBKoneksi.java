/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.koneksi;
import java.sql.Connection;
import java.sql.DriverManager;
/**
 *
 * @author danil
 */
public class DBKoneksi {
    
       private static Connection koneksi;
       
       public static Connection getKoneksi(){
        if(koneksi == null){
            try {
                String url="jdbc:mysql://localhost/db_inventaris_bbt";
                String username ="root";
                String pass ="";
                
                DriverManager.registerDriver(new com.mysql.jdbc.Driver());
                koneksi = DriverManager.getConnection(url,username,pass);
                System.out.println("Koneksi ke Database Success");
            } catch (Exception e) {
                System.out.println("Gagal Koneksi ke Database");
            }
        }
        return koneksi;
    }
    
}
