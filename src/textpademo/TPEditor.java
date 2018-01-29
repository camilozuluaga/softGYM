/**
 * TPEditor.java
 *
 * Ejemplo de un editor básico para documentos de texto plano utilizando la biblioteca gráfica Swing.
 * Funciona desde Java SE 5.0 en adelante.
 */
 
package textpademo;
 
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JToolBar;
import javax.swing.KeyStroke;
import javax.swing.UIManager;
import javax.swing.WindowConstants;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.text.BadLocationException;
import javax.swing.undo.UndoManager; 
 
/**
 * Clase principal donde se construye la GUI del editor.
 *
 * @author Dark[byte]
 */
public class TPEditor {
 
    private JFrame jFrame;            //instancia de JFrame (ventana principal)

    public JFrame getjFrame() {
        return jFrame;
    }
    private JMenuBar jMenuBar;        //instancia de JMenuBar (barra de menú)
    private JToolBar jToolBar;        //instancia de JToolBar (barra de herramientas)
    private JTextArea jTextArea;      //instancia de JTextArea (área de edición)
    private JLabel asunto;
    private JTextField txtAsunto;

    public JTextField getTxtAsunto() {
        return txtAsunto;
    }
    private JPopupMenu jPopupMenu;    //instancia de JPopupMenu (menú emergente)
    private JPanel statusBar;         //instancia de JPanel (barra de estado)
 
    private JCheckBoxMenuItem itemLineWrap;         //instancias de algunos items de menú que necesitan ser accesibles
    private JCheckBoxMenuItem itemShowToolBar;
    private JCheckBoxMenuItem itemFixedToolBar;
    private JCheckBoxMenuItem itemShowStatusBar;
    private JMenuItem mbItemUndo;
    private JMenuItem mbItemRedo;
    private JMenuItem mpItemUndo;
    private JMenuItem mpItemRedo;
 
    private JButton buttonUndo;    //instancias de algunos botones que necesitan ser accesibles
    private JButton buttonRedo;
 
    private JLabel sbFilePath;    //etiqueta que muestra la ubicación del archivo actual
    private JLabel sbFileSize;    //etiqueta que muestra el tamaño del archivo actual
    private JLabel sbCaretPos;    //etiqueta que muestra la posición del cursor en el área de edición
 
    private boolean hasChanged = false;    //el estado del documento actual, no modificado por defecto
    private File currentFile = null;       //el archivo actual, ninguno por defecto
 
    private final EventHandler eventHandler;          //instancia de EventHandler (la clase que maneja eventos)
    private final ActionPerformer actionPerformer;    //instancia de ActionPerformer (la clase que ejecuta acciones)
    private final UndoManager undoManager;            //instancia de UndoManager (administrador de edición)
 
    /**
     * Punto de entrada del programa.
     *
     * Instanciamos esta clase para construir la GUI y hacerla visible.
     *
     * @param args argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        //construye la GUI en el EDT (Event Dispatch Thread)
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
 
            @Override
            public void run() {
                new TPEditor().jFrame.setVisible(true);    //hace visible la GUI creada por la clase TPEditor
            }
        });
    }
 
    /**
     * Constructor de la clase.
     *
     * Se construye la GUI del editor, y se instancian clases importantes.
     */
    public TPEditor() {
        try {    //LookAndFeel nativo
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ex) {
            System.err.println(ex);
        }
 
        //construye un JFrame con título
        jFrame = new JFrame("Correo Dreamsoft - Sin Título");
        jFrame.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
 
        //asigna un manejador de eventos para el cierre del JFrame
        jFrame.addWindowListener(new WindowAdapter() {
 
            @Override
            public void windowClosing(WindowEvent we) {
                actionPerformer.actionExit();    //invoca el método actionExit()
            }
        });
 
        eventHandler = new EventHandler();              //construye una instancia de EventHandler
        actionPerformer = new ActionPerformer(this);    //construye una instancia de ActionPerformer
        undoManager = new UndoManager();                //construye una instancia de UndoManager
        undoManager.setLimit(50);                       //le asigna un límite al buffer de ediciones
 
        buildTextArea();     //construye el área de edición, es importante que esta sea la primera parte en construirse
        buildAsunto();
        buildMenuBar();      //construye la barra de menú
        buildToolBar();      //construye la barra de herramientas
        buildStatusBar();    //construye la barra de estado
        buildPopupMenu();    //construye el menú emergente
 
        jFrame.setJMenuBar(jMenuBar);                              //designa la barra de menú del JFrame
        Container c = jFrame.getContentPane();                     //obtiene el contendor principal
        c.add(jToolBar, BorderLayout.NORTH);                       //añade la barra de herramientas, orientación NORTE del contendo
        c.add(new JScrollPane(jTextArea), BorderLayout.CENTER);    //añade el area de edición en el CENTRO
        c.add(statusBar, BorderLayout.SOUTH);                      //añade la barra de estado, orientación SUR
 
        //configura el JFrame con un tamaño inicial proporcionado con respecto a la pantalla
        Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();
        jFrame.setSize(pantalla.width / 2, pantalla.height / 2);
 
        //centra el JFrame en pantalla
        jFrame.setLocationRelativeTo(null);
    }
 private void buildAsunto() {
  asunto=new JLabel();
  asunto.setText("Asunto");
  txtAsunto=new JTextField();
  txtAsunto.setText("");
 }
 
