/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import papeleria.model.TipoDAO;

/**
 *
 * @author heber
 */
public class VentasController implements ActionListener{

    private int TXT_PRICE = 0;
    private int CBX_TYPE_SALE = 1;
    private int BTN_SELL = 2;
    private int TBL_OBJECT_LIST = 3;

    private List<Object> componentes;

    public VentasController(List<Object> componentes) {
        this.componentes = componentes;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
   
    }

}
