package Joueur;

import graphique.*;

import java.io.ObjectInputStream;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.awt.*;
import java.awt.event.*;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;
import javax.sound.sampled.AudioSystem;

import java.io.*;

public class Start implements Serializable {
	public static Jeu jeu;
	public String text;
	public Vue v;

	public Start(Jeu j) {
		Modele m = new Modele(j);
		v = new Vue(m);
		Controleur c = new Controleur(v, m);
		v.setC(c);
		v.start = this;
		v.f.setVisible(true);
	}

	public Start(Jeu j,String s) {
		Modele m = new Modele(j);
		v = new Vue(m);
		v.f.setTitle(s);
		Controleur c = new Controleur(v, m);
		v.setC(c);
		v.start = this;
		v.f.setVisible(true);
	}	

	public static boolean reinitialiser(){
        String[] tab = new String[10];
		for (int i = 0; i < tab.length; i++) {
			tab[i] = "inconnu,0points.";
		}
        try {    
			
            Path file = Paths.get("scores.txt");
            Files.write(file, Arrays.asList(tab), StandardCharsets.UTF_8);
            System.out.println("ecriture ok");
             return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }

	public static void supp(String s) {
		File f = new File(s);
		f.delete();
	}

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (UnsupportedLookAndFeelException e1) {

		}
		ObjectInputStream ois = null;
		try {
			//verifie si vue.txt existe (partie sauvgardee)
			ois = new ObjectInputStream(new FileInputStream("vue.txt"));

			supp("vue.txt");
			Vue v = (Vue) ois.readObject();
			//si v n'est pas a null on ouvre une nouvelle fenetre
			v.f.setVisible(true);
			if (!v.c.multi) {

				v.entrerMot.addKeyListener(new KeyListener() {
					public void keyReleased(KeyEvent e) {
						//touche entrer
						if (e.getKeyCode() == 10) {
							v.c.bouttonValider1();
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
				
				File file = new File("musiques/musique.wav");
				if (file.exists()) {			
				v.clip = AudioSystem.getClip();
				v.clip.open(AudioSystem.getAudioInputStream(file));
				v.clip.setLoopPoints(v.clip.getFramePosition(), v.clip.getFrameLength() - 1);
				v.clip.loop(5);
				v.clip.start();



				v.c.scheduler = Executors.newScheduledThreadPool(1);
				Runnable runnable = new Runnable() {

					public void run() {
						v.c.temps--;
						if (String.valueOf(v.c.temps % 60).length() == 1) {
							((JLabel) v.titreSolo.getComponent(4))
									.setText("0" + v.c.temps / 60 + ":0" + v.c.temps % 60);
						} else {
							((JLabel) v.titreSolo.getComponent(4))
									.setText("0" + v.c.temps / 60 + ":" + v.c.temps % 60);
						}
		
						if(v.c.temps <= 60){
							((JLabel) v.titreSolo.getComponent(4)).setForeground(new Color(0xbb1f09));
						}
		
						if (v.c.temps == 0) {
						   
								Vue.nouveauScore(v.m.getJ().getCourant());
								Vue.nouveauScorePrec(v.m.getJ().getCourant());
								v.c.miseAJour();
								v.f.setContentPane(v.jeuTermine());
							   
								v.c.scheduler.shutdown();
						}
					}
				};
				v.c.scheduler.scheduleAtFixedRate(runnable, 0, 1, TimeUnit.SECONDS);
			}

			// File f = new File("vue.data");
			// f.delete();
		} 

	}catch (ClassNotFoundException e) {
		System.out.println("aucune classe");
	} catch (Exception e) {
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				Parametrage v = new Parametrage();
				
			}
		});
	} finally {
		try {
			ois.close();
		} catch (Exception e) {
			//System.out.println(e.getMessage());
		}
	}

}
}
