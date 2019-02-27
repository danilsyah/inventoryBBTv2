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
import com.view.FrmTransferOrder;
import com.controller.controller_transfer_order;
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
public class model_transfer_order implements controller_transfer_order {

    private DefaultTableModel tblModelGudang, tblModelItems, tblModelSN, tblModelTO;
    private final String header_gudang[] = {"NO", "CODE", "WAREHOUSE", "LOCATION", "DESCRIPTION"};
    private final String header_items[] = {"No.", "CODE", "ITEM", "CATEGORY", "UNIT", "STOCK MIN", "STOCK", "KONDISI"};
    private final String header_sn[] = {"No.", "SN/MAC"};
    private final String header_transfer_order[] = {"No.", "ID TRANSFER ", "CODE ITEM", "QTY", "SN/MAC", "KONDISI"};
    final Connection con = DBKoneksi.getKoneksi();
    private Date DateNow = new java.util.Date();
    private SimpleDateFormat simpleTahun = new SimpleDateFormat("yy", Locale.getDefault());
    private final SimpleDateFormat simpleTahunFull = new SimpleDateFormat("yyyy", Locale.getDefault());
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-M-dd");
    private Calendar cal = Calendar.getInstance();
    private String SQL;
    private PreparedStatement pstmt;
    private Statement stmt;
    private ResultSet res;
    private Integer row;
    private Integer qty;
    private Date tgl;
    private String idTransferOrder, codeItem, serialNumber, kondisi;
    ArrayList<model_transfer_order> listData = new ArrayList<>();
    
    
    public model_transfer_order(){
    
    }

    public model_transfer_order(String idTransferOrder, String codeItem, int qty, String serialNumber, String kondisi) {
        this.idTransferOrder = idTransferOrder;
        this.codeItem = codeItem;
        this.qty = qty;
        this.serialNumber = serialNumber;
        this.kondisi = kondisi;
    }

    private String getIdTransferOrder() {
        return idTransferOrder;
    }

    private String getCodeItem() {
        return codeItem;
    }

    private Integer getQty() {
        return qty;
    }

    private String getSerialnumber() {
        return serialNumber;
    }

    private String getKondisi() {
        return kondisi;
    }

    private Date getTanggal_Now() {
        return tgl;
    }

