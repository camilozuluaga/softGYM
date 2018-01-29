/**
 * JFontChooser.java
 */
 
package textpademo;
 
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GraphicsEnvironment;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.Position;
 
/**
 * Clase que extiende javax.swing.JComponent para crear un componente Swing donde el usuario
 * pueda seleccionar un fuente.
 * 
 * @author Dark[byte]
 */
public class JFontChooser extends JComponent {
 
    private final Font initialFont;    //fuente inicial
    private Font font;                 //fuente seleccionado por el usuario
 
    /**
     * Constructor de esta clase.
     * 
     * @param initialFont el fuente inicial
     */
    public JFontChooser(Font initialFont) {
        this.initialFont = initialFont;    //guarda el fuente inicial
    }
 
    /**
     * Método estático que construye e inicializa la clase JFontChooser para presentar al 
     * usuario el dialogo de selección de fuente.
     * 
     * @param owner la ventana padre
     * @param title el título de la ventana
     * @param initialFont el fuente inicial
     * 
     * @return el fuente seleccionado
     */
    public static Font showDialog(Frame owner, String title, Font initialFont) {
        JFontChooser fontChooser = new JFontChooser(initialFont);    //construye una instancia de JFontChooser
 
        //construye el dialogo de selección de fuente sobre la ventana padre
        JDialog dialog = new FontChooserDialog(owner, title, fontChooser);
        dialog.setVisible(true);    //hace visible el dialogo
 
        return fontChooser.getSelectedFont();    //retorna el fuente seleccionado
    }
 
    /**
     * Retorna el fuente inicial.
     * 
     * @return el fuente inicial
     */
    public Font getInitialFont() {
        return initialFont;
    }
 
    /**
     * Retorna el fuente seleccionado.
     * 
     * @return el fuente seleccionado
     */
    public Font getSelectedFont() {
        return font;
    }
 
    /**
     * Establece el fuente seleccionado.
     * 
     * @param font el fuente seleccionado
     */
    public void setSelectedFont(Font font) {
        this.font = font;
    }
}
 
class FontChooserDialog extends JDialog {
 
    private JTextField textFieldNames;     //campo de texto para el nombre del fuente
    private JTextField textFieldStyles;    //campo de texto para el estilo del fuente
    private JTextField textFieldSizes;     //campo de texto para el tamaño del fuente
    private JList listFontNames;     //lista para nombres de fuente
    private JList listFontStyles;    //lista para estilos de fuente
    private JList listFontSizes;     //lista para tamaños de fuente
    private JLabel textExample;    //etiqueta que muestra un ejemplo del fuente seleccionado
    //arreglo con nombres de fuente disponibles
    private static final String[] FONT_NAMES = GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames();
    //arreglo con estilos de fuente
    private static final String[] FONT_STYLES = {
        "Normal", "Bold", "Italic", "Bold Italic"
    };
    //arreglo con tamaños de fuente
    private static final String[] FONT_SIZES = {
        "8", "9", "10", "11", "12", "13", "14", "16", "18", "20", "24", "28", "32", "48", "72"
    };
 
