package model;

import java.util.HashMap;

import controller.Erreur;

public class ServeurStorage {
	
	private HashMap<String, String> user_password = new HashMap<String, String>();
	private HashMap<Integer, Connexion> connexions = new HashMap<Integer, Connexion>();
	private int nextId = 1;
	
	public Boolean containsUser(String user) {
		return user_password.containsKey(user);
	}
	
	public void addUser(String user, String password) {
		if (containsUser(user)) {new Erreur("Nom d'utilisateur déjà pris"); return;}
		if (password.isBlank()) {new Erreur("Mot de passe vide"); return;}
		user_password.put(user, password);
		System.out.println("[Serveur] Utilisateur ajouté");
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
		if (!containsUser(user)) {
			new Erreur("L'utilisateur n'existe pas");
			return "";
		}
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

}
