package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

/**
 * Le client se connecte à un serveur dont le protocole est 
 * menu-choix-question-réponse client-réponse service
 * la réponse est saisie au clavier en String
 **/
class Application {
	
		private final static int PORT_SERVICE = 3000;
		private final static String HOST = "localhost";
	
	public static void main(String[] args) {
		Socket s = null;		
		try {
			/* On créé la socket allant questionner le serveur */
			s = new Socket(HOST, PORT_SERVICE);

			BufferedReader sin = new BufferedReader (new InputStreamReader(s.getInputStream ( )));
			PrintWriter sout = new PrintWriter (s.getOutputStream ( ), true);
			BufferedReader clavier = new BufferedReader(new InputStreamReader(System.in));			
			
			System.out.println("Connecté au serveur " + s.getInetAddress() + ":"+ s.getPort());
			
			String line;
			/* Menu et choix du service */
			line = sin.readLine();
			System.out.println(line.replaceAll("##", "\n"));
			/* Saisie/envoie du choix */
			sout.println(clavier.readLine());
			
			/* Réception/affichage de la question */
			System.out.println(sin.readLine());
			/* Saisie clavier/envoie au service de la réponse */
			sout.println(clavier.readLine());
			/* Réception/affichage de la réponse */
			System.out.println(sin.readLine());
				
			
		}
		catch (IOException e) { 
			System.err.println("Fin de la connexion"); 
		}
		/* Refermer dans tous les cas la socket */
		try { 
			if (s != null) s.close(); 
		} catch (IOException e2) {}		
	}
}
