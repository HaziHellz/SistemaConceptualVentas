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
import java.time.LocalDateTime;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;
import papeleria.model.AbonoDAO;
import papeleria.model.Apartado;
import papeleria.model.ApartadoDAO;
import papeleria.model.Cliente;
import papeleria.model.ClienteDAO;
import papeleria.view.Apartar;

/**
 *
 * @author heber
 */
public class TABApartadosController extends MouseAdapter implements ActionListener, KeyListener {

    private final int CLIENTES = 0;
    private final int APARTADOS = 1;
    private final int APORTACIONES = 2;
    private final int COL_TELEFONO = 0;

    private final int APARTAR = 0;
    private final int ABONAR = 1;
    private final int EDITAR_ABONO = 2;

    private JTextField txtSearchCostumer;
    private int index[] = new int[3];
    private Cliente cliente = new Cliente();
    private Apartado apartado = new Apartado();
    private List<JTable> tablas;
    private List<JButton> botones;
    private JLabel lblInformacion;

    private Apartar apartar = new Apartar(cliente, apartado, index, tablas, false, botones, false);

    public TABApartadosController(List<JTable> tablas, List<JButton> botones, JTextField txtSearchCostumer, JLabel lblInformacion) {
        this.tablas = tablas;
        this.botones = botones;
        this.txtSearchCostumer = txtSearchCostumer;
        this.lblInformacion = lblInformacion;
        index[CLIENTES] = -1;
        index[APARTADOS] = -1;
        index[APORTACIONES] = -1;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() instanceof JButton) {
            JButton boton = (JButton) e.getSource();

            if (boton.equals(botones.get(APARTAR))) {
                if (boton.getText().equals("Apartar")) {
                    apartar.dispose();
                    apartar = new Apartar(cliente, apartado, index, tablas, false, botones, false);
                    apartar.setVisible(true);
                } else if (boton.getText().equals("Editar Apartado")){
                    //ALGORITMO PARA EDITAR APARTADO
                    /*apartar.dispose();
                    apartar = new Apartar(cliente, apartado, index, tablas, false, botones, true);
                    apartar.setVisible(true);
                    */
                   JOptionPane.showMessageDialog(null, "Esta opción está en desarrollo", "No disponible", JOptionPane.INFORMATION_MESSAGE);
                } 
            } else if (boton.equals(botones.get(ABONAR))) {
                apartar.dispose();
                apartar = new Apartar(cliente, apartado, index, tablas, true, botones, false);
                apartar.setVisible(true);
            }else if (boton.equals(botones.get(EDITAR_ABONO))) {
                apartar.dispose();
                JOptionPane.showMessageDialog(null, "Esta opción está en desarrollo", "No disponible", JOptionPane.INFORMATION_MESSAGE);
                //apartar = new Apartar(cliente, apartado, index, tablas, true, botones, true);
                //apartar.setVisible(true);
            }

        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        //SI FUE UNA TABLA LA QUE ACTIVO EL EVENTO
        if (e.getSource() instanceof JTable) {

            //SE OBTIENE LA DIRECCION DE MEMORIA DE ESA TABLA Y SE GUARDA EN SOURCE PARA PODER MANIPULAR LA TABLA
            JTable source = (JTable) e.getSource();

            //SE VERIFICA QUE LA TABLA DE CLIENTES HAYA ACTIVADO EL EVENTO
            if (source.equals(tablas.get(CLIENTES))) {
                //SE COMPARA EL INDEX SELECCIONADO DE LA TABLA PARA TENER LA REFERENCIA
                if (index[CLIENTES] != tablas.get(CLIENTES).getSelectedRow()) {
                    //SI EL INDEX ES DISTINTO AL SELECCIONADO, ENTONCES SE ACTUALIZA EL INDEX
                    index[CLIENTES] = tablas.get(CLIENTES).getSelectedRow();

                    //UNA VEZ ACTUALIZADO EL INDEX, SE ACTUALIZA EL CLIENTE SELECCIONADO
                    cliente = ClienteDAO.getDatos((String) (tablas.get(CLIENTES)).getValueAt(index[CLIENTES], 0));
                    lblInformacion.setText("Cliente: " + cliente.getNombre() + " " + cliente.getApellido());
                    //CARGA LA TABLA DE APARTADOS CON LOS APARTADOS QUE HA HECHO EL CLIENTE
                    //tablas.get(APARTADOS).setModel(ApartadoDAO.tableModel(cliente));
                    resetApartado();

                } else {
                    //SI EL INDEX SELECCIONADO ES EL MISMO QUE YA ESTABA SELECCIONADO, SE RESETEAN LAS TABLAS
                    resetAll();
                }

                //SI LA TABLA DE CLIENTES NO ACTIVO EL EVENTO ENTONCES SE VERIFICA SI ES LA TABLA DE APARTADOS:
            } else if (source.equals(tablas.get(APARTADOS))) {
                
                //SE COMPARA EL INDEX ANTERIOR CON EL SELECCIONADO DE LA TABLA PARA TENER LA REFERENCIA
                if (index[APARTADOS] != tablas.get(APARTADOS).getSelectedRow()) {
                    //SI EL INDEX ES DISTINTO AL SELECCIONADO, ENTONCES SE ACTUALIZA EL INDEX
                    index[APARTADOS] = tablas.get(APARTADOS).getSelectedRow();

                    //UNA VEZ ACTUALIZADO EL INDEX, SE ACTUALIZA EL APARTADO SELECCIONADO
                    apartado = ApartadoDAO.getDatos(cliente, (LocalDateTime) (tablas.get(APARTADOS)).getValueAt(index[APARTADOS], 0));
                    botones.get(APARTAR).setText("Editar Apartado");
                    botones.get(ABONAR).setEnabled(true);
                    //CARGA LA TABLA DE APARTADOS CON LOS APARTADOS QUE HA HECHO EL CLIENTE
                    tablas.get(APORTACIONES).setModel(AbonoDAO.tableModel(cliente, apartado));
                } else {
                //SI EL INDEX ANTERIOR ES IGUAL AL SELECCIONADO, SE RESETEA LA TABLA DE APARTADO
                    resetApartado();
                }
                //SI LA TABLA DE APARTADOS NO ACTIVO EL EVENTO ENTONCES SE VERIFICA SI ES LA TABLA DE APORTACIONES:
            } else if (source.equals(tablas.get(APORTACIONES))) {
                if (index[APORTACIONES] != tablas.get(APORTACIONES).getSelectedRow()) {
                    //SI EL INDEX ES DISTINTO AL SELECCIONADO, ENTONCES SE ACTUALIZA EL INDEX
                    index[APORTACIONES] = tablas.get(APORTACIONES).getSelectedRow();
                    botones.get(EDITAR_ABONO).setEnabled(true);
                    


                    
                } else {
                    System.out.println(index[APARTADOS]);
                    resetAportacion();
                }

            }
        }
    }

