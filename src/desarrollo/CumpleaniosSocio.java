/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author capriatto
 */
public class CumpleaniosSocio {

    /**
     * Esta funcion permite conocer si un socio tiene fecha de naciemiento
     * @param socioId
     * @return boolean
     * @throws SQLException
     */
    public boolean tieneFechaNacimiento(int socioId) throws SQLException {
        Date fecha = null;
        String consulta = String.format("SELECT fecha_nacimiento FROM socio WHERE socio.id=%s; ", socioId);
        ResultSet rs = new logica.DB().sqlDatos(consulta);
        while (rs.next()) {
            fecha = rs.getDate(1);
        }
        return fecha != null;
    }

    /**
     * Esta funcion permite conocer la fecha de nacimiento del socio
     * @param socioId
     * @return Date
     */
    public Date fechaNacimientoSocio(int socioId) {
        Date fechaNacimiento = null;
        try {
            String query = String.format("SELECT fecha_nacimiento FROM socio WHERE id='%s';", socioId);
            ResultSet rs = new logica.DB().sqlDatos(query);
            while (rs.next()) {
                fechaNacimiento = rs.getDate(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(CumpleaniosSocio.class.getName()).log(Level.SEVERE, null, ex);
        }
        return fechaNacimiento;
    }

    /**
     * Esta funcion permite saber si un socio cumple anios hoy
     * @param socioId
     * @return boolean
     * @throws SQLException
     */
    public boolean cumpleañosHoy(int socioId) throws SQLException {
        Date fechaNacimientoSocio = null;
        Date fechaHoy = null;
        String fechaNacimientoFormateada = null;
        String fechaHoyFormateada = null;

        if (tieneFechaNacimiento(socioId)) {
            //obteniendo fecha de nacimiento y convirtiendola a string para manipularla.
            fechaNacimientoSocio = fechaNacimientoSocio(socioId);
            String fechaNacimientoString = fechaNacimientoSocio.toString();

            //formateando fecha de nacimiento del socio a formato dia-mes ejemplo:(22-12)
            String mesNacimiento = fechaNacimientoString.substring(5, 7);
            String diaNacimiento = fechaNacimientoString.substring(8, 10);
            fechaNacimientoFormateada = diaNacimiento + '-' + mesNacimiento;

            //formateando fecha de hoy a dia-mes ejemplo:(19-10)
            fechaHoy = new Date();
            SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
            String fechaHoyString = formato.format(fechaHoy);
            String mesActual = fechaHoyString.substring(5, 7);
            String diaActual = fechaHoyString.substring(8, 10);
            fechaHoyFormateada = diaActual + '-' + mesActual;
        }
        // retorna true solamente si la fecha de nacimiento es igual a la fecha de hoy.
        return fechaNacimientoFormateada.equals(fechaHoyFormateada);
    }

    /**
     * Esta funcion permite saber si un socio cumple anios maniana
     * @param socioId
     * @return boolean
     * @throws SQLException
     */
    public boolean cumpleañosManiana(int socioId) throws SQLException {
        String fechaAyerFormateada = null;
        String fechaNacimientoFormateada = null;
        Date fechaNacimientoSocio = null;

        //obteniendo la fecha de maniana
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, +1);
        Date fechaAyer = cal.getTime();
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        String ayer = formato.format(fechaAyer);
        //fin obteniendo fecha de ayer

        //formateando fecha de maniana
        String mesDiaAyer = ayer.substring(5, 7);
        String diaManiana = ayer.substring(8, 10);
        fechaAyerFormateada = diaManiana + '-' + mesDiaAyer;
        //fin formateado
        
        //obteniendo fecha nacimiento del socio y convirtiendola a string
        fechaNacimientoSocio = fechaNacimientoSocio(socioId);
        String fechaNacimientoString = fechaNacimientoSocio.toString();

        //formateando fecha de nacimiento del socio a formato dia-mes ejemplo:(22-12)
        String mesNacimiento = fechaNacimientoString.substring(5, 7);
        String diaNacimiento = fechaNacimientoString.substring(8, 10);
        fechaNacimientoFormateada = diaNacimiento + '-' + mesNacimiento;

        return fechaAyerFormateada.equals(fechaNacimientoFormateada);
    }
}
