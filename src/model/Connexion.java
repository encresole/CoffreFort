package model;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Connexion {
	private Socket client;
	private BufferedReader in;
	private PrintWriter out;

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
}