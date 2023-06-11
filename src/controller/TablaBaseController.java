/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import papeleria.model.Base;
import papeleria.model.TableBaseDAO;

/**
 *
 * @author heber
 */
public class TablaBaseController extends MouseAdapter implements ActionListener {

    /*
        componentes.add(txtName);
        componentes.add(btnAdd);
        componentes.add(btnDelete);
     */
    //SE DEFINEN LOS INDEX DE CADA COMPONENTE EN EL ARRAY COMPONENTES
    final static private int TABLA = 0;
    final static private int CBXFILTRO = 1;
    final static private int TXTNAME = 2;
    final static private int BTNADD = 3;
    final static private int BTNDELETE = 4;
    final static private int BTNRESTAURAR = 5;
    final static private int CBXTYPESALE = 0;

    //SE DEFINE EL TITULO DE LA TABLA BASE CONCEPTO/PROVEEDOR
    private final String titulo;

    //PARA SABER SI SERA UN ELEMENTO EDITADO O UNO NUEVO
    private boolean nuevo = true;

    //PARA SABER EL INDEX Y EL ID(EN CASO DE SER EDITADO)
    private int index = -1;
    private int id = -1;

    private List<Component> componentes;
    private List<JComboBox> combos;
    private Base base;

    //EL ARRAY COMPONENTES, CONTIENE LOS COMPONENTES DE LA VISTA, EL TITULO CORRESPONDE A LA TABLA DE LA BASE DE DATOS
    public TablaBaseController(List<Component> componentes, String titulo, List<JComboBox> combos) {
        this.componentes = componentes;
        this.combos = combos;
        this.titulo = titulo;
        actualizarTabla();
    }

    private void actualizarTabla() {
        ((JTable) componentes.get(TABLA)).setModel(TableBaseDAO.tableModel(((JComboBox) (componentes.get(CBXFILTRO))).getSelectedItem().toString(), titulo));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //IDENTIFICA ACTIVIDAD EN UN JBUTTON
        if (e.getSource() instanceof JButton) {
            //HACE UNA REFERENCIA HACIA EL BUTTON EN SOURCE
            JButton source = (JButton) e.getSource();

            //IDENTIFICA EL BOTON QUE FUE PRESIONADO
            if (source.getText().equals("Aceptar")) {
                //BOTON ACEPTAR
                //SI EL ELEMENTO ES UN ELEMENTO NUEVO LO INGRESA A LA BASE DE DATOS, SI NO ES NUEVO, LO EDITA
                if (nuevo) {
                    base = new Base.TipoBuilder().nameBase(((JTextField) componentes.get(TXTNAME)).getText()).build();
                    TableBaseDAO.agregarBase(base, titulo);
                    resetForm();
                } else {
                    base = new Base.TipoBuilder().nameBase(((JTextField) componentes.get(TXTNAME)).getText()).idBase(id).build();
                    TableBaseDAO.editarNombre(base, titulo);
                    resetForm();
                }
            } else if (source.getText().equals("Eliminar")) {
                base = new Base.TipoBuilder().idBase(id).build();
                TableBaseDAO.eliminar(base, titulo);
                resetForm();
            } else if (source.getText().equals("Restaurar")) {
                base = new Base.TipoBuilder().idBase(id).build();
                TableBaseDAO.restaurar(base, titulo);
                resetForm();
            }

            //ACTUALIZA LOS COMBOBOX DEL TIPO DE LAS OTRAS VENTANAS
            if (titulo.equals("Conceptos")) {
                for (int i = 0; i < (combos.size()-2); i++) {
                    if (i == 1 || i == 4 || i == 6) {
                        combos.get(i).setModel(TableBaseDAO.comboModelTodo(titulo));
                    }else{
                        combos.get(i).setModel(TableBaseDAO.comboModel(titulo));
                    }
                }
                
            }else{
                //ACTUALIZA LOS COMBOBOX DEL PROVEEDOR
                for (int i = 5; i < combos.size(); i++) {
                    if (i != 6) {
                        combos.get(i).setModel(TableBaseDAO.comboModel(titulo));
                    }else{
                        combos.get(i).setModel(TableBaseDAO.comboModelTodo(titulo));
                    }
                }
            }

        }

        if (e.getSource() instanceof JComboBox) {
            JComboBox source = (JComboBox) e.getSource();
            if (source.equals((JComboBox) componentes.get(CBXFILTRO))) {
                resetForm();
            }
        }

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //IDENTIFICA SI ES EN JTABLE
        if (e.getSource() instanceof JTable) {
            //SELECCIONA LA TABLA EN SOURCE
            JTable source = (JTable) e.getSource();
            //CAMBIA DE INDEX O DESELECCIONA EL INDEX
            if (index != source.getSelectedRow()) {
                index = source.getSelectedRow();

                //AGARRA EL ID PARA EN CASO DE SER EDITADO
                id = TableBaseDAO.getID(titulo, (String) source.getValueAt(index, 0));

                if (((JComboBox) componentes.get(CBXFILTRO)).getSelectedItem().equals("Todos")) {
                    Base base = new Base.TipoBuilder().idBase(id).build();
                    if (TableBaseDAO.consultarExistencia(base, titulo)) {
                        habilitarEdicion();
                    } else {
                        habilitarRestauracion();
                    }
                } else if (((JComboBox) componentes.get(CBXFILTRO)).getSelectedItem().equals("Eliminados")) {
                    habilitarRestauracion();
                } else {
                    habilitarEdicion();
                }

                //PARA PRUEBA
                //System.out.println(index + "    " + id + "      " + nuevo);
            } else {
                //SI EL INDEX ES EL MISMO, LO DESELECCIONA Y RESETEA EL INDEX, EL ID Y EL FORMULARIO
                resetForm();
                //System.out.println(index + "    " + id + "      " + nuevo);   PARA PRUEBA
            }
        }
    }

    private void habilitarEdicion() {
        //INDICA QUE NO ES NUEVO EN CASO DE USAR EL BOTON ACEPTAR
        nuevo = false;
        ((JTextField) componentes.get(TXTNAME)).setEnabled(true);
        ((JTextField) componentes.get(TXTNAME)).setText((String) ((JTable) componentes.get(TABLA)).getValueAt(index, 0));
        ((JButton) componentes.get(BTNDELETE)).setEnabled(true);
        ((JButton) componentes.get(BTNRESTAURAR)).setEnabled(false);
        ((JTextField) componentes.get(TXTNAME)).requestFocus();

    }

    private void habilitarRestauracion() {
        //INDICA QUE NO ES NUEVO EN CASO DE USAR EL BOTON ACEPTAR
        nuevo = false;
        ((JTextField) componentes.get(TXTNAME)).setText((String) ((JTable) componentes.get(TABLA)).getValueAt(index, 0));
        ((JButton) componentes.get(BTNRESTAURAR)).setEnabled(true);
        ((JButton) componentes.get(BTNDELETE)).setEnabled(false);
        ((JTextField) componentes.get(TXTNAME)).setEnabled(false);

    }

    private void resetForm() {
        //DEVUELVE LOS OBJETOS A LOS VALORES INICIALES
        actualizarTabla();
        index = -1;
        id = -1;
        nuevo = true;
        ((JTextField) componentes.get(TXTNAME)).setEnabled(true);
        ((JTextField) componentes.get(TXTNAME)).setText("");
        ((JTextField) componentes.get(TXTNAME)).requestFocus();
        ((JButton) componentes.get(BTNDELETE)).setEnabled(false);
        ((JButton) componentes.get(BTNRESTAURAR)).setEnabled(false);

    }

}
