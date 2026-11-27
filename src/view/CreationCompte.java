package view;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import controller.Erreur;

public class CreationCompte  extends JPanel{
	private static final long serialVersionUID = 1L;
	
	JLabel indicationUsername = new JLabel("Nom d'utilisateur :");
	JLabel indicationMotDePasse = new JLabel("Mot de passe :");
	JLabel indicationConfirmationMotDePasse = new JLabel("Réecrire le mot de passe :");

	JTextField username = new JTextField(18);
	JTextField password = new JTextField(18);
	JPasswordField confirm = new JPasswordField(18);
	
	JPanel actions = new JPanel();
	JButton valider = new JButton("Valider la création");
	JButton retour = new JButton("Retour");
	
	public CreationCompte(ViewManager viewManager) {
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
		gbc.gridy = 2;
		formulaire.add(indicationConfirmationMotDePasse, gbc);

		gbc.gridx = 1;
		gbc.gridy = 0;
		formulaire.add(username, gbc);
		gbc.gridy = 1;
		formulaire.add(password, gbc);
		gbc.gridy = 2;
		formulaire.add(confirm, gbc);
		this.add(formulaire, BorderLayout.CENTER);

		valider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (password.getText().equals(new String(confirm.getPassword()))) {
					viewManager.client.creationCompte(username.getText().toLowerCase().trim(), password.getText());
				} else {
					new Erreur("Mot de passe différent de la validation");
				}
			}
		}); 
		retour.addActionListener(e -> viewManager.showFenetrePrincipale());
		actions.add(valider, BorderLayout.SOUTH);
		actions.add(retour, BorderLayout.SOUTH);
		
		this.add(actions, BorderLayout.SOUTH);
	}
}
