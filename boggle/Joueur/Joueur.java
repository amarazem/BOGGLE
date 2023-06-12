package Joueur;

import graphique.*;
//import serveur.*;

import java.io.Serializable;
//import java.lang.ModuleLayer.Controller;
import java.io.*;
import java.net.*;

import javax.swing.JButton;

public class Joueur implements Serializable {

	private String nom;
	private int score;
	private Controleur control;
	private Jeu jeu;
	private PlayerServeurConnexion csc;
	private int playerID;


	public Joueur() {
		nom = "";
	}

	public Joueur(String n) {
		nom = n;
		score = 0;
		control = null;
	}
	public Joueur(String n , int s) {
		nom = n;
		score = s;
		control = null;
	}

	public Joueur(Controleur control) {
		this.control = control;
		nom = "";
	}

	public Joueur(String n, Controleur control) {
		nom = n;
		score = 0;
		this.control = control;
	}

	public void connexion() {
		csc = new PlayerServeurConnexion();
	}

	public int getPlayerID() {
		return playerID;
	}

	public void setPlayerID(int playerID) {
		this.playerID = playerID;
	}

	public Jeu getJeu() {
		return jeu;
	}

	public void setJeu(Jeu jeu) {
		this.jeu = jeu;
	}

	public Controleur getControl() {
		return control;
	}

	public PlayerServeurConnexion getCsc() {
		return csc;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getNom() {
		return nom;
	}

	public int getScore() {
		return score;
	}

	public void setControl(Controleur control) {
		this.control = control;
	}

	public void selectionnerUneLettre(int i, JButton b) {
		control.bouttonAppuye(i, b);
	}

	public void validerUneReponseAvecBoutton(JButton b) {
		control.bouttonValider(b);
	}

	public void validerUneReponseAvecClavier() {
		control.bouttonValider1();
	}

	public void RevenirEnarriereBoutton(JButton b) {
		control.bouttonAnnuler(b);
	}

	public class PlayerServeurConnexion implements Serializable {
		transient private Socket s;
		transient private DataInputStream dataIn;
		transient private DataOutputStream dataOut;
		private Jeu jeu;

		public DataInputStream getDataIn() {
			return dataIn;
		}

		public DataOutputStream getDataOut() {
			return dataOut;
		}

		public void setDataIn(DataInputStream dataIn) {
			this.dataIn = dataIn;
		}

		public void setDataOut(DataOutputStream dataOut) {
			this.dataOut = dataOut;
		}

		public Jeu getJeu() {
			return jeu;
		}

		public void setJeu(Jeu jeu) {
			this.jeu = jeu;
		}

		public PlayerServeurConnexion() {
			try {
				s = new Socket("localhost", 22222);
				dataIn = new DataInputStream(s.getInputStream());
				dataOut = new DataOutputStream(s.getOutputStream());
				
				playerID = dataIn.readInt();
				System.out.println("Connecter au serveur comme joueur." + playerID + "(" + nom + ")");
				//dataOut.writeObject(jeu);
			} catch (Exception e) {
				e.getStackTrace();
			}
		}
	}

}
