/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author heber
 */
public class Conexion {

    private static final String DB_NAME = "papeleria";
    //private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&useTimezone=true";
    //private static final String URL = "jdbc:mysql://10.181.151.142:3306/" + DB_NAME + "?useSSL=false&useTimezone=true"; //PAPELERIA
    private static final String URL = "jdbc:mysql://localhost:3306/" + DB_NAME + "?useSSL=false&useTimezone=true";
    public Conexion() {

    }

    public static Connection getConnection() throws SQLException {
      return DriverManager.getConnection(URL, "Cliente", "Ananava7211");
    }

    public static void close(Connection conn) throws SQLException {
        conn.close();
    }

    public static void close(PreparedStatement stmt) throws SQLException {
        stmt.close();
    }

    public static void close(Statement stmt) throws SQLException {
        stmt.close();
    }

    public static void close(ResultSet rs) throws SQLException {
        rs.close();
    }


}
