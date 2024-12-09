package Vue;

import javax.swing.*;
import java.awt.*;

public class Accueil extends JFrame {
	private JPanel pnlBtns;

	private JButton btnCreerQuestion;
	private JButton btnCreerEval;
	private JButton btnParametres;

	private JLabel lblTitre;

	public Accueil() {
		this.setTitle("Accueil");
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);

		// Création du panel avec GridBagLayout pour centrer les boutons
		pnlBtns = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10); // marges entre les boutons
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE; // pour empiler les boutons verticalement

		// Création des boutons de même taille
		btnCreerQuestion = new JButton("Créer une question");
		btnCreerEval = new JButton("Créer une évaluation");
		btnParametres = new JButton("Paramètres");

		// Définir la même taille pour tous les boutons
		Dimension btnSize = new Dimension(200, 50);
		btnCreerQuestion.setPreferredSize(btnSize);
		btnCreerEval.setPreferredSize(btnSize);
		btnParametres.setPreferredSize(btnSize);

		// Ajout des boutons au panel avec les contraintes
		pnlBtns.add(btnCreerQuestion, gbc);
		pnlBtns.add(btnCreerEval, gbc);
		pnlBtns.add(btnParametres, gbc);

		// Ajout du panel à la fenêtre
		this.add(pnlBtns);

		this.setVisible(true);
	}
}