package view;

import javax.swing.JFrame;

import model.Configs;

public class FenetrePrincipale extends JFrame{
	private static final long serialVersionUID = 1L;
	
	public FenetrePrincipale(Configs configs) {
		this.setSize(configs.FrameWidth, configs.frameHeight);
		
		this.setVisible(true);
	}
}
