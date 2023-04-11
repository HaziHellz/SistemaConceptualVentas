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
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import static papeleria.model.Conexion.*;

/**
 *
 * @author heber
 */
public class VentaDAO {

    private static VentaDAO ventaDAO;

    public static TableModel tableModel() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        TableModel tableModel = new TableModel();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT distinct p.id_venta as 'Numero de Venta Diaria', p.fecha_venta as Fecha, (select sum(cantidad_tipo) from venta_tipo k where k.id_venta = p.id_venta && k.fecha_venta = p.fecha_venta) as Total FROM venta p, venta_tipo v where p.id_venta = v.id_venta && p.fecha_venta = v.fecha_venta && p.existe_venta = true");
            metaData = rs.getMetaData();
            //metaData = Conexion.consulta("SELECT distinct p.id_venta as 'Numero de Venta Diaria', p.fecha_venta as Fecha, (select sum(cantidad_tipo) from venta_tipo k where k.id_venta = p.id_venta && k.fecha_venta = p.fecha_venta) as Total FROM venta p, venta_tipo v where p.id_venta = v.id_venta && p.fecha_venta = v.fecha_venta && p.existe_venta = true");
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

    

    public static VentaDAO getInstance() {
        if (ventaDAO == null) {
            ventaDAO = new VentaDAO();
        }
        return ventaDAO;
    }
//
//    public static void insert(ProductDTO product) {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        try {
//            conn = getConnection();
//            conn.setAutoCommit(false);
//            stmt = conn.prepareStatement("INSERT INTO producto(nombre_producto, precio, en_oferta, cantidad_stock) VALUES (?,?,?,?)");
//            stmt.setString(1, product.getNameProduct());
//            stmt.setDouble(2, product.getPrice());
//            stmt.setBoolean(3, product.isOffert());
//            stmt.setInt(4, product.getStock());
//            stmt.executeUpdate();
//            conn.commit();
//        } catch (SQLException e) {
//            e.printStackTrace(System.out);
//            try {
//                conn.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace(System.out);
//            }
//        } finally {
//            try {
//                close(stmt);
//                close(conn);
//            } catch (SQLException ex) {
//                ex.printStackTrace(System.out);
//            }
//        }
//    }
//
//    public static void update(ProductDTO product) {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        try {
//            conn = getConnection();
//            conn.setAutoCommit(false);
//            stmt = conn.prepareStatement("UPDATE producto SET nombre_producto = ?, precio = ?, en_oferta = ?, cantidad_stock = ? WHERE id_producto = ?");
//            stmt.setString(1, product.getNameProduct());
//            stmt.setDouble(2, product.getPrice());
//            stmt.setInt(3, product.isOffert() ? 1 : 0);
//            stmt.setInt(4, product.getStock());
//            stmt.setInt(5, product.getIdProduct());
//            int a = stmt.executeUpdate();
//            conn.commit();
//        } catch (SQLException e) {
//            try {
//                conn.rollback();
//            } catch (SQLException ex) {
//                ex.printStackTrace(System.out);
//            }
//            e.printStackTrace(System.out);
//        } finally {
//            try {
//                close(stmt);
//                close(conn);
//            } catch (SQLException ex) {
//                ex.printStackTrace(System.out);
//            }
//        }
//    }
//
//    public static void delete(int idProduct) {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        try {
//            conn = getConnection();
//            conn.setAutoCommit(false);
//            stmt = conn.prepareStatement("DELETE FROM producto WHERE id_producto = ?");
//            stmt.setInt(1, idProduct);
//            stmt.executeUpdate();
//            conn.commit();
//        } catch (SQLException e) {
//            try {
//                conn.rollback();
//            } catch (SQLException e1) {
//                e1.printStackTrace(System.out);
//            }
//            e.printStackTrace(System.out);
//        } finally {
//            try {
//                close(stmt);
//                close(conn);
//            } catch (Exception e) {
//            }
//        }
//    }

    public static TableModel searchWithLike(String strParam) {
        TableModel tableModel = new TableModel();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData = null;
        try {
            conn = getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("SELECT SELECT p.id_venta as Venta, p.fecha_venta as Fecha FROM venta p WHERE fecha_venta LIKE '" + strParam + "%'");
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
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                Object obj = tableModel.getValueAt(i, columns - 3);
                if (obj instanceof Integer) {
                    boolean value = ((Integer) obj) == 1;
                    tableModel.setValueAt(value, i, columns - 3);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(System.out);
        } finally {
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException e) {
                e.printStackTrace(System.out);
            }
            return tableModel;
        }

    }
//
//    public static List<ProductDTO> getProducts() {
//        Connection conn = null;
//        PreparedStatement stmt = null;
//        ResultSet rs = null;
//        TableModel tableModel = new TableModel();
//        List<ProductDTO> productos = new ArrayList();
//
//        try {
//            conn = ConnectionSys.getConnection();
//            stmt = conn.prepareStatement("SELECT nombre_producto, cantidad_stock from producto");
//            rs = stmt.executeQuery();
//
//            while (rs.next()) {
//                productos.add(new ProductDTO.ProductDTOBuilder()
//                        .nameProduct(rs.getString("nombre_producto"))
//                        .stock(rs.getInt("cantidad_stock")).build()
//                );
//            }
//        } catch (SQLException e) {
//            e.printStackTrace(System.out);
//        } finally {
//            try {
//                close(rs);
//                close(stmt);
//                close(conn);
//            } catch (SQLException ex) {
//                ex.printStackTrace(System.out);
//            }
//            return productos;
//        }

//    }
}