    /**
     * Construye el área de edición.
     */
    private void buildTextArea() {
        jTextArea = new JTextArea();    //construye un JTextArea
 
        //se configura por defecto para que se ajusten las líneas al tamaño del área de texto ...
        jTextArea.setLineWrap(true);
        //... y que se respete la integridad de las palaras en el ajuste
        jTextArea.setWrapStyleWord(true);
 
        //asigna el manejador de eventos para el cursor
        jTextArea.addCaretListener(eventHandler);
        //asigna el manejador de eventos para el ratón
        jTextArea.addMouseListener(eventHandler);
        //asigna el manejador de eventos para registrar los cambios sobre el documento
        jTextArea.getDocument().addUndoableEditListener(eventHandler);
 
        //remueve las posibles combinaciones de teclas asociadas por defecto con el JTextArea
        jTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK), "none");    //remueve CTRL + X ("Cortar")
        jTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK), "none");    //remueve CTRL + C ("Copiar")
        jTextArea.getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK), "none");    //remueve CTRL + V ("Pegar")
    }
 
    /**
     * Construye la barra de menú.
     */
    private void buildMenuBar() {
        jMenuBar = new JMenuBar();    //construye un JMenuBar
 
        //construye el menú "Archivo", a continuación se construyen los items para este menú
        JMenu menuFile = new JMenu("Archivo");
 
        //construye el item "Nuevo"
        JMenuItem itemNew = new JMenuItem("Nuevo");
        //le asigna una conbinación de teclas
        itemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, KeyEvent.CTRL_MASK));
        //le asigna un nombre de comando
        itemNew.setActionCommand("cmd_new");
 
        JMenuItem itemOpen = new JMenuItem("Abrir");
        itemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_MASK));
        itemOpen.setActionCommand("cmd_open");
 
        JMenuItem itemSave = new JMenuItem("Guardar");
        itemSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, KeyEvent.CTRL_MASK));
        itemSave.setActionCommand("cmd_save");
 
        JMenuItem itemSaveAs = new JMenuItem("Guardar como...");
        itemSaveAs.setActionCommand("cmd_saveas");
        itemSaveAs.addActionListener(eventHandler);
 
        JMenuItem itemPrint = new JMenuItem("Imprimir");
        itemPrint.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_MASK));
        itemPrint.setActionCommand("cmd_print");
 
        JMenuItem itemExit = new JMenuItem("Salir");
        itemExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, KeyEvent.CTRL_MASK));
        itemExit.setActionCommand("cmd_exit");
 
        menuFile.add(itemNew);    //se añaden los items al menú "Archivo"
        menuFile.add(itemOpen);
        menuFile.add(itemSave);
        menuFile.add(itemSaveAs);
        menuFile.addSeparator();
        menuFile.add(itemPrint);
        menuFile.addSeparator();
        menuFile.add(itemExit);
        //----------------------------------------------
 
        //construye el menú "Editar", a continuación se construyen los items para este menú
        JMenu menuEdit = new JMenu("Editar");
 
        mbItemUndo = new JMenuItem("Deshacer");
        mbItemUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
        mbItemUndo.setEnabled(false);
        mbItemUndo.setActionCommand("cmd_undo");
 
        mbItemRedo = new JMenuItem("Rehacer");
        mbItemRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
        mbItemRedo.setEnabled(false);
        mbItemRedo.setActionCommand("cmd_redo");
 
        JMenuItem itemCut = new JMenuItem("Cortar");
        itemCut.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
        itemCut.setActionCommand("cmd_cut");
 
        JMenuItem itemCopy = new JMenuItem("Copiar");
        itemCopy.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, ActionEvent.CTRL_MASK));
        itemCopy.setActionCommand("cmd_copy");
 
        JMenuItem itemPaste = new JMenuItem("Pegar");
        itemPaste.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_V, ActionEvent.CTRL_MASK));
        itemPaste.setActionCommand("cmd_paste");
 
        JMenuItem itemSearch = new JMenuItem("Buscar");
        itemSearch.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F, ActionEvent.CTRL_MASK));
        itemSearch.setActionCommand("cmd_search");
 
        JMenuItem itemSearchNext = new JMenuItem("Buscar siguiente");
        itemSearchNext.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
        itemSearchNext.setActionCommand("cmd_searchnext");
 
        JMenuItem itemGotoLine = new JMenuItem("Ir a la línea...");
        itemGotoLine.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        itemGotoLine.setActionCommand("cmd_gotoline");
 
        JMenuItem itemSelectAll = new JMenuItem("Seleccionar todo");
        itemSelectAll.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
        itemSelectAll.setActionCommand("cmd_selectall");
 
        menuEdit.add(mbItemUndo);    //se añaden los items al menú "Editar"
        menuEdit.add(mbItemRedo);
        menuEdit.addSeparator();     //añade separadores entre algunos items
        menuEdit.add(itemCut);
        menuEdit.add(itemCopy);
        menuEdit.add(itemPaste);
        menuEdit.addSeparator();
        menuEdit.add(itemSearch);
        menuEdit.add(itemSearchNext);
        menuEdit.add(itemGotoLine);
        menuEdit.addSeparator();
        menuEdit.add(itemSelectAll);
        //----------------------------------------------
 
        //construye el menú "Opciones", a continuación se construyen los items para este menú
        JMenu menuTools = new JMenu("Opciones");
 
        itemLineWrap = new JCheckBoxMenuItem("Ajuste de línea");
        itemLineWrap.setSelected(true);
        itemLineWrap.setActionCommand("cmd_linewrap");
 
        itemShowToolBar = new JCheckBoxMenuItem("Ver barra de herramientas");
        itemShowToolBar.setSelected(true);
        itemShowToolBar.setActionCommand("cmd_showtoolbar");
 
        itemFixedToolBar = new JCheckBoxMenuItem("Fijar barra de herramientas");
        itemFixedToolBar.setSelected(true);
        itemFixedToolBar.setActionCommand("cmd_fixedtoolbar");
 
        itemShowStatusBar = new JCheckBoxMenuItem("Ver barra de estado");
        itemShowStatusBar.setSelected(true);
        itemShowStatusBar.setActionCommand("cmd_showstatusbar");
 
        JMenuItem itemFont = new JMenuItem("Fuente de letra");
        itemFont.setActionCommand("cmd_font");
 
        JMenuItem itemFontColor = new JMenuItem("Color de letra");
        itemFontColor.setActionCommand("cmd_fontcolor");
 
        JMenuItem itemBackgroundColor = new JMenuItem("Color de fondo");
        itemBackgroundColor.setActionCommand("cmd_backgroundcolor");
 
        menuTools.add(itemLineWrap);    //se añaden los items al menú "Opciones"
        menuTools.add(itemShowToolBar);
        menuTools.add(itemFixedToolBar);
        menuTools.add(itemShowStatusBar);
        menuTools.addSeparator();
        menuTools.add(itemFont);
        menuTools.add(itemFontColor);
        menuTools.add(itemBackgroundColor);
 
        //construye el menú "Ayuda", a continuación se construye el único item para este menú
        JMenu menuHelp = new JMenu("Ayuda");
 
        JMenuItem itemAbout = new JMenuItem("Acerca de");
        itemAbout.setActionCommand("cmd_about");
 
        menuHelp.add(itemAbout);     //se añade el único item al menú "Ayuda"
        //----------------------------------------------
 
        jMenuBar.add(menuFile);    //se añaden los menúes construidos a la barra de menú
        jMenuBar.add(Box.createHorizontalStrut(5));    //añade espacios entre cada menú
        jMenuBar.add(menuEdit);
        jMenuBar.add(Box.createHorizontalStrut(5));
        jMenuBar.add(menuTools);
        jMenuBar.add(Box.createHorizontalStrut(5));
        jMenuBar.add(menuHelp);
 
        /** itera sobre todos los componentes de la barra de menú, se les asigna el mismo
        manejador de eventos a todos excepto a los separadores */
        for (Component c1 : jMenuBar.getComponents()) {
            //si el componente es un menú
            if (c1.getClass().equals(javax.swing.JMenu.class)) {
                //itera sobre los componentes del menú
                for (Component c2 : ((JMenu) c1).getMenuComponents()) {
                    //si el componente no es un separador
                    if (!c2.getClass().equals(javax.swing.JPopupMenu.Separator.class)) {
                        ((JMenuItem) c2).addActionListener(eventHandler);
                    }
                }
            }
        }
    }
 
    /**
     * Construye la barra de herramientas.
     */
    private void buildToolBar() {
        jToolBar = new JToolBar();       //construye un JToolBar
        jToolBar.setFloatable(false);    //se configura por defecto como barra fija
 
        //construye el botón "Nuevo"
        JButton buttonNew = new JButton();
        //le asigna una etiqueta flotante
        buttonNew.setToolTipText("Nuevo");
        //le asigna una imagen ubicada en los recursos del proyecto
        buttonNew.setIcon(new ImageIcon(getClass().getResource("/res/tp_new.png")));
        //le asigna un nombre de comando
        buttonNew.setActionCommand("cmd_new");
 
        JButton buttonOpen = new JButton();
        buttonOpen.setToolTipText("Abrir");
        buttonOpen.setIcon(new ImageIcon(getClass().getResource("/res/tp_open.png")));
        buttonOpen.setActionCommand("cmd_open");
 
        JButton buttonSave = new JButton();
        buttonSave.setToolTipText("Guardar");
        buttonSave.setIcon(new ImageIcon(getClass().getResource("/res/tp_save.png")));
        buttonSave.setActionCommand("cmd_save");
 
        JButton buttonSaveAs = new JButton();
        buttonSaveAs.setToolTipText("Guardar como...");
        buttonSaveAs.setIcon(new ImageIcon(getClass().getResource("/res/tp_saveas.png")));
        buttonSaveAs.setActionCommand("cmd_saveas");
 
        JButton buttonPrint = new JButton();
        buttonPrint.setToolTipText("Imprimir");
        buttonPrint.setIcon(new ImageIcon(getClass().getResource("/res/tp_print.png")));
        buttonPrint.setActionCommand("cmd_print");
 
        //construye el botón "Enviar"
        JButton buttonSend = new JButton();
        //le asigna una etiqueta flotante
        buttonSend.setToolTipText("Enviar");
        //le asigna una imagen ubicada en los recursos del proyecto
        buttonSend.setIcon(new ImageIcon(getClass().getResource("/res/tp_send.png")));
        //le asigna un nombre de comando
        buttonSend.setActionCommand("cmd_send");
        
        buttonUndo = new JButton();
        buttonUndo.setEnabled(false);
        buttonUndo.setToolTipText("Deshacer");
        buttonUndo.setIcon(new ImageIcon(getClass().getResource("/res/tp_undo.png")));
        buttonUndo.setActionCommand("cmd_undo");
 
        buttonRedo = new JButton();
        buttonRedo.setEnabled(false);
        buttonRedo.setToolTipText("Rehacer");
        buttonRedo.setIcon(new ImageIcon(getClass().getResource("/res/tp_redo.png")));
        buttonRedo.setActionCommand("cmd_redo");
 
        JButton buttonCut = new JButton();
        buttonCut.setToolTipText("Cortar");
        buttonCut.setIcon(new ImageIcon(getClass().getResource("/res/tp_cut.png")));
        buttonCut.setActionCommand("cmd_cut");
 
        JButton buttonCopy = new JButton();
        buttonCopy.setToolTipText("Copiar");
        buttonCopy.setIcon(new ImageIcon(getClass().getResource("/res/tp_copy.png")));
        buttonCopy.setActionCommand("cmd_copy");
 
        JButton buttonPaste = new JButton();
        buttonPaste.setToolTipText("Pegar");
        buttonPaste.setIcon(new ImageIcon(getClass().getResource("/res/tp_paste.png")));
        buttonPaste.setActionCommand("cmd_paste");
        
        JButton buttonClosed = new JButton();
        buttonClosed.setToolTipText("Cancelar");
        buttonClosed.setIcon(new ImageIcon(getClass().getResource("/res/closed.png")));
        buttonClosed.setActionCommand("cmd_exit");
 
        jToolBar.add(buttonNew);    //se añaden los botones construidos a la barra de herramientas
        jToolBar.add(buttonOpen);
        jToolBar.add(buttonSave);
        jToolBar.add(buttonSaveAs);
        jToolBar.addSeparator();    //añade separadores entre algunos botones
        jToolBar.add(buttonPrint);
        jToolBar.addSeparator();
        jToolBar.add(buttonUndo);
        jToolBar.add(buttonRedo);
        jToolBar.addSeparator();
        jToolBar.add(buttonCut);
        jToolBar.add(buttonCopy);
        jToolBar.add(buttonPaste);
        jToolBar.addSeparator(); 
        jToolBar.add(buttonSend);
        jToolBar.addSeparator(); 
        jToolBar.add(buttonClosed);
        jToolBar.addSeparator(); 
        jToolBar.add(asunto);
        jToolBar.add(txtAsunto);
 
        /** itera sobre todos los componentes de la barra de herramientas, se les asigna el
        mismo margen y el mismo manejador de eventos unicamente a los botones */
        for (Component c : jToolBar.getComponents()) {
            //si el componente es un botón
            if (c.getClass().equals(javax.swing.JButton.class)) {
                JButton jb = (JButton) c;
                jb.setMargin(new Insets(0, 0, 0, 0));
                jb.addActionListener(eventHandler);
            }
        }
    }
 
    /**
     * Construye la barra de estado.
     */
    private void buildStatusBar() {
        statusBar = new JPanel();    //construye un JPanel
        //se configura con un BoxLayout
        statusBar.setLayout(new BoxLayout(statusBar, BoxLayout.LINE_AXIS));
        //le añade un borde compuesto
        statusBar.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLoweredBevelBorder(),
                BorderFactory.createEmptyBorder(5, 5, 5, 5)));
 
        //construye la etiqueta para mostrar la ubicación del archivo actual
        sbFilePath = new JLabel("...");
        //construye la etiqueta para mostrar el tamaño del archivo actual
        sbFileSize = new JLabel("");
        //construye la etiqueta para mostrar la posición del cursor en el documento actual
        sbCaretPos = new JLabel("...");
 
        /** se añaden las etiquetas construidas al JPanel, el resultado es un panel
        similar a una barra de estado */
        statusBar.add(sbFilePath);
        statusBar.add(Box.createRigidArea(new Dimension(10, 0)));
        statusBar.add(sbFileSize);
        statusBar.add(Box.createRigidArea(new Dimension(10, 0)));
        statusBar.add(Box.createHorizontalGlue());
        statusBar.add(sbCaretPos);
    }
 
    /**
     * Construye el menú emergente.
     */
    private void buildPopupMenu() {
        jPopupMenu = new JPopupMenu();    //construye un JPopupMenu
 
        //construye el item "Deshacer"
        mpItemUndo = new JMenuItem("Deshacer");
        //se configura desactivado por defecto
        mpItemUndo.setEnabled(false);
        //le asigna un nombre de comando
        mpItemUndo.setActionCommand("cmd_undo");
 
        mpItemRedo = new JMenuItem("Rehacer");
        mpItemRedo.setEnabled(false);
        mpItemRedo.setActionCommand("cmd_redo");
 
        JMenuItem itemCut = new JMenuItem("Cortar");
        itemCut.setActionCommand("cmd_cut");
 
        JMenuItem itemCopy = new JMenuItem("Copiar");
        itemCopy.setActionCommand("cmd_copy");
 
        JMenuItem itemPaste = new JMenuItem("Pegar");
        itemPaste.setActionCommand("cmd_paste");
 
        JMenuItem itemGoto = new JMenuItem("Ir a...");
        itemGoto.setActionCommand("cmd_gotoline");
 
        JMenuItem itemSearch = new JMenuItem("Buscar");
        itemSearch.setActionCommand("cmd_search");
 
        JMenuItem itemSearchNext = new JMenuItem("Buscar siguiente");
        itemSearchNext.setActionCommand("cmd_searchnext");
 
        JMenuItem itemSelectAll = new JMenuItem("Seleccionar todo");
        itemSelectAll.setActionCommand("cmd_selectall");
 
        jPopupMenu.add(mpItemUndo);    //se añaden los items al menú emergente
        jPopupMenu.add(mpItemRedo);
        jPopupMenu.addSeparator();     //añade separadores entre algunos items
        jPopupMenu.add(itemCut);
        jPopupMenu.add(itemCopy);
        jPopupMenu.add(itemPaste);
        jPopupMenu.addSeparator();
        jPopupMenu.add(itemGoto);
        jPopupMenu.add(itemSearch);
        jPopupMenu.add(itemSearchNext);
        jPopupMenu.addSeparator();
        jPopupMenu.add(itemSelectAll);
 
        /** itera sobre todos los componentes del menú emergente, se les asigna el mismo
        manejador de eventos a todos excepto a los separadores */
        for (Component c : jPopupMenu.getComponents()) {
            //si el componente es un item
            if (c.getClass().equals(javax.swing.JMenuItem.class)) {
                ((JMenuItem) c).addActionListener(eventHandler);
            }
        }
    }
 
    /**
     * Hace visible el menú emergente.
     *
     * @param me evento del ratón
     */
    private void showPopupMenu(MouseEvent me) {
        if (me.isPopupTrigger() == true) {    //si el evento es el desencadenador de menú emergente
            //hace visible el menú emergente en las coordenadas actuales del ratón
            jPopupMenu.show(me.getComponent(), me.getX(), me.getY());
        }
    }
 
    /**
     * Actualiza el estado de las opciones "Deshacer" y "Rehacer".
     */
    void updateControls() {
        //averigua si se pueden deshacer los cambios en el documento actual
        boolean canUndo = undoManager.canUndo();
        //averigua si se pueden rehacer los cambios en el documento actual
        boolean canRedo = undoManager.canRedo();
 
        //activa o desactiva las opciones en la barra de menú
        mbItemUndo.setEnabled(canUndo);
        mbItemRedo.setEnabled(canRedo);
 
        //activa o desactiva las opciones en la barra de herramientas
        buttonUndo.setEnabled(canUndo);
        buttonRedo.setEnabled(canRedo);
 
        //activa o desactiva las opciones en el menú emergente
        mpItemUndo.setEnabled(canUndo);
        mpItemRedo.setEnabled(canRedo);
    }
 
    /**
     * Retorna la instancia de EventHandler, la clase interna que maneja eventos.
     *
     * @return el manejador de eventos.
     */
    EventHandler getEventHandler() {
        return eventHandler;
    }
 
    /**
     * Retorna la instancia de UndoManager, la cual administra las ediciones sobre
     * el documento en el área de texto.
     *
     * @return el administrador de edición.
     */
    UndoManager getUndoManager() {
        return undoManager;
    }
 
    /**
     * Retorna el estado del documento actual.
     *
     * @return true si ah sido modificado, false en caso contrario
     */
    boolean documentHasChanged() {
        return hasChanged;
    }
 
    /**
     * Establece el estado del documento actual.
     *
     * @param hasChanged true si ah sido modificado, false en caso contrario
     */
    void setDocumentChanged(boolean hasChanged) {
        this.hasChanged = hasChanged;
    }
 
    /**
     * Retorna la instancia de JTextArea, el área de edición.
     *
     * @return retorna el área de edición.
     */
    JTextArea getJTextArea() {
        return jTextArea;
    }

    /**
     * Retorna la instancia de JFrame, la ventana principal del editor.
     *
     * @return la ventana principal del editor.
     */
    JFrame getJFrame() {
        return jFrame;
    }
 
    /**
     * Retorna la instancia de File, el archivo actual.
     *
     * @return el archivo actual
     */
    File getCurrentFile() {
        return currentFile;
    }
 
    /**
     * Establece el archivo actual.
     *
     * @param currentFile el archivo actual
     */
    void setCurrentFile(File currentFile) {
        this.currentFile = currentFile;
    }
 
    /**
     * Retorna la instancia de la etiqueta sbFilePath, donde se muestra la ubicación
     * del archivo actual.
     *
     * @return la instancia de la etiqueta sbFilePath
     */
    JLabel getJLabelFilePath() {
        return sbFilePath;
    }
 
    /**
     * Retorna la instancia de la etiqueta sbFileSize, donde se muestra el tamaño
     * del archivo actual
     *
     * @return la instancia de la etiqueta sbFileSize
     */
    JLabel getJLabelFileSize() {
        return sbFileSize;
    }
 
    /**
     * Clase interna que extiende e implementa las clases e interfaces necesarias para
     * atender y manejar los eventos sobre la GUI principal del editor.
     */
    class EventHandler extends MouseAdapter implements ActionListener,
                                                       CaretListener,
                                                       UndoableEditListener {
 
        /**
         * Atiende y maneja los eventos de acción.
         *
         * @param ae evento de acción
         */
        @Override
        public void actionPerformed(ActionEvent ae) {
            String ac = ae.getActionCommand();    //se obtiene el nombre del comando ejecutado
 
            if (ac.equals("cmd_new") == true) {    //opción seleccionada: "Nuevo"
                actionPerformer.actionNew();
            } else if (ac.equals("cmd_open") == true) {    //opción seleccionada: "Abrir"
                actionPerformer.actionOpen();
            }else if (ac.equals("cmd_send") == true) {    //opción seleccionada: "Guardar"
                actionPerformer.actionSend();
            } else if (ac.equals("cmd_save") == true) {    //opción seleccionada: "Guardar"
                actionPerformer.actionSave();
            } else if (ac.equals("cmd_saveas") == true) {    //opción seleccionada: "Guardar como"
                actionPerformer.actionSaveAs();
            } else if (ac.equals("cmd_print") == true) {    //opción seleccionada: "Imprimir"
                actionPerformer.actionPrint();
            } else if (ac.equals("cmd_exit") == true) {    //opción seleccionada: "Salir"
                actionPerformer.actionExit();
            } else if (ac.equals("cmd_undo") == true) {    //opción seleccionada: "Deshacer"
                actionPerformer.actionUndo();
            } else if (ac.equals("cmd_redo") == true) {    //opción seleccionada: "Rehacer"
                actionPerformer.actionRedo();
            } else if (ac.equals("cmd_cut") == true) {    //opción seleccionada: "Cortar"
                //corta el texto seleccionado en el documento
                jTextArea.cut();
            } else if (ac.equals("cmd_copy") == true) {    //opción seleccionada: "Copiar"
                //copia el texto seleccionado en el documento
                jTextArea.copy();
            } else if (ac.equals("cmd_paste") == true) {    //opción seleccionada: "Pegar"
                //pega en el documento el texto del portapapeles
                jTextArea.paste();
            } else if (ac.equals("cmd_gotoline") == true) {    //opción seleccionada: "Ir a la línea..."
                actionPerformer.actionGoToLine();
            } else if (ac.equals("cmd_search") == true) {    //opción seleccionada: "Buscar"
                actionPerformer.actionSearch();
            } else if (ac.equals("cmd_searchnext") == true) {    //opción seleccionada: "Buscar siguiente"
                actionPerformer.actionSearchNext();
            } else if (ac.equals("cmd_selectall") == true) {    //opción seleccionada: "Seleccionar todo"
                jTextArea.selectAll();
            } else if (ac.equals("cmd_linewrap") == true) {    //opción seleccionada: "Ajuste de línea"
                //si esta propiedad esta activada se desactiva, o lo inverso
                jTextArea.setLineWrap(!jTextArea.getLineWrap());
                jTextArea.setWrapStyleWord(!jTextArea.getWrapStyleWord());
            } else if (ac.equals("cmd_showtoolbar") == true) {    //opción seleccionada: "Ver barra de herramientas"
                //si la barra de herramientas esta visible se oculta, o lo inverso
                jToolBar.setVisible(!jToolBar.isVisible());
            } else if (ac.equals("cmd_fixedtoolbar") == true) {    //opción seleccionada: "Fijar barra de herramientas"
                //si esta propiedad esta activada se desactiva, o lo inverso
                jToolBar.setFloatable(!jToolBar.isFloatable());
            } else if (ac.equals("cmd_showstatusbar") == true) {    //opción seleccionada: "Ver barra de estado"
                //si la barra de estado esta visible se oculta, o lo inverso
                statusBar.setVisible(!statusBar.isVisible());
            } else if (ac.equals("cmd_font") == true) {    //opción seleccionada: "Fuente de letra"
                actionPerformer.actionSelectFont();
            } else if (ac.equals("cmd_fontcolor") == true) {    //opción seleccionada: "Color de letra"
                actionPerformer.actionSelectFontColor();
            } else if (ac.equals("cmd_backgroundcolor") == true) {    //opción seleccionada: "Color de fondo"
                actionPerformer.actionSelectBackgroundColor();
            } else if (ac.equals("cmd_about") == true) {    //opción seleccionada: "Acerca de"
                //presenta un dialogo modal con alguna informacion
                JOptionPane.showMessageDialog(jFrame,
                                              "TextPad Demo por Dreamsoft",
                                              "Acerca de",
                                              JOptionPane.INFORMATION_MESSAGE);
            }
        }
 
        /**
         * Atiende y maneja los eventos del cursor.
         *
         * @param ce evento del cursor
         */
        @Override
        public void caretUpdate(CaretEvent e) {
            final int caretPos;  //valor de la posición del cursor sin inicializar
            int y = 1;           //valor de la línea inicialmente en 1
            int x = 1;           //valor de la columna inicialmente en 1
 
            try {
                //obtiene la posición del cursor con respecto al inicio del JTextArea (área de edición)
                caretPos = jTextArea.getCaretPosition();
                //sabiendo lo anterior se obtiene el valor de la línea actual (se cuenta desde 0)
                y = jTextArea.getLineOfOffset(caretPos);
 
                /** a la posición del cursor se le resta la posición del inicio de la línea para
                determinar el valor de la columna actual */
                x = caretPos - jTextArea.getLineStartOffset(y);
 
                //al valor de la línea actual se le suma 1 porque estas comienzan contándose desde 0
                y += 1;
            } catch (BadLocationException ex) {    //en caso de que ocurra una excepción
                System.err.println(ex);
            }
 
            /** muestra la información recolectada en la etiqueta sbCaretPos de la
            barra de estado, también se incluye el número total de lineas */
            sbCaretPos.setText("Líneas: " + jTextArea.getLineCount() + " - Y: " + y + " - X: " + x);
        }
 
        /**
         * Atiende y maneja los eventos sobre el documento en el área de edición.
         *
         * @param uee evento de edición
         */
        @Override
        public void undoableEditHappened(UndoableEditEvent uee) {
            /** el cambio realizado en el área de edición se guarda en el buffer
            del administrador de edición */
            undoManager.addEdit(uee.getEdit());
            updateControls();    //actualiza el estado de las opciones "Deshacer" y "Rehacer"
 
            hasChanged = true;
        }
 
        /**
         * Atiende y maneja los eventos sobre el ratón cuando este es presionado.
         *
         * @param me evento del ratón
         */
        @Override
        public void mousePressed(MouseEvent me) {
            showPopupMenu(me);
        }
 
        /**
         * Atiende y maneja los eventos sobre el ratón cuando este es liberado.
         *
         * @param me evento del ratón
         */
        @Override
        public void mouseReleased(MouseEvent me) {
            showPopupMenu(me);
        }
    }
}