    //RESETEA TODAS LAS TABLAS Y LOS INDEXS
    public void resetAll() {
        System.out.println(index);
        index[CLIENTES] = index[APARTADOS] = index[APORTACIONES] = -1;
        System.out.println(index + "\n");
        cliente = new Cliente();
        apartado = new Apartado();
        
        lblInformacion.setText("Cliente: ");

        botones.get(ABONAR).setEnabled(false);
        botones.get(APARTAR).setText("Apartar");

        tablas.get(CLIENTES).setModel(ClienteDAO.buscarClienteTableModel(txtSearchCostumer.getText()));
        tablas.get(CLIENTES).getColumnModel().getColumn(COL_TELEFONO).setMaxWidth(100);
        tablas.get(APARTADOS).setModel(tableModelVacio());
        tablas.get(APORTACIONES).setModel(tableModelVacio());
    }

    //DEVUELVE UN TABLE MODEL VACIO
    private TableModel tableModelVacio() {
        return new javax.swing.table.DefaultTableModel(
                new Object[][]{},
                new String[]{}
        );
    }

    //RESETEA LA TABLA DE APARTADOS Y ABONOS/APORTACIONES
    private void resetApartado() {
        //RESETEA LOS INDEX 
        index[APARTADOS] = index[APORTACIONES] = -1;
        apartado = new Apartado();
        botones.get(APARTAR).setText("Apartar");
        botones.get(ABONAR).setEnabled(false);
        tablas.get(APARTADOS).setModel(ApartadoDAO.tableModel(cliente));
        tablas.get(APORTACIONES).setModel(tableModelVacio());
    }

    //RESETEA LA TABLA DE APORTACIONES Y EL INDEX SELECCIONADO
    private void resetAportacion() {
        index[APORTACIONES] = -1;
        tablas.get(APORTACIONES).setModel(AbonoDAO.tableModel(cliente, apartado));
        botones.get(EDITAR_ABONO).setEnabled(false);
    }

    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getSource().equals(txtSearchCostumer)) {
            resetAll();
        }
    }

}
