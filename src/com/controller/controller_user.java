/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;
import com.view.FrmLogin;
import java.sql.SQLException;
/**
 *
 * @author danil
 */
public interface controller_user  {
    public void Login(FrmLogin login)throws SQLException; 
}
