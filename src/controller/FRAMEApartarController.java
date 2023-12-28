/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import papeleria.model.AbonoDAO;
import papeleria.model.Apartado;
import papeleria.model.ApartadoDAO;
import papeleria.model.Base;
import papeleria.model.Cliente;
import papeleria.model.ClienteDAO;
import papeleria.model.TableBaseDAO;
import papeleria.model.VentaTipo;
import papeleria.view.BaseTable;

/**
 *
 * @author heber
 */
public class FRAMEApartarController extends MouseAdapter implements ActionListener {

    private Cliente cliente;
    private Apartado apartado;
    private JTextField[] textFields;
    private JLabel[] labels;
    private List<JPanel> modificadores;
    private JFrame gui;
    private JTextArea txtaDescripcion;
    private JComboBox<String> cbxConcepto;
    private JButton[] buttons;
    private List<JTable> tablas;
    private int[] index;

    private final int CLIENTES = 0;
    private final int APARTADOS = 1;
    private final int APORTACIONES = 2;

    private final int TXT_NOMBRE = 0;
    private final int TXT_APELLIDO = 1;
    private final int TXT_TELEFONO = 2;
    private final int TXT_TOTAL = 3;
    private final int TXT_ABONO = 4;

    private final int LBL_ABONO = 0;
    private final int LBL_A_PAGAR = 1;
    private final int LBL_PAGADO = 2;
    private final int LBL_FECHA_MAXIMA = 3;

    private final int BTN_ABONAR = 0;
    private final int BTN_APARTAR = 1;
    private final int BTN_FRAME_PRINCIPAL_APARTAR = 2;
    private final int BTN_FRAME_PRINCIPAL_ABONAR = 3;

    private final int COL_TELEFONO = 0;

    private final int MINIMIZAR = 0;
    private final int SALIR = 1;
    private final int TOGRAB = 2;
    private boolean nuevoCliente;
    private boolean nuevoApartado;
    private boolean nuevoAbono;
    private boolean abonar;
    private int xMouse;
    private int yMouse;

    public FRAMEApartarController(Cliente cliente, Apartado apartado, JTextField[] textFields, JLabel[] labels, List<JPanel> modificadores, JFrame gui, JTextArea txtaDescripcion, JComboBox<String> cbxConcepto, boolean abonar, JButton[] buttons, List<JTable> tablas, int[] index) {
        this.cliente = cliente;
        this.apartado = apartado;
        this.textFields = textFields;
        this.labels = labels;
        this.modificadores = modificadores;
        this.gui = gui;
        this.txtaDescripcion = txtaDescripcion;
        this.cbxConcepto = cbxConcepto;
        this.abonar = abonar;
        this.buttons = buttons;
        this.tablas = tablas;
        this.index = index;
        configuracionInicial();
    }

