package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class PageConnexion extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel indicationUsername = new JLabel("Nom d'utilisateur :");
	JLabel indicationMotDePasse = new JLabel("Mot de passe :");

	JTextField username = new JTextField(18);
	JPasswordField password = new JPasswordField(18);
	
	JPanel actions = new JPanel();
	JButton connexion = new JButton("Se connecter");
	JButton oublie = new JButton("Mot de passe oublié");
	JButton retour = new JButton("Retour");
	
	public PageConnexion(ViewManager viewManager) {
		this.setLayout(new BorderLayout());

		JPanel formulaire = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(5, 5, 5, 5);
		gbc.anchor = GridBagConstraints.WEST;

		gbc.gridx = 0;
		gbc.gridy = 0;
		formulaire.add(indicationUsername, gbc);
		gbc.gridy = 1;
		formulaire.add(indicationMotDePasse, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		formulaire.add(username, gbc);
		gbc.gridy = 1;
		formulaire.add(password, gbc);
		this.add(formulaire, BorderLayout.CENTER);

		connexion.addActionListener(e -> viewManager.client.demandeConnexion(username.getText().toLowerCase().trim(), new String(password.getPassword())));
		retour.addActionListener(e -> viewManager.showFenetrePrincipale());
		oublie.addActionListener(e -> modalOublie());
		actions.add(connexion, BorderLayout.SOUTH);
		actions.add(oublie, BorderLayout.SOUTH);
		actions.add(retour, BorderLayout.SOUTH);
		
		this.add(actions, BorderLayout.SOUTH);
	}
	
	public void vider() {
		username.setText("");
		password.setText("");
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
