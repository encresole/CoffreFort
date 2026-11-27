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
	
	public String addUser(String user, String password) {
		if (user.isBlank()) return "Nom d'utilisateur vide";
		if (!motDePasseFort(password)) {
			return "Mot de passe trop faible : 8 caractères minimum avec majuscule, minuscule, chiffre et caractère spécial";
		}
		if (!serveurStorage.addUser(user, password)) return "Nom d'utilisateur déjà pris";
		return null;
	}

	private boolean motDePasseFort(String password) {
		return password.length() >= 8
				&& password.chars().anyMatch(Character::isUpperCase)
				&& password.chars().anyMatch(Character::isLowerCase)
				&& password.chars().anyMatch(Character::isDigit)
				&& password.chars().anyMatch(c -> !Character.isLetterOrDigit(c));
	}
}
