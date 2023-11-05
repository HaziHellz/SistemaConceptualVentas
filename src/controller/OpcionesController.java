/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import papeleria.view.BaseTable;
import papeleria.view.Export;

/**
 *
 * @author heber
 */
public class OpcionesController implements ActionListener {

    //SE DEFINE EL INDEX DE CADA BOTON
    private int CONCEPTOS = 0;
    private int PROVEEDORES = 1;
    private int EXPORTAR = 2;
    private int SALIR = 3;
    private List<JMenuItem> componentes; //CONTIENE LOS BOTONES DEL MENU
    private List<JComboBox> combos;
    private BaseTable baseTable = new BaseTable("Componentes", combos);
    private Export export= new Export();

    public OpcionesController(List<JMenuItem> componentes, List<JComboBox> cbx) {
        this.componentes = componentes;
        this.combos = cbx;
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
            
        } else if (e.getSource().equals(componentes.get(SALIR))) {
            System.exit(0);
        }
    }

}
