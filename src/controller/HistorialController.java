/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import papeleria.model.TableModel;
import papeleria.model.TipoDAO;
import papeleria.model.VentaDAO;
import papeleria.model.VentaTipoDAO;

/**
 *
 * @author heber
 */
public class HistorialController extends MouseAdapter implements ActionListener, KeyListener, ItemListener {

    private final int TBL_HISTORIAL_VENTAS = 0;
    private final int TBL_VENTA = 1;
    private final int TYPE_SOLDS = 4;

    private final int CBX_YEAR_FILTER = 0;
    private final int CBX_MONTH_FILTER = 1;
    private final int CBX_DELETED = 2;
    private final int CBX_TYPE_SPENDS_FILTER_SOLDS = 3;
    private final int CBX_TYPES_SOLDS = 4;

    private final int BTN_DELETE_ITEM = 0;
    private final int BTN_DELETE_SALE = 1;
    private final int BTN_ACCEPT = 2;

    private boolean nuevo = true;
    private boolean defecto;

    private JLabel ventaDiaria;

    private List<JTable> tablesHistorial;
    private List<JComboBox> combos;
    private JTextField txtPrecio;
    private List<JButton> buttons;

    private int selectedIndexHistorial = -1;
    private int selectedIndexVentaHistorial = -1;

    public HistorialController(List<JTable> tablesHistorial, List<JComboBox> combos, JLabel ventaDiaria, JTextField txtPrecio, List<JButton> buttonsHistorial) {
        this.tablesHistorial = tablesHistorial;
        this.combos = combos;
        this.ventaDiaria = ventaDiaria;
        this.txtPrecio = txtPrecio;
        this.buttons = buttonsHistorial;

        actualizarVentaDiaria();
        resetTableHistorial();
    }

