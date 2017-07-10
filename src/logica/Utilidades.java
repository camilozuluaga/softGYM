/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import VisorDatos.ListaCampos;
import desarrollo.CierreCaja;
import static desarrollo.Frame.escritorio;
import desarrollo.ListadoSocios;
import desarrollo.RegistrarPagoMembresia;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sql.rowset.CachedRowSet;
import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import net.lingala.zip4j.model.ZipParameters;
import net.lingala.zip4j.util.Zip4jConstants;
import net.sf.jcarrierpigeon.WindowPosition;
import net.sf.jtelegraph.Telegraph;
import net.sf.jtelegraph.TelegraphQueue;
import net.sf.jtelegraph.TelegraphType;

/**
 *
 * @author gaso
 */
public class Utilidades {

    String texto;
    int tamano;
    String borraUno;
    private DB db = new DB();
    private Clip sonido;
    private String ruta = "/sonido/";

    public void buscarIdentificacion(String identificacion, String campo, String nombreTabla, int condicion, JTextField txtIdentificacion) {
        CachedRowSet data;
        String querySQL = String.format("SELECT " + campo + " ,identificacion FROM " + nombreTabla + " WHERE identificacion = '%s' ", identificacion);
        if (!identificacion.isEmpty()) {
            String sql = String.format("AND id <> %s;", condicion);
            querySQL = querySQL + sql;
            System.out.println(querySQL);
        }
        System.out.println(querySQL);
        data = db.sqlDatos(querySQL);
        try {
            if (data.next()) {
                if (data.getBoolean(campo) == true) {
                    if (!data.getString("identificacion").isEmpty()) {
                        Telegraph tele = new Telegraph("Identificacion Registrada", "El numero de Identificacion ya esta Registrado en el Sistema", TelegraphType.NOTIFICATION_WARNING, WindowPosition.TOPRIGHT, 5000);
                        TelegraphQueue q = new TelegraphQueue();
                        q.add(tele);
                        txtIdentificacion.setText("");
                        txtIdentificacion.requestFocus();
                    }
                } else {
                    Telegraph tele = new Telegraph("Precaucion", "El Usuario se encuentra DESACTIVADO", TelegraphType.NOTIFICATION_WARNING, WindowPosition.TOPRIGHT, 5000);
                    TelegraphQueue q = new TelegraphQueue();
                    q.add(tele);
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarPagoMembresia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buscarIdentificacion(String identificacion, int condicion, JTextField txtIdentificacion) {
        CachedRowSet data;
        String querySQL = String.format("SELECT identificacion FROM usuario_sistema WHERE identificacion = '%s' AND id <> %s;", identificacion, condicion);
        data = db.sqlDatos(querySQL);
        try {
            if (data.next()) {
                if (!data.getString("identificacion").isEmpty()) {
                    Telegraph tele = new Telegraph("Identificacion Registrada", "El numero de Identificacion ya esta Registrado en el Sistema", TelegraphType.NOTIFICATION_WARNING, WindowPosition.TOPRIGHT, 9000);
                    TelegraphQueue q = new TelegraphQueue();
                    q.add(tele);
                    txtIdentificacion.setText("");
                    txtIdentificacion.requestFocus();
                }

            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarPagoMembresia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean socioActivo(int idSocio) {

        boolean activo = true;
        CachedRowSet data;
        String querySQL = String.format("SELECT activo FROM socio WHERE id = '%s';", idSocio);
        data = db.sqlDatos(querySQL);
        try {
            if (data.next()) {
                activo = data.getBoolean("activo");
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarPagoMembresia.class.getName()).log(Level.SEVERE, null, ex);
        }

        return activo;
    }

    public static JTable llenarTabla(CachedRowSet crs, DefaultTableModel modelo, JTable tbl) {
        try {
            ResultSetMetaData rsmd = crs.getMetaData();
            int numCols = rsmd.getColumnCount();
            modelo.setRowCount(0);
            Object[] datos = new Object[numCols];
            while (crs.next()) {
                for (int i = 0; i < numCols; i++) {
                    datos[i] = crs.getObject(i + 1);
                }
                modelo.addRow(datos);
            }
            tbl.setModel(modelo);
            crs.close();

            return tbl;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new JTable();
        }
    }

    public void validarCampoNumericos(String texto, JTextField cajaDeTexto) {
        texto = cajaDeTexto.getText();
        tamano = texto.length();
        if (!cajaDeTexto.getText().matches("[0-9]*")) {
            borraUno = cajaDeTexto.getText().substring(0, texto.length() - 1);
            cajaDeTexto.setText(borraUno);
            Telegraph tele = new Telegraph("Informacion", "Este Campo solo debe contener numeros", TelegraphType.NOTIFICATION_INFO, WindowPosition.TOPRIGHT, 2000);
            TelegraphQueue q = new TelegraphQueue();
            q.add(tele);
        }
        if (tamano > 10) {
            borraUno = cajaDeTexto.getText().substring(0, texto.length() - 1);
            cajaDeTexto.setText(borraUno);

        }
    }

    public void validarCampoNumericos(String texto, JTextField cajaDeTexto, int longitud) {
        texto = cajaDeTexto.getText();
        tamano = texto.length();
        if (!cajaDeTexto.getText().matches("[0-9]*")) {
            borraUno = cajaDeTexto.getText().substring(0, texto.length() - 1);
            cajaDeTexto.setText(borraUno);
            Telegraph tele = new Telegraph("Informacion", "Este Campo solo debe contener numeros", TelegraphType.NOTIFICATION_INFO, WindowPosition.TOPRIGHT, 2000);
            TelegraphQueue q = new TelegraphQueue();
            q.add(tele);
        }
        if (tamano > longitud) {
            borraUno = cajaDeTexto.getText().substring(0, texto.length() - 1);
            cajaDeTexto.setText(borraUno);

        }
    }

    public void validarCamposTexto(String texto, JTextField cajaDeTexto) {
        texto = cajaDeTexto.getText();
        tamano = texto.length();
        if (!cajaDeTexto.getText().matches("[/s [á-ú]-[Ñ]-[a-z]-[A-Z]']*")) {
            Telegraph tele = new Telegraph("Informacion", "Este Campos solo debe contener letras", TelegraphType.NOTIFICATION_INFO, WindowPosition.TOPRIGHT, 2000);
            TelegraphQueue q = new TelegraphQueue();
            q.add(tele);
            borraUno = cajaDeTexto.getText().substring(0, texto.length() - 1);
            cajaDeTexto.setText(borraUno);
        }
        if (tamano > 30) {
            borraUno = cajaDeTexto.getText().substring(0, texto.length() - 1);
            cajaDeTexto.setText(borraUno);

        }
    }

    public void llamarMensaje(String nombreCampo) {
        Telegraph tele = new Telegraph("Campos Requeridos", "El campo " + nombreCampo + " es requerido ", TelegraphType.NOTIFICATION_INFO, WindowPosition.TOPRIGHT, 2000);
        TelegraphQueue q = new TelegraphQueue();
        q.add(tele);
    }

    public void llamarMensaje() {
        Telegraph tele = new Telegraph("Campos Requeridos", "Debe Seleccionar Algun campo", TelegraphType.NOTIFICATION_INFO, WindowPosition.TOPRIGHT, 2000);
        TelegraphQueue q = new TelegraphQueue();
        q.add(tele);
    }

    public void botonTransparente(JButton boton) {
        boton.setOpaque(false);
        boton.setContentAreaFilled(false);
        boton.setBorderPainted(false);
    }

    public static String formatearFecha(Date fecha, String patronFecha) throws ParseException {
        Date fechaConvertida = null;
        SimpleDateFormat formato = new SimpleDateFormat(patronFecha);
        String formateado = formato.format(fecha);

        return formateado;
    }

    public static String castFecha(Date fecha, String patronFecha) {
        String formateado = "";
        try {
            SimpleDateFormat formato = new SimpleDateFormat(patronFecha);
            formateado = formato.format(fecha);
        } catch (Exception e) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.WARNING, "No se logro convertir la Fecha");
            castFecha(new Date(), "yyyy-MM-dd");
        }
        return formateado;
    }

    public static void centrarInternalFrame(JInternalFrame nombreInternal) {
        Dimension desktopSize = escritorio.getSize(); // se obtiene tamaÃ±o del escritorio
        Dimension internal = nombreInternal.getSize(); //se obtiene tamaÃ±o del internal frame
        int width = (desktopSize.width - internal.width) / 2; // ancho dividido 2
        int height = (desktopSize.height - internal.height) / 2; //alto dividido 2
        nombreInternal.setLocation(width, height);// se posiciona
        nombreInternal.show(); // se muestra
    }

    public String fecha_apertura() {
        DB db = new DB();
        CachedRowSet data;
        String fecha_apertura = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        Date fecha = null;
        try {
            String querySQL = "SELECT fecha_apertura FROM caja WHERE estado = true";
            data = db.sqlDatos(querySQL);
            if (data.next()) {
                fecha = data.getDate("fecha_apertura");
                fecha_apertura = format.format(fecha);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CierreCaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fecha_apertura;
    }

    public int cajaActual() {
        DB db1 = new DB();
        CachedRowSet data;
        int caja = 0;
        try {
            String querySQL = "SELECT max(id) as caja FROM caja";
            data = db1.sqlDatos(querySQL);
            if (data.next()) {
                caja = data.getInt("caja");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CierreCaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caja;
    }

    public void createBackupBD(String tipo, String ruta, boolean notificar) {
        String nombrearchivo = "gym_" + tipo + "_" + castFecha(new Date(), "ddMMYYYYHms");
        String s = System.getProperty("file.separator");
        String archivoBD = ruta + s + nombrearchivo + ".gs";
        String archivoZIP = ruta + s + nombrearchivo + ".zip";
        try {
            String databaseName = "gimnasio";
            String usuario = Conexion.getConexion().getMetaData().getUserName();
            String pass = "admin";

            String  postgreSQLPatch= "C:/Program Files (x86)/PostgreSQL/9.1/bin/pg_dump.exe";

            CachedRowSet data;
            data = db.sqlDatos("Select REPLACE(setting,'data','bin/pg_dump.exe') as ruta from pg_settings where name = 'data_directory' LIMIT 1");
            System.out.println("El valor data: " + data);
            while (data.next()) {
                postgreSQLPatch = data.getString(1);
                System.out.println("adentro:  " + postgreSQLPatch);
            }
            System.out.println("afuera:  " + postgreSQLPatch);
            
           // postgreSQLPatch= "C:/Program Files (x86)/PostgreSQL/9.1/bin/pg_dump.exe";
            if (postgreSQLPatch.isEmpty()) {
                msj.show("Error Generando Backup", "No se detecto ruta de Instalacion DBMS", TelegraphType.DOCUMENT_WARNING);
                return;
            }

            try {
                Runtime r = Runtime.getRuntime();
                Process p;
                ProcessBuilder pb;

                r = Runtime.getRuntime();

                System.out.println("postgreSQLPatch = " + postgreSQLPatch);

                pb = new ProcessBuilder(postgreSQLPatch, "-i", "-h", "localhost", "-p", "5432", "-F", "c", "-b", "-v", "-f", archivoBD, "-U", usuario, databaseName);

                pb.environment().put("PGPASSWORD", pass);
                pb.redirectErrorStream(true);

                p = pb.start();

                try {
                    InputStream is = p.getInputStream();
                    InputStreamReader isr = new InputStreamReader(is);
                    BufferedReader br = new BufferedReader(isr);
                    String ll;
                    while ((ll = br.readLine()) != null) {
                        //System.out.println(ll);
                    }
                    createZip(archivoBD, archivoZIP);

                    if (notificar) {
                        JOptionPane.showMessageDialog(escritorio, "Copia de seguridad Generada, puede acceder al archivo en la ruta: " + ruta, "Respaldo Información", JOptionPane.INFORMATION_MESSAGE);
                    }

                } catch (Exception e) {
                    System.out.println(e);

                }

            } catch (IOException e) {
                Logger.getLogger(Utilidades.class.getName()).log(Level.WARNING, "message", e);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crear Archivo Comprimido con Clave
     *
     * @see
     * http://mdsaputra.wordpress.com/2011/06/30/java-zip-and-extract-zip-files/
     * @param filePath
     * @param zipPath
     */
    public void createZip(String filePath, String zipPath) {
        try {
            //zipPath is absolute path of zipped file
            ZipFile zipFile = new ZipFile(zipPath);
            //filePath is absolute path of file that want to be zip
            File fileToAdd = new File(filePath);
            //create zip parameters such a password, encryption method, etc
            ZipParameters parameters = new ZipParameters();
            parameters.setCompressionMethod(Zip4jConstants.COMP_DEFLATE);
            parameters.setCompressionLevel(Zip4jConstants.DEFLATE_LEVEL_NORMAL);
            parameters.setEncryptFiles(true);
            parameters.setEncryptionMethod(Zip4jConstants.ENC_METHOD_STANDARD);
            parameters.setPassword("www.gidsoft.org");
            zipFile.addFile(fileToAdd, parameters);

            //Eliminar Archivo Temporal
            delFileTemp(fileToAdd);
        } catch (ZipException e) {
            JOptionPane.showMessageDialog(escritorio, "Algo ha salido mal mientras se generaba la copia de seguridad,\nse recomienda realizar la copia de seguridad de los datos Manualmente \n(" + e.getMessage() + ")", "Error al Respaldar Información", JOptionPane.ERROR_MESSAGE);
        }
    }//end of create zip

    public CachedRowSet buscarSocios(String busqueda, Boolean inactivos) {
        String sql = "SELECT s.id , s.clave , concat(s.primer_nombre , ' ' , s.segundo_nombre , ' ', s.primer_apellido , ' ',s.segundo_apellido) as socio , s.telefono, CASE WHEN s.activo = true Then 'Activo' WHEN s.activo = false Then 'Inactivo' END AS \"Estado\" FROM socio s";
        String whereSQL = "";
        String orderBy = " ORDER BY s.id DESC ";

        if (!busqueda.isEmpty()) {
            busqueda = busqueda.replaceAll("^\\s+", ""); //si escriben caracteres en blanco antes de la busqueda borrarlos
            String filtro = "s.clave = '" + busqueda + "' OR s.identificacion = '" + busqueda + "' OR to_tsvector(trim(replace(concat_ws(' ',trim(s.primer_nombre),trim(s.segundo_nombre),trim(s.primer_apellido), TRIM(s.segundo_apellido,' ')),'  ', ' '))) @@ to_tsquery('" + busqueda.trim().replace(" ", "&") + "') OR s.telefono = '" + busqueda + "'";
            whereSQL += " WHERE " + filtro;
        }

        //Si no se van a mostrar los inactivos
        if (!inactivos) {
            String filtro = "activo = true";
            if (whereSQL.contains("WHERE")) {
                whereSQL += " AND " + filtro;
            } else {
                whereSQL += " WHERE " + filtro;
            }
        }

        sql = sql + whereSQL + orderBy;
        CachedRowSet data;
        data = db.sqlDatos(sql);

        return data;
    }

    public int esUsuarioUnico(String busqueda, Boolean inactivos) {

        CachedRowSet data;
        data = buscarSocios(busqueda, inactivos);
        int id = 0;
        int count = data.size();
        if (count == 1) {
            try {
                while (data.next()) {
                    id = data.getInt("id");
                }
            } catch (SQLException ex) {
                Logger.getLogger(ListadoSocios.class.getName()).log(Level.SEVERE, null, ex);
            }

        } else {
            id = -1;
        }
        return id;
    }

    public void sonar(String value) {
        try {
            sonido = AudioSystem.getClip();
            sonido.open(AudioSystem.getAudioInputStream(getClass().getResourceAsStream(ruta + value + ".wav")));
            sonido.start();
        } catch (Exception ex) {
            System.err.println(ex.getMessage());
        }
    }

    public Double obtenerSaldoUsuario(int socioId) {
        Double deudaUsuario = 0.0;

        try {
            CachedRowSet data;
            //mem_usu.activa = True AND
            String queryMembresiasActivas = "SELECT mem_dat.id as id \n"
                    + "FROM membresia_usuario mem_usu , membresia_datos mem_dat\n"
                    + "WHERE  mem_usu.id = mem_dat.membresia_socio_id AND mem_dat.estado = 'Sin Cancelar'\n"
                    + "AND mem_usu.socio_id = " + socioId;

            data = db.sqlDatos(queryMembresiasActivas);

            while (data.next()) {
                CachedRowSet saldoObject;
                String querySaldoPendiente = String.format("SELECT saldo FROM pago_membresia WHERE membresiadatos_id = %s order by fecha_pago desc limit 1", data.getString("id"));
                saldoObject = db.sqlDatos(querySaldoPendiente);

                while (saldoObject.next()) {
                    Double saldoMembresia = saldoObject.getDouble("saldo");
                    if (saldoMembresia != 0.0) {
                        deudaUsuario += saldoMembresia;
                    }
                }
            }

            System.out.println("El usuario debe $ " + deudaUsuario);

        } catch (SQLException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, null, ex);
        }

        return deudaUsuario;
    }

    public Double obtenerSaldoAFavor(int socioId) {
        Double aFavor = 0.0;
        try {
            CachedRowSet data;

            String querySaldoFavor = "SELECT valor from saldofavor WHERE socio_id = " + socioId + " ORDER BY id DESC LIMIT 1";
            data = db.sqlDatos(querySaldoFavor);

            while (data.next()) {
                aFavor = data.getDouble("valor");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, "Error consultado saldo a favor usuario", ex);
        }

        return aFavor;
    }

    public int obtenerCajaActiva() {
        int id = -1;

        try {
            CachedRowSet data;

            String queryCajaActiva = "SELECT id from caja WHERE estado = True ORDER BY id DESC LIMIT 1";
            data = db.sqlDatos(queryCajaActiva);

            while (data.next()) {
                id = data.getInt("id");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, "Error consultado caja activa", ex);
        }

        return id;
    }

    public void resaltarTexto(JLabel label, String color) {
        switch (color) {
            case "verde":
                label.setForeground(new java.awt.Color(0, 102, 0));
                break;
            case "rojo":
                label.setFont(new java.awt.Font("Tahoma", 1, 18));
                label.setForeground(new java.awt.Color(255, 0, 0));
                break;
            case "naranja":
                label.setFont(new java.awt.Font("Tahoma", 1, 18));
                label.setForeground(new java.awt.Color(255, 153, 0));
                break;
        }

    }

    void delFileTemp(File f) {
        boolean success = f.delete();
        System.out.println("Archivo Elimando?? " + success);

    }

    /**
     * Verifica si de acuerdo al evento parametrizado se debe o no ejecutar
     * copias de seguridad.
     *
     * @param evento backup_sesion|backup_abrircaja|backup_cierrecaja
     * @return respuesta booleana
     */
    private boolean requiereBackup(String evento) {
        boolean requiere = false;
        try {
            String sql = "SELECT " + evento + " , backup_ruta FROM empresa";
            CachedRowSet data;
            data = db.sqlDatos(sql);
            while (data.next()) {
                requiere = data.getBoolean(evento);
                if (requiere) {
                    System.setProperty("backup_ruta", data.getString("backup_ruta"));
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(Utilidades.class.getName()).log(Level.SEVERE, "Error consultado si requiere backup", ex);
        }

        return requiere;
    }

    /**
     * Verifica si el evento asociado requiere
     *
     * @param evento
     */
    public void verificarBackups(String evento) {
        boolean backups = requiereBackup(evento);
        final String event = evento;
        if (backups) {
            //ejecuta hilo para no bloquear UI
            Thread t1 = new Thread(new Runnable() {
                public void run() {
                    createBackupBD(event, System.getProperty("backup_ruta"), false);
                }
            });
            t1.start();

        }
    }

    /**
     * Enviar texto al portapapeles
     *
     * @param texto - cadena de texto a enviar
     */
    public static void enviarPortapales(String texto) {
        StringSelection stringSelection = new StringSelection(texto);
        Clipboard clpbrd = Toolkit.getDefaultToolkit().getSystemClipboard();
        clpbrd.setContents(stringSelection, null);
    }

    public void construirFormulario(JTable tabla, int fila, String titulo) {
        String data = "";
        int nColumnas = tabla.getColumnCount();
        for (int i = 0; i < nColumnas; i++) {
            //tabla.getColumnName(i);
            //tabla.getValueAt(fila, i);
            data += tabla.getColumnName(i) + "==" + tabla.getValueAt(fila, i) + "||";
        }

        //Construir Formulario Aqui
        JFrame frame = new JFrame();
        ListaCampos panel = new ListaCampos(data);
        System.out.println("Numero de componentes " + panel.getComponentCount());
        frame.getContentPane().add(panel);
        frame.pack();
        frame.setTitle(titulo);
        frame.setLocationRelativeTo(null);
        frame.setIconImage(new ImageIcon(getClass().getResource("/imagen/gym.png")).getImage());
        frame.setVisible(true);
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
    }

    public boolean esAnioBisiesto(int anio) {

        if ((anio % 400 == 0) || ((anio % 4 == 0) && (anio % 100 != 0))) {
            return true;
        } else {
            return false;
        }
    }

    public String getSelectedButtonText(ButtonGroup buttonGroup) {
        for (Enumeration<AbstractButton> buttons = buttonGroup.getElements(); buttons.hasMoreElements();) {
            AbstractButton button = buttons.nextElement();

            if (button.isSelected()) {
                return button.getText();
            }
        }

        return null;
    }

    public static JTable llenarTablaPermisos(CachedRowSet crs, DefaultTableModel modelo, JTable tbl) {
        try {
            ResultSetMetaData rsmd = crs.getMetaData();
            int numCols = rsmd.getColumnCount();
            modelo.setRowCount(0);

            Object[] datos = new Object[numCols];
            while (crs.next()) {
                for (int i = 0; i < numCols; i++) {
                    //Si es campo Boolean, verificar que no venga Vacio y darle el valor que corresponde
                    if ("java.lang.Boolean".equals(rsmd.getColumnClassName(i + 1))) {
                        datos[i] = false ? crs.getObject(i + 1) == null : crs.getObject(i + 1);
                    } else {
                        datos[i] = crs.getObject(i + 1);
                    }

                }
                modelo.addRow(datos);

            }

            tbl.setModel(modelo);
            crs.close();

            return tbl;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return new JTable();
        }
    }

    public String obtnerFechaActual() {
        CachedRowSet data;
        String fechaHoy = null;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

        java.sql.Date fecha = null;
        try {
            String querySQL = "SELECT now() AS fecha";
            data = db.sqlDatos(querySQL);
            if (data.next()) {
                fecha = data.getDate("fecha");
                System.out.println("La fecha hoy date es: " + fecha);
            }
            fechaHoy = format.format(fecha);
        } catch (SQLException ex) {
            Logger.getLogger(CierreCaja.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("La fecha actual es: " + fechaHoy);
        return fechaHoy;
    }



    public boolean validarFechaRegistro(String fecha_Aper, String fecha_Hoy) throws SQLException {
        String fechaActual = fecha_Hoy;
        String fechaApertura = fecha_Aper;
        System.out.println("La fecha actual del sistema es: " + fechaActual);
        System.out.println("La fecha de cierre del sistema es: " + fechaApertura);
        CachedRowSet data;
        boolean fecha;
        String querySQL = "SELECT '" + fecha_Hoy + "' >= '" + fecha_Aper + "' AS fecha";
        System.out.println("La consulta a comparar es: " + querySQL);
        data = db.sqlDatos(querySQL);
        if (data.next()) {
            fecha = data.getBoolean("fecha");
            System.out.println("La fecha hoy es: " + fecha);
            return fecha;
        }

        //boolean otraFecha;
        String querySQLFecha = "SELECT '" + fecha_Hoy + "' < '" + fecha_Aper + "' AS fecha";
        System.out.println("La segunda consulta a comparar es: " + querySQL);
        data = db.sqlDatos(querySQLFecha);
        if (data.next()) {
            fecha = data.getBoolean("fecha");
            System.out.println("La segunda fecha hoy es: " + fecha);
            return fecha;
        }

        return false;
    }

    /**
     * Obtener nombre de Clase
     *
     * @param c - ejemplo: login.class
     * @return
     *
     */
//    public static String nClase(Class<?> c){
//        return c.getTypeName();
//    }
}
