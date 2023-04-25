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
import java.util.ArrayList;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import static papeleria.model.Conexion.close;

/**
 *
 * @author heber
 */
public class TipoDAO {
    
    
    public static DefaultComboBoxModel comboModel() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        DefaultComboBoxModel cbxModel = new DefaultComboBoxModel();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select nombre_tipo from tipo where existe_tipo = true");
         
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
    
    public static DefaultComboBoxModel comboModelTodo() {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        ResultSetMetaData metaData;
        DefaultComboBoxModel cbxModel = new DefaultComboBoxModel();
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select nombre_tipo from tipo where existe_tipo = true");
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
    
    public static int getIdTipo(String nombreTipo){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int idTipo = -1;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select id_tipo from tipo where existe_tipo = true && nombre_tipo = '" + nombreTipo + "'");
            
            
            //CUENTA LOS RENGLONES Y GUARDA EL NOMBRE DE CADA RENGLON
            while (rs.next()) {
                idTipo = (int) rs.getObject(1);
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
            return idTipo;
        }
    }
    
    public static Tipo getTipo(String nombreTipo){
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        Tipo tipo = null;
        
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select id_tipo, nombre_tipo, existe_tipo from tipo where existe_tipo = true && nombre_tipo = '" + nombreTipo + "'");
            
            
            //CUENTA LOS RENGLONES Y GUARDA EL NOMBRE DE CADA RENGLON
            while (rs.next()) {
                tipo = new Tipo.TipoBuilder().idType((int) rs.getObject(1)).nameType((String) rs.getObject(2)).exists((boolean) rs.getObject(3)).build();
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
    
    public static List<Tipo> getTipos() {
        List<Tipo> tipos = new ArrayList();
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = Conexion.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery("select nombre_tipo from tipo where existe_tipo = true");
            
            while (rs.next()) {
                tipos.add(new Tipo.TipoBuilder().nameType((String) rs.getObject(1)).build());
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
