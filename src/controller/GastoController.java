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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import papeleria.model.Gasto;
import papeleria.model.GastoDAO;
import papeleria.model.TableBaseDAO;

/**
 *
 * @author heber
 */
public class GastoController extends MouseAdapter implements ActionListener, KeyListener {

    /*
        combos.add(cbxProvider);
        combos.add(cbxTypeSpends);
        combos.add(cbxYear);
        combos.add(cbxMonth);
        combos.add(cbxYearFilter);
        combos.add(cbxMonthFilter);
        combos.add(cbxProviderFilter);
        combos.add(cbxTypeSpendsFilter);
     */

    int CBX_PROVIDER = 0;
    int CBX_TYPE_SPENDS = 1;
    int CBX_YEAR = 2;
    int CBX_MONTH = 3;
    int CBX_YEAR_FILTER = 4;
    int CBX_MONTH_FILTER = 5;
    int CBX_PROVIDER_FILTER = 6;
    int CBX_TYPE_SPENDS_FILTER = 7;

    List<JComboBox> combos;
    List<JButton> buttons = new ArrayList();
    JTextField txtCantidad;
    JTable tblSpend;

    boolean nuevo = true;

    public GastoController(List<JComboBox> combos, List<Object> componentes) {
        this.combos = combos;
        txtCantidad = (JTextField) componentes.get(0);
        buttons.add(((JButton) componentes.get(1)));
        buttons.add(((JButton) componentes.get(2)));
        tblSpend = (JTable) componentes.get(3);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

                //si es nuevo
                if (nuevo) {

                    //y el jtext no esta vacio
                    if (!txtCantidad.getText().isEmpty()) {
                        //se agrega
                        agregar();

                        //si no
                    } else {

                    }
                } else { //Si no es nuevo el gasto

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
            Gasto gasto = new Gasto.GastoBuilder()
                    .id(GastoDAO.idSiguienteGastoDelMes())
                    .cantidad(InputController.getDouble(txtCantidad.getText()))
                    .fecha(new Timestamp(System.currentTimeMillis()))
                    .idProveedor(TableBaseDAO.getID("proveedor", combos.get(CBX_PROVIDER).getSelectedItem().toString()))
                    .idTipo(TableBaseDAO.getID("tipo", combos.get(CBX_TYPE_SPENDS).getSelectedItem().toString()))
                    .build();

            GastoDAO.insert(gasto);
            resetForm();
        }catch (NullPointerException ex){
            JOptionPane.showMessageDialog(null, "<html><center><p style=\"font: 15px\">Asegurate de seleccionar un Concepto y una tienda o proveedor</p><br>", "Registro de Gasto", JOptionPane.ERROR_MESSAGE);                    
        }
        
        try{
            tblSpend.setModel(GastoDAO.tableModel(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString(), combos.get(CBX_TYPE_SPENDS_FILTER).getSelectedItem().toString(), combos.get(CBX_PROVIDER_FILTER).getSelectedItem().toString()));
    
        }catch(NullPointerException ex){
            combos.get(CBX_YEAR_FILTER).setModel(GastoDAO.comboModelAño());
            combos.get(CBX_MONTH_FILTER).setModel(GastoDAO.comboModelMeses(combos.get(CBX_YEAR_FILTER)));
            tblSpend.setModel(GastoDAO.tableModel(combos.get(CBX_YEAR_FILTER).getSelectedItem().toString(), combos.get(CBX_MONTH_FILTER).getSelectedItem().toString(), combos.get(CBX_TYPE_SPENDS_FILTER).getSelectedItem().toString(), combos.get(CBX_PROVIDER_FILTER).getSelectedItem().toString()));
        }
        
    }
    
    public void resetForm(){
        txtCantidad.setText("");
    }

}
