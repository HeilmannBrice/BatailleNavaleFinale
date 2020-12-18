package package_Serveur;

import java.net.ServerSocket;
import java.net.Socket;

public class MainServer {
public static void main(String[] args) {
	try {
		ServerSocket ecoute = new ServerSocket(1500);
		System.out.println("Serveur lancé!");
		int nbr_Client =0;
		while(nbr_Client<=2) {
		Socket client1 = ecoute.accept();
		Socket client2 = ecoute.accept();

		new ThreadChat(client1, client2).start();
		}
		} catch(Exception e) {
		// Traitement d�erreur
		}

}
}

