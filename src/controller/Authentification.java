package controller;

import model.ServeurStorage;

public class Authentification {
	ServeurStorage serveurStorage;
	
	public Authentification(ServeurStorage s) {
		this.serveurStorage = s;
	}
	
	public Boolean ok(String user, String password) {
		String secret = serveurStorage.getPassword(user);
		if (secret.isEmpty()) return false;
		System.out.println("[Auth] Bon mot de passe : " + secret.equals(password));
		return secret.equals(password);
	}
	
	public void addUser(String user, String password) {
		serveurStorage.addUser(user, password); // contraite déjà présent vérifiée plus tard
	}
}
