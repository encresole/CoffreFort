package view;

import java.awt.CardLayout;

import javax.swing.JFrame;

import model.Configs;

public class ViewManager extends JFrame{
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout = new CardLayout();
	
	private FenetrePrincipale fenetrePrincipale;	
	private Connexion connexion;
	private CreationCompte creationCompte;
	
	
	public ViewManager(Configs configs) {
		super("Coffre fort");
		this.setSize(configs.FrameWidth, configs.frameHeight);
		this.setLayout(cardLayout);
		initLayout();
		
		showFenetrePrincipale();
		this.setVisible(true);
	}
	
	private void initLayout() {
		fenetrePrincipale = new FenetrePrincipale(this);
		this.add(fenetrePrincipale, "principale");
		connexion = new Connexion(this);
		this.add(connexion, "connexion");
		creationCompte = new CreationCompte(this);
		this.add(creationCompte, "creationCompte");
	}
	
	public void showFenetrePrincipale() {
		cardLayout.show(this.getContentPane(), "principale");
	}
	
	public void showConnexion() {
		cardLayout.show(this.getContentPane(), "connexion");
	}
	
	public void showCreationCompte() {
		cardLayout.show(this.getContentPane(), "creationCompte");
	}

}
