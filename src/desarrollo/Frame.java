/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

import help.Help;
import java.awt.Component;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.beans.PropertyVetoException;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sql.rowset.CachedRowSet;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import logica.Acceso;
import logica.ConexionFoto;
import logica.DB;
import logica.Fondo;
import logica.PlaceHolder;
import logica.Utilidades;
import puerta.Puerta;

/**
 *
 * @author GID
 */
public class Frame extends javax.swing.JFrame {

    /**
     * Creates new form frame
     */
    private DB db = new DB();
    private CachedRowSet data;
    private Connection connection = null;
     private ResultSet resultSet = null;
    private Utilidades utiles = new Utilidades();
    private PlaceHolder placeholder;
    public InputStream foto1 = this.getClass().getResourceAsStream("/imagen/biofisic_logo.png");
    ConexionFoto foto = new ConexionFoto();

    public AperturaCaja aperturaCaja;
    public Utilidades utilidades;
    Puerta pp;

    public Frame() {
        initComponents();
        utilidades = new Utilidades();
        this.setDefaultCloseOperation(0);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setTitle(utiles.CargarNombreTitulo().toUpperCase() + " - > Sistema de Control Acceso");
        this.setVisible(true);
        txtBuscar.requestFocusInWindow();
        setIconImage(new ImageIcon(getClass().getResource("/imagen/icon.png")).getImage());
        PlaceHolder placeHolder = new PlaceHolder("Buscar socio por identificación o codigo ...", txtBuscar);
        System.out.println("pos aca");
        //cargarImagen1(escritorio);
        cargarImagen(escritorio, foto1);

        comprobarPermisosMenu();
        aperturaCaja = new AperturaCaja();
        lbUsuario.setText("USUARIO: " + aperturaCaja.cargarUsuario());
        pp = new puerta.Puerta();
       
        pp.abrirConexion(pp.consultarPuerto());
    }

    public Frame(String id) throws SQLException, ParseException {
        initComponents();
        this.setDefaultCloseOperation(0);
        this.setExtendedState(this.MAXIMIZED_BOTH);
        this.setTitle(utiles.CargarNombreTitulo().toUpperCase() + " - > Sistema de Control Acceso");
        this.setVisible(true);
        txtBuscar.requestFocusInWindow();
        txtBuscar.setText(id);
        buscar();
        //cargarImagen1(escritorio);
        setIconImage(new ImageIcon(getClass().getResource("/imagen/icon.png")).getImage());
        pp = new puerta.Puerta();

        pp.abrirConexion(pp.consultarPuerto());
    }