    private void setTanggal_Now() {
        try {
            tgl = dateFormat.parse(dateFormat.format(cal.getTime()));
        } catch (ParseException ex) {
            Logger.getLogger(model_transfer_order.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void aturKolomTabel(JTable tbl, int lebar[]) {
        try {
            tbl.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
            int banyak = tbl.getColumnCount();
            for (int i = 0; i < banyak; i++) {
                TableColumn kolom = tbl.getColumnModel().getColumn(i);
                kolom.setPreferredWidth(lebar[i]);
                tbl.setRowHeight(20);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Salah" + e);
        }
    }

    @Override
    public void new_posting_transfer_order(FrmTransferOrder to) {
        String tahun = simpleTahun.format(DateNow);
        String tahunfull = simpleTahunFull.format(DateNow);
        String thn_old ="";
        try {
            stmt = con.createStatement();
            SQL = "SELECT MAX(RIGHT(idTransferOrder,4)) AS idTransferOrder , DATE_FORMAT(tanggal,'%y') AS tanggal FROM transfer_order WHERE tanggal LIKE '%"+tahunfull+"%'";
            res = stmt.executeQuery(SQL);
            while (res.next()) {
                if (res.first() == false) {
                    to.txtIdTransferOrder.setText("BP-LGI" + tahun + "/3/0001");
                } else {
                    res.last();
                    thn_old = res.getString("tanggal");
                    if(thn_old != tahun){
                        int autoid = res.getInt(1) + 1;
                        String nomor = String.valueOf(autoid);
                        int noLong = nomor.length();
                        for (int a = 0; a < 4 - noLong; a++) {
                             nomor = "0" + nomor;
                         }
                        to.txtIdTransferOrder.setText("BP-LGI" + tahun + "/3/" + nomor);
                    }else{
                          to.txtIdTransferOrder.setText("BP-LGI" + tahun + "/3/0001");
                    }
                    
                }
            }
            setTanggal_Now();
            to.jDateChooserTO.setDate(getTanggal_Now());
            res.close();
            stmt.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void add_item_transfer_order(FrmTransferOrder to) {
        int jml = Integer.parseInt(to.txtQty.getText());
        int totalStok = Integer.parseInt(to.getStockTotal());

        if (to.lbCodeItem.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Item belum di isi..!!!", "Pesan", JOptionPane.INFORMATION_MESSAGE);
        } else {
            if (jml <= totalStok) {
                if (to.lbSerialNumber.getText().isEmpty() == false) {
                    if (jml == 1) {
                        try {
                            add_Items(to.txtIdTransferOrder.getText(), to.lbCodeItem.getText(), jml, to.lbSerialNumber.getText(), to.CBkondisi.getSelectedItem().toString());
                            view_data_item_transfer_order(to);
                        } catch (SQLException e) {
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Qty Harus Satu (1) Jika Anda Meng-Input SN..!!!", "Pesan", JOptionPane.ERROR_MESSAGE);
                    }
                } else {
                    String SN = "-";
                    if (jml >= 1) {
                        try {
                            add_Items(to.txtIdTransferOrder.getText(), to.lbCodeItem.getText(), jml, to.lbSerialNumber.getText(), to.CBkondisi.getSelectedItem().toString());
                            view_data_item_transfer_order(to);
                        } catch (SQLException e) {
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Qty Min 1 (Satu)!!!", "Pesan", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } else {
                JOptionPane.showMessageDialog(null, "qty melebihi stok..!!!", "Pesan", JOptionPane.INFORMATION_MESSAGE);
            }
        }

    }

    private void add_Items(String idTransferOrder, String codeItem, int qty, String serialNumber, String kondisi) throws SQLException {
        model_transfer_order order = new model_transfer_order(idTransferOrder, codeItem, qty, serialNumber, kondisi);
        listData.add(order);
    }

    @Override
    public void delete_item_transfer_order(FrmTransferOrder to) {
        int a = to.tblTO.getSelectedRow();
        if (a == -1) {
            JOptionPane.showMessageDialog(null, "Pilih Data Yang Terdapat Di Dalam Tabel List..!!!", "Pesan", JOptionPane.ERROR_MESSAGE);
        } else {
            listData.remove(a);
        }
        view_data_item_transfer_order(to);
    }

    @Override
    public void view_data_gudang(FrmTransferOrder to) {

        tblModelGudang = new DefaultTableModel(null, header_gudang) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        aturKolomTabel(to.jTableGudang, new int[]{20, 100, 120, 120, 200});
        to.jTableGudang.setModel(tblModelGudang);
        try {
            stmt = con.createStatement();
            SQL = "SELECT codeWarehouse,warehouse,location,description FROM warehouse";
            res = stmt.executeQuery(SQL);
            int no = 0;
            while (res.next()) {
                Object[] ob = new Object[5];
                no++;
                ob[0] = String.valueOf(no).toString();
                ob[1] = res.getString(1);
                ob[2] = res.getString(2);
                ob[3] = res.getString(3);
                ob[4] = res.getString(4);
                tblModelGudang.addRow(ob);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    @Override
    public void view_data_items(FrmTransferOrder to) {
        try {
            tblModelItems = new DefaultTableModel(null, header_items) {
                @Override
                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return false;
                }
            };
            to.tblItems.setModel(tblModelItems);
            aturKolomTabel(to.tblItems, new int[]{50, 50, 200, 200, 80, 80, 80, 80});
            if (to.txtCariItems.getText().isEmpty()) {
                SQL = "SELECT * FROM v_stock_per_kondisi WHERE stockMin <= stock AND kondisi='" + to.CBkondisi.getSelectedItem().toString() + "'";
            } else {
                SQL = "SELECT * FROM v_stock_per_kondisi WHERE stockMin <= stock AND (codeItem LIKE '%" + to.txtCariItems.getText() + "%' OR"
                        + " itemName LIKE '%" + to.txtCariItems.getText() + "%' ) AND kondisi ='" + to.CBkondisi.getSelectedItem().toString() + "'";
            }

            stmt = con.createStatement();
            res = stmt.executeQuery(SQL);
            int no = 0;
            while (res.next()) {
                Object[] ob = new Object[8];
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
    public void view_data_sn(FrmTransferOrder to) {
        tblModelSN = new DefaultTableModel(null, header_sn) {
            @Override
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        };
        to.tblSN.setModel(tblModelSN);
        aturKolomTabel(to.tblSN, new int[]{30, 100});
        try {
            stmt = con.createStatement();
            if (to.txtSearchSN.getText().isEmpty()) {
                SQL = "SELECT serialNumber FROM v_sn WHERE  codeItem ='" + to.lbCodeItem.getText() + "' AND kondisi = '" + to.CBkondisi.getSelectedItem().toString() + "' ";
            } else {
                SQL = "SELECT serialNumber FROM v_sn WHERE  codeItem ='" + to.lbCodeItem.getText() + "' AND kondisi = '" + to.CBkondisi.getSelectedItem().toString() + "' AND "
                        + "serialNumber LIKE '%" + to.txtSearchSN.getText() + "%' ";
            }
            res = stmt.executeQuery(SQL);
            int no = 0;
            while (res.next()) {
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
    public void view_data_item_transfer_order(FrmTransferOrder to) {
        Object[][] objData = new Object[listData.size()][5];
        int a = 0;
        for (model_transfer_order n : listData) {
            String[] arrData = {String.valueOf(a + 1),
                n.getIdTransferOrder(),
                n.getCodeItem(),
                String.valueOf(n.getQty()),
                n.getSerialnumber(),
                n.getKondisi()};
            objData[a] = arrData;
            a++;
        }
        tblModelTO = new DefaultTableModel(objData, header_transfer_order) {
            @Override
            public boolean isCellEditable(int rowIndex, int colIndex) {
                return false;
            }
        };
        to.tblTO.setModel(tblModelTO);
        aturKolomTabel(to.tblTO, new int[]{50, 200, 80, 80, 150, 80});
    }

    @Override
    public void insert_posting_transfer_order(FrmTransferOrder to) {
        String tanggal = dateFormat.format(to.jDateChooserTO.getDate());
        if (to.txtIdTransferOrder.getText().isEmpty() || to.txtCdWarehouse.getText().isEmpty() || to.txtKeterangan.getText().isEmpty()) {
            JOptionPane.showMessageDialog(null, "Data Tidak Lengkap...!!!");
        } else {
            try {
                SQL = "INSERT INTO transfer_order(idTransferOrder,tanggal,codeWarehouse,description,idUser)VALUES(?,?,?,?,?)";
                pstmt = con.prepareStatement(SQL);
                pstmt.setString(1, to.txtIdTransferOrder.getText());
                pstmt.setString(2, tanggal);
                pstmt.setString(3, to.txtCdWarehouse.getText());
                pstmt.setString(4, to.txtKeterangan.getText());
                pstmt.setInt(5, to.idUser);
                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("com.model.model_transfer_order.insert_posting_transfer_order() " + e);
            }
        }
    }

    @Override
    public void insert_posting_detail_transfer_order(FrmTransferOrder to) {
        try {
            stmt = con.createStatement();
            int baris = to.tblTO.getRowCount();
            for (int a = 0; a < baris; a++) {
                SQL = "INSERT INTO transfer_order_detail(idTransferOrder,codeItem,qty,serialNumber,kondisi) VALUES('" + to.tblTO.getValueAt(a, 1) + "','" + to.tblTO.getValueAt(a, 2) + "',"
                        + "'" + to.tblTO.getValueAt(a, 3) + "','" + to.tblTO.getValueAt(a, 4) + "','" + to.tblTO.getValueAt(a, 5) + "') ";
                stmt.executeUpdate(SQL);
            }
            stmt.close();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Data Item Gagal disimpan", "Pesan", JOptionPane.ERROR_MESSAGE);
        }
    }

}
