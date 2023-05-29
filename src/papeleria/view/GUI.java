/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package papeleria.view;

import controller.HistorialController;
import controller.OpcionesController;
import controller.VentasController;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JTable;
import papeleria.model.TipoDAO;
import papeleria.model.VentaDAO;

/**
 *
 * @author heber
 */
public class GUI extends javax.swing.JFrame {

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        this.setLocationRelativeTo(this);
        this.setTitle("Sistema Conceptual");
        Thread historial = new Thread(new Historial());
        historial.start();
        
        opciones();
    }

    
    private void opciones() {

        List<JMenuItem> componentes = new ArrayList();
        componentes.add(btnMenuConceptos);
        componentes.add(btnMenuProveedores);
        componentes.add(btnMenuExportar);
        componentes.add(btnMenuSalir);
        
        List<JComboBox> cbx = new ArrayList();
        cbx.add(cbxTypeSale);
        cbx.add(cbxTypeFilterSolds);
        cbx.add(cbxTypesSolds);
        cbx.add(cbxTypeSpends);
        cbx.add(cbxTypeSpendsFilter);
        cbx.add(cbxProvider);
        cbx.add(cbxProviderFilter);
        
        OpcionesController controller = new OpcionesController(componentes, cbx);
        
        btnMenuConceptos.addActionListener(controller);
        btnMenuProveedores.addActionListener(controller);
        btnMenuExportar.addActionListener(controller);
        btnMenuSalir.addActionListener(controller);

    }

    private void venta() {
        List<Object> componentes = new ArrayList();

        componentes.add(txtPrice);
        componentes.add(cbxTypeSale);
        componentes.add(btnSell);
        componentes.add(tblObjectList);

        VentasController controller = new VentasController(componentes);
        
        cbxTypeSale.addActionListener(controller);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem2 = new javax.swing.JMenuItem();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        cbxTypeSale = new javax.swing.JComboBox<>();
        txtPrice = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblObjectList = new javax.swing.JTable();
        btnSell = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cbxMonthFilter = new javax.swing.JComboBox<>();
        cbxYearFilter = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cbxProvider = new javax.swing.JComboBox<>();
        txtQuantitySpends = new javax.swing.JTextField();
        cbxTypeSpends = new javax.swing.JComboBox<>();
        cbxTypeSpendsFilter = new javax.swing.JComboBox<>();
        cbxProviderFilter = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        cbxYear = new javax.swing.JComboBox<>();
        cbxMonth = new javax.swing.JComboBox<>();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblHistorialVentas = new javax.swing.JTable();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblHistorialVenta = new javax.swing.JTable();
        txtQuantitySolds = new javax.swing.JTextField();
        cbxTypesSolds = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        cbxYearFilterSolds = new javax.swing.JComboBox<>();
        cbxMonthFilterSolds = new javax.swing.JComboBox<>();
        cbxDeletedSolds = new javax.swing.JComboBox<>();
        cbxTypeFilterSolds = new javax.swing.JComboBox<>();
        btnAcceptHistory = new javax.swing.JButton();
        btnDeleteItemHistory = new javax.swing.JButton();
        lblVentaDiaria = new javax.swing.JLabel();
        btnDeleteSaleHistory = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        btnMenuConceptos = new javax.swing.JMenuItem();
        btnMenuProveedores = new javax.swing.JMenuItem();
        btnMenuExportar = new javax.swing.JMenuItem();
        btnMenuSalir = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        cbxTypeSale.setModel(TipoDAO.comboModel("Conceptos"));
        cbxTypeSale.setPreferredSize(new java.awt.Dimension(72, 23));

        tblObjectList.getTableHeader().setReorderingAllowed(false);
        tblObjectList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        tblObjectList.setRowHeight(30);
        tblObjectList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblObjectList);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 998, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addContainerGap())
        );

        btnSell.setText("Vender");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxTypeSale, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnSell)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxTypeSale, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtPrice, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSell, javax.swing.GroupLayout.PREFERRED_SIZE, 22, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Ingresos", jPanel1);

        cbxMonthFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cbxYearFilter.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Filtros");

        cbxProvider.setModel(TipoDAO.comboModel("Proveedores")
        );

        cbxTypeSpends.setModel(TipoDAO.comboModel("Conceptos"));

        cbxTypeSpendsFilter.setModel(TipoDAO.comboModel("Conceptos")
        );

        cbxProviderFilter.setModel(TipoDAO.comboModel("Proveedores")
        );

        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jTable1.setRowHeight(30);
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane2.setViewportView(jTable1);

        cbxYear.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxYear.setVisible(false);

        cbxMonth.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cbxMonth.setVisible(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(txtQuantitySpends, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxProvider, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTypeSpends, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 265, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxYearFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxMonthFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxProviderFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTypeSpendsFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxYear, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxMonthFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxYearFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(cbxProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtQuantitySpends, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxTypeSpends, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxProviderFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxTypeSpendsFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Gastos", jPanel2);

        tblHistorialVentas.getTableHeader().setReorderingAllowed(false);
        tblHistorialVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHistorialVentas.setRowHeight(30);
        tblHistorialVentas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane3.setViewportView(tblHistorialVentas);

        tblHistorialVenta.getTableHeader().setReorderingAllowed(false);
        tblHistorialVenta.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHistorialVenta.setRowHeight(30);
        tblHistorialVenta.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane4.setViewportView(tblHistorialVenta);

        txtQuantitySolds.setEnabled(false);

        cbxTypesSolds.setModel(TipoDAO.comboModel("Conceptos"));
        cbxTypesSolds.setEnabled(false);

        jLabel2.setText("Filtros");

        cbxYearFilterSolds.setModel(VentaDAO.comboModelAño());

        cbxMonthFilterSolds.setModel(VentaDAO.comboModelMeses(cbxYearFilterSolds)
        );

        cbxDeletedSolds.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Registradas", "Eliminadas" }));

        cbxTypeFilterSolds.setModel(TipoDAO.comboModelTodo());

        btnAcceptHistory.setText("Aceptar");
        btnAcceptHistory.setEnabled(false);

        btnDeleteItemHistory.setText("Eliminar");
        btnDeleteItemHistory.setEnabled(false);

        lblVentaDiaria.setText("\"\"");

        btnDeleteSaleHistory.setText("Eliminar Venta");
        btnDeleteSaleHistory.setEnabled(false);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 260, Short.MAX_VALUE)
                    .addComponent(btnDeleteItemHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(txtQuantitySolds, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxTypesSolds, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnAcceptHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnDeleteSaleHistory, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxYearFilterSolds, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxMonthFilterSolds, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxDeletedSolds, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(cbxTypeFilterSolds, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblVentaDiaria, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 732, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cbxMonthFilterSolds, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxYearFilterSolds, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(cbxDeletedSolds, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTypeFilterSolds, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtQuantitySolds, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cbxTypesSolds, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblVentaDiaria))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 508, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteItemHistory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteSaleHistory)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAcceptHistory)))
                .addContainerGap())
        );

        jTabbedPane1.addTab("Historial de Ingreso", jPanel4);

        menu.setText("Opciones");

        btnMenuConceptos.setText("Conceptos");
        menu.add(btnMenuConceptos);

        btnMenuProveedores.setText("Proveedores");
        menu.add(btnMenuProveedores);

        btnMenuExportar.setText("Exportar");
        menu.add(btnMenuExportar);

        btnMenuSalir.setText("Salir");
        menu.add(btnMenuSalir);

        jMenuBar1.add(menu);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private class Historial implements Runnable {

        @Override
        public void run() {
            List<JTable> tablesHistorial = new ArrayList();
            tablesHistorial.add(tblHistorialVentas);
            tablesHistorial.add(tblHistorialVenta);

            List<JComboBox> combos = new ArrayList();
            combos.add(cbxYearFilterSolds);
            combos.add(cbxMonthFilterSolds);
            combos.add(cbxDeletedSolds);
            combos.add(cbxTypeFilterSolds);
            combos.add(cbxTypesSolds);

            List<JButton> buttonsHistorial = new ArrayList();
            buttonsHistorial.add(btnDeleteItemHistory);
            buttonsHistorial.add(btnDeleteSaleHistory);
            buttonsHistorial.add(btnAcceptHistory);

            HistorialController controller = new HistorialController(tablesHistorial, combos, lblVentaDiaria, txtQuantitySolds, buttonsHistorial);
            tblHistorialVentas.addMouseListener(controller);
            tblHistorialVentas.addKeyListener(controller);
            tblHistorialVenta.addMouseListener(controller);
            cbxYearFilterSolds.addActionListener(controller);
            cbxMonthFilterSolds.addActionListener(controller);
            cbxTypesSolds.addItemListener(controller);
            cbxDeletedSolds.addActionListener(controller);
            cbxTypeFilterSolds.addActionListener(controller);
            txtQuantitySolds.addKeyListener(controller);
            btnDeleteItemHistory.addActionListener(controller);
            btnAcceptHistory.addActionListener(controller);
            btnDeleteSaleHistory.addActionListener(controller);
        }

    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAcceptHistory;
    private javax.swing.JButton btnDeleteItemHistory;
    private javax.swing.JButton btnDeleteSaleHistory;
    private javax.swing.JMenuItem btnMenuConceptos;
    private javax.swing.JMenuItem btnMenuExportar;
    private javax.swing.JMenuItem btnMenuProveedores;
    private javax.swing.JMenuItem btnMenuSalir;
    private javax.swing.JButton btnSell;
    private javax.swing.JComboBox<String> cbxDeletedSolds;
    private javax.swing.JComboBox<String> cbxMonth;
    private javax.swing.JComboBox<String> cbxMonthFilter;
    private javax.swing.JComboBox<String> cbxMonthFilterSolds;
    private javax.swing.JComboBox<String> cbxProvider;
    private javax.swing.JComboBox<String> cbxProviderFilter;
    private javax.swing.JComboBox<String> cbxTypeFilterSolds;
    private javax.swing.JComboBox<String> cbxTypeSale;
    private javax.swing.JComboBox<String> cbxTypeSpends;
    private javax.swing.JComboBox<String> cbxTypeSpendsFilter;
    private javax.swing.JComboBox<String> cbxTypesSolds;
    private javax.swing.JComboBox<String> cbxYear;
    private javax.swing.JComboBox<String> cbxYearFilter;
    private javax.swing.JComboBox<String> cbxYearFilterSolds;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JLabel lblVentaDiaria;
    private javax.swing.JMenu menu;
    private javax.swing.JTable tblHistorialVenta;
    private javax.swing.JTable tblHistorialVentas;
    private javax.swing.JTable tblObjectList;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQuantitySolds;
    private javax.swing.JTextField txtQuantitySpends;
    // End of variables declaration//GEN-END:variables
}

