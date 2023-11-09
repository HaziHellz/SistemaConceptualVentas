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
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import papeleria.view.BaseTable;
import papeleria.view.Export;
import papeleria.view.GUI;

/**
 *
 * @author heber
 */
public class OpcionesController implements MouseListener, MouseMotionListener, ActionListener {

    //SE DEFINE EL INDEX DE CADA BOTON
    private int CONCEPTOS = 0;
    private int PROVEEDORES = 1;
    private int EXPORTAR = 2;
    private int MINIMIZAR = 0;
    private int MAXIMIZAR = 1;
    private int SALIR = 2;
    private int xMouse;
    private int yMouse;
    private GUI gui;

    
    private List<JMenuItem> componentes; //CONTIENE LOS BOTONES DEL MENU
    private List<JComboBox> combos;
    private List<JPanel> modificadores;
    private BaseTable baseTable = new BaseTable("Componentes", combos);
    private Export export = new Export();

    public OpcionesController(List<JMenuItem> componentes, List<JComboBox> cbx, GUI gui, List<JPanel> modificadores) {
        this.componentes = componentes;
        this.combos = cbx;
        this.gui = gui;
        this.modificadores = modificadores;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //SI ES PRESIONADO EL BOTON CONCEPTOS VVVVVV
        if (e.getSource().equals(componentes.get(CONCEPTOS))) {
            baseTable.dispose();
            baseTable = BaseTable.getInstance("Conceptos", combos);
            baseTable.setVisible(true);

            //SI ES PRESIONADO EL BOTON PROVEEDORES VVVVVV
        } else if (e.getSource().equals(componentes.get(PROVEEDORES))) {
            baseTable.dispose();
            baseTable = BaseTable.getInstance("Proveedores", combos);
            baseTable.setVisible(true);
            //SI ES PRESIONADO EL BOTON EXPORTAR VVVVVV
        } else if (e.getSource().equals(componentes.get(EXPORTAR))) {
            if (baseTable.isVisible()) {
                baseTable.dispose();
            }
            if (!export.isVisible()) {
                export.setVisible(true);
            }

        }
    }
    
    public void maximizarVentana(){
        gui.setExtendedState(GUI.MAXIMIZED_BOTH);
    }
    
    public void minimizarVentana(){
        gui.setExtendedState(1);
 
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        int x = e.getXOnScreen();
        int y = e.getYOnScreen();
        gui.setLocation(x - xMouse - 120, y - yMouse);
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
            }
            else if (boton.equals(modificadores.get(SALIR))) {
                System.exit(0);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        xMouse = e.getX();
        yMouse = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            if (e.getSource().equals(modificadores.get(SALIR))) {
                modificadores.get(SALIR).setBackground(Color.red);
            } else if(e.getSource().equals(modificadores.get(MINIMIZAR))){
                modificadores.get(MINIMIZAR).setBackground(Color.lightGray);
            } else if(e.getSource().equals(modificadores.get(MAXIMIZAR))){
                modificadores.get(MAXIMIZAR).setBackground(Color.lightGray);
            }
        }
    }

    @Override
    public void mouseExited(MouseEvent e) {
        if (e.getSource() instanceof JPanel) {
            if (e.getSource().equals(modificadores.get(SALIR))) {
                modificadores.get(SALIR).setBackground(new java.awt.Color(153,153,153));
            } else if(e.getSource().equals(modificadores.get(MAXIMIZAR))){
                modificadores.get(MAXIMIZAR).setBackground(new java.awt.Color(153,153,153));
            } else if(e.getSource().equals(modificadores.get(MINIMIZAR))){
                modificadores.get(MINIMIZAR).setBackground(new java.awt.Color(153,153,153));
            }
        }
    }

}
