/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import static papeleria.model.Conexion.close;

/**
 *
 * @author heber
 */
public class GastoDAO {

    public static TableModel tableModel(String año, String mes, String tipo, String tienda) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        TableModel tableModel = new TableModel();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query = "";

            if (tipo.equals("Todo") && tienda.equals("Todo")) {
                query = "select id_gasto as 'Gasto #', nombre_tipo as 'Concepto', nombre_proveedor as 'Tienda', cantidad_gasto as 'Cantidad $', fecha_gasto as 'Fecha' from gastos g inner join proveedor p on g.id_proveedor = p.id_proveedor inner join tipo t on g.id_tipo = t.id_tipo where fecha_gasto like '" + año + "-" + mes + "%' order by Fecha desc;";
            } else if (tipo.equals("Todo") && !tienda.equals("Todo")) {
                query = "select id_gasto as 'Gasto #', nombre_tipo as 'Concepto', nombre_proveedor as 'Tienda', cantidad_gasto as 'Cantidad $', fecha_gasto as 'Fecha' from gastos g inner join proveedor p on g.id_proveedor = p.id_proveedor inner join tipo t on g.id_tipo = t.id_tipo where fecha_gasto like '" + año + "-" + mes + "%' && nombre_proveedor = '" + tienda + "' order by Fecha desc;";
            } else if (!tipo.equals("Todo") && tienda.equals("Todo")) {
                query = "select id_gasto as 'Gasto #', nombre_tipo as 'Concepto', nombre_proveedor as 'Tienda', cantidad_gasto as 'Cantidad $', fecha_gasto as 'Fecha' from gastos g inner join proveedor p on g.id_proveedor = p.id_proveedor inner join tipo t on g.id_tipo = t.id_tipo where fecha_gasto like '" + año + "-" + mes + "%' && nombre_tipo = '" + tipo + "' order by Fecha desc;";
            } else {
                query = "select id_gasto as 'Gasto #', nombre_tipo as 'Concepto', nombre_proveedor as 'Tienda', cantidad_gasto as 'Cantidad $', fecha_gasto as 'Fecha' from gastos g inner join proveedor p on g.id_proveedor = p.id_proveedor inner join tipo t on g.id_tipo = t.id_tipo where fecha_gasto like '" + año + "-" + mes + "%' && nombre_proveedor = '" + tienda + "' && nombre_tipo = '" + tipo + "' order by Fecha desc;";
            }

