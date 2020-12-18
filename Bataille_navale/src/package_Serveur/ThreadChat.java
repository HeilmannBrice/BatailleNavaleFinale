package package_Serveur;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class ThreadChat extends Thread{

BufferedReader in1;
PrintWriter out1;
BufferedReader in2;
PrintWriter out2;



int battle10[][] = new int[10][10];
int battle20[][] = new int[10][10];


public ThreadChat(Socket client1, Socket client2) {
	try {
	in1 = new BufferedReader(new InputStreamReader(client1.getInputStream()));
	out1 = new PrintWriter(client1.getOutputStream(), true);
	
	in2 = new BufferedReader(new InputStreamReader(client2.getInputStream()));
	out2 = new PrintWriter(client2.getOutputStream(), true);
	
	out1.println("Bienvenu dans la partie joueur 1 !");
	out2.println("Bienvenu dans la partie joueur 2 !");
	

	}catch (Exception e) {}
}

public void run() {
	try {
	boolean tour_1 = true;
	boolean tour_2 = false;
	boolean defaite = false;
	boolean revanche = true;
	int bateaux_placement1 [] = {2,3,3,4,5};
	String bateaux_placement2 [] = {"torpilleur","sous_marin1","sous_marin2","croiseur","porte_avion"};

	while (revanche==true) {
		revanche = false;
		//initialisation des grilles
		Grille battle1 = new Grille(battle10);
		Grille battle2 = new Grille(battle20);
		char coor1;
		char coor2;
		char orient;
		int coordonnee1 = 0;
		int coordonnee2 = 0;
		int orientation = 0;
		boolean correct = false;
		
		out2.println("c'est au joueur 1 de commencer à placer ses bateaux !");
		
		//placement des bateaux des 2 joueurs
		for(int i=0;i<bateaux_placement2.length;i++) {
			//placement des bateaux du joueur 1
			out1.println("placer le bateau : "+bateaux_placement2[i]);
			String message1=in1.readLine();
			
			//vérification de l'entrée
			while(correct==false) {
				coor1 = message1.charAt(0);
				coor2 = message1.charAt(1);
				orient = message1.charAt(2);
				coordonnee1 = Integer.parseInt(Character.toString(coor1));
				coordonnee2 = Integer.parseInt(Character.toString(coor2));
				orientation = Integer.parseInt(Character.toString(orient));
				
				if(orientation!=1 && orientation!=0) {
					correct=false;
				}
				else {
					correct = battle1.vérifier_placement(bateaux_placement1[i], coordonnee1, coordonnee2, orientation, bateaux_placement2[i]);
				}
				if(correct==false) {
					out1.println("entrée invalide !");
					message1=in1.readLine();
				}
			}
			//placement du bateau
			battle1.placer_un_bateau(bateaux_placement1[i], coordonnee1, coordonnee2, orientation, bateaux_placement2[i]);
			correct = false;
			
			
			//placement des bateaux du joueur 2
			out2.println("placer le bateau : "+bateaux_placement2[i]);
			String message2=in2.readLine();
					
			//vérification de l'entrée
			while(correct==false) {
				coor1 = message2.charAt(0);
				coor2 = message2.charAt(1);
				orient = message2.charAt(2);
				coordonnee1 = Integer.parseInt(Character.toString(coor1));
				coordonnee2 = Integer.parseInt(Character.toString(coor2));
				orientation = Integer.parseInt(Character.toString(orient));
			
				if(orientation!=1 && orientation!=0) {
					correct=false;
				}
				else {
					correct = battle2.vérifier_placement(bateaux_placement1[i], coordonnee1, coordonnee2, orientation, bateaux_placement2[i]);
				}
				if(correct==false) {
					out2.println("entrée invalide !");
					message2=in2.readLine();
				}
			}
			//placement du bateau			
			battle2.placer_un_bateau(bateaux_placement1[i], coordonnee1, coordonnee2, orientation, bateaux_placement2[i]);
			correct = false;	
		}
		//fin placement des bateaux chez les 2 joueurs 
		out1.println("tout les bateaux ont été placés !");
		out2.println("tout les bateaux ont été placés !");
		out2.println("C'est au joueur 1 de commencer !");
	
		//début de la bataille !
		while (defaite==false) {
			out1.println("joueur 1:");
			String message1=in1.readLine();
			if (tour_1==true && defaite==false) {
					defaite = battle1.defeat(out1, out2);
					battle2.action(message1, out1);
					battle2.afficher_grille(out1);
					out1.println("voici ta grille :");
					battle1.afficher_grille_joueur(out1);
					
					tour_1=false;
					tour_2=true;
					out1.println("c'est au joueur 2 de jouer !");
				}
			out2.println("joueur 1:");
			String message2=in2.readLine();
			if (tour_2==true && defaite==false) {
					defaite = battle2.defeat(out2, out1);
					battle1.action(message2, out2);
					battle1.afficher_grille(out2);
					out2.println("voici ta grille :");
					battle2.afficher_grille_joueur(out2);
		
					tour_1=true;
					tour_2=false;
					out2.println("c'est au joueur 1 de jouer !");	
				}
		}
		
		out1.println("Entre 100 si tu veux faire la revanche !");
		out2.println("Entre 100 si tu veux faire la revanche !");
		String message1=in1.readLine();
		String message2=in2.readLine();
			
		if(message1.equals("100") && message2.equals("100")) {
			revanche = true;
		}
		else {
			out1.println("C'était super chouette, revenez vite !");
			out2.println("C'était super chouette, revenez vite !");
			System.exit(0);
		}
	}
	}catch (Exception e) {
		out1.println("oh, il semblerait que l'autre joueur soit partie !");
		out2.println("oh, il semblerait que l'autre joueur soit partie !");
		System.exit(0);
	}
}
}