    public void cargarImagen(JDesktopPane jDeskp, InputStream fileImagen) {
        try {
            BufferedImage image = ImageIO.read(fileImagen);
            jDeskp.setBorder(new Fondo(image));
        } catch (IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void cargarImagen1(JDesktopPane jDeskp) {
        CachedRowSet data;
        String querySQL = String.format("SELECT imagen_principal FROM empresa");
        data = db.sqlDatos(querySQL);
        
        System.out.println("entro a este metodo");
        try {
            while (data.next()) {

                if (data.getBytes("imagen_principal") != null) {
                   ImageIcon foto = new ImageIcon(data.getBytes("imagen_principal"));
                  
                   Image j =foto.getImage();
                   //BufferedImage image = ImageIO.
                  
                   
                   
                    
                    
                    
                    
                    
                    
                   
                    
                    
                   // jDeskp.setBorder(new Fondo(image));
                    System.out.println("hola como estas");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Producto.class.getName()).log(Level.SEVERE, null, ex);
        }    
   }
    public void agregarInternalFrame(JDesktopPane desktop, JInternalFrame internal) { //esta funcion permite agregar un internal frame a un jframe rapidamente :)
        desktop.add(internal);
        Utilidades.centrarInternalFrame(internal);
    }

    public void agregarInternalFrame(JInternalFrame internal) { //esta funciÃ³n permite agregar un internal frame a un jframe rapidamente :)
        escritorio.add(internal);
        Utilidades.centrarInternalFrame(internal);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenu1 = new javax.swing.JMenu();
        jMenuItem3 = new javax.swing.JMenuItem();
        escritorio = new javax.swing.JDesktopPane();
        btnRegistrarVisita = new javax.swing.JButton();
        btnCrearSocio = new javax.swing.JButton();
        btnCierreCaja = new javax.swing.JButton();
        btnRegistrarEgreso = new javax.swing.JButton();
        btnCrearMembresia = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        bBuscar = new javax.swing.JButton();
        bInicio = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        bPuerta1 = new javax.swing.JButton();
        btnCorreo = new javax.swing.JButton();
        bInicio1 = new javax.swing.JButton();
        lbUsuario = new javax.swing.JLabel();
        MenuAplicacion = new javax.swing.JMenuBar();
        mArchivo = new javax.swing.JMenu();
        jMenuItem13 = new javax.swing.JMenuItem();
        mAcciones = new javax.swing.JMenu();
        jMenuItem11 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem9 = new javax.swing.JMenuItem();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem6 = new javax.swing.JMenuItem();
        mAdministracion = new javax.swing.JMenu();
        mMembresia = new javax.swing.JMenuItem();
        mEmpleado = new javax.swing.JMenuItem();
        separador = new javax.swing.JPopupMenu.Separator();
        mIngreso = new javax.swing.JMenuItem();
        mEgreso = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        itemAdministrarPuerta = new javax.swing.JMenuItem();
        mInformes = new javax.swing.JMenu();
        jMenuItem2 = new javax.swing.JMenuItem();
        jSeparator2 = new javax.swing.JPopupMenu.Separator();
        jMenuItem14 = new javax.swing.JMenuItem();
        jMenuItem19 = new javax.swing.JMenuItem();
        jMenuItem15 = new javax.swing.JMenuItem();
        jMenuItem16 = new javax.swing.JMenuItem();
        jMenuItem17 = new javax.swing.JMenuItem();
        jMenuItem18 = new javax.swing.JMenuItem();
        mInformes1 = new javax.swing.JMenu();
        jSeparator3 = new javax.swing.JPopupMenu.Separator();
        jMenuItem10 = new javax.swing.JMenuItem();
        mInformes2 = new javax.swing.JMenu();
        jSeparator4 = new javax.swing.JPopupMenu.Separator();
        jMenuItem20 = new javax.swing.JMenuItem();

        jMenu1.setText("jMenu1");

        jMenuItem3.setText("jMenuItem3");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(0, 0, 0));

        escritorio.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                escritorioKeyTyped(evt);
            }
        });

