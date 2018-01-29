/**
 * PrintAction.java
 */
 
package textpademo;
 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
 
/**
 * Clase que implementa la interface java.awt.print.Printable para imprimir el documento
 * presente en el área de edición.
 * 
 * @author Dark[byte]
 */
public class PrintAction implements Printable {
 
    private final JTextArea jTextArea;    //área de edición donde se encuentra el documento actual
    private JDialog dialog;               //dialogo de estado, muestra el estado de la impresión
    private int[] pageBreaks;             //arreglo de quiebres de página
    private String[] textLines;           //arreglo de líneas de texto
    private int currentPage = -1;         //página actual impresa, por defecto inicilizada en -1
    private boolean result = false;       //resultado de la impresión, por defecto es negativo
 
    /**
     * Constructor de la clase.
     * 
     * @param jComponent componente cuyo contenido se imprimirá
     */
    public PrintAction(JComponent jComponent) {
        this.jTextArea = (JTextArea) jComponent;    //guarda la instancia del área de edición
    }
 
    /**
     * Método estático que construye e inicializa la clase PrintAction para imprimir
     * el documento presente en el área de edición.
     * 
     * @param jComponent componente cuyo contenido se imprimirá
     * @param owner la ventana padre
     * @return true si la impresión fue exitosa, false en caso contrario
     */
    public static boolean print(JComponent jComponent, Frame owner) {
        PrintAction pa = new PrintAction(jComponent);    //construye una instancia de PrintAction
        return pa.printDialog(owner);                    //inicia la impresión y retorna un valor booleano
    }
 
    /**
     * Le permite al usuario configurar algunos aspectos de la impresión antes de comenzar. Durante
     * la impresión muestra una ventana de dialogo con información de la misma.
     * 
     * @param owner la ventana padre
     * @return true si la impresión fue exitosa, false en caso contrario
     */
    public boolean printDialog(Frame owner) {
        //construye un trabajo de impresión
        final PrinterJob pj = PrinterJob.getPrinterJob();
        //construye un conjunto de atributos para la impresión
        final PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        //establece a la clase PrintAction como responsable de renderizar las páginas del documento
        pj.setPrintable(this);
 
        boolean option = pj.printDialog(pras);    //presenta un dialogo de impresión
 
        if (option == true) {    //si el usuario acepta
            //construye el dialogo modal de estado sobre la ventana padre
            dialog = new PrintingMessageBox(owner, pj);
 
            //crea un nuevo hilo para que se ocupe de la impresión
            new Thread(new Runnable() {
 
                @Override
                public void run() {
                    try {
                        pj.print();                        //inicia la impresión
                        PrintAction.this.result = true;    //resultado positivo
                    } catch (PrinterException ex) {        //en caso de que ocurra una excepción
                        System.err.println(ex);
                    }
 
                    dialog.setVisible(false);    //oculta el dialogo de estado
                }
            }).start();    //inicia el hilo de impresión
 
            dialog.setVisible(true);    //hace visible el dialogo de estado
        }
 
        return PrintAction.this.result;    //retorna el resultado de la impresión
    }
 
    /**
     * Se renderiza cada página solicitada por el sistema de impresión.
     * 
     * @param g objeto de gráficos
     * @param pf el formato de la página
     * @param pageIndex el índice de la página a imprimir
     * @return PAGE_EXISTS si la página se tiene que imprimir, NO_SUCH_PAGE si la página no es valida
     */
    @Override
    public int print(Graphics g, PageFormat pf, int pageIndex) {
        Graphics2D g2d = (Graphics2D) g;                      //conversión de gráficos simples a gráficos 2D
        g2d.setFont(new Font("Serif", Font.PLAIN, 10));       //establece un fuente para todo el texto
        int lineHeight = g2d.getFontMetrics().getHeight();    //obtiene la altura del fuente
 
        if (pageBreaks == null) {    //si los quiebres de página no fueron calculados
            //construye un arreglo con las líneas de texto presentes en el área de edición
            textLines = jTextArea.getText().split("\n");
            //calcula el número de líneas que caben en cada página
            int linesPerPage = (int) (pf.getImageableHeight() / lineHeight);
            //calcula el número de quiebres de página necesarios para imprimir todo el documento
            int numBreaks = (textLines.length - 1) / linesPerPage;
            //construye un arreglo con los quiebres de página 
            pageBreaks = new int[numBreaks];
            for (int i = 0 ; i < numBreaks ; i++) {
                //se calcula la posición para cada quiebre de página
                pageBreaks[i] = (i + 1) * linesPerPage;
            }
        }
 
        //si el índice de página solicitado es menor o igual que la cantidad de quiebres total
        if (pageIndex <= pageBreaks.length) {
            /** establece una igualdad entre el origen del espacio gráfico (x:0,y:0) y el origen 
            del área imprimible definido por el formato de página */
            g2d.translate(pf.getImageableX(), pf.getImageableY());
 
            int y = 0;    //coordenada "y", inicializada en 0 (principio de página)
            //obtiene la primera línea para la página actual
            int startLine = (pageIndex == 0) ? 0 : pageBreaks[pageIndex - 1];
            //obtiene la última línea para la página actual
            int endLine = (pageIndex == pageBreaks.length) ? textLines.length : pageBreaks[pageIndex];
 
            //itera sobre las líneas que forman parte de la página actual
            for (int line = startLine ; line < endLine ; line++) {
                y += lineHeight;                          //aumenta la coordenada "y" para cada línea
                g2d.drawString(textLines[line], 0, y);    //imprime la linea en las coordenadas actuales
            }
 
            updateStatus(pageIndex);    //actualiza el estado de impresión
 
            return PAGE_EXISTS;     //la página solicitada será impresa
        } else {
            return NO_SUCH_PAGE;    //la pagina solicitada no es valida
        }
    }
 