    /**
     * Constructor de la clase.
     * 
     * Construye una ventana modal de dialogo sobre una ventana padre. El usuario 
     * puede seleccionar un fuente.
     * 
     * @param owner la ventana padre
     * @param title
     * @param jFontChooser 
     */
    public FontChooserDialog(Frame owner, String title, final JFontChooser jFontChooser) {
        /** invoca el constructor de la superclase para establecer la ventana padre, el título 
        de la ventana, y que será una ventana modal */
        super(owner, title, true);
 
        final EventHandler eventHandler = new EventHandler();    //construye una instancia de EventHandler
        JScrollPane jScrollPane;
 
        JPanel cp = (JPanel) getContentPane();                        //obtiene el panel de contenido principal
        cp.setLayout(new GridBagLayout());                            //establece un GridBagLayout
        cp.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));    //establece un borde de espacio
 
        //construye un conjunto de limitaciones para los componentes del GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
 
        JLabel label1 = new JLabel("Name:");    //construye la etiqueta "Name:"
        gbc.insets = new Insets(0, 5, 0, 5);
        gbc.anchor = GridBagConstraints.FIRST_LINE_START;
        gbc.weightx = 0.5;
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.add(label1, gbc);    //añade la etiqueta, coordenadas X:1 - Y:1
 
        JLabel label2 = new JLabel("Style:");    //construye la etiqueta "Style:"
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.add(label2, gbc);    //añade la etiqueta, coordenadas X:2 - Y:1
 
        JLabel label3 = new JLabel("Size:");    //construye la etiqueta "Size:"
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.add(label3, gbc);    //añade la etiqueta, coordenadas X:3 - Y:1
 
        textFieldNames = new JTextField("");    //construye el campo de texto para el nombre del fuente
        int fixedWidth = textFieldNames.getPreferredSize().width;
        Dimension fixedSize = new Dimension(fixedWidth, 20);
        textFieldNames.setMinimumSize(fixedSize);
        textFieldNames.setMaximumSize(fixedSize);
        textFieldNames.setPreferredSize(fixedSize);
        textFieldNames.addKeyListener(eventHandler);    //asigna el manejador de eventos para el teclado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.add(textFieldNames, gbc);    //añade el campo de texto, coordenadas X:1 - Y:2
 
        textFieldStyles = new JTextField("");    //construye el campo de texto para el estilo del fuente
        fixedWidth = textFieldStyles.getPreferredSize().width;
        fixedSize = new Dimension(fixedWidth, 20);
        textFieldStyles.setMinimumSize(fixedSize);
        textFieldStyles.setMaximumSize(fixedSize);
        textFieldStyles.setPreferredSize(fixedSize);
        textFieldStyles.addKeyListener(eventHandler);    //asigna el manejador de eventos para el teclado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 2;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.add(textFieldStyles, gbc);    //añade el campo de texto, coordenadas X:2 - Y:2
 
        textFieldSizes = new JTextField("");    //construye el campo de texto para el tamaño del fuente
        fixedWidth = textFieldSizes.getPreferredSize().width;
        fixedSize = new Dimension(fixedWidth, 20);
        textFieldSizes.setMinimumSize(fixedSize);
        textFieldSizes.setMaximumSize(fixedSize);
        textFieldSizes.setPreferredSize(fixedSize);
        textFieldSizes.addKeyListener(eventHandler);    //asigna el manejador de eventos para el teclado
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 3;
        gbc.gridy = 2;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.add(textFieldSizes, gbc);    //añade el campo de texto, coordenadas X:3 - Y:2
 
        listFontNames = new JList(FONT_NAMES);    //construye la lista para nombres de fuente
        jScrollPane = new JScrollPane(listFontNames);
        String fontName = jFontChooser.getInitialFont().getName();
        listFontNames.setSelectedValue(fontName, true);    //selecciona el nombre de fuente inicial
        textFieldNames.setText(fontName);
        listFontNames.addListSelectionListener(eventHandler);
        gbc.gridx = 1;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.add(jScrollPane, gbc);    //añade la lista, coordenadas X:1 - Y:3
 
        listFontStyles = new JList(FONT_STYLES);    //construye la lista para estilos de fuente
        jScrollPane = new JScrollPane(listFontStyles);
        int fontSyle = jFontChooser.getInitialFont().getStyle();
        listFontStyles.setSelectedIndex(fontSyle);    //selecciona el estilo de fuente inicial
        textFieldStyles.setText(listFontStyles.getSelectedValue().toString());
        listFontStyles.addListSelectionListener(eventHandler);
        gbc.gridx = 2;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.add(jScrollPane, gbc);    //añade la lista, coordenadas X:2 - Y:3
 
        listFontSizes = new JList(FONT_SIZES);    //construye la lista para tamaños de fuente
        jScrollPane = new JScrollPane(listFontSizes);
        String fontSize = String.valueOf(jFontChooser.getInitialFont().getSize());
        listFontSizes.setSelectedValue(fontSize, true);    //selecciona el tamaño de fuente inicial
        textFieldSizes.setText(fontSize);
        listFontSizes.addListSelectionListener(eventHandler);
        gbc.gridx = 3;
        gbc.gridy = 3;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        cp.add(jScrollPane, gbc);    //añade la lista, coordenadas X:3 - Y:3
 
        textExample = new JLabel("AaBaCcDdEeFfGgHhJj");        //construye la etiqueta para mostrar un ejemplo del fuente seleccionado 
        textExample.setHorizontalAlignment(JLabel.CENTER);
        textExample.setFont(jFontChooser.getInitialFont());    //establece el fuente inicial
        textExample.setOpaque(true);
        textExample.setBackground(Color.WHITE);
        textExample.setBorder(BorderFactory.createCompoundBorder(BorderFactory.createTitledBorder("Texto de ejemplo"),
                                                                 BorderFactory.createEmptyBorder(10, 10, 10, 10)));
        int fixedHeight = textExample.getPreferredSize().height;
        fixedSize = new Dimension(100, fixedHeight);
        textExample.setMinimumSize(fixedSize);
        textExample.setMaximumSize(fixedSize);
        textExample.setPreferredSize(fixedSize);
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.gridwidth = 3;
        gbc.gridheight = 1;
        cp.add(textExample, gbc);    //añade la etiqueta, coordenadas X:1 - Y:4
 
        JPanel jp = new JPanel();                        //construye un panel para los botones
        JButton buttonOk = new JButton("Ok");            //construye el botón de aceptar
        JButton buttonCancel = new JButton("Cancel");    //construye el botón de cancelar
        jp.add(buttonOk);                                //añade los botones al panel
        jp.add(buttonCancel);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridx = 1;
        gbc.gridy = 5;
        cp.add(jp, gbc);    //añade el panel, coordenadas X:1 - Y:5
 
        //asigna un manejador de eventos para el botón de cancelar
        buttonOk.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                jFontChooser.setSelectedFont(getSelectedFont());
                //fontChooser.font = getSelectedFont();
                setVisible(false);
            }
        });
 
        //asigna un manejador de eventos para el botón de cancelar
        buttonCancel.addActionListener(new ActionListener() {
 
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);    //oculta esta ventana
            }
        });
 
        //asigna un manejador de eventos para el cierre del JDialog
        this.addWindowListener(new WindowAdapter() {
 
            @Override
            public void windowClosing(WindowEvent e) {
                Window w = e.getWindow();    //convierte el componente afectado en una ventana
                w.setVisible(false);         //oculta esta ventana
            }
        });
 
        //asigna un manejador de eventos para cuando la ventana pierde la visibilidad
        this.addComponentListener(new ComponentAdapter() {
 
            @Override
            public void componentHidden(ComponentEvent e) {
                Window w = (Window) e.getComponent();    //convierte el componente afectado en una ventana
                w.dispose();                             //destruye la ventana
            }
        });
 
        setResizable(false);              //no se permite redimensionar la ventana
        pack();                           //se le da el tamaño preferido
        setLocationRelativeTo(owner);     //la ventana se centra sobre el editor de texto
    }
 
    /**
     * Retorna el fuente seleccionado por el usuario. Es el producto resultante de la elección de 
     * un tipo, un estilo y un tamaño
     * 
     * @return el fuente seleccionado.
     */
    public Font getSelectedFont() {
        try {
            //retorna el fuente seleccionado en el dialogo
            return new Font(String.valueOf(listFontNames.getSelectedValue()), listFontStyles.getSelectedIndex(),
                            Integer.parseInt(String.valueOf(listFontSizes.getSelectedValue())));
        } catch (NumberFormatException nfe) {    //en caso de que ocurra una excepción
            System.err.println(nfe);
        }
        return null;    //retorna null
    }
 
    /**
     * Clase interna que extiende e implementa las clases e interfaces necesarias para 
     * atender y manejar los eventos sobre la ventana de selección de fuente.
     */
    class EventHandler extends KeyAdapter implements Comparator<String>,
                                                     ListSelectionListener {
 
        /**
         * Atiende y maneja los eventos de teclado cuando se libera una tecla.
         * 
         * @param ke evento de tecla
         */
        @Override
        public void keyReleased(KeyEvent ke) {
            //obtiene el origen del evento, y lo convierte en un campo de texto
            final JTextField eventTField = (JTextField) ke.getSource();
            final String text = eventTField.getText();    //obtiene el contenido del campo de texto
 
 
            //se averigua en que campo de texto se ah ejecutado el evento
            if (eventTField == textFieldNames) {    //si el campo de texto es textFieldNames
                //obtiene el índice del proximo elemento en la lista que coincida con el contenido del campo de texto
                int index = listFontNames.getNextMatch(text, 0, Position.Bias.Forward);
 
                if (index > -1) {    //si el índice es mayor que -1
                    /** realiza una búsqueda binaria sobre el arreglo de nombres de fuente, se utiliza
                    esta clase como comparador implementando la interface Comparator */
                    if (Arrays.binarySearch(FONT_NAMES, text, this) > -1) //si el resultado es mayor que -1
                    {
                        listFontNames.setSelectedIndex(index);    //selecciona el índice
                    }
 
                    listFontNames.ensureIndexIsVisible(index);    //hace el índice visible en la lista
                }
            } else if (eventTField == textFieldStyles) {    //si el campo de texto es textFieldStyles
                //obtiene el índice del proximo elemento en la lista que coincida con el contenido del campo de texto
                int index = listFontStyles.getNextMatch(text, 0, Position.Bias.Forward);
 
                if (index > -1) {    //si el índice es mayor que -1
                    //itera sobre los elementos en el arreglo de estilos de fuente
                    for (int i = 0 ; i < FONT_STYLES.length ; i++) {
                        //si el contenido del campo de texto es igual al elemento actual
                        if (text.equalsIgnoreCase(FONT_STYLES[i]) == true) {
                            listFontStyles.setSelectedIndex(index);    //selecciona el índice
                        }
                    }
 
                    listFontStyles.ensureIndexIsVisible(index);    //hace el índice visible en la lista
                }
            } else if (eventTField == textFieldSizes) {    //si el campo de texto es textFieldSizes
                //obtiene el índice del proximo elemento en la lista que coincida con el contenido del campo de texto
                int index = listFontSizes.getNextMatch(text, 0, Position.Bias.Forward);
 
                if (index > -1) {    //si el índice es mayor que -1
                    //itera sobre los elementos en el arreglo de tamaños de fuente
                    for (int i = 0 ; i < FONT_SIZES.length ; i++) {
                        //si el contenido del campo de texto es igual al elemento actual
                        if (text.equalsIgnoreCase(FONT_SIZES[i]) == true) {
                            listFontSizes.setSelectedIndex(index);    //selecciona el índice
                        }
                    }
 
                    listFontSizes.ensureIndexIsVisible(index);    //hace el índice visible en la lista
                }
            }
 
            //establece el fuente seleccionado en la etiqueta de muestra
            textExample.setFont(getSelectedFont());
        }
 
        /**
         * Comparación lexicográfica de dos cadenas de texto ignorando mayúsculas.
         * 
         * @param string1 una cadena de texto
         * @param string2 otra cadena de texto
         * @return true si son dos cadenas iguales, false en caso contrario 
         */
        @Override
        public int compare(String string1, String string2) {
            //compara dos cadenas de texto ignorando mayúsculas
            return string1.compareToIgnoreCase(string2);
        }
 
        /**
         * Atiende y maneja los eventos de selección en las listas de la ventana.
         * 
         * @param lse evento de selección en una lista
         */
        @Override
        public void valueChanged(ListSelectionEvent lse) {
            //averigua en que lista se ah ejecutado el evento
            if (lse.getSource() == listFontNames) {    //si la lista es listFontNames
                //establece el contenido del campo de texto textFieldNames
                textFieldNames.setText(String.valueOf(listFontNames.getSelectedValue()));
            } else if (lse.getSource() == listFontStyles) {    //si la lista es listFontStyles
                //establece el contenido del campo de texto textFieldStyles
                textFieldStyles.setText(String.valueOf(listFontStyles.getSelectedValue()));
            } else if (lse.getSource() == listFontSizes) {    //si la lista es listFontSizes
                //establece el contenido del campo de texto textFieldSizes
                textFieldSizes.setText(String.valueOf(listFontSizes.getSelectedValue()));
            }
 
            //establece el fuente seleccionado en la etiqueta de muestra
            textExample.setFont(getSelectedFont());
        }
    }
}