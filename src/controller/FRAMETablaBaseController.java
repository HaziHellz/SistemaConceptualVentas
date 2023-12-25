/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import papeleria.model.Base;
import papeleria.model.Cliente;
import papeleria.model.ClienteDAO;
import papeleria.model.TableBaseDAO;
import papeleria.view.BaseTable;

/**
 *
 * @author heber
 */
public class FRAMETablaBaseController extends MouseAdapter implements ActionListener {

    //SE DEFINEN LOS INDEX DE CADA COMPONENTE EN EL ARRAY COMPONENTES
    final static private int TABLA = 0;
    final static private int CBXFILTRO = 1;
    final static private int TXTNAME = 2;
    final static private int BTNADD = 3;
    final static private int BTNDELETE = 4;
    final static private int BTNRESTAURAR = 5;
    final static private int TXTAPELLIDO = 6;
    final static private int TXTTELEFONO = 7;
    final static private int CBXTYPESALE = 0;
    private final int MINIMIZAR = 0;
    private final int SALIR = 1;
    private final int TOGRAB = 2;
    private int xMouse;
    private int yMouse;
    private final boolean defaultSize = true;

    //SE DEFINE EL TITULO DE LA TABLA BASE CONCEPTO/PROVEEDOR
    private final String titulo;

    //PARA SABER SI SERA UN ELEMENTO EDITADO O UNO NUEVO
    private boolean nuevo = true;

    //PARA SABER EL INDEX Y EL ID(EN CASO DE SER EDITADO)
    private int index = -1;
    private int id = -1;

    private List<Component> componentes;
    private List<JComboBox> combos;
    private JFrame gui;
    private List<JPanel> modificadores;
    private JTable tblClientes;
    private Base base;
    private Cliente cliente;

    //EL ARRAY COMPONENTES, CONTIENE LOS COMPONENTES DE LA VISTA, EL TITULO CORRESPONDE A LA TABLA DE LA BASE DE DATOS
    public FRAMETablaBaseController(List<Component> componentes, String titulo, List<JComboBox> combos, JFrame gui, List<JPanel> modificadores, JTable tblClientes) {
        this.componentes = componentes;
        this.combos = combos;
        this.titulo = titulo;
        this.gui = gui;
        this.modificadores = modificadores;
        this.tblClientes = tblClientes;
        actualizarTabla();
    }

