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
                query = "SELECT nombre_" + tabla + " as " + titulo + " FROM papeleria." + tabla + " where existe_" + tabla + " = true order by nombre_" + tabla + " asc;";
            } else if (filtro.equals("Eliminados")) {
                query = "SELECT nombre_" + tabla + " as " + titulo + " FROM papeleria." + tabla + " where existe_" + tabla + " = false order by nombre_" + tabla + " asc;";
            } else {
                query = "SELECT nombre_" + tabla + " as " + titulo + " FROM papeleria." + tabla + " order by nombre_" + tabla + " asc;";
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
        if (titulo.equals("Conceptos") || titulo.equals("tipo")) {
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
    
    public static DefaultComboBoxModel comboModel(String titulo) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultComboBoxModel cbxModel = new DefaultComboBoxModel();
        try {
            
            if (titulo.equals("Conceptos")) {
                titulo = "tipo";
            }else{
                titulo = "proveedor";
            }
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select nombre_" + titulo + " from " + titulo + " where existe_" + titulo  + " = true order by nombre_" + titulo + " asc;");
         
            int rows = 0;
            List<Object> name = new ArrayList();
            
            while (rs.next()) {
                rows += 1;
                name.add(rs.getObject(1));
                //System.out.println(rows);
            }
            
            String[] types = new String[rows];
            for (int i = 0; i < rows; i++) {
                types[i] = (String) name.get(i);
            }
            
            cbxModel = new javax.swing.DefaultComboBoxModel<>(types);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo establecer la conexion con la base de datos", "Error de conexion", 0);
            e.printStackTrace(System.out);
        } finally {
            
            try {
                close(rs);
                close(stmt);
                close(conn);
            } catch (SQLException ex) {
                ex.printStackTrace(System.out);
            }
            return cbxModel;
        }

    }
    
    public static DefaultComboBoxModel comboModelTodo(String titulo) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        DefaultComboBoxModel cbxModel = new DefaultComboBoxModel();
        
        if (titulo.equals("Conceptos")) {
                titulo = "tipo";
            }else{
                titulo = "proveedor";
            }
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select nombre_" + titulo + " from " + titulo + " where existe_" + titulo + " = true order by nombre_" + titulo + " asc;");
            metaData = rs.getMetaData();
            
            int rows = 0;
            List<Object> name = new ArrayList();
            
            //CUENTA LOS RENGLONES Y GUARDA EL NOMBRE DE CADA RENGLON
            while (rs.next()) {
                rows += 1;
                name.add(rs.getObject(1));
            }
            
            
            String[] types = new String[rows+1];
            
            //AGREGA LA OPCION TODO
            types[0] = "Todo";
            //AGREGA EL NOMBRE AL ARREGLO
            for (int i = 0; i < rows; i++) {
                types[i+1] = (String) name.get(i);
            }
            //CREA EL MODELO PARA COMBO BOX CON EL ARREGLO
            cbxModel = new javax.swing.DefaultComboBoxModel<>(types);

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
            return cbxModel;
        }

    }
    
    public static Base getNombre(String nombreTipo, String titulo){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Base tipo = null;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select id_" + titulo + ", nombre_" + titulo + ", existe_" + titulo + " from " + titulo + " where existe_" + titulo + " = true && nombre_" + titulo + " = '" + nombreTipo + "'");
            
            
            //CUENTA LOS RENGLONES Y GUARDA EL NOMBRE DE CADA RENGLON
            while (rs.next()) {
                tipo = new Base.TipoBuilder().idBase((int) rs.getObject(1)).nameBase((String) rs.getObject(2)).exists((boolean) rs.getObject(3)).build();
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
            return tipo;
        }
    }
    
    public static List<Base> getTipos() {
        List<Base> tipos = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select nombre_tipo, id_tipo from tipo where existe_tipo = true");
            
            while (rs.next()) {
                tipos.add(new Base.TipoBuilder().nameBase((String) rs.getObject(1)).idBase((Integer) rs.getObject(2)).build());
                //System.out.println(rows);
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
            return tipos;
        }

    }
}
