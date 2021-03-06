/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import logica.DB;
import logica.Utilidades;
import net.sf.jcarrierpigeon.WindowPosition;
import net.sf.jtelegraph.Telegraph;
import net.sf.jtelegraph.TelegraphQueue;
import net.sf.jtelegraph.TelegraphType;
import puerta.Puerta;

/**
 * Esta clase registra la entrada de un socio.
 *
 * @author Gidsoft
 */
public final class RegistrarEntrada {
    
    private DB db = new DB();
    RegistroEntradaAutomatica registroEntradaAutomatica;
    RegistroEntradaAutomatica entradaAuto;
    Utilidades util;
    DB miDb;
    int idEmpresa;
    String tiempogracia;
    String unidad_tiempogracia;
    boolean entrada; // determina si al abrir la puerta el cliente está entrando o saliendo.
    int idMembresiaSocio; // el socio tiene una membresía, esa adquisición tiene un id que se almacena en esta variable.
    int socio; // id del socio.
    int valorAuxiliar; //utilizado para un algoritmo que permite verificar cuantas veces se ha entrado por semana 
    double saldo; //deuda total del usuario
    String diaActual; // almacena lun, mar, mie, jue, vie, sab, dom según el día de hoy.
    String diaActualCompleto;// almacena lunes, martes, miercoles, sabado, domingo según sea el día de hoy. A diferencia de diaActual el nombre del día está completo. 
    double diaSemana; // día de la semana en números, siempre se carga el día de hoy, ejemplo lunes=1.0
    int cantidadDiasSemana; // cantidad de veces que puede entrar un socio en la semana.
    int cantidadDiasMes;
    int idMembresiaAdquirida; // almacena el id de la membresía que adquirió el socio.
    int entradasHoy; // cantidad de entradas y salidas registradas del socio el día de hoy.
    boolean entrarTodoElDia; // usado para cuando hay límite de días a la semana pero no hay límite de visitas por día.
    boolean contadorFlag; //usado con un propósito específico de notificar si ya se le avisó al socio que es su ultima entrada. 
    Utilidades sonido = new Utilidades();
    Puerta pp;
    int plazoEntrada;

    /**
     *
     * @param socio_id
     * @param ventanaVerSocio
     * @throws java.sql.SQLException
     * @throws java.text.ParseException
     */
    public RegistrarEntrada(Puerta arduino, int socio_id, VerSocio ventanaVerSocio) throws SQLException, ParseException {
        pp = arduino;
        this.socio = socio_id;
        idEmpresa = 1;
        registroEntradaAutomatica = new RegistroEntradaAutomatica(arduino);
        contadorFlag = false;
        entrada = false;
        diaSemana = 0;
        saldo = 0;
        socio = socio_id;
        tiempogracia = "";
        unidad_tiempogracia = "";
        util = new Utilidades();
        miDb = new DB();
        idMembresiaSocio = membresia_id();
        diaDeLaSemana();
        validarCongelacion(socio_id);
        validarentradas(socio_id);
        idMembresiaAdquirida = traerIdMembresiaAdquirida(); // no es el id de la membresía sino el id de membresia_usuario. (es el id de contrato de adquisicion)
        validaciones();
        
        
      
        try {
            ventanaVerSocio.updateDatos();
        } catch (Exception e) {
        }

    }

