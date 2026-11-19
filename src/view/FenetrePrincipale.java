package view;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class FenetrePrincipale extends JPanel {
	private static final long serialVersionUID = 1L;
		
	private JLabel titre = new JLabel("Coffre fort");
	private JPanel panelTitre = new JPanel(new GridBagLayout());
	private JPanel panelButtons = new JPanel(new GridBagLayout());
	private JButton connexion = new JButton("Se connecter");
	private JButton creation = new JButton("Créer un compte");
	private ViewManager viewManager;
	
	public FenetrePrincipale(ViewManager viewManager) {
		this.setLayout(new GridLayout(2, 1));
		this.viewManager = viewManager;
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 15, 10, 15);
		
		panelTitre.add(titre);
		this.add(panelTitre);
		
		panelButtons.add(connexion, gbc);
		gbc.gridy = 1;
		panelButtons.add(creation, gbc);
		setupButtons();
		this.add(panelButtons);
	
		this.setVisible(true);
	}
	
	private void setupButtons() {
		connexion.addActionListener(e -> viewManager.showConnexion());
		creation.addActionListener(e -> viewManager.showCreationCompte());
	}
}
