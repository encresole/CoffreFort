package view;

import java.awt.CardLayout;

import javax.swing.JFrame;

import controller.Authentification;
import model.Configs;

public class ViewManager extends JFrame{
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout = new CardLayout();
	
	private FenetrePrincipale fenetrePrincipale;	
	private Connexion connexion;
	private CreationCompte creationCompte;
	private Accueil accueil;
	
	private Authentification auth;
	
	
	public ViewManager(Authentification auth) {
		super("Coffre fort");
		this.setSize(Configs.FrameWidth, Configs.frameHeight);
		this.setLayout(cardLayout);
		this.auth = auth;
		initLayout();
		
		showFenetrePrincipale();
		this.setVisible(true);
	}
	
	private void initLayout() {
		fenetrePrincipale = new FenetrePrincipale(this);
		this.add(fenetrePrincipale, "principale");
		connexion = new Connexion(this, auth);
		this.add(connexion, "connexion");
		creationCompte = new CreationCompte(this, auth);
		this.add(creationCompte, "creationCompte");
		accueil = new Accueil(this);
		this.add(accueil, "acceuil");
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
	
	public void showAccueil() {
		cardLayout.show(this.getContentPane(), "acceuil");
	}

}
