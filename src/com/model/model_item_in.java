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
import com.view.FrmItem_in;
import com.view.FrmItem;
import com.controller.controller_item_in;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import java.text.SimpleDateFormat;
import java.util.Locale;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
/**
 *
 * @author danil
 */
public class model_item_in implements controller_item_in {
    final Connection con = DBKoneksi.getKoneksi();
    private String SQL;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;
    final FrmItem item =  new FrmItem();
    private Integer row;
    private String idItemIn,codeItem,serialnumber,kondisi;
    private int qty;    
    ArrayList<model_item_in> listData = new ArrayList<>();
    private final SimpleDateFormat simpleTahun = new SimpleDateFormat("yy",Locale.getDefault());
    private final SimpleDateFormat simpleTahunFull = new SimpleDateFormat("yyyy",Locale.getDefault());
     java.util.Date DateNow = new java.util.Date();
    private final String[] kolom ={"ID ITEM IN","CODE ITEM","QTY","SN/MAC","KONDISI"};
    private final String [] kolomItems={"CODE","ITEM","CATEGORY","UNIT","STOCK MIN"};
  
    
    
    public model_item_in() throws SQLException {
     
    }
    
    public model_item_in(String idItemIn, String codeItem, int qty,String serialnumber,String kondisi)throws SQLException{
        this.idItemIn = idItemIn;
        this.codeItem = codeItem;
        this.qty = qty;
        this.serialnumber = serialnumber;
        this.kondisi = kondisi;
    }
    
    public String getIdItemIn(){
        return idItemIn;
    }
    public String getCodeItem(){
        return codeItem;
    }
    
    public int getQty(){
        return qty;
    }
    
    public String getSerialNumber(){
        return serialnumber;
    }
    public String getKondisi(){
        return kondisi;
    }
    
    public void clearKolom(FrmItem_in i){
         i.txtcodeItem.setText("");
           i.lbItem.setText("");
            i.txtQty.setText("");
            i.txtSN.setText("");
           i. lbKategori.setText("");
           i. lbSatuan.setText("");
           i.buttonGroup1.clearSelection();
    }
    
    
    @Override
    public void New(FrmItem_in i) {
        String tahun = simpleTahun.format(DateNow);
        String tahunfull = simpleTahunFull.format(DateNow);
        String thn_old ="";
        
        try {
            stmt = con.createStatement();
            SQL = "SELECT MAX(RIGHT(idItemIn,4)) as idItemIn, DATE_FORMAT(tanggal,'%y') as tanggal FROM item_in where tanggal LIKE  '%"+tahunfull+"%'  ";
            res = stmt.executeQuery(SQL);
            while(res.next()){
                if(res.first()==false){
                    i.txtIdItemIn.setText("BM-LGI"+tahun+"/0/0001");
                     System.out.println("data pertama");
                }
                else{
                    res.last();
                    thn_old = res.getString("tanggal");
                    
                    if(thn_old != tahun){
                        int autoid = res.getInt(1) + 1;
                        String nomor = String.valueOf(autoid);
                        int noLong = nomor.length();
                        for (int a = 0; a < 4 - noLong; a++) {
                            nomor = "0" + nomor;
                        }
                        i.txtIdItemIn.setText("BM-LGI" + tahun + "/0/" + nomor);
                    }else{
                         i.txtIdItemIn.setText("BM-LGI"+tahun+"/0/0001");
                        System.out.println("Kode Reload");
                        System.out.println(thn_old + "="+tahun);
                        System.out.println("nilai = "+ thn_old == tahun );
                    }
                }
            }
            aktifKolom(i, true);
            res.close();
            stmt.close();
        } catch (SQLException e) {
        }
    }

