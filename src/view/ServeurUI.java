package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.HashMap;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import main.Serveur;
import model.Configs;

public class ServeurUI extends JFrame {
	private static final long serialVersionUID = 1L;

	Serveur serveur;

	private JLabel titre = new JLabel();
	private JPanel liste = new JPanel();
	private HashMap<Integer, JPanel> panels = new HashMap<Integer, JPanel>();

	public ServeurUI(Serveur s) {
		super("Serveur");
		serveur = s;
		this.addWindowListener(serveur);
		this.setSize(Configs.FrameWidth, Configs.frameHeight);
		this.setLayout(new BorderLayout());

		titre.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		this.add(titre, BorderLayout.NORTH);

		liste.setLayout(new BoxLayout(liste, BoxLayout.Y_AXIS));
		JPanel haut = new JPanel(new BorderLayout()); 
		haut.add(liste, BorderLayout.NORTH);
		this.add(new JScrollPane(haut), BorderLayout.CENTER);

		actualise();
		this.setVisible(true);
	}

	public void ajouteClient(Integer id) {
		JPanel panel = panelClient(id);
		panels.put(id, panel);
		liste.add(panel);
		actualise();
	}

	public void retireClient(Integer id) {
		JPanel panel = panels.remove(id);
		if (panel != null) liste.remove(panel);
		actualise();
	}

	private JPanel panelClient(Integer id) {
		JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
		panel.setBorder(BorderFactory.createEtchedBorder());
		panel.add(new JLabel("Client n°" + id));
		return panel;
	}

	private void actualise() {
		titre.setText("Clients connectés : " + panels.size());
		liste.revalidate();
		liste.repaint();
	}

}
