package view;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Accueil extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public Accueil(ViewManager viewManager) {
		this.setLayout(new GridBagLayout());
		this.add(new JLabel("page de connexion !"));
	}
}
