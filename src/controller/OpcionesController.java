/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import papeleria.view.BaseTable;
import papeleria.view.Export;
import papeleria.view.GUI;

/**
 *
 * @author heber
 */
public class OpcionesController implements WindowListener, MouseListener, MouseMotionListener, ActionListener {

    //SE DEFINE EL INDEX DE CADA BOTON
    private int CONCEPTOS = 0;
    private int PROVEEDORES = 1;
    private int EXPORTAR = 2;
    private int CLIENTES = 3;
    private int MINIMIZAR = 0;
    private int MAXIMIZAR = 1;
    private boolean defaultSize = true;
    private int SALIR = 2;
    private int TOGRAB = 3;
    private int xMouse;
    private int yMouse;
    private JFrame gui;

    private List<JMenuItem> componentes; //CONTIENE LOS BOTONES DEL MENU
    private List<JComboBox> combos;
    private List<JPanel> modificadores;
    private JTable tblClientes;
    private BaseTable baseTable = new BaseTable("Componentes", combos, tblClientes);
    private Export export = new Export();

    public OpcionesController(List<JMenuItem> componentes, List<JComboBox> cbx, JFrame gui, List<JPanel> modificadores, JTable tblClientes) {
        this.componentes = componentes;
        this.combos = cbx;
        this.gui = gui;
        this.modificadores = modificadores;
        this.tblClientes = tblClientes;
    }

    
    @Override
    public void actionPerformed(ActionEvent e) {
        //SI ES PRESIONADO EL BOTON CONCEPTOS VVVVVV
        if (e.getSource().equals(componentes.get(CONCEPTOS))) {
            baseTable.dispose();
            baseTable = BaseTable.getInstance("Conceptos", combos, tblClientes);
            baseTable.setVisible(true);

            //SI ES PRESIONADO EL BOTON PROVEEDORES VVVVVV
        } else if (e.getSource().equals(componentes.get(PROVEEDORES))) {
            baseTable.dispose();
            baseTable = BaseTable.getInstance("Proveedores", combos, tblClientes);
            baseTable.setVisible(true);
            //SI ES PRESIONADO EL BOTON EXPORTAR VVVVVV
        } else if (e.getSource().equals(componentes.get(EXPORTAR))) {
            if (baseTable.isVisible()) {
                baseTable.dispose();
            }
            if (!export.isVisible()) {
                //export.setVisible(true);
                JOptionPane.showMessageDialog(gui, "Esta opción está en desarrollo", "No disponible", JOptionPane.INFORMATION_MESSAGE);
            }

        } else if(e.getSource().equals(componentes.get(CLIENTES))){
            baseTable.dispose();
            baseTable = BaseTable.getInstance("Clientes", combos , tblClientes);
            baseTable.setVisible(true);
        }
    }

    public void maximizarVentana() {
        if (defaultSize) {
            gui.setExtendedState(GUI.MAXIMIZED_BOTH);
            modificadores.get(MAXIMIZAR).removeAll();
            modificadores.get(MAXIMIZAR).add(new javax.swing.JLabel(new javax.swing.ImageIcon(getClass().getResource("/images/re-scale-icon.png")))); 
            gui.setLocation(0, 0);
            defaultSize = false;
        }else{
            gui.setExtendedState(GUI.NORMAL);
            gui.setSize(1115, 700);
            modificadores.get(MAXIMIZAR).removeAll();
            modificadores.get(MAXIMIZAR).add(new javax.swing.JLabel(new javax.swing.ImageIcon(getClass().getResource("/images/maximize-icon.png")))); 
            defaultSize = true;
        }

    }

    public void minimizarVentana() {
        gui.setExtendedState(GUI.ICONIFIED);
      
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (e.getSource().equals(modificadores.get(TOGRAB))) {
            int x = e.getXOnScreen();
            int y = e.getYOnScreen();
            gui.setLocation(x - xMouse - 120, y - yMouse);
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            JPanel boton = (JPanel) e.getSource();
            if (boton.equals(modificadores.get(MINIMIZAR))) {
                minimizarVentana();
            } else if (boton.equals(modificadores.get(MAXIMIZAR))) {
                maximizarVentana();
            } else if (boton.equals(modificadores.get(SALIR))) {
                System.exit(0);
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
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            if (e.getSource().equals(modificadores.get(SALIR))) {
                modificadores.get(SALIR).setBackground(Color.red);
            } else if (e.getSource().equals(modificadores.get(MINIMIZAR))) {
                modificadores.get(MINIMIZAR).setBackground(Color.lightGray);
            } else if (e.getSource().equals(modificadores.get(MAXIMIZAR))) {
                modificadores.get(MAXIMIZAR).setBackground(Color.lightGray);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            if (e.getSource().equals(modificadores.get(SALIR))) {
                modificadores.get(SALIR).setBackground(new java.awt.Color(153, 153, 153));
            } else if (e.getSource().equals(modificadores.get(MAXIMIZAR))) {
                modificadores.get(MAXIMIZAR).setBackground(new java.awt.Color(153, 153, 153));
                
            } else if (e.getSource().equals(modificadores.get(MINIMIZAR))) {
                modificadores.get(MINIMIZAR).setBackground(new java.awt.Color(153, 153, 153));
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
    
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
    
    }

    @Override
    public void windowIconified(WindowEvent e) {
        
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        if (defaultSize) {
            gui.setExtendedState(GUI.NORMAL);
            gui.setSize(1115, 700);
            
        } else{
            gui.setExtendedState(GUI.MAXIMIZED_BOTH );
            
        }
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

}