    //fungsi tambah item ke dalam tabel
    @Override
    public void Save(FrmItem_in i) {
        String kondisi1 = null;
        int qty1=0;
         qty1 = Integer.parseInt(i.txtQty.getText());
        //pilihan combo box
        if(i.RBnew.isSelected()){
           kondisi1 =  i.RBnew.getText();
        }else if(i.RBsecond.isSelected()){
           kondisi1 =  i.RBsecond.getText();
        }
        
        if(i.txtSN.getText().isEmpty()==false){
             if(qty1<1 || qty1>1){
                JOptionPane.showMessageDialog(null, "Qty Harus Satu (1) Jika Anda Meng-Input SN..!!!","Pesan",JOptionPane.ERROR_MESSAGE);
            }
             else if(qty1==1){
                     try {
                          isiData(i.txtIdItemIn.getText(), i.txtcodeItem.getText(),qty1,i.txtSN.getText(),kondisi1 );
                          clearKolom(i);
                    } catch (SQLException ex) {
                        Logger.getLogger(model_item_in.class.getName()).log(Level.SEVERE, null, ex);
                    }
             }
        }else{
            String SN = "-";
            if(qty1<1){
                 JOptionPane.showMessageDialog(null, "Qty Min 1 (Satu)!!!","Pesan",JOptionPane.ERROR_MESSAGE);
            }else{
                try {
                    isiData(i.txtIdItemIn.getText(), i.txtcodeItem.getText(),qty1,SN,kondisi1 );
                    clearKolom(i);
                } catch (SQLException ex) {
                    Logger.getLogger(model_item_in.class.getName()).log(Level.SEVERE, null, ex);
                }
             }
        }
         ViewTable(i);
    }

