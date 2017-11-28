package help;
/**
 * @web http://www.jc-moue.net/
 * @author Mouse
 */
public class HelpArchivo {
    
    private String Texto;
    private String archivo;

    public String getTexto() {
        return Texto;
    }

    public void setTexto(String Texto) {
        this.Texto = Texto;
    }

    public String getArchivo() {
        return archivo;
    }

    public void setArchivo(String archivo) {
        this.archivo = archivo;
    }

    @Override
    public String toString() {
        return Texto;
    }
    
}