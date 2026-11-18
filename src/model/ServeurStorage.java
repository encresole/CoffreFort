package model;

import java.util.HashMap;

public class ServeurStorage {
	
	private HashMap<String, String> user_password = new HashMap<String, String>();
	
	public void addUser(String user, String password) {
		user_password.put(user, password);
	}

}
