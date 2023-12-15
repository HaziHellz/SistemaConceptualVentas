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
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.JTextField;
import papeleria.model.TableModel;
import papeleria.model.VentaDAO;
import papeleria.model.VentaTipoDAO;
import papeleria.model.Base;
import papeleria.model.TableBaseDAO;
import papeleria.model.Venta;
import papeleria.model.VentaTipo;

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
        try {
            actualizarVentaDiaria();
            resetTableHistorial();
        } catch (NullPointerException ex) {

        }

    }

    public void actualizarCombos() {
        try {

            Date date = new Date(System.currentTimeMillis());
            if ((date.getMonth() + 1) > Integer.parseInt(combos.get(CBX_MONTH_FILTER).getItemAt(combos.get(CBX_MONTH_FILTER).getModel().getSize() - 1).toString())) {
                combos.get(CBX_YEAR_FILTER).setModel(VentaDAO.comboModelAño());
                combos.get(CBX_MONTH_FILTER).setModel(VentaDAO.comboModelMeses(combos.get(CBX_YEAR_FILTER)));
            }

        } catch (NullPointerException ex) {
            combos.get(CBX_YEAR_FILTER).setModel(VentaDAO.comboModelAño());
            combos.get(CBX_MONTH_FILTER).setModel(VentaDAO.comboModelMeses(combos.get(CBX_YEAR_FILTER)));
        }
    }

    public void actualizarVentaDiaria() {
        try {
            ventaDiaria.setText(VentaDAO.ventaDiaria(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString(), combos.get(CBX_TYPE_SPENDS_FILTER_SOLDS).getSelectedItem().toString()));
            ventaDiaria.setVisible(true);
        } catch (NullPointerException ex) {

        }

    }

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
                    //TOMA LA VENTA EN SELECCION
                    getSelectedItemHistorial(source);
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
                        resetFormVenta();
                    }

                }
            }

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void resetTableHistorial() {
        tablesHistorial.get(TBL_HISTORIAL_VENTAS).setModel(VentaDAO.tableModel(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString(), registrada(), combos.get(CBX_TYPE_SPENDS_FILTER_SOLDS).getSelectedItem().toString()));
        tablesHistorial.get(TBL_VENTA).setModel(new TableModel());
        buttons.get(BTN_DELETE_ITEM).setEnabled(false);
        buttons.get(BTN_ACCEPT).setEnabled(false);
        buttons.get(BTN_DELETE_SALE).setEnabled(false);
        txtPrecio.setEnabled(false);
        combos.get(CBX_TYPES_SOLDS).setEnabled(false);
        selectedIndexHistorial = -1;
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
            }

            if (!source.equals(combos.get(CBX_TYPES_SOLDS))) {
                actualizarVentaDiaria();
                aplicarFiltros();
            }

            if (source.equals(combos.get(CBX_DELETED))) {
                if (combos.get(CBX_DELETED).getSelectedItem().equals("Registradas")) {
                    buttons.get(BTN_DELETE_SALE).setText("Eliminar Venta");
                } else {
                    buttons.get(BTN_DELETE_SALE).setText("Restaurar Venta");
                }
            }

        }

        if (e.getSource() instanceof JButton) {
            JButton source = (JButton) e.getSource();

            if (source.equals(buttons.get(BTN_ACCEPT))) {
                List<Base> tipos = TableBaseDAO.getTipos();
                //TOMA LOS TIPOS EXISTENTES EN LA BASE DE DATOS  ^^^^^^^^

                //TOMA LOS ITEMS EXISTENTES DE ESA VENTA EN LA BASE DE DATOS
                List<VentaTipo> itemsBD = VentaTipoDAO.getVenta(getSelectedVenta());

                //TOMA LOS ITEMS EXISTENTES EN LA TABLA DE VENTA
                List<VentaTipo> items = getItemsTableVenta_Tipo();

                List<VentaTipo> datosAgrupados = new ArrayList();

                //AGRUPA LAS CANTIDADES EN SU TIPO 
                for (int i = 0; i < tipos.size(); i++) {
                    boolean nuevoTipo = true;
                    for (int j = 0; j < items.size(); j++) {
                        if (items.get(j).getTipo().getNombreBase().equals(tipos.get(i).getNombreBase())) {
                            if (nuevoTipo) {

                                datosAgrupados.add(new VentaTipo.VentaBuilder()
                                        .cantidadTipo(items.get(j).getCantidadTipo())
                                        .tipo(TableBaseDAO.getNombre(tipos.get(i).getNombreBase(), "tipo"))
                                        .venta(getSelectedVenta())
                                        .build());

                                datosAgrupados.get(datosAgrupados.size() - 1).getVenta().toString();

                                nuevoTipo = false;
                            } else {
                                datosAgrupados.get(datosAgrupados.size() - 1).setCantidadTipo(datosAgrupados.get(datosAgrupados.size() - 1).getCantidadTipo() + items.get(j).getCantidadTipo());
                            }
                        }
                    }
                }

                //ELIMINA EN LA BD LOS TIPOS QUE NO EXISTAN EN LOS DATOS AGRUPADOS DE LA VENTA EDITADA
                for (int i = 0; i < itemsBD.size(); i++) {
                    boolean existeTipoEnItemsYBD = false;
                    for (int j = 0; j < datosAgrupados.size(); j++) {
                        if (itemsBD.get(i).getTipo().getNombreBase().equals(datosAgrupados.get(j).getTipo().getNombreBase())) {
                            existeTipoEnItemsYBD = true;
                        }
                    }
                    if (!existeTipoEnItemsYBD) {
                        VentaTipoDAO.delete(itemsBD.get(i));
                    }

                }

                //REALIZA LOS CAMBIOS EN LA BASE DE DATOS
                for (int i = 0; i < datosAgrupados.size(); i++) {
                    boolean existeEnBD = false;
                    for (int j = 0; j < itemsBD.size(); j++) {
                        if (datosAgrupados.get(i).getTipo().getIdBase() == itemsBD.get(j).getTipo().getIdBase()) {
                            existeEnBD = true;
                        }
                    }
                    //ACTUALIZA LA CANTIDAD DEL TIPO A LA VENTA QUE CORRESPONDE
                    if (existeEnBD) {
                        VentaTipoDAO.update(datosAgrupados.get(i));
                    } else {
                        //AGREGA EL TIPO Y LA CANTIDAD A LA VENTA QUE CORRESPONDE
                        VentaTipoDAO.insert(datosAgrupados.get(i));
                    }
                }

                actualizarVentaDiaria();
                resetTableHistorial();
                resetFormVenta();
            }
            if (source.equals(buttons.get(BTN_DELETE_ITEM))) {
                deleteItemVenta();
                selectedIndexVentaHistorial = -1;
            }

            if (source.equals(buttons.get(BTN_DELETE_SALE))) {
                if (buttons.get(BTN_DELETE_SALE).getText().equals("Eliminar Venta")) {
                    VentaDAO.changeExists(getSelectedVenta(), false);
                } else {
                    VentaDAO.changeExists(getSelectedVenta(), true);
                }
                actualizarVentaDiaria();
                resetTableHistorial();
                resetFormVenta();

            }

        }

    }

    private Venta getSelectedVenta() {
        return VentaDAO.getVenta((Timestamp) tablesHistorial.get(TBL_HISTORIAL_VENTAS).getValueAt(selectedIndexHistorial, 1));
    }

    @Override
    public void keyTyped(KeyEvent e) {
        if (e.getSource().equals(txtPrecio)) {
            InputController.validarNumero(e, txtPrecio.getText());
            if ('\n' == e.getKeyChar() && !txtPrecio.getText().isEmpty()) {
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
        if (e.getSource().equals(tablesHistorial.get(TBL_HISTORIAL_VENTAS))) {
            JTable source = (JTable) e.getSource();
            getSelectedItemHistorial(source);
        }
    }

    private void getSelectedItemHistorial(JTable source) {
        if (source.equals(tablesHistorial.get(TBL_HISTORIAL_VENTAS))) {
            if (selectedIndexHistorial != source.getSelectedRow()) {
                selectedIndexHistorial = source.getSelectedRow();
                showSale((int) source.getValueAt(selectedIndexHistorial, 0), String.valueOf(source.getValueAt(selectedIndexHistorial, 1)));
                txtPrecio.setEnabled(true);
                combos.get(CBX_TYPES_SOLDS).setEnabled(true);
                buttons.get(BTN_ACCEPT).setEnabled(true);
                buttons.get(BTN_DELETE_SALE).setEnabled(true);
            } else {
                aplicarFiltros();
            }

        }
    }

    private boolean editarItem() {
        tablesHistorial.get(TBL_VENTA).setValueAt(txtPrecio.getText(), selectedIndexVentaHistorial, 1);
        tablesHistorial.get(TBL_VENTA).setValueAt(combos.get(CBX_TYPES_SOLDS).getSelectedItem(), selectedIndexVentaHistorial, 0);
        resetFormVenta();
        return true;
    }

    private void resetFormVenta() {
        refreshItemsVentas();
        nuevo = true;
        defecto = true;
        txtPrecio.setText("");
        buttons.get(BTN_DELETE_ITEM).setEnabled(false);
        selectedIndexVentaHistorial = -1;
    }

    private List<VentaTipo> getItemsTableVenta_Tipo() {
        int rows = tablesHistorial.get(TBL_VENTA).getRowCount();
        List<VentaTipo> items = new ArrayList();
        for (int i = 0; i < rows; i++) {
            //items.add(new Object[]{tablesHistorial.get(TBL_VENTA).getValueAt(i, 0), tablesHistorial.get(TBL_VENTA).getValueAt(i, 1)});
            items.add(new VentaTipo.VentaBuilder()
                    .tipo(TableBaseDAO.getNombre((String) tablesHistorial.get(TBL_VENTA).getValueAt(i, 0), "tipo"))
                    .cantidadTipo(Double.parseDouble(tablesHistorial.get(TBL_VENTA).getValueAt(i, 1).toString()))
                    .build());

        }
        //System.out.println(items.size());
        return items;
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

    private void aplicarFiltros() {
        selectedIndexHistorial = -1;
        selectedIndexVentaHistorial = -1;
        nuevo = true;
        defecto = true;
        resetTableHistorial();
    }
}
