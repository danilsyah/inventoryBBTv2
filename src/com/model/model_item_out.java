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
import com.koneksi.DBKoneksi;
import com.view.frmItem_out;
import com.controller.controller_item_out;
import java.awt.HeadlessException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
/**
 *
 * @author danil
 */
public class model_item_out implements controller_item_out{
    
    private DefaultTableModel tblModelItemOut,tblModelItems,tblModelSN;
    private final String header_item_out[] = {"No","ID ITEM OUT","CODE ITEM","QTY","SN/MAC","KONDISI"};
    private final String header_items[] = {"No.","CODE","ITEM","CATEGORY","UNIT","STOCK MIN","STOCK","KONDISI"};
    private final String header_sn[] ={"No.","SN/MAC"};
    private SimpleDateFormat simpleTahun = new SimpleDateFormat("yy",Locale.getDefault());
    private final SimpleDateFormat simpleTahunFull = new SimpleDateFormat("yyyy",Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
    private Calendar cal = Calendar.getInstance();
    final Connection con = DBKoneksi.getKoneksi();
    private String SQL;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;
    private Integer row;
    private int qty;
    private String idItemOut,codeItem,serialNumber,stockTotal,kondisi;
    private Date DateNow = new java.util.Date();
    private Date tgl;
    ArrayList<model_item_out> listData = new ArrayList<>();
    
    public model_item_out(String idItemOut,String codeItem,int qty,String serialNumber,String kondisi)throws SQLException{
        this.idItemOut = idItemOut;
        this.codeItem = codeItem;
        this.qty = qty;
        this.serialNumber = serialNumber;
        this.kondisi = kondisi;
    }

    public model_item_out() {
        
    }
    
    private void isiData(String idItemOut, String codeItem,int qty,String serialNumber,String kondisi) throws SQLException{
        model_item_out out = new model_item_out(idItemOut, codeItem, qty, serialNumber, kondisi);
        listData.add(out);
    }
    
    private String getIdItemOut(){
        return idItemOut;
    }
    
    private String getCodeItem(){
        return codeItem;
    }
    
    private int getQty(){
        return qty;
    }
    
    private String getSerialNumber(){
        return serialNumber;
    }
    
    private String getKondisi(){
        return kondisi;
    }
    
   public  String getStockTotal(){
        return stockTotal;
    }

    @Override
    public void New(frmItem_out out) {
       String tahun = simpleTahun.format(DateNow);
       String tahunfull = simpleTahunFull.format(DateNow);
       String thn_old = "";
        try {
            stmt = con.createStatement();
            if(out.jCheckBox_proyek.isSelected()){
                SQL = " SELECT MAX(RIGHT(idItemOut,4)) AS idItemOut , DATE_FORMAT(tanggal,'%y') AS tanggal FROM item_out WHERE tanggal LIKE '%"+tahunfull+"%' AND idItemOut LIKE '%/2/%'";
                 res = stmt.executeQuery(SQL);
                  while(res.next()){
                if(res.first()==false){
                    out.txtIdItemOut.setText("BK-LGI"+tahun+"/2/0001");
                }else{
                    res.last();
                     thn_old = res.getString("tanggal");
                   if(thn_old != tahun){
                         int autoid = res.getInt(1)+1;
                        String nomor = String.valueOf(autoid);
                        int noLong = nomor.length();
                        for(int a = 0; a<4-noLong;a++){
                            nomor = "0"+nomor;
                        }
                        out.txtIdItemOut.setText("BK-LGI"+tahun+"/2/"+nomor);
                   }else{
                        out.txtIdItemOut.setText("BK-LGI"+tahun+"/2/0001");
                   }
                    
                }
            }
            }else{
                SQL = " SELECT MAX(RIGHT(idItemOut,4)) AS idItemOut , DATE_FORMAT(tanggal,'%y') AS tanggal FROM item_out WHERE tanggal LIKE '%"+tahunfull+"%' AND idItemOut LIKE '%/1/%'";
                 res = stmt.executeQuery(SQL);
                while(res.next()){
                if(res.first()==false){
                    out.txtIdItemOut.setText("BK-LGI"+tahun+"/1/0001");
                }else{
                    res.last();
                    thn_old = res.getString("tanggal");
                     if(thn_old != tahun){
                         int autoid = res.getInt(1)+1;
                        String nomor = String.valueOf(autoid);
                        int noLong = nomor.length();
                        for(int a = 0; a<4-noLong;a++){
                            nomor = "0"+nomor;
                        }
                        out.txtIdItemOut.setText("BK-LGI"+tahun+"/1/"+nomor);
                   }else{
                        out.txtIdItemOut.setText("BK-LGI"+tahun+"/1/0001");
                   }
                    
                }
            }
            }
           
            aktifKolom(out, true);
            setTanggal();
            out.jDateChooser_tgl.setDate(getTanggal());
            res.close();
            stmt.close();
        } catch (SQLException e) {
        }
    }

    @Override
    public void save(frmItem_out out) {
       int jml = Integer.parseInt(out.txtQty.getText());
       int stoktotal = Integer.parseInt(getStockTotal());
       
      if(out.lblCodeItem.getText().isEmpty()){
           JOptionPane.showMessageDialog(null, "Item belum di isi..!!!","Pesan",JOptionPane.INFORMATION_MESSAGE);
       }
       
       if(out.lbSN.getText().isEmpty()==false){
         if(jml>stoktotal){
              JOptionPane.showMessageDialog(null, "qty melebihi stok..!!!","Pesan",JOptionPane.INFORMATION_MESSAGE);
         }else if(jml!=1){
             JOptionPane.showMessageDialog(null, "Qty Harus Satu (1) Jika Anda Meng-Input SN..!!!","Pesan",JOptionPane.ERROR_MESSAGE);
         }else{
              try {
                  isiData(out.txtIdItemOut.getText(), out.lblCodeItem.getText(), jml, out.lbSN.getText(), out.cbKondisi.getSelectedItem().toString());
                    view_tbl_item_out(out);
                     out.btnPosting.setEnabled(true);
                     out.lblCodeItem.setText("");
                     out.lbItems.setText("");
                     out.lbSN.setText("");
                     out.lbUnit.setText("");
                     out.lbCategory.setText("");
                     out.txtQty.setText("");
                     out.cbKondisi.setSelectedItem("-PILIH-");
             } catch (Exception e) {
             }
         }
       }else{
           String SN = "-";
           if(jml>stoktotal){
            JOptionPane.showMessageDialog(null, "qty melebihi stok..!!!","Pesan",JOptionPane.INFORMATION_MESSAGE);
           }else{
               if(jml>=1){
                        try {
                        isiData(out.txtIdItemOut.getText(), out.lblCodeItem.getText(), jml, SN, out.cbKondisi.getSelectedItem().toString());
                        view_tbl_item_out(out);
                         out.btnPosting.setEnabled(true);
                         out.lblCodeItem.setText("");
                         out.lbItems.setText("");
                         out.lbSN.setText("");
                         out.lbUnit.setText("");
                         out.lbCategory.setText("");
                         out.txtQty.setText("");
                         out.cbKondisi.setSelectedItem("-PILIH-");
                   } catch (SQLException e) {
                   }
               }else{
                   JOptionPane.showMessageDialog(null, "Qty Min 1 (Satu)!!!","Pesan",JOptionPane.ERROR_MESSAGE);
               }
           }
       }
       
    }

    @Override
    public void Delete(frmItem_out out) {
       int a = out.tblItemOut.getSelectedRow();
       if(a==-1){
          JOptionPane.showMessageDialog(null, "Pilih Data Yang Terdapat Di Dalam Tabel List..!!!","Pesan",JOptionPane.ERROR_MESSAGE);
       }else{
           listData.remove(a);
       }
        view_tbl_item_out(out);
    }

    @Override
    public void view_tbl_item_out(frmItem_out out) {
       Object[][]objData = new Object[listData.size()][5];
       int a = 0;
       for(model_item_out n : listData){
           String[] arrData = {String.valueOf(a+1),n.getIdItemOut(),n.getCodeItem(),String.valueOf(n.getQty()),n.getSerialNumber(),n.getKondisi()};
           objData[a] = arrData;
           a++;
       }
       tblModelItemOut = new DefaultTableModel(objData,header_item_out){
           @Override
           public boolean isCellEditable(int rowIndex,int colIndex){
               return false;
            }
       };
       out.tblItemOut.setModel(tblModelItemOut);
       aturKolomTabel(out.tblItemOut, new int[]{50,200,80,80,150,80});
    }

    @Override
    public void view_tbl_items(frmItem_out out) {
        try {
            tblModelItems = new DefaultTableModel(null,header_items){
               @Override
               public boolean isCellEditable(int rowIndex,int columnIndex){
                   return false;
               }
            };
            out.tblItems.setModel(tblModelItems);
            aturKolomTabel(out.tblItems, new int[]{50,50,200,200,80,80,80,80});
            SQL = "SELECT * FROM v_stock_per_kondisi WHERE stockMin <= stock AND kondisi= '"+out.cbKondisi.getSelectedItem().toString()+"' ORDER BY itemName ";
            stmt = con.createStatement();
            res = stmt.executeQuery(SQL);
            int no = 0;
            while(res.next()){
                Object[]ob = new Object[8];
                no++;
                ob[0] = String.valueOf(no).toString();
                ob[1] = res.getString(1);
                ob[2] = res.getString(2);
                ob[3] = res.getString(3);
                ob[4] = res.getString(4);
                ob[5] = res.getString(5);
                ob[6] = res.getString(6);
                ob[7] = res.getString(7);
                tblModelItems.addRow(ob);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void view_tbl_sn(frmItem_out out, String codeItem,String kondisi) {
        tblModelSN = new DefaultTableModel(null,header_sn){
               @Override
               public boolean isCellEditable(int rowIndex,int columnIndex){
                   return false;
               }
            };
        out.tblSN.setModel(tblModelSN);
         aturKolomTabel(out.tblSN, new int[]{30,100});
        try {
            stmt = con.createStatement();
            SQL = "SELECT serialNumber FROM v_sn WHERE  codeItem ='"+codeItem+"' AND kondisi = '"+kondisi+"' ";
            res = stmt.executeQuery(SQL);
            int no = 0;
            while(res.next()){
                Object[] ob = new Object[2];
                no++;
                ob[0] = String.valueOf(no).toString();
                ob[1] = res.getString(1);
                tblModelSN.addRow(ob);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void search_items(frmItem_out out) {
        try {
                tblModelItems = new DefaultTableModel(null,header_items){
                   @Override
                   public boolean isCellEditable(int rowIndex,int columnIndex){
                       return false;
                   }
                };
            out.tblItems.setModel(tblModelItems);
            aturKolomTabel(out.tblItems, new int[]{50,50,200,200,80,80,80,80});
            stmt = con.createStatement();
            SQL = "SELECT * FROM v_stock_per_kondisi WHERE stockMin <= stock AND (codeItem LIKE '%"+out.txtCariItems.getText()+"%' OR"
                    + " itemName LIKE '%"+out.txtCariItems.getText()+"%' ) AND kondisi ='"+out.cbKondisi.getSelectedItem().toString()+"'";
            res = stmt.executeQuery(SQL);
            int no = 0;
            while(res.next()){
                Object[]ob = new Object[8];
                no++;
                ob[0] = String.valueOf(no).toString();
                ob[1] = res.getString(1);
                ob[2] = res.getString(2);
                ob[3] = res.getString(3);
                ob[4] = res.getString(4);
                ob[5] = res.getString(5);
                ob[6] = res.getString(6);
                ob[7] = res.getString(7);
                tblModelItems.addRow(ob);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void insert_item_out(frmItem_out out) throws SQLException {
        String tgl = dateFormat.format(out.jDateChooser_tgl.getDate());
        if(out.txtIdItemOut.getText().isEmpty()||out.txtRequest.getText().isEmpty()||out.txtDesc.getText().isEmpty()){
            JOptionPane.showMessageDialog(null,"Data Tidak Lengkap...!!!");
        }else{
            try {
                SQL ="INSERT INTO item_out(idItemOut,request,tanggal,description,idUser)VALUES(?,?,?,?,?)";
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1, out.txtIdItemOut.getText());
                pstmt.setString(2, out.txtRequest.getText());
                pstmt.setString(3, tgl);
                pstmt.setString(4, out.txtDesc.getText());
                pstmt.setInt(5, out.idUser);
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
            }
        }
    }

    @Override
    public void insert_item_out_detail(frmItem_out out) throws SQLException {
        try {
            stmt = con.createStatement();
            int baris = out.tblItemOut.getRowCount();
            for(int a=0; a<baris;a++){
                SQL = "INSERT INTO item_out_detail(idItemOut,codeItem,qty,serialNumber,kondisi) VALUES('"+out.tblItemOut.getValueAt(a, 1)+"',"
                        + "'"+out.tblItemOut.getValueAt(a, 2)+"','"+out.tblItemOut.getValueAt(a, 3)+"','"+out.tblItemOut.getValueAt(a, 4)+"','"+out.tblItemOut.getValueAt(a, 5)+"')";
                stmt.executeUpdate(SQL);
            }
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal disimpan","Pesan",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void getItems(frmItem_out out) {
       out.tblItems.setRowSelectionAllowed(true);
       row = out.tblItems.getSelectedRow();
       String code = out.tblItems.getValueAt(row, 1).toString();
       String item = out.tblItems.getValueAt(row, 2).toString();
       String category = out.tblItems.getValueAt(row, 3).toString();
       String satuan = out.tblItems.getValueAt(row, 4).toString();
       stockTotal = out.tblItems.getValueAt(row, 6).toString();
       out.lbItems.setText(item);
       out.lbCategory.setText(category);
       out.lbUnit.setText(satuan);
       out.lblCodeItem.setText(code);
        System.out.println(stockTotal);
    }

    @Override
    public void getSN(frmItem_out out) {
        out.tblSN.setRowSelectionAllowed(true);
        row = out.tblSN.getSelectedRow();
        String SN = out.tblSN.getValueAt(row, 1).toString();
        out.lbSN.setText(SN);
    }
    
    public void aktifKolom(frmItem_out out,boolean x){
        out.txtRequest.setEnabled(x);
        out.jDateChooser_tgl.setEnabled(x);
        out.txtDesc.setEnabled(x);
        out.txtQty.setEnabled(x);
    }
    
    private Date getTanggal(){
        return tgl;
    }
    
    private void setTanggal(){
        try {
            tgl= dateFormat.parse(dateFormat.format(cal.getTime()));
            
        } catch (Exception e) {
        }
    }

    @Override
    public void search_sn(frmItem_out out, String codeItem,String kondisi) {
       tblModelSN = new DefaultTableModel(null,header_sn){
               @Override
               public boolean isCellEditable(int rowIndex,int columnIndex){
                   return false;
               }
            };
        out.tblSN.setModel(tblModelSN);
         aturKolomTabel(out.tblSN, new int[]{30,100});
        try {
            stmt = con.createStatement();
            SQL = "SELECT serialNumber FROM v_sn WHERE  codeItem ='"+codeItem+"' AND kondisi = '"+kondisi+"' AND "
                    + "serialNumber LIKE '%"+ out.txtSearchSN.getText()+"%' ";
            res = stmt.executeQuery(SQL);
            int no = 0;
            while(res.next()){
                Object[] ob = new Object[2];
                no++;
                ob[0] = String.valueOf(no).toString();
                ob[1] = res.getString(1);
                tblModelSN.addRow(ob);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void aturKolomTabel(JTable tbl, int lebar[]){
        try {
            tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            int banyak = tbl.getColumnCount();
            for(int i=0;i<banyak;i++){
                TableColumn kolom = tbl.getColumnModel().getColumn(i);
                kolom.setPreferredWidth(lebar[i]);
                tbl.setRowHeight(20);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Salah"+e);
        }
    }
  
}
