package graphique;

import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class RoundedPanel extends JPanel
{
private Color backgroundColor;
private int cornerRadius = 15;
private int dessin;


public RoundedPanel(int radius, int dessin) {
super();
cornerRadius = radius;
this.dessin = dessin;
}
public RoundedPanel(int radius, Color bgColor) {
super();
cornerRadius = radius;
backgroundColor = bgColor;
}

public static BufferedImage changeSize(String inImg, int w, int h)
throws IOException 
{
     
   // lit l'image d'entrée
     File f = new File(inImg);
     BufferedImage inputImage = ImageIO.read(f);

     // crée l'image de sortie
     BufferedImage img = new BufferedImage(w, h, inputImage.getType());

     // balancer l'image d'entrée à l'image de sortie
     Graphics2D g = img.createGraphics();
     g.drawImage(inputImage, 0, 0, w, h, null);
     g.dispose();

     // extrait l'extension du fichier de sortie
    return img;
}
@Override
protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    Dimension arcs = new Dimension(cornerRadius, cornerRadius);
    int width = getWidth();
    int height = getHeight();
    Graphics2D graphics = (Graphics2D) g;
    graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    //Draws the rounded panel with borders.
    if (backgroundColor != null) {
    graphics.setColor(backgroundColor);
    } else {
    graphics.setColor(getBackground());
    }
    graphics.fillRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint background
    graphics.setColor(new Color(0, 0, 0, 0 ));
    graphics.drawRoundRect(0, 0, width-1, height-1, arcs.width, arcs.height); //paint border

    switch (dessin) {
        case 0:
        try { //etoiles
            for (int i = 0; i < 10; i++) {
                g.drawImage(changeSize("src/etoile.png", 20, 20), (int)(this.getWidth()*270/300), (int)(this.getHeight()/11+this.getHeight()*48*i/550), null);
            }
        } catch (Exception e) {
            //TODO: handle exception
        }
            
            break;
    
        case 1: //chrono
        try {
            // BufferedImage inputImg = ImageIO.read(new File("src/chronometre.png"));
               
            // // crée l'image de sortie
            // BufferedImage outputImg = new BufferedImage(50, 50, inputImg.getType());
        
            // // balancer l'image d'entrée à l'image de sortie
            // Graphics2D g2 = outputImg.createGraphics();
            // g2.drawImage(inputImg, 0, 0, 50, 50, null);
            // g2.dispose();
            
            g.drawImage(changeSize("src/chronometre.png", this.getWidth()*50/1550, this.getHeight()/2), (int)(this.getWidth()*1250/1550), (int)(this.getHeight()*19/100), null);
           
            
        } catch (Exception e) {
            //TODO: handle exception
        }
            break;

        default: break;
    }
}
}
