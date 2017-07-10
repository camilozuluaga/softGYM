/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.util.ResourceBundle;

/**
 *
 * @author gaso
 */
public class p {

    public static String getParamConfig(String clave) {
        //Obtener Archivo de Propiedades de Configuracion
        ResourceBundle p = ResourceBundle.getBundle("configuracion");
        return p.getString(clave);
    }
}
