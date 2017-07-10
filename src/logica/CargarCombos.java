/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;
import javax.swing.JComboBox;

/**
 *
 * @author Usuario
 */
public class CargarCombos {
    private DB db = new DB();

    public void llenarCombo(JComboBox cboCombo, int hasta) {
        cboCombo.addItem("");
        for (int i = 1; i <= hasta; i++) {
            cboCombo.addItem(i);
        }
    }

    public void llenarComboUnidad(JComboBox cboUnidad) {
        cboUnidad.addItem("");
        cboUnidad.addItem("Dias");
        cboUnidad.addItem("Semanas");
        cboUnidad.addItem("Meses");
        cboUnidad.addItem("Años");
        cboUnidad.addItem("Visitas");
    }
    
    public void llenarComboUnidad(JComboBox cboCombo,String itemUno,String itemDos){
        cboCombo.addItem("");
        cboCombo.addItem(itemUno);
        cboCombo.addItem(itemDos);
    }

    public void cargarCombo(String identificacion){
        CachedRowSet data;
        String querySQL=String.format("SELECT identificacion FROM usuario_sistema WHERE estado=true AND identificacion='%s';",identificacion);
        data = db.sqlDatos(querySQL);
        try {
            
            while(!data.next()){
                System.out.println("la cedula no existe");
            }
        } catch (SQLException ex) {
            Logger.getLogger(CargarCombos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void horas(JComboBox cboHora) {
        final Calendar c = Calendar.getInstance();
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        int mes = Calendar.MONTH;
        int anio = Calendar.YEAR;
        c.clear();
        int i = 1;
        cboHora.addItem("");
        for (c.set(anio, mes, 1, 0, 0, 0); i <= 96; c.add(Calendar.MINUTE, 15)) {
            cboHora.addItem(format.format(c.getTime()));
            i++;
        }

    }
    
    
}
