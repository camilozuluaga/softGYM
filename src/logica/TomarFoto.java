package logica;

import java.awt.BorderLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.lang.Thread.UncaughtExceptionHandler;

import javax.swing.JFrame;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamEvent;
import com.github.sarxos.webcam.WebcamListener;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamPicker;
import java.awt.Dimension;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import net.sf.jtelegraph.TelegraphType;

/**
 * Proof of concept of how to handle webcam video stream from Java
 *
 * @author Bartosz Firyn (SarXos)
 */
public class TomarFoto extends JFrame implements Runnable, WebcamListener, WindowListener, UncaughtExceptionHandler, ItemListener {

    /*Es un número de versión que posee cada clase Serializable, 
     el cual es usado en la deserialización para verificar que el emisor y el receptor de un objeto serializado 
     mantienen una compatibilidad en lo que a serialización se refiere 
     con respecto a la clases que tienen cargadas (el emisor y el receptor)*/
    private static final long serialVersionUID = 1L;

    private Webcam webcam = null;
    private WebcamPanel panel = null;
    private WebcamPicker picker = null;
    private final JButton boton = new JButton();
    /*varaible para guardar el valor de la cedula*/
    public String clave;
    /*ruta relativa donde se esta ejecutando el programa, la usamos para obtener la imagen
     tomada para despues redimensionarla con el metodo redimensionar*/
    public String ruta = System.getProperty("user.dir").concat(System.getProperty("file.separator"));
    /*contiene la ruta donde se encuentra nuestro archivo*/
    File eliminarArchivo;
    boolean sinFoto;

    public boolean isSinFoto() {
        return sinFoto;
    }

    public void setSinFoto(boolean sinFoto) {
        this.sinFoto = sinFoto;
    }
    private ConexionFoto conexionFoto = new ConexionFoto();
    JLabel lbFoto = new JLabel();
    String tabla;

    public String getTabla() {
        return tabla;
    }

    public void setTabla(String tabla) {
        this.tabla = tabla;
    }




    public JLabel getLbFoto() {
        return lbFoto;
    }

    public void setLbFoto(JLabel lbFoto) {
        this.lbFoto = lbFoto;
    }
    
    /*meotod que ustilizamos para cerrar la ventana.*/
    public void cerrarVentana() {
        this.setVisible(false);
        webcam.close();
    }

    /*metodo constructor que recibe como parametro la cedula, para con ese 
     dato guardar las fotos de los usuarios en la base de datos.*/
     
    public void tomarFoto(String clave) {
        this.run();
        this.clave=clave;
    }

    /*Informacion Metodo----> redimensionar
    
     metodo con el que redimensionamos las imagenes. 
     Recibe 4 parametros que son.
     nombreFotoCapturada ---- es el nombre de la foto que se acaba de tomar.
     ancho ---- el ancho que le queremos dar a la imagen
     alto ---- el alto que le queremos dar a la imagen
     nombreImagen ---- nombre con el cual queremos guardar nuestra imagen
   
     este metodo tiene la particularidad de que redimensiona la imagen 
     pero no la guarda sino que la convierte a base64 y la retorna para ser usada por el
     metodo guardarFoto();
     */
    public void guardarFoto(String nombreFotoCapturada, String nombreTabla) {
        conexionFoto.guardarfoto(clave, ruta.concat(nombreFotoCapturada), tabla);
        conexionFoto.consultarFoto(clave, lbFoto);

    }


    @Override
    public void run() {
        /*Informacion Metodo----> accionDelBoton
         en este metodo guardamos, un boton con la accion que realiza cuando se da click
         sobre el.*/
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                Webcam webcam = Webcam.getDefault();
                String name = String.format("Foto.png");
                try {
                    ImageIO.write(webcam.getImage(), "png", new File(name));
                    guardarFoto(name,tabla);
                    cerrarVentana();
                    eliminarArchivo = new File(ruta.concat(name));
                    eliminarArchivo.delete();
                } catch (IOException ex) {
                    Logger.getLogger(TomarFoto.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        
        boton.setText("Tomar Foto");
        setTitle("Capturar Foto");
        setLocationRelativeTo(this);
        setAlwaysOnTop(true);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setResizable(false);

        addWindowListener(this);
        picker = new WebcamPicker();
        picker.addItemListener(this);
        webcam = picker.getSelectedWebcam();

        if (webcam == null) {
            setSinFoto(false);
            msj.show("Informacion", "No se encuentra una camara conectada", TelegraphType.NOTIFICATION_WARNING, 5000);
            return;
        }

        webcam.setViewSize(new Dimension(176, 144));
        webcam.addWebcamListener(TomarFoto.this);

        panel = new WebcamPanel(webcam, false);
        panel.setFPSDisplayed(true);

        add(picker, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
        add(boton, BorderLayout.SOUTH);
        pack();
        setVisible(true);

        Thread t = new Thread() {
            @Override
            public void run() {
                panel.start();
            }
        };
        t.setName("Capturar Foto");
        t.setDaemon(true);
        t.setUncaughtExceptionHandler(this);
        t.start();
    }


    @Override
    public void webcamOpen(WebcamEvent we) {
        System.out.println("webcam open");
    }

    @Override
    public void webcamClosed(WebcamEvent we) {
        System.out.println("webcam closed");
    }

    @Override
    public void webcamDisposed(WebcamEvent we) {
        System.out.println("webcam disposed");
    }

    @Override
    public void webcamImageObtained(WebcamEvent we) {
        // do nothing
    }

    @Override
    public void windowActivated(WindowEvent e) {
    }

    @Override
    public void windowClosed(WindowEvent e) {
        webcam.close();
    }

    @Override
    public void windowClosing(WindowEvent e) {
    }

    @Override
    public void windowOpened(WindowEvent e) {
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        System.out.println("webcam viewer resumed");
        panel.resume();
    }

    @Override
    public void windowIconified(WindowEvent e) {
        System.out.println("webcam viewer paused");
        panel.pause();
    }

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        System.err.println(String.format("Exception in thread %s", t.getName()));
        e.printStackTrace();
    }

    @Override
    public void itemStateChanged(ItemEvent e) {
        System.out.println("as");
    }

}
