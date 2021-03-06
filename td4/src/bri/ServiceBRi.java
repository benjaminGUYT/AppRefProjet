package bri;


import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.net.*;


class ServiceBRi implements Runnable {
	
	private Socket client;
	
	ServiceBRi(Socket socket) {
		client = socket;
	}

	public void run() {
		try {BufferedReader in = new BufferedReader (new InputStreamReader(client.getInputStream ( )));
			PrintWriter out = new PrintWriter (client.getOutputStream ( ), true);
			out.println(ServiceRegistry.toStringue()+" ##Tapez le num�ro de service d�sir� :");
			int choix = Integer.parseInt(in.readLine());
			
			// instancier le service num�ro "choix" en lui passant la socket "client"
			Constructor<? extends Service> constr = ServiceRegistry.getServiceClass(choix).getConstructor(Socket.class);
			
			try {
				// si on met un new Thread on passe directement dans le client close et �a retourne null
				// alors que en utilisant run() on reste dans la pile d'exec de ServiceBRI et on
				// ne passe pas dans le close()
				constr.newInstance(client).run();
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				e.printStackTrace();
			}
				
			}
		catch (IOException | NoSuchMethodException | SecurityException e) {
			//Fin du service
		}

		try {client.close();} catch (IOException e2) {}
	}
	
	protected void finalize() throws Throwable {
		 client.close(); 
	}

	// lancement du service
	public void start() {
		(new Thread(this)).start();		
	}

}