            rs = stmt.executeQuery(query);
            metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();
            for (int i = 1; i <= columns; i++) {
                tableModel.addColumn(metaData.getColumnLabel(i));
            }
            while (rs.next()) {
                Object[] fila = new Object[columns];
                for (int i = 0; i < columns; i++) {
                    fila[i] = rs.getObject(i + 1);
                }
                tableModel.addRow(fila);
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            return tableModel;
        }
    }

    public static DefaultComboBoxModel comboModelAño() {
        DefaultComboBoxModel modelFechas = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Timestamp firstSale = null;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        int firstYear, lastYear;

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select min(fecha_gasto) from gastos");

            while (rs.next()) {
                firstSale = (Timestamp) rs.getObject(1);
            }

            firstYear = firstSale.getYear() + 1900;
            lastYear = now.getYear() + 1900;

            String[] años = new String[lastYear - firstYear + 1];

            for (int i = 0; i < años.length; i++) {
                años[i] = String.valueOf(firstYear + i);
            }

            modelFechas = new javax.swing.DefaultComboBoxModel<>(años);
            modelFechas.setSelectedItem(lastYear);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            return modelFechas;
        }

    }

    public static DefaultComboBoxModel comboModelMeses(JComboBox año) {
        DefaultComboBoxModel modelFechas = new DefaultComboBoxModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<Object> meses = new ArrayList();

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select distinct substring(fecha_gasto, 1 , 7) as Fecha from gastos where fecha_gasto like '" + año.getSelectedItem().toString() + "-%' order by Fecha desc");

            int q = 0;

            while (rs.next()) {
                q += 1;
                meses.add((rs.getObject(1).toString()).substring(5, 7));
            }

            String[] cantidadMeses = new String[meses.size()];

            for (int i = 0; i < cantidadMeses.length; i++) {
                cantidadMeses[i] = String.valueOf(meses.get(i));
            }

            modelFechas = new javax.swing.DefaultComboBoxModel<>(cantidadMeses);
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            return modelFechas;
        }

    }

    public static DefaultComboBoxModel comboModelActualMeses(JComboBox año) {
        DefaultComboBoxModel modelFechas = new DefaultComboBoxModel();

        try {
            List<Object> meses = new ArrayList();
            Timestamp now = new Timestamp(System.currentTimeMillis());
            int añoSeleccionado = Integer.parseInt(año.getSelectedItem().toString());
            int añoActual = now.getYear() + 1900;

            if (añoSeleccionado == añoActual) {
                for (int i = 0; i < now.getMonth() + 1; i++) {
                    if (i < 9) {
                        meses.add("0" + (i + 1));
                    } else {
                        meses.add(i + 1);
                    }
                }
            } else {
                for (int i = 0; i < 12; i++) {
                    if (i < 9) {
                        meses.add("0" + (i + 1));
                    } else {
                        meses.add(i + 1);
                    }
                }
            }

            String[] cantidadMeses = new String[meses.size()];

            for (int i = 0; i < cantidadMeses.length; i++) {
                cantidadMeses[i] = String.valueOf(meses.get(i));
            }

            modelFechas = new javax.swing.DefaultComboBoxModel<>(cantidadMeses);

        } catch (NullPointerException ex) {

        }

        return modelFechas;

    }

    public static int idSiguienteGastoDelMes() {
        String query = "";
        int result = 1;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            Timestamp now = new Timestamp(System.currentTimeMillis());

            query = "select max(id_gasto) from gastos where year(fecha_gasto) = " + (now.getYear() + 1900) + " && month(fecha_gasto) = " + (now.getMonth() + 1) + ";";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                result = (Integer) rs.getObject(1) + 1;
            }

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            return result;
        }

    }

    public static void update(Gasto gasto, Gasto gastoOriginal) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            //System.out.println(ventaTipo.getVenta().getIdVenta());
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("UPDATE `papeleria`.`gastos` SET `fecha_gasto` = ? , `cantidad_gasto` = ? , `id_tipo` = ? , `id_proveedor` = ? WHERE (`id_gasto` = ? ) and (`fecha_gasto` = ? );");
            stmt.setTimestamp(1, gasto.getFecha());
            stmt.setDouble(2, gasto.getCantidad());
            stmt.setInt(3, gasto.getIdTipo());
            stmt.setInt(4, gasto.getIdProveedor());
            stmt.setInt(5, gastoOriginal.getIdGasto());
            stmt.setTimestamp(6, gastoOriginal.getFecha());

            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {

                ex.printStackTrace(System.out);
            }
        }
    }

    public static String gastoDelMes(String año, String mes, String tipo, String proveedor) {
        String query = "";
        String result = "";
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        double totalDia = 0;
        double totalMes = 0;
        //System.out.println("año: " + año + " mes: " + mes);

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            Timestamp now = new Timestamp(System.currentTimeMillis());

            if (tipo.equals("Todo") && proveedor.equals("Todo")) {
                query = "select sum(cantidad_gasto) from gastos g where g.fecha_gasto like '" + año + "-" + mes + "-%';";
                //System.out.println("TODO: " + query);
            }else if(tipo.equals("Todo") && !proveedor.equals("Todo")){
                query = "select sum(cantidad_gasto) from gastos g where g.fecha_gasto like '" + año + "-" + mes + "-%' && id_proveedor = " + TableBaseDAO.getID("proveedor", proveedor) + " ;";
            
            }else if(!tipo.equals("Todo") && proveedor.equals("Todo")){
                query = "select sum(cantidad_gasto) from gastos g where g.fecha_gasto like '" + año + "-" + mes + "-%' && id_tipo = " + TableBaseDAO.getID("tipo", tipo) + ";";
            
            }else {
                query = "select sum(cantidad_gasto) from gastos g where g.fecha_gasto like '" + año + "-" + mes + "-%' && id_tipo = " + TableBaseDAO.getID("tipo", tipo) + " && id_proveedor = " + TableBaseDAO.getID("proveedor", proveedor) + " ;";
            }
            
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                totalMes =  Double.valueOf(rs.getObject(1).toString());
            }
            result += "Mes: " + totalMes;

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            return result;
        }
    }

    public static void insert(Gasto gasto) {
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("INSERT INTO `papeleria`.`gastos` (`id_gasto`, `cantidad_gasto`, `id_tipo`, `id_proveedor`, `fecha_gasto`) VALUES (?, ?, ?, ?, ?);");
            stmt.setInt(1, gasto.getIdGasto());
            stmt.setDouble(2, gasto.getCantidad());
            stmt.setInt(3, gasto.getIdTipo());
            stmt.setInt(4, gasto.getIdProveedor());
            stmt.setTimestamp(5, gasto.getFecha());
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {

                ex.printStackTrace(System.out);
            }
        }
    }
    
    public static void delete(Gasto gasto){
        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("DELETE FROM `papeleria`.`gastos` WHERE id_gasto = ? && fecha_gasto = ?;");
            //System.out.println("ID: " +  gasto.getIdGasto() + " FECHA: " + gasto.getFecha());
            stmt.setInt(1, gasto.getIdGasto());
            stmt.setTimestamp(2, gasto.getFecha());
            stmt.executeUpdate();
            conn.commit();
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {

            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {

                ex.printStackTrace(System.out);
            }
        }
    }
    
    public static Timestamp getDate() {
        Timestamp date = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select current_timestamp()");
            rs.next();
            date = rs.getTimestamp(1);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            return date;
        }
    }

}
