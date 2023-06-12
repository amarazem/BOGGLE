package serveur;

import java.io.Serializable;

public class Score implements Serializable{
    private int valeur;
    private boolean aJour;

    public Score(int v,boolean aJour){
        valeur = v;
        this.aJour = aJour;
    }

    public int getValeur() {
        return valeur;
    }

    public boolean getAJour() {
        return aJour;
    }

    public void setValaur(int valaur) {
        this.valeur = valaur;
    }

    public void setaJour(boolean aJour) {
        this.aJour = aJour;
    }
    
}
