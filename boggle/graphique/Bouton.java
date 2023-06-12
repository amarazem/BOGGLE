package graphique;

import javax.swing.JButton;
import java.awt.*;
import java.io.IOException;
public class Bouton extends JButton{
    public int bonus;
    public boolean utilise;
    public Bouton(){
        bonus = 0;
        utilise = true;
    }
    public void paint(Graphics g) 
{       
    
    Graphics2D g2 = (Graphics2D) g.create();
    g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) 0.5));
    super.paint(g2);
    g2.dispose();
    try {
        if(bonus>=1){
            g.drawImage(RoundedPanel.changeSize("src/Etoile-Rouge.png", 20, 20), getWidth()-30, getHeight()-30, null);
        }
        
        if(bonus>=2){
            g.drawImage(RoundedPanel.changeSize("src/Etoile-Rouge.png", 20, 20), getWidth()-60, getHeight()-30, null);
        }
        if(bonus == 3){
            g.drawImage(RoundedPanel.changeSize("src/Etoile-Rouge.png", 20, 20), getWidth()-90, getHeight()-30, null);
        }
    } catch (IOException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
    }
}
}
