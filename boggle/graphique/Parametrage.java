package graphique;

import Joueur.*;

import javax.swing.*;
import javax.swing.text.TabExpander;

import java.awt.*;
import java.awt.event.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import grille.*;

public class Parametrage implements ActionListener, Serializable {
    JPanel parametrage = new JPanel(new FlowLayout(FlowLayout.CENTER));
    JPanel parametrageSolo = new JPanel();
    JPanel debut = new JPanel();
    public JFrame f = new JFrame();
    private int playerID;
    private int playerMulti;
    private Jeu jeu;
    public String prenom;

    public Parametrage() {
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(220, 200);
        f.setLocationRelativeTo(null);
        f.setResizable(false);
        f.getContentPane().setBackground(new Color(0x3ddbf7));
        f.setLayout(null);
        ImageIcon icone = new ImageIcon("src/icone.jpg");
		f.setIconImage(icone.getImage());
        f.setVisible(true);

        parametrage.setBounds(500, 0, 500, 800);
        parametrage.setLocation(500, 0);

        parametrageSolo.setLocation(500, 0);
        


        // parametres.add(saisieNom2, new Rectangle(0,6,6,1));
        JLabel taille = new JLabel("Veuillez choisir la taille");
        // parametres.add(taille, new Rectangle(0,8,6,1));
        JSpinner saisieTaille = new JSpinner(new SpinnerNumberModel(4, 4, 8, 1));
        saisieTaille.setValue(6);

        JLabel temps1 = new JLabel("Choisissez (en minutes) la durée de la partie");
        JSpinner saisieTemps1 = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
        saisieTemps1.setValue(1);

        // parametres.add(saisieTaille, new Rectangle(0,7,6,1));
        JLabel l = new JLabel("Veuillez choisir la langue");
        JComboBox<String> langue = new JComboBox<String>();
        langue.addItem("Français");
        langue.addItem("Anglais");
        langue.addItem("Espagnol");

        JButton ok = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String l = "";
                switch ((String) langue.getSelectedItem()) {
                    case "Français":
                        l = "Fr";
                        break;
                    case "Anglais":
                        l = "En";
                        break;
                    case "Espagnol":
                        l = "Es";
                        break;
                }
                Joueur player = new Joueur(prenom);
                player.setPlayerID(playerID);
                System.out.println();
                player.connexion();
                
                playerID = player.getPlayerID();
                //player.setJeu(j);
                //player.getCsc().setJeu(j);
                //jeu = player.getCsc().getJeu();
                String titre = "";
                if (playerID == 1) {
                    titre = "player #" + playerID + "(" + player.getNom() + ")";
                    f.setTitle(titre);
                    playerMulti = 2;
                    jeu = new Jeu(player, new Joueur("test"), (Integer) saisieTaille.getValue(), (Integer) saisieTemps1.getValue()*60 +5, l);
                    try {
                        ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("serveur/jeuMulti.bin"));
                        oos.writeObject(jeu);
                        oos.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                    
                } else if (playerID == 2) {
                    titre = "player #" + playerID + "(" + player.getNom() + ")";
                    f.setTitle(titre);
                    playerMulti = 1;
                    try {
                        ObjectInputStream ois = new ObjectInputStream(new FileInputStream("serveur/jeuMulti.bin"));     
                        jeu = (Jeu)ois.readObject();
                        jeu.setJ1(player);
                        jeu.getJ1().setPlayerID(playerID);
                        ois.close();
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }
                if(playerID == 2){
                    Start s = new Start(jeu,titre);
                    f.dispose();
                    try {
                        ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream("serveur/confirmation.bin"));
                        oos2.writeObject(true);
                        oos2.close();
                        //System.out.println("ici");
                    } catch (Exception e1) {
                        e1.printStackTrace();
                    }
                }else if(playerID == 1){
                    f.setTitle("En attente d'un adversaire ...");
                    while(true){
                        try {
                            ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream("serveur/confirmation.bin"));     
                            boolean b = (Boolean)ois2.readObject();
                            ois2.close();
                            if(b){
                                jeu.setJ2(player);    
                                Start s = new Start(jeu,titre);
                                f.dispose();  
                                ObjectOutputStream oos3 = new ObjectOutputStream(new FileOutputStream("serveur/confirmation.bin"));
                                oos3.writeObject(false);
                                oos3.close();
                                break;
                            }
                        } catch (Exception e1) {
                            
                        }
                    }
                }
                

            }

        });
        ok.setText("Valider");
        
        parametrage.add(new JLabel("Choisissez vos paramètres de jeu"));
        parametrage.add(taille);
        parametrage.add(saisieTaille);
        parametrage.add(temps1);
        parametrage.add(saisieTemps1);
        parametrage.add(l);
        parametrage.add(langue);
        parametrage.add(ok);

       

        JLabel taille1 = new JLabel("Veuillez choisir la taille de la grille");
        taille1.setForeground(new Color(0x203961));
        JSpinner saisieTaille1 = new JSpinner(new SpinnerNumberModel(4, 4, 8, 1));
        saisieTaille1.setValue(6);

       

        // parametres.add(saisieTaille, new Rectangle(0,7,6,1));
        JLabel temps = new JLabel("Choisissez (en minutes) la durée de la partie");
        temps.setForeground(new Color(0x203961));
        JSpinner saisieTemps = new JSpinner(new SpinnerNumberModel(1, 1, 6, 1));
        saisieTemps.setValue(3);
        JLabel l1 = new JLabel("Veuillez choisir la langue");
        l1.setForeground(new Color(0x203961));
        JComboBox<String> langue1 = new JComboBox<String>();
        langue1.addItem("Français");
        langue1.addItem("Anglais");
        langue1.addItem("Espagnol");
        JLabel mode = new JLabel("Selectionnez votre mode de jeu");
        mode.setForeground(new Color(0x203961));
        JRadioButton oui = new JRadioButton("aleatoire");
        oui.setForeground(new Color(0x203961));
        JRadioButton non = new JRadioButton("normal");
        non.setForeground(new Color(0x203961));
        JLabel bonus = new JLabel("Voulez-vous activer les bonus de lettres?");
        bonus.setForeground(new Color(0x203961));
        JRadioButton ouiBonus = new JRadioButton("oui");
        ouiBonus.setForeground(new Color(0x203961));
        JRadioButton nonBonus = new JRadioButton("non");
        nonBonus.setForeground(new Color(0x203961));
        JButton ok1 = new JButton(new AbstractAction() {
            public void actionPerformed(ActionEvent e) {
                String l = "";
                switch ((String) langue1.getSelectedItem()) {
                    case "Français":
                        l = "Fr";
                        break;
                    case "Anglais":
                        l = "En";
                        break;
                    case "Espagnol":
                        l = "Es";
                        break;
                }
                Jeu j = new Jeu(new Joueur(prenom), (Integer) saisieTaille1.getValue(), false,
                     (Integer) saisieTemps.getValue()*60, l, oui.isSelected(), ouiBonus.isSelected());
                Start s = new Start(j);
                //Dictionnaire.afficheMot11(s.v.m.getJ().getLangue(), s.v.m.getJ().getG());
                f.dispose();

            }

        });
        ok1.setText("Commencer la partie");
        
        parametrageSolo.add(new JLabel());
        parametrageSolo.add(new JLabel());
        parametrageSolo.add(new JLabel());
        parametrageSolo.add(taille1);
        parametrageSolo.add(saisieTaille1);
        parametrageSolo.add(temps);
        parametrageSolo.add(saisieTemps);
        parametrageSolo.add(l1);
        parametrageSolo.add(langue1);
        parametrageSolo.add(mode);
        parametrageSolo.add(oui);
        parametrageSolo.add(non);
        parametrageSolo.add(bonus);
        parametrageSolo.add(ouiBonus);
        parametrageSolo.add(nonBonus);
        parametrageSolo.add(ok1);
        

       
        JLabel debutPartie = new JLabel("BOGGLE");
        debutPartie.setFont(new Font("Consolas", 1, 15));

        JLabel nom = new JLabel("Saisissez votre prénom: ");
        nom.setFont(new Font("Consolas", 1, 15));
        JTextField saisieNom = new JTextField();
        saisieNom.setPreferredSize(new Dimension(100, 30));
        saisieNom.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if(e.getKeyCode() == 10) {
					prenom = saisieNom.getText();
                    ((JLabel)parametrageSolo.getComponent(0)).setText("BIENVENU(E), " + prenom);
                    ((JLabel)parametrageSolo.getComponent(1)).setText("Total des points obtenus: " + membreExists(prenom)[1]);
                    ((JLabel)parametrageSolo.getComponent(2)).setText("Total des mots trouvés: " + membreExists(prenom)[2]);
                    f.setContentPane(parametrageSolo);
                    //f.setSize(350, 200);
                    f.setSize(450, 600);
                    rearrangement();
                    f.setLocationRelativeTo(null);
				}
               
			}
		
		 public void keyTyped(KeyEvent e) {
		 }
		
		 public void keyPressed(KeyEvent e) {
            if(e.getKeyCode() == 32) {
                prenom = saisieNom.getText();
            f.setContentPane(parametrage);
            f.setSize(450, 500);
            rearrangementMulti();

            }
		 }
		});

        JButton solo = new JButton(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                prenom = saisieNom.getText();
                ((JLabel)parametrageSolo.getComponent(0)).setText("BIENVENU(E), " + prenom);
                ((JLabel)parametrageSolo.getComponent(1)).setText("Total des points obtenus: " + membreExists(prenom)[1]);
                ((JLabel)parametrageSolo.getComponent(2)).setText("Total des mots trouvés: " + membreExists(prenom)[2]);
                
               
                f.setContentPane(parametrageSolo);
                f.setSize(450, 500);
               // f.setSize(350, 200);
               rearrangement();
            }

        });
        solo.setText("Solo");
        solo.setBackground(new Color(0xff6235));
        solo.setFont(new Font("Consolas", 1, 15));
        

        JButton multi = new JButton(new AbstractAction() {

            @Override
            public void actionPerformed(ActionEvent e) {
                prenom = saisieNom.getText();
                f.setContentPane(parametrage);
                f.setSize(550, 600);
                rearrangementMulti();
            }

        });
        multi.setText("Multijoueur");
        multi.setBackground(new Color(0xffd10b)); 
        multi.setFont(new Font("Consolas", 1, 15));
           

        debut.add(debutPartie);
        debut.add(nom);
        debut.add(saisieNom);
        debut.add(multi);
        debut.add(solo);
        debut.setBackground( new Color(0x3ddbf7));
        f.setContentPane(debut);
        System.out.println(multi);
        // System.out.println("debut partie: " + debutPartie.getX() + " " + debutPartie.getY() + " " + debutPartie.getWidth() + " " + debutPartie.getHeight());
        // System.out.println("nom: " + nom.getX() + " " + nom.getY() + " " + nom.getWidth() + " " + nom.getHeight());
        // System.out.println("saisie: " + saisieNom.getX() + " " + saisieNom.getY() + " " + saisieNom.getWidth() + " " + saisieNom.getHeight());
        // System.out.println("solo: " + multi.getX() + " " + multi.getY() + " " + multi.getWidth() + " " + multi.getHeight());
        // System.out.println("multi: " + solo.getX() + " " + solo.getY() + " " + solo.getWidth() + " " + solo.getHeight());
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub

    }

    public String[] membreExists(String nom){
        File doc = new File("baseDeDonnees/pseudos.txt");
        Scanner sc;
        try {
            sc = new Scanner(doc);
            while(sc.hasNext()){
                String[] tab = sc.next().split(",");
                if(nom.equals(tab[0])) return tab;
            }
        } catch (Exception e) {
    
        }
        ajoutNouveauMembre(nom);
        return new String[] {nom,"0","0"};
    }

    public void ajoutNouveauMembre(String nom){
        File doc = new File("baseDeDonnees/pseudos.txt");
        Scanner sc;
        try {
            sc = new Scanner(doc);
            ArrayList<String> liste = new ArrayList<String>();
            while(sc.hasNext()){
                liste.add(sc.next());
            }
            liste.add(nom+",0,0");
            Path file = Paths.get("baseDeDonnees/pseudos.txt");
			Files.write(file, liste, StandardCharsets.UTF_8);
        } catch (Exception e) {
            //TODO: handle exception
        }
    }

    public void rearrangement(){
        f.getContentPane().setBackground(new Color(0x3ddbf7));
        parametrageSolo.setLayout(null);
        parametrageSolo.getComponent(0).setBounds(115, 30, 335, 20);
        parametrageSolo.getComponent(0).setFont(new Font("Consolas", 1, 20));
       ((JLabel)parametrageSolo.getComponent(0)).setForeground(new Color(0x203961));
        parametrageSolo.getComponent(1).setBounds(100, 50, 300, 20);
        ((JLabel)parametrageSolo.getComponent(1)).setForeground(new Color(0x203961));
        parametrageSolo.getComponent(2).setBounds(105, 70, 290, 20);
        ((JLabel)parametrageSolo.getComponent(2)).setForeground(new Color(0x203961));
        parametrageSolo.getComponent(3).setBounds(50, 135, 330, 20);
        parametrageSolo.getComponent(4).setBounds(175, 160, 50, 25);
        parametrageSolo.getComponent(5).setBounds(50, 185, 370, 20);
        parametrageSolo.getComponent(6).setBounds(175, 205, 50, 25);
        parametrageSolo.getComponent(7).setBounds(100, 230, 220, 20);
        parametrageSolo.getComponent(8).setBounds(150, 255, 100, 25);
        parametrageSolo.getComponent(9).setBounds(100, 290, 300, 25);
        parametrageSolo.getComponent(10).setBounds(80, 325, 100, 25);
        parametrageSolo.getComponent(11).setBounds(220, 325, 100, 25);
        parametrageSolo.getComponent(12).setBounds(80, 360, 400, 25);
        parametrageSolo.getComponent(13).setBounds(80, 395, 100, 25);
        parametrageSolo.getComponent(14).setBounds(220, 395, 100, 25);
        parametrageSolo.getComponent(15).setBounds(110, 455, 200, 50);
        parametrageSolo.getComponent(15).setBackground(new Color(0xff6235));
        parametrageSolo.getComponent(15).setForeground(Color.WHITE);
      //  parametrageSolo.getComponent(9)
        for (int i = 1; i < parametrageSolo.getComponentCount(); i++) {
            parametrageSolo.getComponent(i).setFont(new Font("Consolas", 1, 15));
            
        }
    }

    public void rearrangementMulti(){
        f.getContentPane().setBackground(new Color(0x3ddbf7));
        parametrage.setLayout(null);
        parametrage.getComponent(0).setBounds(20, 50, 430, 20);
        parametrage.getComponent(0).setFont(new Font("Consolas", 1, 20)); 
        parametrage.getComponent(1).setBounds(100, 115, 300, 20);
        parametrage.getComponent(2).setBounds(175, 140, 50, 25);
        parametrage.getComponent(3).setBounds(40, 165, 375, 20);
        parametrage.getComponent(4).setBounds(175, 190, 50, 25);
        parametrage.getComponent(5).setBounds(100, 215, 370, 20);
        parametrage.getComponent(6).setBounds(150, 240, 100, 25);
        parametrage.getComponent(7).setBounds(100, 290, 220, 50);
        parametrage.getComponent(7).setBackground(new Color(0xffd10b));
        parametrage.getComponent(7).setForeground(Color.BLACK);
      //  parametrageSolo.getComponent(9)
        for (int i = 1; i < parametrage.getComponentCount(); i++) {
            parametrage.getComponent(i).setFont(new Font("Consolas", 1, 15));
            
        }
    }

    

}
