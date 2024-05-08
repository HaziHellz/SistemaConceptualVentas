/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package papeleria.model;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import static papeleria.model.Conexion.close;

/**
 *
 * @author heber
 */
public class TipoDAO {
    private static final QueryRunner QR = new QueryRunner();
    
    public List<Tipo> getTipos(){
        Connection conn = null;
        List<Tipo> tipos = null;
        try{
            conn = Conexion.getConnection();
            String query = "SELECT * FROM tipo";
            tipos = QR.query(conn, query, new BeanListHandler<>(Tipo.class));
        }catch(SQLException ex){
            ex.printStackTrace(System.err);
        }finally{
            try{
                close(conn);
            }catch(SQLException ex){
                ex.printStackTrace(System.out);
            }
        }
        return tipos;
    }
}
