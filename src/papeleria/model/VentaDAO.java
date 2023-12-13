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
import static papeleria.model.Conexion.*;

/**
 *
 * @author heber
 */
public class VentaDAO {

    private static VentaDAO ventaDAO;

    public static TableModel tableModel(String año, String mes, String registrada, String tipo) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        TableModel tableModel = new TableModel();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query = "";

            if (tipo.equals("Todo")) {
                query = "SELECT distinct p.id_venta as 'Numero de Ingreso Mensual', p.fecha_venta as Fecha, (select sum(cantidad_tipo) from venta_tipo k where k.id_venta = p.id_venta && k.fecha_venta = p.fecha_venta && k.existe = true) as Total FROM venta p, venta_tipo v where p.id_venta = v.id_venta && p.fecha_venta = v.fecha_venta && p.existe_venta = " + registrada + " && p.fecha_venta like '" + año + "-" + mes + "%' order by Fecha desc";
            } else {
                query = "SELECT distinct p.id_venta as 'Numero de Ingreso Mensual', p.fecha_venta as Fecha, v.cantidad_tipo as " + tipo + " FROM venta p, venta_tipo v where v.existe = true && p.id_venta = v.id_venta && p.fecha_venta = v.fecha_venta && p.existe_venta = " + registrada + " && p.fecha_venta like '" + año + "-" + mes + "%' && v.id_tipo like (select id_tipo from tipo where nombre_tipo like '" + tipo + "') order by Fecha desc";
            }

            //System.out.println("QUERY TABLE: " + query);
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
            rs = stmt.executeQuery("select min(fecha_venta) from venta");

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
        //Timestamp firstSale = null;
        //Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Object> meses = new ArrayList();

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select distinct substring(fecha_venta, 1 , 7) from venta where fecha_venta like '" + año.getSelectedItem().toString() + "-%'");
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
            //modelFechas.setSelectedItem(lastYear);

        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            } catch (NullPointerException ex){
                
            }
            return modelFechas;
        }

    }

    public static Venta getVenta(Timestamp fecha) {
        Venta venta = null;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select id_venta, fecha_venta, existe_venta from venta where fecha_venta = '" + fecha + "'");

            while (rs.next()) {
                venta = new Venta.VentaBuilder().idVenta((int) rs.getObject(1)).fecha((Timestamp) rs.getObject(2)).existe((boolean) rs.getObject(3)).build();
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
            return venta;
        }
    }

    public static void changeExists(Venta venta, boolean exists) {
        //UPDATE `papeleria`.`venta` SET `existe_venta` = '0' WHERE (`id_venta` = '6') and (`fecha_venta` = '2023-04-12 13:30:58');

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("UPDATE `papeleria`.`venta` SET `existe_venta` = ? WHERE (`id_venta` = ?) and (`fecha_venta` = ?);");
            stmt.setBoolean(1, exists);
            stmt.setInt(2, venta.getIdVenta());
            stmt.setTimestamp(3, venta.getFecha());
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

    public static VentaDAO getInstance() {
        if (ventaDAO == null) {
            ventaDAO = new VentaDAO();
        }
        return ventaDAO;
    }

    public static String ventaDiaria(String año, String mes, String tipo) {
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

            if (tipo.equals("Todo")) {
                query = "select sum(cantidad_tipo) from venta_tipo vt, venta v where vt.fecha_venta like '" + año + "-" + mes + "-%' && vt.existe = true && v.id_venta = vt.id_venta && vt.fecha_venta = v.fecha_venta && v.existe_venta = true";
                //System.out.println("TODO: " + query);
            } else {
                query = "select sum(cantidad_tipo) from venta_tipo vt, venta v where id_tipo = (select id_tipo from tipo where nombre_tipo = '" + tipo + "') && vt.fecha_venta like '" + año + "-" + mes + "-%' && vt.existe = true && v.id_venta = vt.id_venta && vt.fecha_venta = v.fecha_venta && v.existe_venta = true;";
                //System.out.println("TIPO: " + query);
            }

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                // firstSale = (Timestamp) rs.getObject(1);
                totalMes = (double) rs.getObject(1);
                //System.out.println("TOTAL MES: " + totalMes);
            }
            result += "Mes: " + totalMes;

            if (Integer.parseInt(año) == now.getYear() + 1900 && Integer.parseInt(mes) == now.getMonth() + 1) {

                if (tipo.equals("Todo")) {
                    query = "select sum(cantidad_tipo) from venta_tipo vt inner join venta v on v.id_venta = vt.id_venta where v.fecha_venta = vt.fecha_venta && existe_venta = true && vt.fecha_venta like '" + año + "-" + mes + "-%" + now.getDate() + " %';";
                    //query = "select sum(cantidad_tipo) from venta_tipo vt, venta v where vt.fecha_venta like '" + año + "-" + mes + "-" + now.getDate() + "%' && existe = true && v.id_venta = vt.id_venta && v.existe_venta = true;";
                    //System.out.println("TODO: " + query);
                } else {
                    //query = "select sum(cantidad_tipo) from venta_tipo where id_tipo = (select id_tipo from tipo where nombre_tipo = '" + tipo + "') && fecha_venta like '" + año + "-" + mes + "-" + now.getDate() + "%' && existe = true";
                    //query = "select sum(cantidad_tipo) from venta_tipo vt, venta v where id_tipo = (select id_tipo from tipo where nombre_tipo = '" + tipo + "') && vt.fecha_venta like '" + año + "-" + mes + "-" + now.getDate() + "%' && vt.existe = true && v.id_venta = vt.id_venta && v.existe_venta = true;";
                    //System.out.println("TODO: " + query);
                    query = "select sum(cantidad_tipo) from venta_tipo vt inner join venta v on v.id_venta = vt.id_venta where v.fecha_venta = vt.fecha_venta && existe_venta = true && vt.fecha_venta like '" + año + "-" + mes + "-%" + now.getDate() + " %' && id_tipo = '" + TableBaseDAO.getNombre(tipo, "tipo").getIdBase() + "';";
                }

                rs = stmt.executeQuery(query);

                while (rs.next()) {
                    //System.out.println(query);
                    totalDia = (double) rs.getObject(1);

                }
                result += " / " + "Dia: $" + totalDia;
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

    public static int idSiguienteVentaDelMes() {
        String query = "";
        int result = 1;
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            Timestamp now = new Timestamp(System.currentTimeMillis());

            query = "select max(id_venta) from venta where year(fecha_venta) = " + (now.getYear() + 1900) + " && month(fecha_venta) = " + (now.getMonth() + 1) + ";";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                // firstSale = (Timestamp) rs.getObject(1);
                result = (Integer) rs.getObject(1) + 1;
                //System.out.println("TOTAL MES: " + totalMes);
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

    public static void insert(Venta venta) {
        //INSERT INTO `papeleria`.`venta` (`id_venta`, `fecha_venta`, `existe_venta`) VALUES ('1', '2023-06-23 17:55:16', '1');

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("INSERT INTO `papeleria`.`venta` (`id_venta`, `fecha_venta`, `existe_venta`) VALUES ( ? , ?, ?)");
            stmt.setBoolean(3, true);
            stmt.setInt(1, venta.getIdVenta());
            stmt.setTimestamp(2, venta.getFecha());
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
