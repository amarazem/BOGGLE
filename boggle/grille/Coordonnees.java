package grille;

import java.io.Serializable;

/**
	* Cette classe modelise la position d'une lettre dans la grille
	*/

public class Coordonnees implements Serializable{

	private int xCoord;
	private int yCoord;
		
		
		
	public Coordonnees(int x, int y) {
		xCoord = x;
		yCoord = y;
	}
		

	/**
	 * retourne l'abcisse
	 */
	public int getXCoord() {
		return xCoord;
	}
	
	/**
	 * retourne l'ordonn�e
	 */
	public int getYCoord() {
		return yCoord;
	}
	public void setXcoord(int x) {
		xCoord = x;
	}
		
	public void setYcoord(int y) {
		yCoord = y;
	}
	
}
