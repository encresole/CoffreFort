package controller;

import java.util.HashMap;

import model.Configs;
import model.ServeurStorage;

public class Authentification {
	ServeurStorage serveurStorage;
	private final HashMap<String, Integer> echecs = new HashMap<String, Integer>();
	private final HashMap<String, Long> finBlocage = new HashMap<String, Long>();

	public Authentification(ServeurStorage s) {
		this.serveurStorage = s;
	}
	
	public boolean estBloque(String user) {
		Long fin = finBlocage.get(user);
		return fin != null && System.currentTimeMillis() < fin;
	}

	public int tentativesRestantes(String user) {
		if (estBloque(user)) return 0;
		if (!echecs.containsKey(user)) return Configs.TENTATIVES_MAX;
		return Configs.TENTATIVES_MAX - echecs.get(user);
	}

	public Boolean ok(String user, String password) {
		String secret = serveurStorage.getPassword(user);
		boolean reussi = !secret.isEmpty() && secret.equals(password);
		System.out.println("[Auth] Bon mot de passe : " + reussi);
		if (reussi) {
			echecs.remove(user);
		} else {
			int nombre = 1;
			if (echecs.containsKey(user)) nombre = echecs.get(user) + 1;
			echecs.put(user, nombre);

			if (nombre >= Configs.TENTATIVES_MAX) {
				finBlocage.put(user, System.currentTimeMillis() + Configs.DUREE_BLOCAGE);
				echecs.remove(user);
			}
		}
		return reussi;
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
