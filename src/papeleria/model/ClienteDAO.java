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
import static papeleria.model.Conexion.close;

/**
 *
 * @author heber
 */
public class ClienteDAO {
    
    public static TableModel tableModel() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        TableModel tableModel = new TableModel();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query = "";

            
            query = "call sps_clientes()";
            

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
    
        public static TableModel buscarClienteTableModel(String busqueda) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        TableModel tableModel = new TableModel();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query = "";

            
            query = "call spse_cliente ('" + busqueda + "')";
            

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
    
    
    public static Cliente getDatos(String telefono){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        Cliente cliente = new Cliente();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query = "";

            
            query = "call sps_cliente(" + telefono + ")";
            

            rs = stmt.executeQuery(query);
            if (rs.next()) {
                cliente = new Cliente.ClienteBuilder()
                    .setNombre((String) rs.getObject(1))
                    .setApellido((String) rs.getObject(2))
                    .setTelefono((String) rs.getObject(3))
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
            return cliente;
        }
    }
    
    public static void insertar(Cliente cliente){
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            
            System.out.println(cliente.toString());
            String query = "call spi_cliente('"+ cliente.getNombre() + "','" + cliente.getApellido() + "', '"  + cliente.getTelefono() + "')";
            
            stmt.executeQuery(query);

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
    
    public static void actualizar(Cliente cliente, String telefono){
        
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();
            String query = "call spu_cliente(" + telefono + "," + cliente.getTelefono() + ",'" + cliente.getNombre() + "','" + cliente.getApellido() + "')";
            
            stmt.executeQuery(query);

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
    
}
