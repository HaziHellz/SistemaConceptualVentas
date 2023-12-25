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
import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import papeleria.model.Gasto;
import papeleria.model.GastoDAO;
import papeleria.model.TableBaseDAO;
import papeleria.model.TableModel;
import papeleria.model.VentaDAO;

/**
 *
 * @author Heber Haziel Nava Martinez
 * @version 0.8.5
 */
public class TABGastoController extends MouseAdapter implements ActionListener, KeyListener {

    int CBX_PROVIDER = 0;
    int CBX_TYPE_SPENDS = 1;
    int CBX_YEAR = 2;
    int CBX_MONTH = 3;
    int CBX_YEAR_FILTER = 4;
    int CBX_MONTH_FILTER = 5;
    int CBX_PROVIDER_FILTER = 6;
    int CBX_TYPE_SPENDS_FILTER = 7;

    int BTN_ACCEPT_SPEND = 0;
    int BTN_DELETE_SPEND = 1;

    int index = -1;

    List<JComboBox> combos;
    List<JButton> buttons = new ArrayList();
    JTextField txtCantidad;
    JTable tblSpend;
    JLabel lblSpend;

    boolean nuevo = true;

    public TABGastoController(List<JComboBox> combos, List<Object> componentes) {
        this.combos = combos;
        txtCantidad = (JTextField) componentes.get(0);
        buttons.add(((JButton) componentes.get(1)));
        buttons.add(((JButton) componentes.get(2)));
        tblSpend = (JTable) componentes.get(3);
        lblSpend = ((JLabel) componentes.get(4));
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getSource() instanceof JTable) {

            JTable source = (JTable) e.getSource();

            if (index != source.getSelectedRow()) {
                index = source.getSelectedRow();
                getDatos();
            } else {
                refreshTable(source);
            }

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton source = (JButton) e.getSource();
            if (source.equals(buttons.get(BTN_ACCEPT_SPEND))) {
                if (nuevo) {
                    agregar();
                } else {
                    editar();
                }
            }

            if (source.equals(buttons.get(BTN_DELETE_SPEND))) {
                eliminar();
            }

        }

