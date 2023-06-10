/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

import java.sql.Connection;
import java.sql.ResultSet;
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
        Timestamp firstSale = null;
        Timestamp now = new Timestamp(System.currentTimeMillis());
        List<Object> meses = new ArrayList();

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select distinct substring(fecha_gasto, 1 , 7) from gastos where fecha_gasto like '" + año.getSelectedItem().toString() + "-%' order by fecha_gasto desc");
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
            }
            return modelFechas;
        }

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

            query = "select max(id_gasto) from gastos where year(fecha_gasto) = " + (now.getYear() + 1900) + " && month(fecha_gastp) = " + (now.getMonth() + 1) + ";";

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
    
    
    
    //  INSERT INTO `papeleria`.`gastos` (`id_gastos`, `cantidad_gasto`, `id_tipo`, `id_proveedor`) VALUES ('3', '3500', '3', '3');
    
}
