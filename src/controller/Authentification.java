package controller;

import java.util.HashMap;

import model.ServeurStorage;

public class Authentification {
	ServeurStorage serveurStorage;
	
	public Authentification(ServeurStorage s) {
		this.serveurStorage = s;
	}
	
	public Boolean ok(String user, String password, HashMap<String, String> userPassword) {
		if (userPassword.containsKey(user)) {
			return userPassword.get(user) == password;
		} else {
			return false;
		}
	}
	
	public void addUser(String user, String password) {
		serveurStorage.addUser(user, password);
	}
}
