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
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;

/**
 *
 * @author heber
 */
public class GastoController extends MouseAdapter implements ActionListener, KeyListener{

    List<JComboBox> combos;
    List<JButton> buttons;
    JTextField txtCantidad;
    JTable tblSpend;

    public GastoController(List<JComboBox> combos, List<Object> componentes) {
        this.combos = combos;
        txtCantidad = (JTextField) componentes.get(0);
        buttons.add((JButton) componentes.get(1));
        buttons.add((JButton) componentes.get(2));
        tblSpend = (JTable) componentes.get(3);
    }
    
    @Override
    public void mouseReleased(MouseEvent e){
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
    
    
}
