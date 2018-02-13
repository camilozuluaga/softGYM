/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.SpinnerNumberModel;
import logica.DB;
import logica.Reportes;
import logica.Utilidades;

/**
 *
 * @author jucazuse
 */
public class ImprimirReciboProducto extends javax.swing.JInternalFrame {

    Reportes reporte = new Reportes();
    private DB db = new DB();
    int idFactura;
    String descripcion;
    Date fecha = new Date();
    Utilidades utilitis = new Utilidades();
    double valorAPagar;

    SpinnerNumberModel model = new SpinnerNumberModel(
            new Integer(1), // Dato visualizado al inicio en el spinner 
            new Integer(1), // Límite inferior 
            new Integer(10), // Límite superior 
            new Integer(1) // incremento-decremento 
    );

    /**
     * Creates new form ImprimirRecibo
     */
    public ImprimirReciboProducto(int idFactura) {
        initComponents();

        this.idFactura = idFactura;
        this.setTitle(".:: Hércules - Vista Previa Recibo ::.");
        lbNombre.setText(utilitis.CargarNombreTitulo().toUpperCase());
        lbDireccion.setText("DIRECCION: "+utilitis.cargarDireccionEmpresa().toUpperCase());
        lbNit.setText("NIT: "+utilitis.cargarNitEmpresa().toUpperCase());
        txtFecha.setEditable(false);
        txtOperador.setEditable(false);
        txtDescripcion.setEditable(false);
        txtPaga.setEditable(false);
        txtValorPagar.setEditable(false);
        System.out.println(idFactura);
        encabezado(idFactura);
        descripcion(idFactura);
        txtFecha.setText(String.valueOf(fecha.toLocaleString()));
        jSVeces.setModel(model);

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
        lbNombre = new javax.swing.JLabel();
        lbNit = new javax.swing.JLabel();
        lbDireccion = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtOperador = new javax.swing.JTextField();
        txtFecha = new javax.swing.JTextField();
        lbNumeroRecibo = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        txtPaga = new javax.swing.JTextField();
        txtValorPagar = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        txtDescripcion = new javax.swing.JTextArea();
        txtImprimir = new javax.swing.JButton();
        jSVeces = new javax.swing.JSpinner();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        txtCambio = new javax.swing.JTextField();

        setClosable(true);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Vista Previa Recibo"));

        lbNombre.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        lbNombre.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbNombre.setText("BioFisic GYM");

        lbNit.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        lbNit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbNit.setText("Nit: 10270746-1");

        lbDireccion.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        lbDireccion.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbDireccion.setText("Carrera 50 No. 49 - 59");

        jLabel5.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("Recibo de Pago No.");

        jLabel6.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        jLabel6.setText("Fecha:");

        jLabel7.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        jLabel7.setText("Operador:");

        txtOperador.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        txtOperador.setBorder(null);
        txtOperador.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtOperadorActionPerformed(evt);
            }
        });

        txtFecha.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
        txtFecha.setBorder(null);

        jLabel8.setText("--------------------------------------------------------------------------------------------");

        jLabel9.setText("--------------------------------------------------------------------------------------------");

        jLabel10.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        jLabel10.setText("Dinero recibido $");

        jLabel11.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        jLabel11.setText("Valor a pagar $");

        jLabel13.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        jLabel13.setText("***NO SE HACEN DEVOLUCIONES DE DINERO***");

        jScrollPane2.setBorder(null);

        txtDescripcion.setColumns(30);
        txtDescripcion.setFont(new java.awt.Font("Monospaced", 0, 12)); // NOI18N
        txtDescripcion.setLineWrap(true);
        txtDescripcion.setRows(2);
        txtDescripcion.setBorder(null);
        txtDescripcion.setFocusable(false);
        jScrollPane2.setViewportView(txtDescripcion);

        txtImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/imprimir.png"))); // NOI18N
        txtImprimir.setText("Imprimir");
        txtImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtImprimirActionPerformed(evt);
            }
        });

        jSVeces.setModel(new javax.swing.SpinnerNumberModel(2, 2, 10, 1));

        jLabel14.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        jLabel14.setText("Numero de Copias");

        jLabel15.setFont(new java.awt.Font("Monospaced", 1, 11)); // NOI18N
        jLabel15.setText("Cambio $");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 369, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(txtOperador, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel6)
                                        .addGap(31, 31, 31)
                                        .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel13)
                                .addGap(55, 55, 55))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel10)
                                            .addComponent(jLabel11))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(txtPaga)
                                            .addComponent(txtValorPagar, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lbDireccion, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 343, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(lbNumeroRecibo, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(71, 71, 71))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                                .addComponent(jLabel14)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jSVeces, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(txtImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 155, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(lbNit, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(lbNombre, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCambio, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(lbNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbNit)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lbDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lbNumeroRecibo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(txtFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtOperador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(28, 28, 28)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtPaga, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(txtValorPagar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(txtCambio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 46, Short.MAX_VALUE)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jSVeces, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(txtImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void txtImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtImprimirActionPerformed
        Thread hiloFactura = new Thread(new Runnable() {

            @Override
            public void run() {
                String veces = jSVeces.getValue().toString();
                int impresiones = Integer.parseInt(veces);
                reporte.reporte("headerfoooter.jasper", impresiones, idFactura, "Factura", "Factura");

            }
        });

        hiloFactura.start();

    }//GEN-LAST:event_txtImprimirActionPerformed

    private void txtOperadorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtOperadorActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtOperadorActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSpinner jSVeces;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lbDireccion;
    private javax.swing.JLabel lbNit;
    private javax.swing.JLabel lbNombre;
    private javax.swing.JLabel lbNumeroRecibo;
    private javax.swing.JTextField txtCambio;
    private javax.swing.JTextArea txtDescripcion;
    private javax.swing.JTextField txtFecha;
    private javax.swing.JButton txtImprimir;
    private javax.swing.JTextField txtOperador;
    private javax.swing.JTextField txtPaga;
    private javax.swing.JTextField txtValorPagar;
    // End of variables declaration//GEN-END:variables

    public void encabezado(int idFactura) {
        try {
            CachedRowSet data;
            String sql;
            sql = String.format("SELECT fac.id as id,fac.pago as paga,CONCAT(us.primer_nombre||' '||us.primer_apellido) AS operador \n"
                    + "FROM factura_producto fac,usuario_sistema us\n"
                    + "WHERE fac.id = %s\n"
                    + "AND fac.usuario_sistema_id = us.id;", idFactura);

            data = db.sqlDatos(sql);
            while (data.next()) {
                lbNumeroRecibo.setText(data.getString("id"));
                txtPaga.setText(data.getString("paga"));
                txtOperador.setText(data.getString("operador"));

               
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImprimirReciboProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void descripcion(int idFactura) {
        try {
            CachedRowSet data;
            String sql;

            sql = String.format("select fp.precio_total as pago,p.nombre as nombre, p.precio as precio from producto p,factura_producto fp, facturaproductos fps where p.id=fps.producto_id and fps.factura_producto_id=fp.id and fp.id=%s", idFactura);
            data = db.sqlDatos(sql);
            while (data.next()) {
                descripcion = data.getString("nombre") + "    " + data.getString("precio") + " " + "\n";
                txtDescripcion.append(descripcion);
                txtValorPagar.setText(String.valueOf(data.getDouble("pago")));
                txtCambio.setText(String.valueOf(Double.parseDouble(txtPaga.getText())-Double.parseDouble(txtValorPagar.getText())));
            }
        } catch (SQLException ex) {
            Logger.getLogger(ImprimirReciboProducto.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