        if (e.getSource() instanceof JComboBox) {
            JComboBox source = (JComboBox) e.getSource();
            if (source.equals(combos.get(CBX_YEAR_FILTER))) {
                combos.get(CBX_MONTH_FILTER).setModel(GastoDAO.comboModelMeses(combos.get(CBX_YEAR_FILTER)));
                resetForm();
            }

            if (source.equals(combos.get(CBX_MONTH_FILTER)) || source.equals(combos.get(CBX_PROVIDER_FILTER)) || source.equals(combos.get(CBX_TYPE_SPENDS_FILTER))) {
                resetForm();
            }

            if (source.equals(combos.get(CBX_YEAR))) {
                combos.get(CBX_MONTH).setModel(GastoDAO.comboModelActualMeses(source));
            }

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

        //si fue en el jtext
        if (e.getSource() instanceof JTextField) {

            //actualiza la fuente
            JTextField source = (JTextField) e.getSource();

            //controla solo entredas numericas y multiplicativas 10x5 ej
            InputController.validarNumeroCompuesto(e, source.getText());

            //si se uso enter
            if (e.getKeyChar() == '\n') {

                //Si el jtext no esta vacio
                if (!txtCantidad.getText().isEmpty()) {

                    //y es nuevo
                    if (nuevo) {
                        //se agrega
                        agregar();
                    } else {
                        //si no es nuevo, se edita
                        editar();
                    }

                    //si esta vacio, entonces VVV
                } else {

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

    private void agregar() {
        //crea el objeto gasto
        try {
            Gasto gasto = generarObjeto();
            GastoDAO.insert(gasto);
            combos.get(CBX_YEAR).setModel(GastoDAO.comboModelAño());
            combos.get(CBX_MONTH).setModel(GastoDAO.comboModelActualMeses(combos.get(CBX_YEAR)));
            actualizarCombos();
            resetForm();
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "<html><center><p style=\"font: 15px\">Asegurate de seleccionar un Concepto y una tienda o proveedor</p><br>", "Registro de Gasto", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void actualizarCombos() {
        try {
            Date date = new Date(System.currentTimeMillis());
            // System.out.println(combos.get(CBX_MONTH_FILTER).getItemAt(combos.get(CBX_MONTH_FILTER).getModel().getSize()-1).toString());
            if ((date.getMonth() + 1) > Integer.parseInt(combos.get(CBX_MONTH_FILTER).getItemAt(combos.get(CBX_MONTH_FILTER).getModel().getSize() - 1).toString())) {
             //   System.out.println("SE ACTUALIZAN LOS GASTOOS");
                combos.get(CBX_YEAR_FILTER).setModel(GastoDAO.comboModelAño());
                combos.get(CBX_MONTH_FILTER).setModel(GastoDAO.comboModelMeses(combos.get(CBX_YEAR_FILTER)));
            }

        } catch (NullPointerException ex) {
        }
    }

    public void resetForm() {
        combos.get(CBX_MONTH).setVisible(false);
        combos.get(CBX_YEAR).setVisible(false);
        buttons.get(BTN_DELETE_SPEND).setEnabled(false);
        txtCantidad.setText("");

        try {
            tblSpend.setModel(GastoDAO.tableModel(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString(), combos.get(CBX_TYPE_SPENDS_FILTER).getSelectedItem().toString(), combos.get(CBX_PROVIDER_FILTER).getSelectedItem().toString()));

        } catch (NullPointerException ex) {
            combos.get(CBX_YEAR_FILTER).setModel(GastoDAO.comboModelAño());
            combos.get(CBX_MONTH_FILTER).setModel(GastoDAO.comboModelMeses(combos.get(CBX_YEAR_FILTER)));
            tblSpend.setModel(GastoDAO.tableModel(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString(), combos.get(CBX_TYPE_SPENDS_FILTER).getSelectedItem().toString(), combos.get(CBX_PROVIDER_FILTER).getSelectedItem().toString()));

        }

        lblSpend.setText(GastoDAO.gastoDelMes(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString(), combos.get(CBX_TYPE_SPENDS_FILTER).getSelectedItem().toString(), combos.get(CBX_PROVIDER_FILTER).getSelectedItem().toString()));
        index = -1;
        nuevo = true;
    }

    private void getDatos() {

        nuevo = false;
        combos.get(CBX_MONTH).setVisible(true);
        combos.get(CBX_YEAR).setVisible(true);
        buttons.get(BTN_DELETE_SPEND).setEnabled(true);

        txtCantidad.setText(String.valueOf(tblSpend.getValueAt(index, 3)));
        combos.get(CBX_TYPE_SPENDS).setSelectedItem(tblSpend.getValueAt(index, 1));
        combos.get(CBX_PROVIDER).setSelectedItem(tblSpend.getValueAt(index, 2));
        combos.get(CBX_YEAR).setSelectedItem(tblSpend.getValueAt(index, 4).toString().substring(0, 3));
        combos.get(CBX_MONTH).setSelectedItem(tblSpend.getValueAt(index, 4).toString().substring(5, 7));

    }

    private void refreshTable(JTable itemsTable) {
        TableModel tableModel = new TableModel();
        List<Object[]> items = getItemsTableGastos();
        tableModel.addColumn("Gasto #");
        tableModel.addColumn("Concepto");
        tableModel.addColumn("Tienda");
        tableModel.addColumn("Cantidad $");
        tableModel.addColumn("Fecha");
        for (int i = 0; i < tblSpend.getRowCount(); i++) {
            tableModel.addRow(items.get(i));
        }

        combos.get(CBX_MONTH).setVisible(false);
        combos.get(CBX_YEAR).setVisible(false);
        buttons.get(BTN_DELETE_SPEND).setEnabled(false);

        itemsTable.setModel(tableModel);
        txtCantidad.setText("");
        txtCantidad.requestFocus();
        index = -1;
        nuevo = true;

    }

    private List<Object[]> getItemsTableGastos() {
        int rows = tblSpend.getRowCount();
        List<Object[]> items = new ArrayList();
        for (int i = 0; i < rows; i++) {
            items.add(new Object[]{tblSpend.getValueAt(i, 0), tblSpend.getValueAt(i, 1), tblSpend.getValueAt(i, 2), tblSpend.getValueAt(i, 3), tblSpend.getValueAt(i, 4)});
        }

        return items;
    }

    private void editar() {
        Gasto gasto = generarObjeto();
        Gasto gastoAEditar = new Gasto.GastoBuilder()
                .fecha((Timestamp) tblSpend.getValueAt(index, 4))
                .id(Integer.parseInt(tblSpend.getValueAt(index, 0).toString()))
                .build();

        GastoDAO.update(gasto, gastoAEditar);
        combos.get(CBX_YEAR_FILTER).setModel(GastoDAO.comboModelAño());
        combos.get(CBX_MONTH_FILTER).setModel(GastoDAO.comboModelMeses(combos.get(CBX_YEAR_FILTER)));

        resetForm();
    }

    private Gasto generarObjeto() {
        if (nuevo) {
            return new Gasto.GastoBuilder()
                    .id(GastoDAO.idSiguienteGastoDelMes())
                    .cantidad(InputController.getDouble(txtCantidad.getText()))
                    .fecha(new Timestamp(System.currentTimeMillis()))
                    .idProveedor(TableBaseDAO.getID("proveedor", combos.get(CBX_PROVIDER).getSelectedItem().toString()))
                    .idTipo(TableBaseDAO.getID("tipo", combos.get(CBX_TYPE_SPENDS).getSelectedItem().toString()))
                    .build();
        } else {
            System.out.println();
            return new Gasto.GastoBuilder()
                    .id(Integer.parseInt(tblSpend.getValueAt(index, 0).toString()))
                    .cantidad(InputController.getDouble(txtCantidad.getText()))
                    .fecha(new Timestamp((Integer.parseInt(combos.get(CBX_YEAR).getSelectedItem().toString()) - 1900), Integer.parseInt(combos.get(CBX_MONTH).getSelectedItem().toString()) - 1, Integer.parseInt(tblSpend.getValueAt(index, 4).toString().substring(8, 10)), Integer.parseInt(tblSpend.getValueAt(index, 4).toString().substring(11, 13)), Integer.parseInt(tblSpend.getValueAt(index, 4).toString().substring(14, 16)), Integer.parseInt(tblSpend.getValueAt(index, 4).toString().substring(17, 19)), 0))
                    .idProveedor(TableBaseDAO.getID("proveedor", combos.get(CBX_PROVIDER).getSelectedItem().toString()))
                    .idTipo(TableBaseDAO.getID("tipo", combos.get(CBX_TYPE_SPENDS).getSelectedItem().toString()))
                    .build();

        }
    }

    private void eliminar() {
        Gasto gasto = generarObjeto();
        GastoDAO.delete(gasto);
        resetForm();
    }

}
