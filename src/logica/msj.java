/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import net.sf.jcarrierpigeon.WindowPosition;
import net.sf.jtelegraph.Telegraph;
import net.sf.jtelegraph.TelegraphQueue;
import net.sf.jtelegraph.TelegraphType;

/**
 *
 * @author gaso
 */
public class msj {

    /**
     * Mostrar Burbuja de Notificacion
     * @param titulo Titulo de la Notificacion
     * @param mensaje Mensaje de Notificacion
     * @param tipo Tipo de Notificacion
     * @param duracion tiempo que estara la notificacion en la pantalla
     */
    public static void show(String titulo, String mensaje, TelegraphType tipo, int duracion) {
        Telegraph tele = new Telegraph(titulo, mensaje, tipo, WindowPosition.TOPRIGHT, duracion);
        TelegraphQueue q = new TelegraphQueue();
        q.add(tele);
        System.gc();
    }

    /**
     * Mostrar Burbuja de Notificacions
     * @param titulo Titulo de la Notificacion
     * @param mensaje Mensaje de Notificacion
     * @param tipo Tipo de Notificacion
     */
    public static void show(String titulo, String mensaje, TelegraphType tipo) {
        show(titulo, mensaje, tipo, 9000);
    }

}
