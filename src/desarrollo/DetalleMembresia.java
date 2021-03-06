/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.table.DefaultTableModel;
import logica.DB;

/**
 *
 * @author Usuario
 */
public class DetalleMembresia extends javax.swing.JInternalFrame {

    /**
     * Creates new form DetalleMembresia
     */
    logica.Utilidades utilidades = new logica.Utilidades();

    private DB db = new DB();

    public DetalleMembresia() {
        initComponents();
        cargarMembresias();
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
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaMembresia = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Detalles Membresias");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Detalles Ajustes de Membresia");

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel3.setText("Permite detallar las membresias.");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/hoja.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 668, Short.MAX_VALUE)
                .addGap(97, 97, 97))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(129, 129, 129))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaMembresia.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaMembresia);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 745, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 245, Short.MAX_VALUE)
                .addContainerGap())
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
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tablaMembresia;
    // End of variables declaration//GEN-END:variables

    private void cargarMembresias() {
        CachedRowSet data;
        String nombre_usuario = null;
        String usuario;
        usuario = System.getProperty("usuario_sistema");

        try {
            DefaultTableModel tableModel = new DefaultTableModel(null, new String[]{"Usuario", "Socio Nombre", "Nombre Membresía", "Pago Membresía", "Descuento", "Hora Del Pago"});

            String sql = "SELECT sis.primer_nombre \n"
                    + "       ||' ' \n"
                    + "       ||sis.primer_apellido, \n"
                    + "       s.primer_nombre \n"
                    + "       || ' ' \n"
                    + "       || s.segundo_nombre \n"
                    + "       || ' ' \n"
                    + "       || s.primer_apellido \n"
                    + "       || ' ' \n"
                    + "       || s.segundo_apellido, \n"
                    + "       memb.nombre, \n"
                    + "       mem.Pago - f.saldo_favor_uso, \n"
                    + "       mem_d.descuento, \n"
                    + "       f.fecha_registro \n"
                    + "FROM   pago_membresia mem, \n"
                    + "       caja ca, \n"
                    + "       factura f, \n"
                    + "       usuario_sistema sis, \n"
                    + "       membresia_datos mem_d, \n"
                    + "       membresia_usuario mem_u, \n"
                    + "       membresia memb, \n"
                    + "       socio s \n"
                    + "WHERE  ca.estado = TRUE \n"
                    + "       AND mem.fecha_pago >= ca.fecha_apertura \n"
                    + "       AND pago <> 0.0 \n"
                    + "       AND mem.valor_adquirido - f.saldo_favor_uso <> 0.0 \n"
                    + "       AND sis.id = f.usuario_sistema_id \n"
                    + "       AND f.socio_id = s.id \n"
                    + "       AND mem.factura_id = f.id \n"
                    + "       AND mem.membresiadatos_id = mem_d.id \n"
                    + "       AND mem_d.membresia_socio_id = mem_u.id \n"
                    + "       AND mem_u.membresia_id = memb.id \n"
                    + "UNION \n"
                    + "SELECT sis.primer_nombre \n"
                    + "       ||' ' \n"
                    + "       ||sis.primer_apellido, \n"
                    + "       s.primer_nombre \n"
                    + "       || ' ' \n"
                    + "       || s.segundo_nombre \n"
                    + "       || ' ' \n"
                    + "       || s.primer_apellido \n"
                    + "       || ' ' \n"
                    + "       || s.segundo_apellido, \n"
                    + "       'Uso Saldo a Favor' :: text, \n"
                    + "       sf.valor_caja * -1 AS Dinero, \n"
                    + "       0, \n"
                    + "       sf.fecha_registro \n"
                    + "FROM   saldofavor sf, \n"
                    + "       usuario_sistema sis, \n"
                    + "       socio s \n"
                    + "WHERE  sf.id IN (SELECT Max(sf.id) \n"
                    + "                 FROM   saldofavor sf, \n"
                    + "                        caja c \n"
                    + "                 WHERE  sf.caja_id = c.id \n"
                    + "                        AND c.estado = TRUE \n"
                    + "                        AND sf.socio_id IN (SELECT sf.socio_id \n"
                    + "                                            FROM   saldofavor sf, \n"
                    + "                                                   caja c \n"
                    + "                                            WHERE  sf.caja_id = c.id \n"
                    + "                                                   AND c.estado = TRUE \n"
                    + "                                            GROUP  BY sf.valor, \n"
                    + "                                                      sf.valor_caja, \n"
                    + "                                                      sf.id \n"
                    + "                                            HAVING sf.valor - sf.valor_caja <= 0 \n"
                    + "                                           ) \n"
                    + "                 GROUP  BY sf.socio_id) \n"
                    + "       AND sf.valor_caja < 0 \n"
                    + "       AND sis.id = sf.usuario_sistema_id \n"
                    + "       AND s.id = sf.socio_id ";

            data = db.sqlDatos(sql);
            
            tablaMembresia = logica.Utilidades.llenarTabla(data.createCopy(), tableModel, tablaMembresia);
        } catch (Exception ex) {
            Logger.getLogger(RegistrarPagoMembresia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
