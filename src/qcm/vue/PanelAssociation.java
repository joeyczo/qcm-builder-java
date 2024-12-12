package qcm.vue;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class PanelAssociation extends JPanel implements ActionListener
{
    private JTextArea txtQuestion;
    private JScrollPane scQuestion;

    private ArrayList<JTextArea> lstTxtReponses;
    private ArrayList<JTextArea> lstTxtDefinitions;
    private ArrayList<JButton> lstBtnSupprimerGauche; // Boutons à gauche
    private ArrayList<JButton> lstBtnSupprimerDroite; // Boutons à droite

    private JButton btnAjouter;
    private JButton btnExplication;

    public PanelAssociation()
    {
        this.txtQuestion = new JTextArea("Ajouter une question", 10, 20);
        this.scQuestion = new JScrollPane(this.txtQuestion, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        this.lstTxtReponses = new ArrayList<>();
        this.lstTxtDefinitions = new ArrayList<>();
        this.lstBtnSupprimerGauche = new ArrayList<>();
        this.lstBtnSupprimerDroite = new ArrayList<>();

        this.btnAjouter = new JButton("Ajouter");
        this.btnExplication = new JButton("Explication");

        Dimension buttonSize = new Dimension(80, 30); // Par exemple, 80x30
        this.btnAjouter.setPreferredSize(buttonSize);
        this.btnExplication.setPreferredSize(buttonSize);

        // Ajouter deux réponses et définitions par défaut
        addDefinition("Ajouter une définition");
        addReponse("Ajouter une réponse");

        // Layout
        this.setLayout(new GridBagLayout());

        // Activation des événements
        this.btnAjouter.addActionListener(this);
        this.btnExplication.addActionListener(this);

        // Afficher l'IHM
        majIHM();
    }

    @Override
    public void actionPerformed(ActionEvent e)
    {
        if (e.getSource() == this.btnAjouter) {
            // Ajouter une nouvelle définition et une nouvelle réponse
            addDefinition("Ajouter une définition");
            addReponse("Ajouter une réponse");
            majIHM();
        }

        if(e.getSource() == this.btnExplication){
            JTextArea textArea = new JTextArea(10, 30);
            JScrollPane scrollPane = new JScrollPane(textArea);

            int result = JOptionPane.showConfirmDialog(
                    this,
                    scrollPane,
                    "Ajouter explication",
                    JOptionPane.OK_CANCEL_OPTION,
                    JOptionPane.PLAIN_MESSAGE
            );

            if (result == JOptionPane.OK_OPTION) {
                String explication = textArea.getText();
                System.out.println("Explication entrée : " + explication);
            }
        }
    }

    private void addReponse(String text)
    {
        // Ajouter une nouvelle réponse avec un bouton de suppression
        JTextArea newReponse = new JTextArea(text, 2, 20);
        JScrollPane scReponse = new JScrollPane(newReponse, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton btnSupprimerDroite = new JButton("X");

        // Rendre les boutons plus petits (par exemple 20x20)
        btnSupprimerDroite.setPreferredSize(new Dimension(20, 20));

        this.lstTxtReponses.add(newReponse);
        this.lstBtnSupprimerDroite.add(btnSupprimerDroite);
    }

    private void addDefinition(String text) {
        // Ajouter une nouvelle définition avec un bouton de suppression
        JTextArea newDefinition = new JTextArea(text, 2, 20);
        newDefinition.setPreferredSize(new Dimension(200, 50)); // Définir une taille uniforme pour tous les JTextArea
        JScrollPane scDefinition = new JScrollPane(newDefinition, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

        JButton btnSupprimerGauche = new JButton("X");
        btnSupprimerGauche.setPreferredSize(new Dimension(20, 20)); // Taille du bouton

        this.lstTxtDefinitions.add(newDefinition);
        this.lstBtnSupprimerGauche.add(btnSupprimerGauche);
    }

    private void majIHM() {
        // Effacer toutes les anciennes réponses
        this.removeAll();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        gbc.gridx = 0;
        gbc.gridy = 0;
        this.add(new JLabel("Question :"), gbc);

        // Réafficher la question
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 4; // La question prend toute la ligne
        this.add(this.scQuestion, gbc);

        // Réafficher les définitions et réponses avec leurs boutons
        gbc.gridwidth = 1; // Réinitialiser gridwidth
        int yIndex = 2;

        for (int i = 0; i < this.lstTxtDefinitions.size(); i++) {
            // Bouton de suppression à gauche de la définition
            gbc.gridx = 0;
            gbc.gridy = yIndex;
            this.add(this.lstBtnSupprimerGauche.get(i), gbc);

            // Première définition (colonne 1) avec taille uniforme
            gbc.gridx = 1;
            JScrollPane scDefinition = new JScrollPane(this.lstTxtDefinitions.get(i), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            gbc.weightx = 1.0;
            this.add(scDefinition, gbc);

            // Bouton de suppression à droite de la réponse
            gbc.gridx = 2;
            this.add(this.lstBtnSupprimerDroite.get(i), gbc);

            // Première réponse (colonne 3) avec taille uniforme
            gbc.gridx = 3;
            JScrollPane scReponse = new JScrollPane(this.lstTxtReponses.get(i), JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
            gbc.weightx = 1.0;
            this.add(scReponse, gbc);

            yIndex++;
        }

        // Ajouter le bouton "Ajouter" et "Explication"
        gbc.gridx = 0;
        gbc.gridy = yIndex;
        gbc.gridwidth = 4;
        JPanel pnlButtons = new JPanel();
        pnlButtons.add(this.btnAjouter);
        pnlButtons.add(this.btnExplication);
        this.add(pnlButtons, gbc);

        // Rafraîchir l'interface
        this.revalidate();
        this.repaint();
    }
}