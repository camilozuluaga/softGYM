/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puerta;

import desarrollo.login;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JComboBox;
import jssc.*;
import logica.DB;
import net.sf.jcarrierpigeon.WindowPosition;
import net.sf.jtelegraph.Telegraph;
import net.sf.jtelegraph.TelegraphQueue;
import net.sf.jtelegraph.TelegraphType;

public class Puerta {

    static SerialPort puertoBluetooth = null;
    private DB db = new DB();

    public void openDoor() {
        //ejecuta hilo para no bloquear UI
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                enviarDato();
            }
        });
        t1.start();
    }

    public void openConnection() {
        //ejecuta hilo para no bloquear UI
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                abracadabra();
            }
        });
        t1.start();
    }

    public void openConnection(final String puerto) {
        //ejecuta hilo para no bloquear UI
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                abracadabra(puerto);
            }
        });
        t1.start();
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
     * Metodo para listar los puertos que hay actualmente en el computador
     *
     * @param cboPuertos
     */
    public void listarPuertos(JComboBox cboPuertos) {
        String com[] = SerialPortList.getPortNames();

        for (int i = 0; i < com.length; i++) {
            cboPuertos.addItem(com[i]);
        }
    }

    /**
     * *
     * Metodo para enviar los datos de comunicacion, solamente una vez este
     * abierto el puerto
     */
    public void enviarDato() {
        try {
            puertoBluetooth.writeString("1");
        } catch (SerialPortException ex) {
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * *
     * Metodo que permite abrir la comunicacion entre el computador y el
     * dispositivo
     */
    public void abracadabra() {
        try {

            if (puertoBluetooth == null) {
                puertoBluetooth = new SerialPort(consultarPuerto());
            }

            puertoBluetooth.openPort();
            puertoBluetooth.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            puertoBluetooth.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
                    | SerialPort.FLOWCONTROL_RTSCTS_OUT);
            puertoBluetooth.writeString("1");

        } catch (SerialPortException ex) {
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, "Puerto no disponible o bluetooth no conectado; verifique", ex);
            mensaje("Dispositivo de control no configurado", "No se logró identificar el dispositivo bluetooth para el control de la puerta, verifique que este conectado \n Asegurese de configurar la cantonera", TelegraphType.NOTIFICATION_WARNING, 2500);
            // .show("Dispositivo de Control de Acceso no detectado", "No se logró identificar el dispositivo bluetooth para el control de la puerta, verifique que este conectado(puerto: "+ p.getParamConfig("puerto_bluetooth") +") e intente nuevamente.", TelegraphType.NOTIFICATION_ERROR, 8000);
        }
    }



    public void cerrarConexion() {
        try {
            puertoBluetooth.closePort();
        } catch (SerialPortException ex) {
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void abracadabra(String puerto) {
        try {

            if (puertoBluetooth == null) {
                puertoBluetooth = new SerialPort(consultarPuerto());
            }

            puertoBluetooth.openPort();
            puertoBluetooth.setParams(SerialPort.BAUDRATE_9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1, SerialPort.PARITY_NONE);

            puertoBluetooth.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
                    | SerialPort.FLOWCONTROL_RTSCTS_OUT);
            puertoBluetooth.writeString("1");

        } catch (SerialPortException ex) {
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, "Puerto no disponible o bluetooth no conectado; verifique", ex);
            mensaje("Dispositivo de control no configurado", "No se logró identificar el dispositivo bluetooth para el control de la puerta, verifique que este conectado \n Asegurese de configurar la cantonera", TelegraphType.NOTIFICATION_WARNING, 2500);
            // .show("Dispositivo de Control de Acceso no detectado", "No se logró identificar el dispositivo bluetooth para el control de la puerta, verifique que este conectado(puerto: "+ p.getParamConfig("puerto_bluetooth") +") e intente nuevamente.", TelegraphType.NOTIFICATION_ERROR, 8000);
        }
    }

//    public void abracadabra(String puerto) {
//        try {
//
//            if (puertoBluetooth == null) {
//                puertoBluetooth = new SerialPort(puerto);
//            }
//
//            if (!puertoBluetooth.isOpened()) {
//                puertoBluetooth.openPort();
//                puertoBluetooth.setParams(SerialPort.BAUDRATE_9600,
//                        SerialPort.DATABITS_8,
//                        SerialPort.STOPBITS_1,
//                        SerialPort.PARITY_NONE);
//
//                puertoBluetooth.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
//                        | SerialPort.FLOWCONTROL_RTSCTS_OUT);
//                puertoBluetooth.writeString("1");
//            } else {
//                puertoBluetooth.writeString("1");
//                puertoBluetooth.closePort();
//            }
//
//        } catch (SerialPortException ex) {
//            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, "Puerto no disponible o bluetooth no conectado; verifique", ex);
//            mensaje("Dispositivo de control no configurado", "No se logró identificar el dispositivo bluetooth para el control de la puerta, verifique que este conectado \n Asegurese de configurar la cantonera", TelegraphType.NOTIFICATION_WARNING, 2500);
//            // .show("Dispositivo de Control de Acceso no detectado", "No se logró identificar el dispositivo bluetooth para el control de la puerta, verifique que este conectado(puerto: "+ p.getParamConfig("puerto_bluetooth") +") e intente nuevamente.", TelegraphType.NOTIFICATION_ERROR, 8000);
//        }
//    }
    public void mensaje(String titulo, String mensaje, TelegraphType TipoMensaje, int tiempo) {
        Telegraph tele = new Telegraph(titulo, mensaje, TipoMensaje, WindowPosition.TOPRIGHT, tiempo);
        TelegraphQueue q = new TelegraphQueue();
        q.add(tele);
    }

    public void probando() {
        try {

            if (puertoBluetooth == null) {
                puertoBluetooth = new SerialPort(consultarPuerto());
            }

            if (!puertoBluetooth.isOpened()) {
                puertoBluetooth.openPort();
                puertoBluetooth.setParams(SerialPort.BAUDRATE_9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                puertoBluetooth.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
                        | SerialPort.FLOWCONTROL_RTSCTS_OUT);
                puertoBluetooth.writeString("1");
            } else {
                puertoBluetooth.writeString("1");
                puertoBluetooth.closePort();
            }

        } catch (SerialPortException ex) {
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, "Puerto no disponible o bluetooth no conectado; verifique", ex);
            mensaje("Dispositivo de control no configurado", "No se logró identificar el dispositivo bluetooth para el control de la puerta, verifique que este conectado \n Asegurese de configurar la cantonera", TelegraphType.NOTIFICATION_WARNING, 2500);
            // .show("Dispositivo de Control de Acceso no detectado", "No se logró identificar el dispositivo bluetooth para el control de la puerta, verifique que este conectado(puerto: "+ p.getParamConfig("puerto_bluetooth") +") e intente nuevamente.", TelegraphType.NOTIFICATION_ERROR, 8000);
        }
    }
}
