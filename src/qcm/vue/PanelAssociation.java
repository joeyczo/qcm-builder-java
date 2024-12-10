package qcm.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelAssociation extends JPanel implements ActionListener
{
    private JTextArea txtQuestion;
    private ArrayList<JTextField> lstTxtReponses;
    private ArrayList<JButton> lstBtnSupprimerGauche; // Boutons à gauche
    private ArrayList<JButton> lstBtnSupprimerDroite; // Boutons à droite

    private JButton btnAjouterReponse;
    private JButton btnValider;

    public PanelAssociation()
    {
        this.txtQuestion = new JTextArea("Ajouter une question", 10, 20);
        this.lstTxtReponses = new ArrayList<>();
        this.lstBtnSupprimerGauche = new ArrayList<>();
        this.lstBtnSupprimerDroite = new ArrayList<>();

        this.btnAjouterReponse = new JButton("+");
        this.btnValider = new JButton("Valider");

        // Ajouter deux réponses par défaut
        addReponse("Ajouter une définition/réponse");
        addReponse("Ajouter une définition/réponse");

        // Layout
        this.setLayout(new GridBagLayout());

        // Activation des événements
        this.btnAjouterReponse.addActionListener(this);

        // Afficher l'IHM
        majIHM();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAjouterReponse) {
            // Ajouter une nouvelle réponse
            addReponse("Ajouter une définition/réponse");
            majIHM();
        } else {
            // Gestion de la suppression des réponses
            for (int i = 0; i < this.lstBtnSupprimerGauche.size(); i++) {
                if (e.getSource() == this.lstBtnSupprimerGauche.get(i) || e.getSource() == this.lstBtnSupprimerDroite.get(i)) {
                    this.lstTxtReponses.remove(i);
                    this.lstBtnSupprimerGauche.remove(i);
                    this.lstBtnSupprimerDroite.remove(i);
                    majIHM();
                    break;
                }
            }
        }
    }

    private void addReponse(String text)
    {
        // Ajouter une nouvelle réponse avec un bouton de suppression
        JTextField newReponse = new JTextField(text, 20);
        JButton btnSupprimerGauche = new JButton("X");
        JButton btnSupprimerDroite = new JButton("X");

        // Rendre les boutons plus petits (par exemple 20x20)
        btnSupprimerGauche.setPreferredSize(new Dimension(20, 20));
        btnSupprimerDroite.setPreferredSize(new Dimension(20, 20));

        // Ajouter les actions listeners pour les boutons
        btnSupprimerGauche.addActionListener(this);
        btnSupprimerDroite.addActionListener(this);

        this.lstTxtReponses.add(newReponse);
        this.lstBtnSupprimerGauche.add(btnSupprimerGauche);
        this.lstBtnSupprimerDroite.add(btnSupprimerDroite);
    }

    private void majIHM()
    {
        // Effacer toutes les anciennes réponses
        this.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Réafficher la question
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2; // La question prend toute la ligne
        this.add(this.txtQuestion, gbc);

        // Réafficher les réponses avec leurs boutons, 2 par ligne
        gbc.gridwidth = 1; // Réinitialiser gridwidth
        int yIndex = 1;
        int xIndex = 0;

        for (int i = 0; i < this.lstTxtReponses.size(); i += 2) {
            // Bouton de suppression à gauche de la première réponse
            gbc.gridx = 0;
            gbc.gridy = yIndex;
            this.add(this.lstBtnSupprimerGauche.get(i), gbc);

            // Première réponse (colonne 1)
            gbc.gridx = 1;
            this.add(this.lstTxtReponses.get(i), gbc);

            // Si une deuxième réponse existe dans cette paire
            if (i + 1 < this.lstTxtReponses.size()) {
                // Seconde réponse (colonne 2)
                gbc.gridx = 2;
                this.add(this.lstTxtReponses.get(i + 1), gbc);

                // Bouton de suppression à droite de la deuxième réponse
                gbc.gridx = 3;
                this.add(this.lstBtnSupprimerDroite.get(i + 1), gbc);
            }

            // Passer à la ligne suivante après deux réponses
            yIndex++;
        }

        // Ajouter le bouton "+" en bas à gauche
        gbc.gridx = 0;
        gbc.gridy = yIndex;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;
        this.add(this.btnAjouterReponse, gbc);

        // Redessiner et revalider l'IHM
        this.revalidate();
        this.repaint();
    }
}