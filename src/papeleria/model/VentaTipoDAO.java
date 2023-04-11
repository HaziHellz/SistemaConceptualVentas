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
import static papeleria.model.Conexion.close;

/**
 *
 * @author heber
 */
public class VentaTipoDAO {
    
    private static VentaTipoDAO ventaTipoDAO;
    
    public static TableModel tableModel(int id_venta, String fecha_venta) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        TableModel tableModel = new TableModel();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT (select nombre_tipo from tipo t where t.id_tipo = v.id_tipo) as Concepto, cantidad_tipo as Cantidad FROM venta_tipo v where v.id_venta = " + id_venta + " && v.fecha_venta = '" + fecha_venta + "'");
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
    
    public static VentaTipoDAO getInstance() {
        if (ventaTipoDAO == null) {
            ventaTipoDAO = new VentaTipoDAO();
        }
        return ventaTipoDAO;
    }
}