    @Override
    public void Update(FrmItem_in i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void Delete(FrmItem_in i) {
        int a = i.jTableItemIn.getSelectedRow();
        if(a==-1){
            
        }else{
            listData.remove(a);
        }
        ViewTable(i);
    }

    @Override
    public void ViewTable(FrmItem_in i) {
       
       Object[][]objData = new Object[listData.size()][5];
       int a = 0;
       for(model_item_in n : listData){
           String[] arrData = {n.getIdItemIn(),n.getCodeItem(),String.valueOf(n.getQty()),n.getSerialNumber(),n.getKondisi()};
           objData[a] = arrData;
           a++;
       }
       i.tblModel1 = new DefaultTableModel(objData,kolom){
         @Override
         public boolean isCellEditable(int rowIndex,int colIndex){
             return false;
         }
       };
       i.jTableItemIn.setModel(i.tblModel1);
        aturKolomTabel(i.jTableItemIn, new int[]{150,150,60,100,80});
    }
   
    
    
    public void isiData(String idItemIn, String codeItem, int qty, String serialnumber, String kondisi) throws SQLException{
        model_item_in in = new model_item_in(idItemIn, codeItem, qty, serialnumber, kondisi);
        listData.add(in);
    }

    @Override
    public void ClickTable(FrmItem_in i) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void dialogItem(FrmItem_in i) throws SQLException {

        try {
                    i.tblModel2 = new DefaultTableModel(null,kolomItems){
                    @Override
                    public boolean isCellEditable(int rowIndex,int colIndex){
                        return false;
                    }
                  };
                   i.tblItems.setModel(i.tblModel2);
                   aturKolomTabel(i.tblItems,new int[]{50,200,150,80,80});
                    stmt = con.createStatement();
                    SQL ="SELECT * FROM v_items";
                    res = stmt.executeQuery(SQL);
                   while(res.next()){
                  Object[] ob = new Object[6];
                  ob[0] = res.getString(1);
                  ob[1] = res.getString(2);
                  ob[2] = res.getString(3);
                  ob[3] = res.getString(4);
                  ob[4] = res.getString(5);
                  i.tblModel2.addRow(ob);
              }
                   
        } catch (SQLException e) {
            System.out.println(e);
        }
      
    }

    @Override
    public void getItems(FrmItem_in i) {
            i.tblItems.setRowSelectionAllowed(true);
            row = i.tblItems.getSelectedRow();
            String code = i.tblItems.getValueAt(row, 0).toString();
            String item1 = i.tblItems.getValueAt(row, 1).toString();
            String kategori = i.tblItems.getValueAt(row, 2).toString();
            String satuan = i.tblItems.getValueAt(row, 3).toString();
            i.txtcodeItem.setText(code);
            i.lbItem.setText(item1);
            i.lbKategori.setText(kategori);
            i.lbSatuan.setText(satuan);
        
    }

    @Override
    public void SearchItems(FrmItem_in i) throws SQLException {
        
        try {
                     i.tblModel2 = new DefaultTableModel(null,item.header){
                   @Override
                   public boolean isCellEditable(int rowIndex,int columnIndex){
                       return false;
                   }
                };
                 i.tblItems.setModel(i.tblModel2);
                aturKolomTabel(i.tblItems, new int[]{50,200,150,80,80});
                    stmt = con.createStatement();
                    SQL ="SELECT * FROM v_items WHERE codeItem LIKE '%"+i.txtSearchItemDialog.getText()+"%' OR"
                    + " itemName LIKE '%"+i.txtSearchItemDialog.getText()+"%'";
                    res = stmt.executeQuery(SQL);
                   while(res.next()){
                  Object[] ob = new Object[6];
                  ob[0] = res.getString(1);
                  ob[1] = res.getString(2);
                  ob[2] = res.getString(3);
                  ob[3] = res.getString(4);
                  ob[4] = res.getString(5);
                  i.tblModel2.addRow(ob);
              }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void process_item_in_detail(FrmItem_in i) throws SQLException {
        try {
            stmt = con.createStatement();
            int baris = i.jTableItemIn.getRowCount();
            for(int a = 0; a<baris; a++){
                SQL = "INSERT INTO item_in_detail(idItemIn,codeItem,qty,serialNumber,kondisi) VALUES('"+i.jTableItemIn.getValueAt(a, 0)+"','"+i.jTableItemIn.getValueAt(a, 1)+"',"
                        + "'"+i.jTableItemIn.getValueAt(a, 2)+" ','"+i.jTableItemIn.getValueAt(a, 3)+"','"+i.jTableItemIn.getValueAt(a, 4)+"')";
                stmt.executeUpdate(SQL);
            }
            stmt.close();
           // JOptionPane.showMessageDialog(null, "Data Berhasil disimpan","Pesan",JOptionPane.INFORMATION_MESSAGE);
        } catch (HeadlessException | SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Gagal disimpan","Pesan",JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void process_item_in(FrmItem_in i) throws SQLException {
       String tgl = i.dateFormat.format(i.jDateChooserTgl.getDate());
       if(i.txtIdItemIn.getText().isEmpty()||i.jDateChooserTgl.getDate().equals("")||i.txtDesc.getText().isEmpty()||i.cbSourceItem.getSelectedItem().equals("-select-")){
           JOptionPane.showMessageDialog(null,"Data Tidak Lengkap...!!!");
       }else{
           try {
               SQL = "INSERT INTO item_in(idItemIn,sourceItemIn,tanggal,description,idUser) VALUES(?,?,?,?,?)";
               pstmt = con.prepareStatement(SQL);
               pstmt.setString(1, i.txtIdItemIn.getText());
               pstmt.setString(2, i.cbSourceItem.getSelectedItem().toString());
               pstmt.setString(3, tgl);
               pstmt.setString(4, i.txtDesc.getText());
               pstmt.setInt(5, i.idUser);
               pstmt.executeUpdate();
               pstmt.close();
           } catch (SQLException e) {
           }
       }
    }

    public void aktifKolom(FrmItem_in i,boolean x){
        i.txtDesc.setEnabled(x);
        i.txtQty.setEnabled(x);
        i.jDateChooserTgl.setEnabled(x);

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
