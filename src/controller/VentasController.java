/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import papeleria.model.Base;
import papeleria.model.TableModel;
import papeleria.model.TipoDAO;
import papeleria.model.Venta;
import papeleria.model.VentaDAO;
import papeleria.model.VentaTipo;
import papeleria.model.VentaTipoDAO;
import papeleria.view.GUI;

/**
 *
 * @author heber
 */
public class VentasController implements ActionListener, KeyListener {

    private int TXT_PRICE = 0;
    private int CBX_TYPE_SALE = 1;
    private int BTN_SELL = 2;
    private int TBL_OBJECT_LIST = 3;
    private int LBL_TOTAL = 4;

    private double total = 0;

    private List<Object> componentes;
    private GUI.Historial historialController;

    public VentasController(List<Object> componentes, GUI.Historial historial) {
        this.componentes = componentes;
        this.historialController = historial;
    }

    private void agregarItem() {
        TableModel tableModel = new TableModel();
        List<Object[]> items = getItemsTableVenta();
        tableModel.addColumn("Concepto");
        tableModel.addColumn("Cantidad");
        for (int i = 0; i < ((JTable) componentes.get(TBL_OBJECT_LIST)).getRowCount(); i++) {
            tableModel.addRow(items.get(i));
        }
        items.add(new Object[]{((JComboBox) componentes.get(CBX_TYPE_SALE)).getSelectedItem().toString(), ((JTextField) componentes.get(TXT_PRICE)).getText()});
        //tableModel.addRow(new Object[]{((JComboBox)componentes.get(CBX_TYPE_SALE)).getSelectedItem().toString(), ((JTextField) componentes.get(TXT_PRICE)).getText()});
        tableModel.addRow(items.get(items.size() - 1));

        ((JTable) componentes.get(TBL_OBJECT_LIST)).setModel(tableModel);

        ((JTextField) componentes.get(TXT_PRICE)).setText("");

        calcularTotal(items);
    }

    private void calcularTotal(List<Object[]> items) {
        total = 0;

        for (int i = 0; i < items.size(); i++) {
            total += getDouble((String) items.get(i)[1]);
        }

        ((JLabel) componentes.get(LBL_TOTAL)).setText("Total: " + total);
    }

    //SEPARA Y CONVIERTE LA ENTRADA STRING EN DOUBLE EN MULTIPLICACIONES POR EJEMPLO PARA ENTRADA 20X3 LA SALIDA SERIA 60
    private double getDouble(String numero){
        boolean ladoUno = true;
        String numeroUno = "";
        String numeroDos = "";
        for (int i = 0; i < numero.length(); i++) {
            if (ladoUno) {
                if (!(numero.charAt(i) == 'x' || numero.charAt(i) == 'X')) {
                    numeroUno += numero.charAt(i);
                }else{
                    ladoUno = false;
                }
            }else{
                numeroDos += numero.charAt(i);
            }
        }
        
        if (!ladoUno) {
            return Double.parseDouble(numeroUno)*Double.parseDouble(numeroDos);
        }else{
            return Double.parseDouble(numeroUno);
        }
    }
    
    private List<Object[]> getItemsTableVenta() {
        int rows = ((JTable) componentes.get(TBL_OBJECT_LIST)).getRowCount();
        List<Object[]> items = new ArrayList();
        for (int i = 0; i < rows; i++) {
            items.add(new Object[]{((JTable) componentes.get(TBL_OBJECT_LIST)).getValueAt(i, 0), ((JTable) componentes.get(TBL_OBJECT_LIST)).getValueAt(i, 1)});
        }

        return items;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton source = (JButton) e.getSource();
            if (source.equals(componentes.get(BTN_SELL))) {
                //try{
                vender();
                // }catch(Exception ex){
                //      System.err.print(ex.toString());
                //  }
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource() instanceof JTextField) {

            JTextField source = (JTextField) e.getSource();
            InputController.validarNumeroCompuesto(e, source.getText());

            if (e.getKeyChar() == '\n') {
                agregarItem();
            }

        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private void vender() {
        Venta venta = new Venta.VentaBuilder().idVenta(VentaDAO.idSiguienteVentaDelMes()).fecha(new Timestamp(System.currentTimeMillis())).existe(true).build();

        List<VentaTipo> ventaTipos = agruparItems(venta);

        if (ventaTipos.size() > 0) {
            VentaDAO.insert(venta);
            for (int i = 0; i < ventaTipos.size(); i++) {
                VentaTipoDAO.insert(ventaTipos.get(i));
            }
            resetTable();
            historialController.actualizarVista();
        }else{
            JOptionPane.showMessageDialog(null, "No hay items en el ingreso", "Error al ingresar", JOptionPane.ERROR_MESSAGE);
        }
    }

    private List<VentaTipo> agruparItems(Venta venta) {
        List<Object[]> items = getItemsTableVenta();

        List<Base> tipos = TipoDAO.getTipos();
        List<VentaTipo> ventaTipos = new ArrayList();

        for (int i = 0; i < tipos.size(); i++) {
            boolean creado = false;
            for (int j = 0; j < items.size(); j++) {
                if (tipos.get(i).getNombreBase().equals(items.get(j)[0])) {
                    if (!creado) {
                        ventaTipos.add(new VentaTipo.VentaBuilder().venta(venta).cantidadTipo(getDouble((String) items.get(j)[1])).tipo(tipos.get(i)).build());
                        creado = true;
                    } else {
                        ventaTipos.get(ventaTipos.size() - 1).setCantidadTipo(ventaTipos.get(ventaTipos.size() - 1).getCantidadTipo() + getDouble((String) items.get(j)[1]));
                    }

                }
            }
        }

        return ventaTipos;
    }

    private void resetTable() {
        total = 0;
        ((JTextField) componentes.get(TXT_PRICE)).setText("");
        ((JLabel) componentes.get(LBL_TOTAL)).setText("Total: " + total);
        ((JTable) componentes.get(TBL_OBJECT_LIST)).setModel(new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "Concepto", "Cantidad"
                }
        ) {
            Class[] types = new Class[]{
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean[]{
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types[columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit[columnIndex];
            }
        });
    }

}
