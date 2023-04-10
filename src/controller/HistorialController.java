/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import javax.swing.JTable;
import papeleria.model.TableModel;
import papeleria.model.VentaDAO;

/**
 *
 * @author heber
 */
public class HistorialController {
    JTable tblHistorial = new JTable();
    
    public HistorialController(JTable tblHistorial){
        this.tblHistorial = tblHistorial;
        showRegisters("");
    }
    
    private void showRegisters(String strParam) {
        TableModel tbModel = null;
        if (strParam.trim().isEmpty()) {
            tbModel = VentaDAO.tableModel();
        } else {
            tbModel = VentaDAO.searchWithLike(strParam);
        }
        tblHistorial.setModel(tbModel);
        tblHistorial.setRowHeight(30);
        
    }
}