        btnRegistrarVisita.setBackground(new java.awt.Color(255, 255, 255));
        btnRegistrarVisita.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrarVisita.setText("REGISTRAR VISITA (F3)");
        btnRegistrarVisita.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarVisitaActionPerformed(evt);
            }
        });

        btnCrearSocio.setBackground(new java.awt.Color(255, 255, 255));
        btnCrearSocio.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCrearSocio.setText("CREAR SOCIO (F2)");
        btnCrearSocio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearSocioActionPerformed(evt);
            }
        });

        btnCierreCaja.setBackground(new java.awt.Color(255, 255, 255));
        btnCierreCaja.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCierreCaja.setText("CIERRE DE CAJA (F7)");
        btnCierreCaja.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCierreCajaActionPerformed(evt);
            }
        });

        btnRegistrarEgreso.setBackground(new java.awt.Color(255, 255, 255));
        btnRegistrarEgreso.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnRegistrarEgreso.setText("REGISTRAR EGRESO (F5)");
        btnRegistrarEgreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRegistrarEgresoActionPerformed(evt);
            }
        });

        btnCrearMembresia.setBackground(new java.awt.Color(255, 255, 255));
        btnCrearMembresia.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        btnCrearMembresia.setText("CREAR MEMBRESÍA (F6)");
        btnCrearMembresia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearMembresiaActionPerformed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        txtBuscar.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(153, 153, 153));
        txtBuscar.setToolTipText("BUSCAR UN SOCIO...");
        txtBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtBuscarActionPerformed(evt);
            }
        });
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txtBuscarKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        bBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/buscar.png"))); // NOI18N
        bBuscar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bBuscarActionPerformed(evt);
            }
        });

        bInicio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/home.png"))); // NOI18N
        bInicio.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInicioActionPerformed(evt);
            }
        });

        jLabel1.setBackground(new java.awt.Color(0, 153, 255));
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/gym.jpg"))); // NOI18N

        bPuerta1.setBackground(new java.awt.Color(153, 153, 153));
        bPuerta1.setForeground(new java.awt.Color(204, 204, 204));
        bPuerta1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/icono_puerta.png"))); // NOI18N
        bPuerta1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bPuerta1ActionPerformed(evt);
            }
        });

        btnCorreo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/message.png"))); // NOI18N
        btnCorreo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCorreoActionPerformed(evt);
            }
        });

        bInicio1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagen/iconotienda.png"))); // NOI18N
        bInicio1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bInicio1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 338, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(bInicio1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCorreo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bInicio)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bPuerta1)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(9, 9, 9)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(bBuscar)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bPuerta1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(bInicio1, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lbUsuario.setBackground(new java.awt.Color(255, 255, 255));
        lbUsuario.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbUsuario.setForeground(java.awt.Color.red);
        lbUsuario.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbUsuario.setText("jLabel2");

        escritorio.setLayer(btnRegistrarVisita, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnCrearSocio, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnCierreCaja, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnRegistrarEgreso, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(btnCrearMembresia, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        escritorio.setLayer(lbUsuario, javax.swing.JLayeredPane.DEFAULT_LAYER);

        javax.swing.GroupLayout escritorioLayout = new javax.swing.GroupLayout(escritorio);
        escritorio.setLayout(escritorioLayout);
        escritorioLayout.setHorizontalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(escritorioLayout.createSequentialGroup()
                .addGap(82, 82, 82)
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addComponent(btnCrearSocio, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnRegistrarEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnCierreCaja, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addComponent(btnRegistrarVisita, javax.swing.GroupLayout.PREFERRED_SIZE, 237, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26)
                        .addComponent(btnCrearMembresia, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lbUsuario, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 487, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        escritorioLayout.setVerticalGroup(
            escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, escritorioLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addGap(322, 322, 322)
                        .addComponent(btnCierreCaja, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(escritorioLayout.createSequentialGroup()
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnCrearSocio, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnRegistrarEgreso, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(11, 11, 11)
                        .addGroup(escritorioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnRegistrarVisita, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnCrearMembresia, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(102, 102, 102)
                        .addComponent(lbUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        MenuAplicacion.setFont(new java.awt.Font("Segoe UI", 0, 24)); // NOI18N

        mArchivo.setText("Archivo");
        mArchivo.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jMenuItem13.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem13.setText("Copias de Seguridad");
        jMenuItem13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem13ActionPerformed(evt);
            }
        });
        mArchivo.add(jMenuItem13);

        MenuAplicacion.add(mArchivo);

        mAcciones.setText("Acciones");
        mAcciones.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jMenuItem11.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_F1, 0));
        jMenuItem11.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem11.setText("Registrar Entrada Automática");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        mAcciones.add(jMenuItem11);
        mAcciones.add(jSeparator1);

        jMenuItem5.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem5.setText("Crear Socio");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        mAcciones.add(jMenuItem5);

        jMenuItem4.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem4.setText("Crear Membresía");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        mAcciones.add(jMenuItem4);

        jMenuItem9.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem9.setText("Crear Usuario Sistema");
        jMenuItem9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem9ActionPerformed(evt);
            }
        });
        mAcciones.add(jMenuItem9);

        jMenuItem1.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem1.setText("Cerrar Caja");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        mAcciones.add(jMenuItem1);

        jMenuItem6.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem6.setText("Tienda");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        mAcciones.add(jMenuItem6);

        MenuAplicacion.add(mAcciones);

        mAdministracion.setText("Administración");
        mAdministracion.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        mMembresia.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        mMembresia.setText("Administrar Membresías");
        mMembresia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mMembresiaActionPerformed(evt);
            }
        });
        mAdministracion.add(mMembresia);

        mEmpleado.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        mEmpleado.setText("Administrar Usuario Sistema");
        mEmpleado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mEmpleadoActionPerformed(evt);
            }
        });
        mAdministracion.add(mEmpleado);
        mAdministracion.add(separador);

        mIngreso.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        mIngreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mIngresoActionPerformed(evt);
            }
        });
        mAdministracion.add(mIngreso);

        mEgreso.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        mEgreso.setText("Administrar Egresos");
        mEgreso.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mEgresoActionPerformed(evt);
            }
        });
        mAdministracion.add(mEgreso);

        jMenuItem8.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem8.setText("Administrar Visitas");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        mAdministracion.add(jMenuItem8);

        jMenuItem12.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem12.setText("Administrar Caja");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        mAdministracion.add(jMenuItem12);

        itemAdministrarPuerta.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        itemAdministrarPuerta.setText("Administrar Puerta");
        itemAdministrarPuerta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemAdministrarPuertaActionPerformed(evt);
            }
        });
        mAdministracion.add(itemAdministrarPuerta);

        MenuAplicacion.add(mAdministracion);

        mInformes.setText("Informes");
        mInformes.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N

        jMenuItem2.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem2.setText("Ventas del día");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        mInformes.add(jMenuItem2);
        mInformes.add(jSeparator2);

        jMenuItem14.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem14.setText("Socios");
        jMenuItem14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem14ActionPerformed(evt);
            }
        });
        mInformes.add(jMenuItem14);

        jMenuItem19.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem19.setText("Socios Comfenalco");
        jMenuItem19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem19ActionPerformed(evt);
            }
        });
        mInformes.add(jMenuItem19);

        jMenuItem15.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem15.setText("Asistencia");
        jMenuItem15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem15ActionPerformed(evt);
            }
        });
        mInformes.add(jMenuItem15);

        jMenuItem16.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem16.setText("Caja");
        jMenuItem16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem16ActionPerformed(evt);
            }
        });
        mInformes.add(jMenuItem16);

        jMenuItem17.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem17.setText("Pagos");
        jMenuItem17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem17ActionPerformed(evt);
            }
        });
        mInformes.add(jMenuItem17);

        jMenuItem18.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem18.setText("Membresias");
        jMenuItem18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem18ActionPerformed(evt);
            }
        });
        mInformes.add(jMenuItem18);

        MenuAplicacion.add(mInformes);

        mInformes1.setText("Configuraciones");
        mInformes1.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mInformes1.add(jSeparator3);

        jMenuItem10.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem10.setText("Editar Empresa");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        mInformes1.add(jMenuItem10);

        MenuAplicacion.add(mInformes1);

        mInformes2.setText("Ayuda");
        mInformes2.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        mInformes2.add(jSeparator4);

        jMenuItem20.setFont(new java.awt.Font("Segoe UI", 0, 16)); // NOI18N
        jMenuItem20.setText("Mostrar Archivo de Ayuda");
        jMenuItem20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem20ActionPerformed(evt);
            }
        });
        mInformes2.add(jMenuItem20);

        MenuAplicacion.add(mInformes2);

        setJMenuBar(MenuAplicacion);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(escritorio, javax.swing.GroupLayout.Alignment.TRAILING)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void mMembresiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mMembresiaActionPerformed
        agregarInternalFrame(escritorio, new AdministrarMembresia());
    }//GEN-LAST:event_mMembresiaActionPerformed

    private void mEmpleadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mEmpleadoActionPerformed
        agregarInternalFrame(escritorio, new AdministrarUsuarioSistema());
    }//GEN-LAST:event_mEmpleadoActionPerformed

    private void mIngresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mIngresoActionPerformed
        //agregarInternalFrame(escritorio, new AdministrarIngresos());
    }//GEN-LAST:event_mIngresoActionPerformed

    private void mEgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mEgresoActionPerformed
        agregarInternalFrame(escritorio, new AdministrarEgresos());
    }//GEN-LAST:event_mEgresoActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        agregarInternalFrame(escritorio, new CrearSocio());        // TODO add your handling code here:
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        agregarInternalFrame(escritorio, new CrearMembresia());
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void btnCrearMembresiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearMembresiaActionPerformed
        agregarInternalFrame(escritorio, new CrearMembresia());
    }//GEN-LAST:event_btnCrearMembresiaActionPerformed

    private void btnCrearSocioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearSocioActionPerformed
        agregarInternalFrame(escritorio, new CrearSocio());     }//GEN-LAST:event_btnCrearSocioActionPerformed

    private void btnRegistrarVisitaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarVisitaActionPerformed
        agregarInternalFrame(escritorio, new RegistrarVisita(pp));
    }//GEN-LAST:event_btnRegistrarVisitaActionPerformed

    private void btnRegistrarEgresoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnRegistrarEgresoActionPerformed
        agregarInternalFrame(escritorio, new RegistrarEgreso());
    }//GEN-LAST:event_btnRegistrarEgresoActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        // TODO add your handling code here:
        agregarInternalFrame(escritorio, new AdministrarVisita());
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        cierreCaja();
    }//GEN-LAST:event_jMenuItem1ActionPerformed
    private void jMenuItem9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem9ActionPerformed
        agregarInternalFrame(escritorio, new CrearUsuarioSistema());
    }//GEN-LAST:event_jMenuItem9ActionPerformed

    private void btnCierreCajaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCierreCajaActionPerformed
        // TODO add your handling code here:
        cierreCaja();
    }//GEN-LAST:event_btnCierreCajaActionPerformed


    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        RegistroEntradaAutomatica miFrameBloqueo1 = new RegistroEntradaAutomatica(pp);
        miFrameBloqueo1.setVisible(true);
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        // TODO add your handling code here:
        agregarInternalFrame(escritorio, new AdministrarCaja());
    }//GEN-LAST:event_jMenuItem12ActionPerformed
    private void bPuerta1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bPuerta1ActionPerformed
        pp.openDoor();
    }//GEN-LAST:event_bPuerta1ActionPerformed

    private void bInicioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInicioActionPerformed
         JInternalFrame items[] = escritorio.getAllFrames();
        for (int i = 0; i < items.length; i++) {
            try {
                items[i].setClosed(true); //cerrar todos los internal frames
            } catch (PropertyVetoException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        txtBuscar.requestFocusInWindow(); //darle foco al buscador
    }//GEN-LAST:event_bInicioActionPerformed

    private void bBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bBuscarActionPerformed
        try {
            buscar();
        } catch (SQLException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_bBuscarActionPerformed

    private void txtBuscarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            try {
                buscar();
            } catch (SQLException | ParseException ex) {
                Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (evt.getKeyCode() == KeyEvent.VK_F2) {
            btnCrearSocio.doClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F3) {
            btnRegistrarVisita.doClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F5) {
            btnRegistrarEgreso.doClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F6) {
            btnCrearMembresia.doClick();
        }
        if (evt.getKeyCode() == KeyEvent.VK_F7) {
            btnCierreCaja.doClick();
        }
    }//GEN-LAST:event_txtBuscarKeyPressed

    private void bPuertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton3ActionPerformed

    private void btnAbrirPuertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAbrirPuertaActionPerformed
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                try {
                    Process proc = Runtime.getRuntime().exec("java -jar puerta.jar");
                    proc.waitFor();
                    // Then retreive the process output
                    InputStream in = proc.getInputStream();
                    InputStream err = proc.getErrorStream();

                    byte b[] = new byte[in.available()];
                    in.read(b, 0, b.length);
                    System.out.println(new String(b));

                    byte c[] = new byte[err.available()];
                    err.read(c, 0, c.length);
                    System.out.println(new String(c));
                } catch (IOException | InterruptedException ex) {
                    Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        t1.start();
    }//GEN-LAST:event_btnAbrirPuertaActionPerformed

    private void escritorioKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_escritorioKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_escritorioKeyTyped

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        agregarInternalFrame(escritorio, new VentasDia());
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem13ActionPerformed
        agregarInternalFrame(escritorio, new CopiasSeguridad());
    }//GEN-LAST:event_jMenuItem13ActionPerformed

    private void jMenuItem14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem14ActionPerformed
        // TODO add your handling code here:
        agregarInternalFrame(escritorio, new InformeSocio(pp));
    }//GEN-LAST:event_jMenuItem14ActionPerformed

    private void jMenuItem15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem15ActionPerformed
        // TODO add your handling code here:
        agregarInternalFrame(escritorio, new InformeAsistencia());
    }//GEN-LAST:event_jMenuItem15ActionPerformed

    private void jMenuItem16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem16ActionPerformed
        // TODO add your handling code here:
        agregarInternalFrame(escritorio, new InformeCaja());
    }//GEN-LAST:event_jMenuItem16ActionPerformed

    private void jMenuItem17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem17ActionPerformed
        // TODO add your handling code here:
        agregarInternalFrame(escritorio, new InformePagos());
    }//GEN-LAST:event_jMenuItem17ActionPerformed

    private void jMenuItem18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem18ActionPerformed
        // TODO add your handling code here:
        agregarInternalFrame(escritorio, new InformeMembresia());
    }//GEN-LAST:event_jMenuItem18ActionPerformed

    private void itemAdministrarPuertaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemAdministrarPuertaActionPerformed
        try {
            agregarInternalFrame(escritorio, new AdministrarPuerta(pp,this));
        } catch (SQLException | IOException ex) {
            Logger.getLogger(Frame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_itemAdministrarPuertaActionPerformed

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtBuscarKeyReleased
        // TODO add your handling code here:

    }//GEN-LAST:event_txtBuscarKeyReleased

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        EmpresaData empresa = new EmpresaData();
        empresa.setVisible(true);

    }//GEN-LAST:event_jMenuItem10ActionPerformed

    private void jMenuItem19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem19ActionPerformed
        agregarInternalFrame(escritorio, new InformeSocioComfenalco(pp));
    }//GEN-LAST:event_jMenuItem19ActionPerformed

    private void jMenuItem20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem20ActionPerformed
        Help help = new Help();
        help.setVisible(true);
    }//GEN-LAST:event_jMenuItem20ActionPerformed

    private void btnCorreoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCorreoActionPerformed
        agregarInternalFrame(escritorio, new Correo());
    }//GEN-LAST:event_btnCorreoActionPerformed

    private void bInicio1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bInicio1ActionPerformed
         agregarInternalFrame(escritorio, new RegistrarPagoProductos());
    }//GEN-LAST:event_bInicio1ActionPerformed

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtBuscarActionPerformed
         // TODO add your handling code here:
    }//GEN-LAST:event_txtBuscarActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
         agregarInternalFrame(escritorio, new Tienda(this));
    }//GEN-LAST:event_jMenuItem6ActionPerformed

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
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Frame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Frame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar MenuAplicacion;
    private javax.swing.JButton bBuscar;
    private javax.swing.JButton bInicio;
    private javax.swing.JButton bInicio1;
    private javax.swing.JButton bPuerta1;
    private javax.swing.JButton btnCierreCaja;
    private javax.swing.JButton btnCorreo;
    private javax.swing.JButton btnCrearMembresia;
    private javax.swing.JButton btnCrearSocio;
    private javax.swing.JButton btnRegistrarEgreso;
    private javax.swing.JButton btnRegistrarVisita;
    public static javax.swing.JDesktopPane escritorio;
    private javax.swing.JMenuItem itemAdministrarPuerta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem13;
    private javax.swing.JMenuItem jMenuItem14;
    private javax.swing.JMenuItem jMenuItem15;
    private javax.swing.JMenuItem jMenuItem16;
    private javax.swing.JMenuItem jMenuItem17;
    private javax.swing.JMenuItem jMenuItem18;
    private javax.swing.JMenuItem jMenuItem19;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem20;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JMenuItem jMenuItem9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JPopupMenu.Separator jSeparator2;
    private javax.swing.JPopupMenu.Separator jSeparator3;
    private javax.swing.JPopupMenu.Separator jSeparator4;
    private javax.swing.JLabel lbUsuario;
    private javax.swing.JMenu mAcciones;
    private javax.swing.JMenu mAdministracion;
    private javax.swing.JMenu mArchivo;
    private javax.swing.JMenuItem mEgreso;
    private javax.swing.JMenuItem mEmpleado;
    private javax.swing.JMenu mInformes;
    private javax.swing.JMenu mInformes1;
    private javax.swing.JMenu mInformes2;
    private javax.swing.JMenuItem mIngreso;
    private javax.swing.JMenuItem mMembresia;
    private javax.swing.JPopupMenu.Separator separador;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration//GEN-END:variables

    public void nombreResponsable() {
        String querySQL = "SELECT nombre_usuario FROM usuario_sistema WHERE id= (SELECT usuario_sistema_id FROM caja WHERE usuario_sistema_id = '2')";
        data = db.sqlDatos(querySQL);

    }

    public void aperturaCaja() {
        try {
            String querySQL = "SELECT estado, total_venta  FROM caja ORDER BY id DESC LIMIT 1";
            data = db.sqlDatos(querySQL);

            if (data.size() == 0) {
                AperturaCaja aperturaCaja = new AperturaCaja();
                aperturaCaja.setVisible(true);
                aperturaCaja.setLocationRelativeTo(this);
                aperturaCaja.setAlwaysOnTop(true);
                return;
            }

            while (data.next()) {

                boolean estado = data.getBoolean("estado");

                if ((estado == false)) {
                    AperturaCaja aperturaCaja = new AperturaCaja();
                    aperturaCaja.setVisible(true);
                    aperturaCaja.setLocationRelativeTo(this);
                    aperturaCaja.setAlwaysOnTop(true);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CierreCaja.class.getName()).log(Level.WARNING, "Error SQL  JC", ex);
        }
    }

    public void cierreCaja() {
        try {
            String querySQL = "SELECT estado, total_venta  FROM caja ORDER BY id DESC LIMIT 1";
            data = db.sqlDatos(querySQL);
            while (data.next()) {
                boolean estado = data.getBoolean("estado");
                if ((estado == true)) {
                    CierreCaja cierreCaja = new CierreCaja(this);
                    Frame.escritorio.add(cierreCaja);
                    cierreCaja.toFront();
                    cierreCaja.setVisible(true);
                    cierreCaja.moveToFront();
                    cierreCaja.setLocation(150, 50);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(CierreCaja.class.getName()).log(Level.WARNING, "Error SQL  JC", ex);
        }
    }

    private void buscar() throws SQLException, ParseException {
        int usuarioId = utiles.esUsuarioUnico(txtBuscar.getText(), true);
        if (usuarioId == -1) {
            agregarInternalFrame(escritorio, new ListadoSocios(txtBuscar.getText(), this));
        } else {
            agregarInternalFrame(new VerSocio(pp,usuarioId));
        }
    }

    private void comprobarPermisosMenu() {

        //Si no es Administrador ... comprobar permisos
        if (!Acceso.esAdmin()) {

            Acceso acceso = new Acceso();

            int menus = MenuAplicacion.getMenuCount();

            for (int i = 0; i < menus; i++) {
                JMenu menu = MenuAplicacion.getMenu(i);
                int elementosMenu = menu.getMenuComponentCount();
                int elementosOcultos = 0; //Cantidad de submenus que no se mostraran

                Component[] sub = menu.getMenuComponents();

                for (Component componente : sub) {
                    if (componente instanceof JMenuItem) {
                        JMenuItem elementoMenu = (JMenuItem) componente;
                        boolean visible = acceso.menuPermitido(elementoMenu.getText(), menu.getText());
                        elementoMenu.setVisible(visible);
                        if (!visible) {
                            if (elementoMenu.getText().equals("Crear Membresía")) {
                                btnCrearMembresia.setEnabled(false);
                            } else if (elementoMenu.getText().equals("Crear Socio")) {
                                btnCrearSocio.setEnabled(false);
                            }
                            elementosOcultos++;
                        }
                    }
                }

                //Si el menu padre no tiene elementos visibles, ocultarlo
                menu.setVisible(elementosMenu != elementosOcultos);

            }
        }
    }   
}
