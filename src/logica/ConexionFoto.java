/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

/**
 *
 * @author jucazuse
 */
public class ConexionFoto {

    private String user = "gidsoft";
    private String password = "admin";
    private String url = "jdbc:postgresql://localhost:5432/gimnasio?allowEncodingChanges=true";

    private Connection connection = null;
    private ResultSet resultSet = null;
    private Statement statement = null;

    //Constructor de clase que se conecta a la base de datoS
    public ConexionFoto() {
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection(url, user, password);
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    public boolean guardarfoto(String clave, String ruta, String tabla) {
        FileInputStream fis = null;
        try {
            File file = new File(ruta);
            fis = new FileInputStream(file);
            PreparedStatement pstm = null;
            if (tabla.contains("usua")) {
                pstm = connection.prepareStatement("UPDATE " + tabla + " SET foto = ? WHERE id= " + clave + ";");
            } else {
                pstm = connection.prepareStatement("UPDATE " + tabla + " SET foto = ? WHERE clave= '" + clave + "';");
            }
            pstm.setBinaryStream(1, fis, (int) file.length());
            pstm.execute();
            pstm.close();
            return true;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                fis.close();
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionFoto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean consultarFoto(String clave, JLabel lbFoto) {
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT foto FROM socio WHERE clave= '" + clave + "';");
            resultSet = pstm.executeQuery();
            while (resultSet.next()) {
                System.out.println(resultSet.getBytes("foto"));
                if (resultSet.getBytes("foto") != null) {
                    ImageIcon foto = new ImageIcon(resultSet.getBytes("foto"));
                    lbFoto.setIcon(foto);
                }
            }
            pstm.close();
            return true;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(ConexionFoto.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    
}
