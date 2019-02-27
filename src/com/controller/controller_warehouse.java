/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;
import java.sql.SQLException;
import com.view.FrmWarehouse;
/**
 *
 * @author danil
 */
public interface controller_warehouse {
    
    public void New (FrmWarehouse wh);
    public void Save(FrmWarehouse wh)throws SQLException;
    public void Update(FrmWarehouse wh)throws SQLException;
    public void Delete(FrmWarehouse wh)throws SQLException;
    public void ViewTable(FrmWarehouse wh)throws SQLException;
    public void ClickTable(FrmWarehouse wh);
    
}
