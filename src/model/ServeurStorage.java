package model;

import java.util.HashMap;

public class ServeurStorage {
	
	private HashMap<String, String> user_password = new HashMap<String, String>();
	
	public void addUser(String user, String password) {
		if (user_password.containsKey(user)) {
			System.err.println("Nom d'utilisateur déjà pris");
		} else {
			user_password.put(user, password);
		}
	}
	
	public void removeUser(String user) {
		if (user_password.containsKey(user)) {
			user_password.remove(user);
		} else {
			System.err.println("On ne peut pas supprimer un user qui n'existe pas");
		}
	}
	
	public void updatePassword(String user, String newPassword) {
		if (user_password.containsKey(user)) {
			user_password.put(user, newPassword);
		} else {
			System.err.println("L'user n'existe pas encore, on ne peut docn pas changer son mot de passe");
		}	
	}
	
}
