/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.image.BufferedImage;
import javax.swing.border.Border;

/**
 *
 * @author BRAYHAN JARAMILLO
 */
public class Fondo implements Border{
    private BufferedImage image;
    
    public Fondo(BufferedImage image){
        this.image= image;
    }
    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        int x0=x+ (width - image.getWidth())/2;
        int y0=y+ (height - image.getHeight())/2;
        g.drawImage(image, x0, y0, null);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(0, 0, 0, 0);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}
