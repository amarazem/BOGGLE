Bienvenue Sur Boggle :

/*********************************************************************************/

Sous windows voici les étapes à suivre pour compiler et éxécuter le projet depuis une console : 
01. Placez-vous dans le dossier où est enregistré le projet. Utilisez la commande cd suivie de l'arborescence et du nom du répertoire où vous avez enregistré votre code source.
02. Compilez votre programme. Une fois que vous vous êtes positionné dans le répertoire correct, vous pouvez compiler le programme en entrant
javac -d classes -cp ./ Joueur/Start.java
03. Exécutez votre programme. Entrez :
 java -cp . -classpath classes Joueur.Start
ou alors vous pouvez directement la commande :
javac -d classes -cp ./ Joueur/Start.java ; java -cp . -classpath classes Joueur.Start

/*********************************************************************************/

Sous Linux :
01. Placez-vous sur le dossier boggle.
02. Tapez la commande suivante : 
javac -d classes -cp ./ Joueur/Start.java && java -cp . -classpath classes Joueur.Start

Pour Le mode multijoueurs :
Activez le serveur avec la commande suivante sur un autre terminal :
Sur Windows :
javac -d classes -cp ./ serveur/GameServer.java ; java -cp . -classpath classes serveur.GameServer
Sur Linux : 
javac -d classes -cp ./ serveur/GameServer.java && java -cp . -classpath classes serveur.GameServer

/*********************************************************************************/
Si vous avez des problémes d'execution tapez la commande suivante :
sudo apt install openjdk-17-jdk
/*********************************************************************************/