package desarrollo;

import java.awt.event.KeyEvent;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.ImageIcon;
import logica.DB;
import logica.Utilidades;
import net.sf.jcarrierpigeon.WindowPosition;
import net.sf.jtelegraph.Telegraph;
import net.sf.jtelegraph.TelegraphQueue;
import net.sf.jtelegraph.TelegraphType;

/**
 *
 * @author GermanV
 */
public class RegistroEntradaAutomatica extends javax.swing.JFrame {
    int idMembresia;
    Utilidades sonidos;
    CumpleaniosSocio miCumpleañosSocio;
    RegistrarEntrada miEntrada;
    DB midb;
    int socio;
    int clave = 0;
    int idEmpresa;
    String claveGuardar;
    Frame frame;
    VerSocio versocio;
    puerta.Puerta arduino;
    String vencio;
    String recordatorio;
    private DB db = new DB();

    /**
     * Creates new form FrameBloqueo
     */
    public RegistroEntradaAutomatica(){
        sonidos = new Utilidades();
        miCumpleañosSocio = new CumpleaniosSocio();
        midb = new DB(); //objeto de base de datos
        this.setUndecorated(true);//quita bordes a jframe
        initComponents();
        idEmpresa = 1;
        lblImagen.setVisible(false);
        jLabel1.setText("BIENVENIDO A " + sonidos.CargarNombreTitulo().toUpperCase());
        this.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);//evita cerra jframe con ALT+C
        this.setExtendedState(MAXIMIZED_BOTH);//maximizado
        this.setAlwaysOnTop(true);//siempre al frente
        lblFoto.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        imagen = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        pass = new javax.swing.JPasswordField();
        jButton1 = new javax.swing.JButton();
        lblNombreSocio = new javax.swing.JLabel();
        lblMembresiaVence = new javax.swing.JLabel();
        lblCumpleanios = new javax.swing.JLabel();
        lblImagen = new javax.swing.JLabel();
        lblFoto = new javax.swing.JLabel();
        lmsjVencimiento = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                formFocusLost(evt);
            }
        });

        imagen.setBackground(new java.awt.Color(102, 102, 102));
        imagen.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        imagen.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                imagenKeyPressed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("BIENVENIDO A BioFisic GYM");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel2.setText("Código de acceso:");

        pass.setFont(new java.awt.Font("Tahoma", 0, 30)); // NOI18N
        pass.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                passActionPerformed(evt);
            }
        });
        pass.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                passKeyPressed(evt);
            }
        });

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton1.setText("x");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        lblNombreSocio.setFont(new java.awt.Font("Tahoma", 0, 48)); // NOI18N
        lblNombreSocio.setForeground(new java.awt.Color(102, 102, 102));

        lblMembresiaVence.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblMembresiaVence.setForeground(new java.awt.Color(255, 51, 51));

        lblCumpleanios.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N

        lblImagen.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/cumpleaños.png"))); // NOI18N

        lblFoto.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/sin_foto_hombre.png"))); // NOI18N

        lmsjVencimiento.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lmsjVencimiento.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(156, 156, 156)
                        .addComponent(lmsjVencimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 601, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 152, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblNombreSocio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lblMembresiaVence, javax.swing.GroupLayout.DEFAULT_SIZE, 505, Short.MAX_VALUE)
                                    .addComponent(lblCumpleanios, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lblImagen)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(195, 195, 195)
                .addComponent(jLabel2)
                .addGap(10, 10, 10)
                .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jButton1)
                .addContainerGap(211, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(88, 88, 88)
                        .addComponent(lblFoto, javax.swing.GroupLayout.PREFERRED_SIZE, 182, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(16, 16, 16)
                                .addComponent(jLabel2))
                            .addComponent(pass, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(lblNombreSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(lblMembresiaVence, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lblCumpleanios, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(44, 44, 44)
                                .addComponent(lblImagen)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lmsjVencimiento, javax.swing.GroupLayout.DEFAULT_SIZE, 48, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("www.dreamsoftcolombia.com");

        javax.swing.GroupLayout imagenLayout = new javax.swing.GroupLayout(imagen);
        imagen.setLayout(imagenLayout);
        imagenLayout.setHorizontalGroup(
            imagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagenLayout.createSequentialGroup()
                .addGap(275, 275, 275)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 477, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, imagenLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(imagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, imagenLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel3)))
                .addContainerGap())
        );
        imagenLayout.setVerticalGroup(
            imagenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(imagenLayout.createSequentialGroup()
                .addGap(71, 71, 71)
                .addComponent(jLabel1)
                .addGap(47, 47, 47)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        getContentPane().add(imagen, java.awt.BorderLayout.CENTER);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void imagenKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imagenKeyPressed

    }//GEN-LAST:event_imagenKeyPressed

    private void formFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusLost
        pass.requestFocus();
    }//GEN-LAST:event_formFocusLost

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
        pass.requestFocus();
    }//GEN-LAST:event_formFocusGained

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        pass.setText("");
        pass.requestFocus();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void passKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_passKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            try {
                if (pass.getText().trim().length() == 4) {
                    if (!pass.getText().trim().isEmpty()) {
                        clave = getIdMembresia(pass.getText().trim());
                        claveGuardar = pass.getText().trim();
                        socio = idSocio(claveGuardar);
                        System.out.println("************** ID DEL SOCIO *************** "+socio);
                        actualizarMembresias(socio);
                        System.out.println("*******************************************************");
                    }
                    miEntrada = new RegistrarEntrada(clave, null);
                    pass.setText("");

                    Thread hiloEntrada = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                if (usuarioExiste(clave)) {

                                    //comprobar si el usuario esta cumpliendo años hoy o maniana
                                    if (miCumpleañosSocio.tieneFechaNacimiento(socio)) {
                                        if (miCumpleañosSocio.cumpleañosManiana(socio)) {
                                            lblCumpleanios.setText("Mañana es tu cumpleaños...Felicidades! :)");
                                            lblImagen.setVisible(true);
                                            sonidos.sonar("cumpleaños");
                                        } else if (miCumpleañosSocio.cumpleañosHoy(socio)) {
                                            lblCumpleanios.setText("Te Deseamos Muchas Felicidades En Tu Cumpleaños! :)");
                                            lblImagen.setVisible(true);
                                            sonidos.sonar("cumpleaños");
                                        }
                                    }
                                    //fin comprobacion
                                    
                                    
                                    if(!validarSiEntro(socio)){
                                       lblNombreSocio.setVisible(true);
                                    lblNombreSocio.setText("Hasta Luego: " + miEntrada.traerNombreSocio(clave));
                                    lblMembresiaVence.setText(venceMembresia());
                                    getVencimientoMembresias(); 
                                    }else{
                                    lblNombreSocio.setVisible(true);
                                    lblNombreSocio.setText("Bienvenido: " + miEntrada.traerNombreSocio(clave));
                                    lblMembresiaVence.setText(venceMembresia());
                                    getVencimientoMembresias();
                                    }

                                    getImagen();

                                    delay(4500);
                                    lblNombreSocio.setText("");
                                    lblMembresiaVence.setText("");
                                    lblCumpleanios.setText("");
                                    lmsjVencimiento.setText("");
                                    lblImagen.setVisible(false);
                                    lblFoto.setVisible(false);

                                }

                            } catch (SQLException ex) {
                                Logger.getLogger(RegistroEntradaAutomatica.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (ParseException ex) {
                                Logger.getLogger(RegistroEntradaAutomatica.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    });
                    hiloEntrada.start();

                } else {
                    if (usuarioSistema(pass.getText().trim()) > 0) {
                        //PuertaController.OpenDoor();
                        arduino = new puerta.Puerta();
                        arduino.openDoor();
                        Telegraph tele = new Telegraph("Bienvenido", "Usuario del sistema", TelegraphType.NOTIFICATION_DONE, WindowPosition.TOPRIGHT, 4000);
                        TelegraphQueue q = new TelegraphQueue();
                        q.add(tele);
                        pass.setText("");
                    }

                }
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(RegistroEntradaAutomatica.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_ESCAPE) {
            this.dispose();
        }
    }//GEN-LAST:event_passKeyPressed

    private void passActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_passActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_passActionPerformed
    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(RegistroEntradaAutomatica.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new RegistroEntradaAutomatica().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel imagen;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JLabel lblCumpleanios;
    private javax.swing.JLabel lblFoto;
    private javax.swing.JLabel lblImagen;
    private javax.swing.JLabel lblMembresiaVence;
    public javax.swing.JLabel lblNombreSocio;
    private javax.swing.JLabel lmsjVencimiento;
    public javax.swing.JPasswordField pass;
    // End of variables declaration//GEN-END:variables

    private int getIdMembresia(String clave) {
        idMembresia = 0;
        try {
            CachedRowSet data;
            String sql = String.format("SELECT s.id as id\n"
                    + "FROM socio s\n"
                    + "WHERE clave='%s';", clave);
            data = midb.sqlDatos(sql);

            while (data.next()) {
                idMembresia = data.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(VerSocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idMembresia;
    }

    private int usuarioSistema(String clave) {
        int idMembresia = 0;
        try {
            CachedRowSet data;
            String sql = String.format("SELECT us.id as id\n"
                    + "FROM usuario_sistema us\n"
                    + "WHERE clave='%s';", clave);
            data = midb.sqlDatos(sql);

            while (data.next()) {
                idMembresia = data.getInt("id");
                System.out.println("id membresia= " + idMembresia);
            }
        } catch (SQLException ex) {
            Logger.getLogger(VerSocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return idMembresia;
    }

    private int idSocio(String clave) throws SQLException {
        String query = String.format("SELECT id FROM socio WHERE clave='%s';", clave);
        ResultSet rs = new logica.DB().sqlDatos(query);
        while (rs.next()) {
            socio = rs.getInt(1);
        }
        return socio;
    }

    private boolean usuarioExiste(int socio) {
        try {
            venceMembresia();
        } catch (SQLException ex) {
            Logger.getLogger(RegistroEntradaAutomatica.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(RegistroEntradaAutomatica.class.getName()).log(Level.SEVERE, null, ex);
        }
         int id = 0;
        int id2 = 0;
        int id3 = 0;
        try {
            CachedRowSet data, data2, data3;
            DB db = new DB();


            String sql = String.format("SELECT count(mu.membresia_id) as cantidad FROM membresia_datos md, membresia_usuario mu where now() between md.fecha_inicio_membresia + interval '1h'  and md.fecha_fin_membresia + interval '23h'  and md.membresia_socio_id= mu.id and md.activa=TRUE and mu.socio_id=%s", socio);
            data = db.sqlDatos(sql);

            String sql3 = String.format("SELECT plazo_entrada FROM empresa WHERE id=%s", idEmpresa);
            data3 = db.sqlDatos(sql3);
            while (data3.next()) {
                id3 = data3.getInt("plazo_entrada");
            }
            int plazo_permitido = id3 * 24;
            String sql2 = String.format("SELECT count(mu.membresia_id) as cantidad FROM membresia_datos md, membresia_usuario mu where now() between md.fecha_inicio_membresia + interval '1h'  and md.fecha_fin_membresia + interval '" + plazo_permitido + "h'  and md.membresia_socio_id= mu.id  and md.activa=FALSE and mu.socio_id=%s", socio);
            data2 = db.sqlDatos(sql2);

            while (data.next()) {
                id = data.getInt("cantidad");
            }
            while (data2.next()) {
                id2 = data2.getInt("cantidad");
            }
            if (id >= 1) {
                System.out.println("----------LOG DE VALIDACIONES/ENTRADA SOCIO------");
                System.out.println("Su membresía no ha caducado o no es promocional.");
                vencio = "Su membresía vence el día:";
                recordatorio = "";
                getVencimientoMembresias();
                return true;
            } else if (id2 >= 1 && id3 >= 1) {
                System.out.println("----------LOG DE VALIDACIONES/ENTRADA SOCIO------");

                vencio = "Su membresía vencio el día:";
                System.out.println("Su membresía caduco tiene " + id3 + " dias para ponerse al dia");
                System.out.println("Su membresía caduco tiene " + plazo_permitido + " horas para ponerse al dia");
                getVencimientoMembresias();
                return true;
            } else {
                System.out.println("No tiene membresías activas para entrar hoy,\nSi tenía una membresía promocional, ésta ya venció.");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    private String venceMembresia() throws SQLException, ParseException {
        String vence = "";
        Date fecha = null;
        String cadena = null;

        int membresia = traerIdMembresiaAdquirida();

        CachedRowSet data;
        DB db = new DB();
        String sql = String.format("SELECT fecha_fin_membresia as fecha from membresia_datos where membresia_datos.membresia_socio_id=%s", membresia);
        data = db.sqlDatos(sql);
        while (data.next()) {
            fecha = data.getDate("fecha");
        }
        String mesLetras = null;

        if (fecha != null) {
            vence = fecha.toString();
            String mes = vence.substring(5, 7);
            String año = vence.substring(0, 4);
            String dia = vence.substring(8, 10);

            switch (mes) {
                case "01":
                    mesLetras = "Enero";
                    break;
                case "02":
                    mesLetras = "Febrero";
                    break;
                case "03":
                    mesLetras = "Marzo";
                    break;
                case "04":
                    mesLetras = "Abril";
                    break;
                case "05":
                    mesLetras = "Mayo";
                    break;
                case "06":
                    mesLetras = "Junio";
                    break;
                case "07":
                    mesLetras = "Julio";
                    break;
                case "08":
                    mesLetras = "Agosto";
                    break;
                case "09":
                    mesLetras = "Septiembre";
                    break;
                case "10":
                    mesLetras = "Octubre";
                    break;
                case "11":
                    mesLetras = "Noviembre";
                    break;
                case "12":
                    mesLetras = "Diciembre";
                    break;
            }
            cadena = vencio + " " + dia + " de " + mesLetras + " de " + año + " " + recordatorio;
        }

        return cadena;
    }

    public int membresia_id() throws SQLException {
        int membresia_id = 0;
        CachedRowSet data;
        DB db = new DB();
        String Sql = String.format("SELECT mu.membresia_id FROM membresia_usuario mu where mu.socio_id=%s", socio);
        data = db.sqlDatos(Sql);
        while (data.next()) {
            try {
                membresia_id = data.getInt("membresia_id");
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return membresia_id;
    }

    public int traerIdMembresiaAdquirida() throws SQLException {
        CachedRowSet data;
        int id = 0;
        String consulta = String.format("SELECT id from membresia_usuario where socio_id=%s and activa=true;", socio, membresia_id());
        data = midb.sqlDatos(consulta);
        while (data.next()) {
            try {
                id = data.getInt("id");
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }

    private static void
            delay(int millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ix) {
        }
    }

    public void getImagen() {
        try {
            CachedRowSet data;

            System.out.println("pass " + pass.getText());
            data = db.sqlDatos("SELECT foto,REPLACE(concat(soc.primer_nombre,' ',soc.segundo_nombre,' ',soc.primer_apellido,' ',soc.segundo_apellido),'  ',' ') as Usuario, EXTRACT(year FROM AGE(NOW(),soc.fecha_nacimiento))::text || ' Años' edad , soc.sexo ,soc.clave FROM socio soc WHERE soc.id = " + socio);

            while (data.next()) {
                if (data.getBytes("foto") != null) {
                    ImageIcon foto = new ImageIcon(data.getBytes("foto"));
                    lblFoto.setVisible(true);
                    lblFoto.setIcon(foto);
                    lblFoto.setBorder(null);

                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistroEntradaAutomatica.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean validarSiEntro(int socio) throws SQLException {

        if (obtenerEntradas(socio) % 2 == 1) {
            return true;
        } else {
            return false;
        }

    }

    public int obtenerEntradas(int clave) throws SQLException {
        int entradas = 0;
        CachedRowSet data;
        String query = String.format("SELECT entradas_hoy FROM socio WHERE id=%s;", clave);
        data = db.sqlDatos(query);
        while (data.next()) {
            try {
                entradas = data.getInt("entradas_hoy");

            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return entradas;

    }

    private void getVencimientoMembresias() {
        try {

            int dias = 0;
            java.util.Date fechaVencimiento = new java.util.Date();
            CachedRowSet data;
            String sql = "SELECT mendatos.fecha_fin_membresia - now()::date as dias  , mendatos.fecha_fin_membresia as fecha_vencimiento FROM membresia mem , membresia_usuario memusu , socio s , membresia_datos mendatos\n"
                    + "WHERE  mem.id = memusu.membresia_id AND s.id = memusu.socio_id AND memusu.id = mendatos.membresia_socio_id AND mendatos.estado = 'Pagada' AND memusu.socio_id = " + socio + " ORDER BY memusu.id DESC LIMIT 1";

            data = db.sqlDatos(sql);

            if (data.size() == 0) {
                lmsjVencimiento.setText("Socio no reporta Membresias Activas");
                //btnRegistrarEntrada.setVisible(false);
                return;
            }

            //****
            while (data.next()) {
                dias = data.getInt("dias");
                fechaVencimiento = data.getDate("fecha_vencimiento");
            }

            String msj = " ";
            String fecha = Utilidades.castFecha(fechaVencimiento, "dd/MM/yyyy");

            if (dias == 0) {
                msj = "La membresia del Usuario Vence Hoy";
                sonidos.resaltarTexto(lmsjVencimiento, "naranja");
            } else if (dias < 0) {
                dias = dias * -1;
                msj = String.format("Membresia Vencida hace %s días (%s)", dias, fecha);
                sonidos.resaltarTexto(lmsjVencimiento, "rojo");
            } else {
                msj = String.format("Membresía Vence en: %s (%s Días)", fecha, dias);
                sonidos.resaltarTexto(lmsjVencimiento, "verde");
            }
            lmsjVencimiento.setText(msj);
        } catch (SQLException ex) {
            Logger.getLogger(VerSocio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     public void actualizarMembresias(int idSocio){
        try {
            CachedRowSet dataMembresia; 
            int id5=0;

            String sql5 = String.format("SELECT mu.id, FROM membresia_usuario mu, membresia_datos md WHERE md.membresia_socio_id=mu.id AND mu.socio_id=%s;", idSocio);
            dataMembresia = db.sqlDatos(sql5);
            System.out.println("SQL 5"+sql5);
            while (dataMembresia.next()) {
                id5 = dataMembresia.getInt("id");
                String querySQL = String.format("UPDATE membresia_datos SET activa = false WHERE activa=true AND (SELECT count(mu.membresia_id) as cantidad FROM membresia_datos md, membresia_usuario mu where now() between md.fecha_inicio_membresia + interval '1h'  and md.fecha_fin_membresia + interval '23h'  and md.membresia_socio_id="+id5+" and md.activa=true  and mu.socio_id="+idSocio+")<1 AND membresia_socio_id=%s", id5);
                db.sqlEjec(querySQL);
               System.out.println("SQL1:"+querySQL);
                String querySQL2 = String.format("UPDATE membresia_datos SET activa = true WHERE activa=false AND (SELECT count(mu.membresia_id) as cantidad FROM membresia_datos md, membresia_usuario mu where now() between md.fecha_inicio_membresia + interval '1h'  and md.fecha_fin_membresia + interval '23h'  and md.membresia_socio_id="+id5+" and md.activa=false  and mu.socio_id="+idSocio+")>0 AND membresia_socio_id=%s", id5);
                db.sqlEjec(querySQL2);
                System.out.println("SQL2:"+querySQL2);

            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
