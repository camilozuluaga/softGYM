package logica;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.sql.rowset.RowSetProvider;

public class DB {

    PreparedStatement pt = null;
    ResultSet rsQuery = null;
    CachedRowSet crs = null;
    //Retorna la Llave Primaria Generada
    private int keys;

    /**
     * Ejecuta una Query
     *
     * @param sql
     * @return un Objecto bidimencional con el resultado de la Consulta
     */
    public CachedRowSet sqlDatos(String sql) {

        Object[][] datos = null;

        try {

            crs = RowSetProvider.newFactory().createCachedRowSet();
            crs.setCommand(sql);
            crs.execute(Conexion.getConexion());

        } catch (SQLException e) {
            //this.i("SQLException: Util.java (selectSQL) = " + e.getMessage());
            Logger.getLogger(DB.class.getName()).log(Level.SEVERE, "Error SQL ", e);
            //mail.sendBug(mail.toHTML("SQLException", "Util.java", "sqlDatos(String sql)", e.getMessage(), e.toString().concat(sql)));
        }

        return crs;
    }

    public boolean sqlEjec(String sql) {
        this.pt = null;
        boolean estado = false;
        try {
            pt = Conexion.getConexion().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pt.execute();
            ResultSet rsKeys = pt.getGeneratedKeys();
            while (rsKeys.next()) {
                setKeys(rsKeys.getInt(1));
            }
            pt = null;
            estado = true;
        } catch (SQLException e) {
            //this.i("Error: Util: " + e.toString() );
            System.out.println("e = " + e);
            estado = false;
            //mail.sendBug(mail.toHTML("SQLException", "Util.java", "sqlEjec(String sql)", e.getMessage(), e.toString()));

        } catch (Exception e) {
            System.out.println("e = " + e);
//            mail.sendBug(mail.toHTML("Exception", "Util.java", "sqlEjec(String sql)", e.getMessage(), e.toString()));
        }
        return estado;
    }

    
    public int getKeys() {
        return keys;
    }

    public void setKeys(int keys) {
        this.keys = keys;
    }

}
