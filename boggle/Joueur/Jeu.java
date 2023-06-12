package Joueur;

import java.io.Serializable;
import java.util.ArrayList;

import grille.Grille;
import grille.Lettre;

public class Jeu implements Serializable {
	private Grille g;
	private Joueur j1;
	private Joueur j2;
	
	private String langue;
	public boolean multi; // true pour multijoueur et false pour solo
	public int secondes;
	public boolean aleatoire;
	public boolean bonus;

	private ArrayList<String> motTrouves = new ArrayList<String>();

	public ArrayList<String> getMotsTrouves() {
		return motTrouves;
	}

	public Grille getG() {
		return g;
	}

	public String getLangue() {
		return langue;
	}

	public Joueur getJ1() {
		return j1;
	}

	public Joueur getJ2() {
		return j2;
	}

	public void setJ1(Joueur j1) {
		this.j1 = j1;
	}

	public void setJ2(Joueur j2) {
		this.j2 = j2;
	}

	public Joueur getCourant() {
		return courant;
	}

	private Joueur courant;

	public Jeu(Joueur joueur1, Joueur joueur2, int taille, int s, String l) {
		g = new Grille(taille, l);
		j1 = joueur1;
		j2 = joueur2;
		courant = j1;
		multi = true;
		secondes = s;
		langue = l;
	}

	public Jeu(Joueur joueur1, int taille, boolean m, int s, String l, boolean a, boolean b) {
		g = new Grille(taille ,l);
		j1 = joueur1;
		courant = j1;
		multi = m;
		secondes = s;
		langue = l;
		aleatoire = a;
		bonus = b;
	}

	public void setCourant() {
		if (courant == j1)
			courant = j2;
		else
			courant = j1;
	}


	public Joueur joueurGagnant() {
		if (j1.getScore() >= j2.getScore()) {
			return j1;
		} else  {
			return j2;
		}

	}
	public int calculPointsMotBonus(int[] cpt, int tmp){
		int res = 0;
		for (int i = tmp; i >1; i--) {
			res += g.getGrille()[cpt[i]/g.taille][cpt[i]%g.taille].pts;
		}
		return res;


	}

	public int calculPointsMotBonusClavier(ArrayList<Lettre> list){
		int res = 0;
		for (Lettre l: list) {
			res += l.pts;
		}
		return res;


	}

	public int calculPointsMot(String s){
		int l = s.length();
		switch (l) {
			case 2:
				return 1;
			case 3:
				return 1;
			case 4:
				return 1;
			case 5:
				return 2;
			case 6:
				return 3;
			case 7:
				return 5;
			default:
				return 11;
		}
	}
}
