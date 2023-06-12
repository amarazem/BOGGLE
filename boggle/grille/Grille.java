package grille;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
//import Joueur.*;

public class Grille implements Serializable{

	public ArrayList<Lettre> list = new ArrayList<Lettre>();
	private Lettre[][] grille;
	public Arbre arbre;
	public int taille;
	public String l;

	public Grille(int t,String langue){
		this(t);
		this.l = langue;
		arbre= Dictionnaire.arbreLexicographique(this.l);
	}

	
	public Grille(int t) {
		taille = t;
		grille = new Lettre[taille][taille];
		/*
		 * REPARTIR LES LISTES PAR RAPPORT AU NOMBRES D'OCCCURENCES DES LETTRES
		 * LES LETTRES AVEC LA MEME OCCURENCE SERONT DANS UNE MEME LISTE
		 */
		ArrayList<Character> liste1 = new ArrayList<Character>();
		liste1.add('J'); liste1.add('K'); liste1.add('Q'); liste1.add('W'); liste1.add('X'); liste1.add('Y'); liste1.add('Z');
		
		ArrayList<Character> liste2 = new ArrayList<Character>();
		 liste2.add('B'); liste2.add('C'); liste2.add('F'); liste2.add('G'); liste2.add('H'); liste2.add('P'); liste2.add('V'); 
		
		ArrayList<Character> liste3 = new ArrayList<Character>();
		liste3.add('D'); liste3.add('M'); 
		
		ArrayList<Character> liste6 = new ArrayList<Character>();
		liste6.add('N'); liste6.add('O'); liste6.add('R'); liste6.add('S'); liste6.add('T'); liste6.add('U'); 
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille[0].length; j++) {
				Random r = new Random();
				int rand = r.nextInt(100);
				if(rand<15) {
					grille[i][j] = new Lettre('E', i, j, r.nextInt(3)+1);
				}else if(rand<24) {
					grille[i][j] = new Lettre('A', i, j, r.nextInt(3)+1);
				}else if(rand<32) {
					grille[i][j] = new Lettre('I', i, j, r.nextInt(3)+1);
				}else if(rand<68) {
					Collections.shuffle(liste6);
					grille[i][j] = new Lettre(liste6.get(0), i, j, r.nextInt(3)+1);
				}else if(rand<73) {
					grille[i][j] = new Lettre('L', i, j, r.nextInt(2)+1);
				}else if(rand<79) {
					Collections.shuffle(liste3);
					grille[i][j] = new Lettre(liste3.get(0), i, j, r.nextInt(3)+1);
				}else if(rand<93) {
					Collections.shuffle(liste2);
					grille[i][j] = new Lettre(liste2.get(0), i, j, r.nextInt(3)+1);
				}else {
					Collections.shuffle(liste1);
					grille[i][j] = new Lettre(liste1.get(0), i, j, r.nextInt(3)+1);
				}
			}
		}
	
	}
	
	public Grille(Lettre[][] tab) {
		grille = tab;
	}

	public Lettre[][] getGrille() {
		return grille;
	}
	
	public void afficher() {
		System.out.print("  ");
		for (int i = 0; i < grille.length; i++) {
			System.out.print(""+i + "|");
		}
		System.out.println();
		for(int i=0;i<grille.length;i++) {
			System.out.print(i + "|");
			for(int j=0;j<grille[i].length;j++) {
				System.out.print(grille[i][j].getLettre()+  " " + grille[i][j].pts + "|");
			}
			System.out.println();
		}
	}

	// renvois vrai si le mot existe dans la grille
	public boolean boardContains(String mot) {
			list.removeAll(list);
		if (mot.isEmpty()){
			list.removeAll(list);
			return false;
		}
		// Try each tile as the start of the word
		for (int i = 0; i < grille.length; i++) {
			for (int j = 0; j < grille.length; j++) {
				boolean foundWord = motDansLaGrille(i, j, 0, mot);
				//System.out.println(foundWord);
				if (foundWord)
					return true;
			}
		}
		return false;
	}
	
	boolean motDansLaGrille(int i, int j, int wordIndex, String mot){
		if(i<0 || i>grille.length-1 || j<0 || j>grille.length-1){
			list.removeAll(list);
			return false;
		}
			

		if(grille[i][j].getLettre() != mot.charAt(wordIndex)){
			list.removeAll(list);
			return false;
		}else{
			list.add(grille[i][j]);
		}

		if(wordIndex == mot.length()-1)
			return true;
		
		Lettre oldLetter = grille[i][j];
		grille[i][j] = new Lettre('*',i,j, 0);
		boolean foundWord = 
			motDansLaGrille(i-1, j-1,  wordIndex+1, mot)  || 
			motDansLaGrille(i-1, j,  wordIndex+1, mot)  || 
			motDansLaGrille(i-1, j+1,  wordIndex+1, mot)  || 
			motDansLaGrille(i, j-1,  wordIndex+1, mot)  || 
			motDansLaGrille(i, j+1, wordIndex+1, mot) || 
			motDansLaGrille(i+1, j-1, wordIndex+1, mot) || 
			motDansLaGrille(i+1, j, wordIndex+1, mot) ||
			motDansLaGrille(i+1, j+1, wordIndex+1, mot);
		
		// Fix the tile.
		grille[i][j] = oldLetter;
		list.add(grille[i][j]);
		
		return foundWord;
	}
	
	public int nbMotsDansGrille() {
		int cpt = 0;
		String res = "dictionnaires/dictionnaireFr.txt";
		System.out.println(res);
		File doc = new File(res);
		Scanner sc;
		try {
			sc = new Scanner(doc);
			while(sc.hasNext()) {
				String s = sc.next();
				if(boardContains(s)) {
					System.out.println(s);
					cpt++;
				}
			}
		} catch (FileNotFoundException e) {
			System.out.println("erreur1");
			return 0;
		}
		sc.close();
		return cpt;
		
	}

	public void setG(int i, int j,  char c) {
		Random r = new Random();
		Lettre l = new Lettre(c, i, j, r.nextInt(3)+1);
		grille[i][j] = l;
	}

}
