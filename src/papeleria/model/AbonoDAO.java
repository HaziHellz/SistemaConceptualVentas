/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import static papeleria.model.Conexion.close;

/**
 *
 * @author heber
 */
public class AbonoDAO {

    public static TableModel tableModel(Cliente cliente, Apartado apartado) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        TableModel tableModel = new TableModel();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query = "";
            
            query = "call sps_aportaciones_apartado(" + cliente.getTelefono() + ", '" + apartado.getFechaApartado().toString() + "')";

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

    public static List<VentaTipo> getAbonos(Cliente cliente, Apartado apartado) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        List<VentaTipo> abonos = new ArrayList<>();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query = "";

            query = "call sps_aportaciones_apartado(" + cliente.getTelefono() + ", '" + apartado.getFechaApartado().toString() + "')";

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                abonos.add(
                        new VentaTipo.VentaBuilder()
                                .venta(new Venta.VentaBuilder()
                                        .fecha((Timestamp) rs.getObject("Fecha"))
                                        .build()
                                )
                                .tipo(TableBaseDAO.getNombreByID(String.valueOf(apartado.getIdTipo()), "tipo"))
                                .cantidadTipo((double) rs.getObject("Cantidad Abonada"))
                                .build()
                );
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
            return abonos;
        }

    }

}
