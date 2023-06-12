package serveur;

import java.beans.Transient;
import java.io.*;
import java.net.*;
import Joueur.*;

public class GameServer implements Serializable {
    transient private ServerSocket ss;
    private int numPlayers;
    private Jeu jeu;
    private ServerConnexion player1;
    private ServerConnexion player2;

    public GameServer() {
        System.out.println("--Game Server Boggle--");
        numPlayers = 0;
        try {
            ss = new ServerSocket(22222);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("Waiting for connections...");
            while (numPlayers < 2) {
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("Player #" + numPlayers + " has connected.");
                ServerConnexion ssc = new ServerConnexion(s, numPlayers);
                if (numPlayers == 1) {
                    player1 = ssc;
                } else {
                    player2 = ssc;
                }
                Thread t = new Thread(ssc);
                t.start();
                if(numPlayers==2){
                    numPlayers = 0;
                }
            }
            System.exit(0);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private class ServerConnexion implements Runnable,Serializable {
        transient private Socket s;
        transient private DataInputStream dataIn;
        transient private DataOutputStream dataOut;
        private ObjectInputStream objectIn;
        private ObjectOutputStream objectOut;
        private int playerID;
        private Jeu jeu;

        public ServerConnexion(Socket s, int id) {
            this.s = s;
            playerID = id;
            try {
                dataIn = new DataInputStream(s.getInputStream());
                dataOut = new DataOutputStream(s.getOutputStream());
            } catch (Exception e) {

            }
        }

        @Override
        public void run() {
            try {
                dataOut.writeInt(playerID);
                dataOut.flush();
                //jeu = (Jeu) dataIn.readObject();
                // dataOut.writeObject(jeu);
                while (true) {

                }
            } catch (Exception e) {

            }
        }
    }

    public static void main(String[] args) {
        GameServer gs = new GameServer();
        gs.acceptConnections();
    }
}