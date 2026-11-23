package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;


import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PageConnexion extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JPanel infos = new JPanel();
	JLabel indicationUsername = new JLabel("Nom d'utilisateur :");
	JLabel indicationMotDePasse = new JLabel("Mot de passe :");
	
	JPanel reponses = new JPanel();
	JTextField username = new JTextField();
	JPasswordField password = new JPasswordField();
	
	JPanel actions = new JPanel();
	JButton connexion = new JButton("Se connecter");
	JButton oublie = new JButton("Mot de passe oublié");
	JButton retour = new JButton("Retour");
	
	public PageConnexion(ViewManager viewManager) {
		this.setLayout(new BorderLayout());
		
		infos.setLayout(new GridLayout(2, 1));
		infos.add(indicationUsername);
		infos.add(indicationMotDePasse);
		this.add(infos, BorderLayout.WEST);
		
		reponses.setLayout(new GridLayout(2, 1));
		reponses.add(username);
		reponses.add(password);
		this.add(reponses, BorderLayout.CENTER);
	
		connexion.addActionListener(e -> viewManager.client.demandeConnexion(username.getText().toLowerCase().trim(), new String(password.getPassword())));
		retour.addActionListener(e -> viewManager.showFenetrePrincipale());
		oublie.addActionListener(e -> modalOublie());
		actions.add(connexion, BorderLayout.SOUTH);
		actions.add(oublie, BorderLayout.SOUTH);
		actions.add(retour, BorderLayout.SOUTH);
		
		this.add(actions, BorderLayout.SOUTH);
	}
	
	private void modalOublie() {
		JFrame frame = new JFrame();
		frame.setSize(300,200);
		frame.setLayout(new BorderLayout());
		
		JPanel center = new JPanel();
		center.setLayout(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		
		JLabel indication = new JLabel("Entrez votre nom d'utilisateur :");
		center.add(indication, gbc);
		JTextField user = new JTextField(20);
		user.setSize(100, user.getHeight());
		JButton valider = new JButton("Valider");
		gbc.gridy = 2;
		center.add(user, gbc);
		gbc.gridy = 3;
		center.add(valider, gbc);
		frame.add(center);
		
		
		JButton ok = new JButton("fermer");
		ok.addActionListener(e -> frame.dispose());
		frame.add(ok, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
}