    private void actualizarTabla() {
        ((JTable) componentes.get(TABLA)).setModel(TableBaseDAO.tableModel(((JComboBox) (componentes.get(CBXFILTRO))).getSelectedItem().toString(), titulo));
        if (titulo == "Clientes") {
            ((JTable) componentes.get(TABLA)).getColumnModel().getColumn(0).setMaxWidth(100);
        }
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
                if (titulo != "Clientes") {
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
                } else if (titulo == "Clientes") {
                    if (telefonoVerificado()) {
                        //SI EL TELEFONO TIENE 10 DIGITOS, ENTRA A ESTE IF
                        //CREA UNA INSTANCIA DEL CLIENTE CON LOS DATOS EN EL FORMULARIO
                        Cliente cliente = new Cliente.ClienteBuilder()
                                .setNombre(((JTextField) componentes.get(TXTNAME)).getText())
                                .setApellido(((JTextField) componentes.get(TXTAPELLIDO)).getText())
                                .setTelefono(((JTextField) componentes.get(TXTTELEFONO)).getText())
                                .build();

                        if (nuevo) {
                            // SI EL CLIENTE ES NUEVO CREA UN NUEVO CLIENTE
                            insertarCliente(cliente);
                        } else {
                            // SI EL CLIENTE EXISTE, ACTUALIZA LOS DATOS EN LA BASE DE DATOS
                            actualizarCliente(cliente);
                        }
                    } else {
                        JOptionPane.showMessageDialog(gui, "El teléfono debe tener 10 digitos", "Verificación", JOptionPane.WARNING_MESSAGE);
                    }

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

            if (titulo != "Clientes") {

                //ACTUALIZA LOS COMBOBOX DEL TIPO DE LAS OTRAS VENTANAS
                if (titulo.equals("Conceptos")) {
                    for (int i = 0; i < (combos.size() - 2); i++) {
                        if (i == 1 || i == 4 || i == 6) {
                            combos.get(i).setModel(TableBaseDAO.comboModelTodo(titulo));
                        } else {
                            combos.get(i).setModel(TableBaseDAO.comboModel(titulo));
                        }
                    }

                } else {
                    //ACTUALIZA LOS COMBOBOX DEL PROVEEDOR
                    for (int i = 5; i < combos.size(); i++) {
                        if (i != 6) {
                            combos.get(i).setModel(TableBaseDAO.comboModel(titulo));
                        } else {
                            combos.get(i).setModel(TableBaseDAO.comboModelTodo(titulo));
                        }
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

                if (titulo != "Clientes") {
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

                } else if (titulo == "Clientes") {
                    habilitarEdicionCliente();
                }
            } else {
                //SI EL INDEX ES EL MISMO, LO DESELECCIONA Y RESETEA EL INDEX, EL ID Y EL FORMULARIO
                resetForm();
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            JPanel boton = (JPanel) e.getSource();
            if (boton.equals(modificadores.get(MINIMIZAR))) {
                minimizarVentana();
            } else if (boton.equals(modificadores.get(SALIR))) {
                gui.setVisible(false);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource().equals(modificadores.get(TOGRAB))) {
            xMouse = e.getX();
            yMouse = e.getY();
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            if (e.getSource().equals(modificadores.get(SALIR))) {
                modificadores.get(SALIR).setBackground(Color.red);
            } else if (e.getSource().equals(modificadores.get(MINIMIZAR))) {
                modificadores.get(MINIMIZAR).setBackground(Color.lightGray);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            if (e.getSource().equals(modificadores.get(SALIR))) {
                modificadores.get(SALIR).setBackground(new java.awt.Color(153, 153, 153));
                /*} else if (e.getSource().equals(modificadores.get(MAXIMIZAR))) {
                modificadores.get(MAXIMIZAR).setBackground(new java.awt.Color(153, 153, 153));
                 */
            } else if (e.getSource().equals(modificadores.get(MINIMIZAR))) {
                modificadores.get(MINIMIZAR).setBackground(new java.awt.Color(153, 153, 153));
            }
        }
    }

    public void minimizarVentana() {
        gui.setExtendedState(BaseTable.ICONIFIED);

    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource().equals(modificadores.get(TOGRAB))) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            gui.setLocation(x - xMouse, y - yMouse);
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

    private void habilitarEdicionCliente() {
        //INDICA QUE NO ES NUEVO EN CASO DE USAR EL BOTON ACEPTAR
        nuevo = false;
        cliente = ClienteDAO.getDatos((String) ((JTable) componentes.get(TABLA)).getValueAt(index, 0));

        ((JTextField) componentes.get(TXTNAME)).setText(cliente.getNombre());
        ((JTextField) componentes.get(TXTAPELLIDO)).setText(cliente.getApellido());
        ((JTextField) componentes.get(TXTTELEFONO)).setText(cliente.getTelefono());

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
        base = new Base();
        cliente = new Cliente();

        ((JTextField) componentes.get(TXTNAME)).setEnabled(true);
        ((JTextField) componentes.get(TXTNAME)).setText("");
        ((JTextField) componentes.get(TXTAPELLIDO)).setText("");
        ((JTextField) componentes.get(TXTTELEFONO)).setText("");
        ((JTextField) componentes.get(TXTNAME)).requestFocus();

        ((JButton) componentes.get(BTNDELETE)).setEnabled(false);
        ((JButton) componentes.get(BTNRESTAURAR)).setEnabled(false);
        /*
        if (titulo == "Clientes") {
            ((JTable) componentes.get(TABLA)).getColumnModel().getColumn(0).setMaxWidth(100);
        }
         */
    }

    private boolean telefonoVerificado() {
        if (((JTextField) componentes.get(TXTTELEFONO)).getText().length() != 10) {
            return false;
        } else {
            return true;
        }
    }

    private void actualizarCliente(Cliente clienteActualizado) {
        //Y ACTUALIZA EL CLIENTE USANDO EL NUMERO DE TELEFONO QUE YA SE TENIA ANTES
        ClienteDAO.actualizar(clienteActualizado, this.cliente.getTelefono());
        resetFormYTableCliente();

    }

    private void insertarCliente(Cliente clienteNuevo) {
        //INSERTA EL CLIENTE USANDO LA INSTANCIA DE CLIENTE QUE CONTIENE LOS DATOS DEL FORMULARIO
        ClienteDAO.insertar(clienteNuevo);
        resetFormYTableCliente();
    }

    private void resetFormYTableCliente() {
        // RESETEA EL FORMULARIO
        resetForm();

        //ACTUALIZA LA TABLA CLIENTES EN LA VENTANA DE APARTADOS
        tblClientes.setModel(ClienteDAO.tableModel());
        tblClientes.getColumnModel().getColumn(0).setMaxWidth(100);
    }

}
