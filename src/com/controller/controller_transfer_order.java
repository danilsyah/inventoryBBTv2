/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;
import com.view.FrmTransferOrder;
import java.sql.SQLException;
/**
 *
 * @author danil
 */
public interface controller_transfer_order {
    
    public void new_posting_transfer_order (FrmTransferOrder to);
    public void add_item_transfer_order(FrmTransferOrder to);
    public void delete_item_transfer_order(FrmTransferOrder to);
    public void view_data_gudang(FrmTransferOrder to);
    public void view_data_items(FrmTransferOrder to);
    public void view_data_sn(FrmTransferOrder to);
    public void view_data_item_transfer_order(FrmTransferOrder to);
    public void insert_posting_transfer_order(FrmTransferOrder to);
    public void insert_posting_detail_transfer_order(FrmTransferOrder to);
}
