/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JComboBox;
import papeleria.model.VentaDAO;

/**
 *
 * @author heber
 */
public class ExportController implements ActionListener{
    private List<JComboBox> combos;
    private final int CBX_CONCEPTOS = 0;
    private final int CBX_YEAR = 1;
    private final int CBX_MONTH = 2;
    
    public ExportController(List<JComboBox> combos) {
        this.combos = combos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource().equals(combos.get(CBX_YEAR))) {
            JComboBox cbxYear = (JComboBox) e.getSource();
            JComboBox cbxMonth = combos.get(CBX_MONTH);
            cbxMonth.setModel(VentaDAO.comboModelMeses(cbxYear));
        }
    }
    
}
