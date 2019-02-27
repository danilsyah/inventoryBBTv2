/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;
import com.view.FrmItem;
import java.sql.SQLException;
/**
 *
 * @author danil
 */
public interface controller_item {
    
    public void New(FrmItem item );
    public void Save(FrmItem item) throws SQLException;
    public void Update(FrmItem item) throws SQLException;
    public void Delete(FrmItem item)throws SQLException;
    public void ViewTable(FrmItem item)throws SQLException;
    public void ClickTable(FrmItem item);
    public void ListCategory(FrmItem item)throws SQLException;
    public void ListUnits(FrmItem item)throws SQLException;
    public void Searching_Items(FrmItem item)throws SQLException;
    public void Pagination(FrmItem item, int limit,int getpagesize )throws SQLException;
    public  String JmlRecord(FrmItem i)throws SQLException;
}