    private void configuracionInicial() {
        try {
            if (!cliente.getNombre().equals("vacio")) {
                nuevoCliente = false;
                textFields[TXT_NOMBRE].setEnabled(false);
                textFields[TXT_APELLIDO].setEnabled(false);
                textFields[TXT_TELEFONO].setEnabled(false);

                textFields[TXT_NOMBRE].setText(cliente.getNombre());
                textFields[TXT_APELLIDO].setText(cliente.getApellido());
                textFields[TXT_TELEFONO].setText(cliente.getTelefono());

                if (!apartado.getCliente().getNombre().equals("vacio")) {
                    nuevoApartado = false;
                    txtaDescripcion.setText(apartado.getDescripcion());
                    cbxConcepto.setSelectedItem(TableBaseDAO.getNombreByID(String.valueOf(apartado.getIdTipo()), "tipo").getNombreBase());
                    textFields[TXT_TOTAL].setText(String.valueOf(apartado.getTotalPagar()));

                    labels[LBL_PAGADO].setText("Pagado: $" + apartado.getPagado());
                    labels[LBL_A_PAGAR].setText("A pagar: $" + apartado.getRestante());
                    labels[LBL_FECHA_MAXIMA].setText("Fecha máxima: " + apartado.getFechaMaxima().toString());

                    abonara();

                } else {
                    nuevoApartado = true;
                    txtaDescripcion.setText("");
                    cbxConcepto.setSelectedItem("Example");
                    buttons[BTN_ABONAR].setEnabled(false);
                }

            } else {
                nuevoCliente = true;
                textFields[TXT_NOMBRE].setEnabled(true);
                textFields[TXT_APELLIDO].setEnabled(true);
                textFields[TXT_TELEFONO].setEnabled(true);
                buttons[BTN_ABONAR].setEnabled(false);

                textFields[TXT_NOMBRE].setText("");
                textFields[TXT_APELLIDO].setText("");
                textFields[TXT_TELEFONO].setText("");
            }

        } catch (NullPointerException ex) {
            //ex.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton source = (JButton) e.getSource();
            if (source.getText().equals("Apartar")) {
                System.out.println("PROCEDIMIENTO APARTAR");
                apartar();
            } else if (source.getText().equals("Editar")) {
                System.out.println("PROCEDIMIENTO EDITAR");
                editar();
            } else if (source.getText().equals("Abonar")) {
                System.out.println("PROCEDIMIENTO ABONAR");
                abonar();
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

    private void editar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    private void abonar() {
        double abono = Double.parseDouble(textFields[TXT_ABONO].getText());
        ApartadoDAO.abonar(cliente, apartado, abono);
        resetMainFrameApartadosCliente();
        gui.setVisible(false);
    }

    private void apartar() {
        //SI EL CLIENTE ES NUEVO
        if (nuevoCliente) {
            //SE CREA UN OBJETO CLIENTE CON LOS DATOS PROPORCIONADOS POR EL USUARIO
            cliente = new Cliente.ClienteBuilder()
                    .setTelefono(textFields[TXT_TELEFONO].getText())
                    .setNombre(textFields[TXT_NOMBRE].getText())
                    .setApellido(textFields[TXT_APELLIDO].getText())
                    .build();
            
            /*
            TAMBIEN SE CREA UN OBJETO APARTADO CON EL FIN DE HACER MAS LEGIBLE EL CODIGOUSANDO LOS DATOS PROPORCIONADOS
            POR EL USUARIO
            */
            apartado = getDatosApartado();
            
            //SE ESPECIFICA EL ABONO
            double abono = Double.parseDouble(textFields[TXT_ABONO].getText());
            
            //SE LLAMA AL METODO PARA INSERTAR EL APARTADO Y CLIENTE NUEVOS A LA BASE DE DATOS
            ApartadoDAO.insertarApartadoYCliente(cliente, apartado, abono);
            
            //SE RESETEAN LAS VARIABLES DE CLIENTE Y APARTADO ASI COMO LOS INDEX Y TABLAS DE LA VENTANA PRINCIPAL
            resetAllMainFrameApartados();
            gui.setVisible(false);
        } else {
            apartado = getDatosApartado();
            double abono = Double.parseDouble(textFields[TXT_ABONO].getText());
            
            ApartadoDAO.insertarApartado(cliente, apartado, abono);

            resetMainFrameApartadosCliente();
            
            gui.setVisible(false);
        }
        
    }

    private void abonara() {
        if (abonar) {
            cbxConcepto.setEnabled(false);
            textFields[TXT_TOTAL].setEditable(false);
            textFields[TXT_ABONO].setText("");
            labels[LBL_ABONO].setText("Cantidad: ");
            textFields[TXT_ABONO].requestFocus();
            buttons[BTN_APARTAR].setEnabled(false);
            txtaDescripcion.setEditable(false);

        } else if (!abonar) {
            textFields[TXT_ABONO].setText(String.valueOf(apartado.getAbonos().get(apartado.getAbonos().size() - 1).getCantidadTipo()));
            buttons[BTN_ABONAR].setEnabled(false);
        }
    }

    public void resetAllMainFrameApartados() {
        System.out.println(index);
        index[CLIENTES] = index[APARTADOS] = index[APORTACIONES] = -1;
        cliente = new Cliente();
        apartado = new Apartado();

        buttons[BTN_FRAME_PRINCIPAL_ABONAR].setEnabled(false);
        buttons[BTN_FRAME_PRINCIPAL_APARTAR].setText("Apartar");

        tablas.get(APARTADOS).setModel(tableModelVacio());
        tablas.get(APORTACIONES).setModel(tableModelVacio());
        tablas.get(CLIENTES).setModel(ClienteDAO.tableModel());
        tablas.get(CLIENTES).getColumnModel().getColumn(COL_TELEFONO).setMaxWidth(100);
    }
    
     private void resetMainFrameApartadosCliente() {
        //RESETEA LOS INDEX 
        index[APARTADOS] = index[APORTACIONES] = -1;
        apartado = new Apartado();
        buttons[BTN_FRAME_PRINCIPAL_APARTAR].setText("Apartar");
        buttons[BTN_FRAME_PRINCIPAL_ABONAR].setEnabled(false);
        tablas.get(APARTADOS).setModel(ApartadoDAO.tableModel(cliente));
        tablas.get(APORTACIONES).setModel(tableModelVacio());
    }
     
     private void resetMainFrameAportacionesApartado() {
        index[APORTACIONES] = -1;
        tablas.get(APORTACIONES).setModel(AbonoDAO.tableModel(cliente, apartado));
    }

    //DEVUELVE UN TABLE MODEL VACIO
    private TableModel tableModelVacio() {
        return new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{}
        );
    }

    private Apartado getDatosApartado() {
        return new Apartado.ApartadoBuilder()
                    .setCliente(cliente)
                    .setDescripcion(txtaDescripcion.getText())
                    .setIdTipo((TableBaseDAO.getID("tipo", cbxConcepto.getSelectedItem().toString())))
                    .setTotalPagar(Double.parseDouble(textFields[TXT_TOTAL].getText()))
                    .build();
    }

}
