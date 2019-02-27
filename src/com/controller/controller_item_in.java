/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;
import com.view.FrmItem_in;
import java.sql.SQLException;
/**
 *
 * @author danil
 */
public interface controller_item_in {
    
    public void New(FrmItem_in i);
    public void Save(FrmItem_in i);
    public void Update(FrmItem_in i);
    public void Delete(FrmItem_in i);
    public void ViewTable(FrmItem_in i);
    public void ClickTable(FrmItem_in i);
    public void dialogItem(FrmItem_in i)throws SQLException;
    public void getItems(FrmItem_in i);
    public void SearchItems(FrmItem_in i )throws SQLException;
    public void process_item_in_detail(FrmItem_in i )throws SQLException;
    public void process_item_in(FrmItem_in i)throws SQLException;
} 
