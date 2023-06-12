package grille;

import java.io.Serializable;

public class Lettre implements Serializable{
	private char lettre;
	public int pts;
	private Coordonnees pos;
	
	public Coordonnees getPos() {
		return pos;
	}
	public Lettre(char c, int x, int y, int p) {
		lettre = c;
		pos= new Coordonnees(x,y);
		
		pts= p;
	}
	public char getLettre() {
		return lettre;
	}
	public void affiche() {
		System.out.println("contenu: "+ lettre+ ". Points: "+ pts + ". Coordonnees: "+ pos.getXCoord()+ ", " + pos.getYCoord() );
	}

}