    /**
     * Determina si el socio está entrando o saliendo.
     *
     * @param id_socio
     * @return
     */
    public boolean entraOSale(int id_socio) {
        try {
            //nos damos cuenta cuantas veces ha entrado el socio hoy.
            CachedRowSet data;
            DB db = new DB();
            String Sql = String.format("SELECT id as id FROM entrada_socio WHERE socio_id=%s ", id_socio);
            data = db.sqlDatos(Sql);
            while (data.next()) {
                try {
                    entradasHoy = data.getInt("id");
                } catch (SQLException ex) {
                    Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            entrada = entradasHoy % 2 == 0;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void mensaje(String titulo, String mensaje, TelegraphType TipoMensaje) {
        Telegraph tele = new Telegraph(titulo, mensaje, TipoMensaje, WindowPosition.TOPRIGHT, 2500);
        TelegraphQueue q = new TelegraphQueue();
        q.add(tele);
    }

    public void mensaje(String titulo, String mensaje, TelegraphType TipoMensaje, int tiempo) {
        Telegraph tele = new Telegraph(titulo, mensaje, TipoMensaje, WindowPosition.TOPRIGHT, tiempo);
        TelegraphQueue q = new TelegraphQueue();
        q.add(tele);
    }

    public int membresia_id() throws SQLException {
        int membresia_id = 0;
        CachedRowSet data;
        DB db = new DB();
        String Sql = String.format("SELECT mu.membresia_id AS membresia_id FROM membresia_usuario mu,membresia_datos md WHERE mu.socio_id=%s AND md.membresia_socio_id=mu.id ORDER BY  mu.id DESC LIMIT 1;", socio);
        data = db.sqlDatos(Sql);
        while (data.next()) {
            try {
                membresia_id = data.getInt("membresia_id");
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println("Ultima membresia: " + membresia_id);
        return membresia_id;
    }

    private boolean cantidadEntradasPorDia(int socio) {
        try {
            int cantidadEntradasPermitidas = 0;
            int cantidadEntradasHoy = obtenerEntradas(socio);

            CachedRowSet data;
            DB db = new DB();
            String sql = String.format("SELECT asistencia_diaria as asistencias_permitidas FROM membresia WHERE id=%s", idMembresiaSocio);
            data = db.sqlDatos(sql);
            while (data.next()) {
                cantidadEntradasPermitidas = data.getInt("asistencias_permitidas");
            }
            data.close();

            // teniendo la cantidad de entradas permitidas y las entradas que se han hecho hoy, validamos.
            System.out.println("esta es la cantidad de entradas hoy " + cantidadEntradasHoy + " esta es la cantidad permitida " + cantidadEntradasPermitidas);
            if ((cantidadEntradasHoy < cantidadEntradasPermitidas) && (cantidadEntradasHoy != (cantidadEntradasPermitidas - 2))) {

                return true;
            } else if (cantidadEntradasHoy == (cantidadEntradasPermitidas - 2)) {
                System.out.println("Bienvenido, Recuerde que esta sería su ultima entrada hoy.");

                mensaje("Bienvenido", "Atención! Recuerde que esta es su última entrada el día de hoy.", TelegraphType.NOTIFICATION_DONE, 4000);
                contadorFlag = true;
                return true;
            } else {
                sonido.sonar("alerta");
                System.out.println("No puede entrar, ya sobrepasó el límite de visitas por día.");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private boolean entreFechaInicioFin() {
        int id = 0;
        int id2 = 0;
        int id3 = 0;
        int plazo_permitido = 0;
        try {
            CachedRowSet data, data2, data3;


            String sql = String.format("SELECT count(mu.membresia_id) as cantidad FROM membresia_datos md, membresia_usuario mu where now() between md.fecha_inicio_membresia + interval '1h'  and md.fecha_fin_membresia + interval '23h'  and md.membresia_socio_id= mu.id and md.activa=TRUE and mu.socio_id=%s", socio);
            data = db.sqlDatos(sql);
            while (data.next()) {
                id = data.getInt("cantidad");
            }
            if(id<1){
            String sql3 = String.format("SELECT plazo_entrada FROM empresa");
            data3 = db.sqlDatos(sql3);
            while (data3.next()) {
                id3 = data3.getInt("plazo_entrada");
            }
            
            plazo_permitido = (id3 * 24) + 24;
            String sql2 = String.format("SELECT count(mu.membresia_id) as cantidad FROM membresia_datos md, membresia_usuario mu where now() between md.fecha_inicio_membresia + interval '1h'  and md.fecha_fin_membresia + interval '" + plazo_permitido + "h'  and md.membresia_socio_id= mu.id and md.activa=FALSE and mu.socio_id=%s", socio);
            data2 = db.sqlDatos(sql2);


            while (data2.next()) {
                id2 = data2.getInt("cantidad");
            }
            System.out.println("plazo entrada :" + plazo_permitido + "h");
            }
            if (id >= 1) {
                System.out.println("----------LOG DE VALIDACIONES/ENTRADA SOCIO REGISTRAR ENTRADA------");
                System.out.println("Su membresía no ha caducado o no es promocional.");
                return true;
            } else if (id2 >= 1 && id3 >= 1) {
                System.out.println("----------LOG DE VALIDACIONES/ENTRADA SOCIO REGISTRAR ENTRADA------");
                System.out.println("Su membresía caduco tiene " + id3 + " dias para ponerse al dia");
                System.out.println("Su membresía caduco tiene " + plazo_permitido + " horas para ponerse al dia");
                return true;
            } else {
                sonido.sonar("alarma");
                System.out.println("----------LOG DE VALIDACIONES/ENTRADA SOCIO REGISTRAR ENTRADA------");
                System.out.println("No tiene membresías activas para entrar hoy,\nSi tenía una membresía promocional, ésta ya venció.");
                mensaje("Lo sentimos.", "No tiene una membresía; debe adquirir una.\n.Si la tenía, es posible que haya caducado.", TelegraphType.NOTIFICATION_ERROR, 6000);
                contadorFlag = true;
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }

    }

    public boolean congelado() throws SQLException {
        String aux = "";
        CachedRowSet data;
        data = db.sqlDatos("SELECT congelado FROM socio WHERE socio.id = " + socio);

        while (data.next()) {
            aux = data.getString("congelado");

        }
        if (aux.equals("congelar")) {
            return true;
        }
        return false;
    }

    private void validaciones() throws SQLException, ParseException {
        if (!congelado()) {
            if (entreFechaInicioFin() && membresiaNoExpirada(idMembresiaSocio)) { // se valida que no haya expirado si es una membresia promocional o haya terminado el tiempo de la membresia.
                if (tieneCantidadDiasMes() && tieneLimiteAsistenciaDiaria()) {
                    if (permitidoCantidadDiasMes()) { // se valida lo sigte= como tiene cantidad de díás permitidos, hoy puede entrar?
                        if (cantidadEntradasPorDia(socio)) {
                            if (bloqueHorario()) {
                                if (socioDebe()) {
                                    validadorTiempoGracia(idMembresiaSocio);
                                    sumarEntrada(socio, obtenerEntradas(socio) + 1);

                                } else {
                                    System.out.println("No se pudo persistir 1");
                                    mensaje("Upss!", "Tiene saldo pendiente de: " + "<b>" + saldo + "</b>" + ". Póngase al día con los pagos.", TelegraphType.NOTIFICATION_ERROR, 4000);
                                }
                            } else {
                                if (!contadorFlag) {
                                    sonido.sonar("alerta");
                                    mensaje("Lo sentimos", "No puede entrar en este horario.", TelegraphType.NOTIFICATION_ERROR);
                                    contadorFlag = true;
                                }
                            }
                        } else {
                            sonido.sonar("alerta");
                            mensaje("Lo sentimos", "Usted ha sobrepasado el límite de entradas hoy.", TelegraphType.NOTIFICATION_ERROR);
                        }
                    } else {
                        sonido.sonar("alerta");
                        System.out.println("No se pudo persistir 2");
                        mensaje("Lo sentimos", "usted agotó sus entradas por este mes.", TelegraphType.NOTIFICATION_ERROR, 4000);
                        return;
                    }
                } 
                if (tieneCantidadDias() && tieneLimiteAsistenciaDiaria()) { // se valida lo sigte= esta membresia tiene limite de cantidad de dias por semana?
                        if (permitidoCantidadDias()) { // se valida lo sigte= como tiene cantidad de díás permitidos, hoy puede entrar?
                            if (cantidadEntradasPorDia(socio)) {
                                if (bloqueHorario()) {
                                    if (socioDebe()) {
                                        validadorTiempoGracia(idMembresiaSocio);
                                        sumarEntrada(socio, obtenerEntradas(socio) + 1);

                                    } else {
                                        System.out.println("No se pudo persistir 1");
                                        mensaje("Upss!", "Tiene saldo pendiente de: " + "<b>" + saldo + "</b>" + ". Póngase al día con los pagos.", TelegraphType.NOTIFICATION_ERROR, 4000);
                                    }
                                } else {
                                    if (!contadorFlag) {
                                        sonido.sonar("alerta");
                                        mensaje("Lo sentimos", "No puede entrar en este horario.", TelegraphType.NOTIFICATION_ERROR);
                                        contadorFlag = true;
                                    }
                                }
                            } else {
                                sonido.sonar("alerta");
                                mensaje("Lo sentimos", "Usted ha sobrepasado el límite de entradas hoy.", TelegraphType.NOTIFICATION_ERROR);
                            }
                        } else {
                            sonido.sonar("alerta");
                            System.out.println("No se pudo persistir 2");
                            mensaje("Lo sentimos", "usted agotó sus entradas por esta semana.", TelegraphType.NOTIFICATION_ERROR, 4000);
                        }
                    } else {
                        if (validarDiaPermitido() && tieneLimiteAsistenciaDiaria()) { // Este día de la semana puede entrar? 
                            if (cantidadEntradasPorDia(socio)) {
                                if (socioDebe()) {
                                    if (bloqueHorario()) {
                                        validadorTiempoGracia(idMembresiaSocio);
                                        sumarEntrada(socio, obtenerEntradas(socio) + 1);
                                    } else {
                                        if (!contadorFlag) {
                                            sonido.sonar("alerta");
                                            mensaje("Lo sentimos", "No puede entrar a esta hora. Consulte los horarios de su membresía.", TelegraphType.NOTIFICATION_ERROR);
                                            contadorFlag = true;
                                        }
                                    }
                                } else {
                                    sonido.sonar("alerta");
                                    System.out.println("No se pudo persistir 3");
                                    mensaje("Upss!", "Tiene saldo pendiente de: " + "<b>" + saldo + "</b>" + ". Póngase al día con los pagos.", TelegraphType.NOTIFICATION_ERROR, 4000);
                                }
                            } else {
                                sonido.sonar("alerta");
                                mensaje("Lo sentimos", "Usted ha sobrepasado el límite de entradas hoy.", TelegraphType.NOTIFICATION_ERROR);
                            }
                        } else if (!tieneCantidadDias() && !tieneRestriccionesSemana() && tieneLimiteAsistenciaDiaria()) {
                            if (cantidadEntradasPorDia(socio)) {
                                if (socioDebe()) {
                                    if (bloqueHorario()) {
                                        validadorTiempoGracia(idMembresiaSocio);
                                        sumarEntrada(socio, obtenerEntradas(socio) + 1);
                                    } else {
                                        if (!contadorFlag) {
                                            sonido.sonar("alerta");
                                            mensaje("Lo sentimos", "No puede entrar a esta hora. Consulte los horarios de su membresía.", TelegraphType.NOTIFICATION_ERROR);
                                            contadorFlag = true;
                                        }
                                    }
                                } else {
                                    System.out.println("No se pudo persistir 4");
                                    sonido.sonar("alerta");
                                    mensaje("Upss!", "Tiene saldo pendiente de: " + "<b>" + saldo + "</b>" + ". Póngase al día con los pagos.", TelegraphType.NOTIFICATION_ERROR, 4000);
                                }
                            } else {
                                sonido.sonar("alerta");
                                mensaje("Lo sentimos", "Usted ha sobrepasado el límite de entradas hoy.", TelegraphType.NOTIFICATION_ERROR);
                            }
                        } else if (tieneCantidadDias()) {
                            if (permitidoCantidadDias()) { // se valida lo sigte= como tiene cantidad de díás permitidos, hoy puede entrar?
                                if (bloqueHorario()) {
                                    if (socioDebe()) {
                                        validadorTiempoGracia(idMembresiaSocio);
                                    } else {
                                        sonido.sonar("alerta");
                                        System.out.println("No se pudo persistir 5");
                                        mensaje("Upss!", "Tiene saldo pendiente de: " + "<b>" + saldo + "</b>" + ". Póngase al día con los pagos.", TelegraphType.NOTIFICATION_ERROR, 4000);
                                    }
                                } else {
                                    if (!contadorFlag) {
                                        sonido.sonar("alerta");
                                        mensaje("Lo sentimos", "No puede entrar en este horario.", TelegraphType.NOTIFICATION_ERROR);
                                        contadorFlag = true;
                                    }
                                }
                            } else {
                                sonido.sonar("alerta");
                                System.out.println("No se pudo persistir 6");
                                mensaje("Lo sentimos", "usted agotó sus entradas por esta semana.", TelegraphType.NOTIFICATION_ERROR, 4000);
                            }
                        } else if (!validarDiaPermitido()) {
                            
                            if (socioDebe()) {
                                if (bloqueHorario()) {
                                    validadorTiempoGracia(idMembresiaSocio);
                                } else {
                                    if (!contadorFlag) {
                                        sonido.sonar("alerta");
                                        mensaje("Lo sentimos", "No puede entrar a esta hora. Consulte los horarios de su membresía.", TelegraphType.NOTIFICATION_ERROR);
                                        contadorFlag = true;
                                    }
                                }
                            } else {
                                sonido.sonar("alerta");
                                System.out.println("No se pudo persistir 7");
                                mensaje("Upss!", "Tiene saldo pendiente de: " + "<b>" + saldo + "</b>" + ". Póngase al día con los pagos.", TelegraphType.NOTIFICATION_ERROR, 4000);
                            }
                        } else {
                            sonido.sonar("alerta");
                            System.out.println("No se pudo persistir 8");
                            mensaje("Upss!", "Su membresía no le permite ingresar el día " + "<b>" + diaActualCompleto + "</b>" + ".\n Pida ayuda al administrador.", TelegraphType.NOTIFICATION_ERROR, 4000);
                        }
                    }
                
            } else {
                if (!contadorFlag) {
                    sonido.sonar("alerta");
                    System.out.println("Esto es realmente vergonzoso, No se va a persistir");
                    Telegraph tele = new Telegraph("Lo sentimos", "No puede entrar. Pida ayuda al administrador.", TelegraphType.NOTIFICATION_WARNING, WindowPosition.TOPRIGHT, 4000);
                    TelegraphQueue q = new TelegraphQueue();
                    q.add(tele);
                    contadorFlag = true;
                }

            }
        } else {
            Telegraph tele = new Telegraph("Lo sentimos", "No puede entrar. Tu membresia esta congelada.", TelegraphType.NOTIFICATION_WARNING, WindowPosition.TOPRIGHT, 4000);
            TelegraphQueue q = new TelegraphQueue();
            q.add(tele);
        }
    }

    public boolean tieneLimiteAsistenciaDiaria() {
        int cantidad = 0;
        try {
            CachedRowSet data;
            DB db = new DB();
            String sql = String.format("SELECT asistencia_diaria as cantidad FROM membresia WHERE id=%s", idMembresiaSocio);
            data = db.sqlDatos(sql);
            System.out.println("Sql asistenciaDiaria "+sql);
            while (data.next()) {
                cantidad = data.getInt("cantidad");
            }

            return cantidad >= 1;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean tieneCantidadDias() {

        try {
            CachedRowSet data;
            DB db = new DB();
            String sql = String.format("SELECT mem.cantidad_dias as cantidad\n"
                    + "FROM membresia_restriccion_semana mem\n"
                    + "WHERE membresia_id=%s;", membresia_id());
            data = db.sqlDatos(sql);
            while (data.next()) {
                cantidadDiasSemana = data.getInt("cantidad");
            }
            //VALIDACION cantidad de días que ha entrado el socio al gimnasio
            System.out.println("sql cantidad dias semana: "+sql);
            if (cantidadDiasSemana == -1 || cantidadDiasSemana == 0) {
                System.out.println("No tiene límite de cantidad de días x semana");
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean tieneCantidadDiasMes() {

        try {
            CachedRowSet data;
            DB db = new DB();
            String sql = String.format("SELECT mem.cantidad_dias as cantidad\n"
                    + "FROM membresia_restriccion_entradas mem\n"
                    + "WHERE membresia_id=%s;", membresia_id());
            data = db.sqlDatos(sql);
            while (data.next()) {
                cantidadDiasMes = data.getInt("cantidad");
            }
            //VALIDACION cantidad de días que ha entrado el socio al gimnasio

            if (cantidadDiasMes == -1 || cantidadDiasMes == 0) {
                System.out.println("No tiene límite de cantidad de días x Mes");
                return false;
            } else {
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean permitidoCantidadDias() throws SQLException {
        int cantidadEntradasSemana = calcularLunesAdomingo(diaSemana); // cantidad de veces que ha entrado el socio esta semana.
        if ((cantidadEntradasSemana < cantidadDiasSemana)) {
            System.out.println("Cantidad Días Por semana---> Bienvenido");
            return true;
        } else if (cantidadEntradasSemana == cantidadDiasSemana) {
            System.out.println("Cantidad Días Por semana---> Esta es su última entrada esta semana");
            mensaje("¡Atención!", "Recuerde que:\nYa agotó sus entradas de esta semana", TelegraphType.NOTIFICATION_ERROR, 6000);
            contadorFlag = true;
            return true;
        } else if (cantidadEntradasSemana > cantidadDiasSemana) {
            sonido.sonar("alerta");
            System.out.println("Cantidad Días por semana---> Lo sentimos, usted agotó sus entradas por esta semana.");
            return false;
        }
        return false;

    }

    public boolean permitidoCantidadDiasMes() throws SQLException {
        int calcularEntradasDelMes = calcularNumeroDeEntradasMes(); // cantidad de veces que ha entrado el socio esta semana.
        if ((calcularEntradasDelMes < cantidadDiasMes)) {
            System.out.println("Cantidad Días Por Mes---> Bienvenido");
            return true;
        } else if (calcularEntradasDelMes == cantidadDiasMes) {
            System.out.println("Cantidad Días Por Mes---> Esta es su última entrada esta mes");
            mensaje("¡Atención!", "Recuerde que:\nYa agotó sus entradas de esta mes", TelegraphType.NOTIFICATION_ERROR, 6000);
            contadorFlag = true;
            return true;
        } else if (calcularEntradasDelMes > cantidadDiasMes) {
            sonido.sonar("alerta");
            System.out.println("Cantidad Días por Mes---> Lo sentimos, usted agotó sus entradas por este Mes.");
            return false;
        }
        return false;

    }

    /**
     * Si el usuario debe no debe permitirsele entrar al establecimiento. Esta
     * función permite averiguar si el usuario tiene saldo pendiente.
     *
     * @return
     * @throws java.sql.SQLException
     */
    public boolean socioDebe() throws SQLException {
        CachedRowSet data;

        String consulta = String.format("SELECT saldo FROM pago_membresia WHERE socio_id=%s;", socio);
        data = miDb.sqlDatos(consulta);
        while (data.next()) {
            saldo = data.getDouble("saldo");
        }
        return saldo == 0.0;
    }

    /**
     * Permite saber que día de la semana es. Tipo de dato booleano, por ejemplo
     * miercoles 3.0
     *
     * @return
     * @throws java.sql.SQLException
     */
    public double diaDeLaSemana() throws SQLException {
        CachedRowSet data;
        DB db = new DB();
        data = db.sqlDatos("SELECT EXTRACT(dow from now()) as dia;");// preguntando que día es hoy
        while (data.next()) {
            diaSemana = data.getDouble("dia");
        }
        System.out.println("diaSemana: " + diaSemana);
        return diaSemana;

    }

    /*
     Este método permite validad qué dias de la semana el socio tiene permitido entrar.
     */
    public boolean tieneRestriccionesSemana() {
        try {
            int hay = 0;
            CachedRowSet data1;
            String querySQL = String.format("SELECT id FROM membresia_restriccion_semana WHERE membresia_id=%s; ", idMembresiaSocio);//preguntando si hoy se puede entrar.
            System.out.println("sql restriccion semana "+ querySQL);
            data1 = miDb.sqlDatos(querySQL);
            while (data1.next()) {
                hay = data1.getInt("id");
            }
            if(hay != 0 && hay !=-1){
                 System.out.println(" retorne true" +hay);
            return true;
               
            }else{
                 System.out.println(" retorne false");
                return false;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    
    }
    /*
     Este método permite validad qué dias de la semana el socio tiene permitido entrar.
     */
    public boolean tieneRestriccionesMes() {
        try {
            int hay = 0;
            CachedRowSet data1;
            String querySQL = String.format("SELECT id FROM membresia_restriccion_entradas WHERE membresia_id=%s; ", idMembresiaSocio);//preguntando si hoy se puede entrar.
            data1 = miDb.sqlDatos(querySQL);
            while (data1.next()) {
                hay = data1.getInt("id");
            }
            return hay != 0;

        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    public boolean validarDiaPermitido() {
        System.out.println("entre al metodo");
        if (tieneRestriccionesSemana()) {
            System.out.println("Entre");
            try {
                DB db = new DB();
                boolean permitidoEntrar = false; // almacena t o f si el usuario puede entrar (True, false)
                double dia = diaDeLaSemana();
                int diaInt = (int) dia;
                validarDiaSemana(diaInt);
                CachedRowSet data1;
                System.out.println("dia actual:" + diaActual);
     
                String querySQL = String.format("SELECT %s as permitido FROM membresia_restriccion_semana WHERE membresia_id=%s ", diaActual, idMembresiaSocio);//preguntando si hoy se puede entrar.
                data1 = db.sqlDatos(querySQL);
                while (data1.next()) {
                    permitidoEntrar = data1.getBoolean("permitido");
                }
                return permitidoEntrar;
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        return false;
    }
       public boolean validarMesPermitido() {
        if (tieneRestriccionesMes()) {

            try {
                DB db = new DB();
                boolean permitidoEntrar = false; // almacena t o f si el usuario puede entrar (True, false)
                CachedRowSet data1;
                String querySQL = String.format("SELECT %s as permitido FROM membresia_restriccion_semana WHERE membresia_id=%s ", diaActual, idMembresiaSocio);//preguntando si hoy se puede entrar.
                data1 = db.sqlDatos(querySQL);
                while (data1.next()) {
                    permitidoEntrar = data1.getBoolean("permitido");
                }
                return permitidoEntrar;
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
            return false;
        }
        return false;
    }

    /**
     * Adquirir una membresía tiene un código, lo obtenemos con la sigte funcion
     *
     *
     * @return
     * @throws java.sql.SQLException
     */
    public int traerIdMembresiaAdquirida() throws SQLException {
        CachedRowSet data;
        int id = 0;
        String consulta = String.format("SELECT mu.id AS idmu FROM membresia_usuario mu,membresia_datos md WHERE mu.socio_id=%s and mu.membresia_id=%s AND md.membresia_socio_id=mu.id AND md.activa=true ORDER BY  mu.id DESC LIMIT 1;", socio, idMembresiaSocio);
        data = miDb.sqlDatos(consulta);
        while (data.next()) {
            try {
                id = data.getInt("idmu");
                System.out.println("id de membresia adquirida = " + id);
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return id;
    }

    public int validarDiaSemana(int i) {
        switch (i) {
            case 0:
                diaActual = "dom";
                diaActualCompleto = "domingo";
                break;
            case 1:
                diaActual = "lun";
                diaActualCompleto = "lunes";
                break;
            case 2:
                diaActual = "mar";
                diaActualCompleto = "martes";
                break;
            case 3:
                diaActual = "mie";
                diaActualCompleto = "miércoles";
                break;
            case 4:
                diaActual = "jue";
                diaActualCompleto = "jueves";
                break;
            case 5:
                diaActual = "vie";
                diaActualCompleto = "viernes";
                break;
            case 6:
                diaActual = "sab";
                diaActualCompleto = "sábado";
                break;
        }
        return i;
    }

    public int calcularLunesAdomingo(double diaSemana) throws SQLException {
        int resta = 0;
        Date lunes = null;
        Date domingo = null;
        int dia = (int) diaSemana;
        if (dia != 1) {
            resta = (dia - 1);
        }
        DB db = new DB();
        CachedRowSet data;
        String querySQL = String.format("SELECT Date(now()) - integer '%s' as lunes", resta);
        data = db.sqlDatos(querySQL);
        while (data.next()) {
            lunes = data.getDate("lunes");
        }

        String querySQL1 = String.format("SELECT Date('%s') + integer '6' as domingo", lunes);
        data = db.sqlDatos(querySQL1);
        while (data.next()) {
            domingo = data.getDate("domingo");
        }

        String entradasEstaSemana = "";
        String querySQL2 = String.format("WITH uniq AS (\n"
                + "SELECT DISTINCT ON (fecha_registro)\n"
                + "id as cantidad\n"
                + "FROM   entrada_socio\n"
                + "WHERE fecha_registro BETWEEN '%s' and '%s' and socio_id=%s \n"
                + "ORDER  BY fecha_registro DESC\n"
                + ")\n"
                + "SELECT COUNT(*)\n"
                + "FROM uniq;\n"
                + "", lunes, domingo, socio);
        data = db.sqlDatos(querySQL2);
        while (data.next()) {
            entradasEstaSemana = data.getString(1);
        }
        valorAuxiliar = Integer.parseInt(entradasEstaSemana);
        System.out.println("Entradas esta semana " + (Integer.parseInt(entradasEstaSemana)));
        return valorAuxiliar;
    }
    public int calcularNumeroDeEntradasMes() throws SQLException {
 
        DB db = new DB();
        CachedRowSet data;
        String entradasEsteMes = "";
        
        String querySQL2 = String.format("WITH uniq AS (SELECT DISTINCT ON (fecha_registro) id as cantidad\n" +
        "FROM   entrada_socio\n" +
        "WHERE fecha_registro BETWEEN \n" +
        "(SELECT fecha_inicio_membresia FROM membresia_datos md, membresia_usuario mu WHERE mu.socio_id="+socio+" AND mu.id=md.membresia_socio_id AND md.activa=true) \n" +
        "AND(SELECT fecha_fin_membresia FROM membresia_datos md, membresia_usuario mu WHERE mu.socio_id="+socio+" AND mu.id=md.membresia_socio_id AND md.activa=true)\n" +
        "AND socio_id=%s" +
        "ORDER  BY fecha_registro DESC)\n" +
        "SELECT COUNT(*) FROM uniq;\n" +
         "", socio);
        data = db.sqlDatos(querySQL2);
        while (data.next()) {
            entradasEsteMes = data.getString(1);
        }
        valorAuxiliar = Integer.parseInt(entradasEsteMes);
        System.out.println("Entradas esta mes " + (Integer.parseInt(entradasEsteMes)));
        return valorAuxiliar;
    }
    public boolean expiraOno(int membresia_id) throws SQLException {
        DB db = new DB();
        CachedRowSet data;
        Date fecha = null;
        String querySQL = String.format("select fecha_expiracion from membresia_promocional where membresia_id=%s;", membresia_id);
        data = db.sqlDatos(querySQL);
        while (data.next()) {
            fecha = data.getDate("fecha_expiracion");
        }
        if (fecha == null) {
            return false;
        } else {
            System.out.println("Si hay fecha de expiración");
            return true;
        }

    }

    public boolean membresiaNoExpirada(int membresia_id) throws SQLException {
        DB db = new DB();
        CachedRowSet data;
        Date fecha = null;
        if (expiraOno(membresia_id)) {// si hay una fecha de expiración
            String querySQL = String.format("SELECT fecha_expiracion as expiracion  FROM membresia_promocional, membresia_usuario mu WHERE Date(now()) <= fecha_expiracion and membresia_promocional.membresia_id=%s and mu.id=%s;", membresia_id, idMembresiaAdquirida);
            data = db.sqlDatos(querySQL);
            while (data.next()) {
                fecha = data.getDate("expiracion");
            }
            if (fecha == null) {
                System.out.println("Lo Sentimos, Su membresía promocional expiró.");
                mensaje("Lo sentimos", "Lo Sentimos, Su membresía promocional expiró.", TelegraphType.NOTIFICATION_ERROR);
                contadorFlag = true;
                return false;
            } else {
                System.out.println("Tiene una membresía vigente :)");
                return true;
            }

        } else {
            System.out.println("No hay una fecha de expiración para esta Membresía");
            return true;
        }
    }

    /**
     * Esta verificación de saldo nos permite saber si el usuario debe para
     * ofrecerle hacer una excepción si el socio debe.
     *
     * @return
     */
//    public void verificarSaldoEntrada() throws SQLException, ParseException {
//        //permite saber si el socio es deudor o no. De esto depende su entrada al gym.
//
//        CachedRowSet data;
//        double saldo = 0;
//        String consulta= String.format("SELECT saldo FROM pago_membresia WHERE socio_id=%s;", socio );
//        data = miDb.sqlDatos(consulta);
//        while (data.next()) {
//            saldo = data.getDouble("saldo");
//        }
//        if (saldo > 0.0) {
//            int seleccion = JOptionPane.showOptionDialog(null, // Componente padre
//                    "El usuario tiene saldo pendiente.\n¿Desea permitir su ingreso de todas formas? ", //Mensaje
//                    "Seleccione una opción", // Título
//                    JOptionPane.YES_NO_CANCEL_OPTION,
//                    JOptionPane.QUESTION_MESSAGE,
//                    null, // null para icono por defecto.
//                    new Object[]{"Si", "No"}, // null para YES, NO y CANCEL
//                    "Si");
//
//            if (seleccion != -1) {
//                if ((seleccion + 1) == 1) {
//                    validadorTiempoGracia(idMembresiaSocio, "Ingreso Autorizado/ Socio Con Saldo Pendiente.");  //el usuario del sistema autoriza un socio a entrar aunque tenga saldo pendiente.
//
//                }
//            }
//        } else {
//            validadorTiempoGracia(idMembresiaSocio);
//        }
//    }
    public boolean hayBloqueHorario() {
        int cantidadBloques = 0;
        try {
            CachedRowSet data;
            String sentencia = String.format("SELECT count(id) as id FROM membresia_restriccion_horario WHERE membresia_id=%s", idMembresiaSocio);
            data = miDb.sqlDatos(sentencia);
            while (data.next()) {
                cantidadBloques = data.getInt("id");
            }
            if (cantidadBloques == 0) {
                System.out.println("No hay bloques horarios para esta membresia.");
                return false;
            } else {
                System.out.println("Hay bloques para esta membresía.");
                return true;
            }
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean bloqueHorario() {
        try {
            if (hayBloqueHorario()) {
                int aciertoHorario = 0;// cantidad de aciertos horarios// es decir cantidad de coincidencias entre la hora actual y un bloque horario definido.
                CachedRowSet data;
                System.out.println("membresia socio en bloque horario " + idMembresiaSocio);
                String query = String.format("SELECT count(id) as id FROM membresia_restriccion_horario WHERE current_time BETWEEN hora_inicio and hora_fin and membresia_id=%s;", idMembresiaSocio);
                data = miDb.sqlDatos(query);
                while (data.next()) {
                    aciertoHorario = data.getInt("id");
                }
                if (aciertoHorario >= 1) {
                    return true;
                } else {
                    sonido.sonar("alerta");
                    mensaje("Lo sentimos", "Usted no puede entrar en este horario, Solicite más información.", TelegraphType.NOTIFICATION_ERROR);
                    return false;
                }
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    private void validadorTiempoGracia(int membresia_id) throws SQLException {
        if (socioEntroHoy()) {
            System.out.println(tieneTiempoGracia());
            if (tieneTiempoGracia() && estaEnFranjaTiempoGracia() != 0) {
                pp.openDoor();
            } else {
                efectuarRegistroEntradaBD(membresia_id);
            }
        } else {
            efectuarRegistroEntradaBD(membresia_id);
        }

    }

    /**
     * Registro en base de datos de la entrada del socio.
     *
     * @param membresia_id
     * @throws SQLException
     */
    public void efectuarRegistroEntradaBD(int membresia_id) throws SQLException {
        boolean exitoso;
        int usuario_sistema = Integer.parseInt(System.getProperty("usuario_sistema"));
        String sql = String.format("INSERT INTO entrada_socio (fecha_hora, socio_id, membresia_id, usuario_sistema_id, fecha_registro) VALUES (now(),%s,%s,%s,now())", socio, membresia_id, usuario_sistema);
        System.out.println("Insert "+sql);
        exitoso = miDb.sqlEjec(sql);

        if (exitoso) {
            pp.openDoor();
            System.out.println("---------PERSISTIDO REGISTRAR ENTRADA---------");
            if (!contadorFlag) {
                mensaje("Bienvenido", "Entrada Registrada Satisfactoriamente.", TelegraphType.NOTIFICATION_DONE);

            }
        } else {
            sonido.sonar("alerta");
            System.out.println("---------NO SE HA PERSISTIDO---------");
            mensaje("Problemas Para Entrar", "Solicite ayuda al administrador.", TelegraphType.NOTIFICATION_ERROR);

        }
    }

    /**
     * Se obtiene el tiempo de gracia de la puerta. Tiempo de gracia: tiempo en
     * el que un socio puede entrar y salir del gimnasio sin persistir en bd su
     * entrada/salida.
     *
     * @return
     * @throws SQLException
     */
    public boolean getTiempoGraciaPuerta() throws SQLException {
        CachedRowSet data;
        String query = "select tiempogracia_puerta, unidad_tiempogracia_puerta from empresa;";
        data = miDb.sqlDatos(query);
        while (data.next()) {
            tiempogracia = data.getString(1);
            unidad_tiempogracia = data.getString(2);
        }
        return unidad_tiempogracia == null && tiempogracia == null;
    }

    /**
     * Se verifica si el socio ya entró hoy.
     *
     * @return
     * @throws java.sql.SQLException
     */
    public boolean socioEntroHoy() throws SQLException {
        CachedRowSet data;
        String query = String.format("SELECT count(id) from entrada_socio where socio_id=%s and date(fecha_hora)=date(now());", socio);
        data = miDb.sqlDatos(query);
        while (data.next()) {
            return data.getInt(1) >= 1;
        }
        return false;
    }

    /**
     * este método comprueba si en este momento el socio esta dentro el tiempo
     * de gracia.
     *
     * @return
     * @throws SQLException
     */
    public Integer estaEnFranjaTiempoGracia() throws SQLException {
        int pivote = -1;
        CachedRowSet data;
        String intervalo = tiempogracia;
        if (unidad_tiempogracia.equals("Minutos")) {
            intervalo += " minutes";
        } else {
            intervalo += " hours";
        }
        String query = String.format("SELECT count(fecha_hora) \n"
                + "FROM entrada_socio\n"
                + "WHERE socio_id=%s \n"
                + "and now()::timestamp \n"
                + "BETWEEN fecha_hora AND fecha_hora+interval '%s' LIMIT 1;", socio, intervalo);
        data = miDb.sqlDatos(query);
        while (data.next()) {
            pivote = data.getInt(1);
        }
        return pivote;
    }

    /**
     * Se verifica si está configurado el tiempo gracia de la puerta.
     *
     * @return boolean
     * @throws SQLException
     */
    public boolean tieneTiempoGracia() throws SQLException {
        CachedRowSet data;
        String query = "select tiempogracia_puerta, unidad_tiempogracia_puerta from empresa;";
        data = miDb.sqlDatos(query);
        while (data.next()) {
            tiempogracia = data.getString(1);
            unidad_tiempogracia = data.getString(2);
        }
        return tiempogracia != null && unidad_tiempogracia != null;
    }

    public String traerNombreSocio(int clave) throws SQLException {
        String primerNombre = null;
        String segundoNombre = null;
        String primerApellido = null;
        String segundoApellido = null;
        String nombreCompleto = null;

        CachedRowSet data;
        String query = String.format("SELECT primer_nombre, segundo_nombre, primer_apellido, segundo_apellido FROM socio WHERE id=%s;", clave);
        data = miDb.sqlDatos(query);
        while (data.next()) {
            try {
                primerNombre = data.getString("primer_nombre");
                segundoNombre = data.getString("segundo_nombre");
                primerApellido = data.getString("primer_apellido");
                segundoApellido = data.getString("segundo_apellido");
            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (primerNombre != null && segundoNombre != null && primerApellido != null && segundoApellido != null) {
            nombreCompleto = String.format("%s %s %s %s ", primerNombre, segundoNombre, primerApellido, segundoApellido);
            System.out.println("Nombre completo : " + nombreCompleto);
        } else {
            nombreCompleto = "";
        }

        return nombreCompleto;

    }

    public int obtenerEntradas(int clave) throws SQLException {
        int entradas = 0;
        CachedRowSet data;
        String query = String.format("SELECT entradas_hoy FROM socio WHERE id=%s;", clave);
        data = miDb.sqlDatos(query);
        while (data.next()) {
            try {
                entradas = data.getInt("entradas_hoy");

            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return entradas;

    }

    public String obtenerEechaEntrada(int clave) throws SQLException {
        String entradas = "";
        CachedRowSet data;
        String query = String.format("SELECT fecha_ultima_enntrada FROM socio WHERE id=%s", clave);
        data = miDb.sqlDatos(query);
        while (data.next()) {
            try {
                entradas = data.getString("fecha_ultima_enntrada");

            } catch (SQLException ex) {
                Logger.getLogger(RegistrarEntrada.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        return entradas;

    }

    public void editarFechaEntrada(int clave) throws SQLException, ParseException {
        String querySQL = "";
        Calendar c1 = Calendar.getInstance();

        String dia = Integer.toString(c1.get(Calendar.DATE));
        String mes = Integer.toString(c1.get(Calendar.MONTH) + 1);
        String anio = Integer.toString(c1.get(Calendar.YEAR));

        String fecha_actual = anio + "-0" + mes + "-" + dia;

        System.out.println(fecha_actual + "esta es la fecha actual del dia de hoy");
        boolean suceses;
        querySQL = String.format("UPDATE socio SET fecha_ultima_enntrada='%s' WHERE id=%s", fecha_actual, clave);
        suceses = db.sqlEjec(querySQL);

        if (suceses) {

            return;

        }

    }

    public void sumarEntrada(int clave, int entada) throws SQLException {

        String querySQL = "";

        boolean suceses;
        querySQL = String.format("UPDATE socio SET entradas_hoy=%s WHERE id=%s", entada, clave);
        suceses = db.sqlEjec(querySQL);

        if (suceses) {

            return;

        }

    }

    public boolean validarentradas(int clave) throws SQLException, ParseException {
        Calendar c1 = Calendar.getInstance();

        String dia = Integer.toString(c1.get(Calendar.DATE));
        String mes = Integer.toString(c1.get(Calendar.MONTH) + 1);
        String anio = Integer.toString(c1.get(Calendar.YEAR));

        String fecha_actual = anio + "-0" + mes + "-" + dia;
        System.out.println("hola socia esta es la actual " + fecha_actual + "esta es la otra entrada " + obtenerEechaEntrada(clave));
        System.out.println(fecha_actual.equals(obtenerEechaEntrada(clave)));
        System.out.println(obtenerEechaEntrada(clave) == null);
        if (obtenerEechaEntrada(clave) == null) {
            editarFechaEntrada(clave);
        } else if (!(fecha_actual.equals(obtenerEechaEntrada(clave)))) {

            editarFechaEntrada(clave);
            sumarEntrada(clave, 0);

        }

        return true;
    }
   

    public boolean validarCongelacion(int clave) throws ParseException, SQLException {
        Calendar c1 = Calendar.getInstance();

        String dia = Integer.toString(c1.get(Calendar.DATE));
        String mes = Integer.toString(c1.get(Calendar.MONTH) + 1);
        String anio = Integer.toString(c1.get(Calendar.YEAR));  
        String fechafin=obtenerFechaCongelacion(clave);
        String fecha_actual = anio + "-0" + mes + "-" + dia;
        System.out.println(fechafin);
        if(fechafin==null||fechafin.equals("")){
            fechafin=fecha_actual;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date1 = sdf.parse(fecha_actual);
        
        Date date2 = sdf.parse(fechafin);

        int v = date1.compareTo(date2);
        if (v == 1) {

            editarCongelado("descongelar", clave);
            return true;

        } else {
            return false;
        }
    }

    public String congelado(int clave) throws SQLException {
        String aux = "";
        CachedRowSet data;
        data = db.sqlDatos("SELECT congelado FROM socio WHERE id =%s ", clave);

        while (data.next()) {
            aux = data.getString("congelado");

        }
        if (aux == null) {
            return "no";
        }
        return aux;
    }

    public String obtenerFechaCongelacion(int clave) throws SQLException {
        String aux = "";
        CachedRowSet data;
        data = db.sqlDatos("SELECT fecha_descongelar FROM socio WHERE socio.id = " + clave);

        while (data.next()) {
            aux = data.getString("fecha_descongelar");

        }
        if (aux == null) {
        }
        return aux;
    }

    public void editarCongelado(String congelado, int clave) {
        String querySQL = "";

        boolean suceses;

        querySQL = String.format("UPDATE socio SET congelado='%s' WHERE id=%s", congelado, clave);
        suceses = db.sqlEjec(querySQL);

        if (suceses) {

            return;

        }

    }
    
}
