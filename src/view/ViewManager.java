package view;

import java.awt.CardLayout;
import java.util.List;

import javax.swing.JFrame;

import main.Client;
import model.Configs;

public class ViewManager extends JFrame {
	private static final long serialVersionUID = 1L;
	private CardLayout cardLayout = new CardLayout();

	private FenetrePrincipale fenetrePrincipale;
	private PageConnexion connexion;
	private CreationCompte creationCompte;
	private Accueil accueil;
	public Client client;

	public ViewManager(Client client) {
		super("Coffre fort (Client)");
		this.client = client;
		this.addWindowListener(client);
		this.setSize(Configs.FrameWidth, Configs.frameHeight);
		this.setLayout(cardLayout);
		initLayout();
		showFenetrePrincipale();
		this.setVisible(true);
	}

	private void initLayout() {
		fenetrePrincipale = new FenetrePrincipale(this);
		this.add(fenetrePrincipale, "principale");
		connexion = new PageConnexion(this);
		this.add(connexion, "connexion");
		creationCompte = new CreationCompte(this);
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

	public void setFichiers(List<String> noms) {
		accueil.setFichiers(noms);
	}

}
