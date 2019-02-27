/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;
import com.view.FrmUnits;
import java.sql.SQLException;

/**
 *
 * @author danil
 */
public interface controller_units {
    
    public void New(FrmUnits unit);
    public void Save(FrmUnits unit)throws SQLException;
    public void Update(FrmUnits unit)throws SQLException;
    public void Delete(FrmUnits unit)throws SQLException;
    public void ViewTable(FrmUnits unit)throws SQLException;
    public void ClickTable(FrmUnits unit);
}
