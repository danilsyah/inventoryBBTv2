/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.model;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Locale;
import  com.koneksi.DBKoneksi;
import java.util.Date;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.view.JasperViewer;
/**
 *
 * @author danil
 */
public class model_report {
    
        final Connection con = DBKoneksi.getKoneksi();
        private JasperDesign jasperDesign;
        private JasperReport jasperReport;
        private JasperPrint jasperPrint;
        private JasperViewer jasperviewer;
        HashMap parameter = new HashMap();
        
        public model_report(){
        
        }
        
        public void setLaporan_transfer_order(String tgl1,String tgl2,String fileReport){
            
             try {
                parameter.put("p_tgl1", tgl1);
                parameter.put("p_tgl2", tgl2);
                File report_file = new File(fileReport);
                jasperDesign = JRXmlLoader.load(report_file);
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                  jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, con);
                 JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                System.out.println(e.getMessage());
            }
            
        }
        
        public void setLaporan_barang_keluar(String tgl1,String tgl2,String fileReport){
            
             try {
                parameter.put("p_tgl", tgl1);
                parameter.put("p_tgl2", tgl2);
                File report_file = new File(fileReport);
                jasperDesign = JRXmlLoader.load(report_file);
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                  jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, con);
                 JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                System.out.println(e.getMessage());
            }
            
        }
        
        public void setLaporan_barang_masuk(String tgl1,String tgl2,String fileReport){
            try {
                parameter.put("tgl", tgl1);
                parameter.put("tgl1", tgl2);
                File report_file = new File(fileReport);
                jasperDesign = JRXmlLoader.load(report_file);
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                  jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, con);
                 JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                System.out.println(e.getMessage());
            }
        }
        
        public void setLaporan_barang_masuk_per_posting(String idItemIn, String fileReport){
        
            try {
                parameter.put("idItemIn", idItemIn);
                File report_file = new File(fileReport);
                jasperDesign = JRXmlLoader.load(report_file);
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                 jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, con);
                 JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                System.out.println(e.getMessage());
            }
        }
        
        public void setLaporan_barang_keluar_per_posting(String idItemOut,String fileReport){
            try {
                parameter.put("idItemOut", idItemOut);
                File report_file = new File(fileReport);
                 jasperDesign = JRXmlLoader.load(report_file);
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                 jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, con);
                 JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                System.out.println(e.getMessage());
            }
        }
        
        public void setLaporan_stock_per_kondisi(String kondisi,String fileReport){
             try {
                parameter.put("p_kondisi", kondisi);
                File report_file = new File(fileReport);
                 jasperDesign = JRXmlLoader.load(report_file);
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                 jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, con);
                 JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                System.out.println(e.getMessage());
            }
        }
        
        public void setLaporan_stock(String fileReport){
             try {
                File report_file = new File(fileReport);
                 jasperDesign = JRXmlLoader.load(report_file);
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                 jasperPrint = JasperFillManager.fillReport(jasperReport,null, con);
                 JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                System.out.println(e.getMessage());
            }
        }
        
        public void laporan_transfer_order_per_journal(String fileReport, String idTransferOrder){
        
             try {
                parameter.put("idTransferOrder", idTransferOrder);
                File report_file = new File(fileReport);
                 jasperDesign = JRXmlLoader.load(report_file);
                jasperReport = JasperCompileManager.compileReport(jasperDesign);
                 jasperPrint = JasperFillManager.fillReport(jasperReport, parameter, con);
                 JasperViewer.viewReport(jasperPrint, false);
            } catch (JRException e) {
                System.out.println(e.getMessage());
            }
        }
       
}
