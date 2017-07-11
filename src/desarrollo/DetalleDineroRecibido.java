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
public class DetalleDineroRecibido extends javax.swing.JInternalFrame {

    /**
     * Creates new form DetalleDineroRecibido
     */
    private DB db = new DB();
    logica.Utilidades utilidades = new logica.Utilidades();

    public DetalleDineroRecibido() {
        initComponents();
        cargarDineroRecibido();
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
        tablaTotal = new javax.swing.JTable();

        setClosable(true);
        setIconifiable(true);
        setMaximizable(true);
        setResizable(true);
        setTitle("Detalle Dinero Recibido");

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel1.setText("Suma total de Dinero Recibido");

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        jLabel3.setText("Permite visualizar el  total de dinero recibido en la caja . ");

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/hoja.png"))); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 554, Short.MAX_VALUE)
                        .addGap(97, 97, 97))
                    .addComponent(jLabel1)))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(114, 114, 114))
        );

        jPanel1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        tablaTotal.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tablaTotal);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 663, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 191, Short.MAX_VALUE)
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
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
    private javax.swing.JTable tablaTotal;
    // End of variables declaration//GEN-END:variables

    private void cargarDineroRecibido() {
        CachedRowSet data;

        try {
            DefaultTableModel tableModel = new DefaultTableModel(null, new String[]{"USUARIO", "NOMBRE SOCIO", "TIPO DE PAGO", "VALOR DE PAGO", "DESCUENTO", "FECHA DEL PAGO"});

            String sql
                    = //                    "SELECT us.primer_nombre AS \"USUARIO\", 'Movimiento' AS \"Nombre Socio\", mo.tipo AS \"TIPO DE PAGO\", mo.valor AS \"VALOR PAGO\", mo.nota AS \"DESCRIPCIÓN\", 0.0 AS \"Descuento\", mo.fecha_registro AS \"FECHA PAGO\"\n"
                    //                    + "FROM movimiento mo, caja ca, usuario_sistema us\n"
                    //                    + "WHERE mo.fecha_registro >= ca.fecha_apertura\n"
                    //                    + "AND ca.estado= true\n"
                    //                    + "AND mo.usuario_sistema_id= us.id\n"
                    //                    + "AND mo.tipo = 'Egreso'\n"
                    //                    + "UNION\n"
                    "SELECT us.primer_nombre AS \"USUARIO\", pago.nombres || ' ' || pago.apellidos AS \"Nombre Socio\", 'VISITAS' AS \"TIPO DE PAGO\", pago.costo AS \"VALOR PAGO\",0.0 AS \"Descuento\", pago.fecha_registro AS \"FECHA PAGO\"\n"
                    + "FROM pago_visita pago, caja ca, usuario_sistema us\n"
                    + "WHERE pago.fecha_registro >= ca.fecha_apertura\n"
                    + "AND ca.estado= true\n"
                    + "AND pago.usuario_sistema_id= us.id\n"
                    + "UNION\n"
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
                    + "       AND s.id = sf.socio_id "
                    
                    
;

            System.out.println("El total es: " + sql);
            data = db.sqlDatos(sql);

            /**
             * *
             * "SELECT us.primer_nombre AS \"USUARIO\", 'Movimiento' AS \"Nombre
             * Socio\", mo.tipo AS \"TIPO DE PAGO\", mo.valor AS \"VALOR PAGO\",
             * mo.nota AS \"DESCRIPCIÓN\", 0.0 AS \"Descuento\",
             * mo.fecha_registro AS \"FECHA PAGO\"\n" + "FROM movimiento mo,
             * caja ca, usuario_sistema us\n" + "WHERE mo.fecha_registro >=
             * ca.fecha_apertura\n" + "AND ca.estado= true\n" + "AND
             * mo.usuario_sistema_id= us.id\n" + "AND mo.tipo != 'Egreso'\n" +
             * "UNION\n" + "SELECT us.primer_nombre AS \"USUARIO\", pago.nombres
             * || ' ' || pago.apellidos AS \"Nombre Socio\", 'Visitas' AS \"TIPO
             * DE PAGO\", pago.costo AS \"VALOR PAGO\", pago.nombres AS
             * \"DESCRIPCIÓN\",0.0 AS \"Descuento\", pago.fecha_registro AS
             * \"FECHA PAGO\"\n" + "FROM pago_visita pago, caja ca,
             * usuario_sistema us\n" + "WHERE pago.fecha_registro >=
             * ca.fecha_apertura\n" + "AND ca.estado= true\n" + "AND
             * pago.usuario_sistema_id= us.id\n" + "UNION\n" + "SELECT
             * us.primer_nombre AS \"USUARIO\", so.primer_nombre || ' ' ||
             * so.segundo_nombre || ' ' || so.primer_apellido || ' ' ||
             * so.segundo_apellido AS \"Nombre Socio\", memNom.nombre AS \"TIPO
             * DE PAGO\", mem.pago AS \"VALOR PAGO\", 'Cancelando Membresia' AS
             * \"DESCRIPCIÓN\", da.descuento AS \"Descuento\", mem.fecha_pago AS
             * \"FECHA PAGO\"\n" + "FROM pago_membresia mem, caja ca,
             * usuario_sistema us, socio so, membresia memNom, membresia_usuario
             * mu, membresia_datos da, membresia_duracion md\n" + "WHERE
             * mem.fecha_pago >= ca.fecha_apertura\n" + "AND so.id =
             * mem.socio_id \n" + "AND ca.estado= true\n" + "AND
             * mem.usuario_sistema_id= us.id\n" + "AND mem.pago <> 0.0\n" + "AND
             * mu.socio_id = da.membresia_socio_id \n" + "AND md.membresia_id =
             * memNom.id \n" + "AND md.membresia_id = mu.membresia_id\n" + "AND
             * da.usuario_sistema_id = us.id \n" + "AND da.membresia_socio_id =
             * so.id AND mu.activa = True"
             */
            tablaTotal = logica.Utilidades.llenarTabla(data.createCopy(), tableModel, tablaTotal);
        } catch (Exception ex) {
            Logger.getLogger(RegistrarPagoMembresia.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}