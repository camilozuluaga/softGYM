package logica;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.rowset.CachedRowSet;

public class Acceso {

    //Acceso al manejo de Base de datos
    private DB db = new DB();

    /**
     * Verificar con la informacion del Sistema al momento de autenticarse si es
     * un usuario Administrador para evitar hacer verificacion inncesarias
     *
     * @return True si es administrador o False si no lo es.
     */
    public static boolean esAdmin() {
        try {
            String bd = System.getProperty("admin", "false");
            return true ? bd.equals("true") : false;
        } catch (Exception e) {
            return false;
        }
    }

    public static String uid() {
        return System.getProperty("usuario_sistema");
    }

    public boolean crearPermisoMenu(String usuarioId, Object menuId, Object permitir) {
        if (permitir == null) {
            permitir = false;
        }
        String querySQL = String.format("INSERT INTO sistema_menu_usuario(usuario_id,menu_id,permitir) VALUES(%s,%s,%s)", usuarioId, menuId, permitir);
        return db.sqlEjec(querySQL);
    }

    public boolean actualizarPermisoMenu(String usuarioId, Object menuId, Object permitir) {
        if (permitir == null) {
            permitir = false;
        }
        String querySQL = String.format("UPDATE sistema_menu_usuario SET  permitir = %s WHERE usuario_id = %s AND menu_id = %s ", permitir, usuarioId, menuId);
        return db.sqlEjec(querySQL);
    }

    public boolean existePermisoMenu(String usuarioId, Object menuId) {
        int conteo = 0;
        try {
            CachedRowSet data;
            String querySQL = String.format("SELECT COUNT(*) FROM sistema_menu_usuario WHERE usuario_id = %s AND menu_id = %s ", usuarioId, menuId);
            data = db.sqlDatos(querySQL);
            while (data.next()) {
                conteo = data.getInt(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(Acceso.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true ? conteo == 1 : false;
    }

    public boolean menuPermitido(String menu, String menuPadre) {
        boolean esPermitido = false;

        //Menus que se pueden para todos
        List<String> menusGenerales = Arrays.asList("Salir", "Cerrar Sesión", "Registrar Entrada Automática");

        try {
            CachedRowSet data;
            String querySQL = String.format("SELECT permisos.permitir FROM sistema_menu_usuario permisos , sistema_menu menu WHERE permisos.menu_id = menu.id AND usuario_id = %s AND menu.nombre = '%s' AND menu.padre = '%s'", uid(), menu, menuPadre);
            data = db.sqlDatos(querySQL);
            while (data.next()) {
                esPermitido = data.getBoolean("permitir");
            }

        } catch (SQLException ex) {
            Logger.getLogger(Acceso.class.getName()).log(Level.SEVERE, "No se pudo verificar acceso sobre el menu correspondiente", ex);
        }

        if (menusGenerales.contains(menu)) {
            esPermitido = true; //Si menu es generico ( Salir, Entrada Automatica )
        }

        System.out.println("Menu " + menu + " del menu padre "+ menuPadre + " es permitdo? " + esPermitido);
        return esPermitido;
    }
}
