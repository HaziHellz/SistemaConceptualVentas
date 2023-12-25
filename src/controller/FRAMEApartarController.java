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
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import papeleria.model.Apartado;
import papeleria.model.Cliente;
import papeleria.model.TableBaseDAO;
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

    private final int MINIMIZAR = 0;
    private final int SALIR = 1;
    private final int TOGRAB = 2;
    private int xMouse;
    private int yMouse;

    public FRAMEApartarController(Cliente cliente, Apartado apartado, JTextField[] textFields, JLabel[] labels, List<JPanel> modificadores, JFrame gui, JTextArea txtaDescripcion, JComboBox<String> cbxConcepto) {
        this.cliente = cliente;
        this.apartado = apartado;
        this.textFields = textFields;
        this.labels = labels;
        this.modificadores = modificadores;
        this.gui = gui;
        this.txtaDescripcion = txtaDescripcion;
        this.cbxConcepto = cbxConcepto;
        configuracionInicial();
    }

    private void configuracionInicial() {
        try {
            if (!cliente.getNombre().equals("vacio")) {
                textFields[TXT_NOMBRE].setEnabled(false);
                textFields[TXT_APELLIDO].setEnabled(false);
                textFields[TXT_TELEFONO].setEnabled(false);
                
                textFields[TXT_NOMBRE].setText(cliente.getNombre());
                textFields[TXT_APELLIDO].setText(cliente.getApellido());
                textFields[TXT_TELEFONO].setText(cliente.getTelefono());
                
                if (!apartado.getCliente().getNombre().equals("vacio")) {
                    txtaDescripcion.setText(apartado.getDescripcion());
                    cbxConcepto.setSelectedItem(TableBaseDAO.getNombreByID(String.valueOf(apartado.getIdTipo()), "tipo").getNombreBase());
                    textFields[TXT_TOTAL].setText(String.valueOf(apartado.getTotalPagar()));
                    textFields[TXT_ABONO].setText(String.valueOf(apartado.getAbonos().get(apartado.getAbonos().size()-1).getCantidadTipo()));
                    labels[LBL_PAGADO].setText("Pagado: $" + apartado.getPagado());
                    labels[LBL_A_PAGAR].setText("A pagar: $" + apartado.getRestante());
                    labels[LBL_FECHA_MAXIMA].setText("Fecha máxima: " + apartado.getFechaMaxima().toString());
                    
                } else{
                    txtaDescripcion.setText("");
                    cbxConcepto.setSelectedItem("Example");
                }
                
            } else {
                textFields[TXT_NOMBRE].setEnabled(true);
                textFields[TXT_APELLIDO].setEnabled(true);
                textFields[TXT_TELEFONO].setEnabled(false);
                
                textFields[TXT_NOMBRE].setText("");
                textFields[TXT_APELLIDO].setText("");
                textFields[TXT_TELEFONO].setText("");
            }
        } catch (NullPointerException ex){
            System.out.println("EXCEPCIOOON NULL EN FRAMEAPARTARCONTROLLER CONFIGURACION INICIAL");
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

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

}
