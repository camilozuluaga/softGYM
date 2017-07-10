/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puerta;

import java.util.logging.Level;
import java.util.logging.Logger;
import jssc.*;
import logica.msj;
import logica.p;
import net.sf.jtelegraph.TelegraphType;

public class Puerta {

    static SerialPort puertoBluetooth = null;

    public void openDoor() {
        //ejecuta hilo para no bloquear UI
        Thread t1 = new Thread(new Runnable() {
            @Override
            public void run() {
                abracadabra();
            }
        });
        t1.start();
    }

    public void abracadabra() {
        try {

            if (puertoBluetooth == null) {
                puertoBluetooth = new SerialPort(p.getParamConfig("puerto_bluetooth"));
            }

            if (!puertoBluetooth.isOpened()) {
                puertoBluetooth.openPort();
                puertoBluetooth.setParams(SerialPort.BAUDRATE_9600,
                        SerialPort.DATABITS_8,
                        SerialPort.STOPBITS_1,
                        SerialPort.PARITY_NONE);

                puertoBluetooth.setFlowControlMode(SerialPort.FLOWCONTROL_RTSCTS_IN
                        | SerialPort.FLOWCONTROL_RTSCTS_OUT);
            }

            puertoBluetooth.writeString("1");

        } catch (SerialPortException ex) {
            Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, "Puerto no disponible o bluetooth no conectado; verifique", ex);
            msj.show("Dispositivo de Control de Acceso no detectado", "No se logró identificar el dispositivo bluetooth para el control de la puerta, verifique que este conectado(puerto: "+ p.getParamConfig("puerto_bluetooth") +") e intente nuevamente.", TelegraphType.NOTIFICATION_ERROR, 8000);
        }
    }

}
