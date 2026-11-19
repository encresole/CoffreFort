package view;

import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class CreationCompte  extends JPanel{
	private static final long serialVersionUID = 1L;
	
	public CreationCompte(ViewManager viewManager) {
		this.setLayout(new GridBagLayout());
		this.add(new JLabel("page de création de compte !"));
	}
}
