
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 *
 * @author jucazuse
 */
public class Reportes  {

    String separador = System.getProperty("file.separator");
    String directorio = System.getProperty("user.dir");
    String contenedorJasper = "/reporte/";
    String ruta = directorio.concat(separador).concat(contenedorJasper);

    public void reporte(String archivoJasper, int veces, int idFactura, String titulo, String nombrePDF) {
        try {
            
            Map parametro = new HashMap();
            parametro.put("factura_id", idFactura);
            URL in = this.getClass().getResource("/reporte/headerfoooter.jasper");
            JasperReport reporte = (JasperReport) JRLoader.loadObject(in);
            JasperPrint jasperprint = JasperFillManager.fillReport(reporte, parametro, Conexion.getConexion());
            for (int i = 1; i <= veces; i++) {
                JasperPrintManager.printPage(jasperprint, 0, false);
            }

        } catch (JRException ex) {
            Logger.getLogger(Reportes.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
