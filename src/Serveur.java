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
			while (true) {
				Socket client = server.accept();
				System.out.println("[Serveur] Nouveau client connecté !");
				lanceClient(client);
			}
		} catch (IOException e) {
			new Erreur("Erreur lors de la creation du ServerSocket");
		}
	}
	
	public void lanceClient(Socket client) {
		try {
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			String message;
			while ((message = in.readLine()) != null) {
				System.out.println("[Serveur] message lu : " + message);
			}
			in.close();
			out.close();
			client.close();
			System.out.println("[Serveur] Client deconnecté");
		} catch (IOException e) {
			new Erreur("Erreur lors de la creation des i/o");
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		new Serveur();
	}
}
