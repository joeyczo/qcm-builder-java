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

		pnlBtns = new JPanel(new GridBagLayout());
		GridBagConstraints gbc = new GridBagConstraints();
		gbc.insets = new Insets(10, 10, 10, 10);
		gbc.fill = GridBagConstraints.HORIZONTAL;
		gbc.gridx = 0;
		gbc.gridy = GridBagConstraints.RELATIVE;

		lblTitre = new JLabel("QCM Builder");
		lblTitre.setFont(new Font("Arial", Font.BOLD, 30));

		btnCreerQuestion = new JButton("Créer une question");
		btnCreerEval = new JButton("Créer une évaluation");
		btnParametres = new JButton("Paramètres");

		Dimension btnSize = new Dimension(200, 50);
		btnCreerQuestion.setPreferredSize(btnSize);
		btnCreerEval.setPreferredSize(btnSize);
		btnParametres.setPreferredSize(btnSize);

		pnlBtns.add(lblTitre);
		pnlBtns.add(btnCreerQuestion, gbc);
		pnlBtns.add(btnCreerEval, gbc);
		pnlBtns.add(btnParametres, gbc);

		this.add(pnlBtns);

		this.setVisible(true);
	}
}