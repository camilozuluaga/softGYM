/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JFileChooser;
import logica.DB;
import logica.Utilidades;
import logica.msj;
import net.sf.jtelegraph.TelegraphType;

/**
 *
 * @author gaso
 */
public class CopiasSeguridad extends javax.swing.JInternalFrame {

    Utilidades utilidades = new Utilidades();
    private final DB db = new DB();
    private JFileChooser chooser;

    /**
     * Creates new form CopiasSeguridad
     */
    public CopiasSeguridad() {
        initComponents();
        obtenerConfiguracion();
        
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        checkSesion = new javax.swing.JCheckBox();
        checkAbrirCaja = new javax.swing.JCheckBox();
        checkCerrarCaja = new javax.swing.JCheckBox();
        jLabel5 = new javax.swing.JLabel();
        cUbicacion = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        btnGuardar1 = new javax.swing.JButton();
        bAbrirRuta = new javax.swing.JButton();

        setClosable(true);
        setTitle("Gestor Copias de Seguridad");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Copias de Seguridad ");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/backup.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel3.setText("Gestione la forma en que se administran las copia de su información");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 420, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(122, 122, 122))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar Cambios");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel4.setText("Periodicidad de las copias");

        checkSesion.setText("Al iniciar sesión");
        checkSesion.setToolTipText("cada vez que se inicie sesión se generará una copia de seguridad de los datos del sistema, esta opción puede hacer que se demore un poco mas en arrancar el sistema");
        checkSesion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkSesionActionPerformed(evt);
            }
        });

        checkAbrirCaja.setText("Al Abrir Caja");
        checkAbrirCaja.setToolTipText("Al Abrir la caja se generará una copia de seguridad de su información en la carpeta configurada en esta ventana");
        checkAbrirCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkAbrirCajaActionPerformed(evt);
            }
        });

        checkCerrarCaja.setText("Al Cerrar Caja");
        checkCerrarCaja.setToolTipText("Al cerrar la caja se generará una copia de seguridad de su información en la carpeta configurada en esta ventana");
        checkCerrarCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                checkCerrarCajaActionPerformed(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel5.setText("Ubicación");

        cUbicacion.setEditable(false);
        cUbicacion.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cUbicacionActionPerformed(evt);
            }
        });

        jButton1.setText("Seleccionar Ubicación");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        btnGuardar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/backup_start.png"))); // NOI18N
        btnGuardar1.setText("Generar Backup ahora");
        btnGuardar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardar1ActionPerformed(evt);
            }
        });

        bAbrirRuta.setText("Abrir Carpeta");
        bAbrirRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bAbrirRutaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(cUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, 216, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jButton1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(bAbrirRuta))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addComponent(jLabel4))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(checkSesion)
                        .addGap(18, 18, 18)
                        .addComponent(checkAbrirCaja)
                        .addGap(18, 18, 18)
                        .addComponent(checkCerrarCaja)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 169, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(44, 44, 44))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(checkSesion)
                    .addComponent(checkAbrirCaja)
                    .addComponent(checkCerrarCaja))
                .addGap(7, 7, 7)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cUbicacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(bAbrirRuta))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnGuardar)
                    .addComponent(btnGuardar1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(15, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed

                String ubicacion = cUbicacion.getText();
                Boolean backupSesion = checkSesion.isSelected();
                Boolean backupAbrirCaja = checkAbrirCaja.isSelected();
                Boolean backupCerrarCaja = checkCerrarCaja.isSelected();
                
                String query = "UPDATE empresa SET backup_ruta = '"+ubicacion+"' , backup_sesion = "+backupSesion+" , backup_abrircaja = "+backupAbrirCaja+" , backup_cierrecaja = "+backupCerrarCaja;
                
                if (db.sqlEjec(query)){
                    //Colocar parametro disponible en todo el contexto de la aplicacion
                    System.setProperty("backup_ruta", ubicacion);
                    msj.show("Copias de seguridad", "Cambios guardados en la configuración.", TelegraphType.NOTIFICATION_INFO, 3000);
                }
                
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void checkSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkSesionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkSesionActionPerformed

    private void checkAbrirCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkAbrirCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkAbrirCajaActionPerformed

    private void checkCerrarCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_checkCerrarCajaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_checkCerrarCajaActionPerformed

    private void btnGuardar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardar1ActionPerformed
        utilidades.createBackupBD("usuario", cUbicacion.getText(),true);
    }//GEN-LAST:event_btnGuardar1ActionPerformed

    private void bAbrirRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bAbrirRutaActionPerformed
        if (!cUbicacion.getText().isEmpty()) {
            File directorio = new File(cUbicacion.getText());
            if (Desktop.isDesktopSupported()) {
                try {
                    Desktop.getDesktop().open(directorio);
                } catch (IOException ex) {
                    Logger.getLogger(CopiasSeguridad.class.getName()).log(Level.SEVERE, "No se encontró la ruta", ex);
                }
            }
        } else {
            System.out.println("Carpeta no establecida, imposible abrir");
        }
    }//GEN-LAST:event_bAbrirRutaActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File(cUbicacion.getText()));
        chooser.setDialogTitle("Seleccione Ruta de Copia de Seguridad");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
           
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
            cUbicacion.setText(chooser.getSelectedFile().toString());
        } else {
            System.out.println("No Selection ");
        }

    }//GEN-LAST:event_jButton1ActionPerformed

    private void cUbicacionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cUbicacionActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cUbicacionActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton bAbrirRuta;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnGuardar1;
    private javax.swing.JTextField cUbicacion;
    private javax.swing.JCheckBox checkAbrirCaja;
    private javax.swing.JCheckBox checkCerrarCaja;
    private javax.swing.JCheckBox checkSesion;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables

    private void obtenerConfiguracion() {
        String query = "SELECT backup_ruta, backup_sesion , backup_abrircaja , backup_cierrecaja FROM empresa LIMIT 1";
        try {
            CachedRowSet data;
            data = db.sqlDatos(query);

            while (data.next()) {
                cUbicacion.setText(data.getString("backup_ruta"));
                checkSesion.setSelected(data.getBoolean("backup_sesion"));
                checkAbrirCaja.setSelected(data.getBoolean("backup_abrircaja"));
                checkCerrarCaja.setSelected(data.getBoolean("backup_cierrecaja"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(VerSocio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
