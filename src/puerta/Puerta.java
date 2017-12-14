/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puerta;

import com.panamahitek.ArduinoException;
import com.panamahitek.PanamaHitek_Arduino;
import desarrollo.AdministrarCaja;
import desarrollo.AdministrarPuerta;
import desarrollo.login;
import desarrollo.loginReinicio;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import jssc.*;
import logica.DB;
import net.sf.jcarrierpigeon.WindowPosition;
import net.sf.jtelegraph.Telegraph;
import net.sf.jtelegraph.TelegraphQueue;
import net.sf.jtelegraph.TelegraphType;

public class Puerta {

    static SerialPort puertoBluetooth = null;
    private DB db = new DB();
    PanamaHitek_Arduino arduino = new PanamaHitek_Arduino();

    /**
     * *
     * Metodo que sirve para hacer la conexion entre el arduino y el puerto del
     * computador
     *
     * @param puerto COM2, COM3... Parametro de conexion, el cual es el nombre
     * del puerto
     */
    public void abrirConexion(String puerto) {
        try {
            arduino.arduinoTX(puerto, 9600);
        } catch (ArduinoException ex) {
            mensaje("Dispositivo de control", "No se ha podido conectar al puerto. \n Asegurese de configurar el dispositivo", TelegraphType.NOTIFICATION_ERROR, 2500);
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *
     * Metodo que permite configurar de manera automatica el puerto
     *
     * @param puerto nombre del puerto para la configuración
     */
    public void configuararPuerto(String puerto, AdministrarPuerta puerta) {
        PanamaHitek_Arduino conexion = new PanamaHitek_Arduino();
        try {
            conexion.arduinoTX(puerto, 9600);
            conexion.sendData("1");
            conexion.killArduinoConnection();
            System.out.println("Se reiniciara su sistema");
            JOptionPane.showMessageDialog(puerta, "Se ha configurado correctamente el dispositivo. /n Por favor de clic guardar.", "Configuración Puerto", JOptionPane.INFORMATION_MESSAGE);
        } catch (ArduinoException ex) {
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *
     * Metodo que sirve para enviar la información al arduino
     */
    public void openDoor() {
        try {
            arduino.sendData("1");

        } catch (ArduinoException ex) {
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SerialPortException ex) {
            mensaje("Dispositivo de control", "No se ha podido conectar al puerto. \n Asegurese de configurar el dispositivo", TelegraphType.NOTIFICATION_ERROR, 2500);
            mensaje("Dispositivo de control", "No se ha podido enviar la información al puerto. \n Asegurese de configurar el dispositivo", TelegraphType.NOTIFICATION_ERROR, 2500);
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *
     * Metodo que sirve para cerrar la conexion
     */
    public void cerrarConexion() {
        try {
            arduino.killArduinoConnection();

        } catch (ArduinoException ex) {
            mensaje("Dispositivo de control", "No se ha podido finalizar la conexión. \n Asegurese de configurar el dispositivo", TelegraphType.NOTIFICATION_ERROR, 2500);
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *
     * Metodo que sirve para cargar todos los puertos en un combo box
     *
     * @param puertos combo box en el cual se cargaran los puertos
     */
    public void cargarPuertos(JComboBox puertos) {
        ArrayList<String> cargandoPuertos = new ArrayList<>();
        cargandoPuertos = (ArrayList<String>) arduino.getSerialPorts();

        for (int i = 0; i < cargandoPuertos.size(); i++) {
            puertos.addItem(String.valueOf(cargandoPuertos.get(i)));
        }
    }

    /**
     * *
     * Metodo para consultar el puerto que actualmente esta configurado en la
     * aplicacion
     *
     * @return Retorna el nombre del puerto
     */
    public String consultarPuerto() {
        CachedRowSet data;
        String puerto = "";
        String querySQL = "SELECT port FROM puerto";
        data = db.sqlDatos(querySQL);

        try {
            if (data.next()) {
                puerto = data.getString("port");

                System.out.println("El puerto cargado de la bd es: " + puerto);
                System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
            mensaje("Dispositivo de control", "No se ha podido configurar el dispositivo bluetooth para el control de la puerta, verifique que este conectado \n Asegurese de configurar la cantonera", TelegraphType.NOTIFICATION_WARNING, 2500);

        }
        return puerto;
    }

    /**
     * *
     * Metodo que sirve para mostrar un mensaje animado
     *
     * @param titulo nombre del mensaje
     * @param mensaje descripcion del mensaje
     * @param TipoMensaje tipo del mensaje
     * @param tiempo tiempo el cual se mostrara el mensaje
     */
    public void mensaje(String titulo, String mensaje, TelegraphType TipoMensaje, int tiempo) {
        Telegraph tele = new Telegraph(titulo, mensaje, TipoMensaje, WindowPosition.TOPRIGHT, tiempo);
        TelegraphQueue q = new TelegraphQueue();
        q.add(tele);
    }

}
