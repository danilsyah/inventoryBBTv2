/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;
import com.view.frmItem_out;
import java.sql.SQLException;
/**
 *
 * @author danil
 */
public interface controller_item_out {
    public void New(frmItem_out out);
    public void save(frmItem_out out);
    public void Delete(frmItem_out out);
    public void view_tbl_item_out(frmItem_out out);
    public void view_tbl_items(frmItem_out out);
    public void view_tbl_sn(frmItem_out out, String codeItem,String kondisi);
    public void search_items(frmItem_out out);
    public void search_sn(frmItem_out out, String codeItem,String kondisi);
    public void getItems(frmItem_out out);
    public void getSN(frmItem_out out);
    public void insert_item_out(frmItem_out out)throws SQLException;
    public void insert_item_out_detail(frmItem_out out)throws SQLException;
}
