package package_Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class ListeningThread extends Thread{
	
	BufferedReader in;
	
	static int placement=0;
	
	static boolean touche;
	
	static boolean fin=false;

	
	public ListeningThread(Socket s) throws IOException {
		in = new BufferedReader(new InputStreamReader(s.getInputStream()));
	}
	//vérifie si la phase de placement est bien terminé, si tel est le cas, il renvoit une limite de taille de 2
	public static int en_placement() {
		if(placement!=0) {
			return 2;
		}
		else {
			return 3;
		}
	}
	//vérifie si le joueur a touché ou non un bateau de l'adversaire pour actualiser la grille de sauvegarde du client
	public static int a_touche() {
		if(touche) {
			return 2;
		}
		else {
			return -1;
		}
	}
	//vérifie si c'est la fin de la partie 
	public static boolean fin() {
		return fin;
	}
	
	
	public void run(){
		try {
		while (true) {
			String message = in.readLine();
			System.out.println(message);
			fin = false;
			if(message.equals("tout les bateaux ont été placés !")) {
				placement++;
			}
			if(message.equals("touché !")) {
				touche = true;
			}
			if(message.equals("plouf")) {
				touche = false;
			}
			if(message.equals("Entre oui si tu veux faire la revanche !")) {
				fin = true;
			}
		}
		}catch (IOException e) {
			System.out.println("Le serveur est inaccessible !");
			System.exit(0);
		}
	}
}
