package grille;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
public class Dictionnaire implements Serializable{
	
	//initialise l'arbre lexicographique de la partie
	public static Arbre arbreLexicographique(String langue) {
		File doc = new File("dictionnaires/dictionnaire"+langue+".txt");
		Arbre abr = new Arbre();
		Scanner sc;
		try {
			sc = new Scanner(doc);
			while(sc.hasNext()) {
				String next = sc.next();
				if(next.length()>2)
				abr.ajouterMot(next);
			}
		} catch (FileNotFoundException e) {
			System.out.println("erreur");
		}
		
		return abr;
	}

	// public static Arbre afficheMot11(String langue,Grille g) {
    //     File doc = new File("dictionnaires/dictionnaire"+langue+".txt");
    //     Arbre abr = new Arbre();
    //     Scanner sc;
    //     try {
    //         sc = new Scanner(doc);
    //         while(sc.hasNext()) {
    //             String next = sc.next();
    //             if(g.boardContains(next) && next.length() >7)
    //                 System.out.println(next);
    //         }
    //     } catch (FileNotFoundException e) {
    //         System.out.println("erreur");
    //     }
        
    //     return abr;
    // }

}
