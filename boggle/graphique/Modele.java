package graphique;

import java.io.Serializable;
import Joueur.Jeu;

public class Modele implements Serializable {
    private Jeu j;

    public Modele(Jeu j) {
        this.j = j;
    }

  
    public Jeu getJ() {
        return j;
    }

    public void setJ(Jeu j) {
        this.j = j;
    }

}
