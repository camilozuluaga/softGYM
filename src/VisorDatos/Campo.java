/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package VisorDatos;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 *
 * @author gaso
 */
public class Campo extends JPanel{
    private JLabel label;
    private JTextField textField;
    
    public Campo(String labelText, String fieldText){
        this.label = new JLabel(labelText);
        this.textField = new JTextField(fieldText, fieldText.length());
        this.textField.setEditable(false);
        add(this.label);
        add(this.textField);
    }
}
