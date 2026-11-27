package model;

import java.util.HashMap;
import java.util.Set;

import controller.Erreur;

public class ServeurStorage {
	
	private HashMap<String, String> user_password = new HashMap<String, String>();
	private HashMap<Integer, Connexion> connexions = new HashMap<Integer, Connexion>();
	private int nextId = 1;
	private HashMap<String, HashMap<String, String>> fichiers = new HashMap<String, HashMap<String, String>>();
	
	public Boolean containsUser(String user) {
		return user_password.containsKey(user);
	}
	

	public boolean addUser(String user, String password) {
		return user_password.putIfAbsent(user, password) == null;
	}
	
	public void removeUser(String user) {
		if (!containsUser(user)) {new Erreur("On ne peut pas supprimer un user qui n'existe pas"); return;}
		System.out.println("[Serveur] Utilisateur supprimé");
		user_password.remove(user);
	}
	
	public void updatePassword(String user, String newPassword) {
		if (!containsUser(user)) {new Erreur("L'utilisateur n'existe pas encore, on ne peut donc pas changer son mot de passe"); return;}
		user_password.put(user, newPassword);
		System.out.println("[Serveur] Mot de passe modifié");
	}
	

	public String getPassword(String user) {
		if (!containsUser(user)) return "";
		System.out.println("[Serveur] Utilisateur trouvé");
		return user_password.get(user);
	}

	public HashMap<Integer, Connexion> getConnexions() {
		return connexions;
	}

	public void setConnexions(HashMap<Integer, Connexion> connexions) {
		this.connexions = connexions;
	}
	
	public Integer addConnexion(Connexion connexion) {
		this.connexions.put(nextId, connexion);
		nextId++;
		return nextId - 1;
	}
	
	public void addFichier(String user, String nom, String contenu) {
		fichiers.putIfAbsent(user, new HashMap<String, String>());
		fichiers.get(user).put(nom, contenu);
	}
	
	public String getFichier(String user, String nom) {
		if (!fichiers.containsKey(user)) return null;
		return fichiers.get(user).get(nom);
	}
	
	public Set<String> getNomsFichiers(String user) {
		if (!fichiers.containsKey(user)) return Set.of();
		return Set.copyOf(fichiers.get(user).keySet());
	}
	
	public void removeFichier(String user, String nom) {
		if (fichiers.containsKey(user)) fichiers.get(user).remove(nom);
	}

}
