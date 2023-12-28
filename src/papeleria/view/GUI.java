/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package papeleria.view;

import controller.TABApartadosController;
import controller.TABGastoController;
import controller.TABHistorialController;
import controller.OpcionesController;
import controller.TABVentasController;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTable;
import papeleria.model.ClienteDAO;
import papeleria.model.GastoDAO;
import papeleria.model.TableBaseDAO;
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
        Historial historialController = new Historial();
        Thread historial = new Thread(historialController);
        historial.start();
        venta(historialController);
        opciones();
        gasto();
        
        apartados();
    }
    
    private void apartados(){
        List<JTable> tablas = new ArrayList();
        tablas.add(tblClientes);
        tablas.add(tblClienteApartados);
        tablas.add(tblApartadoAportaciones);
        
        List<JButton> botones = new ArrayList();
        botones.add(btnApartar);
        botones.add(btnAbonar);
        
        TABApartadosController controller = new TABApartadosController(tablas, botones, txtSearchCostumer);
        
        
        tblClientes.addMouseListener(controller);
        tblClienteApartados.addMouseListener(controller);
        tblApartadoAportaciones.addMouseListener(controller);
        btnApartar.addActionListener(controller);
        btnAbonar.addActionListener(controller);
        txtSearchCostumer.addKeyListener(controller);
    }

    private void opciones() {

        List<JMenuItem> componentes = new ArrayList();
        componentes.add(btnMenuConceptos);
        componentes.add(btnMenuProveedores);
        componentes.add(btnMenuExportar);
        componentes.add(btnMenuClientes);
        
        List<JPanel> modificadores = new ArrayList();
        modificadores.add(btnMenuMinimizar);
        modificadores.add(btnMenuMaximizar);
        modificadores.add(btnMenuCerrar);
        modificadores.add(toGrab);

        List<JComboBox> cbx = new ArrayList();
        cbx.add(cbxTypeSale);
        cbx.add(cbxTypeFilterSolds);
        cbx.add(cbxTypesSolds);
        cbx.add(cbxTypeSpends);
        cbx.add(cbxTypeSpendsFilter);
        cbx.add(cbxProvider);
        cbx.add(cbxProviderFilter);

        OpcionesController controller = new OpcionesController(componentes, cbx, this, modificadores, this.tblClientes);

        this.addWindowListener(controller);
        toGrab.addMouseListener(controller);
        toGrab.addMouseMotionListener(controller);
        btnMenuConceptos.addActionListener(controller);
        btnMenuProveedores.addActionListener(controller);
        btnMenuClientes.addActionListener(controller);
        btnMenuExportar.addActionListener(controller);
        btnMenuCerrar.addMouseListener(controller);
        btnMenuMaximizar.addMouseListener(controller);
        btnMenuMinimizar.addMouseListener(controller);

    }

    private void gasto() {
        List<JComboBox> combos = new ArrayList();
        combos.add(cbxProvider);
        combos.add(cbxTypeSpends);
        combos.add(cbxYear);
        combos.add(cbxMonth);
        combos.add(cbxYearFilter);
        combos.add(cbxMonthFilter);
        combos.add(cbxProviderFilter);
        combos.add(cbxTypeSpendsFilter);

        List<Object> componentes = new ArrayList();
        componentes.add(txtQuantitySpends);
        componentes.add(btnAcceptSpend);
        componentes.add(btnDeleteSpend);
        componentes.add(tblSpends);
        componentes.add(lblSpends);

        TABGastoController controller = new TABGastoController(combos, componentes);

        cbxYearFilter.addActionListener(controller);
        cbxMonthFilter.addActionListener(controller);
        cbxTypeSpendsFilter.addActionListener(controller);
        cbxProviderFilter.addActionListener(controller);
        cbxYear.addActionListener(controller);

        tblSpends.addMouseListener(controller);
        txtQuantitySpends.addKeyListener(controller);
        btnAcceptSpend.addActionListener(controller);
        btnDeleteSpend.addActionListener(controller);

    }

    private void venta(Historial historial) {
        List<Object> componentes = new ArrayList();

        componentes.add(txtPrice);
        componentes.add(cbxTypeSale);
        componentes.add(btnSell);
        componentes.add(tblObjectList);
        componentes.add(lblTotal);

        TABVentasController controller = new TABVentasController(componentes, historial);

        cbxTypeSale.addActionListener(controller);
        btnSell.addActionListener(controller);
        txtPrice.addKeyListener(controller);
        tblObjectList.addKeyListener(controller);
        tblObjectList.addMouseListener(controller);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        cbxTypeSale = new javax.swing.JComboBox<>();
        txtPrice = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblObjectList = new javax.swing.JTable();
        lblTotal = new javax.swing.JLabel();
        btnSell = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        cbxYearFilter = new javax.swing.JComboBox<>();
        cbxMonthFilter = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        cbxProvider = new javax.swing.JComboBox<>();
        txtQuantitySpends = new javax.swing.JTextField();
        cbxTypeSpends = new javax.swing.JComboBox<>();
        cbxTypeSpendsFilter = new javax.swing.JComboBox<>();
        cbxProviderFilter = new javax.swing.JComboBox<>();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblSpends = new javax.swing.JTable();
        cbxYear = new javax.swing.JComboBox<>();
        cbxMonth = new javax.swing.JComboBox<>();
        btnAcceptSpend = new javax.swing.JButton();
        btnDeleteSpend = new javax.swing.JButton();
        lblSpends = new javax.swing.JLabel();
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
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane5 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel3 = new javax.swing.JLabel();
        txtSearchCostumer = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        tblClienteApartados = new javax.swing.JTable();
        btnApartar = new javax.swing.JButton();
        btnAbonar = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        tblApartadoAportaciones = new javax.swing.JTable();
        jMenuBarOpciones = new javax.swing.JMenuBar();
        menu = new javax.swing.JMenu();
        btnMenuConceptos = new javax.swing.JMenuItem();
        btnMenuProveedores = new javax.swing.JMenuItem();
        btnMenuClientes = new javax.swing.JMenuItem();
        btnMenuExportar = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(255, 255, 255));
        setUndecorated(true);
        setPreferredSize(new java.awt.Dimension(1115, 700));
        setSize(new java.awt.Dimension(1115, 1115));

        jTabbedPane1.setBackground(new java.awt.Color(255, 255, 255));
        jTabbedPane1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        try{
            cbxTypeSale.setBackground(new java.awt.Color(240, 240, 240));
            cbxTypeSale.setForeground(new java.awt.Color(0, 0, 0));
            cbxTypeSale.setModel(TableBaseDAO.comboModel("Conceptos"));
        }catch(NullPointerException ex){}
        cbxTypeSale.setBorder(null);
        cbxTypeSale.setPreferredSize(new java.awt.Dimension(72, 23));

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        tblObjectList.getTableHeader().setReorderingAllowed(false);
        tblObjectList.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Concepto", "Cantidad"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblObjectList.setRowHeight(30);
        tblObjectList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblObjectList.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tblObjectList);
        if (tblObjectList.getColumnModel().getColumnCount() > 0) {
            tblObjectList.getColumnModel().getColumn(0).setResizable(false);
            tblObjectList.getColumnModel().getColumn(1).setResizable(false);
        }

        lblTotal.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N
        lblTotal.setText("Total: ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1106, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(lblTotal)))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal)
                .addContainerGap())
        );

        btnSell.setBackground(new java.awt.Color(240, 240, 240));
        btnSell.setForeground(new java.awt.Color(0, 0, 0));
        btnSell.setText("Ingresar");
        btnSell.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

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

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        try{
            cbxYearFilter.setBackground(new java.awt.Color(240, 240, 240));
            cbxYearFilter.setForeground(new java.awt.Color(0, 0, 0));
            cbxYearFilter.setModel(GastoDAO.comboModelAño()
            );
        }catch(NullPointerException ex){}

        try{
            cbxMonthFilter.setBackground(new java.awt.Color(240, 240, 240));
            cbxMonthFilter.setForeground(new java.awt.Color(0, 0, 0));
            cbxMonthFilter.setModel(GastoDAO.comboModelMeses(cbxYearFilter));
        }catch(NullPointerException ex){

        }

        jLabel1.setText("Filtros");

        try{
            cbxProvider.setBackground(new java.awt.Color(240, 240, 240));
            cbxProvider.setForeground(new java.awt.Color(0, 0, 0));
            cbxProvider.setModel(TableBaseDAO.comboModel("Proveedores")
            );
        }catch(NullPointerException ex){}

        try{
            cbxTypeSpends.setBackground(new java.awt.Color(240, 240, 240));
            cbxTypeSpends.setForeground(new java.awt.Color(0, 0, 0));
            cbxTypeSpends.setModel(TableBaseDAO.comboModel("Conceptos"));
        }catch(NullPointerException ex){}

        try{
            cbxTypeSpendsFilter.setBackground(new java.awt.Color(240, 240, 240));
            cbxTypeSpendsFilter.setForeground(new java.awt.Color(0, 0, 0));
            cbxTypeSpendsFilter.setModel(TableBaseDAO.comboModelTodo("Conceptos")
            );
        }catch(NullPointerException ex){}

        try{
            cbxProviderFilter.setBackground(new java.awt.Color(240, 240, 240));
            cbxProviderFilter.setForeground(new java.awt.Color(0, 0, 0));
            cbxProviderFilter.setModel(TableBaseDAO.comboModelTodo("Proveedor")
            );
        }catch(NullPointerException ex){}

        tblSpends.getTableHeader().setReorderingAllowed(false);
        try{
            tblSpends.setModel(GastoDAO.tableModel(cbxYearFilter.getSelectedItem().toString(), cbxMonthFilter.getSelectedItem().toString(), cbxTypeSpendsFilter.getSelectedItem().toString(), cbxProviderFilter.getSelectedItem().toString())
            );
        } catch(NullPointerException ex){

        }
        tblSpends.setRowHeight(30);
        tblSpends.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSpends.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tblSpends);

        cbxYear.setBackground(new java.awt.Color(240, 240, 240));
        cbxYear.setForeground(new java.awt.Color(0, 0, 0));
        cbxYear.setModel(GastoDAO.comboModelAño());
        cbxYear.setVisible(false);

        cbxMonth.setBackground(new java.awt.Color(240, 240, 240));
        cbxMonth.setForeground(new java.awt.Color(0, 0, 0));
        cbxMonth.setModel(GastoDAO.comboModelActualMeses(cbxYear)
        );
        cbxMonth.setVisible(false);

        btnAcceptSpend.setBackground(new java.awt.Color(240, 240, 240));
        btnAcceptSpend.setForeground(new java.awt.Color(0, 0, 0));
        btnAcceptSpend.setText("Aceptar");

        btnDeleteSpend.setBackground(new java.awt.Color(240, 240, 240));
        btnDeleteSpend.setForeground(new java.awt.Color(0, 0, 0));
        btnDeleteSpend.setText("Eliminar");
        btnDeleteSpend.setEnabled(false);

        try{
            lblSpends.setText(GastoDAO.gastoDelMes(cbxYearFilter.getSelectedItem().toString(), cbxMonthFilter.getSelectedItem().toString(), cbxTypeSpendsFilter.getSelectedItem().toString(), cbxProviderFilter.getSelectedItem().toString())
            );
        }catch(NullPointerException ex){

        }

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
                        .addComponent(cbxYear, javax.swing.GroupLayout.PREFERRED_SIZE, 72, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxMonth, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAcceptSpend)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnDeleteSpend)
                        .addGap(18, 18, 18)
                        .addComponent(lblSpends)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 173, Short.MAX_VALUE)
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxYearFilter, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxMonthFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                        .addComponent(cbxYear, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnAcceptSpend)
                        .addComponent(btnDeleteSpend)
                        .addComponent(lblSpends))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cbxYearFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel1)
                        .addComponent(cbxProvider, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtQuantitySpends, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxTypeSpends, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxProviderFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxTypeSpendsFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(cbxMonthFilter, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
                .addContainerGap())
        );

        jTabbedPane1.addTab("Gastos", jPanel2);

        jPanel4.setBackground(new java.awt.Color(255, 255, 255));

        tblHistorialVentas.getTableHeader().setReorderingAllowed(false);
        tblHistorialVentas.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        tblHistorialVentas.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblHistorialVentas.setRowHeight(30);
        tblHistorialVentas.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblHistorialVentas.getTableHeader().setReorderingAllowed(false);
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
        tblHistorialVenta.getTableHeader().setReorderingAllowed(false);
        jScrollPane4.setViewportView(tblHistorialVenta);

        txtQuantitySolds.setEnabled(false);

        try{
            cbxTypesSolds.setBackground(new java.awt.Color(240, 240, 240));
            cbxTypesSolds.setForeground(new java.awt.Color(0, 0, 0));
            cbxTypesSolds.setModel(TableBaseDAO.comboModel("Conceptos"));
        }catch(NullPointerException ex){}
        cbxTypesSolds.setEnabled(false);

        jLabel2.setText("Filtros");

        try{
            cbxYearFilterSolds.setBackground(new java.awt.Color(240, 240, 240));
            cbxYearFilterSolds.setForeground(new java.awt.Color(0, 0, 0));
            cbxYearFilterSolds.setModel(VentaDAO.comboModelAño());
        }catch(NullPointerException ex){}

        try{
            cbxMonthFilterSolds.setBackground(new java.awt.Color(240, 240, 240));
            cbxMonthFilterSolds.setForeground(new java.awt.Color(0, 0, 0));
            cbxMonthFilterSolds.setModel(VentaDAO.comboModelMeses(cbxYearFilterSolds)
            );
        }catch(NullPointerException ex){

        }

        cbxDeletedSolds.setBackground(new java.awt.Color(240, 240, 240));
        cbxDeletedSolds.setForeground(new java.awt.Color(0, 0, 0));
        cbxDeletedSolds.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Registradas", "Eliminadas" }));

        try{
            cbxTypeFilterSolds.setBackground(new java.awt.Color(240, 240, 240));
            cbxTypeFilterSolds.setForeground(new java.awt.Color(0, 0, 0));
            cbxTypeFilterSolds.setModel(TableBaseDAO.comboModelTodo("Conceptos"));
        }catch(NullPointerException ex){}

        btnAcceptHistory.setBackground(new java.awt.Color(240, 240, 240));
        btnAcceptHistory.setForeground(new java.awt.Color(0, 0, 0));
        btnAcceptHistory.setText("Aceptar");
        btnAcceptHistory.setEnabled(false);

        btnDeleteItemHistory.setBackground(new java.awt.Color(240, 240, 240));
        btnDeleteItemHistory.setForeground(new java.awt.Color(0, 0, 0));
        btnDeleteItemHistory.setText("Eliminar");
        btnDeleteItemHistory.setEnabled(false);

        lblVentaDiaria.setText("\"\"");

        btnDeleteSaleHistory.setBackground(new java.awt.Color(240, 240, 240));
        btnDeleteSaleHistory.setForeground(new java.awt.Color(0, 0, 0));
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
                    .addComponent(jScrollPane3))
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
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE)
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

        jPanel5.setBackground(new java.awt.Color(255, 255, 255));

        jPanel6.setBackground(new java.awt.Color(255, 255, 255));
        jPanel6.setPreferredSize(new java.awt.Dimension(1112, 616));
        jPanel6.setLayout(null);

        tblSpends.getTableHeader().setReorderingAllowed(false);
        tblClientes.setModel(ClienteDAO.tableModel());
        tblClientes.setRowHeight(30);
        tblClientes.getColumnModel().getColumn(0).setMaxWidth(100);
        tblClientes.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblClientes.getTableHeader().setReorderingAllowed(false);
        jScrollPane5.setViewportView(tblClientes);

        jLabel3.setText("Buscar:");

        tblClienteApartados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblClienteApartados.setRowHeight(30);
        tblClienteApartados.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblClienteApartados.getTableHeader().setReorderingAllowed(false);
        jScrollPane6.setViewportView(tblClienteApartados);

        btnApartar.setBackground(new java.awt.Color(240, 240, 240));
        btnApartar.setForeground(new java.awt.Color(0, 0, 0));
        btnApartar.setText("Apartar");
        btnApartar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

        btnAbonar.setBackground(new java.awt.Color(240, 240, 240));
        btnAbonar.setForeground(new java.awt.Color(0, 0, 0));
        btnAbonar.setText("Abonar");
        btnAbonar.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAbonar.setEnabled(false);

        tblApartadoAportaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        tblApartadoAportaciones.setRowHeight(30);
        tblApartadoAportaciones.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblApartadoAportaciones.getTableHeader().setReorderingAllowed(false);
        jScrollPane7.setViewportView(tblApartadoAportaciones);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel3)
                        .addGap(6, 6, 6)
                        .addComponent(txtSearchCostumer, javax.swing.GroupLayout.PREFERRED_SIZE, 270, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAbonar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnApartar, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 320, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 774, Short.MAX_VALUE)
                            .addComponent(jScrollPane7))))
                .addContainerGap())
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel3))
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtSearchCostumer, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnApartar)
                        .addComponent(btnAbonar)))
                .addGap(6, 6, 6)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 365, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 577, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Apartados", jPanel5);

        jMenuBarOpciones.setBackground(new java.awt.Color(153, 153, 153));
        jMenuBarOpciones.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(102, 102, 102)));
        jMenuBarOpciones.setForeground(new java.awt.Color(204, 204, 204));
        jMenuBarOpciones.setAlignmentY(0.5F);
        jMenuBarOpciones.setBorderPainted(false);
        jMenuBarOpciones.setPreferredSize(new java.awt.Dimension(1115, 35));

        menu.setBackground(new java.awt.Color(240, 240, 240));
        menu.setForeground(new java.awt.Color(255, 255, 255));
        menu.setText("Configuracion");
        menu.setMargin(new java.awt.Insets(0, 0, 0, 0));
        menu.setMaximumSize(new java.awt.Dimension(120, 32767));
        menu.setMinimumSize(new java.awt.Dimension(120, 35));
        menu.setPreferredSize(new java.awt.Dimension(120, 22));

        btnMenuConceptos.setText("Conceptos");
        btnMenuConceptos.setMaximumSize(new java.awt.Dimension(120, 32767));
        btnMenuConceptos.setMinimumSize(new java.awt.Dimension(120, 0));
        btnMenuConceptos.setPreferredSize(new java.awt.Dimension(120, 35));
        menu.add(btnMenuConceptos);

        btnMenuProveedores.setText("Proveedores");
        btnMenuProveedores.setPreferredSize(new java.awt.Dimension(120, 35));
        menu.add(btnMenuProveedores);

        btnMenuClientes.setText("Clientes");
        btnMenuClientes.setPreferredSize(new java.awt.Dimension(120, 35));
        menu.add(btnMenuClientes);

        btnMenuExportar.setText("Exportar");
        btnMenuExportar.setPreferredSize(new java.awt.Dimension(120, 35));
        menu.add(btnMenuExportar);

        jMenuBarOpciones.add(menu);
        toGrab = new javax.swing.JPanel();
        btnMenuMinimizar = new javax.swing.JPanel();
        btnMenuMaximizar = new javax.swing.JPanel();
        btnMenuCerrar = new javax.swing.JPanel();
        //1115, 640
        toGrab.setBackground(new java.awt.Color(153,153,153));
        toGrab.setPreferredSize(new java.awt.Dimension(950, 35));
        toGrab.setMaximumSize(new java.awt.Dimension(32767, 35));
        jMenuBarOpciones.add(toGrab);

        btnMenuMinimizar.add(new javax.swing.JLabel(new javax.swing.ImageIcon(getClass().getResource("/images/minimize-icon.png")))); // NOI18N
        btnMenuMinimizar.setBackground(new java.awt.Color(153,153,153));
        btnMenuMinimizar.setPreferredSize(new java.awt.Dimension(45, 35));
        btnMenuMinimizar.setMaximumSize(new java.awt.Dimension(45, 35));
        jMenuBarOpciones.add(btnMenuMinimizar);

        btnMenuMaximizar.add(new javax.swing.JLabel(new javax.swing.ImageIcon(getClass().getResource("/images/maximize-icon.png")))); // NOI18N
        btnMenuMaximizar.setBackground(new java.awt.Color(153,153,153));
        btnMenuMaximizar.setPreferredSize(new java.awt.Dimension(45, 35));
        btnMenuMaximizar.setMaximumSize(new java.awt.Dimension(45, 35));
        //toGrab.add(btnMenuMaximizar);
        jMenuBarOpciones.add(btnMenuMaximizar);
        jMenuBarOpciones.setMaximumSize(new java.awt.Dimension(0, 0));

        btnMenuCerrar.add(new javax.swing.JLabel(new javax.swing.ImageIcon(getClass().getResource("/images/close-icon.png")))); // NOI18N
        btnMenuCerrar.setBackground(new java.awt.Color(153,153,153));
        btnMenuCerrar.setPreferredSize(new java.awt.Dimension(45, 35));
        btnMenuCerrar.setMaximumSize(new java.awt.Dimension(45, 35));
        jMenuBarOpciones.add(btnMenuCerrar);

        //jMenuBarOpciones.add(btnMenuMinimizar);

        setJMenuBar(jMenuBarOpciones);

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

    public class Historial implements Runnable {

        TABHistorialController historial;

        public void actualizarVista() {
            historial.actualizarCombos();
            historial.actualizarVentaDiaria();
            historial.resetTableHistorial();

        }

        public TABHistorialController getHistorial() {
            return historial;
        }

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

            TABHistorialController controller = new TABHistorialController(tablesHistorial, combos, lblVentaDiaria, txtQuantitySolds, buttonsHistorial);
            historial = controller;
            historial.actualizarCombos();

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

    private javax.swing.JPanel toGrab;
    private javax.swing.JPanel btnMenuMinimizar;
    private javax.swing.JPanel btnMenuMaximizar;
    private javax.swing.JPanel btnMenuCerrar;
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAbonar;
    private javax.swing.JButton btnAcceptHistory;
    private javax.swing.JButton btnAcceptSpend;
    private javax.swing.JButton btnApartar;
    private javax.swing.JButton btnDeleteItemHistory;
    private javax.swing.JButton btnDeleteSaleHistory;
    private javax.swing.JButton btnDeleteSpend;
    private javax.swing.JMenuItem btnMenuClientes;
    private javax.swing.JMenuItem btnMenuConceptos;
    private javax.swing.JMenuItem btnMenuExportar;
    private javax.swing.JMenuItem btnMenuProveedores;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JMenuBar jMenuBarOpciones;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JLabel lblSpends;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblVentaDiaria;
    private javax.swing.JMenu menu;
    private javax.swing.JTable tblApartadoAportaciones;
    private javax.swing.JTable tblClienteApartados;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTable tblHistorialVenta;
    private javax.swing.JTable tblHistorialVentas;
    private javax.swing.JTable tblObjectList;
    private javax.swing.JTable tblSpends;
    private javax.swing.JTextField txtPrice;
    private javax.swing.JTextField txtQuantitySolds;
    private javax.swing.JTextField txtQuantitySpends;
    private javax.swing.JTextField txtSearchCostumer;
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
