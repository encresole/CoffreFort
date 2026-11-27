package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Base64;

import controller.Authentification;
import controller.Erreur;
import controller.GestionClient;
import model.Configs;
import model.Connexion;
import model.ServeurStorage;
import view.ServeurUI;

public class Serveur implements WindowListener {
	
	ServerSocket server;
	public ServeurStorage storage;
	public ServeurUI ui;
	public Authentification auth;
	
	public Serveur() {
		storage = new ServeurStorage();
		auth = new Authentification(storage);
		try {
			server = new ServerSocket(Configs.PORT);
			System.out.println("[Serveur] Ecoute sur le port " + Configs.PORT);
			ui = new ServeurUI(this);
			while (true) {
				Socket client = server.accept();
				System.out.println("[Serveur] Nouveau client connecté !");
				new GestionClient(client, this).start();;
			}
		} catch (IOException e) {
			new Erreur("[Serveur] Erreur lors de la creation du ServerSocket : " + e);
		}
	}

	public void interprete(String message) {
		String[] parties = message.split(";", 4);
		if (parties.length >= 2 && (parties[1].equals("PUT") || parties[1].equals("LIST")
				|| parties[1].equals("GET") || parties[1].equals("DELETE"))) {
			fichier(Integer.parseInt(parties[0]), parties);
		} else if (parties.length == 2 && parties[1].equals("LOGOUT")) {
			storage.getConnexions().get(Integer.parseInt(parties[0])).setUser(null);
		} else if (parties.length == 4) {
			if (parties[1].equals("SIGNUP")) {
				creation(Integer.parseInt(parties[0]), parties[2], parties[3]);
			} else if (parties[1].equals("LOGIN")) {
				connexion(Integer.parseInt(parties[0]), parties[2], parties[3]);
			} else {
				System.out.println("[Serveur] message lu : " + message);
			}
		} else {
			System.out.println("[Serveur] message lu : " + message);
		}
	}
	
	private void creation(Integer id, String username, String password) {
		PrintWriter out = storage.getConnexions().get(id).getOut();
		String refus = auth.addUser(username, password);
		out.println(refus == null ? "SIGNUP;true" : "ERREUR;" + refus);
	}
	
	private Boolean connexion(Integer id, String username, String password) {
		Connexion connexion = storage.getConnexions().get(id);
		PrintWriter out = connexion.getOut();
		if (auth.estBloque(username)) {
			out.println("ERREUR;Trop de tentatives, réessayez dans " + Configs.DUREE_BLOCAGE / 1000 + " secondes maximum");
			return false;
		}
		Boolean reussi = auth.ok(username, password);
		if (!reussi) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
		if (reussi) {
			connexion.setUser(username);
			out.println("LOGIN;true");
		} else {
			connexion.setUser(null);
			int restantes = auth.tentativesRestantes(username);
			String suite = restantes > 0 ? "Tentatives restantes : " + restantes
					: "Compte bloqué " + Configs.DUREE_BLOCAGE / 1000 + " secondes";
			out.println("ERREUR;Mauvais nom d'utilisateur ou mot de passe. " + suite);
		}
		return reussi;
	}
	
	private void fichier(Integer id, String[] parties) {
		Connexion connexion = storage.getConnexions().get(id);
		PrintWriter out = connexion.getOut();
		String user = connexion.getUser();
		if (user == null) {
			out.println("ERREUR;Vous n'êtes pas connecté");
			return;
		}
		if (parties[1].equals("PUT") && parties.length == 4) {
			if (Base64.getDecoder().decode(parties[3]).length > Configs.TAILLE_MAX) {
				out.println("ERREUR;Fichier trop volumineux");
				return;
			}
			storage.addFichier(user, parties[2], parties[3]);
			envoieListe(out, user);
		} else if (parties[1].equals("LIST")) {
			envoieListe(out, user);
		} else if (parties[1].equals("GET") && parties.length == 3) {
			String contenu = storage.getFichier(user, parties[2]);
			if (contenu == null) {
				out.println("ERREUR;Fichier introuvable");
			} else {
				out.println("FILE;" + parties[2] + ";" + contenu);
			}
		} else if (parties[1].equals("DELETE") && parties.length == 3) {
			storage.removeFichier(user, parties[2]);
			envoieListe(out, user);
		}
	}
	
	private void envoieListe(PrintWriter out, String user) {
		String message = "LIST";
		for (String nom : storage.getNomsFichiers(user)) {
			message += ";" + nom;
		}
		out.println(message);
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