    /**
     * Actualiza la información en el dialogo de estado de la impresión.
     * 
     * @param pageIndex índice de la página impresa
     */
    private void updateStatus(int pageIndex) {
        if (pageIndex != currentPage) {
            currentPage++;    //incrementa la página actual    
 
            //acceso seguro al EDT (Event Dispatch Thread) para actualizar la GUI
            javax.swing.SwingUtilities.invokeLater(new Runnable() {
 
                @Override
                public void run() {
                    //actualiza la información de la etiqueta lbStatusMsg
                    ((PrintingMessageBox) dialog).setStatusMsg("Imprimiendo página " + (currentPage + 1) + " ...");
                }
            });
        }
    }
 
    /**
     * Clase interna que extiende javax.swing.JDialog para presentar una ventana modal de dialogo
     * que muestra el estado de la impresión y un botón para cancelar la operación.
     */
    private class PrintingMessageBox extends JDialog {
 
        private JLabel lbStatusMsg;    //etiqueta que muestra el estado de impresión
 
        /**
         * Constructor de esta clase.
         * 
         * Construye una ventana modal de dialogo sobre una ventana padre.
         * 
         * @param owner la ventana padre
         * @param pj trabajo de impresión
         */
        public PrintingMessageBox(Frame owner, final PrinterJob pj) {
            /** invoca el constructor de la superclase para establecer la ventana padre, el título 
            de la ventana, y que será una ventana modal */
            super(owner, "Impresión", true);
            setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
 
            //construye y configura la etiqueta que muestra el estado
            lbStatusMsg = new JLabel("Iniciando ...");
            lbStatusMsg.setPreferredSize(new Dimension(200, 30));
            lbStatusMsg.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
 
            //construye el botón de cancelar
            JButton buttonCancel = new JButton("Cancelar");
            JPanel jp = new JPanel();
            jp.add(buttonCancel);
 
            //asigna un manejador de eventos para el botón de cancelar
            buttonCancel.addActionListener(new ActionListener() {
 
                @Override
                public void actionPerformed(ActionEvent e) {
                    pj.cancel();          //cancela el trabajo de impresión
                    setVisible(false);    //oculta esta ventana
                }
            });
 
            getContentPane().add(lbStatusMsg, BorderLayout.CENTER);    //añade la etiqueta en el CENTRO
            getContentPane().add(jp, BorderLayout.SOUTH);              //añade el botón, orientación SUR
 
            //asigna un manejador de eventos para cuando la ventana pierde la visibilidad
            this.addComponentListener(new ComponentAdapter() {
 
                @Override
                public void componentHidden(ComponentEvent e) {
                    Window w = (Window) e.getComponent();    //convierte el componente afectado en una ventana
                    w.dispose();                             //destruye la ventana
                }
            });
 
            setResizable(false);             //no se permite redimensionar la ventana
            pack();                          //se le da el tamaño preferido
            setLocationRelativeTo(owner);    //la ventana se centra sobre el editor de texto
        }
 
        /**
         * Establece el texto de estado que se muestra en el dialogo.
         * 
         * @param statusMsg texto de estado
         */
        public void setStatusMsg(String statusMsg) {
            lbStatusMsg.setText(statusMsg);    //establece el texto de la etiqueta lbStatusMsg
        }
    }
}