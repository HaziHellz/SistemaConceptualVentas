    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

import java.sql.Connection;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import static papeleria.model.Conexion.close;

/**
 *
 * @author heber
 */
public class ApartadoDAO {

    public static TableModel tableModel(Cliente cliente) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        TableModel tableModel = new TableModel();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query = "";

            query = "call sps_apartados_cliente(" + cliente.getTelefono() + ")";

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

    public static Apartado getDatos(Cliente cliente, Date fecha_apartado) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Apartado apartado = new Apartado();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query = "";

            
            query = "call spg_apartado(" + cliente.getTelefono() + ", '" + (fecha_apartado.getYear()+1900) + "-"+ (fecha_apartado.getMonth()+1) + "-"+ (fecha_apartado.getDate()) +"')";
            

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                System.out.println(rs.getObject("fecha_apartado").toString());
                System.out.println("AA");
                apartado = new Apartado.ApartadoBuilder()
                        .setCliente(cliente)
                        .setFechaApartado((Date) rs.getObject(2))
                        .setDescripcion((String) rs.getObject(3))
                        .setIdTipo((int) rs.getObject(4))
                        .setTotalPagar((double) rs.getObject(5))
                        .setCancelado(Boolean.parseBoolean(rs.getObject(6).toString()))
                        .setFechaMaxima((Date) rs.getObject(7))
                        .build();
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
            return apartado;
        }
    }

}
