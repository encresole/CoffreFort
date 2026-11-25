package view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.DefaultListModel;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class Accueil extends JPanel {
	private static final long serialVersionUID = 1L;

	private final ViewManager viewManager;
	private final DefaultListModel<String> modele = new DefaultListModel<>();
	private final JList<String> liste = new JList<>(modele);

	private final JButton importer = new JButton("Importer un fichier");
	private final JButton telecharger = new JButton("Télécharger");
	private final JButton supprimer = new JButton("Supprimer");

	public Accueil(ViewManager viewManager) {
		this.viewManager = viewManager;
		this.setLayout(new BorderLayout(5, 5));

		this.add(new JLabel("Mes fichiers", JLabel.CENTER), BorderLayout.NORTH);

		liste.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		this.add(new JScrollPane(liste), BorderLayout.CENTER);

		JPanel actions = new JPanel(new FlowLayout());
		actions.add(importer);
		actions.add(telecharger);
		actions.add(supprimer);
		this.add(actions, BorderLayout.SOUTH);

		majBoutons();
		liste.addListSelectionListener(e -> majBoutons());

		importer.addActionListener(e -> importerFichier());
		telecharger.addActionListener(e -> telechargerSelection());
		supprimer.addActionListener(e -> supprimerSelection());
	}

	private void majBoutons() {
		boolean selection = !liste.isSelectionEmpty();
		telecharger.setEnabled(selection);
		supprimer.setEnabled(selection);
	}

	public void setFichiers(List<String> noms) {
		modele.clear();
		noms.forEach(modele::addElement);
	}

	private void importerFichier() {
		JFileChooser chooser = new JFileChooser();
		if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
			File fichier = chooser.getSelectedFile();
			viewManager.client.envoieFichier(fichier);
		}
	}

	private void telechargerSelection() {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		if (chooser.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
			for (String nom : liste.getSelectedValuesList()) {
				viewManager.client.demandeFichier(nom, chooser.getSelectedFile());
			}
		}
	}

	private void supprimerSelection() {
		for (String nom : liste.getSelectedValuesList()) {
			viewManager.client.supprimeFichier(nom);
		}
	}

}
