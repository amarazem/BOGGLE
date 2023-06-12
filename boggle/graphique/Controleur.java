package graphique;

import java.awt.Color;
import java.io.File;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.Scanner;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.Timer;
import javax.swing.JButton;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

public class Controleur implements Serializable {
    private Vue v;
    private Modele m;
    private int[] cpt;
    private int tmp = 1;
    private boolean[] tab;

    public boolean multi;
    public int temps;
    boolean aleatoire;
    boolean bonus;
    public transient ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public Controleur(Vue v, Modele m) {
        this.v = v;
        cpt = new int[v.bouttons.length];
        tab = new boolean[v.bouttons.length];
        this.m = m;
        multi = m.getJ().multi;
        if (!multi)
            temps = m.getJ().secondes;
        else
            temps = m.getJ().secondes;
        aleatoire = m.getJ().aleatoire;
        bonus = m.getJ().bonus;
        Runnable runnable = new Runnable() {

            public void run() {
                //minuteur
                    temps--;
                if (String.valueOf(temps % 60).length() == 1) {
                    ((JLabel) v.titreSolo.getComponent(4))
                            .setText("0" + temps / 60 + ":0" + temps % 60);
                } else {
                    ((JLabel) v.titreSolo.getComponent(4))
                            .setText("0" + temps / 60 + ":" + temps % 60);
                }
                //si le minuteur arrive a 1 minute il deviens rouge
                if(temps <= 60){
                    ((JLabel) v.titreSolo.getComponent(4)).setForeground(new Color(0xbb1f09));
                }
                //si le miinuteur arrive a 00:00
                if (temps == 0) {
                        //on met a jour le score du joueur
                        Vue.nouveauScore(m.getJ().getCourant());
                        Vue.nouveauScorePrec(m.getJ().getCourant());
                        miseAJour();
                        if(!multi)
                            v.f.setContentPane(v.jeuTermine());
                        else
                            v.f.setContentPane(v.jeuGagne());
                        scheduler.shutdown();
                }
                
                
                }
                
            
        };
        scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
    }

    public Vue getV() {
        return v;
    }

    public Modele getM() {
        return m;
    }

    //cette fonction sert a ajouter a mot courant la lettre choisie par la souris
    public void bouttonAppuye(int i, JButton b) {
        if (!tab[i]) {
            b.setBackground(new Color(0xe1755e));
            v.motCourant.setText(v.motCourant.getText() + v.bouttons[i].getText());
            tab[i] = true;
            tmp++;
            cpt[tmp] = i;
            
            //On disactive tous les bouttons
            disableAll();
            //on initialise liste par les bouttons que le joueur peut toujours
            //utiliser pour former son mot
            ArrayList<JButton> liste = bouttonAdj(i);

            for (JButton bo : liste) {
                //tous les botton de liste seront clicables
                bo.setEnabled(true);
            }
            tab[i] = true;
        }

    }

