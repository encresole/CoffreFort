package model;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connexion {
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;
	private String user; // null tant que le client n'est pas connecté

	public Connexion(Socket client, BufferedReader in, PrintWriter out) {
		this.client = client;
		this.in = in;
		this.out = out;
	}


	public Socket getClient() {
		return client;
	}

	public BufferedReader getIn() {
		return in;
	}

	public PrintWriter getOut() {
		return out;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}
}