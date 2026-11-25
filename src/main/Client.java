package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Base64;

import javax.swing.JOptionPane;

import controller.Erreur;
import model.Configs;
import view.ViewManager;

public class Client implements WindowListener {
	Socket client;
	BufferedReader in;
	PrintWriter out;
	
	Integer id;
	ViewManager ui;
	File dossier;
	
	public Client() {
		try {
			client = new Socket("localhost", Configs.PORT);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			ui = new ViewManager(this);
			this.boucleDeLecture();
		} catch (UnknownHostException e) {
			new Erreur("[Client Hôte inconnu");
		} catch (IOException e) {
			new Erreur("[Client] Erreur de connexion");
		}		
	}	

	
	public void boucleDeLecture() {
		try {
			String message;
			while ((message = in.readLine()) != null) {
				interprete(message);
			}
		} catch (IOException e) {
			new Erreur("[Client] Erreur lors de la lecture du message");
		}
	}
	
	private void interprete(String message) {
		String[] parties = message.split(";");
		if (parties[0].equals("LIST")) {
			ui.setFichiers(Arrays.asList(parties).subList(1, parties.length));
		} else if (parties[0].equals("FILE") && parties.length == 3) {
			enregistreFichier(parties[1], parties[2]);
		} else if (parties[0].equals("ERREUR") && parties.length == 2) {
			new Erreur(parties[1]);
		} else if (parties.length == 2) {
			if (parties[0].equals("ID") && id == null) {
				this.id = Integer.parseInt(parties[1]);
				System.out.println("id set");
			} else if (parties[0].equals("LOGIN")) {
				traiteConnexion(parties[1]);
			} else {
				System.out.println("[Client] Message lu : " + message);
			}
		} else {
			System.out.println("[Client] Message lu : " + message);
		}
	}


	public void demandeConnexion(String user, String password) {
		out.println(id.toString()+";LOGIN;"+user+";"+password);
	}
	
	public void creationCompte(String user, String password) {
		out.println(id.toString()+";SIGNUP;"+user+";"+password);
	}
	
	public void traiteConnexion(String message) {
		if (message.equals("true")) {
			ui.showAccueil();
			out.println(id.toString()+";LIST");
		} else {
			new Erreur("Mauvais nom d'utilisateur ou mot de passe");
		}
	}

	public void envoieFichier(File fichier) {
		if (fichier.length() == 0 || fichier.length() > Configs.TAILLE_MAX || fichier.getName().contains(";")) {
			new Erreur("Fichier refusé (vide, plus de 10 Mo ou nom avec \";\")");
			return;
		}
		try {
			String contenu = Base64.getEncoder().encodeToString(Files.readAllBytes(fichier.toPath()));
			out.println(id.toString()+";PUT;"+fichier.getName()+";"+contenu);
		} catch (IOException e) {
			new Erreur("[Client] Impossible de lire le fichier");
		}
	}
	
	public void demandeFichier(String nom, File dossier) {
		this.dossier = dossier;
		out.println(id.toString()+";GET;"+nom);
	}
	
	public void supprimeFichier(String nom) {
		out.println(id.toString()+";DELETE;"+nom);
	}
	
	private void enregistreFichier(String nom, String contenu) {
		File destination = new File(dossier, new File(nom).getName());
		if (destination.exists() && JOptionPane.showConfirmDialog(ui, "Écraser " + destination + " ?",
				"Confirmation", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
		try {
			Files.write(destination.toPath(), Base64.getDecoder().decode(contenu));
			JOptionPane.showMessageDialog(ui, "Fichier enregistré : " + destination);
		} catch (IOException e) {
			new Erreur("[Client] Impossible d'enregistrer le fichier");
		}
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}


	@Override
	public void windowClosing(WindowEvent e) {
		System.exit(0);
		try {
			in.close();
			out.close();
			client.close();
		} catch (IOException e1) {
			System.err.println("Erreur lors de la fermeture du client");
		}
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
		new Client();
	}
}
