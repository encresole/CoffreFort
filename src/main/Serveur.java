package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import controller.Authentification;
import controller.Erreur;
import controller.GestionClient;
import model.Configs;
import model.ServeurStorage;
import view.ServeurUI;

public class Serveur implements WindowListener {
	
	ServerSocket server;
	public ServeurStorage storage;
	ServeurUI ui;
	public Authentification auth;
	
	public Serveur() {
		storage = new ServeurStorage();
		auth = new Authentification(storage);
		ui = new ServeurUI(this);
		try {
			server = new ServerSocket(Configs.PORT);
			System.out.println("[Serveur] Ecoute sur le port " + Configs.PORT);
			while (true) {
				Socket client = server.accept();
				System.out.println("[Serveur] Nouveau client connecté !");
				new GestionClient(client, this).start();;
			}
		} catch (IOException e) {
			new Erreur("[Serveur] Erreur lors de la creation du ServerSocket");
		}
	}

	public void interprete(String message) {
		String[] parties = message.split(";");
		if (parties.length == 4) {
			if (parties[1].equals("SIGNUP")) {
				creation(parties[2], parties[3]);
			} else if (parties[1].equals("LOGIN")) {
				connexion(Integer.parseInt(parties[0]), parties[2], parties[3]);
			} else {
				System.out.println("[Serveur] message lu : " + message);
			}
		} else {
			System.out.println("[Serveur] message lu : " + message);
		}
	}
	
	private void creation(String username, String password) {
		auth.addUser(username, password);
	}
	
	private Boolean connexion(Integer id, String username, String password) {
		PrintWriter out = storage.getConnexions().get(id).getOut();
		Boolean reussi = auth.ok(username, password);
		out.println("LOGIN;"+reussi.toString());
		return reussi;
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}

	@Override
	public void windowClosing(WindowEvent e) {
		try {
			server.close();
		} catch (IOException e1) {
			System.err.println("Erreur lors de la fermeture du serveur");
		}
		System.exit(0);
	}

	@Override
	public void windowClosed(WindowEvent e) {
	}

	@Override
	public void windowIconified(WindowEvent e) {
	}

	@Override
	public void windowDeiconified(WindowEvent e) {
	}

	@Override
	public void windowActivated(WindowEvent e) {
	}

	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
	public static void main(String[] args) {
		new Serveur();
	}
}