    //renvois les bouttons adjacent disponible (peuvent etre utiliser pour former un mot) d'un bouttons
    private ArrayList<JButton> bouttonAdj(int index) {
        ArrayList<JButton> res = new ArrayList<JButton>();
        int taille = v.m.getJ().getG().taille;
        if (index < taille) {
            int i = index % taille;
            int j = taille - 1;

            if (i == 0) {
                res.add(v.bouttons[index + 1]);
                res.add(v.bouttons[index + taille]);
                res.add(v.bouttons[index + taille + 1]);
            } else if (i == j) {
                res.add(v.bouttons[index - 1]);
                res.add(v.bouttons[index + taille]);
                res.add(v.bouttons[index + taille - 1]);
            } else {
                res.add(v.bouttons[index + 1]);
                res.add(v.bouttons[index - 1]);
                res.add(v.bouttons[index + taille]);
                res.add(v.bouttons[index + taille + 1]);
                res.add(v.bouttons[index + taille - 1]);
            }

        } else if (index >= taille * (taille - 1) && index < taille * taille) {
            int i = index % taille;
            int j = taille - 1;

            if (i == 0) {
                res.add(v.bouttons[index + 1]);
                res.add(v.bouttons[index - taille]);
                res.add(v.bouttons[index - taille + 1]);
            } else if (i == j) {
                res.add(v.bouttons[index - 1]);
                res.add(v.bouttons[index - taille]);
                res.add(v.bouttons[index - taille - 1]);
            } else {
                res.add(v.bouttons[index + 1]);
                res.add(v.bouttons[index - 1]);
                res.add(v.bouttons[index - taille]);
                res.add(v.bouttons[index - taille + 1]);
                res.add(v.bouttons[index - taille - 1]);
            }

        } else {
            int i = index % taille;
            int j = taille - 1;
            if (i == 0) {
                res.add(v.bouttons[index + 1]);
                res.add(v.bouttons[index + taille]);
                res.add(v.bouttons[index - taille]);
                res.add(v.bouttons[index + taille + 1]);
                res.add(v.bouttons[index - taille + 1]);
            } else if (i == j) {
                res.add(v.bouttons[index - 1]);
                res.add(v.bouttons[index + taille]);
                res.add(v.bouttons[index - taille]);
                res.add(v.bouttons[index + taille - 1]);
                res.add(v.bouttons[index - taille - 1]);
            } else {
                res.add(v.bouttons[index + 1]);
                res.add(v.bouttons[index - 1]);
                res.add(v.bouttons[index + taille]);
                res.add(v.bouttons[index - taille]);
                res.add(v.bouttons[index + taille + 1]);
                res.add(v.bouttons[index + taille - 1]);
                res.add(v.bouttons[index - taille + 1]);
                res.add(v.bouttons[index - taille - 1]);
            }

        }

        return res;
    }
    //pour les mots qu'on a entrer par la souris
    public void bouttonValider(JButton b) {
        //si le mot est incorrect
        if (!m.getJ().getG().arbre.motDansArbre(v.motCourant.getText())){
            v.infoMot.setText("Mot incorrect!");
            hideInfo();
            v.infoMot.setForeground(new Color(0xbb1f09));
            File file = new File("musiques/incorrect.wav");
           
            
            if (file.exists()) {
                Clip c;
               try {                
                   c = AudioSystem.getClip();
                   c.open(AudioSystem.getAudioInputStream(file));
                   c.start();
               } catch (Exception e1) {
                   // TODO Auto-generated catch block
                   e1.printStackTrace();
               }
               
            }
        //si le mot a deja etait trouver
        }else if( m.getJ().getMotsTrouves().contains(v.motCourant.getText()))  {
            v.infoMot.setText("Déja trouvé!");
            hideInfo();
            v.infoMot.setForeground(new Color(0xbb1f09));
             File file = new File("musiques/incorrect.wav");
           
            
         if (file.exists()) {
             Clip c;
            try {               
                c = AudioSystem.getClip();
                c.open(AudioSystem.getAudioInputStream(file));
                c.start();
            } catch (Exception e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
            
         }
        
            
        }else{
            if(bonus){
                v.infoMot.setText("Mot correct! ("+m.getJ().calculPointsMotBonus(cpt, tmp)+" points)");
            }
            else{
                v.infoMot.setText("Mot correct! ("+m.getJ().calculPointsMot(v.motCourant.getText())+" points)");
            }
           
            hideInfo();
            v.infoMot.setForeground(new Color(0x188706));
            m.getJ().getMotsTrouves().add(v.motCourant.getText());
           
            if(bonus){
                m.getJ().getJ1().setScore( m.getJ().getJ1().getScore() + m.getJ().calculPointsMotBonus(cpt, tmp));
            }
            else{
                m.getJ().getJ1().setScore( m.getJ().getJ1().getScore() + m.getJ().calculPointsMot(v.motCourant.getText()));
            }
            
            
            
            actualiserJoueur();
            if(aleatoire)
            changeLetters();
        }
        enableAll();
        couleurOrigine();
        v.motCourant.setText("");
        tmp = 1;
        mettreAFalse();
     //  boutons();

        b.setText("Valider");

    }
    
    //pour les mots qu'on a entrer par clavier
    public void bouttonValider1() {
        if (m.getJ().getMotsTrouves().contains(v.entrerMot.getText())){
            v.infoMot.setText("Déja trouvé!");
            hideInfo();
            v.infoMot.setForeground(new Color(0xbb1f09));
            File file = new File("musiques/incorrect.wav");
            
            if (file.exists()) {
                Clip c;
               try {                
                   c = AudioSystem.getClip();
                   c.open(AudioSystem.getAudioInputStream(file));
                   c.start();
               } catch (Exception e1) {
                   // TODO Auto-generated catch block
                   e1.printStackTrace();
               }
               
            }

        }else if(!m.getJ().getG().arbre.motDansArbre(v.entrerMot.getText())){
            v.infoMot.setText("mot inexistant!");
            hideInfo();
            v.infoMot.setForeground(new Color(0xbb1f09));
            File file = new File("musiques/incorrect.wav");
            
            if (file.exists()) {
                Clip c;
               try {                
                   c = AudioSystem.getClip();
                   c.open(AudioSystem.getAudioInputStream(file));
                   c.start();
               } catch (Exception e1) {
                   // TODO Auto-generated catch block
                   e1.printStackTrace();
               }
               
            }

        }else if(!m.getJ().getG().boardContains(v.entrerMot.getText().toUpperCase()))  {
            v.infoMot.setText("mot inexistant!");
            hideInfo();
            v.infoMot.setForeground(new Color(0xbb1f09));
                    File file = new File("musiques/incorrect.wav");
            
                    if (file.exists()) {
                        Clip c;
                       try {                
                           c = AudioSystem.getClip();
                           c.open(AudioSystem.getAudioInputStream(file));
                           c.start();
                       } catch (Exception e1) {
                           // TODO Auto-generated catch block
                           e1.printStackTrace();
                       }
                       
                    }
            
           
        }else{
            if(bonus){
                v.infoMot.setText("Mot correct! ("+m.getJ().calculPointsMotBonusClavier(m.getJ().getG().list)+" points)");
            }
            else{
                v.infoMot.setText("Mot correct! ("+m.getJ().calculPointsMot(v.motCourant.getText())+" points)");
            }
            hideInfo();
            v.infoMot.setForeground(new Color(0x188706));
            m.getJ().getMotsTrouves().add(v.entrerMot.getText());
           
            if(bonus){
                m.getJ().getJ1().setScore( m.getJ().getJ1().getScore() + m.getJ().calculPointsMotBonusClavier(m.getJ().getG().list));
            }
            else{
                m.getJ().getJ1().setScore( m.getJ().getJ1().getScore() + m.getJ().calculPointsMot(v.motCourant.getText()));
            }

            
                actualiserJoueur();
                if(aleatoire)
                    changeLettersKeyboard();
        }
        enableAll();
        couleurOrigine();
        v.entrerMot.setText("");
        tmp = 0;
        mettreAFalse();
       

    }
    
    //actualise le nombre de points
    private void actualiserJoueur() {
        ((JLabel) v.titreSolo.getComponent(2))
                .setText(m.getJ().getJ1().getNom() + " (" + m.getJ().getJ1().getScore() + " points)");
    }

    private void enableAll() {
        for (int i = 0; i < v.bouttons.length; i++) {
            v.bouttons[i].setEnabled(true);
        }
    }

    private void disableAll() {
        for (int i = 0; i < v.bouttons.length; i++) {
            v.bouttons[i].setEnabled(false);
        }
    }

    //pour revenir en arriére
    public void bouttonAnnuler(JButton b) {

        // bouttons[cpt[tmp]].setEnabled(true);
        // tmp--;
        disableAll();
        if (tmp > 0) {
            v.bouttons[cpt[tmp]].setBackground(new Color(0x3ddbf7));
            tab[cpt[tmp]] = false;
            ArrayList<JButton> liste = bouttonAdj(cpt[tmp - 1]);
            tmp--;
            for (JButton bo : liste) {
                bo.setEnabled(true);
            }
        }

        if (v.motCourant.getText().length() >= 1) {
            v.motCourant.setText(v.motCourant.getText().substring(0, v.motCourant.getText().length() - 1));
        }
        if (v.motCourant.getText().length() == 0) {
            enableAll();
            couleurOrigine();
            v.Annuler = false;
        }

        b.setText("annuler");
    }
    
    //remplis la grille 
    public void remplirGrille() {
        int cpt = 0;
        for (int i = 0; i < m.getJ().getG().getGrille().length; i++) {
            for (int k = 0; k < m.getJ().getG().getGrille().length; k++) {
                v.bouttons[cpt].setText(v.bouttons[cpt].getText() + m.getJ().getG().getGrille()[i][k].getLettre());
                if(bonus)
                    v.bouttons[cpt].bonus = m.getJ().getG().getGrille()[i][k].pts;
                cpt++;
            }
        }
    }

    private void mettreAFalse() {
        for (int i = 0; i < tab.length; i++) {
            tab[i] = false;
        }
    }

    public void couleurOrigine() {
        for (int i = 0; i < v.bouttons.length; i++) {
            v.bouttons[i].setBackground(new Color(0x3ddbf7));

        }
    }

    public void miseAJour(){
        ArrayList<String> list = new ArrayList<String>();
		File doc = new File("baseDeDonnees/pseudos.txt");
		Scanner sc;
		try {
			sc = new Scanner(doc);
			while (sc.hasNext()) {
                String[] tab = sc.next().split(",");
                if(m.getJ().getCourant().getNom().equals(tab[0])){
                    tab[1] = Integer.toString(Integer.parseInt(tab[1])+m.getJ().getCourant().getScore());
                    tab[2] = Integer.toString(Integer.parseInt(tab[2])+m.getJ().getMotsTrouves().size());
                }
				list.add(tab[0]+","+tab[1]+","+tab[2]);
			}

			Path file = Paths.get("baseDeDonnees/pseudos.txt");
			Files.write(file, list, StandardCharsets.UTF_8);
			sc.close();

		} catch (Exception e) {

		}
    }

    public void boutons(){
        v.Valider = false;
       v.Annuler = false;
       v.titreSolo.remove(v.titreSolo.getComponent(v.titreSolo.getComponentCount()-1));
       v.titreSolo.remove(v.titreSolo.getComponent(v.titreSolo.getComponentCount()-1));
    }

    public void hideInfo(){
        Timer timer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                   v.infoMot.setText("");
                } catch (Exception ex) {
                    ex.printStackTrace();;
                   
                }
            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void changeLetters(){

        for (int i = tmp; i > 1; i--) {
            char c = generateLetter();
            v.bouttons[cpt[i]].setText(Character.toString(c));
            if(bonus) { 
                Random r = new Random();
                v.bouttons[cpt[i]].bonus = r.nextInt(3)+1;
            }
            int indice = cpt[i]/m.getJ().getG().taille;
            m.getJ().getG().setG(indice, cpt[i]%m.getJ().getG().taille, c);
            v.tableau.repaint();
        }   
        
    }

    public void changeLettersKeyboard(){
        for (int i = 0; i<m.getJ().getG().list.size(); i++) {
            char c = generateLetter();
            int x = m.getJ().getG().list.get(i).getPos().getXCoord();
            int y = m.getJ().getG().list.get(i).getPos().getYCoord();
            int taille = m.getJ().getG().taille;
            v.bouttons[x*taille+y].setText(Character.toString(c));
            if(bonus){
                Random r = new Random();
                v.bouttons[x*taille+y].bonus = r.nextInt(3)+1;
            }
            m.getJ().getG().setG(x, y, c);
            v.tableau.repaint();
        }   
    }

    public char generateLetter(){
        ArrayList<Character> liste1 = new ArrayList<Character>();
		liste1.add('J'); liste1.add('K'); liste1.add('Q'); liste1.add('W'); liste1.add('X'); liste1.add('Y'); liste1.add('Z');
		
		ArrayList<Character> liste2 = new ArrayList<Character>();
		 liste2.add('B'); liste2.add('C'); liste2.add('F'); liste2.add('G'); liste2.add('H'); liste2.add('P'); liste2.add('V'); 
		
		ArrayList<Character> liste3 = new ArrayList<Character>();
		liste3.add('D'); liste3.add('M'); 
		
		ArrayList<Character> liste6 = new ArrayList<Character>();
		liste6.add('N'); liste6.add('O'); liste6.add('R'); liste6.add('S'); liste6.add('T'); liste6.add('U'); 

		Random r = new Random();
		int rand = r.nextInt(100);
        char c;
		if(rand<15) {
			c ='E';
		}else if(rand<24) {
			c = 'A';
		}else if(rand<32) {
			c = 'I';
		}else if(rand<68) {
			Collections.shuffle(liste6);
			c = liste6.get(0);
		}else if(rand<73) {
			c = 'L';
		}else if(rand<79) {
			Collections.shuffle(liste3);
			c = liste3.get(0);
		}else if(rand<93) {
			Collections.shuffle(liste2);
			c = liste2.get(0);
		}else {
			Collections.shuffle(liste1);
			c = liste1.get(0);
		}
        return c;
	}

}
