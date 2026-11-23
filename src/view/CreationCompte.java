package view;

import java.awt.BorderLayout;
import java.awt.GridLayout;
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
	
	JPanel infos = new JPanel();
	JLabel indicationUsername = new JLabel("Nom d'utilisateur :");
	JLabel indicationMotDePasse = new JLabel("Mot de passe :");
	JLabel indicationConfirmationMotDePasse = new JLabel("Réecrire le mot de passe :");
	
	JPanel reponses = new JPanel();
	JTextField username = new JTextField();
	JTextField password = new JTextField();
	JPasswordField confirm = new JPasswordField();
	
	JPanel actions = new JPanel();
	JButton valider = new JButton("Valider la création");
	JButton retour = new JButton("Retour");
	
	public CreationCompte(ViewManager viewManager) {
		this.setLayout(new BorderLayout());
		
		infos.setLayout(new GridLayout(3, 1));
		infos.add(indicationUsername);
		infos.add(indicationMotDePasse);
		infos.add(indicationConfirmationMotDePasse);
		this.add(infos, BorderLayout.WEST);
		
		reponses.setLayout(new GridLayout(3, 1));
		reponses.add(username);
		reponses.add(password);
		reponses.add(confirm);
		this.add(reponses, BorderLayout.CENTER);
	
		valider.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (password.getText().equals(confirm.getText())) {
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
