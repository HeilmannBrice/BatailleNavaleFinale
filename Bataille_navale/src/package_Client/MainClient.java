package package_Client;

import java.net.*;
import java.util.Scanner;
import java.io.*;


public class MainClient {
	
	static boolean correct = false;
	static int battle[][] = new int[10][10];
	
	//actualise la sauvegarde de la grille de jeu du client
	public static void update_battle(String message) {
		char coor1 = message.charAt(0);
		char coor2 = message.charAt(1);
		int coordonnee1 = Integer.parseInt(Character.toString(coor1));
		int coordonnee2 = Integer.parseInt(Character.toString(coor2));
		
		int touche = ListeningThread.a_touche();
		boolean fin = ListeningThread.fin();
		
		battle[coordonnee1][coordonnee2] = touche;
		
		if(fin) {
			for(int i=0;i<10;i++) {
				for(int j=0;j<10;j++) {
					battle[i][j]=0;
				}
			}
		}
	}
	
	
	public static void main(String[] args) {
		try {
			//instanciation socket, printwriter et lancement du thread d'écoute
			Socket s = new Socket("127.0.0.1", 1500);
			PrintWriter out = new PrintWriter(s.getOutputStream(), true);
			new ListeningThread(s).start();
			System.out.println("Connexion reussie!");
			Scanner sc=new Scanner(System.in);
			String message="";
			
			//zone envoie de message au serveur, entrer "quit" pour quitter le serveur et éteindre le client 
			while (!message.equals("quit")) {
				message=sc.nextLine();
				int taille=message.length(); 
				int limite = ListeningThread.en_placement();
				try {
					int i=Integer.parseInt(message);
					correct=true;
				}catch(Exception e) {
					correct=false;
				}
				//traitement d'une entrÃ©e invalide, non int, pas de la bonne taille
				while((correct!=true && !message.equals("quit")) || ((taille!=limite) && !message.equals("quit"))) {
					if((taille!=limite) || correct==false) {
						System.out.println(":(, l'entree est invalide");
					}
					message=sc.nextLine();
					taille=message.length();
					try {
						int i=Integer.parseInt(message);
						correct=true;
					}catch(Exception e) {
						correct = false;
					}
				}
				out.println(message);
				update_battle(message);
			
				
			}
			sc.close();
			s.close();
			System.out.println("au revoir !");
			System.exit(0);
			} catch(Exception e) {
			System.out.println("Le serveur est inaccessible !");
			System.exit(0);
			}

	}
	

}
