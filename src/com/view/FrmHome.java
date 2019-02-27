/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import javax.swing.table.DefaultTableModel;
import com.koneksi.DBKoneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JTable;
import javax.swing.table.TableColumn;
/**
 *
 * @author danil
 */
public class FrmHome extends javax.swing.JFrame implements Runnable{
    private DefaultTableModel tblModelStokMin,tblModelViewItemSN;
    private final String header_tblStokMin[] = {"No","Code","Item","Category","UoM","Stock Min","Stock","Kondisi"};
    private final String header_tblItemSN[] = {"No","Code","ItemName","Serial/MAC","Category","UoM","Kondisi"};
    final Connection con = DBKoneksi.getKoneksi();
    java.util.Date DateNow = new java.util.Date();
     private String SQL;
     private PreparedStatement pstmt;
     private Statement stmt;
    private ResultSet res;
    //format penulisan tanggal, bisa di ubah sesuai keinginan
    private SimpleDateFormat simpledateformat = new SimpleDateFormat("dd MMMMMMMMMM yyyy",Locale.getDefault());
    private String tanggal = simpledateformat.format(DateNow);
    String nama;
    int idUser;
    
    //text berjalan
    private Thread T;
    boolean kanan = true;
    boolean kiri = false;
    boolean berjalan = true;
    int x,y;
    /**
     * Creates new form FrmHome
     */
    public FrmHome(int idUser,String nama) {
        initComponents();
        view_stok_min(tblStokMin);
        set_view_item_sn(jTable_list_itemSN, txtCariItemSN.getText());
        this.nama = nama;
        this.idUser = idUser;
        
        lblTgl.setText(tanggal);
        setJam();
        lblUser.setText(idUser +":"+nama);
        x = lbText.getX();
        y = lbText.getY();
        T = new Thread(this);
        T.start();
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

    
    public final void setJam(){
        ActionListener taskPerformer = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nol_jam = "", nol_menit = "",nol_detik="";
                java.util.Date dateTime = new java.util.Date();
                int nilai_jam = dateTime.getHours();
                int nilai_menit = dateTime.getMinutes();
                int nilai_detik = dateTime.getSeconds();
                
                if(nilai_jam <= 9 ) nol_jam="0";
                if(nilai_menit<=9)nol_menit="0";
                if(nilai_detik<=9)nol_detik="0";
                
                String jam  = nol_jam + Integer.toString(nilai_jam);
                String menit = nol_menit + Integer.toString(nilai_menit);
                String detik = nol_detik + Integer.toString(nilai_detik);
                
                lblTime.setText(jam+":"+menit+":"+detik+"");
            }
        };
        new Timer(1000,taskPerformer).start();
    }
    
    private void view_stok_min(JTable tbl){
       tblModelStokMin = new DefaultTableModel(null,header_tblStokMin){
           @Override
           public boolean isCellEditable(int rowIndex, int columnIndex){
               return false;
           }
       };
       tbl.setModel(tblModelStokMin);
        aturKolomTabel(tbl, new int[]{30,50,300,150,70,70,80,70});
        try {
            stmt = con.createStatement();
            SQL = "   SELECT * FROM v_stock_per_kondisi WHERE stock <= stockMin + 2 ORDER BY itemName ASC  ";
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
                tblModelStokMin.addRow(ob);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
    
    private void set_view_item_sn(JTable tbl,String keyword){
       
         try {
              tblModelViewItemSN = new DefaultTableModel(null, header_tblItemSN) {
                 @Override
                 public boolean isCellEditable(int rowIndex, int columnIndex) {
                     return false;
                 }
             };
             tbl.setModel(tblModelViewItemSN);
             aturKolomTabel(tbl, new int[]{40,60, 300, 150, 130, 70,70});
             if(keyword.isEmpty()){
                 SQL = "select * from v_item_sn";
             }else{
                 SQL = "select * from v_item_sn where codeItem like '%"+keyword+"%'  or itemName like '%"+keyword+"%' or serialNumber like '%"+keyword+"%'  or categoryName like '%"+keyword+"%' ";
             }
             stmt = con.createStatement();
             res = stmt.executeQuery(SQL);
             int no = 0;
             while (res.next()){
                 Object[]ob = new Object[7];
                 no++;
                 ob[0] = String.valueOf(no).toString();
                 ob[1] = res.getString(1);
                 ob[2] = res.getString(2);
                 ob[3] = res.getString(3);
                 ob[4] = res.getString(4);
                 ob[5] = res.getString(5);
                 ob[6] = res.getString(6);
                 tblModelViewItemSN.addRow(ob);
             }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        lbText = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        lblTime = new javax.swing.JLabel();
        lblTgl = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStokMin = new javax.swing.JTable();
        btnRefresh = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable_list_itemSN = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        txtCariItemSN = new javax.swing.JTextField();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu = new javax.swing.JMenu();
        jMenuItem_category = new javax.swing.JMenuItem();
        jMenuItem_units = new javax.swing.JMenuItem();
        jMenuItem_items = new javax.swing.JMenuItem();
        jMenuItem_warehouse = new javax.swing.JMenuItem();
        jMenu_management = new javax.swing.JMenu();
        jMenuItemBarangMasuk = new javax.swing.JMenuItem();
        jMenuItem_brg_keluar = new javax.swing.JMenuItem();
        jMenuItem_transfer_order = new javax.swing.JMenuItem();
        jMenu_report = new javax.swing.JMenu();
        jMenuItem_lap_barangMasuk = new javax.swing.JMenuItem();
        jMenuItem_lap_brg_keluar = new javax.swing.JMenuItem();
        jMenuItem_lap_transfer_order = new javax.swing.JMenuItem();
        jMenuItem_stock = new javax.swing.JMenuItem();
        jMenu_logout = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 153, 255));

        jPanel3.setBackground(new java.awt.Color(102, 153, 255));

        lbText.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lbText.setForeground(new java.awt.Color(204, 0, 0));
        lbText.setText("~~ Sistem Inventory Asset Perangkat Jaringan PT.Batam Bintan Telekomunikasi ~~");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbText, javax.swing.GroupLayout.DEFAULT_SIZE, 1183, Short.MAX_VALUE)
                .addGap(27, 27, 27))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lbText)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));

        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel1.setText("Login :");

        lblUser.setForeground(new java.awt.Color(255, 255, 255));
        lblUser.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblUser.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        lblTime.setForeground(new java.awt.Color(255, 255, 255));
        lblTime.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTime.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        lblTgl.setForeground(new java.awt.Color(255, 255, 255));
        lblTgl.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lblTgl.setBorder(javax.swing.BorderFactory.createTitledBorder(""));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblTime, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblTgl, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(16, 16, 16))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(lblTgl, javax.swing.GroupLayout.DEFAULT_SIZE, 28, Short.MAX_VALUE)
                            .addComponent(lblTime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lblUser, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Daftar Stok Minimum", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        tblStokMin.setBorder(javax.swing.BorderFactory.createTitledBorder(""));
        tblStokMin.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblStokMin);

        btnRefresh.setBackground(new java.awt.Color(255, 0, 0));
        btnRefresh.setText("Refresh");
        btnRefresh.setFocusCycleRoot(true);
        btnRefresh.setFocusPainted(false);
        btnRefresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRefreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 591, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnRefresh)))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(btnRefresh)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Daftar MAC/SN Perangkat", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 14))); // NOI18N

        jTable_list_itemSN.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable_list_itemSN);

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel2.setText("Cari");

        txtCariItemSN.setBackground(new java.awt.Color(153, 255, 153));
        txtCariItemSN.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCariItemSNKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtCariItemSN, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCariItemSN, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenuBar1.setBackground(new java.awt.Color(0, 0, 0));
        jMenuBar1.setBorder(null);

        jMenu.setBackground(new java.awt.Color(0, 0, 0));
        jMenu.setForeground(new java.awt.Color(255, 255, 255));
        jMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Data_Recovery_48px.png"))); // NOI18N
        jMenu.setText("Master");

        jMenuItem_category.setText("Kategori");
        jMenuItem_category.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_categoryActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItem_category);

        jMenuItem_units.setText("Satuan");
        jMenuItem_units.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_unitsActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItem_units);

        jMenuItem_items.setText("Barang");
        jMenuItem_items.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_itemsActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItem_items);

        jMenuItem_warehouse.setText("Gudang");
        jMenuItem_warehouse.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_warehouseActionPerformed(evt);
            }
        });
        jMenu.add(jMenuItem_warehouse);

        jMenuBar1.add(jMenu);

        jMenu_management.setBackground(new java.awt.Color(0, 0, 0));
        jMenu_management.setForeground(new java.awt.Color(255, 255, 255));
        jMenu_management.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Add_Database_48px.png"))); // NOI18N
        jMenu_management.setText("Management");

        jMenuItemBarangMasuk.setText("Barang Masuk");
        jMenuItemBarangMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItemBarangMasukActionPerformed(evt);
            }
        });
        jMenu_management.add(jMenuItemBarangMasuk);

        jMenuItem_brg_keluar.setText("Barang Keluar");
        jMenuItem_brg_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_brg_keluarActionPerformed(evt);
            }
        });
        jMenu_management.add(jMenuItem_brg_keluar);

        jMenuItem_transfer_order.setText("Mutasi Barang");
        jMenuItem_transfer_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_transfer_orderActionPerformed(evt);
            }
        });
        jMenu_management.add(jMenuItem_transfer_order);

        jMenuBar1.add(jMenu_management);

        jMenu_report.setBackground(new java.awt.Color(0, 0, 0));
        jMenu_report.setForeground(new java.awt.Color(255, 255, 255));
        jMenu_report.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Report_Card_48px.png"))); // NOI18N
        jMenu_report.setText("Report");

        jMenuItem_lap_barangMasuk.setText("Laporan Barang Masuk");
        jMenuItem_lap_barangMasuk.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_lap_barangMasukActionPerformed(evt);
            }
        });
        jMenu_report.add(jMenuItem_lap_barangMasuk);

        jMenuItem_lap_brg_keluar.setText("Laporan Barang Keluar");
        jMenuItem_lap_brg_keluar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_lap_brg_keluarActionPerformed(evt);
            }
        });
        jMenu_report.add(jMenuItem_lap_brg_keluar);

        jMenuItem_lap_transfer_order.setText("Laporan Mutasi Barang");
        jMenuItem_lap_transfer_order.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_lap_transfer_orderActionPerformed(evt);
            }
        });
        jMenu_report.add(jMenuItem_lap_transfer_order);

        jMenuItem_stock.setText("Laporan Stok");
        jMenuItem_stock.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_stockActionPerformed(evt);
            }
        });
        jMenu_report.add(jMenuItem_stock);

        jMenuBar1.add(jMenu_report);

        jMenu_logout.setBackground(new java.awt.Color(0, 0, 0));
        jMenu_logout.setForeground(new java.awt.Color(255, 255, 255));
        jMenu_logout.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/icons8_Shutdown_48px.png"))); // NOI18N
        jMenu_logout.setText("LogOut");
        jMenu_logout.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jMenu_logoutMouseClicked(evt);
            }
        });
        jMenuBar1.add(jMenu_logout);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem_categoryActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_categoryActionPerformed
        try {
            new FrmKategori().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(FrmKategori.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem_categoryActionPerformed

    private void jMenuItem_unitsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_unitsActionPerformed
        try {
            new FrmUnits().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(FrmUnits.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem_unitsActionPerformed

    private void jMenu_logoutMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jMenu_logoutMouseClicked

       int ok = JOptionPane.showConfirmDialog(null, "Logout Sistem..???","Konfirmasi",JOptionPane.YES_NO_OPTION);
       if(ok==0){
           this.dispose();
           new FrmLogin().setVisible(true);
       }
    }//GEN-LAST:event_jMenu_logoutMouseClicked

    private void jMenuItem_itemsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_itemsActionPerformed
        try {
            new FrmItem().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(FrmItem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem_itemsActionPerformed

    private void jMenuItem_warehouseActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_warehouseActionPerformed
        try {
            new FrmWarehouse().setVisible(true);
        } catch (SQLException ex) {
            Logger.getLogger(FrmWarehouse.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jMenuItem_warehouseActionPerformed

    private void jMenuItemBarangMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItemBarangMasukActionPerformed
        try {
            new FrmItem_in(idUser,nama).setVisible(true);
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_jMenuItemBarangMasukActionPerformed

    private void jMenuItem_lap_barangMasukActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_lap_barangMasukActionPerformed
        try {
            new FrmLap_Barang_Masuk().setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jMenuItem_lap_barangMasukActionPerformed

    private void jMenuItem_brg_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_brg_keluarActionPerformed
        try {
            new frmItem_out(idUser, nama).setVisible(true);
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_jMenuItem_brg_keluarActionPerformed

    private void jMenuItem_stockActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_stockActionPerformed
        try {
            new FrmLap_Stock().setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jMenuItem_stockActionPerformed

    private void jMenuItem_transfer_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_transfer_orderActionPerformed
        try {
            new FrmTransferOrder(idUser, nama).setVisible(true);
        } catch (SQLException e) {
        }
    }//GEN-LAST:event_jMenuItem_transfer_orderActionPerformed

    private void jMenuItem_lap_brg_keluarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_lap_brg_keluarActionPerformed
        try {
            new FrmLap_Barang_keluar().setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jMenuItem_lap_brg_keluarActionPerformed

    private void jMenuItem_lap_transfer_orderActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_lap_transfer_orderActionPerformed
        try {
            new FrmLap_transfer_order().setVisible(true);
        } catch (Exception e) {
        }
    }//GEN-LAST:event_jMenuItem_lap_transfer_orderActionPerformed

    private void btnRefreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRefreshActionPerformed
        view_stok_min(tblStokMin);
        set_view_item_sn(jTable_list_itemSN, txtCariItemSN.getText());
    }//GEN-LAST:event_btnRefreshActionPerformed

    private void txtCariItemSNKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCariItemSNKeyReleased
        set_view_item_sn(jTable_list_itemSN, txtCariItemSN.getText());
    }//GEN-LAST:event_txtCariItemSNKeyReleased

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(FrmHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(FrmHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(FrmHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(FrmHome.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(() -> {
//            //new FrmHome().setVisible(true);
//        });
//    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnRefresh;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenu jMenu;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItemBarangMasuk;
    private javax.swing.JMenuItem jMenuItem_brg_keluar;
    private javax.swing.JMenuItem jMenuItem_category;
    private javax.swing.JMenuItem jMenuItem_items;
    private javax.swing.JMenuItem jMenuItem_lap_barangMasuk;
    private javax.swing.JMenuItem jMenuItem_lap_brg_keluar;
    private javax.swing.JMenuItem jMenuItem_lap_transfer_order;
    private javax.swing.JMenuItem jMenuItem_stock;
    private javax.swing.JMenuItem jMenuItem_transfer_order;
    private javax.swing.JMenuItem jMenuItem_units;
    private javax.swing.JMenuItem jMenuItem_warehouse;
    private javax.swing.JMenu jMenu_logout;
    private javax.swing.JMenu jMenu_management;
    private javax.swing.JMenu jMenu_report;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable_list_itemSN;
    private javax.swing.JLabel lbText;
    private javax.swing.JLabel lblTgl;
    private javax.swing.JLabel lblTime;
    private javax.swing.JLabel lblUser;
    private javax.swing.JTable tblStokMin;
    private javax.swing.JTextField txtCariItemSN;
    // End of variables declaration//GEN-END:variables

    @Override
    public void run() {
        while(true){
            if(berjalan){
                if(x >= jPanel3.getWidth()-200){
                    kiri = true;
                    kanan = false;
                }
                if(kiri){
                    x--;
                    lbText.setLocation(x,y);
                }
                if(x<=5){
                    kanan = true;
                    kiri = false;
                }
                if(kanan){
                    x++;
                    lbText.setLocation(x, y);
                }
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                 Logger.getLogger(FrmHome.class.getName()).log(Level.SEVERE,null,e);
            }
        }
    }

}