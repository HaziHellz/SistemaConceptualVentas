/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import papeleria.model.TableModel;
import papeleria.model.TipoDAO;
import papeleria.model.VentaDAO;
import papeleria.model.VentaTipoDAO;

/**
 *
 * @author heber
 */
public class HistorialController extends MouseAdapter implements ActionListener, KeyListener {

    private final int HISTORIAL_VENTAS = 0;
    private final int VENTA = 1;
    private final int TYPE_SOLDS = 4;

    private final int CBX_YEAR_FILTER = 0;
    private final int CBX_MONTH_FILTER = 1;
    private final int CBX_DELETED = 2;
    private final int CBX_TYPE_SPENDS_FILTER_SOLDS = 3;
    private final int CBX_TYPES_SOLDS = 4;
    private JLabel ventaDiaria;
    
    private List<JTable> tablesHistorial;
    private List<JComboBox> combos;

    private int selectedIndexHistorial = -1;

    public HistorialController(List<JTable> tablesHistorial, List<JComboBox> combos, JLabel ventaDiaria) {
        this.tablesHistorial = tablesHistorial;
        this.combos = combos;
        this.ventaDiaria = ventaDiaria;
        actualizarVentaDiaria();
        resetTables();
    }
    
    private void actualizarVentaDiaria(){
        ventaDiaria.setText(VentaDAO.ventaDiaria(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString(), combos.get(CBX_TYPE_SPENDS_FILTER_SOLDS).getSelectedItem().toString()));
    }

    private void loadCombos() {
        combos.get(TYPE_SOLDS).setModel(TipoDAO.comboModel());
        
    }

    /*
    private void showHistory(String strParam) {
        TableModel tbModel = null;
        if (strParam.trim().isEmpty()) {
            tbModel = VentaDAO.tableModel();
        } else {
            tbModel = VentaDAO.searchWithLike(strParam);
        }
        tablesHistorial.get(HISTORIAL_VENTAS).setModel(tbModel);
        tablesHistorial.get(HISTORIAL_VENTAS).setRowHeight(30);

    }
     */
    private void showSale(int id_venta, String fecha_venta) {
        TableModel tbModel = null;
        tbModel = VentaTipoDAO.tableModel(id_venta, fecha_venta);
        tablesHistorial.get(VENTA).setModel(tbModel);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof JTable) {
            JTable source = (JTable) e.getSource();
            if (source.equals(tablesHistorial.get(HISTORIAL_VENTAS))) {
                if (selectedIndexHistorial != source.getSelectedRow()) {
                    selectedIndexHistorial = source.getSelectedRow();
                    showSale((int) source.getValueAt(selectedIndexHistorial, 0), String.valueOf(source.getValueAt(selectedIndexHistorial, 1)));
                } else {
                    selectedIndexHistorial = -1;
                    resetTables();
                }

            }

        }
    }
    
    private void resetTables() {
        tablesHistorial.get(HISTORIAL_VENTAS).setModel(VentaDAO.tableModel(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString(), registrada(), combos.get(CBX_TYPE_SPENDS_FILTER_SOLDS).getSelectedItem().toString()));
        tablesHistorial.get(VENTA).setModel(new TableModel());
    }
    
    
    
    private String registrada(){
        if (combos.get(CBX_DELETED).getSelectedItem().toString().equals("Registradas")) {
            return "true";
        }else{
            return "false";
        }
    }
    

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() instanceof JComboBox) {
            JComboBox source = (JComboBox) e.getSource();
            if (source.equals(combos.get(CBX_YEAR_FILTER))) {
                combos.get(CBX_MONTH_FILTER).setModel(VentaDAO.comboModelMeses(combos.get(CBX_YEAR_FILTER)));
                //tablesHistorial.get(HISTORIAL_VENTAS).setModel(VentaDAO.tableModel(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString()));
            } 

            if (!source.equals(combos.get(CBX_TYPES_SOLDS))) {
                resetTables();
                actualizarVentaDiaria();
            }
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
