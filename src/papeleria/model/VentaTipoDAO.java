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
            rs = stmt.executeQuery("SELECT (select nombre_tipo from tipo t where t.id_tipo = v.id_tipo) as Concepto, cantidad_tipo as Cantidad FROM venta_tipo v where v.id_venta = " + id_venta + " && v.fecha_venta = '" + fecha_venta + "' && v.existe = true");
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
    
    public static void insert(VentaTipo ventaTipo){
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("INSERT INTO `papeleria`.`venta_tipo` (`id_venta`, `id_tipo`, `cantidad_tipo`, `fecha_venta`) VALUES ( ?, ?, ?, ?);");
            stmt.setInt(1, ventaTipo.getVenta().getIdVenta());
            stmt.setInt(2, ventaTipo.getIdTipo());
            stmt.setDouble(3, ventaTipo.getCantidadTipo());
            stmt.setTimestamp(4, ventaTipo.getVenta().getFecha());
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
    
    public static void delete(VentaTipo ventaTipo){
        //DELETE FROM `papeleria`.`venta_tipo` WHERE (`id_venta` = '1') and (`fecha_venta` = '2023-04-10 13:37:19') and (`id_tipo` = '3');
        
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("DELETE FROM `papeleria`.`venta_tipo` WHERE (`id_venta` = ?) and (`fecha_venta` = ?) and (`id_tipo` = ?)");
            stmt.setInt(1, ventaTipo.getVenta().getIdVenta());
            stmt.setTimestamp(2, ventaTipo.getVenta().getFecha());
            stmt.setInt(3, ventaTipo.getIdTipo());
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
    
    public static void update(VentaTipo ventaTipo){
        Connection conn = null;
        PreparedStatement stmt = null;
        
        try {
            //System.out.println(ventaTipo.getVenta().getIdVenta());
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("UPDATE `papeleria`.`venta_tipo` SET `cantidad_tipo` = ? WHERE (`id_venta` = ?) and (`fecha_venta` = ?) and (`id_tipo` = ?);");
            stmt.setDouble(1, ventaTipo.getCantidadTipo());
            stmt.setInt(2, ventaTipo.getVenta().getIdVenta());
            stmt.setTimestamp(3, ventaTipo.getVenta().getFecha());
            stmt.setInt(4, ventaTipo.getIdTipo());
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
    
    public static List<VentaTipo> getVenta(Venta venta){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        List<VentaTipo> ventaTipos = new ArrayList();
        //System.out.println(venta.toString());
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT (select nombre_tipo from tipo t where t.id_tipo = v.id_tipo) as Concepto, cantidad_tipo as Cantidad, id_tipo, id_venta, fecha_venta FROM venta_tipo v where v.id_venta = " + venta.getIdVenta() + " && v.fecha_venta = '" + venta.getFecha() + "' && v.existe = true");
            metaData = rs.getMetaData();
            int columns = metaData.getColumnCount();
            
            while (rs.next()) {
                Object[] item = new Object[columns];
                for (int i = 0; i < columns; i++) {
                    item[i] = rs.getObject(i + 1);
                }
                ventaTipos.add(new VentaTipo.VentaBuilder().cantidadTipo((double) item[1]).nombreTipo((String) item[0]).idTipo((int) item[2]).venta(venta).build());
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
            return ventaTipos;
        }
    }
    
    public static VentaTipoDAO getInstance() {
        if (ventaTipoDAO == null) {
            ventaTipoDAO = new VentaTipoDAO();
        }
        return ventaTipoDAO;
    }
}