/*
    private void historial() {
        List<JTable> tablesHistorial = new ArrayList();
        tablesHistorial.add(tblHistorialVentas);
        tablesHistorial.add(tblHistorialVenta);

        List<JComboBox> combos = new ArrayList();
        combos.add(cbxYearFilterSolds);
        combos.add(cbxMonthFilterSolds);
        combos.add(cbxDeletedSolds);
        combos.add(cbxTypeSpendsFilterSolds);
        combos.add(cbxTypesSolds);

        List<JButton> buttonsHistorial = new ArrayList();
        buttonsHistorial.add(btnDeleteItemHistory);
        buttonsHistorial.add(btnDeleteSaleHistory);
        buttonsHistorial.add(btnAcceptHistory);

        HistorialController controller = new HistorialController(tablesHistorial, combos, lblVentaDiaria, txtQuantitySolds, buttonsHistorial);
        tblHistorialVentas.addMouseListener(controller);
        tblHistorialVentas.addKeyListener(controller);
        tblHistorialVenta.addMouseListener(controller);
        cbxYearFilterSolds.addActionListener(controller);
        cbxMonthFilterSolds.addActionListener(controller);
        cbxTypesSolds.addItemListener(controller);
        cbxDeletedSolds.addActionListener(controller);
        cbxTypeSpendsFilterSolds.addActionListener(controller);
        txtQuantitySolds.addKeyListener(controller);
        btnDeleteItemHistory.addActionListener(controller);
        btnAcceptHistory.addActionListener(controller);
        btnDeleteSaleHistory.addActionListener(controller);
    }
*/
