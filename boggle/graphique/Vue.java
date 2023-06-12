package graphique;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;
import javax.swing.border.*;


import Joueur.*;
import serveur.Score;
import java.awt.*;
import java.awt.image.*;



import java.awt.event.*;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import java.util.Arrays;
import java.util.Scanner;

public class Vue implements ActionListener, Serializable {
	public JFrame f = new JFrame();
	
	public Start start;
	public Modele m;
	public Controleur c;
	JPanel tableau = new JPanel();
	public RoundedPanel  titreSolo = new RoundedPanel(20, 1);
	JLabel motCourant = new JLabel();
	JLabel infoMot = new JLabel();
	public JTextField entrerMot = new JTextField();
	Bouton[] bouttons;
	JButton valider;
	JButton annuler;
	boolean Valider = false;
	boolean Annuler = false;
	JPanel victoire = new JPanel();
	Joueur Player;
	Font font = new Font("Arial", 1, 20);
	Joueur playerMulti;
	public transient Clip clip;

	public Vue jeu() {
		return this;
	}

	public Vue(Modele m) {
		
		//lance la musique
		

		this.m = m;
		bouttons = new Bouton[m.getJ().getG().getGrille().length * m.getJ().getG().getGrille().length];
		c = new Controleur(this, m);
		
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		if(!c.multi){
			f.addWindowListener(new WindowAdapter() {
				public void windowClosing(WindowEvent e) {
	
					int reponse = JOptionPane.showConfirmDialog(f,
							"Voulez-vous sauvegarder la partie?",
							"Confirmation",
							JOptionPane.YES_NO_OPTION,
							JOptionPane.QUESTION_MESSAGE);
					if (reponse == JOptionPane.YES_OPTION) {
						ObjectOutputStream oos = null;
						try {
							oos = new ObjectOutputStream(new FileOutputStream("vue.txt"));
							oos.writeObject(jeu());
						} catch (IOException e1) {
							e1.printStackTrace();
						} finally {
							try {
								oos.close();
							} catch (Exception e2) {
								System.out.println("erreur2");
							}
						}
						f.dispose();
						clip.stop();
					}
	
				}
			});
		}
		f.setVisible(true);

		f.pack();
		JFrame.setDefaultLookAndFeelDecorated(true);
		f.setExtendedState(Frame.MAXIMIZED_BOTH);
		
		ImageIcon icone = new ImageIcon("src/icone.jpg");
		f.setIconImage(icone.getImage());
		f.setLocationRelativeTo(null);
		f.setLayout(null);
		f.setVisible(true);

		File file = new File("musiques/musique.wav");
		if (file.exists()) {

			try {
				if(!c.multi){
				clip = AudioSystem.getClip();
				clip.open(AudioSystem.getAudioInputStream(file));
				clip.setLoopPoints(clip.getFramePosition(), clip.getFrameLength() - 1);
				clip.loop(5);
				clip.start();}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}


		entrerMot.setBorder(new LineBorder(new Color(0x203961)));

		entrerMot.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				//touche entrer
				if (e.getKeyCode() == 10) {
					c.bouttonValider1();
				}
				JTextField textField = (JTextField) e.getSource();
				String text = textField.getText();
				textField.setText(text.toUpperCase());
				textField.setFont(new Font("Consolas", 1, 22));
			}

			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {

			}
		});

		
		
		titreSolo.setLayout(null);
		titreSolo.setBackground(Color.WHITE);
		titreSolo.setBounds(0, (int)(f.getHeight()*700/838),f.getWidth(), (int)((f.getHeight()*100)/838));
		

		
			entrerMot.setBounds((int)(f.getWidth()/2-25), 0, (int)(titreSolo.getWidth()*120/1550), (int)(titreSolo.getHeight()*30/100));
			// System.out.println((int)(titreSolo.getWidth()*120/1550) + "   " + (int)(titreSolo.getHeight()*30/100));
			// System.out.println(entrerMot.getX() +"    "+ entrerMot.getY() + "   " +entrerMot.getWidth() +"    "+ entrerMot.getHeight());
			titreSolo.add(entrerMot);
			infoMot.setForeground(new Color(0x203961 ));
			infoMot.setFont(new Font("Consolas", Font.BOLD, 20));
			infoMot.setBounds(entrerMot.getX()+entrerMot.getWidth()+(int)(titreSolo.getWidth()*100/1550), 0, (int)(titreSolo.getWidth()*300/1550), (int)titreSolo.getHeight()*30/100);
			titreSolo.add(infoMot);
			JLabel joueur = new JLabel(m.getJ().getJ1().getNom() + " (" + m.getJ().getJ1().getScore() + " points)");
			joueur.setForeground(new Color(0x203961 ));
			joueur.setBounds((int)(titreSolo.getWidth()*20/1550), (int)(titreSolo.getHeight()/4), (int)(titreSolo.getWidth()*300/1550), (int)(titreSolo.getHeight()/4));
			joueur.setFont(new Font("Consolas", Font.BOLD, 20));
			titreSolo.add(joueur);
			//compoSolo.add(new KComposant(joueur, new Rectangle(0, 0, 6, 1)));
			motCourant.setForeground(new Color(0x203961));
			motCourant.setFont(new Font("Consolas", Font.BOLD, 20));
			JPanel courant = new JPanel();
			courant.setBackground(Color.WHITE);
			courant.add(motCourant);
			courant.setBounds((int)(titreSolo.getWidth()*700/1550), (int)(titreSolo.getHeight()*55/100), (int)(titreSolo.getWidth()*250/1550), (int)titreSolo.getHeight()/2);
			titreSolo.add(courant);
			JLabel temps = new JLabel("0" + c.temps / 60 + ":0" + c.temps % 60);
			
			temps.setForeground(new Color(0x203961 ));
			temps.setFont(new Font("Consolas", Font.BOLD, 50));
			temps.setBounds((int)(titreSolo.getWidth()*1320/1550), (int)(titreSolo.getHeight()/4), (int)(titreSolo.getWidth()*200/1550), (int)titreSolo.getHeight()/2);
			
			titreSolo.add(temps);
		
		tableau = new Fond("src/bord-min.png");
		tableau.setBackground(Color.WHITE);
		tableau.setLayout(new GridLayout(m.getJ().getG().getGrille().length, m.getJ().getG().getGrille().length));
		tableau.setBounds(0, 0, f.getWidth(), titreSolo.getY());

		for (int i = 0; i < bouttons.length; i++) {
			bouttons[i] = new Bouton();
			tableau.add(bouttons[i]);

			
			bouttons[i].setBackground(new Color(0x3ddbf7));
			bouttons[i].setForeground(Color.BLACK);
			bouttons[i].setFont(new Font("Consolas", Font.BOLD, 50));
			bouttons[i].setFocusable(false);
			bouttons[i].addActionListener(this);
		}
		c.remplirGrille();

		
		
		f.add(titreSolo);
		f.add(tableau);
	}

	void setM(Modele modele) {
		m = modele;
	}

	

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println(c.temps);
		for (int i = 0; i < bouttons.length; i++) {
			if (e.getSource() == bouttons[i]) {
				valider = new JButton(new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						c.bouttonValider(valider);
						
					}
				});
				if (!this.Valider) {
					valider.setText("Valider");
				
						valider.setFont(new Font("Consolas", Font.BOLD, 20));
						valider.setBounds((int)(titreSolo.getWidth()*950/1550), (int)(titreSolo.getHeight()/2), (int)(titreSolo.getWidth()*150/1550),  (int)(titreSolo.getHeight()*40/100));
						titreSolo.add(valider);
					
						
					this.Valider = true;
				}
				valider.setBackground(new Color(0x188706));
				annuler = new JButton(new AbstractAction() {
					public void actionPerformed(ActionEvent e) {
						c.bouttonAnnuler(annuler);
						
					}
				});

				if (!this.Annuler) {
					annuler.setText("Annuler");
					
						annuler.setFont(new Font("Consolas", Font.BOLD, 20));
						annuler.setBounds((int)(titreSolo.getWidth()*550/1550), (int)(titreSolo.getHeight()/2), (int)(titreSolo.getWidth()*150/1550),  (int)(titreSolo.getHeight()*40/100));
						titreSolo.add(annuler);
					
						
					this.Annuler = true;
				}
				annuler.setBackground(new Color(0xbb1f09));
				c.bouttonAppuye(i, bouttons[i]);

				
				

			}
		}
	}


	public void setC(Controleur c) {
		this.c = c;
	}
	//fenetre de fin pour le mutijoueur
	public JPanel jeuGagne() {

		f.getContentPane().removeAll();
		f.getContentPane().setBackground(Color.WHITE);

		JPanel victoire = new JPanel();
		// victoire.setBackground(Color.PINK);

		if (m.getJ().getJ1().getPlayerID() == 1) {
			try {
				ObjectOutputStream oos1 = new ObjectOutputStream(new FileOutputStream("serveur/score1.bin"));
				Score score = new Score(m.getJ().getJ1().getScore(), true);
				oos1.writeObject(score);
				oos1.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else if (m.getJ().getJ1().getPlayerID() == 2) {
			try {
				ObjectOutputStream oos2 = new ObjectOutputStream(new FileOutputStream("serveur/score2.bin"));
				Score score = new Score(m.getJ().getJ1().getScore(), true);
				oos2.writeObject(score);
				oos2.close();
				Thread.sleep(1000);
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}

		int score1 = 0;
		int score2 = 0;
		if (m.getJ().getJ1().getPlayerID() == 1) {
			score1 = m.getJ().getJ1().getScore();
		} else if (m.getJ().getJ1().getPlayerID() == 2) {
			score2 = m.getJ().getJ1().getScore();
		}
		if (m.getJ().getJ1().getPlayerID() == 1) {

			try {
				ObjectInputStream ois1 = new ObjectInputStream(new FileInputStream("serveur/score2.bin"));

				score2 = ((Score) ois1.readObject()).getValeur();
				ObjectOutputStream oos3 = new ObjectOutputStream(new FileOutputStream("serveur/score2.bin"));
				Score score = new Score(m.getJ().getJ1().getScore(), false);
				oos3.writeObject(score);
				oos3.close();
				ois1.close();
				// break;

			} catch (Exception e1) {
				e1.printStackTrace();
			}

		} else if (m.getJ().getJ1().getPlayerID() == 2) {
			try {
				ObjectInputStream ois2 = new ObjectInputStream(new FileInputStream("serveur/score1.bin"));
				score1 = ((Score) ois2.readObject()).getValeur();
				ObjectOutputStream oos4 = new ObjectOutputStream(new FileOutputStream("serveur/score1.bin"));
				Score score = new Score(m.getJ().getJ1().getScore(), false);
				oos4.writeObject(score);
				oos4.close();
				ois2.close();
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

		JLabel v = null;
		if (m.getJ().getJ1().getPlayerID() == 1) {
			if (score1 > score2)
				v = new JLabel("Vous avez gagné!");
			else if (score1 < score2)
				v = new JLabel("Vous avez perdu!");
			else
				v = new JLabel("Match nul!");
		} else if (m.getJ().getJ1().getPlayerID() == 2) {
			if (score1 > score2)
				v = new JLabel("Vous avez perdu!");
			else if (score1 < score2)
				v = new JLabel("Vous avez gagné!");
			else
				v = new JLabel("Match nul!");
		}

			v.setFont(new Font("Consolas", 1, 30));
			v.setForeground(Color.WHITE);
			victoire.add(v);
			
			

			

			JPanel score = new JPanel();
			JLabel jl = new JLabel("");
			if(m.getJ().getJ1().getPlayerID() == 1){
				 jl.setText("Score obtenu  " + score1); 
			}else{
				jl.setText("Score obtenu  " + score2); 
			}

			
			jl.setFont(new Font("Consolas", 1, 30));
			jl.setForeground(Color.WHITE);
			score.add(jl);

		JPanel motsTrouves = new RoundedPanel(20, 2);
			motsTrouves.setOpaque(true);
		
			motsTrouves.add(new JLabel(String.format("%"+(int)(f.getWidth()*35/1550)+"s", "Mots trouvés")));
			motsTrouves.add(new JLabel(" "));
			if (m.getJ().getMotsTrouves().size() == 0)
				motsTrouves.add(new JLabel(String.format("%"+(int)(f.getWidth()*31/1550)+"s","Aucun mot trouvé")));
			if (m.getJ().getMotsTrouves().size() != 0){
				motsTrouves.add(new JLabel("                   Mot                       Points"));
				motsTrouves.add(new JLabel("   "));
			}
			
			for (int i = 0; i < m.getJ().getMotsTrouves().size(); i++) {
				motsTrouves.add(new JLabel("                   "+
						 String.format("%-"+(int)(f.getWidth()*28/1550)+"s",m.getJ().getMotsTrouves().get(i).toLowerCase())+ m.getJ().calculPointsMot(m.getJ().getMotsTrouves().get(i))));
				motsTrouves.add(new JLabel(" "));
			}
			motsTrouves.setLayout(new BoxLayout(motsTrouves, BoxLayout.Y_AXIS));
			motsTrouves.getComponent(0).setFont(new Font("Consolas", 1, 25));
			motsTrouves.getComponent(0).setForeground(Color.WHITE);
			for (int i = 1; i < motsTrouves.getComponentCount(); i++) {
				motsTrouves.getComponent(i).setFont(new Font("Consolas", 1, 20));
				motsTrouves.getComponent(i).setForeground(Color.WHITE);
				
			}
			JScrollPane j = new JScrollPane(motsTrouves, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

	
					
		JPanel button = new JPanel();
			//button.setBorder(new RoundedPanelExample(Color.BLACK,2,16,16));
			JButton rejouer = new JButton(new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					Parametrage v = new Parametrage();
					// v.prenom = m.getJ().getCourant().getNom();
                    // ((JLabel)v.parametrageSolo.getComponent(0)).setText("Bienvenu(e), " + v.prenom);
                	// ((JLabel)v.parametrageSolo.getComponent(1)).setText("Total des points obtenus: " + v.membreExists(v.prenom)[1]);
               		// ((JLabel)v.parametrageSolo.getComponent(2)).setText("Total des mots trouvés: " + v.membreExists(v.prenom)[2]);
                	// v.f.setContentPane(v.parametrageSolo);
            		// v.f.setSize(450, 400);
					// v.rearrangement();
					f.setLocationRelativeTo(null);
					f.dispose();
				}
			});
			rejouer.setPreferredSize(new Dimension(200, 50));
			rejouer.setText("rejouer");
			rejouer.setFont(font);
			rejouer.setBackground(new Color(7, 67, 63));
			rejouer.setForeground(Color.WHITE);
			button.add(rejouer);

			victoire.setBackground(new Color(0xffd10b));
			score.setBackground(new Color(0, 0, 0, 0 ));
			motsTrouves.setBackground(new Color(32, 57, 97, 150));
			j.setBackground(Color.BLUE);
			button.setBackground(new Color(0, 0, 0, 0 ));

			victoire.setBounds((int)(f.getWidth()*625/1550), (int)(f.getHeight()*50/838), (int)(f.getWidth()*300/1550), (int)(f.getHeight()*50/838));
			f.add(victoire);
			score.setBounds((int)(f.getWidth()*650/1550), (int)(f.getHeight()*150/838), (int)(f.getWidth()*300/1550), (int)(f.getHeight()*50/838));
			f.add(score);
			j.setBounds((int)(f.getWidth()*340/1550), (int)(f.getHeight()*200/838), (int)(f.getWidth()*870/1550), (int)(f.getHeight()*500/838));
			f.add(j);
			button.setBounds((int)(f.getWidth()*625/1550), (int)(f.getHeight()*700/838), (int)(f.getWidth()*200/1550),(int)(f.getHeight()*50/838));
			f.add(button);
			JPanel fond =  new Fond("src/boggle.png");
			fond.setBounds(0, 0, f.getWidth(), f.getHeight());
			f.add(fond);

		File file = new File("musiques/applaudissements.wav");
		if (file.exists()) {

			try {
				if(clip != null){
					clip.stop();
					clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(file));
					clip.start();
				}
			} catch (Exception e1) {
				e1.printStackTrace();
			}

		}

		return (JPanel) f.getContentPane();

	}

	public JPanel jeuTermine() {

		if (m.getJ().getCourant().getScore() >= 0) {
			f.getContentPane().removeAll();
			
			
			

			JPanel victoire = new RoundedPanel(20, 2);
			victoire.setOpaque(false);
			
			// victoire.setBackground(Color.PINK);
			JLabel message = new JLabel("Partie terminée!");
			message.setFont(new Font("Consolas", 1, 30));
			message.setForeground(Color.WHITE);
			victoire.add(message);
			
			

			

			JPanel score = new JPanel();
			
			JLabel jl = new JLabel("Score obtenu  " + m.getJ().getCourant().getScore()); 
			jl.setFont(new Font("Consolas", 1, 30));
			jl.setForeground(Color.WHITE);
			score.add(jl);
				
			

			JPanel motsTrouves = new RoundedPanel(20, 2);
			motsTrouves.setOpaque(true);
		
			motsTrouves.add(new JLabel(String.format("%"+(int)(f.getWidth()*35/1550)+"s", "Mots trouvés")));
			motsTrouves.add(new JLabel(" "));
			if (m.getJ().getMotsTrouves().size() == 0)
				motsTrouves.add(new JLabel(String.format("%"+(int)(f.getWidth()*31/1550)+"s","Aucun mot trouvé")));
			if (m.getJ().getMotsTrouves().size() != 0){
				motsTrouves.add(new JLabel("                   Mot                       Points"));
				motsTrouves.add(new JLabel("   "));
			}
			
			for (int i = 0; i < m.getJ().getMotsTrouves().size(); i++) {
				motsTrouves.add(new JLabel("                   "+
						 String.format("%-"+(int)(f.getWidth()*28/1550)+"s",m.getJ().getMotsTrouves().get(i).toLowerCase())+ m.getJ().calculPointsMot(m.getJ().getMotsTrouves().get(i))));
				motsTrouves.add(new JLabel(" "));
			}
			
			motsTrouves.setLayout(new BoxLayout(motsTrouves, BoxLayout.Y_AXIS));
			motsTrouves.getComponent(0).setFont(new Font("Consolas", 1, 25));
			motsTrouves.getComponent(0).setForeground(Color.WHITE);
			for (int i = 1; i < motsTrouves.getComponentCount(); i++) {
				motsTrouves.getComponent(i).setFont(new Font("Consolas", 1, 20));
				motsTrouves.getComponent(i).setForeground(Color.WHITE);
				
			}
			JScrollPane j = new JScrollPane(motsTrouves, ScrollPaneConstants.VERTICAL_SCROLLBAR_AS_NEEDED,
					ScrollPaneConstants.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			JPanel meilleursScores = new RoundedPanel(20, 0);
			meilleursScores.setOpaque(false);
			
			meilleursScores.add(new JLabel("  Top Classement"));
			meilleursScores.add(new JLabel("                "));
			// meilleursScores.setBackground(Color.GREEN);
			File doc = new File("baseDeDonnees/scores.txt");
			Scanner sc;
			try {
				sc = new Scanner(doc);
				
				while (sc.hasNext()) {
					String[] t = sc.next().split(",");
					if(t[0].equals(""))
						t[0] = "visiteur";
					JLabel label = new JLabel(String.format("%-22s", t[0])  + t[1]  );
					//label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
					meilleursScores.add(label);
					meilleursScores.add(new JLabel("                "));
					
				}
			} catch (FileNotFoundException e) {
				System.out.println("erreur2");
			}
			meilleursScores.getComponent(0).setFont(new Font("Consolas", 1, 25));
			meilleursScores.getComponent(0).setForeground(Color.WHITE);
			for (int i = 1; i < meilleursScores.getComponentCount(); i++) {
				meilleursScores.getComponent(i).setFont(new Font("Consolas", 1, 20));
				meilleursScores.getComponent(i).setForeground(Color.WHITE);
			}

			meilleursScores.setLayout(new BoxLayout(meilleursScores, BoxLayout.Y_AXIS));
			//meilleursScores.setBorder(new RoundedPanelExample(Color.BLACK,2,16,16));

			JPanel ScoresPrec = new RoundedPanel(20, 0);
			ScoresPrec.setOpaque(false);

			ScoresPrec.add(new JLabel("  Scores précédents"));
			ScoresPrec.add(new JLabel("                "));
			File doc1 = new File("baseDeDonnees/scoresPrecedants.txt");
			Scanner sc1;
			try {
				sc1 = new Scanner(doc1);

				while (sc1.hasNext()) {
					String[] t = sc1.next().split(",");
					if(t[0].equals(""))
						t[0] = "visiteur";
					JLabel label = new JLabel(String.format("%-"+(int)(f.getWidth()*22/1550)+"s", t[0]) + t[1] );
					//label.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
					ScoresPrec.add(label);
					ScoresPrec.add(new JLabel("                "));
					
				}
			} catch (FileNotFoundException e) {
				System.out.println("erreur2");
			}
			ScoresPrec.getComponent(0).setFont(new Font("Consolas", 1, 25));
			ScoresPrec.getComponent(0).setForeground(Color.WHITE);
			for (int i = 1; i < ScoresPrec.getComponentCount(); i++) {
				ScoresPrec.getComponent(i).setFont(new Font("Consolas", 1, 20));
				ScoresPrec.getComponent(i).setForeground(Color.WHITE);
			}

			ScoresPrec.setLayout(new BoxLayout(ScoresPrec, BoxLayout.Y_AXIS));
			//ScoresPrec.setBorder(new RoundedPanelExample(Color.BLACK,2,16,16));

			JPanel button = new JPanel();
			//button.setBorder(new RoundedPanelExample(Color.BLACK,2,16,16));
			JButton rejouer = new JButton(new AbstractAction() {
				public void actionPerformed(ActionEvent e) {
					Parametrage v = new Parametrage();
					v.prenom = m.getJ().getCourant().getNom();
                    ((JLabel)v.parametrageSolo.getComponent(0)).setText("Bienvenu(e), " + v.prenom);
                	((JLabel)v.parametrageSolo.getComponent(1)).setText("Total des points obtenus: " + v.membreExists(v.prenom)[1]);
               		((JLabel)v.parametrageSolo.getComponent(2)).setText("Total des mots trouvés: " + v.membreExists(v.prenom)[2]);
                	v.f.setContentPane(v.parametrageSolo);
            		v.f.setSize(450, 400);
					v.rearrangement();
					f.setLocationRelativeTo(null);
					f.dispose();
				}
			});
			rejouer.setPreferredSize(new Dimension(200, 50));
			rejouer.setText("rejouer");
			rejouer.setFont(font);
			rejouer.setBackground(new Color(7, 67, 63));
			rejouer.setForeground(Color.WHITE);
			button.add(rejouer);

			
			victoire.setBackground(new Color(0xffd10b));
			score.setBackground(new Color(0, 0, 0, 0 ));
			meilleursScores.setBackground(new Color(61, 219, 247, 150));
			ScoresPrec.setBackground(new Color(225, 117, 94, 225));
			motsTrouves.setBackground(new Color(32, 57, 97, 150));
			j.setBackground(Color.BLUE);
			button.setBackground(new Color(0, 0, 0, 0 ));
			
			
			
			victoire.setBounds((int)(f.getWidth()*625/1550), (int)(f.getHeight()*50/838), (int)(f.getWidth()*300/1550), (int)(f.getHeight()*50/838));
			f.add(victoire);
			score.setBounds((int)(f.getWidth()*650/1550), (int)(f.getHeight()*150/838), (int)(f.getWidth()*400/1550), (int)(f.getHeight()*50/838));
			f.add(score);
			ScoresPrec.setBounds((int)(f.getWidth()*20/1550), (int)(f.getHeight()*50/838),(int)(f.getWidth()*300/1550), (int)(f.getHeight()*550/838));
			f.add(ScoresPrec);
			meilleursScores.setBounds((int)(f.getWidth()*1230/1550), (int)(f.getHeight()*50/838), (int)(f.getWidth()*300/1550), (int)(f.getHeight()*550/838));
			f.add(meilleursScores);
			j.setBounds((int)(f.getWidth()*340/1550), (int)(f.getHeight()*200/838), (int)(f.getWidth()*870/1550), (int)(f.getHeight()*500/838));
			f.add(j);
			button.setBounds((int)(f.getWidth()*625/1550), (int)(f.getHeight()*700/838), (int)(f.getWidth()*200/1550),(int)(f.getHeight()*50/838));
			f.add(button);
			JPanel fond =  new Fond("src/boggle.png");
			fond.setBounds(0, 0, f.getWidth(), f.getHeight());
			f.add(fond);

			

			File file = new File("musiques/applaudissements.wav");
			if (file.exists()) {

				try {
					clip.stop();
					clip = AudioSystem.getClip();
					clip.open(AudioSystem.getAudioInputStream(file));
					clip.start();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		}
		
		return (JPanel) f.getContentPane();
	}

	public static boolean nouveauScore(Joueur j) {
		String[] tab = new String[10];
		File doc = new File("baseDeDonnees/scores.txt");
		Scanner sc;
		try {
			sc = new Scanner(doc);
			int cpt = 0;
			while (sc.hasNext()) {
				tab[cpt] = sc.next();
				cpt++;

			}

			for (int i = 0; i < 10; i++) {
				String[] t = tab[i].split(",");

				if (j.getScore() >= Integer.parseInt(t[1])) {
					for (int k = 9; k > i; k--) {
						tab[k] = tab[k - 1];
					}
					tab[i] = j.getNom() + "," + j.getScore() ;
					break;
				}
			}

			Path file = Paths.get("baseDeDonnees/scores.txt");
			Files.write(file, Arrays.asList(tab), StandardCharsets.UTF_8);
			sc.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public static boolean nouveauScorePrec(Joueur j) {
		String[] tab = new String[10];
		File doc = new File("baseDeDonnees/scoresPrecedants.txt");
		Scanner sc;
		try {
			sc = new Scanner(doc);
			int cpt = 0;
			while (sc.hasNext()) {
				tab[cpt] = sc.next();
				cpt++;

			}

			for (int i = 9; i >= 1; i--) {
				tab[i] = tab[i - 1];
			}
			tab[0] = j.getNom() + "," + j.getScore();

			Path file = Paths.get("baseDeDonnees/scoresPrecedants.txt");
			Files.write(file, Arrays.asList(tab), StandardCharsets.UTF_8);
			sc.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

	}

	public class Fond extends JPanel{
		String path;
		public Fond(String path){
			this.path = path;
		}
		public void paintComponent(Graphics g) {
			
			try {
				BufferedImage inputImg = ImageIO.read(new File(path));
				Dimension dimension = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				int height = (int)dimension.getHeight();
                int width  = (int)dimension.getWidth();
		   
				// crée l'image de sortie
				BufferedImage outputImg = new BufferedImage(width, height, inputImg.getType());
		   
				// balancer l'image d'entrée à l'image de sortie
				Graphics2D g2 = outputImg.createGraphics();
				g2.drawImage(inputImg, 0, 0, width, height, null);
				g2.dispose();
				g.drawImage(outputImg, 0, 0, null);
				
				
		  
			} catch (Exception e) {
				//TODO: handle exception
			}
		
		
		}
	}

	
		
		
	

	
}
