/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import logica.DB;
import logica.Utilidades;
import net.sf.jcarrierpigeon.WindowPosition;
import net.sf.jtelegraph.Telegraph;
import net.sf.jtelegraph.TelegraphQueue;
import net.sf.jtelegraph.TelegraphType;

/**
 *
 * @author GID
 */
public class RegistrarEgreso extends javax.swing.JInternalFrame {

    /**
     * Creates new form internalEjemplo
     */
    private DB db = new DB();
    private Utilidades utilidades = new Utilidades();

    public RegistrarEgreso() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        btnGuardar = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        txtCantidad = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();

        setClosable(true);
        setIconifiable(true);
        setTitle("Registrar Egresos");

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        btnGuardar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/guardar.png"))); // NOI18N
        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnGuardarActionPerformed(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel4.setText("Descripción del Egreso");

        txtDescripcion.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtDescripcion.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtDescripcionKeyTyped(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel5.setText("Cantidad");

        txtCantidad.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtCantidad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtCantidadKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtCantidadKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5))
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtDescripcion))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(txtCantidad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Administrar Gastos de Caja (Egresos)");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/registradora.png"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel3.setText("Permite administrar el dinero que sale de la caja.");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(68, Short.MAX_VALUE))
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
                .addGap(131, 131, 131))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
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
        int tamanoValor = txtCantidad.getText().length();

        if (tamanoValor <= 6) {
            try {
                boolean validacion = utilidades.validarFechaRegistro(utilidades.fecha_apertura(), utilidades.obtnerFechaActual());

                if (validacion == false) {
                    JOptionPane.showMessageDialog(this, "NO SE HA REGISTRADO EL EGRESO", "REGISTRANDO EGRESO", JOptionPane.WARNING_MESSAGE);
                    Telegraph tele = new Telegraph("Cierre Caja", "No se puede crear el egreso. \n La fecha actual es menor que la fecha de apertura", TelegraphType.NOTIFICATION_WARNING, WindowPosition.TOPRIGHT, 9000);
                    TelegraphQueue q = new TelegraphQueue();
                    q.add(tele);
                } else {
                    registrarEgreso();
                }
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEgreso.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            Telegraph tele = new Telegraph("REGISTRO EGRESO", "NO SE HA PODIDO CREAR EL EGRESO \n HA SOBREPASASO EL VALOR DE LOS EGRESOS", TelegraphType.NOTIFICATION_ERROR, WindowPosition.TOPRIGHT, 5000);
            TelegraphQueue q = new TelegraphQueue();
            q.add(tele);
            txtCantidad.setText("");
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void txtCantidadKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyReleased
        // TODO add your handling code here:
        utilidades.validarCampoNumericos(txtCantidad.getText(), txtCantidad);
    }//GEN-LAST:event_txtCantidadKeyReleased

    private void txtCantidadKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCantidadKeyPressed
        int tamanoValor = txtCantidad.getText().length();
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (tamanoValor <= 6) {
                registrarEgreso();
            } else {
                Telegraph tele = new Telegraph("REGISTRO EGRESO", "NO SE HA PODIDO CREAR EL EGRESO \n HA SOBREPASASO EL VALOR DE LOS EGRESOS", TelegraphType.NOTIFICATION_ERROR, WindowPosition.TOPRIGHT, 5000);
                TelegraphQueue q = new TelegraphQueue();
                q.add(tele);
                txtCantidad.setText("");
            }
        }
    }//GEN-LAST:event_txtCantidadKeyPressed

    private void txtDescripcionKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDescripcionKeyTyped

    private void txtDescripcionKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtDescripcionKeyReleased
       txtDescripcion.setText(utilidades.formatearCadena(txtDescripcion.getText()));
    }//GEN-LAST:event_txtDescripcionKeyReleased


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField txtCantidad;
    private javax.swing.JTextField txtDescripcion;
    // End of variables declaration//GEN-END:variables

    public void registrarEgreso() {
        String descripcion = txtDescripcion.getText();
        String usuario;
        usuario = System.getProperty("usuario_sistema");
        int usuario_id = Integer.parseInt(usuario);
        //Es false cuando es Egreso
        String tipo = "Egreso";

        if ((descripcion.isEmpty()) && (txtCantidad.getText().isEmpty())) {
            Telegraph tele = new Telegraph("Validación", "No ha llenado el campo descripcion ni cantidad", TelegraphType.NOTIFICATION_INFO, WindowPosition.TOPRIGHT, 9000);
            TelegraphQueue q = new TelegraphQueue();
            q.add(tele);
        } else if ((descripcion.length() > 0) && (txtCantidad.getText().isEmpty())) {
            Telegraph tele = new Telegraph("Validación", "No ha llenado el campo cantidad", TelegraphType.NOTIFICATION_INFO, WindowPosition.TOPRIGHT, 9000);
            TelegraphQueue q = new TelegraphQueue();
            q.add(tele);
        } else if ((descripcion.length() < 0) && (txtCantidad.getText().isEmpty())) {
            Telegraph tele = new Telegraph("Validación", "No ha llenado el campo descripcion", TelegraphType.NOTIFICATION_INFO, WindowPosition.TOPRIGHT, 9000);
            TelegraphQueue q = new TelegraphQueue();
            q.add(tele);
        } else if (Double.parseDouble(txtCantidad.getText().substring(0, 1)) == 0) {
            Telegraph tele = new Telegraph("Validación", "No se permitern valores menores a 0", TelegraphType.NOTIFICATION_INFO, WindowPosition.TOPRIGHT, 9000);
            TelegraphQueue q = new TelegraphQueue();
            q.add(tele);
        } else {

            Double cantidad = Double.valueOf(txtCantidad.getText());

            String querySQL = String.format("INSERT INTO movimiento(usuario_sistema_id, fecha_hora, valor, nota, tipo, fecha_registro) VALUES (%s,now(),%s,'%s','%s', now())", usuario_id, cantidad, descripcion, tipo);
            boolean success = db.sqlEjec(querySQL);
            txtDescripcion.setText("");
            txtCantidad.setText("");
            if (success) {
                Telegraph tele = new Telegraph("Egreso Registrado", "Se ha registrado Correctamente el Egreso", TelegraphType.NOTIFICATION_DONE, WindowPosition.TOPRIGHT, 9000);
                TelegraphQueue q = new TelegraphQueue();
                q.add(tele);
                this.dispose();
            } else {
                Telegraph tele = new Telegraph("Egreso No Registrado", "No se ha registrado Correctamente el Egreso", TelegraphType.NOTIFICATION_ERROR, WindowPosition.TOPRIGHT, 9000);
                TelegraphQueue q = new TelegraphQueue();
                q.add(tele);
            }

        }
    }
}
