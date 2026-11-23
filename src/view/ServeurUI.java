package view;

import javax.swing.JFrame;

import main.Serveur;
import model.Configs;

public class ServeurUI extends JFrame {
	private static final long serialVersionUID = 1L;
	
	Serveur serveur;
	
	public ServeurUI(Serveur s) {
		super("Serveur");
		serveur = s;
		this.addWindowListener(serveur);
		this.setSize(Configs.FrameWidth, Configs.frameHeight);
		this.setVisible(true);
	}

}
