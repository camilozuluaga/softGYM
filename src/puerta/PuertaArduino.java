/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package puerta;

//import com.panamahitek.PanamaHitek_Arduino;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
//import org.zu.ardulink.Link;

/**
 *
 * @author Usuario
 */
public class PuertaArduino {

    public void openDoor() {
        //ejecuta hilo para no bloquear UI
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                abraCadabra();
            }
        });
        t1.start();
    }

    public void abraCadabra() {
//        System.out.println(System.getProperty("java.library.path"));
//        Link link = Link.getDefaultInstance();
//        List<String> portList = link.getPortList();
//        if (portList != null && portList.size() > 0) {
//            try {
//                String port = portList.get(0);
//                System.out.println("Connecting on port: " + port);
//                boolean connected = link.connect(port, 9600);
//                System.out.println("Connected:" + connected);
//                Thread.sleep(1600);
//                link.sendCustomMessage("1");
//            } catch (Exception ex) {
//                Logger.getLogger(Puerta.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public void cerrarConexion() {
//            try {              
//                arduino.killArduinoConnection();
//            } catch (Exception ex) {
//                Logger.getLogger(PuertaArduino.class.getName()).log(Level.SEVERE, null, ex);
//            }       
    }

    public void conexion() {
//        try {
//            arduino.arduinoTX("COM3", 9600);
//        } catch (Exception ex) {
//            Logger.getLogger(PuertaArduino.class.getName()).log(Level.SEVERE, null, ex);
//        }
    }
}
