package grille;

import java.io.Serializable;

public class Arbre implements Serializable{

	private Noeud racine;
	
	public Arbre() {
		racine=null;
	}
	
	public Arbre(Noeud r) {
		racine = r;
	}
	
	public Noeud getRacine() {
		return racine;
	}
	
	public boolean isEmpty() {
		return racine == null;
	}
	
	public void afficher() {
		if(isEmpty()) 
			return;
		racine.afficher();
		System.out.println();
	}
	
	public void ajouterMot(String s) {
		if(isEmpty()) {
			racine = new Noeud(s.charAt(0),false);
		}
		racine.ajouterMot(s);
	}

	public boolean motDansArbre(String s){
		if(isEmpty() || s.isEmpty())
			return false;
		return racine.motDansArbre(s);
	}
	public void Tri_arbre() {
		if(isEmpty()) 
			return;
		racine.Tri_arbre();
	}

	private class Noeud implements Serializable{
		private char lettre;
		private boolean motTermine;
		private Noeud fg;
		private Noeud fd;
		
		public Noeud(char l,boolean t) {
			this(l,t,null,null);
		}
		
		public Noeud(char l,boolean t,Noeud fg,Noeud fd) {
			this.lettre = l;
			this.motTermine = t;
			this.fg=fg;
			this.fd=fd;
		}
		
		public char getLettre() {
			return lettre;
		}
		
		public boolean getMotTermine() {
			return motTermine;
		}
		
		public Noeud getFd() {
			return fd;
		}
		
		public Noeud getFg() {
			return fg;
		}
		
		public void afficher() {
			if(fg!=null)
				fg.afficher();
			System.out.print(lettre);
			//if(motTermine) System.out.println();
			if(fd!=null)
				fd.afficher();
		}
		
		public void ajouterMot(String s) {
	
			if(s.length()==1 && s.charAt(0) == this.lettre){
				this.motTermine=true;
				return;
			}
	
			if(s.charAt(0)==this.lettre){
				if(fd==null){
					fd = new Noeud(s.charAt(1),false);
				}
				fd.ajouterMot(s.substring(1));
			}else{
				if(fg==null){
					fg = new Noeud(s.charAt(0),false);
				}
				fg.ajouterMot(s);
			}
			
		}
	
		public boolean motDansArbre(String s){
			if(s.length()==1  && s.charAt(0) == this.lettre){
				if(motTermine) return true;
				return false;
			}
			
	
			if(s.charAt(0)==this.lettre){
				if(fd==null){
					return false;
				}
				return fd.motDansArbre(s.substring(1));
			}else{
				if(fg==null){
					return false;
				}
				return fg.motDansArbre(s);
			}
	
		}

		public void Tri_arbre(){
			if(this.fg.lettre<this.lettre){
				Noeud tmp = this; 
				this.fg.fg = this.fg;
				this.fg = tmp;
			}
			if(this.fg != null)
				fg.Tri_arbre();
			else
				return;
			if(this.fd != null)
				fd.Tri_arbre();
			else 
				return;
		}
	
	}
	
}
