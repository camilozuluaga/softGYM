package logica;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author GID
 */
public class Conexion {

    //Instancia estatica de la conexion, sino uno llega a existir
    private static Connection connection = null;

    /**
     *
     * @return Devuelve la instancia unica de Conexion
     */
    public static Connection getConexion() {
        //Si la instancia no ha sido creado aun, se crea
        if (Conexion.connection == null) {
            contruyendoConexion();
        }
        return Conexion.connection;
    }

    //Obtener las instancias de Conexion JDBC
    private static void contruyendoConexion() {
        try {
            Class.forName("org.postgresql.Driver");
            String url = "jdbc:postgresql://localhost:5432/gimnasio?allowEncodingChanges=true";
            String usuario = "gidsoft";
            String clave = "admin";
            Conexion.connection = DriverManager.getConnection(url, usuario, clave);
        } catch (ClassNotFoundException e) {
            System.out.println("ClassNotFoundException(contruyendoConexion)  : " + e.getMessage());
            System.gc();
        } catch (SQLException e) {
            System.out.println("SQLException(contruyendoConexion) : " + e.getMessage());
            System.gc();
            JOptionPane.showMessageDialog(null, e.getMessage(), "No fue Posible Conectar con la Base de Datos", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (Exception e) {
            System.out.println(" Exception General (contruyendoConexion) : " + e.getMessage());
            System.gc();
        }
    }

    public static void liberarConexionS(Connection conex) {
        try {
            conex.close();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Cierra la conexion. Al provenir de BasicDataSource, en realidad no se
     * esta cerrando. La llamada a close() le indica al BasicDataSource que
     * hemos terminado con dicha conexion y que puede asignarsela a otro que la
     * pida.
     *
     * @param conexion
     */
    //esto es un pool de conexiones, pero no se esta usando
    public static void liberaConexion(Connection conexion) {
        try {
            if (null != conexion) {
                // En realidad no cierra, solo libera la conexion.
                conexion.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void liberarStatement(PreparedStatement p) {
        try {
            if (null != p) {
                p.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
