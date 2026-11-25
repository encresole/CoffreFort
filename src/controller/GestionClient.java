package controller;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

import main.Serveur;
import model.Connexion;

public class GestionClient extends Thread {
	private Socket client;
	private Integer id;
	private Serveur serveur;

	public GestionClient(Socket client, Serveur serveur) {
		this.client = client;
		this.serveur = serveur;
	}

	@Override
	public void run() {
		try { 
			BufferedReader in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			PrintWriter out = new PrintWriter(client.getOutputStream(), true);
			id = serveur.storage.addConnexion(new Connexion(client, in, out));
			out.println("ID;" + id);
			serveur.ui.ajouteClient(id);
			String message;
			while ((message = in.readLine()) != null) {
				if (message.startsWith(id + ";")) serveur.interprete(message);
			}
		} catch (IOException e) {
			System.err.println("[Serveur] Connexion perdue : " + e.getMessage() + "\nId client : " + id.toString());
		} finally {
			if (id != null) serveur.ui.retireClient(id);
		}
	}
}