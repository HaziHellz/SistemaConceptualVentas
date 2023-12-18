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
import javax.swing.JPanel;
import papeleria.model.VentaDAO;
import papeleria.view.BaseTable;

/**
 *
 * @author heber
 */
public class ExportController extends MouseAdapter implements ActionListener {

    private List<JComboBox> combos;
    private JFrame gui;
    private List<JPanel> modificadores;

    private int xMouse;
    private int yMouse;
    private final int MINIMIZAR = 0;
    private final int SALIR = 1;
    private final int TOGRAB = 2;
    private final int CBX_CONCEPTOS = 0;
    private final int CBX_YEAR = 1;
    private final int CBX_MONTH = 2;

    public ExportController(List<JComboBox> combos, JFrame gui, List<JPanel> modificadores) {
        this.combos = combos;
        this.gui = gui;
        this.modificadores = modificadores;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(combos.get(CBX_YEAR))) {
            JComboBox cbxYear = (JComboBox) e.getSource();
            JComboBox cbxMonth = combos.get(CBX_MONTH);
            cbxMonth.setModel(VentaDAO.comboModelMeses(cbxYear));
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

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource().equals(modificadores.get(TOGRAB))) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            gui.setLocation(x - xMouse, y - yMouse);
        }
    }
    
    public void minimizarVentana() {
        gui.setExtendedState(BaseTable.ICONIFIED);

    }

    

}
