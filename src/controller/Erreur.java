package controller;

import java.awt.BorderLayout;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Erreur extends Error {
	private static final long serialVersionUID = 1L;
	
	public Erreur(String erreur) {
		JFrame frame = new JFrame("Erreur");
		frame.setSize(300,200);
		frame.setLayout(new BorderLayout());
		
		JPanel center = new JPanel();
		center.setLayout(new GridBagLayout());

		JLabel indication = new JLabel(erreur);
		center.add(indication);
		frame.add(center);
		
		JButton ok = new JButton("fermer");
		ok.addActionListener(e -> frame.dispose());
		frame.add(ok, BorderLayout.SOUTH);
		frame.setVisible(true);
	}
	
}
