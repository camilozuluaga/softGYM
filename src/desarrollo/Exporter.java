/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package desarrollo;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;

/**
 *
 * @author IdlhDeveloper
 */
public class Exporter {

    private File file;
    private List<JTable> tabla;
    private List<String> nom_files;

    public Exporter(File file, List<JTable> tabla, List<String> nom_files) throws Exception {
        this.file = file;
        this.tabla = tabla;
        this.nom_files = nom_files;
        if (nom_files.size() != tabla.size()) {
            throw new Exception("Error");
        }
    }

    public boolean exports() {
        try {
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            WritableWorkbook w = Workbook.createWorkbook(out);
            for (int index = 0; index < tabla.size(); index++) {
                JTable table = tabla.get(index);
                WritableSheet s = w.createSheet(nom_files.get(index), 0);
                for (int i = 0; i < table.getColumnCount(); i++) {
                    for (int j = 0; j < table.getRowCount(); j++) {
                        Object object = table.getValueAt(j, i);
                        s.addCell(new Label(i, j, String.valueOf(object)));
                    }
                }
            }
            w.write();
            w.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
public boolean export(){
        try{
            DataOutputStream out = new DataOutputStream(new FileOutputStream(file));
            WritableWorkbook w = Workbook.createWorkbook(out);
            for(int index = 0; index < tabla.size(); index++){
                JTable jtable = this.tabla.get(index);
                WritableSheet s = w.createSheet(nom_files.get(index), 0);
                for(int k = 0; k < jtable.getColumnCount(); k++){
                    s.addCell(new Label(k, 0, jtable.getColumnName(k)));
                }              
                for(int i = 0; i < jtable.getColumnCount(); i++){
                    for(int j = 0; j < jtable.getRowCount(); j++){
                        Object obj = jtable.getValueAt(j, i);
                        s.addCell(new Label(i, j+1, String.valueOf(obj)));
                    }
                }
            }
            w.write();
            w.close();
            out.close();
            return true;
        } catch(IOException | WriteException e){
            return false;
        }

    }


}