    private void actualizarVentaDiaria() {
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
        tablesHistorial.get(TBL_VENTA).setModel(tbModel);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        try {
            if (e.getSource() instanceof JTable) {
                JTable source = (JTable) e.getSource();
                if (source.equals(tablesHistorial.get(TBL_HISTORIAL_VENTAS))) {
                    if (selectedIndexHistorial != source.getSelectedRow()) {
                        selectedIndexHistorial = source.getSelectedRow();
                        showSale((int) source.getValueAt(selectedIndexHistorial, 0), String.valueOf(source.getValueAt(selectedIndexHistorial, 1)));
                        txtPrecio.setEnabled(true);
                        combos.get(CBX_TYPES_SOLDS).setEnabled(true);
                        buttons.get(BTN_ACCEPT).setEnabled(true);
                        buttons.get(BTN_DELETE_SALE).setEnabled(true);
                    } else {
                        selectedIndexHistorial = -1;
                        resetTableHistorial();
                    }

                } else if (source.equals(tablesHistorial.get(TBL_VENTA))) {
                    if (selectedIndexVentaHistorial != source.getSelectedRow()) {
                        selectedIndexVentaHistorial = source.getSelectedRow();

                        txtPrecio.setText(String.valueOf(source.getValueAt(selectedIndexVentaHistorial, 1)));
                        defecto = true;
                        combos.get(CBX_TYPES_SOLDS).setSelectedItem(source.getValueAt(selectedIndexVentaHistorial, 0));
                        buttons.get(BTN_DELETE_ITEM).setEnabled(true);
                        nuevo = false;
                    } else {
                        selectedIndexVentaHistorial = -1;
                        resetTableVenta();
                    }

                }
            }

//            } else if (e.getSource() instanceof JComboBox) {
//                JComboBox source = (JComboBox) e.getSource();
//                if (source.equals(combos.get(CBX_TYPES_SOLDS))) {
//                    if (!nuevo) {
//                        editarItem();
//                    }
//                }
//            }
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    private void resetTableHistorial() {
        tablesHistorial.get(TBL_HISTORIAL_VENTAS).setModel(VentaDAO.tableModel(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString(), registrada(), combos.get(CBX_TYPE_SPENDS_FILTER_SOLDS).getSelectedItem().toString()));
        tablesHistorial.get(TBL_VENTA).setModel(new TableModel());
        buttons.get(BTN_DELETE_ITEM).setEnabled(false);
        buttons.get(BTN_ACCEPT).setEnabled(false);
        buttons.get(BTN_DELETE_SALE).setEnabled(false);
        txtPrecio.setEnabled(false);
        combos.get(CBX_TYPES_SOLDS).setEnabled(false);
    }

    private String registrada() {
        if (combos.get(CBX_DELETED).getSelectedItem().toString().equals("Registradas")) {
            return "true";
        } else {
            return "false";
        }
    }

    private void agregarItem() {
        TableModel tableModel = new TableModel();
        List<Object[]> items = getItemsTableVenta();
        tableModel.addColumn("Concepto");
        tableModel.addColumn("Cantidad");
        for (int i = 0; i < tablesHistorial.get(TBL_VENTA).getRowCount(); i++) {
            items.add(new Object[]{tablesHistorial.get(TBL_VENTA).getValueAt(i, 0), tablesHistorial.get(TBL_VENTA).getValueAt(i, 1)});
            tableModel.addRow(items.get(i));
        }
        tableModel.addRow(new Object[]{combos.get(CBX_TYPES_SOLDS).getSelectedItem().toString(), txtPrecio.getText()});

        tablesHistorial.get(TBL_VENTA).setModel(tableModel);

        txtPrecio.setText("");
    }

    private void refreshItemsVentas() {
        TableModel tableModel = new TableModel();
        List<Object[]> items = getItemsTableVenta();
        tableModel.addColumn("Concepto");
        tableModel.addColumn("Cantidad");
        for (int i = 0; i < tablesHistorial.get(TBL_VENTA).getRowCount(); i++) {
            tableModel.addRow(items.get(i));
        }
        tablesHistorial.get(TBL_VENTA).setModel(tableModel);
    }

    private void deleteItemVenta() {
        TableModel tableModel = new TableModel();
        List<Object[]> items = getItemsTableVenta();
        tableModel.addColumn("Concepto");
        tableModel.addColumn("Cantidad");
        for (int i = 0; i < tablesHistorial.get(TBL_VENTA).getRowCount(); i++) {
            if (selectedIndexVentaHistorial != i) {
                tableModel.addRow(items.get(i));
            }
        }
        tablesHistorial.get(TBL_VENTA).setModel(tableModel);
        nuevo = true;
        txtPrecio.setText("");
        buttons.get(BTN_DELETE_ITEM).setEnabled(false);
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
                resetTableHistorial();
                actualizarVentaDiaria();
            }
        }

        if (e.getSource() instanceof JButton) {
            JButton source = (JButton) e.getSource();

            if (source.equals(buttons.get(BTN_ACCEPT))) {
                //TOMA LOS ITEMS EXISTENTES EN LA BASE DE DATOS
                List<Object[]> itemsBD = VentaTipoDAO.getVenta(Integer.parseInt(tablesHistorial.get(TBL_HISTORIAL_VENTAS).getValueAt(selectedIndexHistorial, 0).toString()), tablesHistorial.get(TBL_HISTORIAL_VENTAS).getValueAt(selectedIndexHistorial, 1).toString());

                //TOMA LOS ITEMS EXISTENTES EN LA TABLA DE VENTA
                List<Object[]> items = getItemsTableVenta();

            }
            if (source.equals(buttons.get(BTN_DELETE_ITEM))) {
                deleteItemVenta();
            }

        }

    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource().equals(txtPrecio)) {
            InputController.validarNumero(e, txtPrecio.getText());
            if ('\n' == e.getKeyChar()) {
                if (nuevo) {
                    agregarItem();
                } else {
                    editarItem();
                }
            }
        }
    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    private boolean editarItem() {

        tablesHistorial.get(TBL_VENTA).setValueAt(txtPrecio.getText(), selectedIndexVentaHistorial, 1);

        tablesHistorial.get(TBL_VENTA).setValueAt(combos.get(CBX_TYPES_SOLDS).getSelectedItem(), selectedIndexVentaHistorial, 0);

        resetTableVenta();

        return true;
    }

    private void resetTableVenta() {
        refreshItemsVentas();
        nuevo = true;
        defecto = true;
        txtPrecio.setText("");
        buttons.get(BTN_DELETE_ITEM).setEnabled(false);
        selectedIndexVentaHistorial = -1;
    }

    private List<Object[]> getItemsTableVenta() {
        int rows = tablesHistorial.get(TBL_VENTA).getRowCount();
        List<Object[]> items = new ArrayList();
        for (int i = 0; i < rows; i++) {
            items.add(new Object[]{tablesHistorial.get(TBL_VENTA).getValueAt(i, 0), tablesHistorial.get(TBL_VENTA).getValueAt(i, 1)});
        }

        return items;
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        if (e.getSource() instanceof JComboBox) {
            //JComboBox source = (JComboBox) e.getSource();
            boolean editado = false;
            if (selectedIndexVentaHistorial != -1) {
                if (!e.getItem().toString().equals(tablesHistorial.get(TBL_VENTA).getValueAt(selectedIndexVentaHistorial, 0).toString())) {
                    if (!nuevo && !defecto) {
                        editado = editarItem();
                    }
                }
            }

            if (editado) {
                defecto = true;
            } else {
                defecto = false;
            }
        }
    }
}
