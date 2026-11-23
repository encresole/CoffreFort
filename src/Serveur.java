import java.awt.Frame;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import controller.Erreur;
import model.Configs;

public class Serveur {
	
	ServerSocket server;
	
	public Serveur() {
		try {
			server = new ServerSocket(Configs.PORT);
			System.out.println("[Serveur] Ecoute sur le port " + Configs.PORT);
			Socket client = server.accept();
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			while (client.isConnected()) {
				String message = in.readLine();
				if (message != null) {
					System.out.println("[Serveur] lu : " + message);
				}
			}
		} catch (IOException e) {
			new Erreur("Erreur lors de la creation du ServerSocket");
		}
	}
	
	
	public static void main(String[] args) {
		new Serveur();
	}
}
