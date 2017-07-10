

package VisorDatos;

import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author gaso
 */
public class ListaCampos extends JPanel {

    private List<Campo> campos;

    public ListaCampos(String campos) {
        this.campos = new ArrayList<Campo>();

        String[] field = campos.split("\\|\\|");
        for (String f : field) {
            String[] n = f.split("==");
            Campo c1 = new Campo(n[0], n[1]);
            addItem(c1);
        }

    }

    private void addItem(Campo entry) {
        campos.add(entry);
        add(entry);
        revalidate();
    }

}
