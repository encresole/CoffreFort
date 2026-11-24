package main;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.Erreur;
import model.Configs;
import view.ViewManager;

public class Client implements WindowListener {
	Socket client;
	BufferedReader in;
	PrintWriter out;
	
	Integer id;
	ViewManager ui;
	
	public Client() {
		ui = new ViewManager(this);

		try {
			client = new Socket("localhost", Configs.PORT);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
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
		if (parties.length == 2) {
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
		} else {
			new Erreur("Mauvais nom d'utilisateur ou mot de passe");
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
