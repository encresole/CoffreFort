import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import controller.Erreur;
import model.Configs;

public class Client {
	Socket client;
	BufferedReader in;
	PrintWriter out;
	
	public Client() {
		try {
			client = new Socket("localhost", Configs.PORT);
			in = new BufferedReader(new InputStreamReader(client.getInputStream()));
			out = new PrintWriter(client.getOutputStream(), true);
			out.println("Message du client");
		} catch (UnknownHostException e) {
			new Erreur("[Client Hôte inconnu");
		} catch (IOException e) {
			new Erreur("[Client] Erreur de connexion");
		}
	}
	
	public static void main(String[] args) {
		new Client();
	}
}
