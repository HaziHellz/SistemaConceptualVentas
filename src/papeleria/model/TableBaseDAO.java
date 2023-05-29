/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

import java.awt.Component;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import static papeleria.model.Conexion.close;

/**
 *
 * @author heber
 */
public class TableBaseDAO {

    public static TableModel tableModel(String filtro, String titulo) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        TableModel tableModel = new TableModel();
        String tabla = getTabla(titulo);

        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query = "";

            if (filtro.equals("Registrados")) {
                query = "SELECT nombre_" + tabla + " as " + titulo + " FROM papeleria." + tabla + " where existe_" + tabla + " = true;";
            } else if (filtro.equals("Eliminados")) {
                query = "SELECT nombre_" + tabla + " as " + titulo + " FROM papeleria." + tabla + " where existe_" + tabla + " = false;";
            } else {
                query = "SELECT nombre_" + tabla + " as " + titulo + " FROM papeleria." + tabla + ";";
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

    public static int getID(String titulo, String nombre) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int id = -1;
        String tabla = getTabla(titulo);
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            String query;
            query = "SELECT id_" + tabla + " FROM papeleria." + tabla + " where nombre_" + tabla + " = '" + nombre + "';";

            //System.out.println("QUERY TABLE: " + query);
            rs = stmt.executeQuery(query);

            while (rs.next()) {
                id = (int) rs.getObject(1);
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
            return id;
        }
    }

    public static void editarNombre(Base base, String titulo) {
        //UPDATE `papeleria`.`venta` SET `existe_venta` = '0' WHERE (`id_venta` = '6') and (`fecha_venta` = '2023-04-12 13:30:58');

        Connection conn = null;
        PreparedStatement stmt = null;

        try {

            String tabla = getTabla(titulo);

            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("UPDATE `papeleria`.`" + tabla + "` SET `nombre_" + tabla + "` = ? WHERE (`id_" + tabla + "` = ?);");
            stmt.setString(1, base.getNombreBase());
            stmt.setInt(2, base.getIdBase());
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

    public static void agregarBase(Base base, String titulo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String tabla = getTabla(titulo);

        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("INSERT INTO `papeleria`.`" + tabla + "` (`nombre_" + tabla + "`) VALUES (?);");
            stmt.setString(1, base.getNombreBase());
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

    private static String getTabla(String titulo) {
        String tabla;
        if (titulo.equals("Conceptos")) {
            tabla = "tipo";
        } else {
            tabla = "proveedor";
        }
        return tabla;
    }

    public static void eliminar(Base base, String titulo) {
        //UPDATE `papeleria`.`venta` SET `existe_venta` = '0' WHERE (`id_venta` = '6') and (`fecha_venta` = '2023-04-12 13:30:58');

        if (!delete(base, titulo)) {
            Connection conn = null;
            PreparedStatement stmt = null;

            try {

                String tabla = getTabla(titulo);

                conn = Conexion.getConnection();
                conn.setAutoCommit(false);
                stmt = conn.prepareStatement("UPDATE `papeleria`.`" + tabla + "` SET `existe_" + tabla + "` = ? WHERE (`id_" + tabla + "` = ?);");
                stmt.setBoolean(1, false);
                stmt.setInt(2, base.getIdBase());
                stmt.executeUpdate();
                conn.commit();
                JOptionPane.showMessageDialog(null, "Se configuro como Eliminado", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);

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
        } else {
            JOptionPane.showMessageDialog(null, "Se elimino de la base de datos correctamente", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);
        }

    }

    private static boolean delete(Base base, String titulo) {
        Connection conn = null;
        PreparedStatement stmt = null;
        String tabla = getTabla(titulo);
        try {
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("DELETE FROM papeleria." + tabla + " where id_" + tabla + " = " + base.getIdBase() + ";");
            stmt.executeUpdate();
            conn.commit();
            return true;
        } catch (SQLException e) {
            e.printStackTrace(System.out);
            System.out.println("NO SE PUDO ELIMINAR, DESECHANDO A EXIST= FALSE");
        } finally {

            try {
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
        }

        return false;
    }

    public static void restaurar(Base base, String titulo) {
        //UPDATE `papeleria`.`venta` SET `existe_venta` = '0' WHERE (`id_venta` = '6') and (`fecha_venta` = '2023-04-12 13:30:58');

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            String tabla = getTabla(titulo);
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("UPDATE `papeleria`.`" + tabla + "` SET `existe_" + tabla + "` = ? WHERE (`id_" + tabla + "` = ?);");
            stmt.setBoolean(1, true);
            stmt.setInt(2, base.getIdBase());
            stmt.executeUpdate();
            conn.commit();
            JOptionPane.showMessageDialog(null, "Se ha restaurado", "INFORMACION", JOptionPane.INFORMATION_MESSAGE);

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
    
    public static boolean consultarExistencia(Base base, String titulo) {
        //UPDATE `papeleria`.`venta` SET `existe_venta` = '0' WHERE (`id_venta` = '6') and (`fecha_venta` = '2023-04-12 13:30:58');

        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;
        boolean existe = false;
        System.out.println("hola");
        
        try {
            String tabla = getTabla(titulo);
            conn = Conexion.getConnection();
            conn.setAutoCommit(false);
            stmt = conn.prepareStatement("SELECT `existe_" + tabla + "` FROM papeleria." + tabla + " WHERE (`id_" + tabla + "` = ?);");
            stmt.setInt(1, base.getIdBase());
            rs = stmt.executeQuery();
            if (rs.next()) {
                existe = rs.getBoolean(1);
            }
            
            
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
        return existe;
    }
}
