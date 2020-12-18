package package_Serveur;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.management.openmbean.ArrayType;

public class Grille {

	private int battle[][];
	
	int bateaux [] = {2,3,3,4,5};
	int all_ship_alive = 17;
	
	
	
	Bateaux porte_avion = new Bateaux(5,5);
	Bateaux croiseur = new Bateaux(4,4);
	Bateaux sous_marin1 = new Bateaux(3,3);
	Bateaux sous_marin2 = new Bateaux(3,3);
	Bateaux torpilleur = new Bateaux(2,2);
	
	
	
	public Grille(int[][] battle) {
		super();
		this.battle = battle;
	}


	//place un bateau sur la grille 
	public int[][] placer_un_bateau(int taille, int ligne, int colonne, int orientation, String sous_marin) {
		
		for (int i=0; i<=taille-1; i++) {
			
			switch(taille) {
			
			case 2:
				if(orientation==1) {
					battle[ligne+i][colonne]=12;
				}
				else {
					battle[ligne][colonne+i]=12;
				}
				break;
			case 3:
				if(sous_marin.equals("sous_marin1")) {
					if(orientation==1) {
						battle[ligne+i][colonne]=13;
					}
					else {
						battle[ligne][colonne+i]=13;
					}
				}
				else if(sous_marin.equals("sous_marin2")) {
					if(orientation==1) {
						battle[ligne+i][colonne]=133;
					}
					else {
						battle[ligne][colonne+i]=133;
					}
				}
				break;
			case 4:
				if(orientation==1) {
					battle[ligne+i][colonne]=14;
				}
				else {
					battle[ligne][colonne+i]=14;
				}
				break;
			case 5:
				if(orientation==1) {
					battle[ligne+i][colonne]=15;
				}
				else {
					battle[ligne][colonne+i]=15;
				}
				break;
			default:
	
			}
		}
		return battle;
	}

	//vérifie si il est possible de placer le bateau
	public boolean vérifier_placement(int taille, int ligne, int colonne, int orientation, String sous_marin) {
		
		int format = 0;
		int correct = 0;
		for (int i=0; i<=taille-1; i++) {
			
			if(orientation==1) {
				format = ligne+taille-1;
				if(format>9 || battle[ligne+i][colonne]!=0) {
					correct++;
				}
			}
			else {
				format = colonne+taille-1;
				if(format>9 || battle[ligne][colonne+i]!=0) {
					correct++;
				}
			}
		}
		if(correct==0) {
			return true; 
		}
		else {
			return false;
		}
	}



	//actualise la grille suivant si un bateau est touché ou non et prévient le joueur si il a touché ou coulé un bateau ou bien encore si il a raté son tir
	public void action(String message, PrintWriter out) {
		char coor1 = message.charAt(0);
		char coor2 = message.charAt(1);
		int coordonnee1 = Integer.parseInt(Character.toString(coor1));
		int coordonnee2 = Integer.parseInt(Character.toString(coor2));
		int position = battle[coordonnee1][coordonnee2];
		int touchee = 0;
		
		switch(position) {
		case 12:
			torpilleur.impact();
			if (torpilleur.est_coule()) {
				out.println("torpilleur coulé !");
			}
			break;
		case 13:
			sous_marin1.impact();
			if (sous_marin1.est_coule()) {
				out.println("sous_marin1 coulé !");
			}
			break;
		case 133:
			sous_marin2.impact();
			if (sous_marin2.est_coule()) {
				out.println("sous_marin2 coulé !");
			}
			break;
		case 14:
			croiseur.impact();
			if (croiseur.est_coule()) {
				out.println("croiseur coulé !");
			}
			break;
		case 15:
			porte_avion.impact();
			if (porte_avion.est_coule()) {
				out.println("porte_avion coulé !");
			}
			break;
		default:
			touchee++;
			break;
		}
		
		if(touchee==0) {
			battle[coordonnee1][coordonnee2]=2;
			all_ship_alive--;
			out.println("touché !");
		}
		else {
			battle[coordonnee1][coordonnee2]=-1;
			out.println("plouf");
		}
	}

	//renvoie un booléen suivant si un joueur à perdu ou non
	public boolean defeat(PrintWriter out1, PrintWriter out2) {
		if(all_ship_alive==0) {
			out1.println("vous avez échoué, game over !");
			out2.println("vous avez gagné !");
			return true;
		}
		else {
			return false;
		}
	}
	

		

	
	//Affichage de la grille cachée de l'adversaire chez le joueur concerné	
	public void afficher_grille(PrintWriter out) {
		for (int i=0; i<=battle.length-1; i++) {
			for(int j=0; j<=battle.length-1; j++) {	
				if (battle[i][j]!=2 && battle[i][j]!=-1) {
					out.print("~"+"|");
				}
				else {
					if(battle[i][j]==2) {
						out.print("x"+"|");
					}
					else {
						out.print("o"+"|");
					}
					
				}
			}
			out.println();
			}
		}

	//Affichage grille du joueur sur sa console
	public void afficher_grille_joueur(PrintWriter out) {
		for (int i=0; i<=battle.length-1; i++) {
			for(int j=0; j<=battle.length-1; j++) {	
				switch(battle[i][j]) {
				case 0:
					out.print("~"+"|");
					break;
				case 2:
					out.print("x"+"|");
					break;
				case -1:
					out.print("o"+"|");
					break;
				case 12:
					out.print("t"+"|");
					break;
				case 13:
					out.print("s"+"|");
					break;
				case 133:
					out.print("s"+"|");
					break;
				case 14:
					out.print("c"+"|");
					break;
				case 15:
					out.print("p"+"|");
					break;
				}
					
				}
			out.println();
			}
		}
	
}
